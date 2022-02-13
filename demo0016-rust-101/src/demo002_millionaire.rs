use std::str::FromStr;
use poc_framework::solana_program::pubkey::Pubkey;
use poc_framework::{LocalEnvironment, solana_program};
use poc_framework::solana_sdk::system_program;
use poc_framework::solana_client::rpc_client::RpcClient;
use poc_framework::solana_program::native_token::sol_to_lamports;
use poc_framework::solana_program::program_pack::Pack;
use poc_framework::solana_sdk::{
    commitment_config::CommitmentConfig,
    signature::{Keypair, Signer},
};
use solend_token_lending::state::Reserve;
pub fn demo002() {
    // clone state with BTC reserve from cluster

    let solend_program_key =
        Pubkey::from_str("So1endDq2YkqhipRh3WViPa8hdiSpxWy6z3Z6tMCpAo").unwrap();
    let lending_market_key =
        Pubkey::from_str("4UpD2fh7xH3VP9QQaXtsS1YY3bxzWhtfpks7FatyKvdY").unwrap();
// BTC reserve
    let reserve_key = Pubkey::from_str("GYzjMCXTDue12eUGKKWAqtF5jcBYNmewr6Db6LaguEaX").unwrap();

    let url = "https://solana-api.projectserum.com".to_string();
    let mainnet_client = RpcClient::new_with_commitment(url, CommitmentConfig::confirmed());
    let data = mainnet_client.get_account(&reserve_key).unwrap().data;

    let reserve = Reserve::unpack(&data).unwrap();

// accounts we will use
    let attacker = Keypair::new();
//     let obligation = Keypair::new();
//     let liquidity_ata =
//         get_associated_token_address(&attacker.pubkey(), &reserve.liquidity.mint_pubkey);
//     let collateral_ata =
//         get_associated_token_address(&attacker.pubkey(), &reserve.collateral.mint_pubkey);

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
}
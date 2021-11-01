package com.niuge.demos.redis;

import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestWeb3j {
    BlockingQueue blockingQueue = new ArrayBlockingQueue<>(10);
    private Web3jConfig web3jConfig = new Web3jConfig();

    // 线程池
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, blockingQueue);
    ;
    // 关闭程序时候记得调用dispose()
    private Disposable subscribe;

    @Test
    public void test() throws Exception {
        System.out.println("222222");
        // 获取web3j实例
        Web3j web3j = web3jConfig.getWeb3j();

        BigInteger max = BigInteger.valueOf(13487165l);

//    // 从上一次的最高块+1开始同步到最新块 注意第三个参数true代表获取全量的transaction数据
//
        web3j.replayPastBlocksFlowable(
                DefaultBlockParameter.valueOf(max.add(BigInteger.ONE)),
                DefaultBlockParameterName.LATEST,
                true);
    }

    public void execute(Runnable r) {
//        executorService.execute(r);
    }

    public void executeBlock(EthBlock block) {
        execute(() -> {
            System.out.println("nihao3");
            // 获取到所需的块信息
            EthBlock.Block ethBlock = block.getBlock();
            // transaction信息获取
            executeTransaction(ethBlock.getTransactions());
        });
    }

    public void executeTransaction(List<EthBlock.TransactionResult> transactions) {
        System.out.println();
        System.out.println("size is:" + transactions.size());
        System.out.println();
        System.out.println();
        if (transactions.size() == 0) {
            return;
        }
        try {
            Web3j web3j = web3jConfig.getWeb3j();
            for (EthBlock.TransactionResult<EthBlock.TransactionObject> transactionResult : transactions) {
                EthBlock.TransactionObject transaction = transactionResult.get();
                // log 数据的获取 记得过滤一下已经removed 的log数据
//        executeLog(receipt.getLogs());
                // TransactionReceipt 数据获取
                TransactionReceipt receipt = web3j.ethGetTransactionReceipt(transaction.getHash()).send().getResult();
                List<Log> logs = receipt.getLogs();
                System.out.println("logs are: " + logs);
            }
        } catch (IOException e) {
            log.error("transaction input error, msg:{}", e.getMessage());
        }
    }
}

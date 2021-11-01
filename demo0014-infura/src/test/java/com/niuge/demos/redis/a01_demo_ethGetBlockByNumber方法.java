package com.niuge.demos.redis;

import io.reactivex.disposables.Disposable;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通过ethGetBlockByNumber方法，可以获取当前最大区块的number.
 */
public class a01_demo_ethGetBlockByNumber方法 {
    BlockingQueue blockingQueue = new ArrayBlockingQueue<>(10);
    private Web3jConfig web3jConfig = new Web3jConfig();

    // 线程池
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, blockingQueue);

    // 关闭程序时候记得调用dispose()
    private Disposable subscribe;

    @Test
    public void test_获取ethBlockMaxNumber() throws Exception {
        // 获取web3j实例
        Web3j web3j = web3jConfig.getWeb3j();

        EthBlock ethBlock =
            web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();

        System.out.println(ethBlock.getBlock().getTransactions().size());
    }
}

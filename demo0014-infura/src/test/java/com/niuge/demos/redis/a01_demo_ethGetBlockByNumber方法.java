package com.niuge.demos.redis;

import io.reactivex.disposables.Disposable;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

//        System.out.println(ethBlock.getBlock().getTransactions().size());
        System.out.println(ethBlock.getBlock().getNumber());
    }
    public static BigInteger hexToBigInteger(String strHex) {
        if (strHex.length() > 2) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                strHex = strHex.substring(2);
            }
            BigInteger bigInteger = new BigInteger(strHex, 16);
            return bigInteger;
        }
        return null;
    }
    @Test
    public void decodeInput() throws Exception {
        String inputData = "0xa0712d680000000000000000000000000000000000000000000000000000000000000006";
        String method = inputData.substring(0, 10);
        System.out.println(hexToAddress(method));
        System.out.println(method);
        String to = inputData.substring(10, 74);
        String value = inputData.substring(74);
        Method refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
        refMethod.setAccessible(true);
        Address address = (Address) refMethod.invoke(null, to, 0, Address.class);
        System.out.println(address.toString());
        Uint256 amount = (Uint256) refMethod.invoke(null, value, 0, Uint256.class);
        System.out.println(amount.getValue());
    }
    public static String hexToAddress(String strHex) {
        if (strHex.length() > 42) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                strHex = strHex.substring(2);
            }
            strHex = strHex.substring(24);
            return "0x" + strHex;
        }
        return null;
    }
    @Test
    public void t() {
        String inputData = "0xa0712d680000000000000000000000000000000000000000000000000000000000000006";
        String to = inputData.substring(10, 74);
        System.out.println(hexToAddress(to));
        String value = inputData.substring(74);
        BigInteger bigInteger = hexToBigInteger(value);
        System.out.println(bigInteger);
    }
}

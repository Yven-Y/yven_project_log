package com.yven.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author yven
 * @date 2018/8/13
 */
public class LogbackTest {

    private static Logger log = LoggerFactory.getLogger(LogbackTest.class);

    public static void main(String[] args) throws InterruptedException {
        int messageSize = 1000000;
        int threadSize = 50;
        final int everySize = messageSize / threadSize;

        final CountDownLatch cdl = new CountDownLatch(threadSize);
        long startTime = System.currentTimeMillis();
        for (int ts = 0; ts < threadSize; ts++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int es = 0; es < everySize; es++) {
                        log.info("======info");
                    }
                    cdl.countDown();
                }
            }).start();
        }

        cdl.await();
        long endTime = System.currentTimeMillis();
        System.out.println("logback:messageSize = " + messageSize
                + ",threadSize = " + threadSize + ",costTime = "
                + (endTime - startTime) + "ms");
    }
}

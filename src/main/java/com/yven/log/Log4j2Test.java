package com.yven.log;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yven.log.thread.*;


/**
 * @author yven
 * @date 2018/8/13
 */
public class Log4j2Test {
    private static final Log log = LogFactory.getLog(Log4j2Test.class);
//    private static final Logger log = LogManager.getLogger(Log4j2Test.class);

    public static void main(String[] args) throws InterruptedException {

        //全异步模式
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        Log4j2Test test = new Log4j2Test();
        test.runThread();
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

    public void runThread(){
        for (int i = 0; i < 1; i++) {
            new Thread(new ThreadA()).start();
            new Thread(new ThreadB()).start();
            new Thread(new ThreadC()).start();
            new Thread(new ThreadD()).start();
        }
    }
}

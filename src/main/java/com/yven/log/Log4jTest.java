package com.yven.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yven
 * @date 2018/8/13
 */
public class Log4jTest {

    private static Logger log = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) throws InterruptedException {

        Log4jTest test = new Log4jTest();
        test.runThread();
    }

    public void runThread(){
        for (int i = 0; i < 500; i++) {
            new Thread(new ThreadA()).start();
            new Thread(new ThreadB()).start();
            new Thread(new ThreadC()).start();
            new Thread(new ThreadD()).start();
        }
    }


    class ThreadA implements Runnable{
        @Override
        public void run() {
            while(true){
                log.error(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class ThreadB implements Runnable{
        @Override
        public void run() {
            while(true){
                log.warn(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class ThreadC implements Runnable{
        @Override
        public void run() {
            while (true) {
                for (int i = 1000; i < 9999; i++) {
                    log.info("info from=\"" + i + "\" to=\"" + (i + 5) + "\"");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    class ThreadD implements Runnable{
        @Override
        public void run() {
            while(true){
                log.debug(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }        }
    }
}


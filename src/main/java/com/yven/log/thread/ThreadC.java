package com.yven.log.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadC implements Runnable{

    private static final Log log = LogFactory.getLog(ThreadC.class);

    @Override
    public void run() {

        while(true){
            log.info(Thread.currentThread().getName());
            for (int i = 1200; i < 1299; i++) {
                log.info(Thread.currentThread().getName()+" from=\""+i+"   to=\""+i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

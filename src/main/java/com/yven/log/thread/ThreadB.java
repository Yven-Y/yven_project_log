package com.yven.log.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadB implements Runnable{

    private static final Log log = LogFactory.getLog(ThreadB.class);

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

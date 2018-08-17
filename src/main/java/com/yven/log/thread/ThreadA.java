package com.yven.log.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadA implements Runnable{

    private static final Log log = LogFactory.getLog(ThreadA.class);

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

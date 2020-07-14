package com.sf.autotest.common;

import java.util.concurrent.*;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-10 14:18
 */
public class MultiData {
    public static void main(String[] args) {
//        ExecutorService pool = new ThreadPoolExecutor(2, 4,
//                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 5000; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.submit(new Task());
        }
        pool.shutdown();

    }


}
class Task implements Runnable{

    @Override
    public void run() {
        new SendData().sendXml("opt_35.xml");
    }
}
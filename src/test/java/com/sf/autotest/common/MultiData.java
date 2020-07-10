package com.sf.autotest.common;

import java.util.concurrent.*;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-10 14:18
 */
public class MultiData {
    public static void main(String[] args) {
        //创建指定动态范围(2-4)的线程池
        ExecutorService pool = new ThreadPoolExecutor(2, 4,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < 10000; i++) {
            pool.submit(new Task());
        }
        pool.shutdown();

    }


}
class Task implements Runnable{

    @Override
    public void run() {
        new SendData().sendXml("ipt.xml");
    }
}
package it.artisan.sync;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author dkp
 * @create 2022-11-03 20:26
 */
public class CompletableFutureTest {



    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
//        threadPoolTaskExecutor.setKeepAliveSeconds();
//        threadPoolTaskExecutor.setThreadFactory();
//        threadPoolTaskExecutor.setRejectedExecutionHandler();
//        threadPoolTaskExecutor.setQueueCapacity();
//        threadPoolTaskExecutor.set
//        CompletableFuture.runAsync()
//        CompletableFuture.supplyAsync()
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(new ThreadRunnable01(),
                threadPoolTaskExecutor);

        CompletableFuture.supplyAsync(null,threadPoolTaskExecutor);
    }
}


class ThreadRunnable01 implements Runnable{

    @Override
    public void run() {
        System.out.println("实现runnable 接口的线程");
    }
}

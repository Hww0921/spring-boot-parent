package it.artisan.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 1.核心线程数:corePoolSize 初始化的数量,不因任务数据的多少而改变
 * 2.最大线程数量：maximumPoolSize 使用该线程池的任务，最大启动的线程数量
 * 3.表示空闲线程（最大线程-核心线程）的存活时间
 * 4.存活时间单位
 * 5.用于缓存任务的阻塞队列,核心线程数量满之后 任务进入阻塞队列
 * 6.threadFactory：指定创建线程的工厂
 * 7.handler：表示当workQueue已满，且池中的线程数达到maximumPoolSize时，线程池拒绝添加新任务时采取的策略。
 *
 * 核心线程数-->阻塞队列-->最大线程数-->阻塞队列
 * @author dkp0911
 */
@Configuration
public class ThreadPoolConfig {

        @Bean("lineageThread")
        public TaskExecutor lineageThread(){
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            //设置核心线程数
            executor.setCorePoolSize(4);
            //设置最大线程数
            executor.setMaxPoolSize(50);
            //
            executor.setKeepAliveSeconds(60);
            //设置队列容量
            executor.setQueueCapacity(100);
            //拒绝策略
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            //
            executor.setWaitForTasksToCompleteOnShutdown(true);

            return executor;
        }
}

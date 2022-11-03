package it.artisan.entity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dkp
 * @create 2022-11-03 22:06
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws Exception{
        UserInfoService userInfoService = new UserInfoService();
        Long time = 999L;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                UserInfo userInfo = userInfoService.getUserInfo(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },executorService);
        voidCompletableFuture.get();

        CompletableFuture<UserInfo> uCompletableFuture = CompletableFuture.supplyAsync(() -> {
            UserInfo userInfo = new UserInfo();
            try {
                userInfo = userInfoService.getUserInfo(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return userInfo;
        },executorService);
        UserInfo userInfo = uCompletableFuture.get();
        System.out.println(userInfo);
    }
}

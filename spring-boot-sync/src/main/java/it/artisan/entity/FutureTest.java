package it.artisan.entity;

import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        UserInfoService userInfoService = new UserInfoService();
        MedalService medalService = new MedalService();

        long userId =666L;
        long startTime = System.currentTimeMillis();

        //调用用户服务获取用户基本信息
        FutureTask<UserInfo> userInfoFutureTask = new FutureTask<>(
                () -> userInfoService.getUserInfo(userId));
        executorService.submit(userInfoFutureTask);

        Thread.sleep(300); //模拟主线程其它操作耗时

        FutureTask<MedalInfo> medalInfoFutureTask = new FutureTask<>(new Callable<MedalInfo>() {
            @Override
            public MedalInfo call() throws Exception {
                return medalService.getMedalInfo(userId);
            }
        });
        executorService.submit(medalInfoFutureTask);

        UserInfo userInfo = userInfoFutureTask.get();//获取个人信息结果
        MedalInfo medalInfo = medalInfoFutureTask.get();//获取勋章信息结果

        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }
}

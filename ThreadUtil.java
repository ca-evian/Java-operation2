package com.captain.demo.utils;
import java.util.*;
import java.util.concurrent.*;

public class ThreadUtil {

    public static List<Object> runCheckCallable(List<Callable<Object>> l, Boolean b) {
        FutureTask<Object> futureTask1 = new FutureTask<Object>(l.get(0));
        // 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<Object> futureTask2 = new FutureTask<Object>(l.get(1));

        // 创建线程池并返回ExecutorService实例
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 执行任务
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {//  两个任务都完成
                    System.out.println("Done");

                    // 关闭线程池和服务
                    executor.shutdown();

                    List<Object> Lobject = null;
                    Lobject.add(l.get(0));
                    Lobject.add(l.get(1));
                    return Lobject;

                }

                if (!futureTask1.isDone()) { // 任务1没有完成，会等待，直到任务完成
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = (String) futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }

    }
}
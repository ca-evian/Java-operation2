package com.captain.demo.crazy;

import java.util.concurrent.*;

/**
 * 注水线程
 * Created by captain on 2017/7/27.
 */
public class InputThread implements Callable<Object>{

    private BlockingQueue<String> queue;

    public InputThread(BlockingQueue queue){
        this.queue = queue;
    }

    @Override
    public Object call() throws InterruptedException {
        while(true){

            boolean flag = true;
            for(int cnt=0;cnt<5;cnt++){
                flag = queue.offer("一立方米");
                if(!flag){
                    break;
                }
            }
            System.out.println("注水5立方米，当前水量："+queue.size());
            if(!flag){
                break;
            }
            TimeUnit.SECONDS.sleep(1);
        }
        return "success";
    }
}

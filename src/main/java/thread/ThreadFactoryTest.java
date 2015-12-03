package thread;

/**
 * User: huangqiang
 * Date: 12/3/15
 * Time: 5:42 PM
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class Task implements Runnable {
    int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "--taskId: " + taskId);
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wake up.");

    }
}

class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }

}

public class ThreadFactoryTest {
    /**
     * 开启了守护线程,所以在线程池中被调度的线程, 会随着main线程的结束而结束.
     * 不会打印出"wake up". 因为main线程关闭太快了, 3个线程还并未执行完就随着main线程而结束.
     */
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(3, new DaemonThreadFactory());
        for (int i = 0; i < 3; i++) {
            exec.submit(new Task(i));
        }
        exec.shutdown();
    }
}

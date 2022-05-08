package com.gongxm.photo.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtils {
	// 线程池相关参数
	private static final int CORE_POOL_SIZE = 5;
	private static final int MAXIMUM_POOL_SIZE = 50;
	private static final long TIMEOUT = 5L;
	private static final int QUEUE_SIZE = 5;
	private static final int MAX_TASK_COUNT = MAXIMUM_POOL_SIZE + QUEUE_SIZE;

	// 线程池对象
	private static ExecutorService threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, TIMEOUT,
			TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(QUEUE_SIZE), Executors.defaultThreadFactory(),
			new ThreadPoolExecutor.DiscardPolicy());

	private volatile static BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(); // 临时任务队列
	private static AtomicInteger taskCount = new AtomicInteger(); // 运行中的线程数量

	// 标记
	private volatile static boolean scan = false;

	/**
	 * 在线程池中执行线程任务
	 * 
	 * @param task
	 * @throws InterruptedException 
	 */
	public static void execute(Runnable task) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				task.run();
				taskCount.getAndDecrement();
			}
		};
		taskQueue.add(runnable);
		if (!scan) {
			scan = true;
			startScan();
		}
	}

	// 开启任务扫描
	private static void startScan() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				int count = taskCount.get();
				while (count < MAX_TASK_COUNT && taskQueue.size() > 0) {
					count = taskCount.incrementAndGet();
					Runnable task = taskQueue.remove();
					threadPool.execute(task);
				}
				if (taskQueue.size() == 0) {
					scan = false;
					timer.cancel();
				}
			}
		};

		timer.schedule(task, 0, 2000);
	}

	public static int getTaskCount() {
		return taskCount.get();
	}

	public static int getTaskQueueSize() {
		return taskQueue.size();
	}


}

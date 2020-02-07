package com.gongxm.photo.utils;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPoolUtils {
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 500, 60L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>()); 

	private static LinkedList<Runnable> tasks = new LinkedList<>();
	private static int taskCount = 0; // 运行中的线程数量
	private static final int MAX = 50; // 最大线程数量
	private static final int INTERVAL = 100;// 间隔时间扫描任务集合(毫秒)
	private static final int THREAD_TIME_OUT = 1200; // 线程执行超时时间(秒)

	// 静态代码块
	static {
		new Thread() {
			public void run() {
				while (true) {
					try {
						if (tasks.size() > 0) {
							taskCount++;
							System.out.println("总任务数量:"+tasks.size()+", 运行中的任务数量:"+taskCount);
							Runnable task = tasks.removeFirst();
							execute(task);
							while (taskCount > MAX) {
								try {
									Thread.sleep(INTERVAL);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						Thread.sleep(INTERVAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	/**
	 * 把线程任务添加到任务队列中
	 * 
	 * @param task
	 */
	public static void executeOnNewThread(Runnable task) {
		tasks.add(task);
	}

	/**
	 * 在线程池中执行线程任务
	 * 
	 * @param task
	 */
	public static void execute(Runnable task) {
		Runnable run = new Runnable() {
			public void run() {
				Future<?> future = executor.submit(task);
				try {
					future.get(THREAD_TIME_OUT, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					future.cancel(true);// 取消任务
					System.out.println("任务超时,取消任务!");
				} finally {
					taskCount--;
				}
			};
		};
		executor.execute(run);
	}


}

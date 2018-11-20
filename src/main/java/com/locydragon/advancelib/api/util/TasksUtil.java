package com.locydragon.advancelib.api.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TasksUtil {
	private static Executor threadPool = Executors.newCachedThreadPool();
	public static void runAsync(Runnable task) {
		threadPool.execute(task);
	}

	public static void runAsyncLater(Runnable task, int second) {
		threadPool.execute(() -> {
			try {
				Thread.sleep(second * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			task.run();
		});
	}
}

package com.sg.sample.juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 3氵哥
 * @date:   2018年5月16日 下午10:10:49   
 *     
 * @Copyright: 2018 . All rights reserved.
 */
public class CyclicBarrierTest {

	private final static Logger logger = LoggerFactory.getLogger(CyclicBarrierTest.class);

	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier cb = new CyclicBarrier(4);
		//初始化运行 4个异步线程
		for (int i = 0; i < 4; i++) {
			new CbThread(cb).start();
		}

		Thread.sleep(Integer.MAX_VALUE);
		logger.info("main thread end.");
	}

	/**
	 * @Description:测试线程类
	 * @author: 3氵哥
	 * @date:   2018年5月16日 下午10:11:05   
	 *     
	 * @Copyright: 2018 . All rights reserved.
	 */
	public static class CbThread extends Thread {

		private static final AtomicInteger seq = new AtomicInteger(1);

		private CyclicBarrier cb;

		private int id;

		public CbThread(CyclicBarrier cb) {
			this.id = seq.getAndIncrement();
			this.cb = cb;
		}

		@Override
		public void run() {
			try {
				int sleepTime = RandomUtils.nextInt(0, 10);
				logger.info("my id is {}, i want to sleep {} s", new Object[] { id, sleepTime });
				Thread.sleep(sleepTime * 1000);
				logger.info("i wake up. id is {}", new Object[] { id });
				cb.await();
				logger.info("done. id is : " + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

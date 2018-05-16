/**  
 * All rights Reserved, Designed By 3氵哥
 * @Title:  CountDownLatchTest.java   
 * @Package com.sg.sample   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 3氵哥     
 * @date:   2018年5月16日 下午10:15:43   
 * @version V1.0 
 * @Copyright: 2018 . All rights reserved. 
 */
package com.sg.sample.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 3氵哥
 * @date: 2018年5月16日 下午10:15:43
 * 
 * @Copyright: 2018 . All rights reserved.
 */
public class CountDownLatchTest {

	private final static Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

	public static void main(String[] args) {
		try {
			int latchNum = 5;
			CountDownLatch cdl = new CountDownLatch(latchNum);
			for (int i = 0; i < 10; i++) {
				new CdlThread(cdl).start();
			}
			Thread.sleep(2 * 1000);
			new CountDownThread(cdl).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class CdlThread extends Thread {

		private static final AtomicInteger seq = new AtomicInteger(1);

		private CountDownLatch cdl;

		private int id;

		public CdlThread(CountDownLatch cdl) {
			this.id = seq.getAndIncrement();
			this.cdl = cdl;
		}

		@Override
		public void run() {
			try {
				logger.info("i await. id is {}", new Object[] { id });
				cdl.await();
				logger.info("done. id is : " + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static class CountDownThread extends Thread {

		private final static Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

		private CountDownLatch cdl;

		public CountDownThread(CountDownLatch cdl) {
			this.cdl = cdl;
		}

		@Override
		public void run() {
			logger.info("start latch count down process.");
			try {
				for (;;) {
					if (cdl.getCount() == 0) {
						break;
					}

					logger.info("count down");
					cdl.countDown();
					int sleepTime = RandomUtils.nextInt(1, 3);
					Thread.sleep(sleepTime * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

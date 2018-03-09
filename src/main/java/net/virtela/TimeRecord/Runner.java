package net.virtela.TimeRecord;

import net.virtela.TimeRecord.utils.StopWatch;

public class Runner {

	public static void main(String[] args) throws InterruptedException {
		
		StopWatch stopWatch = new StopWatch();
		
		stopWatch.startTimer();
		Thread.sleep(2000);
		System.out.println(stopWatch.getLapElapsedTime());
		Thread.sleep(2000);
		System.out.println(stopWatch.getLapElapsedTime());
		Thread.sleep(1000);
		System.out.println(stopWatch.getLapElapsedTime());
		Thread.sleep(2000);
		System.out.println(stopWatch.getLapElapsedTime());
		System.out.println(stopWatch.getElapsedTime());
		
	}

}

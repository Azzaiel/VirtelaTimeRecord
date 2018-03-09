package net.virtela.TimeRecord.utils;

public class StopWatch {
	
	private long prevLapEnd;
	private long startTime;
	
	/**
	 *  Start the timer for lap.
	 * 
	 */
	public void startTimer() {
		this.startTime = System.currentTimeMillis();
		this.prevLapEnd = 0;
	}
	
	/**
	 * Get the elapsed time since the last lap.
	 * 
	 * @return Lap elapsed time in seconds.
	 */
	public double getLapElapsedTime() {
		long endTime = System.currentTimeMillis();
		double elapcedTime;
		if (prevLapEnd == 0) {
			elapcedTime = this.getElapsedTime();
		} else {
			elapcedTime = (double) (endTime - prevLapEnd) / (1000);
		}
		prevLapEnd = endTime;
        return elapcedTime;
	}
	
	/**
	 *  Gets the elapsed time (in seconds) since the time the object of StopWatch is started
	 * 
	 * @return Elapsed time in seconds.
	 */
	public double getElapsedTime() {
		long endTime = System.currentTimeMillis();
		return (double) (endTime - startTime) / (1000);
	}
	
}

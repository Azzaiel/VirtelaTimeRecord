package net.virtela.TimeRecord.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StopWatch {
	
	public static final String MINUTES = "Min";
	public static final String SECONDS = "Sec";
	public static final String MILLIS = "Ms";
	
	private long prevLapEnd;
	private long startTime;
	
	private static DecimalFormat DF_TWO_DECIMAL;
	
	static {
		DF_TWO_DECIMAL = new DecimalFormat("##");
		DF_TWO_DECIMAL.setRoundingMode(RoundingMode.DOWN);
	}
	
	
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
	 * @return Lap elapsed time MM Min, SS, Sec, MS Ms format.
	 */
	public String getLapElapsedTime() {
		long endTime = System.currentTimeMillis();
		String elapcedTime;
		if (prevLapEnd == 0) {
			elapcedTime = this.getElapsedTime();
		} else {
			elapcedTime = this.toDisplayFormat(endTime - prevLapEnd);
		}
		prevLapEnd = endTime;
        return elapcedTime;
	}
	
	/**
	 *  Gets the elapsed time (in seconds) since the time the object of StopWatch is started
	 * 
	 * @return Elapsed time MM Min, SS, Sec, MS Ms format.
	 */
	public String getElapsedTime() {
		long endTime = System.currentTimeMillis();
		return this.toDisplayFormat(endTime - startTime);
	}
	
    private  String toDisplayFormat(Long millis) {
		
		final StringBuilder display = new StringBuilder();
		
		if (millis > 60_000) {
			final String minutesWhole = DF_TWO_DECIMAL.format(millis / (60_000));
			display.append(minutesWhole);
			display.append(Constants.SPACE);
			display.append(MINUTES);
			millis = (millis - (60_000 * Integer.parseInt(minutesWhole)));
		}

		if (millis > 1_000 ) {
			final String secondsWhole = DF_TWO_DECIMAL.format(millis / (1_000));
			display.append(Constants.SPACE);
			display.append(secondsWhole);
			display.append(Constants.SPACE);
			display.append(SECONDS);
			millis = (millis - (1_000 * Integer.parseInt(secondsWhole)));
		}
		
		if (millis > 0) {
			display.append(Constants.SPACE);
			display.append(millis);
			display.append(Constants.SPACE);
			display.append(MILLIS);
		} 
		
		return display.toString();
	}
	
}

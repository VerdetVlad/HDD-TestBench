package timer;

public class Timer implements ITimer {
	long start_time, end_time, elapsed = 0, total = 0;

	/**
	 * The first time for the timer is saved and the elapsed and total time is initialized 
	 */
	@Override
	public void start() {
		start_time = System.nanoTime();
		elapsed = 0;
		total = 0;
	}
	
	/**
	 * The timer is paused, the current cycle elapsed time and total time are updated
	 *@return Elapsed <b>nanoseconds</b> from the last starting point
	 */
	@Override
	public long pause() {
		end_time = System.nanoTime();
		elapsed = end_time - start_time;
		total += elapsed;
		return elapsed;
	}

	/**
	 * The time recovers from a pause
	 */
	@Override
	public void resume() {
		start_time = System.nanoTime();
	}

	/**
	 * Stop the timer and update the elapsed and total time for the last time
	 * @return Total <b>nanoseconds</b> from the first starting point
	 */
	@Override
	public long stop() {
		if(start_time > end_time) {
			end_time = System.nanoTime();
			elapsed = end_time - start_time;
			total += elapsed;
		}
		return total;
	}

}

package timer;

public interface ITimer {
	void start();
	long pause();
	void resume();
	long stop();
}

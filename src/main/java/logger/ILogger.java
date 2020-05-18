package logger;

public interface ILogger {
	void write(Object...args);
//	String writeTime(long value, TimeUnit timeUnit);
	void close();
}

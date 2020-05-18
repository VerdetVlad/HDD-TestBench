package bench;

public interface IBenchmark {
	void initialize();	void initialize(Object...init_params);
	void run();			void run(Object...run_params);
	void warmUp();
	void clean();
	void cancel();
	String getResult();
}
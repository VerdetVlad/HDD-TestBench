package bench;

public interface IBenchmark {


	//creates file and opens them
	void initialize();


	//options[0] == fixedFile/fixedBuffer
	//options[1] == clean or not
	void run(int[] buffer, int[] file, int[] options);


	//run for smallest value
	void warmUp();


	//delete files
	void clean();


	//stops forever
	void cancel();


	//The data to be put in the text box
	String getResult();

}
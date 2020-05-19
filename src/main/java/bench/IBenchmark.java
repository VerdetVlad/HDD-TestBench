package bench;

public interface IBenchmark {


	//creates file and opens them
	//options[0] == fixedFile/fixedBuffer
	//options[1] == clean or not
	void initialize(int[] buffer, int[] file, boolean[] options, int indexDif);


	//run for the parameters given in initailize
	void run();


	//run for smallest value
	void warmUp();


	//delete files
	void clean();


	//stops forever
	void cancel();


	//The data to be put in the text box
	String getResult();

}
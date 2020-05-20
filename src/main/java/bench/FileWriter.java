package bench;

import java.io.*;
import java.util.Random;
import timer.Timer;

public class FileWriter {
    private static Timer timer = new Timer();





    /**
     * Writes a file with random binary content on the disk, using a given file
     * path and buffer size.
     */
    public static double writeWithBufferSize(String fileName, int myBufferSize, int fileSize) throws IOException
    {

        File pathMaker = new File(System.getProperty("user.dir") + "\\filesCreated");
        pathMaker.mkdirs();

        OutputStream folderPath = new FileOutputStream(System.getProperty("user.dir") + "\\filesCreated\\" + fileName);


        // create stream writer with given buffer size
        final BufferedOutputStream outputStream = new BufferedOutputStream(folderPath, myBufferSize);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        int toWrite = fileSize / myBufferSize;
        Random rand = new Random();


        timer.start();
        while (i < toWrite) {
            // generate random content to write
            //pause
            timer.pause();
            rand.nextBytes(buffer);
            timer.resume();
            //resume

            outputStream.write(buffer);
            i++;
        }

        outputStream.close();
        return timer.stop();
    }


    public static String streamWriteFixedSize(int minIndex, int maxIndex, int fileIndex) throws IOException {
        String exportVal = "";
        int indexDiff = (maxIndex - minIndex + 1);
        int counter = 0;
        double benchScore = 0;
        double timeAvg = 0.0;
        int fileSize = ParametersSizes.fileSizes[fileIndex];

        while (counter < indexDiff) {
            int currentBufferSize = ParametersSizes.bufferSizes[minIndex + counter];
            int repeat = 10;

            while(repeat-- > 0) {
                double timeNano = writeWithBufferSize(HDDBench.path.get(counter), currentBufferSize, fileSize);
                timeAvg += timeNano / ParametersSizes.SecInNano;
            }

            timeAvg /= 10;
            double crtScore = ((double)fileSize / ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += crtScore;
            exportVal += String.format("%.2f", crtScore) + " MB/sec" + ";";

            counter++;
        }

        benchScore /= indexDiff;

        exportVal += (String.format("%.2f", benchScore) + " MB/sec");

        return exportVal;
    }



    public static String streamWriteFixedBuffer(int minIndex, int maxIndex, int bufferIndex) throws IOException {
        String exportVal = "";
        int indexDiff = (maxIndex - minIndex + 1);
        int counter = 0;
        double benchScore = 0;
        double timeAvg = 0.0;
        int bufferSize = ParametersSizes.bufferSizes[bufferIndex];

        while (counter < indexDiff) {
            int currentFileSize = ParametersSizes.fileSizes[minIndex + counter];
            int repeat = 10;

            while(repeat-- > 0) {
                double timeNano = writeWithBufferSize(HDDBench.path.get(counter), bufferSize, currentFileSize);
                timeAvg += timeNano / ParametersSizes.SecInNano;
            }

            timeAvg /= 10;
            double crtScore = ((double)currentFileSize / ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += crtScore;
            exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");

            counter++;
        }

        benchScore /= indexDiff;

        exportVal += (String.format("%.2f", benchScore) + " MB/sec");

        return exportVal;
    }
}
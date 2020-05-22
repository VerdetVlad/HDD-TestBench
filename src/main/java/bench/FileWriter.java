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

            //pause
            timer.pause();
            // generate random content to write
            rand.nextBytes(buffer);
            i++;
            //resume
            timer.resume();

            outputStream.write(buffer);

        }







        long time = timer.stop();
        outputStream.close();
        return time;
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
            int repeat = ParametersSizes.repeatPerSize;

            while(repeat-- > 0 && HDDBench.running) {
                double timeNano = writeWithBufferSize(HDDBench.path.get(counter), currentBufferSize, fileSize);
                timeAvg += timeNano / ParametersSizes.SecInNano;
            }

            if(!HDDBench.running) {
                return null;
            }

            timeAvg /= ParametersSizes.repeatPerSize;
            double crtScore = ((double)fileSize / ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += crtScore;
            if(crtScore >= 1024) {
                exportVal += (String.format("%.2f", crtScore / 1024.0) + " GB/sec" + ";");
            }
            else {
                exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");
            }

            counter++;
        }

        benchScore /= indexDiff;

        if(benchScore >= 1024) {
            exportVal += (String.format("%.2f", benchScore / 1024.0) + " GB/sec");
        }
        else {
            exportVal += (String.format("%.2f", benchScore) + " MB/sec");
        }

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
            int repeat = ParametersSizes.repeatPerSize;

            while(repeat-- > 0 && HDDBench.running) {
                double timeNano = writeWithBufferSize(HDDBench.path.get(counter), bufferSize, currentFileSize);
                timeAvg += timeNano / ParametersSizes.SecInNano;
            }

            if(!HDDBench.running) {
                return null;
            }

            timeAvg /= ParametersSizes.repeatPerSize;
            double crtScore = ((double)currentFileSize / ParametersSizes.MB_SIZE) / timeAvg;
            benchScore += crtScore;
            if(crtScore >= 1024) {
                exportVal += (String.format("%.2f", crtScore / 1024.0) + " GB/sec" + ";");
            }
            else {
                exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");
            }

            counter++;
        }

        benchScore /= indexDiff;

        if(benchScore >= 1024) {
            exportVal += (String.format("%.2f", benchScore / 1024.0) + " GB/sec");
        }
        else {
            exportVal += (String.format("%.2f", benchScore) + " MB/sec");
        }

        return exportVal;
    }
}
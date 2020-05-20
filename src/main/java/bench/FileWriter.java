package bench;

import java.io.*;
import java.util.Random;

import timer.Timer;

public class FileWriter {

    private static final int KB_SIZE = 1024; // KB
    private static final int MB_SIZE = 1024 * 1024; // MB
    private static final int SECinNANO = (int) Math.pow(10, 9);
    private static final int[] bufferSizes = {
            1 * KB_SIZE,
            2 * KB_SIZE,
            4 * KB_SIZE,
            8 * KB_SIZE,
            16 * KB_SIZE,
            32 * KB_SIZE,
            64 * KB_SIZE,
            128 * KB_SIZE,
            256 * KB_SIZE,
            512 * KB_SIZE,
            1 * MB_SIZE
    };
    private static final int[] fileSizes = {
            32 * KB_SIZE,
            64 * KB_SIZE,
            128 * KB_SIZE,
            256 * KB_SIZE,
            512 * KB_SIZE,
            1 * MB_SIZE,
            2 * MB_SIZE,
            4 * MB_SIZE,
            8 * MB_SIZE,
            16 * MB_SIZE,
            32 * MB_SIZE,
            64 * MB_SIZE
    };
    private static Timer timer = new Timer();





    /**
     * Writes a file with random binary content on the disk, using a given file
     * path and buffer size.
     */
    private static double writeWithBufferSize(String fileName, int myBufferSize, int fileSize) throws IOException
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
        int fileSize = fileSizes[fileIndex];

        while (counter < indexDiff) {
            int currentBufferSize = bufferSizes[minIndex + counter];
            double timeNano = writeWithBufferSize(HDDBench.path.get(counter), currentBufferSize, fileSize);
            timeNano/=SECinNANO;
            double crtScore = ((double)fileSize / MB_SIZE) / timeNano;
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
        int bufferSize = bufferSizes[bufferIndex];

        while (counter < indexDiff) {
            int currentFileSize = fileSizes[minIndex + counter];
            double timeNano = writeWithBufferSize(HDDBench.path.get(counter), bufferSize, currentFileSize);
            timeNano/=SECinNANO;
            double crtScore = ((double)currentFileSize / MB_SIZE) / timeNano;
            benchScore += crtScore;
            exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");

            counter++;
        }

        benchScore /= indexDiff;

        exportVal += (String.format("%.2f", benchScore) + " MB/sec");

        return exportVal;
    }
}
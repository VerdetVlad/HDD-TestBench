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
            4 * MB_SIZE
    };
    private static Timer timer = new Timer();



    public static void main(String[] args) {

        System.out.println(3^4);
    }



    /**
     * Writes a file with random binary content on the disk, using a given file
     * path and buffer size.
     */
    private static double writeWithBufferSize(String fileName, int myBufferSize, int fileSize) throws IOException
    {

        OutputStream folderPath = new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName);

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
            rand.nextBytes(buffer);
            //resume

            outputStream.write(buffer);
            i++;
        }

        return (double)(int)(timer.stop() / SECinNANO);
    }

    /**
     * Writes files on disk using a variable write buffer and fixed file size.
     *
     * @param minIndex
     *            - start buffer size index
     * @param maxIndex
     *            - end buffer size index
     * @param fileSize
     *            - size of the benchmark file to be written in the disk
     * @throws IOException
     */
    public static String streamWriteFixedSize(int minIndex, int maxIndex, int fileSize) throws IOException {
        String exportVal = "";
        int indexDiff = (maxIndex - minIndex + 1);
        int counter = 0;
        double benchScore = 0;

        while (counter < indexDiff) {
            int currentBufferSize = bufferSizes[minIndex + counter];
            double timeNano = writeWithBufferSize(HDDBench.path.get(counter), currentBufferSize, fileSize);
            double crtScore = (fileSize / MB_SIZE) / timeNano;
            benchScore += crtScore;
            exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");

            counter++;
        }

        benchScore /= indexDiff;

        exportVal += (String.format("%.2f", benchScore) + " MB/sec");

        return exportVal;
    }

    /**
     * Writes files on disk using a variable file size and fixed buffer size.
     *
     * @param minIndex
     *            - start file size index
     * @param maxIndex
     *            - end file size index
     * @param bufferSize
     *            - size of the buffer used for writing in the disk
     */

    public static String streamWriteFixedBuffer(int minIndex, int maxIndex, int bufferSize) throws IOException {
        String exportVal = "";
        int indexDiff = (maxIndex - minIndex + 1);
        int counter = 0;
        double benchScore = 0;

        while (counter < indexDiff) {
            int currentFileSize = fileSizes[minIndex + counter];
            double timeNano = writeWithBufferSize(HDDBench.path.get(counter), bufferSize, currentFileSize);
            double crtScore = (currentFileSize / MB_SIZE) / timeNano;
            benchScore += crtScore;
            exportVal += (String.format("%.2f", crtScore) + " MB/sec" + ";");

            counter++;
        }

        benchScore /= indexDiff;

        exportVal += (String.format("%.2f", benchScore) + " MB/sec");

        return exportVal;
    }
}
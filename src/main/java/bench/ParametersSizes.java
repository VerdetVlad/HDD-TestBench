package bench;

public class ParametersSizes {
    public static final int repeatPerSize = 4;
    public static final int SecInNano = (int)Math.pow(10,9);
    public static final int KB_SIZE = 1024; // KB
    public static final int MB_SIZE = 1024 * 1024; // MB
    public static final int[] bufferSizes = {
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
            1 * MB_SIZE,
            2 * MB_SIZE,
            4 * MB_SIZE,
            8 * MB_SIZE,
            16 * MB_SIZE,
            32 * MB_SIZE,

    };
    public static final int[] fileSizes = {
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
            64 * MB_SIZE,
            128 * MB_SIZE,
            256 * MB_SIZE,
            512 * MB_SIZE
    };
}

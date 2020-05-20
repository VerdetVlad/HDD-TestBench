package utilities;

public class StringManagement {

    public static double makeDoubleFromStr(String s)
    {
        String[] a = new String[2];
        a= s.split(" ");
        return Double.parseDouble(a[0]);
    }

    public static String makeReadAndWrite(String s, String bufferSize, String fileSize)
    {
        String[] a = new String[2];
        a = s.split(",");
        String res = new String("");

        res="For buffer:" + bufferSize + "and file: " + fileSize +
            "\n  =>Write speed: " + a[0] + "    Read speed: " + a[1] +"\n\n";

        return res;
    }




}

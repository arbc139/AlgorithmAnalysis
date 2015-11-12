package dy;

/**
 * Created by DYKim on 15. 10. 6..
 */
import java.io.*;

public class DYFile {
    // static constants
    private final static String OS = System.getProperty("os.name").toLowerCase();
    private final static String MAC_PATH = "/Users/DYKim/Desktop/TestCases/hw";		// + 2/input.txt, + n/2012147504.txt;
    private final static String WIN_PATH = "c:\\TestCases\\hw";

    // static functions
    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }
    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public PrintWriter fout;


    public DYFile(int homeworkNum) throws Exception {
        String path;
        if(isWindows())	path = WIN_PATH;
        else if(isMac()) path = MAC_PATH;
        else throw new Exception("Only work in window OS or mac OS");

        String output_path = path + homeworkNum + "/input.txt";

        fout = new PrintWriter(output_path);
    }

    public void close() throws Exception {
        fout.close();
    }
}

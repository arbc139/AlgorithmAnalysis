import dy.*;
import java.util.Random;

public class Main {
    static DYFile file;
    final static int N = 10;         // no of test case
    final static int MAX_DEGREE = 63;   // MAX degree

    public static void main(String[] args) throws  Exception {
        file = new DYFile(3);
        Random ran = new Random();

        file.fout.println(N);

        for(int i=0; i<N; i++) {
            int degree = ran.nextInt(MAX_DEGREE);

            file.fout.print(degree);
            for (int j = 0; j <= degree; j++) {
                file.fout.print(" " + ran.nextInt(100));
            }
            file.fout.println("");
        }

        file.close();
    }
}

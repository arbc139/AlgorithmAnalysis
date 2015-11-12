import dy.*;
import java.util.Random;

public class Main {
    public static DYFile file;

    final public static int MATRIX_SIZE = 50;
    final public static int RANDOM_RANGE = 100;
    final public static int PERCENT_OF_NO_EDGE = 5; // 1/10 chance to no edge

    public static void main(String[] args) throws Exception {
        file = new DYFile(5);

        file.fout.println(1);
        file.fout.print(MATRIX_SIZE);
        int[][] matrix = makeTestCase(MATRIX_SIZE);

        for(int[] line: matrix) {
            file.fout.println("");
            for(int i=0; i < line.length; i++) {
                if (i == line.length-1) file.fout.print(line[i]);
                else file.fout.print(line[i] + " ");
            }
        }

        file.close();
    }

    public static int[][] makeTestCase(final int size) {
        int[][] matrix = new int[size][size];

        for(int i=0; i<size; i++) {

            for(int j=i; j<size; j++) {
                if (i==j) matrix[i][j] = -1;
                else {
                    int weight = randomNumber();
                    matrix[i][j] = weight;
                    matrix[j][i] = weight;
                }
            }
        }

        return matrix;
    }

    public static int randomNumber() {
        Random rand = new Random();

        // NO EDGE CASE
        if (rand.nextInt(PERCENT_OF_NO_EDGE) == 0) return -1;
        // return choice to 1 ~ RANDOM_EDGE
        else return rand.nextInt(RANDOM_RANGE) + 1;
    }
}

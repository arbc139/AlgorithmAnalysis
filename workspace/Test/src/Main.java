public class Main {

    public static void main(String[] args) {

    }

    public static void switch1() {
        int x = 2;
        switch (x) x++;
    }

    public static void switch2() {
        int x = 2;
        switch (x)
        { x++; }
    }

    public static void switch3() {
        int x = 2;
        switch (x)
        {
            case 1: 	if (x>2)
                case 2:		x++;
            default:	break;
        }
    }
}

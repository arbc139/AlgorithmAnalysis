package dy;

/**
 * Created by DYKim on 15. 10. 6..
 */
public class DYConsole {
    public Boolean consoleTurnOn = false;

    public void println(String str) {
        if(consoleTurnOn) System.out.println(str);
    }
}

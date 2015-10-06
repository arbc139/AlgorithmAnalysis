package dy;

public class DYConsole {
    public Boolean consoleTurnOn;

    public DYConsole() {
        this.consoleTurnOn = false;
    }

    public void println(String str) {
        if(consoleTurnOn) System.out.println(str);
    }
}

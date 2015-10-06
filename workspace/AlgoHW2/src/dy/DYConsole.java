package dy;

public class DYConsole {
	public static Boolean consoleTurnOn = false;
	
	public static void println(String str) {
		if(consoleTurnOn) System.out.println(str);
	}
}

package dy;

public class DYTimeRecoder {
	private static long start;
	private static long finish;
	
	public static void startPoint() {
		start = System.currentTimeMillis();
	}
	public static void finishPoint() {
		finish = System.currentTimeMillis();
	}
	public static long runningTime() throws Exception {
		if(finish-start >= 0) return finish - start;
		else throw new Exception("Not Recording finish, yet");
	}
}

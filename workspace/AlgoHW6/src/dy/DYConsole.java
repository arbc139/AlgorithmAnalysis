package dy;

/**
 * Created by DYKim on 15. 10. 6..
 */
public class DYConsole {
    public Boolean consoleTurnOn = false;
    String classPrefix = "NONE";
    String methodPrefix = "NONE";

    public void println(Object str) {
        if(consoleTurnOn) System.out.println("<CLASS::" + classPrefix + ", METHOD::" + methodPrefix + ">" + " " + str);
    }
    public void print(Object str) {
        if(consoleTurnOn) System.out.print("<CLASS::" + classPrefix + ", METHOD::" + methodPrefix + ">" + " " + str);
    }

    public void setClassPrefix(String className) {
        classPrefix = className;
    }
    public void setMethodPrefix(String methodName) {
        methodPrefix = methodName;
    }
}

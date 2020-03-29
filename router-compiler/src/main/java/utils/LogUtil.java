package utils;


import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * @author zhang
 * @date 2020/3/26 0026
 * @time 18:17
 * @describe TODO
 */
public class LogUtil {
    private Messager msg;

    public LogUtil(Messager messager) {
        msg = messager;
    }

    public void info(CharSequence info) {
        if (!isNotEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, Constants.PREFIX_OF_LOGGER + info);
        }
    }

    public void error(CharSequence error) {
        if (!isNotEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.ERROR, Constants.PREFIX_OF_LOGGER + "An exception is encountered, [" + error + "]");
        }
    }

    public void error(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, Constants.PREFIX_OF_LOGGER + "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void warning(CharSequence warning) {
        if (!isNotEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, Constants.PREFIX_OF_LOGGER + warning);
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isNotEmpty(CharSequence character) {
        if (character == null || character.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
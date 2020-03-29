package com.zrs.router.api.log;

import static android.content.ContentValues.TAG;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 13:28
 * @describe TODO
 */
public interface ILogger {
    boolean isShowLog = false;
    boolean isShowStackTrace = false;
    String defaultTag = TAG;

    void showLog(boolean isShowLog);

    void showStackTrace(boolean isShowStackTrace);

    void debug(String tag, String message);

    void info(String tag, String message);

    void warning(String tag, String message);

    void error(String tag, String message);

    void monitor(String message);

    boolean isMonitorMode();

    String getDefaultTag();
}

package com.github.tanghuibo.remotedebuglikelocal.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * LogUtils
 *
 * @author tanghuibo
 * @date 2021/11/16 23:03
 */
public class LogUtils {

    public static void logError(String message, Exception e) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        e.printStackTrace(printStream);
        String data = outputStream.toString(StandardCharsets.UTF_8);
        printStream.close();
        Notifications.Bus.notify(new Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                "RemoteDebug告警",
                message + ";" + data,
                NotificationType.ERROR, null));
    }

    public static void logError(String message) {
        Notifications.Bus.notify(new Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                "RemoteDebug告警",
                message,
                NotificationType.ERROR, null));
    }

    public static void logInfo(String message) {
        Notifications.Bus.notify(new Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                "RemoteDebug通知",
                message,
                NotificationType.INFORMATION, null));
    }
}

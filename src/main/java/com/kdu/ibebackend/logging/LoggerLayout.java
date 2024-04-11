package com.kdu.ibebackend.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

/**
 * Layout file that has been customized and is being used
 * to present logs to logs/app.log.
 * I have implemented filters for the logging to file and
 * console along-with custom colors using the Jansi Library.
 * Do checkout the logback.xml in resources folder
 */
public class LoggerLayout extends LayoutBase<ILoggingEvent> {

    public String doLayout(ILoggingEvent event) {
        return event.getTimeStamp() +
                " " +
                event.getLevel() +
                " [" +
                event.getThreadName() +
                "] " +
                event.getLoggerName() +
                " - " +
                event.getFormattedMessage() +
                "\n";
    }
}
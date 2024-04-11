package com.kdu.ibebackend.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.kdu.ibebackend.constants.Constants;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

import java.util.LinkedList;
import java.util.Queue;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsResponse;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;

public class CloudWatchAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private CloudWatchLogsClient client;
    private String logGroupName;
    private String logStreamName;

    private Queue<InputLogEvent> eventQueue;

    public CloudWatchAppender() {
        logGroupName = Constants.LOG_GROUP_NAME;
        logStreamName = Constants.LOG_STREAM_NAME;

        client = CloudWatchLogsClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();

        eventQueue = new LinkedList<>();
    }

    @Override
    protected void append(ILoggingEvent event) {
        String sbuf = event.getTimeStamp() +
                " [" +
                event.getLevel() +
                "] " +
                event.getLoggerName() +
                " - " +
                event.getFormattedMessage();

        InputLogEvent logEvent = InputLogEvent.builder()
                .message(sbuf)
                .timestamp(event.getTimeStamp())
                .build();

        eventQueue.add(logEvent);

//        if (eventQueue.size() >= 10) {
//            flushEvents();
//        }

        flushEvents();
    }

    private void flushEvents() {
        DescribeLogStreamsResponse describeLogStreamsResponse = client.describeLogStreams(DescribeLogStreamsRequest.builder()
                .logGroupName(logGroupName)
                .logStreamNamePrefix(logStreamName)
                .build());

        String sequenceToken = describeLogStreamsResponse.logStreams().get(0).uploadSequenceToken();

        LinkedList<InputLogEvent> logEventsBatch = new LinkedList<>();
        while (!eventQueue.isEmpty() && logEventsBatch.size() < 10) {
            logEventsBatch.add(eventQueue.poll());
        }

        if (logEventsBatch.isEmpty()) {
            return;
        }

        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logGroupName(logGroupName)
                .logStreamName(logStreamName)
                .logEvents(logEventsBatch)
                .sequenceToken(sequenceToken)
                .build();

        client.putLogEvents(putLogEventsRequest);
    }

    @Override
    public void stop() {
        flushEvents();
        client.close();

        super.stop();
    }
}

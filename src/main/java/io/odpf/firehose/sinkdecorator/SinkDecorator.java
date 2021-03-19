package io.odpf.firehose.sinkdecorator;

import io.odpf.firehose.consumer.Message;
import io.odpf.firehose.exception.DeserializerException;
import io.odpf.firehose.sink.Sink;

import java.io.IOException;
import java.util.List;

public class SinkDecorator implements Sink {

    private final Sink sink;

    public SinkDecorator(Sink sink) {
        this.sink = sink;
    }

    @Override
    public List<Message> pushMessage(List<Message> message) throws IOException, DeserializerException {
        return this.sink.pushMessage(message);
    }

    @Override
    public void close() throws IOException {

    }
}
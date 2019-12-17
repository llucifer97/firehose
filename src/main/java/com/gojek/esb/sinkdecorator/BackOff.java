package com.gojek.esb.sinkdecorator;

import com.gojek.esb.metrics.Instrumentation;
import com.gojek.esb.metrics.StatsDReporter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BackOff {

    private Instrumentation instrumentation;

    public static BackOff withInstrumentationFactory(StatsDReporter statsDReporter) {
        Instrumentation instrumentation = new Instrumentation(statsDReporter, BackOff.class);
        return new BackOff(instrumentation);
    }

    public void inMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            instrumentation.captureNonFatalError(e, "Backoff thread sleep for {} milliseconds interrupted : {} {}",
            milliseconds, e.getClass(), e.getMessage());
        }
    }
}

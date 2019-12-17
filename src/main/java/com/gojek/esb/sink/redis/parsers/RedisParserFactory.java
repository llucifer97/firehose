package com.gojek.esb.sink.redis.parsers;

import com.gojek.de.stencil.parser.ProtoParser;
import com.gojek.esb.config.RedisSinkConfig;
import com.gojek.esb.config.enums.RedisSinkType;
import com.gojek.esb.metrics.Instrumentation;
import com.gojek.esb.metrics.StatsDReporter;
import com.gojek.esb.proto.ProtoToFieldMapper;

public class RedisParserFactory {

    public static RedisParser getParser(ProtoToFieldMapper protoToFieldMapper, ProtoParser protoParser, RedisSinkConfig redisSinkConfig, StatsDReporter statsDReporter) {

        RedisParser redisParser;

        if (redisSinkConfig.getRedisSinkType().equals(RedisSinkType.LIST)) {
            Instrumentation instrumentation = new Instrumentation(statsDReporter, RedisListParser.class);
            redisParser = new RedisListParser(protoParser, redisSinkConfig, instrumentation);
        } else {
            Instrumentation instrumentation = new Instrumentation(statsDReporter, RedisHashSetParser.class);
            redisParser = new RedisHashSetParser(protoToFieldMapper, protoParser, redisSinkConfig, instrumentation);
        }
        return redisParser;
    }
}

package com.gojek.esb.sink.redis.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gojek.de.stencil.parser.ProtoParser;
import com.gojek.esb.config.RedisSinkConfig;
import com.gojek.esb.consumer.EsbMessage;
import com.gojek.esb.proto.ProtoToFieldMapper;
import com.gojek.esb.sink.redis.dataentry.RedisDataEntry;
import com.gojek.esb.sink.redis.dataentry.RedisHashSetFieldEntry;
import com.google.protobuf.DynamicMessage;

public class RedisHashSetParser extends RedisParser {
    private ProtoToFieldMapper protoToFieldMapper;
    private RedisSinkConfig redisSinkConfig;

    public RedisHashSetParser(ProtoToFieldMapper protoToFieldMapper, ProtoParser protoParser, RedisSinkConfig redisSinkConfig) {
        super(protoParser, redisSinkConfig);
        this.protoToFieldMapper = protoToFieldMapper;
        this.redisSinkConfig = redisSinkConfig;
    }

    @Override
    public List<RedisDataEntry> parse(EsbMessage esbMessage) {
        DynamicMessage parsedMessage = parseEsbMessage(esbMessage);
        String redisKey = parseTemplate(parsedMessage, redisSinkConfig.getRedisKeyTemplate());
        List<RedisDataEntry> messageEntries = new ArrayList<>();
        Map<String, Object> protoToFieldMap = protoToFieldMapper.getFields(getPayload(esbMessage));
        protoToFieldMap.forEach((key, value) -> messageEntries.add(new RedisHashSetFieldEntry(redisKey, parseTemplate(parsedMessage, key), String.valueOf(value))));
        return messageEntries;
    }
}
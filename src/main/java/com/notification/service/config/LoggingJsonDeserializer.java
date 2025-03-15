package com.notification.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggingJsonDeserializer<T> extends JsonDeserializer<T> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingJsonDeserializer.class);
    private final String trustedPackages;

    public LoggingJsonDeserializer(Class<T> targetType, ObjectMapper objectMapper, String trustedPackages) {
        super(targetType, objectMapper);
        this.trustedPackages = trustedPackages;
        logger.info("LoggingJsonDeserializer created for type: {}, trusted packages: {}", targetType.getName(), trustedPackages);
    }

    public LoggingJsonDeserializer(Class<T> targetType, String trustedPackages) {
        super(targetType);
        this.trustedPackages = trustedPackages;
        logger.info("LoggingJsonDeserializer created for type: {}, trusted packages: {}", targetType.getName(), trustedPackages);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (IllegalArgumentException e) {
            logger.error("Deserialization failed due to untrusted package. Trusted packages: [{}]. Exception: {}",
                         trustedPackages, e.getMessage());
            throw e;
        }
    }
}

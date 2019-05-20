package com.sp.mq.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by alexlu on 2018/2/11.
 */
@Component
@ConfigurationProperties(prefix = "lk.kafka")
public class KafkaConfig {

    private String bootstrapServers;

    private KafkaTopic topic;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public KafkaTopic getTopic() {
        return topic;
    }

    public void setTopic(KafkaTopic topic) {
        this.topic = topic;
    }
}

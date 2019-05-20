package com.sp.mq.kafka.constant;

/**
 * Created by alexlu on 2018/2/11.
 */
public interface KafkaConsts {

    /**
     * 默认订阅组
     */
    String GROUP_ID = "PaymentRecord";

    /**
     * Kafka通知消息:用户付款记录
     */
    String TOPIC_USER_PAYMENT_RECORD = "payment-topic";

    /**
     * Kafka通知消息:用户退款记录
     */
    String TOPIC_USER_REFUND_RECORD = "refund-topic";
}

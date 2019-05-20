package com.sp.mq.kafka.config;

/**
 * Created by alexlu on 2018/2/11.
 */
public class KafkaTopic {
    /**
     * 用户付款记录Topic
     */
    private String userPaymentRecordTopic;

   /**
     * 用户退款记录Topic
     */
    private String userRefundRecordTopic;

    public String getUserPaymentRecordTopic() {
        return userPaymentRecordTopic;
    }

    public void setUserPaymentRecordTopic(String userPaymentRecordTopic) {
        this.userPaymentRecordTopic = userPaymentRecordTopic;
    }

    public String getUserRefundRecordTopic() {
        return userRefundRecordTopic;
    }

    public void setUserRefundRecordTopic(String userRefundRecordTopic) {
        this.userRefundRecordTopic = userRefundRecordTopic;
    }
}

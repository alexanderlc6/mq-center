package com.sp.mq.kafka.listener;

import com.sp.mq.common.JsonFormatter;
import com.sp.mq.common.ResponseVo;
import com.sp.mq.common.enums.SystemErrorCodeEnum;
import com.sp.mq.common.utils.DateUtil;
import com.sp.mq.common.utils.rest.RestClient;
import com.sp.mq.common.utils.rest.RestException;
import com.sp.mq.kafka.constant.KafkaConsts;
import com.sp.mq.pojo.RestMessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Kafka消费端监听器
 * Created by alexlu on 2018/2/11.
 */
@Component
public class TopicListener {
    private final static Logger logger = LoggerFactory.getLogger(TopicListener.class);

    private static final String SYS_PROPERTY_REST_REQUEST_HEADER_PARAMS = "Header_Param";
    private static final String USER_PROPERTY_HTTP_METHOD = "http_method";

    @KafkaListener(topics = {KafkaConsts.TOPIC_USER_PAYMENT_RECORD,KafkaConsts.TOPIC_USER_REFUND_RECORD},
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(ConsumerRecord<?, ?> record) {
        System.out.println("**************************Kafka消息监听****************************");
        logger.error("kafka消费时间#####################################"+ DateUtil.getMsTime());

        try {
            logger.info("Kafka Key:" + record.key());
            RestMessageInfo objData = JsonFormatter.toObject(String.valueOf(record.value()), RestMessageInfo.class);
            logger.info("Kafka value:" + String.valueOf(record.value()));
            System.out.println(String.format("Rest API invoke: %s, Request URL:%s", objData instanceof RestMessageInfo,
                    objData.getServiceUrl()));

            //调用REST服务进行操作
            if (StringUtils.isNotEmpty(objData.getServiceUrl())) {
                RestMessageInfo restMessageInfo = objData;
                Properties sysProperties = restMessageInfo.getSystemProperties();
                Map<String, String> svcHeaderParams = new HashMap<>();

                if (null != sysProperties) {
                    sysProperties.forEach((o1, o2) -> {
                        /** Http请求头参数按规则取出(放入Map时注意需以[Header_Param]开头来定义key)*/
                        if (StringUtils.isNotBlank(o1.toString())
                                && o1.toString().indexOf(SYS_PROPERTY_REST_REQUEST_HEADER_PARAMS) != -1) {
                            String headerParamName = o1.toString().replace(
                                    SYS_PROPERTY_REST_REQUEST_HEADER_PARAMS, "");
                            svcHeaderParams.put(headerParamName, o2.toString());
                        }
                    });
                }

                Map<String, String> svcCriteriaProps = new HashMap<>();
                Properties userProperties = restMessageInfo.getUserProperties();
                if(null != userProperties) {
                    userProperties.forEach((o1, o2) -> svcCriteriaProps.put(o1.toString(), o2.toString()));
                }

                ResponseVo responseVo = new ResponseVo();

                try {
                    String restReqData = JsonFormatter.toJsonAsString(restMessageInfo.getRequestData());
                    RestClient restClient = new RestClient(restMessageInfo.getServiceUrl(), restReqData);
                    restClient.setMethod(svcCriteriaProps.get(USER_PROPERTY_HTTP_METHOD));
                    logger.info("队列自动发起API接口请求:{},请求数据:{}", restMessageInfo.getServiceUrl(), restReqData);
                    responseVo.setSuccess(true);
                    responseVo.setCode(SystemErrorCodeEnum.SUCCESS.getCode());
                    try{
                        responseVo.setData(restClient.executeWithNoEncodeWithHeader(svcHeaderParams));
                    }catch (RestException e){
                        e.printStackTrace();
                        logger.info("队列执行API接口失败, API地址:{},响应数据:{}", restMessageInfo.getServiceUrl(),
                                JsonFormatter.toJsonAsString(responseVo));
                        //做队列消费失败处理
                        throw e;
                    }

                    logger.info("队列执行API接口成功! API地址:{},响应数据:{}", restMessageInfo.getServiceUrl(),
                            JsonFormatter.toJsonAsString(responseVo));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("队列自动发起API接口请求执行异常!", e);
                }
            }
        } catch (Exception e) {
            logger.error("消息处理失败,服务器异常！", e);
        }
    }
}



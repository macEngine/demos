package com.niuge.rocketmq;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProducerService {
  private Logger logger = LoggerFactory.getLogger(getClass());

  @Resource
  private RocketMQTemplate mqTemplate;

  @Value(value = "${boot.rocketmq.topic}")
  private String springTopic;

  @Value(value = "${boot.rocketmq.topic.user}")
  private String userTopic;

  @Value(value = "${boot.rocketmq.tag}")
  private String tag;

  public SendResult sendString(String message) {
    // 发送 String 类型的消息
    // 调用 RocketMQTemplate 的 syncSend 方法
    SendResult sendResult = mqTemplate.syncSend(springTopic + ":" + tag, message);
    logger.info("syncSend String to topic {} sendResult={} \n", springTopic, sendResult);
    return sendResult;
  }

  public SendResult sendUser(User user) {
    // 发送 User
    SendResult sendResult = mqTemplate.syncSend(userTopic, user);
    logger.info("syncSend User to topic {} sendResult= {} \n", userTopic, sendResult);
    return sendResult;
  }
}

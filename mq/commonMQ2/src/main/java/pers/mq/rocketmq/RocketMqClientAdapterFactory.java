package pers.mq.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.stereotype.Component;
import pers.common.adapter.MqClientAdapter;
import pers.common.adapter.MqClientAdapterFactory;
import pers.common.vo.MqInstanceMsg;
import pers.constants.MqType;

/**
 * @auther ken.ck
 * @date 2022/7/11 15:46
 */
@Component(MqType.ROCKET_MQ)
public class RocketMqClientAdapterFactory extends MqClientAdapterFactory {

    private final RocketMQMessageConverter rocketMQMessageConverter = new RocketMQMessageConverter();

    @Override
    protected MqClientAdapter createClientAdapter(MqInstanceMsg msg) {
        RocketMqClientAdapter rocketMqClientAdapter = new RocketMqClientAdapter();
        RocketMQTemplate rocketMQTemplate = createRocketMQTemplate(msg);
        rocketMqClientAdapter.setRocketMQTemplate(rocketMQTemplate);
        return rocketMqClientAdapter;
    }

    private RocketMQTemplate createRocketMQTemplate(MqInstanceMsg msg) {

        try {
            RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
            rocketMQTemplate.setMessageConverter(rocketMQMessageConverter.getMessageConverter());

            // DefaultMQProducer
            RocketMQProperties.Producer producerConfig = new RocketMQProperties.Producer();
            String nameServer = msg.getHost() + ":" + msg.getPort();
            String groupName = msg.getProducerGroup();
            String accessChannel = null;
            String ak = null;
            String sk = null;
            boolean isEnableMsgTrace = false;
            String customizedTraceTopic = producerConfig.getCustomizedTraceTopic();

            DefaultMQProducer producer = RocketMQUtil.createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);
            producer.setNamesrvAddr(nameServer);
            if (!StringUtils.isEmpty(accessChannel)) {
                producer.setAccessChannel(AccessChannel.valueOf(accessChannel));
            }
            producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
            producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
            producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
            producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
            producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());
            producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryNextServer());
            producer.setUseTLS(producerConfig.isTlsEnable());
            producer.setNamespace(producerConfig.getNamespace());
            producer.start();

            rocketMQTemplate.setProducer(producer);

            return rocketMQTemplate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

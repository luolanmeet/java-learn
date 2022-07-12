package pers.core.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.core.vo.MqInstanceMsg;

import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/7/12 17:54
 */
@Component
public class MqClientAdapterManager {

    @Autowired(required = false)
    private Map<String, MqClientAdapterFactory> adapterFactoryMap;

    public MqClientAdapter getClientAdapter(MqInstanceMsg msg) {
        MqClientAdapterFactory factory = adapterFactoryMap.get(msg.getMqType());
        return factory.getAdapterClient(msg);
    }

    public void shutdown() {
        if (adapterFactoryMap == null || adapterFactoryMap.isEmpty()) {
            return ;
        }
        adapterFactoryMap.values().forEach(MqClientAdapterFactory::shutdown);
    }

}

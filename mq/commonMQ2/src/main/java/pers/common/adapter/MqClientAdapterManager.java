package pers.common.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.common.vo.MqInstanceMsg;

import java.util.Map;

/**
 * @auther ken.ck
 * @date 2022/7/11 14:45
 */
@Component
public class MqClientAdapterManager {

    @Autowired
    private Map<String, MqClientAdapterFactory> adapterFactoryMap;

    public MqClientAdapter getClientAdapter(MqInstanceMsg msg) {
        MqClientAdapterFactory factory = adapterFactoryMap.get(msg.getMqType());
        MqClientAdapter mqClientAdapter = factory.getAdapterClient(msg);
        return mqClientAdapter;
    }

}

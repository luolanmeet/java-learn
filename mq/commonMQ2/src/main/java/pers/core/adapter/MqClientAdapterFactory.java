package pers.core.adapter;


import pers.core.vo.MqInstanceMsg;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 客户端适配器工厂类
 * @auther ken.ck
 * @date 2022/7/12 17:54
 */
public abstract class MqClientAdapterFactory {

    private Map<String, MqClientAdapter> adapterMap = new HashMap<>();

    protected abstract MqClientAdapter createClientAdapter(MqInstanceMsg msg);

    protected String getClientSign(MqInstanceMsg msg) {
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(msg.getMqType()).add(msg.getHost()).add(msg.getPort());
        return joiner.toString();
    }

    public MqClientAdapter getAdapterClient(MqInstanceMsg msg) {

        String clientSign = getClientSign(msg);

        MqClientAdapter clientAdapter = adapterMap.get(clientSign);
        if (clientAdapter != null) {
            return clientAdapter;
        }

        synchronized (this) {
            clientAdapter = adapterMap.get(clientSign);
            if (clientAdapter != null) {
                return clientAdapter;
            }
            clientAdapter = createClientAdapter(msg);
            adapterMap.put(clientSign, clientAdapter);
        }

        return clientAdapter;
    }

    public void shutdown() {
        adapterMap.values().forEach(MqClientAdapter::shutdown);
    }

}

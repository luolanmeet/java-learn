package pers.common.adapter;

import pers.common.vo.MqInstanceMsg;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @auther ken.ck
 * @date 2022/7/11 14:46
 */
public abstract class MqClientAdapterFactory {

    protected abstract MqClientAdapter createClientAdapter(MqInstanceMsg msg);

    private Map<String, MqClientAdapter> adapterMap = new HashMap<>();

    protected String getClientSign(MqInstanceMsg msg) {
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(msg.getMqType())
                .add(msg.getHost()).add(msg.getPort())
                .add(msg.getUsername()).add(msg.getPassword())
                .add(msg.getProducerGroup());
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

}

package pers.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/5/30 15:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcProviderMsgDTO {

    /**
     * 服务提供者信息
     */
    private List<RpcProviderMsg> provider;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RpcProviderMsg {

        /**
         * 服务提供方地址
         */
        private String address;

        /**
         * 连通性
         * TODO
         */
        private boolean connectivityCheckResult;

    }

}

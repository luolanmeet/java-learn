package com.pers.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @auther ken.ck
 * @date 2022/4/18 16:57
 */
@FeignClient(name = "cnap-lark")
public interface CnaplarkServiceClient {

    @PostMapping("/out_side/unified_entry")
    Object unifiedEntry(@RequestBody UnifiedEntranceDTO unifiedEntranceDTO);

}

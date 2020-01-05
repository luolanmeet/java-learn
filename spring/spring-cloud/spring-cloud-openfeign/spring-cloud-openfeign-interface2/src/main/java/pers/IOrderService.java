package pers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.dto.Order;
import pers.dto.req.FindOrderReq;

import java.util.List;

@FeignClient(name = "order-service")
public interface IOrderService {

    @PostMapping("findOrderByOrderId")
    Order findOrderByOrderId(String orderId);

    @PostMapping("findOrder")
    List<Order> findOrder(@RequestBody FindOrderReq findOrderReq);

}

package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pers.IHelloService;
import pers.IHelloWorldService;
import pers.IOrderService;
import pers.dto.Order;
import pers.dto.req.FindOrderReq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class OrderService implements IOrderService {

    // 其他模块提供的服务
    @Autowired
    private IHelloService helloService;
    @Autowired
    private IHelloWorldService helloWorldService;

    @Override
    public Order findOrderByOrderId(String orderId) {

        System.out.println(helloService.hello("cck"));
        System.out.println(helloWorldService.helloWorld());

        System.out.println(orderId);
        return getOrders().get(0);
    }

    @Override
    public List<Order> findOrder(FindOrderReq findOrderReq) {

        System.out.println(findOrderReq);
        return getOrders();
    }

    private List<Order> getOrders() {
        List<Order> orders = new ArrayList<>(1);
        orders.add(
                Order.builder()
                    .orderId(System.currentTimeMillis() + "")
                    .userId(new Random().nextInt(100))
                    .build());
        return orders;
    }

}

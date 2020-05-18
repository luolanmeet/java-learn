package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pers.service.IOrderService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        Thread.currentThread().setName("主线程");
        ConfigurableApplicationContext context = SpringApplication.run(Main.class,args);

        IOrderService orderService = (IOrderService)context.getBean("orderService");
        orderService.create();
        orderService.create();

        orderService.pay(1);

        new Thread("客户线程"){
            @Override
            public void run() {
                orderService.deliver(1);
                orderService.receive(1);
            }
        }.start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println("全部订单状态：" + orderService.getOrders());
    }

}

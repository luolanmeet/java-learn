package pers.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import pers.state.OrderStatus;

@Data
@Builder
@ToString
public class Order {

    private int id;
    private OrderStatus status;

}

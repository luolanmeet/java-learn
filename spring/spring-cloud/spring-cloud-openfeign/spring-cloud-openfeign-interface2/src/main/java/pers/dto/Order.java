package pers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = -3473135933204816586L;

    private Integer userId;
    private String orderId;

}

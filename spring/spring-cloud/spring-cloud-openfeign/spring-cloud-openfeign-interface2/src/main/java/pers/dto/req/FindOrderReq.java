package pers.dto.req;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderReq implements Serializable {

    private static final long serialVersionUID = -3571788214895942912L;

    private String orderId;
    private Integer userId;

}

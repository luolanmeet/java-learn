package pers.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_user")
public class User {

    @Id
    private Integer userId;
    private String name;
    private String address;
    
}

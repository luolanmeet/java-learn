package pers.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库实体对象，模拟从数据库中获取的数据
 * @author cck
 */
@Getter
@Setter
@Builder
public class ShopCategory implements Serializable {
    
    private static final long serialVersionUID = -4254909899375968290L;
    
    private Integer id;
    
    private String name;
    
    private String nameTw;
    
    private Integer level;
    
    private Integer parentId;
    
    private String icon;
    
}

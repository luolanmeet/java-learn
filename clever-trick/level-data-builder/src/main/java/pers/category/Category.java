package pers.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 要发送给前端的响应数据
 * @author cck
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Category implements Serializable {
    
    /**   */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;
    private String nameTw;
    
    // 不发送给前端
    private transient Integer parentId;
    
    public void addChild(Object obj) {
    
    }
    
    public final static Category getCategory(ShopCategory sc) {
        
        Category category;
        
        switch (sc.getLevel()) {
            case ShopCategoryLevel.LEVEL_1:
                category = new FirstCategory();
                break;
            
            case ShopCategoryLevel.LEVEL_2:
                category = new SecondCategory();
                break;
            
            case ShopCategoryLevel.LEVEL_3:
                category = new Category();
                break;
            
            default:
                String msg = "error category level. id[%d], level[%d]";
                throw new RuntimeException(String.format(
                        msg, sc.getId(), sc.getLevel()));
        }
        
        category.setId(sc.getId());
        category.setName(sc.getName());
        category.setNameTw(sc.getNameTw());
        category.setParentId(sc.getParentId());
        
        return category;
    }

}

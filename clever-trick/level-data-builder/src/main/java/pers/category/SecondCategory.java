package pers.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级分类
 * @author cck
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SecondCategory extends Category {
    
    /**   */
    private static final long serialVersionUID = 1L;
    
    private List<Category> thirdCategorys = new ArrayList<>();
    
    public void addChild(Object obj) {
        thirdCategorys.add((Category) obj);
    }
    
}

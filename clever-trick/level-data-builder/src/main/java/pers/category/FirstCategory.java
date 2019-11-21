package pers.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 * @author cck
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FirstCategory extends Category {
    
    /**   */
    private static final long serialVersionUID = 1L;
    
    private List<SecondCategory> secondCategorys = new ArrayList<>();
    
    public void addChild(Object obj) {
        secondCategorys.add((SecondCategory) obj);
    }
    
}
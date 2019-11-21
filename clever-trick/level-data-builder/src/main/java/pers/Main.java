package pers;

import pers.category.Category;
import pers.category.FirstCategory;
import pers.category.ShopCategory;
import pers.category.ShopCategoryLevel;

import java.util.*;

/**
 * 
 * @author cck
 */
public class Main {
    
    public static void main(String[] args) {
    
        // 模拟从数据库获取数据
        List<ShopCategory> scs = initData();
    
        // 给前端的响应
        List<FirstCategory> resp = new ArrayList<>();
        // 临时存放数据
        Map<Integer, Category> map = new HashMap<>(scs.size());
    
        // 层级关系不再此循环中处理
        for (ShopCategory sc : scs) {
            Category category = Category.getCategory(sc);
            map.put(sc.getId(), category);
        
            if (sc.getLevel() == ShopCategoryLevel.LEVEL_1) {
                resp.add((FirstCategory) category);
            }
        }
    
        for (Category c : map.values()) {
            Category parent = map.get(c.getParentId());
            if (parent != null) {
                parent.addChild(c);
            }
        }
    
        // 返回给前端
        System.out.println(resp);
    }
    
    private static List<ShopCategory> initData() {
    
        List<ShopCategory> scs = new ArrayList<>();
    
        ShopCategory sc = ShopCategory.builder()
                .id(1)
                .name("娱乐")
                .nameTw("娛樂")
                .level(ShopCategoryLevel.LEVEL_1)
                .parentId(0)
                .icon("icon_url")
                .build();
        scs.add(sc);
    
        sc = ShopCategory.builder()
                .id(2)
                .name("游戏")
                .nameTw("遊戲")
                .level(ShopCategoryLevel.LEVEL_2)
                .parentId(1)
                .icon("icon_url")
                .build();
        scs.add(sc);
    
        sc = ShopCategory.builder()
                .id(3)
                .name("旅游")
                .nameTw("旅遊")
                .level(ShopCategoryLevel.LEVEL_2)
                .parentId(1)
                .icon("icon_url")
                .build();
        scs.add(sc);
    
        sc = ShopCategory.builder()
                .id(4)
                .name("横版通关")
                .nameTw("橫版通關")
                .level(ShopCategoryLevel.LEVEL_3)
                .parentId(2)
                .icon("icon_url")
                .build();
        scs.add(sc);
    
        sc = ShopCategory.builder()
                .id(5)
                .name("剧情冒险")
                .nameTw("劇情冒險")
                .level(ShopCategoryLevel.LEVEL_3)
                .parentId(2)
                .icon("icon_url")
                .build();
        scs.add(sc);
        
        return scs;
    }
    
}

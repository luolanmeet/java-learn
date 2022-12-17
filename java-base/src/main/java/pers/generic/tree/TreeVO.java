package pers.generic.tree;

import java.util.List;

/**
 * 用于表示属性结构的类
 * @auther ken.ck
 * @date 2022/12/17 19:19
 */
public class TreeVO {

    private String label;

    private List<? extends TreeVO> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<? extends TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<? extends TreeVO> children) {
        this.children = children;
    }
}

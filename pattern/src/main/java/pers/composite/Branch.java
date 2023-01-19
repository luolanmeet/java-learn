package pers.composite;

import java.util.ArrayList;
import java.util.List;

public class Branch extends Node {

    private List<Node> childs = new ArrayList<>();

    public void addChild(Node child) {
        childs.add(child);
    }

    public List<Node> getChild() {
        return childs;
    }

    @Override
    void method() {
        System.out.println("branch");
        childs.forEach(Node::method);
    }



}

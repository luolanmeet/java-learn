package pers.memento;

import java.util.Stack;

/**
 * 草稿箱
 * Caretaker
 */
public class DraftsBox {

    private Stack<ArticleMemento> stack = new Stack<>();

    public ArticleMemento getMemento() {
        return stack.pop();
    }

    public void addMemento(ArticleMemento memento) {
        stack.push(memento);
    }

}

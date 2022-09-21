package com.dom4j;

import org.dom4j.*;
import org.dom4j.tree.DefaultElement;

/**
 * @auther ken.ck
 * @date 2022/9/14 20:57
 */
public class NameSpaceCleaner extends VisitorSupport {

//    @Override
//    public void visit(Document document) {
//        ((DefaultElement) document.getRootElement())
//                .setNamespace(Namespace.NO_NAMESPACE);
//        document.getRootElement().additionalNamespaces().clear();
//    }

//    @Override
//    public void visit(Namespace namespace) {
//        if (namespace.getParent() != null) {
//            namespace.getParent().remove(namespace);
//        }
//    }

    @Override
    public void visit(Attribute node) {
        node.getParent().remove(node);
    }

    @Override
    public void visit(Element node) {
        if (node instanceof DefaultElement) {
            ((DefaultElement) node).setNamespace(Namespace.NO_NAMESPACE);
            node.additionalNamespaces().clear();
        }
    }

}

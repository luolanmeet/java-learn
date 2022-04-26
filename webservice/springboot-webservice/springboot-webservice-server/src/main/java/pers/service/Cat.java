package pers.service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @auther ken.ck
 * @date 2022/4/26 17:47
 */
@XmlRootElement(name = "cat")
public class Cat implements Serializable {

    private static final long serialVersionUID = -8666985670463091236L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

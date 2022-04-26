package pers.service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @auther ken.ck
 * @date 2022/4/25 10:38
 */
@XmlRootElement(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -8666985670463091236L;
    private String id;
    private String name;
    private Cat cat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
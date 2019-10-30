package pers.bean.lookup;

/**
 * Reader是单例的，book是原型的
 * 希望在单例bean中引用原型bean，则使用lookup-method
 * 声明成抽象类也可以
 */
public class Reader {

    // 这里可以声明成抽象方法
    public Book getBook() {
        return null;
    }
    
}

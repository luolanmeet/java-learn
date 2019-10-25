package pers.bean;

import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

/**
 * 这里可以做成泛型类，然后返回代理对象，实现更强的功能
 */
public class TeacherFactoryBean implements FactoryBean<ITeacher> {
    
    @Setter
    private Class<ITeacher> interfaceClass;
    
    @Override
    public ITeacher getObject() throws Exception {
        
        return new ITeacher() {
            @Override
            public void teach() {
                System.out.println("teach Java");
            }
        };
    }
    
    @Override
    public Class<?> getObjectType() {
        return ITeacher.class;
    }
}

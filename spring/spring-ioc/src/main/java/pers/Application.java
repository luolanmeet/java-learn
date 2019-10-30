package pers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.bean.Food;
import pers.bean.ITeacher;
import pers.bean.Student;
import pers.bean.User;
import pers.bean.lookup.Reader;
import pers.bean.replaced.Water;

public class Application {
    
    public static void main(String[] args) {
    
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        
//        testBeanLifeCycle(applicationContext);

//        testReplacedMethod(applicationContext);

//        testLookUpMethod(applicationContext);
        
//        testFactoryBean(applicationContext);
        
        testFactory(applicationContext);
    }
    
    private static void testBeanLifeCycle(ClassPathXmlApplicationContext applicationContext) {

        // 创建Bean实例之前
        //    BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry
        // -> BeanDefinitionRegistryPostProcessor#postProcessBeanFactory
        // -> BeanFactoryPostProcessor#postProcessBeanFactory

        // 创建Bean实例之后
        // -> BeanNameAware#setBeanName
        // -> BeanFactoryAware#setBeanFactory
        // -> ApplicationContextAware#setApplicationContext
        
        // -> BeanPostProcessor#postProcessBeforeInitialization
        // -> InitializingBean#afterPropertiesSet
        // -> initMethod
        // -> BeanPostProcessor#postProcessAfterInitialization
        
        // 容器关闭
        // -> DisposableBean#destroy
        // -> destroyMethod
        
        Student s1 = applicationContext.getBean(Student.class);
        Student s2 = applicationContext.getBean(Student.class);
        System.out.println(s2);
        System.out.println(s1 == s2);
    
        ITeacher teacher = applicationContext.getBean(ITeacher.class);
        teacher.teach();
    
        applicationContext.close();
    }
    
    private static void testReplacedMethod(ClassPathXmlApplicationContext applicationContext) {
    
        Water water = applicationContext.getBean(Water.class);
        water.helloWorld();
    }
    
    private static void testLookUpMethod(ClassPathXmlApplicationContext applicationContext) {
        Reader reader = applicationContext.getBean(Reader.class);
        System.out.println(reader == applicationContext.getBean(Reader.class));
        System.out.println(reader.getBook());
        System.out.println(reader.getBook() == reader.getBook());
    }
    
    private static void testFactoryBean(ClassPathXmlApplicationContext applicationContext) {
    
        // 拿到的是UserFactoryBean产生的User实例
        User user1 = (User) applicationContext.getBean("user");
        System.out.println(user1);
    
        // 如果UserFactoryBean中设置了单例，那么返回的是实例都是单例的
        User user2 = (User) applicationContext.getBean("user");
        user2.setName("ck");
        System.out.println(user1);
    
        // 加 & 拿到的是UserFactoryBean本身的实例
        Object userFactoryBean = applicationContext.getBean("&user");
        System.out.println(userFactoryBean);
    }
    
    private static void testFactory(ClassPathXmlApplicationContext applicationContext) {
    
        Food foodOne = applicationContext.getBean("foodOne", Food.class);
        Food foodTwo = applicationContext.getBean("foodTwo", Food.class);
        System.out.println(foodOne);
        System.out.println(foodOne == applicationContext.getBean("foodOne", Food.class));
        System.out.println(foodTwo);
        System.out.println(foodTwo == applicationContext.getBean("foodTwo", Food.class));
    }
    
}

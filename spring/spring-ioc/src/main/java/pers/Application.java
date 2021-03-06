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
        
        testBeanLifeCycle(applicationContext);

//        testReplacedMethod(applicationContext);

//        testLookUpMethod(applicationContext);
        
//        testFactoryBean(applicationContext);
        
//        testFactory(applicationContext);
    }
    
    private static void testBeanLifeCycle(ClassPathXmlApplicationContext applicationContext) {

        /*------------BeanFactoryPostProcessor,在BeanDefinition创建好后进行一些增强-------------*/
        //    BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry
        // -> BeanDefinitionRegistryPostProcessor#postProcessBeanFactory
        // -> BeanFactoryPostProcessor#postProcessBeanFactory

        /*------------Bean的声明周期实际上去看BeanFactory类的注释就有了-------------*/
        // Bean实例化前后
        // -> InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation
        // -> InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation
        
        // Bean初始化 见 org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeAwareMethods
        // -> BeanNameAware#setBeanName Aware注入需要的对象
        // -> BeanClassLoaderAware#setBeanClassLoader
        // -> BeanFactoryAware#setBeanFactory
        // -> ApplicationEventPublisherAware#setApplicationEventPublisher
        // -> ApplicationContextAware#setApplicationContext
        
        // 见 org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean
        // -> BeanPostProcessor#postProcessBeforeInitialization 初始化完成之前
        // -> @PostConstruct#method
        // -> InitializingBean#afterPropertiesSet
        // -> initMethod
        // -> BeanPostProcessor#postProcessAfterInitialization  初始化完成之后
        
        // 容器关闭
        // -> @PreDestroy#method
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

        /*---------------FactoryBean是用来创建一些复杂的Bean的，Spring Cloud 的 Feign就是了-------------------*/

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

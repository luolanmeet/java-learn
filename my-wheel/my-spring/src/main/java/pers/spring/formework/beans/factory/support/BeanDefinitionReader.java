package pers.spring.formework.beans.factory.support;

import lombok.Getter;
import pers.spring.formework.beans.factory.config.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class BeanDefinitionReader {

    @Getter
    private Properties config = new Properties();

    private final String SCAN_PACKAGE = "scan.package";

    private List<String> registyBeanClasses = new ArrayList<>();

    public BeanDefinitionReader (String location) {

        location = location.replace("classpath:", "");

        try (InputStream is = BeanDefinitionReader.class.getClassLoader().getResourceAsStream(location)){
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        doScan(config.getProperty(SCAN_PACKAGE));
    }

    private void doScan(String scanPackage) {

        URL url = BeanDefinitionReader.class.getResource("/" + scanPackage.replaceAll("\\.", "/"));

        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {

            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
                continue;
            }

            if (!file.getName().endsWith(".class")) {
                continue;
            }
            registyBeanClasses.add(scanPackage + "." + file.getName().replace(".class", ""));
        }

    }

    public List<BeanDefinition> loadBeanDefinitions() {

        List<BeanDefinition> beanDefinitionList = new ArrayList<>();

        try {

            for (String className : registyBeanClasses) {

                Class<?> beanClass = Class.forName(className);

                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if(beanClass.isInterface()){
                    continue;
                }

                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClassName(className);
                beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));

                beanDefinitionList.add(beanDefinition);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanDefinitionList;
    }

    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}

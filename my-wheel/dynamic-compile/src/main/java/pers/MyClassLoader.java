package pers;

import java.util.Map;

import javax.tools.JavaFileObject;

public class MyClassLoader extends ClassLoader {

    Map<String, JavaFileObject> fileObjects;
    
    public MyClassLoader(Map<String, JavaFileObject> fileObjects) {
        this.fileObjects = fileObjects;
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        
        JavaFileObject fileObject = fileObjects.get(name);
        if(fileObject != null){
            byte[] bytes = ((MyJavaFileObject)fileObject).getCompiledBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
        try{
            return ClassLoader.getSystemClassLoader().loadClass(name);
        } catch (Exception e){
            return super.findClass(name);
        }
    }
    
}
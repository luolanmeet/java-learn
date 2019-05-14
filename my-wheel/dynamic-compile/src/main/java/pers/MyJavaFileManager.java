package pers;

import java.io.IOException;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

public class MyJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    
    Map<String, JavaFileObject> fileObjects;

    protected MyJavaFileManager(
            Map<String, JavaFileObject> fileObjects,
            StandardJavaFileManager fileManager) {
        super(fileManager);
        this.fileObjects = fileObjects;
    }

//    @Override
//    public JavaFileObject getJavaFileForInput(
//            Location location, 
//            String className, 
//            JavaFileObject.Kind kind) throws IOException {
//        
//        JavaFileObject javaFileObject = fileObjects.get(className);
//        if(javaFileObject == null){
//            super.getJavaFileForInput(location, className, kind);
//        }
//        return javaFileObject;
//    }

    @Override
    public JavaFileObject getJavaFileForOutput(
            Location location, 
            String qualifiedClassName, 
            JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        
        JavaFileObject javaFileObject = new MyJavaFileObject(qualifiedClassName, kind);
        fileObjects.put(qualifiedClassName, javaFileObject);
        return javaFileObject;
    }
}
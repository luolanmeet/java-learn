package pers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class MyJavaFileObject extends SimpleJavaFileObject {
    
    private String source;
    private ByteArrayOutputStream outPutStream;


    public MyJavaFileObject(String name, String source) {
        super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = source;
    }

    public MyJavaFileObject(String name, Kind kind){
        super(URI.create("String:///" + name + kind.extension), kind);
        source = null;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors){
        if(source == null){
            throw new IllegalArgumentException("source == null");
        }
        return source;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        outPutStream = new ByteArrayOutputStream();
        return outPutStream;
    }

    public byte[] getCompiledBytes(){
        return outPutStream.toByteArray();
    }
    
}

package pers.demo.one;

public class Cat {

    public void sayHi() {
    
        if (Math.random() > 0.8) {
            throw new RuntimeException("不喵了");
        }
        
        System.out.println("喵喵喵");
    }
    
    public void sayHello() {
        System.out.println("hello 喵喵喵");
    }
    
}

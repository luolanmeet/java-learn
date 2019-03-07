package per.origin;

public class Main {

    public static void main(String[] args) {

        Subject subject = new RealSubject();
        StaticSubjectProxy staticSubjectProxy = new StaticSubjectProxy(subject);
        staticSubjectProxy.sayHello("cck");

        System.out.println();

        DynamicSubjectProxy dynamicSubjectProxy = new DynamicSubjectProxy();
        Subject sb = (Subject) dynamicSubjectProxy.bind(subject);
        sb.sayHello("cck");
    }
}

package per.origin;

public class StaticSubjectProxy implements Subject {

    private Subject subject;

    public StaticSubjectProxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void sayHello(String name) {
        subject.sayHello(name);
    }
}

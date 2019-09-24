package pers.process;
/**
 * 操作接口
 */
public interface IProcess {
    
    void process(String msg) ;
    
    void setNext(IProcess nextProcess);
    
}

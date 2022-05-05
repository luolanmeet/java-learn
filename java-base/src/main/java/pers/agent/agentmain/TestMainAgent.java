package pers.agent.agentmain;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * @auther ken.ck
 * @date 2022/5/5 23:55
 */
public class TestMainAgent {

    public static void main(String[] args) throws Exception {
        //正在运行的java 程序 ps id
        VirtualMachine vm = VirtualMachine.attach("17529");
//        VirtualMachine vm = VirtualMachine.attach(args[0]);
        //刚刚编译好的agent jar 位置
        vm.loadAgent("/Users/chenken/dev/project/learn/cck/java-learn/java-base/target/java-base-1.0-SNAPSHOT.jar");
    }

}

package pers.agent.agentmain;

import java.lang.instrument.Instrumentation;

/**
 * @auther ken.ck
 * @date 2022/5/5 23:25
 */
public class AgentDemo {

    public static void agentmain(String agentArgs, Instrumentation inst) {

        System.out.println("load agent after main run. args=" + agentArgs);

        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
        }

        System.out.println("agent run completely");
    }

}

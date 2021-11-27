package pers.obj.one;

import java.lang.instrument.Instrumentation;

public class ObjSizeTool {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object obj) {
        return instrumentation.getObjectSize(obj);
    }

}

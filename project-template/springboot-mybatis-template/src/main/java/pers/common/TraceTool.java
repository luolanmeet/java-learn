package pers.common;

import org.slf4j.MDC;

import java.util.Random;
import java.util.UUID;

/**
 * @auther ken.ck
 * @date 2023/7/7 17:39
 */
public class TraceTool {

    public final static String TRACE_ID = "TRACE_ID";

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void genTraceId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Random random = new Random();
        String rod = uuid +
                (char) (97 + random.nextInt(26)) +
                (char) (97 + random.nextInt(26)) +
                (char) (97 + random.nextInt(26)) +
                (char) (97 + random.nextInt(26)) +
                (char) (97 + random.nextInt(26));
        MDC.put(TRACE_ID, rod);
    }

    public static void removeTraceId() {
        MDC.remove(TRACE_ID);
    }

}

package pers.calc.param;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 源数据
 * @auther ken.ck
 * @date 2021/11/19 14:30
 */
public abstract class SourceParam {

    protected Map<String, Integer> variableReaderIndexMap = new HashMap<>();

    /**
     * 获取源数据值
     * @param paramPath
     * @return
     */
    public abstract Param getParam(String paramPath);

    /**
     * 判断该路径的变量是否为集合变量，是则返回集合路径
     * @param paramPath
     * @return
     */
    public abstract String getListVariablePath(String paramPath);

    /**
     * 判断该路径的变量是否为集合变量
     * @param paramPath
     * @return
     */
    public abstract boolean isListVariable(String paramPath);

    /**
     * 集合是否有下个值
     * @param listVariablePath
     * @return
     */
    public abstract boolean hasNext(String listVariablePath);

    /**
     * 自增集合变量的下标
     * @param listVariablePath
     */
    public abstract void increateReaderIndex(String listVariablePath);

    /**
     * 标记读取的变量的下标
     * @param paramPath
     * @return
     */
    public void markReaderIndex(String paramPath) {

        loopPath(paramPath, path -> {
            if (variableReaderIndexMap.containsKey(path)) {
                throw new RuntimeException("path has read index. " + path);
            }
            if (isListVariable(path)) {
                variableReaderIndexMap.put(path, 0);
            }
        });
    }

    /**
     * 循环路径
     * @param paramPath
     * @param consumer
     */
    public void loopPath(String paramPath, Consumer<String> consumer) {

        consumer.accept(paramPath);

        int idx = paramPath.lastIndexOf(".");

        while (idx != -1) {
            paramPath = paramPath.substring(0, idx);
            idx = paramPath.lastIndexOf(".");
            consumer.accept(paramPath);
        }

    }

    /**
     * 重置读取的变量
     * @param paramPath
     * @return
     */
    public void resetReaderIndex(String paramPath) {
        variableReaderIndexMap.remove(paramPath);
    }

}

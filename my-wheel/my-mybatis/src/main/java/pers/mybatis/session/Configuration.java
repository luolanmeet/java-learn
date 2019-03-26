package pers.mybatis.session;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import pers.mybatis.annotations.Select;
import pers.mybatis.binding.MapperRegistry;
import pers.mybatis.builder.SqlSourceBuilder;
import pers.mybatis.executor.Executor;
import pers.mybatis.executor.SimpleExecutor;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.mapping.SqlCommandType;
import pers.mybatis.mapping.SqlSource;
import pers.mybatis.transaction.Transaction;

public class Configuration {

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    // key   ：接口全路径.方法名
    // value ：映射
    // mybatis这里用的是自己实现的Map
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    
    public Configuration() {
        
        try {
            
            buildAllStatements();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public Executor newExecutor(Transaction tx) {
        return new SimpleExecutor(this, tx);
    }

    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {

        return mapperRegistry.getMapper(clazz, sqlSession);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }
    
    /**
     * 读配置文件里边注册的映射类
     */
    protected void buildAllStatements() {
        
        try {
            
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.properties");
            Properties properties = new Properties();
            properties.load(is);
            
            String mappers = (String) properties.get("mappers");
            String[] mapperClasss = mappers.split(",");
            
            SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(this);
            
            for (String mapperClass : mapperClasss) {
                
                Class<?> clazz = Class.forName(mapperClass);
                
                if (!clazz.isInterface()) {
                    continue;
                }
                
                mapperRegistry.addMapper(clazz);
                
                Method[] methods = clazz.getMethods();
                
                for (Method method : methods) {
                    
                    String methodId = mapperClass + "." + method.getName();
                    
                    MappedStatement ms = new MappedStatement(); 
                    ms.setId(methodId);
                    ms.setSqlCommandType(SqlCommandType.SELECT);
                    Select select = method.getAnnotation(Select.class);
                    SqlSource sqlSource = sqlSourceBuilder.parse(select.value(), null, null);
                    ms.setSqlSource(sqlSource);
                    ms.setResultType(method.getReturnType());
                    mappedStatements.put(methodId, ms);
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

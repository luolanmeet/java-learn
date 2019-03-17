package pers.mybatis.session;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import pers.mybatis.annotations.Select;
import pers.mybatis.binding.MapperRegistry;
import pers.mybatis.builder.StaticSqlSource;
import pers.mybatis.executor.Executor;
import pers.mybatis.executor.SimpleExecutor;
import pers.mybatis.mapping.MappedStatement;
import pers.mybatis.mapping.SqlCommandType;
import pers.mybatis.mapping.SqlSource;
import pers.mybatis.test.TestMapper;
import pers.mybatis.transaction.Transaction;

public class Configuration {

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    // key   ：接口全路径.方法名
    // value ：映射
    // mybatis这里用的是自己实现的Map
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    
    public Configuration() {
        
        // FIXME 这里直接初始化了 mappedStatements，没有通过解析的方式获取
        
        try {
            
            MappedStatement ms = new MappedStatement(); 
            ms.setId("pers.mybatis.test.TestMapper.findByid");
            ms.setSqlCommandType(SqlCommandType.SELECT);
            Method method = TestMapper.class.getMethod("findByid", Integer.class);
            Select select = method.getAnnotation(Select.class);
            SqlSource sqlSource = new StaticSqlSource(this, select.value(), null);
            ms.setSqlSource(sqlSource);
            
            mappedStatements.put("pers.mybatis.test.TestMapper.findByid", ms);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public Executor newExecutor(Transaction tx) {
        return new SimpleExecutor(this, tx);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(
            Class<T> clazz,
            SqlSession sqlSession) {

        mapperRegistry.addMapper(TestMapper.class);

        return mapperRegistry.getMapper(clazz, sqlSession);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }

}

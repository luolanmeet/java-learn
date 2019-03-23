package pers.mybatis.builder;

import pers.mybatis.session.Configuration;

public abstract class BaseBuilder {
    
    protected final Configuration configuration;
    
    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    
}

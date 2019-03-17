package pers.mybatis.executor;

import pers.mybatis.session.Configuration;
import pers.mybatis.transaction.Transaction;

public abstract class BaseExecutor implements Executor {

    protected Transaction transaction;
    protected Executor wrapper;
    protected Configuration configuration;
    
    public BaseExecutor(
            Configuration configuration, 
            Transaction transaction) {
        
        this.transaction = transaction;
        this.wrapper = this;
        this.configuration = configuration;
    }

}

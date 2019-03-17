package pers.mybatis.datasource.unpooled;

import java.util.Properties;

import javax.sql.DataSource;

import pers.mybatis.datasource.DataSourceFactory;

public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected DataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }
    
    @Override
    public void setProperties(Properties props) {
        
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

}

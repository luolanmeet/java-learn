package pers.mybatis.datasource.unpooled;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 不使用连接池的数据源
 * 
 * @author cck
 */
public class UnpooledDataSource implements DataSource {

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {

        Connection connection = null;
        
        try {
            
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("mybatis.properties");
            Properties properties = new Properties();
            properties.load(is);
            Class.forName(properties.getProperty("driver"));
            
            connection = DriverManager.getConnection(
                    properties.getProperty("jdbcUrl"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}

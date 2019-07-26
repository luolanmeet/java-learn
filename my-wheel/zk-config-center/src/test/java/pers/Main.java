package pers;

import pers.center.ZkConfigCenter;

import java.io.IOException;

/**
 * 
 * @author cck
 */
public class Main {
    
    public static void main(String[] args) {
        
        ZkConfigCenter zkConfigCenter = new ZkConfigCenter("127.0.0.1:2181");
        DbUserConfig config = zkConfigCenter.getConfig("/db-user-config", DbUserConfig.class);
        
        System.out.println(config);
        
        DbUserConfig config2 = zkConfigCenter.getConfig("/db-user-config", DbUserConfig.class);
        System.out.println(config.equals(config2));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

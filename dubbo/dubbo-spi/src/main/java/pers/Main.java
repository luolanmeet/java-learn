package pers;

import org.apache.dubbo.common.extension.ExtensionLoader;
import pers.api.DataBaseDriver;

public class Main {

    public static void main(String[] args) {

        ExtensionLoader<DataBaseDriver> extensionLoader =
                ExtensionLoader.getExtensionLoader(DataBaseDriver.class);

        String host = "127.0.0.1";

        DataBaseDriver mysqlDriver = extensionLoader.getExtension("mysqlDriver");
        System.out.println(mysqlDriver.connect(host));

        DataBaseDriver oracleDriver = extensionLoader.getExtension("oracleDriver");
        System.out.println(oracleDriver.connect(host));

    }

}

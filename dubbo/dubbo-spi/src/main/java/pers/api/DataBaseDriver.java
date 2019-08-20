package pers.api;

import org.apache.dubbo.common.extension.SPI;

/**
 * dubbo 的SPI，需要使用@SPI的注解
 * 可以传值，代表默认实现
 */
@SPI
public interface DataBaseDriver {

    String connect(String host);

}

package pers.datatype;

import pers.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Redis HyperLogLog 是用来做基数统计的算法，
 * HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，
 * 计算基数所需的空间总是固定的、并且是很小的。
 *
 * 在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基数。
 * 这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。
 *
 * 但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，
 * 所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
 *
 * 数据集 {1,3,5,7,5,7,8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。
 */
public class HyperLogLogTest {
    
    public static void main(String[] args) {
        
        float size = 1000;
        
        Jedis jedis = JedisUtil.getJedis();
        jedis.del("hll");
        
        Pipeline pipelined = jedis.pipelined();
        for (int i = 0; i < size; i++) {
            pipelined.pfadd("hll", "hll-" + i);
        }
        pipelined.sync();
        
        long total = jedis.pfcount("hll");
        System.out.println(String.format("统计个数: %s", total)); // 会比真实数据多？
        System.out.println(String.format("正确率: %s", (total / size)));
        System.out.println(String.format("误差率: %s", 1 - (total / size)));
        
        jedis.close();
    }

}

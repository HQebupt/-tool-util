package guava.example;

/**
 * User: huangqiang
 * Date: 12/3/15
 * Time: 12:08 PM
 */


import com.google.common.collect.MapMaker;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EntryWeigher;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import java.util.concurrent.ConcurrentMap;

public class ConcurrentLinkedHashMapExample {

    public static void main(String[] args) {
        // MapMaker的用法
        System.out.println("MapMaker的用法.");
        ConcurrentMap<String, String> testMap = new MapMaker()
            .concurrencyLevel(4)
            .weakKeys()
            .makeMap();
        testMap.put("a", "testa");
        testMap.put("b", "testb");

        System.out.println(testMap.get("a"));
        System.out.println(testMap.get("b"));
        System.out.println(testMap.get("c"));

        // ConcurrentLinkedHashMap的用法
        System.out.println("\nConcurrentLinkendHashMap的用法.");
        EvictionListener<String, String> listener = new EvictionListener<String, String>() {
            @Override
            public void onEviction(String key, String value) {
                System.out.println("Evicted key=" + key + ", value=" + value);
            }
        };
        EntryWeigher<String, String> memoryUsageWeigher = new EntryWeigher<String, String>() {
            @Override
            public int weightOf(String key, String value) {
                long bytes = key.getBytes().length + value.getBytes().length;
                return (int) Math.min(bytes, Integer.MAX_VALUE);
            }
        };
        ConcurrentMap<String, String> cache = new ConcurrentLinkedHashMap.Builder<String, String>()
            .maximumWeightedCapacity(10)
            .listener(listener)
            .weigher(memoryUsageWeigher)
            .build();
        cache.put("a", "aaa");
        cache.put("b", "bbb");
        cache.put("c", "c");
        cache.put("d", "d");
        cache.put("e", "ee");
        System.out.println("一个字母占用一个字节,验证:" + "aaaabbbbcc".getBytes().length);
        System.out.println(cache.get("a"));
        System.out.println(cache.get("b"));
        System.out.println(cache.get("c"));
        System.out.println(cache.get("d"));
        System.out.println(cache.get("e"));
    }

}

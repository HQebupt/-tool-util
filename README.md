# MapMaker
和 *ConcurrentHashMap* 用法一样,功能更多.继承自
```java
java.lang.Object
com.google.common.collect.MapMaker
```
## 描述
A builder of ConcurrentMap instances having any combination of the following features:
* keys or values automatically wrapped in weak or soft references
* notification of evicted (or otherwise removed) entries
* on-demand computation of values for keys not already present

## Usage example:
```java
   ConcurrentMap<Request, Stopwatch> timers = new MapMaker()
       .concurrencyLevel(4)
       .weakKeys()
       .makeMap();  
```     
These features are all optional; `new MapMaker().makeMap()` \
returns a valid concurrent map that behaves similarly to a ConcurrentHashMap.

## maven 依赖
```xml
    <dependencies>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>16.0.1</version>
        </dependency>
    </dependencies>
```


# ConcurrentLinkedHashmap
1. [用法](https://code.google.com/p/concurrentlinkedhashmap/wiki/ExampleUsage)
2. [源码解析](http://janeky.iteye.com/blog/1534352)

## Usage exmaple
```java
ConcurrentMap<String, String> cache = new ConcurrentLinkedHashMap.Builder<String, String>()
            .maximumWeightedCapacity(10)
            .listener(listener)
            .weigher(memoryUsageWeigher)
            .build();
```
* maximumWeightedCapacity: 缓存的最大容量
* weighter: 容量的计算方法. 什么时候判断容易已经满了，是根据weight。每个元素都有一个weight，每增加一个元素，weight累计。\
当达到最大值的时候，就需要剔除最少操作的那个元素了，并且触发相关的事件listener。
* listener: 元素被删除时,触发的事件.

## maven 依赖
```xml
<dependency>
    <groupId>com.googlecode.concurrentlinkedhashmap</groupId>
    <artifactId>concurrentlinkedhashmap-lru</artifactId>
    <version>1.4.2</version>
</dependency>
```

# ThreadFactory
封装一次线程,添加线程的额外属性,比如是否是守护线程, 如果是守护线程会随着main线程的结束而立刻结束.详细见`thread.ThreadFactoryTest`

# 自动过期的Map实现
`TimeCacheMap`和`AutoExpiredMap`哪个更加高效呢?

[程序完整性的钩子函数](http://www.cnblogs.com/baibaluo/p/3185925.html)

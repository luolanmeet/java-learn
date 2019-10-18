package pers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JDK8 ArrayBlockingQueue 是依靠 ReentrantLock 和 Condition 实现的
 * 阻塞的原因是使用了Condition#await
 */
public class MyArrayBlockingQueue<E> {

    // 存储数据的数组
    final Object[] items;
    
    // 从数组取数据时，数据的下标
    int takeIndex;
    
    // 下个数据存入的位置
    int putIndex;
    
    // 当前存储的数据总数
    int count;
    
    final ReentrantLock lock;

    // 当队列为空，而调用take()时，会调用notEmpty.await()阻塞线程，
    // 有值存入时，notEmpty.signal()，唤醒因为调take()阻塞的线程
    private final Condition notEmpty;
    
    // 当队列为满，而调用put()时，会调用notFull.await()阻塞线程，
    // 有值出队时，notFull.signal()，唤醒因为调put()阻塞的线程
    private final Condition notFull;
    
    public MyArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }
    
    public MyArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }
    
    /**
     * 队列满时会阻塞notFull.await()
     * 直到有数据出队notFull.signal()
     */
    public void put(E e) throws InterruptedException {
    
        // 源码是会另外声明一个变量存储lock，为什么不直接使用呢？
        // 并发场景下可能对象的属性会发生变化，因此先存起来？
        final ReentrantLock lock = this.lock;
        
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                notFull.await();
            }
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }
    
    public boolean offer(E e) throws InterruptedException {
        
        final ReentrantLock lock = this.lock;
        
        lock.lockInterruptibly();
        try {
            if (count == items.length) {
                return false;
            } else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 队列空时会阻塞notEmpty.await()
     * 直到有数据入队notEmpty.signal()
     */
    public E take() throws InterruptedException {
    
        final ReentrantLock lock = this.lock;
        
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
    
    public E poll() {
        
        final ReentrantLock lock = this.lock;
        
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 数据入队
     * 循环队列，到队尾则下标重置
     */
    private void enqueue(E e) {
        final Object[] items = this.items;
        items[putIndex] = e;
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
        notEmpty.signal(); // 唤醒因take()数据而阻塞的线程
    }
    
    /**
     * 数据出队
     * 循环队列，到队尾则下标重置
     */
    private E dequeue() {
        final Object[] items = this.items;
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        notFull.signal(); // 唤醒因put()数据而阻塞的线程
        return x;
    }
    
}

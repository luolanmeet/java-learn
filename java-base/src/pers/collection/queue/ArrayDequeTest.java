package pers.collection.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeTest {

    public static void main(String[] args) {
        Deque<Integer> queue = new ArrayDeque<>();

        // 入队
        System.out.println(queue.add(1));
        System.out.println(queue.offer(2));

        // 队首数据出队
        System.out.println(queue.pop());
        System.out.println(queue.poll());

        queue.addFirst(10);
        queue.addLast(20);

        // 获取队首数据，但不出队
        System.out.println(queue.peek());
        System.out.println(queue.element());

        // 移除队首数据
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }

}

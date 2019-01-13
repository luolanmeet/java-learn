package com;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 指定当前类为websocket服务器端 
 * @author cck
 */
@ServerEndpoint("/websocket")
public class WebSocketDemo {
    
    /**
     * 保存在线用户数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    
    /**
     * 存放每个客户端对应的Session对象
     */
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();
    
    /**
     * 与某个客户端的连接会话，通过它来给用户发送数据
     */
    private Session session;
    
    /**
     * 连接成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        
        this.session = session;
        sessionSet.add(session);
        onlineCount.incrementAndGet();
        System.out.println("有新连接加入，当前在线人数为" + onlineCount.get());
    }
    
    @OnClose
    public void onClose() {
        
        sessionSet.remove(this.session);
        onlineCount.decrementAndGet();
        System.out.println("有连接关闭，当前在线人数为" + onlineCount.get());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息：" + message);
        
        // 群发消息
        for (Session s : sessionSet) {
            s.getBasicRemote().sendText(message);
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    
}

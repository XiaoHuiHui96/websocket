package com.socke;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket的聊天简单例子
 * @author Administrator
 *
 */
@ServerEndpoint("/websocket")
public class websocketTest {
	
	/** 记录当前在线人数 */
	private static int onlineCount=0;
	/** 存放每个客户端的对象 */
	private static CopyOnWriteArraySet<websocketTest> webSocketSet =new CopyOnWriteArraySet<websocketTest>();
	/** 与某个客户端的连接会话 */
	private Session session;
	/**
	 * 连接建立成功调用的方法
	 * @param session 可选的参数，session为与某个客户端的连接会话，需要通过他来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session=session;
		webSocketSet.add(this);//加入集合中		addOnlineCount();
		addOnlineCount();//连接数加一
		System.out.println("新的连接");
	}
	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);//移除
		subOnlineCount();//减一
		System.out.println("有连接关闭");
	}
	/**
	 * 收到客户端消息后调用的方法
	 * @param messge 客户段发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message,Session session){
		System.out.println("客户端的消息"+message);
		for(websocketTest item : webSocketSet){
			try {
				item.sendMessage(message);//向集合中的所有的socket类发送该消息
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session,Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
	/**
	 * 发送消息
	 * @param message 消息
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException  {
		this.session.getBasicRemote().sendText(message);
	}
	
	/**
	 * 线程安全
	 * 返回当前全部人数
	 * @return
	 */
	public static synchronized int getOnlineCount(){
		return onlineCount;
	}
	
	/**
	 * 增加连接数
	 */
	public static synchronized void addOnlineCount(){
		websocketTest.onlineCount++;
	}
	/**
	 * 减少连接数
	 */
	public static synchronized  void subOnlineCount(){
		websocketTest.onlineCount--;
	}
}

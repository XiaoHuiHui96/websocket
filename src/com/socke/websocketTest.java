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
 * websocket�����������
 * @author Administrator
 *
 */
@ServerEndpoint("/websocket")
public class websocketTest {
	
	/** ��¼��ǰ�������� */
	private static int onlineCount=0;
	/** ���ÿ���ͻ��˵Ķ��� */
	private static CopyOnWriteArraySet<websocketTest> webSocketSet =new CopyOnWriteArraySet<websocketTest>();
	/** ��ĳ���ͻ��˵����ӻỰ */
	private Session session;
	/**
	 * ���ӽ����ɹ����õķ���
	 * @param session ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session=session;
		webSocketSet.add(this);//���뼯����		addOnlineCount();
		addOnlineCount();//��������һ
		System.out.println("�µ�����");
	}
	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);//�Ƴ�
		subOnlineCount();//��һ
		System.out.println("�����ӹر�");
	}
	/**
	 * �յ��ͻ�����Ϣ����õķ���
	 * @param messge �ͻ��η��͹�������Ϣ
	 * @param session ��ѡ�Ĳ���
	 */
	@OnMessage
	public void onMessage(String message,Session session){
		System.out.println("�ͻ��˵���Ϣ"+message);
		for(websocketTest item : webSocketSet){
			try {
				item.sendMessage(message);//�򼯺��е����е�socket�෢�͸���Ϣ
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
	/**
	 * ��������ʱ����
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session,Throwable error){
		System.out.println("��������");
		error.printStackTrace();
	}
	
	/**
	 * ������Ϣ
	 * @param message ��Ϣ
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException  {
		this.session.getBasicRemote().sendText(message);
	}
	
	/**
	 * �̰߳�ȫ
	 * ���ص�ǰȫ������
	 * @return
	 */
	public static synchronized int getOnlineCount(){
		return onlineCount;
	}
	
	/**
	 * ����������
	 */
	public static synchronized void addOnlineCount(){
		websocketTest.onlineCount++;
	}
	/**
	 * ����������
	 */
	public static synchronized  void subOnlineCount(){
		websocketTest.onlineCount--;
	}
}

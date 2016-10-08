<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
      Welcome<br/><input id="text" type="text"/>
      <button onclick="send()">发送消息</button>
     <hr/>
     <button onclick="closeWebSocket()">关闭WebSocket连接</button>
     <hr/>
     <div id="message"></div>
</body>
<script type="text/javascript">
     var websocket = null;//socket
     //判断当前浏览器是否支持WebSocket
     if ('WebSocket' in window) {
         websocket = new WebSocket("ws://localhost:8080/websocke/websocket");//通过指定的url去连接指定的websocket类
     }
     else {
         alert('当前浏览器 Not support websocket')
     }
 
     //连接发生错误的回调方法
     websocket.onerror = function () {
         setMessageInnerHTML("WebSocket连接发生错误");
     };
 
     //连接成功建立的回调方法
     websocket.onopen = function () {
         setMessageInnerHTML("WebSocket连接成功");
     }
 
     //接收到消息的回调方法，event事件对象，服务器发送回来的消息
     websocket.onmessage = function (event) {
         setMessageInnerHTML(event.data);//获取到消息事件对象中的消息内容，并且输出
     }
 
     //连接关闭的回调方法
     websocket.onclose = function () {
         setMessageInnerHTML("WebSocket连接关闭");
     }
 
     //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
     window.onbeforeunload = function () {
         closeWebSocket();
     }
 
     //将消息显示在网页上
     function setMessageInnerHTML(innerHTML) {
         document.getElementById('message').innerHTML += innerHTML + '<br/>';
     }
 
     //关闭WebSocket连接
     function closeWebSocket() {
         websocket.close();
     }
 
     //发送消息
     function send() {
         var message = document.getElementById('text').value;
         websocket.send(message);//通过socket把消息发送到服务器
     }
 </script>
</html>
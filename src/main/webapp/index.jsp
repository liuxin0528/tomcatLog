<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>log</title>
<script type="text/javascript" src="./common/js/jquery-1.10.2.min.js"></script>
</head>
<body>

<div id="log-container" style="height:500px; overflow-y:scroll; background:#333; color:#aaa; padding:10px">
	<div></div>
</div>

</body>

<script type="text/javascript">
$(document).ready(function(){
	//指定websocket路径
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
  	if('WebSocket' in window){
  		websocket = new WebSocket("ws://localhost:8080/tomcatLog/log");
  	}else{
  		alert('Not support websocket')
  	}
	
  //连接成功建立的回调方法
  	websocket.onopen = function(event){
  		//alert("open");
  	}
	
	websocket.onmessage = function(event){
		//接收服务端的实时日志并添加到HTML页面中
		$("#log-container div").append(event.data);
		//滚动条滚动到最底部
		$("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
	}
	
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		closeWebSocket();
	}
	
	//关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
	}
})
</script>

</html>
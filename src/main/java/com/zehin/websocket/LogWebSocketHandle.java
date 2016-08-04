/*
 * @(#)LogWebSocketHandle.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.websocket;

import java.io.InputStream;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *	日期		:	2016年8月4日<br>
 *	作者		:	liuxin<br>
 *	项目		:	tomcatLog<br>
 *	功能		:	<br>
 */

@ServerEndpoint("/log")
public class LogWebSocketHandle {
	
	private Process process;
	
	private InputStream inputStream;
	
	/**
	 * 开启websocket
	 * Description : 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session){
		try {
			System.out.println("=========================开启websocket========================================");
			//执行tail -f 命令
			process = Runtime.getRuntime().exec("tail -f /root/apache-tomcat-7.0.62/logs/catalina.out");
			inputStream = process.getInputStream();
			
			//一定要启动新线程，防止inputstream 阻塞处理websocket的线程
			TailLogThread thread = new TailLogThread(inputStream, session);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭websocket
	 * Description :
	 */
	@OnClose
	public void onClose(){
		System.out.println("=========================关闭websocket========================================");
		try {
			if(inputStream != null){
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(process != null){
			process.destroy();
		}
	}
	
	@OnError
	public void onError(Throwable thr){
		thr.printStackTrace();
	}
}

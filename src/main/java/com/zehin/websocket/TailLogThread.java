/*
 * @(#)TailLogThread.java 
 * 
 * Copyright 2016 by 青岛众恒信息科技股份有限公司 . 
 * All rights reserved.
 *
 */
package com.zehin.websocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.websocket.Session;

/**
 *	日期		:	2016年8月4日<br>
 *	作者		:	liuxin<br>
 *	项目		:	tomcatLog<br>
 *	功能		:	线程类<br>
 */
public class TailLogThread extends Thread{
	
	private BufferedReader reader;
	private Session session;
	
	public TailLogThread(InputStream in, Session session) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.session = session;
	}

	
	@Override
	public void run() {
		String line;
		try {
			while((line = reader.readLine()) != null){
				//将实时日志通过websocket发送到客户端，给每一行添加一个HTML换行
				session.getBasicRemote().sendText(line + "<br>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
	
	

}

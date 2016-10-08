package com.socke;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;

public class protoco implements HttpUpgradeHandler {
	
	private static final String CRLF="\r\n";
	private final byte[] echoData=new byte[128];
	private WebConnection wc=null;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		try {
//			wc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(WebConnection wc) {
		this.wc=wc;
		try {
			ServletOutputStream output=wc.getOutputStream();
			ServletInputStream input=wc.getInputStream();
			input.read(echoData);
			String data=new String(echoData).trim();
			int one=Integer.parseInt(data.substring(0, data.indexOf(":")));
			int two=Integer.parseInt(data.substring(data.indexOf(":")+1,data.length()));
			int sum=one+two;
			String resStr="½á¹û:"+sum+CRLF;
					resStr+=CRLF;
			output.write(resStr.getBytes());
			output.flush();
		} catch (IOException e) {
			System.out.println("´íÎó");
			e.printStackTrace();
		}
	}

}

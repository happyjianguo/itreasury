package com.iss.itreasury.settlement.exporttrans.bizlogic;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;


public class ExportTXT {
	
	private String sContent = "";    //文本内容
	
	public ExportTXT(String sContent){
		this.sContent = sContent;
	}
	
	/**
	 * 创建一个TXT文件
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	public void createTXT (HttpServletResponse response,String fileName) throws IOException{
		
		OutputStream out = response.getOutputStream();//取得输出流
        response.reset();//清空输出流
        String att="attachment; filename="+this.gbkToIso8859(fileName)+".txt";
        response.setHeader("Content-disposition",att );//设定输出文件头
        response.setContentType("text/plain");//定义输出类型
        out.write(this.sContent.getBytes());//写出内存中
        out.flush();
        out.close();
        
	}
	
	/**
	 * 将得到的GBK编码的字符转换成ISO8859-1编码的字符
	 * 
	 * @param s 目标字符串.
	 * @return 可显示符串.
	 */

	private static String gbkToIso8859(String s) {
		try {
			String s2 = new String(s.getBytes("GBK"), "iso8859-1");
			return s2;
		} catch (Exception e) {
			return null;
		}
	}
}

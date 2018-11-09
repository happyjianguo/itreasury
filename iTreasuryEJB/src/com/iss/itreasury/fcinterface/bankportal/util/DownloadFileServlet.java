/*
 * Created on 2005-7-4
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * @author jsxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DownloadFileServlet extends HttpServlet
{
	/** 日志对象 */
	private static Logger log = new Logger(DownloadFileServlet.class);
	
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException
	{
		doGetPost(request, response);
	}
	/**
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException
	{
		doGetPost(request, response);
	}
	public void doGetPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException
		{
			
			String docName = request.getParameter("docName");
            String docURI = request.getParameter("docURI");
            log.info("docName="+docName+",docURI="+docURI);
            docName = DataFormat.toChinese(docName);
            docURI = DataFormat.toChinese(docURI);
		    if(docName.lastIndexOf(".")>=0)
		    {
		        docName = docName.substring(0,docName.lastIndexOf("."));//去掉扩展名
		    }
		    int len = (docName.length()>19)?16:docName.length();
            String fileName = docName.substring(0,len);
		    if(docURI.lastIndexOf(".")>=0)
		    {
		        fileName = fileName + docURI.substring(docURI.lastIndexOf("."));
		    }
			  
            ServletContext sc = getServletContext();
            String mimeType = sc.getMimeType(fileName);
			            
            if (mimeType == null) {
          	   mimeType="application/any";
             }
             	               
            response.setHeader("Content-Disposition","attachment; filename=\"" + toUtf8String(fileName)+"\"");	              
            
	        response.setContentType(mimeType); 			
	        try
			{
			    File file = new File(docURI);
			    FileInputStream fis = new FileInputStream(file);
			    long lFileLength = file.length();
			    byte[] binaryBytes = new byte[(int) lFileLength];
			    fis.read(binaryBytes);
			    fis.close();
			  
	            response.setContentLength(binaryBytes.length);
	            java.io.OutputStream out = response.getOutputStream();
	            out.write(binaryBytes);
	            out.flush();	
	            out.close();
			}catch(IOException e)
			{				
				log.error("文件下载失败",e);
			}
		}
		/**
	     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	     * @param s 原文件名
	     * @return 重新编码后的文件名
	     */
	    protected String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length();i++) {
		    char c = s.charAt(i);
		    if (c >= 0 && c <= 255) {
			sb.append(c);
		    } else {
			byte[] b;
			try {
				Character character = new Character(c);
			    b = character.toString().getBytes("utf-8");
			} catch (Exception ex) {
			    System.out.println(ex);
			    b = new byte[0];
			}
			for (int j = 0; j < b.length; j++) {
			    int k = b[j];
			    if (k < 0) k += 256;
			    sb.append("%" + Integer.toHexString(k).toUpperCase());
			}
		   }
		}
		return sb.toString();
	   }
}

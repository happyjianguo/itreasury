package com.iss.itreasury.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import com.jspsmart.upload.*;
import com.iss.itreasury.util.AutoFileBean;
import com.iss.itreasury.util.DownLoadFileNameEncryptionAndDecrypt;
import com.iss.itreasury.dataentity.AutoFileInfo;

public class DownLoadClientReportServlet extends HttpServlet
{
	private ServletConfig config;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		this.config = config;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		downloadFile(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		downloadFile(req, res);
	}

	/**
	* 
	* @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	* 
	* 需要提交的变量有 
	* 1 FileID 文件的ID
	*/
	public void downloadFile(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
			String sDocPath = "";
			String temp = "";
			
		try
		{
			temp = req.getParameter("sDocPath");
		
			if(!temp.equals("")&&temp.trim().length()>0)
			{
				sDocPath = temp.trim();
				//jspsmart initial;
				SmartUpload mySmartUpload = new SmartUpload();
				mySmartUpload.initialize(config, req, res);
				//download file
				mySmartUpload.setContentDisposition(null); 
				mySmartUpload.downloadFile(sDocPath);
				
			}
			
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
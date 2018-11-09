package com.iss.itreasury.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import com.jspsmart.upload.*;
import com.iss.itreasury.util.Env;

public class DownLoadModelServlet extends HttpServlet
{
	private ServletConfig config;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		this.config = config;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		downloadModel(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		downloadModel(req, res);
	}

	/**
	* 
	* @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	* 
	* 网银端批量付款模板下载
	* 
	*/
	public void downloadModel(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
			String strAction1=req.getParameter("strAction1");
			String sDocPath="";
			if("model".equals(strAction1)){
				sDocPath = Env.UPLOAD_PATH+"ztxsqpjmodel.xls";
			}
			else{
				sDocPath = Env.UPLOAD_PATH+"ebank/model/model.xls";
			}
			
			//add by xiang 20120618
			String reqFile = req.getParameter("file");
			if(reqFile!=null && !reqFile.equals("")){
				sDocPath = reqFile;
			}
			
		try
		{

			res.setContentType("application/x-msdownload;charset=gbk");
//			res.setHeader("Content-Disposition", "attachment;" + "filename="
//                     + new String(reqFile.getBytes("gb2312"), "iso-8859-1"));
			//jspsmart initial;
			SmartUpload mySmartUpload = new SmartUpload();
			mySmartUpload.initialize(config, req, res);
			//download file
			mySmartUpload.setContentDisposition(null); 
			mySmartUpload.downloadFile(sDocPath);
				
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
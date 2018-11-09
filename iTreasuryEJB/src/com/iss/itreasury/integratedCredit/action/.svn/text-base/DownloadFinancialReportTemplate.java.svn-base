package com.iss.itreasury.integratedCredit.action;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 下载财务分析模板Servlet
 * @author lcliu
 */
public class DownloadFinancialReportTemplate extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private ServletConfig config = null;
	
	private String filePath = "/upload/loan/template/FinancialReportTemplate.xls";	// 默认文件路径
	private String fileName = "财务报表模板.xls";			// 默认文件名
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		this.config = config;
	}
	
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String strTemp = null;
		
		strTemp = (String)request.getAttribute("filePath");		// 如果有参数传入
		if (strTemp != null && !strTemp.equals(""))
		{
			filePath = strTemp;
		}

		strTemp = (String)request.getAttribute("fileName");		// 如果有参数传入
		if (strTemp != null && !strTemp.equals(""))
		{
			fileName = strTemp;
		}
		
		SmartUpload upload = new SmartUpload();
		upload.initialize(config, request, response);
		upload.setContentDisposition(null);
		try
		{
			upload.downloadFile(filePath, "", URLEncoder.encode(fileName,"utf-8"));
		}
		catch (SmartUploadException e)
		{
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		downloadTemplate(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		downloadTemplate(request, response);
	}
}

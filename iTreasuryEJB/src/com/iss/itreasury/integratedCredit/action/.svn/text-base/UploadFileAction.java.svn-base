package com.iss.itreasury.integratedCredit.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.iss.itreasury.integratedCredit.CreditUtil.Tools;
import com.iss.itreasury.loan.aftercredit.dao.AfterCreditFileDao;
import com.iss.itreasury.loan.aftercredit.dataentity.AfterCreditFileInfo;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 上传文件类，封装jspsmart进行上传
 * create @ 08-11-21
 * @author lcliu
 */
public class UploadFileAction
{
	private SmartUpload smartUplaod = new SmartUpload();
	private com.jspsmart.upload.File uploadFile = null;
	private Request request = null;
	
	private String strFileNameTotal = "";	// 上传文件的命名
	private String strFilePath = "";		// 文件路径
	private String strFileName = "";		// 文件名
	private String strFileTimeName = "";	// 文件的时间命名
	private String strFileURL = "";		// 文件的URL		= strFilePath + strFileTimeName
	private String strFileExt = "";		// 可用的文件扩展名
	private PageContext context = null;		// 
	private String strFileExt1 = "";		// 可用的文件扩展名1 :xls,xlsx
	
	private Log4j log = null;
	
	public UploadFileAction(PageContext context, long moduleID) throws ServletException
	{
		this.context = context;
		log = new Log4j(moduleID);
	}
	
	/**
	 * 将上传文件保存到指定的文件夹
	 * create @ 08-11-21
	 * @param strFilePath
	 * @return boolean
	 * @author lcliu
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws SmartUploadException 
	 */
	public boolean uploadTo(String strFilePath) throws ServletException, SmartUploadException, IOException
	{
		if (strFilePath.endsWith("/"))
		{
			this.strFilePath = strFilePath;
		}
		else
		{
			this.strFilePath = strFilePath + "/";
		}
		File filePath = new File(this.strFilePath);				// 若文件夹不存在则创建
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		
		smartUplaod.initialize(context);			// 初始化
		smartUplaod.setMaxFileSize(1024*1024*5);		// 为了上传安全，限制上传文件大小为5M
		if (!strFileExt.equals(""))
		{
			smartUplaod.setAllowedFilesList(strFileExt);	// 初始化允许的文件后缀名
		}
		smartUplaod.upload();						// 上传
		request = smartUplaod.getRequest();			// 获取request
		uploadFile = smartUplaod.getFiles().getFile(0);		// 获得上传的文件
		strFileName = uploadFile.getFileName();		// 获得上传文件的文件名
		
		strFileTimeName = Tools.fileName(uploadFile.getFileExt());		// 获得上传文件的时间命名
		strFileURL = this.strFilePath + strFileTimeName;			// 获得strFileURL
		
		uploadFile.saveAs(strFileURL);			// 将上传文件保存到strFileURL
		
		log.print("===aeolus===:" + uploadFile.getFilePathName());
		
		return true;
	}
	
	public long upload4AfterCredit(String strFilePath,long reportID,long userID) throws ServletException, SmartUploadException, IOException
	{
		 long iResult = -1;
		if (strFilePath.endsWith("/"))
		{
			this.strFilePath = strFilePath;
		}
		else
		{
			this.strFilePath = strFilePath + "/";
		}
		File filePath = new File(this.strFilePath);				// 若文件夹不存在则创建
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		
		smartUplaod.initialize(context);			// 初始化
		smartUplaod.setMaxFileSize(1024*1024*5);		// 为了上传安全，限制上传文件大小为5M
		if (!strFileExt.equals(""))
		{
			smartUplaod.setAllowedFilesList(strFileExt1);	// 初始化允许的文件后缀名
		}
		smartUplaod.upload();						// 上传
		request = smartUplaod.getRequest();			// 获取request
		uploadFile = smartUplaod.getFiles().getFile(0);		// 获得上传的文件
		strFileName = uploadFile.getFileName();		// 获得上传文件的文件名
		strFileTimeName = Tools.fileName(uploadFile.getFileExt());		// 获得上传文件的时间命名
		strFileNameTotal = reportID+"_"+strFileName;		// 组合上传文件的名字，用户ID+贷后调查报告ID+上传文件原始的名字
		strFileURL = this.strFilePath + strFileTimeName ;			// 获得strFileURL
		
		uploadFile.saveAs(strFileURL);			// 将上传文件保存到strFileURL
		AfterCreditFileDao acf  = new AfterCreditFileDao();
		AfterCreditFileInfo acfi = new AfterCreditFileInfo();
		acfi.setFileName(strFileTimeName);
		acfi.setOldfileName(strFileName);
		acfi.setReportID(reportID);
		//acfi.setId(0);
		acfi.setUpFileDate(Env.getSystemDate());
		acfi.setUpUserID(userID);
		iResult = acf.saveUploadFile(acfi);
		log.print("===aeolus===:" + uploadFile.getFilePathName());
		
		return iResult;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public String getStrFilePath() {
		return strFilePath;
	}

	public void setStrFilePath(String strFilePath) {
		this.strFilePath = strFilePath;
	}

	public String getStrFileTimeName() {
		return strFileTimeName;
	}

	public String getStrFileURL() {
		return strFileURL;
	}

	public void setStrFileExt(String strFileExt) {
		this.strFileExt = strFileExt;
	}

	public Request getRequest() {
		return request;
	}
}

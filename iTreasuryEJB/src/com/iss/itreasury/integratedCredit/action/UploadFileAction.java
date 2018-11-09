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
 * �ϴ��ļ��࣬��װjspsmart�����ϴ�
 * create @ 08-11-21
 * @author lcliu
 */
public class UploadFileAction
{
	private SmartUpload smartUplaod = new SmartUpload();
	private com.jspsmart.upload.File uploadFile = null;
	private Request request = null;
	
	private String strFileNameTotal = "";	// �ϴ��ļ�������
	private String strFilePath = "";		// �ļ�·��
	private String strFileName = "";		// �ļ���
	private String strFileTimeName = "";	// �ļ���ʱ������
	private String strFileURL = "";		// �ļ���URL		= strFilePath + strFileTimeName
	private String strFileExt = "";		// ���õ��ļ���չ��
	private PageContext context = null;		// 
	private String strFileExt1 = "";		// ���õ��ļ���չ��1 :xls,xlsx
	
	private Log4j log = null;
	
	public UploadFileAction(PageContext context, long moduleID) throws ServletException
	{
		this.context = context;
		log = new Log4j(moduleID);
	}
	
	/**
	 * ���ϴ��ļ����浽ָ�����ļ���
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
		File filePath = new File(this.strFilePath);				// ���ļ��в������򴴽�
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		
		smartUplaod.initialize(context);			// ��ʼ��
		smartUplaod.setMaxFileSize(1024*1024*5);		// Ϊ���ϴ���ȫ�������ϴ��ļ���СΪ5M
		if (!strFileExt.equals(""))
		{
			smartUplaod.setAllowedFilesList(strFileExt);	// ��ʼ��������ļ���׺��
		}
		smartUplaod.upload();						// �ϴ�
		request = smartUplaod.getRequest();			// ��ȡrequest
		uploadFile = smartUplaod.getFiles().getFile(0);		// ����ϴ����ļ�
		strFileName = uploadFile.getFileName();		// ����ϴ��ļ����ļ���
		
		strFileTimeName = Tools.fileName(uploadFile.getFileExt());		// ����ϴ��ļ���ʱ������
		strFileURL = this.strFilePath + strFileTimeName;			// ���strFileURL
		
		uploadFile.saveAs(strFileURL);			// ���ϴ��ļ����浽strFileURL
		
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
		File filePath = new File(this.strFilePath);				// ���ļ��в������򴴽�
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		
		smartUplaod.initialize(context);			// ��ʼ��
		smartUplaod.setMaxFileSize(1024*1024*5);		// Ϊ���ϴ���ȫ�������ϴ��ļ���СΪ5M
		if (!strFileExt.equals(""))
		{
			smartUplaod.setAllowedFilesList(strFileExt1);	// ��ʼ��������ļ���׺��
		}
		smartUplaod.upload();						// �ϴ�
		request = smartUplaod.getRequest();			// ��ȡrequest
		uploadFile = smartUplaod.getFiles().getFile(0);		// ����ϴ����ļ�
		strFileName = uploadFile.getFileName();		// ����ϴ��ļ����ļ���
		strFileTimeName = Tools.fileName(uploadFile.getFileExt());		// ����ϴ��ļ���ʱ������
		strFileNameTotal = reportID+"_"+strFileName;		// ����ϴ��ļ������֣��û�ID+������鱨��ID+�ϴ��ļ�ԭʼ������
		strFileURL = this.strFilePath + strFileTimeName ;			// ���strFileURL
		
		uploadFile.saveAs(strFileURL);			// ���ϴ��ļ����浽strFileURL
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

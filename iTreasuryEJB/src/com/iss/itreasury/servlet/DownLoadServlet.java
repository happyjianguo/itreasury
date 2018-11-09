package com.iss.itreasury.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import com.jspsmart.upload.*;
import com.iss.itreasury.util.AutoFileBean;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DownLoadFileNameEncryptionAndDecrypt;
import com.iss.itreasury.dataentity.AutoFileInfo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class DownLoadServlet extends HttpServlet
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
	
	
	public boolean isNumeric(String str)
	{
	Pattern pattern = Pattern.compile("[0-9]*");
	Matcher isNum = pattern.matcher(str);
	if( !isNum.matches() )
	{
	return false;
	}
	return true;
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
		long lFileID = -1;
		long lDispatcherType = -1;
		String strDispatcherPath = "";
		String strServerFileName = "";
		String strTemp = "";
		DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
		boolean isFileIDEncrypt=Config.getBoolean(ConfigConstant.GLOBAL_DOWNLOAD_FILEID_CAN_ENCRYPT,false);

		try
		{
		    //get the source filepath
			strTemp = req.getParameter("FileID");
			if (strTemp == null || strTemp.equals(""))
			{
				// 缺少出错处理
				lFileID = -1;
			}
			else
			{
				//验证FileID是明文还是密文（全数字还是非全数字）add@songwenxiao
				if(isFileIDEncrypt)
				{
					lFileID = Long.parseLong(en.getDesString(strTemp));
				}
				else 
				{
					lFileID=Long.parseLong(strTemp);
				}
				

			}
			
			String sFileName = "";
			strTemp = req.getParameter("FileName");
			if (strTemp == null || strTemp.equals(""))
			{
				// 缺少出错处理
				sFileName = "";
			}
			else
			{
				sFileName = en.getDesString(strTemp.trim());
			}
			
			AutoFileInfo fileInfo = null;
			fileInfo = AutoFileBean.getFileByID(lFileID);
			strServerFileName = fileInfo.getServerPath() + fileInfo.getServerFileName();
			System.out.println(strServerFileName);
			//jspsmart initial;
			SmartUpload mySmartUpload = new SmartUpload();
			mySmartUpload.initialize(config, req, res);
			mySmartUpload.setContentDisposition(null);
			//download file
			
			if (lFileID!=-1)
			{
				mySmartUpload.downloadFile(strServerFileName, strServerFileName);
			}
			else if (sFileName!=null&&!sFileName.equals(""))
			{
				mySmartUpload.downloadFile(sFileName, sFileName);
			}
			else 
			{
				return;
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
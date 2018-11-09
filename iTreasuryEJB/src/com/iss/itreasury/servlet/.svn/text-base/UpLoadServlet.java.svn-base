package com.iss.itreasury.servlet;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.dataentity.AutoFileInfo;
import com.iss.itreasury.util.AutoFileBean;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.jspsmart.upload.SmartUpload;

public class UpLoadServlet extends HttpServlet {
	private ServletConfig config;

	public UpLoadServlet() {

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.config = config;
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest,
	 *      HttpServletResponse)
	 * 
	 * 需要提交的变量有 1 ParentID 文件所属对象的ID 2 DocType 上传文档类型 参见Constant.DocType 3
	 * DestJsp 上传成功后，返回的目标jsp文件 4 UserID 用户标示 5 ClientName 用户输入的文件名称 返回的对象为
	 * AutoFileInfo （具体信息参看AutoFileInfo类定义）
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
		uploadFile(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
		uploadFile(req, res);
	}

	public void uploadFile(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
		long lDocType = -1;
		long lModuleTypeID = -1;
		String strDestPath = "";
		long lUserID = -1;
		String sClientName = "";
		String strDispatcherPath = "";
		String strServerFileName = "";
		String strTemp = "";
		long lParentID = -1;
		long ParentIDType = -1;
		long ModuleID = -1;
		long TransTypeID = -1;
		long TransSubTypeID = -1;
		long CurrencyID = -1;
		long OfficeID = -1;
		String transCode = "";
		// 客户中心需要的变量
		long lClientID = -1;
		long lTypeID = -1;
		long ArchivesTypeID=-1;  //档案类型ID
		long PayFormID=-1;  //放款单ID
		long ContractID=-1; //合同ID
		// SessionMng sessionMng =
		// (SessionMng)req.getSession().getAttribute("sessionMng");

		try {
			// get the destination filepath
			strTemp = req.getParameter("DocType");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param DocTypeis null");

			} else {
				lDocType = Long.parseLong(strTemp);
			}

			strDestPath = AutoFileBean.getDestPath(lDocType);
			if (strDestPath == null || strDestPath.equals("")) {
				// 缺少出错处理
				System.out.println("param strDestPath is null");
			}

			// get the request dispatcher path
			strTemp = req.getParameter("DestJsp");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param DestJsp is null");
			} else {
				strDispatcherPath = DataFormat.toChinese(strTemp.trim());
			}
			if (strDispatcherPath == null || strDispatcherPath.equals("")) {
				// 缺少出错处理
				System.out.println("param strDispatcherPath is null");

			}
			// get the uploader
			strTemp = req.getParameter("ModuleID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ModuleID is null");

			} else {
				ModuleID = Long.parseLong(strTemp);
			}
			
			strTemp = req.getParameter("UserID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param UserID is null");

			} else {
				lUserID = Long.parseLong(strTemp);
			}
			strTemp = req.getParameter("ClientName");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ClientName is null");

			} else {
				if(req.getCharacterEncoding() == null || !req.getCharacterEncoding().equals("GBK")){
					sClientName = DataFormat.toChinese(strTemp.trim());
				}else{
					sClientName = strTemp.trim();
				}
			}
			strTemp = req.getParameter("ParentID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ParentID is null");

			} else {
				lParentID = Long.parseLong(strTemp);
			}

			strTemp = req.getParameter("ArchivesTypeID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ArchivesTypeID is null");

			} else {
				ArchivesTypeID = Long.parseLong(strTemp);
			}
			
			
			System.out.println("ArchivesTypeID="+ArchivesTypeID);
			
			
			strTemp = req.getParameter("ContractID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ContractID is null");
 
			} else {
				ContractID = Long.parseLong(strTemp);
			}
			
			
			System.out.println("ContractID="+ContractID);
			
			strTemp = req.getParameter("PayFormID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param PayFormID is null");

			} else {
				PayFormID = Long.parseLong(strTemp);
			}
			
			System.out.println("PayFormID="+PayFormID);
			strTemp = req.getParameter("TransTypeID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param TransTypeID is null");

			} else {
				TransTypeID = Long.parseLong(strTemp);
			}
			strTemp = req.getParameter("TransSubTypeID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param TransSubTypeID is null");

			} else {
				TransSubTypeID = Long.parseLong(strTemp);
			}
			strTemp = req.getParameter("CurrencyID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param CurrencyID is null");

			} else {
				CurrencyID = Long.parseLong(strTemp);
			}
			strTemp = req.getParameter("OfficeID");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param OfficeID is null");

			} else {
				OfficeID = Long.parseLong(strTemp);
			}
			strTemp = req.getParameter("ParentIDType");
			if (strTemp == null || strTemp.equals("")) {
				// 缺少出错处理
				System.out.println("param ParentIDType is null");

			} else {
				ParentIDType = Long.parseLong(strTemp);
			}
			// 客户中心需要的变量
			strTemp = req.getParameter("lClientID");
			if (strTemp != null && strTemp.length() > 0) {
				lClientID = Long.parseLong(strTemp);
			}
			System.out.println("lClientID="+lClientID);
			
			strTemp = req.getParameter("lTypeID");
			if (strTemp != null && strTemp.length() > 0) {
				lTypeID = Long.parseLong(strTemp);
			}

			strTemp = req.getParameter("transCode");
			if (strTemp != null && strTemp.length() > 0) {
				transCode = strTemp;
			}

			// create a new AutoFileInfo object and initialize it
			AutoFileInfo fileInfo = new AutoFileInfo();
			fileInfo.setFileSucc(false);
			fileInfo.setServerPath(strDestPath);
			fileInfo.setInputUserID(lUserID);
			fileInfo.setInputTime(DataFormat.getDateTime(DataFormat
					.getDateString()));
			fileInfo.setClientID(lClientID);
			fileInfo.setTypeID(lTypeID);
			
			fileInfo.setContractID(ContractID);
			fileInfo.setPayFormID(PayFormID);
			fileInfo.setArchivesTypeID(ArchivesTypeID); 
			fileInfo.setOfficeID(OfficeID);

			// jspsmart initial;
			SmartUpload mySmartUpload = new SmartUpload();
			mySmartUpload.initialize(config, req, res);

			if ((fileInfo.getFileDeniedExt() != null)
					&& (fileInfo.getFileDeniedExt().trim().length() != 0)) {
				mySmartUpload.setDeniedFilesList(fileInfo.getFileDeniedExt());
			}

			if ((fileInfo.getFileAllowedExt() != null)
					&& (fileInfo.getFileAllowedExt().trim().length() != 0)) {
				mySmartUpload.setAllowedFilesList(fileInfo.getFileAllowedExt());
			}

			if (fileInfo.getMaxFileSize() > 0) {
				mySmartUpload.setMaxFileSize(fileInfo.getMaxFileSize());
			}
			// set file directory by current time
			fileInfo.setServerPath(AutoFileBean.getPathByTime(fileInfo
					.getServerPath()));

			// upload file
			mySmartUpload.upload();

			com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(
					0);

			java.io.File f = new java.io.File(fileInfo.getServerPath());
			if (!f.exists()) {
				f.mkdirs();
			}

			try{
				if (myFile.isMissing()) 
				{
					throw new Exception("找不到上传后的文件");
				}
				
				fileInfo.setParentID(lParentID);
				fileInfo.setParentIDType(ParentIDType);
				fileInfo.setStatus(Constant.RecordStatus.VALID);
				fileInfo.setModuleID(ModuleID);
				fileInfo.setTransTypeID(TransTypeID);
				fileInfo.setTransSubTypeID(TransSubTypeID);
				fileInfo.setCurrencyID(CurrencyID);
				fileInfo.setOfficeID(OfficeID);
				fileInfo.setTransCode(transCode);
				fileInfo.setFileType(myFile.getFileExt());
				fileInfo.setClientFileName(sClientName);
				fileInfo.setClientPath(myFile.getFilePathName());
				fileInfo.setFileContentType(myFile.getContentType());
				fileInfo.setFileMimeType(myFile.getTypeMIME());
				fileInfo.setServerPath(fileInfo.getServerPath()
						+ (fileInfo.getServerPath().endsWith("/") ? "" : "/"));
				fileInfo.setServerFileName(AutoFileBean.getFileName(fileInfo));

				// 客户中心 需要的变量
				fileInfo.setClientID(lClientID);
				fileInfo.setTypeID(lTypeID);

				myFile.saveAs(fileInfo.getServerPath()
						+ fileInfo.getServerFileName());
				fileInfo.setFileSucc(true);
				
				if (myFile.getSize()<=0) 
				{
					throw new Exception("上传文件大小为零");
				}
				
				fileInfo = AutoFileBean.InsertFileInfoIntoDB(fileInfo);
				
			}catch(Exception exp){
				req.setAttribute("filenotfound", "文件上传失败，原因：" + exp.getMessage());				
			}

			req.setAttribute("fileInfo", fileInfo);

			ServletContext sc = getServletContext();
			// set the return url
			RequestDispatcher rd = sc.getRequestDispatcher(strDispatcherPath);

			rd.forward(req, res);
			
		} catch (Exception exp) {
			exp.printStackTrace();
			return;
		}
	}

}
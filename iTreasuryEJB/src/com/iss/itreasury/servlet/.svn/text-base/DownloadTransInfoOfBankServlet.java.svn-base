package com.iss.itreasury.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.iss.itreasury.settlement.bankinterface.bizlogic.ExportBankTransDataBiz;
import com.iss.itreasury.settlement.bankinterface.dao.Sett_BalanceOfBankAccountDAO;
//import com.iss.itreasury.settlement.bankinterface.dao.Sett_HisTransInfoOfBankAccountDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryBankAccountTransConditionInfo;
import com.iss.itreasury.settlement.setting.bizlogic.BranchBiz;
import com.iss.itreasury.settlement.setting.bizlogic.FilialeAccountSetBiz;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.QueryFilialeAccountInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;



public class DownloadTransInfoOfBankServlet extends HttpServlet 
{
	private static Log4j log = new Log4j(DownloadTransInfoOfBankServlet.class);
	private StringBuffer str_balance=new StringBuffer(256);
	private StringBuffer str_transAction=new StringBuffer(256);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) 
	{
		doGetPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) 
	{
		doGetPost(request, response);
	}

	public void doGetPost(HttpServletRequest request,HttpServletResponse response)
	{
		/*System.out.println("进入Servlet:DownloadTransInfoOfBankServlet");
		try
		{
			String sdate = (String) request.getParameter("strStartDate");
			String edate = (String) request.getParameter("strEndDate");
			String strAction = (String)request.getParameter("strAction");
			long lBankType = Long
					.parseLong(request.getParameter("lBankType") == null ? String
							.valueOf(0).toString()
							: (String) request.getParameter("lBankType"));
			if(lBankType<0)
			{
				lBankType = 0;
			}
			long officeID = Long.parseLong(request.getParameter("m_lOfficeID"));
			long currencyID=Long.parseLong(request.getParameter("m_lCurrencyID"));
			
			String returnStr = greatString(strAction,officeID,lBankType,currencyID,sdate,edate);
			
			//从配置文件中取得文件导出路径
			String outPutFilePath=Config.getProperty(Config.INTEGRATION_SERVICE_EXPORFILE_PATH,"");
			String exportFileName = null;
			String docName = null;
			String docURI = null;
			
			ExportBankTransDataBiz exportBankTransDataBiz=new ExportBankTransDataBiz();
			if(strAction.equals("exportBalance"))
			{
				docName = lBankType+"=bankAccountBalance.dat";
				exportFileName=outPutFilePath+docName;//余额文件
				log.print("账户余额文件:"+exportFileName);
				docURI = exportFileName;
				
				File exportOfBlanceFile=exportBankTransDataBiz.fileOpreat(exportFileName);
				exportBankTransDataBiz.dateExport(returnStr,exportOfBlanceFile);
			}
			else if(strAction.equals("exportTransaction"))
			{
				docName = lBankType+"="+sdate+"="+edate+".dat";
				exportFileName=outPutFilePath+docName;//交易文件
				log.print("账户交易文件:"+exportFileName);
				docURI = exportFileName;
				
				File exportOfTransactionFile=exportBankTransDataBiz.fileOpreat(exportFileName);
				exportBankTransDataBiz.dateExport(returnStr,exportOfTransactionFile);
			}
			downLoad(request,response,docName,exportFileName);	
			System.out.println("结束Servlet:DownloadTransInfoOfBankServlet");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}
	
	
	/**
	 * 查询符合条件的开户行账户 
	 * @param officeID
	 * @param lBankType
	 * @param currencyID
	 * @return
	 * @throws IException
	 */
	protected Collection findAllBranch(long officeID,long lBankType,long currencyID) throws IException 
	{
		BranchBiz branchBiz = new BranchBiz();//初始化开户行账户信息查询类
		QueryBranchInfo qbInfo = new QueryBranchInfo();//初始化开户行账户信息查询条件类	
		Collection c_branchinfo = null;//初始化开户行账户信息结果集	
		try
		{
			qbInfo.setOfficeID(officeID);
			qbInfo.setCurrencyID(currencyID);	
			qbInfo.setBankType(lBankType);
			c_branchinfo=branchBiz.findAllBranch(qbInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return c_branchinfo;
	}
	
	/**
	 * 查询符合条件的下属单位账户
	 * @param lBankType
	 * @param officeID
	 * @return
	 */
	protected Collection findAllFiliale (long lBankType,long officeID)
	{
		FilialeAccountSetBiz filialeAccountSetBiz=new FilialeAccountSetBiz();//初始化下属单位账户信息查询类
		QueryFilialeAccountInfo qfaInfo=new QueryFilialeAccountInfo();//初始化下属单位账户信息查询条件类	
		Collection c_filialeAccountInfo = null;//初始化下属单位账户信息结果集	
		
		try
		{
			qfaInfo.setBankType(lBankType);
			qfaInfo.setOfficeID(officeID);
			c_filialeAccountInfo=filialeAccountSetBiz.findByCondition(qfaInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return c_filialeAccountInfo;	
	}
	
	/**
	 * 将数据库中数据转换成规定的格式
	 * @param strAction
	 * @param officeID
	 * @param lBankType
	 * @param currencyID
	 * @param sdate
	 * @param edate
	 * @return 
	 * @throws Exception
	 */
	public String greatString (String strAction,long officeID,long lBankType,long currencyID,String sdate,String edate) throws Exception
	{
		StringBuffer str_Result = new StringBuffer(256);
		String str = null;
		/*ExportBankTransDataBiz exportBankTransDataBiz=new ExportBankTransDataBiz();
		
		Date dtStartDate = DataFormat.getDate(sdate);
		Date dtEndDate = DataFormat.getDate(edate);
		Timestamp starDate = DataFormat.getDateTime(sdate);
		Timestamp endDate = DataFormat.getDateTime(edate);	
		
		if(strAction.equals("exportBalance"))
		{
			String str_BalanceOfBranch = null;
			String str_BalanceOfFiliale = null;
			Sett_BalanceOfBankAccountDAO dao = new Sett_BalanceOfBankAccountDAO(); //初始化余额查询类
			Collection c_Result = null;//初始化余额结果集	
			
			BranchInfo branchInfo= null;
			FilialeAccountInfo filialeAccountInfo = null;
			//符合条件的开户行账户
			Collection c_branchinfo = findAllBranch (officeID,lBankType,officeID);
			log.print("BranchInfo符合条件账户数为："+c_branchinfo.size());
			for(Iterator it = c_branchinfo.iterator();it.hasNext();)
			{
				branchInfo=(BranchInfo)it.next();
				c_Result=dao.findHistoryBalance(branchInfo.getBankAccountCode(),branchInfo.getBankTypeID(),dtStartDate,dtEndDate);
				log.print(branchInfo.getBankAccountCode()+"余额结果集大小为： "+c_Result.size());
				
				str_BalanceOfBranch = exportBankTransDataBiz.creatString(c_Result,1);	
				str_Result.append(str_BalanceOfBranch);
			}
			
			//符合条件的下属单位账户
			Collection c_filialeAccountInfo = findAllFiliale(lBankType,officeID);
			log.print("FilialeAccountInfo符合条件账户数为："+c_filialeAccountInfo.size());	
			for(Iterator it=c_filialeAccountInfo.iterator();it.hasNext();)
			{
				filialeAccountInfo = (FilialeAccountInfo)it.next();
				c_Result=dao.findHistoryBalance(filialeAccountInfo.getBankAccountNo(),filialeAccountInfo.getBankType(),dtStartDate,dtEndDate);
				log.print(filialeAccountInfo.getBankAccountNo()+"余额结果集大小为： "+c_Result.size());
				
				str_BalanceOfFiliale = exportBankTransDataBiz.creatString(c_Result,1);		
				str_Result.append(str_BalanceOfFiliale);
			}
			if(str_Result.length()==0)
			{
				str = null;
			}
			else
			{
				str = str_Result.substring(0,str_Result.length()-2);
			}	
		}
		else if(strAction.equals("exportTransaction"))
		{
			String str_TransactionOfBranch = null;
			String Str_TransactionOfFiliale = null;
			
			Sett_HisTransInfoOfBankAccountDAO dao = new Sett_HisTransInfoOfBankAccountDAO();//初始化交易查询类
			QueryBankAccountTransConditionInfo condition=new QueryBankAccountTransConditionInfo();//初始化交易查询条件类
			Collection c_Result = null;//初始化交易结果集
			condition.setOfficeID(officeID);
			condition.setCurrencyID(currencyID);
			condition.setStartDate(starDate);
			condition.setEndDate(endDate);
			condition.setIsDeleteByBank(Constant.FALSE);
			
			BranchInfo branchInfo = null;
			FilialeAccountInfo filialeAccountInfo = null;
				
            //符合条件的开户行账户
			Collection c_branchinfo = findAllBranch (officeID,lBankType,officeID);;
			log.print("BranchInfo符合条件账户数为："+c_branchinfo.size());
			
			for(Iterator it = c_branchinfo.iterator();it.hasNext();)
			{
				branchInfo=(BranchInfo)it.next();
				
				String[] accountNo=new String[1];
				accountNo[0]=branchInfo.getBankAccountCode();
				condition.setBankAccountNos(accountNo);
				condition.setBankType(branchInfo.getBankTypeID());
				
				c_Result=exportBankTransDataBiz.findByCondition(condition);
				log.print(branchInfo.getBankAccountCode()+"交易结果集大小为： "+c_Result.size());
				str_TransactionOfBranch = exportBankTransDataBiz.creatString(c_Result,2);
				str_Result.append(str_TransactionOfBranch);
			}
			
			//符合条件的下属单位账户
			Collection c_filialeAccountInfo = findAllFiliale(lBankType,officeID);
			log.print("FilialeAccountInfo符合条件账户数为："+c_filialeAccountInfo.size());	
			for(Iterator it=c_filialeAccountInfo.iterator();it.hasNext();)
			{
				filialeAccountInfo = (FilialeAccountInfo)it.next();
				
				String[] accountNo=new String[1];
				accountNo[0]=filialeAccountInfo.getBankAccountNo();
				condition.setBankAccountNos(accountNo);
				condition.setBankType(filialeAccountInfo.getBankType());
			
				c_Result=exportBankTransDataBiz.findByCondition(condition);
				log.print(filialeAccountInfo.getBankAccountNo()+"交易结果集大小为： "+c_Result.size());
				Str_TransactionOfFiliale = exportBankTransDataBiz.creatString(c_Result,2);
				str_Result.append(Str_TransactionOfFiliale);
			}
			if(str_Result.length()==0)
			{
				str = null;
			}
			else
			{
				str = str_Result.substring(0,str_Result.length()-2);
			}	
		}*/
		return str;		
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param docName
	 * @param docURI
	 * @throws ServletException
	 */
	public void downLoad(HttpServletRequest request,HttpServletResponse response,String docName,String docURI) 
				throws ServletException 
	{
		docName = DataFormat.toChinese(docName);
		if (docName.lastIndexOf(".") >= 0) 
		{
			docName = docName.substring(0, docName.lastIndexOf("."));// 去掉扩展名
		}
		int len = docName.length();
		String fileName = docName.substring(0, len);
		if (docURI.lastIndexOf(".") >= 0) 
		{
			fileName = fileName + docURI.substring(docURI.lastIndexOf("."));
		}

		ServletContext sc = getServletContext();
		String mimeType = sc.getMimeType(fileName);

		if (mimeType == null) 
		{
			mimeType = "application/any";
		}

		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ toUtf8String(fileName) + "\"");

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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			log.error("文件下载失败");
		}	
	}
	
	/**
	 * 中文编码处理
	 * @param s
	 * @return
	 */
	protected String toUtf8String(String s) 
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) 
		{
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
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}

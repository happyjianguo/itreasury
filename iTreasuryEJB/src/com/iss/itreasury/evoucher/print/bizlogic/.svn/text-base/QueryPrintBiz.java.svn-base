package com.iss.itreasury.evoucher.print.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.print.dao.QueryPrintDao;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class QueryPrintBiz extends VoucherBaseBean implements java.io.Serializable{
     long userID = -1;
	 public PageLoader QueryPrintTransInfo (QueryPrintConditionInfo info) throws VoucherException 
	 {
		 PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try 
		 {
			 pageLoader = dao.QueryPrintTransInfo(info);
		 } 
		 catch (Exception e) 
		 {
				e.printStackTrace();
				throw new VoucherException("Gen_E001", e);
		 }
		 
		 return pageLoader; 
	 }
	 
	public PageLoader QueryForEndorseTransInfo (QueryPrintConditionInfo info) throws VoucherException 
    {
		 PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try {
			 pageLoader = dao.QueryForEndorseTransInfo(info);
		 } catch (Exception e) {
				e.printStackTrace();
				throw new VoucherException("Gen_E001", e);
			}
		 return pageLoader;
    }
	 
	 public Collection getPrintOptionByTransID (String TransIDs , long lDeptID, long lCurrencyID , long lOfficeID) throws VoucherException
	 {
		 Collection coll = null;
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try {
			 coll = dao.getPrintOptionByTransID(TransIDs,lDeptID,lCurrencyID,lOfficeID);
		 } catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		 }
		 return coll;
	 }
	 
	 //修改查找业务关联模板 boxu add 2007-9-5
	 public Collection getPrintOption(PrintXMLTimeInfo printInfo) throws VoucherException
	 {
		 Collection coll = null;
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try {
			 coll = dao.getPrintOption(printInfo);
		 } catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		 }
		 return coll;
	 }
	 
	 public Collection getPrintOptionsByTransID (String TransIDs , long lDeptID, long lCurrencyID , long lOfficeID, long lModuleID) throws VoucherException
	 {
		 Collection coll = null;
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try {
			 coll = dao.getPrintOptionsByTransID(TransIDs,lDeptID,lCurrencyID,lOfficeID,lModuleID);
		 } catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		 }
		 return coll;
	 }
	 
	 public Collection getPrintTemplateContent (String strTransNos, String transIDs , String[] templateIDs, int deptID, long lCurrencyID , long lOfficeID)throws Exception
	 {
		 Collection resultPrintOption = null;
		 QueryPrintDao dao = new QueryPrintDao();
		 
		 try {
			 resultPrintOption = dao.getPrintTemplateContent(strTransNos,transIDs,templateIDs,deptID,lCurrencyID,lOfficeID);
		 } catch(Exception e) {
			 e.printStackTrace();
				//throw new VoucherException("Gen_E001", e);
			 throw new IException(e.getMessage());
		 }
		 //String[] billName = new String[1];
		 return resultPrintOption;
	 }
	 
	 //2007-7-17 打印修改 Boxu
	 public boolean valadatePrint(PrintXMLTimeInfo printXMLInfo) throws Exception
	 {
		 boolean blreturn = false;
		 Connection conn = Database.getConnection();
		 conn.setAutoCommit(false);
		 QueryPrintDao dao = new QueryPrintDao(conn);

		 try
		 {
			 blreturn = dao.validatePrint(printXMLInfo);
			 conn.commit();
		 } 
		 catch(Exception e) 
		 {
			 conn.rollback();
			 e.printStackTrace();
			 throw new IException(e.getMessage());
		 }
		 finally{
			 conn.close();
		 }
		 return blreturn;
	 }
	 
	 //2007-8-9 打印修改 Boxu 增加一个方法明确提示信息
	 public boolean valadatePrintTWO(PrintXMLTimeInfo printXMLInfo) throws Exception
	 {
		 boolean blreturn = false;
		 Connection conn = Database.getConnection();
		 conn.setAutoCommit(false);
		 QueryPrintDao dao = new QueryPrintDao(conn);
		 
		 try
		 {
			 blreturn = dao.validatePrintTWO(printXMLInfo);
			 conn.commit();
		 } 
		 catch(Exception e) 
		 {
			 conn.rollback();
			 e.printStackTrace();
			 throw new IException(e.getMessage());
		 }
		 finally{
			 conn.close();
		 }
		 return blreturn;
	 }
	 //2010-08-02 张雷添加，不返回boolean，返回打印次数。
	 public String valadatePrintTHREE(PrintXMLTimeInfo printXMLInfo) throws Exception
	 {
		 String blreturn = "0_cant";
		 Connection conn = Database.getConnection();
		 conn.setAutoCommit(false);
		 QueryPrintDao dao = new QueryPrintDao(conn);
		 
		 try
		 {
			 blreturn = dao.validatePrintTHREE(printXMLInfo);
			 conn.commit();
		 } 
		 catch(Exception e) 
		 {
			 conn.rollback();
			 e.printStackTrace();
			 throw new IException(e.getMessage());
		 }
		 finally{
			 conn.close();
		 }
		 return blreturn;
	 }
	 public Collection getPrintTemplateContentmany (String strTransNos, String transIDs , int deptID, long lCurrencyID , long lOfficeID, long lModuleID,long userID)throws Exception{
		 this.userID = userID;
		 return getPrintTemplateContentmany(strTransNos,transIDs ,deptID,lCurrencyID ,lOfficeID,lModuleID);
	 }
	 //addby zyyao 批量打印 2007-6-28
	 public Collection getPrintTemplateContentmany (String strTransNos, String transIDs , int deptID, long lCurrencyID , long lOfficeID, long lModuleID)throws Exception
	 {
		 Collection resultPrintOption = null;
		 Connection conn = Database.getConnection();
		 conn.setAutoCommit(false);
		 QueryPrintDao dao = new QueryPrintDao(conn);
		
		 try 
		 {
			 resultPrintOption = dao.getPrintTemplateContentmany(strTransNos,transIDs,deptID,lCurrencyID,lOfficeID,lModuleID,this.userID);
			 conn.commit();
		 } 
		 catch(Exception e) 
		 {
			 conn.rollback();
			 e.printStackTrace();
			 throw new IException(e.getMessage());
		 }
		 finally{
			 conn.close();
		 }
		 return resultPrintOption;
	 }
	 /**
	  * 计算打印次数集合
	  * @param printXMLInfo
	  * @return
	  * @throws Exception
	  */
	 public HashMap getPrintCountMap(PrintXMLTimeInfo printXMLInfo) throws Exception
	 {
		 HashMap map = new HashMap();
		 QueryPrintDao dao = new QueryPrintDao();
		 String billName[] = printXMLInfo.getBillName();
		 for(int i=0 ; i<billName.length ; i++)
 		 {
			 long lPrintTime = dao.getPrintXMLTime(printXMLInfo, billName[i]);
			 map.put(billName[i], lPrintTime+"");
 		 }
		 return map;
	 }
}

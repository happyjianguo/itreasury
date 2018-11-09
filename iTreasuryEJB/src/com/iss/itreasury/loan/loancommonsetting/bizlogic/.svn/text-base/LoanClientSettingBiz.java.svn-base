/**
 * 
 */
package com.iss.itreasury.loan.loancommonsetting.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.loancommonsetting.dao.LoanClientSettingDao;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientOtherShareInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.QueryClientInfo;
import com.iss.itreasury.util.IException;

//import com.iss.itreasury.util.Log4j;

/**
 * @author weihuang
 *
 */
public class LoanClientSettingBiz {
	//private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	private LoanClientSettingDao lcs=null;
	public LoanClientSettingBiz(){
		LoanClientSettingDao cs=new LoanClientSettingDao();
		lcs=cs;;
	}
	public ClientInfo findClientByID(long lClientID) 
	throws Exception
	{
		ClientInfo info=new ClientInfo();
		try{
		  // LoanClientSettingDao lcs=new LoanClientSettingDao();
		   info=lcs.findClientByID(lClientID);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	    return info;
	}
	public Collection findOtherShareByClientID(long lClientID) 
	throws Exception
	{Vector v=new Vector();
	 try{
		// LoanClientSettingDao lcs=new LoanClientSettingDao();
		 v=(Vector)lcs.findOtherShareByClientID(lClientID);
		     
	 }catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		
		return v;
	}
	public long saveClientInfo(ClientInfo clientinfo) 
	throws Exception
	{
	  long lResult = -1;	
	 try {
		// LoanClientSettingDao lcs=new LoanClientSettingDao();
		 lResult=lcs.saveClientInfo(clientinfo);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return lResult;
	}
	public long saveOtherShareInfo(ClientOtherShareInfo info) 
	throws IException,Exception
	{  long lResult=-1;
		try {
			lResult=lcs.saveOtherShareInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lResult;
	}
	public long checkName(String name,long clientid) {
		long result=-1;
		try {
			result=lcs.checkName(name, clientid);
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		return result;
	}
	public long checkCode(String code,long clientid) {
		long result=-1;
		try {
			result=lcs.checkCode(code, clientid);
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		return result;
	}
	public Collection findByMultiOption(QueryClientInfo qInfo){
		ArrayList v=new ArrayList();
		try {
			LoanClientSettingDao dao=new LoanClientSettingDao();
			v=(ArrayList)dao.findByMultiOption(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}return v;
	}
	public String findName(String tablename,long id) {
		String name="";
		try {
			name=lcs.findName(tablename,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	public String findIndustryTypeName(long id) {
		String name="";
		try {
			name=lcs.findIndustryTypeName(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	public String findExtendAttributeName(long id,long ExtendAttribute) {
		String name="";
		try {
			name=lcs.findExtendAttributeName(id,ExtendAttribute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
}

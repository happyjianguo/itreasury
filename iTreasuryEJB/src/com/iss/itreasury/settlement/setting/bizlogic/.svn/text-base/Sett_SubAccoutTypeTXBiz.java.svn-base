package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.bizdelegation.AccountTypeSettingDelegation;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountTypeSettingQueryDAO;
import com.iss.itreasury.settlement.setting.dao.SubAccountTypeSettingDao;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sett_SubAccoutTypeTXBiz {
	
	SubAccountTypeSettingDao dao =new SubAccountTypeSettingDao();
	
	public PagerInfo queryBranchInfo(long lAccountGroupID,long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.findAllAccountTypeByGroupID(lAccountGroupID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			map.put("accountgroupid", lAccountGroupID);
			map.put("officeid", lOfficeID);
			map.put("currencyid", lCurrencyID);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccoutTypeTXBiz.class, "resultSetHandle",map);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		
		return pagerInfo;
		
	}
	
	  public ArrayList resultSetHandle(ResultSet rs,Map map) throws Exception
	  {
	    ArrayList resultList = new ArrayList();
	    ArrayList cellList = null;
	    ResultPagerRowInfo rowInfo = null;
	    AccountTypeSettingDelegation delegation = new AccountTypeSettingDelegation();
	    
	    long lAccountGroup= (Long) map.get("accountgroupid");
	    long lOfficeID= (Long) map.get("officeid");
	    long lCurrencyID= (Long) map.get("currencyid");
	  
	    String AccountTypeCode = "";
	    String AccountType = "";
	    long IsExistSubClass = -1;
	    long IsClient = -1;
	    long isAccount = -1;
	    long isDeposit = -1;
	    long accountModule = -1;// 账户模式
	    long payModule = -1;// 付款方式  
	    String SubjectCode = "";//科目号
	    long Id = -1;
	    

	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
	        	
	          Id=rs.getLong("ID");
	          AccountTypeCode = rs.getString("sAccountTypeCode");
	          AccountType = rs.getString("sAccountType");
	          IsExistSubClass= rs.getLong("nIsExistSubClass");
	          IsClient=rs.getLong("nIsClient");
	          isAccount=rs.getLong("nIsAccount");
	          isDeposit=rs.getLong("nIsDeposit");
	          accountModule=rs.getLong("AccountModule");
	          payModule=rs.getLong("PayModule");
	          SubjectCode=rs.getString("sSubjectCode");
	          
	           AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
				
				accountTypeInfo.setId(rs.getLong("ID"));
				accountTypeInfo.setAccountTypeCode(rs.getString("sAccountTypeCode"));
				accountTypeInfo.setAccountType( rs.getString("sAccountType"));
				accountTypeInfo.setIsExistSubClass( rs.getLong("nIsExistSubClass"));
				accountTypeInfo.setIsLoanType(rs.getLong("nIsLoanType"));
				accountTypeInfo.setIsLoanMonth( rs.getLong("nIsLoanMonth"));
				accountTypeInfo.setIsLoanYear(rs.getLong("nIsLoanYear"));
				accountTypeInfo.setIsConsign( rs.getLong("nIsConsign"));
				accountTypeInfo.setIsDraftType(rs.getLong("nIsDraftType"));
				accountTypeInfo.setDefaultDocCode(rs.getString("sDefaultDocCode"));
				accountTypeInfo.setAutoClearAccount( rs.getLong("nAutoClearAccount"));
				accountTypeInfo.setAccountGroupId(rs.getLong("nAccountGroupID"));
				accountTypeInfo.setIsClient( rs.getLong("nIsClient"));
				accountTypeInfo.setIsAccount(rs.getLong("nIsAccount"));
				accountTypeInfo.setIsDeposit(rs.getLong("nIsDeposit"));
				accountTypeInfo.setIsContract(rs.getLong("nIsContract"));
				accountTypeInfo.setSubjectCode(rs.getString("sSubjectCode"));
				accountTypeInfo.setInterestSubjectCode( rs.getString("sInterestSubjectCode"));
				accountTypeInfo.setNegotiateInterestSubjectCode(rs.getString("sNegotiateInterestSubjectCode"));
				accountTypeInfo.setBookedInterestSubjectCode(rs.getString("sBookedInterestSubjectCode"));
				accountTypeInfo.setAccountModule(rs.getLong("AccountModule"));
				accountTypeInfo.setInterestCalculationMode(rs.getLong("interestCalculationMode"));
				accountTypeInfo.setpayModule( rs.getLong("PayModule")); 
	          
	          //处理数据
	          
	          //第一列
				String strAccountTypeCode = accountTypeInfo.getAccountTypeCode();
				if(strAccountTypeCode.length()<2)
				{
				     strAccountTypeCode = "0"+strAccountTypeCode;
				}
				String strSubject = delegation.getSubjectCodeByAccountTypeID(lCurrencyID,lOfficeID,accountTypeInfo.getId());
				
				//第二列 
				String accountType2 = accountTypeInfo.getAccountType();
				//第三列 
				
				String isExistSubClass="";
				String url2="";
				 if(accountTypeInfo.getIsExistSubClass() == 1)
				 {
					 isExistSubClass="是";
				 
				 }
				 else
				 {
					 isExistSubClass="否";
				 }
				 
				 //第四列 
				 String accountLabelByID = delegation.getAccountLabelByAccountTypeID(accountTypeInfo);
				 
				 //第五列 
				 
				 String DefaultDocCode= accountTypeInfo.getDefaultDocCode();
				 
				 //第六列 
				 String AutoClearAccount="";
				 if(accountTypeInfo.getAutoClearAccount()==1)
				 {
					 AutoClearAccount="是";
				 }
					else
					{
					 AutoClearAccount="否";
					}
				
				 //第七列 账户组
				 
				 String AccountGroup =SETTConstant.AccountGroupType.getName(accountTypeInfo.getAccountGroupId());
				 
				 //第八列 对应科目号
				String subjectString= accountTypeInfo.getSubjectCode();
				
				//第九列 计息方式
				String InterestCalculation="";
				if(accountTypeInfo.getAccountGroupId() == SETTConstant.AccountGroupType.DISCOUNT)
				{
					if(accountTypeInfo.getInterestCalculationMode() == -1){
						InterestCalculation="未设置";
					}
					else {
						InterestCalculation=SETTConstant.InterestCalculationMode.getName(accountTypeInfo.getInterestCalculationMode());
					}
				}
				
				

	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, strAccountTypeCode + "," +accountTypeInfo.getId());
	          PagerTools.returnCellList(cellList, accountType2);
	          
	          if(accountTypeInfo.getIsExistSubClass() == 1)
				 {
					 PagerTools.returnCellList(cellList, isExistSubClass + ","+accountTypeInfo.getId()+","+accountTypeInfo.getIsExistSubClass());
				 
				 }
				 else
				 {
					 PagerTools.returnCellList(cellList, isExistSubClass);
				 }
	          
	          PagerTools.returnCellList(cellList, accountLabelByID);
	          PagerTools.returnCellList(cellList, DefaultDocCode);
	          PagerTools.returnCellList(cellList, AutoClearAccount);
	          PagerTools.returnCellList(cellList, AccountGroup);
	          PagerTools.returnCellList(cellList, strSubject);
	          if(accountTypeInfo.getAccountGroupId() == SETTConstant.AccountGroupType.DISCOUNT)
				{
	        	  	PagerTools.returnCellList(cellList, InterestCalculation);
				}

	          rowInfo = new ResultPagerRowInfo();
	          rowInfo.setCell(cellList);
	          rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

	          resultList.add(rowInfo);
	        }
	      }
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
	    }

	    return resultList;
	  }

}

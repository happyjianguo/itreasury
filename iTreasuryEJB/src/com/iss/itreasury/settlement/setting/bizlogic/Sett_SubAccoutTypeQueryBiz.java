package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.bizdelegation.AccountTypeSettingDelegation;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountTypeSettingQueryDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sett_SubAccoutTypeQueryBiz {
	
	Sett_AccountTypeSettingQueryDAO dao =new Sett_AccountTypeSettingQueryDAO();
	
	public PagerInfo queryBranchInfo(long lAccountGroupID, long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryAccountTypeSql(lAccountGroupID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			map.put("accountgroupid", lAccountGroupID);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccoutTypeQueryBiz.class, "resultSetHandle",map);
			
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
	    
	    long lAccountGroup= (Long) map.get("accountgroupid");
	    boolean blnIsIntegration = com.iss.itreasury.util.Config.getBoolean(com.iss.itreasury.util.Config.INTEGRATION_SERVICE_ISVALID, false); 
	    boolean blnIsCurrent = SETTConstant.AccountGroupType.CURRENT == lAccountGroup ? true:false;

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
	          
	          //第一列 账户类型编码
	          String strAccountFormNo = accountTypeInfo.getAccountTypeCode();
				if(strAccountFormNo.length()<2)
				{
				     strAccountFormNo = "0"+strAccountFormNo;
				}
				else
				{
				     strAccountFormNo = strAccountFormNo.substring(0,2);
				}
				String url1="/NASApp/iTreasury-settlement/settlement/set/S381-2.jsp?control=view&nID="+accountTypeInfo.getId()+"&lAccountGroup="+accountTypeInfo.getAccountGroupId();
				//第二列 账户类型
				String accountType2 = accountTypeInfo.getAccountType();
				//第三列 下级分类
				String isExistSubClass="";
				String url2="";
				 if(accountTypeInfo.getIsExistSubClass() == 1)
				 {
					 isExistSubClass="是";
				 
				 //<A href="/NASApp/iTreasury-settlement/settlement/set/control/c305.jsp?lAccountTypeID=<%=accountTypeInfo.getId()%>&lAccountGroupID=<%=lAccountGroup%>&strSuccessPageURL=../view/v305.jsp&strFailPageURL=../S380.jsp">是</A>
					 url2="/NASApp/iTreasury-settlement/settlement/set/control/c305.jsp?lAccountTypeID="+accountTypeInfo.getId()+"&lAccountGroupID="+lAccountGroup+"&strSuccessPageURL=../view/v305.jsp&strFailPageURL=../S380.jsp";
				 
				 }
				 else
				 {
					 isExistSubClass="否";
				 }
				 
				 //第四列 分类
				 AccountTypeSettingDelegation delegation = new AccountTypeSettingDelegation();
				 String accountLabelByID = delegation.getAccountLabelByID(accountTypeInfo);
				 //第五列 账户模式
				 String AccountModule = SETTConstant.AccountModule.getName(accountTypeInfo.getAccountModule());
				 //第四列 付款方式
				 String payModule1 = SETTConstant.PayModule.getName(accountTypeInfo.getpayModule());
				 //第四列 账户组
				 String AccountGroup = SETTConstant.AccountGroupType.getName(lAccountGroup);
				 //第四列 对应科目号
				String subjectString= accountTypeInfo.getSubjectCode() != null ? accountTypeInfo.getSubjectCode():"";

	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, strAccountFormNo + "," +accountTypeInfo.getId()+","+accountTypeInfo.getAccountGroupId());
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
	          if(blnIsIntegration && blnIsCurrent)
	          {
	        	  PagerTools.returnCellList(cellList, AccountModule);
	        	  PagerTools.returnCellList(cellList, payModule1 );
	          }
	          PagerTools.returnCellList(cellList, AccountGroup);
	          PagerTools.returnCellList(cellList, subjectString);

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

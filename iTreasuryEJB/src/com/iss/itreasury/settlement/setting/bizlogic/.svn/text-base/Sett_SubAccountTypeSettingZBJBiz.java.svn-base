package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.bizdelegation.AccountTypeSettingDelegation;
import com.iss.itreasury.settlement.setting.dao.SubAccountTypeSettingDao;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeCurrentSettingInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
/**
 * 子账户查询
 * @author songwenxiao
 *
 */
public class Sett_SubAccountTypeSettingZBJBiz {
	
	SubAccountTypeSettingDao dao =new SubAccountTypeSettingDao();
	
	public PagerInfo querySubAccount(long lAccountGroupID, long lAccountTypeID, long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.querySubAccountSQL(lAccountTypeID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			map.put("accounttypeid", lAccountTypeID);
			map.put("accountgroupid", lAccountGroupID);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccountTypeSettingZBJBiz.class, "resultSetHandle",map);
			
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
	    
	    long lAccountTypeID= (Long) map.get("accounttypeid");
	    long lAccountGroupID= (Long) map.get("accountgroupid");
	    
	    AccountTypeSettingDelegation delegation = new AccountTypeSettingDelegation();
		AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
		
		accountTypeInfo = delegation.findAccountTypeById(lAccountTypeID);

	   
	    long Id = -1;
	    long ClientID = -1;
	    long accountId = -1;
	    String sDepositNO = "";
	    String SubjectCode = "";
	    String PayInterestSubject = "";
	    String CommissionSubject = "";
	    String NegotiateInterestSubject ="";
	    String BookedInterestSubject = "";
	    

	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
	        	
	          Id=rs.getLong("ID");
	          ClientID=rs.getLong("nClientID");
	          accountId=rs.getLong("naccountId");
	          sDepositNO = rs.getString("sDepositNO");
	          SubjectCode = rs.getString("sSubjectCode");
	          PayInterestSubject=rs.getString("sPayInterestSubject");
	          CommissionSubject=rs.getString("sCommissionSubject");
	          NegotiateInterestSubject=rs.getString("sNegotiateInterestSubject");
	          BookedInterestSubject=rs.getString("sBookedInterestSubject");
	          
	          SubAccountTypeCurrentSettingInfo subCurrentInfo =new SubAccountTypeCurrentSettingInfo();
	          
	          //处理数据
	          
	          //第一列 账户类型编码
				String strAccountCode = "";
				strAccountCode = accountTypeInfo.getAccountTypeCode();
				if(strAccountCode.length() < 2)
				{
					strAccountCode = "0" + strAccountCode;
				}
				
				//第二列 客户编号
				String clientCodeByID = NameRef.getClientCodeByID(ClientID);
				//第三列 账户编号
				String accountNoByID = NameRef.getAccountNoByID(accountId);
				//第四列 存单号
				//第五列 账户组类型
				String AccountGroup = SETTConstant.AccountGroupType.getName(lAccountGroupID);
				 //第六列 对应科目号
				 //第七列 利息支出科目号
				 //第八列 手续费收入科目号   协定利息支出科目号
				//第九列 计提利息科目号
				
	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, strAccountCode + "," +Id+","+accountTypeInfo.getId());
	          if (accountTypeInfo.getIsAccount() > 0) {
	        	  PagerTools.returnCellList(cellList, accountNoByID);
	          }
	          PagerTools.returnCellList(cellList, AccountGroup);
	          PagerTools.returnCellList(cellList, SubjectCode);
	          PagerTools.returnCellList(cellList, PayInterestSubject);
	          PagerTools.returnCellList(cellList, BookedInterestSubject);
	          
	         

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

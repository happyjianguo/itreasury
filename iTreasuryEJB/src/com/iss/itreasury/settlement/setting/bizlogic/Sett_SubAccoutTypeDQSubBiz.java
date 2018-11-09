package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.setting.dao.SubAccountTypeSettingDao;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sett_SubAccoutTypeDQSubBiz {
	
	SubAccountTypeSettingDao dao =new SubAccountTypeSettingDao();
	
	public PagerInfo queryBranchInfo(long lAccountTypeID, long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.findAllSubAccountTypeFixedDepositByAccountType(lAccountTypeID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			 long lIsDisplayClient = -1;
			 SubAccountTypeSettingBiz ejb = new SubAccountTypeSettingBiz();
		    AccountTypeSettingInfo  ai = ejb.findAccountTypeByID(lAccountTypeID,lOfficeID,lCurrencyID);
			if( ai != null)
			{
				lIsDisplayClient=ai.getIsClient();
			}
			Map map=new HashedMap();
			map.put("accounttypeinfo", ai);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccoutTypeDQSubBiz.class, "resultSetHandle",map);
			
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
	    
	    AccountTypeSettingInfo  ai= (AccountTypeSettingInfo) map.get("accounttypeinfo");
	    long lIsDisplayClient = -1;
	    if( ai != null)
		{
			lIsDisplayClient=ai.getIsClient();
		}
	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
				
	            SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
				subAccountTypeFixedDepositInfo.m_lID = rs.getLong(1);
				subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID = rs.getLong(2);
				subAccountTypeFixedDepositInfo.m_lAccountTypeID = rs.getLong(3);
				subAccountTypeFixedDepositInfo.m_strSubjectCode = rs.getString(4);
	            subAccountTypeFixedDepositInfo.m_strPayInterestSubjectCode = rs.getString(5);
	            subAccountTypeFixedDepositInfo.m_strBookedInterestSubjectCode = rs.getString(6);
				subAccountTypeFixedDepositInfo.m_lClientID = rs.getLong(7);
				subAccountTypeFixedDepositInfo.setAccountId(rs.getLong(8));
				subAccountTypeFixedDepositInfo.setDepositNo(rs.getString(9));
				subAccountTypeFixedDepositInfo.m_strAccountTypeName = rs.getString(10);
	          
	          //处理数据
	          
	          //第一列
				 long lDepositTerm=-1;
				  lDepositTerm=subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID;
				  String allottedTime="";
				  
				  if (subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID >= 10000){
				  		
					  allottedTime=(subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID-10000) + "天";
					}	
					else{
					    
						allottedTime=subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID + "个月";
						}
	        
				//第二列 
				  String clientname="";
				  if(subAccountTypeFixedDepositInfo.m_lClientID>0)
					{
					  
					  clientname= NameRef.getClientNameByID(subAccountTypeFixedDepositInfo.m_lClientID);
					}
				//第三列 
				  String accountNo="";
				  if(subAccountTypeFixedDepositInfo.getAccountId()>0) 
				  {
					  
					 accountNo= NameRef.getAccountNoByID(subAccountTypeFixedDepositInfo.getAccountId());
				  }
				  
				//第四列 
				  String depositno="";
				  if(subAccountTypeFixedDepositInfo.getDepositNo()!=null) {
					  
					  depositno=  subAccountTypeFixedDepositInfo.getDepositNo();
				  }
				 
				 //第五列 
				  String accounttypename="";
				  if(subAccountTypeFixedDepositInfo.m_strAccountTypeName!=null)
				  {
					  accounttypename= subAccountTypeFixedDepositInfo.m_strAccountTypeName; 
					  
				  }
				//第六列 
				String SubjectCode= subAccountTypeFixedDepositInfo.m_strSubjectCode != null ? subAccountTypeFixedDepositInfo.m_strSubjectCode:"";
			    //第七列 
				String PayInterestSubjectCode= subAccountTypeFixedDepositInfo.m_strPayInterestSubjectCode != null ? subAccountTypeFixedDepositInfo.m_strPayInterestSubjectCode:"";
				//第八列 
			   String BookedInterestSubjectCode=subAccountTypeFixedDepositInfo.m_strBookedInterestSubjectCode != null ? subAccountTypeFixedDepositInfo.m_strBookedInterestSubjectCode:"";
				 
			

	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, allottedTime + ","+subAccountTypeFixedDepositInfo.m_lID+","+lDepositTerm);
	          if(lIsDisplayClient > 0){
				
	        	  PagerTools.returnCellList(cellList, clientname);
	          }
	          if (ai.getIsAccount()> 0) 
	          {
	        	  PagerTools.returnCellList(cellList, accountNo);
	          }
	          
	          if (ai.getIsDeposit()> 0) 
	          {
	        	  PagerTools.returnCellList(cellList, depositno);
	          }
	          
	          PagerTools.returnCellList(cellList, accounttypename);
	          PagerTools.returnCellList(cellList, SubjectCode);
	          PagerTools.returnCellList(cellList, PayInterestSubjectCode);
	          PagerTools.returnCellList(cellList, BookedInterestSubjectCode);
	         
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

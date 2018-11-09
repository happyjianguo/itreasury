package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.bizdelegation.AccountTypeSettingDelegation;
import com.iss.itreasury.settlement.setting.dao.SubAccountTypeSettingDao;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeLoanSettingInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sett_SubAccoutTypeLoanSubBiz {
	
	SubAccountTypeSettingDao dao =new SubAccountTypeSettingDao();
	
	public PagerInfo queryBranchInfo(long lAccountGroupID,long lAccountTypeID, long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.findByAccountTypeID(lAccountTypeID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			AccountTypeSettingDelegation delegation = new AccountTypeSettingDelegation();
			AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
			accountTypeInfo = delegation.findAccountTypeById(lAccountTypeID);
			
			Map map=new HashedMap();
			map.put("accounttypeinfo", accountTypeInfo);
			map.put("accountgroupid", lAccountGroupID);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccoutTypeLoanSubBiz.class, "resultSetHandle",map);
			
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
	    
	    AccountTypeSettingInfo  accountTypeInfo= (AccountTypeSettingInfo) map.get("accounttypeinfo");
	    long lAccountGroupID=  (Long) map.get("accountgroupid");
	    
	  
	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
				
	        	SubAccountTypeLoanSettingInfo subLoanInfo=new SubAccountTypeLoanSettingInfo();
	        	subLoanInfo.setId(rs.getLong("ID"));
	        	subLoanInfo.setSerialNo(rs.getLong("nSerialNo"));
	        	subLoanInfo.setAccountTypeId(rs.getLong("NACCOUNTTYPEID"));
	        	subLoanInfo.setLoanTypeId(rs.getLong("NLOANTYPEID"));
	        	subLoanInfo.setLoanMonthId(rs.getLong("NLOANMONTHID"));
	        	subLoanInfo.setLoanYearId(rs.getLong("NLOANYEARID"));
	        	subLoanInfo.setConsignerClientId(rs.getLong("NCONSIGNERCLIENTID"));
	        	subLoanInfo.setDraftTypeId(rs.getLong("NDRAFTTYPEID"));
	        	subLoanInfo.setSubjectCode(rs.getString("SSUBJECTCODE"));
	        	subLoanInfo.setStatusId(rs.getLong("NSTATUSID"));
	        	subLoanInfo.setLoanMonthStart(rs.getLong("NLOANMONTHSTART"));
	        	subLoanInfo.setLoanMonthEnd(rs.getLong("NLOANMONTHEND"));
	        	subLoanInfo.setInterestSubject(rs.getString("SINTERESTSUBJECT"));
	        	subLoanInfo.setBookedInterestSubject(rs.getString("SBOOKEDINTERESTSUBJECT"));
	        	subLoanInfo.setInterestTaxSubject(rs.getString("SINTERESTTAXSUBJECT"));
	        	subLoanInfo.setClientId(rs.getLong("NCLIENTID"));
	        	subLoanInfo.setPayInterestSubject(rs.getString("SPAYINTERESTSUBJECT"));
	        	subLoanInfo.setTransDiscountTypeId(rs.getLong("NTRANSDISCOUNTTYPEID"));
	        	subLoanInfo.setAssureTypeID(rs.getLong("NASSURETYPEID"));
	        	subLoanInfo.setCommissionSubject(rs.getString("SCOMMISSIONSUBJECT"));
	        	subLoanInfo.setContractId(rs.getLong("NCONTRACTID"));
	        	subLoanInfo.setLoanNoteId(rs.getLong("NLOANNOTEID"));
	        	
	          
	          //处理数据
	          
	          //第一列
	       String groupName="贷款";

	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, subLoanInfo.getSerialNo() + ","+subLoanInfo.getId());
	          
	          if(accountTypeInfo.getIsLoanType() > 0){
				
	        	  PagerTools.returnCellList(cellList, SETTConstant.SettLoanType.getName(subLoanInfo.getLoanTypeId()));
	          }
	         
	          if (accountTypeInfo.getIsLoanMonth() > 0) 
	          {
	        	  PagerTools.returnCellList(cellList, subLoanInfo.getLoanMonthStart());
	        	  PagerTools.returnCellList(cellList, subLoanInfo.getLoanMonthEnd());
	          }
	          
	          if (accountTypeInfo.getIsLoanYear() > 0) 
	          {
	        	  PagerTools.returnCellList(cellList, subLoanInfo.getLoanYearId());
	          }
	          if(accountTypeInfo.getIsConsign() > 0)
				{
	        	  PagerTools.returnCellList(cellList, NameRef.getClientCodeByID(subLoanInfo.getConsignerClientId()));
	        	  
				}
	          if(accountTypeInfo.getIsDraftType() > 0)
				{
	        	  PagerTools.returnCellList(cellList,LOANConstant.DraftType.getName(subLoanInfo.getDraftTypeId()));
	        	  
				}
	          if(accountTypeInfo.getIsClient() > 0)
				{
	        	  PagerTools.returnCellList(cellList, NameRef.getClientCodeByID(subLoanInfo.getClientId()));
	        	  
				}
	          if(accountTypeInfo.getIsTransDiscountType() > 0)
				{
	        	  PagerTools.returnCellList(cellList, SETTConstant.TransDiscountType.getName(subLoanInfo.getTransDiscountTypeId()));
	        	  
				}
	          if(accountTypeInfo.getIsAssure() > 0)
				{
	        	  PagerTools.returnCellList(cellList, LOANConstant.AssureType.getName(subLoanInfo.getAssureTypeID()));
	        	  
				}
	          if(accountTypeInfo.getIsContract() > 0)
				{
	        	  PagerTools.returnCellList(cellList, NameRef.getContractNoByID(subLoanInfo.getContractId()));
				}
	          if(accountTypeInfo.getIsLoanNote() > 0)
				{
	        	  
	        	  PagerTools.returnCellList(cellList, NameRef.getPayFormNoByID(subLoanInfo.getLoanNoteId()));
				}
	          
	          PagerTools.returnCellList(cellList,groupName);
	          PagerTools.returnCellList(cellList,subLoanInfo.getSubjectCode());
	          PagerTools.returnCellList(cellList,subLoanInfo.getInterestSubject());
	          PagerTools.returnCellList(cellList,subLoanInfo.getPayInterestSubject());
	          PagerTools.returnCellList(cellList,subLoanInfo.getBookedInterestSubject());
	          PagerTools.returnCellList(cellList,subLoanInfo.getInterestTaxSubject());
	          PagerTools.returnCellList(cellList,subLoanInfo.getCommissionSubject());
	         
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

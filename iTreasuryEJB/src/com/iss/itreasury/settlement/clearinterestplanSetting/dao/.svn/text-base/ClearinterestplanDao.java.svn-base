package com.iss.itreasury.settlement.clearinterestplanSetting.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.clearinterestplanSetting.dataentity.ClearinterestplanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

public class ClearinterestplanDao extends SettlementDAO{

	
	public ClearinterestplanDao()
	{
		super("Sett_ClearInterestPlan");
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	
	
	
	 /*
	  * 对比，是否有重复记录（根据贷款子类型）
	  * 
	  * 
	  * */
	
	public boolean findClearinterestplanInfoBySubLoanType(ClearinterestplanInfo cinfo) throws Exception
	{
		
		boolean isBool = true;
		long overDueType = -1;
		StringBuffer strTmpSQL =new StringBuffer();
		try
		{
			initDAO();
			//
			strTmpSQL.append("select * from Sett_ClearInterestPlan where StatusID = 1 ");
			strTmpSQL.append("and loanType =? and subLoanType =? and OfficeID = ? and CurrentID = ? ");
			strTmpSQL.append("and LoanTermTypeID = ? ");
			if(cinfo.getId() > 0)
			{
				strTmpSQL.append("and id <> ?");
			}
			
			transPS = prepareStatement(strTmpSQL.toString());
			transPS.setLong(1, cinfo.getLoanType());
			transPS.setLong(2, cinfo.getSubLoanType());
			transPS.setLong(3, cinfo.getOfficeID());
			transPS.setLong(4, cinfo.getCurrentID());
			transPS.setLong(5, cinfo.getLoanTermTypeID());
			if(cinfo.getId() > 0)
			{
				transPS.setLong(6, cinfo.getId());
			}
			
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				overDueType = transRS.getLong("overDueType");
				
				if(cinfo.getOverDueType() == overDueType)
				{
					isBool = false;
				}
			}
			
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}

		return isBool;
		
	}
	
	
	 /*
	  * 结息计划设置查询
	  * 
	  * 
	  * */
	public Collection findAllClearinterestplanInfo(long OfficeID,long CurrencyID) throws Exception{
		
		Vector v = new Vector();
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			StringBuffer strSQLBuffer = new StringBuffer();
			
			strSQLBuffer.append("select a.*, b.name subLoanTypeName, c1.sname inputUserName ");
			strSQLBuffer.append(" from Sett_ClearInterestPlan a, loan_loanTypeSetting b, userInfo c1" );
			strSQLBuffer.append(" where a.StatusID = 1 and a.OfficeID = ? and a.CurrentID =? ");
			strSQLBuffer.append(" and b.id = a.subLoanType and c1.id=a.inputUserID");
			
			String strSQL = strSQLBuffer.toString();
			System.out.println(strSQL);
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, OfficeID);
			transPS.setLong(2, CurrencyID);
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				
				ClearinterestplanInfo cinfo=new ClearinterestplanInfo();
				
				cinfo.setId(transRS.getLong("ID"));
				cinfo.setLoanType(transRS.getLong("loanType"));
				cinfo.setSubLoanType(transRS.getLong("subLoanType"));
				cinfo.setSubLoanTypeName(transRS.getString("subLoanTypeName"));
				cinfo.setLoanTermTypeID(transRS.getLong("LoanTermTypeID"));
				cinfo.setLoanTermTypeName(LOANConstant.LoanTerm.getName(cinfo.getLoanTermTypeID()));
				cinfo.setOverDueType(transRS.getLong("OverDueType"));
				cinfo.setOverDueTypeName(SETTConstant.ClearInterestPlayOverDueType.getName(cinfo.getOverDueType()));
				cinfo.setClearType(transRS.getLong("ClearType"));
				cinfo.setClearTypeName(SETTConstant.ClearInterestPlayClearType.getName(cinfo.getClearType()));
				cinfo.setClearTime(transRS.getLong("ClearTime"));
				cinfo.setCurrentID(transRS.getLong("CurrentID"));
				cinfo.setOfficeID(transRS.getLong("OfficeID"));
				//cinfo.setOfficeName(transRS.getString("OfficeName"));
				cinfo.setInputUserID(transRS.getLong("inputUserID"));
				cinfo.setInputUserName(transRS.getString("inputUserName"));
				cinfo.setInputTime(transRS.getTimestamp("inputTime"));
				//cinfo.setModifyUserID(transRS.getLong("modifyUserID"));
				//cinfo.setModifyUserName(transRS.getString("modifyUserName"));
				//cinfo.setModifyTime(transRS.getTimestamp("modifyTime"));
				
				v.addElement(cinfo);
				
				
			}

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}

		return (v.size() > 0 ? v : null);
		
	}



	public String findClearinterestplanInfo(long officeID, long currencyID) {
		StringBuffer strSQLBuffer = new StringBuffer();
		
		strSQLBuffer.append("select a.id,a.LOANTERMTYPEID,a.OVERDUETYPE,a.CLEARTYPE,a.CLEARTIME||'号' as CLEARTIME,a.INPUTTIME, b.name subLoanTypeName, c1.sname inputUserName ");
		strSQLBuffer.append(" from Sett_ClearInterestPlan a, loan_loanTypeSetting b, userInfo c1" );
		strSQLBuffer.append(" where a.StatusID = 1 and a.OfficeID = '"+officeID+"' and a.CurrentID ='"+currencyID+"' ");
		strSQLBuffer.append(" and b.id = a.subLoanType and c1.id=a.inputUserID");
		return strSQLBuffer.toString();
	} 
	
}

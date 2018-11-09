package com.iss.itreasury.settlement.transadjustinterest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfoQuery;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfoQuery;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class QueryAdjustInterestDao
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT,this);
	
	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public PageLoader adjustFindFormHistory(AccumulateFeeInfoQuery accInfo)throws Exception
	{
        String[] sql = new String[4];
        //select
        sql[0] = " b.nSubAccountID as lID,a.Id as lAccountID,a.sAccountNO as sAccountNo"
               + ",-1 as lDueBillID, '' as sLetoutRequisitionNo,-1 as lContractID,'' as sContractNo"
               + ",d.sName as sInterestRatePlanName, a.nAccountTypeID as lDepositTypeID ,b.dtDate as tsOpen"
               + ", b.MINTEREST as dInterest"
               + ",0 as dCommission"
               + ",0 as dSuretyFee"
               + ",0 as dInterestTax";
        //from
        sql[1] = " sett_Account a,SETT_DAILYACCOUNTBALANCE b,sett_subAccount c,"
        	   + " (select * from sett_interestRatePlan xx where xx.nofficeid = "
                           + accInfo.getLOfficeID()  +" and xx.ncurrencyid = "+ accInfo.getLCurrencyID()+") d ," 
        	   + " sett_accountType f";

        //where
        sql[2] = " b.nAccountID=a.id "
	            +" and b.nSubAccountID=c.id "
	            +" and c.AF_nInterestPlanID=d.id(+) "
	            +" and a.nAccountTypeID=f.id " 		//账户类型相等						//定期和活期
	            +" and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
	            +" and b.nAccountStatusID >0 "
	            +" and b.nSubAccountStatusID >0 "
	            +" and a.NOFFICEID = " + accInfo.getLOfficeID()			//办事处ID
	            +" and a.NCURRENCYID = "+ accInfo.getLCurrencyID() ; 	//币种ID
        //账户号
        if (accInfo.getSAccountNoStartCtrl() != null && accInfo.getSAccountNoStartCtrl().length() > 0)
        {
        	sql[2] = sql[2] + " and a.sAccountNo >= '" + accInfo.getSAccountNoStartCtrl() + "'";
        }
        if (accInfo.getSAccountNoEndCtrl() != null && accInfo.getSAccountNoEndCtrl().length() > 0)
        {
        	sql[2] = sql[2] + " and a.sAccountNo <= '" + accInfo.getSAccountNoEndCtrl() + "'";
        }
        //结息日期
        if (accInfo.getTsDateStart()!=null)	
        {
        	sql[2] = sql[2] + " and b.dtDate >=  to_date('" + DataFormat.getDateString( accInfo.getTsDateStart() ) + "','yyyy-mm-dd')";
        }
        if (accInfo.getTsDateEnd()!=null)	
        {
        	sql[2] = sql[2] + " and b.dtDate <= to_date('" + DataFormat.getDateString( accInfo.getTsDateEnd() ) + "','yyyy-mm-dd')";
        }
        
        sql[3] = "  ";

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfo", null);
        pageLoader.setOrderBy(" " + sql[3] + " ");
        return pageLoader;
	}
	
	public PageLoader adjustFind(AccumulateFeeInfoQuery accInfo)throws Exception
	{
        String[] sql = new String[4];
        //select
        sql[0] = " b.id as lID,a.Id as lAccountID,a.sAccountNO as sAccountNo,c.id as lDueBillNo, "
        	   + "  c.sCode as sLetoutRequisitionNo,e.id as lContractID,e.sContractCode as sContractNo "
        	   + " ,d.sName as sInterestRatePlanName ,b.MINTEREST as dInterest,b.AL_mCommission as dCommission, "
        	   + " b.AL_mSuretyFee as dSuretyFee,b.AL_mInterestTax as dInterestTax , a.nAccountTypeID as lDepositTypeID" ;
        //from
        sql[1] = " sett_Account a,sett_subAccount b,loan_payForm c,"
        	   + " (select * from sett_interestRatePlan xx where xx.nofficeid = "
                           + accInfo.getLOfficeID()  +" and xx.ncurrencyid = "+ accInfo.getLCurrencyID()+") d ," 
        	   + "loan_contractform e , sett_accountType f";

        //where
        sql[2] = " b.nAccountID=a.id "
	           + " and b.AF_nInterestPlanID=d.id(+) "
	           + " and b.AL_nLoanNoteID=c.id (+) "
	           + " and c.ncontractid = e.id(+) "
			   + " and a.nAccountTypeID=f.id "		//账户类型相等
	           + " and f.nAccountGroupID  in ("+SETTConstant.AccountGroupType.CURRENT+","+SETTConstant.AccountGroupType.FIXED+") "
	        	   //账户组为活期和定期两种类型
	           + " and a.nStatusID>0 "
	           + " and b.nStatusID>0 "
	           + " and a.NOFFICEID = " + accInfo.getLOfficeID()	     //办事处ID
			   + " and a.NCURRENCYID = "+ accInfo.getLCurrencyID() 	 //币种ID
		       + " and d.NOFFICEID = " + accInfo.getLOfficeID()	     //办事处ID
			   + " and d.NCURRENCYID = "+ accInfo.getLCurrencyID() ; //币种ID
        //账户号
        if (accInfo.getSAccountNoStartCtrl() != null && accInfo.getSAccountNoStartCtrl().length() > 0)
        {
        	sql[2] = sql[2] + " and a.sAccountNo >= '" + accInfo.getSAccountNoStartCtrl() + "'";
        }
        if (accInfo.getSAccountNoEndCtrl() != null && accInfo.getSAccountNoEndCtrl().length() > 0)
        {
        	sql[2] = sql[2] + " and a.sAccountNo <= '" + accInfo.getSAccountNoEndCtrl() + "'";
        }
        
        sql[3] = "  ";

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.settlement.transadjustinterest.dataentity.AccumulateFeeInfo", null);
        pageLoader.setOrderBy(" " + sql[3] + " ");
        return pageLoader;
	}
	
	public PageLoader findAdjustByCondition(AdjustInterestInfoQuery adjustInfo)throws Exception
	{
        String[] sql = new String[4];
        //select
        sql[0] = " id as lID , nOfficeID as lOfficeID ,nCurrencyID as lCurrencyID , "
		       + " nAccountID as lAccountID,  nContractID as  lContractID , nDueBillID as lDueBillID, "
		       + " nAddOrRedueID as lAddOrRedueID , mInterest as dInterest,  mCommission as dCommission, "
		       + " mSuretyFee as dSuretyFee,   mInterestTax as dInterestTax , nInputUserID  as lInputUserID, "
		       + " dtInput as tsInput,  nCheckUserID as lCheckUserID ,  dtCheck as tsCheck, "
		       + " sAdjustReason ,nStatusID as lStatusID ,dtExecute as tsExecute,NDEPOSITTYPE as lDepositTypeID ,NSUBACCOUNTID as lSubAccountID " ;
        //from
        sql[1] = " Sett_AdjustInterest ";

        //where
        sql[2] = " 1=1 ";

        if(adjustInfo.getLStatusID()==-1){
        	//全部(不包括删除的)
        	sql[2] += " and nStatusID !=" + Constant.RecordStatus.INVALID+ " \n" ;
        	
        }else {
        	//各个状态的值 0:删除 2:保存 3:复核
        	sql[2] += " and nStatusID=" + adjustInfo.getLStatusID()+ " \n" ;
        }
        
        if(adjustInfo.getSAccountNoStart()!=-1)	//账户ID
        	sql[2] += "  and nAccountID =" + adjustInfo.getSAccountNoStart()+ " \n" ;
        
        if(adjustInfo.getSContractNoStart()!=-1)	//合同ID
        	sql[2] += "  and nContractID =" + adjustInfo.getSContractNoStart()+ " \n" ;
        
        if(adjustInfo.getSDueBillCodeStart()!=-1)	//放款通知单ID
        	sql[2] += "  and nDueBillID =" + adjustInfo.getSDueBillCodeStart()+ " \n" ;
        
        //结息日期
        if (adjustInfo.getTsDateStart()!=null)	
        {
        	sql[2] += "   and dtExecute >=  to_date('" + DataFormat.getDateString( adjustInfo.getTsDateStart() ) + "','yyyy-mm-dd')";
        }
        if (adjustInfo.getTsDateEnd()!=null)	
        {
        	sql[2] += "  and dtExecute <= to_date('" + DataFormat.getDateString( adjustInfo.getTsDateEnd() ) + "','yyyy-mm-dd')";
        }
        
        sql[2] += "   and nOfficeID=" + adjustInfo.getLOfficeID();
        sql[2] += "   and nCurrencyID=" + adjustInfo.getLCurrencyID();
        
        
        sql[3] = "  ";
        //针对哪个字段进行排序
		switch ((int) adjustInfo.getLOrderParam()) 
		{
			case 1:
				sql[3] += " order by ID ";
				break;
			case 2:
				sql[3] += " order by mInterest ";
				break;
			case 3:
				sql[3] += " order by mCommission ";
				break;
			case 4:
				sql[3] += " order by mSuretyFee ";
				break;
			case 5:
				sql[3] += " order by mInterestTax ";
				break;
			case 6:
				sql[3] += " order by nStatusID ";
				break;
			case 7:
				sql[3] += " order by nInputUserID ";
				break;
			case 8:
				sql[3] += " order by dtInput ";
				break;
	
			case 11:
				sql[3] += " order by nAccountID ";
				break;
			case 12:
				sql[3] += " order by nContractID ";
				break;
			case 13:
				sql[3] += " order by nDueBillID ";
				break;
			case 14:
				sql[3] += " order by dtExecute ";
				break;
				
			default:
				sql[3] += " order by id "; ////ID号
		}

		//是升序还是降序
		if (adjustInfo.getSDesc().equals("asc"))
			sql[3] += " asc ";
		else
			sql[3] += " desc ";

        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo", null);
        pageLoader.setOrderBy(" " + sql[3] + " ");
        return pageLoader;
	}
	
	
}
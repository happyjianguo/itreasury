package com.iss.itreasury.loan.loaninterestsetting.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAO;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;

import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustPayConditionInfo;

import com.iss.itreasury.loan.repayplan.dataentity.PlanModifyInfo;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANConstant;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IRollbackException;

import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.InterestRateInfo;

public class LoanInterestSettingDao extends ITreasuryDAO{
	
	protected StringBuffer m_sbSelect = null;

	protected StringBuffer m_sbFrom = null;

	protected StringBuffer m_sbWhere = null;

	protected StringBuffer m_sbOrderBy = null;

	private String mergeString(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString()).append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	/**
	 * 查询一个合同编号和一个放款通知单调整业务
	 * 
	 * @throws Exception
	 */
	public PageLoader findAdjustContractInterest(AdjustInterestConditionInfo Info)
			throws Exception {
		

		String str1 = getContractInterestSql1(Info);
		 String str2= getContractInterestSql2(Info);
		
		String NLoanType = Info.getLoanTypeIDsList();
		
		String[] sql3= new String[4];
		
		boolean flag =isLoan_WT(NLoanType, Info.getM_lOfficeID(), Info.getLCurrency());
		
		
		//贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if(!flag && Info.getConsignClientIDFrom()<0 && Info.getConsignClientIDTo()<0){
			sql3[0]=" s.*,lpad(rownum, 3,'00' ) as numberRow ";
			sql3[1] = "( "+str1+") s";
			sql3[2]="1=1";
			sql3[3]="";
		}else if(Info.getConsignClientIDFrom()>0 || Info.getConsignClientIDTo()>0){
			sql3[0]="s.*,lpad(rownum, 3,'00' ) as numberRow";
			sql3[1] = "( "+str2+") s";
			sql3[2]="1=1";
			sql3[3]="order by numberRow";
			
		}else 
		{ 
		  sql3[0]="s.*,lpad(rownum, 3,'00' ) as numberRow";
		  sql3[1] = "( "+str1 + " union "+ str2+") s";
		  sql3[2]="1=1";
		  sql3[3]="order by numberRow";
		  
		}
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						sql3[1],
						sql3[0],
						sql3[2],
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestDetailInfo",
						null);
		pageLoader.setOrderBy(" " + sql3[3] + " ");
		
		return pageLoader;
	}
	
	
	/**
	 * 判断子类型是否为委托贷款
	 * @param subLoanType 贷款子类型
	 * @param officeID  办事处
	 * @param currencyID  币种
	 * @return
	 */
	private boolean isLoan_WT(String subLoanType, long officeID, long currencyID){
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		if(subLoanType==null||("").equals(subLoanType)){
			return true;
		}
		try{
		
			sql="select  loantypeid  from loan_loantypesetting  where statusid=1 and currencyid="+currencyID+" and officeid="+officeID+" and id in ("+subLoanType+")";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{	
			  if(rs.getLong("loantypeid")== LOANConstant.LoanType.WT){
				  
				  return true;
				 
			  }
			
		   }
		
	    }catch (Exception e){
		
		  // TODO Auto-generated catch block
		   e.printStackTrace();
		  try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		   } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		

	}finally{
		
		try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		return false;
		
}
	
	
	
	/**
	 * 查询查找一个合同编号和所有放款通知单调整业务
	 * 
	 * @throws Exception
	 */
	/*public PageLoader findAdjustContract(AdjustInterestConditionInfo Info)
			throws Exception {
		getContractSql(Info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestDetailInfo",
						null);
		//pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}*/
	
	/**
	 * 查询一个合同编号的调整业务
	 * 
	 * @throws Exception
	 */
	public PageLoader findContract(AdjustInterestConditionInfo Info)
			throws Exception {
		getContractDetailSql(Info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustcontractdetailInfo",
						null);
		//pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	
	
	 /**
     * 根据贷款期限找出距离申请日最近的生效日的利率
     * @param term        贷款期限
     * @param officeid    机构ID
     * @param currencyid  币种ID
     * @return
     * @throws java.rmi.RemoteException
     */
    public InterestRateInfo findLatelyInterestRateByTerm (long term,long officeid,long currencyid,Timestamp applydate) throws java.rmi.RemoteException
    {
		int result = 0;
		InterestRateInfo ii = new InterestRateInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if((int)term>=0&&(int)term<=6)
			result = 1;
		else if((int)term>6&&(int)term<=12)
			result = 2;
		else if((int)term>12&&(int)term<=36)
			result = 3;
		else if((int)term>36&&(int)term<=60)
			result = 4;
		else
			result = 5;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
//			sb.append("select mrate,nBankInterestTypeID from  (SELECT a.*, b.SINTERESTRATENO,b.SINTERESTRATENAME,b.id as parentid, b.adjustratetypeid ");
//			sb.append(" FROM loan_InterestRate a,loan_INTERESTRATETYPEINFO b ");
//			sb.append(" WHERE b.adjustratetypeid=? and b.ID=a.NBANKINTERESTTYPEID and b.nofficeid = ? and b.ncurrencyid = ? ) u where dtvalidate<=? and rownum = 1 ");
			sb.append("select * from (select a.id as id ,a.dtvalidate as dtvalidate,a.mrate as mrate,a.NBANKINTERESTTYPEID as NBANKINTERESTTYPEID from  (select a.dtInput  as dtInput,   a.id   as id, a.NBANKINTERESTTYPEID as NBANKINTERESTTYPEID, a.mrate    as mrate,  a.dtvalidate  as dtvalidate, a.ncurrencyid  as ncurrencyid, a.nofficeid   as nofficeid, b.SINTERESTRATENAME   as SINTERESTRATENAME,  b.SINTERESTRATENO  as SINTERESTRATENO , c.SNAME  as name1, a.nInputUserID        as nInputUserID , b.adjustratetypeid    as adjustratetypeid from loan_InterestRate a,  loan_INTERESTRATETYPEINFO b, userinfo c where b.ID  =a.NBANKINTERESTTYPEID  and c.ID  =a.NINPUTUSERID and b.NCURRENCYID =? and b.NOFFICEID =? and (b.id,a.dtValidate) in (select b.id, max(a.dtvalidate) from loan_InterestRate a, loan_INTERESTRATETYPEINFO b where b.ID=a.NBANKINTERESTTYPEID group by b.id )) a where a.adjustratetypeid=? and a.nofficeid  = ? and a.ncurrencyid  = ? order by a.dtvalidate desc) b where b.dtvalidate<=? and rownum=1");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, currencyid);
			ps.setLong(2,officeid);
			ps.setLong(3, result);
			ps.setLong(4,officeid);
			ps.setLong(5, currencyid);
			ps.setTimestamp(6, applydate);
			rs = ps.executeQuery();
			if( rs != null && rs.next() )
			{
				//ii.setID(rs.getLong("ID"));
				ii.setBankInterestTypeID(rs.getLong("id"));
				ii.setInterestRate(rs.getDouble("mRate"));
				/*ii.setValiDate(rs.getTimestamp("dtValidate"));
				ii.setInputUserID(rs.getLong("nInputUserID"));
				ii.setInputDate(rs.getTimestamp("dtInput"));
				ii.setInterestRateCode(rs.getString("SINTERESTRATENO"));
				ii.setInterestRateName(rs.getString("SINTERESTRATENAME"));
				ii.setAdjustratetypeid(rs.getLong("adjustratetypeid"));
				ii.setCheckUserID(rs.getLong("nModifyUserID"));
				ii.setCheckDate(rs.getTimestamp("dtModify"));*/
			}
			
			rs.close();rs = null;
			ps.close();ps = null;
			con.close();con = null;
		}
		catch(Exception e)
		{
			
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if( rs != null )
				{
					rs.close();
					rs = null;
				}
				if( ps != null )
				{
					ps.close();
					ps = null;
				}
				if( con != null )
				{
					con.close();
					con = null;
				}
			}
			catch(Exception e)
			{
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return ii;
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	//查找一个合同号和一个放款通知单
	public String getContractInterestSql1(AdjustInterestConditionInfo Info)
	{
		StringBuffer select = new StringBuffer(); 
		select.append( " to_number(a.ncontractid) as ncontractid,c.sname as nInputUser,a.BATCHID as batchID,SCONTRACTCODE as sContractCode ,lt.name as nSubTypeName,d.name as loanClientName,'' as consignClientName,b.nTypeID as nType, a.DTINPUTDATE as dtInputDate, a.nloanpaynoticeid as nloanpaynoticeid,a.mrate as mRate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid as NadjustStatus,e.MINTERESTRATE as beforeBaseRate,e.MADJUSTRATE as beforeMadjustRate, a.NCURRENCYID as nCurrencyID,a.NOFFICEID as nofficeID,  e.MSTAIDADJUSTRATE as beforeMstaidAdjustRate \n ") ;                           //此处为 新增 的 放款通知单 的状态,增加了新的表的字段  全哨。2010-4-29
    	 // from
		StringBuffer from = new StringBuffer();
    	from.append( " loan_rateadjustpaycondition a,loan_contractform b ,userinfo c, client_clientinfo d ,loan_payform e,loan_loantypesetting lt");   //此处为 新增 的 放款通知单 的状态,增加了新的表  全哨。2010-4-29 
    	 // where 
    	StringBuffer where = new StringBuffer();
    	 
    	 where.append(" a.ncontractid = b.id  and e.id=a.NLOANPAYNOTICEID and lt.id=b.nSubTypeID and c.id=a.ninputuserid and d.id = b.nborrowclientid  and e.NCONTRACTID=a.NCONTRACTID and a.nofficeid="+Info.getM_lOfficeID()+" and a.ncurrencyid= "+Info.getLCurrency());
    	 
    	 
   	
    	 
    	 //合同编号
    	if( (Info.getContractCodeFrom()!=null) && (Info.getContractCodeFrom().length()>0)){
    	
    		where.append(" and b.SCONTRACTCODE>= '" + Info.getContractCodeFrom()+"'");
    	}
    	
    	if( (Info.getContractCodeTo()!=null) && (Info.getContractCodeTo().length()>0)){
        	
    		where.append("and  b.SCONTRACTCODE<= '" + Info.getContractCodeTo()+"'");
    	}
    	
    	//借款单位
		if (Info.getBorrowClientIDFrom() > 0){
			where.append(" and b.nBorrowClientID>=" + Info.getBorrowClientIDFrom());
		}
		if (Info.getBorrowClientIDTo() > 0){
			where.append(" and b.nBorrowClientID<=" + Info.getBorrowClientIDTo());
		}
		
		//放款通知单
		if (Info.getLLoanPayNoticeCodeFrom()!=null && Info.getLLoanPayNoticeCodeFrom().length()>0){
			where.append(" and e.sCode>=" + Info.getLLoanPayNoticeCodeFrom());
		}
		if (Info.getLLoanPayNoticeCodeTo() !=null && Info.getLLoanPayNoticeCodeTo().length()>0){
			where.append(" and e.sCode<=" + Info.getLLoanPayNoticeCodeTo());
		}

		
		//调整后利率
		
		if (Info.getTsAdjustRateFrom() > 0){
			
			where.append("  and( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate)>=" +DataFormat.formatRate(Info.getTsAdjustRateFrom()));
			
		}
		if  (Info.getTsAdjustRateTo() > 0) {
			
			where.append(" and ( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate) <= " + DataFormat.formatRate(Info.getTsAdjustRateTo()));
		}
		
		//生效日期
		if (Info.getTsValidateFrom() != null){
			where.append(" and TRUNC(a.DTVALIDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsValidateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsValidateTo() != null){
			where.append(" and TRUNC(a.DTVALIDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsValidateTo()) + "','yyyy-mm-dd') ");
		}
		
		//录入日期
		if (Info.getTsInputDateFrom() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsInputDateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsInputDateTo() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsInputDateTo()) + "','yyyy-mm-dd') ");
		}
		

		//贷款类型
	       if(Info.getLoanTypeIDsList()==null || Info.getLoanTypeIDsList().equals("")||Info.getLoanTypeIDsList().equals("-1")  ){
	    	   
	    	   where.append(" and b.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
	        		"  a.loantypeid  in (1,5) and a.id=b.subloantypeid " +
	        		"and b.currencyid="+Info.getLCurrency()+" and b.officeid="+Info.getM_lOfficeID()+")");
	       } 
	  		
	       else{
	  			
	    	   where.append(" and b.nSubTypeID in (" + Info.getLoanTypeIDsList() + ") and b.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and "+
	  					"  a.loantypeid=2 and a.id=b.subloantypeid " +
    	        		"and b.currencyid="+Info.getLCurrency()+" and b.officeid="+Info.getM_lOfficeID()+")");
	  			
	  		}
	       
    	
 		   
 		 long loanTypeStatus[]=LOANConstant.InterestRateSettingStatus.getAllCode(Info.getM_lOfficeID(),Info.getLCurrency()); 
 		 String loanTypeList="";
  			for ( int i=0;i<loanTypeStatus.length;i++ )
  			{
  				
  				
  				if(loanTypeStatus[i]==LOANConstant.InterestRateSettingStatus.REFUSE){
  	  				continue;
  	  				}
  				
  				loanTypeList+=String.valueOf(loanTypeStatus[i]);
  				if((loanTypeStatus.length-1)>0){
  				
  					loanTypeList+=",";
  				
  				}
  			} 
  		//审批状态
  	  	   if(Info.getLoanStatusTypeIDsList()==null ||Info.getLoanStatusTypeIDsList().equals("")|| Info.getLoanStatusTypeIDsList().trim().equals("-1") ){
  	  		   
  	  		     where.append(" and a.NSTATUSID in (" + loanTypeList.substring(0, loanTypeList.length()-1) + ")");
  	  	    }
  	  	   else{
  	   			
  	   			where.append("and a.NSTATUSID in (" + Info.getLoanStatusTypeIDsList() + ")");
  	   			
  	   		}

		 
		 
		 String sql="select "+select.toString()+" from "+from.toString()+" where "+where.toString();
		 
		 return sql;
		
	}
	
	
	
	

	//查找一个合同号和一个放款通知单
	public String getContractInterestSql2(AdjustInterestConditionInfo Info)
	{
		StringBuffer select = new StringBuffer();
		 select.append( " to_number(a.ncontractid) as ncontractid,c.sname as nInputUser,a.BATCHID as batchID, SCONTRACTCODE as sContractCode, lt.name as nSubTypeName,d.name as loanClientName,d1.name as consignClientName,b.nTypeID as nType, a.DTINPUTDATE as dtInputDate, a.nloanpaynoticeid  as nloanpaynoticeid,a.mrate as mRate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid as NadjustStatus,e.MINTERESTRATE as beforeBaseRate,e.MADJUSTRATE as beforeMadjustRate, a.NCURRENCYID as nCurrencyID,a.NOFFICEID as nofficeID, e.MSTAIDADJUSTRATE as beforeMstaidAdjustRate \n ") ;                           //此处为 新增 的 放款通知单 的状态,增加了新的表的字段  全哨。2010-4-29
    	 // from
		 StringBuffer from = new StringBuffer();
		 from.append( " loan_rateadjustpaycondition a,loan_contractform b ,userinfo c, client_clientinfo d ,client_clientinfo d1 ,loan_payform e,loan_loantypesetting lt");   //此处为 新增 的 放款通知单 的状态,增加了新的表  全哨。2010-4-29 
    	 // where 
		 StringBuffer where = new StringBuffer();
		 
    	 
    	 where.append("  lt.id= b.nSubTypeId and e.id=a.NLOANPAYNOTICEID and  a.ncontractid = b.id and c.id=a.ninputuserid and d.id = b.nborrowclientid and d1.id=b.NCONSIGNCLIENTID  and e.NCONTRACTID=a.NCONTRACTID and a.nofficeid="+Info.getM_lOfficeID()+" and a.ncurrencyid= "+Info.getLCurrency());
    	 
    	
   		
   	   
    	 
   	    //合同编号
    	if( (Info.getContractCodeFrom()!=null) && (Info.getContractCodeFrom().length()>0)){
    	
    		where.append(" and b.SCONTRACTCODE>= '" + Info.getContractCodeFrom()+"'");
    	}
    	
    	if( (Info.getContractCodeTo()!=null) && (Info.getContractCodeTo().length()>0)){
        	
    		where.append("and  b.SCONTRACTCODE<= '" + Info.getContractCodeTo()+"'");
    	}
    	
    	//放款通知单
		if (Info.getLLoanPayNoticeCodeFrom()!=null && Info.getLLoanPayNoticeCodeFrom().length()>0){
			where.append(" and e.sCode>=" + Info.getLLoanPayNoticeCodeFrom());
		}
		if (Info.getLLoanPayNoticeCodeTo() !=null && Info.getLLoanPayNoticeCodeTo().length()>0){
			where.append(" and e.sCode<=" + Info.getLLoanPayNoticeCodeTo());
		}
    	
    	
    	//借款单位
		if (Info.getBorrowClientIDFrom() > 0){
			where.append(" and b.nBorrowClientID>=" + Info.getBorrowClientIDFrom());
		}
		if (Info.getBorrowClientIDTo() > 0){
			where.append(" and b.nBorrowClientID<=" + Info.getBorrowClientIDTo());
		}
		
		//委托单位
		if (Info.getConsignClientIDFrom() > 0){
			where.append(" and b.NCONSIGNCLIENTID>=" + Info.getConsignClientIDFrom());
		}
		if (Info.getConsignClientIDTo() > 0){
			where.append(" and b.NCONSIGNCLIENTID<=" + Info.getConsignClientIDTo());
		}
		
		//调整后利率
		
		if (Info.getTsAdjustRateFrom() > 0){
			
			where.append("  and( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate)>=" +DataFormat.formatRate(Info.getTsAdjustRateFrom()));
			
		}
		if  (Info.getTsAdjustRateTo() > 0) {
			
			where.append(" and ( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate) <= " + DataFormat.formatRate(Info.getTsAdjustRateTo()));
		}
		
		//生效日期
		if (Info.getTsValidateFrom() != null){
			where.append(" and TRUNC(a.DTVALIDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsValidateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsValidateTo() != null){
			where.append(" and TRUNC(a.DTVALIDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsValidateTo()) + "','yyyy-mm-dd') ");
		}
		
		//录入日期
		if (Info.getTsInputDateFrom() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsInputDateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsInputDateTo() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsInputDateTo()) + "','yyyy-mm-dd') ");
		}
		
		
		//贷款类型
	       if(Info.getLoanTypeIDsList()==null ||Info.getLoanTypeIDsList().equals("")|| Info.getLoanTypeIDsList().trim().equals("-1") ){
	    	   where.append(" and b.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
	        		"  a.loantypeid  in (2) and a.id=b.subloantypeid " +
	        		"and b.currencyid="+Info.getLCurrency()+" and b.officeid="+Info.getM_lOfficeID()+")");
	       } 
	  		
	       else{
	  			
	    	   where.append(" and b.nSubTypeID in (" + Info.getLoanTypeIDsList() + ")");
	  			
	  		}
 		   
 		 long loanTypeStatus[]=LOANConstant.InterestRateSettingStatus.getAllCode(Info.getM_lOfficeID(),Info.getLCurrency()); 
 		 String loanTypeList="";
  			for ( int i=0;i<loanTypeStatus.length;i++ )
  			{
  				
  				if(loanTypeStatus[i]==LOANConstant.InterestRateSettingStatus.REFUSE){
  	  				continue;
  	  			}
  				
  				
  				loanTypeList+=String.valueOf(loanTypeStatus[i]);
  				if((loanTypeStatus.length-1)>0){
  				
  					loanTypeList+=",";
  				
  				}
  			} 
  			
  	//审批状态
  	    if(Info.getLoanStatusTypeIDsList()==null ||Info.getLoanStatusTypeIDsList().equals("")|| Info.getLoanStatusTypeIDsList().trim().equals("-1") ){
  		   
  		     where.append(" and a.NSTATUSID in (" + loanTypeList.substring(0, loanTypeList.length()-1) + ")");
  	    }
  	    else{
   			
   			where.append("and a.NSTATUSID in (" + Info.getLoanStatusTypeIDsList() + ")");
   			
   		}
        
		 String sql="select "+select.toString()+" from "+from.toString()+" where "+where.toString();
		 
		 return sql;
	
	}
	
	
	
	
	
	/*//查找一个合同编号和所有放款通知单
	public void getContractSql(AdjustInterestConditionInfo Info)
	{
		m_sbSelect = new StringBuffer(); 
	   	m_sbSelect.append( "to_number(a.ncontractid) as ncontractid,a.nloanpaynoticeid,a.mrate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid NadjustStatus  \n ") ;                         //此处为 新增 的 放款通知单 的状态,增加了新的表的字段  全哨。2010-4-29
	   	// from
	   	m_sbFrom = new StringBuffer();
	   	m_sbFrom.append( "  loan_rateadjustpaycondition a  "); //此处为 新增 的 放款通知单 的状态,增加了新的表  全哨。2010-4-29
	   	// where 
	   	m_sbWhere = new StringBuffer();
	   	m_sbWhere.append("  a.nContractID=" + Info.getLContractID()+" \n");
	   	m_sbWhere.append(" order by a.id,a.nloanpaynoticeid,a.dtvalidate desc ");               //此处修改了排序的字段  全哨。2010-4-29 
	   	// m_sbWhere.append("  and bb.Status != "+Constant.RecordStatus.INVALID +"\n");
	   	System.out.println(m_sbSelect);
	   	System.out.println(m_sbFrom);
		System.out.println(m_sbWhere);
		//System.out.println ( m_sbSelect +  m_sbFrom + m_sbWhere);
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
	}*/
	
	
	/*
	//贷款合同执行利率调整查询
	public String getRateAdjustOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 :
				oBuf.append(" \n order by c.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c1.sName" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.sName" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by c.mLoanAmount" + strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by c.mInterestRate" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by c.nIntervalNum" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by c.dtStartDate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by c.nStatusID" + strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
				break;
			case 12 :
				oBuf.append(" \n order by c.mExamineAmount" + strDesc);
				break;
			case 13 :
				oBuf.append(" \n order by c.mCheckAmount" + strDesc);
				break;
			case 14 :
				oBuf.append(" \n order by c.mDiscountRate" + strDesc);
				break;
			case 15 :
				oBuf.append(" \n order by c.dtEndDate" + strDesc);
				break;
			case 20 :
				oBuf.append(" \n order by u2.sName" + strDesc);
				break;
			case 21:
				oBuf.append(" \n order by c.assureChargeRate" +strDesc);
				break;
			case 22 :
				oBuf.append(" \n order by c.nrisklevel" + strDesc);
				break;					
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	*/
	
	/**
	 * 
	 */
	public double getLatelyRate (long lcontractID, long payNoticeID,long currencyID,long officeID,Timestamp dtValidate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		double lRate = -1;
		
		try
		{
			conn = Database.getConnection();
	
			
			strSQL = "select  (a.mrate*(1+a.MADJUSTRATE*1/100)+a.MSTAIDADJUSTRATE) as beforeAjustRate from loan_rateadjustpaycondition a,loan_payform b where a.nContractID=b.ncontractid and a.nloanpaynoticeid=b.id and a.NSTATUSID =4 and a.ncontractid = "+lcontractID+" and a.NLOANPAYNOTICEID= "+payNoticeID+" and a.ncurrencyid= "+currencyID+" and a.nofficeid= "+officeID+" and TRUNC(a.DTVALIDATE)<to_date( '" +DataFormat.getDateString(dtValidate)+"','yyyy-mm-dd') order by a.dtvalidate desc,a.id desc ";

			System.out.println("--------------------****lipeng's test*****-------------------"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs.next()){
				lRate=rs.getDouble("beforeAjustRate");
			}
			rs.close();
			ps.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if (rs != null)
					rs.close();
					rs = null;
				if (ps != null)
					ps.close();
					ps = null;
				if (conn != null)
					conn.close();
					conn = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lRate;
	}
	
	
	
	//查找只有一个合同编号
	public void getContractDetailSql(AdjustInterestConditionInfo Info)
	{
		m_sbSelect = new StringBuffer(); 
	   	m_sbSelect.append( "aa.* \n ") ;
	   	// from
	   	m_sbFrom = new StringBuffer();
	   	m_sbFrom.append( "  loan_rateadjustcontractdetail aa");
	   	// where 
	   	m_sbWhere = new StringBuffer();
	   	m_sbWhere.append("  aa.nContractID=" + Info.getLContractID()+" \n");
	   	m_sbWhere.append("  order by  aa.dtstartdate desc " );
	   	// m_sbWhere.append("  and aa.Status != "+Constant.RecordStatus.INVALID +"\n");
		//System.out.println ( m_sbSelect +  m_sbFrom + m_sbWhere);
		////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
	}
	
	//保存融资租赁利率调整 add by zwxiao 2010-06-21
	public long adjustInterestRate (AdjustPayConditionInfo conditionInfo)throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
	
			//判断逻辑，生效日期不能再有已经调整过的日期之前
			strSQL = " select count(*) from loan_rateadjustpaycondition where 1=1 ";

			if (conditionInfo.getNcontractid() != null && conditionInfo.getNcontractid().length() > 0){
				strSQL += " and nContractID = " + Long.valueOf(conditionInfo.getNcontractid()).longValue();
			}
			if (conditionInfo.getDtvalidate() != null){
				strSQL += " and dtvalidate >= to_date('"+DataFormat.formatDate(conditionInfo.getDtvalidate())+"','yyyy-MM-dd')"  ;
			}
			strSQL += " and nstatusId != 0 ";
	
			System.out.println("--------------------haoliang-------------------"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			System.out.println("lMaxID1=" + lMaxID);
			if (lReturn > 0)
			{
				conn.close();
				conn = null;
				return ( -2);
			}
			
			strSQL = "select nvl(max(id),0)+1 as maxId from loan_rateadjustpaycondition";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			int index = 1;
			if (conditionInfo.getId() <= 0)
			{
				strSQL = " Insert into loan_rateadjustpaycondition( " +
					" ID,nBankInterestID,nContractID,nLoanPayNoticeID,mRate,dtValiDate,sReason, " +
					" nInputUserID,dtInputDate,nStatusID,nOfficeID,nNextCheckUserID,MADJUSTRATE,MSTAIDADJUSTRATE,nNextCheckLevel,nCurrencyID ) " +
					" values ((select nvl(max(id),0)+1 from loan_rateadjustpaycondition),?,?,?,?,?,?,?,sysdate,?,?,?,?,?,-1,?) ";
	
				System.out.println(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(index++, conditionInfo.getNbankinterestid());
				ps.setString(index++,conditionInfo.getNcontractid());
				ps.setLong(index++, conditionInfo.getNloanpaynoticeid());
				ps.setDouble(index++, conditionInfo.getMrate());
				ps.setTimestamp(index++, conditionInfo.getDtvalidate());
				ps.setString(index++, conditionInfo.getSreason());
				ps.setLong(index++, conditionInfo.getNinputuserid());
				ps.setLong(index++, LOANConstant.InterestRateSettingStatus.SUBMIT);
				ps.setLong(index++, conditionInfo.getNofficeid());
				ps.setLong(index++, -1);
				ps.setDouble(index++, conditionInfo.getMadjustrate());
				ps.setDouble(index++, conditionInfo.getMstaidadjustrate());
				ps.setLong(index++, conditionInfo.getNcurrencyid());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
			{
				strSQL = " Update loan_rateadjustpaycondition Set " +
					" nBankInterestID=?,mRate=?,dtValiDate=?,sReason=?,nstatusID=?,MADJUSTRATE=?,MSTAIDADJUSTRATE=? " +
					" Where ID=? ";
	
				ps = conn.prepareStatement(strSQL);
				ps.setLong(index++, conditionInfo.getNbankinterestid());
				ps.setDouble(index++, conditionInfo.getMrate());
				ps.setTimestamp(index++, conditionInfo.getDtvalidate());
				ps.setString(index++, conditionInfo.getSreason());
				ps.setLong(index++, LOANConstant.InterestRateSettingStatus.SUBMIT);
				ps.setDouble(index++, conditionInfo.getMadjustrate());
				ps.setDouble(index++, conditionInfo.getMstaidadjustrate());
				ps.setLong(index++, conditionInfo.getId());
				ps.executeUpdate();
				ps.close();
				ps = null;
				lMaxID = conditionInfo.getId();
			}
			conn.close();
			conn = null;
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if (rs != null)
					rs.close();
					rs = null;
				if (ps != null)
					ps.close();
					ps = null;
				if (conn != null)
					conn.close();
					conn = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lMaxID;
	}

	//保存融资租赁计划调整 add by zwxiao 2010-06-21
	//repayColl为重新安排计划后的集合
	public long saveFinancePlan(Collection repayColl,ContractInfo contractInfo) throws Exception
	{
		long lResult = 1;
		long lVersionID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		RepayPlanInfo rp_info = new RepayPlanInfo();
		int nIndex = 1;

		try
		{
			conn = Database.getConnection();
			sb.append("select nvl(max(id)+1,1) from loan_loanContractPlan");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lVersionID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			sb.append("insert into loan_loanContractPlan (ID, NLOANID, NCONTRACTID, NPLANVERSION,  NSTATUSID,nIsUsed,nUserType,dtInputDate) values (?,?,?,(select nvl(max(NPLANVERSION)+1,1) from loan_loanContractPlan where NCONTRACTID = ?),?,?,?,sysdate)");
			ps = conn.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lVersionID);
			ps.setLong(nIndex++, contractInfo.getLoanID());
			ps.setLong(nIndex++, contractInfo.getContractID());
			ps.setLong(nIndex++, contractInfo.getContractID());
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			ps.setLong(nIndex++, Constant.YesOrNo.YES);
			ps.setLong(nIndex++, 1);
			if (ps.executeUpdate() < 1)
			{
				Log.print("error.update.loan_loanContractPlan");
			}
			Log.print("updated.loan_loanContractPlan.ID: " + lVersionID);
			ps.close();
			ps = null;
			if (repayColl.size() != 0)
			{
				Iterator iter = repayColl.iterator();
				while (iter.hasNext())
				{
					nIndex = 1;
					rp_info = (RepayPlanInfo) iter.next();
					sb.setLength(0);
					sb.append("insert into  loan_loanContractPlanDetail (ID, nContractPlanID, DTPLANDATE,  NPAYTYPEID, MAMOUNT, STYPE, DTMODIFYDATE,nLastExtendID,nLastOverdueID,nLastVersionPlanID,MINTERESTAMOUNT,MRECOGNIZANCEAMOUNT ) values (nvl((select max(id) from loan_loanContractPlanDetail),0)+1,?,?,?,?,?,?,?,?,?,?,?)");
					ps = conn.prepareStatement(sb.toString());
					ps.setLong(nIndex++, lVersionID);
					ps.setTimestamp(nIndex++, rp_info.tsPlanDate);
					ps.setLong(nIndex++, (long) rp_info.nLoanOrRepay);
					ps.setDouble(nIndex++, rp_info.dAmount);
					ps.setString(nIndex++, rp_info.sType);
					ps.setTimestamp(nIndex++, rp_info.tsInputDate);
					ps.setLong(nIndex++, rp_info.lLastExtendID);
					ps.setLong(nIndex++, rp_info.lLastOverDueID);
					ps.setLong(nIndex++, rp_info.lLastVersionPlanID);
					ps.setDouble(nIndex++, rp_info.mINTERESTAMOUNT);
					ps.setDouble(nIndex++, rp_info.mRECOGNIZANCEAMOUNT);
					if (ps.executeUpdate() < 1)
					{
						Log.print("error.insert.loan_loanContractPlanDetail");
					}
					Log.print("success.insert.loan_loanContractPlanDetail");
					ps.close();
					ps = null;
				}
			}

			conn.close();
			conn = null;
			lResult = lVersionID;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}
	
	//新增合同执行计划修改记录  add by zwxiao 2010-06-21
	public long savePlanModify(PlanModifyInfo o) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int nIndex = 1;
		ResultSet rs = null;
		long maxID = -1;
		try
		{
			conn = Database.getConnection();
			
			sb.append("select nvl(max(id)+1,1) from loan_PlanModifyForm");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				maxID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			
			// 要新增一条loan_PlanModifyForm记录
			sb.append("insert into loan_PlanModifyForm (ID,NCURRENCYID, NOFFICEID, NCONTRACTID,nPlanID, NINPUTUSERID, DTINPUT, NNextCHECKUSERID,  NSTATUSID, nNextCheckLevel) values (?,?,?,?,?,?,sysdate,?,?,1)");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, maxID);
			ps.setLong(nIndex++, o.getCurrencyID());
			ps.setLong(nIndex++, o.getOfficeID());
			ps.setLong(nIndex++, o.getContractID());
			ps.setLong(nIndex++, o.getPlanID());
			ps.setLong(nIndex++, o.getInputUserID());
			ps.setLong(nIndex++, o.getInputUserID());
			//说明：本来这个地方应该置为已保存的状态，但是由于我的工作中查询出显示待处理中的利率调整和执行计划调整两个地方都会有显示，
			//应该只显示在利率调整部分，所以将状态特殊处理一下，将执行计划调整的状态修改为审批中，这样能避免两边都显示
			//现在调用审批流，把状态更改为审批中
			ps.setLong(nIndex++, LOANConstant.PlanModifyStatus.APPROVALING);

			lResult = ps.executeUpdate();
			if (lResult < 1)
			{
				Log.print("error.update.loan_PlanModifyForm");
			}
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			sb.setLength(0);
			lResult = maxID;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		// 写入计划
		return lResult;
	}
	
	//通过ID查询调整信息  add by zwxiao 2010-06-21
	public AdjustInterestConditionInfo findAdjustInfoByID(long id) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int nIndex = 1;
		ResultSet rs = null;
		AdjustInterestConditionInfo info = new AdjustInterestConditionInfo ();
		try
		{
			conn = Database.getConnection();
			
			sb.append("select * from loan_rateadjustpaycondition where id = "+id);
			Log.print(sb.toString()); 
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				info.lID = id;
				info.lLoanPayNoticeID = rs.getLong ("nLoanPayNoticeID");
				info.lContractID = rs.getLong ("nContractID");
				info.lBankLoanInterestRateID = rs.getLong ("nBankInterestID");
				info.tsValidate = rs.getTimestamp ("dtValiDate");
				info.strReason = rs.getString ("sReason");
				info.lStatusID = rs.getLong ("nStatusID");
				info.lInputUserID = rs.getLong ("nInputUserID");
				info.lNextCheckLevel = rs.getLong ("nNextCheckLevel");
				info.dAdjustRate = rs.getDouble ("MADJUSTRATE");
				info.dStaidAdjustRate = rs.getDouble ("MSTAIDADJUSTRATE");
				info.fRate = rs.getDouble("MRATE");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}	
		return info;
	}
	
	//add by zwxiao 2010-07-03 删除利率调整表
	public long deleteLoanInterestSetting(long id)throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			sb.append("delete from loan_rateadjustpaycondition where id = "+id);
			ps = conn.prepareStatement(sb.toString());
			lReturn = ps.executeUpdate();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lReturn;
	}
	
	//add by zwxiao 2010-07-03 删除执行计划
	public long deleteByPlanID(long id)throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			sb.append("delete from loan_loanContractPlan where id = "+id);
			ps = conn.prepareStatement(sb.toString());
			lReturn = ps.executeUpdate();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lReturn;
	}
	
	//add by zwxiao 2010-07-03 删除执行计划明细
	public long deleteByPlandetailID(long id)throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			sb.append("delete from loan_loancontractplandetail where ncontractplanid = "+id);
			ps = conn.prepareStatement(sb.toString());
			lReturn = ps.executeUpdate();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lReturn;
	}
	
	//add by zwxiao 2010-07-03 删除版本修改
	public long deleteByPlanModifyID(long contractID,long planID)throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			sb.append("delete from loan_PlanModifyForm where NCONTRACTID = "+contractID +" and NPLANID = "+planID);
			ps = conn.prepareStatement(sb.toString());
			lReturn = ps.executeUpdate();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lReturn;
	}
	
	//检查融资租赁利率调整的生效日 add by zwxiao 2010-06-21
	public long checkRateAdjust(AdjustPayConditionInfo conditionInfo)throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			//判断逻辑，生效日期不能再有已经调整过的日期之前
			strSQL = " select count(*) from loan_rateadjustpaycondition where 1=1 ";

			if (conditionInfo.getNcontractid() != null && conditionInfo.getNcontractid().length() > 0){
				strSQL += " and nContractID = " + Long.valueOf(conditionInfo.getNcontractid()).longValue();
			}
			if (conditionInfo.getDtvalidate() != null){
				strSQL += " and dtvalidate >= to_date('"+DataFormat.formatDate(conditionInfo.getDtvalidate())+"','yyyy-MM-dd')"  ;
			}
			strSQL += " and nstatusId != 0";
			if (conditionInfo.getId() > 0){
				strSQL += " and id != "+conditionInfo.getId();
			}
	
			System.out.println("--------------------haoliang-------------------"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			System.out.println("lMaxID1=" + lMaxID);
			if (lReturn > 0)
			{
				conn.close();
				conn = null;
				return -2;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if (rs != null)
					rs.close();
					rs = null;
				if (ps != null)
					ps.close();
					ps = null;
				if (conn != null)
					conn.close();
					conn = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lMaxID;
	}
	
	/**
	 * @author 		zwxiao
	 * @date 		2010-06-21
	 * @function 	判断是否能融资租赁利率调整
	 * @param 		long lContractID(合同ID)
	 * @return 		lMaxID
	 * @modify		yunchang
	 * @date 		2010-11-08
	 * @function	对于方法进行优化调整
	 */
	public long isCanRateAdjustCheck(long lContractID)throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			/*
			 * 判断逻辑，生效日期不能再有已经调整过的日期之前
			 */
			strSQL = " select count(*) from loan_rateadjustpaycondition where 1=1 ";
			if (lContractID > 0)
			{
				strSQL += " and nContractID = " + lContractID;
			}
			strSQL += " and nstatusId not in (0,3)";
			System.out.println("===================================================");
			System.out.println("判断逻辑，生效日期不能在已经调整过的日期之前 strSQL="+strSQL);			
			System.out.println("===================================================");			
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			System.out.println("===================================================");
			System.out.println("判断逻辑，生效日期不能在已经调整过的日期之前 返回值="+lMaxID);			
			System.out.println("===================================================");
			if (lReturn > 0)
			{
				conn.close();
				conn = null;
				return -2;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;					
				}
				if (ps != null)
				{
					ps.close();
					ps = null;					
				}
				if (conn != null)
				{
					conn.close();
					conn = null;					
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lMaxID;
	}
	
	//判断是否有提前还款的通知单流程为完成的 add by zwxiao 2010-06-21
	public long isHavaPrepayment(long lContractID)throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			//判断逻辑，提前还款的通知单必须是在结算已经处理完成的
			strSQL = " select count(*)     ";
			strSQL += "   from loan_leaseholdrepayform a   ";
			strSQL += " where a.nstatusid not in (0,4,5)   ";
			strSQL += "   and a.contractid = "+lContractID;
			strSQL += "   and a.issue not in (select ISSUE   ";
			strSQL += "                         from sett_transreturnfinance b   ";
			strSQL += "                        where b.ncontractid = a.contractid   ";
			strSQL += "                          and b.nstatusid = "+SETTConstant.TransactionStatus.CHECK+")   ";
			
			System.out.println("--------------------isHavaPrepayment-------------------"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			System.out.println("lMaxID1=" + lMaxID);
			if (lReturn > 0)
			{
				conn.close();
				conn = null;
				return -2;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if (rs != null)
					rs.close();
					rs = null;
				if (ps != null)
					ps.close();
					ps = null;
				if (conn != null)
					conn.close();
					conn = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lMaxID;
	}
	
	//判断是否能融资租赁利率调整 add by zwxiao 2010-08-03
	public long isHavaPrepaymentCurrentIssue(AdjustPayConditionInfo conditionInfo)throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			
			strSQL = " select count(*)   ";
			strSQL += "  from loan_leaseholdrepayform a,   ";
			strSQL += "       (SELECT id,      ";
			strSQL += "               NCONTRACTPLANID,   ";
			strSQL += "               DTPLANDATE,   ";
			strSQL += "               RANK() OVER(PARTITION BY NCONTRACTPLANID ORDER BY DTPLANDATE) as issue   ";
			strSQL += "          FROM loan_loancontractplandetail) b,   ";
			strSQL += "       (select max(id) as id, NCONTRACTID as ncontractid   ";
			strSQL += "          from loan_loancontractplan   ";
			strSQL += "         where NISUSED = 2   ";
			strSQL += "           and NSTATUSID = 1   ";
			strSQL += "         group by NCONTRACTID) c   ";
			strSQL += " where a.contractid = c.ncontractid   ";
			strSQL += "   and c.id = b.NCONTRACTPLANID   ";
			strSQL += "   and a.nstatusid not in (0, 5)   ";
			strSQL += "   and a.contractid = "+Long.valueOf(conditionInfo.getNcontractid()).longValue() ;
			strSQL += "   and a.issue = b.issue   ";
			strSQL += "   and b.DTPLANDATE >= to_date('"+DataFormat.formatDate(conditionInfo.getDtvalidate())+"','yyyy-MM-dd')"  ;
			System.out.println("--------------------isHavaPrepaymentCurrentIssue-------------------"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			System.out.println("lMaxID1=" + lMaxID);
			if (lReturn > 0)
			{
				conn.close();
				conn = null;
				return -2;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if (rs != null)
					rs.close();
					rs = null;
				if (ps != null)
					ps.close();
					ps = null;
				if (conn != null)
					conn.close();
					conn = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lMaxID;
	}
	
	
	private static  void cleanup(ResultSet rs) throws SQLException
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
	private static void cleanup(PreparedStatement ps) throws SQLException
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
	private static void cleanup(Connection con) throws SQLException
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
	public long deleteRateAdjustContractDetail(AdjustPayConditionInfo adjinfo )throws Exception
	{
		long lReturn=-1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lContractID = -1;
		long lLoanPayNoticeID = -1;
		long lAdjustConditionID=-1;
		try
		{
			lAdjustConditionID=adjinfo.getId();
			con = Database.getConnection();
			//查出该loan_rateadjustpaycondition下的合同id和放款通知单id
			strSQL = " select ncontractid,nloanpaynoticeid from loan_rateadjustpaycondition "
				   + " where id = ?";			
			ps = con.prepareStatement(strSQL);
			ps.setLong(1,lAdjustConditionID);
			rs = ps.executeQuery ();
			if (rs!=null && rs.next ()) {
				lContractID = rs.getLong ("nContractID");
				lLoanPayNoticeID = rs.getLong ("nLoanPayNoticeID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (lContractID > 0)//删除
			{
				strSQL = " delete from loan_rateadjustcontractdetail where ncontractid = ? and nadjustconditionid = ? ";				
				ps = con.prepareStatement(strSQL);
				ps.setLong(1,lContractID);
				ps.setLong(2,lAdjustConditionID);
				ps.execute();
				ps.close();
				ps = null;
			}
			if (lLoanPayNoticeID > 0)
			{
				strSQL = " delete from loan_rateadjustpaydetail where nLoanpayNoticeID = ? and nadjustconditionid = ? ";				
				ps = con.prepareStatement(strSQL);
				ps.setLong(1,lLoanPayNoticeID);
				ps.setLong(2,lAdjustConditionID);
				ps.execute();
				ps.close();
				ps = null;
			}
			else//删除该合同下所有放款通知单的相关数据
			{
				strSQL = " delete from loan_rateadjustpaydetail where nLoanpayNoticeID in ( "
					   + " select id from loan_payform where ncontractid = ? ) "
					   + " and nadjustconditionid = ? ";				
				ps = con.prepareStatement(strSQL);
				ps.setLong(1,lContractID);
				ps.setLong(2,lAdjustConditionID);
				ps.execute();
				ps.close();
				ps = null;
			}		
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				if (rs != null) rs.close ();rs = null;
				if (ps != null) ps.close ();ps = null;
				if (con != null) con.close ();con = null;
			}
			catch(Exception ex) {			
				throw ex;
			}
		}
		return lReturn;
	}
	
	
	
	
}
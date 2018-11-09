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
	 * ��ѯһ����ͬ��ź�һ���ſ�֪ͨ������ҵ��
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
		
		
		//�������Ͳ�����ί�д����Ҳ�ѡί�е�λ ��getPayNoticeSQL1(payNInfo)
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
	 * �ж��������Ƿ�Ϊί�д���
	 * @param subLoanType ����������
	 * @param officeID  ���´�
	 * @param currencyID  ����
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
	 * ��ѯ����һ����ͬ��ź����зſ�֪ͨ������ҵ��
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
	 * ��ѯһ����ͬ��ŵĵ���ҵ��
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
     * ���ݴ��������ҳ������������������Ч�յ�����
     * @param term        ��������
     * @param officeid    ����ID
     * @param currencyid  ����ID
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	//����һ����ͬ�ź�һ���ſ�֪ͨ��
	public String getContractInterestSql1(AdjustInterestConditionInfo Info)
	{
		StringBuffer select = new StringBuffer(); 
		select.append( " to_number(a.ncontractid) as ncontractid,c.sname as nInputUser,a.BATCHID as batchID,SCONTRACTCODE as sContractCode ,lt.name as nSubTypeName,d.name as loanClientName,'' as consignClientName,b.nTypeID as nType, a.DTINPUTDATE as dtInputDate, a.nloanpaynoticeid as nloanpaynoticeid,a.mrate as mRate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid as NadjustStatus,e.MINTERESTRATE as beforeBaseRate,e.MADJUSTRATE as beforeMadjustRate, a.NCURRENCYID as nCurrencyID,a.NOFFICEID as nofficeID,  e.MSTAIDADJUSTRATE as beforeMstaidAdjustRate \n ") ;                           //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı���ֶ�  ȫ�ڡ�2010-4-29
    	 // from
		StringBuffer from = new StringBuffer();
    	from.append( " loan_rateadjustpaycondition a,loan_contractform b ,userinfo c, client_clientinfo d ,loan_payform e,loan_loantypesetting lt");   //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı�  ȫ�ڡ�2010-4-29 
    	 // where 
    	StringBuffer where = new StringBuffer();
    	 
    	 where.append(" a.ncontractid = b.id  and e.id=a.NLOANPAYNOTICEID and lt.id=b.nSubTypeID and c.id=a.ninputuserid and d.id = b.nborrowclientid  and e.NCONTRACTID=a.NCONTRACTID and a.nofficeid="+Info.getM_lOfficeID()+" and a.ncurrencyid= "+Info.getLCurrency());
    	 
    	 
   	
    	 
    	 //��ͬ���
    	if( (Info.getContractCodeFrom()!=null) && (Info.getContractCodeFrom().length()>0)){
    	
    		where.append(" and b.SCONTRACTCODE>= '" + Info.getContractCodeFrom()+"'");
    	}
    	
    	if( (Info.getContractCodeTo()!=null) && (Info.getContractCodeTo().length()>0)){
        	
    		where.append("and  b.SCONTRACTCODE<= '" + Info.getContractCodeTo()+"'");
    	}
    	
    	//��λ
		if (Info.getBorrowClientIDFrom() > 0){
			where.append(" and b.nBorrowClientID>=" + Info.getBorrowClientIDFrom());
		}
		if (Info.getBorrowClientIDTo() > 0){
			where.append(" and b.nBorrowClientID<=" + Info.getBorrowClientIDTo());
		}
		
		//�ſ�֪ͨ��
		if (Info.getLLoanPayNoticeCodeFrom()!=null && Info.getLLoanPayNoticeCodeFrom().length()>0){
			where.append(" and e.sCode>=" + Info.getLLoanPayNoticeCodeFrom());
		}
		if (Info.getLLoanPayNoticeCodeTo() !=null && Info.getLLoanPayNoticeCodeTo().length()>0){
			where.append(" and e.sCode<=" + Info.getLLoanPayNoticeCodeTo());
		}

		
		//����������
		
		if (Info.getTsAdjustRateFrom() > 0){
			
			where.append("  and( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate)>=" +DataFormat.formatRate(Info.getTsAdjustRateFrom()));
			
		}
		if  (Info.getTsAdjustRateTo() > 0) {
			
			where.append(" and ( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate) <= " + DataFormat.formatRate(Info.getTsAdjustRateTo()));
		}
		
		//��Ч����
		if (Info.getTsValidateFrom() != null){
			where.append(" and TRUNC(a.DTVALIDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsValidateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsValidateTo() != null){
			where.append(" and TRUNC(a.DTVALIDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsValidateTo()) + "','yyyy-mm-dd') ");
		}
		
		//¼������
		if (Info.getTsInputDateFrom() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsInputDateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsInputDateTo() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsInputDateTo()) + "','yyyy-mm-dd') ");
		}
		

		//��������
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
  		//����״̬
  	  	   if(Info.getLoanStatusTypeIDsList()==null ||Info.getLoanStatusTypeIDsList().equals("")|| Info.getLoanStatusTypeIDsList().trim().equals("-1") ){
  	  		   
  	  		     where.append(" and a.NSTATUSID in (" + loanTypeList.substring(0, loanTypeList.length()-1) + ")");
  	  	    }
  	  	   else{
  	   			
  	   			where.append("and a.NSTATUSID in (" + Info.getLoanStatusTypeIDsList() + ")");
  	   			
  	   		}

		 
		 
		 String sql="select "+select.toString()+" from "+from.toString()+" where "+where.toString();
		 
		 return sql;
		
	}
	
	
	
	

	//����һ����ͬ�ź�һ���ſ�֪ͨ��
	public String getContractInterestSql2(AdjustInterestConditionInfo Info)
	{
		StringBuffer select = new StringBuffer();
		 select.append( " to_number(a.ncontractid) as ncontractid,c.sname as nInputUser,a.BATCHID as batchID, SCONTRACTCODE as sContractCode, lt.name as nSubTypeName,d.name as loanClientName,d1.name as consignClientName,b.nTypeID as nType, a.DTINPUTDATE as dtInputDate, a.nloanpaynoticeid  as nloanpaynoticeid,a.mrate as mRate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid as NadjustStatus,e.MINTERESTRATE as beforeBaseRate,e.MADJUSTRATE as beforeMadjustRate, a.NCURRENCYID as nCurrencyID,a.NOFFICEID as nofficeID, e.MSTAIDADJUSTRATE as beforeMstaidAdjustRate \n ") ;                           //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı���ֶ�  ȫ�ڡ�2010-4-29
    	 // from
		 StringBuffer from = new StringBuffer();
		 from.append( " loan_rateadjustpaycondition a,loan_contractform b ,userinfo c, client_clientinfo d ,client_clientinfo d1 ,loan_payform e,loan_loantypesetting lt");   //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı�  ȫ�ڡ�2010-4-29 
    	 // where 
		 StringBuffer where = new StringBuffer();
		 
    	 
    	 where.append("  lt.id= b.nSubTypeId and e.id=a.NLOANPAYNOTICEID and  a.ncontractid = b.id and c.id=a.ninputuserid and d.id = b.nborrowclientid and d1.id=b.NCONSIGNCLIENTID  and e.NCONTRACTID=a.NCONTRACTID and a.nofficeid="+Info.getM_lOfficeID()+" and a.ncurrencyid= "+Info.getLCurrency());
    	 
    	
   		
   	   
    	 
   	    //��ͬ���
    	if( (Info.getContractCodeFrom()!=null) && (Info.getContractCodeFrom().length()>0)){
    	
    		where.append(" and b.SCONTRACTCODE>= '" + Info.getContractCodeFrom()+"'");
    	}
    	
    	if( (Info.getContractCodeTo()!=null) && (Info.getContractCodeTo().length()>0)){
        	
    		where.append("and  b.SCONTRACTCODE<= '" + Info.getContractCodeTo()+"'");
    	}
    	
    	//�ſ�֪ͨ��
		if (Info.getLLoanPayNoticeCodeFrom()!=null && Info.getLLoanPayNoticeCodeFrom().length()>0){
			where.append(" and e.sCode>=" + Info.getLLoanPayNoticeCodeFrom());
		}
		if (Info.getLLoanPayNoticeCodeTo() !=null && Info.getLLoanPayNoticeCodeTo().length()>0){
			where.append(" and e.sCode<=" + Info.getLLoanPayNoticeCodeTo());
		}
    	
    	
    	//��λ
		if (Info.getBorrowClientIDFrom() > 0){
			where.append(" and b.nBorrowClientID>=" + Info.getBorrowClientIDFrom());
		}
		if (Info.getBorrowClientIDTo() > 0){
			where.append(" and b.nBorrowClientID<=" + Info.getBorrowClientIDTo());
		}
		
		//ί�е�λ
		if (Info.getConsignClientIDFrom() > 0){
			where.append(" and b.NCONSIGNCLIENTID>=" + Info.getConsignClientIDFrom());
		}
		if (Info.getConsignClientIDTo() > 0){
			where.append(" and b.NCONSIGNCLIENTID<=" + Info.getConsignClientIDTo());
		}
		
		//����������
		
		if (Info.getTsAdjustRateFrom() > 0){
			
			where.append("  and( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate)>=" +DataFormat.formatRate(Info.getTsAdjustRateFrom()));
			
		}
		if  (Info.getTsAdjustRateTo() > 0) {
			
			where.append(" and ( a.mrate*(1+a.madjustrate*1/100)+a.mstaidadjustrate) <= " + DataFormat.formatRate(Info.getTsAdjustRateTo()));
		}
		
		//��Ч����
		if (Info.getTsValidateFrom() != null){
			where.append(" and TRUNC(a.DTVALIDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsValidateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsValidateTo() != null){
			where.append(" and TRUNC(a.DTVALIDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsValidateTo()) + "','yyyy-mm-dd') ");
		}
		
		//¼������
		if (Info.getTsInputDateFrom() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)>= To_Date('" + DataFormat.getDateString(Info.getTsInputDateFrom()) + "','yyyy-mm-dd') ");
		}
	
		if (Info.getTsInputDateTo() != null){
			where.append(" and TRUNC(a.DTINPUTDATE)<= To_Date('" + DataFormat.getDateString(Info.getTsInputDateTo()) + "','yyyy-mm-dd') ");
		}
		
		
		//��������
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
  			
  	//����״̬
  	    if(Info.getLoanStatusTypeIDsList()==null ||Info.getLoanStatusTypeIDsList().equals("")|| Info.getLoanStatusTypeIDsList().trim().equals("-1") ){
  		   
  		     where.append(" and a.NSTATUSID in (" + loanTypeList.substring(0, loanTypeList.length()-1) + ")");
  	    }
  	    else{
   			
   			where.append("and a.NSTATUSID in (" + Info.getLoanStatusTypeIDsList() + ")");
   			
   		}
        
		 String sql="select "+select.toString()+" from "+from.toString()+" where "+where.toString();
		 
		 return sql;
	
	}
	
	
	
	
	
	/*//����һ����ͬ��ź����зſ�֪ͨ��
	public void getContractSql(AdjustInterestConditionInfo Info)
	{
		m_sbSelect = new StringBuffer(); 
	   	m_sbSelect.append( "to_number(a.ncontractid) as ncontractid,a.nloanpaynoticeid,a.mrate,a.madjustrate,a.mstaidadjustrate,a.dtvalidate as dtstartdate ,a.nstatusid NadjustStatus  \n ") ;                         //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı���ֶ�  ȫ�ڡ�2010-4-29
	   	// from
	   	m_sbFrom = new StringBuffer();
	   	m_sbFrom.append( "  loan_rateadjustpaycondition a  "); //�˴�Ϊ ���� �� �ſ�֪ͨ�� ��״̬,�������µı�  ȫ�ڡ�2010-4-29
	   	// where 
	   	m_sbWhere = new StringBuffer();
	   	m_sbWhere.append("  a.nContractID=" + Info.getLContractID()+" \n");
	   	m_sbWhere.append(" order by a.id,a.nloanpaynoticeid,a.dtvalidate desc ");               //�˴��޸���������ֶ�  ȫ�ڡ�2010-4-29 
	   	// m_sbWhere.append("  and bb.Status != "+Constant.RecordStatus.INVALID +"\n");
	   	System.out.println(m_sbSelect);
	   	System.out.println(m_sbFrom);
		System.out.println(m_sbWhere);
		//System.out.println ( m_sbSelect +  m_sbFrom + m_sbWhere);
		////////////////////////////������//////////////////////////////////////////////////////////////////////
	}*/
	
	
	/*
	//�����ִͬ�����ʵ�����ѯ
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
	
	
	
	//����ֻ��һ����ͬ���
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
		////////////////////////////������//////////////////////////////////////////////////////////////////////
	}
	
	//���������������ʵ��� add by zwxiao 2010-06-21
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
	
			//�ж��߼�����Ч���ڲ��������Ѿ�������������֮ǰ
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

	//�����������޼ƻ����� add by zwxiao 2010-06-21
	//repayCollΪ���°��żƻ���ļ���
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
	
	//������ִͬ�мƻ��޸ļ�¼  add by zwxiao 2010-06-21
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
			
			// Ҫ����һ��loan_PlanModifyForm��¼
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
			//˵������������ط�Ӧ����Ϊ�ѱ����״̬�����������ҵĹ����в�ѯ����ʾ�������е����ʵ�����ִ�мƻ����������ط���������ʾ��
			//Ӧ��ֻ��ʾ�����ʵ������֣����Խ�״̬���⴦��һ�£���ִ�мƻ�������״̬�޸�Ϊ�����У������ܱ������߶���ʾ
			//���ڵ�������������״̬����Ϊ������
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
		// д��ƻ�
		return lResult;
	}
	
	//ͨ��ID��ѯ������Ϣ  add by zwxiao 2010-06-21
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
	
	//add by zwxiao 2010-07-03 ɾ�����ʵ�����
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
	
	//add by zwxiao 2010-07-03 ɾ��ִ�мƻ�
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
	
	//add by zwxiao 2010-07-03 ɾ��ִ�мƻ���ϸ
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
	
	//add by zwxiao 2010-07-03 ɾ���汾�޸�
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
	
	//��������������ʵ�������Ч�� add by zwxiao 2010-06-21
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
			//�ж��߼�����Ч���ڲ��������Ѿ�������������֮ǰ
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
	 * @function 	�ж��Ƿ��������������ʵ���
	 * @param 		long lContractID(��ͬID)
	 * @return 		lMaxID
	 * @modify		yunchang
	 * @date 		2010-11-08
	 * @function	���ڷ��������Ż�����
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
			 * �ж��߼�����Ч���ڲ��������Ѿ�������������֮ǰ
			 */
			strSQL = " select count(*) from loan_rateadjustpaycondition where 1=1 ";
			if (lContractID > 0)
			{
				strSQL += " and nContractID = " + lContractID;
			}
			strSQL += " and nstatusId not in (0,3)";
			System.out.println("===================================================");
			System.out.println("�ж��߼�����Ч���ڲ������Ѿ�������������֮ǰ strSQL="+strSQL);			
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
			System.out.println("�ж��߼�����Ч���ڲ������Ѿ�������������֮ǰ ����ֵ="+lMaxID);			
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
	
	//�ж��Ƿ�����ǰ�����֪ͨ������Ϊ��ɵ� add by zwxiao 2010-06-21
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
			//�ж��߼�����ǰ�����֪ͨ���������ڽ����Ѿ�������ɵ�
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
	
	//�ж��Ƿ��������������ʵ��� add by zwxiao 2010-08-03
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
			//�����loan_rateadjustpaycondition�µĺ�ͬid�ͷſ�֪ͨ��id
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
			if (lContractID > 0)//ɾ��
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
			else//ɾ���ú�ͬ�����зſ�֪ͨ�����������
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
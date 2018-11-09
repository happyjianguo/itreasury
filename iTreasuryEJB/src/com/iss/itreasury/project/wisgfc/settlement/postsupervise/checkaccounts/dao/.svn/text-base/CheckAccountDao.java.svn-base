package com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.analysis.BaseQueryObject;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckInAccountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class CheckAccountDao extends BaseQueryObject
{
	//public final static int OrderBy_AccountNo = 1;
	//
	String m_sbSelect = null;
	String m_sbFrom = null;
	String m_sbWhere = null;
	String m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public CheckAccountDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	public PageLoader getCheckAccountInfo(CheckAccountInfo conditionInfo) {
		m_sbSelect=" SETT_GLENTRY.ID,NOFFICEID,NCURRENCYID,SSUBJECTCODE,STRANSNO,NTRANSACTIONTYPEID,NTRANSDIRECTION,MAMOUNT,DTEXECUTE," +
				"DTINTERESTSTART,SABSTRACT,SMULTICODE,NINPUTUSERID,NCHECKUSERID,SETT_GLENTRY.NSTATUSID,NGROUP,NTYPE,NPOSTSTATUSID,SBATCHNO ";
		m_sbFrom=" SETT_GLENTRY left join SETT_ACCOUNTCHECK on  SETT_GLENTRY.id = SETT_ACCOUNTCHECK.nglentryid ";
		m_sbWhere=" ssubjectcode in (select ssubjectcode from sett_branch where nstatusid =1) and SETT_GLENTRY.NSTATUSID=3 and  SETT_ACCOUNTCHECK.nglentryid is null ";
		if(!conditionInfo.getDtinputdates().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE >=to_date('"+conditionInfo.getDtinputdates()+"','yyyy-mm-dd') ";
		}
		if(!conditionInfo.getDtinputdatee().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd') ";
		}
		
		m_sbOrderBy=" order by STRANSNO ";
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}
	
	public PageLoader getDoCheckAccountInfo(CheckAccountInfo conditionInfo) {
		m_sbSelect=" SETT_GLENTRY.ID,NOFFICEID,NCURRENCYID,SSUBJECTCODE,STRANSNO,NTRANSACTIONTYPEID,NTRANSDIRECTION,MAMOUNT,DTEXECUTE," +
				"DTINTERESTSTART,SABSTRACT,SMULTICODE,NINPUTUSERID,NCHECKUSERID,SETT_GLENTRY.NSTATUSID,NGROUP,NTYPE,NPOSTSTATUSID,SBATCHNO ";
		m_sbFrom=" SETT_GLENTRY left join SETT_ACCOUNTCHECK on  SETT_GLENTRY.id = SETT_ACCOUNTCHECK.nglentryid ";
		m_sbWhere=" ssubjectcode in (select ssubjectcode from sett_branch where nstatusid =1) and SETT_GLENTRY.NSTATUSID=3 and  SETT_ACCOUNTCHECK.nglentryid is not null ";
		if(!conditionInfo.getDtinputdates().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE >=to_date('"+conditionInfo.getDtinputdates()+"','yyyy-mm-dd') ";
		}
		if(!conditionInfo.getDtinputdatee().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd') ";
		}
		
		m_sbOrderBy=" order by STRANSNO ";
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}
	
	public int addAccountCheck(CheckAccountInfo conditionInfo,String startDate)throws Exception {
		int flag =0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
	 
		String sql = "";
		try
		{
			sql="select a.ID from sett_glentry a left join sett_accountcheck b on a.id=b.nglentryid where b.nglentryid is null and ssubjectcode = (select ssubjectcode from sett_branch where id="+conditionInfo.getLbranchid()+" ) " +
					" and a.NSTATUSID=3 and a.ntransactiontypeid ="+conditionInfo.getNTRANSACTIONTYPEID()+" and a.mamount ="+conditionInfo.getMAMOUNT()+" and to_char(DTEXECUTE,'yyyy-mm-dd') ='"+conditionInfo.getDtinputdates()
					+"' and DTEXECUTE >=to_date('"+startDate+"','yyyy-mm-dd') and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd')";
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				sql ="insert into sett_accountcheck (id ,nglentryid,NSTATUSID) values (SEQ_SETT_ACCOUNTCHECK.NEXTVAL,"+rs.getString("ID")+",1)";
				ps = con.prepareStatement(sql);
				flag =ps.executeUpdate();
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return flag;
	}

	public PageLoader getCheckInAccountInfo(CheckInAccountInfo conditionInfo) {
		m_sbSelect=" a.ID," +
					"a.NOFFICEID," +
					"a.NCURRENCYID," +
					"NTRANSACTIONTYPEID," +
					"DTEXECUTE,STRANSNO," +
					"NTRANSACCOUNTID," +
					"NSUBACCOUNTID," +
					"MAMOUNT," +
					"NTRANSDIRECTION," +
					"NOPPACCOUNTID," +
					"NOPPSUBACCOUNTID," +
					"DTINTERESTSTART," +
					"a.SABSTRACT," +
					"NABSTRACTID," +
					"SETT_ACCOUNTCHECK.NSTATUSID," +
					"NBILLTYPEID," +
					"SBILLNO," +
					"SBANKCHEQUENO," +
					"NGROUP," +
					"NINTERESTBACKFLAG," +
					"NSERIALNO," +
					"NDISCOUNTBILLID," +
					"BUDGETITEMID," +
					"BUDGETSTATUSID," +
					"OPPACCOUNTNO," +
					"OPPACCOUNTNAME," +
					"AMOUNTTYPE," +
					"sett_account.SACCOUNTNO";
		m_sbFrom=" sett_account,sett_transaccountdetail a left join SETT_ACCOUNTCHECK on  a.id = SETT_ACCOUNTCHECK.nglentryid ";
		m_sbWhere=" ntransaccountid in (select id from sett_account where naccounttypeid in (select id from sett_accounttype where  nstatusid=1 and naccountgroupid="+conditionInfo.getAccountKind()+")  and nstatusid=1) " +
				"and  SETT_ACCOUNTCHECK.nglentryid is null and a.nstatusid=3 AND sett_account.ID=a.ntransaccountid and a.NOFFICEID="+conditionInfo.getNOFFICEID()+" and a.NCURRENCYID="+conditionInfo.getNCURRENCYID();
		if(!conditionInfo.getDtinputdates().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE >=to_date('"+conditionInfo.getDtinputdates()+"','yyyy-mm-dd') ";
		}
		if(!conditionInfo.getDtinputdatee().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd') ";
		}
		
		m_sbOrderBy=" order by STRANSNO ";
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckInAccountInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}

	public PageLoader getDoCheckInAccountInfo(CheckInAccountInfo conditionInfo) {
		m_sbSelect=" a.ID," +
					"a.NOFFICEID," +
					"a.NCURRENCYID," +
					"NTRANSACTIONTYPEID," +
					"DTEXECUTE,STRANSNO," +
					"NTRANSACCOUNTID," +
					"NSUBACCOUNTID," +
					"MAMOUNT," +
					"NTRANSDIRECTION," +
					"NOPPACCOUNTID," +
					"NOPPSUBACCOUNTID," +
					"DTINTERESTSTART," +
					"a.SABSTRACT," +
					"NABSTRACTID," +
					"SETT_ACCOUNTCHECK.NSTATUSID," +
					"NBILLTYPEID," +
					"SBILLNO," +
					"SBANKCHEQUENO," +
					"NGROUP," +
					"NINTERESTBACKFLAG," +
					"NSERIALNO," +
					"NDISCOUNTBILLID," +
					"BUDGETITEMID," +
					"BUDGETSTATUSID," +
					"OPPACCOUNTNO," +
					"OPPACCOUNTNAME," +
					"AMOUNTTYPE," +
					"sett_account.SACCOUNTNO";
		m_sbFrom=" sett_account,sett_transaccountdetail a left join SETT_ACCOUNTCHECK on  a.id = SETT_ACCOUNTCHECK.nglentryid ";
		m_sbWhere=" ntransaccountid in (select id from sett_account where naccounttypeid in (select id from sett_accounttype where  nstatusid=1 and naccountgroupid="+conditionInfo.getAccountKind()+")  and nstatusid=1) " +
				"and  SETT_ACCOUNTCHECK.nglentryid is not null and a.nstatusid=3 AND sett_account.ID=a.ntransaccountid and a.NOFFICEID="+conditionInfo.getNOFFICEID()+" and a.NCURRENCYID="+conditionInfo.getNCURRENCYID();
		if(!conditionInfo.getDtinputdates().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE >=to_date('"+conditionInfo.getDtinputdates()+"','yyyy-mm-dd') ";
		}
		if(!conditionInfo.getDtinputdatee().trim().equals("")){
			m_sbWhere=m_sbWhere+" and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd') ";
		}
		
		m_sbOrderBy=" order by STRANSNO ";
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom,
			m_sbSelect,
			m_sbWhere,
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckInAccountInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy + " ");
		return pageLoader;
	}

	public int addInAccountCheck(CheckInAccountInfo conditionInfo,String startDate) throws Exception {
		int flag =0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
	 
		String sql = "";
		try
		{
			sql="select a.ID from sett_account b,sett_transaccountdetail a left join sett_accountcheck c on a.id=c.nglentryid where c.nglentryid is null and b.ID=a.ntransaccountid  and mamount ="+conditionInfo.getMAMOUNT()+" " +
					"and to_char(DTEXECUTE,'yyyy-mm-dd') ='"+conditionInfo.getDtinputdates()+"' and a.NTRANSDIRECTION="+conditionInfo.getNTRANSDIRECTION()+" " +
					"and b.saccountno='"+conditionInfo.getSACCOUNTNO()+"' and a.NOFFICEID="+conditionInfo.getNOFFICEID()+" and a.NCURRENCYID="+conditionInfo.getNCURRENCYID()
					+" and DTEXECUTE >=to_date('"+startDate+"','yyyy-mm-dd') and DTEXECUTE <= to_date('"+conditionInfo.getDtinputdatee()+"','yyyy-mm-dd')";
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				sql ="insert into sett_accountcheck (id ,nglentryid,NSTATUSID) values (SEQ_SETT_ACCOUNTCHECK.NEXTVAL,"+rs.getString("ID")+",1)";
				ps = con.prepareStatement(sql);
				flag =ps.executeUpdate();
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		
		return flag;
	}
}
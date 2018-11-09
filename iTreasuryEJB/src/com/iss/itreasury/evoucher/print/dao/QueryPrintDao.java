package com.iss.itreasury.evoucher.print.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.print.bizlogic.QueryPrintBiz;
import com.iss.itreasury.evoucher.print.dataentity.PrintRecordInfo;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.evoucher.setting.dao.PrintBillSettingDao;
import com.iss.itreasury.evoucher.setting.dataentity.BillrelationSetInfo;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillTemplateInfo;
import com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.print.IPrintTemplate;

import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.print.TemplateSettingXml;
import com.iss.itreasury.settlement.print.dataentity.TemplateSettingXmlInfo;
import com.iss.itreasury.settlement.print.templateinfo.PrintInfoVo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class QueryPrintDao extends VoucherDAO 
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	long userID = -1;
	
	public static String TEMPLET_SEPERATOR = " &&& ";
	public static String CODE_SEPERATORSTART = "<%";
	public static String CODE_SEPERATOREND = "%>";
	
	public QueryPrintDao()
    {
        super("print_billsetting");
    }
    
    public QueryPrintDao(Connection con)
    {
        super("print_billsetting",con);
    }

    public PageLoader QueryPrintTransInfo(QueryPrintConditionInfo info) throws VoucherException 
    {
    	m_sbSelect = new StringBuffer();
    	m_sbFrom = new StringBuffer();
    	m_sbWhere = new StringBuffer();
    	m_sbOrderBy = new StringBuffer();
    	
    	m_sbSelect.append(" a.id ID,a.officeid OfficeID,a.currencyid CurrencyID,a.transno TransNo," +
    					  " a.operationtypeid OperationTypeID, " +
    					  " a.transactiontypeid TransactionTypeID,a.statusid StatusID,a.execute dtTrans," +
    					  " a.inputuserid InputUserID,a.abstract Abstract,a.payaccountid PayAccountID,a.payaccountno," +
    					  " a.receiveaccountid ReceiveAccountID,a.receiveaccountno,a.receiveamount transAmount ");
    	
    	m_sbFrom.append(" SETT_VTRANSACTION a ");
    	
    	//m_sbWhere.append(" (a.statusid=2 or a.statusid=8)");
    	m_sbWhere.append(" (a.statusid not in("+SETTConstant.TransactionStatus.DELETED+","
    			//+SETTConstant.TransactionStatus.APPROVAL + ","
    			+SETTConstant.TransactionStatus.TEMPSAVE+","+SETTConstant.TransactionStatus.REFUSE+"))");
    	
    	//编号为空的不能查出来
    	m_sbWhere.append(" and a.transno is not null ");
    	
    	//根据页面条件增加查询条件
    	if(info.getTransactionTypeIDs() != null && info.getTransactionTypeIDs().length() > 0) {
    		//m_sbWhere.append(" and a.transactiontypeid in (" + info.getTransactionTypeIDs() + ")");
    		m_sbWhere.append(" and ( a.transactiontypeid in (" + info.getTransactionTypeIDs() + " ) or a.OperationTypeID in (" + info.getTransactionTypeIDs() + " ) ) ");
    	}
    	if(info.getClientIDStart() > 0) {
    		m_sbWhere.append(" and a.payclientid >= " + info.getClientIDStart());
    	}
    	if(info.getClientIDEnd() > 0) {
    		m_sbWhere.append(" and a.payclientid <= " + info.getClientIDEnd());
    	}
    	if(info.getAmountStart()>0) {
    		m_sbWhere.append(" and a.receiveamount >= " + info.getAmountStart());
    	}
    	if(info.getAmountEnd()>0) {
    		m_sbWhere.append(" and a.receiveamount <= " + info.getAmountEnd());
    	}
    	if(info.getTransNoStart() != null && info.getTransNoStart().length() > 0) {
    		m_sbWhere.append(" and a.transno >= '" + info.getTransNoStart() + "'");
    	}
    	if(info.getTransNoEnd() != null && info.getTransNoEnd().length() > 0) {
    		m_sbWhere.append(" and a.transno <= '" + info.getTransNoEnd() + "'");
    	}
    	if(info.getDtTransStart() != null) {
    		m_sbWhere.append(" and a.execute >= to_date('" + DataFormat.getDateString(info.getDtTransStart()) + "','yyyy-mm-dd')");
    	}
    	if(info.getDtTransEnd() != null) {
    		m_sbWhere.append(" and a.execute <= to_date('" + DataFormat.getDateString(info.getDtTransEnd()) + "','yyyy-mm-dd')");
    	}
    	if(info.getInputuserID() > 0 || info.getInputuserID() == -100) {
    		m_sbWhere.append(" and a.inputuserid = " + info.getInputuserID());
    	}
    	if(info.getCheckUserID() > 0 || info.getCheckUserID() == -101) {
    		m_sbWhere.append(" and a.checkuserid = " + info.getCheckUserID());
    	}
    	//modify by xwhe 2008-09-25
    	if(info.getTransactionStatusID() > 0) {
    		m_sbWhere.append(" and a.statusid = " + info.getTransactionStatusID());
    	}
    	if(info.getOfficeID() > 0)
    	{
    		m_sbWhere.append(" and a.officeid = " + info.getOfficeID());
    	}
    	if(info.getCurrencyID() > 0)
    	{
    		m_sbWhere.append(" and a.currencyid = " + info.getCurrencyID());
    	}
    	
    	//add by minzhao 2009.3.20
    	if(info.getLClientIDStartIn() > 0)
    	{
    		m_sbWhere.append(" and a.ReceiveClientID >= " + info.getLClientIDStartIn());
    	}
    	if(info.getLClientIDEndIn() > 0)
    	{
    		m_sbWhere.append(" and a.ReceiveClientID <= " + info.getLClientIDEndIn());
    	}
    	
    	if(info.getSigner()>0)
		{
			m_sbWhere.append(" and nvl(a.PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			m_sbWhere.append(" and nvl(a.ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
    	//modify by xwhe 2009-01-06 
    	if(info.getLIsClient() > 0)
    	{
    		m_sbOrderBy.append(" order by a.execute,a.payclientid, a.transno ");
    	}
    	else
    	{
    	   m_sbOrderBy.append(" order by a.execute, a.transno ");
    	}
    	log.print("查询sql：select " + m_sbSelect.toString() + " from" + m_sbFrom.toString() + " where" + m_sbWhere.toString() + m_sbOrderBy.toString());
    	
    	PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
    	pageLoader.initPageLoader(
    			new AppContext(),
    			m_sbFrom.toString(),
    			m_sbSelect.toString(),
    			m_sbWhere.toString(),
    			(int) Constant.PageControl.CODE_PAGELINECOUNT,
    			"com.iss.itreasury.evoucher.print.dataentity.QueryPrintResultInfo",
    			null);
    	pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
    	return pageLoader;
    }
    
    /**
     * 查找待签章业务类型
     * @param info
     * @return
     * @throws VoucherException
     */
    public PageLoader QueryForEndorseTransInfo (QueryPrintConditionInfo info) throws VoucherException 
    {
    	m_sbSelect = new StringBuffer();
    	m_sbFrom = new StringBuffer();
    	m_sbWhere = new StringBuffer();
    	m_sbOrderBy = new StringBuffer();
    	
    	m_sbSelect.append(" a.id,a.officeid,a.currencyid,a.transno,a.transactiontypeid,a.statusid,a.execute dtTrans," +
    			"a.inputuserid,a.abstract,a.payaccountid,a.payaccountno,a.receiveaccountid,a.receiveaccountno," +
    			"a.receiveamount transAmount");
    	m_sbFrom.append(" sett_vtransaction a,print_printapply b,print_billtemplate c");
    	m_sbWhere.append(" a.id=b.nprintcontentid and b.ntempid=c.id");
    	m_sbWhere.append(" and b.nstatusid=" + VOUConstant.VoucherStatus.APPROVED);
    	m_sbWhere.append(" and b.ndeptid=" + VOUConstant.IsEbankApply.YES);
    	m_sbWhere.append(" and c.nisneedseal=" + VOUConstant.IsChapter.YES);
    	
    	// 根据页面条件增加查询条件
    	if(info.getTransactionTypeIDs() != null && info.getTransactionTypeIDs().length() > 0) {
    		m_sbWhere.append(" and a.transactiontypeid in (" + info.getTransactionTypeIDs() + ")");
    	}
    	if(info.getClientIDStart() > 0) {
    		m_sbWhere.append(" and a.payclientid >= " + info.getClientIDStart());
    	}
    	if(info.getClientIDEnd() > 0) {
    		m_sbWhere.append(" and a.payclientid <= " + info.getClientIDEnd());
    	}
    	if(info.getAmountStart()>0) {
    		m_sbWhere.append(" and a.receiveamount >= " + info.getAmountStart());
    	}
    	if(info.getAmountEnd()>0) {
    		m_sbWhere.append(" and a.receiveamount <= " + info.getAmountEnd());
    	}
    	if(info.getTransNoStart() != null && info.getTransNoStart().length() > 0) {
    		m_sbWhere.append(" and a.transno >= '" + info.getTransNoStart() + "'");
    	}
    	if(info.getTransNoEnd() != null && info.getTransNoEnd().length() > 0) {
    		m_sbWhere.append(" and a.transno <= '" + info.getTransNoEnd() + "'");
    	}
    	if(info.getDtTransStart() != null) {
    		m_sbWhere.append(" and a.execute >= to_date('" + DataFormat.getDateString(info.getDtTransStart()) + "','yyyy-mm-dd')");
    	}
    	if(info.getDtTransEnd() != null) {
    		m_sbWhere.append(" and a.execute <= to_date('" + DataFormat.getDateString(info.getDtTransEnd()) + "','yyyy-mm-dd')");
    	}
    	if(info.getInputuserID() > 0) {
    		m_sbWhere.append(" and a.inputuserid = " + info.getInputuserID());
    	}
    	
    	m_sbWhere.append(" group by a.id,a.officeid,a.currencyid,a.transno,a.transactiontypeid,a.statusid,a.execute," +
    			"a.inputuserid,a.abstract,a.payaccountid,a.payaccountno,a.receiveaccountid,a.receiveaccountno," +
    			"a.receiveamount");
    	
    	m_sbOrderBy.append(" order by a.id desc");
    	
    	System.out.println("****签章sql："+"select " + m_sbSelect.toString() + " from " + m_sbFrom.toString()
    			+ " where " + m_sbWhere.toString() + m_sbOrderBy.toString());
    	
    	PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
    	
    	pageLoader.initPageLoader(
    			new AppContext(),
    			m_sbFrom.toString(),
    			m_sbSelect.toString(),
    			m_sbWhere.toString(),
    			(int) Constant.PageControl.CODE_PAGELINECOUNT,
    			"com.iss.itreasury.evoucher.print.dataentity.QueryPrintResultInfo",
    			null);
    	
    	return pageLoader;
    }
    
    public Collection getPrintOptionByTransID (String TransIDs , long lDeptID, long lCurrencyID , long lOfficeID) throws Exception 
    {
    	Vector vector = new Vector();
    	String strSQL = "";
    	BillrelationSetInfo info = null;
    	
    	try{
			initDAO();
			
			strSQL = "select min(a.id) id,min(a.ndeptid) ndeptid,a.nbillid,a.ntempid,min(a.nmaxprint) nmaxprint," +
					" min(b.sname) sname,min(c.sdescription) sdescription,min(c.ncoupletno) ncoupletno " +
					" from print_billrelation a,print_billsetting b,print_billtemplate c,SETT_VTRANSACTION d " +
					" where a.nbillid = b.id and a.ntempid=c.id and ( a.ntransactiontypeid=d.transactiontypeid or a.ntransactiontypeid = d.operationtypeid ) ";
			strSQL += " and d.id in (" + TransIDs + ")";
			strSQL += " and a.nofficeid=" + lOfficeID;
			strSQL += " and a.ncurrency=" + lCurrencyID;
			strSQL += " and a.ndeptid=" + lDeptID;
			strSQL += " and d.statusid not in ( "   
				+SETTConstant.TransactionStatus.DELETED+","   
				+SETTConstant.TransactionStatus.TEMPSAVE + ","
    			+SETTConstant.TransactionStatus.REFUSE+" ) ";
			strSQL += " group by a.ntempid,a.nbillid";
			
			//log.print("查询打印选项sql："+strSQL);
			//System.out.println("查询打印选项sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new BillrelationSetInfo();
				info.setId(transRS.getLong("id"));
				info.setRelationdeptid(transRS.getLong("ndeptid"));
				info.setRelationmax(transRS.getLong("nmaxprint"));
				info.setSetid(transRS.getLong("nbillid"));
				info.setSetname(transRS.getString("sname"));
				info.setTemplateid(transRS.getLong("ntempid"));
				info.setTemplatename(transRS.getString("sdescription"));
				info.setTemplateno(transRS.getLong("ncoupletno"));				
				
				vector.add(info);
			}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	return vector;
    }
    
    //修改查找业务关联模板 boxu add 2007-9-5
    public Collection getPrintOption(PrintXMLTimeInfo printInfo) throws Exception 
    {
    	Vector vector = new Vector();
    	String strSQL = "";
    	BillrelationSetInfo info = null;
    	
    	try{
			initDAO();
			
			if (printInfo.getModule() == Constant.ModuleType.SETTLEMENT)
			{
				strSQL = " select a.* ";
				strSQL += " from print_billrelation a, SETT_VTRANSACTION d ";
				strSQL += " where ( a.ntransactiontypeid = d.transactiontypeid or a.ntransactiontypeid = d.operationtypeid ) ";
				strSQL += " and d.id in (" + printInfo.getTransIDs() + ")";
				strSQL += " and a.nofficeid=" + printInfo.getOfficeID();
				strSQL += " and a.ncurrency=" + printInfo.getCurrencyID();
				strSQL += " and a.ndeptid=" + printInfo.getDeptID();
				strSQL += " and d.statusid not in ( "
													+ SETTConstant.TransactionStatus.DELETED +","
													+ SETTConstant.TransactionStatus.TEMPSAVE + ","
													+ SETTConstant.TransactionStatus.REFUSE +" ) ";
				
				if(printInfo.getOpretionType() > 0)
				{
					strSQL += " and a.ntransactiontypeid=" + printInfo.getOpretionType();
					strSQL += " and d.operationtypeid=" + printInfo.getOpretionType();
				}
				else
				{
					strSQL += " and a.ntransactiontypeid=" + printInfo.getTransTypeID();
					strSQL += " and d.transactiontypeid=" + printInfo.getTransTypeID();
				}
				
				strSQL += " order by a.stempname ";
			}
			
			//System.out.println("查询打印选项sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new BillrelationSetInfo();
				
				info.setId(transRS.getLong("id"));
				info.setRelationdeptid(transRS.getLong("ndeptid"));
				info.setRelationmax(transRS.getLong("nmaxprint"));
				info.setSetname(transRS.getString("sbilltypename"));
				info.setTemplatename(transRS.getString("stempName"));			
				info.setModuleid(transRS.getLong("nmoduleid"));
				
				vector.add(info);
			}
    	}
    	catch( Exception ex ) 
    	{
			ex.printStackTrace();
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return vector;
    }
    
    public Collection getPrintOptionsByTransID (String TransIDs, long lDeptID, long lCurrencyID, long lOfficeID, long lModuleID) throws Exception 
    {
    	Vector vector = new Vector();
    	String strSQL = "";
    	BillrelationSetInfo info = null;
    	
    	try{
			initDAO();
			
			if (lModuleID == Constant.ModuleType.SETTLEMENT)
			{
				strSQL = " select a.* ";
				strSQL += " from print_billrelation a, SETT_VTRANSACTION d ";
				strSQL += " where ( a.ntransactiontypeid = d.transactiontypeid or a.ntransactiontypeid = d.operationtypeid ) ";
				strSQL += " and d.id in (" + TransIDs + ")";
				strSQL += " and a.nofficeid=" + lOfficeID;
				strSQL += " and a.ncurrency=" + lCurrencyID;
				strSQL += " and a.ndeptid=" + lDeptID;
				strSQL += " and d.statusid not in ( "
													+ SETTConstant.TransactionStatus.DELETED +","   
													+ SETTConstant.TransactionStatus.TEMPSAVE + ","
													+ SETTConstant.TransactionStatus.REFUSE +" ) ";
				
				strSQL += " order by a.sbilltypename desc ";
			}
			
			//log.print("查询打印选项sql："+strSQL);
			//System.out.println("查询打印选项sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new BillrelationSetInfo();
				info.setId(transRS.getLong("id"));
				info.setRelationdeptid(transRS.getLong("ndeptid"));
				info.setRelationmax(transRS.getLong("nmaxprint"));
				//info.setSetid(transRS.getLong("nbillid"));
				info.setSetname(transRS.getString("sbilltypename"));
				//info.setTemplateid(transRS.getLong("ntempid"));
				info.setTemplatename(transRS.getString("stempName"));
				//info.setTemplateno(transRS.getLong("ncoupletno"));				
				info.setModuleid(transRS.getLong("nmoduleid"));
				
				vector.add(info);
			}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	return vector;
    }
    
	 //addby zyyao 批量打印 2007-6-28
    public Collection getPrintOptionByTransIDmany (String TransIDs , long lDeptID, long lCurrencyID , long lOfficeID) throws Exception 
    {
    	Vector vector = new Vector();
    	String strSQL = "";
    	BillrelationSetInfo info = null;
    	
    	try{
			initDAO();
			
			strSQL = "select min(a.id) id,min(a.ndeptid) ndeptid,a.nbillid,a.ntempid,min(e.nmaxprint) nmaxprint," +
					" min(b.sname) sname,min(c.sdescription) sdescription,min(c.ncoupletno) ncoupletno " +
					" from print_manybillrelation a,print_billsetting b,print_billtemplate c,SETT_VTRANSACTION d,print_billrelation e " +
					" where a.nbillid = b.id and a.ntempid=c.id and ( a.ntransactiontypeid=d.transactiontypeid or a.ntransactiontypeid = d.operationtypeid ) ";
			strSQL += " and e.nbillid = b.id and e.ntempid=c.id and ( e.ntransactiontypeid=d.transactiontypeid or e.ntransactiontypeid = d.operationtypeid) ";
			strSQL += " and d.id in (" + TransIDs + ")";
			strSQL += " and a.nofficeid=" + lOfficeID;
			strSQL += " and a.ncurrency=" + lCurrencyID;
			strSQL += " and a.ndeptid=" + lDeptID;
			strSQL += " and e.nofficeid=" + lOfficeID;
			strSQL += " and e.ncurrency=" + lCurrencyID;
			strSQL += " and e.ndeptid=" + lDeptID;
			strSQL += " and d.statusid not in ( "   
				+SETTConstant.TransactionStatus.DELETED+","   
				+SETTConstant.TransactionStatus.TEMPSAVE + ","
    			+SETTConstant.TransactionStatus.REFUSE+" ) ";
			strSQL += " group by a.ntempid,a.nbillid";
			
			//log.print("查询打印选项sql："+strSQL);
			//System.out.println("查询打印选项sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				info = new BillrelationSetInfo();
				info.setId(transRS.getLong("id"));
				info.setRelationdeptid(transRS.getLong("ndeptid"));
				info.setRelationmax(transRS.getLong("nmaxprint"));
				info.setSetid(transRS.getLong("nbillid"));
				info.setSetname(transRS.getString("sname"));
				info.setTemplateid(transRS.getLong("ntempid"));
				info.setTemplatename(transRS.getString("sdescription"));
				info.setTemplateno(transRS.getLong("ncoupletno"));				
				
				vector.add(info);
			}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	return vector;
    }
    
    
    public Collection getPrintTemplateContent (String strTransNos,String transIDs , String[] templateIDs, int deptID, long lCurrencyID , long lOfficeID)throws Exception 
    {
    	Collection resultPrintOption = new ArrayList();
    	int index = -1;
    	//int EIndex = -1;
    	int num = 1;
    	try {
    		// 取得交易id数组大小
    		index = transIDs.indexOf(",");
    		while (index >= 0) {
    			num ++;
	    		index = transIDs.indexOf(",",index+1);
	    	}
    		
    		// 将交易id转换为数组集合
    		long[] lTransIDs = new long[num];
    		String[] TransNos = new String[num];
    		int i = 0;
    		index = transIDs.indexOf(",");
    		while(index >= 0) {
    			lTransIDs[i++] = Long.valueOf(transIDs.substring(0,index)).longValue();
    			transIDs = transIDs.substring(index+1);
    			index = transIDs.indexOf(",");
    		}
    		lTransIDs[lTransIDs.length-1] = Long.valueOf(transIDs).longValue();
    		
    		index = -1;
    		i = 0;
    		index = strTransNos.indexOf(",");
    		while(index >= 0)
    		{
    			TransNos[i++] = strTransNos.substring(0,index);
    			strTransNos = strTransNos.substring(index+1);
    			index = strTransNos.indexOf(",");
    		}
    		TransNos[TransNos.length-1] = strTransNos;
    		
    		// 将所选交易、模版类型一一对应得到所以打印的内容
    		// 如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		for(int k=0;k<lTransIDs.length;k++)
    		{
    			Collection coll = getPrintOptionByTransID(String.valueOf(lTransIDs[k]),deptID,lCurrencyID,lOfficeID);
    			Iterator iter = null;
    			BillrelationSetInfo info = null;
    			String strTemp = "";
    			if(coll.size() > 0)
    			{
    				long lTransactionType=getTransTypeIDbyTransID(lTransIDs[k]);
    				ManufacturePrintInfo manufacturePrintInfo = new ManufacturePrintInfo();
        			PrintInfo printInfo = manufacturePrintInfo.getPrintInfo(lTransIDs[k],lTransactionType);
        			System.out.println("---------------------------------------transID:"+lTransIDs[k]);
    				iter = coll.iterator();
    				while(iter.hasNext()) {
    					//System.out.println("---------------- here ~~~~~~");
    					info = (BillrelationSetInfo)iter.next();
    					for(int m = 0; m < templateIDs.length; m++){
    						long lBillID = -1;
    						if(info.getTemplateid() == Long.valueOf(templateIDs[m]).longValue()) {
    							
    							PrintBillSettingDao dao = new PrintBillSettingDao();
    							lBillID = dao.findBillIDByTemplateID(Long.valueOf(templateIDs[m]).longValue());
    							
    							long lMaxPrintTime = getMaxPrintTime(lTransIDs[k], TransNos[k], lBillID, Long.parseLong(templateIDs[m]));
    							
    							long lPrintTime = getPrintTime(lTransIDs[k], lBillID, Long.parseLong(templateIDs[m]));
    							
    							//boolean isUseableApply = isUseablePrintApply(lTransIDs[k], TransNos[k], Long.parseLong(templateIDs[m]), lBillID);
    							
    							/**  
    							 * 该对应单据已打印次数小于最大可打印次数，可打印
    							 * 若已打印次数超过最大可打印次数，且申请再次打印通过，可打印
    							 **/
    							if ((lPrintTime<lMaxPrintTime) || (lPrintTime > 0 && lPrintTime>=lMaxPrintTime 
    									//&& isUseableApply==true
    									))
    							{
    								/**  针对每个template合并模版内容  **/
        							strTemp = printTemplate(printInfo,info.getTemplateid());
        							
        							/**  将打印内容与模版合并后返回  **/
        							resultPrintOption.add(strTemp);
        							
        							/**  添加打印记录  **/
        							PrintRecordInfo printRecordInfo = new PrintRecordInfo();
        							System.out.println("templateIDs："+templateIDs[m]);
        							System.out.println("lTransIDs："+lTransIDs[k]);
        							
        							printRecordInfo.setNCurrencyID(lCurrencyID);
        							printRecordInfo.setNOfficeID(lOfficeID);
        							printRecordInfo.setNBillID(lBillID);
        							printRecordInfo.setNDeptID(deptID);
        							printRecordInfo.setNTransID(lTransIDs[k]);
        							printRecordInfo.setNTempID(Long.valueOf(templateIDs[m]).longValue());
        							printRecordInfo.setNTransTypeID(getTransTypeIDbyTransID(lTransIDs[k]));
        							
        							savePrintRecord(printRecordInfo);
        							
        							/**  更新打印申请记录  **/
        							//if(isUseableApply==true)
        							//{
        							//	updatePrintApply(lTransIDs[k], TransNos[k], Long.parseLong(templateIDs[m]), lBillID);
        							//}
    							}    							
    							else
    							{
    								//log.print("交易"+lTransIDs[k]+"对应"+lBillID+"单据"+templateIDs[m]+"模版已达到最大打印次数");
    								
    								//System.out.println("交易"+lTransIDs[k]+"对应"+lBillID+"单据"+templateIDs[m]+"模版已达到最大打印次数");
    								throw new IException("该交易对应单据模版已达到最大打印次数");
    							}
    						}
    					}
    				}    				
    			}  			
    		}

    	}
    	catch (IException ie)
    	{
    		throw new IException(ie.getMessage());
    	}
    	catch (Exception e) {
    		log.error(e.getMessage());
    		//System.out.println(e.toString());
    	}
    	return resultPrintOption;
    }
    
	//2007-7-17 打印修改 Boxu
    public boolean validatePrint(PrintXMLTimeInfo printXMLInfo) throws Exception 
    {
    	String billName[] = printXMLInfo.getBillName();
    	long nameSize = -1;
    	long tempSize = -1;
    	nameSize = billName.length;
    	
    	try 
    	{
    		//如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		for(int i=0 ; i<billName.length ; i++)
    		{
				long lMaxPrintTime = this.getMaxPrintXMLTime(printXMLInfo, billName[i]);
				
				long lPrintTime = getPrintXMLTime(printXMLInfo, billName[i]);
				
				boolean isUseableApply = isUseablePrintApply(printXMLInfo, billName[i]);
				
				/**  
				 * 该对应单据已打印次数小于最大可打印次数，可打印
				 * 若已打印次数超过最大可打印次数，且申请再次打印通过，可打印
				 **/
				if ((lPrintTime<lMaxPrintTime) || (lPrintTime > 0 && lPrintTime>=lMaxPrintTime && isUseableApply==true))
				{
					/**  添加打印记录  **/
					PrintRecordInfo printRecordInfo = new PrintRecordInfo();
					
					printRecordInfo.setNCurrencyID(printXMLInfo.getCurrencyID());
					printRecordInfo.setNOfficeID(printXMLInfo.getOfficeID());
					//printRecordInfo.setNBillID(lBillID);
					printRecordInfo.setNDeptID(printXMLInfo.getDeptID());
					printRecordInfo.setNTransID(printXMLInfo.getId());
					//printRecordInfo.setNTempID(Long.valueOf(templateIDs[m]).longValue());
					printRecordInfo.setNTransTypeID(printXMLInfo.getOpretionType());
					printRecordInfo.setTempName(billName[i]);
					printRecordInfo.setModuleId(printXMLInfo.getModule());
					
					this.savePrintRecord(printRecordInfo);
					
					/**  更新打印申请记录  **/
					if(isUseableApply==true)
					{
						PrintApplyInfo printApply = new PrintApplyInfo();
						printApply.setNOfficeID(printXMLInfo.getOfficeID());
						printApply.setNCurrencyID(printXMLInfo.getCurrencyID());
						printApply.setNDeptID(printXMLInfo.getDeptID());
						printApply.setModuleId(printXMLInfo.getModule());
						printApply.setLTransTypeID(printXMLInfo.getOpretionType());
						printApply.setStrBillName(billName[i]);
						printApply.setNTransID(printXMLInfo.getId());
						printApply.setNTransNo(printXMLInfo.getTransNo());
						
						this.updatePrintApply(printApply);
						this.updateebankPrintApply(printApply);
					}
					
					tempSize = i+1;
				}
				//else
				//{
				//	blreturn = false;
				//	throw new IException("该交易对应单据模版已达到最大打印次数");
				//}
    		}
    	}
    	catch (IException ie)
    	{
    		throw new IException(ie.getMessage());
    	}
    	catch (Exception e) 
    	{
    		log.error(e.getMessage());
    	}
    	
    	return nameSize==tempSize?true:false;
    }
    //jzw 2010-05-21 添加签章打印信息保存
    private void validatePrintSignature(PrintRecordInfo printRecordInfo){

    	String strSQL = "";
    	long lPrintRecord = -1;
    	long lID = -1;
    	int index = 0;
    	try{
			initDAO();
			
			/**  检验该交易此种单据打印次数，如果为第一次打印则新增打印记录，否则更新打印记录  **/
			strSQL = " select count(*) printcount from sett_signature_printinfo ";
//			strSQL += " where STRANSNO=" + printRecordInfo.getNTransID();   //交易编号
			strSQL += " where STRANSNO='" + printRecordInfo.getNTransNO() + "' ";   //交易编号
			strSQL += " and NCURRENCYID=" + printRecordInfo.getNCurrencyID();//币种ID
			strSQL += " and nofficeid=" + printRecordInfo.getNOfficeID();//办事处ID
			strSQL += " and NBILLTYPEID=" + printRecordInfo.getNTransTypeID(); //交易单据类型编号
			
			log.print("查询打印次数sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				lPrintRecord = transRS.getLong("printcount");
			}
			
			if (transPS != null) {
				transPS.close();
				transPS = null;
			}
			if (transRS != null) {
				transRS.close();
				transRS = null;
			}
			
//			/**  非第一次打印  **/
//			if( lPrintRecord > 0 ) 
//			{
//				/**  更新打印次数  **/
//				strSQL =  " update sett_signature_printinfo set DTPRINTDATE = sysdate,nprintcount= "+String.valueOf(lPrintRecord+1);
//				strSQL += " where STRANSNO = " + printRecordInfo.getNTransID();
//				strSQL += " and NCURRENCYID = " + printRecordInfo.getNCurrencyID();
//				strSQL += " and nofficeid = " + printRecordInfo.getNOfficeID();
//				strSQL += " and NBILLTYPEID=" + printRecordInfo.getNTransTypeID();
//				
//				transPS = transConn.prepareStatement(strSQL);
//				transPS.executeUpdate();
//			}
//			/**  第一次打印  **/
//			else {
				lID = this.getPrint_printRecordID();
				
				/**  新增打印记录  **/
				strSQL =  " insert into sett_signature_printinfo ";
				strSQL += " ( ";
				strSQL += " ID, NOFFICEID, NCURRENCYID, STRANSNO ";
				strSQL += " , DTPRINTDATE, NBILLTYPEID,PRINTUSER,NCLIENTID,DTINPUTDATE,INPUTUSERID,NPRINTCOUNT";
				strSQL += " ) ";
				strSQL += " values (?, ?, ?, ?, sysdate, ?, ?, ?, ?, ?,?) ";

				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(++index , lID);
				transPS.setLong(++index , printRecordInfo.getNOfficeID());
				transPS.setLong(++index , printRecordInfo.getNCurrencyID());
				transPS.setString(++index , printRecordInfo.getNTransNO());
				transPS.setLong(++index , printRecordInfo.getNTransTypeID());
				transPS.setLong(++index , printRecordInfo.getPrintUser());
				transPS.setLong(++index , printRecordInfo.getNClientID());
				transPS.setString(++index , printRecordInfo.getInputDate());
				transPS.setLong(++index , printRecordInfo.getInputUserID());
				transPS.setLong(++index , lPrintRecord+1);
				
				transPS.executeUpdate();
//			}
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
			}	
		}
    
    }
    //2007-8-9 打印修改 Boxu 增加一个方法明确提示信息  
    public boolean validatePrintTWO(PrintXMLTimeInfo printXMLInfo) throws Exception 
    {
    	String billName[] = printXMLInfo.getBillName();
    	boolean retuenbl = true;
    	TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
    	String isSignature = "0";    	
    	try 
    	{
    		//如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		for(int i=0 ; i<billName.length ; i++)
    		{
				long lMaxPrintTime = this.getMaxPrintXMLTime(printXMLInfo, billName[i]);
				
				long lPrintTime = getPrintXMLTime(printXMLInfo, billName[i]);
				
				boolean isUseableApply = isUseablePrintApply(printXMLInfo, billName[i]);
				
				//获得XML的别名
				String strXMLName = this.getPrintXMLName(printXMLInfo, billName[i]);
				
				/**  
				 * 该对应单据已打印次数小于最大可打印次数，可打印
				 * 若已打印次数超过最大可打印次数，且申请再次打印通过，可打印
				 **/
				if ((lPrintTime<lMaxPrintTime) || (lPrintTime > 0 && lPrintTime>=lMaxPrintTime && isUseableApply==true))
				{
					/**  添加打印记录  **/
					PrintRecordInfo printRecordInfo = new PrintRecordInfo();
					isSignature = transEntity.isSignature(printXMLInfo.getClientID(),printXMLInfo.getTransTypeID(),printXMLInfo.getOfficeID(),printXMLInfo.getCurrencyID(),printXMLInfo.getDeptID(),billName[i]);
					printXMLInfo.setIsSave(isSignature);
					printRecordInfo.setNCurrencyID(printXMLInfo.getCurrencyID());
					printRecordInfo.setNOfficeID(printXMLInfo.getOfficeID());
					printRecordInfo.setNDeptID(printXMLInfo.getDeptID());
					printRecordInfo.setNTransID(printXMLInfo.getId());
					printRecordInfo.setPrintUser(printXMLInfo.getPrintUser());
					printRecordInfo.setIsSave(printXMLInfo.getIsSave());
					printRecordInfo.setInputDate(printXMLInfo.getInputDate());
					printRecordInfo.setInputUserID(printXMLInfo.getInputUserID());
					printRecordInfo.setNClientID(printXMLInfo.getClientID());
					printRecordInfo.setNTransNO(printXMLInfo.getTransNo());
					
					if(printXMLInfo.getOpretionType() > 0)
					{
						printRecordInfo.setNTransTypeID(printXMLInfo.getOpretionType());
					}
					else
					{
						printRecordInfo.setNTransTypeID(printXMLInfo.getTransTypeID());
					}
					
					printRecordInfo.setTempName(billName[i]);
					printRecordInfo.setModuleId(printXMLInfo.getModule());
					
					this.savePrintRecord(printRecordInfo);
					
					/**  更新打印申请记录  **/
					if(isUseableApply==true)
					{
						PrintApplyInfo printApply = new PrintApplyInfo();
						printApply.setNOfficeID(printXMLInfo.getOfficeID());
						printApply.setNCurrencyID(printXMLInfo.getCurrencyID());
						printApply.setNDeptID(printXMLInfo.getDeptID());
						printApply.setModuleId(printXMLInfo.getModule());
						
						if(printXMLInfo.getOpretionType() > 0)
						{
							printApply.setLTransTypeID(printXMLInfo.getOpretionType());
						}
						else
						{
							printApply.setLTransTypeID(printXMLInfo.getTransTypeID());
						}
						
						printApply.setStrBillName(billName[i]);
						printApply.setNTransID(printXMLInfo.getId());
						printApply.setNTransNo(printXMLInfo.getTransNo());
						
						this.updatePrintApply(printApply);
						this.updateebankPrintApply(printApply);
					}
				}
				else
				{
					retuenbl = false;
					throw new IException("该交易"+ printXMLInfo.getTransNo() +"对应单据模版["+ strXMLName +"]已达到最大打印次数");
				}
    		}
    	}
    	catch (IException ie)
    	{
    		throw new IException(ie.getMessage());
    	}
    	catch (Exception e) 
    	{
    		log.error(e.getMessage());
    	}
    	
    	return retuenbl;
    }
    
    //2010-08-02  张雷 添加   不返回boolean型，返回打印次数
    public String validatePrintTHREE(PrintXMLTimeInfo printXMLInfo) throws Exception 
    {
    	String billName[] = printXMLInfo.getBillName();
    	String retuenbl = "0_cant";
    	TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
    	String isSignature = "0";    	
    	try 
    	{
    		//如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		for(int i=0 ; i<billName.length ; i++)
    		{
				long lMaxPrintTime = this.getMaxPrintXMLTime(printXMLInfo, billName[i]);
				
				long lPrintTime = getPrintXMLTime(printXMLInfo, billName[i]);
				boolean isUseableApply = isUseablePrintApply(printXMLInfo, billName[i]);
				//获得XML的别名
				String strXMLName = this.getPrintXMLName(printXMLInfo, billName[i]);
				
				/**  
				 * 该对应单据已打印次数小于最大可打印次数，可打印
				 * 若已打印次数超过最大可打印次数，且申请再次打印通过，可打印
				 **/
				if ((lPrintTime<lMaxPrintTime) || (lPrintTime > 0 && lPrintTime>=lMaxPrintTime && isUseableApply==true))
				{
					retuenbl = String.valueOf(lPrintTime)+"_can";
					/**  添加打印记录  **/
					PrintRecordInfo printRecordInfo = new PrintRecordInfo();
					isSignature = transEntity.isSignature(printXMLInfo.getClientID(),printXMLInfo.getTransTypeID(),printXMLInfo.getOfficeID(),printXMLInfo.getCurrencyID(),printXMLInfo.getDeptID(),billName[i]);
					printXMLInfo.setIsSave(isSignature);
					printRecordInfo.setNCurrencyID(printXMLInfo.getCurrencyID());
					printRecordInfo.setNOfficeID(printXMLInfo.getOfficeID());
					printRecordInfo.setNDeptID(printXMLInfo.getDeptID());
					printRecordInfo.setNTransID(printXMLInfo.getId());
					printRecordInfo.setPrintUser(printXMLInfo.getPrintUser());
					printRecordInfo.setIsSave(printXMLInfo.getIsSave());
					printRecordInfo.setInputDate(printXMLInfo.getInputDate());
					printRecordInfo.setInputUserID(printXMLInfo.getInputUserID());
					printRecordInfo.setNClientID(printXMLInfo.getClientID());
					printRecordInfo.setNTransNO(printXMLInfo.getTransNo());
					
					if(printXMLInfo.getOpretionType() > 0)
					{
						printRecordInfo.setNTransTypeID(printXMLInfo.getOpretionType());
					}
					else
					{
						printRecordInfo.setNTransTypeID(printXMLInfo.getTransTypeID());
					}
					
					printRecordInfo.setTempName(billName[i]);
					printRecordInfo.setModuleId(printXMLInfo.getModule());
					if(printXMLInfo.getIsPreview()==-1){//张雷添加判断，如果是打印预览则不记录   2010-08-07
						this.savePrintRecord(printRecordInfo);
					}
					
					/**  更新打印申请记录  **/
					if(isUseableApply==true)
					{
						PrintApplyInfo printApply = new PrintApplyInfo();
						printApply.setNOfficeID(printXMLInfo.getOfficeID());
						printApply.setNCurrencyID(printXMLInfo.getCurrencyID());
						printApply.setNDeptID(printXMLInfo.getDeptID());
						printApply.setModuleId(printXMLInfo.getModule());
						
						if(printXMLInfo.getOpretionType() > 0)
						{
							printApply.setLTransTypeID(printXMLInfo.getOpretionType());
						}
						else
						{
							printApply.setLTransTypeID(printXMLInfo.getTransTypeID());
						}
						
						printApply.setStrBillName(billName[i]);
						printApply.setNTransID(printXMLInfo.getId());
						printApply.setNTransNo(printXMLInfo.getTransNo());
						
						this.updatePrintApply(printApply);
						this.updateebankPrintApply(printApply);
					}
				}
				else
				{
					retuenbl = String.valueOf(lPrintTime)+"_cant";
					if(printXMLInfo.getIsPreview()==-1){
						throw new IException("该交易"+ printXMLInfo.getTransNo() +"对应单据模版["+ strXMLName +"]已达到最大打印次数");
					}
				}
    		}
    	}
    	catch (IException ie)
    	{
    		throw new IException(ie.getMessage());
    	}
    	catch (Exception e) 
    	{
    		log.error(e.getMessage());
    	}
    	
    	return retuenbl;
    }
    public Collection getPrintTemplateContentmany (String strTransNos, String transIDs, int deptID, long lCurrencyID, long lOfficeID, long lModuleID,long lUserID)throws Exception{
    	this.userID = lUserID;
    	return getPrintTemplateContentmany(strTransNos,transIDs,deptID,lCurrencyID,lOfficeID,lModuleID);
    }
    //addby zyyao 批量打印 2007-6-28
    public Collection getPrintTemplateContentmany (String strTransNos, String transIDs, int deptID, long lCurrencyID, long lOfficeID, long lModuleID)throws Exception 
    {
    	TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
    	
    	String isSignature="";
    	Collection resultPrintOption = new ArrayList();
    	List lis = new ArrayList();
    	String strSQL="";
    	
    	int index = -1;
    	//int EIndex = -1;
    	int num = 1;
    	try {
    		// 取得交易id数组大小
    		index = transIDs.indexOf(",");
    		while (index >= 0) {
    			num ++;
	    		index = transIDs.indexOf(",",index+1);
	    	}
    		
    		// 将交易id转换为数组集合
    		long[] lTransIDs = new long[num];
    		String[] TransNos = new String[num];
    		int i = 0;
    		index = transIDs.indexOf(",");
    		while(index >= 0) {
    			lTransIDs[i++] = Long.valueOf(transIDs.substring(0,index)).longValue();
    			transIDs = transIDs.substring(index+1);
    			index = transIDs.indexOf(","); 
    		}
    		lTransIDs[lTransIDs.length-1] = Long.valueOf(transIDs).longValue();
    		
    		index = -1;
    		i = 0;
    		index = strTransNos.indexOf(",");
    		while(index >= 0)
    		{
    			TransNos[i++] = strTransNos.substring(0,index);
    			strTransNos = strTransNos.substring(index+1);
    			index = strTransNos.indexOf(",");
    		}
    		TransNos[TransNos.length-1] = strTransNos;
    		
    		// 将所选交易、模版类型一一对应得到所以打印的内容
    		// 如果所选交易类型尚未与所选模版类型相关联，则不打印此种类型单据
    		this.initDAO();
    		for(int k=0;k<lTransIDs.length;k++)
    		{
    			if (lModuleID == Constant.ModuleType.SETTLEMENT)
    			{
    				strSQL =  " select distinct d.OperationTypeID operationTypeID, b.*,d.inputuserid ";
    				strSQL += " from print_billrelation a,SETT_VTRANSACTION d ,print_manybillrelation b ";  
    				strSQL += " where ( a.ntransactiontypeid=d.transactiontypeid or a.ntransactiontypeid = d.operationtypeid ) and ( b.ntransactiontypeid=d.transactiontypeid or b.ntransactiontypeid = d.operationtypeid) ";
    				strSQL += " and a.stempname = b.stempname ";
    				strSQL += " and d.id in (" + lTransIDs[k] + ")";
    				//modify by xwhe 2009-01-09
    				strSQL += " and d.transno in ('" + TransNos[k] + "')";
    				strSQL += " and a.nofficeid=" + lOfficeID;
    				strSQL += " and a.ncurrency=" + lCurrencyID;
    				strSQL += " and a.ndeptid=" + deptID;
    				strSQL += " and b.nofficeid=" + lOfficeID;
    				strSQL += " and b.ncurrency=" + lCurrencyID;
    				strSQL += " and b.ndeptid=" + deptID;
    				strSQL += " and d.statusid not in ( "   
														+SETTConstant.TransactionStatus.DELETED+","   
														+SETTConstant.TransactionStatus.TEMPSAVE + ","
										    			+SETTConstant.TransactionStatus.REFUSE+" ) ";
    				
    				//strSQL += " group by a.ntempid,a.nbillid";
    			}
    			
    			//log.print("查询打印选项sql："+strSQL);
    			//System.out.println("查询打印选项sql："+strSQL);
    				//strSQL += " order by a.stempname ";
    				//modify by bingliu 2012-06-26 a.stempname不在select中，导致SQL语句报错。
    				strSQL += " order by b.stempname ";
    			transPS = transConn.prepareStatement(strSQL);
    			transRS = transPS.executeQuery();
    			
    			while(transRS.next())
    			{
    				BillrelationSetInfo info = new BillrelationSetInfo();
    				info.setId(transRS.getLong("id"));
    				info.setRelationdeptid(transRS.getLong("ndeptid"));
    				info.setRelationmax(transRS.getLong("nmaxprint"));
    				info.setSetname(transRS.getString("sbilltypename"));
    				info.setTemplatename(transRS.getString("stempName"));			
    				info.setModuleid(transRS.getLong("nmoduleid"));
    				info.setRelationtypeid(transRS.getLong("ntransactiontypeid"));
    				info.setTransID(lTransIDs[k]);
    				info.setTransNo(TransNos[k]);
    				resultPrintOption.add(info);
    				
    				//以前对单据限制，现在由于不能预览的问题，所以不控制批量打印数量限制 boxu update 2007年11月7日
     				PrintXMLTimeInfo printXMLInfo = new PrintXMLTimeInfo();
     				printXMLInfo.setId(lTransIDs[k]);
     				printXMLInfo.setTransNo(TransNos[k]);
     				printXMLInfo.setBillName(new String[]{info.getTemplatename()});
     				printXMLInfo.setDeptID(deptID);
     				printXMLInfo.setOfficeID(lOfficeID);
     				printXMLInfo.setCurrencyID(lCurrencyID);
     				printXMLInfo.setModule(Constant.ModuleType.SETTLEMENT);
     				printXMLInfo.setInputUserID(transRS.getLong("inputuserid"));
     				printXMLInfo.setPrintUser(this.userID);
					isSignature = transEntity.isSignature(printXMLInfo.getClientID(),printXMLInfo.getTransTypeID(),printXMLInfo.getOfficeID(),printXMLInfo.getCurrencyID(),printXMLInfo.getDeptID(),info.getTemplatename());
					printXMLInfo.setIsSave(isSignature);
     				
     				if(transRS.getLong("operationTypeID") > 0)
     				{
     					printXMLInfo.setOpretionType(transRS.getLong("operationTypeID"));
     					printXMLInfo.setTransTypeID(info.getRelationtypeid());
     				}
     				else 
 					{
 						printXMLInfo.setTransTypeID(info.getRelationtypeid());
 					}
     				
     				lis.add(printXMLInfo);
    			}
    			
    			//以前对单据限制，现在由于不能预览的问题，所以不控制批量打印数量限制 boxu update 2007年11月7日
    			if(lis !=null && lis.size() > 0)
    			{
    				for(int j=0; j<lis.size(); j++)
    				{
    					PrintXMLTimeInfo printXMLInfo = (PrintXMLTimeInfo)lis.get(j);
	     				boolean blPrint = this.validatePrintTWO(printXMLInfo);
	     				
	     				if(!blPrint)
	     				{
	     					throw new IException("该交易"+TransNos[k]+"对应单据模版已达到最大打印次数");
	     				}
    				}
    				lis.clear();
    			}
        	}

    	}
    	catch (Exception ie)
    	{
    		ie.printStackTrace();
    		throw new IException(ie.getMessage());
    	}
    	finally
    	{
    		this.finalizeDAO();
    	}
    	
    	if ( resultPrintOption.isEmpty() )
    	{
    		throw new IException("     批量打印未设置     ");
    	}
    	
    	return resultPrintOption;
    }
    
    public PrintInfo getCurrentPrintInfo(long lID) throws Exception
    {
    	PrintInfo printInfo = new PrintInfo();
    	Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
    	
    	try {
    		TransCurrentDepositInfo resultInfo = null;
    		resultInfo = dao.findByID(lID);
    		
    		if (resultInfo != null)
			{
				log.print("result not null");
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//在银行付款中-bankid 为付款银行			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//在银行收款中-bankid 为收款银行			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				if (resultInfo.getPayAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayAccountID());
				}
				if (resultInfo.getReceiveAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveAccountID());
				}
				if (resultInfo.getReceiveClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getReceiveClientID());
				}
				if (resultInfo.getPayClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getPayClientID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getExtAccountNo() != null && resultInfo.getExtAccountNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAccountNo());
				}
				log.print("外部帐户号:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				log.print("外部客户名称:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				log.print("外部银行:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				log.print("外部银行省:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				log.print("外部银行市:" + printInfo.getExtRemitInCity());
				if (resultInfo.getAbstractStr() != null && resultInfo.getAbstractStr().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstractStr());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
			}
    	}
    	catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
    	
    	return printInfo;
    }
    /**
     * 取得指定模版的打印内容
     * @param pi
     * @param lTemplateID
     * @param out
     * @throws Exception
     */
    public String printTemplate (PrintInfo pi , long lTemplateID) throws Exception 
    {
    	String templateContent = "";
    	try 
    	{
    		PrintInfoVo PIVo=new PrintInfoVo();
    		
    		log.print("根据printInfo,生成PIVo!");
    		
    		PIVo.setExecuteDate(DataFormat.formatDate(pi.getExecuteDate()));
    		if (PIVo.getExecuteDate().length() < 10) {
				PIVo.setExecuteDate("0000000000");
    		}
			PIVo.setYear(PIVo.getExecuteDate().substring(0, 4));
			PIVo.setMonth(PIVo.getExecuteDate().substring(5, 7));
			PIVo.setDay(PIVo.getExecuteDate().substring(8, 10));
			
			PIVo.setTransNo(pi.getTransNo());
			if (pi.getPayAccountID() > 0)
			{
				//取由户的银行号 add by feiye 2006.5.16
				//PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByAccountIDandBankID(pi.getPayAccountID(), pi.getPayBankID())));
				PIVo.setPayBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getPayBankID())));
				PIVo.setPayProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getPayBankID())));
				PIVo.setPayCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getPayBankID())));
				PIVo.setPayAddress("&nbsp;");
				
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getPayAccountID())));
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getPayAccountID()))); //用的是内部帐户
				
				//如果是换开定期存单业务的话，取的是预算业务的银行号
				if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
					System.out.println(" :    是换开定期存单业务!");
					PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankFactAccountName(pi.getPayAccountID())));
					PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankFactAccountNO(pi.getPayAccountID())));
				}
				
				System.out.println("1:得到的付款方帐户号为:"+PIVo.getPayAccountNo()+"pi.getPayBankID()"+pi.getPayBankID()+"pi.getPayAccountID()"+pi.getPayAccountID());

			}
			else	//
			{
				PIVo.setPayAccountName(DataFormat.formatString(NameRef.getBankNameByID(pi.getPayBankID())));	//查外部行的名称
				PIVo.setPayAccountNo(DataFormat.formatString(NameRef.getBankAccountCodeByID(-1,pi.getPayBankID())));	//查外部行的帐号
				
				PIVo.setPayBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setPayProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setPayCity(DataFormat.formatString(pi.getPayExtRemitInCity()));
				PIVo.setPayAddress("&nbsp;");
				System.out.println("2:得到的付款方帐户号为:"+PIVo.getPayAccountNo()+"及付款方银行ID为:"+pi.getPayBankID());
			}
			
			if (pi.getReceiveAccountID() > 0)
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(NameRef.getAccountNameByID(pi.getReceiveAccountID())));
				PIVo.setReceiveAccountNo(DataFormat.formatString(NameRef.getAccountNoByID(pi.getReceiveAccountID())));
				PIVo.setReceiveBankName(DataFormat.formatString(IPrintTemplate.getBankAccountPrintNameByID(pi.getReceiveBankID())));
				PIVo.setReceiveProvince(DataFormat.formatString(IPrintTemplate.getBankAccountProvinceByID(pi.getReceiveBankID())));
				PIVo.setReceiveCity(DataFormat.formatString(IPrintTemplate.getBankAccountCityByID(pi.getReceiveBankID())));
				PIVo.setReceiveAddress("&nbsp;");
				System.out.println("1:得到的收款方帐户号为:"+PIVo.getReceiveAccountNo()+"pi.getReceiveBankID()"+pi.getReceiveBankID()+"pi.getReceiveAccountID()"+pi.getReceiveAccountID());
			}
			else	//如果没有设置，就只接从PI里面取值
			{
				PIVo.setReceiveAccountName(DataFormat.formatString(pi.getExtClientName()));
				PIVo.setReceiveAccountNo(DataFormat.formatString(pi.getExtAccountNo()));
				PIVo.setReceiveBankName(DataFormat.formatString(pi.getExtRemitInBank()));
				PIVo.setReceiveProvince(DataFormat.formatString(pi.getExtRemitInProvince()));
				PIVo.setReceiveCity(DataFormat.formatString(pi.getExtRemitInCity()));
				PIVo.setReceiveAddress("&nbsp;");
				System.out.println("2:得到的收款方帐户号为:"+pi.getExtAccountNo());
			}
			System.out.println("---------------------------------PrintAmount"+pi.getAmount());
			PIVo.setAmount(DataFormat.formatAmount(pi.getAmount()));
			String amount = PIVo.getAmount();
			int leg = amount.length();
			System.out.println("leg："+leg);
			int dex = amount.indexOf(".");
			System.out.println("dex："+dex);
			System.out.println(amount.substring(dex+1,leg));
			amount = amount.substring(0,dex)+amount.substring(dex+1,leg);
			
			System.out.println("金额的长度："+amount.length());
			if(amount.length()>0)
			{
				System.out.println("金额的长度>0");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("&nbsp;");
				PIVo.setAmount6("&nbsp;");
				PIVo.setAmount7("&nbsp;");
				PIVo.setAmount8("&nbsp;");
				PIVo.setAmount9("&nbsp;");
				PIVo.setAmount10("￥");
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>1)
			{
				System.out.println("金额的长度>1");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("&nbsp;");
				PIVo.setAmount6("&nbsp;");
				PIVo.setAmount7("&nbsp;");
				PIVo.setAmount8("&nbsp;");
				PIVo.setAmount9("￥");
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>2)
			{
				System.out.println("金额的长度>2");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("&nbsp;");
				PIVo.setAmount6("&nbsp;");
				PIVo.setAmount7("&nbsp;");
				PIVo.setAmount8("￥");
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>3)
			{
				System.out.println("金额的长度>3");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("&nbsp;");
				PIVo.setAmount6("&nbsp;");
				PIVo.setAmount7("￥");
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>4)
			{
				System.out.println("金额的长度>4");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("&nbsp;");
				PIVo.setAmount6("￥");
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>5)
			{
				System.out.println("金额的长度>5");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("&nbsp;");
				PIVo.setAmount5("￥");
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>6)
			{
				System.out.println("金额的长度>6");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("&nbsp;");
				PIVo.setAmount4("￥");
				PIVo.setAmount5(amount.substring(amount.length()-7,amount.length()-6));
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>7)
			{
				System.out.println("金额的长度>7");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("&nbsp;");
				PIVo.setAmount3("￥");
				PIVo.setAmount4(amount.substring(amount.length()-8,amount.length()-7));
				PIVo.setAmount5(amount.substring(amount.length()-7,amount.length()-6));
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>8)
			{
				System.out.println("金额的长度>8");
				PIVo.setAmount1("&nbsp;");
				PIVo.setAmount2("￥");
				PIVo.setAmount3(amount.substring(amount.length()-9,amount.length()-8));
				PIVo.setAmount4(amount.substring(amount.length()-8,amount.length()-7));
				PIVo.setAmount5(amount.substring(amount.length()-7,amount.length()-6));
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>9)
			{
				System.out.println("金额的长度>9");
				PIVo.setAmount1("￥");
				PIVo.setAmount2(amount.substring(amount.length()-10,amount.length()-9));
				PIVo.setAmount3(amount.substring(amount.length()-9,amount.length()-8));
				PIVo.setAmount4(amount.substring(amount.length()-8,amount.length()-7));
				PIVo.setAmount5(amount.substring(amount.length()-7,amount.length()-6));
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}
			if(amount.length()>10)
			{
				System.out.println("金额的长度>10");
				PIVo.setAmount1(amount.substring(amount.length()-11,amount.length()-10));
				PIVo.setAmount2(amount.substring(amount.length()-10,amount.length()-9));
				PIVo.setAmount3(amount.substring(amount.length()-9,amount.length()-8));
				PIVo.setAmount4(amount.substring(amount.length()-8,amount.length()-7));
				PIVo.setAmount5(amount.substring(amount.length()-7,amount.length()-6));
				PIVo.setAmount6(amount.substring(amount.length()-6,amount.length()-5));
				PIVo.setAmount7(amount.substring(amount.length()-5,amount.length()-4));
				PIVo.setAmount8(amount.substring(amount.length()-4,amount.length()-3));
				PIVo.setAmount9(amount.substring(amount.length()-3,amount.length()-2));
				PIVo.setAmount10(amount.substring(amount.length()-2,amount.length()-1));
				PIVo.setAmount11(amount.substring(amount.length()-1,amount.length()));
			}			
			
			PIVo.setChineseAmount(ChineseCurrency.showChinese(PIVo.getAmount(), pi.getCurrencyID()));
			PIVo.setAmount(DataFormat.formatDisabledAmount(pi.getAmount()));
			PIVo.setInputUser(NameRef.getUserNameByID(pi.getInputUserID()));
			PIVo.setCheckUser(NameRef.getUserNameByID(pi.getCheckUserID()));
			PIVo.setAbstract(pi.getAbstract());
			//for 转贴现台帐 start
			PIVo.setBillType(LOANConstant.DraftType.getName(pi.getBillTypeID()));
			PIVo.setDiscountBillNo(DataFormat.formatString(pi.getDiscountBillNo()));

			PIVo.setEndDate(DataFormat.formatDate(pi.getEndDate()));
			System.out.println("    结束日:"+pi.getEndDate());
			if (PIVo.getEndDate().length() < 10)
				PIVo.setEndDate("0000000000");	
			
			PIVo.setEndYear(PIVo.getEndDate().substring(0, 4));
			PIVo.setEndMonth(PIVo.getEndDate().substring(5, 7));
			PIVo.setEndDay(PIVo.getEndDate().substring(8, 10));	
			PIVo.setBillAcceptanceUser(DataFormat.formatString(pi.getBillAcceptanceUser()));
			PIVo.setAcceptanceUserAccount(DataFormat.formatString(pi.getAcceptanceUserAccount()));
			PIVo.setAcceptanceUserBank(DataFormat.formatString(pi.getAcceptanceUserBank()));
			PIVo.setDiscountBillAmount(DataFormat.formatDisabledAmount(pi.getDiscountBillAmount()));
			String DiscountBillAmount = "";
			leg = DiscountBillAmount.length();
			dex = DiscountBillAmount.indexOf(".");
			if(DiscountBillAmount!=null && DiscountBillAmount.length()>0)
			{
				DiscountBillAmount = DiscountBillAmount.substring(0,dex)+DiscountBillAmount.substring(dex+1,leg);
			}			
			
			System.out.println("票据金额的长度："+DiscountBillAmount.length());
			if(DiscountBillAmount.length()>0)
			{
				System.out.println("金额的长度>0");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("&nbsp;");
				PIVo.setDiscountBillAmount6("&nbsp;");
				PIVo.setDiscountBillAmount7("&nbsp;");
				PIVo.setDiscountBillAmount8("&nbsp;");
				PIVo.setDiscountBillAmount9("&nbsp;");
				PIVo.setDiscountBillAmount10("￥");
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>1)
			{
				System.out.println("金额的长度>1");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("&nbsp;");
				PIVo.setDiscountBillAmount6("&nbsp;");
				PIVo.setDiscountBillAmount7("&nbsp;");
				PIVo.setDiscountBillAmount8("&nbsp;");
				PIVo.setDiscountBillAmount9("￥");
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>2)
			{
				System.out.println("金额的长度>2");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("&nbsp;");
				PIVo.setDiscountBillAmount6("&nbsp;");
				PIVo.setDiscountBillAmount7("&nbsp;");
				PIVo.setDiscountBillAmount8("￥");
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>3)
			{
				System.out.println("金额的长度>3");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("&nbsp;");
				PIVo.setDiscountBillAmount6("&nbsp;");
				PIVo.setDiscountBillAmount7("￥");
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>4)
			{
				System.out.println("金额的长度>4");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("&nbsp;");
				PIVo.setDiscountBillAmount6("￥");
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>5)
			{
				System.out.println("金额的长度>5");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("&nbsp;");
				PIVo.setDiscountBillAmount5("￥");
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>6)
			{
				System.out.println("金额的长度>6");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("&nbsp;");
				PIVo.setDiscountBillAmount4("￥");
				PIVo.setDiscountBillAmount5(DiscountBillAmount.substring(DiscountBillAmount.length()-7,DiscountBillAmount.length()-6));
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>7)
			{
				System.out.println("金额的长度>7");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("&nbsp;");
				PIVo.setDiscountBillAmount3("￥");
				PIVo.setDiscountBillAmount4(DiscountBillAmount.substring(DiscountBillAmount.length()-8,DiscountBillAmount.length()-7));
				PIVo.setDiscountBillAmount5(DiscountBillAmount.substring(DiscountBillAmount.length()-7,DiscountBillAmount.length()-6));
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>8)
			{
				System.out.println("金额的长度>8");
				PIVo.setDiscountBillAmount1("&nbsp;");
				PIVo.setDiscountBillAmount2("￥");
				PIVo.setDiscountBillAmount3(DiscountBillAmount.substring(DiscountBillAmount.length()-9,DiscountBillAmount.length()-8));
				PIVo.setDiscountBillAmount4(DiscountBillAmount.substring(DiscountBillAmount.length()-8,DiscountBillAmount.length()-7));
				PIVo.setDiscountBillAmount5(DiscountBillAmount.substring(DiscountBillAmount.length()-7,DiscountBillAmount.length()-6));
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>9)
			{
				System.out.println("金额的长度>9");
				PIVo.setDiscountBillAmount1("￥");
				PIVo.setDiscountBillAmount2(DiscountBillAmount.substring(DiscountBillAmount.length()-10,DiscountBillAmount.length()-9));
				PIVo.setDiscountBillAmount3(DiscountBillAmount.substring(DiscountBillAmount.length()-9,DiscountBillAmount.length()-8));
				PIVo.setDiscountBillAmount4(DiscountBillAmount.substring(DiscountBillAmount.length()-8,DiscountBillAmount.length()-7));
				PIVo.setDiscountBillAmount5(DiscountBillAmount.substring(DiscountBillAmount.length()-7,DiscountBillAmount.length()-6));
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			if(DiscountBillAmount.length()>10)
			{
				System.out.println("金额的长度>10");
				PIVo.setDiscountBillAmount1(DiscountBillAmount.substring(DiscountBillAmount.length()-11,DiscountBillAmount.length()-10));
				PIVo.setDiscountBillAmount2(DiscountBillAmount.substring(DiscountBillAmount.length()-10,DiscountBillAmount.length()-9));
				PIVo.setDiscountBillAmount3(DiscountBillAmount.substring(DiscountBillAmount.length()-9,DiscountBillAmount.length()-8));
				PIVo.setDiscountBillAmount4(DiscountBillAmount.substring(DiscountBillAmount.length()-8,DiscountBillAmount.length()-7));
				PIVo.setDiscountBillAmount5(DiscountBillAmount.substring(DiscountBillAmount.length()-7,DiscountBillAmount.length()-6));
				PIVo.setDiscountBillAmount6(DiscountBillAmount.substring(DiscountBillAmount.length()-6,DiscountBillAmount.length()-5));
				PIVo.setDiscountBillAmount7(DiscountBillAmount.substring(DiscountBillAmount.length()-5,DiscountBillAmount.length()-4));
				PIVo.setDiscountBillAmount8(DiscountBillAmount.substring(DiscountBillAmount.length()-4,DiscountBillAmount.length()-3));
				PIVo.setDiscountBillAmount9(DiscountBillAmount.substring(DiscountBillAmount.length()-3,DiscountBillAmount.length()-2));
				PIVo.setDiscountBillAmount10(DiscountBillAmount.substring(DiscountBillAmount.length()-2,DiscountBillAmount.length()-1));
				PIVo.setDiscountBillAmount11(DiscountBillAmount.substring(DiscountBillAmount.length()-1,DiscountBillAmount.length()));
			}
			
			PIVo.setChineseDiscountBillAmount(ChineseCurrency.showChinese(String.valueOf(pi.getDiscountBillAmount())));			
			
			System.out.println("    利率:"+pi.getRate());

			PIVo.setRate(DataFormat.formatRate(pi.getRate()));
			PIVo.setDiscountInterestAmount(DataFormat.formatDisabledAmount(pi.getDiscountInterestAmount()));
			
			String DiscountInterestAmount = "";
			leg = DiscountInterestAmount.length();
			dex = DiscountInterestAmount.indexOf(".");
			if(DiscountInterestAmount!=null && DiscountInterestAmount.length()>0)
			{
				DiscountInterestAmount = DiscountInterestAmount.substring(0,dex)+DiscountInterestAmount.substring(dex+1,leg);
			}
			
			System.out.println("贴现利息金额的长度："+DiscountInterestAmount.length());
			if(DiscountInterestAmount.length()>0)
			{
				System.out.println("金额的长度>0");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("&nbsp;");
				PIVo.setDiscountInterestAmount6("&nbsp;");
				PIVo.setDiscountInterestAmount7("&nbsp;");
				PIVo.setDiscountInterestAmount8("&nbsp;");
				PIVo.setDiscountInterestAmount9("&nbsp;");
				PIVo.setDiscountInterestAmount10("￥");
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>1)
			{
				System.out.println("金额的长度>1");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("&nbsp;");
				PIVo.setDiscountInterestAmount6("&nbsp;");
				PIVo.setDiscountInterestAmount7("&nbsp;");
				PIVo.setDiscountInterestAmount8("&nbsp;");
				PIVo.setDiscountInterestAmount9("￥");
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>2)
			{
				System.out.println("金额的长度>2");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("&nbsp;");
				PIVo.setDiscountInterestAmount6("&nbsp;");
				PIVo.setDiscountInterestAmount7("&nbsp;");
				PIVo.setDiscountInterestAmount8("￥");
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>3)
			{
				System.out.println("金额的长度>3");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("&nbsp;");
				PIVo.setDiscountInterestAmount6("&nbsp;");
				PIVo.setDiscountInterestAmount7("￥");
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>4)
			{
				System.out.println("金额的长度>4");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("&nbsp;");
				PIVo.setDiscountInterestAmount6("￥");
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>5)
			{
				System.out.println("金额的长度>5");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("&nbsp;");
				PIVo.setDiscountInterestAmount5("￥");
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>6)
			{
				System.out.println("金额的长度>6");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("&nbsp;");
				PIVo.setDiscountInterestAmount4("￥");
				PIVo.setDiscountInterestAmount5(DiscountInterestAmount.substring(DiscountInterestAmount.length()-7,DiscountInterestAmount.length()-6));
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>7)
			{
				System.out.println("金额的长度>7");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("&nbsp;");
				PIVo.setDiscountInterestAmount3("￥");
				PIVo.setDiscountInterestAmount4(DiscountInterestAmount.substring(DiscountInterestAmount.length()-8,DiscountInterestAmount.length()-7));
				PIVo.setDiscountInterestAmount5(DiscountInterestAmount.substring(DiscountInterestAmount.length()-7,DiscountInterestAmount.length()-6));
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>8)
			{
				System.out.println("金额的长度>8");
				PIVo.setDiscountInterestAmount1("&nbsp;");
				PIVo.setDiscountInterestAmount2("￥");
				PIVo.setDiscountInterestAmount3(DiscountInterestAmount.substring(DiscountInterestAmount.length()-9,DiscountInterestAmount.length()-8));
				PIVo.setDiscountInterestAmount4(DiscountInterestAmount.substring(DiscountInterestAmount.length()-8,DiscountInterestAmount.length()-7));
				PIVo.setDiscountInterestAmount5(DiscountInterestAmount.substring(DiscountInterestAmount.length()-7,DiscountInterestAmount.length()-6));
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>9)
			{
				System.out.println("金额的长度>9");
				PIVo.setDiscountInterestAmount1("￥");
				PIVo.setDiscountInterestAmount2(DiscountInterestAmount.substring(DiscountInterestAmount.length()-10,DiscountInterestAmount.length()-9));
				PIVo.setDiscountInterestAmount3(DiscountInterestAmount.substring(DiscountInterestAmount.length()-9,DiscountInterestAmount.length()-8));
				PIVo.setDiscountInterestAmount4(DiscountInterestAmount.substring(DiscountInterestAmount.length()-8,DiscountInterestAmount.length()-7));
				PIVo.setDiscountInterestAmount5(DiscountInterestAmount.substring(DiscountInterestAmount.length()-7,DiscountInterestAmount.length()-6));
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			if(DiscountInterestAmount.length()>10)
			{
				System.out.println("金额的长度>10");
				PIVo.setDiscountInterestAmount1(DiscountInterestAmount.substring(DiscountInterestAmount.length()-11,DiscountInterestAmount.length()-10));
				PIVo.setDiscountInterestAmount2(DiscountInterestAmount.substring(DiscountInterestAmount.length()-10,DiscountInterestAmount.length()-9));
				PIVo.setDiscountInterestAmount3(DiscountInterestAmount.substring(DiscountInterestAmount.length()-9,DiscountInterestAmount.length()-8));
				PIVo.setDiscountInterestAmount4(DiscountInterestAmount.substring(DiscountInterestAmount.length()-8,DiscountInterestAmount.length()-7));
				PIVo.setDiscountInterestAmount5(DiscountInterestAmount.substring(DiscountInterestAmount.length()-7,DiscountInterestAmount.length()-6));
				PIVo.setDiscountInterestAmount6(DiscountInterestAmount.substring(DiscountInterestAmount.length()-6,DiscountInterestAmount.length()-5));
				PIVo.setDiscountInterestAmount7(DiscountInterestAmount.substring(DiscountInterestAmount.length()-5,DiscountInterestAmount.length()-4));
				PIVo.setDiscountInterestAmount8(DiscountInterestAmount.substring(DiscountInterestAmount.length()-4,DiscountInterestAmount.length()-3));
				PIVo.setDiscountInterestAmount9(DiscountInterestAmount.substring(DiscountInterestAmount.length()-3,DiscountInterestAmount.length()-2));
				PIVo.setDiscountInterestAmount10(DiscountInterestAmount.substring(DiscountInterestAmount.length()-2,DiscountInterestAmount.length()-1));
				PIVo.setDiscountInterestAmount11(DiscountInterestAmount.substring(DiscountInterestAmount.length()-1,DiscountInterestAmount.length()));
			}
			
			PIVo.setDiscountAmount(DataFormat.formatDisabledAmount(pi.getDiscountAmount()));
			
			String DiscountAmount = "";
			leg = DiscountAmount.length();
			dex = DiscountAmount.indexOf(".");
			if(DiscountAmount!=null && DiscountAmount.length()>0)
			{
				DiscountAmount = DiscountAmount.substring(0,dex)+DiscountAmount.substring(dex+1,leg);
			}
			
			System.out.println("贴现金额的长度："+DiscountAmount.length());
			if(DiscountAmount.length()>0)
			{
				System.out.println("金额的长度>0");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("&nbsp;");
				PIVo.setDiscountAmount6("&nbsp;");
				PIVo.setDiscountAmount7("&nbsp;");
				PIVo.setDiscountAmount8("&nbsp;");
				PIVo.setDiscountAmount9("&nbsp;");
				PIVo.setDiscountAmount10("￥");
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>1)
			{
				System.out.println("金额的长度>1");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("&nbsp;");
				PIVo.setDiscountAmount6("&nbsp;");
				PIVo.setDiscountAmount7("&nbsp;");
				PIVo.setDiscountAmount8("&nbsp;");
				PIVo.setDiscountAmount9("￥");
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>2)
			{
				System.out.println("金额的长度>2");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("&nbsp;");
				PIVo.setDiscountAmount6("&nbsp;");
				PIVo.setDiscountAmount7("&nbsp;");
				PIVo.setDiscountAmount8("￥");
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>3)
			{
				System.out.println("金额的长度>3");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("&nbsp;");
				PIVo.setDiscountAmount6("&nbsp;");
				PIVo.setDiscountAmount7("￥");
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>4)
			{
				System.out.println("金额的长度>4");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("&nbsp;");
				PIVo.setDiscountAmount6("￥");
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>5)
			{
				System.out.println("金额的长度>5");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("&nbsp;");
				PIVo.setDiscountAmount5("￥");
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>6)
			{
				System.out.println("金额的长度>6");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("&nbsp;");
				PIVo.setDiscountAmount4("￥");
				PIVo.setDiscountAmount5(DiscountAmount.substring(DiscountAmount.length()-7,DiscountAmount.length()-6));
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>7)
			{
				System.out.println("金额的长度>7");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("&nbsp;");
				PIVo.setDiscountAmount3("￥");
				PIVo.setDiscountAmount4(DiscountAmount.substring(DiscountAmount.length()-8,DiscountAmount.length()-7));
				PIVo.setDiscountAmount5(DiscountAmount.substring(DiscountAmount.length()-7,DiscountAmount.length()-6));
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>8)
			{
				System.out.println("金额的长度>8");
				PIVo.setDiscountAmount1("&nbsp;");
				PIVo.setDiscountAmount2("￥");
				PIVo.setDiscountAmount3(DiscountAmount.substring(DiscountAmount.length()-9,DiscountAmount.length()-8));
				PIVo.setDiscountAmount4(DiscountAmount.substring(DiscountAmount.length()-8,DiscountAmount.length()-7));
				PIVo.setDiscountAmount5(DiscountAmount.substring(DiscountAmount.length()-7,DiscountAmount.length()-6));
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>9)
			{
				System.out.println("金额的长度>9");
				PIVo.setDiscountAmount1("￥");
				PIVo.setDiscountAmount2(DiscountAmount.substring(DiscountAmount.length()-10,DiscountAmount.length()-9));
				PIVo.setDiscountAmount3(DiscountAmount.substring(DiscountAmount.length()-9,DiscountAmount.length()-8));
				PIVo.setDiscountAmount4(DiscountAmount.substring(DiscountAmount.length()-8,DiscountAmount.length()-7));
				PIVo.setDiscountAmount5(DiscountAmount.substring(DiscountAmount.length()-7,DiscountAmount.length()-6));
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			if(DiscountAmount.length()>10)
			{
				System.out.println("金额的长度>10");
				PIVo.setDiscountAmount1(DiscountAmount.substring(DiscountAmount.length()-11,DiscountAmount.length()-10));
				PIVo.setDiscountAmount2(DiscountAmount.substring(DiscountAmount.length()-10,DiscountAmount.length()-9));
				PIVo.setDiscountAmount3(DiscountAmount.substring(DiscountAmount.length()-9,DiscountAmount.length()-8));
				PIVo.setDiscountAmount4(DiscountAmount.substring(DiscountAmount.length()-8,DiscountAmount.length()-7));
				PIVo.setDiscountAmount5(DiscountAmount.substring(DiscountAmount.length()-7,DiscountAmount.length()-6));
				PIVo.setDiscountAmount6(DiscountAmount.substring(DiscountAmount.length()-6,DiscountAmount.length()-5));
				PIVo.setDiscountAmount7(DiscountAmount.substring(DiscountAmount.length()-5,DiscountAmount.length()-4));
				PIVo.setDiscountAmount8(DiscountAmount.substring(DiscountAmount.length()-4,DiscountAmount.length()-3));
				PIVo.setDiscountAmount9(DiscountAmount.substring(DiscountAmount.length()-3,DiscountAmount.length()-2));
				PIVo.setDiscountAmount10(DiscountAmount.substring(DiscountAmount.length()-2,DiscountAmount.length()-1));
				PIVo.setDiscountAmount11(DiscountAmount.substring(DiscountAmount.length()-1,DiscountAmount.length()));
			}
			
			PIVo.setApplicantName(DataFormat.formatString(pi.getApplicantName()));
			PIVo.setApplicantAccountNo(DataFormat.formatString(pi.getApplicantAccountNo()));
			PIVo.setApplicantAccountBankNo(DataFormat.formatString(pi.getApplicantAccountBankNo()));
			
			
			//第二批需要添加的参数
			PIVo.setInterestStartDate(DataFormat.formatDate(pi.getInterestStartDate()));
			System.out.println("    起息日:"+pi.getInterestStartDate());
			if (PIVo.getInterestStartDate().length() < 10)
				PIVo.setInterestStartDate("0000000000");
			
			System.out.println("    定期期限:"+pi.getFixedDepositTerm());
			if(pi.getFixedDepositTerm()<0)
				PIVo.setFixedDepositTerm("");
			else
				PIVo.setFixedDepositTerm(String.valueOf(pi.getFixedDepositTerm()));
			
			PIVo.setCurrencyType( Constant.CurrencyType.getName(pi.getCurrencyID()) );
			
			if(pi.getDepositBillNO()!=null && !pi.getDepositBillNO().equals("")){
				PIVo.setDepositBillNO(pi.getDepositBillNO());
			}
			if(pi.getTransTypeID()!=-1){
				PIVo.setTransactionType(SETTConstant.TransactionType.getName(pi.getTransTypeID()));
			}
			if(pi.getFixedDepositNo()!=null && !pi.getFixedDepositNo().equals("")){
				PIVo.setFixedDepositNo(pi.getFixedDepositNo());
			}
			log.print("取得pi参数完毕,生成PIVo完毕!");
			
			// 取得模版信息
			PrintBillSettingDao dao = new PrintBillSettingDao();
			PrintBillTemplateInfo templateInfo = dao.findTemplateInfoByID(lTemplateID);
			
			// 取得模版文件内容
			templateContent = dao.getTemplateContent(templateInfo.getNUrl());
			int EntryNum = dao.fileList(templateContent,CODE_SEPERATORSTART);
			
			String[] strCodes = new String[EntryNum];
			
			int SIndex = 0;
			int EIndex = 0;
			int i = 0;
			
			// 得到模版中各条目对应配置文件中的scode
			SIndex = templateContent.indexOf(CODE_SEPERATORSTART);
			EIndex = templateContent.indexOf(CODE_SEPERATOREND);
			while(SIndex > 0 && EIndex > 0) {
				strCodes[i++] = templateContent.substring(SIndex+2 , EIndex);
				//templateContent = templateContent.substring(0,SIndex) + templateContent.substring(SIndex+6);
				templateContent = templateContent.substring(0,SIndex) + templateContent.substring(EIndex+2);
				SIndex = templateContent.indexOf(CODE_SEPERATORSTART);
				EIndex = templateContent.indexOf(CODE_SEPERATOREND);
			}
			
			// 合并单据模版与打印内容
			if(strCodes != null && strCodes.length > 0)
			{
				templateContent = mergeContent(templateContent,strCodes,PIVo);
			}
    	} 
    	catch (Exception exp)
		{
			throw exp;
		}
    	
    	return templateContent;
    }
    
    public String[] fileList(String strURL , String strSeparator) throws Exception
    {
    	String[] strCodes = null;
    	PrintBillSettingDao dao = new PrintBillSettingDao();
    	    	
    	// 取得模版文件内容
		String templateContent = dao.getTemplateContent(strURL);
		int EntryNum = dao.fileList(templateContent,strSeparator);
		
		strCodes = new String[EntryNum];
		int SIndex = 0;
		int i = 0;
		
		SIndex = templateContent.indexOf(strSeparator);
		while(SIndex > 0) {
			strCodes[i++] = templateContent.substring(SIndex+strSeparator.length(),SIndex+strSeparator.length()+2);
			templateContent = templateContent.substring(0,SIndex) + templateContent.substring(SIndex+6);
			SIndex = templateContent.indexOf(strSeparator);
		}
    	return strCodes;
    }
    
    /**
     * 合并单据模版和所要打印内容
     * @param templateContent
     * @param strCodes
     * @param objPiInfo
     * @return
     * @throws Exception
     */
    public String mergeContent (String templateContent , String[] strCodes , Object objPiInfo) throws Exception
    {
    	//String strContent = "";
    	String propertyName="";
    	String[] backInfo = new String[strCodes.length];
    	int index = 0;
    	
    	try {
    		TemplateSettingXml xml = new TemplateSettingXml();
			Collection collXML = null;
			collXML=xml.getTemplateSetting();
			if(collXML!=null)
				System.out.println("得到的XML配置信息集合的长度为："+collXML.size());
			
			// 根据模版中每个条目所对应code，从objPiInfo中取对应值
			for(int i = 0; i < strCodes.length; i++) {
				// 根据sCode 和找到objPiInfo里对应的属性名字（主要有一个配置属性文件)
				for(int j=0;j<((ArrayList)collXML).size();j++){
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)((ArrayList)collXML).get(j);
					// sCode 和 类型均相等
					if(strCodes[i].equals(info.getTemplateDetailCode()) && info.getTemplateType() == 1 ) {
						propertyName = info.getTemplateDetailVariable().trim();
						break;
					}
				}
				System.out.println("=========得到配置XML中的相应的方法名为:"+propertyName);
				
				if(!propertyName.equals("") && !propertyName.equals("noVariable")){
					System.out.println("=========执行类中此方法 get"+propertyName+"()");
					Method meth=objPiInfo.getClass().getMethod("get"+propertyName,new Class[]{});
					Object retobj=meth.invoke(objPiInfo,new Object[]{});
					backInfo[i]=(String)retobj;
					System.out.println(backInfo[i]);
					System.out.println(backInfo[i].length());
					if(backInfo[i]==null || backInfo[i].length()<=0)
					{
						backInfo[i] = "&nbsp;";
					}
				}				
			}
			
			// 将所得模版各条目所对应的值与模版内容合并 TEMPLET_SEPERATOR
			int SIndex = -1;
			SIndex = templateContent.indexOf(TEMPLET_SEPERATOR);
			while(SIndex >= 0) {
				templateContent = templateContent.substring(0,SIndex) + backInfo[index++] + templateContent.substring(SIndex + TEMPLET_SEPERATOR.length());
				SIndex = templateContent.indexOf(TEMPLET_SEPERATOR);
			}
    	}
    	catch (NoSuchMethodException e) {
	       	   System.out.println("=========在这个类中没有此方法 get"+propertyName+"()");
	           System.err.println(e);
	    }
    	catch (Exception e) {
    		throw e;
    	}
    	return templateContent;
    }
    
    public long getTransTypeIDbyTransID (long lTransID) throws Exception
    {
    	long lResult = -1;
    	String strSQL = "";
    	
    	try {
    		initDAO();
    		
    		strSQL = " select transactiontypeid,operationtypeid from sett_vtransaction where id=" + lTransID;
    		System.out.println("--------------查询交易类型id："+strSQL);
    		
    		transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				lResult = transRS.getLong("transactiontypeid");
				if (lResult == SETTConstant.TransactionType.SPECIALOPERATION)
				{
					lResult = transRS.getLong("operationtypeid");
				}
			}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lResult;
    }
    
    public void savePrintRecord(PrintRecordInfo printRecordInfo) throws Exception
    {
    	String strSQL = "";
    	long lPrintRecord = -1;
    	long lID = -1;
    	int index = 0;
    	try{
			initDAO();
			
			/**  检验该交易此种单据打印次数，如果为第一次打印则新增打印记录，否则更新打印记录  **/
			strSQL = " select count(*) printcount from print_printrecord ";
			strSQL += " where nprintcontentid=" + printRecordInfo.getNTransID();
			strSQL += " and ncurrency=" + printRecordInfo.getNCurrencyID();
			strSQL += " and nofficeid=" + printRecordInfo.getNOfficeID();
			strSQL += " and ndeptid=" + printRecordInfo.getNDeptID();
			strSQL += " and ntransactiontypeid=" + printRecordInfo.getNTransTypeID();
			strSQL += " and STEMPNAME= '"+printRecordInfo.getTempName()+"' ";
			strSQL += " and NMODULEID=" + printRecordInfo.getModuleId();
			
			log.print("查询打印次数sql："+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next())
			{
				lPrintRecord = transRS.getLong("printcount");
			}
			
			if (transPS != null) {
				transPS.close();
				transPS = null;
			}
			if (transRS != null) {
				transRS.close();
				transRS = null;
			}
			
			/**  非第一次打印  **/
			if( lPrintRecord > 0 ) 
			{
				/**  更新打印次数  **/
				strSQL =  " update print_printrecord set nprintnum = nvl((nprintnum+1), 1) ";
				strSQL += " , ninputdate = sysdate ";
				strSQL += " where nprintcontentid = " + printRecordInfo.getNTransID();
				strSQL += " and ncurrency = " + printRecordInfo.getNCurrencyID();
				strSQL += " and nofficeid = " + printRecordInfo.getNOfficeID();
				strSQL += " and ndeptid=" + printRecordInfo.getNDeptID();
				strSQL += " and ntransactiontypeid=" + printRecordInfo.getNTransTypeID();
				strSQL += " and STEMPNAME= '"+printRecordInfo.getTempName()+"' ";
				strSQL += " and NMODULEID=" + printRecordInfo.getModuleId();	
				
				transPS = transConn.prepareStatement(strSQL);
				transPS.executeUpdate();
			}
			/**  第一次打印  **/
			else {
				/**  取得新记录id  
				strSQL = " select nvl(max(ID)+1, 1) oID from print_printrecord ";
				
				transPS = prepareStatement(strSQL);
				transRS = transPS.executeQuery();
				
				while(transRS.next())
				{
					lID = transRS.getLong("oID");
				}
				
				if (transPS != null) {
					transPS.close();
					transPS = null;
				}
				if (transRS != null) {
					transRS.close();
					transRS = null;
				}
				**/
				//modify by xwhe 2009-02-13 采用sequence取最大ID值
				lID = this.getPrint_printRecordID();
				
				/**  新增打印记录  **/
				strSQL =  " insert into print_printrecord ";
				strSQL += " ( ";
				strSQL += " ID, NOFFICEID, NCURRENCY, NCLIENTID, NPRINTCONTENTID ";
				strSQL += " , NDEPTID, NPRINTNUM, NINPUTDATE, NTRANSACTIONTYPEID, STEMPNAME ";
				strSQL += " , NMODULEID ";
				strSQL += " ) ";
				strSQL += " values (?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?) ";

				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(++index , lID);
				transPS.setLong(++index , printRecordInfo.getNOfficeID());
				transPS.setLong(++index , printRecordInfo.getNCurrencyID());
				transPS.setLong(++index , printRecordInfo.getNClientID());
				transPS.setLong(++index , printRecordInfo.getNTransID());
				transPS.setLong(++index , printRecordInfo.getNDeptID());
				transPS.setLong(++index , 1);
				transPS.setLong(++index , printRecordInfo.getNTransTypeID());
				
				//Boxu add XMLName 2007-7-17
				transPS.setString(++index , printRecordInfo.getTempName());
				transPS.setLong(++index , printRecordInfo.getModuleId());
				
				transPS.executeUpdate();
			}
			if(printRecordInfo.getIsSave().equals("1"))
				validatePrintSignature(printRecordInfo);
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{	
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    }
    
    /**
     * 查询某交易的某单据最大打印次数
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public long getMaxPrintTime (long lTransID,String strTransno,long lBillID,long lTemplateID) throws Exception
    {
    	long lMaxPrintTime = -1;
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select nmaxprint from print_billrelation a,sett_vtransaction b";
    		strSQL += " where ( a.ntransactiontypeid=b.transactiontypeid or a.ntransactiontypeid = b.operationtypeid ) ";
    		strSQL += " and a.nbillid=" + lBillID;
    		strSQL += " and a.ntempid=" + lTemplateID;
    		strSQL += " and b.id=" + lTransID;
    		strSQL += " and b.transno='" + strTransno + "'";
    		
    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				lMaxPrintTime = transRS.getLong("nmaxprint");
			}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lMaxPrintTime;
    }
    
    /**
     * 查询某交易的某单据最大打印次数
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public long getMaxPrintXMLTime(PrintXMLTimeInfo printXMLInfo, String XMLName) throws Exception
    {
    	long lMaxPrintTime = -1;
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select nmaxprint from print_billrelation a, sett_vtransaction b ";
    		strSQL += " where ( a.ntransactiontypeid = b.transactiontypeid or a.ntransactiontypeid = b.operationtypeid ) ";
    		
    		strSQL += " and a.stempname = '"+XMLName+"' ";
    		strSQL += " and a.nmoduleid = " + printXMLInfo.getModule();
    		strSQL += " and a.nofficeid = " + printXMLInfo.getOfficeID();
    		strSQL += " and a.ncurrency = " + printXMLInfo.getCurrencyID();
    		strSQL += " and a.ndeptid = " + printXMLInfo.getDeptID();
    		
    		if(printXMLInfo.getOpretionType() > 0)
    		{
    			strSQL += " and a.NTRANSACTIONTYPEID = " + printXMLInfo.getOpretionType();
    			strSQL += " and b.operationtypeid = " + printXMLInfo.getOpretionType();
    		}
    		else
    		{
    			strSQL += " and a.NTRANSACTIONTYPEID = " + printXMLInfo.getTransTypeID();
    			strSQL += " and b.transactiontypeid = " + printXMLInfo.getTransTypeID();
    		}
    		
    		strSQL += " and b.id = " + printXMLInfo.getId();
    		
    		if(printXMLInfo.getTransNo() != null && !printXMLInfo.getTransNo().equals(""))
    		{
    			strSQL += " and b.transno = '" + printXMLInfo.getTransNo() + "' ";
    		}

    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				lMaxPrintTime = transRS.getLong("nmaxprint");
			}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lMaxPrintTime;
    }
    
    /**
     * 查询某交易的某单据别名
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public String getPrintXMLName(PrintXMLTimeInfo printXMLInfo, String XMLName) throws Exception
    {
    	String returnXMLName = "";
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select a.sbilltypename sbilltypename from print_billrelation a, sett_vtransaction b ";
    		strSQL += " where ( a.ntransactiontypeid = b.transactiontypeid or a.ntransactiontypeid = b.operationtypeid ) ";
    		strSQL += " and a.stempname = '"+XMLName+"' ";
    		strSQL += " and a.nmoduleid = " + printXMLInfo.getModule();
    		strSQL += " and a.nofficeid = " + printXMLInfo.getOfficeID();
    		strSQL += " and a.ncurrency = " + printXMLInfo.getCurrencyID();
    		strSQL += " and a.ndeptid = " + printXMLInfo.getDeptID();
    		
    		if(printXMLInfo.getOpretionType() > 0)
    		{
    			strSQL += " and a.NTRANSACTIONTYPEID = " + printXMLInfo.getOpretionType();
    			strSQL += " and b.operationtypeid = " + printXMLInfo.getOpretionType();
    		}
    		else
    		{
    			strSQL += " and a.NTRANSACTIONTYPEID = " + printXMLInfo.getTransTypeID();
    			strSQL += " and b.transactiontypeid = " + printXMLInfo.getTransTypeID();
    		}
    		
    		strSQL += " and b.id = " + printXMLInfo.getId();
    		
    		if(printXMLInfo.getTransNo() != null && !printXMLInfo.getTransNo().equals(""))
    		{
    			strSQL += " and b.transno = '" + printXMLInfo.getTransNo() + "' ";
    		}
    		
    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				returnXMLName = transRS.getString("sbilltypename");
			}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return returnXMLName;
    }
    
    /**
     * 查询某交易的某单据最大打印次数
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception //addby zyyao 批量打印 2007-6-28
     */
    public long getMaxPrintTimemany (long lTransID,String strTransno,long lBillID,long lTemplateID) throws Exception
    {
    	long lMaxPrintTime = -1;
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select nmaxprint from print_manybillrelation a,sett_vtransaction b";
    		strSQL += " where ( a.ntransactiontypeid=b.transactiontypeid or a.ntransactiontypeid = b.operationtypeid ) ";
    		strSQL += " and a.nbillid=" + lBillID;
    		strSQL += " and a.ntempid=" + lTemplateID;
    		strSQL += " and b.id=" + lTransID;
    		strSQL += " and b.transno='" + strTransno + "'";
    		
    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				lMaxPrintTime = transRS.getLong("nmaxprint");
			}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lMaxPrintTime;
    }
    
    
    
    /**
     * 查询某交易已打印某单据次数
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public long getPrintTime (long lTransID,long lBillID,long lTemplateID) throws Exception
    {
    	long lPrintTime = -1;
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select nprintnum from print_printrecord c";
    		strSQL += " where c.nprintcontentid=" + lTransID;
    		strSQL += " and c.nbillid=" + lBillID;
    		strSQL += " and c.ntempid=" + lTemplateID;
    		
    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				lPrintTime = transRS.getLong("nprintnum");
			}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lPrintTime;
    }
    
    /**
     * 查询某交易已打印某单据次数
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public long getPrintXMLTime (PrintXMLTimeInfo printXMLInfo, String XMLName) throws Exception
    {
    	long lPrintTime = 0;
    	String strSQL = "";
    	
    	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
    	try
    	{
    		//initDAO();
    		conn = Database.getConnection();
    		
    		strSQL = " select nprintnum from print_printrecord ";
    		strSQL += " where nprintcontentid=" + printXMLInfo.getId();
    		strSQL += " and nofficeid=" + printXMLInfo.getOfficeID();
    		strSQL += " and ncurrency=" + printXMLInfo.getCurrencyID();
    		strSQL += " and ndeptid=" + printXMLInfo.getDeptID();
    		
    		if(printXMLInfo.getOpretionType() > 0)
    		{
    			strSQL += " and ntransactiontypeid=" + printXMLInfo.getOpretionType();
    		}
    		else
    		{
    			strSQL += " and ntransactiontypeid=" + printXMLInfo.getTransTypeID();
    		}
    		
    		strSQL += " and stempname= '"+XMLName+"' ";
    		strSQL += " and nmoduleid=" + printXMLInfo.getModule();
    		pstmt = conn.prepareStatement(strSQL);
    		rset = pstmt.executeQuery();
			
			while (rset.next())
			{
				lPrintTime = rset.getLong("nprintnum");
			}
			
			//finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				//finalizeDAO();
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
				if (pstmt != null)
				{
					pstmt.close();
					pstmt = null;
				}
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lPrintTime;
    }
    /**
     * 查询某交易已打印某单据次数
     */
    public long getPrintXMLTime (PrintXMLTimeInfo printXMLInfo) throws Exception
    {
    	long lPrintTime = -1;
    	String strSQL = "";
    	
    	Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
    	try
    	{
    		conn = Database.getConnection();
    		
    		strSQL = " select min(nprintnum) nprintnum from print_printrecord ";
    		strSQL += " where nprintcontentid=" + printXMLInfo.getId();
    		strSQL += " and nofficeid=" + printXMLInfo.getOfficeID();
    		strSQL += " and ncurrency=" + printXMLInfo.getCurrencyID();
    		strSQL += " and ndeptid=" + printXMLInfo.getDeptID();
    	    strSQL += " and ntransactiontypeid=" + printXMLInfo.getTransTypeID();	
    		strSQL += " and nmoduleid=" + printXMLInfo.getModule();
    		
    		pstmt = conn.prepareStatement(strSQL);
    		rset = pstmt.executeQuery();
			
			while (rset.next())
			{
				lPrintTime = rset.getLong("nprintnum");
			}

    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
				if (pstmt != null)
				{
					pstmt.close();
					pstmt = null;
				}
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return lPrintTime;
    }
    
    /**
     * 判断某交易的某单据再打印申请是否确认（已审批）
     * 财务公司内部的再打印申请需审批通过
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @return
     * @throws Exception
     * 同部门同币种同办事处的人申请同部门的人都可以打印一次
     */
    public boolean isUseablePrintApply(PrintXMLTimeInfo printXMLInfo, String XMLName) throws Exception
    {
    	boolean isUseable = false;
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL = " select a.ndeptid, a.nstatusid from print_printapply a ";
    		strSQL += " where a.nprintcontentid=" + printXMLInfo.getId();
    		
    		if(printXMLInfo.getTransNo() != null && !printXMLInfo.getTransNo().equals(""))
    		{
    			strSQL += " and a.nprintcontentno='" + printXMLInfo.getTransNo() + "'";
    		}
    			
    		//Boxu add 2007-7-17
    		strSQL += " and nofficeid = "+printXMLInfo.getOfficeID()+" ";
    		strSQL += " and ncurrency = "+printXMLInfo.getCurrencyID()+" ";
    		strSQL += " and ndeptid = "+printXMLInfo.getDeptID()+" ";
    		
    		if(printXMLInfo.getOpretionType() > 0)
    		{
    			strSQL += " and ntypeid = " + printXMLInfo.getOpretionType();
    		}
    		else
    		{
    			strSQL += " and ntypeid = " + printXMLInfo.getTransTypeID();
    		}
    		
    		strSQL += " and stempname = '"+XMLName+"' ";
    		strSQL += " and nmoduleid = "+printXMLInfo.getModule()+" ";
    		strSQL += " and nstatusid = "+VOUConstant.VoucherStatus.APPROVED+" ";
    		//System.out.println("isNeedSeal sql:"+strSQL);
    		
    		transPS = prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				//lDeptID = transRS.getLong("ndeptid");
//				待打印申请审批状态确认后修改 add zyyao 2007-8-4
				isUseable = true;
				//isNeedSeal = transRS.getLong("nisneedseal");
			}
			
//			if ((lDeptID==VOUConstant.IsEbankApply.NO && lStatusID == VOUConstant.VoucherStatus.APPROVED)
//					|| (lDeptID==VOUConstant.IsEbankApply.YES && (lStatusID == VOUConstant.VoucherStatus.SIGN || isNeedSeal == VOUConstant.IsChapter.NO)))
//			{
//				isUseable = true;
//			}
			
			//待打印申请审批状态确认后修改 add Boxu 2007-7-17
			//if (lStatusID == VOUConstant.VoucherStatus.APPROVED)
			//{
			//	isUseable = true;
			//}
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	
    	return isUseable;
    }
    
    /**
     * 更新打印申请记录
     * 再打印申请对应交易单据在打印之后，更新该申请记录为已使用
     * 如若要再打印则需要继续申请
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @throws Exception
     */
    public void updatePrintApply(PrintApplyInfo printApply) throws Exception
    {
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL =  " update print_printapply set ";
    		strSQL += " nstatusid = " + VOUConstant.VoucherStatus.USED;
    		strSQL += " where nprintcontentid = " + printApply.getNTransID();
    		
    		if(printApply.getNTransNo() != null && !printApply.getNTransNo().equals(""))
    		{
    			strSQL += " and nprintcontentno = '" + printApply.getNTransNo() + "' ";
    		}
    		
    		
    		//待确认"财务公司"和"网银"状态后修改此地方 Boxu add
    		//strSQL += " and( (nstatusid=" + VOUConstant.VoucherStatus.APPROVED + " and ndeptid="+VOUConstant.IsEbankApply.NO+")" +
    		//		    " or (nstatusid="+VOUConstant.VoucherStatus.SIGN+" and ndeptid="+VOUConstant.IsEbankApply.YES+") )";
    		
    		strSQL += " and nofficeid = "+printApply.getNOfficeID()+" ";
    		strSQL += " and ncurrency = "+printApply.getNCurrencyID()+" ";
    		strSQL += " and ndeptid = "+printApply.getNDeptID()+" ";
    		strSQL += " and ntypeid = "+printApply.getLTransTypeID()+" ";
    		strSQL += " and stempname = '"+printApply.getStrBillName()+"' ";
    		strSQL += " and nmoduleid = "+printApply.getModuleId()+" ";
    		strSQL += " and nstatusid=" + VOUConstant.VoucherStatus.APPROVED+" ";
    		transPS = prepareStatement(strSQL);
			transPS.executeUpdate();
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    }
    /**
     * 更新网银打印申请记录
     * 再打印申请对应交易单据在打印之后，更新该申请记录为已使用
     * 如若要再打印则需要继续申请
     * @param lTransID
     * @param lBillID
     * @param lTemplateID
     * @throws Exception
     */
    public void updateebankPrintApply(PrintApplyInfo printApply) throws Exception
    {
    	String strSQL = "";
    	
    	try
    	{
    		initDAO();
    		
    		strSQL =  " update ebank_printapply set ";
    		strSQL += " nstatusid = " + VOUConstant.VoucherStatus.USED;
    		strSQL += " where nprintcontentid = " + printApply.getNTransID();
    		
    		if(printApply.getNTransNo() != null && !printApply.getNTransNo().equals(""))
    		{
    			strSQL += " and nprintcontentno = '" + printApply.getNTransNo() + "' ";
    		}
    		
    		//待确认"财务公司"和"网银"状态后修改此地方 Boxu add
    		//strSQL += " and( (nstatusid=" + VOUConstant.VoucherStatus.APPROVED + " and ndeptid="+VOUConstant.IsEbankApply.NO+")" +
    		//		    " or (nstatusid="+VOUConstant.VoucherStatus.SIGN+" and ndeptid="+VOUConstant.IsEbankApply.YES+") )";
    		
    		strSQL += " and nofficeid = "+printApply.getNOfficeID()+" ";
    		strSQL += " and ncurrency = "+printApply.getNCurrencyID()+" ";
    		strSQL += " and ndeptid = "+printApply.getNDeptID()+" ";
    		strSQL += " and ntypeid = "+printApply.getLTransTypeID()+" ";
    		strSQL += " and stempname = '"+printApply.getStrBillName()+"' ";
    		strSQL += " and moduleid = "+printApply.getModuleId()+" ";
    		strSQL += " and nstatusid=" + VOUConstant.VoucherStatus.APPROVED+" ";
    		transPS = prepareStatement(strSQL);
			transPS.executeUpdate();
			
			finalizeDAO();
    	}
    	catch ( Exception ex ) 
    	{
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    }
    protected long getPrint_printRecordID() throws Exception
	{
		long id = -1;
		initDAO();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_Print_printRecordID.nextval nextid from dual");
		transPS = prepareStatement(sb.toString());
		ResultSet rs = transPS.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		finalizeDAO();
		return id;
	}
    
    
    public static void main (String[] args)
    {
    	QueryPrintDao dao = new QueryPrintDao();
    	Collection coll = null;

    	/*String templateContent = " &&&  &&&  &&& ";
    	String[] strCodes = {"01","02","03"};
    	PrintInfoVo PIVo=new PrintInfoVo();
    	PIVo.setYear("2006");
    	PIVo.setMonth("06");
    	PIVo.setDay("06");*/
    	
    	String strTransIDs = "1512";
    	String[] templateIDs = {"8","9"};
    	
    	try {
    		/*String[] str1 = dao.fileList(strURL,strSeparator);
    		for(int i = 0;i < str1.length;i++)
    		{
    			System.out.println(str1[i]);
    		}*/
			//int t = dao.getTemplateContent(s,str);
			//System.out.println("t:"+t);
    		//String str = dao.mergeContent(templateContent,strCodes,PIVo);
    		//System.out.println(str);
    		//coll = dao.getPrintTemplateContent(strTransIDs,templateIDs,1,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

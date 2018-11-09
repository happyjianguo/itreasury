package com.iss.itreasury.evoucher.printcontrol.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.print.dataentity.PrintXMLTimeInfo;
import com.iss.itreasury.evoucher.print.dataentity.QueryPrintConditionInfo;
import com.iss.itreasury.evoucher.printcontrol.dataentity.PrintApplyInfo;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;    

public class PrintApplyDao extends VoucherDAO{

	public PrintApplyDao() {
		super("");
	}
	public PrintApplyDao(Connection conn) {
		super("",conn);
	} 
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;

	public PageLoader findForApplyTransInfo (QueryPrintConditionInfo info) throws Exception
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		
		m_sbSelect.append(" a.id,min(a.officeid),min(a.currencyid),a.transno,a.transactiontypeid,a.execute dttrans," +
				"a.inputuserid,a.abstract,a.payaccountid,a.payaccountno,a.receiveaccountid,a.receiveaccountno ");
		m_sbFrom.append(" sett_vtransaction a,print_printrecord b,print_billrelation c");
		m_sbWhere.append(" ( c.ntransactiontypeid=a.transactiontypeid or c.ntransactiontypeid = a.operationtypeid ) and b.ntransactiontypeid=c.ntransactiontypeid and b.nbillid=c.nbillid and b.ntempid=c.ntempid");
		m_sbWhere.append(" and b.nprintnum>=c.nmaxprint and a.id=b.nprintcontentid");
		
		m_sbWhere.append(" and (a.statusid  not  in("+SETTConstant.TransactionStatus.DELETED+","
    			+SETTConstant.TransactionStatus.TEMPSAVE+","+SETTConstant.TransactionStatus.REFUSE+"))");
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
		
		m_sbWhere.append(" group by a.id,a.transno,a.transactiontypeid,a.execute,a.inputuserid,a.abstract," +
				"a.payaccountid,a.payaccountno,a.receiveaccountid,a.receiveaccountno");
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
	
	//Boxu add 2007-7-17
	public PageLoader findApplyTransInfo (QueryPrintConditionInfo info) throws Exception
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		
		m_sbSelect.append(" a.id, min(a.officeid), min(a.currencyid), a.transno, a.transactiontypeid ");
		m_sbSelect.append(" , a.execute dttrans, a.inputuserid, a.abstract, a.payaccountid, a.payaccountno ");
		m_sbSelect.append(" , a.receiveaccountid, a.receiveaccountno ,a.receiveamount TransAmount");
		m_sbSelect.append(" , a.operationtypeid OperationTypeID, b.nprintnum PrintNum ");
		
		m_sbFrom.append(" sett_vtransaction a, print_printrecord b, print_billrelation c ");
		
		m_sbWhere.append(" ( c.ntransactiontypeid = a.transactiontypeid or c.ntransactiontypeid = a.operationtypeid ) and b.ntransactiontypeid = c.ntransactiontypeid ");
		m_sbWhere.append(" and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ");
		m_sbWhere.append(" and b.nprintnum >= c.nmaxprint and a.id = b.nprintcontentid ");
		
		//新加看是否已经有申请了 add by zyyao 2007-8-6
		//boxu update 2007-8-15
		//m_sbWhere.append(" and a.id not in ( select distinct d.nprintcontentid from print_printapply d where d.nstatusid in ("+VOUConstant.VoucherStatus.APPROVALING+", "+VOUConstant.VoucherStatus.APPROVED+", "+VOUConstant.VoucherStatus.SAVE+") and d.nofficeid = "+info.getOfficeID()+" and d.ncurrency = "+info.getCurrencyID()+" and d.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" ) ");
		//xwhe upate 2009-06-13 增加一个状态判断，如果该笔业务拒绝完后也不让它在补打申请里面查询，目的是防止在拒绝业务查询重新提交，和补打申请两笔同样的申请
		m_sbWhere.append(" and a.transno not in ( select distinct d.nprintcontentno from print_printapply d where d.nstatusid in ("+VOUConstant.VoucherStatus.APPROVALING+", "+VOUConstant.VoucherStatus.APPROVED+", "+VOUConstant.VoucherStatus.SAVE+", "+VOUConstant.VoucherStatus.REFUSE+" ) and d.nofficeid = "+info.getOfficeID()+" and d.ncurrency = "+info.getCurrencyID()+" and d.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" ) ");
		
		m_sbWhere.append(" and c.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" and b.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" ");
		
		m_sbWhere.append(" and ( a.statusid not in ("+SETTConstant.TransactionStatus.DELETED+","
    												 +SETTConstant.TransactionStatus.TEMPSAVE+","
    												 +SETTConstant.TransactionStatus.REFUSE+") ) ");
		
		//根据页面条件增加查询条件
    	if(info.getTransactionTypeIDs() != null && info.getTransactionTypeIDs().length() > 0) {
    		m_sbWhere.append(" and ( a.transactiontypeid in (" + info.getTransactionTypeIDs() + " ) or a.operationtypeid in (" + info.getTransactionTypeIDs() + " ) ) ");
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
    	if(info.getOfficeID() > 0)
    	{
    		m_sbWhere.append(" and a.officeid = " + info.getOfficeID());
    	}
    	if(info.getCurrencyID() > 0)
    	{
    		m_sbWhere.append(" and a.currencyid = " + info.getCurrencyID());
    	}
    	
		m_sbWhere.append(" group by a.id, a.transno, a.transactiontypeid, a.execute, a.inputuserid ");
		m_sbWhere.append(" , a.abstract, a.payaccountid, a.payaccountno, a.receiveaccountid, a.receiveaccountno ,a.receiveamount");
		m_sbWhere.append(" , a.operationtypeid, b.nprintnum ");
		
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
	
	//boxu update 2007-8-15
	public Collection getPrintDetailByTransID(long lTransID, long officeID, long currencyID) throws Exception
	{
		Vector vector = new Vector();
		PrintApplyInfo printApplyInfo = null;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL =  " select a.transno, a.transactiontypeid, a.operationtypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , c.nprintnum, c.stempname, b.sbilltypename ";
			strSQL += " from sett_vtransaction a, print_billrelation b, print_printrecord c ";
			strSQL += " where ( b.ntransactiontypeid = a.transactiontypeid or b.ntransactiontypeid = a.operationtypeid ) and a.id = c.nprintcontentid ";
			strSQL += " and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ";
			strSQL += " and a.id = " + lTransID;
			
			strSQL += " and c.nprintnum >= b.nmaxprint ";

			//加入查询条件 打印部门为"财务公司" 和 办事处ID 和币种ID
			strSQL += " and c.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" and b.ndeptid = "+VOUConstant.PrintSection.FINANCECOMPANY+" ";
			strSQL += " and b.nofficeid = "+officeID+" and b.ncurrency = "+currencyID+" ";
			strSQL += " and c.nofficeid = "+officeID+" and c.ncurrency = "+currencyID+" ";
			strSQL += " order by b.sbilltypename desc ";
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				printApplyInfo = new PrintApplyInfo();
				
				printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				//特殊交易类型
				printApplyInfo.setOperationtypeid(transRS.getLong("operationtypeid")); 
				
				vector.add(printApplyInfo);
			}
			
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage());
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
	
	//新增加特殊交易处理 boxu add 2007-9-6
	public Collection getPrintDetail(PrintXMLTimeInfo printInfo) throws Exception
	{
		Vector vector = new Vector();
		PrintApplyInfo printApplyInfo = null;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL =  " select a.transno, a.transactiontypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , c.nprintnum, c.stempname, b.sbilltypename ";
			strSQL += " from sett_vtransaction a, print_billrelation b, print_printrecord c ";
			strSQL += " where ( b.ntransactiontypeid = a.transactiontypeid or b.ntransactiontypeid = a.operationtypeid ) and a.id = c.nprintcontentid ";
			strSQL += " and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ";
			strSQL += " and a.id = " + printInfo.getId();
			strSQL += " and a.transno = " + printInfo.getTransNo();
			
			if(printInfo.getOpretionType() > 0)
			{
				strSQL += " and b.ntransactiontypeid = " + printInfo.getOpretionType();
				strSQL += " and a.operationtypeid = " + printInfo.getOpretionType();
			}
			else
			{
				strSQL += " and b.ntransactiontypeid = " + printInfo.getTransTypeID();
				strSQL += " and a.transactiontypeid = " + printInfo.getTransTypeID();
			}
			
			strSQL += " and c.nprintnum >= b.nmaxprint ";

			//加入查询条件 打印部门为"财务公司" 和 办事处ID 和币种ID
			strSQL += " and c.ndeptid = "+ printInfo.getDeptID() +" and b.ndeptid = "+ printInfo.getDeptID() +" ";
			strSQL += " and b.nofficeid = "+ printInfo.getOfficeID() +" and b.ncurrency = "+ printInfo.getCurrencyID() +" ";
			strSQL += " and c.nofficeid = "+ printInfo.getOfficeID() +" and c.ncurrency = "+ printInfo.getCurrencyID() +" ";
			strSQL += " order by b.sbilltypename desc ";
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				printApplyInfo = new PrintApplyInfo();
				
				printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				
				vector.add(printApplyInfo);
			}
			
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage());
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
	
	//审批拒绝业务 zyyao update 2007-8-15
	public Collection getPrintDetailByTransID(long lTransID, long officeID, long currencyID,long ndeptid) throws Exception
	{
		Vector vector = new Vector();
		PrintApplyInfo printApplyInfo = null;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL =  " select a.transno, a.transactiontypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , c.nprintnum, c.stempname, b.sbilltypename ";
			strSQL += " from sett_vtransaction a, print_billrelation b, print_printrecord c ";
			strSQL += " where a.transactiontypeid = b.ntransactiontypeid and a.id = c.nprintcontentid ";
			strSQL += " and b.stempname = c.stempname and b.nmoduleid = c.nmoduleid ";
			strSQL += " and a.id = " + lTransID;
			
			strSQL += " and c.nprintnum >= b.nmaxprint ";

			//加入查询条件 打印部门为"财务公司" 和 办事处ID 和币种ID
			strSQL += " and c.ndeptid = "+ndeptid+" and b.ndeptid = "+ndeptid+" ";
			strSQL += " and b.nofficeid = "+officeID+" and b.ncurrency = "+currencyID+" ";
			strSQL += " and c.nofficeid = "+officeID+" and c.ncurrency = "+currencyID+" ";
			strSQL += " order by b.sbilltypename desc ";
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				printApplyInfo = new PrintApplyInfo();
				
				printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				
				vector.add(printApplyInfo);
			}
			
			finalizeDAO();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.getMessage());
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
	/**
	 * 保存再打印申请信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long savePrintApplayInfo(PrintApplyInfo info) throws Exception
	{
		long lResult = -1;
		long lID = -1;
		String strSQL = "";
		
		try 
		{
			initDAO();
			
			strSQL = " select nvl(max(id)+1, 1) oID from print_printapply ";
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next())
			{
				lID = transRS.getLong("oID");
			}
			
			if (transRS != null)
			{
				transRS.close();
				transRS = null;
			}
			if (transPS != null)
			{
				transPS.close();
				transPS = null;
			}
			
			strSQL =  " insert into print_printapply ";
			strSQL += " ( ";
			strSQL += " ID, NOFFICEID, NCURRENCY, NPRINTCONTENTID, NPRINTCONTENTNO ";
			strSQL += " , NDEPTID, NSTATUSID, ISCHAPTER, NCLIENTID, NINPUTUSERID ";
			strSQL += " , NINPUTDATE, NTYPEID, STEMPNAME, NMODULEID ";
			strSQL += " ) ";
			strSQL += " values(?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?) ";
			
			transPS = transConn.prepareStatement(strSQL);
			
			int index = 0;
			info.setId(lID);
			transPS.setLong(++index , lID);
			transPS.setLong(++index , info.getNOfficeID());
			transPS.setLong(++index , info.getNCurrencyID());
			transPS.setLong(++index , info.getNTransID());
			transPS.setString(++index , info.getNTransNo());
			transPS.setLong(++index , info.getNDeptID());
			transPS.setLong(++index , VOUConstant.VoucherStatus.APPROVALING);
			transPS.setLong(++index , info.getIschapter());
			transPS.setLong(++index , info.getNclientid());
			transPS.setLong(++index , info.getNInputUserId());
			
			if(info.getOperationtypeid() > 0)
			{
				transPS.setLong(++index , info.getOperationtypeid());
			}
			else
			{
				transPS.setLong(++index , info.getLTransTypeID());
			}
			
			transPS.setString(++index , info.getStrTempName());
			transPS.setLong(++index , info.getModuleId());
			
			lResult = transPS.executeUpdate();
		}
		catch( Exception ex ) 
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
    	
		return lResult;
	}
	
	/**
	 * 审批修改打印申请信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateApplyStatus(long lID,long statusID) throws Exception
	{
		String strSQL = "";
		long lReturn = -1;
		
		try 
		{
			initDAO();
			
			strSQL = " update print_printapply set nstatusid = "+statusID+" where id = "+lID+" ";
			
			System.out.print("更新SQL=="+strSQL);
			
			prepareStatement(strSQL);
			lReturn = executeUpdate();
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
    	
    	return lReturn;
	}
	/**
	 * 取消网行的审批
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateebankApplyStatus(long lID,long statusID) throws Exception
	{
		String strSQL = "";
		long lReturn = -1;
		
		try 
		{
			initDAO();
			
			strSQL = " update ebank_printapply set nstatusid = "+statusID+" where nprintid = "+lID+" ";
			
			System.out.print("更新SQL=="+strSQL);
			
			prepareStatement(strSQL);
			lReturn = executeUpdate();
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
    	
    	return lReturn;
	}
	
	public Collection getPrintDetailByTransIDforsp(long lTransID, String ids) throws Exception
    {
	    Vector vector;
	    vector = new Vector();
	    PrintApplyInfo printApplyInfo = null;
	    String strSQL = "";
	    try
	    {
	        initDAO();
	        strSQL =  " select distinct a.transno, a.transactiontypeid, a.currencyid, a.officeid, b.ndeptid ";
			strSQL += " , decode(c.nprintnum,null,0,c.nprintnum) nprintnum , b.stempname, b.sbilltypename ";
			strSQL += " from sett_vtransaction a, print_billrelation b, print_printrecord c,print_printapply f ";
			strSQL += " where ( b.ntransactiontypeid = a.transactiontypeid or b.ntransactiontypeid = a.operationtypeid ) and a.id = c.nprintcontentid and a.id = f.nprintcontentid ";
			strSQL += "  and  a.id = " + lTransID;
			strSQL += " and c.ndeptid = f.ndeptid and  c.nofficeid = f.nofficeid and c.ncurrency=f.ncurrency  and f.stempname = c.stempname(+) and f.nmoduleid = c.nmoduleid  and f.ntypeid = c.ntransactiontypeid ";
			strSQL += " and b.ndeptid = f.ndeptid  and  b.nofficeid = f.nofficeid and b.ncurrency=f.ncurrency  and f.stempname = b.stempname and f.nmoduleid = b.nmoduleid  and f.ntypeid = b.ntransactiontypeid ";
			strSQL += " and f.id in(" + ids + " )";
			strSQL += " order by b.stempname ";
	        transPS = transConn.prepareStatement(strSQL);
	        transRS = transPS.executeQuery();
	      	while (transRS.next())
	        {
	            printApplyInfo = new PrintApplyInfo();
	            printApplyInfo.setNTransNo(transRS.getString("transno"));
				printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
				printApplyInfo.setNCurrencyID(transRS.getLong("currencyid"));
				printApplyInfo.setNOfficeID(transRS.getLong("officeid"));
				printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
				printApplyInfo.setLPrintNum(transRS.getLong("nprintnum"));
				printApplyInfo.setStrBillName(transRS.getString("sbilltypename"));
				printApplyInfo.setStrTempName(transRS.getString("stempname"));
				vector.add(printApplyInfo);
	        }
	
	        finalizeDAO();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        log.error(e.getMessage());
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

	public PrintApplyInfo getPrintaplyByIDS(String ids) throws Exception
	{
	    PrintApplyInfo printApplyInfo = null;
	    String strSQL = "";
	    try
	    {
	        initDAO();
	        strSQL = " select a.*,b.* from print_printapply a, sett_vtransaction b  where a.nprintcontentid = b.id and (a.ntypeid = b.transactiontypeid or a.ntypeid = b.operationtypeid) and a.id in(" + ids + " )";
	        transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			while (transRS.next())
	        {
	            printApplyInfo = new PrintApplyInfo();
	            printApplyInfo.setNTransID(transRS.getLong("nprintcontentid"));
	            printApplyInfo.setNTransNo(transRS.getString("nprintcontentno"));
	            printApplyInfo.setLTransTypeID(transRS.getLong("transactiontypeid"));
	            printApplyInfo.setNstatusid(transRS.getLong("nstatusid"));
	            printApplyInfo.setReceiveamount(transRS.getDouble("receiveamount"));
	            printApplyInfo.setNInputUserId(transRS.getLong("ninputuserid")); 
	            //特殊交易类型
	            printApplyInfo.setOperationtypeid(transRS.getLong("operationtypeid"));
	            printApplyInfo.setNInputDate(transRS.getTimestamp("ninputdate"));
	            printApplyInfo.setNDeptID(transRS.getLong("ndeptid"));
	        }
	
	        finalizeDAO();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        log.error(e.getMessage());
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
	    return printApplyInfo;
	}
}

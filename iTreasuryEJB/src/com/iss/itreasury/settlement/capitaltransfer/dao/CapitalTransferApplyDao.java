package com.iss.itreasury.settlement.capitaltransfer.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.capitaltransfer.dataentity.CapitalTransferInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;

public class CapitalTransferApplyDao extends SettlementDAO{
	
	StringBuffer sbSelect = null;
	StringBuffer sbFrom = null;
    StringBuffer sbWhere = null;
	/**
	 * 根据资金调拨id查询申请记录
	 * @param ApplyID
	 * @return
	 * @throws Exception
	 */
	public CapitalTransferInfo findByID ( long ApplyID ) throws Exception {
		CapitalTransferInfo info = new CapitalTransferInfo();
		
		try {
			initDAO();
			
			String strSQL = " select id,currencyID,applyno,paybankid,receivebankid,amount,dtinput,dtremit," +
					"dtreceive,dtlimitstart,dtlimitend,interestrate,interest,commission,inputuserid,purpose,abstract,statusid " +
					" from sett_capitaltransferapply" +
					" where id = " + ApplyID;
			
			Log.print("根据id取得资金调拨申请记录SQL：" + strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while (transRS.next()) {
				info.setID(transRS.getLong("id"));
				info.setApplyNo(transRS.getLong("applyno"));
				info.setPayBankID(transRS.getLong("paybankid"));
				info.setReceiveBankID(transRS.getLong("receivebankid"));
				info.setAmount(transRS.getDouble("amount"));
				info.setDtRemit(transRS.getTimestamp("dtremit"));
				info.setDtReceive(transRS.getTimestamp("dtreceive"));
				info.setDtLimitStart(transRS.getTimestamp("dtlimitstart"));
				info.setDtLimitEnd(transRS.getTimestamp("dtlimitend"));
				info.setInrerestRate(transRS.getDouble("interestrate"));
				info.setInterest(transRS.getDouble("interest"));
				info.setCommission(transRS.getDouble("commission"));
				info.setInputUserID(transRS.getLong("inputuserid"));
				info.setDtInput(transRS.getTimestamp("dtinput"));
				info.setPurpose(transRS.getString("purpose"));
				info.setAbstract(transRS.getString("abstract"));
				info.setStatusID(transRS.getLong("statusid"));
			}
			
			if(transRS != null){
				transRS.close();
				transRS = null;
			}
			if(transPS != null){
				transPS.close();
				transPS = null;
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
			Log.print(ex);
			throw new IException("Gen_E001");
		} finally {
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
		
		return info;
	}
	
	/**
	 * 增加新的资金划拨信息
	 * @param info
	 * @return
	 */
	public long add(CapitalTransferInfo info) throws Exception{
		long result = -1;
		long ApplyID = -1;
		String ApplyNo = "";
		String strSQL = "";
		
		try{
			initDAO();
			
			/**
			 * 取得新纪录的ID
			 */
			Log.print("---------get new apply id---------");
			strSQL = "select nvl(max(ID)+1,1) oID from sett_capitaltransferapply";
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next()){
				ApplyID = transRS.getLong("oID");
			}
			
			if(transRS != null){
				transRS.close();
				transRS = null;
			}
			if(transPS != null){
				transPS.close();
				transPS = null;
			}
			
			Log.print("the new apply id is " + ApplyID);
			
			/**
			 * 取新纪录申请编号
			 */
			strSQL = "select NVL(MAX(applyno),TO_CHAR(SYSDATE,'yyyymmdd') || '0000')+1 AS applyno " +
					"from sett_capitaltransferapply " +
					"WHERE applyno > TO_CHAR(SYSDATE,'yyyymmdd') || '0000'";
			
			transPS = transConn.prepareStatement(strSQL);
			transRS = transPS.executeQuery();
			
			while(transRS.next()){
				ApplyNo = transRS.getString("applyno");
			}
			
			if(transRS != null){
				transRS.close();
				transRS = null;
			}
			if(transPS != null){
				transPS.close();
				transPS = null;
			}
			
			Log.print("插入新的资金划拨申请记录----------------");
			strSQL = "insert into sett_capitaltransferapply ";
			strSQL += "( id,currencyID,officeID,applyno,paybankid,receivebankid,amount,dtinput,dtremit,dtreceive,dtlimitstart" +
					",dtlimitend,interestrate,interest,commission,inputuserid,purpose,abstract,statusid ) ";
			strSQL += "values ";
			strSQL += "(?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?) ";
			Log.print("新增申请记录SQL：" + strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			int index = 1;
			transPS.setLong(index++ , ApplyID);
			transPS.setLong(index++ , info.getLCurrencyID());
			transPS.setLong(index++ , info.getLOfficeID());
			transPS.setString(index++ , ApplyNo);
			transPS.setLong(index++ , info.getPayBankID());
			transPS.setLong(index++ , info.getReceiveBankID());
			transPS.setDouble(index++ , info.getAmount());
			transPS.setTimestamp(index++ , info.getDtRemit());
			transPS.setTimestamp(index++ , info.getDtReceive());
			transPS.setTimestamp(index++ , info.getDtLimitStart());
			transPS.setTimestamp(index++ , info.getDtLimitEnd());
			transPS.setDouble(index++ , info.getInrerestRate());
			transPS.setDouble(index++ , info.getInterest());
			transPS.setDouble(index++ , info.getCommission());
			transPS.setLong(index++ , info.getInputUserID());
			transPS.setString(index++ , info.getPurpose());
			transPS.setString(index++ , info.getAbstract());
			transPS.setLong(index++ , SETTConstant.TransactionStatus.APPROVALING);
			
			result = transPS.executeUpdate();
			
			if(transRS != null){
				transRS.close();
				transRS = null;
			}
			if(transPS != null){
				transPS.close();
				transPS = null;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			Log.print(ex);
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
		
		return ApplyID;
	}
	
	public long update( CapitalTransferInfo info ) throws Exception {
		long result = -1;
		String strSQL = "";
		
		try {
			initDAO();
			
			strSQL = " update sett_capitaltransferapply set paybankid=?,receivebankid=?,amount=?,dtremit=?,dtreceive=?,dtlimitstart=?," +
					"dtlimitend=?,interestrate=?,interest=?,commission=?,purpose=?,abstract=? " +
					" where id = " + info.getID();
			
			Log.print("update SQL:"+strSQL);
			
			transPS = transConn.prepareStatement(strSQL);
			int index = 1;
			transPS.setLong(index++ , info.getPayBankID());
			transPS.setLong(index++ , info.getReceiveBankID());
			transPS.setDouble(index++ , info.getAmount());
			transPS.setTimestamp(index++ , info.getDtRemit());
			transPS.setTimestamp(index++ , info.getDtReceive());
			transPS.setTimestamp(index++ , info.getDtLimitStart());
			transPS.setTimestamp(index++ , info.getDtLimitEnd());
			transPS.setDouble(index++ , info.getInrerestRate());
			transPS.setDouble(index++ , info.getInterest());
			transPS.setDouble(index++ , info.getCommission());
			transPS.setString(index++ , info.getPurpose());
			transPS.setString(index++ , info.getAbstract());
			
			result = transPS.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.print(ex);
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
		return result;
	}
	
	public long updateStatusID (long lID,long lStatusID) throws Exception
	{
		long lReturn = -1;
		String strSQL = "";
		
		try {
			initDAO();
			
			strSQL = "update sett_capitaltransferapply set statusid=" + lStatusID;
			strSQL += " where  id=" + lID;
			
			transPS = transConn.prepareStatement(strSQL);
			lReturn = transPS.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Log.print(ex);
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
	
	public long delCapitalTransInfo(CapitalTransferInfo applyInfo) throws Exception
	{
		long lReturn = -1;
		String strSQL = "";
		
		try {
			initDAO();
			
			strSQL = "update sett_capitaltransferapply set statusid=" + SETTConstant.TransactionStatus.DELETED;
			strSQL += " where id=" + applyInfo.getID();
			
			transPS = transConn.prepareStatement(strSQL);
			lReturn = transPS.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Log.print(ex);
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
	
	public void getLinkTransSQL () throws Exception {
		sbSelect = new StringBuffer();
	    sbFrom = new StringBuffer();
	    sbWhere = new StringBuffer();
	    
	    sbSelect.append(" a.id,a.applyno,a.paybankid,a.receivebankid,a.amount,a.dtremit,a.dtreceive,a.purpose ");
	    sbFrom.append(" sett_capitaltransferapply a");
	    sbWhere.append(" statusid="+SETTConstant.TransactionStatus.APPROVALING);
	    
	    System.out.println("链接查找资金划拨业务查询SQL：select " + sbSelect.toString() + " from " + sbFrom.toString() + " where " + sbWhere.toString());
	}
	
	public PageLoader getLinkTrans () throws Exception {
		getLinkTransSQL();
		
		PageLoader pageLoader = (PageLoader)
        com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(
                new AppContext(),
                sbFrom.toString(),
                sbSelect.toString(),
                sbWhere.toString(),
                (int)Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.settlement.capitaltransfer.dataentity.CapitalTransferInfo",
                null);
		
		return pageLoader;
	}
	
	public void getApprovalTransSQL (CapitalTransferInfo conditionInfo,long userID) throws Exception {
		sbSelect = new StringBuffer();
	    sbFrom = new StringBuffer();
	    sbWhere = new StringBuffer();

	    String sql = "";
	    sql="(SELECT c.*,-1 moneysegment,-1 approvalid from sett_capitaltransferapply c";
		sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
		sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+SETTConstant.TransactionType.CAPITALTRANSFER+"";
		sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
		sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
		sql+=" where c.id =d.NAPPROVALCONTENTID and c.statusid="+SETTConstant.TransactionStatus.APPROVALING+"";
		
		if(conditionInfo.getAmount()>0)
		{
			sql+=" and c.amount="+conditionInfo.getAmount();
		}
		if(conditionInfo.getReceiveBankID()>0)
		{
			sql+=" and c.receivebankid=" + conditionInfo.getReceiveBankID();
		}
		if(conditionInfo.getDtRemit()!=null)
		{
			String time = (conditionInfo.getDtRemit()).toString();
			time = time.substring(0, 10);
			sql+=" and c.dtremit= to_date('" + time + "','yyyy-mm-dd')";
		}
		
		sql+=") union ( ";
		
		sql += " select d.* from (";
		sql += " select aaa.* from (";
		sql += " select aa.*,rr.moneysegment,rr.approvalid from sett_capitaltransferapply aa,loan_approvalrelation rr";
		sql += " where rr.loantypeid="+SETTConstant.TransactionType.CAPITALTRANSFER+" and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.amount>rr.moneysegment and aa.statusid="+SETTConstant.TransactionStatus.APPROVALING;

		if(conditionInfo.getAmount()>0)
		{
			sql+=" and aa.amount="+conditionInfo.getAmount();
		}
		if(conditionInfo.getReceiveBankID()>0)
		{
			sql+=" and aa.receivebankid=" + conditionInfo.getReceiveBankID();
		}
		if(conditionInfo.getDtRemit()!=null)
		{
			String time = (conditionInfo.getDtRemit()).toString();
			time = time.substring(0, 10);
			sql+=" and aa.dtremit= to_date('" + time + "','yyyy-mm-dd')";
		}
		
		sql += " ) aaa,(";
		sql += " select aa.id,max(rr.moneysegment) maxamount from sett_capitaltransferapply aa,loan_approvalrelation rr";
		sql += " where rr.loantypeid="+SETTConstant.TransactionType.CAPITALTRANSFER+" and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.amount>rr.moneysegment and aa.statusid="+SETTConstant.TransactionStatus.APPROVALING;
	    
		if(conditionInfo.getAmount()>0)
		{
			sql+=" and aa.amount="+conditionInfo.getAmount();
		}
		if(conditionInfo.getReceiveBankID()>0)
		{
			sql+=" and aa.receivebankid=" + conditionInfo.getReceiveBankID();
		}
		if(conditionInfo.getDtRemit()!=null)
		{
			String time = (conditionInfo.getDtRemit()).toString();
			time = time.substring(0, 10);
			sql+=" and aa.dtremit= to_date('" + time + "','yyyy-mm-dd')";
		}
		
		sql += " group by aa.id ) bbb";
		sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
		sql += " loan_approvalsetting e,loan_approvalitem f";
		sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
		sql +=")";
		System.out.println("查询语句SQL^^^^^^^^^^^"+sql);
		
		sbSelect.append(" * ");
		sbFrom.append(" (" + sql + ") ");
		sbWhere.append(" 1=1");
		
	    System.out.println("待审批资金划拨业务查询SQL：select " + sbSelect.toString() + " from " + sbFrom.toString() + " where " + sbWhere.toString());
	}
	/**
	 * 得到待审批资金调拨业务
	 * @return PageLoader
	 */
	public PageLoader getApprovalTrans ( CapitalTransferInfo conditionInfo ,long lUserID) throws Exception {
		getApprovalTransSQL(conditionInfo,lUserID);
		
		PageLoader pageLoader = (PageLoader)
        com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(
                new AppContext(),
                sbFrom.toString(),
                sbSelect.toString(),
                sbWhere.toString(),
                (int)Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.settlement.capitaltransfer.dataentity.CapitalTransferInfo",
                null);
		
		return pageLoader;
	}
	
	public static void main(String [] args){
		CapitalTransferApplyDao dao = new CapitalTransferApplyDao();
		try {
			System.out.println(dao.findByID(-1)==null);
			System.out.println(dao.findByID(-1).getID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*
 * Created on 2004-11-15
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.loan.discount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.bill.draft.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.bill.draft.dataentity.TransDraftInInfo;
import com.iss.itreasury.bill.draft.dataentity.assemble.DraftStorageAssembleInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyBillDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author zntan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DiscountDao
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	/**
	 * 
	 */
	public DiscountDao()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
	}
	//added by qhzhou 2007.6.27
	public long updateDiscountCredenceStatus(long lId,long statusId)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_discountcredence  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateDiscountBillStatus error : "
						+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
	}
	
	public long updateDiscountPayFormStatus(long lId,long statusId)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update LOAN_DISCOUNTPAYFORM  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			lResult = ps.executeUpdate();
			
			strSQL = " update loan_discountcredence  set NSTATUSID = ? where discountpayform = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			lResult = ps.executeUpdate();


			if (lResult < 0) {
				Log.print(" update loan updateDiscountBillStatus error : "
						+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
	}
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updateStatusAndCheckStatus(long lId,long statusId)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			String strSQL = "update loan_discountcredence set NSTATUSID = ? where ID = ? and NSTATUSID= ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.DiscountCredenceStatus.CHECK);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updatePayFormStatusAndCheckStatus(long lId,long statusId)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			String strSQL = "update LOAN_DISCOUNTPAYFORM set NSTATUSID = ? where ID = ? and NSTATUSID= ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.DiscountCredenceStatus.CHECK);

			lResult = ps.executeUpdate();
			
			strSQL = "update loan_discountcredence set NSTATUSID = ? where discountpayform = ? and NSTATUSID= ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.DiscountCredenceStatus.CHECK);

			lResult = ps.executeUpdate();


		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	//added by qhzhou 2007.6.27
	public long updateContractBillSellStatus(long lId,long statusId)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_discountcredence  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateDiscountBillStatus error : "
						+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
	}
	
	public long findDiscountBillCountByLoanID(long lLoanID)throws IException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		long lReturn = 0;
		try
		{
			con = Database.getConnection();
			strSQL = " select count(id) countBill from loan_discountformbill where NSTATUSID > 0 and NLOANID = "+lLoanID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lReturn = rs.getLong("countBill");
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException(ex.getMessage());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return lReturn;
	}
	
	public DiscountCredenceInfo findDiscountCredenceByID(long lID)throws IException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		DiscountCredenceInfo info=new DiscountCredenceInfo();
		try
		{
			con = Database.getConnection();
			strSelect = " select *";
			strSQL = " from loan_discountcredence where id= "+lID;
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				info.setID(rs.getLong(1));
				info.setAcceptAccount(rs.getString(12));
				info.setDiscountContractID(rs.getLong(2));
				info.setInputDate(rs.getTimestamp(3));
				info.setDraftTypeID(rs.getLong(4));
				info.setDraftCode(rs.getString(5));
				info.setPublicDate(rs.getTimestamp(6));
				info.setAtTerm(rs.getTimestamp(7));
				info.setApplyClientName(rs.getString(9));
				info.setApplyBank(rs.getString(10));
				info.setAcceptClientName(rs.getString(11));
				info.setAcceptAccount(rs.getString(12));
				info.setAcceptBank(rs.getString(13));
				info.setBillAmount(rs.getDouble(14));
				info.setDiscountRate(rs.getDouble(15));
				info.setBillInterest(rs.getDouble(16));
				info.setStatusID(rs.getLong(17));
				info.setInputUserID(rs.getLong(18));
				info.setNextCheckUserID(rs.getLong(19));
				info.setFillDate(rs.getTimestamp(20));
				info.setCode(rs.getString(21));
				info.setGrantTypeID(rs.getLong(22));
				info.setAccountBankID(rs.getLong(23));
				info.setReceiveClientCode(rs.getString(24));
				info.setReceiveClientName(rs.getString(25));
				info.setRemitBank(rs.getString(26));
				info.setRemitInProvince(rs.getString(27));
				info.setRemitInCity(rs.getString(28));
				info.setGrantCurrentAccountID(rs.getLong(29));
				info.setNextCheckLevel(rs.getLong(30));

				info.setOfficeID(rs.getLong(33));
				
				info.setPurchaserInterest(rs.getDouble(37));
				info.setCurrencyID(38);	
				//放款通知单ID 20130111 wcl
				info.setDiscountpayform(rs.getLong("discountpayform"));	
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return info;
	}
	
	public DiscountCredenceInfo findDiscountPayFormByID(long lID)throws IException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		DiscountCredenceInfo info=new DiscountCredenceInfo();
		try
		{
			con = Database.getConnection();
			strSelect = " select *";
			strSQL = " from LOAN_DISCOUNTPAYFORM where id= "+lID;
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				info.setID(rs.getLong(1));
				info.setAcceptAccount(rs.getString(12));
				info.setDiscountContractID(rs.getLong(2));
				info.setInputDate(rs.getTimestamp(3));
				info.setDraftTypeID(rs.getLong(4));
				info.setDraftCode(rs.getString(5));
				info.setPublicDate(rs.getTimestamp(6));
				info.setAtTerm(rs.getTimestamp(7));
				info.setApplyClientName(rs.getString(9));
				info.setApplyBank(rs.getString(10));
				info.setAcceptClientName(rs.getString(11));
				info.setAcceptAccount(rs.getString(12));
				info.setAcceptBank(rs.getString(13));
				info.setBillAmount(rs.getDouble(14));
				info.setDiscountRate(rs.getDouble(15));
				info.setBillInterest(rs.getDouble(16));
				info.setStatusID(rs.getLong(17));
				info.setInputUserID(rs.getLong(18));
				info.setNextCheckUserID(rs.getLong(19));
				info.setFillDate(rs.getTimestamp(20));
				info.setCode(rs.getString(21));
				info.setGrantTypeID(rs.getLong(22));
				info.setAccountBankID(rs.getLong(23));
				info.setReceiveClientCode(rs.getString(24));
				info.setReceiveClientName(rs.getString(25));
				info.setRemitBank(rs.getString(26));
				info.setRemitInProvince(rs.getString(27));
				info.setRemitInCity(rs.getString(28));
				info.setGrantCurrentAccountID(rs.getLong(29));
				info.setNextCheckLevel(rs.getLong(30));

				info.setOfficeID(rs.getLong(33));
				
				info.setPurchaserInterest(rs.getDouble(37));
				info.setCurrencyID(38);	
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return info;
	}
	
	public Collection findDiscountBillByContractID(long lDiscountContractID,
			  long lPageLineCount,
			  long lPageNo,
			  long lOrderParam,
			  long lDesc) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;

		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dTotalAmount = 0; //总票据金额

		try
		{
			con = Database.getConnection();

			//计算记录总数
			strSelect = " select count(*),sum(mAmount) ";
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lDiscountContractID;

			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============");
			Log.print(lRecordCount);
			Log.print(dTotalAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / lPageLineCount;

			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) lOrderParam)
			{
				case 1 :
					strSQL += " order by nSerialNo";
					break;
				case 2 :
					strSQL += " order by sUserName";
					break;
				case 3 :
					strSQL += " order by sBank";
					break;
				case 4 :
					strSQL += " order by nIsLocal";
					break;
				case 5 :
					strSQL += " order by dtCreate";
					break;
				case 6 :
					strSQL += " order by dtEnd";
					break;
				case 7 :
					strSQL += " order by sCode";
					break;
				case 8 :
					strSQL += " order by mAmount";
					break;
				case 9 :
					strSQL += " order by nAddDays";
					break;
				case 10 :
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}

			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;

			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();

				dbill.setDiscountContractID(lDiscountContractID); //贴现标示
				dbill.setID(rs.getLong("ID")); //票据标示
				dbill.setSerialNo(rs.getLong("nSerialNo")); //序列号
				dbill.setUserName(rs.getString("sUserName")); //原始出票人
				dbill.setBank(rs.getString("sBank")); //承兑银行
				dbill.setIsLocal(rs.getLong("nIsLocal")); //承兑银行所在地（是否在本地）
				dbill.setCreate(rs.getTimestamp("dtCreate")); //出票日
				dbill.setEnd(rs.getTimestamp("dtEnd")); //到期日
				dbill.setCode(rs.getString("sCode")); //汇票号码
				dbill.setAmount(rs.getDouble("mAmount")); //汇票金额
				dbill.setAddDays(rs.getLong("nAddDays")); //节假日增加计息天数
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //汇票类型
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //贴现单位直接前手

				dbill.setCount(lRecordCount);
				dbill.setTotalAmount(dTotalAmount);
				v.add(dbill);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}

		return (v.size() > 0 ? v : null);
	
		
	}
	
	//票据入库方法
	public void saveBill(long lLoanID,long lContractID)throws Exception
	{
		
		Collection billTransDraftInInfos = null;
		ContractDao conDao=new ContractDao();
		billTransDraftInInfos = this.in_DiscountDepot(lLoanID, LOANConstant.LoanType.TX);
		TransDiscountApplyBillDAO appBiz = new TransDiscountApplyBillDAO("cra_transdiscountbill");
		if(billTransDraftInInfos != null && billTransDraftInInfos.size() > 0){
			Iterator billDraftItr = billTransDraftInInfos.iterator();
			UtilOperation utilOperation=new UtilOperation();
			while(billDraftItr.hasNext()){
				DraftStorageAssembleInfo  draftStorageAssemble = (DraftStorageAssembleInfo)billDraftItr.next();
				//设置入库交易编号
				TransDraftInInfo tmpInInfo = draftStorageAssemble.getTransDraftInInfo();
				tmpInInfo.setTransCode(utilOperation.getNewDraftTransactionNo(tmpInInfo.getOfficeID(),
						tmpInInfo.getCurrencyID(),
						BILLConstant.DraftOperationType.DraftIn));
				appBiz.saveDraftIn(draftStorageAssemble);
			}			
			//2.需更新该合同票据的卖出状态
			conDao.updateContractBillSellStatus(lContractID, Constant.YesOrNo.YES);			
		}
	}
	
    /**
	 * 得到贴现入库信息
	 * @param id 票据id
	 * @param draftOperationType 操作类型（贴现）
	 * @param draftInOrOut 入、出库类型
	 * @return 票据入库对应需要添加的数据集
	 * @throws IException
	 */
	public Collection in_DiscountDepot(long lApplyID,long lDiscountTypeID) 
	throws IException
	{
		Vector v=new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sqlBuff = null;
		
		try
		{
			con = Database.getConnection();
			sqlBuff = new StringBuffer();
			sqlBuff.append(" select b.ncurrencyid, " + "\n");
			sqlBuff.append("        b.nofficeid, " + "\n");
			sqlBuff.append("        b.ninputuserid, " + "\n");
			sqlBuff.append("        b.scontractcode, " + "\n");
			sqlBuff.append("        c.id, " + "\n");
			sqlBuff.append("        c.dtcreate, " + "\n");
			sqlBuff.append("        c.dtend, " + "\n");
			sqlBuff.append("        c.mamount, " + "\n");
			sqlBuff.append("        c.scode, " + "\n");
			sqlBuff.append("        c.sformerowner, " + "\n");
			sqlBuff.append("        b.mdiscountrate, " + "\n");
			sqlBuff.append("        b.purchaserinterestrate, " + "\n");
			sqlBuff.append("        c.mcheckamount, " + "\n");
			sqlBuff.append("        b.dtinputdate, " + "\n");
			sqlBuff.append("        a.dtdiscountdate, " + "\n");
			sqlBuff.append("        c.nislocal, " + "\n");
			sqlBuff.append("        c.nadddays, " + "\n");
			sqlBuff.append("        a.discountclientname, " + "\n");
			sqlBuff.append("        b.nborrowclientid " + "\n");
			sqlBuff.append("     from loan_loanform a, loan_contractform b, loan_discountcontractbill c " + "\n");
			sqlBuff.append("   where a.id = b.nloanid" + "\n");
			sqlBuff.append("     and c.ncontractid = b.id" + "\n");
			sqlBuff.append("     and c.nstatusid = " + Constant.RecordStatus.VALID + "\n");
			sqlBuff.append("     and b.ntypeid=" + lDiscountTypeID + "\n");
			sqlBuff.append("     and a.id=" + lApplyID + "\n");
            
            System.out.println("入出库信息查询sql："+sqlBuff.toString());
            ps = con.prepareStatement(sqlBuff.toString());
            rs = ps.executeQuery();
           
            while (rs != null && rs.next())
            {
            	// 设置入库信息transDraftInInfo---------------Start-----------------------------------------------------
            	TransDraftInInfo transDraftInInfo = new TransDraftInInfo();
            	transDraftInInfo.setCurrencyID(rs.getLong("ncurrencyid"));						//交易币种
				transDraftInInfo.setOfficeID(rs.getLong("nofficeid"));							//交易办事处
				transDraftInInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);		//交易类型
				//transDraftInInfo.setTransCode(
				//		utilOperation.getNewDraftTransactionNo(transDraftInInfo.getOfficeID(),
				//				transDraftInInfo.getCurrencyID(),
				//				BILLConstant.DraftOperationType.DraftIn));						//交易编号
				transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.DISCOUNT);         	//票据来源【贴现买入】
				transDraftInInfo.setBillID(rs.getLong("id"));									//票据ID
				
				transDraftInInfo.setCurrentHolder(Env.getClientName());							//当前持票人
				transDraftInInfo.setFFormerOwner(rs.getString("sformerowner"));					//上一前手
				//transDraftInInfo.setFormerOwner(rs.getString("discountclientname"));  			//前手,贴现单位
				transDraftInInfo.setFormerOwner(NameRef.getClientNameByID(rs.getLong("nborrowclientid")));  //前手,贴现\借款单位
				transDraftInInfo.setInContractCode(rs.getString("scontractcode"));				//买入合同号
				transDraftInInfo.setInPayFormCode("");	//买入放款通知单号
				transDraftInInfo.setInOnWayDays(-1);  	//买入时的在途天数
				transDraftInInfo.setInRate(rs.getDouble("mdiscountrate"));						//买入时的贴现率
				transDraftInInfo.setInInterest((rs.getDouble("mamount")-rs.getDouble("mcheckamount")));	//买入时的贴现利息
				transDraftInInfo.setAbstract("贴现买入，自动入库");								//入库备注
				transDraftInInfo.setInputUserID(rs.getLong("ninputuserid"));					//录入人
				transDraftInInfo.setInDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//入库日期，贴现日
				transDraftInInfo.setInputDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//录入日期，申请录入日期
				transDraftInInfo.setModifyUserID(-1);	//修改人				
				transDraftInInfo.setModifyDate(null);	//修改日期
				transDraftInInfo.setStatusID(Constant.RecordStatus.VALID);                      //入库记录状态
				// 设置入库信息transDraftInInfo--------------- end -----------------------------------------------------
				
				/******************************************************************************************************/
				
				// 设置票据信息discountContractBillInfo------- start ---------------------------------------------------
				DiscountContractBillInfo discountContractBillInfo = new DiscountContractBillInfo();
				discountContractBillInfo.setId(rs.getLong("id"));
				discountContractBillInfo.setNStorageStatusID(BILLConstant.DraftOperationType.DraftIn);
				discountContractBillInfo.setNInputUserID(transDraftInInfo.getInputUserID());
				discountContractBillInfo.setNOfficeID(transDraftInInfo.getOfficeID());
				discountContractBillInfo.setNCurrencyID(transDraftInInfo.getCurrencyID());
				discountContractBillInfo.setNStatusID(Constant.RecordStatus.VALID);
				discountContractBillInfo.setNModuleSourceID(Constant.ModuleType.BILL);
				discountContractBillInfo.setNQueryStatusID(BILLConstant.BillQuery.NOTQUERY);	
				discountContractBillInfo.setDtCreate(rs.getTimestamp("dtcreate"));
				discountContractBillInfo.setDtEnd(rs.getTimestamp("dtend"));
				discountContractBillInfo.setMAmount(rs.getDouble("mamount"));
				discountContractBillInfo.setSCode(rs.getString("scode"));
				//设置票据信息discountContractBillInfo------- end ----------------------------------------------------
				
				/******************************************************************************************************/
				
				DraftStorageAssembleInfo draftStorageAssembleInfo=new DraftStorageAssembleInfo();
				draftStorageAssembleInfo.setDiscountContractBillInfo(discountContractBillInfo);
				draftStorageAssembleInfo.setTransDraftInInfo(transDraftInInfo);
				
				v.add(draftStorageAssembleInfo);
            }
            
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LoanDAOException(" 入出库有错误! ",e);
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return v;
	}	
	
	public long getBillTypeId(long loanId)throws Exception
	{
		long billTypeId = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sqlBuff = null;
		try
		{
			con = Database.getConnection();
			sqlBuff = new StringBuffer();
			sqlBuff.append(" select t.id from Bill_BillType t,Loan_DiscountFormBill l ");
			sqlBuff.append(" where t.statusid = 1  ");
			sqlBuff.append(" and l.nstatusid = 1 ");
			sqlBuff.append(" and t.officeid = l.nofficeid ");
			sqlBuff.append(" and t.currencyid = l.ncurrencyid ");
			sqlBuff.append(" and t.constanttype = l.nacceptpotypeid ");
			sqlBuff.append(" and l.nloanid ="+loanId);
			ps = con.prepareStatement(sqlBuff.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				billTypeId = rs.getLong("id");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("获取票据类型出错!",e);
		}
		finally
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
			if (con != null)
			{
				con.close();
				con = null;
			}			
		}
		return billTypeId;
	}
}

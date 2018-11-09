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
				//�ſ�֪ͨ��ID 20130111 wcl
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
		double dTotalAmount = 0; //��Ʊ�ݽ��

		try
		{
			con = Database.getConnection();

			//�����¼����
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
			////////////////////////////������//////////////////////////////////////////////////////////////////////
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
			//��������Ľ����
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

				dbill.setDiscountContractID(lDiscountContractID); //���ֱ�ʾ
				dbill.setID(rs.getLong("ID")); //Ʊ�ݱ�ʾ
				dbill.setSerialNo(rs.getLong("nSerialNo")); //���к�
				dbill.setUserName(rs.getString("sUserName")); //ԭʼ��Ʊ��
				dbill.setBank(rs.getString("sBank")); //�ж�����
				dbill.setIsLocal(rs.getLong("nIsLocal")); //�ж��������ڵأ��Ƿ��ڱ��أ�
				dbill.setCreate(rs.getTimestamp("dtCreate")); //��Ʊ��
				dbill.setEnd(rs.getTimestamp("dtEnd")); //������
				dbill.setCode(rs.getString("sCode")); //��Ʊ����
				dbill.setAmount(rs.getDouble("mAmount")); //��Ʊ���
				dbill.setAddDays(rs.getLong("nAddDays")); //�ڼ������Ӽ�Ϣ����
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //��Ʊ����
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //���ֵ�λֱ��ǰ��

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
	
	//Ʊ����ⷽ��
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
				//������⽻�ױ��
				TransDraftInInfo tmpInInfo = draftStorageAssemble.getTransDraftInInfo();
				tmpInInfo.setTransCode(utilOperation.getNewDraftTransactionNo(tmpInInfo.getOfficeID(),
						tmpInInfo.getCurrencyID(),
						BILLConstant.DraftOperationType.DraftIn));
				appBiz.saveDraftIn(draftStorageAssemble);
			}			
			//2.����¸ú�ͬƱ�ݵ�����״̬
			conDao.updateContractBillSellStatus(lContractID, Constant.YesOrNo.YES);			
		}
	}
	
    /**
	 * �õ����������Ϣ
	 * @param id Ʊ��id
	 * @param draftOperationType �������ͣ����֣�
	 * @param draftInOrOut �롢��������
	 * @return Ʊ������Ӧ��Ҫ��ӵ����ݼ�
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
            
            System.out.println("�������Ϣ��ѯsql��"+sqlBuff.toString());
            ps = con.prepareStatement(sqlBuff.toString());
            rs = ps.executeQuery();
           
            while (rs != null && rs.next())
            {
            	// ���������ϢtransDraftInInfo---------------Start-----------------------------------------------------
            	TransDraftInInfo transDraftInInfo = new TransDraftInInfo();
            	transDraftInInfo.setCurrencyID(rs.getLong("ncurrencyid"));						//���ױ���
				transDraftInInfo.setOfficeID(rs.getLong("nofficeid"));							//���װ��´�
				transDraftInInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);		//��������
				//transDraftInInfo.setTransCode(
				//		utilOperation.getNewDraftTransactionNo(transDraftInInfo.getOfficeID(),
				//				transDraftInInfo.getCurrencyID(),
				//				BILLConstant.DraftOperationType.DraftIn));						//���ױ��
				transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.DISCOUNT);         	//Ʊ����Դ���������롿
				transDraftInInfo.setBillID(rs.getLong("id"));									//Ʊ��ID
				
				transDraftInInfo.setCurrentHolder(Env.getClientName());							//��ǰ��Ʊ��
				transDraftInInfo.setFFormerOwner(rs.getString("sformerowner"));					//��һǰ��
				//transDraftInInfo.setFormerOwner(rs.getString("discountclientname"));  			//ǰ��,���ֵ�λ
				transDraftInInfo.setFormerOwner(NameRef.getClientNameByID(rs.getLong("nborrowclientid")));  //ǰ��,����\��λ
				transDraftInInfo.setInContractCode(rs.getString("scontractcode"));				//�����ͬ��
				transDraftInInfo.setInPayFormCode("");	//����ſ�֪ͨ����
				transDraftInInfo.setInOnWayDays(-1);  	//����ʱ����;����
				transDraftInInfo.setInRate(rs.getDouble("mdiscountrate"));						//����ʱ��������
				transDraftInInfo.setInInterest((rs.getDouble("mamount")-rs.getDouble("mcheckamount")));	//����ʱ��������Ϣ
				transDraftInInfo.setAbstract("�������룬�Զ����");								//��ⱸע
				transDraftInInfo.setInputUserID(rs.getLong("ninputuserid"));					//¼����
				transDraftInInfo.setInDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//������ڣ�������
				transDraftInInfo.setInputDate(DataFormat.getDateTime(DataFormat.formatDate(rs.getTimestamp("dtdiscountdate"))));//¼�����ڣ�����¼������
				transDraftInInfo.setModifyUserID(-1);	//�޸���				
				transDraftInInfo.setModifyDate(null);	//�޸�����
				transDraftInInfo.setStatusID(Constant.RecordStatus.VALID);                      //����¼״̬
				// ���������ϢtransDraftInInfo--------------- end -----------------------------------------------------
				
				/******************************************************************************************************/
				
				// ����Ʊ����ϢdiscountContractBillInfo------- start ---------------------------------------------------
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
				//����Ʊ����ϢdiscountContractBillInfo------- end ----------------------------------------------------
				
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
			throw new LoanDAOException(" ������д���! ",e);
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
			throw new IException("��ȡƱ�����ͳ���!",e);
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

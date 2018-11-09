/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yanliu 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcredence.dao;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.bill.util.UtilOperation;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.venture.dao.BlackBillDao;
import com.iss.itreasury.bill.venture.dataentity.BlackBillInfo;
import com.iss.itreasury.bill.bizdelegation.DraftDelegation;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.bill.draft.bizlogic.DraftEJB;
import com.iss.itreasury.bill.draft.dao.DiscountContractBillDao;
import com.iss.itreasury.bill.draft.dao.TransDraftInDAO;
import com.iss.itreasury.bill.draft.dao.TransDraftOutDao;
import com.iss.itreasury.bill.draft.dataentity.*;
import com.iss.itreasury.bill.draft.dataentity.assemble.*;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.transdiscountcontract.dao.TransDiscountContractDAO;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.SelectedTransDiscountBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.craftbrother.dao.TransCraftbrotherDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalSettingInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountCredenceDAO extends LoanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

	public TransDiscountCredenceDAO(String strTable)
	{
		super(strTable);
	}
    private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}
    

	/**
	 *票据查询操作,合同下的票据查询（凭证可用的票据）
	*/
	public Collection findTransDiscountCredenceBill(long lContractID, long lCredenceID, long lBillSourceTypeID) throws LoanDAOException
	{
		Vector v = new Vector();
		ResultSet localRS = null;
		TransDiscountContractBillInfo billInfo = null;
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select a.*,b.dtDiscountDate DiscountDate,b.MDISCOUNTRATE DISCOUNTRATE,v.billREPURCHASEDATE billREPURCHASEDATE,v.billREPURCHASETERM billREPURCHASETERM, ");
			buffer.append(" v.billIsLocal billIsLocal,v.billAddDays billAddDays,v.billCheckAmount billCheckAmount, ");
			buffer.append(" b.NDISCOUNTTYPEID DISCOUNTTYPEID,b.MCHECKAMOUNT CHECKAMOUNT , b.NINOROUT INOROUT,b.id contractid,nvl(v.discountcredenceid,-1) transdiscountcredenceid,nvl(v.repurchasecredenceid,-1) repurchasecredenceid ");
			buffer.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, transdiscountcontractbillview v ");
			buffer.append(" where v.contractid = b.id and a.id=v.billid ");
			buffer.append(" and v.contractid = " + lContractID);
			buffer.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
			if (lCredenceID > 0) //大于0则是回购凭证
			{
				buffer.append(" and nvl(v.discountcredenceid,-1) = " + lCredenceID);
			}
			if (lBillSourceTypeID > 0) //控制票据来源
			{
				buffer.append(" and nvl(a.NBILLSOURCETYPEID,1) = " + lBillSourceTypeID);
			}
			buffer.append(" order by a.NSERIALNO");
			String strSQL = buffer.toString();
			System.out.println("我要看的SQL是:"+strSQL);
			log.debug(strSQL);
			prepareStatement(strSQL.toString());
			localRS = executeQuery();

			while (localRS.next())
			{
				int nDays = 0;
				double dAccrual=0.0;
				double dRealAmount =0.0;
				billInfo = new TransDiscountContractBillInfo();
				billInfo.setId(localRS.getLong("id"));
				billInfo.setSerialNo(localRS.getLong("NSERIALNO"));
				billInfo.setCode(localRS.getString("SCODE"));
				billInfo.setAmount(localRS.getDouble("MAMOUNT"));
				billInfo.setEnd(localRS.getTimestamp("DTEND"));
				billInfo.setAddDays(localRS.getLong("billAddDays"));
				billInfo.setCheckAmount(localRS.getDouble("billCheckAmount"));
				billInfo.setAcceptPoTypeID(localRS.getLong("NACCEPTPOTYPEID"));
				billInfo.setSellStatusID(localRS.getLong("NSELLSTATUSID")); //是否可用
				billInfo.setRepurchaseDate(localRS.getTimestamp("billREPURCHASEDATE"));
				billInfo.setRepurchaseTerm(localRS.getLong("billREPURCHASETERM"));
				billInfo.setDiscountDate(localRS.getTimestamp("DiscountDate"));
				billInfo.setDiscountRate(localRS.getDouble("DISCOUNTRATE"));
				billInfo.setDiscountTypeID(localRS.getLong("DISCOUNTTYPEID"));
				billInfo.setCheckAmount(localRS.getDouble("billCheckAmount"));
				billInfo.setInOrOut(localRS.getLong("INOROUT"));
				if(billInfo.getInOrOut()==2&&billInfo.getRepurchaseDate()!=null){
				billInfo.setContractID(localRS.getLong("CONTRACTID"));
				}
				else{
				billInfo.setContractID(localRS.getLong("nContractID"));
				}
				billInfo.setDiscountCredenceID(localRS.getLong("transdiscountcredenceid"));
				billInfo.setRepurchaseCredenceID(localRS.getLong("repurchasecredenceid"));
				billInfo.setAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));

          //	modify by xwhe 2007-12-8
			//	if(billInfo.getDiscountTypeID()==LOANConstant.TransDiscountType.REPURCHASE)
			//	{
					
			//		billInfo.setRealDiscountDays(billInfo.getRepurchaseTerm());
					
			//	}
			//	else
			//	{
				    if(localRS.getTimestamp("DTEND") != null && localRS.getTimestamp("billREPURCHASEDATE") != null && lCredenceID > 0){

						Timestamp tsEnd = localRS.getTimestamp("DTEND");
						String strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((localRS.getTimestamp("billREPURCHASEDATE").getTime()-localRS.getTimestamp("DiscountDate").getTime()) / 86400000) + localRS.getInt("billAddDays"); //加上节假日增加计息天数
						
				    }
				
				    else if (localRS.getTimestamp("DTEND") != null && localRS.getTimestamp("DiscountDate") != null)
					{
						Timestamp tsEnd = localRS.getTimestamp("DTEND");
						String strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
								new Integer(strEnd.substring(0, 4)).intValue() - 1900,
								new Integer(strEnd.substring(5, 7)).intValue() - 1,
								new Integer(strEnd.substring(8, 10)).intValue(),
								0,
								0,
								0,
								0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - localRS.getTimestamp("DiscountDate").getTime()) / 86400000) + localRS.getInt("billAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (localRS.getLong("billIsLocal") == LOANConstant.YesOrNo.NO&&billInfo.getDiscountTypeID()==LOANConstant.TransDiscountType.BUYBREAK)
						{
							nDays = nDays + 3;
						
						}
						if((billInfo.getInOrOut()==1&&localRS.getTimestamp("DTEND") != null && localRS.getTimestamp("billREPURCHASEDATE") != null && lCredenceID > 0)){
							  dAccrual = localRS.getDouble("MAMOUNT") * (localRS.getDouble("DISCOUNTRATE") / 360) * 0.01 * nDays;
							  dRealAmount = localRS.getDouble("MAMOUNT") - dAccrual;
							  billInfo.setAccrual(dAccrual);
							  billInfo.setCheckAmount(dRealAmount);
							  new TransDiscountContractDAO("RTRANSDISCOUNTCONTRACTBILL").updateBillRelation(billInfo.getId(),billInfo.getContractID(),dRealAmount);
						}
						else if(billInfo.getInOrOut()==2 && localRS.getTimestamp("billREPURCHASEDATE") != null && lCredenceID > 0)
						{dAccrual = localRS.getDouble("MAMOUNT") * (localRS.getDouble("DISCOUNTRATE") / 360) * 0.01 * nDays;
						  dRealAmount = localRS.getDouble("MAMOUNT") - dAccrual;
						  billInfo.setAccrual(dAccrual);
						  billInfo.setCheckAmount(dRealAmount);
						  new TransDiscountContractDAO("RTRANSDISCOUNTCONTRACTBILL").updateBillRelation(billInfo.getId(),billInfo.getContractID(),dRealAmount);							
						}
					}
				
					billInfo.setRealDiscountDays(nDays);
				
			//	}

				v.add(billInfo);
			}
			localRS.close();
			localRS = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			throw new LoanDAOException("查询失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return (v.size() > 0 ? v : null);
	}

	/**
	 *票据修改保存操作
	*/
	public void saveTransDiscountCredenceBill(long lTransDiscountCredenceID, long[] lIDList) throws LoanDAOException
	{
		PreparedStatement localPs = null;
		ResultSet localRs = null;
		String strSelect = null;
		String strSQL = null;
		long nStatusID = -1;
		long lRecordCount = -1;

		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额
		Timestamp tsRepurchaseDate = null; //回购日期
		long lAcceptPOTypeID = -1; //票据种类
		String strDraftCode = ""; //票据号码
		Timestamp tsCreatDate = null; //发票日
		Timestamp tsEndDate = null; //到期日
		long lRepurchaseTerm = -1; //回购期限
		long lCredenceTypeID = -1;

		try
		{
			initDAO();
			TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.strTableName);
			TransDiscountCredenceInfo info = new TransDiscountCredenceInfo();
			info = dao.findCredenceInfoByID(lTransDiscountCredenceID);

			if (lTransDiscountCredenceID > 0 && lIDList != null && lIDList.length > 0)
			{

				String strBillID = "";
				long lBillID = lIDList[0];
				strBillID += lBillID;
				for (int i = 1; i < lIDList.length; i++)
				{
					lBillID = lIDList[i];
					strBillID += "," + lBillID;
				}

				strSQL =
					" select sum(nvl(mAmount,0)) mTotalAmount,sum(nvl(mCheckAmount,0)) mTotalCheckAmount from Loan_DiscountContractBill a where a.NSTATUSID="
						+ LOANConstant.RecordStatus.VALID
						+ " and a.id in ( "
						+ strBillID
						+ " )";

				localPs = prepareStatement(strSQL);
				localRs = localPs.executeQuery();
				if (localRs.next())
				{
					dTotalAmount = localRs.getDouble(1);
					dTotalRealAmount = localRs.getDouble(2);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				Log.print("==============");
				Log.print("汇票金额=" + dTotalAmount);
				Log.print("汇票利息=" + dTotalAccrual);
				Log.print("实付金额=" + dTotalRealAmount);
				Log.print("==============");
				localRs.close();
				localRs = null;
				localPs.close();
				localPs = null;

				//				if(lCredenceTypeID == LOANConstant.CredenceType.REPURCHASECREDENCE)
				//				{
				//					strSQL = " select a.REPURCHASEDATE from RTRANSDISCOUNTCONTRACTBILL a "
				//						+ " where a.DISCOUNTCONTRACTBILLID = " + lIDList[0] ;
				//					
				//					localPs = prepareStatement(strSQL);
				//					localRs = localPs.executeQuery();
				//					if (localRs.next())
				//					{
				//						tsRepurchaseDate = localRs.getTimestamp("REPURCHASEDATE");				
				//					}
				//					Log.print("回购日期=" + tsRepurchaseDate.toString());
				//					localRs.close();
				//					localRs = null;
				//					localPs.close();
				//					localPs = null;
				//				}				
				if (lIDList != null && lIDList.length > 0)
				{
					strSQL =
						" select a.*, b.REPURCHASETERM billRepurchaseTerm, b.REPURCHASEDATE billRepurchaseDate "
							+ " from Loan_DiscountContractBill a, RTRANSDISCOUNTCONTRACTBILL b where a.id = b.DISCOUNTCONTRACTBILLID and a.id="
							+ lIDList[0];

					Log.print(strSQL);
					localPs = prepareStatement(strSQL);
					localRs = localPs.executeQuery();

					if (localRs != null && localRs.next())
					{
						if (lIDList.length == 1)
						{
							lAcceptPOTypeID = localRs.getLong("NACCEPTPOTYPEID");
							strDraftCode = localRs.getString("SCODE");
							tsCreatDate = localRs.getTimestamp("DTCREATE");
							tsEndDate = localRs.getTimestamp("DTEND");
							lRepurchaseTerm = localRs.getLong("billRepurchaseTerm");
						}
						tsRepurchaseDate = localRs.getTimestamp("billRepurchaseDate");
					}

					localRs.close();
					localRs = null;
					localPs.close();
					localPs = null;
				}

				strSQL = " update " + strTableName + " set mAmount=?, mInterest=?,NDRAFTTYPEID=?, SDRAFTCODE=?, DTPUBLICDATE=?, DTATTERM=?, NREPURCHASETERM=?, DTREPURCHASEDATE=?  where ID=? ";

				Log.print(strSQL);
				localPs = transConn.prepareStatement(strSQL);
				localPs.setDouble(1, dTotalAmount);
				localPs.setDouble(2, dTotalAccrual);
				localPs.setLong(3, lAcceptPOTypeID);
				localPs.setString(4, strDraftCode);
				localPs.setTimestamp(5, tsCreatDate);
				localPs.setTimestamp(6, tsEndDate);
				localPs.setLong(7, lRepurchaseTerm);
				localPs.setTimestamp(8, tsRepurchaseDate);
				localPs.setLong(9, lTransDiscountCredenceID);

				localPs.executeUpdate();

				localPs.close();
				localPs = null;
			}
			if (lTransDiscountCredenceID > 0 && info.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
			{
				dao.updateBillCredenceID(lTransDiscountCredenceID, lIDList);
			}
			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("保存失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	}

	/**
	 *票据与凭证关系表保存操作
	*/
	public void saveRCredenceAndBill(long lTransDiscountCredenceID, long[] lIDList) throws LoanDAOException
	{
		PreparedStatement localPs = null;
		ResultSet localRs = null;
		String strSelect = null;
		String strSQL = null;
		long nStatusID = -1;
		int i = 0;
		long lRecordCount = -1;

		long lIsLocal = -1;
		long lAddDays = -1;
		Timestamp dtRepurchaseDate = null;
		long lRepurchaseTerm = -1;

		try
		{
			initDAO();

			if (lTransDiscountCredenceID > 0)
			{
				//删除原有票据记录
				strSQL = " delete RTRANSDISCOUNTCREDENCEBILL where TRANSDISCOUNTCREDENCEID= " + lTransDiscountCredenceID;

				Log.print(strSQL);
				//ps = transConn.prepareStatement(strSQL);
				localPs = prepareStatement(strSQL);
				localPs.executeUpdate();
				localPs.close();
				localPs = null;

				//新增记录
				for (i = 0; i < lIDList.length; i++)
				{
					if (lIDList[i] > 0)
					{
						//新增记录
						strSQL = " insert into RTRANSDISCOUNTCREDENCEBILL (TRANSDISCOUNTCREDENCEID,DISCOUNTCONTRACTBILLID) " + " values(?,?)";

						Log.print(strSQL);
						localPs = prepareStatement(strSQL);
						localPs.setLong(1, lTransDiscountCredenceID);
						localPs.setLong(2, lIDList[i]);

						localPs.executeUpdate();
						localPs.close();
						localPs = null;
					}
				}
			}
			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("保存失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	}

	/**
	 *凭证的保存操作
	*/
	public long saveTransDiscountCredence(TransDiscountCredenceInfo info) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lMaxID = -1;
		long nStatusID = -1;
		String strCredenceCode = "";

		try
		{
			initDAO();
			TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.strTableName);

			if (info.getId() <= 0)
			{
				//第一次暂存
				//得到最大的ID
				strSQL = " select Seq_Loan_PayFrm_DiscountCred.Nextval from dual ";
				ps = prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
				}

				rs.close();
				rs = null;
				ps.close();
				ps = null;

				strCredenceCode = dao.createCredenceCode(info.getContractID());

				//存储记录
				strSQL =
					" Insert into "
						+ strTableName
						+ "(ID,nContractID,dtFillDate,nDraftTypeID,sDraftCode, "
						+ " dtPublicDate,dtAtTerm,sApplyClient,sApplyAccount,sApplyBank,sAcceptClient,sAcceptAccount,sAcceptBank, "
						+ " mAmount,mRate,mInterest,nStatusID,nInputUserID,nNextCheckUserID,dtInputDate,sCode, "
						+ " nGrantTypeID,nAccountBankID,sReceiveAccount,sReceiveClientName,sRemitBank,sRemitInProvince,sRemitInCity,"
						+" nGrantCurrentAccountID,nNextCheckLevel,nTypeID,nBillSourceTypeID,dtRepurchaseDate,NTRANSDISCOUNTCREDENCEID,"
						+" NREPURCHASETERM,nOfficeID,nCurrencyID,attachId) "
						+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,1,?,?,?,?,?,?,?,?) ";

				Log.print(strSQL);
				ps = prepareStatement(strSQL);
				ps.setLong(1, lMaxID);
				ps.setLong(2, info.getContractID());
				ps.setTimestamp(3, info.getFillDate());
				ps.setLong(4, info.getDraftTypeID());
				ps.setString(5, info.getDraftCode());
				ps.setTimestamp(6, info.getPublicDate());
				ps.setTimestamp(7, info.getAtTerm());
				ps.setString(8, info.getApplyClient());
				ps.setString(9, info.getApplyAccount());
				ps.setString(10, info.getApplyBank());
				ps.setString(11, info.getAcceptClient());
				ps.setString(12, info.getAcceptAccount());
				ps.setString(13, info.getAcceptBank());
				ps.setDouble(14, info.getAmount());
				ps.setDouble(15, info.getRate());
				ps.setDouble(16, info.getInterest());
				ps.setLong(17, LOANConstant.DiscountCredenceStatus.SAVE);
				ps.setLong(18, info.getInputUserID());
				ps.setLong(19, info.getInputUserID());
				ps.setString(20, strCredenceCode);
				ps.setLong(21, info.getGrantTypeID());
				ps.setLong(22, info.getAccountBankID());
				ps.setString(23, info.getReceiveAccount());
				ps.setString(24, info.getReceiveClientName());
				ps.setString(25, info.getRemitBank());
				ps.setString(26, info.getRemitInProvince());
				ps.setString(27, info.getRemitInCity());
				ps.setLong(28, info.getGrantCurrentAccountID());
				ps.setLong(29, info.getTypeID());
				ps.setLong(30, info.getBillSourceTypeID());
				ps.setTimestamp(31, info.getRepurchaseDate());
				ps.setLong(32, info.getTransDiscountCredenceID());
				ps.setLong(33, info.getRepurchaseTerm());
                ps.setLong(34,info.getOfficeID());
                ps.setLong(35,info.getNCurrencyID());
                ps.setLong(36, info.getAttachId());
                
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			if (lMaxID > 0 && info.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
			{
				dao.updateBillCredenceID(lMaxID, info.getAllBillID());
			}
			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("保存贴现凭证失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		return lMaxID;
	}

	/**
	 *凭证的修改操作
	*/
	public long updateTransDiscountCredence(TransDiscountCredenceInfo info) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lMaxID = -1;
		long nStatusID = -1;
		String strCredenceCode = "";

		try
		{
			initDAO();

			if (info.getId() > 0)
			{
				//更新记录
				strSQL =
					" update "
						+ strTableName
						+ " set sApplyAccount=?,sApplyBank=?,sAcceptClient=?,sAcceptAccount=?,sAcceptBank=?, "
						+ " nGrantTypeID=?,nAccountBankID=?,sReceiveAccount=?,sReceiveClientName=?,sRemitBank=?,sRemitInProvince=?,sRemitInCity=?,nGrantCurrentAccountID=?,DTFILLDATE=? "
						+ " where ID=? ";

				Log.print(strSQL);
				ps = prepareStatement(strSQL);
				//ps.setLong(1, lDraftTypeID);
				ps.setString(1, info.getApplyAccount());
				ps.setString(2, info.getApplyBank());
				ps.setString(3, info.getAcceptClient());
				ps.setString(4, info.getAcceptAccount());
				ps.setString(5, info.getAcceptBank());
				ps.setLong(6, info.getGrantTypeID());
				ps.setLong(7, info.getAccountBankID());
				ps.setString(8, info.getReceiveAccount());
				ps.setString(9, info.getReceiveClientName());
				ps.setString(10, info.getRemitBank());
				ps.setString(11, info.getRemitInProvince());
				ps.setString(12, info.getRemitInCity());
				ps.setLong(13, info.getGrantCurrentAccountID());
				ps.setTimestamp(14, info.getFillDate());
				ps.setLong(15, info.getId());

				Log.print("lGrantTypeID=" + info.getGrantTypeID());
				Log.print("lAccountBankID=" + info.getAccountBankID());
				Log.print("strReceiveClientCode=" + info.getReceiveAccount());
				Log.print("strReceiveClientName=" + info.getReceiveClientName());
				Log.print("strRemitBank=" + info.getRemitBank());
				Log.print("strRemitInProvince=" + info.getRemitInProvince());
				Log.print("strRemitInCity=" + info.getRemitInCity());
				Log.print("lGrantCurrentAccountID=" + info.getGrantCurrentAccountID());

				ps.executeUpdate();
				ps.close();
				ps = null;
				lMaxID = info.getId();
			}
			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("修改贴现凭证失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		return lMaxID;
	}

	/**
	 *
	 * 生成贴现凭证编号
	 *
	 */
	private String createCredenceCode(long lContractID) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		String strCode = "0000";
		long lCode = 0;

		try
		{
			initDAO();

			strSQL = " select nvl(max(sCode),0) sCode from " + strTableName + " where nContractID = ? ";
			ps = prepareStatement(strSQL);
			ps.setLong(1, lContractID);

			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				lCode = Long.parseLong(strCode) + 1;
				strCode = DataFormat.formatInt(lCode, 4, true);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			finalizeDAO();
		}
		catch (Exception e)
		{
			throw new LoanDAOException("生成贴现凭证编号失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return strCode;
	}

	/**
	 *凭证的单笔查询操作
	*/
	public TransDiscountCredenceInfo findCredenceInfoByID(long lID) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		TransDiscountCredenceInfo lai = new TransDiscountCredenceInfo();
		TransDiscountContractInfo contractInfo = new TransDiscountContractInfo();
		try
		{
			initDAO();

			strSQL = " select a.*, ";
			strSQL
				+= " b.ID nContractID,b.nloanid  nLoanid,b.sContractCode,b.nBorrowClientID,b.mExamineAmount,b.mCheckAmount,b.mDiscountRate,b.DTDISCOUNTDATE DTDISCOUNTDATE,c.sName sClientName,a.NBILLSOURCETYPEID NBILLSOURCETYPEID, ";
			strSQL += " d.sName sInputUserName,b.NDISCOUNTTYPEID NDISCOUNTTYPEID,b.NREPURCHASETYPEID NREPURCHASETYPEID,b.NINOROUT NINOROUT,a.ATTACHID ";
			strSQL += " from " + strTableName + " a, Loan_ContractForm b, Client c, UserInfo d ";
			strSQL += " where a.nContractID=b.ID and b.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and b.nTypeID=" + LOANConstant.LoanType.ZTX + " and a.ID= " + lID;
			//Log.print("凭证的单笔查询操作:*************"+strSQL);
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//贴现申请
				contractInfo.setLoanId(rs.getLong("nLoanid"));//申请ID
				lai.setContractID(rs.getLong("nContractID")); //贴现ID标识
				lai.setContractCode(rs.getString("sContractCode")); //贴现编号
				//申请单位
				lai.setApplyClientId(rs.getLong("nBorrowClientID")); //申请单位ID
				lai.setApplyClient(rs.getString("SAPPLYCLIENT")); //申请单位名称
				lai.setApplyAccount(rs.getString("sApplyAccount")); //申请单位帐户号
				lai.setApplyBank(rs.getString("sApplyBank")); //申请单位开户银行

				lai.setDiscountExamineAmount(rs.getDouble("mExamineAmount")); //批准金额(合同)
				lai.setDiscountCheckAmount(rs.getDouble("mCheckAmount")); //核定金额（合同）
				//lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //贴现利息
				lai.setRate(rs.getDouble("mDiscountRate")); //贴现利率（合同）
				lai.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE")); //转贴现日
				lai.setDiscountTypeID(rs.getLong("NDISCOUNTTYPEID")); //转贴现种类
				lai.setRepurchaseTypeID(rs.getLong("NREPURCHASETYPEID")); //回购种类
				lai.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE")); //回购日期
				lai.setInOrOut(rs.getLong("NINOROUT"));

				//贴现凭证
				lai.setId(lID); //贴现凭证标识
				lai.setCode(rs.getString("sCode")); //贴现凭证编号
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setBillSourceTypeID(rs.getLong("NBILLSOURCETYPEID")); //票据来源	
				lai.setTypeID(rs.getLong("NTYPEID")); //凭证类型			
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //贴现汇票种类标示
				lai.setDraftCode(rs.getString("sDraftCode")); //贴现汇票号码
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //发票日
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //到期日
				lai.setAcceptClient(rs.getString("sAcceptClient")); //承兑单位名称
				lai.setAcceptAccount(rs.getString("sAcceptAccount")); //承兑单位帐户号
				lai.setAcceptBank(rs.getString("sAcceptBank")); //承兑单位银行
				lai.setStatusID(rs.getLong("nStatusID")); //贴现凭证是否删除

				lai.setAmount(rs.getDouble("mAmount")); //贴现凭证金额
				lai.setInterest(rs.getDouble("mInterest")); //贴现凭证利息
				lai.setCheckAmount(rs.getDouble("mAmount") - rs.getDouble("mInterest")); //贴现凭证核定金额

				lai.setInputUserID(rs.getLong("nInputUserID"));
				//lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				lai.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //下一个审核人标示
				lai.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别

				lai.setGrantTypeID(rs.getLong("nGrantTypeID"));
				lai.setAccountBankID(rs.getLong("nAccountBankID"));
				//lai.setAccountBankCode(rs.getString("AccCode")) ;
				//lai.setAccountBankName(rs.getString("AccName")) ;
				//lai.setReceiveClientCode(rs.getString("sReceiveAccount")) ;
				lai.setReceiveClientName(rs.getString("sReceiveClientName"));
				lai.setRemitBank(rs.getString("sRemitBank"));
				lai.setRemitInProvince(rs.getString("sRemitInProvince"));
				lai.setRemitInCity(rs.getString("sRemitInCity"));
				lai.setGrantCurrentAccountID(rs.getLong("nGrantCurrentAccountID"));
				lai.setAttachId(rs.getLong("attachId"));
				//lai.setGrantCurrentAccountCode(rs.getString("sGrantCurrentAccount")) ;
				//回购凭证特有
				if (lai.getTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE)
				{
					lai.setTransDiscountCredenceID(rs.getLong("nTransDiscountCredenceID"));
					lai.setRepurchaseTerm(rs.getLong("nRepurchaseTerm"));
					lai.setTransDiscountCredenceCode(LOANNameRef.getNameByID("scode", strTableName, "id", String.valueOf(lai.getTransDiscountCredenceID()), null));
					
					TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.strTableName);
					double dRepurchasedAmount = 0;
					dRepurchasedAmount = dao.calculateRepurchasedAmount(lai.getTransDiscountCredenceID(),-1);
					lai.setRepurchasedAmount(dRepurchasedAmount);	
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return lai;
	}

	/**
	 *凭证下的票据查询操作
	*/
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID) throws LoanDAOException
	{
		Vector v = new Vector();
		ResultSet localRS = null;
		TransDiscountContractBillInfo billInfo = null;
		
		double dInTotalInterest = 0.00;//转贴现卖出买断专用，统计所有卖出票的买入利息
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select a.*,f.ntypeid,b.MDISCOUNTRATE DiscountRate,b.dtDiscountDate DiscountDate,b.NDISCOUNTTYPEID DiscountTypeID,b.NREPURCHASETYPEID RepurchaseTypeID, b.NINOROUT INOROUT,");
			buffer.append(" d.REPURCHASEDATE billREPURCHASEDATE,d.REPURCHASETERM billREPURCHASETERM,d.ISLOCAL billIsLocal,d.ADDDAYS billAddDays,d.CHECKAMOUNT billCheckAmount ");
			buffer.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, RTRANSDISCOUNTCREDENCEBILL c, RTRANSDISCOUNTCONTRACTBILL d, "+ strTableName + " e, Loan_ContractForm f ");
			buffer.append(
				" where a.ncontractid = f.id and d.transdiscountcontractid = b.ID and a.ID = c.DISCOUNTCONTRACTBILLID and a.id = d.DISCOUNTCONTRACTBILLID and d.transdiscountcontractid=e.ncontractid and c.transdiscountcredenceid =e.id ");
			buffer.append(" and c.transdiscountcredenceid = " + lTransDiscountCredenceID);
			buffer.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
			//buffer.append(" and a.NSELLSTATUSID = " + LOANConstant.YesOrNo.NO );

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				int nDays = 0;
				billInfo = new TransDiscountContractBillInfo();
				billInfo.setId(localRS.getLong("ID"));
				billInfo.setContractID(localRS.getLong("NCONTRACTID"));
				billInfo.setSerialNo(localRS.getLong("NSERIALNO"));
				billInfo.setCode(localRS.getString("SCODE"));
				billInfo.setAmount(localRS.getDouble("MAMOUNT"));
				billInfo.setEnd(localRS.getTimestamp("DTEND"));
				billInfo.setAddDays(localRS.getLong("billAddDays"));
				billInfo.setCheckAmount(localRS.getDouble("billCheckAmount"));
				billInfo.setAcceptPoTypeID(localRS.getLong("NACCEPTPOTYPEID"));
				billInfo.setInOrOut(localRS.getLong("INOROUT")); //买入卖出
				billInfo.setDiscountTypeID(localRS.getLong("DiscountTypeID")); //转贴现类型
				billInfo.setDiscountCredenceID(localRS.getLong("NDISCOUNTCREDENCEID"));//贴现凭证ID
				billInfo.setRepurchaseTypeID(localRS.getLong("RepurchaseTypeID")); //回购种类
				billInfo.setDiscountDate(localRS.getTimestamp("DiscountDate")); //转贴现日
				billInfo.setDiscountRate(localRS.getDouble("DiscountRate"));
				billInfo.setRepurchaseDate(localRS.getTimestamp("billREPURCHASEDATE")); //回购日期
				billInfo.setRepurchaseTerm(localRS.getLong("billREPURCHASETERM")); //回购期限
				billInfo.setAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
				billInfo.setIsLocal(localRS.getLong("nislocal"));//是否本地
				billInfo.setBillSourceTypeID(localRS.getLong("nbillsourcetypeid"));//票据来源
				if(billInfo.getDiscountTypeID()==LOANConstant.TransDiscountType.REPURCHASE)
				{
					billInfo.setRealDiscountDays(billInfo.getRepurchaseTerm()+localRS.getInt("billAddDays"));
					billInfo.setAccrual(localRS.getDouble("MAMOUNT")* (localRS.getDouble("DiscountRate") / 360) * 0.01 * billInfo.getRealDiscountDays() );
				}
				else
				{
					if (localRS.getTimestamp("DTEND") != null && localRS.getTimestamp("DiscountDate") != null)
					{
						Timestamp tsEnd = localRS.getTimestamp("DTEND");
						String strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
							new Integer(strEnd.substring(0, 4)).intValue() - 1900,
							new Integer(strEnd.substring(5, 7)).intValue() - 1,
							new Integer(strEnd.substring(8, 10)).intValue(),
							0,
							0,
							0,
							0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - localRS.getTimestamp("DiscountDate").getTime()) / 86400000) + localRS.getInt("billAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (localRS.getLong("billIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					}
					billInfo.setRealDiscountDays(nDays);
					if(localRS.getLong("ntypeid")==3){//贴现买入的票，买入时的【利息=票面金额 - 核定金额】
						billInfo.setAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("mcheckamount"));
						billInfo.setBreaknotifyAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
					}else{//转贴现买入的票，买入时的利息
						TransCraftbrotherDAO transCraftbrotherDAO = new TransCraftbrotherDAO();
						TransCraftbrotherInfo transCraftbrotherInfo = transCraftbrotherDAO.findBreakInByBillId(localRS.getLong("ID"));//转贴现买入买断发放信息
						if(transCraftbrotherInfo != null){
							if(transCraftbrotherInfo.getId()>0){
								billInfo.setAccrual(transCraftbrotherInfo.getMinterest());
							}
								billInfo.setBreaknotifyAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
							
						}else{
							throw new Exception("查找票据"+localRS.getString("SCODE")+"转贴现买入买断发放信息数据库异常，交易失败！");
						}
					}
				}

				v.add(billInfo);
			}
			localRS.close();
			localRS = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return (v.size() > 0 ? v : null);
	}

	
	/**
	 *转贴现摊销 查询票据明细信息
	*/
	public Collection findDraftForAmortization(long lTransDiscountCredenceID) throws LoanDAOException
	{
		Vector v = new Vector();
		ResultSet localRS = null;
		TransDiscountContractBillInfo billInfo = null;
		
		double dInTotalInterest = 0.00;//转贴现卖出买断专用，统计所有卖出票的买入利息
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select a.*,f.ntypeid,b.MDISCOUNTRATE DiscountRate,b.dtDiscountDate DiscountDate,b.NDISCOUNTTYPEID DiscountTypeID,b.NREPURCHASETYPEID RepurchaseTypeID, b.NINOROUT INOROUT,");
			buffer.append(" d.REPURCHASEDATE billREPURCHASEDATE,d.REPURCHASETERM billREPURCHASETERM,d.ISLOCAL billIsLocal,d.ADDDAYS billAddDays,d.CHECKAMOUNT billCheckAmount,draft.totalAmortizationAmount ");
			buffer.append(" from LOAN_DISCOUNTCONTRACTBILL a,Loan_ContractForm b, RTRANSDISCOUNTCREDENCEBILL c, RTRANSDISCOUNTCONTRACTBILL d, "+ strTableName + " e, Loan_ContractForm f, ");
			buffer.append(" (select draft.draftid id,draw.nnoticeid,sum(draft.amortizationamount) totalAmortizationAmount ");
			buffer.append(" from SETT_CRAINTERESTPREDRAW draw, DraftAmortization draft ");
			buffer.append("  where draw.id = draft.amortizationid ");
			buffer.append(" and draw.nstatusid ="+SETTConstant.TransactionStatus.CHECK);
			buffer.append(" and draft.nstatus ="+SETTConstant.TransactionStatus.SAVE);
			buffer.append(" and draw.nnoticeid ="+lTransDiscountCredenceID);
			buffer.append("  group by draft.draftid,draw.nnoticeid) draft ");

			buffer.append(
				" where a.ncontractid = f.id and d.transdiscountcontractid = b.ID and a.ID = c.DISCOUNTCONTRACTBILLID and a.id = d.DISCOUNTCONTRACTBILLID and d.transdiscountcontractid=e.ncontractid and c.transdiscountcredenceid =e.id ");
			buffer.append(" and c.transdiscountcredenceid = " + lTransDiscountCredenceID);
			buffer.append(" and a.nStatusID=" + LOANConstant.RecordStatus.VALID);
			buffer.append(" and a.id = draft.id(+) ");
			//buffer.append(" and a.NSELLSTATUSID = " + LOANConstant.YesOrNo.NO );

			String strSQL = buffer.toString();
			log.info("计算票据信息sql="+strSQL);
			
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				int nDays = 0;
				billInfo = new TransDiscountContractBillInfo();
				billInfo.setId(localRS.getLong("ID"));
				billInfo.setContractID(localRS.getLong("NCONTRACTID"));
				billInfo.setSerialNo(localRS.getLong("NSERIALNO"));
				billInfo.setCode(localRS.getString("SCODE"));
				billInfo.setAmount(localRS.getDouble("MAMOUNT"));
				billInfo.setEnd(localRS.getTimestamp("DTEND"));
				billInfo.setAddDays(localRS.getLong("billAddDays"));
				billInfo.setCheckAmount(localRS.getDouble("billCheckAmount"));
				billInfo.setAcceptPoTypeID(localRS.getLong("NACCEPTPOTYPEID"));
				billInfo.setInOrOut(localRS.getLong("INOROUT")); //买入卖出
				billInfo.setDiscountTypeID(localRS.getLong("DiscountTypeID")); //转贴现类型
				billInfo.setDiscountCredenceID(localRS.getLong("NDISCOUNTCREDENCEID"));//贴现凭证ID
				billInfo.setRepurchaseTypeID(localRS.getLong("RepurchaseTypeID")); //回购种类
				billInfo.setDiscountDate(localRS.getTimestamp("DiscountDate")); //转贴现日
				billInfo.setDiscountRate(localRS.getDouble("DiscountRate"));
				billInfo.setRepurchaseDate(localRS.getTimestamp("billREPURCHASEDATE")); //回购日期
				billInfo.setRepurchaseTerm(localRS.getLong("billREPURCHASETERM")); //回购期限
				billInfo.setAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
				billInfo.setIsLocal(localRS.getLong("nislocal"));//是否本地
				billInfo.setBillSourceTypeID(localRS.getLong("nbillsourcetypeid"));//票据来源
				billInfo.setDraftAmortizeInterest(localRS.getDouble("totalAmortizationAmount")); //票据已摊销金额
				
				if(billInfo.getDiscountTypeID()==LOANConstant.TransDiscountType.REPURCHASE)
				{
					billInfo.setRealDiscountDays(billInfo.getRepurchaseTerm()+localRS.getInt("billAddDays"));
					billInfo.setAccrual(localRS.getDouble("MAMOUNT")* (localRS.getDouble("DiscountRate") / 360) * 0.01 * billInfo.getRealDiscountDays() );
				}
				else
				{
					if (localRS.getTimestamp("DTEND") != null && localRS.getTimestamp("DiscountDate") != null)
					{
						Timestamp tsEnd = localRS.getTimestamp("DTEND");
						String strEnd = tsEnd.toString();
						tsEnd =
							new java.sql.Timestamp(
							new Integer(strEnd.substring(0, 4)).intValue() - 1900,
							new Integer(strEnd.substring(5, 7)).intValue() - 1,
							new Integer(strEnd.substring(8, 10)).intValue(),
							0,
							0,
							0,
							0);
						nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - localRS.getTimestamp("DiscountDate").getTime()) / 86400000) + localRS.getInt("billAddDays"); //加上节假日增加计息天数
					}
					if (nDays >= 0)
					{
						if (localRS.getLong("billIsLocal") == LOANConstant.YesOrNo.NO)
						{
							nDays = nDays + 3;
						}
					}
					billInfo.setRealDiscountDays(nDays);
					if(localRS.getLong("ntypeid")==3){//贴现买入的票，买入时的【利息=票面金额 - 核定金额】
						billInfo.setAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("mcheckamount"));
						billInfo.setBreaknotifyAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
					}else{//转贴现买入的票，买入时的利息
						TransCraftbrotherDAO transCraftbrotherDAO = new TransCraftbrotherDAO();
						TransCraftbrotherInfo transCraftbrotherInfo = transCraftbrotherDAO.findBreakInByBillId(localRS.getLong("ID"));//转贴现买入买断发放信息
						if(transCraftbrotherInfo != null){
							if(transCraftbrotherInfo.getId()>0){
								billInfo.setAccrual(transCraftbrotherInfo.getMinterest());
							}
							billInfo.setBreaknotifyAccrual(localRS.getDouble("MAMOUNT") - localRS.getDouble("billCheckAmount"));
						}else{
							throw new Exception("查找票据"+localRS.getString("SCODE")+"转贴现买入买断发放信息数据库异常，交易失败！");
						}
					}
				}

				v.add(billInfo);
			}
			localRS.close();
			localRS = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("查询失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return (v.size() > 0 ? v : null);
	}	
	
	/**
	 *	凭证的取消操作
	 */
	public void cancelCredenceByID(long lID) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		long lResult = 0;

		try
		{
			initDAO();

			strSQL = " update " + strTableName + " set nStatusID = ? where ID = ? ";
			ps = prepareStatement(strSQL);

			ps.setLong(1, Constant.RecordStatus.INVALID);
			ps.setLong(2, lID);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;

			//			strSQL = " update Loan_DiscountContractBill set nDiscountCredenceID = null,ob_nDiscountCredenceID = null where nDiscountCredenceID = ? ";
			//			ps = prepareStatement(strSQL);
			//
			//			ps.setLong(1, lID);
			//
			//			lResult = ps.executeUpdate();
			//
			//			ps.close();
			//			ps = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			throw new LoanDAOException("删除失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		//return lResult;
	}

	/**
	 * //: new - 加入金额审批效验 //:~
	 * @param qInfo
	 * @return
	 * @throws LoanDAOException
	 */
	public Collection findCredenceByMultiOption1(TransDiscountCredenceQueryInfo qInfo,long ActionID) throws LoanDAOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strOrder2=null;
		String strIn = null;
		String strUserID = null;
		String strSQL2="";
		String UNION=" union ";
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		
		//模块类型
		long lModuleID = LOANConstant.ModuleType.CRAFTBROTHER;
		//业务类型
		long lLoanTypeID = 23;//LOANConstant.ApprovalLoanType.TX;
		long lActionID=ActionID;    //操作类型
			
		
		try{
			initDAO();

			String field="bb.ID lContractID,bb.sContractCode,bb.mDiscountRate,bb.dtDiscountDate,bb.mExamineAmount,bb.mCheckAmount,bb.NDISCOUNTTYPEID,bb.NINOROUT,bb.NREPURCHASETYPEID,aa.NBILLSOURCETYPEID,"
			+ " aa.ID lCredenceID,aa.sApplyClient,aa.nStatusID nCredenceStatusID,aa.mAmount mBillAmount,aa.mInterest mBillInterest,nvl(aa.nNextCheckLevel,1) nNextCheckLevel, aa.dtRepurchaseDate DTREPURCHASEDATE, "
			+ " aa.NTRANSDISCOUNTCREDENCEID NTRANSDISCOUNTCREDENCEID, aa.NREPURCHASETERM NREPURCHASETERM";
			
			strIn = LOANConstant.DiscountCredenceStatus.SUBMIT + "," + LOANConstant.DiscountCredenceStatus.CHECK + "," + LOANConstant.DiscountCredenceStatus.USED;
			
	    	//: 审批SQL //:~
	        strSQL="(SELECT "+field+",-1 moneysegment,-1 approvalid from "+this.strTableName+" aa,Loan_ContractForm bb ";
	        strSQL+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
	        strSQL+=" where (a.NNEXTUSERID="+qInfo.getUserID()+" ";
	        strSQL+=" and a.nactionid="+lActionID+" and a.nloantypeid = "+lLoanTypeID+" ";
	        strSQL+="  and a.NMODULEID="+lModuleID+" and nstatusid="+Constant.RecordStatus.VALID+"";
	        strSQL+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
	        strSQL+=" where aa.nContractID=bb.ID and aa.id =d.NAPPROVALCONTENTID and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.APPROVALING;	
			
			//: ------------------------ 查询条件 ------------------------ //:~
//			if (qInfo.getCredenceTypeID() != -1)
//			{
//				strSQL = strSQL + " and aa.NTYPEID=" + qInfo.getCredenceTypeID();
//			}
	        if(qInfo.getStatusID()>0){
	        	strSQL = strSQL + " and aa.nStatusID=" + qInfo.getStatusID();
	        }
			if (qInfo.getOfficeID() != -1)
			{
				strSQL = strSQL + " and bb.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getContractIDFrom() != -1)
			{
				strSQL = strSQL + " and bb.ID>=" + qInfo.getContractIDFrom();
			}
			if (qInfo.getContractIDTo() != -1)
			{
				strSQL = strSQL + " and bb.ID<=" + qInfo.getContractIDTo();
			}
			if (qInfo.getCredenceTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE && qInfo.getCredenceID() != -1)
			{
				strSQL = strSQL + " and aa.NTRANSDISCOUNTCREDENCEID==" + qInfo.getCredenceID();
			}
			if (qInfo.getClientID() != -1)
			{
				strSQL = strSQL + " and bb.nBorrowClientID=" + qInfo.getClientID();
			}
			if (qInfo.getAmountFrom() != 0)
			{
				strSQL = strSQL + " and aa.mAmount>=" + qInfo.getAmountFrom();
			}
			if (qInfo.getAmountTo() != 0)
			{
				strSQL = strSQL + " and aa.mAmount<=" + qInfo.getAmountTo();
			}
			if (qInfo.getStartDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'";
			}
			if (qInfo.getEndDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'";
			}

//			if (qInfo.getActionID() == 2)
//			{
//				if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.SUBMIT)
//				{
//					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//				}
//				else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.CHECK)
//				{
//					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK;
//				}
//				else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.USED)
//				{
//					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.USED;
//				}
//				else if(qInfo.getStatusID()==LOANConstant.DiscountCredenceStatus.APPROVALING){
//					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.APPROVALING;
//				}
//				else{
//					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.APPROVALING;
//				}
//			}
			
	        strSQL2 = " (select d.* from (";
	        strSQL2 += " select aaa.* from (";
	        strSQL2 += " select "+field+",rr.moneysegment,rr.approvalid from "+this.strTableName+" aa,Loan_ContractForm bb,loan_approvalrelation rr";
	        strSQL2 += " where aa.nContractID=bb.ID and rr.moduleid="+lModuleID+" and aa.mAmount>rr.moneysegment";// and rr.currencyid =" +currencyID;
	        strSQL2 += " and rr.actionid = "+lActionID ;
	        strSQL2 += " and rr.subloantypeid = "+lLoanTypeID;//lSubLoanTypeID ;    	
	        strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
			
	        if(qInfo.getStatusID()>0){
	        	strSQL2 = strSQL2 + " and aa.nStatusID=" + qInfo.getStatusID();
	        }	        
	        
			if (qInfo.getCredenceTypeID() != -1)
			{
				strSQL2 += " and aa.NTYPEID=" + qInfo.getCredenceTypeID();
			}
			if (qInfo.getOfficeID() != -1)
			{
				strSQL2 += " and bb.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getContractIDFrom() != -1)
			{
				strSQL2 += " and bb.ID>=" + qInfo.getContractIDFrom();
			}
			if (qInfo.getContractIDTo() != -1)
			{
				strSQL2 += " and bb.ID<=" + qInfo.getContractIDTo();
			}
			if (qInfo.getCredenceTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE && qInfo.getCredenceID() != -1)
			{
				strSQL2 += " and aa.NTRANSDISCOUNTCREDENCEID==" + qInfo.getCredenceID();
			}
			if (qInfo.getClientID() != -1)
			{
				strSQL2 += " and bb.nBorrowClientID=" + qInfo.getClientID();
			}
			if (qInfo.getAmountFrom() != 0)
			{
				strSQL2 += " and aa.mAmount>=" + qInfo.getAmountFrom();
			}
			if (qInfo.getAmountTo() != 0)
			{
				strSQL2 += " and aa.mAmount<=" + qInfo.getAmountTo();
			}
			if (qInfo.getStartDate() != null)
			{
				strSQL2 += " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'";
			}
			if (qInfo.getEndDate() != null)
			{
				strSQL2 += " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'";
			}
			
//			if (qInfo.getUserID() > 0)
//			{
//				//修改查找
//				if (qInfo.getActionID() == 1)
//				{
////					strSQL2 += " and aa.nInputUserID = " + qInfo.getUserID();
//					strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//				}
//				//审核查找
//				else if (qInfo.getActionID() == 2)
//				{
//					if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.SUBMIT)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.CHECK)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.USED)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.USED;
//					}
//					else if(qInfo.getStatusID()==LOANConstant.DiscountCredenceStatus.APPROVALING){
////						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.APPROVALING;
//						strSQL2 += " and aa.nStatusID not in (" + strIn + ","+LOANConstant.DiscountCredenceStatus.APPROVALING+")";
//					}
//					else
//					{
//						strSQL2 += " and aa.nStatusID in (" + strIn + ")";
//					}
//				}
//			}
			
			strSQL2 += " ) aaa,(";
	        strSQL2 += " select aa.id,max(rr.moneysegment) maxamount from "+this.strTableName+" aa,Loan_ContractForm bb,loan_approvalrelation rr";
	        strSQL2 += " where aa.nContractID=bb.ID and rr.moduleid="+lModuleID+" and aa.mAmount>rr.moneysegment";// and rr.currencyid =" +currencyID;
	        strSQL2 += " and rr.actionid = " +lActionID ;
	        strSQL2 += " and rr.subloantypeid = " + lLoanTypeID;//lSubLoanTypeID ;            
	        strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
	        
	        if(qInfo.getStatusID()>0){
	        	strSQL2 = strSQL2 + " and aa.nStatusID=" + qInfo.getStatusID();
	        }	        
			if (qInfo.getCredenceTypeID() != -1)
			{
				strSQL2 += " and aa.NTYPEID=" + qInfo.getCredenceTypeID();
			}
			if (qInfo.getOfficeID() != -1)
			{
				strSQL2 += " and bb.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getContractIDFrom() != -1)
			{
				strSQL2 += " and bb.ID>=" + qInfo.getContractIDFrom();
			}
			if (qInfo.getContractIDTo() != -1)
			{
				strSQL2 += " and bb.ID<=" + qInfo.getContractIDTo();
			}
			if (qInfo.getCredenceTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE && qInfo.getCredenceID() != -1)
			{
				strSQL2 += " and aa.NTRANSDISCOUNTCREDENCEID==" + qInfo.getCredenceID();
			}
			if (qInfo.getClientID() != -1)
			{
				strSQL2 += " and bb.nBorrowClientID=" + qInfo.getClientID();
			}
			if (qInfo.getAmountFrom() != 0)
			{
				strSQL2 += " and aa.mAmount>=" + qInfo.getAmountFrom();
			}
			if (qInfo.getAmountTo() != 0)
			{
				strSQL2 += " and aa.mAmount<=" + qInfo.getAmountTo();
			}
			if (qInfo.getStartDate() != null)
			{
				strSQL2 += " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'";
			}
			if (qInfo.getEndDate() != null)
			{
				strSQL2 += " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'";
			}	        
	        
	        
//			if (qInfo.getUserID() > 0)
//			{
//				//修改查找
//				if (qInfo.getActionID() == 1)
//				{
////					strSQL2 += " and aa.nInputUserID = " + qInfo.getUserID();
//					strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//				}
//				//审核查找
//				else if (qInfo.getActionID() == 2)
//				{
//					if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.SUBMIT)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.CHECK)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.USED)
//					{
//						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.USED;
//					}
//					else if(qInfo.getStatusID()==LOANConstant.DiscountCredenceStatus.APPROVALING){
////						strSQL2 += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.APPROVALING;
//						strSQL2 += " and aa.nStatusID not in (" + strIn + ","+LOANConstant.DiscountCredenceStatus.APPROVALING+")";
//					}
//					else
//					{
//						strSQL2 += " and aa.nStatusID in (" + strIn + ")";
//					}
//				}
//			}
			
			
	        strSQL2 += " group by aa.id ) bbb";
	        strSQL2 += " where aaa.lCredenceID = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
	        strSQL2 += " loan_approvalsetting e,loan_approvalitem f";
	        strSQL2 += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+qInfo.getUserID()+")";
	        
			System.out.println("我的SQL:^^^^^^^^^^^^^^^"+strSQL+")"+UNION+strSQL2);
			String tempSQL=strSQL+")"+UNION+strSQL2;
			
			ps = prepareStatement(tempSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				TransDiscountCredenceInfo dci = new TransDiscountCredenceInfo();

				dci.setId(rs.getLong("lCredenceID")); //贴现凭证标示
				dci.setContractID(rs.getLong("lContractID")); //合同ID
				dci.setContractCode(rs.getString("sContractCode")); //合同编号
				dci.setApplyClient(rs.getString("sApplyClient")); //申请单位
				dci.setDiscountExamineAmount(rs.getDouble("mExamineAmount")); //贴现申请金额
				dci.setDiscountCheckAmount(rs.getDouble("mCheckAmount")); //贴现审核金额
				dci.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				dci.setDiscountDate(rs.getTimestamp("dtDiscountDate")); //贴现计息日
				dci.setStatusID(rs.getInt("nCredenceStatusID")); //贴现凭证状态
				dci.setBillSourceTypeID(rs.getLong("NBILLSOURCETYPEID"));
				dci.setAmount(rs.getDouble("mBillAmount"));
				dci.setInterest(rs.getDouble("mBillInterest"));
				dci.setCheckAmount(rs.getDouble("mBillAmount") - rs.getDouble("mBillInterest"));
				dci.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE")); //回购日期
				dci.setInOrOut(rs.getLong("NINOROUT")); //买入卖出
				dci.setDiscountTypeID(rs.getLong("NDISCOUNTTYPEID")); //类型
				dci.setRepurchaseTypeID(rs.getLong("NREPURCHASETYPEID")); //回购种类
				dci.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
				dci.setRepurchaseTerm(rs.getLong("NREPURCHASETERM")); //回购凭证的回购期限
				dci.setTransDiscountCredenceID(rs.getLong("NTRANSDISCOUNTCREDENCEID"));

				dci.setTransDiscountCredenceCode(LOANNameRef.getNameByID("scode", strTableName, "id", String.valueOf(dci.getTransDiscountCredenceID()), null));
				dci.setCount(lRecordCount);
				v.add(dci);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            e1.printStackTrace();
	        }
	    }
		return (v.size() > 0 ? v : null);
	}
	
	/**
	 *凭证的多笔查询操作
	*/
	public Collection findCredenceByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strIn = null;
		String strUserID = null;

		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;

		//模块类型
		long lModuleID = LOANConstant.ModuleType.CRAFTBROTHER;
		//业务类型
		long lLoanTypeID = 23;
		//操作类型
		long lActionID = LOANConstant.ApprovalAction.DISCOUNT_CREDENCE;

		try
		{
			initDAO();

			strIn = LOANConstant.DiscountCredenceStatus.SUBMIT + "," + LOANConstant.DiscountCredenceStatus.CHECK + "," + LOANConstant.DiscountCredenceStatus.USED;

			//计算记录总数
//			strSelect = " select count(*) ";
			strSQL = " from " + strTableName + " aa, Loan_ContractForm bb ";
			strSQL = strSQL + " where aa.nContractID=bb.ID ";

			//////////////////////查询条件////////////////////////////////////////////////////
			if (qInfo.getCredenceTypeID() != -1)
			{
				strSQL = strSQL + " and aa.NTYPEID=" + qInfo.getCredenceTypeID();
			}
			if (qInfo.getOfficeID() != -1)
			{
				strSQL = strSQL + " and bb.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getContractIDFrom() != -1)
			{
				strSQL = strSQL + " and bb.ID>=" + qInfo.getContractIDFrom();
			}
			if (qInfo.getContractIDTo() != -1)
			{
				strSQL = strSQL + " and bb.ID<=" + qInfo.getContractIDTo();
			}
			if (qInfo.getCredenceTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE && qInfo.getCredenceID() != -1)
			{
				strSQL = strSQL + " and aa.NTRANSDISCOUNTCREDENCEID==" + qInfo.getCredenceID();
			}
			if (qInfo.getClientID() != -1)
			{
				strSQL = strSQL + " and bb.nBorrowClientID=" + qInfo.getClientID();
			}
			if (qInfo.getAmountFrom() != 0)
			{
				strSQL = strSQL + " and aa.mAmount>=" + qInfo.getAmountFrom();
			}
			if (qInfo.getAmountTo() != 0)
			{
				strSQL = strSQL + " and aa.mAmount<=" + qInfo.getAmountTo();
			}
			if (qInfo.getStartDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'";
			}
			if (qInfo.getEndDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'";
			}

			strSQL = strSQL + " and aa.nStatusID in (" + strIn + ")";

			if (qInfo.getUserID() > 0)
			{
				//修改查找
//				if (qInfo.getActionID() == 1)
//				{
					strSQL += " and aa.nInputUserID = " + qInfo.getUserID();
					//strSQL += " and aa.nNextCheckUserID = " + lUserID;
					strSQL += " and aa.nNextCheckLevel = 1 ";
					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
//				}
				//审核查找
//				else if (qInfo.getActionID() == 2)
//				{
//					ApprovalDelegation appBiz = new ApprovalDelegation();
//					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
//					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(), qInfo.getUserID());
//
//					if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.SUBMIT)
//					{
//						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT + " and aa.nNextCheckUserID in " + strUserID;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.CHECK)
//					{
//						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK;
//					}
//					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.USED)
//					{
//						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.USED;
//					}
//					else
//					{
//						strSQL += " and ( ( aa.nStatusID = "
//							+ LOANConstant.DiscountCredenceStatus.SUBMIT
//							+ " and aa.nNextCheckUserID in "
//							+ strUserID
//							+ " ) or aa.nStatusID = "
//							+ LOANConstant.DiscountCredenceStatus.CHECK
//							+ " or aa.nStatusID = "
//							+ LOANConstant.DiscountCredenceStatus.USED
//							+ " ) ";
//					}
//				}
			}

//			Log.print(strSelect + strSQL);
//			ps = prepareStatement(strSelect + strSQL);
//			rs = ps.executeQuery();
//
//			if (rs != null && rs.next())
//			{
//				lRecordCount = rs.getLong(1);
//			}
//
//			rs.close();
//			rs = null;
//			ps.close();
//			ps = null;
//
//			lPageCount = lRecordCount / qInfo.getPageLineCount();
//
//			if ((lRecordCount % qInfo.getPageLineCount()) != 0)
//			{
//				lPageCount++;
//			}
//			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
//			switch ((int) qInfo.getOrderParam())
//			{
//				case 1 :
//					strSQL += " order by bb.sContractCode ";
//					break;
//				case 2 :
//					strSQL += " order by aa.sApplyClient ";
//					break;
//				case 3 :
//					strSQL += " order by aa.mAmount ";
//					break;
//				case 4 :
//					strSQL += " order by aa.mAmount-aa.mInterest ";
//					break;
//				case 5 :
//					strSQL += " order by bb.dtDiscountDate ";
//					break;
//				case 6 :
//					strSQL += " order by aa.mInterest ";
//					break;
//				case 7 :
//					strSQL += " order by bb.mDiscountRate";
//					break;
//				case 8 :
//					strSQL += " order by aa.DTREPURCHASEDATE";
//					break;
//				case 9 :
//					strSQL += " order by aa.nStatusID";
//					break;
//				default :
//					strSQL += " order by bb.sContractCode ";
//			}
//
//			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
//			{
//				strSQL += " desc";
//			}
//
//			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			//返回需求的结果集
//			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
//			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			strSQL =
				" select bb.ID lContractID,bb.sContractCode,bb.mDiscountRate,bb.dtDiscountDate,bb.mExamineAmount,bb.mCheckAmount,bb.NDISCOUNTTYPEID,bb.NINOROUT,bb.NREPURCHASETYPEID,aa.NBILLSOURCETYPEID,"
					+ " aa.ID lCredenceID,aa.sApplyClient,aa.nStatusID nCredenceStatusID,aa.mAmount mBillAmount,aa.mInterest mBillInterest,nvl(aa.nNextCheckLevel,1) nNextCheckLevel, aa.dtRepurchaseDate DTREPURCHASEDATE, "
					+ " aa.NTRANSDISCOUNTCREDENCEID NTRANSDISCOUNTCREDENCEID, aa.NREPURCHASETERM NREPURCHASETERM"
					+ strSQL;
//			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
//			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print(strSQL);
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				TransDiscountCredenceInfo dci = new TransDiscountCredenceInfo();

				dci.setId(rs.getLong("lCredenceID")); //贴现凭证标示
				dci.setContractID(rs.getLong("lContractID")); //合同ID
				dci.setContractCode(rs.getString("sContractCode")); //合同编号
				dci.setApplyClient(rs.getString("sApplyClient")); //申请单位
				dci.setDiscountExamineAmount(rs.getDouble("mExamineAmount")); //贴现申请金额
				dci.setDiscountCheckAmount(rs.getDouble("mCheckAmount")); //贴现审核金额
				dci.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				dci.setDiscountDate(rs.getTimestamp("dtDiscountDate")); //贴现计息日
				dci.setStatusID(rs.getInt("nCredenceStatusID")); //贴现凭证状态
				dci.setBillSourceTypeID(rs.getLong("NBILLSOURCETYPEID"));
				dci.setAmount(rs.getDouble("mBillAmount"));
				dci.setInterest(rs.getDouble("mBillInterest"));
				dci.setCheckAmount(rs.getDouble("mBillAmount") - rs.getDouble("mBillInterest"));
				dci.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE")); //回购日期
				dci.setInOrOut(rs.getLong("NINOROUT")); //买入卖出
				dci.setDiscountTypeID(rs.getLong("NDISCOUNTTYPEID")); //类型
				dci.setRepurchaseTypeID(rs.getLong("NREPURCHASETYPEID")); //回购种类
				dci.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
				dci.setRepurchaseTerm(rs.getLong("NREPURCHASETERM")); //回购凭证的回购期限
				dci.setTransDiscountCredenceID(rs.getLong("NTRANSDISCOUNTCREDENCEID"));

				dci.setTransDiscountCredenceCode(LOANNameRef.getNameByID("scode", strTableName, "id", String.valueOf(dci.getTransDiscountCredenceID()), null));
				dci.setCount(lRecordCount);
				v.add(dci);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return (v.size() > 0 ? v : null);
	}

	/**
	 *凭证的多笔查询操作(中油)
	*/
	public Collection cpfFindCredenceByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strIn = null;
		String strUserID = null;

		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;

		//模块类型
		long lModuleID = LOANConstant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = LOANConstant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = LOANConstant.ApprovalAction.DISCOUNT_CREDENCE;

		try
		{
			initDAO();

			strIn = LOANConstant.DiscountCredenceStatus.SUBMIT + "," + LOANConstant.DiscountCredenceStatus.CHECK + "," + LOANConstant.DiscountCredenceStatus.USED;

			//计算记录总数
			strSelect = " select count(*) ";
			strSQL = " from " + strTableName + " aa, Loan_ContractForm bb ";
			strSQL = strSQL + " where aa.nContractID=bb.ID ";

			//////////////////////查询条件////////////////////////////////////////////////////
			if (qInfo.getCredenceTypeID() != -1)
			{
				strSQL = strSQL + " and aa.NTYPEID=" + qInfo.getCredenceTypeID();
			}
			if (qInfo.getOfficeID() != -1)
			{
				strSQL = strSQL + " and bb.nOfficeID=" + qInfo.getOfficeID();
			}
			if (qInfo.getContractIDFrom() != -1)
			{
				strSQL = strSQL + " and bb.ID>=" + qInfo.getContractIDFrom();
			}
			if (qInfo.getContractIDTo() != -1)
			{
				strSQL = strSQL + " and bb.ID<=" + qInfo.getContractIDTo();
			}
			if (qInfo.getCredenceTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE && qInfo.getCredenceID() != -1)
			{
				strSQL = strSQL + " and aa.NTRANSDISCOUNTCREDENCEID==" + qInfo.getCredenceID();
			}
			if (qInfo.getClientID() != -1)
			{
				strSQL = strSQL + " and bb.nBorrowClientID=" + qInfo.getClientID();
			}
			if (qInfo.getAmountFrom() != 0)
			{
				strSQL = strSQL + " and aa.mAmount>=" + qInfo.getAmountFrom();
			}
			if (qInfo.getAmountTo() != 0)
			{
				strSQL = strSQL + " and aa.mAmount<=" + qInfo.getAmountTo();
			}
			if (qInfo.getStartDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartDate()) + "'";
			}
			if (qInfo.getEndDate() != null)
			{
				strSQL = strSQL + " and to_char(bb.dtDiscountDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndDate()) + "'";
			}

			strSQL = strSQL + " and aa.nStatusID in (" + strIn + ")";

			if (qInfo.getUserID() > 0)
			{
				//修改查找
				if (qInfo.getActionID() == 1)
				{
					strSQL += " and aa.nInputUserID = " + qInfo.getUserID();
					strSQL += " and aa.nNextCheckUserID = " + qInfo.getUserID();
					//strSQL += " and aa.nNextCheckLevel = 1 ";
					strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT;
				}
				//审核查找
				else if (qInfo.getActionID() == 2)
				{
					//ApprovalDelegation appBiz = new ApprovalDelegation();
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
					//strUserID = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,qInfo.getUserID());

					if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.SUBMIT)
					{
						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.SUBMIT + " and aa.nNextCheckUserID = " + qInfo.getUserID();
					}
					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.CHECK)
					{
						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.CHECK;
					}
					else if (qInfo.getStatusID() == LOANConstant.DiscountCredenceStatus.USED)
					{
						strSQL += " and aa.nStatusID = " + LOANConstant.DiscountCredenceStatus.USED;
					}
					else
					{
						strSQL += " and ( ( aa.nStatusID = "
							+ LOANConstant.DiscountCredenceStatus.SUBMIT
							+ " and aa.nNextCheckUserID = "
							+ qInfo.getUserID()
							+ " ) or aa.nStatusID = "
							+ LOANConstant.DiscountCredenceStatus.CHECK
							+ " or aa.nStatusID = "
							+ LOANConstant.DiscountCredenceStatus.USED
							+ " ) ";
					}
				}
			}

			Log.print(strSelect + strSQL);
			ps = prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			lPageCount = lRecordCount / qInfo.getPageLineCount();

			if ((lRecordCount % qInfo.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
			switch ((int) qInfo.getOrderParam())
			{
				case 1 :
					strSQL += " order by bb.sContractCode ";
					break;
				case 2 :
					strSQL += " order by aa.sApplyClient ";
					break;
				case 3 :
					strSQL += " order by aa.mAmount ";
					break;
				case 4 :
					strSQL += " order by aa.mAmount-aa.mInterest ";
					break;
				case 5 :
					strSQL += " order by bb.dtDiscountDate ";
					break;
				case 6 :
					strSQL += " order by aa.mInterest ";
					break;
				case 7 :
					strSQL += " order by bb.mDiscountRate";
					break;
				case 8 :
					strSQL += " order by aa.DTREPURCHASEDATE";
					break;
				case 9 :
					strSQL += " order by aa.nStatusID";
					break;
				default :
					strSQL += " order by bb.sContractCode ";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//返回需求的结果集
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			strSQL =
				" select bb.ID lContractID,bb.sContractCode,bb.mDiscountRate,bb.dtDiscountDate,bb.mExamineAmount,bb.mCheckAmount,bb.NDISCOUNTTYPEID,bb.NINOROUT,bb.NREPURCHASETYPEID,aa.NBILLSOURCETYPEID,"
					+ " aa.ID lCredenceID,aa.sApplyClient,aa.nStatusID nCredenceStatusID,aa.mAmount mBillAmount,aa.mInterest mBillInterest,nvl(aa.nNextCheckLevel,1) nNextCheckLevel, aa.dtRepurchaseDate DTREPURCHASEDATE, "
					+ " aa.NTRANSDISCOUNTCREDENCEID NTRANSDISCOUNTCREDENCEID, aa.NREPURCHASETERM NREPURCHASETERM"
					+ strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print(strSQL);
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				TransDiscountCredenceInfo dci = new TransDiscountCredenceInfo();

				dci.setId(rs.getLong("lCredenceID")); //贴现凭证标示
				dci.setContractID(rs.getLong("lContractID")); //合同ID
				dci.setContractCode(rs.getString("sContractCode")); //合同编号
				dci.setApplyClient(rs.getString("sApplyClient")); //申请单位
				dci.setDiscountExamineAmount(rs.getDouble("mExamineAmount")); //贴现申请金额
				dci.setDiscountCheckAmount(rs.getDouble("mCheckAmount")); //贴现审核金额
				dci.setDiscountRate(rs.getDouble("mDiscountRate")); //贴现利率
				dci.setDiscountDate(rs.getTimestamp("dtDiscountDate")); //贴现计息日
				dci.setStatusID(rs.getInt("nCredenceStatusID")); //贴现凭证状态
				dci.setBillSourceTypeID(rs.getLong("NBILLSOURCETYPEID"));
				dci.setAmount(rs.getDouble("mBillAmount"));
				dci.setInterest(rs.getDouble("mBillInterest"));
				dci.setCheckAmount(rs.getDouble("mBillAmount") - rs.getDouble("mBillInterest"));
				dci.setRepurchaseDate(rs.getTimestamp("DTREPURCHASEDATE")); //回购日期
				dci.setInOrOut(rs.getLong("NINOROUT")); //买入卖出
				dci.setDiscountTypeID(rs.getLong("NDISCOUNTTYPEID")); //类型
				dci.setRepurchaseTypeID(rs.getLong("NREPURCHASETYPEID")); //回购种类
				dci.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //下一个审核级别
				dci.setRepurchaseTerm(rs.getLong("NREPURCHASETERM")); //回购凭证的回购期限
				dci.setTransDiscountCredenceID(rs.getLong("NTRANSDISCOUNTCREDENCEID"));

				dci.setTransDiscountCredenceCode(LOANNameRef.getNameByID("scode", strTableName, "id", String.valueOf(dci.getTransDiscountCredenceID()), null));
				dci.setCount(lRecordCount);
				v.add(dci);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			finalizeDAO();
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw new LoanDAOException("失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return (v.size() > 0 ? v : null);
	}

	/**
	 * 选定的票据信息，操作Loan_DiscountContractBill表
	 * @param SelectedTransDiscountBillInfo 选定的票据信息
	 * @return SelectedTransDiscountBillInfo
	 */
	public SelectedTransDiscountBillInfo findBillInterestByBillID(SelectedTransDiscountBillInfo info) throws LoanDAOException
	{
		//SelectedTransDiscountBillInfo returnInfo = new SelectedTransDiscountBillInfo();
		String strSQL = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lBankCount = -1;

		try
		{
			initDAO();

			//计算记录总数
			strSQL =
				" select count(*),sum(nvl(a.mAmount,0)),sum(nvl(b.CheckAmount,0)) "
					+ " from Loan_DiscountContractBill a, RTRANSDISCOUNTCONTRACTBILL b where a.id = b.DISCOUNTCONTRACTBILLID "
					+ " and b.TRANSDISCOUNTCONTRACTID = "
					+ info.getContractID()
					+ " and a.nStatusID="
					+ Constant.RecordStatus.VALID
					+ " and a.ID in ( "
					+ info.getAllBillString()
					+ " ) ";

			Log.print(strSQL);
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				info.setCount(rs.getLong(1));
				info.setTotalAmount(rs.getDouble(2));
				info.setTotalRealAmount(rs.getDouble(3));
				info.setTotalAccrual(rs.getDouble(2) - rs.getDouble(3));
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			if (info.getAllBillID() != null && info.getAllBillID().length > 0)
			{
				strSQL =
					" select a.*, b.REPURCHASETERM billRepurchaseTerm, b.REPURCHASEDATE billRepurchaseDate "
						+ " from Loan_DiscountContractBill a, RTRANSDISCOUNTCONTRACTBILL b where a.id = b.DISCOUNTCONTRACTBILLID and a.id="
						+ info.getAllBillID()[0];

				Log.print(strSQL);
				System.out.print("我要的SQL"+strSQL);
				ps = prepareStatement(strSQL);
				rs = ps.executeQuery();

				if (rs != null && rs.next())
				{
					if (info.getAllBillID().length == 1)
					{
						info.setAcceptPOTypeID(rs.getLong("NACCEPTPOTYPEID"));
						info.setDraftCode(rs.getString("SCODE"));
						info.setCreatDate(rs.getTimestamp("DTCREATE"));
						info.setEndDate(rs.getTimestamp("DTEND"));
						info.setRepurchaseTerm(rs.getLong("billRepurchaseTerm"));
						info.setBillSourceType(rs.getLong("nbillsourcetypeid"));
						//承兑方
						info.setSBank(rs.getString("SBANK"));
					}
					info.setRepurchaseDate(rs.getTimestamp("billRepurchaseDate"));
				}

				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			Log.print("================== info.getCredenceType()" +info.getCredenceType());
			if( info.getCredenceType() == LOANConstant.CredenceType.REPURCHASECREDENCE )
			{
				//计算已回购金额
				TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(this.strTableName);
				double dRepurchasedAmount = 0;
				
				dRepurchasedAmount = dao.calculateRepurchasedAmount(info.getCredenceID(),-1);
				info.setRepurchasedAmount(dRepurchasedAmount);	
				Log.print("--------dRepurchasedAmount = " + dRepurchasedAmount);						
			}
			Log.print("--------dRepurchasedAmount1 = " + info.getRepurchasedAmount());
			finalizeDAO();
		}
		catch (Exception e)
		{
			throw new LoanDAOException("操作失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return info;
	}

	/**
	 *凭证的审核操作
	*/
	public long checkCredence(ApprovalTracingInfo info) throws LoanDAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";

		//相应操作常量
		//模块类型
		long lModuleID = info.getModuleID();
		//业务类型
		long lLoanTypeID = info.getLoanTypeID();
		//操作类型
		long lActionID = info.getActionID();
		
		long lOfficeID = info.getOfficeID();
		long lCurrencyID = info.getCurrencyID();

		//审批设置ID
		long lApprovalID = info.getApprovalID();
		
		long lLevel = -1;

		ApprovalSettingInfo appInfo = new ApprovalSettingInfo();
		ApprovalDelegation appbiz = new ApprovalDelegation();

		TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(strTableName);
		TransDiscountCredenceInfo tdcInfo = new TransDiscountCredenceInfo();
		TransDiscountCredenceInfo tempInfo = new TransDiscountCredenceInfo();
		tempInfo = dao.findCredenceInfoByID(info.getApprovalContentID());

		try
		{
			initDAO();
			
			//下一级审核人级别
			lLevel = appbiz.findApprovalUserLevel(lApprovalID, info.getNextUserID());
			Log.print("下一级审核人级别：" + lLevel);
			//审批设置
			appInfo = appbiz.findApprovalSetting(lApprovalID);

			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
			{
				//逻辑删除
				appbiz.deleteApprovalTracing(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, info.getApprovalContentID(), 2);

				tdcInfo.setId(info.getApprovalContentID());
				tdcInfo.setStatusID(LOANConstant.DiscountCredenceStatus.REFUSE);
				lResultID = Constant.ApprovalDecision.REFUSE;
				dao.update(tdcInfo);
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
			{
				tdcInfo.setId(info.getApprovalContentID());
				tdcInfo.setStatusID(LOANConstant.DiscountCredenceStatus.APPROVALING);
				tdcInfo.setNextCheckUserID(info.getNextUserID());
				if (appInfo.getIsPass() == Constant.YesOrNo.YES && lLevel > 0)
				{
				    tdcInfo.setNextCheckLevel(lLevel);
					Log.print("更新下一个审核级别（可越级）：" + lLevel);
				}
				else
				{
				    tdcInfo.setNextCheckLevel(tempInfo.getNextCheckLevel() + 1);
					Log.print("更新下一个审核级别（不可越级）：" + lLevel);
				}
				lResultID = Constant.ApprovalDecision.PASS;
				dao.update(tdcInfo);
				//strSQL = "update Loan_DiscountCredence set nNextCheckUserID=" + info.getNextUserID() + ", nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + ", nNextCheckLevel=nNextCheckLevel+1 where ID=" + info.getApprovalContentID();
			}

			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
			{
				tdcInfo.setId(info.getApprovalContentID());
				tdcInfo.setStatusID(LOANConstant.DiscountCredenceStatus.CHECK);
				tdcInfo.setNextCheckUserID(info.getNextUserID());
				lResultID = Constant.ApprovalDecision.FINISH;
				dao.update(tdcInfo);

				//: 入出库(回购凭证审批后在此处进行入、出库操作)
				if(lActionID == Constant.ApprovalAction.TRANSDISCOUNT_REPURCHASECREDENCE)
				{
					Iterator it=null;
					long inum = -1;
					long lApplyID = -1;
					TransDiscountApplyBillInfo info1 = new TransDiscountApplyBillInfo();
					info1 = inOrout(tdcInfo.getId());
					if(info1 != null)
					{
						inum = info1.getInOrOut();
						lApplyID = info1.getId();
					}
					
					// 卖出回购型的回购凭证作入库操作
					if(inum==LOANConstant.TransDiscountInOrOut.OUT){
						it=(in_out_depot(tdcInfo.getId(),BILLConstant.DraftOperationType.DraftIn,BILLConstant.DraftInOrOut.IN)).iterator();
						while(it.hasNext()){
							DraftStorageAssembleInfo draftStorageAssembleInfo=(DraftStorageAssembleInfo)it.next();
							//saveDraftIn(draftStorageAssembleInfo);
							DraftEJB draftEJB = new DraftEJB();
							long lRetrun = draftEJB.saveDraftIn(draftStorageAssembleInfo);
							if(lRetrun > 0)
							{
								System.out.println("入库成功!");
							}
							else
							{
								System.out.println("入库失败");
								//throw new IException("入库失败");
							}
						}				
					}
					// 买入回购型的回购凭证对应票据作出库操作
					else if(inum==LOANConstant.TransDiscountInOrOut.IN){
						it=(in_out_depot(tdcInfo.getId(),BILLConstant.DraftOperationType.DraftOut,BILLConstant.DraftInOrOut.OUT)).iterator();
						while(it.hasNext()){
							DraftStorageAssembleInfo draftStorageAssembleInfo=(DraftStorageAssembleInfo)it.next();
							saveDraftOut(draftStorageAssembleInfo);
							System.out.println("出库成功!");
						}
						TransDiscountApplyDAO tdaDao = new TransDiscountApplyDAO("");
						Collection coll = tdaDao.findOutBillByTransDiscountApplyID(lApplyID);
						if(coll != null && coll.size() > 0)
						{
							it = coll.iterator();
							while(it.hasNext())
							{
								TransDiscountApplyBillInfo tdabinfo = (TransDiscountApplyBillInfo)it.next();
	            				TransDiscountApplyInfo aInfo1 = new TransDiscountApplyInfo();
	            				aInfo1 = tdaDao.findByID(lApplyID);
	            				
	            				long lCraftInID = tdaDao.getCraftInIDbyBillID(tdabinfo.getId());
	            				
	            				if(lCraftInID > 0)
	                    		{
	                    			DraftDelegation delegation = new DraftDelegation();
	                    			DraftStorageAssembleInfo draftStorageAssembleInfo = new DraftStorageAssembleInfo();
	                    			TransDraftOutInfo transDraftOutInfo=new TransDraftOutInfo();
	                    			
	                    			transDraftOutInfo.setCurrencyID(tdabinfo.getCurrencyId());
	                    			transDraftOutInfo.setOfficeID(tdabinfo.getOfficeId());
	                    			transDraftOutInfo.setInputDate(Env.getSystemDate(lOfficeID,lCurrencyID));
	                    			transDraftOutInfo.setInputUserID(info.getUserID());
	                    			transDraftOutInfo.setStatusID(Constant.RecordStatus.VALID);
	                    			UtilOperation utilOperation=new UtilOperation(); 
	                    			transDraftOutInfo.setTransCode(utilOperation.getNewDraftTransactionNo(tdabinfo.getOfficeId(),tdabinfo.getCurrencyId(),BILLConstant.DraftOperationType.DraftOut));
	                    			transDraftOutInfo.setTransTypeID(BILLConstant.DraftOperationType.DraftIn);
	                    			//transDraftOutInfo.setTransTypeID(LOANConstant.LoanType.ZTX);
	                    			transDraftOutInfo.setOutDate(tdabinfo.getCreate());
	                    			// 加入卖出转贴现出库类型
	                    			// 逆回购返售（出库）
	                    			transDraftOutInfo.setBillDestinationID(BILLConstant.BillWhither.OBREPURCHASESELLOUT);
	                    			
	                    			//transDraftOutInfo.setOutContractCode(tdabinfo.getContractID());//卖出时合同编号
	                    			
	                    			System.out.println("-----------取得入库信息---------");
	                    			draftStorageAssembleInfo = delegation.findDraftInByID(lCraftInID);
	                    			draftStorageAssembleInfo.setTransDraftOutInfo(transDraftOutInfo);
	                    			
	                    			//保存出库记录
	                    			System.out.println("-----------保存出库信息---------");
	                    			delegation.saveDraftOut(draftStorageAssembleInfo);
	                    		}
	                    		else
	                    		{
	                    			System.out.println("根据票据id取得相应入库信息出错！");
	                    			throw new BillException();
	                    		}
							}
						}
					}
					//:~ end 入出库
				}				
			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
			{
				tdcInfo.setId(info.getApprovalContentID());
				tdcInfo.setStatusID(LOANConstant.DiscountCredenceStatus.SUBMIT);
				tdcInfo.setNextCheckUserID(info.getInputUserID());
				tdcInfo.setNextCheckLevel(1);
				lResultID = Constant.ApprovalDecision.RETURN;
				dao.update(tdcInfo);
				//strSQL = " update Loan_DiscountCredence set nNextCheckUserID=nInputUserID, nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + ", nNextCheckLevel=1 where ID=" + lApprovalContentID;
			}

			/*回购凭证的审核后必须改变相关票据的是否可卖出状态*/
			if (tempInfo.getTypeID() == LOANConstant.CredenceType.REPURCHASECREDENCE)
			{
				long lSellStatusID = -1;
				if (tempInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
				{
					lSellStatusID = Constant.YesOrNo.NO;
				}
				else if (tempInfo.getInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
				{
					lSellStatusID = Constant.YesOrNo.YES;
				}

				//更改状态
				strSQL =
					" update LOAN_DISCOUNTCONTRACTBILL set NSELLSTATUSID = "
						+ lSellStatusID
						+ " where ID in ( "
						+ " select a.id from LOAN_DISCOUNTCONTRACTBILL a, RTRANSDISCOUNTCREDENCEBILL b"
						+ " where a.id = b.DISCOUNTCONTRACTBILLID and b.transdiscountcredenceid = "
						+ info.getApprovalContentID()
						+ " ) ";

				Log.print(strSQL);
				ps = prepareStatement(strSQL);
				ps.executeUpdate();
			}

			finalizeDAO();
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new LoanDAOException("审核失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return info.getApprovalContentID();
	}

	/**
		 *  add by lazhang
		 * 中油凭证的审核操作
		*/
	public long cpfCheckCredence(ApprovalTracingInfo info) throws LoanDAOException, RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		//long lOBStatusID = 0;
		String strSQL = "";
		//String strSQLBill = "";

		Log.print("==========================" + strTableName);

		//TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO(strTableName);
		//相应操作常量
		//模块类型
		//long lModuleID = info.getModuleID();
		//业务类型
		//long lLoanTypeID = info.getLoanTypeID();
		//操作类型
		//long lActionID = info.getActionID();

		//审批设置ID

		//TransDiscountCredenceInfo credenceInfo = new TransDiscountCredenceInfo();

		try
		{
			initDAO();

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			strSQL = "";
			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
			{
				//删除以前的审核纪录
				strSQL = " update ReviewOpinion set nStatusID=? where nReviewTypeID=? and nReviewContentID=? ";
				ps = prepareStatement(strSQL);
				ps.setLong(1, Constant.RecordStatus.INVALID);
				ps.setLong(2, info.getActionID());
				ps.setLong(3, info.getApprovalContentID());
				ps.executeUpdate();
				ps.close();
				ps = null;

				strSQL = "update " + strTableName + " set nStatusID=" + LOANConstant.DiscountCredenceStatus.REFUSE + " where ID=" + info.getApprovalContentID();
				//strSQLBill = "update discountbill set ndiscountcredenceID = null ,ob_nDiscountCredenceID = null where nDiscountCredenceID = " + info.getApprovalContentID();

				//	lOBStatusID = OBNotes.CODE_OBLOAN_INSTR_STATUS_REFUSE;

				//					lResultID = Constant.ApprovalDecision.REFUSE;
				//					credenceInfo.setId(info.getApprovalContentID());
				//					credenceInfo.setStatusID(LOANConstant.DiscountCredenceStatus.REFUSE);
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
			{
				//					TransDiscountCredenceInfo tempInfo = new TransDiscountCredenceInfo();
				//					tempInfo = this.findCredenceInfoByID(info.getApprovalContentID());
				//					credenceInfo.setId( info.getApprovalContentID() );
				//					credenceInfo.setStatusID( LOANConstant.DiscountCredenceStatus.SUBMIT );
				//					credenceInfo.setNextCheckUserID( info.getNextUserID() );
				//					credenceInfo.setNextCheckLevel( tempInfo.getNextCheckLevel() + 1 );
				//					lResultID = Constant.ApprovalDecision.PASS;

				strSQL = "update " + strTableName + " set nNextCheckUserID=" + info.getNextUserID() + " where ID=" + info.getApprovalContentID();
				//	lOBStatusID = OBNotes.CODE_OBLOAN_INSTR_STATUS_ACCEPT;
			}

			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER)
				//审批&&最后
			{
				//					credenceInfo.setId( info.getApprovalContentID() );
				//					credenceInfo.setStatusID( LOANConstant.DiscountCredenceStatus.CHECK );
				//					credenceInfo.setNextCheckUserID( info.getNextUserID() );
				//					lResultID = Constant.ApprovalDecision.FINISH;

				strSQL =
					"update "
						+ strTableName
						+ " set nNextCheckUserID="
						+ info.getNextUserID()
						+ ", nStatusID="
						+ LOANConstant.DiscountCredenceStatus.CHECK
						+ " where ID="
						+ info.getApprovalContentID();
				//	lOBStatusID = OBNotes.CODE_OBLOAN_INSTR_STATUS_EXAMINE;

			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
			{
				//					credenceInfo.setId( info.getApprovalContentID() );
				//					credenceInfo.setStatusID( LOANConstant.DiscountCredenceStatus.SUBMIT );
				//					credenceInfo.setNextCheckUserID( info.getInputUserID() );
				//					credenceInfo.setNextCheckLevel( 1 );
				//lResultID = Constant.ApprovalDecision.RETURN;
				//System.out.println("Update reviewopinion set nnextuserid="+nNextUserID+", nStatusID="+Notes.CODE_RECORD_STATUS_INVALID+" where nReviewContentID="+nReviewContentID );
				//ps = conn.prepareStatement ("Update reviewopinion set nnextuserid="+nNextUserID+", nStatusID="+Notes.CODE_RECORD_STATUS_INVALID+" where nreviewtypeid="+Notes.CODE_EXAMINE_TYPE_DISCOUNT+" and nReviewContentID="+nReviewContentID );
				//ps.executeUpdate ();
				//ps.close();
				//ps=null;
				//strSQL="update discountapply set nStatusID="+Notes.CODE_DISCOUNT_STATUS_SUBMIT+"where ID="+nReviewContentID;
				// by Michele Yu
				strSQL = "update " + strTableName + " set nNextCheckUserID=nInputUserID, nStatusID=" + LOANConstant.DiscountCredenceStatus.SUBMIT + " where ID=" + info.getApprovalContentID();
				//	lOBStatusID = OBNotes.CODE_OBLOAN_INSTR_STATUS_ACCEPT;
			}

			System.out.println(strSQL);
			ps = prepareStatement(strSQL);
			ps.executeUpdate();
			ps.close();
			ps = null;

			//				strSQL = "update ob_discount set nStatusID = ? where ID = (select id from ob_discount where nInDiscountID = ?)";
			//				System.out.println(strSQL);
			//				ps = conn.prepareStatement(strSQL);
			//				ps.setLong(1, lOBStatusID);
			//				ps.setLong(2, info.getApprovalContentID());
			//				ps.executeUpdate();
			//				ps.close();
			//				ps = null;

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//				if(info.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
			//				{	
			//					System.out.println(strSQLBill);
			//					ps = conn.prepareStatement(strSQLBill);
			//					ps.executeUpdate();
			//					ps.close();
			//					ps = null;
			//				}
			//addReviewOpinion(info);
			finalizeDAO();
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			//throw new RemoteException(ex.getMessage());
			throw new LoanDAOException("审核失败", ex);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		
		return info.getApprovalContentID();
	}

	/**
	 * @author lazhang
	 * @param info
	 * 在审核完毕之后添加审核意见  insert into ReviewOpinion
	 */

	public void addReviewOpinion(ApprovalTracingInfo info) throws LoanDAOException, RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lMaxID = -1;
		long lSerialID = -1;
		String strSQL = "";
		Log.print("==========================" + strTableName);
		try
		{
			initDAO();

			strSQL = "select nvl(max(ID)+1,1) ,nvl(max(nSerialID)+1,1) from reviewopinion";
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
				lSerialID = rs.getLong(2);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			strSQL = "";

			if (info.getCheckActionID() != LOANConstant.Actions.REJECT) //拒绝
			{
				strSQL = "insert into ReviewOpinion (ID, nReviewTypeID, nReviewContentID, nSerialID, nUserID, nNextUserID, sOpinion, dtDate, nResultID, nStatusID) values (?,?,?,?,?,?,?,sysdate,?,?)";
				System.out.println(strSQL);
				ps = prepareStatement(strSQL);
				ps.setLong(1, lMaxID);
				ps.setLong(2, info.getActionID());
				ps.setLong(3, info.getApprovalContentID());
				ps.setLong(4, lSerialID);
				ps.setLong(5, info.getUserID());
				ps.setLong(6, info.getNextUserID());
				ps.setString(7, info.getOpinion());
				ps.setLong(8, info.getResultID());
				ps.setLong(9, info.getStatusID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			finalizeDAO();

		} catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	}

	/**
	 *
	 * 更新票据表的凭证id字段（买入型）
	 *
	 */
	private long updateBillCredenceID(long lDiscountCredenceID, long lDiscountBillID[]) throws LoanDAOException
	{
		long lReturn = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try
		{
			initDAO();

			if (lDiscountCredenceID > 0)
			{
				//更新记录
				strSQL = " update Loan_DiscountContractBill set nDiscountCredenceID=? where nDiscountCredenceID=? ";

				Log.print(strSQL);
				ps = prepareStatement(strSQL);
				ps.setLong(1, -1);
				ps.setLong(2, lDiscountCredenceID);

				ps.executeUpdate();
				ps.close();
				ps = null;

				//更新记录
				for (int i = 0; i < lDiscountBillID.length; i++)
				{
					if (lDiscountBillID[i] > 0)
					{
						//更新记录
						strSQL = " update Loan_DiscountContractBill set nDiscountCredenceID=? where ID=? ";

						Log.print(strSQL);
						ps = prepareStatement(strSQL);
						ps.setLong(1, lDiscountCredenceID);
						ps.setLong(2, lDiscountBillID[i]);
                        System.out.println("lDiscountBillID[i]"+lDiscountBillID[i]);
                        System.out.println(strSQL);
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new LoanDAOException("生成贴现凭证编号失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }

		return lReturn;
	}
	
	//－－－－－－－－计算已回购金额 add by lazhang start－－－－－－－－－－
	public double calculateRepurchasedAmount(long lCredenceID ,long lContractID) throws LoanDAOException
	{
		double dRepurchasedAmount =0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";

		try
		{
			initDAO();

			//--if  凭证id>0  前提该合同为回购型转贴现申请合同下的凭证
			if (lCredenceID>0)
			{
				strSQL = " select (subaccount.mopenamount- subaccount.mbalance)  sumbalance from sett_subaccount subaccount ";
				strSQL +=" where subaccount.al_nloannoteid = " +lCredenceID ;	
				
				Log.print(strSQL);
				ps = prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					dRepurchasedAmount = rs.getDouble(1);								
				}						
				rs.close();
				rs = null;
				ps.close();
				ps = null;										
			}
			else
			{
				if(lContractID>0)
				{
					strSQL=" select sum(subaccount.mopenamount - subaccount.mbalance) sumbalance from ";
					strSQL +=" discountcredence   credence,	sett_subaccount subaccount ";
					strSQL +=" where credence.ncontractid= "+lContractID+" and subaccount.al_nloannoteid = credence.id ";
					
					Log.print(strSQL);
					ps = prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dRepurchasedAmount = rs.getDouble(1);								
					}							
					rs.close();
					rs = null;
					ps.close();
					ps = null;	
				}
			}				
			Log.print("==============");
			Log.print("已回购金额=" + dRepurchasedAmount);
			Log.print("==============");	
			finalizeDAO();		
		}
		catch (Exception e)
		{
			throw new LoanDAOException(" 计算已回购金额失败 ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		return dRepurchasedAmount;
	}
	
	/**
	 * 判断出库入库
	 * @return
	 * @throws LoanDAOException
	 */
	private TransDiscountApplyBillInfo inOrout(long id) throws LoanDAOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long ninoroutType=-1;
		TransDiscountApplyBillInfo info = null;
		try
		{
			initDAO();
			strSQL="select id,ninorout from cra_loanform where id=(select nloanid from loan_contractform where id=(select ncontractid from Loan_DiscountCredence where id="+id+"))";
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();				
			if(rs.next()){
				info = new TransDiscountApplyBillInfo();
				ninoroutType=rs.getLong("ninorout");
				info.setInOrOut(ninoroutType);
				info.setId(rs.getLong("id"));
				System.out.println(rs.getLong("ninorout"));
				System.out.println(rs.getLong("ninorout"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;	
			finalizeDAO();
		}catch (Exception e){
				throw new LoanDAOException(" 判断入库出库有误! ", e);
		}finally
		    {
		        try
		        {
		            finalizeDAO();
		        } catch (ITreasuryDAOException e1)
		        {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
		    }
		return info;
	}
	
	/**
	 * 得到转贴现(入出)库信息
	 * @param id 票据id
	 * @param draftOperationType
	 * @param draftInOrOut
	 * @return
	 * @throws LoanDAOException
	 */
	private Collection in_out_depot(long id,long draftOperationType,long draftInOrOut) throws LoanDAOException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		Vector v=new Vector();
		try
		{	

			initDAO();
			
			strSQL="select a.id ID,a.scode Scode,a.nacceptpotypeid Acceptpotypeid," +
					"a.mamount Amount,b.nborrowclientid Borrowclientid," +
					"a.dtcreate Dtcreate,a.dtend Dtend,b.ncurrencyid Currencyid," +
					"b.nofficeid Officeid,b.ninputuserid Inputuserid,b.scontractcode Scontractcode " +
					"from LOAN_DISCOUNTCONTRACTBILL a,loan_contractform b ";
			strSQL+=" where a.ndiscountcredenceid in(select id from Loan_DiscountCredence where id="+id+" and nstatusid = 3) and ";
			strSQL+=" b.id=(select ncontractid from Loan_DiscountCredence where id="+id+")";

			System.out.println("我的SQL^^^^^^^^^"+strSQL);
			Log.print(strSQL);
			ps = prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			while(rs.next()){				
				//设置入库信息transDraftInInfo
				System.out.println(rs.getLong("ID"));
				TransDraftInInfo transDraftInInfo = new TransDraftInInfo();
				transDraftInInfo.setCurrencyID(rs.getLong("Currencyid"));
				transDraftInInfo.setOfficeID(rs.getLong("Officeid"));
				transDraftInInfo.setInputUserID(rs.getLong("Inputuserid"));
				// 入库，票据来源：正回购购回
				if(draftOperationType == BILLConstant.DraftOperationType.DraftIn)
				{
					transDraftInInfo.setBillSoureID(BILLConstant.BillSourceType.REPURCHASEBUYBACK);
				}				
				transDraftInInfo.setInDate(rs.getTimestamp("Dtcreate"));
				UtilOperation utilOperation=new UtilOperation();
				transDraftInInfo.setTransCode(utilOperation.getNewDraftTransactionNo(transDraftInInfo.getOfficeID(),transDraftInInfo.getCurrencyID(),draftOperationType));
				transDraftInInfo.setStatusID(Constant.RecordStatus.VALID);
				transDraftInInfo.setTransTypeID(LOANConstant.LoanType.ZTX);
				//transDraftInInfo.setTransTypeID(draftOperationType);
				transDraftInInfo.setInContractCode(rs.getString("Scontractcode"));
				System.out.println(transDraftInInfo.getTransCode());
				
				//设置票据信息discountContractBillInfo
				DiscountContractBillInfo discountContractBillInfo = new DiscountContractBillInfo();
				discountContractBillInfo.setId(rs.getLong("ID"));
				discountContractBillInfo.setNStorageStatusID(draftInOrOut);
				discountContractBillInfo.setNInputUserID(transDraftInInfo.getInputUserID());
				discountContractBillInfo.setNOfficeID(transDraftInInfo.getOfficeID());
				discountContractBillInfo.setNCurrencyID(transDraftInInfo.getCurrencyID());
				discountContractBillInfo.setNStatusID(Constant.RecordStatus.VALID);
				discountContractBillInfo.setNModuleSourceID(Constant.ModuleType.BILL);
				discountContractBillInfo.setNQueryStatusID(BILLConstant.BillQuery.NOTQUERY);	
				discountContractBillInfo.setDtCreate(rs.getTimestamp("Dtcreate"));
				discountContractBillInfo.setDtEnd(rs.getTimestamp("Dtend"));
				discountContractBillInfo.setMAmount(rs.getDouble("Amount"));
//				discountContractBillInfo.setNDraftTypeID(rs.getLong("Acceptpotypeid"));
				discountContractBillInfo.setSCode(rs.getString("Scode"));
//				
				DraftStorageAssembleInfo draftStorageAssembleInfo=new DraftStorageAssembleInfo();
				draftStorageAssembleInfo.setDiscountContractBillInfo(discountContractBillInfo);
				draftStorageAssembleInfo.setTransDraftInInfo(transDraftInInfo);
				v.add(draftStorageAssembleInfo);
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;														
			finalizeDAO();		
		}
		catch (Exception e)
		{
			throw new LoanDAOException(" 入出库有错误! ", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
		return v;
	}
	
	private long saveDraftIn(DraftStorageAssembleInfo draftStorageAssembleInfo) throws RemoteException,LoanDAOException{
    	DiscountContractBillDao billDao=new DiscountContractBillDao();
		TransDraftInDAO dao=new TransDraftInDAO();
		long newBillId=-1;
		long ReturnId=-1;//-1表示更新操作
		//取得保存所需实体
		DiscountContractBillInfo discountContractBillInfo = new DiscountContractBillInfo();
		TransDraftInInfo transDraftInInfo=null;
		if(draftStorageAssembleInfo.getDiscountContractBillInfo()!=null&&draftStorageAssembleInfo.getTransDraftInInfo()!=null)
		{
			discountContractBillInfo=draftStorageAssembleInfo.getDiscountContractBillInfo();
			transDraftInInfo=draftStorageAssembleInfo.getTransDraftInInfo();
		}
		else
		{
			System.out.println("集合类DraftStorageAssembleInfo，内容不全！");
			//throw new BillException("Bill_E036","DraftStorageAssembleInfo",null);
		}
		
		try {
//			调用票据风险管理模块方法判断当前票据是否在黑名单中
			BlackBillInfo blackBillInfo=new BlackBillInfo();
			BlackBillDao blackBillDao=new BlackBillDao();
			
			blackBillInfo.setBillTypeID(discountContractBillInfo.getNDraftTypeID());
			blackBillInfo.setBillCode(discountContractBillInfo.getSCode());
			blackBillInfo.setStatusID(Constant.RecordStatus.VALID);
			blackBillInfo.setOfficeID(discountContractBillInfo.getNOfficeID());
			blackBillInfo.setCurrencyID(discountContractBillInfo.getNCurrencyID());
			if(blackBillDao.isBillInBlackList(blackBillInfo))
			{
				//Bill_E028=该票据在黑名单中，不能?！
				throw new BillException("Bill_E028","入库",null);
			}
			
			//新增票据保存入库
        	Timestamp tsNow=Env.getSystemDateTime();
        	
			transDraftInInfo.setBillID(discountContractBillInfo.getId());
			transDraftInInfo.setInputDate(tsNow);
			ReturnId=dao.add(transDraftInInfo);
			//已存在票据做更新操作
			DiscountContractBillInfo updateBill = new DiscountContractBillInfo();
			updateBill.setId(discountContractBillInfo.getId());
			updateBill.setNStorageTransID(ReturnId);
			updateBill.setDtModifyDate(tsNow);
			billDao.update(updateBill);
			
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				//throw new BillException("Bill_E032",e);
			} catch (BillException ex){
				ex.printStackTrace();
				//throw new BillException(ex,ctx);
			}
			return ReturnId;
    }	
	
	private long saveDraftOut(DraftStorageAssembleInfo draftStorageAssembleInfo) throws RemoteException,LoanDAOException{
    	System.out.println("----------保存汇票出库信息----------");
    	long ReturnId=-1;
    	Timestamp tsNow=Env.getSystemDateTime();
       	//取得保存所需实体
    	DiscountContractBillInfo discountContractBillInfo;
    	TransDraftInInfo transDraftInInfo=null;
    	TransDraftOutInfo transDraftOutInfo=null;
    	DiscountContractBillDao discountContractBillDao=new DiscountContractBillDao();
    	TransDraftInDAO transDraftInDAO=new TransDraftInDAO();
    	TransDraftOutDao transDraftOutDao=new TransDraftOutDao();
    	if(draftStorageAssembleInfo.getDiscountContractBillInfo()!=null&&draftStorageAssembleInfo.getTransDraftInInfo()!=null)
    	{
    		discountContractBillInfo=draftStorageAssembleInfo.getDiscountContractBillInfo();
    		transDraftInInfo=draftStorageAssembleInfo.getTransDraftInInfo();
    		transDraftOutInfo=draftStorageAssembleInfo.getTransDraftOutInfo();
    	}
    	else
    	{
    		System.out.println("集合类DraftStorageAssembleInfo，内容不全！");
    		throw new LoanDAOException("集合类DraftStorageAssembleInfo，内容不全",null);
    	}
    	try {
//			调用票据风险管理模块方法判断当前票据是否在黑名单中
			BlackBillInfo blackBillInfo=new BlackBillInfo();
			BlackBillDao blackBillDao=new BlackBillDao();
			
			blackBillInfo.setBillTypeID(discountContractBillInfo.getNDraftTypeID());
			blackBillInfo.setBillCode(discountContractBillInfo.getSCode());
			blackBillInfo.setStatusID(Constant.RecordStatus.VALID);
			blackBillInfo.setOfficeID(discountContractBillInfo.getNOfficeID());
			blackBillInfo.setCurrencyID(discountContractBillInfo.getNCurrencyID());
			try {
				if(blackBillDao.isBillInBlackList(blackBillInfo))
				{
					//Bill_E028=该票据在黑名单中，不能?！
//				throw new BillException("Bill_E028","出库",null,this.ctx);
				}
			} catch (BillException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if(transDraftOutInfo!=null&&transDraftOutInfo.getId()<0)
    		{
    			System.out.println("----------新增保存---------");
		    	//设置修改时间,修改人
		    	discountContractBillInfo.setDtModifyDate(tsNow);
		    	discountContractBillInfo.setNModifyUserID(transDraftOutInfo.getInputUserID());
		    	transDraftInInfo.setModifyDate(tsNow);
		    	transDraftInInfo.setModifyUserID(transDraftOutInfo.getInputUserID());
		    	//保存出库信息
	    		transDraftOutInfo.setBillID(discountContractBillInfo.getId());
	    		transDraftOutInfo.setTransDraftInID(transDraftInInfo.getId());
	    		ReturnId=transDraftOutDao.partSave(transDraftOutInfo);
	    		discountContractBillInfo.setNStorageStatusID(BILLConstant.DraftInOrOut.OUT);
	    		discountContractBillInfo.setNStorageTransID(ReturnId);
	    		discountContractBillDao.update(discountContractBillInfo);
	    		transDraftInDAO.update(transDraftInInfo);
    		}
    	} catch (ITreasuryDAOException e) {
    		e.printStackTrace();
//    		throw new BillException("Bill_E012",e,ctx);
    	}
    	return ReturnId;
    }	
	//－－－－－－－－计算已回购金额 add by lazhang   end－－－－－－－－－－

	//test
	public static void main(String args[])throws LoanDAOException,RemoteException
	{
//		TransDiscountCredenceDAO dao = new TransDiscountCredenceDAO("DISCOUNTCREDENCE");
//		TransDiscountCredenceQueryInfo queryInfo = new TransDiscountCredenceQueryInfo();
//		TransDiscountCredenceInfo credenceInfo = new TransDiscountCredenceInfo();
//		ApprovalTracingInfo appInfo = new ApprovalTracingInfo();
//		appInfo.setModuleID(LOANConstant.ModuleType.LOAN);
//		appInfo.setLoanTypeID(LOANConstant.ApprovalLoanType.TX);
//		appInfo.setActionID(LOANConstant.ApprovalAction.DISCOUNT_CREDENCE);
//		appInfo.setApprovalContentID(1);
//		appInfo.setCheckActionID(LOANConstant.Actions.RETURN);
//		//credenceInfo.setId(1);
//		queryInfo.setPageNo(1);
//		queryInfo.setOrderParam(1);
//		queryInfo.setActionID(1);
//		queryInfo.setUserID(91);
//		Collection c = null;
//		long lReturn = -1;
//		long[] lIDList = { 1, 2, 1 };
//		SelectedTransDiscountBillInfo selectInfo = new SelectedTransDiscountBillInfo();
//		selectInfo.setContractID(10066);
//		selectInfo.setAllBillString("1994");
//		credenceInfo.setId(1595);
//		credenceInfo.setNextCheckUserID(1683);
//		try
//		{
//			//c = dao.findTransDiscountCredenceBill(50005,-1,1);
//			//c = dao.findTransDiscountCredenceBill(500,-1,-1);
//			//c = dao.cpfFindCredenceByMultiOption(queryInfo);
//			
//			selectInfo = dao.findBillInterestByBillID(selectInfo);
//			//credenceInfo = dao.findCredenceInfoByID(1595);
//			//  			dao.checkCredence(appInfo);
//			//c=dao.findBillByTransDiscountCredenceID(3550001);
//			//double c1=0;
//			//c1=dao.calculateRepurchasedAmount(4800001,-1);
//			//if (c != null)
//				//Log.print("--------c.size = " + c1);
//			//else
//			//	Log.print("null");
//			//			//Log.print("----------ok:"+selectInfo.getAcceptPOTypeID()+"  "+selectInfo.getDraftCode()+"  "+selectInfo.getTotalRealAmount());
//			//			Log.print("-----"+credenceInfo.getTransDiscountCredenceCode());
//			//dao.cpfCheckCredence(appInfo);
//			
//		}
//		catch (Exception e)
//		{
//			Log.print("error");
//		}
		TransDiscountCredenceDAO dao=new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
		Iterator it=null;
//		long inum=dao.inOrout(553);
//		if(inum==1){
//			it=(dao.in_out_depot(553,BILLConstant.DraftOperationType.DraftIn,BILLConstant.DraftInOrOut.IN)).iterator();
//			while(it.hasNext()){
//				DraftStorageAssembleInfo draftStorageAssembleInfo=(DraftStorageAssembleInfo)it.next();
//				dao.saveDraftIn(draftStorageAssembleInfo);
//				System.out.println("入库成功!");
//			}				
//		}
	}
	
	/**
     * added by xwhe 2007/09/12
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
	public long update(long loanID, long userID, long newStatusID)
	throws Exception {
       PreparedStatement ps = null;
       Connection conn = null;
       String strSQL = null;
       long lResult = -1;

      try {
	conn = Database.getConnection();
	strSQL = " update LOAN_DISCOUNTCREDENCE set nStatusID=? where ID=?";

	ps = conn.prepareStatement(strSQL);
	ps.setLong(1, newStatusID);
	ps.setLong(2, loanID);

	lResult = ps.executeUpdate();
	cleanup(ps);
	cleanup(conn);

	if (lResult < 0) {
		log.info("update LOAN_DISCOUNTCREDENCE property info error:" + lResult);
		return -1;
	} else {
		return loanID;
	}
} catch (Exception e) {

	cleanup(ps);
	cleanup(conn);
	throw e;
} finally {

	cleanup(ps);
	cleanup(conn);
}

}
	
}

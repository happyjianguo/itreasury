package com.iss.itreasury.settlement.transferloancontract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanAmountDetailinfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class TransferLoanAmountDetailDao extends SettlementDAO 
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public TransferLoanAmountDetailDao()
	{
		super("SETT_TRANSFERLOANDETAIL");
	}
	public TransferLoanAmountDetailDao(Connection conn)
	{
		super("SETT_TRANSFERLOANDETAIL",conn);
	}
	public Collection findDetailinfoByTransferId(TransferLoanContractInfo info) throws Exception
	{
		Collection colResult = null;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select det.sellamount,t.* ,loc.scontractcode contractcode,lop.scode paycode,sea.saccountno loanaccountdode" +
					" from Sett_Transferloandetail t , loan_contractform loc, loan_payform lop,sett_account sea, " +
					" (select nvl(sum(a.amount), 0) sellamount,a.noticeformid" +
					" from sett_transferloandetail a ,sett_transferloanamount b " +
					" where a.statusid=" +CRAconstant.TransactionStatus.SAVE +
					" and a.transferamountid!=   " + info.getID() +
					" and b.id = a.transferamountid " +
					" and b.transfercontractid = " + info.getTRANSFERCONTRACTID()+
					" group by a.noticeformid) det " +
					" where  t.contractid=loc.id " +
					" and t.noticeformid=lop.id "+
					" and t.loanaccountid=sea.id " +
					" and t.noticeformid=det.noticeformid(+) "+
					" and t.transferamountid = " + info.getID());
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			
			colResult = getDataEntitiesFromResultSet(TransferLoanAmountDetailinfo.class);
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
		return colResult;
	}
	
	public void deleteTransferDetail(long transferamountid) throws Exception
	{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from SETT_TRANSFERLOANDETAIL where transferamountid= " + transferamountid);
			String strSQL = buffer.toString();
			log.info(strSQL);
			prepareStatement(strSQL);
			executeUpdate();
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
	}
	
	/**
	 * 说明：根据放宽通知单号和转让和合同明细计算未转让金额
	 * 作者:minzhao
	 * 日期:Aug 14, 2009
	 * 参数:@param noticeformid
	 * 参数:@param id
	 * 参数:@return
	 * 参数:@throws Exception
	 * 返回类型:double
	 */
	public double searchSellAmountByContractid(long noticeformid,long id) throws Exception
	{
		double returnamount=0.0;
		ResultSet rs=null;
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select t.contractdetailid,t.noticeformid,nvl(sum(t.amount),0) sellamount" +
					" from Sett_Transferloandetail t  " +
					" where t.noticeformid = " + noticeformid +
					" and t.contractdetailid="+ id +
					//" and t.id<>　" + id +
					" group by t.contractdetailid,t.noticeformid ");
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			rs=executeQuery();
			while(rs.next())
			{
				returnamount=rs.getDouble("sellamount");
			}
			
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			rs.close();
			rs=null;
			finalizeDAO();
		}
		return returnamount;
	}
}

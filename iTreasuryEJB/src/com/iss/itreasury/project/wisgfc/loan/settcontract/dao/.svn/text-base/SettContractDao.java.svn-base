package com.iss.itreasury.project.wisgfc.loan.settcontract.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.settcontract.dao.SettContractDAO;
import com.iss.itreasury.settlement.settcontract.dataentity.SettContractInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

public class SettContractDao extends ITreasuryDAO {
	public SettContractDao(){
		super("Loan_ContractForm");
	}
	public SettContractDao(Connection conn) {
		super("Loan_ContractForm",conn);
	}
	
	public void importContractFromExcel (String sContractCodes, SettContractInfo[] settContractInfos) throws ITreasuryDAOException, Exception{
		try {
			String strSQL = "delete from " + strTableName + " where SCONTRACTCODE in " + sContractCodes;
			initDAO();
			transConn.setAutoCommit(false);
			prepareStatement(strSQL);
			executeUpdate();
			setUseMaxID();
			for(int i = 0; i<settContractInfos.length;i++){
				SettContractInfo dataEntity = settContractInfos[i];
				dataEntity.setId(-1);
				StringBuffer buffer = new StringBuffer();
				buffer.append("INSERT INTO " + strTableName + " (\n");
				String[] buffers = getAllFieldNameBuffer(dataEntity,
						DAO_OPERATION_ADD);
				buffer.append(buffers[0]);
				buffer.append("\n) " + "VALUES (\n");
				buffer.append(buffers[1] + ") \n");
	
				strSQL = buffer.toString();
				System.out.println(strSQL);
				log.debug(strSQL);
				prepareStatement(strSQL);
	
				setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_ADD,
						buffers[0].toString());
	
				executeUpdate();
			}
			transConn.commit();
		} catch (ITreasuryDAOException ide) {
			transConn.rollback();
			throw ide;
		} finally {// added by mzh_fu 2008/03/26 将关闭连接放到 finally 中
			finalizeDAO();
		}
	}
	public Collection contractDao(String contractNos, long lofficeId,long inputUserId,
			long lcurrenyID) throws Exception {
		Vector v = new Vector();
		StringBuffer sbCount = new StringBuffer();
		StringBuffer sbSelect = new StringBuffer();
		StringBuffer sbSQL = new StringBuffer();
		StringBuffer sbOrder = new StringBuffer();
		String strSQL = "";
		long pageCount = -1;
		try
		{
			initDAO();
			//计算记录总数   
			sbCount.setLength(0);
			sbCount.append(" \n select count(*),sum(aa.mExamineAmount) ");
			sbSQL.setLength(0);
			sbSQL.append(" \n from Loan_ContractForm aa ");
			sbSQL.append(" \n where 1 = 1 ");
			sbSQL.append(" \n and aa.nInputUserID = " + inputUserId);
			sbSQL.append(" \n and aa.SCONTRACTCODE in  "+ contractNos);
			sbSQL.append(" \n and aa.NOFFICEID =  "+ lofficeId);
			sbSQL.append(" \n and aa.NCURRENCYID =  "+ lcurrenyID);
			sbSQL.append(" \n and aa.NSTATUSID =  "+ SETTConstant.SettContractStatus.NOTACTIVE);
			
			
			////////////////////////////排序处理/////////////////
			
			sbSelect.append(" \n select id from ( select aa.*,rownum r from ( select * ");
			sbOrder.append(" \n ) aa ) ");
			strSQL = sbSelect.toString() + sbSQL.toString() + sbOrder.toString();
			try
			{
				SettContractDAO dao = new SettContractDAO(this.strTableName);
				prepareStatement(strSQL);
				ResultSet rs1 = executeQuery();
				while (rs1 != null && rs1.next())
				{
					SettContractInfo info = new SettContractInfo();
					info.setId(rs1.getLong("ID"));
					if (info.getId() > 0)
					{
						info = (SettContractInfo) dao.findByID(info.getId(), info.getClass());
						//当前查找总数
						info.setPageCount(pageCount);
						//info.setPageNo(pageNo);
						//info.setRecordCount(lRecordCount);
						//info.setTotalAmount(totalAmount);
					}
					v.add(info);
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException("批量查询合同产生错误", e);
			}
			catch (SQLException e)
			{
				throw new SettlementDAOException("批量查询合同产生错误", e);
			}
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
		}
		return (v.size() > 0 ? v : null);
	}
}

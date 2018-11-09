package com.iss.itreasury.loan.loancontract_test.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.NoticeQueryInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.loancontract_test.dataentity.LoanContractFormInfo;
import com.iss.itreasury.loan.loancontract_test.dataentity.LoanContractQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class LoanContractDAO extends ITreasuryDAO{
	public LoanContractDAO()
	{
		super("LOAN_CONTRACTFORM_TEST");
	}
	public LoanContractDAO(Connection conn)
	{
		super("LOAN_CONTRACTFORM_TEST","SEQ_LOAN_CONTRACTFORM_TEST",conn);
	}
	public StringBuffer m_sbSelect   = null;
	public StringBuffer m_sbFrom     = null;
	public StringBuffer m_sbWhere    = null;
	public StringBuffer m_sbOrderBy  = null;
	
	/**
	 * 贷款合同查询
	 * @param NoticeQueryInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader findByConditions(LoanContractQueryInfo queryInfo) throws IException
	{
		getLoanContractSQL(queryInfo);
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader
		(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.loancontract_test.dataentity.LoanContractQueryInfo",
			null
		);
		pageLoader.setOrderBy(m_sbOrderBy.toString());
		
		return pageLoader;
	}
	public void getLoanContractSQL(LoanContractQueryInfo queryInfo)
	{
        //select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" contract.id ,contract.officeid,contract.currencyid,contract.contractcode,contract.contractamount, \n");
		m_sbSelect.append(" contract.contracttype,contract.clientname,contract.inputUserID,contract.inputdate,contract.statusid,USERINFO.Sname inputUserName \n");
		
        //from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" LOAN_CONTRACTFORM_TEST contract,userinfo USERINFO  \n");
		
        //where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1 = 1 ");
		m_sbWhere.append(" and contract.INPUTUSERID = USERINFO.ID \n");
		
		if(queryInfo.getId()>0)
		{
			m_sbWhere.append(" and contract.id = "+queryInfo.getId()+"\n");
		}
		
		if(queryInfo.getContractType()>0)
		{
			m_sbWhere.append(" and contract.contracttype = "+queryInfo.getContractType()+"\n");
		}
		
		if (!queryInfo.getStrStartContractCode().trim().equals(""))
		{ //贷款合同号开始
			m_sbWhere.append(" and( ");
			m_sbWhere.append(" contract.CONTRACTCODE >= '" + queryInfo.getStrStartContractCode() + "'");
			m_sbWhere.append(" ) ");
		}
		if (!queryInfo.getStrEndContractCode().trim().equals(""))
		{ //贷款合同号结束
			m_sbWhere.append(" and( ");
			m_sbWhere.append(" contract.CONTRACTCODE <= '" + queryInfo.getStrEndContractCode() + "'");
			m_sbWhere.append(" ) ");
		}
		if(queryInfo.getStartAmount()>0.00)
		{
			m_sbWhere.append(" and contract.contractamount >= "+queryInfo.getStartAmount()+"\n");
		}
		if(queryInfo.getEndAmount()>0.00)
		{
			m_sbWhere.append(" and contract.contractamount <= "+queryInfo.getEndAmount()+"\n");
		}
		if(queryInfo.getInputUserID()>0)
		{
			m_sbWhere.append(" and contract.INPUTUSERID = "+queryInfo.getInputUserID()+"\n");
		}
		if(queryInfo.getStatusID()>0)
		{
			m_sbWhere.append(" and contract.statusid = "+queryInfo.getStatusID()+"\n");
		}
		
        //Order By 
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by contract.contractcode desc ");
	}
	 public void deleteByIds(String[] ck) throws ITreasuryDAOException {
		
		 long[] ids = new long[ck.length];
		 for (int i = 0; i < ck.length; i++) 
		 {
			ids[i] = Long.valueOf(ck[i]).longValue();
		 }
		delete(ids);

	  }
	 /**
		 * 数据库更新操作操作
		 * 
		 * @param id
		 * @param statusID
		 *            需要更新到的状态
		 * @return
		 * @throws ITreasuryDAOException
		 */
		public void delete(long[] ids) throws ITreasuryDAOException {
			try {
				initDAO();
				StringBuffer buffer = new StringBuffer();
				buffer.append(" update \n");
				buffer.append(strTableName);
				buffer.append(" \n set statusID ="+LOANConstant.LoanStatus.CANCEL);
				buffer.append("\n  WHERE ID in (");
				for (int i = 0; i < ids.length; i++) {
					buffer.append("" + ids[i] + ",");
				}
				String strSQL = buffer.toString();
				strSQL = strSQL.substring(0, strSQL.length() - 1);
				strSQL += ")";
				log.debug(strSQL);
				prepareStatement(strSQL);
				executeQuery();
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new ITreasuryDAOException("状态更新异常", e);
			}

		}
		/**
		 * 方法说明：根据合同ID，得到合同详细信息
		 * @param INFO
		 * @return LoanContractFormInfo
		 * @throws IException
		 */
		public LoanContractFormInfo findByID(LoanContractFormInfo conInfo) throws Exception
		{
			LoanContractFormInfo info = null;
			ResultSet rs = null;
			try
			{
				initDAO();
				String sql = "select * from " + this.strTableName + " where id=" +conInfo.getId();
				prepareStatement(sql);;
				rs = executeQuery();
				info = this.transferResultsetIntoInfo(rs);
			}
			finally {
				
				finalizeDAO();
			}

			return info;
		}
		private LoanContractFormInfo transferResultsetIntoInfo(ResultSet rs) throws Exception
		{
			LoanContractFormInfo info = null;
			while (rs.next())
			{
				info = new LoanContractFormInfo();
				info.setId(rs.getLong("ID"));
				info.setOfficeID(rs.getLong("OfficeID"));
				info.setCurrencyID(rs.getLong("CurrencyID"));
				info.setContractCode(rs.getString("ContractCode"));
				info.setContractType(rs.getLong("ContractType"));
				info.setClientName(rs.getString("ClientName"));				
				info.setContractAmount(rs.getDouble("ContractAmount"));
	            info.setInputdate(rs.getTimestamp("InputDate"));
	            info.setInputuserID(rs.getLong("InputuserID"));
		        info.setStatusID(rs.getLong("statusid"));
			}

			return info;
		}
}

package com.iss.itreasury.project.wisgfc.ebank.special.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo;
import com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class ConsignReceiveDao extends ITreasuryDAO{
	private static Log4j log4j = new Log4j(Constant.ModuleType.EBANK);
	private String strOrderBy = " order by dtinput desc ";
	
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;
	
	public ConsignReceiveDao() {
		super("OB_CONSIGNRECEIVE","SEQ_OB_CONSIGNRECEIVE");
	}	
	
	/**
	 * 根据条件查询特约收款信息
	 * @param info 查询条件
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findByCondition(ConsignReceiveInfo info) throws ITreasuryDAOException, SQLException {
		return super.findByCondition(info,strOrderBy);		
	}	
		
	/**
	 * 根据条件查询特约收款信息
	 * @param info 查询条件
	 * @param orderByString 排序
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findByCondition(ConsignReceiveInfo info, String status) throws ITreasuryDAOException, SQLException {		
		return findByCondition(info,status,strOrderBy);
	}	
				
	/**
	 * 根据条件查询特约收款信息
	 * @param info 查询条件
	 * @param status 状态 字符串 以,分隔
	 * @param orderByString 排序	 
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */	
	public Collection findByCondition(ConsignReceiveInfo info, String status, String orderByString) throws ITreasuryDAOException, SQLException {
		Collection res = null;
		String[] arrStatus = status.split(",");
		String strTemp = "";
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT * FROM \n");
			buffer.append(strTableName);
			buffer.append("\n WHERE ");
			String strs[] = this.getAllFieldNameBuffer(info,
					DAO_OPERATION_FIND);
			buffer.append(strs[0]);
			
			buffer.append(" and nstatus in (");
			
			for(int i=0; i<arrStatus.length ; i++){
				strTemp += "?,";
			}
			if (strTemp.length() > 0) {
				strTemp = strTemp.substring(0, strTemp.length()-1);
			}
			buffer.append(strTemp);
			buffer.append(")");
			
			String strSQL = buffer.toString();
			if (orderByString != null) {
				strSQL += " ";
				strSQL += orderByString;
			}
			log.debug(strSQL);
			
			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(info, DAO_OPERATION_FIND,
					strs[0]);
			
			int index = transPS.getParameterMetaData().getParameterCount();
			index = index-arrStatus.length+1;
			
			for(int i=0; i<arrStatus.length ; i++){
				transPS.setLong(index++,Long.parseLong(arrStatus[i]));
			}
			
			executeQuery();

			res = this.getDataEntitiesFromResultSet(info.getClass());

		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}

		return res;
	}	
	
	/**
	 * 新增一条内转业务
	 * @param info
	 * @throws Exception
	 */
	public long add(FinanceInfo info) throws Exception {
		long lMaxID = -1;		
			
		StringBuffer sb = new StringBuffer();			
		//调用BizCapital方法，得到最大流水号
		lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
		
		// insert into  拼写插入语句 
		sb.append(" INSERT INTO OB_FinanceInStr(  \n");
		sb.append("ID, nClientID, nCurrencyID,nTransType,");
		sb.append("nPayerAcctID,nRemitType,nPayeeAcctID, ");
		sb.append(" mAmount,nChildClientid, \n");	
		sb.append("dtExecute, sNote, nFixedDepositTime, nNoticeDay, ");
		sb.append("NCONTRACTID,  NLOANNOTEID, dtPay,");
		sb.append("sDepositNo,NSUBACCOUNTID, dtDepositStart, mDepositRate, mDepositAmount, mDepositBalance, \n");
		sb.append("nStatus, nConfirmUserID, dtConfirm, ");
		sb.append("CPF_nOfficeID, CPF_nDefaultTransType, \n");
		sb.append("DTCLEARINTEREST, nInterestPayeeAcctID, nInterestRemitType, ");
		sb.append("mInterest, mCompoundInterest, mOverdueInterest, mSuretyFee, mCommission, ");
		sb.append("mRealInterest, mRealCompoundInterest, mRealOverdueInterest, mRealSuretyFee, mRealCommission, \n");
		sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX,budgetItemID,ISFIXCONTINUE,FIXEDREMARK,MAMOUNTFORTRANS,sBatchNo,sApplyCode,lSource,isSameBank,isDiffLocal,dtmodify,isautocontinue,autocontinuetype,autocontinueaccountid) \n");
		sb.append("VALUES \n");
		sb.append("(?,?,?,?,?,?,?,?,?," );
		if (info.getTransType()== OBConstant.SettInstrType.APPLYCAPITAL)
		{
			sb.append("sysdate,");
		}else
		{
			sb.append("?," );
		}				
	
		sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)");
		log4j.info("add插入语句=" + sb.toString());			
		prepareStatement(sb.toString());
		int nIndex = 1;
		
		log4j.info("lMaxID=" + lMaxID);
		transPS.setLong(nIndex++, lMaxID);
		transPS.setLong(nIndex++, info.getClientID()); // 网上银行客户ID
		transPS.setLong(nIndex++, info.getCurrencyID()); // 币种
		transPS.setLong(nIndex++, info.getTransType()); // 网上银行交易类型
		transPS.setLong(nIndex++, info.getPayerAcctID()); // 付款方账户ID
		transPS.setLong(nIndex++, info.getRemitType()); // 汇款方式
		transPS.setLong(nIndex++, info.getPayeeAcctID()); // 收款方账户ID
		transPS.setDouble(nIndex++, info.getAmount()); // 交易金额
		transPS.setLong(nIndex++, info.getChildClientID()); // 下属单位
		
		if (info.getTransType()!= OBConstant.SettInstrType.APPLYCAPITAL)
		{
			transPS.setTimestamp(nIndex++, info.getExecuteDate()); // 执行日
		}
		
		transPS.setString(nIndex++, info.getNote()); // 汇款用途/摘要
		transPS.setLong(nIndex++, info.getFixedDepositTime()); // 定期存款期限（月）
		transPS.setLong(nIndex++, info.getNoticeDay()); // 通知存款品种（天）
		transPS.setLong(nIndex++, info.getContractID()); // 贷款合同ID
		transPS.setLong(nIndex++, info.getLoanNoteID()); //方款通知单号
		transPS.setTimestamp(nIndex++, info.getPayDate()); // 贷款放款日期
		transPS.setString(nIndex++, info.getDepositNo()); //定期（通知）存款单据号
		transPS.setLong(nIndex++, info.getSubAccountID()); //子账户ID
		transPS.setTimestamp(nIndex++, info.getDepositStart()); //定期（通知）存款起始日
		transPS.setDouble(nIndex++, info.getDepositRate()); //定期存单利率
		transPS.setDouble(nIndex++, info.getDepositAmount()); //存单金额（开立金额）
		transPS.setDouble(nIndex++, info.getDepositBalance()); //存单余额
	
		transPS.setLong(nIndex++, OBConstant.SettInstrStatus.SAVE); // 状态为未复核
		transPS.setLong(nIndex++, info.getConfirmUserID()); // 录入人			
		transPS.setLong(nIndex++, info.getOfficeID()); // CPF-默认办事处	
		transPS.setLong(nIndex++, OBConstant.getDefaultTransactionType(info.getTransType())); // CPF-处理业务类型
	
		transPS.setTimestamp(nIndex++, info.getClearInterest()); //结息日期
		transPS.setLong(nIndex++, info.getInterestPayeeAcctID()); //利息收款方账户ID
		transPS.setLong(nIndex++, info.getInterestRemitType()); //利息汇款方式
		transPS.setDouble(nIndex++, info.getInterest()); //应付贷款利息
		transPS.setDouble(nIndex++, info.getCompoundInterest()); //应付复利
		transPS.setDouble(nIndex++, info.getOverdueInterest()); // 应付逾期罚息
		transPS.setDouble(nIndex++, info.getSuretyFee()); // 应付担保费
		transPS.setDouble(nIndex++, info.getCommission()); // 应付手续费
		transPS.setDouble(nIndex++, info.getRealInterest()); // 实付贷款利息
		transPS.setDouble(nIndex++, info.getRealCompoundInterest()); // 实付复利
		transPS.setDouble(nIndex++, info.getRealOverdueInterest()); // 实付逾期罚息
		transPS.setDouble(nIndex++, info.getRealSuretyFee()); // 实付担保费
		transPS.setDouble(nIndex++, info.getRealCommission()); // 实付手续费
	
		transPS.setTimestamp(nIndex++, info.getInterestStart()); //利息起息日
		transPS.setTimestamp(nIndex++, info.getCompoundStart()); //复利起息日
		transPS.setDouble(nIndex++, info.getCompoundRate()); //复利利率
		transPS.setTimestamp(nIndex++, info.getOverDueStart()); // 罚息起息日
		transPS.setDouble(nIndex++, info.getOverDueRate()); //罚息利率
		transPS.setTimestamp(nIndex++, info.getSuretyStart()); // 担保费起息日
		transPS.setDouble(nIndex++, info.getSuretyRate()); //担保费率
		transPS.setTimestamp(nIndex++, info.getCommissionStart()); // 手续费起息日
		transPS.setDouble(nIndex++, info.getCommissionRate()); //手续费率
		transPS.setDouble(nIndex++, info.getInterestRate()); //贷款利率
		transPS.setDouble(nIndex++, info.getCompoundAmount()); //复利本金
		transPS.setDouble(nIndex++, info.getOverDueAmount()); //逾期罚息本金
		
		transPS.setDouble(nIndex++, info.getInterestReceiveable()); //计提利息
		transPS.setDouble(nIndex++, info.getInterestIncome()); //本次利息
		transPS.setDouble(nIndex++, info.getRealInterestReceiveable()); //实付计提利息
		transPS.setDouble(nIndex++, info.getRealInterestIncome()); //实付本次利息
		transPS.setDouble(nIndex++, info.getInterestTax()); //利息税费
		transPS.setDouble(nIndex++, info.getRealInterestTax()); //实付利息税费
		
	
		transPS.setLong(nIndex++, info.getBudgetItemID());  //预算项目id
	
		transPS.setLong(nIndex++, info.getIsFixContinue()); //到期是否续存
		
		transPS.setString(nIndex++, info.getFixEdremark()); //备注（定期是否续存）
		
		transPS.setDouble(nIndex++, info.getMamOuntForTrans()); //定期子帐户留存金额
		transPS.setString(nIndex++, info.getSBatchNo());	//批次号
        transPS.setString(nIndex++, info.getApplyCode());//业务申请编号
        transPS.setLong(nIndex++, info.getSource()); //数据来源
        transPS.setLong(nIndex++, info.getIsSameBank()); //是否同行
        transPS.setLong(nIndex++, info.getIsDiffLocal()); //是否同城
        transPS.setLong(nIndex++, info.getIsAutoContinue());//是否自动续存
        transPS.setLong(nIndex++, info.getAutocontinuetype());//自动转续存类型（本金or本息）
        transPS.setLong(nIndex++, info.getAutocontinueaccountid());//利息转至活期账户号
		
		transPS.executeUpdate();	
		
		return lMaxID;
	}
	
	/**
	 * 手动更新
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void manualUpdate(ConsignReceiveInfo info) throws ITreasuryDAOException {		
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE " + strTableName + " SET \n");
	
		String[] buffers = getAllFieldNameBuffer(info,DAO_OPERATION_UPDATE);
		buffer.append(buffers[0]);
		buffer.append(" WHERE ID = " + info.getId());
	
		String strSQL = buffer.toString();
		log.debug(strSQL);
		prepareStatement(strSQL);
		setPrepareStatementByDataEntity(info, DAO_OPERATION_UPDATE,
				buffers[0].toString());	
		executeUpdate();
	}
	
	/**
	 * 自动更新
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void update(ConsignReceiveInfo info) throws ITreasuryDAOException {
		update(info,true);
	}
	
	public void update(ConsignReceiveInfo info, boolean autoTransaction) throws ITreasuryDAOException {
		if (autoTransaction) {
			super.update(info);			
		} else {
			manualUpdate(info);
		}
	}
	
	/**
	 * 委托收款确认 新增一条内转业务，更新委托收款相关信息
	 * @param fInfo   内转业务对象
	 * @param cInfo   委托收款业务对象
	 * @throws Exception
	 */
	public long createFinaneInfo(FinanceInfo fInfo,ConsignReceiveInfo cInfo) throws Exception {
		long instrID = -1;
		try {
			initDAO();
			transConn.setAutoCommit(false);
			instrID = add(fInfo);    //新增内转业务
			cInfo.setNInstrID(instrID);
			update(cInfo,false);     //更新委托收款
			transConn.commit();		
		} catch (Exception e) {
			transConn.rollback();
			log.error(e.getMessage());
			throw e;
		} finally {
			finalizeDAO();
		}
		return instrID;
	}
	
//	翻页查询
	public PageLoader queryConsignReceiveInfo(ConsignReceiveInfo info) throws Exception	{
		getConsignReceiveSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.ebank.special.dataentity.ConsignReceiveInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL  
	 * @param info
	 *
	 */
	public void getConsignReceiveSQL(ConsignReceiveInfo info) throws Exception {			
		//select 	
		m_sbSelect = new StringBuffer();		
		m_sbSelect.append(" c.* ");
		
		//from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ob_consignreceive c,client_clientinfo r \n");
		
		//where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" c.npayeeclientid = r.id ");
		m_sbWhere.append(" and c.nstatus > 0 \n");
		m_sbWhere.append(" and c.nofficeid = " + info.getNOfficeID() + " \n");
		m_sbWhere.append(" and c.ncurrencyid = " + info.getNCurrencyID() + " \n");	
		
		//状态
		if (info.getNStatus() > 0) {
			m_sbWhere.append(" and c.nstatus = " + info.getNStatus() + " \n");		
		}
		//付款方
		if (info.getNPayerClientID() > 0) {
			m_sbWhere.append(" and c.npayerclientid = " + info.getNPayerClientID() + " \n");		
		}
		//收款方
		if (info.getNPayeeClientID() > 0) {
			m_sbWhere.append(" and c.npayeeclientid = " + info.getNPayeeClientID() + " \n");		
		}
		//状态字符串
		if (info.getStrStatus() != null && info.getStrStatus().length() > 0) {
			m_sbWhere.append(" and c.nstatus in (" + info.getStrStatus() + ") \n");	
		}
		//查询条件-提交日期 由
		if (info.getQ_inputStart() != null && info.getQ_inputStart().length() > 0) {
			m_sbWhere.append(" and c.dtinput >= to_date('" + info.getQ_inputStart() +"','yyyy-mm-dd') \n");	
		}
		//查询条件-提交日期 至
		if (info.getQ_inputEnd() != null && info.getQ_inputEnd().length() > 0) {
			m_sbWhere.append(" and c.dtinput <= to_date('" + info.getQ_inputEnd() +"','yyyy-mm-dd') \n");	
		}
		//查询条件-收款方 由
		if (info.getQ_payeeClientCodeStart() != null && info.getQ_payeeClientCodeStart().length() > 0) {
			m_sbWhere.append(" and r.code >= '" + info.getQ_payeeClientCodeStart() + "' \n");		
		}
		//查询条件-收款方 至
		if (info.getQ_payeeClientCodeEnd() != null && info.getQ_payeeClientCodeEnd().length() > 0) {
			m_sbWhere.append(" and r.code <= '" + info.getQ_payeeClientCodeEnd() + "' \n");		
		}
		
		//order by 
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by c.dtinput desc");		
	}
	
}

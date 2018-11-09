package com.iss.itreasury.credit.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.credit.query.AmountFromQueryBiz;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.ApplyUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.ContractUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.LoanUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.QueryLoanUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.RepayUsedAmountInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;

public class AmountFormDao extends ITreasuryDAO {

	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	public AmountFormDao() {
		super("CREDIT_AMOUNTFORM");
	}

	public AmountFormDao(Connection conn) {
		super("CREDIT_AMOUNTFORM", conn);
	}
	
	/**
	 * 基本数据库操作（修改）
	 * @param asInfo
	 * @throws ITreasuryDAOException
	 */
	public void updateAmountForm(AmountFormInfo afInfo)
		throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" update " + this.strTableName + " t");
	        	strSQL.append(" set t.CREDITAMOUNT = ?,");
	        	strSQL.append(" t.CONTROLTYPE = ?,");
	        	strSQL.append(" t.ENDDATE = ?");
	        	strSQL.append(" where t.ID = ?");
	        	
	        	prepareStatement(strSQL.toString());
				log4j.info("AmountFormDao.updateAmountForm()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
	        	
	        	int nIndex = 1;
	        	transPS.setDouble(nIndex++, afInfo.getCreditAmount());
	        	transPS.setLong(nIndex++, afInfo.getControlType());
	        	transPS.setTimestamp(nIndex++, afInfo.getEndDate());
	        	transPS.setLong(nIndex++, afInfo.getId());
	        	executeUpdate();
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库操作异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
		}
		finally {
			finalizeDAO();
		}
	}
	
	/**
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo findAmountFormViewInfo(AmountFormInfo queryInfo)
		throws ITreasuryDAOException
	{
		AmountFormViewInfo afvInfo = null;
		StringBuffer strSQL = null;
		try {
			initDAO();
        	strSQL = new StringBuffer();
        	strSQL.append(" select t1.* from " + this.strTableName + " t1");
        	strSQL.append(" where t1.officeId = ?");
        	strSQL.append(" and t1.currencyId = ?");
        	strSQL.append(" and t1.CLIENTID = ?");
        	strSQL.append(" and t1.state = " + LOANConstant.CreditFlowStatus.SAVE);
        	strSQL.append(" and t1.STARTDATE <= ?");
        	strSQL.append(" and t1.ENDDATE >= ?");

	        prepareStatement(strSQL.toString());
			log4j.info("AmountFormDao.findAmountFormViewInfo()\n");
			log4j.info("strSQL=\n" + strSQL.toString());
	        
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
    		transPS.setLong(nIndex++, queryInfo.getClientId());
    		transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
    		transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
			
        	executeQuery();
        	afvInfo = (AmountFormViewInfo)getDataEntityFromResultSet(AmountFormViewInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return afvInfo;
	}
	
	/**
	 * 获取一个AmountFormViewInfo实体
	 * @param afInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public AmountFormViewInfo getAmountFormViewInfo(AmountFormInfo afInfo)
		throws ITreasuryDAOException
	{
		AmountFormViewInfo afvInfo = null;
		StringBuffer strSQL = null;
		try {
			  	initDAO();
			  	
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.*, t2.creditcode groupCreditCode, t2.startdate groupStartDate, t2.enddate groupEndDate");
	        	strSQL.append(" from " + this.strTableName + " t1,");
	        	strSQL.append(" (select t.* from CREDIT_AMOUNTFORM t where t.state = ? and t.officeId = ? and t.currencyId = ?) t2");
	        	strSQL.append(" where t1.groupcreditid = t2.id(+)");
	        	strSQL.append(" and t1.officeId = ?");
	        	strSQL.append(" and t1.currencyId = ?");
	        	strSQL.append(" and t1.state = ?");
	        	strSQL.append(" and t1.id = ?");
	        	

		        prepareStatement(strSQL.toString());
				log4j.info("AmountFormDao.getAmountFormViewInfo()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
		        
	        	int nIndex = 1;
        		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);	        	
        		transPS.setLong(nIndex++, afInfo.getOfficeId());
        		transPS.setLong(nIndex++, afInfo.getCurrencyId());
        		transPS.setLong(nIndex++, afInfo.getOfficeId());
        		transPS.setLong(nIndex++, afInfo.getCurrencyId());
        		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, afInfo.getId());
				
	        	executeQuery();
	        	afvInfo = (AmountFormViewInfo)getDataEntityFromResultSet(AmountFormViewInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return afvInfo;
	}
	
	
	/**
	 * 查询集团授信成员的授信金额已分配额度
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLeaguerCreditAmount(AmountFormViewInfo queryInfo)
		throws ITreasuryDAOException
	{
		double dCreditAmount = 0.0;
		StringBuffer strSQL = null;
		long lCreditType = 0;
		
		try {
			if(queryInfo.getSubAmountFormInfo() != null){
				lCreditType = queryInfo.getSubAmountFormInfo().getCreditType();
			}
			
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	if(lCreditType == 0)
	        	{
		        	strSQL.append(" select nvl(sum(t1.creditamount), 0) creditamount from " + this.strTableName + " t1");
		        	strSQL.append(" where t1.state = ?");
		        	strSQL.append(" and t1.officeid = ?");
		        	strSQL.append(" and t1.currencyId = ?");
		        	strSQL.append(" and t1.groupCreditId = ?");
	        	}
	        	else 
	        	{
		        	strSQL.append(" select nvl(sum(t2.creditamount), 0) creditamount from " + this.strTableName + " t1, credit_subamountform t2");
		        	strSQL.append(" where t1.state = ?");
		        	strSQL.append(" and t1.officeid = ?");
		        	strSQL.append(" and t1.currencyId = ?");
		        	strSQL.append(" and t1.groupCreditId = ?");
		        	strSQL.append(" and t1.id = t2.amountformid");
		        	strSQL.append(" and t2.state = ?");
		        	strSQL.append(" and t2.credittype = ?");
	        	}

	        	prepareStatement(strSQL.toString());
				log4j.info("AmountFormDao.getGroupLeaguerCreditAmount()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, queryInfo.getOfficeId());
	        	transPS.setLong(nIndex++, queryInfo.getCurrencyId());
	        	transPS.setLong(nIndex++, queryInfo.getId());
	        	if(lCreditType > 0)
	        	{
	        		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);	        		
	        		transPS.setLong(nIndex++, lCreditType);
	        	}
	        	
	        	executeQuery();
	        	while(transRS.next()){
	        		dCreditAmount = transRS.getDouble("creditamount");
	        	}
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		return dCreditAmount;
	}
	
	/**
	 * 计算某客户在贷款申请时占用的金额
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getLoanApplyUsedAmount(QueryLoanUsedAmountInfo queryInfo)
		throws ITreasuryDAOException{

		double applyAmount = 0;
		StringBuffer strSQL = null;
	
	    try {
			initDAO();
			
	    	strSQL = new StringBuffer();
	    	
        	strSQL.append(" select nvl(sum(a.MEXAMINEAMOUNT), 0) mApplyAmount");
        	strSQL.append(" from loan_loanForm a ");
        	strSQL.append(" where a.nBorrowClientID = " + queryInfo.getClientId());
        	strSQL.append(" and a.nstatusID = " + LOANConstant.LoanStatus.APPROVALING);
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and to_char(a.dtstartdate,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
    		strSQL.append(" and a.ntypeid = " + queryInfo.getLoanType());
    		
		    prepareStatement(strSQL.toString());
			
		    executeQuery();
		    if (this.transRS.next()) 
		    {
	    	  applyAmount = this.transRS.getDouble("mApplyAmount");
			}
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return applyAmount;
	}
	
	/**
	 * 客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getLoanUsedAmount(QueryLoanUsedAmountInfo queryInfo)
		throws ITreasuryDAOException{

		double usedAmount = 0;
		StringBuffer strSQL = null;
	
	    try {
			initDAO();
			
	    	strSQL = new StringBuffer();
    		
    		strSQL.append(" select nvl(sum(nvl(s.mamount,0)),0) usedAmount");
    	    strSQL.append(" from loan_contractform t,loan_payform s");
    	    strSQL.append(" where t.id = s.ncontractid");
    	    strSQL.append(" and s.nstatusid in ("+ LOANConstant.LoanPayNoticeStatus.CHECK +","+ LOANConstant.LoanPayNoticeStatus.USED +","+ LOANConstant.LoanPayNoticeStatus.APPROVALING +")");
    	    strSQL.append(" and t.nborrowclientid = " + queryInfo.getClientId()); 
    	    strSQL.append(" and t.nofficeid = " + queryInfo.getOfficeId()); 
    	    strSQL.append(" and t.ncurrencyid = " + queryInfo.getCurrencyId()); 
    	    strSQL.append(" and to_char(s.dtstart,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
    	    strSQL.append(" and to_char(s.dtstart,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
    	    strSQL.append(" and t.ntypeid = " + queryInfo.getLoanType());
    	    strSQL.append(" and t.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	 	
		    prepareStatement(strSQL.toString());
			
		    executeQuery();
		    if (this.transRS.next()) 
		    {
		    	usedAmount = this.transRS.getDouble("usedAmount");
			}
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return usedAmount;
	}
	
	/**
	 * 根据客户信息查询贷款合同金额
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getLoanContractUsedAmount(QueryLoanUsedAmountInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
	    double contractAmount = 0;
		StringBuffer strSQL = null;

	    try {
	    	initDAO();
	    	
	    	strSQL = new StringBuffer();

        	strSQL.append(" select nvl(sum(a.MEXAMINEAMOUNT), 0) mContractAmount");
        	strSQL.append(" from loan_contractForm a ");
        	strSQL.append(" where a.nBorrowClientID = " + queryInfo.getClientId());
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.SAVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.SUBMIT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.CHECK + ", ");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.APPROVALING + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");        	
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");        	
    		strSQL.append(" and a.ntypeid = " + queryInfo.getLoanType());
				
		    prepareStatement(strSQL.toString());
			    
		    executeQuery();
		    if (this.transRS.next()) {
		    	contractAmount = this.transRS.getDouble("mContractAmount");
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return contractAmount;
	}
	
	
	/**
	 * 计算某客户在自营贷款非循环贷款时还款单金额
	 * @param afInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getCircleLoanAmount(QueryLoanUsedAmountInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
	    double circleAmount = 0;
		StringBuffer strSQL = null;
		if(queryInfo.getLoanType() != LOANConstant.LoanType.ZY)
		{
			return 0.0;
		}

	    try {
	    	initDAO();
	    	strSQL = new StringBuffer();
	          
	    	strSQL.append(" select nvl(sum(c.mAmount), 0) mCircleAmount");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c");
        	strSQL.append(" where a.nBorrowClientID = " + queryInfo.getClientId());
        	strSQL.append(" and a.niscircle = 2");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and a.nTypeID = " + LOANConstant.LoanType.ZY);
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and c.nstatusid = " + SETTConstant.TransactionStatus.CHECK);

			log4j.info("AmountFormDao.getCircleLoanAmount()\n");
			log4j.info("查询循环贷款金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
			executeQuery();
			
		    if (this.transRS.next()) {
		    	circleAmount = this.transRS.getDouble("mCircleAmount");
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return circleAmount;
	}
	
	/**
	 * 还款单金额
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getLoanBackAmount(QueryLoanUsedAmountInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
	    double backAmount = 0;
		StringBuffer strSQL = null;
		if(queryInfo.getLoanType() != LOANConstant.LoanType.ZY)
		{
			return 0.0;
		}

	    try {
	    	initDAO();
	    	strSQL = new StringBuffer();
	          
	    	strSQL.append(" select nvl(sum(c.mAmount), 0) backAmount");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c");
        	strSQL.append(" where a.nBorrowClientID = " + queryInfo.getClientId());
//        	strSQL.append(" and a.niscircle = 2");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and a.nTypeID = " + LOANConstant.LoanType.ZY);
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and c.nstatusid = " + SETTConstant.TransactionStatus.CHECK);

			log4j.info("AmountFormDao.getLoanBackAmount()\n");
			log4j.info("查询还款单金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
			executeQuery();
			
		    if (this.transRS.next()) {
		    	backAmount = this.transRS.getDouble("backAmount");
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return backAmount;
	}
	
	/**
	 * 统计某客户贷款已占用金额（方法分支）
	 * 授信校验方式（自营贷款），1.放款通知单申请时校验,2.贷款申请时校验
	 * @param queryInfo 查询条件
	 * @return
	 * @throws IException
	 */
	public double getUsedLoanAmount(QueryLoanUsedAmountInfo queryInfo)
		throws IException {


	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			if( (Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1) && (queryInfo.getLoanType()==LOANConstant.CreditType.ZY) )
			{
				countAmount = getUsedLoanAmount1(queryInfo);
			}else{
				countAmount = getUsedLoanAmount2(queryInfo);
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计已占用授信金额时出错（方法分支），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}	
	
	/**
	 * 统计某客户贷款已占用金额（1.放款通知单申请时校验）
	 * 已占用金额 = 放款通知单金额(审批中、已审批、已使用) - 还款单金额
	 * @param queryInfo 查询条件
	 * @return
	 * @throws IException
	 */
	public double getUsedLoanAmount1(QueryLoanUsedAmountInfo queryInfo)
		throws IException {

	  	//客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
	  	double usedAmount = 0.0;

	  	//还款单金额
	  	double backAmount = 0.0;
	  	//不共享的授信金额
	  	double creditShareAmount = 0.0;
	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			//客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
			usedAmount = getLoanUsedAmount(queryInfo);

			//还款单金额
			backAmount = getLoanBackAmount(queryInfo);
  
			  
			//总计金额
			countAmount = UtilOperation.Arith.sub(usedAmount, backAmount);
			countAmount = UtilOperation.Arith.add(countAmount, creditShareAmount);
  
			log4j.debug("客户："+ queryInfo.getClientId() +", 客户已占用的金额（放款通知单金额(审批中、已审批、已使用)） usedAmount：" + usedAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 还款单金额 backAmount：" + backAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 贷款占用总金额 countAmount：" + countAmount);

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计已占用授信金额时出错（1.放款通知单申请时校验），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}
	
	/**
	 * 统计某客户贷款已占用金额（2.贷款申请时校验）
	 * 已占用金额 = 申请金额 + 合同金额 - 非循环贷款时还款单金额
	 * @param queryInfo 查询条件
	 * @return
	 * @throws IException
	 */
	public double getUsedLoanAmount2(QueryLoanUsedAmountInfo queryInfo)
		throws IException {

	  	//客户已占用的申请金额
	  	double applyAmount = 0.0;
	  	//客户已占用的合同金额
	  	double contractAmount = 0.0;
	  	//非循环贷款时还款单金额
	  	double circleAmount = 0.0;
	  	//不共享的授信金额
	  	double creditShareAmount = 0.0;
	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			//根据客户信息查询贷款申请金额
			applyAmount = getLoanApplyUsedAmount(queryInfo);

			//根据客户信息查询贷款合同金额
			contractAmount = getLoanContractUsedAmount(queryInfo);

			//自营贷款循环贷款的还款单金额
			if(queryInfo.getLoanType()==LOANConstant.CreditType.ZY) 
				circleAmount = getCircleLoanAmount(queryInfo);
			  
			//总计金额
			countAmount = UtilOperation.Arith.add(applyAmount, contractAmount);
			countAmount = UtilOperation.Arith.sub(countAmount, circleAmount);
			countAmount = UtilOperation.Arith.add(countAmount, creditShareAmount);
  
			log4j.debug("客户："+ queryInfo.getClientId() +", 占用的申请金额 applyAmount：" + applyAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 占用的合同金额 contractAmount：" + contractAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 非循环贷款时还款单金额 circleAmount：" + circleAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 贷款占用总金额 countAmount：" + countAmount);

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计已占用授信金额时出错（2.贷款申请时校验），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}
	
	/**
	 * 计算集团所有客户在贷款申请时占用的金额
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLoanApplyUsedAmount(QueryLoanUsedAmountInfo queryInfo)
		throws ITreasuryDAOException{

		double applyAmount = 0;
	
	    try {
			initDAO();
			
			StringBuffer strSQL = new StringBuffer();
	    	
        	strSQL.append(" select nvl(sum(a.MEXAMINEAMOUNT), 0) mApplyAmount");
        	strSQL.append(" from loan_loanForm a, ");        	
        	strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");
        	strSQL.append(" where a.nBorrowClientID = client.clientid");
        	strSQL.append(" and a.nstatusID = " + LOANConstant.LoanStatus.APPROVALING);
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
    		strSQL.append(" and a.ntypeid = " + queryInfo.getLoanType());
    		
		    prepareStatement(strSQL.toString());
			
		    executeQuery();
		    if (this.transRS.next()) 
		    {
	    	  applyAmount = this.transRS.getDouble("mApplyAmount");
			}
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return applyAmount;
	}
	
	/**
	 * 计算集团所有客户 客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLoanUsedAmount(QueryLoanUsedAmountInfo queryInfo)
		throws ITreasuryDAOException{

		double usedAmount = 0;
	
	    try {
			initDAO();
			
			StringBuffer strSQL = new StringBuffer();
	    	
    		strSQL.append(" select nvl(sum(nvl(s.mamount,0)),0) usedAmount");
    	    strSQL.append(" from loan_contractform t,loan_payform s,");
    	    strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");
    	    strSQL.append(" where t.id = s.ncontractid and t.nborrowclientid = client.clientid");
    	    strSQL.append(" and s.nstatusid in ("+ LOANConstant.LoanPayNoticeStatus.CHECK +","+ LOANConstant.LoanPayNoticeStatus.USED +","+ LOANConstant.LoanPayNoticeStatus.APPROVALING +")");
//    	    strSQL.append(" and t.nborrowclientid = " + queryInfo.getClientId()); 
    	    strSQL.append(" and t.nofficeid = " + queryInfo.getOfficeId()); 
    	    strSQL.append(" and t.ncurrencyid = " + queryInfo.getCurrencyId()); 
    	    strSQL.append(" and to_char(s.dtstart,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
    	    strSQL.append(" and to_char(s.dtstart,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
    	    strSQL.append(" and t.ntypeid = " + queryInfo.getLoanType());
    	    strSQL.append(" and t.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
    		
		    prepareStatement(strSQL.toString());
			
		    executeQuery();
		    if (this.transRS.next()) 
		    {
		    	usedAmount = this.transRS.getDouble("usedAmount");
			}
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return usedAmount;
	}
	
	/**
	 * 计算集团所有客户的已占用授信金额
	 * 
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLoanContractUsedAmount(QueryLoanUsedAmountInfo queryInfo) throws ITreasuryDAOException {
	    
	    double contractAmount = 0;
		StringBuffer strSQL = null;

	    try {
	    	initDAO();
	    	
	    	strSQL = new StringBuffer();

        	strSQL.append(" select nvl(sum(a.MEXAMINEAMOUNT), 0) mContractAmount");
        	strSQL.append(" from loan_contractForm a, ");
        	strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");
        	strSQL.append(" where a.nBorrowClientID = client.clientid");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.SAVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.SUBMIT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.CHECK + ", ");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.APPROVALING + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and to_char(a.dtstartdate,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");        	
        	strSQL.append(" and to_char(a.dtstartdate,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");        	
    		strSQL.append(" and a.ntypeid = " + queryInfo.getLoanType());
				
    		prepareStatement(strSQL.toString());
		    executeQuery();
		    if (this.transRS.next()) {
		    	contractAmount = this.transRS.getDouble("mContractAmount");
		    }
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("查询合同已占用金额时出错，" + e.getMessage(), e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return contractAmount;
	}
	
	
	/**
	 * 自营贷款非循环贷款时还款单金额
	 * @param afInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLoanCircleAmount(QueryLoanUsedAmountInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
	    double circleAmount = 0;
		StringBuffer strSQL = null;

	    try {
	    	initDAO();
	    	strSQL = new StringBuffer();
	          
	    	strSQL.append(" select nvl(sum(c.mAmount), 0) mCircleAmount");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c, ");
        	strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");        	
        	strSQL.append(" where a.nBorrowClientID = client.clientid");
        	strSQL.append(" and a.niscircle = 2");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and a.nTypeID = " + LOANConstant.LoanType.ZY);
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd')  >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd')  <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd')  >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd')  <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and c.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
		
        	prepareStatement(strSQL.toString());
			executeQuery();
		    if (this.transRS.next()) {
		    	circleAmount = this.transRS.getDouble("mCircleAmount");
		    }
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("统计集团客户的循环贷款的还单金额时出错，" + e.getMessage(),e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return circleAmount;
	}		
	
	/**
	 * 集团  还款单金额
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public double getGroupLoanBackAmount(QueryLoanUsedAmountInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
	    double backAmount = 0;
		StringBuffer strSQL = null;

	    try {
	    	initDAO();
	    	strSQL = new StringBuffer();
	          
	    	strSQL.append(" select nvl(sum(c.mAmount), 0) backAmount");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c, ");
        	strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");        	
        	strSQL.append(" where a.nBorrowClientID = client.clientid");
//        	strSQL.append(" and a.niscircle = 2");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = " + queryInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = " + queryInfo.getCurrencyId());
        	strSQL.append(" and a.nTypeID = " + LOANConstant.LoanType.ZY);
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd')  >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(a.dtstartdate, 'yyyy-mm-dd')  <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd')  >= '" + DataFormat.formatDate(queryInfo.getCheckDateStart()) + "' ");
        	strSQL.append(" and to_char(c.dtintereststart, 'yyyy-mm-dd')  <= '" + DataFormat.formatDate(queryInfo.getCheckDate()) + "' ");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and c.nstatusid = " + SETTConstant.TransactionStatus.CHECK);
		
        	prepareStatement(strSQL.toString());
			executeQuery();
		    if (this.transRS.next()) {
		    	backAmount = this.transRS.getDouble("backAmount");
		    }
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("统计集团客户的还单金额时出错，" + e.getMessage(),e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    
	    return backAmount;
	}
	
	/**
	 * 统计集团已被占用的授信额度（方法分支）
	 * 授信校验方式（自营贷款），1.放款通知单申请时校验,2.贷款申请时校验
	 * @param queryInfo 贷款的已生效的授信信息
	 * @return
	 * @throws IException
	 */
	public double getUsedGroupCreditAmount(QueryLoanUsedAmountInfo queryInfo)
		throws IException {


	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			if( (Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1) && (queryInfo.getLoanType()==LOANConstant.CreditType.ZY) )
			{
				countAmount = getUsedGroupCreditAmount1(queryInfo);
			}else{
				countAmount = getUsedGroupCreditAmount2(queryInfo);
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计集团已占用授信金额时出错（方法分支），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}	
	
	
	/**
	 * 统计集团已被占用的授信额度（1.放款通知单申请时校验）
	 * 已占用金额 = 放款通知单金额(审批中、已审批、已使用) - 还款单金额
	 * @param queryInfo 贷款的已生效的授信信息
	 * @return
	 * @throws IException
	 */
	public double getUsedGroupCreditAmount1(QueryLoanUsedAmountInfo queryInfo)
		throws IException {

	  	//客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
	  	double usedAmount = 0.0;

	  	//还款单金额
	  	double backAmount = 0.0;
	  	//不共享的授信金额
	  	double creditShareAmount = 0.0;
	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			//客户已占用的金额（放款通知单金额(审批中、已审批、已使用)）
			usedAmount = getGroupLoanUsedAmount(queryInfo);

			//还款单金额
			backAmount = getGroupLoanBackAmount(queryInfo);

			  
			//总计金额
			countAmount = UtilOperation.Arith.sub(usedAmount, backAmount);
			countAmount = UtilOperation.Arith.add(countAmount, creditShareAmount);
  
			log4j.debug("客户："+ queryInfo.getClientId() +", 客户已占用的金额（放款通知单金额(审批中、已审批、已使用)） usedAmount：" + usedAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 还款单金额 backAmount：" + backAmount);
			log4j.debug("客户："+ queryInfo.getClientId() +", 贷款占用总金额 countAmount：" + countAmount);

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计集团已占用授信金额时出错（1.放款通知单申请时校验），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}	
	
	
	/**
	 * 统计集团已被占用的授信额度（2.贷款申请时校验）
	 * 已占用金额 = 申请金额 + 合同金额 - 非循环贷款时还款单金额
	 * @param queryInfo 贷款的已生效的授信信息
	 * @return
	 * @throws IException
	 */
	public double getUsedGroupCreditAmount2(QueryLoanUsedAmountInfo queryInfo)
		throws IException {

	  	//客户已占用的申请金额
	  	double applyAmount = 0.0;
	  	//客户已占用的合同金额
	  	double contractAmount = 0.0;
	  	//非循环贷款时还款单金额
	  	double circleAmount = 0.0;
	  	//不共享的授信金额
	  	double creditShareAmount = 0.0;
	  	//客户贷款占用总金额
	  	double countAmount = 0.0;
	  			
		try{
			//根据客户信息查询贷款申请金额
			applyAmount = getGroupLoanApplyUsedAmount(queryInfo);

			//根据客户信息查询贷款合同金额
			contractAmount = getGroupLoanContractUsedAmount(queryInfo);
  
			//自营贷款循环贷款的还款单金额
			if(queryInfo.getLoanType()==LOANConstant.CreditType.ZY) 
				circleAmount = getGroupLoanCircleAmount(queryInfo);
			  
			//总计金额
			countAmount = UtilOperation.Arith.add(applyAmount, contractAmount);
			countAmount = UtilOperation.Arith.sub(countAmount, circleAmount);
			countAmount = UtilOperation.Arith.add(countAmount, creditShareAmount);
  
			log4j.debug("集团客户："+ queryInfo.getClientId() +", 占用的申请金额 applyAmount：" + applyAmount);
			log4j.debug("集团客户："+ queryInfo.getClientId() +", 占用的合同金额 contractAmount：" + contractAmount);
			log4j.debug("集团客户："+ queryInfo.getClientId() +", 非循环贷款时还款单金额 circleAmount：" + circleAmount);
			log4j.debug("集团客户："+ queryInfo.getClientId() +", 贷款占用总金额 countAmount：" + countAmount);

		}catch(Exception e){
			e.printStackTrace();
			throw new IException("统计集团已占用授信金额时出错（2.贷款申请时校验），" + e.getMessage() ,e);
		}
		
		return countAmount;
	}	
	
	
	/**
	 * 查询贷款申请占用的授信额度list
	 * @author 马现福
	 * @param queryInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getApplyUsedAmount(AmountFormViewInfo queryInfo)
  		throws ITreasuryDAOException{
    
		List applyInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	    	
	        initDAO();
	        
        	strSQL = new StringBuffer();
        	strSQL.append(" select c.sName,a.sapplycode, a.ntypeid nloantype, nvl(a.MEXAMINEAMOUNT, 0) mApplyAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_loanForm a, client c ");
        	strSQL.append(" where a.nBorrowClientID = ?");
        	strSQL.append(" and a.nstatusID = ?");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.nBorrowClientID = c.id");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(queryInfo.getOfficeId(), queryInfo.getCurrencyId()) + ")");
        	strSQL.append(" order by a.ntypeid, a.dtstartdate ");

			log4j.info("查询贷款申请占用金额, strSQL=\n" + strSQL.toString());

		    prepareStatement(strSQL.toString());
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getClientId());
    		transPS.setLong(nIndex++, LOANConstant.LoanStatus.APPROVALING);
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
    		
		    executeQuery();
		    while(transRS.next()) {
		    	//授信中申请单占有的额度
		    	ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
		    	applyUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	applyUsedAmountInfo.setApplyNo(transRS.getString("sapplycode"));
		    	applyUsedAmountInfo.setLoanType(transRS.getLong("nloantype"));
		    	applyUsedAmountInfo.setApplyAmount(transRS.getDouble("mApplyAmount"));
		    	applyUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	applyUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	applyUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	applyUsedAmountInfo.setApplyState(transRS.getLong("nstatusid"));
		    	
		    	applyInfoList.add(applyUsedAmountInfo);
		    }
	      
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return applyInfoList;
	}
	
	
	/**
	 * 查询贷款占用的授信额度list（1.放款通知单申请时校验）
	 * @author 马现福
	 * @param queryInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getLoanUsedAmount(AmountFormViewInfo queryInfo)
  		throws ITreasuryDAOException{
    
		List loanUsedAmountInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	    	
	        initDAO();
	        
        	strSQL = new StringBuffer();
        	
        	strSQL.append(" select r.sCode,r.sName,s.scontractcode,t.scode loanpfcode,t.mamount,t.dtstart,t.dtend,t.nstatusid ");
    	    strSQL.append(" from loan_payform t,loan_contractform s,client r");
    	    strSQL.append(" where s.id = t.ncontractid and s.nborrowclientid = r.id");
    	    strSQL.append(" and t.nstatusid in ("+ LOANConstant.LoanPayNoticeStatus.CHECK +","+ LOANConstant.LoanPayNoticeStatus.USED +","+ LOANConstant.LoanPayNoticeStatus.APPROVALING +")");
    	    strSQL.append(" and s.nborrowclientid = " + queryInfo.getClientId()); 
    	    strSQL.append(" and s.nofficeid = " + queryInfo.getOfficeId()); 
    	    strSQL.append(" and s.ncurrencyid = " + queryInfo.getCurrencyId()); 
    	    strSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getStartDate()) + "' ");
    	    strSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getEndDate()) + "' ");
    	    strSQL.append(" and s.ntypeid = " + LOANConstant.CreditType.ZY);
    	    strSQL.append(" and s.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
    	    strSQL.append(" order by r.sCode,t.dtstart ");

			log4j.info("查询贷款占用金额, strSQL=\n" + strSQL.toString());

		    prepareStatement(strSQL.toString());
        	
		    executeQuery();
		    while(transRS.next()) {
		    	//贷款占有的额度
                LoanUsedAmountInfo loanUsedAmountInfo = new LoanUsedAmountInfo();
		    	
		    	loanUsedAmountInfo.setClientCode(transRS.getString("sCode"));
		    	loanUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	loanUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	loanUsedAmountInfo.setLoanpayformNo(transRS.getString("loanpfcode"));
		    	loanUsedAmountInfo.setLoanpayformAmount(transRS.getDouble("mamount"));
		    	loanUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstart"));
		    	loanUsedAmountInfo.setEndDate(transRS.getTimestamp("dtend"));
		    	loanUsedAmountInfo.setLoanpayformState(transRS.getLong("nstatusid"));
		    	
		    	loanUsedAmountInfoList.add(loanUsedAmountInfo);
		    }
	      
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return loanUsedAmountInfoList;
	}
	
	/**
	 * 查询贷款申请占用的授信额度list
	 * @author 马现福
	 * @param queryInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getGroupApplyUsedAmount(AmountFormViewInfo queryInfo)
  		throws ITreasuryDAOException{
    
		List applyInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	        initDAO();
	        
        	strSQL = new StringBuffer();
        	strSQL.append(" select c.sName,a.sapplycode, a.ntypeid nloantype, nvl(a.MEXAMINEAMOUNT, 0) mApplyAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_loanForm a, ");
        	strSQL.append("     (select c1.* from client c1, ");
        	strSQL.append("     	(select distinct clientid from credit_amountform a ");
        	strSQL.append("      	where (a.groupcreditid= ? or a.id= ? )");
        	strSQL.append("      	and a.state= ? ) c2 ");
        	strSQL.append("      where c1.id=c2.clientid and c1.nStatusID=1) c ");
        	strSQL.append(" where 1=1 ");
        	strSQL.append(" and a.nBorrowClientID = c.id");        	
        	strSQL.append(" and a.nstatusID = ?");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(queryInfo.getOfficeId(), queryInfo.getCurrencyId()) + ")");
        	strSQL.append(" order by c.sCode, a.ntypeid, a.dtstartdate ");

			log4j.info("查询贷款申请占用金额, strSQL=\n" + strSQL.toString());

		    prepareStatement(strSQL.toString());
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
    		transPS.setLong(nIndex++, LOANConstant.LoanStatus.APPROVALING);
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
    		
		    executeQuery();
		    while(transRS.next()) {
		    	//授信中申请单占有的额度
		    	ApplyUsedAmountInfo applyUsedAmountInfo = new ApplyUsedAmountInfo();
		    	applyUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	applyUsedAmountInfo.setApplyNo(transRS.getString("sapplycode"));
		    	applyUsedAmountInfo.setLoanType(transRS.getLong("nloantype"));
		    	applyUsedAmountInfo.setApplyAmount(transRS.getDouble("mApplyAmount"));
		    	applyUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	applyUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	applyUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	applyUsedAmountInfo.setApplyState(transRS.getLong("nstatusid"));
		    	
		    	applyInfoList.add(applyUsedAmountInfo);
		    }
	      
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return applyInfoList;
	}	
	
	
	/**
	 * 查询贷款占用的授信额度list（1.放款通知单申请时校验）
	 * @author 马现福
	 * @param queryInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getGroupLoanUsedAmount(AmountFormViewInfo queryInfo)
  		throws ITreasuryDAOException{
    
		List loanUsedAmountInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	        initDAO();
	        
        	strSQL = new StringBuffer();
        	
        	strSQL.append(" select r.sCode,r.sName,s.scontractcode,t.scode loanpfcode,t.mamount,t.dtstart,t.dtend,t.nstatusid ");
    	    strSQL.append(" from loan_payform t,loan_contractform s,client r,");
    	    strSQL.append("     (select distinct clientid from credit_amountform a ");
        	strSQL.append("      where (a.groupcreditid= " + queryInfo.getId() + " or a.id= " + queryInfo.getId() + " )");
        	strSQL.append("      and a.state= " + LOANConstant.CreditFlowStatus.SAVE+ ") client");
    	    strSQL.append(" where s.id = t.ncontractid and s.nborrowclientid = r.id and s.nborrowclientid = client.clientid");
    	    strSQL.append(" and t.nstatusid in ("+ LOANConstant.LoanPayNoticeStatus.CHECK +","+ LOANConstant.LoanPayNoticeStatus.USED +","+ LOANConstant.LoanPayNoticeStatus.APPROVALING +")");
//    	    strSQL.append(" and t.nborrowclientid = " + queryInfo.getClientId()); 
    	    strSQL.append(" and s.nofficeid = " + queryInfo.getOfficeId()); 
    	    strSQL.append(" and s.ncurrencyid = " + queryInfo.getCurrencyId()); 
    	    strSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') >= '" + DataFormat.formatDate(queryInfo.getStartDate()) + "' ");
    	    strSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') <= '" + DataFormat.formatDate(queryInfo.getEndDate()) + "' ");
    	    strSQL.append(" and s.ntypeid = " + LOANConstant.CreditType.ZY);
    	    strSQL.append(" and s.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
    	    strSQL.append(" order by r.sCode,t.dtstart ");


			log4j.info("查询贷款占用金额, strSQL=\n" + strSQL.toString());

		    prepareStatement(strSQL.toString());

		    executeQuery();
		    while(transRS.next()) {
		    	//贷款占有的额度
		    	LoanUsedAmountInfo loanUsedAmountInfo = new LoanUsedAmountInfo();
		    	
		    	loanUsedAmountInfo.setClientCode(transRS.getString("sCode"));
		    	loanUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	loanUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	loanUsedAmountInfo.setLoanpayformNo(transRS.getString("loanpfcode"));
		    	loanUsedAmountInfo.setLoanpayformAmount(transRS.getDouble("mamount"));
		    	loanUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstart"));
		    	loanUsedAmountInfo.setEndDate(transRS.getTimestamp("dtend"));
		    	loanUsedAmountInfo.setLoanpayformState(transRS.getLong("nstatusid"));
		    	
		    	loanUsedAmountInfoList.add(loanUsedAmountInfo);
		    }
	      
	    }
	    catch (Exception e) {
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return loanUsedAmountInfoList;
	}
	
	private String getAllCreditTypeString(long officeId, long currencyId){
		long[] creditTypeId = LOANConstant.CreditType.getAllCode(officeId, currencyId);
		if(creditTypeId!=null && creditTypeId.length>0)
		{
			String strTemp = "";
			for(int i=0; i<creditTypeId.length; i++)
			{
				if(!((creditTypeId[i] == LOANConstant.CreditType.ZY) && (Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1)))
				strTemp += creditTypeId[i] + ",";
			}
			//去掉最后的','号
			return strTemp.substring(0, strTemp.length()-1);
			
		}
		
		return null;
	}	
		
	/**
	 * 查询某一集团授信对应的合同已占用明细情况
	 * @author 马现福
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public List getGroupContractUsedAmount(AmountFormViewInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
		List contractInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
			
            initDAO();
        	strSQL = new StringBuffer();
        	strSQL.append(" select  c.sName,a.scontractcode, a.ntypeid nloantype, nvl(a.MEXAMINEAMOUNT, 0) mContractAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_contractForm a, ");
        	strSQL.append("     (select c1.* from client c1, ");
        	strSQL.append("     	(select distinct clientid from credit_amountform a ");
        	strSQL.append("      	where (a.groupcreditid= ? or a.id= ? )");
        	strSQL.append("      	and a.state= ? ) c2 ");
        	strSQL.append("      where c1.id=c2.clientid and c1.nStatusID=1) c ");
        	strSQL.append(" where 1=1 ");
        	strSQL.append(" and a.nBorrowClientID = c.id");        	
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.SAVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.SUBMIT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.CHECK + ", ");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.APPROVALING + ")");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(queryInfo.getOfficeId(), queryInfo.getCurrencyId()) + ")");
        	strSQL.append(" order by c.sCode, a.ntypeid, a.dtstartdate ");

			log4j.info("查询贷款合同占用金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
		    
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());

		    executeQuery();
		    while(transRS.next()) {
		    	//授信中合同占有的额度
		    	ContractUsedAmountInfo contractUsedAmountInfo = new ContractUsedAmountInfo();
		    	contractUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	contractUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	contractUsedAmountInfo.setLoanType(transRS.getLong("nloantype"));
		    	contractUsedAmountInfo.setContractAmount(transRS.getDouble("mContractAmount"));
		    	contractUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	contractUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	contractUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	contractUsedAmountInfo.setContractState(transRS.getLong("nstatusid"));
		    	
		    	contractInfoList.add(contractUsedAmountInfo);
		    }
	      
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return contractInfoList;
	}
	
	
	/**
	 * 根据客户信息查询贷款合同金额list
	 * @author 马现福
	 * @param queryInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public List getContractUsedAmount(AmountFormViewInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
		List contractInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
			
            initDAO();
        	strSQL = new StringBuffer();
        	strSQL.append(" select  c.sName,a.scontractcode, a.ntypeid loantype, nvl(a.MEXAMINEAMOUNT, 0) mContractAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_contractForm a, client c");
        	strSQL.append(" where a.nBorrowClientID = ?");
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.SAVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.SUBMIT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.CHECK + ", ");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.APPROVALING + ")");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.nBorrowClientID = c.id");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(queryInfo.getOfficeId(), queryInfo.getCurrencyId()) + ")");
        	strSQL.append(" order by a.ntypeid, a.dtstartdate ");

			log4j.info("查询贷款合同占用金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
		    
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getClientId());
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());

		    executeQuery();
		    while(transRS.next()) {
		    	//授信中合同占有的额度
		    	ContractUsedAmountInfo contractUsedAmountInfo = new ContractUsedAmountInfo();
		    	contractUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	contractUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	contractUsedAmountInfo.setLoanType(transRS.getLong("loanType"));
		    	contractUsedAmountInfo.setContractAmount(transRS.getDouble("mContractAmount"));
		    	contractUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	contractUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	contractUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	contractUsedAmountInfo.setContractState(transRS.getLong("nstatusid"));
		    	
		    	contractInfoList.add(contractUsedAmountInfo);
		    	
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return contractInfoList;
	}	
	
	
	/**
	 * 自营贷款非循环贷款时还款单金额List
	 * @author 马现福
	 * @param afInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getRepayUsedAmount(AmountFormViewInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
		List repayInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	        initDAO();
	          
        	strSQL = new StringBuffer();
        	strSQL.append(" select d.sName,a.scontractcode, nvl(c.mAmount, 0) mCircleAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c,client d ");
        	strSQL.append(" where a.nBorrowClientID = ?");
        	if(!(Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1))
        	{
        	   strSQL.append(" and a.niscircle = 2");
        	}
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.nTypeID = ?");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and c.dtintereststart <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and c.dtintereststart >= ?");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and a.nBorrowClientID = d.id");
        	strSQL.append(" and c.nstatusid = ?");
        	strSQL.append(" order by a.dtstartdate ");

			log4j.info("查询非循环贷款还款金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
		    
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getClientId());
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
    		transPS.setLong(nIndex++, LOANConstant.LoanType.ZY);
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
		    transPS.setLong(nIndex++, SETTConstant.TransactionStatus.CHECK);

			executeQuery();
			while(transRS.next()) {
		    	//授信中还款单释放的额度
		    	RepayUsedAmountInfo repayUsedAmountInfo = new RepayUsedAmountInfo();
		    	repayUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	repayUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	repayUsedAmountInfo.setRepayAmount(transRS.getDouble("mCircleAmount"));
		    	repayUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	repayUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	repayUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	repayUsedAmountInfo.setContractState(transRS.getLong("nstatusid"));
		    	
		    	repayInfoList.add(repayUsedAmountInfo);
		    	
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return repayInfoList;
	}
	
	
	/**
	 * 自营贷款非循环贷款时还款单金额List
	 * @author 马现福
	 * @param afInfo
	 * @return List
	 * @throws ITreasuryDAOException
	 */
	public List getGroupRepayUsedAmount(AmountFormViewInfo queryInfo)
	  	throws ITreasuryDAOException{
	    
		List repayInfoList = new ArrayList();
		StringBuffer strSQL = null;

	    try {
	        initDAO();
	          
        	strSQL = new StringBuffer();
        	strSQL.append(" select d.sName,a.scontractcode, nvl(c.mAmount, 0) mCircleAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c,");
        	strSQL.append("     (select c1.* from client c1, ");
        	strSQL.append("     	(select distinct clientid from credit_amountform a ");
        	strSQL.append("      	where (a.groupcreditid= ? or a.id= ? )");
        	strSQL.append("      	and a.state= ? ) c2 ");
        	strSQL.append("      where c1.id=c2.clientid and c1.nStatusID=1) d ");        	
        	strSQL.append(" where 1=1 ");
        	strSQL.append(" and a.nBorrowClientID = d.id"); 
        	if(!(Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1))
        	{
        	   strSQL.append(" and a.niscircle = 2");
        	}
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = ?");
        	strSQL.append(" and a.ncurrencyid = ?");
        	strSQL.append(" and a.nTypeID = ?");
        	strSQL.append(" and a.dtstartdate <= ?");
        	strSQL.append(" and c.dtintereststart <= ?");
        	strSQL.append(" and a.dtstartdate >= ?");
        	strSQL.append(" and c.dtintereststart >= ?");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and c.nstatusid = ?");
        	strSQL.append(" order by d.sCode, a.dtstartdate ");

			log4j.info("查询非循环贷款还款金额, strSQL=\n" + strSQL.toString());
			
		    prepareStatement(strSQL.toString());
		    
        	int nIndex = 1;
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, queryInfo.getId());
    		transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
    		transPS.setLong(nIndex++, queryInfo.getOfficeId());
    		transPS.setLong(nIndex++, queryInfo.getCurrencyId());
    		transPS.setLong(nIndex++, LOANConstant.LoanType.ZY);
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getEndDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
		    transPS.setTimestamp(nIndex++, queryInfo.getStartDate());
		    transPS.setLong(nIndex++, SETTConstant.TransactionStatus.CHECK);

			executeQuery();
			while(transRS.next()) {
		    	//授信中还款单释放的额度
		    	RepayUsedAmountInfo repayUsedAmountInfo = new RepayUsedAmountInfo();
		    	repayUsedAmountInfo.setClientName(transRS.getString("sName"));
		    	repayUsedAmountInfo.setContractNo(transRS.getString("scontractcode"));
		    	repayUsedAmountInfo.setRepayAmount(transRS.getDouble("mCircleAmount"));
		    	repayUsedAmountInfo.setStartDate(transRS.getTimestamp("dtstartdate"));
		    	repayUsedAmountInfo.setEndDate(transRS.getTimestamp("dtenddate"));
		    	repayUsedAmountInfo.setTerm(transRS.getLong("nintervalnum"));
		    	repayUsedAmountInfo.setContractState(transRS.getLong("nstatusid"));
		    	
		    	repayInfoList.add(repayUsedAmountInfo);
		    	
		    }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      throw new ITreasuryDAOException("数据库查询异常",e);
	    }
	    finally {
	      finalizeDAO();
	    }
	    return repayInfoList;
	}	
	
	
	public Collection findByCollection(AmountFormInfo afInfo)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer strSQL = null;
		try {
			/*----------------- init DAO --------------------*/
			try {
			  initDAO();
			}
			catch (ITreasuryDAOException e) {
			   throw new ITreasuryDAOException("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		        
	        try {
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select t1.*, t2.name clientName, t3.sname inputUserName from " + this.strTableName + " t1, client_clientinfo t2, userinfo t3");
	        	strSQL.append(" where t1.clientid = t2.id(+)");
	        	strSQL.append(" and t1.inputuserid = t3.id(+)");
	        	if(afInfo.getClientId() > 0){
	        		strSQL.append(" and t1.clientId = ?");
	        	}
		        if(afInfo.getCreditModel() > 0){
		        	strSQL.append(" and t1.CreditModel = ?");
				}
	        	if(afInfo.getCreditAmount() > 0.0){
	        		strSQL.append(" and t1.creditAmount <= ?");
	        	}
	        	if(afInfo.getStartDate() != null && afInfo.getEndDate() == null){
	        		strSQL.append(" and t1.STARTDATE >= ?");
	        	}
	        	if(afInfo.getEndDate() != null && afInfo.getStartDate() == null){
	        		strSQL.append(" and t1.ENDDATE <= ?");
	        	}
	        	if(afInfo.getStartDate() != null && afInfo.getEndDate() != null){
	        		strSQL.append(" and t1.STARTDATE >= ?");
	        		strSQL.append(" and t1.ENDDATE <= ?");
	        	}
		        strSQL.append(" and t1.STATE = ?");
	        	strSQL.append(" and t1.officeId = ?");
	        	strSQL.append(" and t1.currencyId = ?");

		        prepareStatement(strSQL.toString());
				log4j.info("AmountFormDao.checkByCondition()\n");
				log4j.info("strSQL=\n" + strSQL.toString());
				
	        	int nIndex = 1;
	        	if(afInfo.getClientId() > 0){
	        		transPS.setLong(nIndex++, afInfo.getClientId());
	        	}
		        if(afInfo.getCreditModel() > 0){
		        	transPS.setLong(nIndex++, afInfo.getCreditModel());
				}
	        	if(afInfo.getCreditAmount() > 0.0){
	        		transPS.setDouble(nIndex++, afInfo.getCreditAmount());
	        	}
	        	if(afInfo.getStartDate() != null && afInfo.getEndDate() == null){
	        		transPS.setTimestamp(nIndex++, afInfo.getStartDate());
	        	}
	        	if(afInfo.getEndDate() != null && afInfo.getStartDate() == null){
	        		transPS.setTimestamp(nIndex++, afInfo.getEndDate());
	        	}
	        	if(afInfo.getStartDate() != null && afInfo.getEndDate() != null){
	        		transPS.setTimestamp(nIndex++, afInfo.getStartDate());
	        		transPS.setTimestamp(nIndex++, afInfo.getEndDate());
	        	}
	        	transPS.setLong(nIndex++, LOANConstant.CreditFlowStatus.SAVE);
	        	transPS.setLong(nIndex++, afInfo.getOfficeId());
	        	transPS.setLong(nIndex++, afInfo.getCurrencyId());
		        
	        	executeQuery();
	        	coll = getDataEntitiesFromResultSet(AmountFormViewInfo.class);
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("数据库查询异常", e);
		    }
	
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库查询异常",e);
		}
		finally {
			finalizeDAO();
		}
		if(coll == null || coll.size() == 0){
			return null;
		}
		else {
			return coll;
		}
	}
	
	/**
	 * 授信可用额度列表查询，查询客户、授信额度等除可用额度外的其它列表信息
	 * 
	 * @param amountFormViewInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public List getCreditRemainAmountQuery(AmountFormViewInfo amountFormViewInfo)throws ITreasuryDAOException
	{
		List remainAmountList = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		try{
			
			initDAO();
			
		}catch(ITreasuryDAOException e1){
			e1.printStackTrace();
			throw new ITreasuryDAOException("创建数据库连接时发生异常",e1);
		}
		try{
			sbSQL.append("SELECT F.ID AS FID,");
			sbSQL.append("       F.CREDITCODE AS CREDITCODE,");
			sbSQL.append("       (SELECT C.SNAME FROM CLIENT C WHERE C.ID = F.CLIENTID) AS CLIENTNAME,");
			sbSQL.append("       F.CREDITMODEL AS CREDITMODEL,");
			sbSQL.append("       F.CREDITAMOUNT AS CREDITAMOUNT,");
			sbSQL.append("       F.STARTDATE AS STARTDATE,");
			sbSQL.append("       F.ENDDATE AS ENDDATE,");
			sbSQL.append("       F.INPUTUSERID AS INPUTUSERID,");
			sbSQL.append("       (SELECT U.SNAME FROM USERINFO U WHERE U.ID = F.INPUTUSERID) AS INPUTUSERNAME,");
			sbSQL.append("       F.INPUTDATE AS INPUTDATE,");
			sbSQL.append("       F.STATE AS STATE,");
			sbSQL.append("       F.OFFICEID AS OFFICEID,");
			sbSQL.append("       F.CURRENCYID AS CURRENCYID,");
			sbSQL.append("       F.CLIENTID AS CLIENTID,");
			sbSQL.append("       F.CONTROLTYPE AS CONTROLTYPE,");
			sbSQL.append("       F.GROUPCREDITID AS GROUPCREDITID");
			sbSQL.append("  FROM CREDIT_AMOUNTFORM F");
			sbSQL.append("   WHERE 1=1");
			sbSQL.append("   AND F.STATE = " + LOANConstant.CreditFlowStatus.SAVE);
			sbSQL.append("   AND F.OFFICEID = " + amountFormViewInfo.getOfficeId());
			sbSQL.append("   AND F.CURRENCYID = " + amountFormViewInfo.getCurrencyId());
			
			if(amountFormViewInfo.getClientId() > 0)
			{
				sbSQL.append(" AND F.CLIENTID = " + amountFormViewInfo.getClientId());
			}
			if(amountFormViewInfo.getCreditAmount() > 0)
			{
				sbSQL.append(" AND F.CREDITAMOUNT <= "+amountFormViewInfo.getCreditAmount());
			}
			
			if(amountFormViewInfo.getStartDate() != null )
			{
				sbSQL.append(" AND F.STARTDATE  <= TO_DATE('"+DataFormat.getDateString(amountFormViewInfo.getStartDate())+"','yyyy-mm-dd')");
				sbSQL.append(" AND F.ENDDATE  >= TO_DATE('"+DataFormat.getDateString(amountFormViewInfo.getStartDate())+"','yyyy-mm-dd')");
			}
			
			sbSQL.append(" order by CLIENTNAME ");
			
			this.prepareStatement(sbSQL.toString());
			this.executeQuery();
			while(this.transRS.next() && this.transRS != null)
			{
				AmountFormViewInfo formViewInfo = new AmountFormViewInfo();
				formViewInfo.setId(transRS.getLong("FID"));
				formViewInfo.setCreditCode(transRS.getString("CREDITCODE"));
				formViewInfo.setClientId(transRS.getLong("CLIENTID"));
				formViewInfo.setClientName(transRS.getString("CLIENTNAME"));
				formViewInfo.setCreditModel(transRS.getLong("CREDITMODEL"));
				formViewInfo.setGroupCreditId(transRS.getLong("GROUPCREDITID"));
				formViewInfo.setCreditAmount(transRS.getDouble("CREDITAMOUNT"));
				formViewInfo.setControlType(transRS.getLong("CONTROLTYPE"));
				formViewInfo.setStartDate(transRS.getTimestamp("STARTDATE"));
				formViewInfo.setEndDate(transRS.getTimestamp("ENDDATE"));
				formViewInfo.setOfficeId(transRS.getLong("OFFICEID"));
				formViewInfo.setCurrencyId(transRS.getLong("CURRENCYID"));
				formViewInfo.setInputUserId(transRS.getLong("INPUTUSERID"));
				formViewInfo.setInputUserName(transRS.getString("INPUTUSERNAME"));
				formViewInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
				formViewInfo.setState(transRS.getLong("STATE"));
				remainAmountList.add(formViewInfo);
			}
			
		}catch(Exception e2){
			e2.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001",e2);
			
		}finally{
			this.finalizeDAO();
		}
		
		
		return remainAmountList;
	}
	
	public AmountFormViewInfo getAmountFormViewDetail(long ID)throws ITreasuryDAOException
	{
		StringBuffer stringBufferSQL = new StringBuffer();
		AmountFormViewInfo amountFormViewInfo = null;
		try{
			stringBufferSQL.append("SELECT A1.ID AS ID,A1.CREDITCODE AS CREDITCODE,(SELECT C1.SCODE FROM CLIENT C1 WHERE C1.ID = A1.CLIENTID) AS CLIENTCODE,\n");
			stringBufferSQL.append("(SELECT C2.SNAME FROM CLIENT C2 WHERE C2.ID = A1.CLIENTID) AS CLIENTNAME,A1.CREDITMODEL AS CREDITMODEL,A2.CREDITCODE AS GROUPCREDITCODE,\n");
			stringBufferSQL.append("A2.STARTDATE AS GROUPCREDITSTARTDATE,A2.ENDDATE AS GROUPCREDITENDDATE,A1.CREDITAMOUNT AS CREDITAMOUNT,A1.CONTROLTYPE AS CONTROLTYPE,\n");
			stringBufferSQL.append("A1.STARTDATE AS STARTDATE,A1.ENDDATE AS ENDDATE,A1.CLIENTID AS CLIENTID,A1.OFFICEID AS OFFICEID,A1.CURRENCYID AS CURRENCYID,\n");
			stringBufferSQL.append("A1.GROUPCREDITID AS GROUPCREDITID FROM CREDIT_AMOUNTFORM A1,CREDIT_AMOUNTFORM A2 WHERE A1.GROUPCREDITID = A2.ID(+) AND A1.ID = ?");
			log4j.info("getAmountFormViewDetail--:::"+stringBufferSQL.toString());
			initDAO();
			this.prepareStatement(stringBufferSQL.toString());
			this.transPS.setLong(1,ID);
			this.executeQuery();
			if(this.transRS.next() && this.transRS != null)
			{
				amountFormViewInfo = new AmountFormViewInfo();
				amountFormViewInfo.setId(transRS.getLong("ID"));
				amountFormViewInfo.setCreditCode(transRS.getString("CREDITCODE"));
				amountFormViewInfo.setClientId(this.transRS.getLong("CLIENTID"));
				amountFormViewInfo.setClientCode(transRS.getString("CLIENTCODE"));
				amountFormViewInfo.setClientName(transRS.getString("CLIENTNAME"));
				amountFormViewInfo.setCreditModel(transRS.getLong("CREDITMODEL"));
				amountFormViewInfo.setGroupCreditId(this.transRS.getLong("GROUPCREDITID"));
				amountFormViewInfo.setGroupCreditCode(transRS.getString("GROUPCREDITCODE"));
				amountFormViewInfo.setGroupStartDate(transRS.getTimestamp("GROUPCREDITSTARTDATE"));
				amountFormViewInfo.setGroupEndDate(transRS.getTimestamp("GROUPCREDITENDDATE"));
				amountFormViewInfo.setCreditAmount(transRS.getDouble("CREDITAMOUNT"));
				amountFormViewInfo.setControlType(transRS.getLong("CONTROLTYPE"));
				amountFormViewInfo.setStartDate(transRS.getTimestamp("STARTDATE"));
				amountFormViewInfo.setEndDate(transRS.getTimestamp("ENDDATE"));
				amountFormViewInfo.setOfficeId(this.transRS.getLong("OFFICEID"));
				amountFormViewInfo.setCurrencyId(this.transRS.getLong("CURRENCYID"));
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001",e);
			
		}finally{
			
			this.finalizeDAO();
			
		}
		return amountFormViewInfo;
	}

	/**
	 * 查询符合条件的集团授信的所有下级成员单位的授信
	 * 
	 * @param queryInfo 集团授信
	 * @return
	 * @throws IException
	 */
	public Collection getSubClientGroupAmountCollection(AmountFormViewInfo queryInfo) throws IException
	{
		StringBuffer  sbSQL = new StringBuffer();
		Collection colResult = new ArrayList();
		
		try{
			initDAO();
			
			sbSQL.append("select distinct c.id clientId, c.sCode clientCode, c.sName clientName, af.*");
			sbSQL.append("  from credit_amountform af, client c");
			sbSQL.append(" where af.state = " + LOANConstant.CreditFlowStatus.SAVE);
			sbSQL.append("   and af.groupcreditid = " + queryInfo.getClientId());
			sbSQL.append("   and af.clientid = c.id");
			sbSQL.append("   and c.nStatusID = 1");
			sbSQL.append(" order by clientName asc");
			
			prepareStatement(sbSQL.toString());
			this.executeQuery();
			
			colResult = getDataEntitiesFromResultSet(AmountFormViewInfo.class);
			
		}catch(Exception exp){
			throw new ITreasuryDAOException("查询集团授信的成员单位授信时出错，" + exp.getMessage(), exp);
		}finally{
			finalizeDAO();
		}

		return colResult;
	}
	
	/**
	 * 保存授信已使用的额度（授信报表专用）
	 * @param amountFormViewInfo
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public void updateUsedAmount(AmountFormViewInfo amountFormViewInfo) throws ITreasuryDAOException, SQLException {

		try {
			String credittype = "";
			credittype = this.getCreditType(amountFormViewInfo);
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE " + strTableName + " SET \n");
			buffer.append(" usedamount = " + amountFormViewInfo.getUsedAmount());
			buffer.append(" , CREDITTYPE = '" + credittype +"'");
			buffer.append(" WHERE ID = " + amountFormViewInfo.getId());
			String strSQL = buffer.toString();
			prepareStatement(strSQL);
			executeUpdate();
		} catch (ITreasuryDAOException ide) {
			throw ide;
		} finally {
			finalizeDAO();
		}
	}
	/**
	 * 得到授信的品种（授信报表专用）
	 * @param amountFormViewInfo
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public String getCreditType(AmountFormViewInfo amountFormViewInfo) throws ITreasuryDAOException, SQLException {
		
		String credittype = "";
		int credittypeLen = 0;
		AmountFromQueryBiz amountFromQueryBiz = new AmountFromQueryBiz();
		try {
			amountFormViewInfo =  amountFromQueryBiz.getAmountFormViewDetail(amountFormViewInfo.getId());
			if( amountFormViewInfo != null)
			{
				Iterator it = amountFormViewInfo.getSubAmountFormList().iterator();
				while(it.hasNext()){
					SubAmountFormInfo safInfo = (SubAmountFormInfo)it.next();
					credittype += LOANConstant.CreditType.getName(safInfo.getCreditType())+",";
				}
			}
			credittypeLen = credittype.length();
			if(credittypeLen>0 && credittype.substring(credittypeLen-1, credittypeLen).equals(",")) credittype = credittype.substring(0, credittypeLen-1);
		} catch (ITreasuryDAOException ide) {
			throw ide;
		}
		return credittype;
	}

}

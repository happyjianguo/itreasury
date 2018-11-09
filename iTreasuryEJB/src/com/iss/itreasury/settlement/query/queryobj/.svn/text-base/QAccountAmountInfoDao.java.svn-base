/*
  Created on 2003-12-05
 * 
 * To change the template for this generated file go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-09-20
 */
public class QAccountAmountInfoDao extends BaseQueryObject {
    public final static int OrderBy_AccountNo = 1;
    public final static int OrderBy_Name = 2;
    public final static int OrderBy_AccountTypeID = 3;
    public final static int OrderBy_StartBalance = 4;
    public final static int OrderBy_PayAmount = 5;
    public final static int OrderBy_RecAmount = 6;
    public final static int OrderBy_EndBalance = 7;
    // SQL语法结构
    private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;
    /**
     * 构造函数
     */
    public QAccountAmountInfoDao() {
        super();

        logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
    }
    /**
     * 临时SQL
     * @param  qaawi,nTransTypeID
     * @return bufForm
     */
    private String setFrom(QueryAccountAmountWhereInfo qaawi,
        long lTransTypeID) {
        StringBuffer bufFrom = new StringBuffer();

        String depositAccountType = this.getDepositAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID());
        String loanAccountType = this.getLoanAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID());
        if ((depositAccountType != null && depositAccountType.trim().length()>0) || 
	        (loanAccountType != null && loanAccountType.trim().length()>0)) {
	        bufFrom.append("AND (");
	        if (depositAccountType != null && depositAccountType.trim().length()>0) {
		        bufFrom.append(" (a.nTransDirection = " +
		            (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT?
		            SETTConstant.DebitOrCredit.DEBIT:
		            SETTConstant.DebitOrCredit.CREDIT));
		        bufFrom.append(" AND b.nAccountTypeID IN (" + depositAccountType + "))");
	        }
	        if ((depositAccountType != null && depositAccountType.trim().length()>0) && 
	        	(loanAccountType != null && loanAccountType.trim().length()>0)) {
	        	bufFrom.append(" OR");
	        }
	        if (loanAccountType != null && loanAccountType.trim().length()>0) {
	            bufFrom.append(" (a.nTransDirection = " +
	                    (lTransTypeID == SETTConstant.DebitOrCredit.DEBIT?
	                    SETTConstant.DebitOrCredit.CREDIT:
	                    SETTConstant.DebitOrCredit.DEBIT));
		        bufFrom.append("AND b.nAccountTypeID IN (" + loanAccountType + "))");
	        }
	        bufFrom.append(" )");
        }

        return bufFrom.toString();
    }

    /**
     * 账户金额查询条件的生成
     * @param  qaawi:查询条件
     * @param  nSelectTypeID
     * @return nothing
     */
    private void getInfoSQL(QueryAccountAmountWhereInfo qaawi,
        int nSelectTypeID) {
        StringBuffer bufTmp = new StringBuffer();
        StringBuffer bufTemp = new StringBuffer();
        StringBuffer bufFromTemp = new StringBuffer();
        // create select
        m_sbSelect = new StringBuffer();
        m_sbSelect.append("\n bbb.ID AccountID,bbb.sAccountNo AccountNo, \n");
        if (qaawi.getIsCheckedType() == 3) {
            m_sbSelect.append("bbb.nAccountTypeID AccountTypeID, \n");
        } else if (qaawi.getIsCheckedType() == 6) {
        	//m_sbSelect.append("(select b.id AccountTypeID from sett_accounttype a,(select id,',' || saccounttypeid || ',' saccounttypeid from sett_DEPOSITLOANSEARCHSETTING where nofficeid=" + qaawi.getOfficeID() + " and ncurrencyid="+ qaawi.getCurrencyID() +" and SNAME='"+SETTConstant.DepositLoanType.getName(qaawi.getAccountTypeID2())+"' ) b where b.saccounttypeid like   '%,' || a.id || ',%' and a.id=bbb.nAccountTypeID and a.nstatusId=1 and a.officeId="+qaawi.getOfficeID()+" and a.currencyId="+qaawi.getCurrencyID()+") AccountTypeID, \n");
        	m_sbSelect.append("bbb.nAccountTypeID AccountTypeID, \n");
        }
        m_sbSelect.append("ccc.sName Name, \n");
		m_sbSelect.append("NVL(aaa.mStartBalance,0.0) StartBalance, \n");
		m_sbSelect.append("NVL(aaa.mPayAmount,0.0) PayAmount, \n");
        m_sbSelect.append("NVL(aaa.mRecAmount,0.0) RecAmount, \n");
		m_sbSelect.append("NVL(aaa.mStartBalance,0.0)+ \n");
		m_sbSelect.append("NVL(aaa.mRecAmount,0.0)- \n");
		m_sbSelect.append("NVL(aaa.mPayAmount, 0.0) EndBalance \n");

        // create from 
        m_sbFrom = new StringBuffer();
        m_sbFrom.append("(SELECT dd.nAccountID, \n");
		bufTmp.append("NVL(dd.mStartBalance,0.0) mStartBalance, \n");
		bufTmp.append("NVL(cc.mPayAmount,0.0) mPayAmount,"+
            "NVL(cc.mRecAmount,0.0) mRecAmount FROM ( \n");
		bufTmp.append("    SELECT bb.nTransAccountID,"+
            "aa.mPayAmount,bb.mRecAmount FROM \n");
        bufFromTemp.append("        (SELECT nTransAccountID,"+
            "nTransDirection,SUM(mAmount) mPayAmount \n");
        bufTemp.append("        FROM sett_TransAccountDetail a, "+
            "sett_account b \n");
        bufTemp.append("        WHERE a.nTransAccountID = b.ID \n");
		bufTemp.append("        	AND a.nStatusID = "+
			SETTConstant.TransactionStatus.CHECK+" \n");
            
         if (!(qaawi.getStartQueryDate() == null || qaawi.getStartQueryDate().toString().trim().length() == 0))
         { 
            bufTemp.append(" AND a.dtExecute >= to_date('" + DataFormat.getDateString(qaawi.getStartQueryDate()) + "','yyyy-mm-dd')   \n");
         }
         if (!(qaawi.getEndQueryDate() == null || qaawi.getEndQueryDate().toString().trim().length() == 0))
         {
            bufTemp.append(" AND a.dtExecute <= to_date('" + DataFormat.getDateString(qaawi.getEndQueryDate()) + "','yyyy-mm-dd')   \n");
         }
                
       /* if (!(qaawi.getStartQueryDate() == null || 
            qaawi.getStartQueryDate().toString().trim().length() == 0))
            bufTemp.append("        AND a.dtExecute BETWEEN TO_DATE('" + 
            DataFormat.getDateString(qaawi.getStartQueryDate()) + 
                "','YYYY/MM/DD HH24:MI:SS') \n");
        if (!(qaawi.getEndQueryDate() == null || 
            qaawi.getEndQueryDate().toString().trim().length() == 0))
            bufTemp.append("        AND TO_DATE('" + 
            DataFormat.getDateString(qaawi.getEndQueryDate()) + 
                "','YYYY/MM/DD HH24:MI:SS') \n");*/
                
                
        bufFromTemp.append(bufTemp.toString());
        bufFromTemp.append(this.setFrom(qaawi, SETTConstant.DebitOrCredit.DEBIT));
        bufFromTemp.append("            GROUP BY nTransAccountID,"+
            "nTransDirection) aa, \n");
        bufFromTemp.append("        (SELECT nTransAccountID,"+
            "nTransDirection,SUM(mAmount) mRecAmount \n");
        bufFromTemp.append(bufTemp.toString());
        bufFromTemp.append(this.setFrom(qaawi, SETTConstant.DebitOrCredit.CREDIT));
        bufFromTemp.append("            GROUP BY  nTransAccountID,"+
            "nTransDirection) bb \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID(+) = "+
            "bb.nTransAccountID \n");
		bufTmp.append("        UNION \n");
		bufTmp.append("    SELECT aa.nTransAccountID,"+
			"aa.mPayAmount,bb.mRecAmount FROM \n");
		bufTmp.append(bufFromTemp.toString());
		bufTmp.append("        WHERE aa.nTransAccountID = "+
            "bb.nTransAccountID(+)) cc, \n");
		//如果是当天
		/*if (isToday(qaawi.getOfficeID(), qaawi.getCurrencyID(), qaawi.getStartQueryDate()))
		{
		    bufTmp.append("		(SELECT mBalance mStartBalance,nAccountID FROM sett_subaccount) dd \n");
		}
		else
		{*/
			bufTmp.append("        (SELECT SUM(mBalance) mStartBalance,"+
	            "nAccountID FROM sett_DailyAccountBalance \n");
	        if (!(qaawi.getStartQueryDate() == null || 
	            qaawi.getStartQueryDate().toString().trim().length() == 0))
				bufTmp.append("        WHERE dtDate = TO_DATE('" + 
	            	DataFormat.getDateString(DataFormat.
	                getPreviousDate(qaawi.getStartQueryDate())) + 
	                "','yyyy-mm-dd') \n");
	        bufTmp.append("        GROUP BY nAccountID) dd \n");
		//}
		m_sbFrom.append(bufTmp.toString());
        m_sbFrom.append("    WHERE  cc.nTransAccountID(+) = dd.nAccountID \n");
		m_sbFrom.append("UNION \n");
		m_sbFrom.append("SELECT cc.nTransAccountID, \n");
		m_sbFrom.append(bufTmp.toString());
		m_sbFrom.append("    WHERE  cc.nTransAccountID = dd.nAccountID(+)) aaa, \n");
        m_sbFrom.append("    sett_Account bbb, \n");
        m_sbFrom.append("    Client ccc \n");

        // create where 
        m_sbWhere = new StringBuffer();
        m_sbWhere.append("  aaa.nAccountID = bbb.ID \n");
        m_sbWhere.append("  AND bbb.nClientID = ccc.ID \n");
        m_sbWhere.append("  AND bbb.nOfficeID = " + 
            qaawi.getOfficeID() + " \n");
        m_sbWhere.append("  AND bbb.nCurrencyID = " + 
            qaawi.getCurrencyID() + " \n");
        m_sbWhere.append("  AND bbb.nCheckStatusID = " + 
            SETTConstant.AccountCheckStatus.CHECK + " \n");

        System.out.println("--------得到客户类型ID为:"+qaawi.getClientTypeID());
        if(qaawi.getClientTypeID() != -1){		//客户类型ID
        	m_sbWhere.append("  AND ccc.NSETTCLIENTTYPEID = " + 
        			qaawi.getClientTypeID() + " \n");
        }
        
        if(qaawi.getEnterpriseTypeID1() > 0){		//客户属性1
        	m_sbWhere.append("  AND ccc.nClienttypeID1 = " + 
        			qaawi.getEnterpriseTypeID1() + " \n");
        }
        if(qaawi.getEnterpriseTypeID2() > 0){		//客户属性2
        	m_sbWhere.append("  AND ccc.nClienttypeID2 = " + 
        			qaawi.getEnterpriseTypeID2() + " \n");
        }
        if(qaawi.getEnterpriseTypeID3() > 0){		//客户属性3
        	m_sbWhere.append("  AND ccc.nClienttypeID3 = " + 
        			qaawi.getEnterpriseTypeID3() + " \n");
        }
        if(qaawi.getEnterpriseTypeID4() > 0){		//客户属性4
        	m_sbWhere.append("  AND ccc.nClienttypeID4 = " + 
        			qaawi.getEnterpriseTypeID4() + " \n");
        }
        if(qaawi.getEnterpriseTypeID5() > 0){		//客户属性5
        	m_sbWhere.append("  AND ccc.nClienttypeID5 = " + 
        			qaawi.getEnterpriseTypeID5() + " \n");
        }
        if(qaawi.getEnterpriseTypeID6() > 0){		//客户属性6
        	m_sbWhere.append("  AND ccc.nClienttypeID6 = " + 
        			qaawi.getEnterpriseTypeID6() + " \n");
        }
        if(qaawi.getClientManager() > 0){	    //客户经理
        	m_sbWhere.append("  AND ccc.nCustomerManagerUserId = " + 
        			qaawi.getClientManager() + " \n");
        }
        if (qaawi.getStartAccountNo() != null && 
            qaawi.getStartAccountNo().length() > 0) {
	            m_sbWhere.append("  AND bbb.sAccountNo >= '" +
	            qaawi.getStartAccountNo() + "'");
        }
        if (qaawi.getEndAccountNo() != null && 
            qaawi.getEndAccountNo().length() > 0) {
	            m_sbWhere.append("  AND bbb.sAccountNo <= '" + 
	            qaawi.getEndAccountNo() + "'");
        }
        //add by 2012-05-16 添加指定编号
		if(qaawi.getAppointAccountNo() != null && qaawi.getAppointAccountNo().length() > 0){
			m_sbWhere.append(" AND bbb.sAccountNo in ('"+qaawi.getAppointAccountNo()+"')");
		}
        //modify by zcwang 2007-10-30 
//        if ((qaawi.getStartAccountNo() == null || 
//			qaawi.getStartAccountNo().length() == 0) && 
//			(qaawi.getEndAccountNo() == null || 
//			qaawi.getEndAccountNo().length() == 0)) {
        //
			if (qaawi.getStartClientCode() != null && 
				qaawi.getStartClientCode().length() > 0) {
					m_sbWhere.append("  AND ccc.sCode >= '" + 
					qaawi.getStartClientCode() + "'");
			}
			if (qaawi.getEndClientCode() != null && 
				qaawi.getEndClientCode().length() > 0) {
					m_sbWhere.append("  AND ccc.sCode <= '" + 
					qaawi.getEndClientCode() + "'");
//			}
        }
        if (qaawi.getIsCheckedType() == 3 && qaawi.getAccountTypeID1() > 0) {
            m_sbWhere.append("  AND bbb.nAccountTypeID = " + 
                qaawi.getAccountTypeID1());
        } else if (qaawi.getIsCheckedType() == 6 && 
            qaawi.getAccountTypeID2() > 0) {
            //m_sbWhere.append("  AND bbb.nAccountTypeID IN ( \n");
            //m_sbWhere.append("SELECT sAccountTypeID FROM "+"sett_DEPOSITLOANSEARCHSETTING WHERE \n");
            //m_sbWhere.append(" ID = " + qaawi.getAccountTypeID2() + ") \n");
           // m_sbWhere.append("AND '<' || (SELECT replace(sAccountTypeID,',','><') FROM sett_DEPOSITLOANSEARCHSETTING WHERE SNAME ='" 
           //         + SETTConstant.DepositLoanType.getName(qaawi.getAccountTypeID2()) +"' and NOFFICEID="+qaawi.getOfficeID()+" ) || '>'like '%<' || bbb.nAccountTypeID || '>%' ");
        	m_sbWhere.append("  AND bbb.nAccountTypeID IN ( \n");
            m_sbWhere.append("SELECT ID FROM "+"sett_accounttype WHERE \n");
            m_sbWhere.append(" NACCOUNTGROUPID = " + qaawi.getAccountTypeID2() + ") \n");
        }
        if (nSelectTypeID == 2)
            m_sbWhere.append("  AND (mPayAmount <> 0.0 OR "+
                "mRecAmount <> 0.0) \n");

        // create orderby
        m_sbOrderBy = new StringBuffer();
        String sDesc = qaawi.getDesc() == 1 ? " desc " : "";
        switch ((int) qaawi.getOrderField()) {
            case OrderBy_AccountNo :
                m_sbOrderBy.append(" \n order by AccountNo" + sDesc);
                break;
            case OrderBy_Name :
                m_sbOrderBy.append(" \n order by Name" + sDesc);
                break;
            case OrderBy_AccountTypeID :
                m_sbOrderBy.append(" \n order by AccountTypeID" + sDesc);
                break;
            case OrderBy_StartBalance :
                m_sbOrderBy.append(" \n order by StartBalance" + sDesc);
                break;
            case OrderBy_PayAmount :
                m_sbOrderBy.append(" \n order by PayAmount" + sDesc);
                break;
            case OrderBy_RecAmount :
                m_sbOrderBy.append(" \n order by RecAmount" + sDesc);
                break;
            case OrderBy_EndBalance :
                m_sbOrderBy.append(" \n order by EndBalance" + sDesc);
                break;
            default :
                m_sbOrderBy.append(" \n order by AccountNo" + sDesc);
                break;
        }
    }

    /**
     * 账户金额查询条件的生成
     * @param     qaawi:查询条件
     * @return nothing
     */
    private void getAccountDetailInfoSQL(QueryAccountAmountWhereInfo qaawi) {
        //SELECT
    	m_sbSelect = new StringBuffer();
    	m_sbSelect.append("a.ID ID,NVL(b.ID,-1) trnasID, a.dtExecute executeDate, a.sTransNo transNo, \n");
    	m_sbSelect.append("a.nTransactionTypeId transactionTypeId, a.nTransAccountId transAccountId, \n");
    	m_sbSelect.append("a.mAmount amount, a.nTransDirection transDirection, a.nOppAccountId oppAccountId, \n");
    	//add by gdlian 2011-9-7 添加了通知开立对方账户号的字段显示oppAccountNo
    	m_sbSelect.append("a.nStatusId statusId, a.sAbstract remark, a.nSerialNo serialNo, a.oppaccountno oppAccountNo, \n");
    //	m_sbSelect.append("b.InputUserID, b.CheckUserID, c.nAccountTypeID accountTypeId \n");
    	//增加查询金额类型
    	m_sbSelect.append("b.InputUserID, b.CheckUserID, c.nAccountTypeID accountTypeId,a.amounttype amounttype \n");
    	//
    	//FROM
    	m_sbFrom = new StringBuffer();
    	m_sbFrom.append("sett_TransAccountDetail a, sett_VTransaction b, sett_account c \n");
    	//WHERE
    	m_sbWhere = new StringBuffer();
    	m_sbWhere.append("1 = 1 \n");
    	m_sbWhere.append("	AND a.sTransNo = b.TransNo(+) \n");
    	//modify by bingliu 20110720 为通存通兑业务修改 其他办事处有可能与本账户发生业务
    	//m_sbWhere.append("	AND a.nOfficeID = "+qaawi.getOfficeID()+" \n");
    	m_sbWhere.append("	AND a.nCurrencyID = "+qaawi.getCurrencyID()+" \n");
    	m_sbWhere.append("	And a.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
    	m_sbWhere.append("	AND a.nTransAccountID = "+qaawi.getAccountID()+" \n");
    	if (qaawi.getStartQueryDate() != null && qaawi.getStartQueryDate().toString().trim().length() > 0) {
    		m_sbWhere.append("	AND a.dtExecute >= TO_DATE('"+DataFormat.getDateString(qaawi.getStartQueryDate())+"', 'YYYY-MM-DD') \n");
    	}
    	if (qaawi.getEndQueryDate() != null && qaawi.getEndQueryDate().toString().trim().length() > 0) {
    		m_sbWhere.append("	AND a.dtExecute <= TO_DATE('"+DataFormat.getDateString(qaawi.getEndQueryDate())+"', 'YYYY-MM-DD') \n");
    	}
    	m_sbWhere.append("	AND a.nTransAccountID = c.ID \n");

        logger.debug("SELECT " +m_sbSelect.toString()+" FROM "+m_sbFrom.toString()+" WHERE "+m_sbWhere.toString());
    }

    /**
     * 账户金额查询条件的生成
     * @param     qaawi:查询条件
     * @return nothing
     */
    private void getAccountInfoSQL(QueryAccountAmountWhereInfo qaawi) {
        // SQL的生成
        getInfoSQL(qaawi, (int) 1);

        logger.debug("select " +m_sbSelect.toString() + 
            " from "+ m_sbFrom.toString() + 
            " where "+m_sbWhere.toString() + m_sbOrderBy.toString());
    }

    /**
     * 账户金额动户查询条件的生成
     * @param     qaawi:查询条件
     * @return nothing
     */
    private void getActiveAccountInfoSQL(QueryAccountAmountWhereInfo qaawi) {
        // SQL的生成
        getInfoSQL(qaawi, (int) 2);

        logger.debug("select " +m_sbSelect.toString() + 
            " from "+ m_sbFrom.toString() + 
            " where "+m_sbWhere.toString() + m_sbOrderBy.toString());
    }

    /**
     * 取存款合计、贷款合计
     * @param     qaawi
     * @return QueryAccountSumInfo
     */
    public QueryAccountSumInfo getDepositLoanSum(
        QueryAccountAmountWhereInfo qaawi) throws SQLException {
        QueryAccountSumInfo qasi = new QueryAccountSumInfo();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String strSQL = "";
        String strSelect = "SELECT SUM(ROUND(NVL(aaa.mStartBalance,0.0)+"+
			"NVL(aaa.mRecAmount,0.0)-NVL(aaa.mPayAmount, 0.0),2)) balance \n";
        String strDepositWhere = "AND bbb.nAccountTypeID IN (" +
            getDepositAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID()) + ") \n";
        
		//Modify by leiyang 2008/05/01
		//账户金额查询 存款合计 不统计委托存款账户余额(国电专用)
        strDepositWhere += " and bbb.nAccountTypeID not in (select id from sett_accounttype where nAccountGroupID = "
        	+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="
        	+ qaawi.getOfficeID() +" and currencyId="+ qaawi.getCurrencyID() 
        	+" and saccounttypecode = "+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+")";
		
        
        String strTempInSQL = getLoanAccountType(qaawi.getCurrencyID(),qaawi.getOfficeID());
        String strLoanWhere = "";
        if(strTempInSQL != null && !"".equals(strTempInSQL) && strTempInSQL.trim().length()>0)
        	strLoanWhere= "AND bbb.nAccountTypeID IN(" + strTempInSQL + ") \n";
        try {
            // 获取SQL字句
            if (qaawi.getIsCheckedActive() > 0) {
                getActiveAccountInfoSQL(qaawi);
            } else {
                getAccountInfoSQL(qaawi);
            }
            conn = this.getConnection();
            strSQL = strSelect + " FROM " + m_sbFrom.toString() + " WHERE " +
                m_sbWhere.toString() + strDepositWhere;
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 存款合计：活期+定期
                qasi.setDepositBalanceSum(rs.getDouble("balance"));
            }
            cleanup(rs);
            cleanup(ps);
            strSQL = strSelect + " FROM " + m_sbFrom.toString() + " WHERE " +
                m_sbWhere.toString() + strLoanWhere;
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 贷款合计：信托+委托+贴现
                qasi.setLoanBalanceSum(rs.getDouble("balance"));
            }
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        } catch (SQLException exp) {
            throw exp;
        } finally {
            cleanup(rs);
            cleanup(ps);
            cleanup(conn);
        }

        return qasi;
    }

    /**
     * 账户金额动户、账户金额明细查询
     * @param qaawi
     * @return nothing
     * @throws Exception
     */
    public PageLoader queryAccountAmountDetailInfo(QueryAccountAmountWhereInfo qaawi)
        throws Exception {
        // 获取SQL字句
       	getAccountDetailInfoSQL(qaawi);
        // 获取PageLoader对象
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
            getBaseObject("com.iss.system.dao.PageLoader");

        // 初始化PageLoader对象、实现查询和分页
        pageLoader.initPageLoader(
            new AppContext(),
            m_sbFrom.toString(),
            m_sbSelect.toString(),
            m_sbWhere.toString(),
            (int) Constant.PageControl.CODE_PAGELINECOUNT,
            "com.iss.itreasury.settlement.query.resultinfo.QueryAccountAmountDetailInfo",
            null);
        pageLoader.setOrderBy(" ORDER BY a.dtExecute");
        return pageLoader;
    }

    /**
     * 账户金额动户查询、账户金额查询
     * @param qaawi
     * @return nothing
     * @throws Exception
     */
    public PageLoader queryAccountAmountInfo(QueryAccountAmountWhereInfo qaawi)
        throws Exception {
        // 获取SQL字句
        if (qaawi.getIsCheckedActive() > 0) {
            getActiveAccountInfoSQL(qaawi);
        } else {
            getAccountInfoSQL(qaawi);
        }
        // 获取PageLoader对象
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
            getBaseObject("com.iss.system.dao.PageLoader");

        // 初始化PageLoader对象、实现查询和分页
        pageLoader.initPageLoader(
            new AppContext(),
            m_sbFrom.toString(),
            m_sbSelect.toString(),
            m_sbWhere.toString(),
            (int) Constant.PageControl.CODE_PAGELINECOUNT,
            "com.iss.itreasury.settlement.query.resultinfo.QueryAccountAmountInfo",
            null);
        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
    }

    /**
     * Returns the orderBy.
     * @return StringBuffer
     */
    public StringBuffer getOrderBy() {
        return m_sbOrderBy;
    }

    /**
     * Sets the orderBy.
     * @param orderBy The orderBy to set
     */
    public void setOrderBy(QueryAccountAmountWhereInfo qaawi) {
        // create orderby
        m_sbOrderBy = new StringBuffer();
        String sDesc = qaawi.getDesc() == 1 ? " desc " : "";
        switch ((int) qaawi.getOrderField()) {
            case OrderBy_AccountNo :
                m_sbOrderBy.append(" \n order by AccountNo" + sDesc);
                break;
            case OrderBy_Name :
                m_sbOrderBy.append(" \n order by Name" + sDesc);
                break;
            case OrderBy_AccountTypeID :
                m_sbOrderBy.append(" \n order by AccountTypeID" + sDesc);
                break;
            case OrderBy_StartBalance :
                m_sbOrderBy.append(" \n order by StartBalance" + sDesc);
                break;
            case OrderBy_PayAmount :
                m_sbOrderBy.append(" \n order by PayAmount" + sDesc);
                break;
            case OrderBy_RecAmount :
                m_sbOrderBy.append(" \n order by RecAmount" + sDesc);
                break;
            case OrderBy_EndBalance :
                m_sbOrderBy.append(" \n order by EndBalance" + sDesc);
                break;
            default :
                m_sbOrderBy.append(" \n order by AccountNo" + sDesc);
                break;
        }
    }
}
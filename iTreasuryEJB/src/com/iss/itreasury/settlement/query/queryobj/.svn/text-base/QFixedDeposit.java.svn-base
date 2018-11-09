/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryFixedDepositSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QFixedDeposit extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;	
	public final static int OrderBy_ClientName = 2;
	public final static int OrderBy_DepositNo = 3;
	public final static int OrderBy_Amount = 4;
	public final static int OrderBy_Status = 5;
	public final static int OrderBy_Interest = 6;
	
	//Add by ylguo(郭英亮) at 2008-12-29
	public final static int OrderBy_StartDate = 7;
	public final static int OrderBy_EndDate = 8;
	public final static int OrderBy_DepositTerm = 9;//期限
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QFixedDeposit()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getFixedDepositSQL(QueryFixedDepositInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		// select		
		m_sbSelect.append("acct.sAccountNo as AccountNo, \n");
		m_sbSelect.append("client.sname ClientName, \n");
		m_sbSelect.append("subAcct.AF_sDepositNo as DepositNo, \n");
		m_sbSelect.append("acct.id as AccountID, \n");
		m_sbSelect.append("subAcct.id as SubAccountID, \n");
		m_sbSelect.append("acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("subAcct.mOpenAmount as Amount, \n");
		m_sbSelect.append("round(nvl(subAcct.mBalance,0),2) as Balance, \n");
		m_sbSelect.append("subAcct.nStatusID as subAccountStatusID,  \n");		
		m_sbSelect.append("subAcct.AF_mRate as Rate,  \n");
		m_sbSelect.append("subAcct.AF_dtStart as StartDate,  \n");
		m_sbSelect.append("subAcct.AF_dtEnd as EndDate,  \n");
		//m_sbSelect.append("acctDetail.nTransactionTypeID as TransactionTypeID,  \n");
		//m_sbSelect.append("acctDetail.sTransNo as TransNo,  \n");				
		m_sbSelect.append("subAcct.mInterest as Interest, \n");
		m_sbSelect.append("subAcct.af_ndepositterm as depositterm, \n");
		m_sbSelect.append("subAcct.Af_Nnoticeday as NoticeDay, \n");
		m_sbSelect.append("subAcct.Af_sDepositBillNo as DepositBillNo, \n");	//换开定期存单号 2006.3.29 feiye add
		
		//Boxu Update 2008年3月10日 定期和通知计提利息存放AF_mPreDrawInterest,保证金存放Al_Mpredrawinterest
		if(qInfo.getIsMarginDeposit() > 0)
		{
			m_sbSelect.append("subAcct.Al_Mpredrawinterest as PreDrawInterest \n");
		}
		else
		{
			m_sbSelect.append("subAcct.AF_mPreDrawInterest as PreDrawInterest \n");
		}

		// from 
		m_sbFrom = new StringBuffer();
		//m_sbFrom.append("sett_account acct, sett_subAccount subAcct, sett_TransAccountDetail acctDetail, client \n");
		m_sbFrom.append("sett_account acct, sett_subAccount subAcct, client,sett_accountType acctType \n");
		
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
		//m_sbWhere.append("and subAcct.id=acctDetail.nSubAccountID
		
		/*
		if(qInfo.getIsFixedDeposit()>0)
		{
			if(qInfo.getIsNoticeDeposit()>0)
			{		
				//m_sbWhere.append("and acctDetail.nTransactionTypeID in (12,14,15)  \n");
				m_sbWhere.append("and acct.nAccountTypeID in ("+ getFixAccountType(qInfo.getCurrencyID())+","+ this.getNotifyAccountType(qInfo.getCurrencyID()) +")  \n");
			}
			else
			{
				//m_sbWhere.append("and acctDetail.nTransactionTypeID in (12,14)  \n");
				m_sbWhere.append("and acct.nAccountTypeID in ( "+ getFixAccountType(qInfo.getCurrencyID()) +")  \n");
				
			}
		}else{
			if(qInfo.getIsNoticeDeposit()>0)
			{			
				//m_sbWhere.append("and acctDetail.nTransactionTypeID = 15  \n");
				m_sbWhere.append("and acct.nAccountTypeID in (  "+ getNotifyAccountType(qInfo.getCurrencyID()) +")  \n");
			}
			else
			{
				//m_sbWhere.append("and acctDetail.nTransactionTypeID in (12,14,15)  \n");
			    m_sbWhere.append("and acct.nAccountTypeID in ("+ getFixAccountType(qInfo.getCurrencyID())+","+ this.getNotifyAccountType(qInfo.getCurrencyID()) +")  \n");
			}
		}
		*/
		if (qInfo.getClientManager() > 0)
			m_sbWhere.append(" and client.nCustomerManagerUserId = " + qInfo.getClientManager() +"  \n");
		// 定期存款查询
		String strFixedTmp ="-1";
		if(qInfo.getIsFixedDeposit() > 0)
		{
			strFixedTmp = getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
		}
		// 通知存款查询
		String strNoticeTmp ="-1";
		if(qInfo.getIsNoticeDeposit() > 0)
		{
			strNoticeTmp = getNotifyAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
		}
		//保证金存款查询
		String strMarginTmp ="-1";
		if(qInfo.getIsMarginDeposit() > 0){
			strMarginTmp = getMarginAccountType();
		}
		//组合
		if(strFixedTmp.equals("-1") && strNoticeTmp.equals("-1") && strMarginTmp.equals("-1")){
			m_sbWhere.append(" and acct.nAccountTypeID in (");
			String strtemp="";
			String str="";
			
			strtemp=getFixAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
			if(!strtemp.equals(""))
			{
				str += strtemp + ",";
			}
			strtemp=getNotifyAccountType(qInfo.getCurrencyID(),qInfo.getOfficeID());
			if(!strtemp.equals(""))
			{
				str += strtemp + ",";
			}
			strtemp=getMarginAccountType();
			if(!strtemp.equals(""))
			{
				str += strtemp + ",";
			}
			
			m_sbWhere.append(str.substring(0, str.length()-1)+")") ;
		}else{
			m_sbWhere.append(" and acct.nAccountTypeID in ("
					+ strFixedTmp +"," 
					+ strNoticeTmp + "," 
					+ strMarginTmp 
					+")  \n");
		}
		//m_sbWhere.append("and acctDetail.nTransAccountID<>1  \n");		
		//m_sbWhere.append("and acctDetail.nStatusID=3  \n");		
		m_sbWhere.append("and acct.nofficeid="+ qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and acct.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append("and subAcct.nStatusID <> 0 \n");
		// deleted by fxzhang 2006-12-29  可以查已结清状态
		//m_sbWhere.append("and subAcct.nStatusID <> 2 \n");
		m_sbWhere.append("and acct.nAccountTypeID=acctType.id \n");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and client.scode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo() + "'");
		/*if (qInfo.getClientNameFrom() != null && qInfo.getClientNameFrom().length() > 0)
			m_sbWhere.append("and client.sName>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNameTo() != null && qInfo.getClientNameTo().length() > 0)
			m_sbWhere.append("and client.sName<='" + qInfo.getClientNameTo() + "'");*/
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and acct.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and acct.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		//add by 2012-05-18 添加指定账户编号
		if(qInfo.getAppointAccountNo() != null && qInfo.getAppointAccountNo().length() > 0){
			m_sbWhere.append(" and acct.sAccountNo in ('"+qInfo.getAppointAccountNo()+"')");
		}
		
		if (qInfo.getEnterpriseTypeID1()>0)
		    m_sbWhere.append("and client.nClienttypeID1 = " + qInfo.getEnterpriseTypeID1() + " \n");
		if (qInfo.getEnterpriseTypeID2()>0)
		    m_sbWhere.append("and client.nClienttypeID2 = " + qInfo.getEnterpriseTypeID2() + " \n");
		if (qInfo.getEnterpriseTypeID3()>0)
		    m_sbWhere.append("and client.nClienttypeID3 = " + qInfo.getEnterpriseTypeID3() + " \n");
		if (qInfo.getEnterpriseTypeID4()>0)
		    m_sbWhere.append("and client.nClienttypeID4 = " + qInfo.getEnterpriseTypeID4() + " \n");
		if (qInfo.getEnterpriseTypeID5()>0)
		    m_sbWhere.append("and client.nClienttypeID5 = " + qInfo.getEnterpriseTypeID5() + " \n");
		if (qInfo.getEnterpriseTypeID6()>0)
		    m_sbWhere.append("and client.nClienttypeID6 = " + qInfo.getEnterpriseTypeID6() + " \n");
		
		if (qInfo.getClientSort()>0)
		    m_sbWhere.append("and client.nSettClientTypeID=" + qInfo.getClientSort() + " \n");
		if (qInfo.getClientType()>0)
			m_sbWhere.append("and client.NCORPNATUREID=" + qInfo.getClientType() + " \n");
		if (qInfo.getAccountType()>0)
			m_sbWhere.append("and acctType.id=" + qInfo.getAccountType() + " \n");
		if (qInfo.getParentCompany1()>0)
			m_sbWhere.append("and client.nParentCorpID1=" + qInfo.getParentCompany1() + " \n");
		if (qInfo.getParentCompany2()>0)
			m_sbWhere.append("and client.nParentCorpID2=" + qInfo.getParentCompany2() + " \n");	
		if(qInfo.getIsFixedDeposit()>0)		//针对定期来说
		{
			//***************************************(where条件 add begin)*******************************
			if(qInfo.getDepositNoChoose()==0){
				//查所有的   用原来的SQL判断条件即可
			}	
			if(qInfo.getDepositNoChoose()==1){
				//查开立的数据
				m_sbWhere.append("  and ( subAcct.Af_sDepositBillNo is null or subAcct.Af_sDepositBillNo='')  \n");
			}	
			if(qInfo.getDepositNoChoose()==2){
				//查换开的数据      在换开定期存单复核操作时对此字段进行了插值操作
				m_sbWhere.append("  and subAcct.Af_sDepositBillNo is not null  \n");
			}
			//**********************************************************************
			
			if (qInfo.getFixedDepositNoFrom() != null && qInfo.getFixedDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='" + qInfo.getFixedDepositNoFrom() + "'");
			if (qInfo.getFixedDepositNoTo() != null && qInfo.getFixedDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='" + qInfo.getFixedDepositNoTo() + "'");
			if (qInfo.getFixedStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"+DataFormat.formatDate(qInfo.getFixedStartDateFrom())+"','yyyy-mm-dd') ");
			if (qInfo.getFixedStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"+DataFormat.formatDate(qInfo.getFixedStartDateTo())+"','yyyy-mm-dd') ");	
			if (qInfo.getFixedEndDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"+DataFormat.formatDate(qInfo.getFixedEndDateFrom())+"','yyyy-mm-dd') ");
			if (qInfo.getFixedEndDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd<=to_date('"+DataFormat.formatDate(qInfo.getFixedEndDateTo())+"','yyyy-mm-dd') ");
			if (qInfo.getFixedDepositStatus() >0)
				m_sbWhere.append("and subAcct.nStatusID=" + qInfo.getFixedDepositStatus() + " \n");
			if (qInfo.getFixedDepositTermFrom() >0)
				m_sbWhere.append("and subAcct.AF_nDepositTerm>=" + qInfo.getFixedDepositTermFrom() + " \n");
			if (qInfo.getFixedDepositTermTo() >0)
				m_sbWhere.append("and subAcct.AF_nDepositTerm<=" + qInfo.getFixedDepositTermTo() + " \n");
			if (qInfo.getFixedAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>=" + qInfo.getFixedAmountFrom() + " \n");
			if (qInfo.getFixedAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<=" + qInfo.getFixedAmountTo() + " \n");
			if (qInfo.getFixedRate() >0)
				m_sbWhere.append("and subAcct.AF_mRate=" + qInfo.getFixedRate() + " \n");
			if (qInfo.getFixedEndDate() != null)
			{		
				//处理截止日期		
				if(qInfo.getFixedEndDate().after(Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID())))
				{
					m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"+DataFormat.formatDate(qInfo.getFixedEndDate())+"','yyyy-mm-dd') ");	
				}
				else
				{
					m_sbWhere.append("and (subAcct.dtFinish>=to_date('"+DataFormat.formatDate(qInfo.getFixedEndDate())+"','yyyy-mm-dd') ");
					m_sbWhere.append("or subAcct.dtFinish is null ) ");
				}
			}
		}
		//查询通知存款
		if(qInfo.getIsNoticeDeposit()>0)
		{
			if (qInfo.getNoticeDepositNoFrom() != null && qInfo.getNoticeDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='" + qInfo.getNoticeDepositNoFrom() + "'");
			if (qInfo.getNoticeDepositNoTo() != null && qInfo.getNoticeDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='" + qInfo.getNoticeDepositNoTo() + "'");
			if (qInfo.getNoticeStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"+DataFormat.formatDate(qInfo.getNoticeStartDateFrom())+"','yyyy-mm-dd') ");
			if (qInfo.getNoticeStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"+DataFormat.formatDate(qInfo.getNoticeStartDateTo())+"','yyyy-mm-dd') ");			
			if (qInfo.getNoticeDepositStatus() >0)
				m_sbWhere.append("and subAcct.nStatusID=" + qInfo.getNoticeDepositStatus() + " \n");			
			if (qInfo.getNoticeBalanceFrom() != 0.0)
				m_sbWhere.append("and subAcct.mBalance>=" + qInfo.getNoticeBalanceFrom() + " \n");
			if (qInfo.getNoticeBalanceTo() != 0.0)
				m_sbWhere.append("and subAcct.mBalance<=" + qInfo.getNoticeBalanceTo() + " \n");
			if (qInfo.getNoticeDrawAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>=" + qInfo.getNoticeDrawAmountFrom() + " \n");
			if (qInfo.getNoticeDrawAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<=" + qInfo.getNoticeDrawAmountTo() + " \n");
			if(qInfo.getNoticeDays()>0)
				m_sbWhere.append("and subAcct.AF_nNoticeDay=" + qInfo.getNoticeDays() + " \n");				
			if (qInfo.getNoticeEndDate() != null)
			{		
				//处理截止日期		
				if(qInfo.getNoticeEndDate().after(Env.getSystemDate()))
				{
					//m_sbWhere.append("and subAcct.AF_dtEnd<=to_date('"+DataFormat.formatDate(qInfo.getNoticeEndDate())+"','yyyy-mm-dd') ");	
				    m_sbWhere.append("and decode(acctType.nAccountGroupID," + SETTConstant.AccountGroupType.NOTIFY + ",to_date('1000-01-01','yyyy-mm-dd'),subAcct.AF_dtEnd) <= to_date('"+DataFormat.formatDate(qInfo.getNoticeEndDate())+"','yyyy-mm-dd') ");
				}
				else
				{
					m_sbWhere.append("and (subAcct.dtFinish<=to_date('"+DataFormat.formatDate(qInfo.getNoticeEndDate())+"','yyyy-mm-dd') ");
					m_sbWhere.append("or subAcct.dtFinish is null ) ");
				}
			}				
		}
		
		//针对保证金存款查询
		if(qInfo.getIsMarginDeposit()>0)
		{
			//**********************************************************************
			
			if (qInfo.getMarginDepositNoFrom() != null && qInfo.getMarginDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='" + qInfo.getMarginDepositNoFrom() + "'");
			if (qInfo.getMarginDepositNoTo() != null && qInfo.getMarginDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='" + qInfo.getMarginDepositNoTo() + "'");
			if (qInfo.getMarginStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"+DataFormat.formatDate(qInfo.getMarginStartDateFrom())+"','yyyy-mm-dd') ");
			if (qInfo.getMarginStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"+DataFormat.formatDate(qInfo.getMarginStartDateTo())+"','yyyy-mm-dd') ");	
			if (qInfo.getMarginEndDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"+DataFormat.formatDate(qInfo.getMarginEndDateFrom())+"','yyyy-mm-dd') ");
			if (qInfo.getMarginEndDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd<=to_date('"+DataFormat.formatDate(qInfo.getMarginEndDateTo())+"','yyyy-mm-dd') ");
			if (qInfo.getMarginDepositStatus() >0)
				m_sbWhere.append("and subAcct.nStatusID=" + qInfo.getMarginDepositStatus() + " \n");
			if (qInfo.getMarginAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>=" + qInfo.getMarginAmountFrom() + " \n");
			if (qInfo.getMarginAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<=" + qInfo.getMarginAmountTo() + " \n");
			if (qInfo.getMarginRate() >0)
				m_sbWhere.append("and subAcct.AF_mRate=" + qInfo.getMarginRate() + " \n");
			if (qInfo.getMarginEndDate() != null)
			{		
				//处理截止日期		
				if(qInfo.getMarginEndDate().after(Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID())))
				{
					m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"+DataFormat.formatDate(qInfo.getMarginEndDate())+"','yyyy-mm-dd') ");	
				}
				else
				{
					m_sbWhere.append("and (subAcct.dtFinish>=to_date('"+DataFormat.formatDate(qInfo.getMarginEndDate())+"','yyyy-mm-dd') ");
					m_sbWhere.append("or subAcct.dtFinish is null ) ");
				}
			}				
		}
		//2007-7-25  尧忠贻 去掉关于子帐户结束日期必须大于等于开机日期的条件 
		//String tsToday = DataFormat.getDateString(Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID()));
		//m_sbWhere.append(" AND DECODE(SUBACCT.DTFINISH,NULL,TO_DATE('3000-01-01', 'yyyy-mm-dd'),SUBACCT.DTFINISH) ");
		//m_sbWhere.append(" >= ");
		//m_sbWhere.append(" DECODE('" + tsToday + "',NULL,TO_DATE('1000-01-01', 'yyyy-mm-dd'),TO_DATE('"+tsToday+"', 'yyyy-mm-dd') ");
		//m_sbWhere.append(" )");
		
		if (qInfo.getIsLeaching() > 0)
			m_sbWhere.append(" and subAcct.mBalance<> 0 ");
		//
		
//		上海电气新增扩展属性条件
		if (qInfo.getExtendAttribute1()!=-1 && qInfo.getExtendAttribute1()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = " + qInfo.getExtendAttribute1() + " \n");
		}
		if (qInfo.getExtendAttribute2()!=-1 && qInfo.getExtendAttribute2()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = " + qInfo.getExtendAttribute2() + " \n");
		}
		if (qInfo.getExtendAttribute3()!=-1 && qInfo.getExtendAttribute3()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = " + qInfo.getExtendAttribute3() + " \n");
		}
		if (qInfo.getExtendAttribute4()!=-1 && qInfo.getExtendAttribute4()!=0)
		{
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = " + qInfo.getExtendAttribute4() + " \n");
		}
		//modified by ygzhao 04-12-10 start		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by ");		
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n AccountNo " + strDesc +",");
				break;			
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n ClientName " + strDesc+"," );
				break;
			case OrderBy_DepositNo :
				m_sbOrderBy.append(" \n DepositNo " + strDesc+"," );
				break;
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n Amount " + strDesc+"," );
				break;
			case OrderBy_Status :
				m_sbOrderBy.append(" \n subAccountStatusID " + strDesc+"," );
				break;
			case OrderBy_Interest :
				m_sbOrderBy.append(" \n PreDrawInterest " + strDesc+"," );
				break;		
			//Added by ylguo(郭英亮) at 2008-12-29 以下添加了3个排序字段
			case OrderBy_StartDate :
				m_sbOrderBy.append(" \n StartDate " + strDesc+"," );
				break;
			case OrderBy_EndDate :
				m_sbOrderBy.append(" \n EndDate " + strDesc+"," );
				break;
			case OrderBy_DepositTerm :
				m_sbOrderBy.append(" \n depositterm " + strDesc+"," );
				break;	
		}		
		//Modified by ylguo(郭英亮) at 2008-12-29 新增了2个开始日期和结束日期的排序
		//m_sbOrderBy.append(" AccountTypeID,AccountID,depositterm,NoticeDay ");
		m_sbOrderBy.append(" AccountTypeID,AccountID,NoticeDay");
		
		logger.debug("in QFixedDeposit--getFixedDepositSQL()=" + m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
		//modified by ygzhao 04-12-10 end
	}
	public QueryFixedDepositSumInfo queryFixedDepositSum(QueryFixedDepositInfo qInfo) throws Exception
	{
		QueryFixedDepositSumInfo sumObj = new QueryFixedDepositSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		//

		try
		{
			getFixedDepositSQL(qInfo);
			// select 
			strSelect = " select sum(round(subAcct.mOpenAmount,2)) Amount,sum(round(subAcct.mBalance,2)) balance,sum(round(subAcct.AF_mPreDrawInterest,2)) Interest \n";			

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			//logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setDepositAmountSum(rs.getDouble("Amount"));
				sumObj.setDepositBalanceSum(rs.getDouble("balance"));
				sumObj.setDepositInterestSum(rs.getDouble("Interest"));
			}
			cleanup(rs);
			cleanup(ps);			
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryFixedDepositInfo(QueryFixedDepositInfo qInfo) throws Exception
	{

		getFixedDepositSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
            new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransFixedResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		log.print("JAVA-Select：" + m_sbSelect.toString());
		log.print("JAVA-From：" + m_sbFrom.toString());
		log.print("JAVA-Where：" + m_sbWhere.toString());
		log.print("JAVA-Order By：" + m_sbOrderBy.toString());
		return pageLoader;
	}
	/**
	 * @return
	 */
	public StringBuffer getOrderBy()
	{
		return m_sbOrderBy;
	}

}
package com.iss.itreasury.settlement.query.queryobj;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.query.paraminfo.QueryStockCompanyStatisticsInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryStockCompanyStatisticsResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryStockCompanyStatisticsSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
/**
 * @author weilu 股份公司资产负债(与峰值)分析 To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Generation - Code
 *         and Comments
 */
public class QStockCompanyAssetsStatistics extends BaseQueryObject
{

	public QStockCompanyAssetsStatistics()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 得到满足条件的子账户的SQL语句（核心），无论是怎么分析，无非就是取出满足条件的子账户
	 * 
	 * @param info
	 * @return
	 */
	private String getSubAccountIDSQLstr(QueryStockCompanyStatisticsInfo info)
	{
		log.print("取无合同账户类型下的子账户");
		sSubAccountIDSQL = "select a.id as subID,c.projectID,c.contractType,c.accountType,c.relateClientType,f.id as clientID,f.sname,f.scode,f.nCorpNatureID as clientType \n"
				+ " from sett_subaccount a, \n"
				+ " sett_account b , \n"
				+ "	(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING where statusid=1 and projectType="
				+ SETTConstant.StockProjectType.ASSETHOCK;
		sSubAccountIDSQL += " and accountType != -1 and contractType != 13 \n";//非担保类型		
		if (info.getProjectID() > 0)
			sSubAccountIDSQL += " and projectID = " + info.getProjectID();
		if (info.getAccountType() > 0)
			sSubAccountIDSQL += " and accountType = " + info.getAccountType();
		if (info.getContractType() > 0)
			sSubAccountIDSQL += " and contractType = " + info.getContractType();
		if (info.getRelateClientType() > 0)
			sSubAccountIDSQL += " and relateClientType = " + info.getRelateClientType();
		
		sSubAccountIDSQL += ") c,\n" + " client f \n"
				+ " where a.naccountID=b.id \n"
				+ " and b.naccountTypeID = c.accountType \n"
				+ " and b.naccountTypeID not IN (8,9,10) \n"
				+ " and b.NCLIENTID = f.id \n";
		if (info.getClientType() > 0)
			sSubAccountIDSQL += " and f.nCorpNatureID = " + info.getClientType() + " \n";
		if (info.getClientID() > 0)
			sSubAccountIDSQL += " and f.id = " + info.getClientID() + " \n";

		sSubAccountIDSQL += "union \n";
		
		log.print("取有合同账户类型下的子账户");
		sSubAccountIDSQL += "select a.id as subID,c.projectID,c.contractType,c.accountType,c.relateClientType,f.id as clientID,f.sname,f.scode,f.nCorpNatureID as clientType \n"
				+ " 		from sett_subaccount a, \n"
				+ "		sett_account b , \n"
				+ "		(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING where statusid=1 and projectType="
				+ SETTConstant.StockProjectType.ASSETHOCK;
		sSubAccountIDSQL += " and accountType != -1 and contractType != 13 \n";//非担保类型	
		if (info.getProjectID() > 0)
			sSubAccountIDSQL += " and projectID = " + info.getProjectID();
		sSubAccountIDSQL += ") c,\n"
				+ "		(select '' || id as id,ncontractID from loan_payform union select '0' || id as id,ncontractID from LOAN_DISCOUNTCREDENCE) d, \n"
				+ " 		loan_contractform e, \n"
				+ " 		client f \n"
				+ " 		where a.naccountID=b.id \n"
				+ " 		and b.naccountTypeID IN (8,9,10) \n"
				+ " 		and b.naccountTypeID = c.accountType \n"
				+ " 		and e.ntypeid = decode(sign(c.contractType),1,c.contractType,e.ntypeid) \n"
				+ " 		and decode(c.relateClientType,1,a.AL_NLOANNOTEID,2,a.AL_NLOANNOTEID,3,a.AL_NLOANNOTEID,4,'0'||a.AL_NLOANNOTEID) = d.id \n"
				+ " 		and e.id = d.nContractID \n"
				+ " 		and decode(c.relateClientType,1,b.NCLIENTID,2,e.NCONSIGNCLIENTID,3,e.SELLCLIENTID,4,e.DISCOUNTCLIENTID) = f.id \n";
		if (info.getClientType() > 0)
			sSubAccountIDSQL += " and f.nCorpNatureID = "
					+ info.getClientType() + " \n";
		if (info.getClientID() > 0)
			sSubAccountIDSQL += " and f.id = " + info.getClientID() + " \n";
		return sSubAccountIDSQL;
	}
	/**
	 * 取出豁免额度表下的有效额度(如果某项目下设置了两个豁免额度,取已经生效的最近的一个)
	 * @param ts 生效日期
	 * @param clientSettingFlag 1:按客户设置豁免额度  2 按客户类型设置
	 * @return
	 */
	private String getSQLReleaseAmountLimitSettingStr(Timestamp ts,long clientSettingFlag)
	{
		String SQLReleaseAmountLimitSettingStr = "";
		SQLReleaseAmountLimitSettingStr += "select a.* from sett_ReleaseAmountLimitSetting a, \n"
				+ " ( \n"
				+ " select projectid,clientid,clienttype,max(efficientdate) as efficientdate from sett_ReleaseAmountLimitSetting \n"
				+ " where statusid=1 \n"
				+ " and to_char(efficientdate,'yyyy-mm-dd')<=to_char('"
				+ DataFormat.formatDate(ts)
				+ "') \n"
				+ " group by projectid,clientid,clienttype \n"
				+ ") b \n"
				+ " where a.projectid=b.projectid \n"
				+ " and nvl(a.clientid,0) = nvl(b.clientid,0) \n"
				+ " and nvl(a.clienttype,0) = nvl(b.clienttype,0) \n"
				+ " and a.statusid=1 \n"
				+ " and a.clientSettingFlag = "+ clientSettingFlag +" \n"
				+ " and to_char(a.efficientdate,'yyyy-mm-dd')=to_char(b.efficientdate,'yyyy-mm-dd') \n";
		return SQLReleaseAmountLimitSettingStr;
	}
	/**
	 * 根据所选择条件查询
	 * 
	 * @param info
	 * @return
	 */
	public Collection findByCondition(QueryStockCompanyStatisticsInfo info)
			throws Exception
	{
		ArrayList list = new ArrayList();
		transConn = this.getConnection();
		//得到满足条件的子账户的SQL语句
		getSubAccountIDSQLstr(info);
		if (info.getStatisticsType() == 1)
		{
			if (isToday(info.getOfficeID(), info.getCurrencyID(), info
					.getDateFrom()))
			{
				log.print("查询当天");
				SQLstr = "select eee.clientType,eee.projectID,ccc.balance,ccc.clientID,ccc.scode,ccc.sname,ccc.accountType,ccc.contractType,ccc.relateClientType,ccc.avgBalance,ccc.maxBalance,ccc.maxBalanceDate,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ " (select a.id clientType,projectID from SETT_ENTERPRICETYPE a,SETT_STOCKPROJECTSETTING b where a.nstatusid=1 and b.statusid=1 and b.projectType=1";
				if (info.getProjectID() > 0)
				{
					SQLstr += " and b.projectID=" + info.getProjectID();
				}
				if (info.getClientType() > 0)
				{
					SQLstr += " and a.id=" + info.getClientType();
				}
				SQLstr += " group by a.id,projectID) eee, \n"
						+ "( \n"
						+ "	select \n"
						+ "	clientType, \n"
						+ "	projectID, \n"
						+ "	0 as clientID, \n" //客户ID()(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	0 as accountType, \n" //账户类型
						+ "	0 as contractType, \n" //合同类型
						+ "	0 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"
						+ "	from \n"
						+ "	( \n"
						+ "		select bbb.*,aaa.MBALANCE \n"
						+ "		 from sett_subaccount aaa, \n"
						+ "		( \n"
						+ sSubAccountIDSQL
						+ "		) bbb \n"
						+ "	where aaa.ID(+) = bbb.subID \n"
						//start 为计算担保余额,新增以下 2005-03-09
						+ " UNION ALL \n"
						+ " select -1 as subID,c.projectID, -1 contractType, -1 accountType,-1 relateClientType, \n"
						+ " 		borrowClientID as clientID, '' sname,'' scode, client.nCorpNatureID as clientType, \n"
						+ " 		sum( decode(transactionType,126,amount,127,-amount) ) as MBALANCE \n"
						+ " from sett_offBalance,client, \n"
						+ " 	(select projectID from SETT_STOCKPROJECTSETTING where statusid=1 and projectType=1 \n"
						+ "  	 and accountType = -1 and contractType = 13 \n"; //担保
				if(info.getProjectID() > 0)
				{
				    SQLstr += "  and projectID = " + info.getProjectID() + " \n";
				}
				SQLstr += "  	 ) c\n"
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				{
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				}
				SQLstr += " group by borrowClientID,c.projectID,client.nCorpNatureID \n"						
						//end
						+ "	) \n"
						+ "	group by clientType,projectID \n"
						+ ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
						+ ") ddd \n"
						+ " where eee.projectID = ddd.projectID(+) and eee.clientType = ddd.clientType(+) \n"
						+ " and eee.projectID = ccc.projectID(+) and eee.clientType = ccc.clientType(+) \n"
						+ " order by eee.projectID,eee.clientType";
			} else
			{
				log.print("查询历史");
				SQLstr = "select eee.clientType,eee.projectID,ccc.balance,ccc.clientID,ccc.scode,ccc.sname,ccc.accountType,ccc.contractType,ccc.relateClientType,ccc.avgBalance,ccc.maxBalance,ccc.maxBalanceDate,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ " (select a.id clientType,projectID from SETT_ENTERPRICETYPE a,SETT_STOCKPROJECTSETTING b where a.nstatusid=1 and b.statusid=1 and b.projectType=1";
				if (info.getProjectID() > 0)
				{
					SQLstr += " and b.projectID=" + info.getProjectID();
				}
				if (info.getClientType() > 0)
				{
					SQLstr += " and a.id=" + info.getClientType();
				}
				SQLstr += " group by a.id,projectID) eee, \n"
						+ "( \n"
						+ "	select \n"
						+ "	clientType, \n"
						+ "	projectID, \n"
						+ "	0 as clientID, \n" //客户ID()(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	0 as accountType, \n" //账户类型
						+ "	0 as contractType, \n" //合同类型
						+ "	0 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"
						+ "	from \n"
						+ "	( \n"
						+ "		select aaaa.id as clientType,bbbb.* from  SETT_ENTERPRICETYPE aaaa, \n"
						+ "		( \n"
						+ "			select bbb.clientType as clientTypeID,bbb.subID,bbb.projectID,bbb.contractType,bbb.accountType,bbb.relateClientType,bbb.clientID,bbb.sname,bbb.scode,aaa.MBALANCE \n"
						+ "			from sett_DailyAccountBalance aaa, \n" + "			("
						+ sSubAccountIDSQL + "			) bbb \n"
						+ "			where aaa.nSubAccountID(+) = bbb.subID \n"
						+ "			and to_char(aaa.dtdate,'yyyy-mm-dd') = to_char('"
						+ DataFormat.formatDate(info.getDateFrom()) + "')"
						+ "		)bbbb" + "		where aaaa.id=bbbb.clientTypeID";
				if (info.getClientType() <= 0)
				{
					SQLstr += "(+) \n";
				}
				SQLstr += "		and aaaa.nstatusid=1"
						+ "	) \n"
						+ "	group by clientType,projectID \n"
						//start 为计算担保余额,新增以下 2005-03-09
						+ " UNION ALL \n"
						+ "	select \n"
						+ "	client.nCorpNatureID as clientType, \n"
						+ "	projectID, \n"
						+ "	0 as clientID, \n" //客户ID()(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	0 as accountType, \n" //账户类型
						+ "	0 as contractType, \n" //合同类型
						+ "	0 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance,client, \n"
						+ " 	(select projectID from SETT_STOCKPROJECTSETTING where statusid=1 and projectType=1 \n"
						+ "  	 and accountType = -1 and contractType = 13 \n"; //担保
				if(info.getProjectID() > 0)
				{
				    SQLstr += "  and projectID = " + info.getProjectID() + " \n";
				}
				SQLstr += "  	 ) c\n"
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				{
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";
				}
				SQLstr += " group by borrowClientID,c.projectID,client.nCorpNatureID \n"						
						//end
						+ ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
						+ ") ddd \n"
						+ " where eee.projectID = ddd.projectID(+) and eee.clientType = ddd.clientType(+) \n"
						+ " and eee.projectID = ccc.projectID(+) and eee.clientType = ccc.clientType(+) \n"
						+ " order by eee.projectID,eee.clientType";
			}
		} else if (info.getStatisticsType() == 2)
		{
			log.print("计算峰值");
			SQLstr = "select \n"
					+ " t3.clientType, \n"
					+ " t3.projectID, \n"
					+ " 0 as clientID, \n"
					+ " '' as scode, \n"
					+ " '' as sname, \n"
					+ " 0 as accountType, \n"
					+ " 0 as contractType, \n"
					+ " 0 as relateClientType, \n"
					+ " t4.mAvgBalance as avgBalance, \n"
					+ " t4.mMaxSumBalance as maxBalance, \n"
					+ " t4.dtdate as maxBalanceDate, \n"
					+ " t4.mMaxSumBalance as  balance, \n"
					+ " t5.releaseAmountLimit, \n"
					+ " t5.awokerate\n"
					+ " from \n"
					+ " (select a.id clientType,projectID from SETT_ENTERPRICETYPE a,SETT_STOCKPROJECTSETTING b where a.nstatusid=1 and b.statusid=1 and b.projectType=1";
			if (info.getProjectID() > 0)
			{
				SQLstr += " and b.projectID=" + info.getProjectID();
			}
			if (info.getClientType() > 0)
			{
				SQLstr += " and a.id=" + info.getClientType();
			}
			SQLstr += " group by a.id,projectID) t3, \n"
					+ " ( \n"
					+ " select t1.* ,t2.dtdate from \n"
					+ " ( \n"
					+ " 	select clientType,projectID,max(mSumBalance) mMaxSumBalance,avg(mSumBalance) mAvgBalance from \n"
					+ " 	( \n"
					//start1 增加了担保类型的处理 2005-03-11
					
					//end1
					+ "		select \n"
					+ "		clientType, \n"
					+ "		projectID, \n"
					+ "		sum(mBalance) mSumBalance, \n"
					+ "		0.0 as balance,"
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientType, \n"
					+ "		projectID, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					//start2 
					+ "     UNION ALL \n"
					+ "     select \n"
					+ "     	bb.projectID, \n"
					+ "     	bb.nCorpNatureID, \n"
					+ "     	(select SUM(DECODE(direction,1,amount,2,-amount)) mAmount \n"
					+ "     	 from SETT_OFFBALANCE aa,client \n"
					+ "     	 where transactionType IN (126,127) \n"
					+ "     	 	AND aa.borrowClientID=client.id \n"
					+ "      		AND NVL(executeDate,TO_DATE('20041231','YYYYMMDD')) <= bb.dtDate \n"
					+ "      		AND client.nCorpNatureID = bb.nCorpNatureID \n"
					+ "     		group by nCorpNatureID \n"
					+ "     	 ) mBalance, \n"
					+ "     	bb.dtDate \n"
					+ "     from \n"
					+ "     	(select c.projectID,d.nCorpNatureID,d.dtDate \n"
					+ "     	 from \n"
					+ "      		(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING \n"
					+ "      			where statusID = 1 and projectType = 1 \n"
					+ "      			and accountType = -1 and contractType = 13  \n"; //担保
			if (info.getProjectID() > 0)
			{
				SQLstr += "      			and projectID=" + info.getProjectID() + " \n";
			}
			SQLstr += "			 ) c, \n"		
					
					+ "      	(select * from \n"
					+ "      		(select DISTINCT client.nCorpNatureID from SETT_OFFBALANCE,client where borrowClientID=client.id and transactionType in (126,127)) a, \n"
					+ "      		(select DISTINCT dtDate from sett_dailyAccountBalance \n"
					+ "      				where dtDate>=to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      				and   dtDate<=to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n"
					+ "				 ) b \n"
					+ "      	 order by a.nCorpNatureID,b.dtDate \n"
					+ "      	) d \n"
					+ "      	order by c.projectID,d.nCorpNatureID,d.dtDate \n"
					+ "      	) bb \n"
					+ "      where 1 = 1 \n";
			if (info.getProjectID() > 0)
			{
				SQLstr += " 	and projectID=" + info.getProjectID();
			}		
			if (info.getClientType() > 0)
			{
				SQLstr += " 	and nCorpNatureID = " + info.getClientType();
			}			
			SQLstr += "      	AND bb.dtDate >= to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      	AND bb.dtDate <= to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n" 
						
					//end2	2005-03-11				
					+ "	) \n"
					+ "	group by clientType,projectID,to_char(dtdate,'yyyy-mm-dd') \n"
					+ "	) group by clientType,projectID \n"
					+ " ) t1, \n"
					+ " ( \n"
					+ " select clientType,projectID,min(dtdate) dtdate,msumBalance from \n"
					+ " ( \n"
					+ " 	select \n"
					+ " 	clientType, \n"
					+ " 	projectID, \n"
					+ " 	sum(mBalance) mSumBalance, \n"
					+ " 	avg(mBalance) mAvgBalance, \n"
					+ " 	0.0 as balance, \n"
					+ " 	to_char(dtdate,'yyyy-mm-dd') dtdate \n"
					+ " 	from \n"
					+ " 	( \n"
					+ " 		select \n"
					+ " 		clientType, \n"
					+ " 		projectID, \n"
					+ " 		aaa.mBalance, \n"
					+ " 		aaa.dtdate \n"
					+ " 		from sett_DailyAccountBalance aaa, \n"
					+ " 		( \n"
					+ sSubAccountIDSQL
					+ "			) bbb \n"
					+ "			where  aaa.nsubAccountID=bbb.subID \n"
					+ "			and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "			and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "		) \n"
					+ "		group by clientType,projectID,to_char(dtdate,'yyyy-mm-dd') \n"
					+ " ) \n"
					+ " group by clientType,projectID,msumBalance \n"
					+ " ) t2 \n"
					+ " where t1.clientType = t2.clientType \n"
					+ " and t1.projectID =t2.projectID \n"
					+ " and t1.mMaxsumBalance = t2.msumBalance \n"
					+ " ) t4, \n"
					+ " ("
					+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
					+ ") t5 \n"
					+ " where t3.clientType = t4.clientType(+) and t3.projectID = t4.projectID(+) \n"
					+ " and t3.clientType = t5.clientType(+) and t3.projectID = t5.projectID(+)"
					+ " order by projectID,clientType";
		}
		log.print(SQLstr);
		try
		{
			transPS = transConn.prepareStatement(SQLstr);
			transRS = transPS.executeQuery();
			list = transRsToList(transRS);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 列出该客户类型下的每个客户在此项目下的余额或峰值
	 * 
	 * @param info
	 * @return @throws
	 *         Exception
	 */
	public Collection findByClientType(QueryStockCompanyStatisticsInfo info)
			throws Exception
	{
		ArrayList list = new ArrayList();
		transConn = this.getConnection();
		//得到满足条件的子账户的SQL语句
		getSubAccountIDSQLstr(info);
		if (info.getStatisticsType() == 1)
		{
			if (isToday(info.getOfficeID(), info.getCurrencyID(), info
					.getDateFrom()))
			{
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n"
						//
						+ "	select \n"
						+ "	clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	0 as clientType, \n"
						+ "	projectID, \n"
						+ "	0 as accountType, \n" //账户类型
						+ "	0 as contractType, \n" //合同类型
						+ "	0 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(balance) balance \n"
						+ " from ( \n"
						//
						+ "	select clientID,scode,sname,projectID,sum(MBALANCE) balance \n"						
						+ "	from \n"
						+ "	( \n"
						+ "		select bbb.*,aaa.MBALANCE \n"
						+ "		 from sett_subaccount aaa, \n"
						+ "		( \n"
						+ sSubAccountIDSQL
						+ "		) bbb \n"
						+ "	where aaa.ID(+) = bbb.subID \n"
						+ "	) \n"
						+ "	group by clientID,scode,sname,projectID \n"
						//
						+ " UNION ALL \n"
						+ " select borrowClientID as clientID,scode,sname,projectID, \n"
						+ " 		sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client, \n"
						+ " 	(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING "
						+ "		where statusid=1 and projectType=1 and projectID = " + info.getProjectID() + " \n"
						+ "		and accountType = -1 and contractType = 13) c \n"						
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				SQLstr += " group by borrowClientID, client.scode, client.sname, projectID \n"
				    	+ " )group by projectID,clientID,scode,sname \n"	
						
						//
						+ ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
						+ ") ddd \n"
						+ " where ccc.projectID = ddd.projectID(+) \n"
						+ " and ccc.clientID = ddd.clientID(+) \n"
						+ " order by ccc.projectID,ccc.clientID";
			} else
			{
				log.print("查询历史");
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n"
						//start modify
						+ "	select \n"
						+ "	0 as clientType, \n"
						+ "	projectID, \n"
						+ "	clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	0 as accountType, \n" //账户类型
						+ "	0 as contractType, \n" //合同类型
						+ "	0 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(balance) balance \n"
						+ "	from \n"
						+ "	( \n"
						//
						+ "	select projectID,clientID,scode,sname,sum(MBALANCE) balance \n"						
						+ "	from \n"
						+ "	( \n"
						+ "		select aaaa.id as clientType,bbbb.* from  SETT_ENTERPRICETYPE aaaa, \n"
						+ "		( \n"
						+ "			select bbb.clientType as clientTypeID,bbb.subID,bbb.projectID,bbb.contractType,bbb.accountType,bbb.relateClientType,bbb.clientID,bbb.sname,bbb.scode,aaa.MBALANCE \n"
						+ "			from sett_DailyAccountBalance aaa, \n"
						+ "			("
						+ sSubAccountIDSQL
						+ "			) bbb \n"
						+ "			where aaa.nSubAccountID(+) = bbb.subID \n"
						+ "			and to_char(aaa.dtdate,'yyyy-mm-dd') = to_char('"
						+ DataFormat.formatDate(info.getDateFrom())
						+ "')"
						+ "		)bbbb" + "		where aaaa.id=bbbb.clientTypeID";
				if (info.getClientType() <= 0)
				{
					SQLstr += "(+) \n";
				}
				SQLstr += "		and aaaa.nstatusid=1"
						+ "	) \n"
						+ "	group by clientID,scode,sname,projectID \n"
						//start add
						+ " UNION ALL \n"
						+ " select projectID,borrowClientID as clientID,scode,sname, \n"
						+ " 		sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client, \n"
						+ " 	(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING "
						+ "		where statusid=1 and projectType=1 and projectID = " + info.getProjectID() + " \n"
						+ "		and accountType = -1 and contractType = 13) c \n"
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				SQLstr += " group by borrowClientID, client.scode, client.sname,projectID \n"
				    	+ " )group by projectID,clientID,scode,sname \n"						
						//end add modify
						+ ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
						+ ") ddd \n"
						+ " where ccc.projectID = ddd.projectID(+) \n"
						+ " and ccc.clientID = ddd.clientID(+) \n"
						+ " order by ccc.projectID,ccc.clientID";
			}
		} else if (info.getStatisticsType() == 2)
		{
			log.print("计算峰值");
			SQLstr = "select \n"
					+ " 0 as clientType, \n"
					+ " t4.projectID, \n"
					+ " t4.clientID, \n"
					+ " t4.scode, \n"
					+ " t4.sname, \n"
					+ " 0 as accountType, \n"
					+ " 0 as contractType, \n"
					+ " 0 as relateClientType, \n"
					+ " t4.mAvgBalance as avgBalance, \n"
					+ " t4.mMaxSumBalance as maxBalance, \n"
					+ " t4.dtdate as maxBalanceDate, \n"
					+ " t4.mMaxSumBalance as  balance, \n"
					+ " t5.releaseAmountLimit, \n"
					+ " t5.awokerate\n"
					+ " from \n"
					+ " ( \n"
					+ " select t1.* ,t2.dtdate from \n"
					+ " ( \n"
					+ " 	select clientID,sname,scode,projectID,max(mSumBalance) mMaxSumBalance,avg(mSumBalance) mAvgBalance from \n"
					+ " 	( \n"
					//start1
					+ "		select \n"
					+ "			clientID,sname,scode,projectID, \n"
					+ "			sum(mSumBalance) mSumBalance,0.0 as balance,dtdate \n"
					+ "		from \n"
					+ "		( \n"
					//end1
					+ "		select \n"
					+ "		clientID,sname,scode, \n"
					+ "		projectID, \n"
					+ "		sum(mBalance) mSumBalance, \n"					
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientID,sname,scode, \n"
					+ "		projectID, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "	) \n"
					+ "	group by clientID,sname,scode,projectID,to_char(dtdate,'yyyy-mm-dd') \n"
					//start2
					+ "     UNION ALL \n"
					+ "     select \n"
					+ "     	clientID,sname,scode," + info.getProjectID() + " as projectID, \n"					
					+ "     	(select SUM(DECODE(direction,1,amount,2,-amount)) mAmount \n"
					+ "     	 from SETT_OFFBALANCE aa,client \n"
					+ "     	 where transactionType IN (126,127) \n"
					+ "     	 	AND aa.borrowClientID=client.id \n"
					+ "      		AND NVL(executeDate,TO_DATE('20041231','YYYYMMDD')) <= bb.dtDate \n"
					+ "      		AND aa.borrowClientID = bb.clientID \n"
					+ "     		group by borrowClientID \n"
					+ "     	 ) mBalance, \n"
					+ "     	to_char(bb.dtDate,'yyyy-mm-dd') as dtdate \n"
					+ "     from \n"
					+ "     	(select clientID,sname,scode,d.dtDate \n"
					+ "     	 from \n"					
					+ "      	  (select * from \n"
					+ "      		(select DISTINCT borrowClientID as clientID,sname,scode from SETT_OFFBALANCE,client where borrowClientID=client.id and transactionType in (126,127) "
					+ "						and client.nCorpNatureID = " + info.getClientType() + ") a, \n"				
					+ "      		(select DISTINCT dtDate from sett_dailyAccountBalance \n"
					+ "      				where dtDate>=to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      				and   dtDate<=to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n"
					+ "				 ) b \n"					
					+ "      	 ) d \n"
					+ "      	order by clientID,d.dtDate \n"
					+ "      	) bb \n"
					+ "      where 1 = 1 \n"			
					+ "      	AND bb.dtDate >= to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      	AND bb.dtDate <= to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n" 
					+ "		) \n"
					+ "		group by clientID,sname,scode,projectID,dtdate \n"				
					//end2
					
					+ "	) group by clientID,sname,scode,projectID \n"
					+ " ) t1, \n"
					+ " ( \n"
					+ " select clientID,sname,scode,projectID,min(dtdate) dtdate,msumBalance from \n"
					+ " ( \n"
					+ " 	select \n"
					+ " 	clientID,sname,scode, \n"
					+ " 	projectID, \n"
					+ " 	sum(mBalance) mSumBalance, \n"
					+ " 	avg(mBalance) mAvgBalance, \n"
					+ " 	0.0 as balance, \n"
					+ " 	to_char(dtdate,'yyyy-mm-dd') dtdate \n"
					+ " 	from \n"
					+ " 	( \n"
					+ " 		select \n"
					+ " 		clientID,sname,scode, \n"
					+ " 		projectID, \n"
					+ " 		aaa.mBalance, \n"
					+ " 		aaa.dtdate \n"
					+ " 		from sett_DailyAccountBalance aaa, \n"
					+ " 		( \n"
					+ sSubAccountIDSQL
					+ "			) bbb \n"
					+ "			where  aaa.nsubAccountID=bbb.subID \n"
					+ "			and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "			and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "		) \n"
					+ "		group by clientID,sname,scode,projectID,to_char(dtdate,'yyyy-mm-dd') \n"
					+ " ) \n"
					+ " group by clientID,sname,scode,projectID,msumBalance \n"
					+ " ) t2 \n"
					+ " where t1.clientID = t2.clientID \n"
					+ " and t1.projectID =t2.projectID \n"
					+ " and t1.mMaxsumBalance = t2.msumBalance \n"
					+ " ) t4, \n"
					+ " ("
					+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
					+ ") t5 \n"
					+ " where t4.clientID = t5.clientID(+) and t4.projectID = t5.projectID(+) \n"
					+ " order by projectID,clientID";
		}
		log.print(SQLstr);
		try
		{
			transPS = transConn.prepareStatement(SQLstr);
			transRS = transPS.executeQuery();
			list = transRsToList(transRS);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 列出此客户类型在此项目下的每种组合下的余额或峰值
	 * 
	 * @param info
	 * @return @throws
	 *         Exception
	 */
	public Collection findByProject(QueryStockCompanyStatisticsInfo info)
			throws Exception
	{
		ArrayList list = new ArrayList();
		transConn = this.getConnection();
		//得到满足条件的子账户的SQL语句
		getSubAccountIDSQLstr(info);
		if (info.getStatisticsType() == 1)
		{
			if (isToday(info.getOfficeID(), info.getCurrencyID(), info
					.getDateFrom()))
			{
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n"						
						+ "	select \n"
						+ "	clientType, \n"
						+ "	0 as projectID, \n"
						+ "	0 as clientID, \n" //客户号(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"											
						+ "	from \n"
						+ "	( \n"
						+ "		select bbb.*,aaa.MBALANCE \n"
						+ "		 from sett_subaccount aaa, \n"
						+ "		( \n"
						+ sSubAccountIDSQL
						+ "		) bbb \n"
						+ "	where aaa.ID(+) = bbb.subID \n"
						+ "	) \n"
						+ "	group by clientType,accountType,contractType,relateClientType \n"
						//
						+ " UNION ALL \n"
						+ "	select \n"
						+ "	nCorpNatureID as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	0 as clientID, \n" //客户号(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期						
						+ " sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client, \n"
						+ " 	(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING "
						+ "		where statusid=1 and projectType=1 and projectID = " + info.getProjectID() + " \n"
						+ "		and accountType = -1 and contractType = 13) c \n"
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				SQLstr += " group by nCorpNatureID,accountType,contractType,relateClientType \n"				    	
						//
						+ ") ccc, \n"
						+ " (select * from ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
						+ ")  \n";
				if (info.getProjectID() > 0)
					SQLstr += " where projectID = " + info.getProjectID();
				SQLstr += ") ddd \n"
						+ " where ccc.clientType = ddd.clientType(+) \n"
						+ " order by ccc.clientType";
			} else
			{
				log.print("查询历史");
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n"
						+ "	select \n"
						+ "	clientType, \n"
						+ "	0 as projectID, \n"
						+ "	0 as clientID, \n" //客户ID()(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"
						+ "	from \n"
						+ "	( \n"
						+ "		select aaaa.id as clientType,bbbb.* from  SETT_ENTERPRICETYPE aaaa, \n"
						+ "		( \n"
						+ "			select bbb.clientType as clientTypeID,bbb.subID,bbb.projectID,bbb.contractType,bbb.accountType,bbb.relateClientType,bbb.clientID,bbb.sname,bbb.scode,aaa.MBALANCE \n"
						+ "			from sett_DailyAccountBalance aaa, \n"
						+ "			("
						+ sSubAccountIDSQL
						+ "			) bbb \n"
						+ "			where aaa.nSubAccountID(+) = bbb.subID \n"
						+ "			and to_char(aaa.dtdate,'yyyy-mm-dd') = to_char('"
						+ DataFormat.formatDate(info.getDateFrom())
						+ "')"
						+ "		)bbbb" + "		where aaaa.id=bbbb.clientTypeID";
				if (info.getClientType() <= 0)
				{
					SQLstr += "(+) \n";
				}
				SQLstr += "		and aaaa.nstatusid=1"
						+ "	) \n"
						+ "	group by clientType,accountType,contractType,relateClientType \n"
						//同上
						+ " UNION ALL \n"
						+ "	select \n"
						+ "	nCorpNatureID as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	0 as clientID, \n" //客户号(因为公用一个转换方法,所以也要给他赋值)
						+ "	'' as scode, \n"
						+ "	'' as sname, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期						
						+ " sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client, \n"
						+ " 	(select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING "
						+ "		where statusid=1 and projectType=1 and projectID = " + info.getProjectID() + " \n"
						+ "		and accountType = -1 and contractType = 13) c \n"
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				SQLstr += " group by nCorpNatureID,accountType,contractType,relateClientType \n"	
						//
						+ ") ccc, \n"
						+ " (select * from ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
						+ ")  \n";
				if (info.getProjectID() > 0)
					SQLstr += " where projectID = " + info.getProjectID();
				SQLstr += ") ddd \n"
						+ " where ccc.clientID = ddd.clientID(+) \n"
						+ " order by ccc.clientType";
			}
		} else if (info.getStatisticsType() == 2)
		{
			log.print("计算峰值");
			SQLstr = "select \n"
					+ " t4.clientType, \n"
					+ " 0 as projectID, \n"
					+ " 0 as clientID, \n"
					+ " '' as scode, \n"
					+ " '' as sname, \n"
					+ " accountType, \n"
					+ " contractType, \n"
					+ " relateClientType, \n"
					+ " t4.mAvgBalance as avgBalance, \n"
					+ " t4.mMaxSumBalance as maxBalance, \n"
					+ " t4.dtdate as maxBalanceDate, \n"
					+ " t4.mMaxSumBalance as  balance, \n"
					+ " t5.releaseAmountLimit, \n"
					+ " t5.awokerate\n"
					+ " from \n"
					+ " ( \n"
					+ " select t1.* ,t2.dtdate from \n"
					+ " ( \n"
					+ " 	select clientType,accountType,contractType,relateClientType,max(mSumBalance) mMaxSumBalance,avg(mSumBalance) mAvgBalance from \n"
					+ " 	( \n"
					+ "		select \n"
					+ "		clientType, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		sum(mBalance) mSumBalance, \n"
					+ "		0.0 as balance,"
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientType, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "	) \n"
					+ "	group by clientType,accountType,contractType,relateClientType,to_char(dtdate,'yyyy-mm-dd') \n"
					+ "	) group by clientType,accountType,contractType,relateClientType \n"
					+ " ) t1, \n"
					+ " ( \n"
					+ " select clientType,accountType,contractType,relateClientType,min(dtdate) dtdate,msumBalance from \n"
					+ " ( \n"
					+ "		select \n"
					+ "		clientType, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		sum(mBalance) mSumBalance, \n"
					+ "		0.0 as balance,"
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientType, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "	) \n"
					+ "	group by clientType,accountType,contractType,relateClientType,to_char(dtdate,'yyyy-mm-dd') \n"
					+ " ) \n"
					+ " group by clientType,accountType,contractType,relateClientType,msumBalance \n"
					+ " ) t2 \n" + " where t1.clientType = t2.clientType \n"
					+ " and t1.accountType =t2.accountType \n"
					+ " and t1.contractType =t2.contractType \n"
					+ " and t1.relateClientType =t2.relateClientType \n"
					+ " and t1.mMaxsumBalance = t2.msumBalance \n"
					+ " ) t4, \n" + " (select * from ("
					+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
					+ ")  \n";
			if (info.getProjectID() > 0)
				SQLstr += " where projectID = " + info.getProjectID();
			SQLstr += ") t5 \n" + " where t4.clientType = t5.clientType(+) \n"
					//+ " order by clientType \n"
					//start
					+ "UNION ALL \n"
					+ "select \n"
					+ " clientType, \n"
					+ " 0 as projectID, \n"
					+ " 0 as clientID, \n"
					+ " '' as scode, \n"
					+ " '' as sname, \n"
					+ " accountType, \n"
					+ " contractType, \n"
					+ " relateClientType, \n"
					+ " avg(mSumBalance) as avgBalance, \n"
					+ " max(mSumBalance) as maxBalance, \n"
					+ " min(dtdate) as maxBalanceDate, \n"
					+ " max(mSumBalance) as  balance, \n"
					+ " 0 as releaseAmountLimit, \n"
					+ " 0 as awokerate \n"
					+ "from  \n"
					+ "(  \n"
					+ " select \n"
					+ "  bb.nCorpNatureID as clientType, \n"
					+ "  accountType,contractType,relateClientType, \n"
					+ "  (select SUM(DECODE(direction,1,amount,2,-amount)) mAmount \n"
					+ "   from SETT_OFFBALANCE aa,client \n"
					+ "   where aa.borrowClientID=client.id \n"
					+ "    AND transactionType IN (126,127) \n"
					+ "    AND NVL(executeDate,TO_DATE('20041231','YYYYMMDD')) <= bb.dtDate \n"
					+ "    AND client.nCorpNatureID = bb.nCorpNatureID \n"
					+ "    group by nCorpNatureID \n"
					+ "   ) mSumBalance, \n"
					+ "  0.0 as balance, to_char(bb.dtDate,'yyyy-mm-dd') as dtdate \n"
					+ " from  \n"
					+ "  (select accountType,contractType,relateClientType,d.nCorpNatureID,d.dtDate \n"
					+ "   from \n"
					+ "    (select projectID,accountType,contractType,relateClientType from SETT_STOCKPROJECTSETTING \n"
					+ "      where statusID = 1 and projectType = 1 \n"
					+ "  	 and accountType = -1 and contractType = 13 \n"
					+ "  	 and projectID = " + info.getProjectID() + " \n"
					+ "   ) c, \n"
					+ "   (select * from \n"
					+ "  			(select DISTINCT client.nCorpNatureID from SETT_OFFBALANCE,client where borrowClientID=client.id and transactionType in (126,127) \n"
					+ "  	 			and client.nCorpNatureID = " + info.getClientType() + " \n"
					+ "  	 		) a, \n"
					+ "      		(select DISTINCT dtDate from sett_dailyAccountBalance \n"
					+ "      				where dtDate>=to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      				and   dtDate<=to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n"
					+ "				 ) b \n"	
					+ "   ) d \n"
					//+ "   order by c.projectID,d.nCorpNatureID,d.dtDate \n"
					+ "  ) bb \n"
					//+ "  ORDER BY clientType,accountType,contractType,relateClientType,dtdate \n"
					+ " ) group by clientType,accountType,contractType,relateClientType \n";	
			
					//end
		}
		log.print(SQLstr);
		try
		{
			transPS = transConn.prepareStatement(SQLstr);
			transRS = transPS.executeQuery();
			list = transRsToList(transRS);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 列出此客户类型的每个客户(或某个客户)在此项目下的每种组合下的余额或峰值
	 * 
	 * @param info
	 * @return @throws
	 *         Exception
	 */
	public Collection findByClientTypeAndProject(
			QueryStockCompanyStatisticsInfo info) throws Exception
	{
		ArrayList list = new ArrayList();
		transConn = this.getConnection();
		//得到满足条件的子账户的SQL语句
		getSubAccountIDSQLstr(info);
		if (info.getStatisticsType() == 1)
		{
			if (isToday(info.getOfficeID(), info.getCurrencyID(), info
					.getDateFrom()))
			{
				log.print("查询今天");
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n";
				if(info.getAccountType() != -1 || info.getContractType() != 13)//非担保类型
				{
				 SQLstr += "select \n"
						+ "	clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	0 as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"
						+ "	from \n"
						+ "	( \n"
						+ "		select bbb.*,aaa.MBALANCE \n"
						+ "		 from sett_subaccount aaa, \n"
						+ "		( \n"
						+ sSubAccountIDSQL
						+ "		) bbb \n"
						+ "	where aaa.ID(+) = bbb.subID \n"
						+ "	) \n"
						+ "	group by clientID,scode,sname,accountType,contractType,relateClientType \n";
				}
				else
				{
				 SQLstr += "select \n"
						+ "	borrowClientID as clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	0 as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	-1 as accountType, \n" //账户类型
						+ "	13 as contractType, \n" //合同类型
						+ "	1 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client \n"						
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " 	and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " 	and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " 	and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				{
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				}
				SQLstr += "	group by borrowClientID,scode,sname \n";
				}//end else
				
				SQLstr += ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
						+ ") ddd \n"
						+ " where ccc.projectID = ddd.projectID(+) \n"
						+ " and ccc.clientID = ddd.clientID(+) \n"
						+ " order by ccc.projectID,ccc.clientID";
			} else
			{
				log.print("查询历史");
				SQLstr = "select ccc.*,ddd.releaseAmountLimit,ddd.awokerate from \n"
						+ "( \n";
				if(info.getAccountType() != -1 || info.getContractType() != 13)//非担保类型
				{
				SQLstr += "	select \n"
						+ "	0 as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	accountType, \n" //账户类型
						+ "	contractType, \n" //合同类型
						+ "	relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum(MBALANCE) balance \n"
						+ "	from \n"
						+ "	( \n"
						+ "		select aaaa.id as clientType,bbbb.* from  SETT_ENTERPRICETYPE aaaa, \n"
						+ "		( \n"
						+ "			select bbb.clientType as clientTypeID,bbb.subID,bbb.projectID,bbb.contractType,bbb.accountType,bbb.relateClientType,bbb.clientID,bbb.sname,bbb .scode,aaa.MBALANCE \n"
						+ "			from sett_DailyAccountBalance aaa, \n"
						+ "			("
						+ sSubAccountIDSQL
						+ "			) bbb \n"
						+ "			where aaa.nSubAccountID(+) = bbb.subID \n"
						+ "			and to_char(aaa.dtdate,'yyyy-mm-dd') = to_char('"
						+ DataFormat.formatDate(info.getDateFrom())
						+ "')"
						+ "		)bbbb" + "		where aaaa.id=bbbb.clientTypeID";
				if (info.getClientType() <= 0)
				{
					SQLstr += "(+) \n";
				}
				SQLstr += "		and aaaa.nstatusid=1"
						+ "	) \n"
						+ "	group by clientID,scode,sname,accountType,contractType,relateClientType \n";
				}
				else
				{
				    SQLstr += "select \n"
						+ "	borrowClientID as clientID, \n"
						+ "	scode, \n"
						+ "	sname, \n"
						+ "	0 as clientType, \n"
						+ "	0 as projectID, \n"
						+ "	-1 as accountType, \n" //账户类型
						+ "	13 as contractType, \n" //合同类型
						+ "	1 as relateClientType, \n" //有关客户
						+ "	0.0 as avgBalance, \n" //平均余额
						+ "	0.0 as maxBalance, \n" //峰值余额
						+ "	null as maxBalanceDate, \n" //峰值日期
						+ "	sum( decode(transactionType,126,amount,127,-amount) ) as balance \n"
						+ " from sett_offBalance, client \n"						
						+ " where TRANSACTIONTYPE in (126,127) \n"
						+ " 	and nvl(executeDate,to_date('2004-12-31','yyyy-mm-dd')) <= to_date('" + DataFormat.formatDate(info.getDateFrom()) + "','yyyy-mm-dd') \n"
						+ " 	and statusID = 3 and officeID = " + info.getOfficeID() + " and currencyID = " + info.getCurrencyID() + " \n"
						+ " 	and borrowClientID = client.ID \n";
				if(info.getClientType() > 0)
				{
				    SQLstr += "  and nCorpNatureID = " + info.getClientType() + " \n";	
				}
				SQLstr += "	group by borrowClientID,scode,sname \n";   
				}//end else
				SQLstr += ") ccc, \n"
						+ " ("
						+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
						+ ") ddd \n"
						+ " where ccc.projectID = ddd.projectID(+) \n"
						+ " and ccc.clientID = ddd.clientID(+) \n"
						+ " order by ccc.projectID,ccc.clientID";
			}
		} else if (info.getStatisticsType() == 2)
		{
			log.print("计算峰值");
			SQLstr = "select \n"
					+ " 0 as clientType, \n"
					+ " 0 as projectID, \n"
					+ " t4.clientID, \n"
					+ " scode, \n"
					+ " sname, \n"
					+ " accountType, \n"
					+ " contractType, \n"
					+ " relateClientType, \n"
					+ " t4.mAvgBalance as avgBalance, \n"
					+ " t4.mMaxSumBalance as maxBalance, \n"
					+ " t4.dtdate as maxBalanceDate, \n"
					+ " t4.mMaxSumBalance as  balance, \n"
					+ " t5.releaseAmountLimit, \n"
					+ " t5.awokerate\n"
					+ " from \n"
					+ " ( \n"
					+ " select t1.* ,t2.dtdate from \n"
					+ " ( \n"
					+ " 	select clientID,sname,scode,accountType,contractType,relateClientType,max(mSumBalance) mMaxSumBalance,avg(mSumBalance) mAvgBalance from \n"
					+ " 	( \n"
					+ "		select \n"
					+ "		clientID, \n"
					+ "		sname, \n"
					+ "		scode, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		sum(mBalance) mSumBalance, \n"
					+ "		0.0 as balance,"
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientID, \n"
					+ "		sname, \n"
					+ "		scode, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "	) \n"
					+ "	group by clientID,sname,scode,accountType,contractType,relateClientType,to_char(dtdate,'yyyy-mm-dd') \n"
					+ "	) group by clientID,sname,scode,accountType,contractType,relateClientType \n"
					+ " ) t1, \n"
					+ " ( \n"
					+ " select clientID,sname,scode,accountType,contractType,relateClientType,min(dtdate) dtdate,msumBalance from \n"
					+ " ( \n"
					+ "		select \n"
					+ "		clientID, \n"
					+ "		sname, \n"
					+ "		scode, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		sum(mBalance) mSumBalance, \n"
					+ "		0.0 as balance,"
					+ "		to_char(dtdate,'yyyy-mm-dd') as dtdate \n"
					+ "		from \n"
					+ "		( \n"
					+ "		select \n"
					+ "		clientID, \n"
					+ "		sname, \n"
					+ "		scode, \n"
					+ "		accountType, \n"
					+ "		contractType, \n"
					+ "		relateClientType, \n"
					+ "		aaa.mBalance, \n"
					+ "		aaa.dtdate \n"
					+ "		from sett_DailyAccountBalance aaa, \n"
					+ "		( \n"
					+ sSubAccountIDSQL
					+ "		) bbb \n"
					+ "		where  aaa.nsubAccountID=bbb.subID \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') >= to_char('"
					+ DataFormat.formatDate(info.getDateFrom())
					+ "') \n"
					+ "		and to_char(aaa.DTDATE,'yyyy-mm-dd') <= to_char('"
					+ DataFormat.formatDate(info.getDateTo())
					+ "') \n"
					+ "	) \n"
					+ "	group by clientID,sname,scode,accountType,contractType,relateClientType,to_char(dtdate,'yyyy-mm-dd') \n"
					+ " ) \n"
					+ " group by clientID,sname,scode,accountType,contractType,relateClientType,msumBalance \n"
					+ " ) t2 \n" + " where t1.clientID = t2.clientID \n"
					+ " and t1.accountType =t2.accountType \n"
					+ " and t1.contractType =t2.contractType \n"
					+ " and t1.relateClientType =t2.relateClientType \n"
					+ " and t1.mMaxsumBalance = t2.msumBalance \n"
					+ " ) t4, \n" + " ("
					+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),1)
					+ ") t5 \n" + " where t4.clientID = t5.clientID(+) \n";
					//+ " order by clientID \n";
			if(info.getAccountType()<0 && info.getContractType()==0)//某客户类型下的客户，无账户类型，担保合同类型的组合。
			{
			SQLstr += " UNION ALL \n"
					+ " select \n"
					+ "  0 as clientType, \n"
					+ "  0 as projectID, \n"
					+ "  scode, \n"
					+ "  sname, \n"
					+ "  -1 as accountType, \n"
					+ "  13 as contractType, \n"
					+ "  1 as relateClientType, \n"
					+ "  avgBalance, \n"
					+ "  maxBalance, \n"
					+ "  maxBalanceDate as maxBalanceDate, \n"
					+ "  0 as  balance, \n"
					+ "  t2.releaseAmountLimit, \n"
					+ "  t2.awokerate \n"
					+ " from \n"
					+ "  ( \n"
					+ "   select clientID,sname,scode,max(mBalance) maxBalance,avg(mBalance) avgBalance,min(dtDate) maxBalanceDate \n"
					+ "   from \n"
					+ "   ( \n"
					+ "    select \n"
					+ "  	clientID,sname,scode,\n"
					+ "  	(select SUM(DECODE(direction,1,amount,2,-amount)) mAmount \n"
					+ "  	 from SETT_OFFBALANCE aa,client \n"
					+ "  	 where transactionType IN (126,127) \n"
					+ "  	 	AND aa.borrowClientID=client.id \n"
					+ "  		AND NVL(executeDate,TO_DATE('20041231','YYYYMMDD')) <= bb.dtDate \n"
					+ "  		AND aa.borrowClientID = bb.clientID \n"
					+ "  		group by borrowClientID \n"
					+ "  	 ) mBalance, \n"
					+ "     to_char(bb.dtDate,'yyyy-mm-dd') as dtdate \n"
					+ "    from \n"
					+ "      (select clientID,sname,scode,d.dtDate \n"
					+ "  		from \n"
					+ "  		(select * from \n"
					+ "  			(select DISTINCT borrowClientID as clientID,sname,scode from SETT_OFFBALANCE,client where borrowClientID=client.id and transactionType in (126,127) \n"
					+ "  			and client.nCorpNatureID = " + info.getClientType() + ") a,\n"
					+ "  			(select DISTINCT dtDate from sett_dailyAccountBalance \n"
					+ "      				where dtDate>=to_date('" + DataFormat.getDateString(info.getDateFrom()) + "','yyyy-mm-dd') \n"
					+ "      				and   dtDate<=to_date('" + DataFormat.getDateString(info.getDateTo()) + "','yyyy-mm-dd') \n"
					+ "				 ) b \n"					
					+ "      	 ) d \n"
					+ "      	order by clientID,d.dtDate \n"
					+ "      ) bb \n"
					+ "    ) \n"
					+ "    group by clientID,sname,scode \n"
					+ "    ) t1, \n"
					+ "    ( \n"
					+ getSQLReleaseAmountLimitSettingStr(info.getDateFrom(),2)
					+ "    ) t2 \n"
					+ "  where t1.clientID = t2.clientID(+)	\n";	
			}
		}
		log.print(SQLstr);
		try
		{
			transPS = transConn.prepareStatement(SQLstr);
			transRS = transPS.executeQuery();
			list = transRsToList(transRS);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			try
			{
				this.finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 根据查询已经组合出的SQL计算余额合计（因为设计到SQL执行两次，运行速度比较慢，未采用）
	 * 
	 * @return @throws
	 *         Exception
	 */
	public QueryStockCompanyStatisticsSumInfo computeSumBalance()
			throws Exception
	{
		QueryStockCompanyStatisticsSumInfo info = new QueryStockCompanyStatisticsSumInfo();
		SQLstr = "select sum(balance) as sumBalance,sum(releaseAmountLimit) as sumReleaseAmountLimit from (\n"
				+ SQLstr + " \n )";
		transConn = this.getConnection();
		try
		{
			log.print("计算余额合计SQL" + SQLstr);
			transPS = transConn.prepareStatement(SQLstr);
			transRS = transPS.executeQuery();
			if (transRS.next())
			{
				info.setBalanceSum(transRS.getDouble("sumBalance"));
				info.setReleaseAmountLimitSum(transRS
						.getDouble("sumReleaseAmountLimit"));
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally
		{
			this.finalizeDAO();
		}
		return info;
	}
	/**
	 * 把RS转换成数据实体
	 * 
	 * @param rs
	 * @return @throws
	 *         SQLException
	 */
	private ArrayList transRsToList(ResultSet rs) throws SQLException
	{
		ArrayList list = new ArrayList();
		QueryStockCompanyStatisticsResultInfo info = null;
		while (rs.next())
		{
			info = new QueryStockCompanyStatisticsResultInfo();
			info.setAccountType(rs.getLong("accountType"));
			info.setClientID(rs.getLong("clientID"));
			info.setClientNo(rs.getString("scode"));
			info.setClientName(rs.getString("sname"));
			info.setClientType(rs.getLong("clientType"));
			info.setContractType(rs.getLong("contractType"));
			info.setBalance(rs.getDouble("balance"));
			info.setAvgBalance(rs.getDouble("avgBalance"));
			info.setMaxBalance(rs.getDouble("maxBalance"));
			info.setMaxBalanceDate(rs.getDate("maxBalanceDate"));
			info.setProjectID(rs.getLong("projectID"));
			info.setRelateClientType(rs.getLong("relateClientType"));
			info.setReleaseAmountLimit(rs.getDouble("releaseAmountLimit"));
			info.setUsedPercent(computePercent(rs.getDouble("balance"), rs
					.getDouble("releaseAmountLimit")));
			info.setAwokeRate(rs.getDouble("awokerate"));
			list.add(info);
		}
		return list;
	}
	/**
	 * 计算百分比
	 * 
	 * @param amountOne
	 * @param amountTwo
	 * @return
	 */
	private double computePercent(double amountOne, double amountTwo)
	{
		if (amountTwo == 0.0)
		{
			return 0.0;
		} else
		{
			return DataFormat.formatDouble(amountOne / amountTwo * 100);
		}
	}
	
	private String sSubAccountIDSQL = "";
	private String SQLstr = "";
	public static void main(String[] args)
	{
		QStockCompanyAssetsStatistics query = new QStockCompanyAssetsStatistics();
		QueryStockCompanyStatisticsInfo info = new QueryStockCompanyStatisticsInfo();
		info.setOfficeID(1);
		info.setCurrencyID(1);
		info.setDateFrom(java.sql.Timestamp.valueOf("2005-01-10 00:00:00.0"));
		info.setDateTo(java.sql.Timestamp.valueOf("2005-01-30 00:00:00.0"));
		info.setStatisticsType(1);
		info.setClientType(1);
		info.setProjectID(1);
		Collection c = null;
		try
		{
			c = query.findByClientType(info);
			//query.computeSumBalance();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (c != null && c.size() > 0)
		{
			System.out.println(c.size());
		} else
		{
			System.out.println("null");
		}
		double a = 0.00;
		System.out.println(DataFormat.formatDisabledAmount(a, 2));
	}
}

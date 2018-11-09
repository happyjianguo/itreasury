/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.query.dataentity.PledgeForDetail;
import com.iss.itreasury.securities.query.dataentity.QueryCapitalLandingFormParam;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author wangyi
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryCapitalLandingFormBean {

	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);

	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;

	public final static int OrderBy_CounterpartCode = 1; //交易对手编号
	public final static int OrderBy_businessTypeName = 2; //业务类型名称
	public final static int OrderBy_transactionStartDate = 3; //授信开始日期
	public final static int OrderBy_transactionEndDate = 4; //授信结束日期
	public final static int OrderBy_outPledgeSecuritiesAmount = 5; //资金拆出授信额度
	public final static int OrderBy_inPledgeSecuritiesAmount = 9; //交易对手提供的授信额度
	public final static int OrderBy_term = 6; //拆出最长期限
	public final static int OrderBy_statusId = 7; //单子的状态
	public final static int OrderBy_inputUserName = 8; //录入人

	/**
	 * 返回证券查询的SQLString语句
	 * 
	 * @param queryParam
	 * @return
	 */
	private void getSQL(QueryCapitalLandingFormParam queryParam) {
		sbSelect = new StringBuffer();

		sbSelect.append("   *  \n");


		sbFrom = new StringBuffer();


//[交易对手编号]、[交易对手名称]、[业务类型]、 [授信开始日期]、[授信结束日期]、[资金拆出授信额度]、[拆出最长期限]、
//[交易对手提供的授信额度]、[状态]、[录入人]


		sbFrom.append("(     \n");
		sbFrom.append("  select DISTINCT \n");
		
		sbFrom.append("   SEC_APPLYFORM.ID as applyId,--申请书ID  \n");
		sbFrom.append("   SEC_APPLYFORM.code as applyCode,--申请书编号  \n");
		sbFrom.append("   SEC_COUNTERPART.ID as counterpartId,--交易对手ID  \n");
		sbFrom.append("   SEC_COUNTERPART.CODE as counterpartCode,--交易对手编号  \n");
		sbFrom.append("   SEC_COUNTERPART.NAME as counterpartName,--交易对手名称  \n");
		sbFrom.append("   ST_Type.businessTypeName as businessTypeName ,--业务类型  \n");
		sbFrom.append("   ST_Type.businessTypeId as businessTypeId , --业务类型编号,  \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONSTARTDATE as transactionStartDate,  --授信开始日期\n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONENDDATE as transactionEndDate,  --授信结束日期 \n");
		sbFrom.append("   SEC_APPLYFORM.TRANSACTIONTYPEID as transactionTypeId,  --交易类型 \n");
		sbFrom.append("   SEC_APPLYFORM.PledgeSecuritiesAmount as pledgeSecuritiesAmount,  --授信额度 \n");
		
		sbFrom.append("   decode(ST_Type.businessTypeId ,12,SEC_APPLYFORM.PledgeSecuritiesAmount,null) as outPledgeSecuritiesAmount,  --资金拆出授信额度  \n");
		sbFrom.append("   decode(ST_Type.businessTypeId ,11,SEC_APPLYFORM.PledgeSecuritiesAmount,null) as inPledgeSecuritiesAmount,  --交易对手提供的授信额度  \n");
		sbFrom.append("   SEC_APPLYFORM.Term as term,  --拆出最长期限  \n");
		sbFrom.append("   SEC_APPLYFORM.statusId as statusId,  --状态 \n");
		sbFrom.append("   USERINFO.SNAME as inputUserName  --录入人 \n");
		

		sbFrom.append("  from \n");

		sbFrom.append("   USERINFO , SEC_COUNTERPART , SEC_APPLYFORM ,SEC_CLIENT ,\n");
		
		sbFrom.append("   (select a.id as transactionTypeId, \n");
		sbFrom.append("           a.name as transactionTypeName,  \n");
		sbFrom.append("           b.Id as businessTypeId,  \n");
		sbFrom.append("           b.name as businessTypeName, \n");
		sbFrom.append("            c.ID as businessAttributeID \n");
		sbFrom.append(
			"    from  SEC_TRANSACTIONTYPE a ,SEC_BUSINESSTYPE b,SEC_BUSINESSATTRIBUTE c  \n");
		sbFrom.append(
			"    where subStr(a.Id,0,2) = b.id  and b.BUSINESSATTRIBUTEID = c.ID  \n");
		sbFrom.append("     ) ST_Type  \n");
		

		sbFrom.append("   where  \n");


		sbFrom.append("   SEC_COUNTERPART.id = SEC_APPLYFORM.CounterpartID  \n");
		sbFrom.append("   and \n");
		sbFrom.append("   USERINFO.ID = SEC_APPLYFORM.InputUserID \n");
		sbFrom.append("   and SEC_APPLYFORM.TransactionTypeID = ST_Type.transactionTypeId \n");
		sbFrom.append("   and ( businessTypeId = " + SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.ID 
		+ " or businessTypeId = " + SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.ID + " )");



		//		===========以下是根据页面传递的参数决定的条件======start====
		//条件一：业务通知单录入日期开始日

		Timestamp noticeInputDateStart = queryParam.getNoticeInputDateStart();
		String strNoticeInputDateStart =
			DataFormat.getDateString(noticeInputDateStart);
		if(!"".equals(strNoticeInputDateStart))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_APPLYFORM.TransactionStartDate , 'yyyy-mm-dd') >= " + "'" + strNoticeInputDateStart + "'");
		}
		//条件二：业务通知单录入日期开始日
		Timestamp noticeInputDateEnd = queryParam.getNoticeInputDateEnd();
		String strNoticeInputDateEnd =
			DataFormat.getDateString(noticeInputDateEnd);
		if(!"".equals(strNoticeInputDateEnd))
		{
			sbFrom.append(" and \n");
			sbFrom.append(
				" to_char(SEC_APPLYFORM.TransactionEndDate , 'yyyy-mm-dd') <= " + "'" + strNoticeInputDateEnd + "'");
		}
		//条件四：业务类型
		long businessTypeId = queryParam.getBusinessTypeId();
		if (businessTypeId > 0) 
		{
			sbFrom.append(" and \n");
			if(businessTypeId == SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION)
			{
				sbFrom.append(" ST_Type.businessTypeId = " + SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.ID + " \n");
			}
			else if(businessTypeId == SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION)
			{
				sbFrom.append(" ST_Type.businessTypeId = " + SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.ID + " \n");
			}
		}

		//条件六：业务单位
		String[] clientIds = queryParam.getClientIds();
		if (clientIds != null && clientIds.length > 0) {
			sbFrom.append(" and SEC_CLIENT.id in ( ");
			for (int i = 0; i < clientIds.length - 1; i++) {
				sbFrom.append(Long.parseLong(clientIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(clientIds[clientIds.length - 1]) + ") \n");
		}

		//条件九：交易对手
		String[] interBankCounterPartIds = queryParam.getInterBankCounterPartIds();
		if (interBankCounterPartIds != null
			&& interBankCounterPartIds.length > 0) {
			sbFrom.append(" and SEC_APPLYFORM.counterPartId in ( ");
			for (int i = 0;
				i < interBankCounterPartIds.length - 1;
				i++) {
				sbFrom.append(
					Long.parseLong(interBankCounterPartIds[i]) + ",");
			}
			sbFrom.append(
				Long.parseLong(
					interBankCounterPartIds[interBankCounterPartIds
						.length
						- 1])
					+ ") \n");
		}
		

		//录入人
		if(queryParam.getInputUserId() != -1)
		{
			long inputUserId = queryParam.getInputUserId();
			sbFrom.append(" and \n");
			sbFrom.append(
				" SEC_APPLYFORM.InputUserID = " + inputUserId + " \n");
		}
		//条件四：单子的状态
		long statusId = queryParam.getStatusId();
		if (statusId > -1) {
			sbFrom.append(" and \n");
			sbFrom.append(
				" SEC_APPLYFORM.StatusID = " + statusId + " \n");
		}


		//===========以下是根据页面传递的参数决定的条件======end====
		sbFrom.append("   )  \n");

		//////////////from////////////////end////////////////////////-
		sbWhere = new StringBuffer();

		//////////////where//////////////-end////////////////////////-
		sbWhere.append(" ");

		//////////////where//////////////-end////////////////////////-


		sbOrderBy = new StringBuffer();
		String strDesc = queryParam.getDesc() == 1 ? " desc " : "";
		//log.debug( "////queryParam.getDesc() //////////-" +
		// queryParam.getDesc());
		switch ((int) queryParam.getOrderField()) {
			case OrderBy_CounterpartCode :
				sbOrderBy.append(" \n order by CounterpartCode" + strDesc);
				break;
			case OrderBy_businessTypeName :
				sbOrderBy.append(" \n order by businessTypeName" + strDesc);
				break;
			case OrderBy_transactionStartDate :
				sbOrderBy.append(" \n order by transactionStartDate" + strDesc);
				break;
			case OrderBy_transactionEndDate :
				sbOrderBy.append(" \n order by transactionEndDate" + strDesc);
				break;
			case OrderBy_outPledgeSecuritiesAmount :
				sbOrderBy.append(" \n order by outPledgeSecuritiesAmount" + strDesc);
				break;
			case OrderBy_inPledgeSecuritiesAmount :
				sbOrderBy.append(" \n order by inPledgeSecuritiesAmount" + strDesc);
				break;
			case OrderBy_term :
				sbOrderBy.append(" \n order by term" + strDesc);
				break;
			case OrderBy_statusId :
				sbOrderBy.append(" \n order by statusId" + strDesc);
				break;
			case OrderBy_inputUserName :
				sbOrderBy.append(" \n order by inputUserName" + strDesc);
				break;

		}
		log4j.debug("////////////////////////////////////////-");

	}

	/**
	 * 生成PageLoader
	 * 
	 * @param queryParam
	 * @return @throws
	 *         SecuritiesException
	 */
	public PageLoader queryCapitalLandingFormInfo(QueryCapitalLandingFormParam queryParam)
		throws SecuritiesException {

		getSQL(queryParam);
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");

		log4j.debug("queryNoticeForm ==sbOrderBy :" + sbOrderBy.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			sbFrom.toString(),
			sbSelect.toString(),
			sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.query.dataentity.QueryCapitalLandingFormInfo",
			null);

		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	
	
	/**
	 * 检查资金拆借授信额度
	 * @param counterpartID	交易对手
	 * @param transactionTypeID 交易类型
	 * @param newPledgeSecuritiesAmount 拆借金额
	 * @param lApplyID 申请书标示
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public HashMap checkCapitalLandingCreditExtension(long counterpartID, long transactionTypeID, double newPledgeSecuritiesAmount, long lApplyID) throws SecuritiesDAOException
	{
		StringBuffer strSQL = new StringBuffer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strResult = "";
		String counterpartCode = "";
		String counterpartName = "";
		Timestamp transactionStartDate = null;
		Timestamp transactionEndDate = null;
		long term = 0;
		double creditAmount = 0;
		double applyAmount1 = 0; //申请书金额（已提交，已审核）
		double applyAmount2 = 0; //申请书金额（已使用）
		double deliverOrderAmount = 0; //已使用申请书对应的交割单金额
		double sumCreditAmount1 = 0; //申请书按交易对手、交易类型区分的累计金额
		double sumCreditAmount2 = 0; //申请书按交易类型区分的累计金额
		double pledgeSecuritiesAmount1 = 0; //交割单资金拆入\出累计金额
		double pledgeSecuritiesAmount2 = 0; //交割单资金拆入\出返款累计金额
		double registeredCapital = 0;
		double oldPledgeSecuritiesAmount = 0;
		
		HashMap hsMap = new HashMap();
		
		try
		{
			log4j.print("检查资金拆借授信额度开始");
			con = Database.getConnection();
			if (lApplyID > 0)
			{
				strSQL.append(" select * ");
				strSQL.append(" from SEC_ApplyForm ");
				strSQL.append(" where ID = " + lApplyID);
				log4j.debug(strSQL.toString());
				ps = con.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					//资金拆入\出
					oldPledgeSecuritiesAmount = rs.getDouble("PledgeSecuritiesAmount");
				}
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
				strSQL.setLength(0);
			}
			
			/***************/
			/*拆出/拆入授信*/
			/***************/
			strSQL.append(" select t2.ID,t2.Code,t2.Name,t1.TransactionTypeID,t1.TransactionStartDate, ");
			strSQL.append(" t1.TransactionEndDate,t1.pledgeSecuritiesAmount,t1.Term,t2.StatusID ");
			strSQL.append(" from ");
			strSQL.append(" SEC_ApplyForm t1,SEC_CounterPart t2 ");
			strSQL.append(" where t1.CounterPartID = t2.ID ");
			strSQL.append(" and t1.StatusID = " + SECConstant.ApplyFormStatus.CHECKED);
			strSQL.append(" and t2.StatusID = " + SECConstant.CounterpartStatus.CHECKED);
			strSQL.append(" and t1.TransactionTypeID in ( ");
			strSQL.append(SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION);
			strSQL.append(" , ");
			strSQL.append(SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION);
			strSQL.append(" ) ");
			strSQL.append(" and decode(t1.TransactionTypeID,'");
			strSQL.append(SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION);
			strSQL.append("','");
			strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN);
			strSQL.append("','");
			strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT);
			strSQL.append("') = " + transactionTypeID);
			strSQL.append(" and t2.ID  = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				counterpartCode = rs.getString("Code");
				counterpartName = rs.getString("Name");
				transactionStartDate = rs.getTimestamp("TransactionStartDate");
				transactionEndDate = rs.getTimestamp("TransactionEndDate");
				creditAmount = rs.getDouble("pledgeSecuritiesAmount");
				term = rs.getLong("Term");
			}
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
			/************************/
			/*申请书：已提交，已审核*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.SUBMITED + "," + SECConstant.ApplyFormStatus.CHECKED + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount1 = rs.getDouble(1);
			}
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
			/************************/
			/*申请书：已使用		*/
			/************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform ");
			strSQL.append(" where statusid in (" + SECConstant.ApplyFormStatus.USED + ") ");
			strSQL.append(" and transactiontypeid = " + transactionTypeID);
			strSQL.append(" and counterpartid = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				applyAmount2 = rs.getDouble(1);
			}
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
			/******************************/
			/*已使用申请书对应的交割单金额*/
			/******************************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(b.pledgesecuritiesamount,0)) ");
			strSQL.append(" from sec_applyform a, sec_deliveryorder b ");
			strSQL.append(" where b.applyformid = a.id and b.statusid > 0 and a.statusid = " + SECConstant.ApplyFormStatus.USED);
			strSQL.append(" and b.transactiontypeid = " + transactionTypeID);
			strSQL.append(" and b.counterpartid = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				deliverOrderAmount = rs.getDouble(1);
			}
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
			/***************/
			/*拆出/拆入余额*/
			/***************/
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT);
					strSQL.append(") ");
				}
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出
				pledgeSecuritiesAmount1 = rs.getDouble(1);
			}
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
			strSQL.setLength(0);
			strSQL.append(" select sum(nvl(PledgeSecuritiesAmount,0)) ");
			strSQL.append(" from SEC_DeliveryOrder ");
			strSQL.append(" where StatusID > 0 ");
			if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
			{
				strSQL.append(" and TransactionTypeID in (");
				strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY);
				strSQL.append(") ");
			}
			else
				if (transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					strSQL.append(" and TransactionTypeID in (");
					strSQL.append(SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY);
					strSQL.append(") ");
				}
			strSQL.append(" group by CounterpartID,TransactionTypeID ");
			strSQL.append(" having CounterpartID = " + counterpartID);
			log4j.debug(strSQL.toString());
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				//资金拆入/出返款
				pledgeSecuritiesAmount2 = rs.getDouble(1);
			}
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
			sumCreditAmount1 = applyAmount1 + applyAmount2 - deliverOrderAmount;

			strResult =
				"本申请已超出"
					+ DataFormat.formatString(counterpartName)
					+ "交易对手"
					+ DataFormat.formatDisabledAmount(creditAmount, 2)
					+ "万元的授信额度，超出金额为"
					+ DataFormat.formatDisabledAmount(
						newPledgeSecuritiesAmount + sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - creditAmount - oldPledgeSecuritiesAmount,
						2)
					+ "万元；目前已用授信为"
					+ DataFormat.formatDisabledAmount(sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) - oldPledgeSecuritiesAmount, 2)
					+ "万元，申请金额为"
					+ DataFormat.formatDisabledAmount(sumCreditAmount1, 2)
					+ "万元。";

//				已使用授信额度 中 申请书所占用的
			hsMap.put("usedApplyFormPledge",new Double(sumCreditAmount1));
//				已使用授信额度 中 交割单所占用的
			hsMap.put("usedDeliverYorderFormPledge",new Double((pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2)));
			hsMap.put("usedPledgeSecuritiesAmount",new Double(sumCreditAmount1 + (pledgeSecuritiesAmount1 - pledgeSecuritiesAmount2) ) );

			log4j.print(strResult);
			log4j.print("检查资金拆借授信额度结束");

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return hsMap;
	}
	
	public ArrayList getPledgeForDetail(long counterpartID, long transactionTypeID)
	{
		StringBuffer sbSQL = new StringBuffer();
		Connection conForApply = null;
		Connection conForDeliver = null;
		PreparedStatement psForApply = null;
		ResultSet rsForApply = null;
		PreparedStatement psForDeliver = null;
		ResultSet rsForDeliver = null;
		ArrayList arrayList = new ArrayList();
		
		try
		{
			//计算各个申请书各自占用的授信额度
			sbSQL.append(" select \n ");
			sbSQL.append(" a.id as applyId, \n ");
			sbSQL.append(" a.statusid, \n ");
			sbSQL.append(" a.pledgesecuritiesamount as a1, \n ");
			sbSQL.append(" decode(a.statusid,3,b.pledgesecuritiesamount,0.0) as a2 \n ");
			sbSQL.append(" from sec_applyform a ,(select * from sec_deliveryorder where statusid > 0) b \n ");
			sbSQL.append(" where 1=1 \n ");
			sbSQL.append(" and a.statusid in (1,2,3) \n ");
			sbSQL.append(" and b.applyformid(+) = a.id \n ");
			sbSQL.append(" and b.transactiontypeid(+) = a.transactiontypeid \n ");
			sbSQL.append(" and b.counterpartid(+) = a.counterpartid \n ");
			sbSQL.append(" and a.transactiontypeid = " + transactionTypeID + " \n ");
			sbSQL.append(" and a.counterpartid = " + counterpartID + " \n ");
			log4j.debug(sbSQL.toString());
			
			conForApply = Database.getConnection();
			psForApply = conForApply.prepareStatement(sbSQL.toString());
			rsForApply = psForApply.executeQuery();

			while(rsForApply != null && rsForApply.next())
			{
				PledgeForDetail data = new PledgeForDetail();
				data.setApplyId(rsForApply.getLong("applyId"));
				data.setStasusId(rsForApply.getLong("statusid"));
				data.setPledgeUsedByApply(rsForApply.getDouble("a1") - rsForApply.getDouble("a2"));
				
				//根据所得的申请书id计算出此申请书下的所有交割单占用的额度的合计,并设置到data中去
				sbSQL.setLength(0);
				sbSQL.append(" select \n ");
				sbSQL.append(" b.applyformid, \n ");
				sbSQL.append(" a.statusid, \n ");
				sbSQL.append(" sum(decode(b.transactiontypeid,1301,b.pledgesecuritiesamount,1303,b.pledgesecuritiesamount,0)) as b1, \n ");
				sbSQL.append(" sum(decode(b.transactiontypeid,1302,b.pledgesecuritiesamount,1304,b.pledgesecuritiesamount,0)) as b2 \n ");
				sbSQL.append(" from sec_applyform a , sec_deliveryorder b \n ");
				sbSQL.append(" where 1=1 \n ");
				if(data.getApplyId() != -1)
				{
					sbSQL.append(" and b.applyformid = " + data.getApplyId() + " \n ");
				}
				if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
				{
					sbSQL.append(" and b.transactiontypeid in ( " + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN + "," + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY + " ) \n ");
				}
				if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					sbSQL.append(" and b.transactiontypeid in (" + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT + "," + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY + " ) \n ");
				}
				sbSQL.append(" and b.applyformid = a.id(+) \n ");
				sbSQL.append(" and b.counterpartid = " + counterpartID + " \n ");
				sbSQL.append(" and b.StatusID > 0 \n ");
				sbSQL.append(" group by applyformid,a.statusid \n ");
				log4j.debug(sbSQL.toString());
				
				conForDeliver = Database.getConnection();
				psForDeliver = conForDeliver.prepareStatement(sbSQL.toString());
				rsForDeliver = psForDeliver.executeQuery();
				if(rsForDeliver != null && rsForDeliver.next())
				{
					data.setApplyId(rsForDeliver.getLong("applyformid"));
					data.setStasusId(rsForDeliver.getLong("statusid"));
					data.setPledgeUsedByDeliver(rsForDeliver.getDouble("b1") - rsForDeliver.getDouble("b2"));
				}
				if(rsForDeliver != null)
				{
					rsForDeliver.close();
					rsForDeliver = null;
				}
				if(psForDeliver != null)
				{
					psForDeliver.close();
					psForDeliver = null;
				}
				if(conForDeliver != null)
				{
					conForDeliver.close();
					conForDeliver = null;
				}
				arrayList.add(data);
			}

			//如果根据申请书所得的数据为空,则根据交割单得
			if(arrayList.size() == 0)
			{
				sbSQL.setLength(0);
				sbSQL.append(" select \n ");
				sbSQL.append(" b.applyformid, \n ");
				sbSQL.append(" a.statusid, \n ");
				sbSQL.append(" sum(decode(b.transactiontypeid,1301,b.pledgesecuritiesamount,1303,b.pledgesecuritiesamount,0)) as b1, \n ");
				sbSQL.append(" sum(decode(b.transactiontypeid,1302,b.pledgesecuritiesamount,1304,b.pledgesecuritiesamount,0)) as b2 \n ");
				sbSQL.append(" from sec_applyform a , sec_deliveryorder b \n ");
				sbSQL.append(" where 1=1 \n ");
				if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN)
				{
					sbSQL.append(" and b.transactiontypeid in ( " + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN + "," + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY + " ) \n ");
				}
				if(transactionTypeID == SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT)
				{
					sbSQL.append(" and b.transactiontypeid in (" + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT + "," + SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY + " ) \n ");
				}
				sbSQL.append(" and b.applyformid = a.id(+) \n ");
				sbSQL.append(" and b.counterpartid = " + counterpartID + " \n ");
				sbSQL.append(" and b.StatusID > 0 \n ");
				sbSQL.append(" group by applyformid,a.statusid \n ");
				log4j.debug(sbSQL.toString());
					
				conForDeliver = Database.getConnection();
				psForDeliver = conForDeliver.prepareStatement(sbSQL.toString());
				rsForDeliver = psForDeliver.executeQuery();
				while(rsForDeliver != null && rsForDeliver.next())
				{
					PledgeForDetail data = new PledgeForDetail();
					data.setApplyId(rsForDeliver.getLong("applyformid"));
					data.setStasusId(rsForDeliver.getLong("statusid"));
					data.setPledgeUsedByDeliver(rsForDeliver.getDouble("b1") - rsForDeliver.getDouble("b2"));
					arrayList.add(data);
				}
				if(rsForDeliver != null)
				{
					rsForDeliver.close();
					rsForDeliver = null;
				}
				if(psForDeliver != null)
				{
					psForDeliver.close();
					psForDeliver = null;
				}
				if(conForDeliver != null)
				{
					conForDeliver.close();
					conForDeliver = null;
				}
			}
			if(rsForApply != null)
			{
				rsForApply.close();
				rsForApply = null;
			}
			if(psForApply != null)
			{
				psForApply.close();
				psForApply = null;
			}
			if(conForApply != null)
			{
				conForApply.close();
				conForApply = null;
			}
			
			
		}		
		catch(SQLException sqlEx)
		{
			sqlEx.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return arrayList;
	}
	
	

}

/*
 * Created on 2006-3-21
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.util.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;

import java.sql.*;

/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransChangeFixedDepositDAO extends SettlementDAO 
{
	//主要是为了查询结果后的排序用
	public final static int ORDERBY_TRANSACTIONNOID = 0;  //交易号
	public final static int ORDERBY_ACCOUNTNO = 1;     //定期账户号
	public final static int ORDERBY_CLIENTNAME = 2;    //定期客户名称
	public final static int ORDERBY_DEPOSITNO = 3;    //定期存款单据号
	public final static int ORDERBY_CURRENTACCOUNTNO = 4;    //活期账户号	
	public final static int ORDERBY_BILLNO = 5;    //票据号	
	public final static int ORDERBY_CONSIGNVOUCHERNO = 6;  //委托付款凭证号
	public final static int ORDERBY_BANKNAME = 7;    //开户行	
	public final static int ORDERBY_AMOUNT = 8;            //金额
	public final static int ORDERBY_STARTDATE = 9;   //起始日
	public final static int ORDERBY_ENDDATE = 10;     //到期日
	public final static int ORDERBY_STATUSID = 11;    //状态	
	public final static int ORDERBY_ABSTRACT = 12;     //摘要
	
	
	public final static int ORDERBY_DEPOSITBILLNO = 13;    //换开定期存单号
	public final static int ORDERBY_DEPOSITBILLSTATUS = 14;    //换开定期存单的状态ID
	//这里考虑是否要添加
	
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	/**
	 * 修改定期（通知）换开定期存单交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedOpenInfo, 定期（通知）开立交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransFixedChangeInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			String strSQL="";
			
			buffer.append("update sett_TransOpenFixedDeposit set \n");
			
			//增加定期存单里的方法
			if(info.getDepositBillNO()!=null && !info.getDepositBillNO().equals(""))
				buffer.append(" sDepositBillNO='"+info.getDepositBillNO()+"',");
			
			if(info.getDepositBillStatusID()!=-1)
				buffer.append(" nDepositBillStatusID="+info.getDepositBillStatusID()+",");
			
			if(info.getDepositBillInputUserID()!=-1)
				buffer.append(" nDepositBillInputUserID="+info.getDepositBillInputUserID()+",");
			
			if(info.getDepositBillCheckUserID()!=-1)
				buffer.append(" nDepositBillCheckUserID="+info.getDepositBillCheckUserID()+",");
			
			
			if(info.getDepositBillInputDate()!=null){
				buffer.append(" dtDepositBillInputDate= to_date('"+DataFormat.getDateString(info.getDepositBillInputDate())+"','yyyy-mm-dd'),");
				System.out.println("得到的换开定期存单输入日期为:"+DataFormat.getDateString(info.getDepositBillInputDate()));
			}
			
			if(info.getDepositBillCheckDate()!=null){
				System.out.println("得到的换开定期存单复核日期为:"+DataFormat.getDateString(info.getDepositBillCheckDate()));
				buffer.append(" dtDepositBillCheckDate= to_date('"+DataFormat.getDateString(info.getDepositBillCheckDate())+"','yyyy-mm-dd'),");
			}
			
			if(info.getDepositBillABSTRACT()!=null && !info.getDepositBillABSTRACT().equals(""))
				buffer.append(" SDepositBillABSTRACT='"+info.getDepositBillABSTRACT()+"',");
			
			if(info.getDepositBillCHECKABSTRACT()!=null && !info.getDepositBillCHECKABSTRACT().equals(""))
				buffer.append(" SDepositBillCHECKABSTRACT='"+info.getDepositBillCHECKABSTRACT()+"',");
			
			buffer.append(" dtModify=sysdate,");
			
			log.info("==未截取字符前的SQL:语句:"+buffer.toString()+".");
			strSQL=buffer.substring(0,buffer.length()-1);
			log.info("==截取两个字符后的SQL:语句:"+strSQL+".");

			strSQL=strSQL+" where ID=? ";

			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			//ID
			ps.setLong(1, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 根据标识查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedChangeInfo findByID(long lID) throws Exception {
		TransFixedChangeInfo info = new TransFixedChangeInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
	 * 根据交易号查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param  strTransNo String , 交易号
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedChangeInfo findByTransNo(String strTransNo) throws Exception {
		TransFixedChangeInfo info = new TransFixedChangeInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where sTransNo=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1,strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
		 * 根据条件查询存款单据号是否重复的方法：
		 * 逻辑说明：
		 * 
		 * @param FixedOpenInfo, 定期（通知）交易实体类
		 * @return boolean false 重复
		 * @throws Exception
		 */
		//检查换开定期存单
		public boolean checkDepositNo(TransFixedChangeInfo info) throws Exception 
		{			
			Connection con = getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			boolean rtnFlg = true;
			try 
			{	
				//检查时要判断此 换开定期存单号  在数据库中是否已经存在
				
				StringBuffer buffer = new StringBuffer();
				/*
				buffer.append("select sDepositNo from sett_TransOpenFixedDeposit where \n");
				buffer.append("sDepositNo=? and nAccountID=? and ID<>? and \n");
				buffer.append("nStatusID <> ? \n");
				buffer.append("union select sDepositNo from sett_TransFixedContinue where \n");
				buffer.append("sNewDepositNo=? and nAccountID=? and \n");
				buffer.append("nStatusID <> ?");
				*/
				//不同的账户的换开定期存单号可以相同
				buffer.append(" select sDepositBillNO from sett_TransOpenFixedDeposit where \n ");
				buffer.append(" sDepositBillNO=? and nAccountID=? and ID<>? \n");
				buffer.append(" and nStatusID <> ? \n");
				buffer.append(" and nDepositBillStatusID <> ? \n");
				buffer.append(" and NOFFICEID = ? \n");
				buffer.append(" and NCURRENCYID = ? \n");
				
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index=1;
				ps.setString(index++, info.getDepositBillNO());	//换开定期存单号
				ps.setLong(index++, info.getAccountID());		//定期开立的账户ID
				ps.setLong(index++, info.getID());				//定期开立的ID
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//删除
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//删除
				ps.setLong(index++, info.getOfficeID());	//办事处
				ps.setLong(index++, info.getCurrencyID());	//币种
				
				rs = ps.executeQuery();
				if (rs.next()) 
				{
					rtnFlg=false;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			} 
			finally 
			{
				
				try 
				{
					cleanup(rs);
					cleanup(ps);
					cleanup(con);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					throw e;
				}
			}
			return rtnFlg;
		}
	
	/**
	 * 修改换开定期存单交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 本金交易标识
	 * @param nDepositBillStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("删除换开定期存单----(开始DAO)");
			StringBuffer buffer = new StringBuffer();
			buffer.append(
				"update sett_TransOpenFixedDeposit" +
				" set nDepositBillStatusID=? ," +
				" NDEPOSITBILLINPUTUSERID = -1," +
				" NDEPOSITBILLCHECKUSERID = -1," +
				" DTDEPOSITBILLINPUTDATE = ''," +
				" DTDEPOSITBILLCHECKDATE = ''," +
				" SDEPOSITBILLABSTRACT = ''," +
				" SDEPOSITBILLCHECKABSTRACT = ''," +
				" SDEPOSITBILLNO=''" +
				"where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("删除换开定期存单----(结束DAO)");
		return lReturn;
	}
	/**
	 * 设置定期交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private TransFixedChangeInfo getOpenDeposit(
		TransFixedChangeInfo info,
		ResultSet rs)
		throws Exception {
		info = new TransFixedChangeInfo();
		try {
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setClientID(rs.getLong("nClientID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setDepositNo(rs.getString("sDepositNo"));
			info.setCertificationBankID(rs.getLong("nCertificationBankID"));
			info.setRate(rs.getDouble("mRate"));
			info.setStartDate(rs.getTimestamp("dtStart"));
			info.setEndDate(rs.getTimestamp("dtEnd"));
			info.setDepositTerm(rs.getLong("nDepositTerm"));
			info.setInterestPlanID(rs.getLong("nInterestPlanID"));
			info.setNoticeDay(rs.getLong("nNoticeDay"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setConsignVoucherNo(rs.getString("sConsignVoucherNo"));
			info.setConsignPassword(rs.getString("sConsignPassword"));
			info.setBillNo(rs.getString("sBillNo"));
			info.setBillTypeID(rs.getLong("nBillTypeID"));
			info.setBillBankID(rs.getLong("nBillBankID"));
			info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtBankNo(rs.getString("sExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			
			//增加定期存单里的方法
			info.setDepositBillNO(rs.getString("sDepositBillNO"));
			info.setDepositBillStatusID(rs.getLong("nDepositBillStatusID"));
			info.setDepositBillInputUserID(rs.getLong("nDepositBillInputUserID"));
			info.setDepositBillCheckUserID(rs.getLong("nDepositBillCheckUserID"));
			info.setDepositBillInputDate(rs.getTimestamp("dtDepositBillInputDate"));
			info.setDepositBillCheckDate(rs.getTimestamp("dtDepositBillCheckDate"));
			info.setDepositBillABSTRACT(rs.getString("SDepositBillABSTRACT"));
			info.setDepositBillCHECKABSTRACT(rs.getString("SDepositBillCHECKABSTRACT"));
			info.setIsAutoContinue(rs.getLong("IsAutoContinue"));
			info.setAutocontinuetype(rs.getLong("Autocontinuetype"));
			info.setAutocontinueaccountid(rs.getLong("Autocontinueaccountid"));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	
	//--------------此方法需要研究一下
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
	{
		System.out.println("换开定期存单(findByStatus):----------(开始DAO)");
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			if(info.getStatus()!=null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			
			//排序条件
			String order = getOrderString(info);			
			//业务处理
			if (info.getTypeID() == 0) 
			{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
								
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nDepositBillInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedChangeInfo resultInfo = new TransFixedChangeInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nDepositBillCheckUserID=? and DTDEPOSITBILLCHECKDATE=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedChangeInfo resultInfo = new TransFixedChangeInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			 catch (Exception e)
			 {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("换开定期存单(findByStatus):----------(结束DAO)");
		return arrResult;

	}
	
	//判断"定期开力"业务是否是"换开定期存单"操作 add boxu 2007-9-3
	public boolean findByDepositBill(long lID, String transNO) throws Exception
	{
		boolean IsDepositBill = false;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer buffer = new StringBuffer();
		
			buffer.append(" select * \n");
			buffer.append(" from sett_TransOpenFixedDeposit \n");
			buffer.append(" where \n");
			buffer.append(" id=? \n");
			buffer.append(" and stransno=? and nTransActionTypeID="+SETTConstant.TransactionType.OPENFIXEDDEPOSIT+" \n");
			buffer.append(" and NDEPOSITBILLSTATUSID in ("+SETTConstant.TransactionStatus.SAVE+", "+SETTConstant.TransactionStatus.CHECK+") and sdepositbillno is not null \n");

			ps = con.prepareStatement(buffer.toString());
			
			int index = 1;
			ps.setLong(index++, lID);
			ps.setString(index++, transNO);		
			
			rs = ps.executeQuery();
			
			if(rs.next()) 
			{
				IsDepositBill = true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}

		return IsDepositBill;
	}
	
	//得到查询状态组合的条件SQL
	private String getQueryString(QueryByStatusConditionInfo info) 
	{
		String query;
		query =" nDepositBillStatusID=";
		for(int i=0;i<info.getStatus().length;i++)
		{									
			if(i<info.getStatus().length -1)
			{	
								
				query= query+ info.getStatus()[i] + " or nDepositBillStatusID=";
			}
			else
			{
				query= query+ info.getStatus()[i];
			}
		}	
		return query;
	}
	
	//升降序的排列条件SQL
	private String getOrderString(QueryByStatusConditionInfo info) 
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSACTIONNOID :
			{
				order=" ORDER BY sTransNo ";					
			}	
				break;
			case ORDERBY_ACCOUNTNO :
			{
				order=" ORDER BY nAccountID ";					
			}
				break;
			case ORDERBY_CLIENTNAME :
			{
				order=" ORDER BY nClientID ";					
			}
				break;				
			case ORDERBY_DEPOSITNO :
			{
				order=" ORDER BY sDepositNo ";					
			}
				break;
			case ORDERBY_CURRENTACCOUNTNO :
			{
				order=" ORDER BY nCurrentAccountID ";					
			}
				break;				
			case ORDERBY_CONSIGNVOUCHERNO :
			{
				order=" ORDER BY sConsignVoucherNo ";					
			}
				break;
			case ORDERBY_BILLNO :
			{
				order=" ORDER BY sBillNo ";					
			}
				break;	
			case ORDERBY_BANKNAME :
			{
				order=" ORDER BY nBankID ";					
			}
				break;			
			case ORDERBY_AMOUNT :
			{
				order=" ORDER BY mAmount ";					
			}
				break;
			case ORDERBY_STARTDATE :
			{
				order=" ORDER BY dtStart ";					
			}
				break;
			case ORDERBY_ENDDATE :
			{
				order=" ORDER BY dtEnd ";					
			}
				break;
			case ORDERBY_ABSTRACT :
			{
				order=" ORDER BY sAbstract ";					
			}
				break;
			case ORDERBY_STATUSID :
			{
				order=" ORDER BY nStatusID ";					
			}
				break;
			case ORDERBY_DEPOSITBILLNO :
			{
				order=" ORDER BY SDEPOSITBILLNO ";					
			}
				break;
			case ORDERBY_DEPOSITBILLSTATUS :
			{
				order=" ORDER BY NDEPOSITBILLSTATUSID ";					
			}
				break;
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if(isNeedOrderBy)
		{
			if (info.isDesc())
				order= order +" DESC \n";
			else
				order= order +" ASC \n";		
		}
		else
		{
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}
	/**
	 * 复核匹配的方法：
	 * 逻辑说明：
	 * 
	 * @param FixedOpenInfo,定期（通知）存款交易复核匹配查询条件实体类
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	//这个方法也许也点小变动
	public Collection match(TransFixedChangeInfo info) throws Exception {
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("-----换开定期存单复核匹配--(开始DAO)");
			StringBuffer buffer = null;
				
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT) 
			{
				buffer = new StringBuffer();
				buffer.append("select * from sett_TransOpenFixedDeposit \n");
					//buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					//buffer.append("and nInputUserID <>? and nAccountID=? and \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and NDEPOSITBILLSTATUSID=? \n");
				buffer.append("and nDepositBillInputUserID <>? and nAccountID=? and \n");
				buffer.append("dtStart=? and nDepositTerm=? and mRate=? and \n");
				buffer.append("nCurrentAccountID=? \n");	
					//buffer.append("nCurrentAccountID=? and sConsignVoucherNo=?  \n");
				buffer.append("and nBankID=? and nCashFlowID=? and mAmount=?\n");
				buffer.append("and SDEPOSITBILLNO=? \n");
				buffer.append("order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				log.info("info.getOfficeID():"+info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				log.info("info.getCurrencyID():"+info.getCurrencyID());
					//ps.setLong(index++, info.getStatusID());
					//log.info("info.getStatusID():"+info.getStatusID());
					//ps.setLong(index++, info.getInputUserID());
					//log.info("info.getInputUserID():"+info.getInputUserID());
				ps.setLong(index++, info.getDepositBillStatusID());
				log.info("info.getDepositBillStatusID():"+info.getDepositBillStatusID());
				ps.setLong(index++, info.getDepositBillInputUserID());
				log.info("info.getDepositBillInputUserID():"+info.getDepositBillInputUserID());
				ps.setLong(index++, info.getAccountID());
				log.info("info.getAccountID():"+info.getAccountID());		//定期账号ID
					//ps.setLong(index++, info.getClientID());
				ps.setTimestamp(index++, info.getStartDate());
				log.info("info.getStartDate():"+info.getStartDate());
				ps.setLong(index++, info.getDepositTerm());
				log.info("info.getDepositTerm():"+info.getDepositTerm());		//定期存款期限
				ps.setDouble(index++, info.getRate());
				log.info("info.getRate():"+info.getRate());						//定期存款利率
				ps.setLong(index++, info.getCurrentAccountID());
				log.info("info.getCurrentAccountID():"+info.getCurrentAccountID());		//付活期账户ID
					//ps.setString(index++, info.getConsignVoucherNo());
				ps.setLong(index++, info.getBankID());
				log.info("info.getBankID():"+info.getBankID());
				ps.setLong(index++, info.getCashFlowID());
				log.info("info.getCashFlowID():"+info.getCashFlowID());
				ps.setDouble(index++, info.getAmount());
				log.info("info.getAmount():"+info.getAmount());

				ps.setString(index++, info.getDepositBillNO());					//换开定期存单的号
				log.info("info.getDepositBillNO():"+info.getDepositBillNO());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransFixedChangeInfo depositInfo =
						new TransFixedChangeInfo();
					depositInfo = getOpenDeposit(depositInfo, rs);
					arrResult.add(depositInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {

				cleanup(rs);
				cleanup(ps);
				cleanup(con);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		System.out.println("-----换开定期存单复核匹配--(结束DAO)");
		return arrResult;
	}
	
	
	//找出定期开立己复核并且换开定期存单的状态为空或者为己复核的定期开立数据，用换开定期存单类进行封装即可
	public Collection findForOpenCheck(QueryByStatusConditionInfo qInfo) throws Exception {
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			//排序条件
			String order = getOrderString(qInfo);
			
			//nDepositBillStatusID数据在定期开立增加的时候应该就默认为-1  在数据库里的default里设置为-1 而现在没有设置,需告诉胡志强2006.3.26
			
			
			//真实的SQL查询语句
			//boxu 2007-11-15 update 关联子账户,过滤已结清存单数据
			String strSQL =
				"select * from (select distinct a.* from sett_TransOpenFixedDeposit a, sett_subaccount b "
				+" where a.nStatusID=? and (a.nDepositBillStatusID is null  or a.nDepositBillStatusID= ? or a.nDepositBillStatusID= -1 )"
				+" and a.NTRANSACTIONTYPEID = ? "
				+" and a.NOFFICEID = ? "
				+" and a.NCURRENCYID = ? "
				+" and a.sdepositno = b.af_sdepositno "
				+" and b.nstatusid = "+SETTConstant.SubAccountStatus.NORMAL+" )"  //未结清
				+ ""+ order + "";
			
			ps = con.prepareStatement(strSQL);
			int index = 1;
			ps.setLong(index++, SETTConstant.TransactionStatus.CHECK);		//复核
			ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);	//删除
			ps.setLong(index++, qInfo.getTransactionTypeID());		//交易类型
			ps.setLong(index++, qInfo.getOfficeID());		//办事处
			ps.setLong(index++, qInfo.getCurrencyID());		//币种
			
			rs = ps.executeQuery();
			Log.print("   --Log: "+strSQL);
			
			while (rs.next()) {
				TransFixedChangeInfo depositInfo =
					new TransFixedChangeInfo();
				depositInfo = getOpenDeposit(depositInfo, rs);
				arrResult.add(depositInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;
	}
	
	/**
	 * 修改换开定期存单交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 本金交易标识
	 * @param nDepositBillStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateForCheck(TransFixedChangeInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println("复核和取消复核换开定期存单  sett_subAccount----(开始DAO)");
			StringBuffer buffer = new StringBuffer();
			buffer.append(
				"update sett_subAccount" +
				" set AF_SDEPOSITBILLNO=? " +
				" where nAccountID=?" +
				" and AF_SDEPOSITNO=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setString(index++, info.getDepositBillNO());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			System.out.println("   ==========  SQL:"+buffer.toString());
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("复核和取消复核换开定期存单  sett_subAccount----(结束DAO)");
		return lReturn;
	}
}

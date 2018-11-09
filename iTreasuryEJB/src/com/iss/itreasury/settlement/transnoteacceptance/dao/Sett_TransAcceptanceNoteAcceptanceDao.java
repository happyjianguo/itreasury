/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transnoteacceptance.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dao.LoanInformDao;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dataentity.LoanInformInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.TransAcceptanceNoteAcceptanceInfo;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author feiye 商票承兑到期承兑交易的--到期承兑--DAO类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class Sett_TransAcceptanceNoteAcceptanceDao extends SettlementDAO
{

	public final static int	ORDERBY_TRANSNO		= 0;	//交易号	
	
	public final static int	ORDERBY_CONTRACTID			= 1;	//贷款合同号											

	public final static int	ORDERBY_RECEIVEFORMID			= 2;//通知单编号												

	public final static int	ORDERBY_PAYACCOUNTID			= 3;	//付款方帐户											

	public final static int	ORDERBY_RECEVICEACCOUNTID	= 4;	//收款方帐户											

	public final static int	ORDERBY_ACCEPTANCEAMOUNT				= 5;//承兑金额	
	
	public final static int	ORDERBY_ISADVANCED				= 6;//是否垫付

	public final static int	ORDERBY_INTERESTSTART				= 8;	//起息日											

	public final static int	ORDERBY_EXECUTE			= 9;		//执行日												

	public final static int	ORDERBY_ABSTRACT				= 10;	//摘要											

	public final static int	ORDERBY_STATUSID			= 11;		//状态										
	
	/**
	 * 日志添加
	 */
	private Log4j			log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);


	/**
	 * 商票承兑到期承兑交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransAcceptanceNoteAcceptanceInfo, 商票承兑到期承兑交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception
	 *             不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 */
	public long add(TransAcceptanceNoteAcceptanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 利用数据库的序列号取ID;
			long id = super.getSett_AcceptanceNoteAcceptanceDetailId();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO Sett_TransAcceptanceNote \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nStatusID,dtModify, \n");
			buffer.append("dtInput,nInputUserID,nCheckUserID, \n");
			buffer.append("nAbstractID,sAbstract,sCheckAbstract,nContractID, \n");
			buffer.append("nReceiveFormID,nAcceptanceReceiveAccountID, \n");
			buffer.append("SEXTACCOUNTNO,SEXTCLIENTNAME,SREMITINBANK, SREMITINPROVINCE,SREMITINCITY,\n");
			buffer.append("nAcceptancePayAccountID,nAcceptancePayBankID, \n");
			buffer.append("nAcceptanceAmount,nIsAdvanced, \n");
			buffer.append("dtInterestStart,dtExecute) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,sysdate,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(" 新增商票承兑到期承兑交易:  \n" + buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			//业务信息
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getAcceptanceFormID());
			ps.setLong(index++, info.getAcceptanceReceiveAccountID());
			ps.setString(index++, info.getSExtAccountNO());
			ps.setString(index++, info.getSExtClientName());
			ps.setString(index++, info.getSRemitinBank());
			ps.setString(index++, info.getSRemitinProvince());
			ps.setString(index++, info.getSRemitinCity());
			ps.setLong(index++, info.getAcceptancePayAccountID());
			ps.setLong(index++, info.getAcceptancePayBankID());
			ps.setDouble(index++, info.getAcceptanceAmount());
			ps.setLong(index++, info.getIsAdvanced());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 修改融资租赁收款交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            SettTransReceiveFinanceInfo, 融资租赁 收款交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransAcceptanceNoteAcceptanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update Sett_TransAcceptanceNote set \n");
			buffer.append(" nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append(" nTransactionTypeID=?,nStatusID=?,dtModify=sysdate,dtInput=?,\n");
			buffer.append(" nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,\n");
			buffer.append(" sCheckAbstract=?,nContractID=?,nReceiveFormID=?,\n");
			buffer.append(" nAcceptanceReceiveAccountID=?,SEXTACCOUNTNO=?,SEXTCLIENTNAME=?,SREMITINBANK=?,\n");
			buffer.append(" SREMITINPROVINCE=?,SREMITINCITY=?,nAcceptancePayAccountID=?,nAcceptancePayBankID=?,\n");
			buffer.append(" nAcceptanceAmount=?,nIsAdvanced=?,dtInterestStart=?,dtExecute=?\n");
			buffer.append(" where ID=? \n");
			
			ps = con.prepareStatement(buffer.toString());
			log.info("修改商票承兑到期承兑交易 :  \n " + buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getAcceptanceFormID());
			ps.setLong(index++, info.getAcceptanceReceiveAccountID());
			ps.setString(index++, info.getSExtAccountNO());
			ps.setString(index++, info.getSExtClientName());
			ps.setString(index++, info.getSRemitinBank());
			ps.setString(index++, info.getSRemitinProvince());
			ps.setString(index++, info.getSRemitinCity());
			ps.setLong(index++, info.getAcceptancePayAccountID());
			ps.setLong(index++, info.getAcceptancePayBankID());
			ps.setDouble(index++, info.getAcceptanceAmount());
			ps.setLong(index++, info.getIsAdvanced());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 根据标识查询商票承兑到期承兑交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 商票承兑到期承兑交易实体类
	 * @throws Exception
	 */
	public TransAcceptanceNoteAcceptanceInfo findByID(long lID) throws Exception
	{

		TransAcceptanceNoteAcceptanceInfo info = new TransAcceptanceNoteAcceptanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransAcceptanceNote where id=? ";
			log.info("根据标识查询商票承兑到期承兑交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getAcceptanceNoteAcceptance(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}


	/**
	 * 根据交易号查询商票承兑到期承兑交易明细的方法： 逻辑说明：
	 * 
	 * @param strTransNo
	 *            String , 交易号
	 * @return TransAcceptanceNoteAcceptanceInfo, 商票承兑到期承兑交易实体类
	 * @throws Exception
	 */
	public TransAcceptanceNoteAcceptanceInfo findByTransNo(String strTransNo) throws Exception
	{

		TransAcceptanceNoteAcceptanceInfo info = new TransAcceptanceNoteAcceptanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from  Sett_TransAcceptanceNote  where sTransNo=? ";
			log.info("根据交易号查询商票承兑到期承兑交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getAcceptanceNoteAcceptance(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}

	/**
	 * 修改商票承兑到期承兑交易状态的方法： 逻辑说明：
	 * 
	 * @param lID,
	 *            long, 本金交易标识
	 * @param lStatusID,
	 *            long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Sett_TransAcceptanceNote set nStatusID=? where ID=?");
			log.info("修改商票承兑到期承兑交易状态 : \n" + buffer.toString());
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 设置商票承兑到期承兑交易结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private TransAcceptanceNoteAcceptanceInfo getAcceptanceNoteAcceptance(TransAcceptanceNoteAcceptanceInfo info, ResultSet rs) throws Exception
	{
		info = new TransAcceptanceNoteAcceptanceInfo();
		try {
			//基本信息
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			
			//商票承兑业务信息--到期承兑
			info.setContractID(rs.getLong("NCONTRACTID"));		//合同ID（loan_contractForm表）
			info.setAcceptanceFormID(rs.getLong("NReceiveFormID"));	//收款通知单ID
			info.setAcceptanceReceiveAccountID(rs.getLong("nAcceptanceReceiveAccountID"));	//
			info.setSExtAccountNO(rs.getString("SEXTACCOUNTNO"));
			info.setSExtClientName(rs.getString("SEXTCLIENTNAME"));
			info.setSRemitinBank(rs.getString("SREMITINBANK"));
			info.setSRemitinProvince(rs.getString("SREMITINPROVINCE"));
			info.setSRemitinCity(rs.getString("SREMITINCITY"));
			info.setAcceptancePayAccountID(rs.getLong("nAcceptancePayAccountID"));	//
			info.setAcceptancePayBankID(rs.getLong("nAcceptancePayBankID"));	//
			info.setAcceptanceAmount(rs.getDouble("nAcceptanceAmount"));	//本次承兑金额
			info.setIsAdvanced(rs.getLong("nIsAdvanced"));					//
			info.setExecuteDate(rs.getTimestamp("dtExecute"));				//业务执行日
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));	//融资租赁收款起息日


			//合同
			if(info.getContractID()>0){
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(info.getContractID());

				info.setClientName(NameRef.getClientNameByID(contractInfo.getDiscountClientID()));//承兑客户名称
				info.setContractID(contractInfo.getContractID());	//合同ID
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setClientID(contractInfo.getDiscountClientID());//承兑客户ID
				info.setContractStartDate(contractInfo.getLoanStart());//承兑开始日期
				info.setContractEndDate(contractInfo.getLoanEnd());//承兑结束日期
			}
			//收款单
			if(info.getAcceptanceFormID()>0){
				//查询INFO
				LoanInformInfo loanInformInfoQuery=new LoanInformInfo();
				
				loanInformInfoQuery.setId(info.getAcceptanceFormID());
				
				//update dwj 20090310
				//loanInformInfoQuery.setNnoteTypeId(2);//到期承兑类型
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.ADVANCEDRECEVICENOTEACCEPTANCE)
				{
					loanInformInfoQuery.setNnoteTypeId(LOANConstant.Inform.DFBXSHH);
				}
				else
				{
					loanInformInfoQuery.setNnoteTypeId(LOANConstant.Inform.DQCHD);
				}
				//end 

				LoanInformDao loanInformDao=new LoanInformDao();
				LoanInformInfo loanInformInfoReturn=new LoanInformInfo();
				loanInformInfoReturn=loanInformDao.findByIDInform(loanInformInfoQuery);
				
				//update by dwj 20090313
				if(loanInformInfoReturn!=null)
				{
					//设置返回值
					info.setAcceptanceFormID(loanInformInfoReturn.getId());	//通知单ID
					info.setAcceptanceFormCode(loanInformInfoReturn.getScode());	//通知单code
					info.setAcceptanceFormStartDate(loanInformInfoReturn.getDtOutdate());	//通知单开始日期
					if(info.getID()<0)
					{
						info.setAcceptanceAmount(loanInformInfoReturn.getNamount());	//本次承兑金额
					}
				}
				info.setLoanInformInfoReturn(loanInformInfoReturn);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}

	/**
	 * 根据状态查询的方法： 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo ,
	 *            按状态查询的查询条件实体类。
	 * @return Collection ,包含TransAcceptanceNoteAcceptanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
	{
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
				buffer.append("from Sett_TransAcceptanceNote \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				System.out.println("sql :  " + buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransAcceptanceNoteAcceptanceInfo resultInfo = new TransAcceptanceNoteAcceptanceInfo();
					
					resultInfo = getAcceptanceNoteAcceptance(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from Sett_TransAcceptanceNote \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				System.out.println("sql :  " + buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransAcceptanceNoteAcceptanceInfo resultInfo = new TransAcceptanceNoteAcceptanceInfo();
					
					resultInfo = getAcceptanceNoteAcceptance(resultInfo, rs);
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
		return arrResult;
	}

	private String getQueryString(QueryByStatusConditionInfo info)
	{

		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++) {
			if (i < info.getStatus().length - 1) {

				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else {
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}

	//排序条件
	private String getOrderString(QueryByStatusConditionInfo info)
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSNO :
			{
				order = " ORDER BY sTransNo ";	//交易号
			}
				break;
			case ORDERBY_CONTRACTID :
			{
				order = " ORDER BY nContractID ";	//贷款合同号
			}
				break;
			case ORDERBY_RECEIVEFORMID :
			{
				order = " ORDER BY NRECEIVEFORMID ";		//收款通知单编号
			}
				break;
			case  ORDERBY_PAYACCOUNTID:
			{
				order = " ORDER BY NACCEPTANCEPAYACCOUNTID ";	//付款内部帐户
			}
				break;
			case  ORDERBY_RECEVICEACCOUNTID:
			{
				order = " ORDER BY NACCEPTANCERECEIVEACCOUNTID ";	//收款内部帐户
			}
				break;
			case  ORDERBY_ISADVANCED:
			{
				order = " ORDER BY NACCEPTANCEAMOUNT ";	//是否垫付
			}
				break;
			case  ORDERBY_ACCEPTANCEAMOUNT:
			{
				order = " ORDER BY NACCEPTANCEAMOUNT ";	//承兑金额
			}
				break;
				
			
			case ORDERBY_INTERESTSTART :			//起息日
			{
				order = " ORDER BY dtInterestStart ";
			}
				break;
			case ORDERBY_EXECUTE :		//执行日	
			{
				order = " ORDER BY dtExecute ";
			}
				break;
			case ORDERBY_ABSTRACT :			//摘要
			{
				order = " ORDER BY SABSTRACT ";
			}
				break;
			case ORDERBY_STATUSID :			//状态
			{
				order = " ORDER BY NSTATUSID ";
			}
				break;
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if (isNeedOrderBy) {
			if (info.isDesc())
				order = order + " DESC \n";
			else
				order = order + " ASC \n";
		}
		else {
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}


	/**
	 * 复核匹配的方法： 逻辑说明：
	 * 
	 * @param TransAcceptanceNoteAcceptanceInfo,商票承兑到期承兑交易复核匹配查询条件实体类
	 * @return Collection ,包含TransAcceptanceNoteAcceptanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransAcceptanceNoteAcceptanceInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;
			/**
			 * 	融资租赁收款			基本信息：
			 * 承租单位编号						要不要，值得考虑一下
			 * 合同号ID				NCONTRACTID
			 * 收款通知单ID			NReceiveFormID
			 * 
			 * 保存金帐户编号			nReceiveBailAccountID		
			 * 保证金来源-活期帐户		nPayBailAccountID
			 * 保证金来源-银行收款		nPayBailBankID
			 * 保证金是否计息			nIsBailInterest
			 * 保证金的起息日			dtInterestStart
			 * 保证金金额				mBailAmount
			 * 
			 * 手续费来源-活期帐户		nPayPoundageAccountID
			 * 手续费来源-银行收款		nPayPoundageBankID
			 * 手续费金额				mPoundageAmount
			 * 
			 * 承兑收款帐户ID（收内）   nAcceptanceReceiveAccountID    如果是到期承兑，收款人是内部账户时，则保存到此字段
			 *                                						如果是垫付本息收回，则不使用此字段
			 * 汇入行帐号             SEXTACCOUNTNO				如果是到期承兑，收款人是外部账户时，保存到此5个字段
			 * 汇入行帐号名称			SEXTCLIENTNAME				如果是垫付本息收回时，不使用此字段
			 * 汇入行名称				SREMITINBANK
			 * 汇入地（省）			SREMITINPROVINCE
			 * 汇入地（市）			SREMITINCITY
			 * 
			 * 承兑付款帐户ID（付内）	nAcceptancePayAccountID    如果是到期承兑，则为付款内部账户；如果是垫付本息收回，也是付款的内部账户
			 * 承兑付款银行ID（付银）	nAcceptancePayBankID	如果是到期承兑，则为付款的财务公司开户行账户；如果是垫付本息收回，则是收款的财务公司开户行账户
			 * 
			 * 收款执行日				dtExecute
			 * 
			 * 当前交易状态(未复核),录入人不是操作者 
			 */
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.ACCEPTANCENOTEACCEPTANCE || info.getTransactionTypeID() == SETTConstant.TransactionType.ADVANCEDRECEVICENOTEACCEPTANCE) {
				buffer = new StringBuffer();

				buffer.append(" select * from Sett_TransAcceptanceNote \n");
				buffer.append(" where \n");
				buffer.append(" 1=1 \n");
				buffer.append(" and nOfficeID=? \n");//办事处
				buffer.append(" and nCurrencyID=? \n");//币种
				buffer.append(" and nStatusID=? \n");//状态
				buffer.append(" and nInputUserID<>? \n");//录入人
				buffer.append(" and NCONTRACTID=? \n");//合同ID
				buffer.append(" and nReceiveFormID=? \n");//通知单ID
				buffer.append(" and ntransactiontypeid=? \n");//交易类型(到期承兑，垫付本息收回)
				buffer.append(" and nacceptancepayaccountid=? \n");//付款账户ID
				buffer.append(" and nacceptancepaybankid=? \n");//银行ID（收/付）
				buffer.append(" and nacceptanceamount=? \n");//金额
				buffer.append(" and dtintereststart=? \n");//起息日
				buffer.append(" order by ID \n");//排序
				
				ps = con.prepareStatement(buffer.toString());
				System.out.println(buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());//办事处
				ps.setLong(index++, info.getCurrencyID());//币种
				ps.setLong(index++, info.getStatusID());//状态
				ps.setLong(index++, info.getInputUserID());//录入人
				ps.setLong(index++, info.getContractID());//合同ID
				ps.setLong(index++, info.getAcceptanceFormID());//通知单ID
				ps.setLong(index++, info.getTransactionTypeID());//交易类型(到期承兑，垫付本息收回)
				ps.setLong(index++, info.getAcceptancePayAccountID());//付款账户ID
				ps.setLong(index++, info.getAcceptancePayBankID());//银行ID（收/付）
				ps.setDouble(index++, info.getAcceptanceAmount());//金额
				ps.setTimestamp(index++,info.getInterestStartDate());//起息日
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransAcceptanceNoteAcceptanceInfo depositInfo = new TransAcceptanceNoteAcceptanceInfo();
					depositInfo = getAcceptanceNoteAcceptance(depositInfo, rs);	
					arrResult.add(depositInfo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;
	}
	
	/**
	 * 商票承兑到期承兑交易继续功能： 逻辑说明：
	 * 
	 * 根据合同ID和收款通知单ID查找相应的贷款合同信息及收款通知单信息
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 融资租赁收款交易实体类
	 * @throws Exception
	 */
	public TransAcceptanceNoteAcceptanceInfo next(TransAcceptanceNoteAcceptanceInfo infoTemp) throws Exception
	{
		TransAcceptanceNoteAcceptanceInfo info = new TransAcceptanceNoteAcceptanceInfo();
		try {
			
			if(infoTemp.getContractID()>0){
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(infoTemp.getContractID());
				info.setContractID(contractInfo.getContractID());	//合同ID
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setClientID(contractInfo.getDiscountClientID());//承兑客户ID
				info.setContractStartDate(contractInfo.getLoanStart());//承兑开始日期
				info.setContractEndDate(contractInfo.getLoanEnd());//承兑结束日期
			}
			//收款单
			if(infoTemp.getAcceptanceFormID()>0){
				//查询INFO
				LoanInformInfo loanInformInfoQuery=new LoanInformInfo();
				loanInformInfoQuery.setId(infoTemp.getAcceptanceFormID());
				loanInformInfoQuery.setNnoteTypeId(infoTemp.getNnotetypeid());
				LoanInformDao loanInformDao=new LoanInformDao();
				LoanInformInfo loanInformInfoReturn=new LoanInformInfo();
				loanInformInfoReturn=loanInformDao.findByIDInform(loanInformInfoQuery);
				if(infoTemp.getSExtAccountNO()!=null&&infoTemp.getSExtAccountNO().length()>0)
				{
					loanInformInfoReturn.setSrecBankAccountNO(infoTemp.getSExtAccountNO());
				}
				if(infoTemp.getSExtClientName()!=null&&infoTemp.getSExtClientName().length()>0)
				{
					loanInformInfoReturn.setSrecBankAccountName(infoTemp.getSExtClientName());
				}
				if(infoTemp.getSRemitinBank()!=null&&infoTemp.getSRemitinBank().length()>0)
				{
					loanInformInfoReturn.setSrecBankName(infoTemp.getSRemitinBank());
				}
				if(infoTemp.getSRemitinCity()!=null&&infoTemp.getSRemitinCity().length()>0)
				{
					loanInformInfoReturn.setSrecBankCity(infoTemp.getSRemitinCity());
				}
				if(infoTemp.getSRemitinProvince()!=null&&infoTemp.getSRemitinProvince().length()>0)
				{
					loanInformInfoReturn.setSrecBankProvince(infoTemp.getSRemitinProvince());
				}
				if(infoTemp.getAcceptanceAmount()>0)
				{
					info.setAcceptanceAmount(infoTemp.getAcceptanceAmount());	//本次承兑金额
				}else{
					info.setAcceptanceAmount(loanInformInfoReturn.getNamount());	
				}
				//设置返回值
				info.setAcceptanceFormID(loanInformInfoReturn.getId());	//通知单ID
				info.setAcceptanceFormCode(loanInformInfoReturn.getScode());	//通知单code
				info.setAcceptanceFormStartDate(loanInformInfoReturn.getDtOutdate());	//通知单开始日期
				
				if(infoTemp.getAcceptanceReceiveAccountID()!=-1)
				{
					info.setAcceptanceReceiveAccountID(infoTemp.getAcceptanceReceiveAccountID());
				}else{
					info.setAcceptanceReceiveAccountID(loanInformInfoReturn.getNrecAccountID());		//收款方内部帐户ID
				}	
				if(infoTemp.getAcceptancePayAccountID()!=-1)
				{
					info.setAcceptancePayAccountID(infoTemp.getAcceptancePayAccountID());
				}else{
				info.setAcceptancePayAccountID(loanInformInfoReturn.getNpayAccountID());		//付款方内部帐户ID
				}				
				info.setLoanInformInfoReturn(loanInformInfoReturn);
				info.setAbstract(infoTemp.getAbstract());
				info.setAbstractID(infoTemp.getAbstractID());
				if(infoTemp.getAcceptancePayBankID()!=-1)
				{
				info.setAcceptancePayBankID(infoTemp.getAcceptancePayBankID());
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			
		}
		return info;
	}
	
	
	/**
	 * 检查收款通知单的状态是否正常     
	 * @param lPayFormID
	 * @return
	 * @throws SQLException
	 */
	public boolean checkPayForm(long lReceiveFormID,long lStatusToCheck)throws SQLException{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			Log.print("校验放款通知单状态......");
			Log.print("放款通知单ID:"+lReceiveFormID);
			conn = this.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "select status from loan_inform where id="+lReceiveFormID
				+" and status="+lStatusToCheck;
			System.out.print(strSql);
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if (rs.next()){
				blnIsOK = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
		}catch(SQLException sqlExp){throw sqlExp;}
		finally{
			this.cleanup(conn);
		}
		return blnIsOK;
	}

	public void updateLoanReceiveFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_inform set status=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	public static void main(String []args){
		TransAcceptanceNoteAcceptanceInfo info=new TransAcceptanceNoteAcceptanceInfo();
		info.setTransactionTypeID(SETTConstant.TransactionType.ACCEPTANCENOTEACCEPTANCE);
		info.setContractID(1000092);
	}

}
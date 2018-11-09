/*
 * Created on 2004-9-01
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transabatement.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant.BillSourceType;
import com.iss.itreasury.loan.util.LOANConstant.LoanType;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transabatement.dataentity.DiscountContractBillInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.ReDiscountContractBillInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.DebitOrCredit;
import com.iss.itreasury.settlement.util.SETTConstant.DiscountBillAbatementStatus;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * <p>
 * Title:数据操作类
 * </p>
 * <p>
 * Description:用于将数据插入数据表中
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:isoftstone
 * </p>
 * 
 * @author gqzhang
 * @version 1.0
 */

public class Sett_TransAbatementDAO extends SettlementDAO
{
	//--------------------------------------------------------------------
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	//--------------------------------------------------------------------
	//排序字段
	public final static int ORDERBY_TRANSACTIONNOID = 0; //交易号
	public final static int ORDERBY_ACCOUNTID = 2; //转贴现账户号 
	public final static int ORDERBY_CLIENTID = 3; //转贴现客户
	public final static int ORDERBY_CONTRATID = 4; //转贴现合同
	public final static int ORDERBY_CREDENCEID = 5; //转贴现凭证
	public final static int ORDERBY_AMOUNT = 6; //转贴现汇票金额
	public final static int ORDERBY_ABSTRACT = 7; //摘要
	public final static int ORDERBY_STATUSID = 8; //状态
	//----------------------------------------------------------------------
	public Sett_TransAbatementDAO()
	{
		super("Sett_TransAbatement");
	}
	/**
	 * Method findByID. 根据冲销业务的记录id查找冲销业务的信息包括明细
	 * 
	 * @param ltransID
	 * @return TransCompatibilityInfo
	 * @throws Exception
	 */
	public TransAbatementInfo findByID(long ltransID) throws SettlementDAOException
	{
		
		ResultSet rs = null;
		TransAbatementInfo resultInfo = null;
		try
		{
			log.print("======Sett_TransCompatibilityDAO：进入查找冲销业务信息======");
			log.print("======ltransID======:" + ltransID);
			
			initDAO();
			
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID =" + ltransID);
			strSQLBuffer.append("\n");
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			Vector vctReturn = getAbatementInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				resultInfo = (TransAbatementInfo) vctReturn.elementAt(0);
				if (resultInfo != null)
				{
					log.print("===resultInfo:" + resultInfo);
				}
			
				/* 
				 * if(复核状态明细){
				 *  ????
				 * }else{如果是其它状态
				 *  账户处理明细 = getTransAbatementDetail()
				 */
				log.debug("======取转贴现日开始======");
				resultInfo.setDiscountDate(this.getDisCountDateByCredenceId(resultInfo.getDueBillID()));
				log.debug("======取转贴现日开始======");
				Vector detail = new Vector();
				if(resultInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK){
					Vector vctAccountDetail = new Vector();
					//TransAbatementDetailInfo detailInfo = new TransAbatementDetailInfo();
					//TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo(); 
					sett_TransAccountDetailDAO accountDetailDao = new sett_TransAccountDetailDAO();
					vctAccountDetail = accountDetailDao.findByTransNo(resultInfo.getTransNo(),resultInfo.getOfficeID(),resultInfo.getCurrencyID());
					detail = this.findAbatementDetailByAccountDetail(vctAccountDetail);
					
				}else{
					detail = this.getTransAbatementDetail(resultInfo);
					//resultInfo.setTransAbatementDetailInfo(detail);
				}
				/*
				if(!this.checkDebitAndCredit(detail)){
					e.printStackTrace();throw new SettlementDAOException("借贷方金额不相等",new Exception());
				}*/
				resultInfo.setTransAbatementDetailInfo(detail);
				
			}else{
				log.print("======Sett_TransAbatementDAO:findByID:not found ======");
			}
			if(rs!=null) rs.close();
			log.print("======Sett_TransAbatementDAO：结束查找冲销业务信息======");
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找冲销业务信息产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找冲销业务信息产生错误", e);
		} 
		finally 
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return resultInfo;
	}
	
	public Vector getReDiscountContractBill(long credenceId,String strOrder , boolean isdesc) throws SettlementDAOException{
		Vector rtVector = null;
		//Collection rtColl = null;
		Vector vetDiscountContractBill = null;
		ReDiscountContractBillInfo reBill = null;
		vetDiscountContractBill = this.getDiscountContractBillByCredenceIdOrderBy(credenceId,strOrder,isdesc);
		if(vetDiscountContractBill==null || vetDiscountContractBill.size()==0){
			
		}else{
			rtVector = new Vector(vetDiscountContractBill.size());
			for(int i=0 ; i<vetDiscountContractBill.size();i++){
				reBill = new ReDiscountContractBillInfo();
				DiscountContractBillInfo billInfo = (DiscountContractBillInfo)vetDiscountContractBill.get(i);
				reBill.setAmount(billInfo.getAmount());
				reBill.setBillSourceTypeId(billInfo.getBillSourceTypeId());
				reBill.setDtCreate(billInfo.getDtCreate());
				reBill.setDtEnd(billInfo.getDtEnd());
				reBill.setNAcceptpoTypeId(billInfo.getNAcceptpoTypeId());
				reBill.setNSerialNo(billInfo.getNSerialNo());
				reBill.setSCode(billInfo.getSCode());
				//TODO 根据什么判断合同是贴现还是转贴现？
				//if(billInfo.getBillSourceTypeId()==BillSourceType.PACHASETRANSDISCOUNT){
				if(billInfo.getLContractTypeID()==LoanType.ZTX){
				//if(billInfo.getNDiscountTypeId()>0){

					reBill.setNReContractId(billInfo.getNContractId());
					reBill.setReDiscountCredenceId(billInfo.getDiscountCredenceId());
					
					reBill.setNInOrOut(billInfo.getNInOrOut());
					reBill.setNDiscountTypeId(billInfo.getNDiscountTypeId());  //
				//}else if(billInfo.getBillSourceTypeId()==BillSourceType.DISCOUN){
				//}else{
				}else if(billInfo.getLContractTypeID()==LoanType.TX){
					 reBill.setNContractId(billInfo.getNContractId());
					 reBill.setDiscountCredenceId(billInfo.getDiscountCredenceId());
				}
				rtVector.add(reBill);
			}
		}
		return rtVector;
	}
	
	/**
	 * 复核之后票据信息
	 * @param vctAccountDetail
	 * @return
	 * @throws SettlementDAOException
	 */
	public Vector findAbatementDetailByAccountDetail(Vector vctAccountDetail) throws SettlementDAOException{
		
		Vector rtVector = new Vector(20);
		TransAbatementDetailInfo detailInfo = new TransAbatementDetailInfo();
		TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
		ResultSet rs = null;

		try
		{
			initDAO();
			
			if(vctAccountDetail==null || vctAccountDetail.size()==0){
				
			}else{
				for(int i=0; i<vctAccountDetail.size();i++){
					
					accountDetailInfo = (TransAccountDetailInfo)vctAccountDetail.get(i);
					detailInfo = new TransAbatementDetailInfo();

					detailInfo.setAccountID(accountDetailInfo.getTransAccountID());
					detailInfo.setSubAccountID(accountDetailInfo.getTransSubAccountID());
					detailInfo.setAmount(accountDetailInfo.getAmount());
					detailInfo.setBillID(accountDetailInfo.getDiscountBillID());
					detailInfo.setTransDirectionID(accountDetailInfo.getTransDirection());

					StringBuffer strSQLBuffer = new StringBuffer();
					strSQLBuffer.append("" +"\n");
					strSQLBuffer.append("SELECT   \n");
					strSQLBuffer.append(" b.ID CREDENCEID , " +"\n");
					strSQLBuffer.append(" c.ID CONTRACTID , " +"\n");
					strSQLBuffer.append(" c.NBORROWCLIENTID , " +"\n");
					strSQLBuffer.append(" c.NTYPEID " +"\n");
					strSQLBuffer.append(" FROM " +"\n");
					strSQLBuffer.append(" sett_subaccount a ," +"\n");
					strSQLBuffer.append(" loan_discountcredence b ," +"\n");
					strSQLBuffer.append(" loan_contractform c " +"\n");
					strSQLBuffer.append(" where a.AL_NLOANNOTEID = b.ID " + " \n");
					strSQLBuffer.append(" and b.NCONTRACTID = c.ID " );
					
					strSQLBuffer.append(" and a.ID = " + accountDetailInfo.getTransSubAccountID() );
					
					strSQLBuffer.append(" and a.NSTATUSID > 0");
					log.info(strSQLBuffer.toString());
					prepareStatement(strSQLBuffer.toString());
					
					rs = executeQuery();
					
					if(rs.next()){
						detailInfo.setClientID(rs.getLong("NBORROWCLIENTID"));
						detailInfo.setContractID(rs.getLong("CONTRACTID"));
						detailInfo.setContractTypeID(rs.getLong("NTYPEID"));
						detailInfo.setCredenceID(rs.getLong("CREDENCEID"));
					}
					rtVector.add(detailInfo);
			    }
			}
			if(rs!=null)
			  rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findAbatementDetailByAccountDetail产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("findAbatementDetailByAccountDetail产生错误", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		
		return rtVector;
	}
	
	/**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transID) throws SettlementDAOException
	{
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			initDAO();
			
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ModifyDate FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = " + transID);
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
				log.debug("====修改时间：" + res);
			}
			if(rs!=null)
			  rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找冲销业务修改时间产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("查找冲销业务修改时间产生错误", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return res;
	}
	/**
	 * Method findAbatementByQueryCondition. 根据组合查询条件查找冲销业务记录
	 * 
	 * @param info
	 * @param strOrderByName
	 * @param isDesc
	 * @return Vector
	 * @throws SettlementDAOException
	 */
	public Vector findAbatementByQueryCondition(QueryByStatusConditionInfo info) throws SettlementDAOException
	{
		Vector vctReturn = null;
		ResultSet rs = null;
		try
		{
			initDAO();
			
			String strSql = this.getSQLByQueryByStatusConditionInfo(info);

			log.debug(strSql);
			prepareStatement(strSql);
			
			rs = executeQuery();
			vctReturn = this.getAbatementInfoFromResultSet(rs);
			if(rs!=null)
			rs.close();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();throw new SettlementDAOException("根据组合查询条件查找冲销业务产生错误", e);
		} catch (SQLException e) {
			e.printStackTrace();throw new SettlementDAOException("根据组合查询条件查找冲销业务产生错误", e);
		}finally{		
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}

		return vctReturn;
	}
	/**
	 * 通过查询实体生成SQL
	 * @param info
	 * @return
	 */
   private String getSQLByQueryByStatusConditionInfo( QueryByStatusConditionInfo info){
		StringBuffer strSQLBuffer = new StringBuffer();
		strSQLBuffer.append("select * from " +" \n");
		strSQLBuffer.append(this.strTableName+" \n");
		strSQLBuffer.append("where ID>0 ");
	
		//交易类型
		if (info.getTransTypeID() > 0)
		{
			strSQLBuffer.append(" and TransactionTypeID=" + info.getTransTypeID());
			strSQLBuffer.append("\n");
		}
		//录入人
		if (info.getInputUserID() > 0)
		{
			strSQLBuffer.append(" and InputUserID=" + info.getInputUserID());
			strSQLBuffer.append("\n");
		}
		//复核人
		if (info.getCheckUserID() > 0)
		{
			strSQLBuffer.append(" and CheckUserID=" + info.getCheckUserID());
			strSQLBuffer.append("\n");
		}
		
		if (info.getStatusIDs() != null && info.getStatusIDs().length > 0)
		{
			long lTemp = info.getStatusIDs().length;
			boolean isStart = true;
			for (int i = 0; i < lTemp; i++)
			{
				if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.TEMPSAVE)
				{ //暂存没有时间限制
					if (isStart)
					{
						strSQLBuffer.append(" and (");
						isStart = !isStart;
					}
					else
					{
						strSQLBuffer.append(" or ");
					}
					strSQLBuffer.append("(StatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
				}
				else
					if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.SAVE || info.getStatusIDs()[i] == SETTConstant.TransactionStatus.CHECK)
					{ //保存或者复核要查当天的
						if (isStart)
						{
							strSQLBuffer.append(" and (");
							isStart = !isStart;
						}
						else
						{
							strSQLBuffer.append("or ");
						}
						strSQLBuffer.append("( StatusID = " + info.getStatusIDs()[i]);
						strSQLBuffer.append(" and ExecuteDate= TO_DATE('" + DataFormat.getDateString(info.getExecuteDate()) + "','YYYY/MM/DD HH24:MI:SS') \n");
						strSQLBuffer.append("\n");
						strSQLBuffer.append(" )");
					}
					else
					{
						strSQLBuffer.append(" and ");
						strSQLBuffer.append(" StatusID = " + info.getStatusIDs()[i]); //空白的时候
					}
			}
			strSQLBuffer.append(")");
			strSQLBuffer.append("\n");
		}
		
		strSQLBuffer.append(" and OfficeID=" + info.getOfficeID());
		strSQLBuffer.append("\n");
		strSQLBuffer.append(" and CurrencyID=" + info.getCurrencyID());
		strSQLBuffer.append("\n");
		
		
		switch ((int) info.getOrderByID())
		{
			case (int) ORDERBY_TRANSACTIONNOID :
				strSQLBuffer.append(" order by TransNo ");
				break;
			case (int) ORDERBY_ACCOUNTID :
				strSQLBuffer.append(" order by ACCOUNTID ");
				break;
			case (int) ORDERBY_CLIENTID :
				strSQLBuffer.append(" order by CLIENTID ");
				break;
			case (int) ORDERBY_CONTRATID :
				strSQLBuffer.append(" order by CONTRACTID ");
				break;
			case (int) ORDERBY_CREDENCEID :
				strSQLBuffer.append(" order by DUEBILLID ");
				break;
			case (int) ORDERBY_AMOUNT :
				strSQLBuffer.append(" order by TOTALAMOUNT ");
				break;
			case (int) ORDERBY_ABSTRACT :
				strSQLBuffer.append(" order by ABSTRACT ");
				break;
			case (int) ORDERBY_STATUSID :
				strSQLBuffer.append(" order by STATUSID ");
				break;
			default :
				strSQLBuffer.append(" order by TransNo ");
		}
		strSQLBuffer.append("\n");
		if (info.isDesc())
		{
			strSQLBuffer.append(" desc \n");
		}
	   	return strSQLBuffer.toString();
   }
	
	/**
	 * Method match.
	 * 匹配冲销业务
	 * @param info
	 * @return TransAbatementInfo
	 * @throws SettlementDAOException
	 */
	public TransAbatementInfo match(TransAbatementInfo info) throws SettlementDAOException
	{
		log.debug("=====开始匹配冲销业务====");
		Vector vect = null;
		TransAbatementInfo rtInfo = null;
		ResultSet rs = null;
		try
		{
			initDAO();

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where \n");
			strSQLBuffer.append(getMatchWhereSQL(info));
			log.debug("=====匹配sql:" + strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vect = this.getAbatementInfoFromResultSet(rs);
			if(vect==null || vect.size()==0){
				
			}else{
			   rtInfo = (TransAbatementInfo)vect.get(0);
			}
			if(rs!=null)
			rs.close();
			
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException("根据匹配冲销业务记录产生错误", e);
		}
		catch (SQLException e)
		{
			e.printStackTrace();throw new SettlementDAOException("根据匹配冲销业务记录产生错误", e);
		} 
		finally 
		{
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		log.debug("=====结束匹配冲销业务====");
		return rtInfo;
	}
	/**
	 * Method getWhereSQL.
	 * 
	 * @param info
	 * @return String
	 * @throws SettlementDAOException
	 */
	private String getMatchWhereSQL(TransAbatementInfo info) throws SettlementDAOException
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		boolean isAndNeed = false;
		//id
		long lId = info.getId();
		strSQLBuffer.append(" 1=1 \n");
		if (lId > 0)
		{
			strSQLBuffer.append(" and ID = '" + lId + "' \n");
			
		}
		//Number 交易ID 外键：关联TransactionNo
		/*
		if(info.getTransNOID()>0){
		   strSQLBuffer.append(" and TransNOID = " + info.getTransNOID() + " \n");
		}
		*/
		//客户
		strSQLBuffer.append(" and CLIENTID= "+info.getClientID() +"\n");
		
		//账户
		strSQLBuffer.append(" and ACCOUNTID= "+info.getAccountID()+"\n");
		
		//合同
		strSQLBuffer.append(" and CONTRACTID= "+info.getContractID() +"\n");
		//凭证
		strSQLBuffer.append(" and DUEBILLID = "+info.getDueBillID() +"\n");
		
		//Number 交易类型 
		strSQLBuffer.append("AND  TransactionTypeID = " + info.getTransactionTypeID() + " \n");
		//兼容业务交易类型
		//strSQLBuffer.append("AND  OperationTypeID = " + info.getogetOperationTypeID() + " \n");
		//Number 办事处
		strSQLBuffer.append("AND  OfficeID = " + info.getOfficeID() + " \n");
		//Number 币种
		strSQLBuffer.append("AND  CurrencyID = " + info.getCurrencyID() + " \n");
		//DateTime 起息日
		Timestamp tsInterestStartDate = info.getInterestStartDate();
		if (tsInterestStartDate == null)
		{
			strSQLBuffer.append(" AND InterestStartDate IS NULL ");
		}
		else
		{
			String strTime = tsInterestStartDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InterestStartDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//DateTime 执行日
		Timestamp tsExecuteDate = info.getExecuteDate();
		if (tsExecuteDate == null)
		{
			strSQLBuffer.append(" AND ExecuteDate IS NULL ");
		}
		else
		{
			String strTime = tsExecuteDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ExecuteDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//摘要
		/*
		String strAbstract = info.getAbstract();
		if (strAbstract.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append(" AND Abstract IS NULL \n");
		}
		else
		{
			strSQLBuffer.append(" AND  Abstract = '" + strAbstract + "' \n");
		}*/
		//状态
		strSQLBuffer.append(" AND StatusID = " + SETTConstant.TransactionStatus.SAVE);
		//确认办事处
		long lAffrimOfficeID = info.getAffrimOfficeID();
		if (lAffrimOfficeID > 0)
		{
			strSQLBuffer.append(" AND AffrimOfficeID = " + lAffrimOfficeID);
		}
		
		//录入人,当前操作用户不能等于交易信息录入用户
		strSQLBuffer.append(" AND InputUserID != " + info.getInputUserID() + " \n");
		//复核人
		long lCheckUserID = info.getCheckUserID();
		if (lCheckUserID > 0)
		{
			strSQLBuffer.append(" AND CheckUserID != " + lCheckUserID + " \n");
		}
		//签认人
		long lSignUserID = info.getSignUserID();
		if (lSignUserID > 0)
		{
			strSQLBuffer.append(" AND SignUserID != " + lSignUserID + " \n");
		}
		//确认人
		long lAffrimUserID = info.getAffrimUserID();
		if (lAffrimUserID > 0)
		{
			strSQLBuffer.append(" AND AffrimUserID != " + lAffrimUserID + " \n");
		}
		//录入日期
		/*
		Timestamp tsInputDate = info.getInputDate();
		if (tsInputDate == null)
		{
			strSQLBuffer.append(" AND InputDate IS NULL ");
		}
		else
		{
			String strTime = tsInputDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InputDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}*/
		
		//修改日期
		Timestamp tsModifyDate = info.getModifyDate();
		if (tsModifyDate != null)
		{
			String strTime = tsModifyDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ModifyDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//复核日期
		Timestamp tsCheckDate = info.getCheckDate();
		if (tsCheckDate != null)
		{
			String strTime = tsCheckDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND CheckDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//签认日期
		Timestamp tsSignDate = info.getSignDate();
		if (tsSignDate != null)
		{
			String strTime = tsSignDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND SignDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//确认日期
		Timestamp tsAffirmDate = info.getAffirmDate();
		if (tsAffirmDate != null)
		{
			String strTime = tsAffirmDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND AffirmDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		strSQLBuffer.append(" order by ID desc ");
		return strSQLBuffer.toString();
	}
	/**
	 * Method getAbatementFromResultSet.
	 * 
	 * @param rs
	 * @return Vector
	 * @throws SQLException
	 * @throws Exception
	 */
	private Vector getAbatementInfoFromResultSet(ResultSet rs) throws SettlementDAOException, SQLException
	{
		Vector vctReturn = new Vector();
		TransAbatementInfo info = null;

			while (rs.next())
			{
				info = new TransAbatementInfo();
				info.setId(rs.getLong("ID")); //NUMBER                         ID                 
				info.setTransNOID(rs.getLong("TRANSNOID")); //NUMBER        Y                交易ID             
				info.setTransactionTypeID(rs.getLong("TRANSACTIONTYPEID")); //NUMBER        Y                交易类型           
				info.setTransNo(rs.getString("TRANSNO")==null?"":rs.getString("TRANSNO")); //VARCHAR2(30)  Y                交易号             
				info.setOfficeID(rs.getLong("OFFICEID")); //NUMBER        Y                办事处             
				info.setCurrencyID(rs.getLong("CURRENCYID")); //NUMBER        Y                币种               
				info.setClientID(rs.getLong("CLIENTID")); //NUMBER        Y                转贴现卖出客户ID   
				info.setAccountID(rs.getLong("ACCOUNTID")); //NUMBER        Y                转贴现卖出主账户ID 
				info.setContractID(rs.getLong("CONTRACTID")); //NUMBER        Y                转贴现卖出合同号   
				info.setDueBillID(rs.getLong("DUEBILLID")); //NUMBER        Y                转贴现卖出凭证号   
				info.setGLID(rs.getLong("GLID")); //NUMBER        Y                总账               
				info.setTotalAmount(rs.getDouble("TOTALAMOUNT")); //NUMBER(21,6)  Y                冲销总金额         
				info.setAmountFromDiscount(rs.getDouble("AMOUNTFROMDISCOUNT")); //NUMBER(21,6)  Y                来源于贴现的金额   
				info.setDiscountGLID(rs.getLong("DISCOUNTGLID")); //NUMBER        Y                贴现总账           
				info.setAmountFromReDiscount(rs.getDouble("AMOUNTFROMREDISCOUNT")); //NUMBER(21,6)  Y                来源于转贴现的金额 
				info.setReDiscountGLID(rs.getLong("REDISCOUNTGLID")); //NUMBER        Y                转贴现总账         
				info.setInterestStartDate(rs.getTimestamp("INTERESTSTARTDATE")); //DATE          Y                起息日             
				info.setExecuteDate(rs.getTimestamp("EXECUTEDATE")); //DATE          Y                执行日             
				info.setAbstract(rs.getString("ABSTRACT")==null?"":rs.getString("ABSTRACT")); //VARCHAR2(100) Y                摘要               
				info.setAffrimOfficeID(rs.getLong("AFFRIMOFFICEID")); //NUMBER        Y                确认办事处         
				info.setInputUserID(rs.getLong("INPUTUSERID")); //NUMBER        Y                录入人             
				info.setCheckUserID(rs.getLong("CHECKUSERID")); //NUMBER        Y                复核人             
				info.setSignUserID(rs.getLong("SIGNUSERID")); //NUMBER        Y                签认人             
				info.setAffrimUserID(rs.getLong("AFFRIMUSERID")); //NUMBER        Y                确认人             
				info.setInputDate(rs.getTimestamp("INPUTDATE")); //DATE          Y                录入日期           
				info.setModifyDate(rs.getTimestamp("MODIFYDATE")); //DATE          Y                修改日期时间       
				info.setCheckDate(rs.getTimestamp("CHECKDATE")); //DATE          Y                复核日期           
				info.setSignDate(rs.getTimestamp("SIGNDATE")); //DATE          Y                签认日期           
				info.setAffirmDate(rs.getTimestamp("AFFIRMDATE")); //DATE          Y                确认日期           
				info.setCheckAbstract(rs.getString("CHECKABSTRACT")==null?"":rs.getString("CHECKABSTRACT")); //VARCHAR2(100) Y                复核备注           
				info.setStatusID(rs.getLong("STATUSID")); //NUMBER        Y                状态               
				info.setSubAccountID(rs.getLong("SUBACCOUNTID")); //NUMBER        Y                                
				vctReturn.add(info);
			}
		log.print("====vctReturn.size():" + vctReturn.size());
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	
	/**
	 * 继续方法（
	 * 根据转贴现凭证id 得到其中的贴现和转贴现金额
	 * 及账务处理明细）
	 * @param info
	 * @return
	 * @throws SettlementDAOException
	 */
	public TransAbatementInfo next(TransAbatementInfo info)throws SettlementDAOException{
		
		log.debug("======next开始======");
		Vector detail = null;
		
		detail = this.getTransAbatementDetail(info);
		/*
		if(!this.checkDebitAndCredit(detail)){
			e.printStackTrace();throw new SettlementDAOException("借贷方金额不相等",new Exception());
		}*/
		info.setTransAbatementDetailInfo(detail);
		if(detail!=null && detail.size()>0){
			TransAbatementDetailInfo detailInfo = (TransAbatementDetailInfo)detail.get(0);
			info.setSubAccountID(detailInfo.getSubAccountID());
		}
		
//		判断是否为新的，如果已经存在则取出实体信息
		if(info.getId()>0){
		  TransAbatementInfo _info = this.findByID(info.getId());
		  info.setGLID(_info.getGLID());
		  info.setDiscountGLID(_info.getDiscountGLID());
		  info.setReDiscountGLID(_info.getReDiscountGLID());
		  info.setDiscountDate(_info.getDiscountDate());
		  info.setExecuteDate(_info.getExecuteDate());
		  info.setSubAccountID(_info.getSubAccountID());
		  info.setInterestStartDate(_info.getInterestStartDate());
		 
		}
		if(info.getDueBillID()>0){
			
			long amountFromDiscount = 0;
			long amountFromReDiscount = 0;
			amountFromDiscount = this.getAmountByBillSourceType(BillSourceType.DISCOUN,info.getDueBillID());
			amountFromReDiscount = this.getAmountByBillSourceType(BillSourceType.PACHASETRANSDISCOUNT,info.getDueBillID());

			info.setTotalAmount(amountFromDiscount+amountFromReDiscount);
			info.setAmountFromDiscount(amountFromDiscount);
			info.setAmountFromReDiscount(amountFromReDiscount);
			log.debug("======取转贴现日开始======");
			info.setDiscountDate(this.getDisCountDateByCredenceId(info.getDueBillID()));
			 log.debug("======取转贴现日开始======");
			
		}else{
			//e.printStackTrace();throw new SettlementDAOException("查找转贴现自动冲销业务信息产生错误");
		}
		log.debug("======next结束======");
		return info;
	}
	
	/**
	 * 根据转贴现凭证id和票据来源类型（贴现和转贴现） 
	 * 得到对应合计金额
	 * @param typeid   票据来源类型（贴现和转贴现
	 * @param credenceid    转贴现凭证id
	 * @return
	 * @throws SettlementDAOException
	 */
	private long getAmountByBillSourceType(long typeid,long credenceid) throws SettlementDAOException {
		/*
		LOANConstant.BillSourceType.DISCOUN; //贴现
		LOANConstant.BillSourceType.PACHASETRANSDISCOUNT; //转贴现
		*/
		ResultSet rs = null ;
		long rtAmount = 0 ;
		try
		{
			initDAO();
		
		StringBuffer strSQLBuffer = new StringBuffer();
		strSQLBuffer.append("select sum(b.MAMOUNT) as mamount from " +"\n");
		strSQLBuffer.append(" rtransdiscountcredencebill a ," +"\n");
		strSQLBuffer.append(" loan_discountcontractbill b " +"\n");
		strSQLBuffer.append(" where nvl(b.NBILLSOURCETYPEID,1) = " );
		strSQLBuffer.append(typeid +"\n");
		strSQLBuffer.append(" and a.DISCOUNTCONTRACTBILLID = b.ID" +"\n");
		strSQLBuffer.append(" and a.TRANSDISCOUNTCREDENCEID = "+credenceid +"\n");
		
		log.info(strSQLBuffer.toString());
		
		
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if(rs.next()){
			   rtAmount = rs.getLong("mamount");
			}
		} catch (ITreasuryDAOException e1) {
			e1.printStackTrace();
			throw new SettlementDAOException("计算票据金额产生错误", e1);
			
		} catch (SQLException e) {
			e.printStackTrace();throw new SettlementDAOException("计算票据金额产生错误", e);
		} finally {
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return rtAmount;
	}
	
	/**
	 * 
	 * @param info
	 * @return
	 * @throws SettlementDAOException
	 */
	
	private Vector getTransAbatementDetail(TransAbatementInfo info) throws SettlementDAOException{
		Vector rtVector = new Vector(20);
		TransAbatementDetailInfo _detailInfo = null;
		TransAbatementDetailInfo detailInfo = null;
		DiscountContractBillInfo billInfo = null;
		
		log.debug("======业务处理明细列表开始=======");
		//借方信息
			detailInfo = new TransAbatementDetailInfo();
			_detailInfo = this.getTransAbatementDetialInfoByCredenceIdForDebit(info.getDueBillID());
			if(_detailInfo==null){
				
			}else if(_detailInfo.getId()>0){
				detailInfo.setTransDirectionID(DebitOrCredit.DEBIT); //借贷方向
				detailInfo.setAccountID(_detailInfo.getAccountID());
				detailInfo.setSubAccountID(_detailInfo.getSubAccountID());
				detailInfo.setAmount(_detailInfo.getAmount());
				detailInfo.setClientID(_detailInfo.getClientID());
				detailInfo.setContractID(_detailInfo.getContractID());
				detailInfo.setCredenceID(_detailInfo.getCredenceID());
				detailInfo.setContractTypeID(_detailInfo.getContractTypeID());
				
				rtVector.add(detailInfo);
				log.debug("=====借方信息:金额："+detailInfo.getAmount()+"合同："+detailInfo.getContractID()+"客户："+detailInfo.getClientID());
			}
		//处理贷方信息
		
		Vector bill = this.getDiscountContractBillByCredenceId(info.getDueBillID());
		Vector billACredence = null;
		long billACredenceId = -1;
		long repetitionId = -1;
		boolean isExist = false;
		if(bill==null || bill.size() == 0){
			
		}else{
				for(int i=0;i<bill.size();i++){
					billInfo = (DiscountContractBillInfo)bill.get(i);
					
					if(billACredence==null ||billACredence.size()==0
							||billACredenceId<0 
							|| billACredenceId!=billInfo.getDiscountCredenceId()
							)
				    {
						//取凭证对应的所有票据
						billACredence = this.getAllBillByCredenceId(billInfo.getDiscountCredenceId());
						log.debug("billACredence:"+billACredence.size());
						
						_detailInfo = this.getTransAbatementDetialInfoByCredenceId(billInfo.getDiscountCredenceId());
						if(_detailInfo==null){
							_detailInfo = new TransAbatementDetailInfo();
						}
						billACredenceId = billInfo.getDiscountCredenceId();
						
						//判断凭证对应的所有票据是不是全部 在当时集合内。
						isExist = this.isAllBillCredence(bill,billACredence);
						log.debug("isExist:"+isExist);
					}

					if(isExist){ 
						//凭证对应的所有票据是全部 在当时集合内，则只添加 凭证信息 票据id为空，去掉重复的票据
						if(repetitionId==billInfo.getDiscountCredenceId()){
							
						}else{
							detailInfo = new TransAbatementDetailInfo();
							
								detailInfo.setTransDirectionID(DebitOrCredit.CREDIT);  //借贷方向
								detailInfo.setAccountID(_detailInfo.getAccountID());
								detailInfo.setSubAccountID(_detailInfo.getSubAccountID());
								detailInfo.setAmount(_detailInfo.getAmount());
								detailInfo.setClientID(_detailInfo.getClientID());
								detailInfo.setContractID(_detailInfo.getContractID());
								detailInfo.setContractTypeID(_detailInfo.getContractTypeID());
								detailInfo.setCredenceID(_detailInfo.getCredenceID());
							
							repetitionId = billInfo.getDiscountCredenceId();
							rtVector.add(detailInfo);
							log.debug("=====贷方信息:金额："+detailInfo.getAmount()+"合同："+detailInfo.getContractID()+"客户："+detailInfo.getClientID());
						}
					}else{
						//凭证对应的所有票据不是全部 在当时集合内，则需要逐条添加凭证信息、票据id和票据金额。
						detailInfo = new TransAbatementDetailInfo();
						
							detailInfo.setTransDirectionID(DebitOrCredit.CREDIT);
							detailInfo.setAccountID(_detailInfo.getAccountID());
							detailInfo.setSubAccountID(_detailInfo.getSubAccountID());
							detailInfo.setAmount(billInfo.getAmount());
							detailInfo.setClientID(_detailInfo.getClientID());
							detailInfo.setContractID(_detailInfo.getContractID());
							detailInfo.setContractTypeID(_detailInfo.getContractTypeID());
							detailInfo.setCredenceID(_detailInfo.getCredenceID());
							detailInfo.setBillID(billInfo.getId());
							
						rtVector.add(detailInfo);
						log.debug("=====贷方信息:金额："+detailInfo.getAmount()+"合同："+detailInfo.getContractID()+"客户："+detailInfo.getClientID()+"票据:"+detailInfo.getBillID());
					}
						
					
				}
		}
		
		log.debug("======业务处理明细列表结束=======");
		return rtVector;
	}
	
	
	/**
	 * 判断 一个凭证所有票据 是否 全部包含 在转贴现凭证对应的票据里
	 * @param bill
	 * @param billACredence
	 * @return
	 */
	
	private boolean isAllBillCredence(Vector bill,Vector billACredence){
		DiscountContractBillInfo billInfo = null;
		DiscountContractBillInfo billACredenceInfo = null;
		boolean isExist = false ;
		if(billACredence==null || billACredence.size()==0 
				||bill==null ||bill.size()==0){
			
		}else{
			for(int i =0 ; i<billACredence.size();i++){
				billACredenceInfo = (DiscountContractBillInfo)billACredence.get(i);
				
				for(int j = 0 ;j<bill.size() ; j++){
					billInfo= (DiscountContractBillInfo)bill.get(j);
					if(billACredenceInfo.getId()==billInfo.getId()){
						isExist = true;
						break;
					}
				}
				if(isExist){
					isExist = false;
				}else{
					break;
				}
			}
		}
		return isExist;
	}
	
	/**
	 * 得到bill中凭证id 暂时没用。
	 * @param bill 
	 * @return
	 */
	private Vector getCredenceId(Vector bill){
		Vector rtVector = new Vector();
		long tmpId = -1;
		DiscountContractBillInfo billInfo = null;
		if(bill==null || bill.size()==0){
			
		}else{
			int j=0;
			for(int i=0;i<bill.size();i++){
				billInfo = (DiscountContractBillInfo)bill.get(i);
				if(billInfo.getDiscountCredenceId()!=tmpId){	
					rtVector.add(billInfo);
					tmpId = billInfo.getDiscountCredenceId();
				}
			}
		}
		return rtVector;
	}
	
	private TransAbatementDetailInfo getTransAbatementDetialInfoByCredenceIdForDebit(long credenceId) throws SettlementDAOException{
		
		ResultSet rs  = null;
		TransAbatementDetailInfo detailInfo = null ;
		try
		{
			initDAO();
		StringBuffer strSQLBuffer = new StringBuffer(100); 
		
		/*
		 * 取凭证对应借方信息
		 * 也就是做一张凭证的信息
		 */
		strSQLBuffer.append(""+"\n");
		strSQLBuffer.append("select " +"\n");
		strSQLBuffer.append(" a.ID ,"+"\n"); 
		strSQLBuffer.append(" d.MBALANCE ,"+"\n");           //贴现凭证金额
		strSQLBuffer.append(" a.NCONTRACTID ,"+"\n");       //合同id
		strSQLBuffer.append(" b.NTYPEID ,"+"\n");           //合同类型
		//strSQLBuffer.append(" b.DTDISCOUNTDATE ,"+"\n");    //转贴现日期
		strSQLBuffer.append(" c.NBORROWCLIENTID ,"+"\n");   //客户id(借款单位)
		strSQLBuffer.append(" d.NACCOUNTID ,"+"\n");         //主账户id
		strSQLBuffer.append(" d.ID SUBACCOUNTID "+"\n");    //子账户id
		strSQLBuffer.append(" from LOAN_DISCOUNTCREDENCE a ,"+"\n"); //凭证
		strSQLBuffer.append(" LOAN_CONTRACTFORM b ,"+"\n");     //合同
		strSQLBuffer.append(" LOAN_LOANFORM c,"+"\n");          //贷款信息
		strSQLBuffer.append(" sett_subaccount d "+"\n");       //子账户
		strSQLBuffer.append(" where a.ID = ");
		strSQLBuffer.append(  credenceId +"\n");
		strSQLBuffer.append(" and a.NCONTRACTID = b.ID "+"\n");
		strSQLBuffer.append(" and b.NLOANID = c.ID "+"\n");
		strSQLBuffer.append(" and a.ID = d.AL_NLOANNOTEID"+"\n");  //对应放款单号
		
		log.info(strSQLBuffer.toString());
		prepareStatement(strSQLBuffer.toString());
		rs = executeQuery();
		if(rs.next()){
			detailInfo = new TransAbatementDetailInfo();
			detailInfo.setId(rs.getLong("ID"));
			detailInfo.setAccountID(rs.getLong("NACCOUNTID"));     //账户
			detailInfo.setSubAccountID(rs.getLong("SUBACCOUNTID"));  //子账户
			detailInfo.setClientID(rs.getLong("NBORROWCLIENTID"));      //客户
			detailInfo.setContractID(rs.getLong("NCONTRACTID"));    //合同
			detailInfo.setContractTypeID(rs.getLong("NTYPEID"));        //合同类型
			detailInfo.setCredenceID(credenceId);    //凭证 
			detailInfo.setAmount(rs.getDouble("MBALANCE"));        //凭证金额
		}else{
		    System.out.println("方法：根据凭证id得到 TransAbatementDetailInfo中==未找到记录==");
		}
		if(rs!=null)
			rs.close();
		} catch (ITreasuryDAOException e1) {
			e1.printStackTrace();
			throw new SettlementDAOException(" 根据凭证id得到 TransAbatementDetailInfo中产生错误：id="+credenceId, e1);
			
		} catch (SQLException e) {
			e.printStackTrace();throw new SettlementDAOException(" 根据凭证id得到 TransAbatementDetailInfo中产生错误:id="+credenceId, e);
		} finally {
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return detailInfo;
	}


	
	
	/**
	 * 根据凭证id得到 TransAbatementDetailInfo中的
	 * 账户//子账户 //客户//合同 //凭证 //凭证金额 信息
	 * @param credenceId
	 * @return
	 * @throws SettlementDAOException
	 */
	private TransAbatementDetailInfo getTransAbatementDetialInfoByCredenceId(long credenceId) throws SettlementDAOException{
		
		ResultSet rs  = null;
		TransAbatementDetailInfo detailInfo = null ;
		try
		{
			initDAO();
		StringBuffer strSQLBuffer = new StringBuffer(100); 
		
		/*
		 * 取凭证对应借方信息
		 * 也就是做一张凭证的信息
		 */
		strSQLBuffer.append(""+"\n");
		strSQLBuffer.append("select " +"\n");
		strSQLBuffer.append(" a.ID ,"+"\n"); 
		strSQLBuffer.append(" a.MAMOUNT ,"+"\n");           //贴现凭证金额
		strSQLBuffer.append(" a.NCONTRACTID ,"+"\n");       //合同id
		strSQLBuffer.append(" b.NTYPEID ,"+"\n");           //合同类型
		//strSQLBuffer.append(" b.DTDISCOUNTDATE ,"+"\n");    //转贴现日期
		strSQLBuffer.append(" c.NBORROWCLIENTID ,"+"\n");   //客户id(借款单位)
		strSQLBuffer.append(" d.NACCOUNTID ,"+"\n");         //主账户id
		strSQLBuffer.append(" d.ID SUBACCOUNTID "+"\n");    //子账户id
		strSQLBuffer.append(" from LOAN_DISCOUNTCREDENCE a ,"+"\n"); //凭证
		strSQLBuffer.append(" LOAN_CONTRACTFORM b ,"+"\n");     //合同
		strSQLBuffer.append(" LOAN_LOANFORM c,"+"\n");          //贷款信息
		strSQLBuffer.append(" sett_subaccount d "+"\n");       //子账户
		strSQLBuffer.append(" where a.ID = ");
		strSQLBuffer.append(  credenceId +"\n");
		strSQLBuffer.append(" and a.NCONTRACTID = b.ID "+"\n");
		strSQLBuffer.append(" and b.NLOANID = c.ID "+"\n");
		strSQLBuffer.append(" and a.ID = d.AL_NLOANNOTEID"+"\n");  //对应放款单号
		
		log.info(strSQLBuffer.toString());
		prepareStatement(strSQLBuffer.toString());
		rs = executeQuery();
		if(rs.next()){
			detailInfo = new TransAbatementDetailInfo();
			detailInfo.setId(rs.getLong("ID"));
			detailInfo.setAccountID(rs.getLong("NACCOUNTID"));     //账户
			detailInfo.setSubAccountID(rs.getLong("SUBACCOUNTID"));  //子账户
			detailInfo.setClientID(rs.getLong("NBORROWCLIENTID"));      //客户
			detailInfo.setContractID(rs.getLong("NCONTRACTID"));    //合同
			detailInfo.setContractTypeID(rs.getLong("NTYPEID"));        //合同类型
			detailInfo.setCredenceID(credenceId);    //凭证 
			detailInfo.setAmount(rs.getDouble("MAMOUNT"));        //凭证金额
		}else{
		    System.out.println("方法：根据凭证id得到 TransAbatementDetailInfo中==未找到记录==");
		}
		if(rs!=null)
			rs.close();
		} catch (ITreasuryDAOException e1) {
			e1.printStackTrace();
			throw new SettlementDAOException(" 根据凭证id得到 TransAbatementDetailInfo中产生错误：id="+credenceId, e1);
			
		} catch (SQLException e) {
			e.printStackTrace();throw new SettlementDAOException(" 根据凭证id得到 TransAbatementDetailInfo中产生错误:id="+credenceId, e);
		} finally {
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return detailInfo;
	}


	/**
	 * 取转贴现日期
	 * @param credenceId
	 * @return
	 * @throws SettlementDAOException
	 */
	
	private Timestamp getDisCountDateByCredenceId(long credenceId) throws SettlementDAOException{
		
		ResultSet rs  = null;
		//TransAbatementDetailInfo detailInfo = null ;
		Timestamp rtTimestamp = null;
		try
		{
			initDAO();
		StringBuffer strSQLBuffer = new StringBuffer(100); 

		strSQLBuffer.append(""+"\n");
		strSQLBuffer.append("select " +"\n");
		//strSQLBuffer.append(" a.MAMOUNT ,"+"\n");           //贴现凭证金额
		//strSQLBuffer.append(" a.NCONTRACTID ,"+"\n");       //合同id
		//strSQLBuffer.append(" b.NTYPEID ,"+"\n");           //合同类型
		strSQLBuffer.append(" b.DTDISCOUNTDATE "+"\n");    //转贴现日期
		//strSQLBuffer.append(" c.NBORROWCLIENTID ,"+"\n");   //客户id(借款单位)
		//strSQLBuffer.append(" d.NACCOUNTID ,"+"\n");         //主账户id
		//strSQLBuffer.append(" d.ID SUBACCOUNTID "+"\n");    //子账户id
		strSQLBuffer.append(" from LOAN_DISCOUNTCREDENCE a ,"+"\n"); //凭证
		strSQLBuffer.append(" LOAN_CONTRACTFORM b "+"\n");     //合同
		//strSQLBuffer.append(" LOAN_LOANFORM c,"+"\n");          //贷款信息
		//strSQLBuffer.append(" sett_subaccount d "+"\n");       //子账户
		strSQLBuffer.append(" where a.ID = ");
		strSQLBuffer.append(  credenceId +"\n");
		strSQLBuffer.append(" and a.NCONTRACTID = b.ID "+"\n");
		//strSQLBuffer.append(" and b.NLOANID = c.ID "+"\n");
		//strSQLBuffer.append(" and a.ID = d.AL_NLOANNOTEID"+"\n");  //对应放款单号
		
		log.info(strSQLBuffer.toString());
		prepareStatement(strSQLBuffer.toString());
		rs = executeQuery();
		if(rs.next()){
			rtTimestamp = rs.getTimestamp("DTDISCOUNTDATE");
		}else{
		    System.out.println("方法：取转贴现日期==未找到记录==");
		}
		if(rs!=null)
			rs.close();
		} catch (ITreasuryDAOException e1) {
			e1.printStackTrace();
			throw new SettlementDAOException(" 取转贴现日期产生错误：id="+credenceId, e1);
			
		} catch (SQLException e) {
			e.printStackTrace();throw new SettlementDAOException(" 取转贴现日期产生错误:id="+credenceId, e);
		} finally {
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				e.printStackTrace();throw new SettlementDAOException(e);
			}
		}
		return rtTimestamp;
	}
	
	private Vector getDiscountContractBillByCredenceId(long credenceId) throws SettlementDAOException{
		String strOrder = "NDISCOUNTCREDENCEID";
		boolean isDesc  = false;
		return this.getDiscountContractBillByCredenceIdOrderBy(credenceId,strOrder,isDesc);
	}
	/**
	 * 得到转贴金凭证对应的多票据信息
	 * 
	 * @param credenceId
	 * @return
	 * @throws SettlementDAOException
	 */
	private Vector getDiscountContractBillByCredenceIdOrderBy(long credenceId,String strOrder , boolean isdesc) throws SettlementDAOException{
		ResultSet rs  = null;
		Vector vctBill = new Vector(20);
		DiscountContractBillInfo billInfo = null;
		try
		{
			initDAO();
				
			StringBuffer strSQLBuffer = new StringBuffer(100);
	
			/*
			 * 转贴现凭证对应的票据 的原凭证 有多少张 
			 * 需要票据的编号
			 */
			strSQLBuffer.append(""+"\n");
			strSQLBuffer.append("select "+"\n");
			strSQLBuffer.append(" b.ID BILLID ,"+"\n"); //
			strSQLBuffer.append(" b.MAMOUNT ,"+"\n");   //order by
			strSQLBuffer.append(" b.NDISCOUNTCREDENCEID ,"+"\n");   //
			strSQLBuffer.append(" b.NBILLSOURCETYPEID ,"+"\n");   //
			strSQLBuffer.append(" b.NCONTRACTID ,"+"\n");
			strSQLBuffer.append(" b.NSERIALNO ,"+"\n");    //order by
			strSQLBuffer.append(" b.DTCREATE ,"+"\n");     
			strSQLBuffer.append(" b.DTEND ,"+"\n");        //order by 
			strSQLBuffer.append(" b.NACCEPTPOTYPEID ,"+"\n");
			strSQLBuffer.append(" b.SCODE ,"+"\n");        //order by
			strSQLBuffer.append(" c.NINOROUT ,"+"\n");
			strSQLBuffer.append(" c.NTYPEID ,"+"\n");
			strSQLBuffer.append(" c.NDISCOUNTTYPEID"+"\n");
			
			strSQLBuffer.append(" from "+"\n");
			strSQLBuffer.append(" rtransdiscountcredencebill  a ,"+"\n");
			strSQLBuffer.append(" loan_discountcontractbill  b ,"+"\n");
			strSQLBuffer.append(" loan_contractform c "+"\n");
			//strSQLBuffer.append(" loan_discountcredence c "+"\n");
			strSQLBuffer.append(" where "+"\n");
			strSQLBuffer.append(" a.TRANSDISCOUNTCREDENCEID = ");
			strSQLBuffer.append(  credenceId+"\n");
			strSQLBuffer.append(" and a.DISCOUNTCONTRACTBILLID = b.ID "+"\n");
			strSQLBuffer.append(" and b.NCONTRACTID = c.ID "+"\n");
//			必须是未冲销的票据
			strSQLBuffer.append(" and  nvl(b.NISABATEMENT,0)="+DiscountBillAbatementStatus.NO);
			//strSQLBuffer.append(" group by c.ID "+"\n");
			if(strOrder==null || strOrder.equals("") ){
			}else if( strOrder.equals("MAMOUNT")
					|| strOrder.equals("NSERIALNO")
					|| strOrder.equals("DTEND")
					|| strOrder.equals("SCODE")
					|| strOrder.equals("NDISCOUNTCREDENCEID")){
				strSQLBuffer.append(" order by b."+strOrder+"\n");
				if(isdesc){
					strSQLBuffer.append(" desc ");
				}
			}
			
			
			

			log.info(strSQLBuffer.toString());
			
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			
			if(rs==null){
				
			}else{
				while(rs.next()){
					billInfo = new DiscountContractBillInfo();
					billInfo.setId(rs.getLong("BILLID"));
					billInfo.setAmount(rs.getDouble("MAMOUNT"));
					billInfo.setBillSourceTypeId(rs.getLong("NBILLSOURCETYPEID"));
					billInfo.setDiscountCredenceId(rs.getLong("NDISCOUNTCREDENCEID"));
					billInfo.setDtCreate(rs.getTimestamp("DTCREATE"));
					billInfo.setDtEnd(rs.getTimestamp("DTEND"));
					billInfo.setNAcceptpoTypeId(rs.getLong("NACCEPTPOTYPEID"));
					billInfo.setNContractId(rs.getLong("NCONTRACTID"));
					billInfo.setNDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));
					billInfo.setNInOrOut(rs.getLong("NINOROUT"));
					billInfo.setNSerialNo(rs.getLong("NSERIALNO"));
					billInfo.setLContractTypeID(rs.getLong("NTYPEID"));
					billInfo.setSCode(rs.getString("SCODE")==null?"":rs.getString("SCODE"));
					vctBill.add(billInfo);
				}
			}
			if(rs!=null)rs.close();
	} catch (ITreasuryDAOException e1) {
		e1.printStackTrace();
		throw new SettlementDAOException("得到转贴金凭证对应的多票据信息产生错误", e1);
		
	} catch (SQLException e) {
		e.printStackTrace();throw new SettlementDAOException("得到转贴金凭证对应的多票据信息产生错误", e);
	} finally {
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
	}
		return vctBill;
	}
	
	
	/**
	 * 一个凭证对应的所有票据
	 * @param credenceId
	 * @return
	 * @throws SettlementDAOException
	 */
	
	private Vector getAllBillByCredenceId(long credenceId) throws SettlementDAOException{
		ResultSet rs  = null;
		Vector vctBill = new Vector(20);
		DiscountContractBillInfo billInfo = null;
		try
		{
			initDAO();
				
			StringBuffer strSQLBuffer = new StringBuffer(100);
	
	
			strSQLBuffer.append(""+"\n");
			strSQLBuffer.append("select "+"\n");
			strSQLBuffer.append(" b.ID BILLID ,"+"\n"); //
			strSQLBuffer.append(" b.MAMOUNT ,"+"\n");   //
			strSQLBuffer.append(" b.NDISCOUNTCREDENCEID ,"+"\n");   //
			strSQLBuffer.append(" b.NBILLSOURCETYPEID "+"\n");   //
			strSQLBuffer.append(" from "+"\n");
			//strSQLBuffer.append(" rtransdiscountcredencebill  a ,"+"\n");
			strSQLBuffer.append(" loan_discountcontractbill  b "+"\n");
			//strSQLBuffer.append(" loan_discountcredence c "+"\n");
			strSQLBuffer.append(" where "+"\n");
			strSQLBuffer.append(" b.NDISCOUNTCREDENCEID = ");
			strSQLBuffer.append(  credenceId+"\n");
			//strSQLBuffer.append(" and a.DISCOUNTCONTRACTBILLID = b.ID "+"\n");
			//strSQLBuffer.append(" and b.NDISCOUNTCREDENCEID = c.ID "+"\n");
			//strSQLBuffer.append(" group by c.ID "+"\n");
			strSQLBuffer.append(" order by b.NDISCOUNTCREDENCEID "+"\n");

			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			
			while(rs.next()){
				billInfo = new DiscountContractBillInfo();
				billInfo.setId(rs.getLong("BILLID"));
				billInfo.setAmount(rs.getDouble("MAMOUNT"));
				billInfo.setBillSourceTypeId(rs.getLong("NBILLSOURCETYPEID"));
				billInfo.setDiscountCredenceId(rs.getLong("NDISCOUNTCREDENCEID"));
				
				vctBill.add(billInfo);
			}
		if(rs!=null)rs.close();
	} catch (ITreasuryDAOException e1) {
		e1.printStackTrace();
		throw new SettlementDAOException("一个凭证对应的所有票据产生错误", e1);
		
	} catch (SQLException e) {
		e.printStackTrace();throw new SettlementDAOException("一个凭证对应的所有票据产生错误", e);
	} finally {
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
	}
		return vctBill;
	}
	
	
	
	/**
	 * 根据交易号查找交易ID的方法：
	 */
public long getIDByTransNo(String strTransNo) throws SettlementDAOException
{
	ResultSet rs = null;
	long lID = -1;
	try
	{
		initDAO();
	}
	catch (ITreasuryDAOException e)
	{
		e.printStackTrace();throw new SettlementDAOException(e);
	}
	try
	{
		prepareStatement("select ID from \n" + strTableName + " where TransNo=" + strTransNo);
		rs = executeQuery();
		if (rs != null && rs.next())
		{
			lID = rs.getLong("ID");
		}
		rs.close();
	}
	catch (Exception e)
	{
		e.printStackTrace();throw new SettlementDAOException("根据自动冲销交易编号查找交易id产生错误", e);
	}
	return lID;
}

/**
 * 校验借贷方金额是否相等
 * @param detail
 * @return
 */
public boolean checkDebitAndCredit(Vector detail){
	double debit = 0.0;
	double credit = 0.0;
	TransAbatementDetailInfo detailInfo = null;
	if(detail==null || detail.size()<1){
		
	}else{
		detailInfo = (TransAbatementDetailInfo)detail.get(0);
		if(detailInfo!=null)
			debit = detailInfo.getAmount();
		for(int i= 1 ;i<detail.size();i++){
			detailInfo = (TransAbatementDetailInfo)detail.get(i);
			if(detailInfo!=null)
				credit = credit+detailInfo.getAmount();
		}
		if(debit==0.0 || credit==0.0){
			
		}else if(debit == credit){
			return true;
		}
	}
	return false;
}


	/**
	 * Method main.
	 * 
	 * @param args
	 * @throws Exception
	 */
	
	
	
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
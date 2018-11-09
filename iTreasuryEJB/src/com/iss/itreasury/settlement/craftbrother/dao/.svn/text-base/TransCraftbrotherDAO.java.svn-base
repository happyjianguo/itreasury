package com.iss.itreasury.settlement.craftbrother.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.bs.util.DataFormat;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class TransCraftbrotherDAO extends ITreasuryDAO {
	
	
	public TransCraftbrotherDAO() {
		super("sett_transcraftbrother");
	}

	public TransCraftbrotherDAO(String strTableName) {
		super(strTableName);
	}

	public TransCraftbrotherDAO(Connection conn) {
		super("sett_transcraftbrother", conn);
	}

	public TransCraftbrotherDAO(String strTableName, Connection conn) {
		super(strTableName, conn);
	}

	/**
	 * 业务匹配
	 * @param qInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo qInfo)
			throws ITreasuryDAOException {
		TransCraftbrotherInfo rtnInfo = null;
		StringBuffer sbfSQL = null;
		try{
			initDAO();
			switch((int)qInfo.getNcraBusinessTypeId()){
				case (int)CRAconstant.SameBusinessAttribute.DISCOUNT://转贴现业务
					sbfSQL = new StringBuffer();
					sbfSQL.append(" select a.*, c.id ncontractId, c.scontractcode scontractCode,b.scode snoticeCode " + " \n ");
					sbfSQL.append("   from sett_transcraftbrother a, " + " \n ");
					sbfSQL.append("        loan_discountcredence  b, " + " \n ");
					sbfSQL.append("        loan_contractform      c  " + " \n ");
					sbfSQL.append("  where a.nnoticeid = b.id " + " \n ");
					sbfSQL.append("    and b.ncontractid = c.id " + " \n ");
					sbfSQL.append("    and c.id = " + qInfo.getNcontractId() + " \n ");
					sbfSQL.append("    and b.id = " + qInfo.getNnoticeId() + " \n ");
					sbfSQL.append("    and a.ntransactiontypeid = " + qInfo.getNtransactionTypeId() + " \n ");
					sbfSQL.append("    and a.ncrabusinesstypeid = " + CRAconstant.SameBusinessAttribute.DISCOUNT + " \n ");
					sbfSQL.append("    and a.nsubtransactiontypeid = " + qInfo.getNsubTransactionTypeId() + " \n ");
					sbfSQL.append("    and a.mamount = " + qInfo.getMamount() + " \n ");
					sbfSQL.append("    and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
					sbfSQL.append("    and a.nbankid = " + qInfo.getNbankId() + " \n ");
					sbfSQL.append("    and a.nstatusid = " + SETTConstant.TransactionStatus.SAVE + " \n ");
					sbfSQL.append("    and a.ninputuserid <> " + qInfo.getNinputUserId() );
					if((qInfo.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT&&(qInfo.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.BREAK_INVEST||qInfo.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_INVEST))
							||(qInfo.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE&&qInfo.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_NOTIFY))
					{
						if (qInfo.getSextAccountNo().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n  and a.sextAccountNo IS NULL ");
						else
							sbfSQL.append("\n and a.sextAccountNo ='" + qInfo.getSextAccountNo() + "' \n");
						
						if (qInfo.getSextClientName().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n and a.sextClientName IS NULL \n");
						else
							sbfSQL.append("\n and a.sextClientName ='" + qInfo.getSextClientName() + "' \n");
						
					}
					break;
				case (int)CRAconstant.SameBusinessAttribute.FUND_ATTORN://资金拆借业务
					sbfSQL = new StringBuffer();
					sbfSQL.append("select a.*, b.code snoticeCode, c.id ncontractId, c.code scontractCode");
					sbfSQL.append("\n from sett_transcraftbrother a, sec_noticeform b, SEC_DeliveryOrder c");
					sbfSQL.append("\n where a.nnoticeid=b.id");
					sbfSQL.append("\n and b.deliveryorderid=c.id");
					sbfSQL.append("\n and a.nnoticeid=" + qInfo.getNnoticeId());
					sbfSQL.append("\n and a.nsubtransactiontypeid=" + qInfo.getNsubTransactionTypeId());
					sbfSQL.append("\n and a.ntransactiontypeid=" + qInfo.getNtransactionTypeId());
					sbfSQL.append("\n and a.ncrabusinesstypeid=" + qInfo.getNcraBusinessTypeId());
					sbfSQL.append("\n and a.Mrealamount=" + qInfo.getMamount());
					sbfSQL.append("\n and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')");
					sbfSQL.append("\n and a.nbankid = " + qInfo.getNbankId());
					sbfSQL.append("\n and a.nstatusid = " + SETTConstant.TransactionStatus.SAVE);
					sbfSQL.append("\n and a.ninputuserid <> " + qInfo.getNinputUserId());
					//sbfSQL.append("\n and a.nofficeid=" + qInfo.getNofficeId());
					//sbfSQL.append("\n and a.ncurrencyid=" + qInfo.getNcurrencyId());
					if((qInfo.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN && qInfo.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_OUT)
							||(qInfo.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN_REPAY&&qInfo.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_IN))
					{
						if (qInfo.getSextAccountNo().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n  and a.sextAccountNo IS NULL ");
						else
							sbfSQL.append("\n and a.sextAccountNo ='" + qInfo.getSextAccountNo() + "' \n");
						
						if (qInfo.getSextClientName().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n and a.sextClientName IS NULL \n");
						else
							sbfSQL.append("\n and a.sextClientName ='" + qInfo.getSextClientName() + "' \n");
					}
					break;
				case (int)CRAconstant.SameBusinessAttribute.SAME_BUSINESS://资产转让业务
					sbfSQL = new StringBuffer();
					sbfSQL.append(" select a.*, c.id ncontractId, c.code scontractCode, b.code snoticeCode " + " \n ");
					sbfSQL.append("   from sett_transcraftbrother a, " + " \n ");
					sbfSQL.append("        sec_noticeform  b, " + " \n ");
					sbfSQL.append("        sec_applycontract  c  " + " \n ");
					sbfSQL.append("  where a.nnoticeid = b.id " + " \n ");
					sbfSQL.append("    and b.contractid = c.id " + " \n ");
					sbfSQL.append("    and c.id = " + qInfo.getNcontractId() + " \n ");
					sbfSQL.append("    and b.id = " + qInfo.getNnoticeId() + " \n ");
					sbfSQL.append("    and a.ntransactiontypeid = " + qInfo.getNtransactionTypeId() + " \n ");
					sbfSQL.append("    and a.ncrabusinesstypeid = " + CRAconstant.SameBusinessAttribute.SAME_BUSINESS + " \n ");
					sbfSQL.append("    and a.nsubtransactiontypeid = " + qInfo.getNsubTransactionTypeId() + " \n ");
					sbfSQL.append("    and a.mamount = " + qInfo.getMamount() + " \n ");
					if(qInfo.getMinterest() > 0){
						sbfSQL.append("    and a.minterest = " + qInfo.getMinterest() + " \n ");
					}
					if(qInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT){// 资产转让发放
						if(qInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_INVEST || 
								qInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST ){//买入
							sbfSQL.append("    and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
						if (qInfo.getSextAccountNo().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n  and a.sextAccountNo IS NULL ");
						else
							sbfSQL.append("\n and a.sextAccountNo ='" + qInfo.getSextAccountNo() + "' \n");
						
						if (qInfo.getSextClientName().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n and a.sextClientName IS NULL \n");
						else
							sbfSQL.append("\n and a.sextClientName ='" + qInfo.getSextClientName() + "' \n");
						}else{//卖出
							sbfSQL.append("    and a.dtrealinterest = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
						}
					}else if(qInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE){// 资产转让收回
						if(qInfo.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST ){//买入购回
							sbfSQL.append("    and a.dtrealinterest = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
						}else{//卖出购回
							sbfSQL.append("    and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
						if (qInfo.getSextAccountNo().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n  and a.sextAccountNo IS NULL ");
						else
							sbfSQL.append("\n and a.sextAccountNo ='" + qInfo.getSextAccountNo() + "' \n");
						
						if (qInfo.getSextClientName().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n and a.sextClientName IS NULL \n");
						else
							sbfSQL.append("\n and a.sextClientName ='" + qInfo.getSextClientName() + "' \n");
						}
					}else if(qInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS){//资产转让利息支付
						sbfSQL.append("    and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
						if (qInfo.getSextAccountNo().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n  and a.sextAccountNo IS NULL ");
						else
							sbfSQL.append("\n and a.sextAccountNo ='" + qInfo.getSextAccountNo() + "' \n");
						
						if (qInfo.getSextClientName().compareToIgnoreCase("") == 0)
							sbfSQL.append("\n and a.sextClientName IS NULL \n");
						else
							sbfSQL.append("\n and a.sextClientName ='" + qInfo.getSextClientName() + "' \n");
					}else if(qInfo.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTRECEIVE){//资产转让利息收回
						sbfSQL.append("    and a.dtrealinterest = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
					}else{
						sbfSQL.append("    and a.dtintereststart = to_date('" + DataFormat.formatDate(qInfo.getDtInterestStart()) + "', 'YYYY-MM-DD')" + " \n ");
					}
					sbfSQL.append("    and a.nbankid = " + qInfo.getNbankId() + " \n ");
					sbfSQL.append("    and a.nstatusid = " + SETTConstant.TransactionStatus.SAVE + " \n ");
					sbfSQL.append("    and a.ninputuserid <> " + qInfo.getNinputUserId() );
					break;
				default:
					break;
			}
			if(sbfSQL!=null){
				log.info("复核匹配查找：SQL={"+sbfSQL.toString()+"}");
				transPS = prepareStatement(sbfSQL.toString());
				transRS = transPS.executeQuery();
				rtnInfo = (TransCraftbrotherInfo)this.getDataEntityFromResultSet(TransCraftbrotherInfo.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001", e);
		}finally{
			finalizeDAO();
		}
		return rtnInfo;
	}

	/**连接查找
	 * @param lQueryPurpose 1:修改连接查找 2:复核链接查找
	 * @param lStatusId 查询状态
	 * @param lUserId   当前查询用户
	 * @param nOrderByCode 排序字段名称
	 * @param lIsDesc 是否降序
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws ITreasuryDAOException {
		Collection coll = null;
		StringBuffer sbfSQL = null;
		try{
			initDAO();
			
			switch((int)lQueryPurpose){
				case (int)TransCraftbrotherInfo.QUERYPURPOSE_MODIFY_LINKSEARCH:
					sbfSQL = new StringBuffer();
					sbfSQL.append(" select t.* " + " \n ");
					sbfSQL.append("   from sett_transcraftbrother t " + " \n ");
					sbfSQL.append("  where t.dtexecute = (select o.dtopendate from sett_officetime o where o.nstatusid=1 and ncurrencyid = t.ncurrencyid and nofficeid = t.nofficeid) " + " \n ");
					sbfSQL.append("    and t.ntransactiontypeid = " + lTransactionTypeId + " \n ");
					if(lStatusId > 0){
						sbfSQL.append("    and t.nstatusid = " + lStatusId + " \n ");
					}else{
						sbfSQL.append("    and (t.nstatusid = " + SETTConstant.TransactionStatus.TEMPSAVE + " or " + "t.nstatusid = " + SETTConstant.TransactionStatus.SAVE + ")" + " \n ");
					}
					sbfSQL.append("    and t.ninputuserid = " + lUserId + " \n ");
					
					break;
				case (int)TransCraftbrotherInfo.QUERYPURPOSE_CHECK_LINKSEARCH:
					sbfSQL = new StringBuffer();
					sbfSQL.append(" select t.* " + " \n ");
					sbfSQL.append("   from sett_transcraftbrother t " + " \n ");
					sbfSQL.append("  where t.dtexecute = (select o.dtopendate from sett_officetime o where o.nstatusid=1 and ncurrencyid = t.ncurrencyid and nofficeid = t.nofficeid) " + " \n ");
					sbfSQL.append("    and t.ntransactiontypeid = " + lTransactionTypeId + " \n ");
					if(lStatusId > 0){
						sbfSQL.append("    and t.nstatusid = " + lStatusId + " \n ");
					}else{
						sbfSQL.append("    and t.nstatusid = " + SETTConstant.TransactionStatus.CHECK + " \n ");
					}
					sbfSQL.append("    and t.ncheckuserid = " + lUserId + " \n ");
					break;
				default:
					break;
			}
			
			if(sbfSQL!=null){
				//表头排序
				switch(nOrderIndex){
					case 1:
						sbfSQL.append(" order by STRANSNO " + (lIsDesc?" DESC ":" ASC ") + " \n ");
						break;
					case 2:
						sbfSQL.append(" order by NSUBTRANSACTIONTYPEID " + (lIsDesc?" DESC ":" ASC ") + " \n ");
						break;
					case 3:
						sbfSQL.append(" order by MAMOUNT " + (lIsDesc?" DESC ":" ASC ") + " \n ");
						break;
					default:
						sbfSQL.append(" order by ID " + (lIsDesc?" DESC ":" ASC ") + " \n ");
						break;	
				}
				log.info("连接查找：SQL={"+sbfSQL.toString()+"}");
				transPS = prepareStatement(sbfSQL.toString());
				transRS = transPS.executeQuery();
				coll = getDataEntitiesFromResultSet(TransCraftbrotherInfo.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("Gen_E001", e);
		}finally{
			finalizeDAO();
		}
		return coll;

	}

	/** 根据【同业业务类型,交易对手,同业交易类型对应子类型,分录类型】取
	 *  得交易对手科目号，供结算调用
	 * @param lBisinessTypeID   同业业务类型【转贴现、资金拆借、资产转让】
	 * @param lCounterPartID    同业交易对手
	 * @param lSubTransTypeID   同业交易类型对应的结算交易子类型【买入买断、买入回购、卖出买断、卖出回购】
	 * @param lEntrySubjectType 生成会计分录设置的分录科目类型
	 * @param lOfficeId         办事处
	 * @param lCurrencyId       币种
	 * @return 科目编号
	 * @throws ITreasuryDAOException
	 */
	public String getSubjectCode(long lBisinessTypeID,
							long lCounterPartID,
							long lSubTransTypeID,
							long lEntrySubjectType,
							long lOfficeId, 
							long lCurrencyId)throws ITreasuryDAOException {
		String rSubjectCode = null;
		StringBuffer sbfSQL = null;
		try{
			initDAO();
			sbfSQL = new StringBuffer();
			sbfSQL.append(" select ");
			switch((int) lEntrySubjectType){
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_38:
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_39:
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_40:
					sbfSQL.append(" st.subjectcode as subjectcode "+ " \n ");
					break;
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_41:
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_42:
					sbfSQL.append(" st.repayinterestsubjectcode as subjectcode "+ " \n ");
					break;
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_43:
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_44:
					sbfSQL.append(" st.payinterestsubjectcode as subjectcode "+ " \n ");
					break;
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_45:
				case (int) SETTConstant.EntrySubjectType.SUBJECT_TYPE_46:
					sbfSQL.append(" st.predrawinterestsubjectcode as subjectcode "+ " \n ");
					break;
				default:
					sbfSQL.append(" '' as subjectcode "+ " \n ");
					break;
			}
			sbfSQL.append("   from cra_subjectsetting t, cra_sub_subjectsetting st " + " \n ");
			sbfSQL.append("  where st.subjectsettid = t.id " + " \n ");
			sbfSQL.append("    and t.businesstypeid = " + lBisinessTypeID + " \n ");
			if(lCounterPartID > 0){
				sbfSQL.append("    and st.counterpartid = " + lCounterPartID + " \n ");
			}
			if(lSubTransTypeID > 0){
				sbfSQL.append("    and st.subtypeid = " + lSubTransTypeID + " \n ");
			}
			sbfSQL.append("    and t.statusid = " + Constant.RecordStatus.VALID + " \n ");
			sbfSQL.append("    and st.statusid = " + Constant.RecordStatus.VALID + " \n ");
			sbfSQL.append("    and t.officeid = " + lOfficeId + " \n ");
			sbfSQL.append("    and t.currencyid = " + lCurrencyId + " \n ");
			
			log.info("根据会计分录定义信息查找同业科目设置：SQL={"+sbfSQL.toString()+"}");
			transPS = prepareStatement(sbfSQL.toString());
			transRS = transPS.executeQuery();
			if(transRS!=null && transRS.next()){
				rSubjectCode = transRS.getString("subjectcode");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("查找同业科目设置信息失败！", e);
		}finally{
			finalizeDAO();
		}
		return rSubjectCode;
	}
	/**
	 * 根据交易号查找同业交易信息
	 * @param strTransNo
	 * @return
	 * @throws Exception
	 */
	public TransCraftbrotherInfo findByTransNo(String strTransNo) throws Exception{
		TransCraftbrotherInfo res = null;
		try
		{
			initDAO();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from "+ this.strTableName + " where nstatusid > 0 and stransno = "+ strTransNo);
			prepareStatement(strSQLBuffer.toString());
			executeQuery();
			res  = (TransCraftbrotherInfo)this.getDataEntityFromResultSet(TransCraftbrotherInfo.class);
		}catch (Exception e) {
			e.printStackTrace();
			new ITreasuryDAOException("根据交易号查找同业交易信息失败！", e);
		}
		finally
		{
			finalizeDAO();
		}
		return res;		
		
		
	}
	/**
	 * 根据合同票据查找转贴现买入买断发放信息
	 * @param strTransNo
	 * @return
	 * @throws Exception
	 */
	public TransCraftbrotherInfo findBreakInByBillId(long lcontractbillID) throws Exception{
		TransCraftbrotherInfo res = null;
		try
		{
			initDAO();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select t.* from "+ this.strTableName + " t, RTRANSDISCOUNTCREDENCEBILL b where  t.nnoticeid = b.transdiscountcredenceid and t.nstatusid > 0 and t.ntransactiontypeid = " + SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT + " and t.nsubtransactiontypeid = " + SETTConstant.SubTransactionType.BREAK_INVEST + " and b.discountcontractbillid = "+ lcontractbillID);
			System.out.println(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			executeQuery();
			res  = (TransCraftbrotherInfo)this.getDataEntityFromResultSet(TransCraftbrotherInfo.class);
		}catch (Exception e) {
			e.printStackTrace();
			new ITreasuryDAOException("根据交易号查找同业交易信息失败！", e);
		}
		finally
		{
			finalizeDAO();
		}
		return res;		
		
		
	}
	/**
	 * 根据合同、票据查找转贴现票据利息
	 * @param strTransNo
	 * @return
	 * @throws Exception
	 */
	public double getTransDiscountContractBillInterest(long lDiscountCountractId,long lDiscountContractBillId) throws Exception{
		
		double discountContractBillInterest = 0.00;
		try
		{
			initDAO();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select (b.mamount-r.checkamount) billInterest from Loan_Discountcontractbill b, RTRANSDISCOUNTCONTRACTBILL r where r.discountcontractbillid = b.id and transdiscountcontractid = " + lDiscountCountractId + " and r.discountcontractbillid = " + lDiscountContractBillId);
			prepareStatement(strSQLBuffer.toString());
			executeQuery();
			if(transRS != null && transRS.next()){
				discountContractBillInterest = transRS.getDouble("billInterest");
			}
		}catch (Exception e) {
			e.printStackTrace();
			new ITreasuryDAOException("根据合同、票据查找转贴现票据利息失败！", e);
		}
		finally
		{
			finalizeDAO();
		}
		return discountContractBillInterest;		
		
		
	}
	
	public boolean checkTransactionRecord(long lnoticeID,long transactionStatus)throws Exception{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			initDAO();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "";
				strSql = "select * from sett_transcraftbrother where nnoticeid="+lnoticeID
				+" and ntransactiontypeid="+SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE+" and nstatusid in ("+SETTConstant.TransactionStatus.SAVE+","+SETTConstant.TransactionStatus.CHECK+")";
			prepareStatement(strSql);
			executeQuery();
			if (transRS != null && transRS.next()){
				blnIsOK = true;
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("校验通知单（凭证）状态失败！", e);
		}
		finally
		{
			finalizeDAO();
		}
		return blnIsOK;
	}
	public boolean checkPayForm(long lnoticeID,long craBusinessTypeId)throws Exception{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			Log.print("校验通知单（凭证）状态......");
			Log.print("通知单（凭证）ID:"+lnoticeID);
			initDAO();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "";
			if(craBusinessTypeId==CRAconstant.SameBusinessAttribute.DISCOUNT){
				strSql = "select nstatusid from loan_discountcredence where id="+lnoticeID
				+" and nstatusid="+LOANConstant.DiscountCredenceStatus.CHECK;
			}else{
				strSql = "select statusid from sec_noticeform where id="+lnoticeID
				+" and statusid="+SECConstant.NoticeFormStatus.CHECKED;
			}
			prepareStatement(strSql);
			executeQuery();
			if (transRS != null && transRS.next()){
				blnIsOK = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("校验通知单（凭证）状态失败！", e);
		}
		finally
		{
			finalizeDAO();
		}
		return blnIsOK;
	}
}

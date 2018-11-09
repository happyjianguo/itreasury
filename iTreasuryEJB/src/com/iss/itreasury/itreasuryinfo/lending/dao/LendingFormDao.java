package com.iss.itreasury.itreasuryinfo.lending.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormInfo;
import com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * 定义拆入单信息对数据库的操作方法。
 * @author gmqiu
 *
 */
public class LendingFormDao extends ITreasuryDAO {
	
	protected Log4j log = new Log4j(Constant.ModuleType.FUNDPLAN, this);
 
	private StringBuffer strSqlBuffer = null;
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	
	public LendingFormDao() {
		super("IPLAN_LENDINGFORM");
		setUseMaxID();
    }

    public LendingFormDao(Connection conn) {
        super("IPLAN_LENDINGFORM", conn);
        setUseMaxID();
    }
	
	public long getSequence(String systemDate) throws ITreasuryDAOException {
		long l = 0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select (nvl(max(rownum),0)+1) id from IPLAN_LENDINGFORM lf \n");
		strSqlBuffer.append("where lf.dtinputdate = to_date('"+systemDate+"','yyyy/mm/dd') ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				l = transRS.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return l;
	}
	
	public String getsCode(String systemDate) throws ITreasuryDAOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb = new StringBuffer();
		sb.append("CR");
		sb.append(sdf.format(new Date()));
		String s = getSequence(systemDate)+"";
		if(s.length() < 4){
			for (int i = 0; i < 4-s.length(); i++) {
				sb.append("0");
			}
		}
		if(s.length() > 4){
			s = s.substring(s.length()-4, s.length());
		}
		sb.append(s);
		return sb.toString();
	}
	
	/**
	 * 新增拆入单信息
	 * @param lendingFormInfo
	 * @throws ITreasuryDAOException
	 */
	public void insertLendingForm(LendingFormInfo lendingFormInfo) throws ITreasuryDAOException {
		add(lendingFormInfo);
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws ClassNotFoundException
	 */
	public LendingFormInfo findById(long id) throws ITreasuryDAOException {
		LendingFormInfo lf = null;
		try {
			lf = (LendingFormInfo) findByID(id,
				  Class.forName("com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormInfo"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return lf;
	}
	
	/**
	 * 修改拆入单信息
	 * @param lendingFormInfo
	 * @throws ITreasuryDAOException
	 */
	public void updateLendingForm(LendingFormInfo lendingFormInfo) throws ITreasuryDAOException {
//		update(lendingFormInfo);
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_LENDINGFORM set \n ");
		strSqlBuffer.append(" dtStartDate=to_date('"+DataFormat.formatDate(lendingFormInfo.getDtStartDate())+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append(",dtEndDate=to_date('"+DataFormat.formatDate(lendingFormInfo.getDtEndDate())+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append(",mAmount="+lendingFormInfo.getmAmount()+" \n ");
		strSqlBuffer.append(",mPayRate="+lendingFormInfo.getmPayRate()+" \n ");
		strSqlBuffer.append(",sCounterParty='"+lendingFormInfo.getsCounterParty()+"' \n ");
		strSqlBuffer.append(",sRecBankAccountNo='"+lendingFormInfo.getsRecBankAccountNo()+"' \n ");
		strSqlBuffer.append(",sRecBankAccountName='"+lendingFormInfo.getsRecBankAccountName()+"' \n ");
		strSqlBuffer.append(",mRate="+lendingFormInfo.getmRate()+" \n ");
		strSqlBuffer.append("where id="+lendingFormInfo.getId()+"");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 改状态
	 * @param id
	 * @param nStatusId  修改后的状态
	 * @param nCheckUserId
	 * @throws ITreasuryDAOException
	 */
	public void updateStatusId(long id, long nStatusId, long nCheckUserId, long nPayInputUserId, long nPayCheckUserId) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_LENDINGFORM set nStatusId="+nStatusId+" \n ");
		//状态改为已拆入
		if(nStatusId == IPLANConstant.LendStatus.LENDED){
			strSqlBuffer.append(",nCheckUserId="+nCheckUserId+",dtCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
		}
		//状态改为还款保存
//		if(nStatusId == IPLANConstant.LendStatus.REPAY_SAVE){
//			strSqlBuffer.append(",nPayInputUserId="+nPayInputUserId+",dtPayInputDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
//			strSqlBuffer.append(",dtPayDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
//		}
		//状态改为已还款
		if(nStatusId == IPLANConstant.LendStatus.REPAYED){
			strSqlBuffer.append(",nPayCheckUserId="+nPayCheckUserId+",dtPayCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
		}
		strSqlBuffer.append("where id="+id+"");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 状态改为还款保存
	 * @param id
	 * @param nStatusId
	 * @param nPayInputUserId
	 * @param dtPayDate
	 * @throws ITreasuryDAOException
	 */
	public void update_repay_save(long id, long nStatusId, long nPayInputUserId, String dtPayDate) throws ITreasuryDAOException{
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_LENDINGFORM set nStatusId="+nStatusId+" \n ");
		strSqlBuffer.append(",nPayInputUserId="+nPayInputUserId+",dtPayInputDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
		strSqlBuffer.append(",dtPayDate=to_date('"+dtPayDate+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("where id="+id+"");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 取消复核
	 * @param id
	 * @param nStatusId
	 * @param nCheckUserId
	 * @param nPayInputUserId
	 * @param nPayCheckUserId
	 * @throws ITreasuryDAOException
	 */
	public void update_uncheck(long id, long nStatusId, long nCheckUserId, long nPayInputUserId, long nPayCheckUserId) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_LENDINGFORM set nStatusId="+nStatusId+" \n ");
		if(nStatusId == IPLANConstant.LendStatus.LEND_SAVE){
			strSqlBuffer.append(",nCheckUserId=-1,dtCheckDate=to_date('','yyyy/mm/dd') \n ");
			strSqlBuffer.append(",nPayInputUserId=-1,dtPayInputDate=to_date('','yyyy/mm/dd') \n ");
			strSqlBuffer.append(",nPayCheckUserId=-1,dtPayCheckDate=to_date('','yyyy/mm/dd') \n ");
		}
		strSqlBuffer.append("where id="+id+"");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 分页查询拆入单信息
	 * @param qInfo
	 * @return
	 */
	public PageLoader queryLendingFormPage(LendingFormQueryInfo qInfo) {
		getLendingFormSql(qInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sbFrom.toString(), sbSelect.toString(), sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.itreasuryinfo.lending.dataentity.LendingFormInfo", null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	/**
	 * 拆入单信息查询语句（只包含"拆入保存"、"已拆入"、"还款保存"、"已还款"信息）
	 * @param qInfo
	 */
	public void getLendingFormSql(LendingFormQueryInfo qInfo){
		sbSelect = new StringBuffer();
		sbSelect.append(" lf.Id,lf.nOfficeId,lf.nCurrencyId,lf.sCode,lf.dtStartDate,lf.dtEndDate, \n ");
		sbSelect.append(" lf.dtPayDate,lf.mAmount,lf.mPayRate,lf.mRate,lf.sCounterParty,lf.sRecBankAccountNo, \n ");
		sbSelect.append(" lf.sRecBankAccountName,lf.nStatusId,lf.nInputUserId,lf.dtInputDate,lf.nCheckUserId, \n ");
		sbSelect.append(" lf.dtCheckDate,lf.nPayInputUserId,lf.dtPayInputDate,lf.nPayCheckUserId,lf.dtPayCheckDate, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = lf.nInputUserId) nInputUserName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = lf.nCheckUserId) nCheckUserName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = lf.nPayInputUserId) nPayInputUserName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = lf.nPayCheckUserId) nPayCheckUserName \n "); 
		sbFrom = new StringBuffer();
		sbFrom.append(" iplan_LendingForm lf \n ");
		sbWhere = new StringBuffer();
		sbWhere.append(" lf.nstatusid in("+IPLANConstant.LendStatus.LEND_SAVE+","+IPLANConstant.LendStatus.LENDED+","+IPLANConstant.LendStatus.REPAY_SAVE+","+IPLANConstant.LendStatus.REPAYED+") \n ");
		if(qInfo.getQ_dtStartDate() != ""){
			sbWhere.append(" and lf.dtStartDate >= to_date('"+qInfo.getQ_dtStartDate()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_dtEndDate() != ""){
			sbWhere.append(" and lf.dtEndDate <= to_date('"+qInfo.getQ_dtEndDate()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_sCounterParty() != ""){
			sbWhere.append(" and lf.sCounterParty like '%"+qInfo.getQ_sCounterParty()+"%' \n ");
		}
		if(qInfo.getQ_sRecBankAccountNo() != ""){
			sbWhere.append(" and lf.sRecBankAccountNo like '%"+qInfo.getQ_sRecBankAccountNo()+"%' \n ");
		}
		if(qInfo.getQ_nStatusId() != -1){
			sbWhere.append(" and lf.nstatusid = "+qInfo.getQ_nStatusId()+" \n ");
		}
		if(qInfo.isQ_isReview() == true){
			sbWhere.append(" and (lf.nstatusid in("+IPLANConstant.LendStatus.LEND_SAVE+","+IPLANConstant.LendStatus.REPAY_SAVE+") \n ");
			sbWhere.append(" and lf.nInputUserId != "+qInfo.getQ_currentUserId()+" \n ");
			sbWhere.append(" and lf.nPayInputUserId != "+qInfo.getQ_currentUserId()+" ) \n ");
		}
		sbOrderBy = new StringBuffer();
		sbOrderBy.append(" \n order by lf.dtStartDate DESC ");
	}
	
	/**
	 * 资金拆入台账查询
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 */
	public Collection findLendingFormForReport(LendingFormQueryInfo qInfo) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select lf.id,lf.sCode,lf.dtstartdate,lf.dtenddate,lf.mAmount,lf.mPayRate,lf.mrate,lf.sCounterParty,lf.nstatusid \n ");
		strSqlBuffer.append("from iplan_lendingform lf \n ");
		strSqlBuffer.append("where lf.nofficeid = "+qInfo.getQ_nOfficeId()+" and lf.ncurrencyid = "+qInfo.getQ_nCurrencyId()+" \n ");
		strSqlBuffer.append("and lf.nstatusid in("+IPLANConstant.LendStatus.LENDED+","+IPLANConstant.LendStatus.REPAY_SAVE+","+IPLANConstant.LendStatus.REPAYED+") \n ");
		strSqlBuffer.append("and lf.dtStartDate >= to_date('"+qInfo.getQ_dtStartDate()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and lf.dtStartDate <= to_date('"+qInfo.getQ_dtEndDate()+"','yyyy/mm/dd') \n ");
		if(qInfo.getQ_sCounterPartys() != "" && qInfo.getQ_sCounterPartys().length() > 0){
			strSqlBuffer.append("and lf.sCounterParty in('"+qInfo.getQ_sCounterPartys()+"') \n ");
		}
		strSqlBuffer.append("order by lf.dtstartdate \n ");
		log.info(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				LendingFormInfo lf = new LendingFormInfo();
				lf.setId(transRS.getLong("id"));
				lf.setsCode(transRS.getString("sCode"));
				lf.setDtStartDate(transRS.getTimestamp("dtstartdate"));
				lf.setDtEndDate(transRS.getTimestamp("dtenddate"));
				lf.setmAmount(transRS.getDouble("mAmount"));
				lf.setmPayRate(transRS.getDouble("mPayRate"));
				lf.setmRate(transRS.getDouble("mrate"));
				lf.setsCounterParty(transRS.getString("sCounterParty"));
				lf.setnStatusId(transRS.getLong("nstatusid"));
				collection.add(lf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
	/**
	 * 查询所有对手方
	 * @param qInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findCounterParty(LendingFormQueryInfo qInfo) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select distinct lf.sCounterParty \n ");
		strSqlBuffer.append("from iplan_lendingform lf \n ");
		strSqlBuffer.append("where lf.nofficeid = "+qInfo.getQ_nOfficeId()+" and lf.ncurrencyid = "+qInfo.getQ_nCurrencyId()+" \n ");
//		strSqlBuffer.append("and lf.nstatusid in("+IPLANConstant.LendStatus.LENDED+","+IPLANConstant.LendStatus.REPAY_SAVE+","+IPLANConstant.LendStatus.REPAYED+") \n ");
		strSqlBuffer.append("order by lf.sCounterParty \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				LendingFormInfo lf = new LendingFormInfo();
//				lf.setId(transRS.getLong("id"));
				lf.setsCounterParty(transRS.getString("sCounterParty"));
				collection.add(lf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
	/**
	 * 获取时间段内最大拆入利率
	 * @param qInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
//	public double findMaxPayRate(LendingFormQueryInfo qInfo) throws ITreasuryDAOException {
//		double d = 0.0;
//		initDAO();
//		strSqlBuffer = new StringBuffer();
//		strSqlBuffer.append("select nvl(max(lf.mpayrate),0) mPayRate \n ");
//		strSqlBuffer.append("from iplan_lendingform lf \n ");
//		strSqlBuffer.append("where lf.nofficeid = "+qInfo.getQ_nOfficeId()+" and lf.ncurrencyid = "+qInfo.getQ_nCurrencyId()+" \n ");
//		strSqlBuffer.append("and lf.nstatusid in("+IPLANConstant.LendStatus.LENDED+","+IPLANConstant.LendStatus.REPAY_SAVE+","+IPLANConstant.LendStatus.REPAYED+") \n ");
//		strSqlBuffer.append("and lf.dtStartDate >= to_date('"+qInfo.getQ_dtStartDate()+"','yyyy/mm/dd') \n ");
//		strSqlBuffer.append("and lf.dtStartDate <= to_date('"+qInfo.getQ_dtEndDate()+"','yyyy/mm/dd') \n ");
//		log.debug(strSqlBuffer.toString());
//		try {
//			prepareStatement(strSqlBuffer.toString());
//			transRS = executeQuery();	
//			while(transRS.next()) {
//				d = transRS.getDouble("mPayRate");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new ITreasuryDAOException("数据库异常发生", e);
//		} finally{
//			finalizeDAO();
//		}
//		return d;
//	}
	
}

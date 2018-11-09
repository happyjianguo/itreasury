package com.iss.itreasury.itreasuryinfo.subject.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceInfo;
import com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * 资金项目余额信息对数据库的操作方法
 * @author gmqiu
 *
 */
public class SubjectBalanceDao extends ITreasuryDAO {
	
	protected Log4j log = new Log4j(Constant.ModuleType.FUNDPLAN, this);

	private StringBuffer strSqlBuffer = null;
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	
	public SubjectBalanceDao() {
		super("IPLAN_SUBJECTBALANCE");
		setUseMaxID();
    }

    public SubjectBalanceDao(Connection conn) {
        super("IPLAN_SUBJECTBALANCE", conn);
        setUseMaxID();
    }
    
    /**
     * 添加资金项目余额信息
     * @param subjectBalanceInfo
     * @throws ITreasuryDAOException
     */
    public void insert(SubjectBalanceInfo subjectBalanceInfo) throws ITreasuryDAOException {
    	add(subjectBalanceInfo);
    }
    
    public void update(long id, double mBalance) throws ITreasuryDAOException {
    	initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_SUBJECTBALANCE set mBalance="+mBalance+" \n ");
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
     * 修改状态（删除、复核）
     * @param id
     * @param nStatusId 修改后的状态
     * @param nCheckUserId
     * @throws ITreasuryDAOException
     */
    public void updateStatusId(long id, long nStatusId, long nCheckUserId) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update IPLAN_SUBJECTBALANCE set nStatusId="+nStatusId+" \n ");
		//复核
		if(nStatusId == IPLANConstant.RateStatus.CHECKED){
			strSqlBuffer.append(",nCheckUserId="+nCheckUserId+" \n ");
			strSqlBuffer.append(",dtCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
		}
		//取消复核
		if(nStatusId == IPLANConstant.RateStatus.SAVED){
			strSqlBuffer.append(",nCheckUserId="+nCheckUserId+" \n ");
			strSqlBuffer.append(",dtCheckDate=to_date('','yyyy/mm/dd') \n ");
		}
		//删除
		if(nStatusId == IPLANConstant.RateStatus.DELETEED){
			strSqlBuffer.append(",nCheckUserId="+nCheckUserId+" \n ");
			strSqlBuffer.append(",dtCheckDate=to_date('','yyyy/mm/dd') \n ");
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
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws ClassNotFoundException
	 */
	public SubjectBalanceInfo findById(long id) throws ITreasuryDAOException{
		SubjectBalanceInfo subjectBalanceInfo = null;
		try {
			subjectBalanceInfo = (SubjectBalanceInfo) findByID(id,
				  Class.forName("com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceInfo"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return subjectBalanceInfo;
	}
	
	/**
	 * 根据ID和时间查询SubjectBalance
	 * @param id
	 * @param dtBalanceDate
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public boolean findByIdAndDate(long id, String dtBalanceDate) throws ITreasuryDAOException {
		boolean b = false;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select count(1) from iplan_subjectbalance sb \n  ");
		strSqlBuffer.append("where sb.nstatusid in(1,3) \n ");
		strSqlBuffer.append("and sb.dtbalancedate = to_date('"+dtBalanceDate+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and sb.id="+id+" \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();
			while(transRS.next()){
				int i = transRS.getInt(1);
				if(i > 0){
					b = true;
				}else{
					b = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return b;
	}
    
	/**
	 * 查询当日有效的资金项目余额信息
	 * @param nInputUserId
	 * @param dtBalanceDate
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findSubjectBalancesByDayForModify(String dtBalanceDate,long nKind) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select sb.nstatusid,sb.mBalance,sb.nInputUserId,s.nsubjectid,s.sSubjectName from iplan_subjectbalance sb,iplan_subject s \n  ");
		strSqlBuffer.append("where sb.nsubjectid = s.nsubjectid and sb.nstatusid in(1,3) \n ");
		strSqlBuffer.append("and sb.dtbalancedate = to_date('"+dtBalanceDate+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and s.nKind="+nKind+" \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();
			while(transRS.next()){
				SubjectBalanceInfo sb = new SubjectBalanceInfo();
				sb.setnStatusId(transRS.getLong("nstatusid"));
				sb.setmBalance(transRS.getDouble("mBalance"));
				sb.setnInputUserId(transRS.getLong("nInputUserId"));
				sb.setnSubjectID(transRS.getLong("nsubjectid"));
				sb.setsSubjectName(transRS.getString("sSubjectName"));
				collection.add(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
	public Collection findSubject(long nKind) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select s.nsubjectid,s.sSubjectName from iplan_subject s \n  ");
		strSqlBuffer.append("where s.nKind="+nKind+" \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();
			while(transRS.next()){
				SubjectBalanceInfo sb = new SubjectBalanceInfo();
				sb.setnSubjectID(transRS.getLong("nsubjectid"));
				sb.setsSubjectName(transRS.getString("sSubjectName"));
				collection.add(sb);
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
	 * 分页查询资金项目余额信息
	 * @param qInfo
	 * @return
	 */
	public PageLoader queryPageList(SubjectBalanceQueryInfo qInfo) {
		getPageSql(qInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sbFrom.toString(), sbSelect.toString(), sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.itreasuryinfo.subject.dataentity.SubjectBalanceInfo", null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
    }
	
	/**
	 * 资金项目余额信息SQL
	 * @param qInfo
	 */
    public void getPageSql(SubjectBalanceQueryInfo qInfo){
		sbSelect = new StringBuffer();
		sbSelect.append(" sb.id,sb.nOfficeId,sb.nCurrencyId,sb.nSubjectID, \n ");
		sbSelect.append(" sb.dtBalanceDate,sb.mBalance,sb.nStatusId,sb.nInputUserId, \n ");
		sbSelect.append(" sb.dtInputDate,sb.nCheckUserId,sb.dtCheckDate,s.nkind,s.ssubjectname, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = sb.nInputUserId) nInputUserName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = sb.nCheckUserId) nCheckUserName \n ");
		sbFrom = new StringBuffer();
		sbFrom.append(" iplan_SubjectBalance sb,iplan_Subject s \n ");
		sbWhere = new StringBuffer();
		sbWhere.append(" sb.nsubjectid = s.nsubjectid ");
		sbWhere.append(" and sb.nStatusId in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n ");
		if(qInfo.getQ_dtBalanceDateStart() != ""){
			sbWhere.append(" and sb.dtBalanceDate >= to_date('"+qInfo.getQ_dtBalanceDateStart()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_dtBalanceDateEnd() != ""){
			sbWhere.append(" and sb.dtBalanceDate <= to_date('"+qInfo.getQ_dtBalanceDateEnd()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_nStatusId() > 0){
			sbWhere.append(" and sb.nStatusId = "+qInfo.getQ_nStatusId()+" \n ");
		}
		if(qInfo.getQ_nKind() > 0){
			sbWhere.append(" and s.nKind = "+qInfo.getQ_nKind()+" \n ");
		}
		if(qInfo.getQ_sSubjectName() != ""){
			sbWhere.append(" and s.ssubjectname like '%"+qInfo.getQ_sSubjectName()+"%' \n ");
		}
		sbOrderBy = new StringBuffer();
		sbOrderBy.append(" \n order by sb.dtBalanceDate DESC ");
	}
    
    
    
}

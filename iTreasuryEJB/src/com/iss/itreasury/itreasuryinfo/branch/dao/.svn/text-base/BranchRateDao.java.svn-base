package com.iss.itreasury.itreasuryinfo.branch.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateInfo;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateQueryInfo;
import com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateQueryResultInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.system.dao.PageLoader;
/**
 * 用于存放银行账户（开户行）利率Dao
 * @author gmqiu
 *
 */
public class BranchRateDao extends ITreasuryDAO {
	
	protected Log4j log = new Log4j(Constant.ModuleType.FUNDPLAN, this);
	
	private StringBuffer strSqlBuffer = null;
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	public BranchRateDao() {
		super("Sett_BranchRate");
		setUseMaxID();
	}
	
	public BranchRateDao(Connection conn) {
		super("Sett_BranchRate",conn);
		setUseMaxID();
	}
	
	/**
	 * 根据ID查询BranchRateInfo
	 * @param id
	 * @return BranchRateInfo
	 * @throws SQLException 
	 */
	public BranchRateInfo findById(long id) throws ITreasuryDAOException{
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select * from Sett_BranchRate br where br.id = "+id+" ");
		log.debug(strSqlBuffer.toString());
		BranchRateInfo bri = new BranchRateInfo();
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				bri.setId(transRS.getLong("id"));
				bri.setnOfficeId(transRS.getLong("nOfficeId"));
				bri.setnCurrencyId(transRS.getLong("nCurrencyId"));
				bri.setBranchId(transRS.getLong("branchId"));
				bri.setDtValidate(transRS.getTimestamp("dtValidate"));
				bri.setmRate(transRS.getDouble("mRate"));
				bri.setnStatusId(transRS.getLong("nStatusId"));
				bri.setnInputUserId(transRS.getLong("nInputUserId"));
				bri.setDtInputDate(transRS.getTimestamp("dtInputDate"));
				bri.setnCheckUserId(transRS.getLong("nCheckUserId"));
				bri.setDtCheckDate(transRS.getTimestamp("dtCheckDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return bri;
	}
	
	/**
	 * 根据id查询用户ID
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public long getUserIdById(long id) throws ITreasuryDAOException{
		long l = 0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.nInputUserId from Sett_BranchRate br where br.id = "+id+" ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				l = transRS.getLong("nInputUserId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return l;
	}
	
	public String getBranchNameByBranchId(long branchId) throws ITreasuryDAOException{
		String s = "";
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select b.sname branchName from Sett_Branch b where b.id = "+branchId+" ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				s = transRS.getString("branchName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return s;
	}
	
	public String getNameByUserId(long userId) throws ITreasuryDAOException {
		String s = "";
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select u.sname userName from userinfo u where u.id = "+userId+" ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				s = transRS.getString("userName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return s;
	}
	
	/**
	 * 获取Sett_BranchRate表最大ID
	 * @return long
	 * @throws SQLException 
	 */
	public long getMaxId() throws ITreasuryDAOException {
		long l = 0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select nvl(max(id),0) id from Sett_BranchRate ");
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
	
	/**
	 * 往Sett_BranchRate表新增一条记录
	 * @param BranchRateInfo
	 */
	public void save(BranchRateInfo br) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("insert into Sett_BranchRate(id,nOfficeId,nCurrencyId,branchId,dtValidate,");
		strSqlBuffer.append("mRate,nStatusId,nInputUserId,dtInputDate,nCheckUserId,dtCheckDate) values");
		strSqlBuffer.append("("+geSequenceID()+","+br.getnOfficeId()+","+br.getnCurrencyId()+",");
		strSqlBuffer.append(""+br.getBranchId()+",to_date('"+br.getDtValidate().toString().substring(0, 10)+"','yyyy/mm/dd'),");
		strSqlBuffer.append(""+br.getmRate()+","+br.getnStatusId()+","+br.getnInputUserId()+",");
		strSqlBuffer.append("to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd'),"+br.getnCheckUserId()+",");
		strSqlBuffer.append("to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd'))");
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
	 * 复核，状态改为3
	 * @param id
	 * @param nCheckUserId
	 * @param dtCheckDate
	 */
	public void update_check(long id, long nCheckUserId) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update Sett_BranchRate set nStatusId="+IPLANConstant.RateStatus.CHECKED+",nCheckUserId="+nCheckUserId+",");
		strSqlBuffer.append("dtCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') where id="+id+"");
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
	 * 取消复核，状态改为1
	 * @param id
	 * @param nCheckUserId
	 * @param dtCheckDate
	 * @throws SQLException 
	 */
	public void update_uncheck(long id) throws ITreasuryDAOException, SQLException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update Sett_BranchRate set nStatusId="+IPLANConstant.RateStatus.SAVED+",nCheckUserId=-1,");
		strSqlBuffer.append("dtCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') ");
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
	 * 根据id修改某条记录
	 * @param id
	 * @param branchId
	 * @param mRate
	 * @param dtValidate
	 */
	public void update(long id, long branchId, double mRate, Timestamp dtValidate) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update Sett_BranchRate set branchId="+branchId+",mRate="+mRate+",");
		strSqlBuffer.append("dtValidate=to_date('"+dtValidate.toString().substring(0, 10)+"','yyyy/mm/dd')"+" where id="+id+"");
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
	 * 根据ID删除Sett_BranchRate表中某条记录
	 * @param id
	 * @return long
	 * @throws SQLException 
	 */
	public long delete(long id) throws ITreasuryDAOException {
		long l = 0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("delete from Sett_BranchRate where id = "+id+"");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			l = executeUpdate();	
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return l;
	}
	
    /**
     * 根据id删除某条记录，实际是把状态改成0
     * @param id
     * @throws SQLException 
     */
	public void delete_upadte(long id) throws ITreasuryDAOException {
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("update Sett_BranchRate set nStatusId="+IPLANConstant.RateStatus.DELETEED+" where id="+id+" ");
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
	
	public boolean isCountmRate(long branchId, double mRate, String dtValidate) throws ITreasuryDAOException {
		boolean b = false;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select count(1) from Sett_BranchRate br ");
		strSqlBuffer.append("where br.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") ");
		strSqlBuffer.append("and br.branchId = "+branchId+" ");
//			strSqlBuffer.append("and br.mRate = "+mRate+" ");
		strSqlBuffer.append("and br.dtValidate = to_date('"+dtValidate+"','yyyy/mm/dd')");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
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
	 * 根据名称模糊查询开户行
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findBranchByName(BranchRateQueryInfo qInfo, String sName) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.branchId, b.sName from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		strSqlBuffer.append("and b.sname like '%"+sName+"%' \n");
		strSqlBuffer.append("group by br.branchId,b.sName order by br.branchId \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setBranchId(transRS.getLong("branchId"));
				br.setsName(transRS.getString("sName"));
				collection.add(br);
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
	 * 根据名称模糊查询其他开户行
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findOtherBranchByName(BranchRateQueryInfo qInfo) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.branchId, b.sName from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		strSqlBuffer.append("and b.sname not like '%中国银行%' \n");
		strSqlBuffer.append("and b.sname not like '%工商%' \n");
		strSqlBuffer.append("and b.sname not like '%建设%' \n");
		strSqlBuffer.append("and b.sname not like '%交通%' \n");
		strSqlBuffer.append("and b.sname not like '%招商%' \n");
		strSqlBuffer.append("and b.sname not like '%邮政%' \n");
		strSqlBuffer.append("group by br.branchId,b.sName order by br.branchId \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setBranchId(transRS.getLong("branchId"));
				br.setsName(transRS.getString("sName"));
				collection.add(br);
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
	 * 获取开户行生效日期
	 * @param qInfo
	 * @return
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection findBranchDate(BranchRateQueryInfo qInfo) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.dtValidate from sett_branchRate br \n");
		strSqlBuffer.append("where br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(qInfo.getValidateStart() != ""){
			strSqlBuffer.append("and br.dtvalidate >= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getValidateEnd() != ""){
			strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getsBanchCode() != ""){
			strSqlBuffer.append("and br.branchid in("+qInfo.getsBanchCode()+") \n");
		}
		strSqlBuffer.append("group by br.dtValidate \n");
		strSqlBuffer.append("order by br.dtvalidate \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setDtValidate(transRS.getTimestamp("dtValidate"));
				collection.add(br);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		}finally{
			finalizeDAO();
		}
		return collection;
	} 
	
	/**
	 * 根据时间查询利率
	 * @param qInfo
	 * @param validate
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 * @throws SQLException
	 */
	public double findRateByCondition(BranchRateQueryInfo qInfo,String validate, String sName) throws ITreasuryDAOException {
		double d = 0.0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.mRate from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(validate != ""){
			strSqlBuffer.append("and br.dtvalidate = to_date('"+validate+"','yyyy/mm/dd') \n");
		}
		if(sName != "" && sName.length() > 5){
			strSqlBuffer.append("and (b.sname like '%"+sName.substring(0, sName.indexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.indexOf(",")+1, sName.lastIndexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.lastIndexOf(",")+1,sName.length())+"%' )\n");
		}
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				d = transRS.getDouble("mRate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		}finally{
			finalizeDAO();
		}
		return d;
	}
	
	/**
	 * 根据时间查询其他利率
	 * @param qInfo
	 * @param validate
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findOtherRateByCondition(BranchRateQueryInfo qInfo,String validate) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.mRate from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(validate != ""){
			strSqlBuffer.append("and br.dtvalidate = to_date('"+validate+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getsBanchCode() != ""){
			strSqlBuffer.append("and br.branchid in("+qInfo.getsBanchCode()+") \n");
		}
		strSqlBuffer.append("and b.sname not like '%中国银行%' \n");
		strSqlBuffer.append("and b.sname not like '%工商%' \n");
		strSqlBuffer.append("and b.sname not like '%建设%' \n");
		strSqlBuffer.append("and b.sname not like '%交通%' \n");
		strSqlBuffer.append("and b.sname not like '%招商%' \n");
		strSqlBuffer.append("and b.sname not like '%邮政%' \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setmRate(transRS.getDouble("mRate"));
				collection.add(br);
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
	 * 查询开户行的名称
	 * @param qInfo
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findByCondition(BranchRateQueryInfo qInfo, String sName) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select b.sName from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(qInfo.getValidateStart() != ""){
			strSqlBuffer.append("and br.dtvalidate >= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getValidateEnd() != ""){
			strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getsBanchCode() != ""){
			strSqlBuffer.append("and br.branchid in("+qInfo.getsBanchCode()+") \n");
		}
		if(sName != ""){
			strSqlBuffer.append("and b.sname like '%"+sName+"%' \n");
		}
		strSqlBuffer.append("group by b.sName \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setsName(transRS.getString("sName"));
				collection.add(br);
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
	 * 查询其他开户行的名称
	 * @param qInfo
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findOtherByCondition(BranchRateQueryInfo qInfo) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select b.sName from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid = "+IPLANConstant.RateStatus.CHECKED+" \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(qInfo.getValidateStart() != ""){
			strSqlBuffer.append("and br.dtvalidate >= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getValidateEnd() != ""){
			strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getsBanchCode() != ""){
			strSqlBuffer.append("and br.branchid in("+qInfo.getsBanchCode()+") \n");
		}
		strSqlBuffer.append("and b.sname not like '%中国银行%' \n");
		strSqlBuffer.append("and b.sname not like '%工商%' \n");
		strSqlBuffer.append("and b.sname not like '%建设%' \n");
		strSqlBuffer.append("and b.sname not like '%交通%' \n");
		strSqlBuffer.append("and b.sname not like '%招商%' \n");
		strSqlBuffer.append("and b.sname not like '%邮政%' \n");
		strSqlBuffer.append("group by b.sName \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setsName(transRS.getString("sName"));
				collection.add(br);
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
	 * 分页查询利率信息
	 * @param qInfo
	 * @return
	 */
	public PageLoader queryBranchRatePage(BranchRateQueryInfo qInfo) {
		getBranchRateSql(qInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sbFrom.toString(), sbSelect.toString(), sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.itreasuryinfo.branch.dataentity.BranchRateQueryInfo", null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
    }
	/**
	 * 利率信息查询语句（只包含"已保存"和"已复核"信息）
	 * @param qInfo
	 */
	public void getBranchRateSql(BranchRateQueryInfo qInfo) {
		sbSelect = new StringBuffer();
		sbSelect.append(" a.* from ( ");
		sbSelect.append(" select br.id,br.nOfficeId,br.nCurrencyId,br.branchId,br.dtValidate,br.mRate, \n ");
		sbSelect.append(" br.nStatusId,br.nInputUserId,br.dtInputDate,br.nCheckUserId,br.dtCheckDate, \n ");
		sbSelect.append(" (select b.sname from sett_branch b where b.id = br.branchid) branchName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = br.ninputuserid) nInputUserName, \n ");
		sbSelect.append(" (select u.sname from userinfo u where u.id = br.ncheckuserid) nCheckUserName \n ");
		sbFrom = new StringBuffer();
		sbFrom.append(" sett_branchrate br )a \n ");
		sbWhere = new StringBuffer();
		sbWhere.append(" a.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n ");
		if(qInfo.getDtValidateStart() != null){
			if(qInfo.getDtValidateStart().toString().length() >= 10){
				String dtValidateStart = qInfo.getDtValidateStart().toString().substring(0, 10);
				sbWhere.append("and a.dtvalidate >= to_date('"+dtValidateStart+"','yyyy/mm/dd') \n ");
			}
		}
		if(qInfo.getDtValidateEnd() != null){
			if(qInfo.getDtValidateEnd().toString().length() >= 10){
				String dtValidateEnd = qInfo.getDtValidateEnd().toString().substring(0, 10);
				sbWhere.append("and a.dtvalidate <= to_date('"+dtValidateEnd+"','yyyy/mm/dd') \n ");
			}
		}
		if(qInfo.getBranchName() != "" && qInfo.getBranchName().length() > 0){
			sbWhere.append("and a.branchName like '%"+qInfo.getBranchName()+"%' \n ");
		}
		if(qInfo.getnStatusId() != -1){
			sbWhere.append("and a.nstatusid = "+qInfo.getnStatusId()+" \n ");
		}
		sbOrderBy = new StringBuffer();
		sbOrderBy.append(" \n order by a.dtvalidate DESC, a.branchId ASC ");
	}
	
	/**
	 * 循环时间段
	 * @param validateStart
	 * @param validateEnd
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findDateSlot(String validateStart, String validateEnd) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select rownum, (to_date('"+validateStart+"','yyyy-mm-dd')+rownum-1) dtValidate from dual \n");
		strSqlBuffer.append("connect by rownum < to_date('"+validateEnd+"','yyyy-mm-dd')-to_date('"+validateStart+"','yyyy-mm-dd')+2 \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateQueryResultInfo br = new BranchRateQueryResultInfo();
				br.setValidate(transRS.getTimestamp("dtValidate"));
				collection.add(br);
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
	 * 循环表头
	 * @param qInfo
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findBranchNameByCondition(BranchRateQueryInfo qInfo, String sName) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select b.sbankaccountcode from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(qInfo.getValidateStart() != ""){
			strSqlBuffer.append("and br.dtvalidate >= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getValidateEnd() != ""){
			strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n");
		}
		if(sName != ""){
			strSqlBuffer.append("and (b.sname like '%"+sName.substring(0, sName.indexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.indexOf(",")+1, sName.lastIndexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.lastIndexOf(",")+1,sName.length())+"%' )\n");
		}
		strSqlBuffer.append("group by b.sbankaccountcode \n");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setsName(transRS.getString("sbankaccountcode"));
				collection.add(br);
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
	 * 循环利率
	 * @param qInfo
	 * @param sName
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findBranchRateByCondition(BranchRateQueryInfo qInfo,long branchId) throws ITreasuryDAOException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select b.sbankaccountcode,br.mRate,br.dtvalidate from sett_branchRate br,sett_branch b \n");
		strSqlBuffer.append("where br.branchid = b.id and b.nstatusid = "+IPLANConstant.VALID+" \n");
		strSqlBuffer.append("and br.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n");
		if(qInfo.getValidateStart() != ""){
			strSqlBuffer.append("and br.dtvalidate >= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n");
		}
		if(qInfo.getValidateEnd() != ""){
			strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n");
		}
		String sName = "";
		sName = IPLANConstant.Bank.getSimpleBankName(branchId);
		if(sName != "" ){
			strSqlBuffer.append("and (b.sname like '%"+sName.substring(0, sName.indexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.indexOf(",")+1, sName.lastIndexOf(","))+"%' \n");
			strSqlBuffer.append("or b.sname like '%"+sName.substring(sName.lastIndexOf(",")+1,sName.length())+"%' )\n");
		}
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateInfo br = new BranchRateInfo();
				br.setsName(transRS.getString("sbankaccountcode"));
				br.setmRate(transRS.getDouble("mRate"));
				br.setDtValidate(transRS.getTimestamp("dtvalidate"));
				collection.add(br);
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
	 * add by fxzhang  查询一段时间内所有开户行变更的记录
	 * @param qInfo 
	 * @return Collection<BranchRateQueryResultInfo>  
	 * @throws ITreasuryException
	 * @throws SQLException
	 */
	public Collection queryBranchRate(BranchRateQueryInfo qInfo) throws ITreasuryException {

//		select br.id,br.sbankaccountcode,br.sname,nbanktype,c.dtvalidate,c.mrate
//		 from sett_branch br,
//		(
//		select a.* from  sett_branchRate a,
//		(
//		--查询银行日利率设置功能中所有开户行的利率起始生效日期
//		select br.branchid,max(br.dtvalidate) maxdtvalidate
//		 from sett_branchRate br 
//		where br.nstatusid in(1,3) 
//		and br.nofficeid = 1 
//		and br.ncurrencyid = 1 
//		and br.dtvalidate <= to_date('2012-06-01','yyyy/mm/dd') 
//		group by br.branchid
//		) b
//		where a.branchid=b.branchid(+) and (a.dtvalidate >=b.maxdtvalidate or b.maxdtvalidate is null)
//		and a.dtvalidate<=to_date('2012-6-30','yyyy-mm-dd')
//		and a.nstatusid in (1,3)
//
//		) c
//		where br.nstatusid=1 and  br.id=c.branchid(+)
//		--and br.id=?
//		order by br.id,c.dtvalidate
		
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select br.id,br.sbankaccountcode,br.sname,nbanktype,c.dtvalidate,c.mrate \n ");
		strSqlBuffer.append("from sett_branch br,(select a.* from  sett_branchRate a,( \n ");
		strSqlBuffer.append("select br.branchid,max(br.dtvalidate) maxdtvalidate \n ");
		strSqlBuffer.append("from sett_branchRate br \n ");
		strSqlBuffer.append("where br.nstatusid in("+IPLANConstant.RateStatus.CHECKED+") \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n ");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n ");
		strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("group by br.branchid) b \n ");
		strSqlBuffer.append("where a.branchid = b.branchid(+) \n ");
		strSqlBuffer.append("and (a.dtvalidate >= b.maxdtvalidate or b.maxdtvalidate is null) \n ");
		strSqlBuffer.append("and a.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and a.nstatusid in("+IPLANConstant.RateStatus.CHECKED+")) c \n ");
		strSqlBuffer.append("where br.nstatusid = "+IPLANConstant.VALID+" and br.id = c.branchid(+) \n ");
		strSqlBuffer.append("and c.mrate is not null \n ");
		strSqlBuffer.append("order by c.dtvalidate \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateQueryResultInfo br = new BranchRateQueryResultInfo();
				br.setBranchID(transRS.getLong("id"));
				br.setBankAccountCode(transRS.getString("sbankaccountcode"));
				br.setBankAccountName(transRS.getString("sname"));
				br.setBankTypeID(transRS.getLong("nbanktype"));
				br.setValidate(transRS.getTimestamp("dtvalidate"));
				br.setRate(transRS.getDouble("mRate"));
				collection.add(br);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
	public double findBefforValidateRate(BranchRateQueryInfo qInfo,String validate, long branchId) throws ITreasuryException {
		double d = 0.0;
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select mrate from \n ");
		strSqlBuffer.append("(select rownum rn,a.* from \n ");
		strSqlBuffer.append("(select br.branchid,max(br.dtvalidate) maxdtvalidate,br.mrate \n ");
		strSqlBuffer.append("from sett_branchRate br  \n ");
		strSqlBuffer.append("where br.nstatusid in("+IPLANConstant.RateStatus.CHECKED+") \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n ");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n ");
		strSqlBuffer.append("and br.dtvalidate <= to_date('"+validate+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and br.branchid = "+branchId+" \n ");
		strSqlBuffer.append("group by br.branchid,br.mrate order by maxdtvalidate desc) a) b where b.rn = 1 \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				d = transRS.getDouble("mrate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return d;
	}
	
	public Collection findFirstColumn(BranchRateQueryInfo qInfo) throws ITreasuryException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select nbanktype from ( \n");
		strSqlBuffer.append("select br.id,br.sbankaccountcode,br.sname,nbanktype,c.dtvalidate,c.mrate \n ");
		strSqlBuffer.append("from sett_branch br,(select a.* from  sett_branchRate a,( \n ");
		strSqlBuffer.append("select br.branchid,max(br.dtvalidate) maxdtvalidate \n ");
		strSqlBuffer.append("from sett_branchRate br \n ");
		strSqlBuffer.append("where br.nstatusid in("+IPLANConstant.RateStatus.CHECKED+") \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n ");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n ");
		strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("group by br.branchid) b \n ");
		strSqlBuffer.append("where a.branchid = b.branchid(+) \n ");
		strSqlBuffer.append("and (a.dtvalidate >= b.maxdtvalidate or b.maxdtvalidate is null) \n ");
		strSqlBuffer.append("and a.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and a.nstatusid in("+IPLANConstant.RateStatus.CHECKED+")) c \n ");
		strSqlBuffer.append("where br.nstatusid = "+IPLANConstant.VALID+" and br.id = c.branchid(+) \n ");
		strSqlBuffer.append("and c.mrate is not null \n ");
		strSqlBuffer.append("order by br.id,c.dtvalidate \n ");
		strSqlBuffer.append(") group by nbanktype order by nbanktype \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateQueryResultInfo br = new BranchRateQueryResultInfo();
				br.setBankTypeID(transRS.getLong("nbanktype"));
				collection.add(br);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
	public Collection findSecondColumn(BranchRateQueryInfo qInfo,long nBankType) throws ITreasuryException {
		Collection collection = new ArrayList();
		initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append("select id,sbankaccountcode,sname,nbanktype from ( \n");
		strSqlBuffer.append("select br.id,br.sbankaccountcode,br.sname,nbanktype,c.dtvalidate,c.mrate \n ");
		strSqlBuffer.append("from sett_branch br,(select a.* from  sett_branchRate a,( \n ");
		strSqlBuffer.append("select br.branchid,max(br.dtvalidate) maxdtvalidate \n ");
		strSqlBuffer.append("from sett_branchRate br \n ");
		strSqlBuffer.append("where br.nstatusid in("+IPLANConstant.RateStatus.CHECKED+") \n ");
		strSqlBuffer.append("and br.nofficeid = "+qInfo.getnOfficeId()+" \n ");
		strSqlBuffer.append("and br.ncurrencyid = "+qInfo.getnCurrencyId()+" \n ");
		strSqlBuffer.append("and br.dtvalidate <= to_date('"+qInfo.getValidateStart()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("group by br.branchid) b \n ");
		strSqlBuffer.append("where a.branchid = b.branchid(+) \n ");
		strSqlBuffer.append("and (a.dtvalidate >= b.maxdtvalidate or b.maxdtvalidate is null) \n ");
		strSqlBuffer.append("and a.dtvalidate <= to_date('"+qInfo.getValidateEnd()+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append("and a.nstatusid in("+IPLANConstant.RateStatus.CHECKED+")) c \n ");
		strSqlBuffer.append("where br.nstatusid = "+IPLANConstant.VALID+" and br.id = c.branchid(+) \n ");
		strSqlBuffer.append("and c.mrate is not null \n ");
		strSqlBuffer.append("order by br.id,c.dtvalidate)  \n ");
		if(nBankType > 0){
			strSqlBuffer.append("where nbanktype = "+nBankType+" ");
		}
		strSqlBuffer.append("group by id,sbankaccountcode,sname,nbanktype order by nbanktype \n ");
		log.debug(strSqlBuffer.toString());
		try {
			prepareStatement(strSqlBuffer.toString());
			transRS = executeQuery();	
			while(transRS.next()) {
				BranchRateQueryResultInfo br = new BranchRateQueryResultInfo();
				br.setBranchID(transRS.getLong("id"));
				br.setBankAccountCode(transRS.getString("sbankaccountcode"));
				br.setBankAccountName(transRS.getString("sname"));
				collection.add(br);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return collection;
	}
	
}

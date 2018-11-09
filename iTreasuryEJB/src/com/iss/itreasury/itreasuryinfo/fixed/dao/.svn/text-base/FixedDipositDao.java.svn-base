package com.iss.itreasury.itreasuryinfo.fixed.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositInfo;
import com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositQueryInfo;
import com.iss.itreasury.itreasuryinfo.util.IPLANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * 对公司定期存款信息的操作
 * @author gmqiu
 *
 */
public class FixedDipositDao extends ITreasuryDAO {
	
	protected Log4j log = new Log4j(Constant.ModuleType.FUNDPLAN, this);

	private StringBuffer strSqlBuffer = null;
	private StringBuffer sbSelect = null;
	private StringBuffer sbFrom = null;
	private StringBuffer sbWhere = null;
	private StringBuffer sbOrderBy = null;
	
	public FixedDipositDao() {
		super("IPLAN_FixedDiposit");
		setUseMaxID();
    }

    public FixedDipositDao(Connection conn) {
        super("IPLAN_FixedDiposit", conn);
        setUseMaxID();
    }
    
    /**
     * 新增定期存款信息
     * @param fixedDipositInfo
     * @throws ITreasuryDAOException
     */
    public void insertFixedDiposit(FixedDipositInfo fixedDipositInfo) throws ITreasuryDAOException{
    	add(fixedDipositInfo);
    }
    
    /**
     * 根据Id查询定期存款信息
     * @param id
     * @return
     * @throws ITreasuryDAOException
     */
    public FixedDipositInfo findById(long id) throws ITreasuryDAOException {
    	FixedDipositInfo fixedDipositInfo = null;
    	try {
    		fixedDipositInfo = (FixedDipositInfo) findByID(id,
				  Class.forName("com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositInfo"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return fixedDipositInfo;
    }
    
    /**
     * 修改定期存款信息
     * @param fixedDipositInfo
     * @throws ITreasuryDAOException
     */
    public void updateFixedDiposit(FixedDipositInfo fixedDipositInfo) throws ITreasuryDAOException{
    	initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append(" update iplan_FixedDiposit set \n ");
		strSqlBuffer.append(" dtOpenDate=to_date('"+DataFormat.formatDate(fixedDipositInfo.getDtOpenDate())+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append(",dtEndDate=to_date('"+DataFormat.formatDate(fixedDipositInfo.getDtEndDate())+"','yyyy/mm/dd') \n ");
		strSqlBuffer.append(",mAmount="+fixedDipositInfo.getmAmount()+" \n ");
		strSqlBuffer.append(",mRate="+fixedDipositInfo.getmRate()+" \n ");
		strSqlBuffer.append(",nDepositTerm="+fixedDipositInfo.getnDepositTerm()+" \n ");
		strSqlBuffer.append(",nMonthOrDay="+fixedDipositInfo.getnMonthOrDay()+" \n ");
		strSqlBuffer.append(",nDepositMode="+fixedDipositInfo.getnDepositMode()+" \n ");
		strSqlBuffer.append(",sRival='"+fixedDipositInfo.getsRival()+"' \n ");
		strSqlBuffer.append(",sRemark='"+fixedDipositInfo.getsRemark()+"' \n ");
		strSqlBuffer.append(" where id="+fixedDipositInfo.getId()+"");
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
     * 修改定期存款状态，状态为0时表示"已删除"，状态为3时表示"已复核"，状态改为1时表示"取消复核"
     * @param nStatusId
     * @throws ITreasuryDAOException
     */
    public void updateStatus(long id, long nStatusId, long nCheckUserId) throws ITreasuryDAOException{
    	initDAO();
		strSqlBuffer = new StringBuffer();
		strSqlBuffer.append(" update iplan_FixedDiposit set \n ");
		//复核
		if(nStatusId == IPLANConstant.RateStatus.CHECKED){
			strSqlBuffer.append(" nStatusId="+nStatusId+" \n ");
			strSqlBuffer.append(",nCheckUserId="+nCheckUserId+" \n ");
			strSqlBuffer.append(",dtCheckDate=to_date(to_char((select sysdate from dual),'yyyy/mm/dd'),'yyyy/mm/dd') \n ");
		}
		//取消复核
		if(nStatusId == IPLANConstant.RateStatus.SAVED){
			strSqlBuffer.append(" nStatusId="+nStatusId+" \n ");
			strSqlBuffer.append(",nCheckUserId=-1 \n ");
			strSqlBuffer.append(",dtCheckDate=to_date('','yyyy/mm/dd') \n ");
		}
		//删除
		if(nStatusId == IPLANConstant.RateStatus.DELETEED){
			strSqlBuffer.append(" nStatusId="+nStatusId+" \n ");
		}
		strSqlBuffer.append(" where id="+id+"");
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
     * 分页查询定期存款信息
     * @param qInfo
     * @return
     */
    public PageLoader queryPage(FixedDipositQueryInfo qInfo) {
    	getPageSql(qInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sbFrom.toString(), sbSelect.toString(), sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.itreasuryinfo.fixed.dataentity.FixedDipositInfo", null);
		pageLoader.setOrderBy(" " + sbOrderBy.toString() + " ");
		return pageLoader;
    }
    
    /**
     * 分页查询语句
     * @param qInfo
     */
    public void getPageSql(FixedDipositQueryInfo qInfo){
    	sbSelect = new StringBuffer();
    	sbSelect.append("t.id,t.nofficeid,t.ncurrencyid,t.dtopendate,t.dtenddate \n ");
    	sbSelect.append(",t.mamount,t.mbalance,t.mrate,t.ndepositterm,t.nmonthorday \n ");
    	sbSelect.append(",t.ndepositmode,t.srival,t.sremark,t.dtfinish,t.ndepositstatusid \n ");
    	sbSelect.append(",t.nstatusid,t.ninputuserid,t.dtinputdate,t.ncheckuserid,t.dtcheckdate  \n ");
    	sbFrom = new StringBuffer();
		sbFrom.append(" iplan_fixeddiposit t \n ");
		sbWhere = new StringBuffer();
		sbWhere.append("t.nstatusid in("+IPLANConstant.RateStatus.SAVED+","+IPLANConstant.RateStatus.CHECKED+") \n ");
		sbWhere.append("and t.nofficeid = "+qInfo.getQ_nOfficeId()+" \n ");
		sbWhere.append("and t.ncurrencyid = "+qInfo.getQ_nCurrencyId()+" \n ");
		if(qInfo.getQ_dtOpenDate() != ""){
			sbWhere.append("and t.dtOpenDate >= to_date('"+qInfo.getQ_dtOpenDate()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_dtEndDate() != ""){
			sbWhere.append("and t.dtOpenDate <= to_date('"+qInfo.getQ_dtEndDate()+"','yyyy/mm/dd') \n ");
		}
		if(qInfo.getQ_nStatusId() != -1){
			sbWhere.append("and t.nstatusid = "+qInfo.getQ_nStatusId()+" \n ");
		}
		sbOrderBy = new StringBuffer();
		if(qInfo.getDesc() == 1){
			if(qInfo.getOrderParam() == 1){
				sbOrderBy.append(" \n order by t.dtopendate ");
			}
			if(qInfo.getOrderParam() == 2){
				sbOrderBy.append(" \n order by t.dtEndDate ");
			}
			if(qInfo.getOrderParam() == 3){
				sbOrderBy.append(" \n order by nDepositMode ");
			}
			if(qInfo.getOrderParam() == 4){
				sbOrderBy.append(" \n order by t.mAmount ");
			}
			if(qInfo.getOrderParam() == 5){
				sbOrderBy.append(" \n order by t.mRate ");
			}
		}else{
			if(qInfo.getOrderParam() == 1){
				sbOrderBy.append(" \n order by t.dtopendate desc ");
			}
			if(qInfo.getOrderParam() == 2){
				sbOrderBy.append(" \n order by t.dtEndDate desc ");
			}
			if(qInfo.getOrderParam() == 3){
				sbOrderBy.append(" \n order by nDepositMode desc ");
			}
			if(qInfo.getOrderParam() == 4){
				sbOrderBy.append(" \n order by t.mAmount desc ");
			}
			if(qInfo.getOrderParam() == 5){
				sbOrderBy.append(" \n order by t.mRate desc ");
			}
		}
    }
	
}

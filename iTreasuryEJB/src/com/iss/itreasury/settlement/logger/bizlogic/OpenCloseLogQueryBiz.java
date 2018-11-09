package com.iss.itreasury.settlement.logger.bizlogic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.logger.dao.OpenCloseLogDao;
import com.iss.itreasury.settlement.logger.dataentity.OpenCloseLogDetailInfo;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.system.dao.PageLoader;

public class OpenCloseLogQueryBiz {
	
	/**
	 * 返回关机日志集合
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public Collection getCloseLogInfo(String code) throws ITreasuryDAOException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		Collection c = dao.getCloseLogInfo(code);
		return c;
	}
	
	/**
	 * 返回关机日志业务校验错误信息集合
	 * @param openCloseLogId
	 * @return
	 * @throws SQLException
	 */
	public Collection getCloseSystemErrorBussinessInfo(long openCloseLogId) throws ITreasuryDAOException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		Collection c = dao.getCloseSystemErrorBussinessInfo(openCloseLogId);
		return c;
	}
	
	public Collection getOpenCLoseSystemLog(QueryOpenCloseLog searchInfo) throws ITreasuryDAOException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		Collection c = dao.getOpenCLoseLogInfo(searchInfo);
		return c;
	}
	
	public Collection getColseLogTransInfo(long openCloseLogId) throws ITreasuryDAOException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		Collection c = dao.getColseLogTransInfo(openCloseLogId);
		return c;
	}
	
	/**
	 * 返回汇总的日志PageLoader
	 * @param searchInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader findOpenCloseLogInfo(QueryOpenCloseLog searchInfo) throws RemoteException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		return dao.findOpenCloseLogInfo(searchInfo);
	}
	
	/**
	 * 根据交易类型查询关机中业务校验的详细日志信息
	 * @param openCloseLogId
	 * @param transId
	 * @return
	 * @throws RemoteException
	 */
	public PageLoader findOpenCloseLogDetailInfo(OpenCloseLogDetailInfo searchInfo) throws RemoteException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		return dao.findOpenCloseLogDetailInfo(searchInfo);
	}
	
	/**
	 * 查询单编号的日志信息
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public QueryOpenCloseLog findOpenCloseLogInfo(String code) throws ITreasuryDAOException {
		OpenCloseLogDao dao = new OpenCloseLogDao();
		return dao.findOpenCloseLogInfo(code);
	}

}

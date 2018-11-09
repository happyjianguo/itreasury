/*
 * Created on 2007-6-27
 */
package com.iss.itreasury.settlement.logger.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.iss.itreasury.settlement.logger.dao.OpenCloseLogDao;
import com.iss.itreasury.settlement.logger.dataentity.OpenCloseLogDetailInfo;
import com.iss.itreasury.settlement.logger.dataentity.OpenCloseLogInfo;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author leiyang
 *
 */
public class OpenCloseLogBiz {
	
	private long lOfficeId = -1;
	private long lCurrencyId = -1;
	public long lUserId = -1;
	public long lopenCloseType = -1;
	public long lBatchNo = -1;
	public String strLogCode = "";
	public Timestamp systemData = null;
	public Connection conn = null;
	public boolean isSelfManagedConn = false;
	
	public OpenCloseLogBiz(long lOfficeId, long lCurrencyId, long lUserId, long lopenCloseType) throws IException{
    	this.lOfficeId = lOfficeId;
    	this.lCurrencyId = lCurrencyId;
    	this.lUserId = lUserId;
    	this.lopenCloseType = lopenCloseType;
    	this.systemData = Env.getSystemDate(lOfficeId,lCurrencyId);
		this.strLogCode = this.formatDate(Env.getSystemDateTime(),lOfficeId,lCurrencyId);
		
		OpenCloseLogDao dao = new OpenCloseLogDao();
		this.lBatchNo = dao.getBatchNo(lOfficeId, lCurrencyId, lopenCloseType,systemData);
	}
	
	public void colseConn() throws IException{
		try {
			if (conn != null && conn.isClosed() == false) {
				isSelfManagedConn = false;
				conn.close();
				conn = null;
			}
        }
        catch (Exception e) {
            throw new IException("关闭数据库连接失败 " + e.getMessage());
        }
	}
	
	private Timestamp getSystemDateTime() throws Exception{
		return Env.getSystemDateTime(lOfficeId, lCurrencyId);
	}
	
	//Format the current time.
    private String formatDate(Timestamp date, long lOfficeId, long lCurrencyId) throws IException {
        String dateString = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
            dateString = formatter.format(date);
            dateString = dateString + "-" + String.valueOf(lOfficeId) + "-" + String.valueOf(lCurrencyId);
        }
        catch (Exception e) {
            throw new IException("Gen_E001");
        }
        return dateString;
    }
    
    private OpenCloseLogDao getOpenCloseLogDaoInstance(){
		if(conn == null && isSelfManagedConn == false){
			return new OpenCloseLogDao();
		}
		return	new OpenCloseLogDao(conn);
    }
    
	/**
	 * 添加关机的log
	 * @param logType
	 * @param succeedType
	 * @param content
	 * @throws IException
	 */
    public void addCloseLog(long logType,long succeedType,String content) throws IException{
		try{
			OpenCloseLogInfo info = new OpenCloseLogInfo();
			info.setOfficeId(lOfficeId);
			info.setCurrencyId(lCurrencyId);
			info.setOpenCloseUserId(lUserId);
			info.setOpenCloseType(SETTConstant.OpenCloseType.CLOSE);
			info.setLogType(logType);
			info.setBatchNo(lBatchNo);
			info.setStatus(succeedType);
			info.setContent(content);
			info.setCode(strLogCode);
			
			getOpenCloseLogDaoInstance().addLog(info, getSystemDateTime());
		}
		catch(Exception e) {
			e.printStackTrace();
			String strLog = "添加 " + SETTConstant.CloseSystemLogType.getName(logType) + " 的关机日志信息失败";
			System.out.println(strLog);
			throw new IException(strLog);
		}
    }
    
    /**
     * 添加关机的log,带返回参数
     * @param logType
     * @param succeedType
     * @param content
     * @throws IException
     */
    public long addCloseLogReturnId(long logType,long succeedType,String content) throws IException{
		try{
			OpenCloseLogInfo info = new OpenCloseLogInfo();
			info.setOfficeId(lOfficeId);
			info.setCurrencyId(lCurrencyId);
			info.setOpenCloseUserId(lUserId);
			info.setOpenCloseType(SETTConstant.OpenCloseType.CLOSE);
			info.setLogType(logType);
			info.setBatchNo(lBatchNo);
			info.setStatus(succeedType);
			info.setContent(content);
			info.setCode(strLogCode);
			
			long id = getOpenCloseLogDaoInstance().addLog(info, getSystemDateTime());
			return id;
		}
		catch(Exception e) {
			e.printStackTrace();
			String strLog = "添加 " + SETTConstant.CloseSystemLogType.getName(logType) + " 的关机日志信息失败";
			System.out.println(strLog);
			throw new IException(strLog);
		}
    }
    
    /**
     * 
     * @param openCloseId
     * @param transTypeId
     * @param transId
     * @param transNo
     * @param logType
     * @throws Exception
     */
	public void addCloseDetailLog(long openCloseId,long transTypeId,long transId,String transNo,long logType) throws Exception {
		try{
			OpenCloseLogDetailInfo detailInfo = new OpenCloseLogDetailInfo();
			detailInfo.setOpenCloseLogId(openCloseId);
			detailInfo.setTransTypeId(transTypeId);
			detailInfo.setTransId(transId);
			detailInfo.setTransNo(transNo);
			detailInfo.setLogType(logType);
			detailInfo.setBatchNo(lBatchNo);
			
			getOpenCloseLogDaoInstance().addDetailLog(detailInfo);
		}
		catch(Exception e) {
			e.printStackTrace();
			String strLog = "添加 " + SETTConstant.CloseSystemLogType.getName(logType) + " 的关机日志信息失败";
			System.out.println(strLog);
			throw new IException(strLog);
		}
	}
	
	/**
	 * 添加开机的log
	 * @param logType
	 * @param succeedType
	 * @param content
	 * @throws IException
	 */
	public void addOpenLog(long logType,long succeedType,String content) throws IException {
		try{
			OpenCloseLogInfo info = new OpenCloseLogInfo();
			info.setOfficeId(lOfficeId);
			info.setCurrencyId(lCurrencyId);
			info.setOpenCloseUserId(lUserId);
			info.setOpenCloseType(SETTConstant.OpenCloseType.OPEN);
			info.setLogType(logType);
			info.setBatchNo(lBatchNo);
			info.setStatus(succeedType);
			info.setContent(content);
			info.setCode(strLogCode);
			
			getOpenCloseLogDaoInstance().addLog(info, getSystemDateTime());
		}
		catch(Exception e) {
			e.printStackTrace();
			String strLog = "添加 " + SETTConstant.CloseSystemLogType.getName(logType) + " 的开机日志信息失败";
			System.out.println(strLog);
			throw new IException(strLog);
		}
	}
	
	

}

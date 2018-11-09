package com.iss.itreasury.settlement.setting.ejb;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.settlement.setting.bizlogic.OfficeBiz;
import com.iss.itreasury.settlement.setting.bizlogic.SettOfficeTimeBiz;
import com.iss.itreasury.settlement.setting.dataentity.SettOfficeTimeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;

public class CurrencySettingEJB implements SessionBean{
	private javax.ejb.SessionContext mySessionCtx = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2592022384328785353L;

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO 自动生成方法存根
		mySessionCtx = arg0;
	}
	
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext(){
		return mySessionCtx;
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException{
	}
	
//	******用户自加入代码*******//
	/**
	 * 新增所有用款计划
	 */
	public long saveAllCurrency(long officeId,String strTransactionType) throws IRollbackException,RemoteException{
		long lreturn = -1;
		try{
			OfficeBiz biz = new OfficeBiz();
			SettOfficeTimeBiz settOfficeTimeBiz = new SettOfficeTimeBiz();
			SettOfficeTimeInfo settOfficeTimeInfo = null;
			//String strOldTransactionType = biz.findCurrencyIDByOfficeID(officeId);
			//String strNewTransactionType = "";
			String[] currencyId = null;
			Collection c = settOfficeTimeBiz.getExistCurrency(officeId);
			lreturn=biz.updateOffiecCurrency(officeId,strTransactionType);
//			if(strOldTransactionType != null && strOldTransactionType.length() > 0 && (strTransactionType.length() > strOldTransactionType.length()) ){
//				strNewTransactionType = strTransactionType.substring(strOldTransactionType.length()+1, strTransactionType.length());
//			}
			if(strTransactionType.length() > 0){
		    	currencyId = DataFormat.splitString(strTransactionType, ",");
			}			
			if(currencyId != null && currencyId.length > 0){
				settOfficeTimeInfo = new SettOfficeTimeInfo();
				for(int i = 0 ; i < currencyId.length ; i ++){
					if(c != null && c.contains(currencyId[i])){
						continue;
					}else{
						settOfficeTimeInfo.setDtApplyTime(Env.getSystemDateTime());
						settOfficeTimeInfo.setDtCalInterest(DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDate())));
						settOfficeTimeInfo.setDtOpenDate(DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDate())));
						settOfficeTimeInfo.setNCloseSystemStatusId(1);
						settOfficeTimeInfo.setNCurrencyId(Long.parseLong(currencyId[i]));
						settOfficeTimeInfo.setNOfficeId(officeId);
						settOfficeTimeInfo.setNStatusId(1);
						settOfficeTimeInfo.setNSystemStatusId(1);
						settOfficeTimeInfo.setSSystemStatusDesc(Constant.SystemStatus.getName(Constant.SystemStatus.OPEN));
						settOfficeTimeBiz.addSettOfficeTime(settOfficeTimeInfo);
					}
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lreturn;
	}
}

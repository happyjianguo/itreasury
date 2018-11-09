/*
 * Created on 2007-2-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.accesslog.bizlogic;

import com.iss.itreasury.fcinterface.bankportal.accesslog.dao.LoggerDAO;
import com.iss.itreasury.fcinterface.bankportal.accesslog.dataentity.LoggerInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;

/**
 * @author xinan
 *
 */
public class LoggerBiz {

    /**
     * 
     */
    public LoggerBiz() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void add(LoggerInfo loggerInfo) throws BusinessException
    {
        LoggerDAO loggerDAO = null;
        try{
            loggerDAO 
        		= (LoggerDAO) DAOFactory.getDAOImpl(LoggerDAO.class, null);
            loggerInfo.setId(-1);
            //币种默认为人民币
            loggerInfo.setCurrencyID(1);
            loggerDAO.add(loggerInfo);
            
        }catch(SystemException ex){
            ex.printStackTrace();
            throw new BusinessException("添加业务日志失败", ex); 
        }
    }
}

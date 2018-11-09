/*
 * Created on 2007-2-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.accesslog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dao.BaseDAO_oracle;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.Database;
import com.iss.itreasury.fcinterface.bankportal.util.Env;

/**
 * @author xinan
 *
 */
public class LoggerDAO_oracle extends BaseDAO_oracle implements LoggerDAO {

    /**
     * @param conn
     * @throws SystemException
     */
    public LoggerDAO_oracle(Connection conn) throws SystemException {
        super(tableName, isNeedPrefix, conn);
        Boolean isNeedLog = new Boolean(Env.getEnvConfigItem(Env.ISACCESSLOGSHARE));
        //如果系统和其它系统共用一个业务系统表，那么id使用sequence
        if(isNeedLog.booleanValue())
        {
            this.setIDType(ID_TYPE_SQUENCE);
        }else{
            this.setIDType(ID_TYPE_MAXID);
        }
    }
    
    public long geSequenceID() throws SystemException
    {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        long nextid = -1;
		String sql = "SELECT SEQ_SYS_LOGGER.nextval nextid from dual";
		try{
			conn = Database.getConnection();				
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();	
			
			if (rs.next())
			{
				nextid = rs.getLong("nextid");
			}				
			
		}catch(Exception ex){
		    ex.printStackTrace();
		    throw new SystemException("获取业务系统ID异常:" + ex.getMessage(), ex);
		}
        return nextid;
    }

}

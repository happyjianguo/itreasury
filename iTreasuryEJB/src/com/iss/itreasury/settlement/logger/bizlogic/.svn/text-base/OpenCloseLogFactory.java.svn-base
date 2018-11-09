package com.iss.itreasury.settlement.logger.bizlogic;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class OpenCloseLogFactory {
	
	private static Map instance = new HashMap();
	
	/**
	 * 初始化并得到 OpenCloseLogBiz 实例
	 * 
	 * @param lOfficeId
	 * @param lCurrencyId
	 * @param lUserId
	 * @param lopenCloseType
	 * @return
	 * @throws IException
	 */
	public static void bulidInstance(long lOfficeId, long lCurrencyId, long lUserId, long lopenCloseType) throws IException{

        try {
        	String strKey = String.valueOf(lOfficeId) + "--" +  String.valueOf(lCurrencyId);
    		OpenCloseLogBiz ocLog = new OpenCloseLogBiz(lOfficeId,lCurrencyId,lUserId,lopenCloseType);
			instance.put(strKey, ocLog);
        }
        catch(Exception e) {
            throw new IException("Gen_E001");
        }
	}

	/**
	 * 得到 OpenCloseLogBiz 实例
	 * 
	 * @return
	 * @throws IException
	 */
	public static OpenCloseLogBiz getInstance(long lOfficeId, long lCurrencyId, boolean isSelfManagedConn) throws IException{
		try {
			String strKey = String.valueOf(lOfficeId) + "--" +  String.valueOf(lCurrencyId);
			if(instance.get(strKey) == null){
				throw new IException("Gen_E001");
			}
			OpenCloseLogBiz biz = (OpenCloseLogBiz)instance.get(strKey);
			if(isSelfManagedConn == true){
				biz.isSelfManagedConn = true;
				biz.conn = Database.getConnection();
			}
			return biz;
        }
        catch(Exception e) {
            throw new IException("Gen_E001");
        }
	}
	
	
	/**
	 * 清除 OpenCloseLogBiz 实例
	 * @throws IException 
	 *
	 */
	public static void clear(long lOfficeId, long lCurrencyId) throws IException{
		try {
			String strKey = String.valueOf(lOfficeId) + "--" +  String.valueOf(lCurrencyId);
			if(instance.get(strKey) != null){
				instance.remove(strKey);
			}
        }
        catch(Exception e) {
            throw new IException("Gen_E001");
        }
	}

}

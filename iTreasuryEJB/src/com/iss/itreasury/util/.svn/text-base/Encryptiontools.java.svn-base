package com.iss.itreasury.util;

import java.sql.SQLException;
import java.util.HashMap;

public class Encryptiontools {
	
	private static HashMap keyMap = new HashMap();
	/**
	 * 自动生成一个密钥，并记录在数据库
	 * @return　密钥 java.lang.String
	 * @throws SQLException 
	 */
	public synchronized static  String getEncryption() throws SQLException{
		long time = System.currentTimeMillis();
		String key = String.valueOf(time);
		try 
		{
			keyMap.put(key, "");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
		return key;
		
	}
	
	/**
	 * 校验一个密钥
	 * １、如果密钥为空ＯＲ不正确，返回false；
	 * ２、如果密钥正确，返回true。并删除数据库相关记录
	 * @param key
	 * @return
	 * @throws SQLException 
	 */
	public static boolean validateEncryption(String key){
		boolean pass = false;
		
		if(key==null || key.equals(""))
			return pass;
        
		try 
		{
			if(keyMap.get(key)!=null)
			{
				keyMap.remove(key);
				pass = true;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
		return pass;
	}
}

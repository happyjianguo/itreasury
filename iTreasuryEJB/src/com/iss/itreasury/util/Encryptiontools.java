package com.iss.itreasury.util;

import java.sql.SQLException;
import java.util.HashMap;

public class Encryptiontools {
	
	private static HashMap keyMap = new HashMap();
	/**
	 * �Զ�����һ����Կ������¼�����ݿ�
	 * @return����Կ java.lang.String
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
	 * У��һ����Կ
	 * ���������ԿΪ�գϣҲ���ȷ������false��
	 * ���������Կ��ȷ������true����ɾ�����ݿ���ؼ�¼
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

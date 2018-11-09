package com.iss.itreasury.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class DownLoadFileNameEncryptionAndDecrypt {

	
	private static  Map map = new HashMap();
	private static  Map map1 = new HashMap();
	
	//加密String明文输入,String密文输出
	public String getEncString(String strMing)
	{
		String strMi = Md5.md5(strMing);
		map.put(strMi, strMing);
		map1.put(strMi, System.currentTimeMillis()+"");
		return strMi;
	}
	//解密以String密文输入,String明文输出
	public String getDesString(String strMi)
	{
		String strMing = "";
		strMing = (String)map.get(strMi);
		//map.remove(strMi);
		//map1.remove(strMi);
		System.out.print(strMing);
		return strMing;
	}
	protected void finalize() throws Throwable
	{
		Set temp = map1.keySet();
		Iterator it = temp.iterator();
		while(it.hasNext())
		{
			String t = it.next().toString();
			if(System.currentTimeMillis()-Long.parseLong((String)map1.get(t))>30*60*1000)
			{
				map1.remove(t);
				map.remove(t);
			}
		}
	}
	  public static void main(String[] args) {
		  
		  DownLoadFileNameEncryptionAndDecrypt en = new DownLoadFileNameEncryptionAndDecrypt();
		  
			System.out.println("=========================开始==============!");
			
			try {
				System.out.println(en.getEncString("179"));
				System.out.println(en.getDesString(en.getEncString("179")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("=========================结束==============!");

	}
	  
}

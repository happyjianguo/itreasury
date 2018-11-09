package com.iss.itreasury.util;

import java.util.HashMap;

import com.iss.itreasury.codingrule.bizlogic.CreateCodeBiz;
import java.sql.Connection;
public class CreateCodeManager {
	/*
	 * 根据传入的参数获取所需编码
	 * 描述:1.根据参数获取编码规则  2.根据所得的编码规则分段获取编码段，然后则合成完整编码
	 * @param       HashMap      	参数集合（key值为codingrule_paralist.xml对应，value为调用着设置）
	 * @return      String        	获取的完整编码
	 */
	public static String createCode(HashMap paraMap)throws IException,Exception
	{
		String createCode = "";
		try
		{
			CreateCodeBiz biz = new CreateCodeBiz((Connection)paraMap.get("connection"));
			createCode = biz.creatCode(paraMap);
		}catch( IException e)
		{
			e.printStackTrace();
			throw  e;
		}
		return createCode;
	}
}

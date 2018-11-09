package com.iss.itreasury.encrypt;

import com.iss.itreasury.encrypt.impl.Encrypt;
import com.iss.itreasury.encrypt.util.LargeFieldEncrypt;
import com.iss.itreasury.encrypt.util.Md5Encrypt;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.IException;

/**
 * 获取加密类型工厂类
 * @author xiang
 *
 */
public class EncryptManager {
	
	public static Encrypt getBeanFactory() throws Exception{
		
		Encrypt encrypt = null;
		try
		{
			long encryptModle = Long.parseLong(Config.getProperty(ConfigConstant.SETT_ENCRYPT_TYPE));
			switch ((int) encryptModle) 
			{
				case 1:
					encrypt = new LargeFieldEncrypt();
					break;
				case 2:
					encrypt = new Md5Encrypt();
					break;
		
				default:
					break;
			}
		}catch(Exception e){
			throw new IException("获取结算系统加密方式出错，" + e.getMessage());
		}
		
		return encrypt;
		
	}
	
	public static Encrypt getOBBeanFactory() throws Exception{
		
		Encrypt encrypt = null;
		try
		{
			long encryptModle = Long.parseLong(Config.getProperty(ConfigConstant.EBANK_ENCRYPT_TYPE));
			switch ((int) encryptModle) 
			{
			case 1:
				encrypt = new LargeFieldEncrypt();
				break;
			case 2:
				encrypt = new Md5Encrypt();
				break;
				
			default:
				break;
			}
		}catch(Exception e){
			throw new IException("获取网银系统加密方式出错，" + e.getMessage());
		}
		
		return encrypt;
		
	}
	
}

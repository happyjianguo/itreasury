/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.IException;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionConfig implements java.io.Serializable{
	
	String configFileName = "configpw";
	String defaultPwd = "isoftstone";
	boolean bIsLogin = false;
	
	/**
	 * 方法说明：登录时校验用户名/密码，
	 * 用户名现固定为“admin”，初始密码为“isoftstone”
	 * 校验密码时，先寻找密码文件，如未找到便创建密码文件，
	 * 如找到便读出其中的密码看是否匹配。
	 * 如不匹配抛出异常。
	 * @param : String login 用户名
	 * @param : String password 密码
	 * @return : 无
	 * @throws Exception
	 */
	public boolean isLogin()
	{
		return this.bIsLogin;
	}
	public void login(String login, String password) throws Exception
	{
		Log.print("用户="+login+"|密码="+password+"登录");
		
		try
		{
			if (!login.equals("admin"))
			{
				throw new IException("Gen_E007");
			}	
			if (!matchPassword(password))
			{
				throw new IException("Gen_E007");
			}
			
			bIsLogin = true;
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 方法说明：修改密码，
	 * 修改密码时，先寻找密码文件
	 * 读出其中的密码看是否和旧密码匹配。
	 * 如不匹配抛出异常。
	 * 如匹配将密码文件修改为新密码
	 * @param : String oldPassword 旧密码
	 * @param : String newPassword 新密码
	 * @return : 无
	 * @throws Exception
	 */
	public void changePassword(String oldPassword, String newPassword) throws Exception
	{
		try
		{
			if (!matchPassword(oldPassword))
			{
				throw new IException("Gen_E010");
			}
			
			FileOutputStream output = new FileOutputStream(configFileName);
			
			String newPwd = Helper.getDigest(newPassword.getBytes());
			output.write(newPwd.getBytes());
			
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}
	
	/**
	 * 方法说明：修改密码，
	 * 修改密码时，先寻找密码文件
	 * 读出其中的密码看是否和旧密码匹配。
	 * 如不匹配抛出异常。
	 * 如匹配将密码文件修改为新密码
	 * @param : String oldPassword 旧密码
	 * @param : String newPassword 新密码
	 * @return : 无
	 * @throws Exception
	 */
	public boolean matchPassword(String password) throws Exception
	{	
		byte[] pwd = new byte[1024];
		String oldPwd = "";
		String newPwd = "";
	
		boolean bIsMatch=false;
		
		try{
			File configFile = null;		
			configFile = new File(configFileName);
			
			Log.print("configFile:"+configFile.getAbsolutePath());
		
			if (configFile.exists())
			{
				FileInputStream input = new FileInputStream(configFile);
	
				int len = input.read(pwd);
				
				byte[] temppwd = new byte[len];
				
				for (int i=0;i<len;i++)
				{
					temppwd[i]=pwd[i];
				}
								
				oldPwd = new String(temppwd);
				newPwd = Helper.getDigest(password.getBytes());
				
				if (oldPwd.equals(newPwd))
				{
					bIsMatch = true;
				}
			}
			else 
			{
				if (password.equals(defaultPwd))
				{
					bIsMatch = true;
				}
				
				configFile.createNewFile();
				FileOutputStream output = new FileOutputStream(configFile);
				
				oldPwd = Helper.getDigest(defaultPwd.getBytes());
				output.write(oldPwd.getBytes());
			}
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
		return bIsMatch;
	}
	
	public static void main(String[] args)
	{
		SessionConfig ss=new SessionConfig();
		try{
			System.out.println("********begin*************");
			System.out.println("bIsMatch="+ss.matchPassword("isoftstone1"));
			//ss.changePassword("isoftstone","isoftstone1");
			System.out.println("********end*************");
		}
		catch(Exception e)
		{
			Log.print(e.toString());
		}	
	}

}

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
	 * ����˵������¼ʱУ���û���/���룬
	 * �û����̶ֹ�Ϊ��admin������ʼ����Ϊ��isoftstone��
	 * У������ʱ����Ѱ�������ļ�����δ�ҵ��㴴�������ļ���
	 * ���ҵ���������е����뿴�Ƿ�ƥ�䡣
	 * �粻ƥ���׳��쳣��
	 * @param : String login �û���
	 * @param : String password ����
	 * @return : ��
	 * @throws Exception
	 */
	public boolean isLogin()
	{
		return this.bIsLogin;
	}
	public void login(String login, String password) throws Exception
	{
		Log.print("�û�="+login+"|����="+password+"��¼");
		
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
	 * ����˵�����޸����룬
	 * �޸�����ʱ����Ѱ�������ļ�
	 * �������е����뿴�Ƿ�;�����ƥ�䡣
	 * �粻ƥ���׳��쳣��
	 * ��ƥ�佫�����ļ��޸�Ϊ������
	 * @param : String oldPassword ������
	 * @param : String newPassword ������
	 * @return : ��
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
	 * ����˵�����޸����룬
	 * �޸�����ʱ����Ѱ�������ļ�
	 * �������е����뿴�Ƿ�;�����ƥ�䡣
	 * �粻ƥ���׳��쳣��
	 * ��ƥ�佫�����ļ��޸�Ϊ������
	 * @param : String oldPassword ������
	 * @param : String newPassword ������
	 * @return : ��
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

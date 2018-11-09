package com.iss.itreasury.loan.util;

import java.util.Properties;
import java.io.FileInputStream;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LOANEnv
{
	//����˾��Ϣ
	public static String CLIENT_NAME = "�Ϻ��ֶ���չ���Ų����������ι�˾";
	public static String CLIENT_LEGALPERSON = "���˼�";
	public static String CLIENT_ADDRESS = "���������������ڴ����16�Ż�ʵ����";
	public static String CLIENT_TEL = "(8610)66217799-6601~6607��66214306";
	public static String CLIENT_FAX = "(8610)66214305";
	public static String CLIENT_ZIP = "100032"; 
	

	static boolean m_bIsInit = false; //�Ƿ�ϵͳ��Ϣ��ʼ��
	public static Properties m_prop = null;
	
	static {
		if (!m_bIsInit)
		{
			Properties p = new Properties();
			String strFile = "loan.properties";
			try
			{
				m_prop = new Properties();
				FileInputStream is = new FileInputStream(strFile);
				p.load(is);
				is.close();
			}
			catch (Exception e)
			{
				System.out.println("���ܴ� loan.properties �ļ���" + strFile);
			}
			try
			{
				//
				m_prop.setProperty("client.name", p.getProperty("client.name"));
				CLIENT_NAME = m_prop.getProperty("client.name");
				CLIENT_NAME = new String(CLIENT_NAME.getBytes("ISO8859_1"), "GBK");
				//
				m_prop.setProperty("client.legalperson", p.getProperty("client.legalperson"));
				CLIENT_LEGALPERSON = m_prop.getProperty("client.legalperson");
				CLIENT_LEGALPERSON = new String(CLIENT_LEGALPERSON.getBytes("ISO8859_1"), "GBK");
				
				//
				m_prop.setProperty("client.address", p.getProperty("client.address"));
				CLIENT_ADDRESS = m_prop.getProperty("client.address");
				CLIENT_ADDRESS = new String(CLIENT_ADDRESS.getBytes("ISO8859_1"), "GBK");
				
				//
				m_prop.setProperty("client.tel", p.getProperty("client.tel"));
				CLIENT_TEL = m_prop.getProperty("client.tel");
				CLIENT_TEL = new String(CLIENT_TEL.getBytes("ISO8859_1"), "GBK");
				//
				m_prop.setProperty("client.fax", p.getProperty("client.fax"));
				CLIENT_FAX = m_prop.getProperty("client.fax");
				//
				m_prop.setProperty("client.zip", p.getProperty("client.zip"));
				CLIENT_ZIP = m_prop.getProperty("client.zip");
				//

				m_bIsInit = true;
			}
			catch (Exception e)
			{
				System.out.println("��ȡ properties item ����.");
			}
		}
	}
	
	public static void main(String[] args)
	{
	}
}

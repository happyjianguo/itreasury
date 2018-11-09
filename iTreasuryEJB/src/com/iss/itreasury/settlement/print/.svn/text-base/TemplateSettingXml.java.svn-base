/*
 * Created on 2005-11-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.iss.itreasury.settlement.print.dataentity.TemplateSettingXmlInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;


/**
 * @author rxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TemplateSettingXml
{
	public static void main(String[] args)
	{
		TemplateSettingXml xml = new TemplateSettingXml();
		try
		{
			System.out.println("--------------------------------:strat:-------------------------");
			Collection coll = xml.getTemplateSetting();
			System.out.println("--------------------------------:end:-------------------------");
			Iterator it = null;
			if (coll != null)
			{
				it = coll.iterator();
				while(it.hasNext())
				{
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)it.next();
					System.out.println(info.getTemplateType());
					System.out.println(info.getTemplateDetailName());
					System.out.println(info.getTemplateDetailCode());
					System.out.println(info.getTemplateDetailVariable());
					System.out.println("==========================================================");
				}
			}
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Collection TemplateSettingDetails = null;

	private String fileseparator = "/";

	private String configFilePath = ".config";

	// 对应于属性文件的文件对象变量
	private static File m_file = null;

	// 属性文件的最后修改日期
	private static long m_lastModifiedTime = 0;
	
	
	public static Collection getTemplateSettingDetails(){
		return TemplateSettingDetails;
	}
	
	//得到XML中定义的所有信息集合
	public Collection getTemplateSetting() throws IException, Exception {
		System.out.println("--------------start getTemplateSetting---------------");
		String xmlFilePath = getXmlPath();
		
		m_file = new File(xmlFilePath);
		long newTime = m_file.lastModified();
		System.out.println("===========newTime==============newTime===" + newTime);
		System.out.println("===========m_lastModifiedTime===" + m_lastModifiedTime);
		if (newTime == 0)
			throw new IException("Sec_E172");

		//如果文件被修改了，则重新读入
		if (m_lastModifiedTime == 0 || newTime > m_lastModifiedTime) {
			if(m_lastModifiedTime == 0)
				System.out.println("===========XML的第一次读入!");
			if(newTime > m_lastModifiedTime)
				System.out.println("===========XML的数据被修改了,重新读入!");
			
			m_lastModifiedTime = newTime;
			try {
				Document doc = readXMLFile(xmlFilePath);
				parseDoc(doc);
			} catch (IException iex) {
				throw (iex);
			} catch (Exception e) {
				throw (e);
			}
		}else{
			//XML的数据集合已经读出,放在静态属性里。现在可以直接用！
			System.out.println("=============XML的数据没有被修改");
			if(getTemplateSettingDetails()!=null){
				System.out.println("=============XML的数据集合已经读出,放在静态属性里。现在可以直接用！");
			}else{
				System.out.println("=============XML的数据集合没有读出,在静态属性为空。现在重新读入！");
				try {
					Document doc = readXMLFile(xmlFilePath);
					parseDoc(doc);
				} catch (IException iex) {
					throw (iex);
				} catch (Exception e) {
					throw (e);
				}
			}
		}

		System.out.println("=========================getInfo===End");
		return TemplateSettingDetails;
	}
	/**
	 * 获取PrintTemplateSetting.xml文件路径
	 * 
	 * @author rxie
	 * @return String
	 */
	private String getXmlPath() throws IException {
		if (Env.getCPF_OS().equalsIgnoreCase("windows")) {
			fileseparator = "\\";
		} else {
			fileseparator = "/";
		}
		configFilePath = ".config" + fileseparator;
		String strName = null;
		
		//strName = Config.getProperty("sett_PrintTemplate_Setting");
		strName = Config.getProperty("sett_PrintTemplate_Setting");
		if (strName == null || "".equals(strName.trim()))
			throw new IException("Sec_E172");
		else
			return configFilePath + strName;
	}
	
	/**
	 * 转换XML文件
	 * @param doc
	 * @throws IException
	 */
	private void parseDoc(Document doc) throws IException {
		TemplateSettingDetails = new ArrayList();
		doc.normalize();//去掉树中的不必要的Text Node对象
		
		//取PrintTemplate元素列表
		NodeList depositPrintTemplateType = doc.getElementsByTagName("DepositPrintTemplateType");
		for (int i = 0; i < depositPrintTemplateType.getLength(); i++)
		{
			
			// 依次取每个"depositPrintTemplateType"元素
			Element templateType = (Element) depositPrintTemplateType.item(i);
			// 创建一个templateSettingXmlInfo 用于保存xml信息
			// 取TemplateTypeID
			String strTemplateTypeID = templateType.getElementsByTagName("TemplateTypeID").item(0).getFirstChild().getNodeValue();
			System.out.println("-------------------------------strTemplateTypeID"+strTemplateTypeID.toString());
			
			//取得模板明细设置
			NodeList templateDetails = templateType.getElementsByTagName("TemplateDetails");
			for (int nTemplateDetails = 0; nTemplateDetails < templateDetails.getLength(); nTemplateDetails++)
			{
				TemplateSettingXmlInfo templateSettingXmlInfo = new TemplateSettingXmlInfo();
				Element templateDetail = (Element) templateDetails.item(nTemplateDetails);
				String strName = templateDetail.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
				//System.out.println("strName="+strName);
				String strCode = templateDetail.getElementsByTagName("Code").item(0).getFirstChild().getNodeValue();
				//System.out.println("strCode="+strCode);
				String strVariable = templateDetail.getElementsByTagName("Variable").item(0).getFirstChild().getNodeValue();
				//System.out.println("strVariable="+strVariable);
				
				templateSettingXmlInfo.setTemplateDetailName(strName);
				templateSettingXmlInfo.setTemplateDetailCode(strCode);
				templateSettingXmlInfo.setTemplateDetailVariable(strVariable);

				//将上一个循环取得的TemplateTypeID置入
				templateSettingXmlInfo.setTemplateType(Integer.parseInt(strTemplateTypeID));
				
				TemplateSettingDetails.add(templateSettingXmlInfo);
				
				//System.out.println("#############################################################");
			}
			if(TemplateSettingDetails!=null)
				System.out.println("===得到的数据集合的总数为:"+TemplateSettingDetails.size());
		}
		if (TemplateSettingDetails.size() == 0)
			throw new IException("Sec_E173");
	}
	
	/**
	 * 读XML文件
	 * @param inFile
	 * @return Document
	 * @throws Exception
	 */
	private Document readXMLFile(String inFile) throws Exception {
		// 为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			throw pce;
		}
		Document doc = null;
		try {
			doc = db.parse(inFile);
		} catch (DOMException dom) {
			throw dom;
		} catch (IOException ioe) {
			throw new IException("Sec_E172");
		}
		System.out.println("=============================readXMLFile" + doc.toString());
		return doc;
	}

}

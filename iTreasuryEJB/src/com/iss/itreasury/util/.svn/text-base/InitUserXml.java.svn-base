/* Copyright ? 2005 Isoftstone All Rights Reserved*/

package com.iss.itreasury.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.iss.itreasury.dataentity.InitUserInfo;

/**
 * @author xgzhang
 * @version 1.0
 */
public class InitUserXml {

	public static Collection m_InitUserInfo;

	private String fileseparator = "/";

	private String configFilePath = ".config";

	// 对应于属性文件的文件对象变量
	private static File m_file = null;

	// 属性文件的最后修改日期
	private static long m_lastModifiedTime = 0;

	public InitUserXml() {
		super();
	}

	/**
	 * @type Feature
	 * @author xgzhang
	 * @priority medium
	 */
	public Collection getIniUserInfo() throws IException, Exception {
		Log.print("=========================getIniUserInfo===begain");
		String filePath = "";
		filePath = getXmlPath();
		m_file = new File(filePath);
		long newTime = m_file.lastModified();
		Log.print("===========newTime==============newTime===" + newTime);
		Log.print("===========m_lastModifiedTime===" + m_lastModifiedTime);
		if (newTime == 0)
			throw new IException("Sec_E172");

		//if (m_lastModifiedTime == 0 || newTime > m_lastModifiedTime) {
			m_lastModifiedTime = newTime;
			try {
				Document doc = readXMLFile(filePath);
				parseDoc(doc);
			} catch (IException iex) {
				throw (iex);
			} catch (Exception e) {
				throw (e);
			}
		//}

		Log.print("=========================getIniUserInfo===End");
		return m_InitUserInfo;
	}

	/**
	 * 获取InitEbankUser.xml文件路径
	 * 
	 * @author xgzhang
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
		strName = Config.getProperty(Config.EBANK_INITUSER_XMLNAME);
		if (strName == null || "".equals(strName.trim()))
			throw new IException("Sec_E172");
		else
			return configFilePath + strName;
	}

	/**
	 * 解析Document到InitUserInfo
	 * 
	 * @author xgzhang
	 * @param doc
	 */
	private void parseDoc(Document doc) throws IException {
		m_InitUserInfo = new ArrayList();
		// 下面是解析XML的全过程 比较简单，先取根元素"UserGroups"
		Element root = doc.getDocumentElement();
		// 取UserGroup元素列表
		NodeList userGroups = root.getElementsByTagName("UserGroup");
		for (int i = 0; i < userGroups.getLength(); i++) {
			// 依次取每个"UserGroup"元素
			Element userGroup = (Element) userGroups.item(i);
			// 创建一个InitUserInfo Bean实例
			InitUserInfo initUserInfo = new InitUserInfo();
			// 取GroupName
			NodeList groupName = userGroup.getElementsByTagName("GroupName");
			if (groupName.getLength() == 1) {
				Element e = (Element) groupName.item(0);
				Text t = (Text) e.getFirstChild();
				if(t!=null)
				{
					String strGroupName = t.getNodeValue();
					initUserInfo.setGroupName(strGroupName);
				}
			}
			//取Userid 的值
			NodeList userID = userGroup.getElementsByTagName("UserNumber");
			if(userID.getLength()>0)
			{
				Node n = userID.item(0);
				Node childNode = n.getFirstChild();
				String strUserID = childNode.getNodeValue();
				initUserInfo.setUserNumber(Long.parseLong(strUserID));
			}
			// 取UserName
			NodeList userName = userGroup.getElementsByTagName("UserName");
			if (userName.getLength() == 1) {
				Element e = (Element) userName.item(0);
				Text t = (Text) e.getFirstChild();
				if(t!=null)
				{
					String strUserName = t.getNodeValue();
					initUserInfo.setUserName(strUserName);
				}
			}
			// 取ModuleId
			NodeList moduleId = userGroup.getElementsByTagName("ModuleId");
			if (moduleId.getLength() == 1) {
				Element e = (Element) moduleId.item(0);
				Text t = (Text) e.getFirstChild();
				if(t!=null)
				{
					String strModuleId = t.getNodeValue();
					initUserInfo.setModuleId(Integer.parseInt(strModuleId));
				}
			}
			// 取Privileges
			NodeList privileges = userGroup.getElementsByTagName("Privileges");
			if (privileges.getLength() == 1) {
				ArrayList alPrivileges = new ArrayList();
				Element e = (Element) privileges.item(0);
				NodeList privilege = e.getElementsByTagName("Privilege");
				for (int j = 0; j < privilege.getLength(); j++) {
					Element ej = (Element) privilege.item(j);
					Text t = (Text) ej.getFirstChild();
					if(t!=null)
					{
						String strPrivilege = t.getNodeValue();
						alPrivileges.add(strPrivilege);
					}
				}
				initUserInfo.setPrivileges(alPrivileges);
			}
			// 取Status
			NodeList status = userGroup.getElementsByTagName("Status");
			if (status.getLength() == 1) {
				Element e = (Element) status.item(0);
				Text t = (Text) e.getFirstChild();
				if(t!=null)
				{
					String strStatus = t.getNodeValue().trim();
					initUserInfo.setStatus(strStatus.equals("1"));
				}

			}
			m_InitUserInfo.add(initUserInfo);
		}
		if (m_InitUserInfo.size() == 0)
			throw new IException("Sec_E173");
	}

	/**
	 * 读XML文件
	 * 
	 * @author xgzhang
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
		Log.print("=============================readXMLFile" + doc.toString());
		return doc;
	}
}
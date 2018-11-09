package com.iss.itreasury.custommenu.bizlogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo;
import com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo;
import com.iss.itreasury.custommenu.dao.CustomMenudao;
import com.iss.itreasury.custommenu.dataentity.CustomMenu_PrivilegeInfo;
import com.iss.itreasury.custommenu.dataentity.User_customprivilegeInfo;
import com.iss.itreasury.custommenu.dataentity.User_customprivilegedetailInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.mywork.dataentity.SettInutWorkInfo;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;

public class CustomMenubiz {

	private Collection m_userPrivilege = new Vector();

	public String m_strMenu = ""; // Tree菜单

	/**
	 * @param userID
	 * @return
	 */
	public String getXMLString(long userID) {

		CustomMenudao custommenudao = new CustomMenudao();
		String XMLString = "";
		try {
			Collection availableModules = custommenudao.findModulesByUser(userID);
			
			if (availableModules != null && availableModules.size() > 0) {
				for (Iterator it = availableModules.iterator(); it.hasNext();) {
					long moduleId = Long.valueOf(it.next().toString()).longValue();
					Collection modulePrivliege = custommenudao.findPrivilegesByMain(moduleId,userID);
					
					if (modulePrivliege != null && modulePrivliege.size() > 0) {
						
						m_userPrivilege.addAll(modulePrivliege);
						// 各模块权限
						Collection userPrivilege = custommenudao.getPrivilegeOfUser(userID, moduleId);
						if (userPrivilege != null && userPrivilege.size() > 0) {
							m_userPrivilege.addAll(userPrivilege);
						}
					}

					//
				}
				prepareMenu();
				parseMenuToHTML();
				

			}
			XMLString = m_strMenu.replaceAll("\"", "'");
			
			//System.out.println(XMLString);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return XMLString;
	}

	/**
	 * 
	 */
	private void prepareMenu() {
		try {
			String strPrivNo = "";
			String strPrivName = "";
			String strPrivLevel = "";
			String strPrivId = "";
			String strModuleId = "";
			String strByName="";
			String  strUid = "";
			Vector vPi = null;
			CustomMenu_PrivilegeInfo pi = new CustomMenu_PrivilegeInfo();

			long lLevelTmp = 0;
			String strNoTmp = "";
			String strNameTmp = "";
			String strIdTmp = "";
			String strModuleTmp = "";
			String strByNameTmp = "";
			long uidtmp = -1;
			vPi = (Vector) m_userPrivilege;
			if (vPi == null) {
				m_strMenu = "";
			} else {
				Enumeration ePi = vPi.elements();
				while (ePi.hasMoreElements()) {
					pi = (CustomMenu_PrivilegeInfo) ePi.nextElement();
					lLevelTmp = pi.getPlevel();
					strNoTmp = pi.getPrivilegeNo();
					strNameTmp = pi.getName();
					strIdTmp = String.valueOf(pi.getId());
					strModuleTmp = String.valueOf(pi.getModuleID());
					strByNameTmp = pi.getByname();
					uidtmp =pi.getUId();
					strPrivNo = strPrivNo + strNoTmp + ";;";
					strPrivName = strPrivName + strNameTmp + ";;";
					strPrivLevel = strPrivLevel + String.valueOf(lLevelTmp) + ";;";
					strPrivId = strPrivId + strIdTmp + ";;";
					strModuleId = strModuleId + strModuleTmp + ";;";
					strByName = strByName + strByNameTmp + ";;";
					strUid = strUid + uidtmp + ";;";
				}
				strPrivName = strPrivName.substring(0, strPrivName.length() - 2);
				strPrivNo = strPrivNo.substring(0, strPrivNo.length() - 2);
				strPrivLevel = strPrivLevel.substring(0,strPrivLevel.length() - 2);
				strPrivId = strPrivId.substring(0, strPrivId.length() - 2);
				strModuleId = strModuleId.substring(0, strModuleId.length() - 2);
				strByName = strByName.substring(0, strByName.length() - 2);
				strUid = strUid.substring(0, strUid.length() - 2);
				m_strMenu = strModuleId + "::" + strPrivId + "::" + strPrivName
						+ "::" + strPrivNo + "::" + strPrivLevel+ "::" + strByName + "::" + strUid;
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}

	/**
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private void parseMenuToHTML() throws IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document htmlDoc = builder.newDocument();
		Element mainTREE = htmlDoc.createElement("tree");
		mainTREE.setAttribute("id","0");
	
		// m_strMenu 的解析
		String[] arrDepart = null; // 取得用户权限;
		
		String[] arrName = null;
		String[] arrNo = null;
		String[] arrLevel = null;
		String[] arrID = null;
		String[] arrModule = null;
		String[] arrByName = null;
		String[] arrUid = null;
		ArrayList sParent = null;
		String[] tmpArrNo = null; // 替换后的编号
		String[] newArrNo = null; // 处理完成后的编号

		if(!this.m_strMenu.equals("")){
			arrDepart = this.m_strMenu.split("::");
			if(arrDepart.length == 7 && arrDepart != null){
				arrModule = arrDepart[0].split(";;");
				arrID = arrDepart[1].split(";;");
				arrName = arrDepart[2].split(";;");
				arrNo = arrDepart[3].split(";;");
				arrLevel = arrDepart[4].split(";;");
				arrByName = arrDepart[5].split(";;");
				arrUid = arrDepart[6].split(";;");
				int iLength = arrName.length;
				int i = 0;
				String tmpStr = "";
				sParent = new ArrayList();
				tmpArrNo = new String[iLength];
				newArrNo = new String[iLength];
				
				
				// 替换编号的"-"号为下划线
				for(i=0; i<iLength; i++){
					tmpArrNo[i] = arrNo[i].replaceAll("-","_");
				}
				
				for(i=0; i<iLength; i++){
				// 根据级别取出上级权限编号
					
						if("1".equals(arrLevel[i]))
						{
							sParent.add("");
							newArrNo[i] = "Mod" + arrModule[i];
						}
						else
						{
							if(tmpArrNo[i].indexOf("_") == tmpArrNo[i].lastIndexOf("_")){
								sParent.add("Mod" + arrModule[i]);
								newArrNo[i] = "Top" + tmpArrNo[i];
							}
							else{
								tmpStr = tmpArrNo[i].substring(0,tmpArrNo[i].lastIndexOf("_"));
								if (tmpStr.lastIndexOf("_")==1 || tmpStr.lastIndexOf("_")==2){
									sParent.add("Top" + tmpStr);
								}
								else{
									sParent.add("Sub" + tmpStr);
								}
								newArrNo[i] = "Sub" + tmpArrNo[i];
							}
						}
					
				}
				// m_strMenu 的解析结束
					
				for(i=0; i<iLength; i++){
					// Element elementA = null;
					Element elementITEM = null;
					Element subElementITEM = null;
					String strParent = sParent.get(i).toString();
					
					if(strParent.equals("")){				

						elementITEM = htmlDoc.createElement("item");
						elementITEM.setAttribute("id",arrID[i]);
						elementITEM.setAttribute("id1",newArrNo[i]+ "_item");
						elementITEM.setAttribute("text",arrName[i]+" &lt;input type=&apos;text&apos; name=&apos;"+ arrID[i]+"&apos; value=&apos;"+ ((!arrByName[i].equals("#"))?arrByName[i]:"自定义别名") +"&apos; maxlength=&apos;10&apos; size=&apos;20&apos; &gt;");
						if(Long.valueOf(arrUid[i]).longValue()>0)
						{
							elementITEM.setAttribute("checked","1");
						}
						elementITEM.setAttribute("im0","book.gif");
						elementITEM.setAttribute("im1","books_open.gif");
						elementITEM.setAttribute("im2","books_close.gif");
						mainTREE.appendChild(elementITEM);
	
					}
					else{
						NodeList nodeList = mainTREE.getElementsByTagName("item");
						for(int j=0; j<nodeList.getLength(); j++){
							elementITEM = (Element)nodeList.item(j);
							if(elementITEM.getNodeName().equals("item") && elementITEM.getAttribute("id1").equals(strParent + "_item")){
								
									
									subElementITEM = htmlDoc.createElement("item");
									subElementITEM.setAttribute("id", arrID[i] );
									subElementITEM.setAttribute("id1", newArrNo[i] + "_item");
									subElementITEM.setAttribute("text",arrName[i]+" &lt;input type=&apos;text&apos; name=&apos;"+ arrID[i]+"&apos; value=&apos;"+ ((!arrByName[i].equals("#"))?arrByName[i]:"自定义别名") +"&apos;  maxlength=&apos;10&apos; size=&apos;20&apos; &gt;");
									if(Long.valueOf(arrUid[i]).longValue()>0)
									{
										subElementITEM.setAttribute("checked","1");
									}
									
									subElementITEM.setAttribute("im0","book.gif");
									subElementITEM.setAttribute("im1","books_open.gif");
									subElementITEM.setAttribute("im2","books_close.gif");
									elementITEM.appendChild(subElementITEM);
//									if(!elementITEM.getAttribute("checked").equals("1") && subElementITEM.getAttribute("checked").equals("1"))
//									{
//										elementITEM.setAttribute("checked","-1");
//									}
								
						    }
					    }
					}
				}
			}
		}
		
		// 输出
		StringBuffer stringOut = new StringBuffer();
		stringOut.append("<\\?xml version='1.0' encoding='gb2312'?><tree id='0'>");
		this.parseMenuDOM(stringOut, mainTREE);
		stringOut.append("</tree>");
		System.out.println("又發現一個菜單-----------------------");
		
		this.m_strMenu = stringOut.toString();
	}
	
	/**
	 * @param stringOut
	 * @param node
	 */
	private void parseMenuDOM(StringBuffer stringOut, Node node){
		NodeList parentList = node.getChildNodes();
		for(int i=0; i<parentList.getLength(); i++){
			Node parentNode = parentList.item(i);
			stringOut.append("<");
			stringOut.append(parentNode.getNodeName());
			
			NamedNodeMap parentAttrMap = parentNode.getAttributes();
			if(parentAttrMap != null){
				for(int j=0; j<parentAttrMap.getLength(); j++){
					Node parentNodeAttr = parentAttrMap.item(j);
					stringOut.append(" ");
					stringOut.append(parentNodeAttr.getNodeName());
					stringOut.append("=\"");
					stringOut.append(parentNodeAttr.getNodeValue());
					stringOut.append("\"");
				}
			}
			stringOut.append(">");
			
			if(parentNode.getChildNodes().getLength() > 1){
				parseMenuDOM(stringOut,parentNode);
			}
			else {
				Node childNode = parentNode.getFirstChild();
					if(childNode!=null)
					{
						if(childNode.getNodeType() == Node.ELEMENT_NODE){
							parseMenuDOM(stringOut,parentNode);
						}
						else{
							stringOut.append(parentNode.getFirstChild().getNodeValue());
						}
					}
				
			}

			stringOut.append("</");
			stringOut.append(parentNode.getNodeName());
			stringOut.append(">");
		}
	}
	
	/**
	 * @param uInfo
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long  toSave(User_customprivilegeInfo uInfo)throws ITreasuryDAOException
	{
		long lReturn = -1;
		CustomMenudao custommenudao = new CustomMenudao();
		long lUpid = -1;
		try {
			if(uInfo.getId()<=0)
			{
				lUpid=custommenudao.toSaveUser_customprivilege(uInfo);
				Collection coll = uInfo.getDetail();
				if(coll!=null && coll.size()>0)
				{
					for(Iterator it=coll.iterator();it.hasNext();)
					{
						User_customprivilegedetailInfo udInfo =(User_customprivilegedetailInfo)it.next();
						udInfo.setUser_customprivilegeId(lUpid);
						custommenudao.toSaveUser_customprivilegedetail(udInfo);
					}	
				}
				
			}
			else
			{
				if(custommenudao.deleteUser_customprivilegedetail(uInfo.getId())>0)
				{
					lUpid = custommenudao.toUpdateUser_customprivilege(uInfo);
					Collection coll = uInfo.getDetail();
					if(coll!=null && coll.size()>0)
					{
						for(Iterator it=coll.iterator();it.hasNext();)
						{
							User_customprivilegedetailInfo udInfo =(User_customprivilegedetailInfo)it.next();
							udInfo.setUser_customprivilegeId(lUpid);
							custommenudao.toSaveUser_customprivilegedetail(udInfo);
						}	
					}
				}
			}
			
			lReturn=1;

		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lReturn;
	}
	
	/**
	 * @param userId
	 * @return
	 */
	public User_customprivilegeInfo getUser_customprivilege(long userId)
	{
		User_customprivilegeInfo uInfo = null;
		CustomMenudao custommenudao = new CustomMenudao();
		try {
			uInfo = custommenudao.getUser_customprivilegeInfo(userId);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uInfo;
	}
	
	public long deleteUser_customprivilege(long uId)
	{	long lReturn = -1;
		CustomMenudao custommenudao = new CustomMenudao();
		try {
			custommenudao.deleteUser_customprivilege(uId);
			custommenudao.deleteUser_customprivilegedetail(uId);
			lReturn =uId;
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lReturn;
		
	}
	
	 /**
     * 根据模块取得用户对应的所有权限，取并集
     * 
     * @param userID
     *            用户编号
     * @param moduleID
     *            系统模块编号
     * @return 用户具有的所有权限
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection getPrivilegeOfUser(long userID, long moduleID) throws ITreasuryDAOException, SQLException
    {
        Collection c = new Vector();
        CustomMenudao custommenudao = new CustomMenudao();
        try
        {
        	Collection availableModules = custommenudao.findModulesByUser(userID);
        	if (availableModules != null && availableModules.size() > 0)
        	{
        		for (Iterator it = availableModules.iterator(); it.hasNext();)
        		{
        			
					long moduleId = Long.valueOf(it.next().toString()).longValue();
					Collection modulePrivliege = custommenudao.findPrivilegesByMainView(moduleId,userID);
					if (modulePrivliege != null && modulePrivliege.size() > 0)
					{
						c.addAll(modulePrivliege);
						// 各模块权限
						Collection userPrivilege = custommenudao.getPrivilegeOfUserView(userID, moduleId);
						if (userPrivilege != null && userPrivilege.size() > 0) {
							c.addAll(userPrivilege);
						}
					}
        		}
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return c;
    }
    
    public int querySumFirstMenuForMoudle10(BillMyWorkInfo qInfo1,TransDiscountApplyQueryInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumFirstMenuForMoudle10(qInfo1,qInfo);
    }
    
    public int querySumSecendMenuForMoudle10(BillMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumSecendMenuForMoudle10(qInfo);
    }
    
    public int querySumThirdMenuForMoudle10(BillMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumThirdMenuForMoudle10(qInfo);
    }
    
    public int querySumFirstMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumFirstMenuForMoudle15(qInfo1,qInfo);
    }
    
    public int querySumSecendMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumSecendMenuForMoudle15(qInfo1,qInfo);
    }
    
    public int querySumThirdMenuForMoudle15(CraMyWorkInfo qInfo1,CreditSettingMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumThirdMenuForMoudle15(qInfo1,qInfo);
    }
    
    public int querySumFirstMenuForMoudle2(LoanMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumFirstMenuForMoudle2(qInfo);
    }

    public int querySumSecondMenuForMoudle2(LoanMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumSecondMenuForMoudle2(qInfo);
    }
    
    public int querySumThirdMenuForMoudle2(LoanMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumThirdMenuForMoudle2(qInfo);
    }
    
    public int querySumFirstMenuForMoudle23(CreRtMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumFirstMenuForMoudle23(qInfo);
    }
    
    public int querySumSecondMenuForMoudle23(CreRtMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumSecondMenuForMoudle23(qInfo);
    }
    
    public int querySumThirdMenuForMoudle23(CreRtMyWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumThirdMenuForMoudle23(qInfo);
    }
    
    public int querySumFirstMenuForMoudle16(SettInutWorkInfo qInfo)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumFirstMenuForMoudle16(qInfo);
    }
    
    public int querySumSecondMenuForMoudle16(SettInutWorkInfo qInfo,long ldesc,long lOrderField)throws ITreasuryDAOException, SQLException{
    	CustomMenudao custommenudao = new CustomMenudao();
    	return custommenudao.querySumSecondMenuForMoudle16(qInfo,ldesc,lOrderField);
    }
}

package com.iss.itreasury.creditrating.set.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iss.itreasury.creditrating.set.dao.RatingProjectDao;
import com.iss.itreasury.creditrating.set.dao.TargetSetupDao;
import com.iss.itreasury.creditrating.set.dataentity.TargetSetupInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * 
 * @author leiyang3
 * 创建于2009-03-03，信用评级指标体系代理类
 *
 */
public class TargetSetupBiz {
	
	public long saveTargetSetup(TargetSetupInfo info)
		throws IException
	{
		long id = -1;
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			TargetSetupDao tsTao = new TargetSetupDao(conn);
			if(info.getPaterId() > 0){
				tsTao.updateTargetSetupChildNum(info.getPaterId(), "+");
			}
			id = tsTao.addTargetSetup(info);
			
			conn.commit();

			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				new IException("数据库操作异常" ,e);
			}
		}
		return id;
	}
	
	public void updateTargetSetup(TargetSetupInfo info)
		throws IException
	{
		try {
			TargetSetupDao tsTao = new TargetSetupDao();
			tsTao.updateTargetSetup(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
	}
	
	public void deleteTargetSetup(TargetSetupInfo info)
		throws IException
	{
		Connection conn = null;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			TargetSetupDao tsTao = new TargetSetupDao(conn);
			if(info.getId() > 0){
				tsTao.updateTargetSetupChildNum(info.getId(), "-");
			}
			tsTao.deleteTargetSetup(info);
			
			conn.commit();

			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new IException("数据库操作异常" ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				new IException("数据库操作异常" ,e);
			}
		}
	}
	
	public TargetSetupInfo getTargetSetupInfo(long id)
		throws IException
	{
		TargetSetupInfo info = null;
		try {
			TargetSetupDao tsTao = new TargetSetupDao();
			info = (TargetSetupInfo)tsTao.findByID(id, TargetSetupInfo.class);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("查询指标体系失败" ,e);
		}
		return info;
	}
	
	public Collection findByRootCondition(TargetSetupInfo info)
		throws IException
	{
		Collection coll = null;
		try {
			TargetSetupDao tsTao = new TargetSetupDao();
			coll = tsTao.findByRootCondition(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("查询指标体系失败" ,e);
		}
		return coll;
	}
	
	public String getTargetTreeString(TargetSetupInfo info)
		throws IException
	{
		StringBuffer strTree = new StringBuffer();
		StringBuffer strTreeDescription = new StringBuffer();
		try {
			TargetSetupDao tsTao = new TargetSetupDao();
			Collection coll = tsTao.findTargetTreeCollection(info);
			
			if(coll != null) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document htmlDoc = builder.newDocument();
				Element elementRoot = htmlDoc.createElement("DIV");
				//elementRoot.setAttribute("id","treebox");
				//elementRoot.setAttribute("class","dhtmlxTree");
				//elementRoot.setAttribute("style","width:420px; height:400px; background-color:#f5f5f5;border :1px solid Silver;");
				
				Element elementUL = null;
				Element elementLI = null;
				Element elementSPAN = null;
				Element elementPater = null;
				
				Iterator it = coll.iterator();
				while (it.hasNext()) {
					TargetSetupInfo tsInfo = (TargetSetupInfo)it.next();
					if(tsInfo.getPaterId() == -1){
						elementRoot = htmlDoc.createElement("DIV");
						elementUL =  htmlDoc.createElement("UL");
						elementLI = htmlDoc.createElement("LI");
						elementLI.setAttribute("id", "li" + tsInfo.getId());
						elementSPAN = htmlDoc.createElement("SPAN");
						elementSPAN.setAttribute("id", "span" + tsInfo.getId());
						elementSPAN.setAttribute("onMouseOver", "tonmouseover('"+ tsInfo.getId() +"')");
						elementSPAN.setAttribute("onMouseOut", "tonmouseout('"+ tsInfo.getId() +"')");
						elementSPAN.setAttribute("onClick", "tonclick('"+ tsInfo.getId() +"', '"+ tsInfo.getLevelCode() +"')");
						elementSPAN.appendChild(htmlDoc.createTextNode(tsInfo.getName()));
						
						elementLI.appendChild(elementSPAN);
						elementUL.appendChild(elementLI);
						elementRoot.appendChild(elementUL);
					}
					else {
						NodeList nodeLis = elementRoot.getElementsByTagName("LI");
						for(int i=0; i<nodeLis.getLength(); i++) {
							Element nodeLI = (Element)nodeLis.item(i);
							if(nodeLI.getAttribute("id").equals("li" + tsInfo.getPaterId())){
								elementPater = (Element)nodeLis.item(i);
								
								NodeList childNodes = elementPater.getChildNodes();
								if(childNodes.getLength() <= 1) {
									elementUL = htmlDoc.createElement("UL");
									elementPater.appendChild(elementUL);
								}
								else {
									for(int j=0; j<childNodes.getLength(); j++){
										Element nodeChild = (Element)childNodes.item(j);
										if(nodeChild.getTagName().equals("UL")){
											elementUL = (Element)childNodes.item(j);
										}
									}
								}
								
							}
						}
						
						elementLI = htmlDoc.createElement("LI");
						elementLI.setAttribute("id", "li" + tsInfo.getId());
						elementSPAN = htmlDoc.createElement("SPAN");
						elementSPAN.setAttribute("id", "span" + tsInfo.getId());
						elementSPAN.setAttribute("onMouseOver", "tonmouseover('"+ tsInfo.getId() +"')");
						elementSPAN.setAttribute("onMouseOut", "tonmouseout('"+ tsInfo.getId() +"')");
						elementSPAN.setAttribute("onClick", "tonclick('"+ tsInfo.getId() +"', '"+ tsInfo.getLevelCode() +"')");
						elementSPAN.appendChild(htmlDoc.createTextNode(tsInfo.getName()));
						
						elementLI.appendChild(elementSPAN);
						elementUL.appendChild(elementLI);
					}
					
					if(tsInfo.getDescription() != null && !tsInfo.getDescription().equals("")){
						strTreeDescription.append("<SPAN id=\"spanDescription"+ tsInfo.getId() +"\" style=\"display: none;\">"+ tsInfo.getDescription() +"</SPAN>\n");
					}
				}
				
				strTree.append("<div id=\"treebox\" class=\"dhtmlxTree\" setImagePath=\"/webcrert/image/csh_yellowbooks/\" style=\"width:420px; height:400px; background-color:#f5f5f5;border :1px solid Silver;\">\n");
				this.parseNodeToString(elementRoot, strTree);
				strTree.append("</div>\n");
				
				strTree.append("<div id=\"treeboxDesciption\" style=\"left:0px; top:200px; height:90px; width:200px; position: absolute; display:none; border-top: 1px dashed blue; border-left: 1px dashed blue; border-right: 1px dashed blue; border-bottom: 1px dashed blue; background-color:#FFFFFF\">\n");
				strTree.append(strTreeDescription.toString());
				strTree.append("</div>\n");
				
				System.out.println("TargetSetupBiz.getTargetTreeString(): \n" + strTree.toString());
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("查询指标体系失败" ,e);
		}
		return strTree.toString();
	}
	
	private void parseNodeToString(Node node, StringBuffer stringOut){
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
				parseNodeToString(parentNode, stringOut);
			}
			else {
				Node childNode = parentNode.getFirstChild();
				if(childNode.getNodeType() == Node.ELEMENT_NODE){
					parseNodeToString(parentNode, stringOut);
				}
				else{
					stringOut.append(parentNode.getFirstChild().getNodeValue());
				}
			}

			stringOut.append("</");
			stringOut.append(parentNode.getNodeName());
			stringOut.append(">");
		}
	}
	
	public Collection findTargetTreeCollection(TargetSetupInfo info)
		throws IException
	{
		Collection coll = null;
		try {
			TargetSetupDao tsTao = new TargetSetupDao();
			coll = tsTao.findTargetTreeCollection(info);
		}
		catch(Exception e){
			e.printStackTrace();
			new IException("查询指标体系失败" ,e);
		}
		return coll;
	}
	public long validateProject(long rootID) throws IException
	{
		TargetSetupDao tsTao = new TargetSetupDao();
		try {
			return tsTao.validateTargetSetup(rootID);
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("校验出错",e);
		}
	}
}

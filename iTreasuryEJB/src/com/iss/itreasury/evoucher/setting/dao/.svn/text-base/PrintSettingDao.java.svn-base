package com.iss.itreasury.evoucher.setting.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.configtool.configmanage.dataentity.ConfigItemInfo;
import com.iss.itreasury.configtool.configmanage.dataentity.ConfigTableInfo;
import com.iss.itreasury.configtool.util.Helper;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Constant;

public class PrintSettingDao
{
    /*public static void main (String args[]) throws Exception
    {
    	
    	String template = "";
    	PrintSettingDao ss = new PrintSettingDao();
    	template = ss.getTemplateContent1("d:/upload/sendout_sample003.xml");
    	ss.getTemplateContent2(template);
    	
    }*/
	private static HashMap resultTable = new HashMap();
	private static HashMap resultField = new HashMap();
	
    private ArrayList resultSet = new ArrayList();
    
    public static int servletPort = 0;
    public static String servletName = "";
    public static String servletContextPath = "";
    
    public String clientID;
    public String isNetBank;
    public String isSignature = "0";
    
    //保存表集合信息
    private ArrayList resultTableSet = new ArrayList();
    
    public ArrayList getArrayList()
    {
    	return resultSet;
    }
    
    public ArrayList getTableArrayList()
    {
    	return resultTableSet;
    }
	
    public String getTemplateContent(String strURL, String XMLName, HashMap hashmap) throws Exception
    {
    	String template = "";
    	String returnTemplate = "";
    	try 
    	{
    		//strURL="D:/jboss-4.0.5.GABc/server/default/deploy/iTreasury.ear/itreasuryWEB.war/iTreasury-evoucher/fceform/prnfile/";
        	PrintSettingDao printSettingdao = new PrintSettingDao();
        	printSettingdao.setIsSignature(this.isSignature);
        	template = printSettingdao.getTemplateContent1(strURL+XMLName);
        	returnTemplate = printSettingdao.getTemplateContent2(template, hashmap, strURL);
    	} 
    	catch (Exception e)
		{
    		
    		throw new IException("Loan_E106");
		}
    	
    	return returnTemplate;
    }
    
    //读取文件中的内容
    public String getTemplateContent1 (String strURL) throws Exception
    {
    	String strContent = "";
    	StringBuffer sbTmp = new StringBuffer();
    	BufferedReader br = null;
    	
    	try {
    		if(strURL!=null && strURL.length()>0) 
    		{
    			br = new BufferedReader( new InputStreamReader (new FileInputStream(strURL ), "gb2312") );
    			int i = 0;
    			
    			while ((i = br.read ( )) != -1)
 				{
 				   sbTmp.append ( (char) i ) ;
 				}
    			strContent = sbTmp.toString();
 				br.close();
    		} 
    		else 
    		{
    			strContent = "";
    		}
    	} 
    	catch (Exception e)
		{
    		//log.error(e.getMessage());
    		throw new IException("Loan_E106");
		}
    	
    	return strContent;
    }
    
    //处理文件中的内容,返回替换后产生的字符串
    public String getTemplateContent2 (String str, HashMap hashmap, String strURL) throws Exception
    {
    	String strContent = "";
    	strContent = str;
    	String strTempContent = "";
    	strTempContent = str;
    	String temp = "";
    	String chineseTableName = "";
    	String chineseFieldName = "";
    	
    	try 
    	{
			int startIndex = 0;
			int endIndex = 0;
    		if(strTempContent != null && strTempContent.length() > 0) 
    		{
    			while (startIndex >= 0)
    			{
    				startIndex = strTempContent.indexOf("[&lt;");
    				endIndex = strTempContent.indexOf("&gt;]");
	    			
    				//暂时不支持模版格式化功能
    				//startIndex = strTempContent.indexOf("&lt;");
    				//endIndex = strTempContent.indexOf("&gt;");
    				
	    			if( startIndex < 0 )
	    			{
	    				break;
	    			}
	    			
    				temp = strTempContent.substring(startIndex+5, endIndex);
    				
	    			//暂时不支持模版格式化功能
    				//temp = strTempContent.substring(startIndex+4, endIndex);
    				
    				//需要替换的字符串
    				String replaceTemplate = "";
    				
    				replaceTemplate = "\\[&lt;" + temp + "&gt;\\]";
    				
    				//暂时不支持模版格式化功能
    				//replaceTemplate = "&lt;" + temp + "&gt;";
    				
    				//System.out.println("需要替换的字符串===="+replaceTemplate);
    				
    				strTempContent = strTempContent.substring(endIndex+5, strTempContent.length());
	    			
    				//暂时不支持模版格式化功能
    				//strTempContent = strTempContent.substring(endIndex+4, strTempContent.length());
    				
	    			//System.out.println("3333====" + temp);
	    			
	    			//拆分出[<****.****>]中"."的前后部分,"."前表中文名称,"."后字段名称
	    			int dotIndex = 0;
	    			dotIndex = temp.indexOf(".");
	    			
	    			if( dotIndex < 0 )
	    			{
	    				continue;
	    			}
	    			
	    			chineseTableName = temp.substring(0, dotIndex);
	    			chineseFieldName = temp.substring(dotIndex+1, temp.length());
	    			//System.out.println("4444====" + chineseTableName);
	    			//System.out.println("5555====" + chineseFieldName);
	    			
	    			//PrintBillSettingDao PrintBillSettingdao = new PrintBillSettingDao();
	    			
	    			//通过XML解析返回表对应XML文件名
	    			String subXMLName = "";
	    			subXMLName = this.loadTableConfig(chineseTableName, VOUConstant.XMLValueType.RETURNVALUE, strURL);
	    			//传值进行XML解析,获得对应表和字段,查询数据返回
	    			String strValue = "";
	    			
	    			//通过XML解析返回表对应XML表名
	    			//String tableName = "";
	    			//tableName = PrintBillSettingdao.loadTableConfig(chineseTableName, VOUConstant.XMLValueType.RETURNTABLENAME);
	    			
	    			strValue = this.loadSubConfig(subXMLName, chineseFieldName, hashmap, strURL);
	    			//System.out.println("通过查询返回的数据为:"+strValue);
	    			
	    			//根据获得的数据替换[<****.****>]全部
	    			if(strValue != null && strValue.length()>0)
	    			{
	    				strContent = strContent.replaceFirst(replaceTemplate, strValue);  //jzw
	    			}
	    			else
	    			{
	    				strContent = strContent.replaceAll(replaceTemplate, " ");
	    			}
    			}
    		}
    		else
    		{
    			strContent = "";
    		}
    	}
    	catch (Exception e)
		{
    		throw new IException("Loan_E106");
		}
    	
		int firstPAGE = strContent.indexOf("</page>");
		String tempString = strContent.substring(0,firstPAGE);
		String tempEndString = strContent.substring(firstPAGE);
		//获取签章服务url add by xiangzhou 2013-03-06
		String GLOBAL_SIGNATURE_SERVER_URL = Config.getProperty(ConfigConstant.GLOBAL_SIGNATURE_SERVER_URL);
		if(GLOBAL_SIGNATURE_SERVER_URL.equals("")){
			GLOBAL_SIGNATURE_SERVER_URL = "http://"+servletName+":"+servletPort;
		}
		strContent = tempString+"<issignature>"+this.isSignature+"</issignature><signatureName>"+Env.getClientName()+"</signatureName>" +
		        "<signatureData>"+(hashmap.containsKey("SignatureDate")?hashmap.get("SignatureDate").toString():"")+"</signatureData>" +
//				"<signatureUrl>http://"+servletName+":"+servletPort+servletContextPath+"/SignatureServlet</signatureUrl>"+
				"<signatureUrl>"+GLOBAL_SIGNATURE_SERVER_URL+servletContextPath+"/SignatureServlet</signatureUrl>"+
				"<showSignatureName>"+Env.getClientName()+"</showSignatureName>"+
				"<signPositionX>"+(hashmap.containsKey("signPositionX")?hashmap.get("signPositionX").toString():"")+"</signPositionX>"+
				"<signPositionY>"+(hashmap.containsKey("signPositionY")?hashmap.get("signPositionY").toString():"")+"</signPositionY>"+
				"<signDatePositionX>"+(hashmap.containsKey("signDatePositionX")?hashmap.get("signDatePositionX").toString():"")+"</signDatePositionX>"+
				"<signDatePositionY>"+(hashmap.containsKey("signDatePositionY")?hashmap.get("signDatePositionY").toString():"")+"</signDatePositionY>"+
				tempEndString;
    	return strContent;
    }
    /**
     * jzw 2010-05-24
     * 判断是否要加签章
     * @param isSignature  是否需要签章
     * @return 
     */
    public void setIsSignature(String isSignature){
    	this.isSignature = isSignature;				//2010-05-21 是否允许签章 0为不允许，1为允许
    }
    public String getIsSignature(){
    	return this.isSignature;
    }
	//加载解析表集合XML文件
	public String loadTableConfig(String chineseTableName, long returnType, String strURL) throws Exception
	{
		String strReturn = "";
		
		//System.out.println("tableName===="+chineseTableName);
		
		try 
		{
	    	//读XML文件
	    	ArrayList arrayListTable = new ArrayList();
	    	
	    	this.findConfigListByTableName(strURL);
	    	
	    	arrayListTable = this.getTableArrayList();
	    	
	    	if(arrayListTable.size() >0)
	    	{
	    		Iterator it = arrayListTable.iterator();
	    		
	    		for (int i=0;it.hasNext();i++)
	    		{
	    			ConfigTableInfo tableInfo = (ConfigTableInfo)it.next();
	    			
	    	    	if(returnType == VOUConstant.XMLValueType.RETURNTABLENAME)
	    	    	{
	    	    		resultTable.put(tableInfo.getName(), tableInfo.getTablename());
	    	    	}
	    	    	else if(returnType == VOUConstant.XMLValueType.RETURNDESC)
	    	    	{
	    	    		resultTable.put(tableInfo.getName(), tableInfo.getDesc());
	    	    	}
	    	    	else if(returnType == VOUConstant.XMLValueType.RETURNVALUE)
	    	    	{
	    	    		resultTable.put(tableInfo.getName(), tableInfo.getVale());
	    	    	}
	    	    	else
	    	    	{
	    	    		resultTable.put(tableInfo.getName(), tableInfo.getVale());
	    	    	}
	    		}
	    	}

	    	strReturn = (String)resultTable.get(chineseTableName);
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

		//System.out.println("我们需要查询的子XML表为:"+strReturn);
		return strReturn;
	}
	
	//加载解析表集合XML文件
	public Collection findConfigListByTableName(String strURL) throws Exception
	{
		ArrayList result = new ArrayList();
		String configTablePath = strURL + "itreasury_table.xml";
		
		//System.out.println("configFilePath=="+configTablePath);
		
		try
		{
			FileInputStream fileInput = Helper.loadConfig(configTablePath);
			
			//所有超级接口： Node
			//Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
			//将xml数据流转换为DOM模型对象
			Document node = Helper.parse(fileInput);
			
			//将Dom模型反解析成响应对象
			result = marshalTable(node);
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Loan_E106");
		}
		
		return result.size() >0 ? result : null;
	}
	
	//加载解析表集合XML文件
	public ArrayList marshalTable(Node xmlDoc) throws Exception
	{
		ArrayList arrayList = null;
		//该 Node 接口是整个文档对象模型的主要数据类型
		if (xmlDoc == null)
		{
			Log.print("xmlDoc is null");
			throw new IException("Gen_E001");
		}

		arrayList = traverseTable(xmlDoc, null);
		
		return arrayList;
	}
	
	//将响应返回的XML文件解析成对象
	private ArrayList traverseTable(Node node, ConfigTableInfo tableInfo) throws Exception
	{
		//该 Node 接口是整个文档对象模型的主要数据类型
		//表示基础对象的类型的节点
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :  //该节点为 Document
				{
					//public interface Documentextends Node
					//Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
					Document document = (Document) node;
					
					//这是一种便捷属性，该属性允许直接访问文档的文档元素的子节点
					traverseTable(document.getDocumentElement(), tableInfo);
					break;
				}

			case Node.ELEMENT_NODE :  //该节点为 Element
				{
					//此节点的第一个子节点
					Node child = node.getFirstChild();
					
					while (child != null)
					{
						//此节点的名称，取决于其类型 child.getNodeName()
						if ("itreasury_configitem".equals(child.getNodeName()))
						{ //如果是一个账户信息
							tableInfo = new ConfigTableInfo(); //new 一个新的信息类
							traverseTable(child, tableInfo); //结息账户信息
							
							resultTableSet.add(tableInfo);
							tableInfo = null;
						}
						else
						{
							traverseTable(child, tableInfo);
						}

						//直接在此节点之后的节点
						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :  //该节点为 Text 节点
				{
					/**
					 * 如果账户交易信息AccountTransactionInfo对象不为null
					 * 
					 * 则检测是否存在账户明细交易信息节点,存在则赋值
					 */

					//此节点的父节点 node.getParentNode()
					//此节点的名称，取决于其类型 getNodeName()
					if (tableInfo != null && "name".equals(node.getParentNode().getNodeName()))
					{
						//此节点的值，取决于其类型
						tableInfo.setName(node.getNodeValue());
					}
					else if (tableInfo != null && "tablename".equals(node.getParentNode().getNodeName()))
					{
						tableInfo.setTablename(node.getNodeValue());
					}
					else if (tableInfo != null && "desc".equals(node.getParentNode().getNodeName()))
					{
						tableInfo.setDesc(node.getNodeValue());
					}
					else if (tableInfo != null && "value".equals(node.getParentNode().getNodeName()))
					{
						tableInfo.setVale(node.getNodeValue());
					}

					break;
				}
		}
		
		return resultSet;
	}
	
	//获得参数得到对应数据
	public String loadSubConfig(String subXMLName, String chineseFieldName, HashMap hashmap, String strURL) throws Exception 
	{
		String returnVlaue = "";
		try 
		{
			//打印次数单独处理   added  by 张雷    2010-08-03
			if(chineseFieldName!=null&&!"".equals(chineseFieldName)&&"打印次数".equals(chineseFieldName.trim())){
				returnVlaue=(String)hashmap.get("thiscanbesigness");//其中hashmap的key值没有业务含义，只是为了保证key的唯一。
			}else if(chineseFieldName!=null&&!"".equals(chineseFieldName)&&"币种符号".equals(chineseFieldName.trim())){
				returnVlaue=(String)hashmap.get("thisiscurrencyMark");;//其中hashmap的key值没有业务含义，只是为了保证key的唯一。
			}else{
	    	//读XML文件
	    	ArrayList arrayListAll = new ArrayList();
	    	
	    	this.findConfigListByFileName(strURL, subXMLName);
	    	
	    	arrayListAll = this.getArrayList();
	    	
	    	if(arrayListAll.size() >0)
	    	{
	    		Iterator it = arrayListAll.iterator();
	    		
	    		for (int i=0;it.hasNext();i++)
	    		{
	    			ConfigItemInfo info = (ConfigItemInfo)it.next();
	    			
	    			resultField.put(info.getName(), info.getVale());
	    			//resultType.put(info.getName(), info.getType());
	    		}
	    	}
	    	
	    	String fieldName = "";
	    	//String fieldType = "";
	    		fieldName = (String)resultField.get(chineseFieldName);
	    	//fieldType = (String)resultType.get(chineseFieldName);
	    	
	    	//if(fieldName != null && fieldName.length()>0)
	    	//{
	    	//	returnVlaue = this.selectInfomation(tableName, fieldName, fieldType, lID, transNo);
	    	//}
	    	
	    	returnVlaue = (String)hashmap.get(fieldName);
	    	if(returnVlaue!=null)
	    		returnVlaue = this.specialChar(returnVlaue);
			}
	    	//System.out.println("通过查询返回的值为:"+returnVlaue);
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

		return returnVlaue;
	}
	
	//处理特殊字符
	public String specialChar(String str)
	{
		//str2和strList是对应的注：\r对应<br/>
		StringBuffer sb2 = new StringBuffer("\r\n");//特殊字符
		String[] strList = {"<br>","<br>"};//html标签
		
		StringBuffer sb = new StringBuffer(str);
		
		//将所有的"\r\n替换成\n"应为这两个字符表示换行即\n
		int kk;
		while(true)
		{
			kk = sb.indexOf("\r\n");
			if(kk>=0)
				sb.deleteCharAt(kk);
			else
				break;
		}
		
		//将特殊字符转换成相应的html标签
		for(int i=0;i<sb.length();i++)
		{
			for(int j=0;j<sb2.length();j++)
			{
				if(sb.charAt(i)==sb2.charAt(j))
				{
					//sb.replace(i, i, strList[j]);
					sb.deleteCharAt(i);
					sb.insert(i, strList[j]);
				}
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 根据模块查询配置项列表
	 * @param configFileName
	 * @throws Exception
	 * @return Collection 
	 */
	public Collection findConfigListByFileName(String strURL, String configFileName) throws Exception
	{
		ArrayList result = new ArrayList();
		String configFilePath = strURL+configFileName+".xml";
		
		//System.out.println("configFilePath=="+configFilePath);
		
		try
		{
			FileInputStream input = Helper.loadConfig(configFilePath);
			
			//所有超级接口： Node
			//Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
			//将xml数据流转换为DOM模型对象
			Document node = Helper.parse(input);
			
			//System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy========"+node);
			
			//将Dom模型反解析成响应对象
			result = marshal(node);
			
		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Loan_E106");
		}
		
		return result.size()>0?result:null;
	}
	
	/**
	 * 将Dom模型反解析成响应对象
	 */
	public ArrayList marshal(Node xmlDoc) throws Exception
	{
		ArrayList arrayList = null;
		//该 Node 接口是整个文档对象模型的主要数据类型
		if (xmlDoc == null)
		{
			Log.print("xmlDoc is null");
			throw new IException("Gen_E001");
		}

		arrayList = traverse(xmlDoc, null);
		
		return arrayList;
	}
	
	/**
	 * 将响应返回的XML文件解析成对象
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	private ArrayList traverse(Node node, ConfigItemInfo info) throws Exception
	{
		//该 Node 接口是整个文档对象模型的主要数据类型
		//表示基础对象的类型的节点
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :  //该节点为 Document
				{
					//public interface Documentextends Node
					//Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问
					Document document = (Document) node;
					
					//这是一种便捷属性，该属性允许直接访问文档的文档元素的子节点
					traverse(document.getDocumentElement(), info);
					break;
				}

			case Node.ELEMENT_NODE :  //该节点为 Element
				{
					//此节点的第一个子节点
					Node child = node.getFirstChild();
					
					while (child != null)
					{
						//此节点的名称，取决于其类型 child.getNodeName()
						if ("field".equals(child.getNodeName()))
						{ //如果是一个账户信息
							info = new ConfigItemInfo(); //new 一个新的信息类
							traverse(child, info); //结息账户信息
							
							resultSet.add(info);
							info = null;
						}
						else
						{
							traverse(child, info);
						}

						//直接在此节点之后的节点
						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :  //该节点为 Text 节点
				{
					/**
					 * 如果账户交易信息AccountTransactionInfo对象不为null
					 * 
					 * 则检测是否存在账户明细交易信息节点,存在则赋值
					 */

					//此节点的父节点 node.getParentNode()
					//此节点的名称，取决于其类型 getNodeName()
					if (info != null && "fieldname".equals(node.getParentNode().getNodeName()))
					{ 
						//此节点的值，取决于其类型
						info.setVale(node.getNodeValue());  //数据库对应字段
					}
					else if (info != null && "datatype".equals(node.getParentNode().getNodeName()))
					{ 
						info.setType(node.getNodeValue());  //字段类型
					}
					else if (info != null && "displaylabel".equals(node.getParentNode().getNodeName()))
					{ 
						info.setName(node.getNodeValue());  //字段中文名称
					}
					else if (info != null && "precision".equals(node.getParentNode().getNodeName()))
					{ 
						info.setDesc(node.getNodeValue());  //描述
					}

					break;
				}
		}
		
		return resultSet;
	}
}

/* Generated by Together */
/* Author:YanLiu */

package com.iss.itreasury.codingrule.util;
import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Collection;
import java.io.FileInputStream;
import com.iss.itreasury.codingrule.util.XMLParseHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.iss.itreasury.codingrule.dataentity.CodingSectInfo;
import com.iss.itreasury.codingrule.dataentity.ReturnFormatInfo;
import com.iss.itreasury.configtool.configmanage.dataentity.ConfigItemInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

import java.util.Vector;
import java.util.HashMap;

public class RuleEnv
{
	private static RuleEnv instance = new RuleEnv();
	//xml�����ļ�·��
	private static final String configFilePath = ".config/";
	//xml�����ļ���
	private static final String configFileName = "codingrule.xml";
    private static final String paraListFileName = "codingrule_paralist.xml";
	//xml�����ļ����ļ��������
	private  File xmlFile = null;
	private  File paraFile = null;
	//xml�����ļ�������޸�����
	private  long lastModifiedTime = 0;
	private long lastModifiedTime1 = 0;
	private  long lastModifiedTimePara = 0;

    //xml�����ļ�����Ӧ�ı������ͺͷ��ظ�ʽ,keyֵΪid,value����ΪCodingSectInfo��ReturnFormatInfo
	public  HashMap CodingSects = new HashMap();
    public  HashMap returnFormats = new HashMap();
    //�����б��ļ�����Ӧ��String����
    public  String[] paraList = null;
    private  ArrayList resultSectInfo =new ArrayList();
    private  ArrayList resultFormatInfo =new ArrayList();
    private  String param="";
    public static RuleEnv getInstance()
	{
    	return instance;
	}

	private RuleEnv()
	{		
		xmlFile = new File(configFilePath+configFileName);
		paraFile = new File(configFilePath+paraListFileName);
		//lastModifiedTime = xmlFile.lastModified();
		//lastModifiedTimePara = paraFile.lastModified();
		try
		{
           // FileInputStream input = XMLParseHelper.loadConfig(configFilePath+configFileName);
			//Document node = XMLParseHelper.parse(input);
			this.getCodingSects();
            this.getReturnFormats();
            this.getParamList();

			//input.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public  HashMap getCodingSects()
    {
		try
        {
			//�ж��Ƿ����
			if (!xmlFile.exists())
			{
				System.err.println(configFileName + " file does exist !");
				CodingSects = null;
				lastModifiedTime = 0;
			}
			else
			{
				// ���xml�ļ��Ƿ��޸Ĺ�
				long newTime = xmlFile.lastModified();
				// ����ǣ���ȡxml�ļ�
				if (newTime == 0)
				{
					CodingSects = null;
				}
	            //���xml�ļ����޸Ĺ����ߵ�һ�ε��ø÷���,���¶�ȡ
				else if (newTime > lastModifiedTime)
				{
					CodingSects = new HashMap();
					resultSectInfo =new ArrayList();
					FileInputStream input = XMLParseHelper.loadConfig(configFilePath+configFileName);
					Document node = XMLParseHelper.parse(input);
					CodingSects = parseToCodingSect(node);
	
					lastModifiedTime = newTime;
					input.close();
				}
			}
		}
		catch (Exception e)
		{
		   e .printStackTrace();
		}
		return CodingSects;
    }

    public HashMap getReturnFormats()
    {
		try
        {
			//�ж��Ƿ����
			if (!xmlFile.exists())
			{
				System.err.println(configFileName + " file does exist !");
				returnFormats = null;
				lastModifiedTime= 0;
			}
			else
			{
				// ���xml�ļ��Ƿ��޸Ĺ�
				long newTime = xmlFile.lastModified();
				// ����ǣ���ȡxml�ļ�
				if (newTime == 0)
				{
					returnFormats = null;
				}
	            //���xml�ļ����޸Ĺ����ߵ�һ�ε��ø÷���,���¶�ȡ
				else if (newTime >lastModifiedTime1 )
				{
					returnFormats = new HashMap();
					resultFormatInfo =new ArrayList();
					FileInputStream input = XMLParseHelper.loadConfig(configFilePath+configFileName);
					Document node = XMLParseHelper.parse(input);
					returnFormats = parseToReturnFormat(node);
	
					lastModifiedTime1 = newTime;
					input.close();
				}
			}
		}
		catch (Exception e)
		{
		   e .printStackTrace();
		}
		return returnFormats;
    }
    
    public String[] getParamList()
    {
    	try
    	{
    		//�ж��Ƿ����
			if (!paraFile.exists())
			{
				System.err.println(paraFile + " file does exist !");
				paraList = null;
				lastModifiedTimePara = 0;
			}
			else
			{
		    	//    	 ���xml�ļ��Ƿ��޸Ĺ�
				long newTime = paraFile.lastModified();
				// ����ǣ���ȡxml�ļ�
				if (newTime == 0)
				{
					paraList = null;
				}
		        //���xml�ļ����޸Ĺ����ߵ�һ�ε��ø÷���,���¶�ȡ
				else if (newTime >lastModifiedTimePara)
				{
					param="";
					FileInputStream input = XMLParseHelper.loadConfig(configFilePath+paraListFileName);
					Document node = XMLParseHelper.parse(input);
					paraList = parseToParaList(node);
		
					lastModifiedTimePara = newTime;
					input.close();
				}
			}
    	}
		catch (Exception e)
		{
		   e .printStackTrace();
		}
		return paraList;
    }
   /*
	 * ��xml�ļ�����ΪCodingSectInfo��������
	 * ����:1.��xml�ļ�����ΪCodingSectInfo��������  2.�����鸳�農̬����CodingSects
	 * @param       Node      			Dom��������������Document����
	 * @return      Collection        	CodingSectInfo���󼯺�
	 */
    private HashMap parseToCodingSect(Node node) throws Exception
	{
		//CodingSectInfo info = new CodingSectInfo();
        HashMap m_Return = new HashMap();
        
        traverseCodingSect(node,null);
		if(resultSectInfo!=null && resultSectInfo.size()>0)
		{  
			//System.out.println("resultSectInfo.size:"+resultSectInfo.size());
			for(Iterator it=resultSectInfo.iterator();it.hasNext();)
			{	
				CodingSectInfo codingsectinfo =(CodingSectInfo)it.next();
				//System.out.println("=========:"+codingsectinfo.getStrparaType());
				if(codingsectinfo.getStrparaType().length()>0)
				{
					codingsectinfo.setParaType(codingsectinfo.getStrparaType().substring(0, codingsectinfo.getStrparaType().length()-1).split(";"));
				}
				//System.out.println("=========:"+codingsectinfo.getStrparaType());
				if(codingsectinfo.getStrparaValue().length()>0)
				{
					codingsectinfo.setParaValue(codingsectinfo.getStrparaValue().substring(0, codingsectinfo.getStrparaValue().length()-1).split(";"));
				}
				m_Return.put(String.valueOf(codingsectinfo.getId()), codingsectinfo);
			}
		}
        //�����߼�
        return m_Return;
	}

   /*
	 * ��xml�ļ�����ΪReturnFormatInfo��������
	 * ����:1.��xml�ļ�����ΪReturnFormatInfo��������  2.�����鸳�農̬����returnFormats
	 * @param       Node      			Dom��������������Document����
	 * @return      Collection        	ReturnFormatInfo���󼯺�
	 */
    private HashMap parseToReturnFormat(Node node) throws Exception
	{
		// ReturnFormatInfo info = new ReturnFormatInfo();
         HashMap m_Return = new HashMap();
        traverseReturnFormat(node,null);
		if(resultFormatInfo!=null && resultFormatInfo.size()>0)
		{
			//System.out.println("returnformatinfo.size:"+resultFormatInfo.size());
			for(Iterator it=resultFormatInfo.iterator();it.hasNext();)
			{	
				ReturnFormatInfo returnformatinfo =(ReturnFormatInfo)it.next();
				m_Return.put(String.valueOf(returnformatinfo.getFormat_type()), returnformatinfo);
			}
		}
         //�����߼�
         return m_Return;
	}

	   /*
	 * ������xml�ļ�����ΪString����
	 * ����:1.��xml�ļ�����ΪString����  2.�����鸳�農̬����paraList
	 * @param       Node      			Dom��������������Document����
	 * @return      String[]        	ReturnFormatInfo���󼯺�
	 */
    private String[] parseToParaList(Node node) throws Exception
	{
		 String[] paraList = null;
		 traverseParaList(node);
		 if(param!=null && param.length()>0)
		 { 
			// System.out.println("param:"+param);
			 paraList=param.substring(0, param.length()-1).split(";");
		 }
         //�����߼�
         return paraList;
	}
    
    /**
	 * ����Ӧ���ص�XML�ļ������ɶ���
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	private void traverseCodingSect(Node node, CodingSectInfo info) throws Exception
	{
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					traverseCodingSect(document.getDocumentElement(), info);
					break;
				}

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						if ("codingtypeitem".equals(child.getNodeName()))
						{ //�����һ���˻���Ϣ
							info = new CodingSectInfo(); //new һ���µ���Ϣ��
							traverseCodingSect(child, info); //��Ϣ�˻���Ϣ
							resultSectInfo.add(info);
							info = null;
						}
						else
						{
							traverseCodingSect(child, info);
						}

						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					/**
					 * ����˻�������ϢAccountTransactionInfo����Ϊnull
					 * 
					 * �����Ƿ�����˻���ϸ������Ϣ�ڵ�,������ֵ
					 */

					//
					if (info != null && "id".equals(node.getParentNode().getNodeName()))
					{ //
						info.setId(Long.valueOf(node.getNodeValue()).longValue());
					}
					else if (info != null && "name".equals(node.getParentNode().getNodeName()))
					{ //
						info.setName(node.getNodeValue());
					}
					else if (info != null && "desc".equals(node.getParentNode().getNodeName()))
					{ //
						info.setDesc(node.getNodeValue());
					}
					else if (info != null && "value_type".equals(node.getParentNode().getNodeName()))
					{ //
						info.setValue_type(Long.valueOf(node.getNodeValue()).longValue());
					}
					else if (info != null && "sql_string".equals(node.getParentNode().getNodeName()))
					{ //
						info.setSql_string(node.getNodeValue());
					}
					//�����÷ֺŷָ�
					else if (info != null && "paratype".equals(node.getParentNode().getNodeName()))
					{ //
						info.setStrparaType(info.getStrparaType()+node.getNodeValue()+";");
					}
					else if (info != null && "value_return".equals(node.getParentNode().getNodeName()))
					{ //
						info.setValue_return(node.getNodeValue());
					}
					else if (info != null && "format_type".equals(node.getParentNode().getNodeName()))
					{ //
						info.setFormat_type(Long.valueOf(node.getNodeValue()).longValue());
					}
					else if (info != null && "method_class".equals(node.getParentNode().getNodeName()))
					{ //
						info.setMethod_class(node.getNodeValue());
					}
					else if (info != null && "method_name".equals(node.getParentNode().getNodeName()))
					{ //
						info.setMethod_name(node.getNodeValue());
					}
					//�÷ֺŷָ�
					else if (info != null && "paravalue".equals(node.getParentNode().getNodeName()))
					{ //
						info.setStrparaValue(info.getStrparaValue()+node.getNodeValue()+";");
					}
					break;
				}
		}
		
	}
	 /**
	 * ����Ӧ���ص�XML�ļ������ɶ���
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	private void traverseReturnFormat(Node node, ReturnFormatInfo info) throws Exception
	{
		HashMap resultSet =new HashMap();
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					traverseReturnFormat(document.getDocumentElement(), info);
					break;
				}

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						if ("formatitem".equals(child.getNodeName()))
						{ //�����һ���˻���Ϣ
							info = new ReturnFormatInfo(); //new һ���µ���Ϣ��
							traverseReturnFormat(child, info); //��Ϣ�˻���Ϣ
							resultFormatInfo.add(info);
							info = null;
						}
						else
						{
							traverseReturnFormat(child, info);
						}

						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					/**
					 * ����˻�������ϢAccountTransactionInfo����Ϊnull
					 * 
					 * �����Ƿ�����˻���ϸ������Ϣ�ڵ�,������ֵ
					 */

					//
					if (info != null && "format_type".equals(node.getParentNode().getNodeName()))
					{ //
						info.setFormat_type(Long.valueOf(node.getNodeValue()).longValue());
					}
					else if (info != null && "format_mode".equals(node.getParentNode().getNodeName()))
					{ //
						info.setFormat_mode(node.getNodeValue());
					}
					
					//�����÷ֺŷָ�
					else if (info != null && "parameter".equals(node.getParentNode().getNodeName()))
					{ //
						info.setParameter(info.getParameter()+node.getNodeValue()+";");
					}
					
					break;
				}
		}
	}
	/**
	 * ����Ӧ���ص�XML�ļ������ɶ���
	 * @param node
	 * @param info
	 * @throws Exception
	 */
	private  void  traverseParaList(Node node) throws Exception
	{	
		short type = node.getNodeType();
		switch (type)
		{
			case Node.DOCUMENT_NODE :
				{
					Document document = (Document) node;
					traverseParaList(document.getDocumentElement());
					break;
				}

			case Node.ELEMENT_NODE :
				{
					Node child = node.getFirstChild();
					while (child != null)
					{
						traverseParaList(child); 
						child = child.getNextSibling();
					}
					break;
				}
			case Node.TEXT_NODE :
				{
					//�÷ֺ����ָ�
					if ("name".equals(node.getParentNode().getNodeName()))
					{ //
						param=param+node.getNodeValue()+";";
					}
					break;
				}
		}
	
	}
	public static void main(String[] args)
	{
		RuleEnv.getInstance();
	}
}
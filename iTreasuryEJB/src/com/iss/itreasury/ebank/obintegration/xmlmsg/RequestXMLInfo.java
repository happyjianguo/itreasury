/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 * 
 * 请求xml报文基类
 */
public class RequestXMLInfo {
    
    protected String OperationType = null; //<OperationType>操作类型</OperationType>
    
    protected String SystemID = null; //<SystemID>系统标识</SystemID>
    
    public RequestXMLInfo() {
    }
    
    /**
     * @return Returns the operationType.
     */
    public String getOperationType() 
    {
        return OperationType;
    }
    /**
     * @param operationType The operationType to set.
     */
    public void setOperationType(String operationType) 
    {
        OperationType = operationType;
    }
    /**
     * @return Returns the systemID.
     */
    public String getSystemID() 
    {
        return SystemID;
    }
    /**
     * @param systemID The systemID to set.
     */
    public void setSystemID(String systemID) 
    {
        SystemID = systemID;
    }
    
    
    public void marshal(Node xmlDoc) throws Exception
    {
        if (xmlDoc == null)
        {
            throw new IException("null DOM object.");
        }

        traverse(xmlDoc);
    }

    private void traverse(Node node) throws Exception
    {
        short type = node.getNodeType();
        String strName = node.getNodeName();
        //logger.debug("Node name:" + strName);
        //logger.debug("Node type:" + type);
        switch (type)
        {
            case Node.DOCUMENT_NODE :
            {
                Document document = (Document) node;

                traverse(document.getDocumentElement());
                break;
            }
            case Node.ELEMENT_NODE :
            {
                Node child = node.getFirstChild();
                while (child != null)
                {
                    traverse(child);

                    child = child.getNextSibling();
                }
                break;
            }
            case Node.TEXT_NODE :
            {
                XMLHelper.setValueFromNodeToObject(node, this);
                break;
            }
        }
    }

    /**
     * 反解析为Node
     * @param doc
     * @return
     * @throws Exception
     */
    public Node unmarshal(Document doc) throws Exception
    {
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer sbResult = new StringBuffer(128);

        sbResult.append(
            this.getClass().getName()
                + " instance (hashCode="
                + this.hashCode()
                + ")\r\n");
        sbResult.append("=========================================\r\n");

        //获得当前对象指定名称的Field对象
        java.lang.reflect.Field[] field = null;
        try
        {
            String strTemp = null;
            ArrayList alClass = new ArrayList(6);
            Class classTemp = this.getClass();
            while (!classTemp.getName().equals("java.lang.Object"))
            {
                alClass.add(classTemp);
                classTemp = classTemp.getSuperclass();
            }

            for (int i = alClass.size() - 1; i >= 0; i--)
            {
                classTemp = (Class) alClass.get(i);
                field = classTemp.getDeclaredFields();
                if (field != null)
                {
                    for (int j = 0; j < field.length; j++)
                    {
                        field[j].setAccessible(true);

                        strTemp = field[j].getName();

                        if (!strTemp.startsWith("this"))
                        {
                            sbResult.append(field[j].getName() + " = ");
                            sbResult.append(field[j].get(this) + ";\r\n");
                        }

                    }

                    field = null;
                }
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }

        return sbResult.toString();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

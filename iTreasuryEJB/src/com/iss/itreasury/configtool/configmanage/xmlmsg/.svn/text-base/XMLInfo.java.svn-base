package com.iss.itreasury.configtool.configmanage.xmlmsg;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class XMLInfo
{
    /**
     * Constructor for XMLInfo.
     */
    public XMLInfo()
    {
        super();
    }
    
    public void marshal(Node xmlDoc) throws Exception
    {
        //stub
    }

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
}

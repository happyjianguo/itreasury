/*
 * Created on 2005-8-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.tags.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.tags.database.DatabaseFactory;


/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBMultipleSelect extends BaseTagSupport{
    
    private Connection con=null;
    private PreparedStatement ps=null;
    private PreparedStatement ps2=null;
    private ResultSet rs=null;
    private ResultSet rs2=null;
    
    public int doStartTag() throws JspException
    {
      try {
        
        showParams();
        if ((sql.indexOf(sqlKey) == -1 || sql.indexOf(sqlValue) == -1) && sql.indexOf("*") < 0) {
          System.out.println("传入的参数不正确!");
          throw new JspException();
        }
        con = DatabaseFactory.getInstance().getTagConnection();
        String strTmp = new String();
        strTmp = sql;
        if (sql != null && sql.length() > 0)
        {
	        System.out.println(strTmp);
	        ps = con.prepareStatement(strTmp);
	        rs = ps.executeQuery();
        }
        if (rightSql != null && rightSql.length() > 0)
        {
        	 strTmp = rightSql;
        	 ps2 = con.prepareStatement(strTmp);
             rs2 = ps2.executeQuery();
        }
        ShowHtml(rs,rs2);
      }
      catch (Exception ex) {
          System.out.println("DBMultipleSelect error!");
          ex.printStackTrace();
      }
      finally
      {
          try {
	          if (rs != null)
	          {
	             rs.close();
	             rs = null;
	          }
	          if (ps != null)
	          {
	              ps.close();
	              ps = null;
	          }
	          if (rs2 != null)
	          {
	             rs2.close();
	             rs2 = null;
	          }
	          if (ps2 != null)
	          {
	              ps2.close();
	              ps2 = null;
	          }
	          if (con != null)
	          {
	              con.close();
	              con = null;
	          }
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }

      return EVAL_PAGE;
    }
    
    
    private void ShowHtml(ResultSet rs,ResultSet rs2)
    {
    	int order = 1;
    	String leftName = name + "Left";
    	String rightName = name + "Right";
    	String strDisabled = "";
    	String methodContent = form +"."+ leftName +", "+ form +"."+ rightName +","+ form +"."+ name;
    	if (disabled) strDisabled = " disabled";
        try
        {
	        JspWriter out = pageContext.getOut();
	        out.println("<table align=center width=\"100%\" "+ tableStyle +">");
	        out.println("<tbody>");
	        out.println("	<tr>");
	        //左边LABEL
	        out.println("	<td width="+ td1Width +" valign=\"top\" "+ td1Style +">");
	        out.println("	" + label);
	        out.println("	</td>");
	        
	        //左边的TD
	        out.println("	<td width="+ td2Width +" valign=\"top\" "+ td2Style +">");
	        
	        out.println("	<select size="+ size +" name=\""+ leftName +"\" style=\"width:"+ width +"\"  multiple "+ strDisabled +" onfocus=\"nextfield ='"+ nextField +"';\" "+ property +">");
	        if (rs != null)
	        {
		        while (rs.next()) {
		            out.println("		<option value=\"" + rs.getString(sqlKey) + "$" + order + "\">" + rs.getString(sqlValue) + "</option>");
		            order++;
		        }
	        }
	        out.println("	</select>");
	        out.println("	</td>");
	        
	        //中间的TD
	        out.println("	<TD width="+ td3Width +" "+ td3Style +">");
	        if (upButtonImg != null && upButtonImg.length() > 0)	//显示图片
	        	out.println("	<input name=\"buttonToRight\" type=\"image\" src=\""+ upButtonImg +"\" onclick=\"return SourceToTarget("+ methodContent +")\""+ strDisabled +">");
	        else
	        	out.println("	<input name=\"buttonToRight\" type=\"button\" class=\""+ buttonClass +"\" onclick=\"return SourceToTarget("+ methodContent +")\" value=\"&nbsp;－>>&nbsp;\""+ strDisabled +">");
	        out.println("	<br><br>");
	        if (downButtonImg != null && downButtonImg.length() > 0)	//显示图片
	        	out.println("	<input name=\"buttonToLeft\" type=\"image\" src=\""+ downButtonImg +"\" onclick=\"return TargetToSource("+ methodContent +")\""+ strDisabled +">");
	        else
	        	out.println("	<input name=\"buttonToLeft\" type=\"button\" class=\""+ buttonClass +"\" onclick=\"return TargetToSource("+ methodContent +")\" value=\"&nbsp;<<－&nbsp;\""+ strDisabled +">");
	        out.println("	</td>");
	        
	        //右边的TD
	        out.println("	<td width="+ td4Width +" valign=\"top\" "+ td4Style +">");
	        out.println("	<select size="+ size +" name=\""+ rightName +"\" style=\"width:"+ width +"\"  multiple"+ strDisabled +">");
	        if (rs2 != null)
	        {
	        	String hiddenValue = "";
		        while (rs2.next()) {
		            out.println("		<option value=\"" + rs2.getString(sqlKey) + "$"+ order +"\">" + rs2.getString(sqlValue) + "</option>");
		            hiddenValue += rs2.getString(sqlKey)+",";
		            order++;
		        }
		        if (value ==null || value.length() == 0)
		        {
			        if (hiddenValue.length() > 0)
			        	value = hiddenValue.substring(0,hiddenValue.length()-1);	//去掉最后一个逗号
		        }
	        }
	        else
	        {
	            if (needAll)	out.println("		<option value=\"-1\">全部</option>");
	        }
	        out.println("	</select>");
	        out.println("	</td>");
	        out.println("	</tr>");
	        out.println("	<input type=\"hidden\" name=\""+ name +"\" value = \""+ value +"\">");
	        out.println("</tbody>");
	        out.println("</table>");
	        
        }catch(IOException e)
        {
            
        } catch (SQLException e) {
            
        }
    }
    
    private void showParams()
    {
      System.out.println("name:"+name);
      System.out.println("form:"+form);
      System.out.println("label:"+label);
      System.out.println("width:"+width);
      System.out.println("size:"+size);
      System.out.println("sql:"+sql);
      System.out.println("rightSql:"+rightSql);
      System.out.println("sqlValue:"+sqlValue);
      System.out.println("sqlKey:"+sqlKey);
      System.out.println("disabled:"+disabled);
      System.out.println("value:"+value);
      System.out.println("buttonClass:"+buttonClass);
      System.out.println("upButtonImg:"+upButtonImg);
      System.out.println("downButtonImg:"+downButtonImg);
      System.out.println("property:"+property);
      System.out.println("nextField:"+nextField);
      System.out.println("tableStyle:"+tableStyle);
      System.out.println("td1Width:"+td1Width);
      System.out.println("td2Width:"+td2Width);
      System.out.println("td3Width:"+td3Width);
      System.out.println("td4Width:"+td4Width);
      System.out.println("td1Style:"+td1Style);
      System.out.println("td2Style:"+td2Style);
      System.out.println("td3Style:"+td3Style);
      System.out.println("td4Style:"+td4Style);
      System.out.println("needAll:"+needAll);
    }
    
    private String name	 			 =		"";				//此控件取值的input的名称
    private String form	 			 =		"form1";		//此控件所在的form的名称
    private String label			 = 		"";				//页面显示标题：如选择用户名
    private String width 			 =		"150";			//select的宽度
    private String size 			 =		"6";			//select的行数
    private String sql				 =		"";				//传入的SQL
    private String rightSql			 =		"";				//传入的SQL
    private String sqlValue			 =		"name";			//select的显示值对应的数据库字段，默认为 name
    private String sqlKey			 =		"id";			//select的value值对应的数据库字段，默认为 id
    private boolean disabled		 =		false;			//是否禁用，默认为false
    private String value			 =		"";				//隐藏值
    private String buttonClass		 =		"";				//按钮的样式
    private String upButtonImg		 =		"";				//上面按钮的图片
    private String downButtonImg	 =		"";				//下面按钮的图片
    private String property			 =		"";				//扩展属性
    private String nextField		 = 		"";				//下一个焦点
    private String tableStyle		 =		"";				//扩展属性
    private String td1Width			 =		"10%";			//TD1宽度
    private String td2Width			 =		"40%";			//TD2宽度
    private String td3Width	 		 =		"10%";			//TD3宽度
    private String td4Width	 		 =		"40%";			//TD4宽度
    private String td1Style			 =		"";				//TD1属性
    private String td2Style			 =		"";				//TD2属性
    private String td3Style	 		 =		"";				//TD3属性
    private String td4Style	 		 =		"";				//TD4属性
    private boolean needAll 		 =		true;			//是否需要全部
    
    
	/**
	 * @param buttonClass The buttonClass to set.
	 */
	public void setButtonClass(String buttonClass)
	{
		this.buttonClass = buttonClass;
	}
	/**
	 * @param disabled The disabled to set.
	 */
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
	/**
	 * @param downButtonImg The downButtonImg to set.
	 */
	public void setDownButtonImg(String downButtonImg)
	{
		this.downButtonImg = downButtonImg;
	}
	/**
	 * @param form The form to set.
	 */
	public void setForm(String form)
	{
		this.form = form;
	}
	/**
	 * @param label The label to set.
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @param property The property to set.
	 */
	public void setProperty(String property)
	{
		this.property = property;
	}
	/**
	 * @param rightSql The rightSql to set.
	 */
	public void setRightSql(String rightSql)
	{
		this.rightSql = rightSql;
	}
	/**
	 * @param size The size to set.
	 */
	public void setSize(String size)
	{
		this.size = size;
	}
	/**
	 * @param sql The sql to set.
	 */
	public void setSql(String sql)
	{
		this.sql = sql;
	}
	/**
	 * @param sqlKey The sqlKey to set.
	 */
	public void setSqlKey(String sqlKey)
	{
		this.sqlKey = sqlKey;
	}
	/**
	 * @param sqlValue The sqlValue to set.
	 */
	public void setSqlValue(String sqlValue)
	{
		this.sqlValue = sqlValue;
	}
	/**
	 * @param upButtonImg The upButtonImg to set.
	 */
	public void setUpButtonImg(String upButtonImg)
	{
		this.upButtonImg = upButtonImg;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width)
	{
		this.width = width;
	}
    
    /**
     * @param tableStyle The tableStyle to set.
     */
    public void setTableStyle(String tableStyle) {
        this.tableStyle = tableStyle;
    }
    /**
     * @param td1Style The td1Style to set.
     */
    public void setTd1Style(String td1Style) {
        this.td1Style = td1Style;
    }
    /**
     * @param td1Width The td1Width to set.
     */
    public void setTd1Width(String td1Width) {
        this.td1Width = td1Width;
    }
    /**
     * @param td2Style The td2Style to set.
     */
    public void setTd2Style(String td2Style) {
        this.td2Style = td2Style;
    }
    /**
     * @param td2Width The td2Width to set.
     */
    public void setTd2Width(String td2Width) {
        this.td2Width = td2Width;
    }
    /**
     * @param td3Style The td3Style to set.
     */
    public void setTd3Style(String td3Style) {
        this.td3Style = td3Style;
    }
    /**
     * @param td3Width The td3Width to set.
     */
    public void setTd3Width(String td3Width) {
        this.td3Width = td3Width;
    }
    /**
     * @param td4Style The td4Style to set.
     */
    public void setTd4Style(String td4Style) {
        this.td4Style = td4Style;
    }
    /**
     * @param td4Width The td4Width to set.
     */
    public void setTd4Width(String td4Width) {
        this.td4Width = td4Width;
    }
    
    /**
     * @param nextField The nextField to set.
     */
    public void setNextField(String nextField) {
        this.nextField = nextField;
    }
    
    
    /**
     * @param isNeedAll The isNeedAll to set.
     */
    public void setNeedAll(boolean needAll) {
        this.needAll = needAll;
    }
    
}

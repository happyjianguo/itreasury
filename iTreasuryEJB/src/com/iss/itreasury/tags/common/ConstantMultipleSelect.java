/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.tags.common;

import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstantMultipleSelect extends BaseTagSupport{
    
    public int doStartTag() throws JspException
    {
      try {
          showParams();

          long[] lArrayKey = null;
          String[] strArrayValue = null;
          int i = 0;

          //得到一个常量类
          Class constantClass = Class.forName(constantName);

          Method methods[] = constantClass.getDeclaredMethods();

          Object param[] = new Object[1];
          Object param1[] = null;
          if (office!=null && office.length() > 0 && currency != null && currency.length() > 0)
          {
              param1 = new Object[]{new Long(office),new Long(currency)};
          }
          for (i = 0; i < methods.length; i++) {
                  if (methods[i].getName().equalsIgnoreCase("getAllCode")) {
                  		if (param1 != null )
                  			if (methods[i].getParameterTypes().length != param1.length) continue;	//参数不一样多继续
                      lArrayKey = (long[]) methods[i].invoke(constantClass, param1);
                          break;
                  }
          }
          
          lArrayKey=shadowArray( lArrayKey,value_scope );

          for (i = 0; i < methods.length; i++) {
                  if (methods[i].getName().equalsIgnoreCase("getName")) {
                      strArrayValue = new String[lArrayKey.length];

                          for (int n = 0; n < lArrayKey.length; n++) {
                                  param[0] = new Long(lArrayKey[n]);
                                  strArrayValue[n] = (String) methods[i].invoke(
                                                  constantClass, param);
                          }
                          break;
                  }
          }
      
        ShowHtml(lArrayKey,strArrayValue);
      }
      catch (Exception ex) {
      	ex.printStackTrace();
      }
      finally
      {
          
      }

      return EVAL_PAGE;
    }
    
    private void ShowHtml(long[] lArrayKey,String[] strArrayValue)
    {
        System.out.println("enter ShowHtml");
    	int order = 1;
    	String leftName = name + "Left";
    	String rightName = name + "Right";
    	String strDisabled = "";
    	String methodContent = form +"."+ leftName +", "+ form +"."+ rightName +","+ form +"."+ name;
    	if (disabled) strDisabled = " disabled";
        try
        {
	        JspWriter out = pageContext.getOut();
	        out.println("<table align=center border='0' width=\"100%\" "+ tableStyle +">");
	        out.println("<tbody>");
	        out.println("	<tr>");
	        //左边LABEL
	        out.println("	<td width="+ td1Width +" valign=\"top\" "+ td1Style +">");
	        out.println("	" + label);
	        out.println("	</td>");
	        
	        //左边的TD
	        out.println("	<td width="+ td2Width +" valign=\"top\" align=\"right\" "+ td2Style +">");
	        
	        out.println("	<select  size="+ size +" name=\""+ leftName +"\" style=\"background-color:#ffffff;width:"+ width +"\"  multiple "+ strDisabled +" onfocus=\"nextfield ='"+ nextField +"';\" "+ property +">");
	        
	        for (int i = 0; i < lArrayKey.length; i++) {
	        	if(strArrayValue[i] != null && strArrayValue[i].length() > 0)
	        	{
	        		out.println("		<option value=\"" + lArrayKey[i] + "$" + order + "\">" + strArrayValue[i] + "</option>");
	        	}
	            order++;
	        }
	        out.println("	</select>");
	        out.println("	</td>");
	        
	        //中间的TD
	        out.println("	<TD width="+ td3Width +"  align=\"center\"  "+ td3Style +">");
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
	        out.println("	<td width="+ td4Width +" valign=\"top\"  align=\"left\"  "+ td4Style +">");
	        out.println("	<select size="+ size +" name=\""+ rightName +"\" style=\"background-color:#ffffff;width:"+ width +"\"  multiple"+ strDisabled +">");
	        if (needAll) out.println("		<option value=\"-1\">全部</option>");
	        out.println("	</select>");
	        out.println("	</td>");
	        out.println("	</tr>");
	        out.println("	<input type=\"hidden\" name=\""+ name +"\" value = \""+ value +"\">");
	        out.println("</tbody>");
	        out.println("</table>");
	        
        }catch(Exception e)
        {
            System.out.println("tag error");
            e.printStackTrace();
        }
    }
    
      
      private void showParams()
      {
        System.out.println("name:"+name);
        System.out.println("form:"+form);
        System.out.println("label:"+label);
        System.out.println("width:"+width);
        System.out.println("size:"+size);
        System.out.println("constantName:"+constantName);
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
        System.out.println("office:"+office);
        System.out.println("currency:"+currency);
      }
      
      private String name	 		 =		"";				//此控件取值的input的名称
      private String form	 		 =		"form1";		//此控件所在的form的名称
      private String label			 = 		"";				//页面显示标题：如选择用户名
      private String width 			 =		"150";			//select的宽度
      private String size 			 =		"6";			//select的行数
      private String constantName	 = 		"";				//常量类名称
      private boolean disabled		 =		false;			//是否禁用，默认为false
      private String value			 =		"";				//隐藏值
      private String buttonClass	 =		"";				//按钮的样式
      private String upButtonImg	 =		"";				//上面按钮的图片
      private String downButtonImg	 =		"";				//下面按钮的图片
      private String property		 =		"";				//扩展属性
      private String nextField		 = 		"";				//下一个焦点
      private String tableStyle		 =		"";				//扩展属性
      private String td1Width		 =		"10%";			//TD1宽度
      private String td2Width		 =		"30%";			//TD2宽度
      private String td3Width	 	 =		"10%";			//TD3宽度
      private String td4Width	 	 =		"";			//TD4宽度
      private String td1Style		 =		"";				//TD1属性
      private String td2Style		 =		"";				//TD2属性
      private String td3Style	 	 =		"";				//TD3属性
      private String td4Style	 	 =		"";				//TD4属性
      private boolean needAll 	 	 =		true;			//是否需要全部 	
      private String office			 = 		"";				//办事处
      private String currency		 = 		"";				//币种
      
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
  	 * @param size The size to set.
  	 */
  	public void setSize(String size)
  	{
  		this.size = size;
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
     * @param constantName The constantName to set.
     */
    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }
    
    
    
    /**
     * @param td1Style The td1Style to set.
     */
    public void setTd1Style(String td1Style) {
        this.td1Style = td1Style;
    }
    /**
     * @param td2Style The td2Style to set.
     */
    public void setTd2Style(String td2Style) {
        this.td2Style = td2Style;
    }
    /**
     * @param td3Style The td3Style to set.
     */
    public void setTd3Style(String td3Style) {
        this.td3Style = td3Style;
    }
    /**
     * @param td4Style The td4Style to set.
     */
    public void setTd4Style(String td4Style) {
        this.td4Style = td4Style;
    }
    /**
     * @param tableStyle The tableStyle to set.
     */
    public void setTableStyle(String tableStyle) {
        this.tableStyle = tableStyle;
    }
    /**
     * @param td1Width The td1Width to set.
     */
    public void setTd1Width(String td1Width) {
        this.td1Width = td1Width;
    }
    /**
     * @param td2Width The td2Width to set.
     */
    public void setTd2Width(String td2Width) {
        this.td2Width = td2Width;
    }
    /**
     * @param td3Width The td3Width to set.
     */
    public void setTd3Width(String td3Width) {
        this.td3Width = td3Width;
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
     * @param currency The currency to set.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    /**
     * @param office The office to set.
     */
    public void setOffice(String office) {
        this.office = office;
    }
    
    /**
     * @param needAll The needAll to set.
     */
    public void setNeedAll(boolean needAll) {
        this.needAll = needAll;
    }
}

/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.tags.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.clientmanage.bizdelegation.CustomFieldDelegation;
import com.iss.itreasury.clientmanage.systemset.customfield.dataentity.CustomFieldInfo;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.util.Constant;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomFiledSelect extends BaseTagSupport{
    
    public int doStartTag() throws JspException
    {
      try {
    	  showParams();
          int j = 0;
          int k = 0;
          Collection allField = new ArrayList();
          Collection existField = new ArrayList();
          //得到一个常量类
          Class constantClass = Class.forName(constantName);
          Field[] field = constantClass.getDeclaredFields();
          long[] lArrayKey = new long[field.length];
          long[] tmplArray = null;
            for(int i=0;i<field.length;i++)
            {
        	  lArrayKey[i] = field[i].getLong(constantClass);
        	  allField.add(String.valueOf(lArrayKey[i]));
            }
	        CustomFieldDelegation cfd = new CustomFieldDelegation();
	      	Vector vResult=cfd.findByCondition(Constant.RecordStatus.VALID,Long.parseLong(office),Long.parseLong(currency));
	      	if(vResult.size()>0){
		      	tmplArray = new long[vResult.size()];
		      	System.out.println("------------------lArray.length----------------------"+tmplArray.length);
		      	for(Iterator it=vResult.iterator();it.hasNext();)
		      	{
		      		CustomFieldInfo info = (CustomFieldInfo)it.next();
		      		tmplArray[j] = info.getFieldID();
		      		existField.add(String.valueOf(tmplArray[j]));
		      		j++;
		      	}
		      	allField.removeAll(existField);
		      	lArrayKey = new long[allField.size()];
		      	for(Iterator it = allField.iterator();it.hasNext();)
		      	{
		      		lArrayKey[k] = Long.parseLong((String)it.next());
		      		k++;
		      	}
	      	}
	      	for(int n=0;n<lArrayKey.length;n++)
	      	{
	      		System.out.println("---------------lArrayKey["+n+"]---------------------:"+lArrayKey[n]);
	      	}
	      	System.out.println("---------------lArrayKey[.length---------------------:"+lArrayKey.length);
	        ShowHtml(lArrayKey,vResult);
      }
      catch (Exception ex) {
      	ex.printStackTrace();
      }
      finally
      {
          
      }

      return EVAL_PAGE;
    }
    
    private void ShowHtml(long[] lArrayKey,Vector vector)
    {
    	if(vector.size()>0){
    		needAll = false;
    	}
        System.out.println("enter ShowHtml");
    	int order = 1;
    	String leftName = name + "Left";
    	String rightName = name + "Right";
    	String strDisabled = "";
    	String methodContent = form +"."+ leftName +", "+ form +"."+ rightName +","+ form +"."+ name+","+form+"."+"existfieldID";
    	if (lArrayKey.length<=0) strDisabled = " disabled";
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
	        
	        for (int i = 0; i < lArrayKey.length; i++) {
	        	
	        		out.println("		<option value=\"" + lArrayKey[i] + "$" + order + "\">" + CMConstant.FieldID(lArrayKey[i]) + "</option>");
	    	        
	            order++;
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
	        String str = "";
	        String existfieldID = "";
	        if(needAll){
	        	 out.println("		<option value=\"-1\">&nbsp;</option>");
	        }else{
	            
	        	for(Iterator it=vector.iterator();it.hasNext();){
	        		CustomFieldInfo info = (CustomFieldInfo)it.next();
	        		str = str+info.getId()+",";
	        		existfieldID =  existfieldID+info.getFieldID()+",";
	        	    out.println("		<option style=\"color: #c0c0c0; background-color: #FFFFFF; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\" value=\""+info.getFieldID()+"\">"+CMConstant.FieldID(info.getFieldID())+"</option>");
	        	}
	        }
	        
	        out.println("	</select>");
	        out.println("	</td>");
	        out.println("	</tr>");
	        for (int i = 0; i < lArrayKey.length; i++) {
	           out.println("	<input type=\"hidden\" name=\"FieldID\" value = \""+  lArrayKey[i] +"\">");
	        }
	        out.println("	<input type=\"hidden\" name=\""+ name +"\" value = \""+ value +"\">");
	        out.println("	<input type=\"hidden\" name=\"ids\" value = \""+ str +"\">");
	        out.println("	<input type=\"hidden\" name=\"existfieldID\" value = \""+ existfieldID +"\">");
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
      private String td2Width		 =		"40%";			//TD2宽度
      private String td3Width	 	 =		"10%";			//TD3宽度
      private String td4Width	 	 =		"40%";			//TD4宽度
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

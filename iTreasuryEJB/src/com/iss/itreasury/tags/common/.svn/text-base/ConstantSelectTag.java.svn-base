package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import java.lang.reflect.*;
import javax.servlet.jsp.JspWriter;
import java.io.*;

public class ConstantSelectTag extends BaseTagSupport{
  private String controlName = "ConstantSelectTag";
  private String constantName= "";
  private long defaultValue = -1;
  private boolean isNeedAll = false;
  private boolean isNeedBlank = false;
  private boolean isDisabled = false;
  private String extProperty = "";
  private String valueScope = "";
  private long[] value_scope=null;

  public int doStartTag() throws JspException {
    try {
      JspWriter out = pageContext.getOut();

      showParams();

      long[] lArrayID = null;
      String[] strArrayName = null;
      int i = 0;

      //得到一个常量类
      Class constantClass = Class.forName(constantName);

      Method methods[] = constantClass.getDeclaredMethods();

      Object param[] = new Object[1];
      
      Method getAllCodeMethod = constantClass.getMethod("getAllCode", new Class[]{});
      if(getAllCodeMethod!=null)
      {
    	  lArrayID = (long[]) getAllCodeMethod.invoke(constantClass, new Object[]{});
      }
      
      lArrayID=shadowArray( lArrayID,value_scope );

      for (i = 0; i < methods.length; i++) {
              if (methods[i].getName().equalsIgnoreCase("getName")) {
                      strArrayName = new String[lArrayID.length];

                      for (int n = 0; n < lArrayID.length; n++) {
                              param[0] = new Long(lArrayID[n]);
                              strArrayName[n] = (String) methods[i].invoke(
                                              constantClass, param);
                      }
                      break;
              }
      }

      out.println("<select name=\"" + controlName + "\" "
                      + extProperty + ">");
      if (isNeedBlank == true) {
              if (defaultValue == -1) {
                      out.println("<option value='-1' selected>&nbsp;</option>");
              } else {
                      out.println("<option value='-1'>&nbsp;</option>");
              }
      }
      for (i = 0; i < lArrayID.length; i++) {
              if (lArrayID[i] == defaultValue) {
                      out.println("<option value='" + lArrayID[i]
                                      + "' selected >" + strArrayName[i] + "</option>");
              } else {
                      out.println("<option value='" + lArrayID[i] + "'>"
                                      + strArrayName[i] + "</option>");
              }
      }
      if (isNeedAll == true) {
              if (defaultValue == 0) {
                      out.println("<option value='0' selected>全部</option>");
              } else {
                      out.println("<option value='0'>全部</option>");
              }
      }
      out.println("</select>");
    }
    catch (IOException ex) {
    	ex.printStackTrace() ;
    }
    catch (Exception ex) {
    	ex.printStackTrace() ;
    }

    return EVAL_PAGE;
  }

  private void showParams() {
    System.out.println("controlName:" + controlName);
    System.out.println("defaultValue:" + defaultValue);
    System.out.println("isNeedAll:" + isNeedAll);
    System.out.println("isNeedBlank:" + isNeedBlank);
    System.out.println("isDisabled:" + isDisabled);
    System.out.println("extProperty:" + extProperty);
    System.out.println("scopeValue:" + valueScope);
    System.out.println("constantName:" + constantName);
    
  }

  public void setControlName(String controlName) {
    this.controlName = controlName;
  }

  public void setDefaultValue(long defaultValue) {
    this.defaultValue = defaultValue;
  }

  public void setExtProperty(String extProperty) {
    this.extProperty = extProperty;
  }

  public void setIsDisabled(boolean isDisabled) {
    this.isDisabled = isDisabled;
  }

  public void setIsNeedAll(boolean isNeedAll) {
    this.isNeedAll = isNeedAll;
  }

  public void setIsNeedBlank(boolean isNeedBlank) {
    this.isNeedBlank = isNeedBlank;
  }
  public void setConstantName(String constantName) {
    this.constantName = constantName;
  }
  
  public static void main(String[] args)
  {
  	ConstantSelectTag tag=new ConstantSelectTag();
  	
  	long[] a = new long[]{1,2,3,4};
  	long[] b = new long[]{1,3};
  	long[] c=tag.shadowArray( a,b);
  	System.out.println(c.length );
  	
  }
  
}

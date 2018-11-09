package com.iss.itreasury.tags.common;

import javax.servlet.jsp.JspException;
import java.sql.*;
import com.iss.itreasury.util.*;
import javax.servlet.jsp.JspWriter;
import java.io.*;

public class DBSelectTag extends BaseTagSupport{
  private String controlName="DBSelectTag";
  private String strSQL="";
  private String displayField="";
  private String valueField="";
  private String defaultValue="";
  private boolean isNeedAll=false;
  private boolean isNeedBlank=false;
  private boolean isDisabled=false;
  private String extProperty="";

  public int doStartTag() throws JspException
  {
    Connection con=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    try {
      JspWriter out = pageContext.getOut();

      showParams();
      if (strSQL.indexOf(valueField) == -1 ||
          strSQL.indexOf(displayField) == -1) {
        System.out.println("传入的参数不正确！");
        throw new JspException();
      }
      out.println("<select class=box name=\"" + controlName + "\"" +
                  extProperty + ">");
      if (isNeedBlank == true) {
        out.println("<option value=\"-1\">&nbsp;</option>");
      }
      con = Database.getConnection();
      String strTmp = new String();
      strTmp = strSQL;
      //System.out.println(strTmp);
      ps = con.prepareStatement(strTmp);
      rs = ps.executeQuery();
      while (rs.next()) {
        if (rs.getString(valueField) != null &&
            rs.getString(valueField).equals(defaultValue)) {
          out.println("<option value=\"" + rs.getString(defaultValue) + "\" selected>" +
                      rs.getString(displayField) + "</option>");
        }
        else {
          out.println("<option value=\"" + rs.getString(valueField) + "\">" +
                      rs.getString(displayField) + "</option>");
        }
      }
      rs.close();
      rs = null;
      ps.close();
      ps = null;
      con.close();
      con = null;
      if (isNeedAll == true) {
        if (defaultValue != null && defaultValue.equals("0")) {
          out.println("<option value=\"0\" selected>全部</option>");
        }
        else {
          out.println("<option value=\"0\" >全部</option>");
        }
      }
      out.println("</select>");
    }
    catch (IOException ex) {
    }
    catch (JspException ex) {
    }
    catch (Exception ex) {
    }

    return EVAL_PAGE;
  }

  private void showParams()
  {
    System.out.println("controlName:"+controlName);
    System.out.println("strSQL:"+strSQL);
    System.out.println("displayField:"+displayField);
    System.out.println("valueField:"+valueField);
    System.out.println("defaultValue:"+defaultValue);
    System.out.println("isNeedAll:"+isNeedAll);
    System.out.println("isNeedBlank:"+isNeedBlank);
    System.out.println("isDisabled:"+isDisabled);
    System.out.println("extProperty:"+extProperty);
  }
  public void setControlName(String controlName) {
    this.controlName = controlName;
  }
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
  public void setDisplayField(String displayField) {
    this.displayField = displayField;
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
  public void setStrSQL(String strSQL) {
    this.strSQL = strSQL;
  }
  public void setValueField(String valueField) {
    this.valueField = valueField;
  }


}

/* Generated by Together */

package com.iss.itreasury.codingrule.dataentity;

import java.util.Collection;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/* Author:YanLiu */

public class CodingSectInfo extends ITreasuryBaseDataEntity 
{

    private long id = -1;
    private String name = "";
    private String desc = "";
    private long value_type = -1;
    private String sql_string ="";
    private String strparaType = "";
    private String[] paraType=null;
    private String value_return = "";
    private long format_type = -1;
    private String method_class = "";
    private String method_name = "";
    private String strparaValue = "";
    private String[] paraValue=null;
    private long statusID = -1;
    private long serialNo = -1;
    private long codingTypeID = -1;
    private long returnFormatID = -1;
    private Collection codingTypeDetail = null;
    
    public Collection getCodingTypeDetail() {
		return codingTypeDetail;
	}
	public void setCodingTypeDetail(Collection codingTypeDetail) {
		this.codingTypeDetail = codingTypeDetail;
	}
	public long getCodingTypeID() {
		return codingTypeID;
	}
	public void setCodingTypeID(long codingTypeID) {
		this.codingTypeID = codingTypeID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getReturnFormatID() {
		return returnFormatID;
	}
	public void setReturnFormatID(long returnFormatID) {
		this.returnFormatID = returnFormatID;
	}
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public CodingSectInfo() {
    }

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getFormat_type() {
		return format_type;
	}
	public void setFormat_type(long format_type) {
		this.format_type = format_type;
	}
	public String getMethod_class() {
		return method_class;
	}
	public void setMethod_class(String method_class) {
		this.method_class = method_class;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getSql_string() {
		return sql_string;
	}
	public void setSql_string(String sql_string) {
		this.sql_string = sql_string;
	}
	public String[] getParaType() {
		return paraType;
	}
	public void setParaType(String[] paraType) {
		this.paraType = paraType;
	}
	public String[] getParaValue() {
		return paraValue;
	}
	public void setParaValue(String[] paraValue) {
		this.paraValue = paraValue;
	}
	public String getStrparaType() {
		return strparaType;
	}
	public void setStrparaType(String strparaType) {
		this.strparaType = strparaType;
	}
	public String getStrparaValue() {
		return strparaValue;
	}
	public void setStrparaValue(String strparaValue) {
		this.strparaValue = strparaValue;
	}
	public String getValue_return() {
		return value_return;
	}
	public void setValue_return(String value_return) {
		this.value_return = value_return;
	}
	public long getValue_type() {
		return value_type;
	}
	public void setValue_type(long value_type) {
		this.value_type = value_type;
	}
}

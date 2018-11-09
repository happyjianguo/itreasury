/*
 * Title:        		iTreasury
 * Description:         通过继承的方法保留IException的所有功能并且增加在JDK1.4中出现的<P>
 * 						嵌套异常的处理(for project that deployed under version or JDK1.4)<P> 
 * Copyright (c) 2006 	Company: iSoftStone
 * @author             	yanliu 
 * @version
 * Date of Creation     2006-9-18
 */

package com.iss.itreasury.evoucher.setting.dataentity;

import java.util.Collection;

import com.iss.itreasury.evoucher.base.VoucherBaseDataEntity;

public class PrintRelationInfo extends VoucherBaseDataEntity
{
	private long officeID = -1;							//办事处
	private long currencyID = -1;						//币种
	private long transTypeID = -1;						//交易类型
	private long printTemplateID = -1;					//模版id
	private long printTypeID = -1;						//打印类型(全打、套打)
	private long printTime = -1;						//最多打印次数
	private long statusID = -1;							//状态标志位，标示该模版是否被关联到该交易类型
	private String PrintTemplateName = "";				//模版名
	private long serialID = -1;							//
	private long templateTypeID = -1;					//模版类型
	private long printSerialID = -1;					//
	private Collection relationWholeCollection = null;	//全打关联设置集合，元素为PrintRelationInfo
	private Collection relationCoverCollection = null;	//套打关联设置集合，元素为PrintRelationInfo
	
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public long getPrintTemplateID() {
		return printTemplateID;
	}
	public void setPrintTemplateID(long printTemplateID) {
		this.printTemplateID = printTemplateID;
	}
	public long getPrintTypeID() {
		return printTypeID;
	}
	public void setPrintTypeID(long printTemplateTypeID) {
		this.printTypeID = printTemplateTypeID;
	}
	public long getPrintTime() {
		return printTime;
	}
	public void setPrintTime(long printTime) {
		this.printTime = printTime;
	}
	public long getTransTypeID() {
		return transTypeID;
	}
	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public Collection getRelationCoverCollection() {
		return relationCoverCollection;
	}
	public void setRelationCoverCollection(Collection relationCoverCollection) {
		this.relationCoverCollection = relationCoverCollection;
	}
	public Collection getRelationWholeCollection() {
		return relationWholeCollection;
	}
	public void setRelationWholeCollection(Collection relationWholeCollection) {
		this.relationWholeCollection = relationWholeCollection;
	}
	public String getPrintTemplateName() {
		return PrintTemplateName;
	}
	public void setPrintTemplateName(String printTemplateName) {
		PrintTemplateName = printTemplateName;
	}
	public long getSerialID() {
		return serialID;
	}
	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}
	public long getTemplateTypeID() {
		return templateTypeID;
	}
	public void setTemplateTypeID(long templateType) {
		this.templateTypeID = templateType;
	}
	public long getPrintSerialID() {
		return printSerialID;
	}
	public void setPrintSerialID(long printSerialID) {
		this.printSerialID = printSerialID;
	}
	
}

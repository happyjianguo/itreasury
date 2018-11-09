/* Generated by Together */

package com.iss.itreasury.clientmanage.dataentity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;





/**
*客户基本信息dataEntity，对应与表Client_ClientInfo
* */
public  class ClientInfoForSwitch extends CimsBaseDataEntity {
	


	
	private String code_switch="客户编号";//客户编号 
	private String name_switch="姓名";//姓名  
	private String simpleName_switch="名字简称";//名字简称
	private String name2_switch="中文别名";//中文别名
	private String engName_switch="英文名";//英文名
	private String simpleEngName_switch="英文简称";//英文简称
	private String engName2_switch="英文别名";//英文别名
	private String clientBaseType_switch="客户基本类型  法人/自然人";//客户基本类型  法人/自然人
	private String systemIdentify_switch= "系统标识 正式客户/内部客户";//系统标识 正式客户/内部客户
	private String queryPassword_switch="查询密码";//查询密码
	private String customerManagerUserID_switch="所属的客户经理";//所属的客户经理
	private String country_switch="国籍";//国籍
	private String inputDate_switch="建立关系日期";//建立关系日期
	private String serviceLevel_switch="服务级别";//服务级别
	private String inputUserID_switch="录入人";//录入人
	private String inputTime_switch="录入时间";//录入时间
	private String modifyUserID_switch="修改人";//修改人
	private String modifyTime_switch="修改时间";//修改时间
	private String statusID_switch="状态";//状态 
	private String createModuleID_switch="属于模块";//属于模块
	private String Clientid_switch ="客户ID"; 
	private String count_switch = "总计";//总计
	private String sum_switch = "总和";//总和
	private String level_switch = "评级";//评级
	private String fact_switch = "实际金额";//实际金额
	private String levelId_switch = "级别ID";
	private String levelCode_switch = "级别CODE";
	
	public String customerManagerUserName_switch="";//所属的客户经理中文名称
//***********************************

	public String getClientBaseType_switch() {
		return clientBaseType_switch;
	}

	public void setClientBaseType_switch(String clientBaseType_switch) {
		this.clientBaseType_switch = clientBaseType_switch;
	}

	public String getClientid_switch() {
		return Clientid_switch;
	}

	public void setClientid_switch(String clientid_switch) {
		Clientid_switch = clientid_switch;
	}

	public String getCode_switch() {
		return code_switch;
	}

	public void setCode_switch(String code_switch) {
		this.code_switch = code_switch;
	}

	public String getCount_switch() {
		return count_switch;
	}

	public void setCount_switch(String count_switch) {
		this.count_switch = count_switch;
	}

	public String getCountry_switch() {
		return country_switch;
	}

	public void setCountry_switch(String country_switch) {
		this.country_switch = country_switch;
	}

	public String getCreateModuleID_switch() {
		return createModuleID_switch;
	}

	public void setCreateModuleID_switch(String createModuleID_switch) {
		this.createModuleID_switch = createModuleID_switch;
	}

	public String getCustomerManagerUserID_switch() {
		return customerManagerUserID_switch;
	}

	public void setCustomerManagerUserID_switch(String customerManagerUserID_switch) {
		this.customerManagerUserID_switch = customerManagerUserID_switch;
	}

	public String getCustomerManagerUserName_switch() {
		return customerManagerUserName_switch;
	}

	public void setCustomerManagerUserName_switch(
			String customerManagerUserName_switch) {
		this.customerManagerUserName_switch = customerManagerUserName_switch;
	}

	public String getEngName_switch() {
		return engName_switch;
	}

	public void setEngName_switch(String engName_switch) {
		this.engName_switch = engName_switch;
	}

	public String getEngName2_switch() {
		return engName2_switch;
	}

	public void setEngName2_switch(String engName2_switch) {
		this.engName2_switch = engName2_switch;
	}

	public String getFact_switch() {
		return fact_switch;
	}

	public void setFact_switch(String fact_switch) {
		this.fact_switch = fact_switch;
	}

	public String getInputDate_switch() {
		return inputDate_switch;
	}

	public void setInputDate_switch(String inputDate_switch) {
		this.inputDate_switch = inputDate_switch;
	}

	public String getInputTime_switch() {
		return inputTime_switch;
	}

	public void setInputTime_switch(String inputTime_switch) {
		this.inputTime_switch = inputTime_switch;
	}

	public String getInputUserID_switch() {
		return inputUserID_switch;
	}

	public void setInputUserID_switch(String inputUserID_switch) {
		this.inputUserID_switch = inputUserID_switch;
	}

	public String getLevel_switch() {
		return level_switch;
	}

	public void setLevel_switch(String level_switch) {
		this.level_switch = level_switch;
	}

	public String getLevelCode_switch() {
		return levelCode_switch;
	}

	public void setLevelCode_switch(String levelCode_switch) {
		this.levelCode_switch = levelCode_switch;
	}

	public String getLevelId_switch() {
		return levelId_switch;
	}

	public void setLevelId_switch(String levelId_switch) {
		this.levelId_switch = levelId_switch;
	}

	public String getModifyTime_switch() {
		return modifyTime_switch;
	}

	public void setModifyTime_switch(String modifyTime_switch) {
		this.modifyTime_switch = modifyTime_switch;
	}

	public String getModifyUserID_switch() {
		return modifyUserID_switch;
	}

	public void setModifyUserID_switch(String modifyUserID_switch) {
		this.modifyUserID_switch = modifyUserID_switch;
	}

	public String getName_switch() {
		return name_switch;
	}

	public void setName_switch(String name_switch) {
		this.name_switch = name_switch;
	}

	public String getName2_switch() {
		return name2_switch;
	}

	public void setName2_switch(String name2_switch) {
		this.name2_switch = name2_switch;
	}

	public String getQueryPassword_switch() {
		return queryPassword_switch;
	}

	public void setQueryPassword_switch(String queryPassword_switch) {
		this.queryPassword_switch = queryPassword_switch;
	}

	public String getServiceLevel_switch() {
		return serviceLevel_switch;
	}

	public void setServiceLevel_switch(String serviceLevel_switch) {
		this.serviceLevel_switch = serviceLevel_switch;
	}

	public String getSimpleEngName_switch() {
		return simpleEngName_switch;
	}

	public void setSimpleEngName_switch(String simpleEngName_switch) {
		this.simpleEngName_switch = simpleEngName_switch;
	}

	public String getSimpleName_switch() {
		return simpleName_switch;
	}

	public void setSimpleName_switch(String simpleName_switch) {
		this.simpleName_switch = simpleName_switch;
	}

	public String getStatusID_switch() {
		return statusID_switch;
	}

	public void setStatusID_switch(String statusID_switch) {
		this.statusID_switch = statusID_switch;
	}

	public String getSum_switch() {
		return sum_switch;
	}

	public void setSum_switch(String sum_switch) {
		this.sum_switch = sum_switch;
	}

	public String getSystemIdentify_switch() {
		return systemIdentify_switch;
	}

	public void setSystemIdentify_switch(String systemIdentify_switch) {
		this.systemIdentify_switch = systemIdentify_switch;
	}

	
   
}

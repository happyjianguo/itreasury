package com.iss.sysframe.pager.dataentity;

import java.util.List;
import java.util.Map;

public class PagerInfo
{
	private String sqlString = "";//sql语句。与resultInfo不能共存
	private ResultInfo resultInfo = null;//返回结果集方式调用。与sql不能共存
	private List depictList = null;//需要进行转换的字段在这里进行配置
	private String usertext = "";//用户自定义的文本
	private String userNoHtmlText = "";//用户自定义的文本(no html显示，用于excel)
	
	private Class extClazz = null;//对于集合的自定义处理类
	private String extMothodName = null;//对于集合的自定义处理方法
	private Map params = null;
	
	private Class extTotalClazz = null;//对于集合汇总的自定义处理类
	private String extTotalMothodName = null;//对于集合汇总的自定义处理方法
	private Map totalParams = null;
	private int rowShowNum = 6;//对于集合汇总的每行显示个数
	
	private List resultList=null;
	
	public void setExtensionMothod(Class clazz, String methodName,Map params)
	{
		this.extClazz = clazz;
		this.extMothodName = methodName;
		this.params = params;
	}
	
	public void setExtensionMothod(Class clazz, String methodName)
	{
		setExtensionMothod(clazz,methodName,null);
	}
	
	public void setTotalExtensionMothod(Class clazz, String methodName, Map params, int lRowShowNum)
	{
		this.extTotalClazz = clazz;
		this.extTotalMothodName = methodName;
		this.totalParams = params;
		this.rowShowNum = lRowShowNum;
	}
	
	public void setTotalExtensionMothod(Class clazz, String methodName, Map params)
	{
		setTotalExtensionMothod(clazz,methodName,params,rowShowNum);
	}
	
	public void setTotalExtensionMothod(Class clazz, String methodName, int lRowShowNum)
	{
		setTotalExtensionMothod(clazz,methodName,null,lRowShowNum);
	}
	
	public void setTotalExtensionMothod(Class clazz, String methodName)
	{
		setTotalExtensionMothod(clazz,methodName,null);
	}
	
	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public ResultInfo getResultInfo()
	{
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo)
	{
		this.resultInfo = resultInfo;
	}

	public String getUsertext() {
		return usertext;
	}

	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}

	public List getDepictList() {
		return depictList;
	}

	public void setDepictList(List depictList) {
		this.depictList = depictList;
	}

	public String getUserNoHtmlText() {
		return userNoHtmlText;
	}

	public void setUserNoHtmlText(String userNoHtmlText) {
		this.userNoHtmlText = userNoHtmlText;
	}

	public Class getExtClazz() {
		return extClazz;
	}

	public void setExtClazz(Class extClazz) {
		this.extClazz = extClazz;
	}

	public String getExtMothodName() {
		return extMothodName;
	}

	public void setExtMothodName(String extMothodName) {
		this.extMothodName = extMothodName;
	}

	public Map getParams() {
		return params;
	}

	public Class getExtTotalClazz() {
		return extTotalClazz;
	}

	public void setExtTotalClazz(Class extTotalClazz) {
		this.extTotalClazz = extTotalClazz;
	}

	public String getExtTotalMothodName() {
		return extTotalMothodName;
	}

	public void setExtTotalMothodName(String extTotalMothodName) {
		this.extTotalMothodName = extTotalMothodName;
	}

	public Map getTotalParams() {
		return totalParams;
	}

	public void setTotalParams(Map totalParams) {
		this.totalParams = totalParams;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getRowShowNum() {
		return rowShowNum;
	}

	public void setRowShowNum(int rowShowNum) {
		this.rowShowNum = rowShowNum;
	}

}

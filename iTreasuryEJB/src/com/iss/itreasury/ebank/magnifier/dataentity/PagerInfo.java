package com.iss.itreasury.ebank.magnifier.dataentity;

import java.util.List;

public class PagerInfo
{
	private String sqlString = "";//sql语句。与resultInfo不能共存
	private List depictList = null;//需要进行转换的字段在这里进行配置
	private String usertext = "";//用户自定义的文本
	private String userNoHtmlText = "";//用户自定义的文本(no html显示，用于excel)
	
	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
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

}

package com.iss.sysframe.jasperPrint.dataentity;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class JasperFlexigridInfo extends BaseDataEntity{
	String ids = "";
	String tdWidths = "";
	String titles = "";
	String elTypes="";
	String tableTitle="";//表头的名字
	long pageType = -1; //页面方向
	String pageSize = ""; //页面大小
	long pageTop = -1; //页面边距顶部
	long pageButtom = -1; //页面边距底部
	long pageLeft = -1;  //页面边距左对齐
	long pageRight = -1; //页面边距右对齐
	String pageChoose = ""; //打印内容
	
	String username="";//登陆人
	
	boolean tag = false;//是否打印票面元素信息

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public String getIds() {
		return ids;
	}

	public String getTdWidths() {
		return tdWidths;
	}

	public String getTitles() {
		return titles;
	}

	public long getPageType() {
		return pageType;
	}

	public String getPageSize() {
		return pageSize;
	}

	public long getPageTop() {
		return pageTop;
	}

	public long getPageButtom() {
		return pageButtom;
	}

	public long getPageLeft() {
		return pageLeft;
	}

	public long getPageRight() {
		return pageRight;
	}

	public String getPageChoose() {
		return pageChoose;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setTdWidths(String tdWidths) {
		this.tdWidths = tdWidths;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public void setPageType(long pageType) {
		this.pageType = pageType;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageTop(long pageTop) {
		this.pageTop = pageTop;
	}

	public void setPageButtom(long pageButtom) {
		this.pageButtom = pageButtom;
	}

	public void setPageLeft(long pageLeft) {
		this.pageLeft = pageLeft;
	}

	public void setPageRight(long pageRight) {
		this.pageRight = pageRight;
	}

	public void setPageChoose(String pageChoose) {
		this.pageChoose = pageChoose;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isTag() {
		return tag;
	}

	public void setTag(boolean tag) {
		this.tag = tag;
	}

	public String getElTypes() {
		return elTypes;
	}

	public void setElTypes(String elTypes) {
		this.elTypes = elTypes;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	
}

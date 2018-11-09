package com.iss.itreasury.system.dataentity;

import com.iss.itreasury.util.*;
/*
 * 
 * @author yanliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class QueryClientSAInfo
{
	private String StartClientCode = "";//客户号-从
	private String EndClientCode = "";//客户号-到
	private String ClientName = "";//客户名称
	private long OfficeID = -1;//办事处ID
	//
	private long TotalPages = -1;
	private long LinesOfPage = Constant.PageControl.CODE_PAGELINECOUNT;
	private String Sort = " asc ";
	private long CurrentPage = 1;
	private String Order = "";
	private Long status ;
	private Long inputUserID;
	private String startDate = "";
	private String endDate = "";
	private long isAdmin = -1;
	private long statusID = -1;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getClientName() {
		return ClientName;
	}

	/**
	 * @return
	 */
	public long getCurrentPage() {
		return CurrentPage;
	}

	/**
	 * @return
	 */
	public String getEndClientCode() {
		return EndClientCode;
	}

	/**
	 * @return
	 */
	public long getLinesOfPage() {
		return LinesOfPage;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * @return
	 */
	public String getOrder() {
		return Order;
	}

	/**
	 * @return
	 */
	public String getSort() {
		return Sort;
	}

	/**
	 * @return
	 */
	public String getStartClientCode() {
		return StartClientCode;
	}

	/**
	 * @return
	 */
	public long getTotalPages() {
		return TotalPages;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string) {
		ClientName = string;
	}

	/**
	 * @param l
	 */
	public void setCurrentPage(long l) {
		CurrentPage = l;
	}

	/**
	 * @param string
	 */
	public void setEndClientCode(String string) {
		EndClientCode = string;
	}

	/**
	 * @param l
	 */
	public void setLinesOfPage(long l) {
		LinesOfPage = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setOrder(String string) {
		Order = string;
	}

	/**
	 * @param string
	 */
	public void setSort(String string) {
		Sort = string;
	}

	/**
	 * @param string
	 */
	public void setStartClientCode(String string) {
		StartClientCode = string;
	}

	/**
	 * @param l
	 */
	public void setTotalPages(long l) {
		TotalPages = l;
	}

	public Long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(Long inputUserID) {
		this.inputUserID = inputUserID;
	}

	public long getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(long isAdmin) {
		this.isAdmin = isAdmin;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

}
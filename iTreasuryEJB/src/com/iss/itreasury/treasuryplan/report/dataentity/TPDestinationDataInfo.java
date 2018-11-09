/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-20
 */
package com.iss.itreasury.treasuryplan.report.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanBaseDataEntity;

/**
 * @author yehuang
 *
* 数据转换目标数据表数据Info类
 * 子类包括：
 * TPForecastDataInfo
 * Trea_TPActualDataInfo 
 */
public abstract class TPDestinationDataInfo extends TreasuryPlanBaseDataEntity {
	private long id = -1;//标识
	private long officeID = -1;//办事处
	private long currencyID = -1;//币种
	private Timestamp executeDate = null;//预测执行日期
	private Timestamp transactionDate = null;//被预测的交易数据日期
	private long lineID = -1;//行项目ID
	private String lineNo = null;//行项目编号
	private String lineName = null;//行项目名称
	private long lineLevel = -1;//行项目级次
	private long parentLineID = -1;//上级行项目ID
	private long isLeaf = -1;//是否叶子
	private String authorizedDepartment = null;//所属部门
	private String authorizedUser = null;//所属用户
	
	private long isTransformation = -1;//是否为转换程序所调用
	
	private long isNeedSum = 1 ; //是否参与公式计算

	/**
	 * @return Returns the isNeedSum.
	 */
	public long getIsNeedSum() {
		return isNeedSum;
	}
	/**
	 * @param isNeedSum The isNeedSum to set.
	 */
	public void setIsNeedSum(long isNeedSum) {
		this.isNeedSum = isNeedSum;
		putUsedField("isNeedSum",isNeedSum );
	}
	/**
	 * @return Returns the isTransformation.
	 */
	public long getIsTransformation() {
		return isTransformation;
	}
	/**
	 * @param isTransformation The isTransformation to set.
	 */
	public void setIsTransformation(long isTransformation) {
		this.isTransformation = isTransformation;
	}
	/**
	 * 设置目标数据，在派生类实现
	 * */
	public abstract void putDestinationAmount(double amount);
	
	
	public TPDestinationDataInfo(){
	}
	
	public TPDestinationDataInfo(TPTemplateInfo templateInfo){
		setOfficeID(templateInfo.getOfficeID());
		setCurrencyID(templateInfo.getCurrencyID());
		setLineID(templateInfo.getId());
		setLineNo(templateInfo.getLineNo());
		setLineName(templateInfo.getLineName());
		setLineLevel(templateInfo.getLineLevel());
		setParentLineID(templateInfo.getParentLineID());
		setIsLeaf(templateInfo.getIsLeaf());
		setIsNeedSum(templateInfo.getIsNeedSum());
		System.out.println("----templateInfo.getAuthorizedDepartment"+templateInfo.getAuthorizedDepartment());		
		if(templateInfo.getAuthorizedDepartment() != null){
			String str = templateInfo.getAuthorizedDepartment().trim();
			if(str.length() != 0)
				setAuthorizedDepartment(templateInfo.getAuthorizedDepartment());
		}
		
		if(templateInfo.getAuthorizedUser() != null){
			String str = templateInfo.getAuthorizedUser().trim();
			if(str.length() != 0)			
			setAuthorizedUser(templateInfo.getAuthorizedUser()); 
		}
	}		

	/**
	 * @return Returns the authorizedDepartment.
	 */
	public String getAuthorizedDepartment() {
		return authorizedDepartment;
	}
	/**
	 * @param authorizedDepartment The authorizedDepartment to set.
	 */
	public void setAuthorizedDepartment(String authorizedDepartment) {
		putUsedField("authorizedDepartment", authorizedDepartment);		
		this.authorizedDepartment = authorizedDepartment;
	}
	/**
	 * @return Returns the authorizedUser.
	 */
	public String getAuthorizedUser() {
		return authorizedUser;
	}
	/**
	 * @param authorizedUser The authorizedUser to set.
	 */
	public void setAuthorizedUser(String authorizedUser) {
		putUsedField("authorizedUser", authorizedUser);
		this.authorizedUser = authorizedUser;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}

	/**
	 * @return Returns the isLeaf.
	 */
	public long getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf The isLeaf to set.
	 */
	public void setIsLeaf(long isLeaf) {
		putUsedField("isLeaf", isLeaf);
		this.isLeaf = isLeaf;
	}
	/**
	 * @return Returns the lineID.
	 */
	public long getLineID() {
		return lineID;
	}
	/**
	 * @param lineID The lineID to set.
	 */
	public void setLineID(long lineID) {
		putUsedField("lineID", lineID);
		this.lineID = lineID;
	}
	/**
	 * @return Returns the lineLevel.
	 */
	public long getLineLevel() {
		return lineLevel;
	}
	/**
	 * @param lineLevel The lineLevel to set.
	 */
	public void setLineLevel(long lineLevel) {
		putUsedField("lineLevel", lineLevel);
		this.lineLevel = lineLevel;
	}
	/**
	 * @return Returns the lineName.
	 */
	public String getLineName() {
		return lineName;
	}
	/**
	 * @param lineName The lineName to set.
	 */
	public void setLineName(String lineName) {
		putUsedField("lineName", lineName);
		this.lineName = lineName;
	}
	/**
	 * @return Returns the lineNo.
	 */
	public String getLineNo() {
		return lineNo;
	}
	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(String lineNo) {
		putUsedField("lineNo", lineNo);
		this.lineNo = lineNo;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
	/**
	 * @return Returns the parentLineID.
	 */
	public long getParentLineID() {
		return parentLineID;
	}
	/**
	 * @param parentLineID The parentLineID to set.
	 */
	public void setParentLineID(long parentLineID) {
		putUsedField("parentLineID", parentLineID);
		this.parentLineID = parentLineID;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		putUsedField("transactionDate", transactionDate);
		this.transactionDate = transactionDate;
	}


	/**
	 * 根据正在执行的抽取操作的类别获取应该使用的DAO的实例
	 * */
	public static TPDestinationDataInfo getDestinationDataInstance(long extractingTypeID,TPTemplateInfo templateInfo){
		TPDestinationDataInfo destinationDataInfo = null;
		if(extractingTypeID == AbstractTransformer.Extracting_Type_Actual){
			if(templateInfo == null)
				destinationDataInfo = new TPActualDataInfo();
			else
				destinationDataInfo = new TPActualDataInfo(templateInfo);
		}else if(extractingTypeID == AbstractTransformer.Extracting_Type_Forecast){
			if(templateInfo == null)
				destinationDataInfo = new TPForecastDataInfo();
			else
				destinationDataInfo = new TPForecastDataInfo(templateInfo);
		}else{
		}
		return destinationDataInfo;
	}
	
	public abstract double retrieveAmount();

}

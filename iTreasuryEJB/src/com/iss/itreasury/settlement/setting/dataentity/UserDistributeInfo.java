/**
 * 
 */
package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author zyzhu
 * 
 */
public class UserDistributeInfo extends SettlementBaseDataEntity {

	private long id = -1;
	private long superiorUserID = -1;   //�ϼ��ͻ�id
	private String superiorUserNo = "";  //�ϼ��ͻ����
	private String superiorUserName = ""; //�ϼ��ͻ�����
	private long juniorUserID = -1; //�¼��ͻ�id
	private String juniorUserNo = ""; //�¼��ͻ����
	private String juniorUserName = ""; //�¼��ͻ�����
	private long directSuperiorUserID = -1; //ֱ���ϼ��ͻ�id
	private String directSuperiorUserNo = ""; //ֱ���ϼ��ͻ����
	private String directSuperiorUserName = ""; //ֱ���ϼ��ͻ�����
	private long accountID = -1; //�˻�id
	private String accountNo = ""; //�˻����
	private long accountTypeID = -1; //�˻�����id
	private String accountType =""; //�˻�����
	private String accountName = ""; //�˻�����
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public long getSuperiorUserID() {
		return superiorUserID;
	}
	public void setSuperiorUserID(long superiorUserID) {
		this.superiorUserID = superiorUserID;
		putUsedField("superiorUserID", superiorUserID);
	}
	public String getSuperiorUserNo() {
		return superiorUserNo;
	}
	public void setSuperiorUserNo(String superiorUserNo) {
		this.superiorUserNo = superiorUserNo;
		putUsedField("superiorUserNo", superiorUserNo);
	}
	public String getSuperiorUserName() {
		return superiorUserName;
	}
	public void setSuperiorUserName(String superiorUserName) {
		this.superiorUserName = superiorUserName;
		putUsedField("superiorUserName", superiorUserName);
	}
	public long getJuniorUserID() {
		return juniorUserID;
	}
	public void setJuniorUserID(long juniorUserID) {
		this.juniorUserID = juniorUserID;
		putUsedField("juniorUserID", juniorUserID);
	}
	public String getJuniorUserNo() {
		return juniorUserNo;
	}
	public void setJuniorUserNo(String juniorUserNo) {
		this.juniorUserNo = juniorUserNo;
		putUsedField("juniorUserNo", juniorUserNo);
	}
	public String getJuniorUserName() {
		return juniorUserName;
	}
	public void setJuniorUserName(String juniorUserName) {
		this.juniorUserName = juniorUserName;
		putUsedField("juniorUserName", juniorUserName);
	}
	public long getDirectSuperiorUserID() {
		return directSuperiorUserID;
	}
	public void setDirectSuperiorUserID(long directSuperiorUserID) {
		this.directSuperiorUserID = directSuperiorUserID;
		putUsedField("directSuperiorUserID", directSuperiorUserID);
	}
	public String getDirectSuperiorUserNo() {
		return directSuperiorUserNo;
	}
	public void setDirectSuperiorUserNo(String directSuperiorUserNo) {
		this.directSuperiorUserNo = directSuperiorUserNo;
		putUsedField("directSuperiorUserNo", directSuperiorUserNo);
	}
	public String getDirectSuperiorUserName() {
		return directSuperiorUserName;
	}
	public void setDirectSuperiorUserName(String directSuperiorUserName) {
		this.directSuperiorUserName = directSuperiorUserName;
		putUsedField("directSuperiorUserName", directSuperiorUserName);
	}
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
		putUsedField("accountID", accountID);
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
		putUsedField("accountNo", accountNo);
	}
	public long getAccountTypeID() {
		return accountTypeID;
	}
	public void setAccountTypeID(long accountTypeID) {
		this.accountTypeID = accountTypeID;
		putUsedField("accountTypeID", accountTypeID);
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
		putUsedField("accountName", accountName);
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
		putUsedField("accountType", accountType);
	}
	
	

}

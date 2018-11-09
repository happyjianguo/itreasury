/**
 * @author Kloud
 * @类名 GLVouchInfo 会计凭证信息
 * @CreateDate 2007-08-17
 * 为南航特定开发 因手动导出与Oracle财务系统对应的会计凭证
 */
package com.iss.itreasury.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class GLVouchInfo implements Serializable {

	// 对应表 sett_glentry
	private long lOfficeID = -1;

	private long lCurrencyID = -1;

	private String strSubjectCode = ""; // 科目编号

	private String strTransNO = ""; // 交易编号

	private long lTransactionTypeID = -1; // 交易类型

	private long lTransDirection = -1; // 交易方向

	private double dbMamount = 0.0; // 交易发生额

	private Timestamp dtExecute = null; // 执行日期

	private Timestamp dtInterestStart = null; // 起息日期

	private String strAbstract = ""; // 摘要

	private String strMulticode = ""; // 多维码

	private long lInputUserID = -1; // 录入人

	private long lCheckUserID = -1; // 复核人

	private long lStatusID = -1;

	private long lGroup = 0;

	private long lType = 0;

	private long lPostStatusID = -1;

	// 对应表 sett_glsubjectdefinition
	private String strSegCode1 = "";

	private String strSegCode2 = "";

	private String strSegCode3 = "";

	private String strSegCode4 = "";

	private String strSegCode5 = "";

	private String strSegCode6 = "";

	private String strSegCode7 = "";

	private String strSegName1 = "";

	private String strSegName2 = "";

	private String strSegName3 = "";

	private String strSegName4 = "";

	private String strSegName5 = "";

	private String strSegName6 = "";

	private String strSegName7 = "";

	private long lSubjectType = -1; // 科目属性

	private long lIsLeaf = -1; // 是否末级科目

	private long lIsRoot = -1; // 是否总帐科目

	private long lParentSubjectID = -1; // 上级科目代码ID

	private long lBalanceDirection = -1; // 控制余额方向

	private long lAmountDirection = 9; // 控制发生额方向

	private long lStatus = -1; // 状态

	private long lLedertype = -1; // 帐类

	private long lSecurityLevel = -1; // 安全级别

	private long lUseScope = -1; // 使用范围

	private long lFlag = -1; // 保留

	private Timestamp dtValidDate = null; // 生效日期
	
	

	public GLVouchInfo(long officeID, long currencyID, String strSubjectCode, String strTransNO, long transactionTypeID, long transDirection, double dbMamount, Timestamp dtExecute, Timestamp dtInterestStart, String strAbstract, String strMulticode, long inputUserID, long checkUserID, long statusID, long group, long type, long postStatusID, String strSegCode1, String strSegCode2, String strSegCode3, String strSegCode4, String strSegCode5, String strSegCode6, String strSegCode7, String strSegName1, String strSegName2, String strSegName3, String strSegName4, String strSegName5, String strSegName6, String strSegName7, long subjectType, long isLeaf, long isRoot, long parentSubjectID, long balanceDirection, long amountDirection, long status, long ledertype, long securityLevel, long useScope, long flag, Timestamp dtValidDate) {
		super();
		lOfficeID = officeID;
		lCurrencyID = currencyID;
		this.strSubjectCode = strSubjectCode;
		this.strTransNO = strTransNO;
		lTransactionTypeID = transactionTypeID;
		lTransDirection = transDirection;
		this.dbMamount = dbMamount;
		this.dtExecute = dtExecute;
		this.dtInterestStart = dtInterestStart;
		this.strAbstract = strAbstract;
		this.strMulticode = strMulticode;
		lInputUserID = inputUserID;
		lCheckUserID = checkUserID;
		lStatusID = statusID;
		lGroup = group;
		lType = type;
		lPostStatusID = postStatusID;
		this.strSegCode1 = strSegCode1;
		this.strSegCode2 = strSegCode2;
		this.strSegCode3 = strSegCode3;
		this.strSegCode4 = strSegCode4;
		this.strSegCode5 = strSegCode5;
		this.strSegCode6 = strSegCode6;
		this.strSegCode7 = strSegCode7;
		this.strSegName1 = strSegName1;
		this.strSegName2 = strSegName2;
		this.strSegName3 = strSegName3;
		this.strSegName4 = strSegName4;
		this.strSegName5 = strSegName5;
		this.strSegName6 = strSegName6;
		this.strSegName7 = strSegName7;
		lSubjectType = subjectType;
		lIsLeaf = isLeaf;
		lIsRoot = isRoot;
		lParentSubjectID = parentSubjectID;
		lBalanceDirection = balanceDirection;
		lAmountDirection = amountDirection;
		lStatus = status;
		lLedertype = ledertype;
		lSecurityLevel = securityLevel;
		lUseScope = useScope;
		lFlag = flag;
		this.dtValidDate = dtValidDate;
	}

	public GLVouchInfo() {
		
	}

	public double getDbMamount() {
		return dbMamount;
	}

	public void setDbMamount(double dbMamount) {
		this.dbMamount = dbMamount;
	}

	public Timestamp getDtExecute() {
		return dtExecute;
	}

	public void setDtExecute(Timestamp dtExecute) {
		this.dtExecute = dtExecute;
	}

	public Timestamp getDtInterestStart() {
		return dtInterestStart;
	}

	public void setDtInterestStart(Timestamp dtInterestStart) {
		this.dtInterestStart = dtInterestStart;
	}

	public Timestamp getDtValidDate() {
		return dtValidDate;
	}

	public void setDtValidDate(Timestamp dtValidDate) {
		this.dtValidDate = dtValidDate;
	}

	public long getLAmountDirection() {
		return lAmountDirection;
	}

	public void setLAmountDirection(long amountDirection) {
		lAmountDirection = amountDirection;
	}

	public long getLBalanceDirection() {
		return lBalanceDirection;
	}

	public void setLBalanceDirection(long balanceDirection) {
		lBalanceDirection = balanceDirection;
	}

	public long getLCheckUserID() {
		return lCheckUserID;
	}

	public void setLCheckUserID(long checkUserID) {
		lCheckUserID = checkUserID;
	}

	public long getLCurrencyID() {
		return lCurrencyID;
	}

	public void setLCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}

	public long getLFlag() {
		return lFlag;
	}

	public void setLFlag(long flag) {
		lFlag = flag;
	}

	public long getLGroup() {
		return lGroup;
	}

	public void setLGroup(long group) {
		lGroup = group;
	}

	public long getLInputUserID() {
		return lInputUserID;
	}

	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}

	public long getLIsLeaf() {
		return lIsLeaf;
	}

	public void setLIsLeaf(long isLeaf) {
		lIsLeaf = isLeaf;
	}

	public long getLIsRoot() {
		return lIsRoot;
	}

	public void setLIsRoot(long isRoot) {
		lIsRoot = isRoot;
	}

	public long getLLedertype() {
		return lLedertype;
	}

	public void setLLedertype(long ledertype) {
		lLedertype = ledertype;
	}

	public long getLOfficeID() {
		return lOfficeID;
	}

	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
	}

	public long getLParentSubjectID() {
		return lParentSubjectID;
	}

	public void setLParentSubjectID(long parentSubjectID) {
		lParentSubjectID = parentSubjectID;
	}

	public long getLPostStatusID() {
		return lPostStatusID;
	}

	public void setLPostStatusID(long postStatusID) {
		lPostStatusID = postStatusID;
	}

	public long getLSecurityLevel() {
		return lSecurityLevel;
	}

	public void setLSecurityLevel(long securityLevel) {
		lSecurityLevel = securityLevel;
	}

	public long getLStatus() {
		return lStatus;
	}

	public void setLStatus(long status) {
		lStatus = status;
	}

	public long getLStatusID() {
		return lStatusID;
	}

	public void setLStatusID(long statusID) {
		lStatusID = statusID;
	}

	public long getLSubjectType() {
		return lSubjectType;
	}

	public void setLSubjectType(long subjectType) {
		lSubjectType = subjectType;
	}

	public long getLTransactionTypeID() {
		return lTransactionTypeID;
	}

	public void setLTransactionTypeID(long transactionTypeID) {
		lTransactionTypeID = transactionTypeID;
	}

	public long getLTransDirection() {
		return lTransDirection;
	}

	public void setLTransDirection(long transDirection) {
		lTransDirection = transDirection;
	}

	public long getLType() {
		return lType;
	}

	public void setLType(long type) {
		lType = type;
	}

	public long getLUseScope() {
		return lUseScope;
	}

	public void setLUseScope(long useScope) {
		lUseScope = useScope;
	}

	public String getStrAbstract() {
		return strAbstract;
	}

	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}

	public String getStrMulticode() {
		return strMulticode;
	}

	public void setStrMulticode(String strMulticode) {
		this.strMulticode = strMulticode;
	}

	public String getStrSegCode1() {
		return strSegCode1;
	}

	public void setStrSegCode1(String strSegCode1) {
		this.strSegCode1 = strSegCode1;
	}

	public String getStrSegCode2() {
		return strSegCode2;
	}

	public void setStrSegCode2(String strSegCode2) {
		this.strSegCode2 = strSegCode2;
	}

	public String getStrSegCode3() {
		return strSegCode3;
	}

	public void setStrSegCode3(String strSegCode3) {
		this.strSegCode3 = strSegCode3;
	}

	public String getStrSegCode4() {
		return strSegCode4;
	}

	public void setStrSegCode4(String strSegCode4) {
		this.strSegCode4 = strSegCode4;
	}

	public String getStrSegCode5() {
		return strSegCode5;
	}

	public void setStrSegCode5(String strSegCode5) {
		this.strSegCode5 = strSegCode5;
	}

	public String getStrSegCode6() {
		return strSegCode6;
	}

	public void setStrSegCode6(String strSegCode6) {
		this.strSegCode6 = strSegCode6;
	}

	public String getStrSegCode7() {
		return strSegCode7;
	}

	public void setStrSegCode7(String strSegCode7) {
		this.strSegCode7 = strSegCode7;
	}

	public String getStrSegName1() {
		return strSegName1;
	}

	public void setStrSegName1(String strSegName1) {
		this.strSegName1 = strSegName1;
	}

	public String getStrSegName2() {
		return strSegName2;
	}

	public void setStrSegName2(String strSegName2) {
		this.strSegName2 = strSegName2;
	}

	public String getStrSegName3() {
		return strSegName3;
	}

	public void setStrSegName3(String strSegName3) {
		this.strSegName3 = strSegName3;
	}

	public String getStrSegName4() {
		return strSegName4;
	}

	public void setStrSegName4(String strSegName4) {
		this.strSegName4 = strSegName4;
	}

	public String getStrSegName5() {
		return strSegName5;
	}

	public void setStrSegName5(String strSegName5) {
		this.strSegName5 = strSegName5;
	}

	public String getStrSegName6() {
		return strSegName6;
	}

	public void setStrSegName6(String strSegName6) {
		this.strSegName6 = strSegName6;
	}

	public String getStrSegName7() {
		return strSegName7;
	}

	public void setStrSegName7(String strSegName7) {
		this.strSegName7 = strSegName7;
	}

	public String getStrSubjectCode() {
		return strSubjectCode;
	}

	public void setStrSubjectCode(String strSubjectCode) {
		this.strSubjectCode = strSubjectCode;
	}

	public String getStrTransNO() {
		return strTransNO;
	}

	public void setStrTransNO(String strTransNO) {
		this.strTransNO = strTransNO;
	}
}

/*
 * queryFreeInfo.java
 *
 * Created on 2002年4月9日, 下午4:39
 */

package com.iss.itreasury.loan.query.dataentity;

import java.sql.*;

/**
 *
 * @author  yzhang
 * @version 
 */
public class QuestFreeInfo implements java.io.Serializable {

	/** Creates new queryFreeInfo */
	public QuestFreeInfo() {
		super();
	}

	//免还申请标示
	public long lFreeApplyID;                             
    
	//序列号 
	public String strSerialNo;

	//贷款种类
	public String strLoanType;
    
	//合同号 
	public String strContractNo;
    
	//借款单位
	public String strClient;
    
	//委托单位
	public String strConsignClient;
    
	//贷款金额
	public double dAmount;
    
	//起始贷款日期
	public Timestamp tsDateFrom;
    
	//截至贷款日期
	public Timestamp tsDateTo;
    
	//免还金额
	public double dFreeAmount;
    
	//免还日期
	public Timestamp tsFreeDate;
    
	//免还状态
	public long lStatus;
    
	//业务负责人 
	public String sUserName;
    
	//总页数
	public long lAllPage;
    
	//总纪录数
	public long lAllRecord;
    




    
	//总贷款金额
	public double dAllAmount;
	//总免还金额
	public double dAllFreeAmount;
	public long lType12 = -1; // 12种贷款类型
}
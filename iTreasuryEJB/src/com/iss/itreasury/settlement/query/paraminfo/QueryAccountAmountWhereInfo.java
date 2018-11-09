/*
 * Created on 2003-12-08
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

/**
 * @author kewen hu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountAmountWhereInfo extends QueryAccountWhereInfo {
    /**
     * 构造函数
     * @param  nothing
     * @return nothing
     */
    public QueryAccountAmountWhereInfo() {
        super();
    }
    // 查询条件
    private long AccountTypeID1 = 0;       // 账户类型ID1
    private long AccountTypeID2 = 0;       // 账户类型ID2
    private long IsCheckedActive = -1;     // 动户查询
    private long IsCheckedType = -1;       // 账户类型1、2(判断)
    /**
     * Returns the accountTypeID1.
     * @return long
     */
    public long getAccountTypeID1() {
        return AccountTypeID1;
    }

    /**
     * Returns the accountTypeID2.
     * @return long
     */
    public long getAccountTypeID2() {
        return AccountTypeID2;
    }

    /**
     * Returns the isCheckedActive.
     * @return long
     */
    public long getIsCheckedActive() {
        return IsCheckedActive;
    }

    /**
     * Returns the isCheckedType.
     * @return long
     */
    public long getIsCheckedType() {
        return IsCheckedType;
    }

    /**
     * Sets the accountTypeID1.
     * @param accountTypeID1 The accountTypeID1 to set
     */
    public void setAccountTypeID1(long accountTypeID1) {
        AccountTypeID1 = accountTypeID1;
    }

    /**
     * Sets the accountTypeID2.
     * @param accountTypeID2 The accountTypeID2 to set
     */
    public void setAccountTypeID2(long accountTypeID2) {
        AccountTypeID2 = accountTypeID2;
    }

    /**
     * Sets the isCheckedActive.
     * @param isCheckedActive The isCheckedActive to set
     */
    public void setIsCheckedActive(long isCheckedActive) {
        IsCheckedActive = isCheckedActive;
    }

    /**
     * Sets the isCheckedType.
     * @param isCheckedType The isCheckedType to set
     */
    public void setIsCheckedType(long isCheckedType) {
        IsCheckedType = isCheckedType;
    }
}
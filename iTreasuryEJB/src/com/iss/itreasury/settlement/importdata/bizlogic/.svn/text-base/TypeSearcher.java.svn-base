/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.importdata.bizlogic;

import java.util.Vector;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yinghu 此类用于设置类型匹配表查询正确的交易类型 TODO To change the template for this
 *         generated type comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
public class TypeSearcher {
    public long searchType(String creditNo, String debitNo, String recordType) {
        long sign = -1;
        if (debitNo.substring(0, 2).equalsIgnoreCase("VB")) {
            if (recordType.equalsIgnoreCase("HISTORY"))
                sign = searchType(Long.parseLong(creditNo.substring(8, 12)),
                        Long.parseLong(debitNo.substring(8, 12)),
                        setVMatcherforHistory());
            if (recordType.equalsIgnoreCase("CURRENT"))
                sign = searchType(Long.parseLong(creditNo.substring(8, 12)),
                        Long.parseLong(debitNo.substring(8, 12)),
                        setVMatcherforCurrent());
        } else
            sign = SETTConstant.TransactionType.BANKPAY;
        sign = specialType(creditNo, debitNo, sign);

        return sign;
    }

    /**
     * 设置当天数据账号匹配表
     *  
     */
    private Vector setVMatcherforCurrent() {
        Vector vMatcher = new Vector();
        //账号匹配表赋值
        long[] a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
    /*    a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2124;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2125;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
    /*    a1 = new long[3];
        a1[0] = 2124;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 2125;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1050;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5015;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1025;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1025;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
  /*      a1 = new long[3];
        a1[0] = 5212;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
  /*      a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
/**
 * [2005-11-01日:经探讨委存账户(2122)的交易不导入]
 * 确认人:yifan、jingxu、zhiqianghu
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
*/
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5090;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5230;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1025;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1050;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2020;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2020;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
/**
 * [2005-11-01日:经探讨委存账户(2122)的交易不导入]
 * 确认人:yifan、jingxu、zhiqianghu
        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);

        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);

        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
*/
    /*    a1 = new long[3];
        a1[0] = 2124;
        a1[1] = 2124;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 5212;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5090;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
/**
 * [2005-11-01日:经探讨委存账户(2122)的交易不导入]
 * 确认人:yifan、jingxu、zhiqianghu
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
*/
        a1 = new long[3];
        a1[0] = 5230;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
 /*       a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
 /*       a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);*/
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 1025;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 5011;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5016;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        return vMatcher;
    }

    /**
     * 设置历史数据账号匹配表
     */
    private Vector setVMatcherforHistory() {
        Vector vMatcher = new Vector();

        //账号匹配表赋值
        long[] a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2124;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2125;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2124;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2125;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1050;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5015;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1025;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1025;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5212;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5016;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5011;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5011;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 1123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 1123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.BANKRECEIVE;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5090;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1020;
        a1[1] = 5230;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1025;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1050;
        a1[1] = 5011;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1050;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1122;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1123;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 1123;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2020;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2020;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.BANKPAY;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2122;
        a1[1] = 5016;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 1122;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2124;
        a1[1] = 2124;
        a1[2] = SETTConstant.TransactionType.INTERNALVIREMENT;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 5212;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5016;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5090;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2122;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5230;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5013;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 2123;
        a1[1] = 5211;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 1050;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5011;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 1025;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5013;
        a1[1] = 5011;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 1020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5015;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5016;
        a1[1] = 2123;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        a1 = new long[3];
        a1[0] = 5211;
        a1[1] = 2020;
        a1[2] = SETTConstant.TransactionType.GENERALLEDGER;
        vMatcher.addElement(a1);
        return vMatcher;
    }

    /**
     * 查表，找出业务类型
     * 
     * @return
     */
    private long searchType(long i1, long i2, Vector vMatcher) {
        long key = -1;
        long[] l_temp = new long[3];

        for (int j = 0; j < vMatcher.size(); j++) {
            l_temp = (long[]) vMatcher.elementAt(j);
            if (i1 == l_temp[0] && i2 == l_temp[1]) {
                key = l_temp[2];
                return key;
            }
        }
        return key;
    }

    /**
     * 特殊处理的，类型
     * 
     * @author yinghu
     * 
     * TODO To change the template for this generated type comment go to Window -
     * Preferences - Java - Code Style - Code Templates
     */
    private long specialType(String creditNo, String debitNo, long sign) {
        //特殊处理
        //账户号：VB1100132124001735,账户名称：工行二级户存款, 导入总账类业务
        //账户号：VB1100132122000030,账户名称：已贷委托存款, 导入总账类业务[2005-11-01日:经探讨委存账户(2122)的交易不导入 确认人:yifan、jingxu、zhiqianghu]
        //账户号：VB1100132125000403,账户名称：贵州工行二级户存款, 导入总账类业务
        if (creditNo.equalsIgnoreCase("VB1100132124001735")
            || debitNo.equalsIgnoreCase("VB1100132124001735")
            || creditNo.equalsIgnoreCase("VB1100132125000403")
            || debitNo.equalsIgnoreCase("VB1100132125000403")
            //|| creditNo.equalsIgnoreCase("VB1100132122000030")
            //|| debitNo.equalsIgnoreCase("VB1100132122000030")
            )
            sign = SETTConstant.TransactionType.GENERALLEDGER;
/**
 * @author zqhu
 * 2005-10-26于航天科工
        //账户号：VB1100131020001005,账户名称：通知业务, 不导入
        //账户号：VB1100131020001720,账户名称：通知业务, 不导入
        if (creditNo.equalsIgnoreCase("VB1100131020001005")
                || debitNo.equalsIgnoreCase("VB1100131020001005")
                || creditNo.equalsIgnoreCase("VB1100131020001720")
                || debitNo.equalsIgnoreCase("VB1100131020001720"))
            sign = -1; //记录未导入的数据
*/
        //借方账户号：属于开户行--sett_branch或者
        //贷方账户号：属于开户行--sett_branch或者
        if (sign == SETTConstant.TransactionType.GENERALLEDGER) {
	        if (NameRef.getBankAccountIdByEnterpriseName(creditNo)>-1 ||
	            NameRef.getBankAccountIdByEnterpriseName(debitNo)>-1) {
	            sign = SETTConstant.TransactionType.SPECIALOPERATION;
	        }
        }
        return sign;
    }
    
    public static void main(String[] args){
    }

}
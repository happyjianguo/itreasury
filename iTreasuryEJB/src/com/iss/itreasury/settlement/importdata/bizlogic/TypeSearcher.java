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
 * @author yinghu ����������������ƥ����ѯ��ȷ�Ľ������� TODO To change the template for this
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
     * ���õ��������˺�ƥ���
     *  
     */
    private Vector setVMatcherforCurrent() {
        Vector vMatcher = new Vector();
        //�˺�ƥ���ֵ
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
 * [2005-11-01��:��̽��ί���˻�(2122)�Ľ��ײ�����]
 * ȷ����:yifan��jingxu��zhiqianghu
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
 * [2005-11-01��:��̽��ί���˻�(2122)�Ľ��ײ�����]
 * ȷ����:yifan��jingxu��zhiqianghu
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
 * [2005-11-01��:��̽��ί���˻�(2122)�Ľ��ײ�����]
 * ȷ����:yifan��jingxu��zhiqianghu
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
     * ������ʷ�����˺�ƥ���
     */
    private Vector setVMatcherforHistory() {
        Vector vMatcher = new Vector();

        //�˺�ƥ���ֵ
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
     * ����ҳ�ҵ������
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
     * ���⴦��ģ�����
     * 
     * @author yinghu
     * 
     * TODO To change the template for this generated type comment go to Window -
     * Preferences - Java - Code Style - Code Templates
     */
    private long specialType(String creditNo, String debitNo, long sign) {
        //���⴦��
        //�˻��ţ�VB1100132124001735,�˻����ƣ����ж��������, ����������ҵ��
        //�˻��ţ�VB1100132122000030,�˻����ƣ��Ѵ�ί�д��, ����������ҵ��[2005-11-01��:��̽��ί���˻�(2122)�Ľ��ײ����� ȷ����:yifan��jingxu��zhiqianghu]
        //�˻��ţ�VB1100132125000403,�˻����ƣ����ݹ��ж��������, ����������ҵ��
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
 * 2005-10-26�ں���ƹ�
        //�˻��ţ�VB1100131020001005,�˻����ƣ�֪ͨҵ��, ������
        //�˻��ţ�VB1100131020001720,�˻����ƣ�֪ͨҵ��, ������
        if (creditNo.equalsIgnoreCase("VB1100131020001005")
                || debitNo.equalsIgnoreCase("VB1100131020001005")
                || creditNo.equalsIgnoreCase("VB1100131020001720")
                || debitNo.equalsIgnoreCase("VB1100131020001720"))
            sign = -1; //��¼δ���������
*/
        //�跽�˻��ţ����ڿ�����--sett_branch����
        //�����˻��ţ����ڿ�����--sett_branch����
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
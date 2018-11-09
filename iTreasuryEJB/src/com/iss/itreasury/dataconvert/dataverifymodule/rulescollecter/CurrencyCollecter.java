/*
 * Created on 2006-5-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.dataverifymodule.rulescollecter;

import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.DateRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.IRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.NotNullRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.NumberRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.SimpleNullLinkRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.StringRule;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurrencyCollecter extends AbstractCollecter{
    
    public List collect(HSSFRow row){
        LinkedList result=new LinkedList();
        if(isNullRow(row,0)){
            return result;
        }
        //�˻���
        IRule tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        //���´����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("office","sCode");
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //�˻�����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("sett_AccountType","sAccountType");
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        //�ͻ���
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("client_ClientInfo","code");
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        //��������
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        //�廧����
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        //�˻�״̬
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        //�˻����
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //�ۼ�Ӧ����Ϣ
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        //�Ƿ�����͸֧
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //��һ��͸֧���
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        //�ڶ���͸֧���
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //������͸֧���
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //���ʼƻ�
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        //�Ƿ�Э�����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //Э������޶�
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        //Э�����ת�Ƶ�λ
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 22));
        result.add(tempRule);
        //Э���������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 23));
        result.add(tempRule);
        //����������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 24));
        result.add(tempRule);
        //���ʽ��׷���������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 25));
        result.add(tempRule);
        //�¶��ۼƷ���������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 26));
        result.add(tempRule);
        //ÿ���ۼƷ�����
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 27));
        result.add(tempRule);
        //¼��ʱ��
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 29));
        result.add(tempRule);
        //����ʱ��
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 31));
        result.add(tempRule);
        //��ֹ������ǰһ��
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        //Э������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 36));
        result.add(tempRule);
        return result;
    }

}
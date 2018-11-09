/*
 * Created on 2006-5-30
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
public class LoanDiscountContractFormCollecter extends AbstractCollecter{
    
    public List collect(HSSFRow row){
        LinkedList result=new LinkedList();
        if(isNullRow(row,0)){
            return result;
        }
        //��ͬ���
        IRule tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        //��������
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //������
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //��ͬ״̬
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        //��ͬ������
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        //��λ�ͻ����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("client_ClientInfo","code");
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        //�����ܽ��
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        //������Ϣ
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        //�������ֺ˶����
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //���гж��ܻ�Ʊ
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        //��Ʊ���
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //��Ʊ����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        //��Ʊ��
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        //��Ʊ����
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        //�ж�����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //���ֵ�����
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        //��Ʊ����
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //��Ʊ���
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        //������Ϣ
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //ʵ�����ֽ��
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        return result;
    }

}

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
public class LoanContractPlanCollecter extends AbstractCollecter{
    
    public List collect(HSSFRow row){
        LinkedList result=new LinkedList();
        if(isNullRow(row,0)){
            return result;
        }
        //合同编号
        IRule tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("loan_ContractForm","sContractCode");
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        //类型
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //执行日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        return result;
    }

}

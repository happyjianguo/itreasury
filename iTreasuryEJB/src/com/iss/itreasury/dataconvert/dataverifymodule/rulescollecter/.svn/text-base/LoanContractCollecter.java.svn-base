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
public class LoanContractCollecter extends AbstractCollecter{
    
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
        //贷款类型
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //贷款子类型
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //币种
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        //借款单位客户编号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("client_ClientInfo","code");
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //贷款金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        //借款期限
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        //贷款开始时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //贷款结束时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        //合同管理人
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //信用贷款金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        //保证金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //质押金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 25));
        result.add(tempRule);
        //抵押金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 31));
        result.add(tempRule);
        //抵押财产总价
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 34));
        result.add(tempRule);
        //抵押率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        //贷款合同利率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 37));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 37));
        result.add(tempRule);
        //贷款执行利率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 38));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 38));
        result.add(tempRule);
        //状态
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 39));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 39));
        result.add(tempRule);
        //手续费率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 40));
        result.add(tempRule);
        //风险状态
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 43));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 43));
        result.add(tempRule);
        //还款时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 47));
        result.add(tempRule);
        //还款金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 48));
        result.add(tempRule);
        return result;
    }

}

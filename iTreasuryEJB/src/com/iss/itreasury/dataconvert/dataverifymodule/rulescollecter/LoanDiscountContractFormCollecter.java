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
        //合同编号
        IRule tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        //贴现利率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //贴现日
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //合同状态
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        //合同管理人
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        //借款单位客户编号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("client_ClientInfo","code");
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        //贴现总金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        //贴现利息
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        //汇总贴现核定金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //银行承兑总汇票
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        //汇票序号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //汇票类型
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        //出票人
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        //出票日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        //承兑银行
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //贴现到期日
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        //汇票号码
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //汇票金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        //贴现利息
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //实付贴现金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        return result;
    }

}

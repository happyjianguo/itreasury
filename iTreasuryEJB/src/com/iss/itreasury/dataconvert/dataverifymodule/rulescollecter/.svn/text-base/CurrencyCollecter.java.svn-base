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
        //账户号
        IRule tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 0));
        result.add(tempRule);
        //办事处编号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("office","sCode");
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //币种
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        //账户类型
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("sett_AccountType","sAccountType");
        tempRule.setHSSFCell(row.getCell((short) 3));
        result.add(tempRule);
        //客户号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new SimpleNullLinkRule("client_ClientInfo","code");
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        //开户日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        //清户日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        //账户状态
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        //账户余额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //累计应付利息
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        //是否允许透支
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //第一级透支金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        //第二级透支金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //第三级透支金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //利率计划
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 19));
        result.add(tempRule);
        //是否协定存款
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //协定存款限额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 21));
        result.add(tempRule);
        //协定存款转移单位
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 22));
        result.add(tempRule);
        //协定存款利率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 23));
        result.add(tempRule);
        //最低余额限制
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 24));
        result.add(tempRule);
        //单笔交易发生额限制
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 25));
        result.add(tempRule);
        //月度累计发生额限制
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 26));
        result.add(tempRule);
        //每月累计发生额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 27));
        result.add(tempRule);
        //录入时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 29));
        result.add(tempRule);
        //复核时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 31));
        result.add(tempRule);
        //截止到上线前一天
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        //协定积数
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 36));
        result.add(tempRule);
        return result;
    }

}

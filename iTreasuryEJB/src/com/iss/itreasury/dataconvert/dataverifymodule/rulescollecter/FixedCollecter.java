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
public class FixedCollecter extends AbstractCollecter{
    
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
        //新系统存单号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 9));
        result.add(tempRule);
        //存单金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 10));
        result.add(tempRule);
        //账户余额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        //年利率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //开始日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        //结束日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 14));
        result.add(tempRule);
        //到期日
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 15));
        result.add(tempRule);
        //已记提利息
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 16));
        result.add(tempRule);
        //上一个结息日期
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 17));
        result.add(tempRule);
        //定期存款期限
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 18));
        result.add(tempRule);
        //录入时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //复核时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 22));
        result.add(tempRule);
        return result;
    }
}

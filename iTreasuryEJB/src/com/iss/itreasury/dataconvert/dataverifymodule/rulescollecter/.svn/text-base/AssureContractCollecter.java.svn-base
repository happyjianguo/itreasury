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
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.StringRule;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AssureContractCollecter extends AbstractCollecter{
    
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
        //币种
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //被担保单位
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 4));
        result.add(tempRule);
        //被担保单位客户号
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 5));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 5));
        result.add(tempRule);
        //担保金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 6));
        result.add(tempRule);
        //担保开始时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 7));
        result.add(tempRule);
        //担保结束时间
        tempRule=new DateRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 8));
        result.add(tempRule);
        //合同管理人
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 11));
        result.add(tempRule);
        //信用反担保金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 12));
        result.add(tempRule);
        //保证金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 13));
        result.add(tempRule);
        //质押金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 20));
        result.add(tempRule);
        //抵押金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 23));
        result.add(tempRule);
        //担保费率
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 26));
        result.add(tempRule);
        //担保类型一
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 35));
        result.add(tempRule);
        //担保类型二
        tempRule=new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 36));
        result.add(tempRule);
        tempRule=new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 36));
        result.add(tempRule);
        //自付保证金额
        tempRule=new NumberRule();
        tempRule.setHSSFCell(row.getCell((short) 37));
        result.add(tempRule);
        return result;
    }
}

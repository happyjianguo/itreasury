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
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.IRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.NotNullRule;
import com.iss.itreasury.dataconvert.dataverifymodule.verifyrule.StringRule;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClientCollecter extends AbstractCollecter {

    public List collect(HSSFRow row) {
        LinkedList result = new LinkedList();
        if(isNullRow(row,1)){
            return result;
        }
        //客户编号
        IRule tempRule = new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        tempRule = new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 1));
        result.add(tempRule);
        //客户中文名称
        tempRule = new StringRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        tempRule = new NotNullRule();
        tempRule.setHSSFCell(row.getCell((short) 2));
        result.add(tempRule);
        return result;
    }
}
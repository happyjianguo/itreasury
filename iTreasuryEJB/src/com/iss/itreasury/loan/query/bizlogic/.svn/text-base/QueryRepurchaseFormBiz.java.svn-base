/*
 * Created on 2005-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.query.bizlogic;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.query.dao.QueryRepurchaseFormDao;
import com.iss.itreasury.loan.query.dao.QueryRepurchaseFormDetailDao;
import com.iss.itreasury.loan.query.dataentity.RepurchaseFormDetailInfo;
import com.iss.itreasury.loan.query.dataentity.RepurchaseFormWhereInfo;
import com.iss.system.dao.PageLoader;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryRepurchaseFormBiz {
    
    /**
     * 
     * @param wInfo
     * @return
     */
    public PageLoader QueryRepurchaseForm(RepurchaseFormWhereInfo wInfo){
        return new QueryRepurchaseFormDao().QueryRepurchaseForm(wInfo);
    } 
    
    /**
     * 
     * @param wInfo
     * @return
     */
    public String getOrderBySql(RepurchaseFormWhereInfo wInfo) {
        return new QueryRepurchaseFormDao().getOrderBySql(wInfo);   
    }
    
    /**
     * 
     * @param repurchaseFormId
     * @return
     * @throws Exception
     */
    public RepurchaseFormDetailInfo[] QueryRepurchaseFormDetail(long repurchaseFormId) throws Exception{
        RepurchaseFormDetailInfo[] result=new QueryRepurchaseFormDetailDao().queryRepurchaseFormDetail(repurchaseFormId);
        ContractOperation co=new ContractOperation();
        for(int i=0;i<result.length;i++){
            result[i].setContractBalance(co.getLateAmount(result[i].getContractId()).getBalanceAmount());
        }
        return result;
    }
    
    public double QuerySumAmountOfRepurchaseForm(RepurchaseFormWhereInfo wInfo) throws Exception{
        return new QueryRepurchaseFormDao().QuerySumAmountOfRepurchaseForm(wInfo);
    }
}

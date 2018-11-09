/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dao;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_AssureChargeFormInfo;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loan_AssureChargeFormInfoDao extends DataTransplantBaseDao{
    
    public Loan_AssureChargeFormInfoDao(){
        this.strTableName="LOAN_ASSURECHARGEFORM";
    }
    
    /**
     * 查询放款单id
     * @param payFormCode
     * @param contractCode
     * @return
     */
    public Loan_AssureChargeFormInfo queryPayFormId(String payFormCode,String contractCode){
        long contractId=NameRef.getLoanContractIdByCode(contractCode);
        if(contractId==-1||payFormCode==null||"".equalsIgnoreCase(payFormCode)){
            return null;
        }
        Loan_AssureChargeFormInfo info=new Loan_AssureChargeFormInfo();
        info.setCode(payFormCode);
        info.setContractId(contractId);
        Collection c=this.findByConditionOrderById(info,null);
        Iterator it=c.iterator();
        if(it.hasNext()){
            return (Loan_AssureChargeFormInfo)it.next();
        }
        else{
            return null;
        }
    }
    
    
}

/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dao;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_PayFormInfo;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loan_PayFormInfoDao extends DataTransplantBaseDao{
    
    public Loan_PayFormInfoDao(){
        this.strTableName="Loan_PayForm";
    }
    
    /**
     * 查询放款单id
     * @param payFormCode
     * @param contractCode
     * @return
     */
    public Loan_PayFormInfo queryPayFormId(String payFormCode,String contractCode){
        long contractId=NameRef.getLoanContractIdByCode(contractCode);
        if(contractId==-1||payFormCode==null||"".equalsIgnoreCase(payFormCode)){
            return null;
        }
        Loan_PayFormInfo info=new Loan_PayFormInfo();
        info.setSCode(payFormCode);
        info.setNContractId(contractId);
        Collection c=this.findByConditionOrderById(info,null);
        Iterator it=c.iterator();
        if(it.hasNext()){
            return (Loan_PayFormInfo)it.next();
        }
        else{
            return null;
        }
    }
    
    
}

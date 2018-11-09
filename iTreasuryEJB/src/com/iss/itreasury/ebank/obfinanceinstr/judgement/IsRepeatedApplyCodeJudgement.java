/**
 * created on Mar 13, 2008
 */
package com.iss.itreasury.ebank.obfinanceinstr.judgement;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.exp.RepeatedApplyCodeException;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 *  网银指令申请中，业务申请编号是否重复判断器。
 */
public class IsRepeatedApplyCodeJudgement {

    public static boolean judge(FinanceInfo financeInfo) throws IException
    {
        OBFinanceInstrDao financeInstrDAO = new OBFinanceInstrDao();
        
        try{
            financeInstrDAO.CheckApplyCodeRepetition(financeInfo);
        }catch(RepeatedApplyCodeException ex1){
            return true;
        }
        
        return false;
    }
}

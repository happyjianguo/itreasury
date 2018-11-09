/**
 * 
 */
package com.iss.itreasury.settlement.bankinstruction;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResultItem;
import com.iss.itreasury.settlement.integration.client.info.AccountTransInfo;
/**
 * @author qijiang
 * 结算入账操作接口
 * 结算入账操作类必须实现此接口
 */
public interface IImportAccount {

    /**
     * 接受银行交易信息，进行结算入账处理
     * @param param
     * @throws IException
     */
    public ImpAccountResultItem save(String accountNO,String bankAccountCode,AccountTransInfo param) throws IException;
}

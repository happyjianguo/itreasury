/*
 * Created on 2005-8-10
 *
 */
package com.iss.itreasury.settlement.bankinstruction;

import java.util.Collection;

import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.IException;


/**
 * @author weilu
 * 银行指令操作接口
 * 所有实际的指令操作类必须实现此接口
 */
public interface IBankInstruction {
    
    /**
     * 根据活期交易对象创建指令
     * @param param
     * @throws IException
     */
    public void createBankInstruction(CreateInstructionParam param) throws IException;
    
    /**
     * 多借多贷新增方发
     * @param params
     * @throws IException
     */
    public void createSpecialBankInstruction(Collection params) throws IException;
    
    /**
     * 根据交易明细创建指令
     * @param param
     * @throws IException
     */
    public void createBankInstructionFromTransDetail(CreateInstructionParam param) throws IException;
    
    /**
     * 发送银行指令,只是发送机制,并不是发送的具体实现方法
     * @param obj 交易对象
     * @throws IException
     */
    public void sendBankInstruction(Object obj) throws IException;
    
    
    /**
     * 拦截器,在进行相关操作前的检查
     * @return 
     */
    public boolean intercept();
    
    /**
     * 进行取消操作时进行相关指令状态的查询判断 
     * @return
     * @throws SettlementException 
     */
    public void cancelCheck(String stransNo) throws IException;
    
}

package com.iss.itreasury.loan.query.dataentity;

/**
 * @author haoning
 * time:   2003-11-23
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.Serializable;

public class QueryMyJobCountInfo implements Serializable
{
    //------------------------------申请--------------------------------//
    //贷款申请——撰写-总数
    private long[] LoanApplySaveCount =null;
    
    //贷款申请——提交-总数
    private long[] LoanApplySubmitCount =null;
    
    //贷款申请——待审核-总数
    private long[] LoanApplyForCheckCount =null;
    
    //展期申请——提交-总数
    private long[] ExtendApplySubmitCount =null;
    
    //展期申请——待审核-总数
    private long[] ExtendApplyForCheckCount =null;
    
    //免还申请——提交-总数
    private long[] FreeApplySubmitCount ={0,0,0,0,0};
    
    //免还申请——待审核-总数
    private long[] FreeApplyForCheckCount ={0,0,0,0,0};
    
    //贴现申请——撰写-总数
    private long DiscountApplySaveCount =0;
    
    //贴现申请——提交-总数
    private long DiscountApplySubmitCount =0;
    
    //贴现申请——待审核-总数
    private long DiscountApplyForCheckCount =0;
    
    //贴现凭证——提交-总数
    private long DiscountCredenceSubmitCount =0;
    
    //贴现凭证——待审核-总数
    private long DiscountCredenceForCheckCount =0;
    
	//转贴现申请——撰写-总数
	private long TransDiscountApplySaveCount =0;
	
	//转贴现申请——提交-总数
	private long TransDiscountApplySubmitCount =0;
    
	//转贴现申请——待审核-总数
	private long TransDiscountApplyForCheckCount =0;
    
	//转贴现凭证——提交-总数
	private long TransDiscountCredenceSubmitCount =0;
    
	//转贴现凭证——待审核-总数
	private long TransDiscountCredenceForCheckCount =0;
	
	//转贴现回购凭证——提交-总数
	private long RepurchaseCredenceSubmitCount =0;
    
	//转贴现回购凭证——待审核-总数
	private long RepurchaseCredenceForCheckCount =0;	
    
    //放款通知单——提交-总数
    private long PaySubmitCount =0;
    
    //放款通知单——待审核-总数
    private long PayForCheckCount =0;
    
    //担保收款通知单--提交--总数（上海电气新增担保类型） 2004-12-8
    private long AssureChargeNoticeSubmitCount = 0;
    
    //担保收款通知单-- 待审核--总数 （上海电气新增担保类型） 2004-12-8
    private long AssureChargeNoticeForCheckCount = 0;
    
    //保后处理通知单--提交--总数 （上海电气新增担保类型） 2004-12-8
    private long AssureManagementNoticeSubmitCount = 0;
    
    //报后处理通知单--待审核--总数 （上海电气新增担保类型） 2004-12-8
    private long AssureManagementNoticeForCheckCount = 0;
    
    //逾期处理——提交-总数
    private long OverDueSubmitCount =0;
    
    //逾期处理——待审核-总数
    private long OverDueForCheckCount =0;
    
    //合同执行计划——提交-总数
    private long ContractPlanSubmitCount =0;
    
    //合同执行计划——待审核-总数
    private long ContractPlanForCheckCount =0;
    
    //利率调整——提交-总数
    private long RateAdjustSubmitCount =0;
    
    //利率调整——待审核-总数
    private long RateAdjustForCheckCount =0;
    
    //提前还款——提交-总数
    private long AheadRePaySubmitCount =0;
    
    //提前还款——待审核-总数
    private long AheadRePayForCheckCount =0;
    
    //风险状态变更——提交-总数
    private long RiskStatusAdjustSubmitCount =0;
    
    //风险状态变更——待审核-总数
    private long RiskStatusAdjustForCheckCount =0;
    
    //合同状态变更——提交-总数
    private long ContractStatusAdjustSubmitCount =0;
    
    //合同状态变更——待审核-总数
    private long ContractStatusAdjustForCheckCount =0;
    
    //银团放款通知单——提交-总数
    private long YTPaySubmitCount =0;
    
    //银团放款通知单——待审核-总数
    private long YTPayForCheckCount =0;
    
    //银团提款通知单——提交-总数
    private long YTDrawNoticeSubmitCount =0;
    
    //银团提款通知单——待审核-总数
    private long YTDrawNoticeForCheckCount =0;
    
    //审核贷款转让申请——撰写-总数
      private long AttornmentApplySaveCount =0;
      
    //审核贷款转让申请——提交-总数
    private long AttornmentApplySubmitCount =0;
    
    //审核贷款转让申请——待审核-总数
    private long AttornmentApplyForCheckCount =0;
    
    
    //-------------------------------合同-------------------------------//
    //贷款合同——撰写-总数
    private long[] ContractApplySaveCount =null;
    
    //贷款合同——提交-总数
    private long[] ContractApplySubmitCount =null;
    
    //贷款合同——待审核-总数
    private long[] ContractApplyForCheckCount =null;
    
    //贷款合同——待激活-总数
    private long ContractApplyForActiveCount =0;
    
    //展期合同——撰写-总数
    private long[] ExtendContractSaveCount =null;
    
    //展期合同——提交-总数
    private long[] ExtendContractSubmitCount =null;
    
    //展期合同——待审核-总数
    private long[] ExtendContractForCheckCount =null;
    
    //贴现合同——撰写-总数
    private long DiscountContractSaveCount =0;
    
    //贴现合同——提交-总数
    private long DiscountContractSubmitCount =0;
    
    //贴现合同——待审核-总数
    private long DiscountContractForCheckCount =0;

	//转贴现合同——撰写-总数
	private long TransDiscountContractSaveCount =0;
    
	//转贴现合同——提交-总数
	private long TransDiscountContractSubmitCount =0;
    
	//转贴现合同——待审核-总数
	private long TransDiscountContractForCheckCount =0;    
    
    //=========================查找条件==========================//
    //
    private long OfficeID = -1;
    
    //
    private long CurrencyID = -1;
    
    //
    private long UserID = -1;
    
    //
    private long StatusID = -1;
    
    
    /**
     * @param 
     * function 得到/设置贷款申请——撰写-总数
     * return long[]
     */
    public long[] getLoanApplySaveCount()
    {
        return LoanApplySaveCount;
    }

    /**
     * @param 
     * function 得到/设置贷款申请——撰写-总数
     * return void
     */
    public void setLoanApplySaveCount(long[] ds)
    {
        this.LoanApplySaveCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贷款申请——提交-总数
     * return long[]
     */
    public long[] getLoanApplySubmitCount()
    {
        return LoanApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置贷款申请——提交-总数
     * return void
     */
    public void setLoanApplySubmitCount(long[] ds)
    {
        this.LoanApplySubmitCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贷款申请——待审核-总数
     * return long[]
     */
    public long[] getLoanApplyForCheckCount()
    {
        return LoanApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置贷款申请——待审核-总数
     * return void
     */
    public void setLoanApplyForCheckCount(long[] ds)
    {
        this.LoanApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function 得到/设置展期申请——提交-总数
     * return long
     */
    public long[] getExtendApplySubmitCount()
    {
        return ExtendApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置展期申请——提交-总数
     * return void
     */
    public void setExtendApplySubmitCount(long[] d)
    {
        this.ExtendApplySubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置展期申请——待审核-总数
     * return long
     */
    public long[] getExtendApplyForCheckCount()
    {
        return ExtendApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置展期申请——待审核-总数
     * return void
     */
    public void setExtendApplyForCheckCount(long[] d)
    {
        this.ExtendApplyForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置免还申请——提交-总数
     * return long[]
     */
    public long[] getFreeApplySubmitCount()
    {
        return FreeApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置免还申请——提交-总数
     * return void
     */
    public void setFreeApplySubmitCount(long[] ds)
    {
        this.FreeApplySubmitCount = ds;
    }

    /**
     * @param 
     * function 得到/设置免还申请——待审核-总数
     * return long[]
     */
    public long[] getFreeApplyForCheckCount()
    {
        return FreeApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置免还申请——待审核-总数
     * return void
     */
    public void setFreeApplyForCheckCount(long[] ds)
    {
        this.FreeApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——撰写-总数
     * return long
     */
    public long getDiscountApplySaveCount()
    {
        return DiscountApplySaveCount;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——撰写-总数
     * return void
     */
    public void setDiscountApplySaveCount(long d)
    {
        this.DiscountApplySaveCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——提交-总数
     * return long
     */
    public long getDiscountApplySubmitCount()
    {
        return DiscountApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——提交-总数
     * return void
     */
    public void setDiscountApplySubmitCount(long d)
    {
        this.DiscountApplySubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——待审核-总数
     * return long
     */
    public long getDiscountApplyForCheckCount()
    {
        return DiscountApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置贴现申请——待审核-总数
     * return void
     */
    public void setDiscountApplyForCheckCount(long d)
    {
        this.DiscountApplyForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现凭证——提交-总数
     * return long
     */
    public long getDiscountCredenceSubmitCount()
    {
        return DiscountCredenceSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置贴现凭证——提交-总数
     * return void
     */
    public void setDiscountCredenceSubmitCount(long d)
    {
        this.DiscountCredenceSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现凭证——待审核-总数
     * return long
     */
    public long getDiscountCredenceForCheckCount()
    {
        return DiscountCredenceForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置贴现凭证——待审核-总数
     * return void
     */
    public void setDiscountCredenceForCheckCount(long d)
    {
        this.DiscountCredenceForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置放款通知单——提交-总数
     * return long
     */
    public long getPaySubmitCount()
    {
        return PaySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置放款通知单——提交-总数
     * return void
     */
    public void setPaySubmitCount(long d)
    {
        this.PaySubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置放款通知单——待审核-总数
     * return long
     */
    public long getPayForCheckCount()
    {
        return PayForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置放款通知单——待审核-总数
     * return void
     */
    public void setPayForCheckCount(long d)
    {
        this.PayForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置逾期处理——提交-总数
     * return long
     */
    public long getOverDueSubmitCount()
    {
        return OverDueSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置逾期处理——提交-总数
     * return void
     */
    public void setOverDueSubmitCount(long d)
    {
        this.OverDueSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置逾期处理——待审核-总数
     * return long
     */
    public long getOverDueForCheckCount()
    {
        return OverDueForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置逾期处理——待审核-总数
     * return void
     */
    public void setOverDueForCheckCount(long d)
    {
        this.OverDueForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置合同执行计划——提交-总数
     * return long
     */
    public long getContractPlanSubmitCount()
    {
        return ContractPlanSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置合同执行计划——提交-总数
     * return void
     */
    public void setContractPlanSubmitCount(long d)
    {
        this.ContractPlanSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置合同执行计划——待审核-总数
     * return long
     */
    public long getContractPlanForCheckCount()
    {
        return ContractPlanForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置合同执行计划——待审核-总数
     * return void
     */
    public void setContractPlanForCheckCount(long d)
    {
        this.ContractPlanForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置利率调整——提交-总数
     * return long
     */
    public long getRateAdjustSubmitCount()
    {
        return RateAdjustSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置利率调整——提交-总数
     * return void
     */
    public void setRateAdjustSubmitCount(long d)
    {
        this.RateAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置利率调整——待审核-总数
     * return long
     */
    public long getRateAdjustForCheckCount()
    {
        return RateAdjustForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置利率调整——待审核-总数
     * return void
     */
    public void setRateAdjustForCheckCount(long d)
    {
        this.RateAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置提前还款——提交-总数
     * return long
     */
    public long getAheadRePaySubmitCount()
    {
        return AheadRePaySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置提前还款——提交-总数
     * return void
     */
    public void setAheadRePaySubmitCount(long d)
    {
        this.AheadRePaySubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置提前还款——待审核-总数
     * return long
     */
    public long getAheadRePayForCheckCount()
    {
        return AheadRePayForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置提前还款——待审核-总数
     * return void
     */
    public void setAheadRePayForCheckCount(long d)
    {
        this.AheadRePayForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置风险状态变更——提交-总数
     * return long
     */
    public long getRiskStatusAdjustSubmitCount()
    {
        return RiskStatusAdjustSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置风险状态变更——提交-总数
     * return void
     */
    public void setRiskStatusAdjustSubmitCount(long d)
    {
        this.RiskStatusAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置风险状态变更——待审核-总数
     * return long
     */
    public long getRiskStatusAdjustForCheckCount()
    {
        return RiskStatusAdjustForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置风险状态变更——待审核-总数
     * return void
     */
    public void setRiskStatusAdjustForCheckCount(long d)
    {
        this.RiskStatusAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置合同状态变更——提交-总数
     * return long
     */
    public long getContractStatusAdjustSubmitCount()
    {
        return ContractStatusAdjustSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置合同状态变更——提交-总数
     * return void
     */
    public void setContractStatusAdjustSubmitCount(long d)
    {
        this.ContractStatusAdjustSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置合同状态变更——待审核-总数
     * return long
     */
    public long getContractStatusAdjustForCheckCount()
    {
        return ContractStatusAdjustForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置合同状态变更——待审核-总数
     * return void
     */
    public void setContractStatusAdjustForCheckCount(long d)
    {
        this.ContractStatusAdjustForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——撰写-总数
     * return long[]
     */
    public long[] getContractApplySaveCount()
    {
        return ContractApplySaveCount;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——撰写-总数
     * return void
     */
    public void setContractApplySaveCount(long[] ds)
    {
        this.ContractApplySaveCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——提交-总数
     * return long[]
     */
    public long[] getContractApplySubmitCount()
    {
        return ContractApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——提交-总数
     * return void
     */
    public void setContractApplySubmitCount(long[] ds)
    {
        this.ContractApplySubmitCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——待审核-总数
     * return long[]
     */
    public long[] getContractApplyForCheckCount()
    {
        return ContractApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——待审核-总数
     * return void
     */
    public void setContractApplyForCheckCount(long[] ds)
    {
        this.ContractApplyForCheckCount = ds;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——待激活-总数
     * return long[]
     */
    public long getContractApplyForActiveCount()
    {
        return ContractApplyForActiveCount;
    }

    /**
     * @param 
     * function 得到/设置贷款合同——待激活-总数
     * return void
     */
    public void setContractApplyForActiveCount(long ds)
    {
        this.ContractApplyForActiveCount = ds;
    }

    /**
     * @param 
     * function 得到/设置展期合同——撰写-总数
     * return long
     */
    public long[] getExtendContractSaveCount()
    {
        return ExtendContractSaveCount;
    }

    /**
     * @param 
     * function 得到/设置展期合同——撰写-总数
     * return void
     */
    public void setExtendContractSaveCount(long[] d)
    {
        this.ExtendContractSaveCount = d;
    }

    /**
     * @param 
     * function 得到/设置展期合同——提交-总数
     * return long
     */
    public long[] getExtendContractSubmitCount()
    {
        return ExtendContractSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置展期合同——提交-总数
     * return void
     */
    public void setExtendContractSubmitCount(long[] d)
    {
        this.ExtendContractSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置展期合同——待审核-总数
     * return long
     */
    public long[] getExtendContractForCheckCount()
    {
        return ExtendContractForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置展期合同——待审核-总数
     * return void
     */
    public void setExtendContractForCheckCount(long[] d)
    {
        this.ExtendContractForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——撰写-总数
     * return long
     */
    public long getDiscountContractSaveCount()
    {
        return DiscountContractSaveCount;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——撰写-总数
     * return void
     */
    public void setDiscountContractSaveCount(long d)
    {
        this.DiscountContractSaveCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——提交-总数
     * return long
     */
    public long getDiscountContractSubmitCount()
    {
        return DiscountContractSubmitCount;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——提交-总数
     * return void
     */
    public void setDiscountContractSubmitCount(long d)
    {
        this.DiscountContractSubmitCount = d;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——待审核-总数
     * return long
     */
    public long getDiscountContractForCheckCount()
    {
        return DiscountContractForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置贴现合同——待审核-总数
     * return void
     */
    public void setDiscountContractForCheckCount(long d)
    {
        this.DiscountContractForCheckCount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeID(long l)
    {
        this.OfficeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyID(long l)
    {
        this.CurrencyID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getUserID()
    {
        return UserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setUserID(long l)
    {
        this.UserID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function 得到/设置银团放款通知单——提交-总数
     * return long
     */
    public long getYTPaySubmitCount()
    {
        return YTPaySubmitCount;
    }

    /**
     * @param l
     * function 得到/设置银团放款通知单——提交-总数
     * return void
     */
    public void setYTPaySubmitCount(long l)
    {
        this.YTPaySubmitCount = l;
    }

    /**
     * function 得到/设置银团提款通知单——待审核-总数
     * return long
     */
    public long getYTPayForCheckCount()
    {
        return YTPayForCheckCount;
    }

    /**
     * @param l
     * function 得到/设置银团提款通知单——待审核-总数
     * return void
     */
    public void setYTPayForCheckCount(long l)
    {
        this.YTPayForCheckCount = l;
    }

    /**
     * function 得到/设置银团提款通知单——提交-总数
     * return long
     */
    public long getYTDrawNoticeSubmitCount()
    {
        return YTDrawNoticeSubmitCount;
    }

    /**
     * @param l
     * function 得到/设置银团提款通知单——提交-总数
     * return void
     */
    public void setYTDrawNoticeSubmitCount(long l)
    {
        this.YTDrawNoticeSubmitCount = l;
    }

    /**
     * function 得到/设置银团提款通知单——待审核-总数
     * return long
     */
    public long getYTDrawNoticeForCheckCount()
    {
        return YTDrawNoticeForCheckCount;
    }

    /**
     * @param l
     * function 得到/设置银团提款通知单——待审核-总数
     * return void
     */
    public void setYTDrawNoticeForCheckCount(long l)
    {
        this.YTDrawNoticeForCheckCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getAttornmentApplyForCheckCount()
    {
        return AttornmentApplyForCheckCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAttornmentApplyForCheckCount(long l)
    {
        AttornmentApplyForCheckCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getAttornmentApplySaveCount()
    {
        return AttornmentApplySaveCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAttornmentApplySaveCount(long l)
    {
        AttornmentApplySaveCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getAttornmentApplySubmitCount()
    {
        return AttornmentApplySubmitCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAttornmentApplySubmitCount(long l)
    {
        AttornmentApplySubmitCount = l;
    }

	/**
	 * @return
	 */
	public long getRepurchaseCredenceForCheckCount()
	{
		return RepurchaseCredenceForCheckCount;
	}

	/**
	 * @return
	 */
	public long getRepurchaseCredenceSubmitCount()
	{
		return RepurchaseCredenceSubmitCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplyForCheckCount()
	{
		return TransDiscountApplyForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplySaveCount()
	{
		return TransDiscountApplySaveCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountApplySubmitCount()
	{
		return TransDiscountApplySubmitCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceForCheckCount()
	{
		return TransDiscountCredenceForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceSubmitCount()
	{
		return TransDiscountCredenceSubmitCount;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseCredenceForCheckCount(long l)
	{
		RepurchaseCredenceForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseCredenceSubmitCount(long l)
	{
		RepurchaseCredenceSubmitCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplyForCheckCount(long l)
	{
		TransDiscountApplyForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplySaveCount(long l)
	{
		TransDiscountApplySaveCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountApplySubmitCount(long l)
	{
		TransDiscountApplySubmitCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceForCheckCount(long l)
	{
		TransDiscountCredenceForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceSubmitCount(long l)
	{
		TransDiscountCredenceSubmitCount = l;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractForCheckCount()
	{
		return TransDiscountContractForCheckCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractSaveCount()
	{
		return TransDiscountContractSaveCount;
	}

	/**
	 * @return
	 */
	public long getTransDiscountContractSubmitCount()
	{
		return TransDiscountContractSubmitCount;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractForCheckCount(long l)
	{
		TransDiscountContractForCheckCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractSaveCount(long l)
	{
		TransDiscountContractSaveCount = l;
	}

	/**
	 * @param l
	 */
	public void setTransDiscountContractSubmitCount(long l)
	{
		TransDiscountContractSubmitCount = l;
	}

	/**
	 * @return Returns the assureChargeNoticeForCheckCount.
	 */
	public long getAssureChargeNoticeForCheckCount()
	{
		return AssureChargeNoticeForCheckCount;
	}
	/**
	 * @param assureChargeNoticeForCheckCount The assureChargeNoticeForCheckCount to set.
	 */
	public void setAssureChargeNoticeForCheckCount(
			long assureChargeNoticeForCheckCount)
	{
		AssureChargeNoticeForCheckCount = assureChargeNoticeForCheckCount;
	}
	/**
	 * @return Returns the assureChargeNoticeSubmitCount.
	 */
	public long getAssureChargeNoticeSubmitCount()
	{
		return AssureChargeNoticeSubmitCount;
	}
	/**
	 * @param assureChargeNoticeSubmitCount The assureChargeNoticeSubmitCount to set.
	 */
	public void setAssureChargeNoticeSubmitCount(
			long assureChargeNoticeSubmitCount)
	{
		AssureChargeNoticeSubmitCount = assureChargeNoticeSubmitCount;
	}
	/**
	 * @return Returns the assureManagementNoticeForCheckCount.
	 */
	public long getAssureManagementNoticeForCheckCount()
	{
		return AssureManagementNoticeForCheckCount;
	}
	/**
	 * @param assureManagementNoticeForCheckCount The assureManagementNoticeForCheckCount to set.
	 */
	public void setAssureManagementNoticeForCheckCount(
			long assureManagementNoticeForCheckCount)
	{
		AssureManagementNoticeForCheckCount = assureManagementNoticeForCheckCount;
	}
	/**
	 * @return Returns the assureManagementNoticeSubmitCount.
	 */
	public long getAssureManagementNoticeSubmitCount()
	{
		return AssureManagementNoticeSubmitCount;
	}
	/**
	 * @param assureManagementNoticeSubmitCount The assureManagementNoticeSubmitCount to set.
	 */
	public void setAssureManagementNoticeSubmitCount(
			long assureManagementNoticeSubmitCount)
	{
		AssureManagementNoticeSubmitCount = assureManagementNoticeSubmitCount;
	}
}
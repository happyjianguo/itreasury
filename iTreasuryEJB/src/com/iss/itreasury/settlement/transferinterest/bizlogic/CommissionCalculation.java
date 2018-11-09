package com.iss.itreasury.settlement.transferinterest.bizlogic;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.IException;

public class CommissionCalculation {
	
	/**
	 * 功能说明：1：计算卖出卖断手续费
	 *     
	 * @param baseAmount 总金额
	 * @param baseInterest 总收息额
	 * @param commissionRate 手续费固定百分比
	 * @param fixRate 贷款利率
	 * @param chargeCommissionType 手续费计算方式 
	 * @return 产生的手续费值
	 * @throws IException
	 */
	public double calculateCommission(double baseAmount ,double baseInterest, double commissionRate,double fixRate ,long chargeCommissionType) throws IException
	{
		double commission = 0.0;
		
		if (chargeCommissionType == CRAconstant.ChargeCommisonPayType.CHARGENONE)
		{
			commission = 0.0;
		}
		else if (chargeCommissionType == CRAconstant.ChargeCommisonPayType.CHARGEAMOUNT)
		{		
			commission = UtilOperation.Arith.mul(baseAmount, UtilOperation.Arith.div(commissionRate,100));

		}
		else if (chargeCommissionType == CRAconstant.ChargeCommisonPayType.CHARGEINTEREST)
		{

			commission = UtilOperation.Arith.mul(baseInterest, UtilOperation.Arith.div(commissionRate,100));
		}
		else 
		{
			
			commission = UtilOperation.Arith.sub(baseInterest,UtilOperation.Arith.mul(baseAmount, UtilOperation.Arith.div(fixRate,100)));
		}

		return commission;
	}
}

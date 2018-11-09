package com.iss.itreasury.evoucher.setting.dataentity;

import java.util.HashMap;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.evoucher.setting.dataentity.TransformOperationDataEntity;
import com.iss.itreasury.evoucher.setting.businessbranches.settlement.TransBakPrintDataEntity;
import com.iss.itreasury.evoucher.setting.businessbranches.settlement.TransInterestFeePaymentDataEntity;
import com.iss.itreasury.evoucher.setting.businessbranches.settlement.TransInternalLendDateEntity;
import com.iss.itreasury.evoucher.setting.businessbranches.settlement.TransReservePrintDataEntity;
import com.iss.itreasury.evoucher.setting.businessbranches.settlement.transJGBakAndReserveAndLendingPrintDataEntity;
import com.iss.itreasury.util.Log4j;

public class OperationDataEntity {
	private Log4j log = new Log4j(Constant.ModuleType.EVOUCHER, this);
	protected HashMap usedFields = new HashMap();
	
	public  HashMap getOperationHashMap(long  transTypeID, long lID) throws Exception
	{
		
		try
		{
			log.print("======开始取数======");
			log.print("======交易类型id是======"+transTypeID);	
			switch ((int) transTypeID)
			{
				case (int)SETTConstant.TransactionType.INERLENDING :
					{
					    log.print("======内部拆借======");	
						TransInternalLendDateEntity transEntity = new TransInternalLendDateEntity();
						usedFields=transEntity.getOperationHashMap(transTypeID, lID);
					}
					break;	
				case (int)SETTConstant.TransactionType.INERLENDINGRETURN :
					{
					    log.print("======内部拆借收回======");	
						TransInternalLendDateEntity transEntity = new TransInternalLendDateEntity();
						usedFields=transEntity.getOperationHashMap(transTypeID, lID);
					}
				break;
				// 总部-备付金相关业务
				case (int)SETTConstant.TransactionType.BAKUPRECEIVE :
				case (int)SETTConstant.TransactionType.BAKRETURN :	
				case (int)SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST :
				case (int)SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST :
				{
				    log.print("======总部-备付金相关业务======");	
				    TransBakPrintDataEntity transEntity = new TransBakPrintDataEntity();
					usedFields=transEntity.getOperationHashMap(transTypeID, lID);
				}
				break;
				//总部-准备金相关业务
				case (int)SETTConstant.TransactionType.RESERVEUPRECEIVE :
				case (int)SETTConstant.TransactionType.RESERVERETURN :	
				case (int)SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST :	
				case (int)SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST :	
				{
				    log.print("======总部-准备金相关业务======");	
				    TransReservePrintDataEntity transEntity = new TransReservePrintDataEntity();
					usedFields=transEntity.getOperationHashMap(transTypeID, lID);
				}
				break;
				// 机构-备付金上存 备付金提取 备付金利息收入 相关业务
				//机构-准备金上存 准备金提取 准备金利息收入 相关业务
				// 机构-资金拆入 资金拆入返款 资金拆入利息支出 相关业务
				case (int)SETTConstant.TransactionType.JGBAKUPSAVE :
				case (int)SETTConstant.TransactionType.JGBAKDRAW :	
				case (int)SETTConstant.TransactionType.JGBAKINTEREST:	
				case (int)SETTConstant.TransactionType.JGRESERVEUPSAVE :	
				case (int)SETTConstant.TransactionType.JGRESERVEDRAW :	
				case (int)SETTConstant.TransactionType.JGRESERVEINTERES :	
				case (int)SETTConstant.TransactionType.JGLENDING :	
				case (int)SETTConstant.TransactionType.JGLENDINGRETURN :	
				case (int)SETTConstant.TransactionType.JGLENDINGINTEREST :
				{
				    log.print("====== 机构-备付金 准备金 资金拆入 相关业务======");	
				    transJGBakAndReserveAndLendingPrintDataEntity transEntity = new transJGBakAndReserveAndLendingPrintDataEntity();
					usedFields=transEntity.getOperationHashMap(transTypeID, lID);
				}
				break;
				case (int)SETTConstant.TransactionType.INTERESTFEEPAYMENT :
				{//利息费用支付
				    log.print("====== 利息费用支付======");	
				    TransInterestFeePaymentDataEntity transEntity = new TransInterestFeePaymentDataEntity();
					usedFields=transEntity.getOperationHashMap(transTypeID, lID);
				}
				break;				
				default :
					{
					    log.print("======调用默认的方法======");	
						TransformOperationDataEntity transEntity = new TransformOperationDataEntity();
						usedFields=transEntity.getOperationHashMap(transTypeID, lID,"");
					}
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return usedFields;
	}

}

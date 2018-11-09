package com.iss.itreasury.system.translog.dataentity;


import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

public class FinanceInfoLogInfo  extends LogBaseInfo
{

	private FinanceInfo info = null;
	
	private  long ActionType = -1;
	
	public void setOriginObjInfo(Object obj)
	{
		this.info =(FinanceInfo)obj;
	}
	
	public void setActionType(long actionType)
	{
		this.ActionType =actionType;
	}

	public String getRemark(long TransType)
	{
		String remark="";
		if(TransType==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
		{
			remark= "汇款方式："+OBConstant.SettRemitType.getName(info.getRemitType())+"；付款方账号："+info.getPayerAcctNo()+"；付款方名称："+info.getPayerName()+"；收款方账号："+info.getPayeeAcctNo() +"；收款方名称："+info.getPayeeName()+"；金额："+DataFormat.formatDouble(3,info.getAmount())+"元。";
		}
		else if(TransType==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY)
		{
			remark= "汇款方式："+OBConstant.SettRemitType.getName(info.getRemitType())+"；付款方账号："+info.getPayerAcctNo()+"；付款方名称："+info.getPayerName()+"；收款方账号："+info.getPayeeAcctNo() +"；收款方名称："+info.getPayeeName()+"；金额："+DataFormat.formatDouble(3,info.getAmount())+"元。";
		}
		else if(TransType==OBConstant.SettInstrType.OPENFIXDEPOSIT)
		{
			remark= "定期开立："+NameRef.getClientNameByID(info.getClientID())+"开立 金额：￥"+DataFormat.formatDouble(3,info.getAmount())+" 期限："+info.getFixedDepositTime()+"个月 定期账户："+NameRef.getNoLineAccountNo(info.getPayeeAcctNo());			
		}
		else if(TransType==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
		{
			remark= "定期支取："+NameRef.getClientNameByID(info.getClientID())+"支取 金额：￥"+DataFormat.formatDouble(3,info.getAmount())+" 本金收款方账号："+NameRef.getNoLineAccountNo(info.getPayeeAcctNo())+"利息收款方账号："+NameRef.getNoLineAccountNo(info.getInterestPayeeAcctNo());
		}
		else if(TransType==OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
		{
			remark= "到期续存："+NameRef.getClientNameByID(info.getClientID())+"定期存单号："+info.getDepositNo()+" 金额：￥"+DataFormat.formatDouble(3,info.getAmount())+" 续存期限："+info.getSDepositBillPeriod()+"个月";
		}
		else if(TransType==OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
		{
			remark= "通知开立："+info.getPayeeName()+"开立 金额：￥"+DataFormat.formatDouble(3,info.getAmount())+"；品种："+DataFormat.formatInt(info.getNoticeDay()-10000,0)+"天；通知存款账户："+NameRef.getNoLineAccountNo(info.getPayeeAcctNo())+"。";
		}
		else if(TransType==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
		{
			remark= "通知支取："+info.getPayeeName()+"支取 金额：￥"+DataFormat.formatDouble(3,info.getAmount())+"；品种："+DataFormat.formatInt(info.getNoticeDay()-10000,0)+"天；通知存款账户："+NameRef.getNoLineAccountNo(info.getPayerAcctNo())+"。";
		}
		else
		{
			remark="没有找到与业务对应的日志生成方式!";
		}
		return remark;
	}
	
	public String getTranstype()
	{
		String returnstr="";
		try {
			returnstr = Constant.TransLogActionType.getName(ActionType)+"-"+OBConstant.SettInstrType.getName(info.getTransType()) ;
		} catch (Exception e) {
			returnstr="获取操作类型出错了！";
			e.printStackTrace();
		}
		return returnstr;
	}
}

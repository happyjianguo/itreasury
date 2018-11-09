package com.iss.itreasury.system.translog.dataentity;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBBankPayInfo;
import com.iss.itreasury.ebank.util.NameRef;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author xfma3
 * 2011-04-20
 */
public class OBBankPayInfoLogInfo  extends LogBaseInfo
{

	private OBBankPayInfo info = null;
	
	private  long ActionType = -1;
	
	public void setOriginObjInfo(Object obj)
	{
		this.info =(OBBankPayInfo)obj;
	}
	
	public void setActionType(long actionType)
	{
		this.ActionType =actionType;
	}

	public String getRemark(long TransType)
	{
		String remark="";
		if(TransType==OBConstant.SettInstrType.ONLINEBANK_BANKPAY)
		{
			remark= "汇款方式：银行直联-银行汇款；付款方账号："+NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid())+"；付款方名称："+info.getName()
			+"；收款方账号："+info.getSpayeeacctno() +"；收款方名称："+info.getSpayeeacctname()+"；金额："+DataFormat.formatDouble(3,info.getMamount())+"元。";
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
			returnstr = Constant.TransLogActionType.getName(ActionType)+"-"+OBConstant.SettInstrType.getName(info.getNtranstype()) ;
		} catch (Exception e) {
			returnstr="获取操作类型出错了！";
			e.printStackTrace();
		}
		return returnstr;
	}
}

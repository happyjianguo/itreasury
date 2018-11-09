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
			remark= "��ʽ������ֱ��-���л�����˺ţ�"+NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid())+"��������ƣ�"+info.getName()
			+"���տ�˺ţ�"+info.getSpayeeacctno() +"���տ���ƣ�"+info.getSpayeeacctname()+"����"+DataFormat.formatDouble(3,info.getMamount())+"Ԫ��";
		}
		else
		{
			remark="û���ҵ���ҵ���Ӧ����־���ɷ�ʽ!";
		}
		return remark;
	}
	
	public String getTranstype()
	{
		String returnstr="";
		try {
			returnstr = Constant.TransLogActionType.getName(ActionType)+"-"+OBConstant.SettInstrType.getName(info.getNtranstype()) ;
		} catch (Exception e) {
			returnstr="��ȡ�������ͳ����ˣ�";
			e.printStackTrace();
		}
		return returnstr;
	}
}

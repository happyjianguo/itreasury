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
			remark= "��ʽ��"+OBConstant.SettRemitType.getName(info.getRemitType())+"������˺ţ�"+info.getPayerAcctNo()+"��������ƣ�"+info.getPayerName()+"���տ�˺ţ�"+info.getPayeeAcctNo() +"���տ���ƣ�"+info.getPayeeName()+"����"+DataFormat.formatDouble(3,info.getAmount())+"Ԫ��";
		}
		else if(TransType==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY)
		{
			remark= "��ʽ��"+OBConstant.SettRemitType.getName(info.getRemitType())+"������˺ţ�"+info.getPayerAcctNo()+"��������ƣ�"+info.getPayerName()+"���տ�˺ţ�"+info.getPayeeAcctNo() +"���տ���ƣ�"+info.getPayeeName()+"����"+DataFormat.formatDouble(3,info.getAmount())+"Ԫ��";
		}
		else if(TransType==OBConstant.SettInstrType.OPENFIXDEPOSIT)
		{
			remark= "���ڿ�����"+NameRef.getClientNameByID(info.getClientID())+"���� ����"+DataFormat.formatDouble(3,info.getAmount())+" ���ޣ�"+info.getFixedDepositTime()+"���� �����˻���"+NameRef.getNoLineAccountNo(info.getPayeeAcctNo());			
		}
		else if(TransType==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER)
		{
			remark= "����֧ȡ��"+NameRef.getClientNameByID(info.getClientID())+"֧ȡ ����"+DataFormat.formatDouble(3,info.getAmount())+" �����տ�˺ţ�"+NameRef.getNoLineAccountNo(info.getPayeeAcctNo())+"��Ϣ�տ�˺ţ�"+NameRef.getNoLineAccountNo(info.getInterestPayeeAcctNo());
		}
		else if(TransType==OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
		{
			remark= "�������棺"+NameRef.getClientNameByID(info.getClientID())+"���ڴ浥�ţ�"+info.getDepositNo()+" ����"+DataFormat.formatDouble(3,info.getAmount())+" �������ޣ�"+info.getSDepositBillPeriod()+"����";
		}
		else if(TransType==OBConstant.SettInstrType.OPENNOTIFYACCOUNT)
		{
			remark= "֪ͨ������"+info.getPayeeName()+"���� ����"+DataFormat.formatDouble(3,info.getAmount())+"��Ʒ�֣�"+DataFormat.formatInt(info.getNoticeDay()-10000,0)+"�죻֪ͨ����˻���"+NameRef.getNoLineAccountNo(info.getPayeeAcctNo())+"��";
		}
		else if(TransType==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
		{
			remark= "֪֧ͨȡ��"+info.getPayeeName()+"֧ȡ ����"+DataFormat.formatDouble(3,info.getAmount())+"��Ʒ�֣�"+DataFormat.formatInt(info.getNoticeDay()-10000,0)+"�죻֪ͨ����˻���"+NameRef.getNoLineAccountNo(info.getPayerAcctNo())+"��";
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
			returnstr = Constant.TransLogActionType.getName(ActionType)+"-"+OBConstant.SettInstrType.getName(info.getTransType()) ;
		} catch (Exception e) {
			returnstr="��ȡ�������ͳ����ˣ�";
			e.printStackTrace();
		}
		return returnstr;
	}
}

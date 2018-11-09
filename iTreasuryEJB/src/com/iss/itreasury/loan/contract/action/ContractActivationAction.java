package com.iss.itreasury.loan.contract.action;

import java.util.Map;

import com.iss.itreasury.loan.contract.bizlogic.ContractActivationBiz;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ContractActivationAction {
	
	ContractActivationBiz contractBiz =new ContractActivationBiz();
	
	/**
	 * �����ͬ���ң����ֺ�ͬ������
	 * @param lType �������࣬��ӦNotes��CODETYPE_LOANTYPE_CODE
	 * @param lCurrencyID ���ֱ�ʶ
	 * @param lOfficeID ���´���ʶ
	 * @param lUserID ��¼�˱�ʶ
	 * @param lContractIDFrom ��ͬ�����ʼ
	 * @param lContractIDTo ��ͬ��Ž���
	 * @param lClientID ��λ��ʶ
	 * @param dAmountFrom �����ʼ
	 * @param dAmountTo ������
	 * @return Collection
	 * @exception Exception
	 * @author zk 2012-01-07
	 */
	public PagerInfo queryContractActivationInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lType = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		long lUserID = -1;
		long lContractIDFrom = -1;
		long lContractIDTo = -1;
		long lClientID = -1;
		double dAmountFrom = 0.00;
		double dAmountTo = 0.00;
		try
		{
			if(map !=null && map.get("lloantype") != null){
				lType = Long.parseLong((String)map.get("lloantype"));
			}
			if(map !=null && map.get("currencyid") != null){
				lCurrencyID = Long.parseLong((String)map.get("currencyid"));
			}
			if(map !=null && map.get("officeid") != null){
				lOfficeID = Long.parseLong((String)map.get("officeid"));
			}
			if(map !=null && map.get("userid") != null){
				lUserID = Long.parseLong((String)map.get("userid"));
			}
			if(map !=null && map.get("lcontractidbeg") != null && !"".equals(map.get("lcontractidbeg"))){
				lContractIDFrom = Long.parseLong((String)map.get("lcontractidbeg"));
			}
			if(map !=null && map.get("lcontractidend") != null && !"".equals(map.get("lcontractidend"))){
				lContractIDTo = Long.parseLong((String)map.get("lcontractidend"));
			}
			if(map !=null && map.get("lclientid") != null && !"".equals(map.get("lclientid"))){
				lClientID = Long.parseLong((String)map.get("lclientid"));
			}
			if(map !=null && map.get("dloansumbeg") != null && !"".equals(map.get("dloansumbeg"))){
				dAmountFrom = Double.parseDouble(DataFormat.reverseFormatAmount((String)map.get("dloansumbeg")));
			}
			if(map !=null && map.get("dloansumend") != null && !"".equals(map.get("dloansumend"))){
				dAmountTo = Double.parseDouble(DataFormat.reverseFormatAmount((String)map.get("dloansumend")));
			}
			pagerInfo = contractBiz.queryContractActivationInfo(lType,lCurrencyID,lOfficeID,lUserID,
					lContractIDFrom,lContractIDTo,lClientID,dAmountFrom,dAmountTo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}

}

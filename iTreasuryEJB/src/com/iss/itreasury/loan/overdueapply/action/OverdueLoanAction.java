package com.iss.itreasury.loan.overdueapply.action;

import java.util.Map;

import com.iss.itreasury.loan.overdueapply.bizlogic.OverdueLoanBiz;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class OverdueLoanAction {
	
	OverdueLoanBiz overdueLoanBiz = new OverdueLoanBiz();
	
	/**
	 * ��ͬ���ң�����������ѯContractForm��LoanForm�� <br>
	 * lContractIDFrom��lContractIDTo�� ͬһ���͵ĺ�ͬ����ˮ�ŵĲ�����Ϊ��ѯ��Χ <br>
	 * ��Ҫ��¼������lUserID�Ĵ����ͬ
	 * @param lType ��������
	 * @param lCurrencyID ���ֱ�ʶ
	 * @param lOfficeID ���´���ʶ
	 * @param lUserID �����˱�ʶ
	 * @param lContractIDFrom ��ͬ�����ʼ
	 * @param lContractIDTo ��ͬ��Ž���
	 * @param lClientID ��λ��ʶ
	 * @param tsLoanStart ����������ʼ
	 * @param tsLoanEnd �������ڽ���
	 * @param lStatusID ��ͬ״̬
	 * @return PagerInfo pagerInfo
	 * @exception Exception
	 * @author zk 2012-01-04
	 */
	public PagerInfo queryContractByMultiOption(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lType = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		long lUserID = -1;
		long lContractIDFrom = -1;
		long lContractIDTo = -1;
		long lClientID = -1;
		String tsLoanStart = null;
		String tsLoanEnd = null;
		long lStatusID = -1;
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
			if(map !=null && map.get("tsdatefrom") != null){
				tsLoanStart = (String)map.get("tsdatefrom");
			}
			if(map !=null && map.get("tsdateto") != null){
				tsLoanEnd = (String)map.get("tsdateto");
			}
			if(map !=null && map.get("lcontractstatus") != null){
				lStatusID = Long.parseLong((String)map.get("lcontractstatus"));
			}
			pagerInfo = overdueLoanBiz.queryContractByMultiOption(lType,lCurrencyID,lOfficeID,lUserID,
					lContractIDFrom,lContractIDTo,lClientID,tsLoanStart,tsLoanEnd,lStatusID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}
	/**
	 * ���ݺ�ͬ�ƻ�id�������ڼƻ���Ϣ
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zk 2012-01-04
	 */
	public PagerInfo queryOverDuePlanByContractPlanID(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		long lContractPlanID = -1;
		long lCurrencyID = -1;
		long lOfficeID = -1;
		try
		{
			if(map !=null && map.get("lplanid") != null){
				lContractPlanID = Long.parseLong((String)map.get("lplanid"));
			}
			if(map !=null && map.get("currencyid") != null){
				lCurrencyID = Long.parseLong((String)map.get("currencyid"));
			}
			if(map !=null && map.get("officeid") != null){
				lOfficeID = Long.parseLong((String)map.get("officeid"));
			}
			
			pagerInfo = overdueLoanBiz.queryOverDuePlanByContractPlanID(lContractPlanID,lCurrencyID,lOfficeID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}

}

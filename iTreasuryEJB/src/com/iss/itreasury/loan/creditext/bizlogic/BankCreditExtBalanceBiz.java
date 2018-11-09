package com.iss.itreasury.loan.creditext.bizlogic;

import com.iss.itreasury.loan.creditext.dao.BankCreditExtBalanceDAO;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;

public class BankCreditExtBalanceBiz {
	//���㵱ǰ��ʹ�õ��������ۺ������ֵ��
	// id:			��������ID
	// companyCode:	��Ա��λ����
	// variety:		����Ʒ�֣��������ڴ���г��ڴ������֤���������Ŵ�֤���ͳжһ�Ʊ
	public double getBalance(long id, String companyCode, long variety) throws Exception {
		double balance = 0.0;
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		double amount1 = dao.getShortTermLoanBalance(id, companyCode);
		double amount2 = dao.getLongTermLoanBalance(id, companyCode);
		double amount3 = dao.getLetterCreditBalance(id, companyCode);
		double amount4 = dao.getLetterGuaranteeBalance(id, companyCode);
		double amount5 = dao.getCreditProveBalance(id, companyCode);
		double amount6 = dao.getAcceptBillBalance(id, companyCode);
		long[] varieties = new long[]{1,2,3,4,5,6};
		double[] amount = new double[]{(amount1 >= 0 ? 0 : Math.abs(amount1)),(amount2 >= 0 ? 0 : Math.abs(amount2)),
										(amount3 >= 0 ? 0 : Math.abs(amount3)),(amount4 >= 0 ? 0 : Math.abs(amount4)),
										(amount5 >= 0 ? 0 : Math.abs(amount5)),(amount6 >= 0 ? 0 : Math.abs(amount6))};
		double mixBalance = getMixBalance(id, companyCode, variety, varieties, amount);
		
		switch ((int)variety)
		{
			case (int)BankCreditVariety.SHORTTERMLOAN:
				balance = (amount1 >= 0 ? amount1 : 0) + mixBalance;
				break;
			case (int)BankCreditVariety.LONGTERMLOAN:
				balance = (amount2 >= 0 ? amount2 : 0) + mixBalance;
				break;
			case (int)BankCreditVariety.LETTEROFCREDIT:
				balance = (amount3 >= 0 ? amount3 : 0) + mixBalance;
				break;
			case (int)BankCreditVariety.LETTEROFIGUARANTEE:
				balance = (amount4 >= 0 ? amount4 : 0) + mixBalance;
				break;
			case (int)BankCreditVariety.PROVEOFCREDIT:
				balance = (amount5 >= 0 ? amount5 : 0) + mixBalance;
				break;
			case (int)BankCreditVariety.ACCEPTBILL:
				balance = (amount6 >= 0 ? amount6 : 0) + mixBalance;
				break;
			default:
				break;
		}
			
		return balance;
	}
	
	//�õ��ۺ����ŷֽ���(�ۺ������)
	public double getSplitedListAmount(long id, String companyCode, long variety) throws Exception {
		return new BankCreditExtBalanceDAO().getSplitedListAmount(id, companyCode,variety);
	}
	
	//�����ĳ������Ʒ�ֿ���ʹ�ã������˷���ϵ�����Ļ��ö�ȣ��ۺ�����ң�
	public double getMixBalance(long id, String companyCode, long variety, long[] varieties, double[] amount) throws Exception {
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		double[] splitedInfo = dao.getSplitedMixInfo(id, companyCode);
		double usedAmount = 0;
		
		if (splitedInfo != null && splitedInfo.length == 7 && varieties != null 
				&& amount != null && amount.length == varieties.length) {

			for (int i = 0; i < varieties.length; i++) {				
				//ʹ�ö��*����ϵ�����Ϳ��Ի�ԭ��ռ�ö��
				usedAmount += amount[i] * splitedInfo[(int)varieties[i]]/100;
			}
			//����ʣ���ȳ��Է���ϵ�������������ʹ�ö��
			return (splitedInfo[0]- usedAmount) / splitedInfo[(int)variety] * 100;
		}

		return 0;
	}
	
	//�����ĳ������Ʒ�ֿ���ʹ�õĻ��ö�ȣ��ۺ�����ң�
	public double getUsedMixAmount(long id, String companyCode, long[] varieties, double[] amount) throws Exception {
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		double[] splitedInfo = dao.getSplitedMixInfo(id, companyCode);
		double usedAmount = 0;
		
		if (splitedInfo != null && splitedInfo.length == 7 && varieties != null 
				&& amount != null && amount.length == varieties.length) {			
			for (int i = 0; i < varieties.length; i++) {
				//ʹ�ö��*����ϵ�����Ϳ��Ի�ԭ��ռ�ö��
				usedAmount += amount[i] * splitedInfo[(int)varieties[i]] / 100;
			}
			
			//����ʣ���ȳ��Է���ϵ�������������ʹ�ö��
			return  usedAmount;
		}

		return 0;
	}
	
	//	�õ����ж��ڴ�����ʹ�ý��(�ۺ������)
	public double getUsedShortTermLoan(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedShortTermLoan(id, companyCode);
	}
	
	//	�õ������г��ڴ�����ʹ�ý��(�ۺ������)
	public double getUsedLongTermLoan(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLongTermLoan(id, companyCode);
	}
	
	//	�õ�����֤��ʹ�ý��(�ۺ������)
	public double getUsedLetterCredit(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLetterCredit(id, companyCode);
	}
	
	//�õ�������ʹ�ý��(�ۺ������)
	public double getUsedLetterGuarantee(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLetterGuarantee(id, companyCode);
	}
	
	//	�õ��Ŵ�֤����ʹ�ý��(�ۺ������)
	public double getUsedCreditProve(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedCreditProve(id, companyCode);
	}
	
	//	�õ��жһ�Ʊ��ʹ�ý��(�ۺ������)
	public double getUsedAcceptBill(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedAcceptBill(id, companyCode);
	}
		
	//	���㵱ǰ�������ۺ�����ң�
	// id:			��������ID
	// companyCode:	��Ա��λ����
	// variety:		����Ʒ�֣��������ڴ���г��ڴ������֤���������Ŵ�֤���ͳжһ�Ʊ
	public double getAssureBalance(long id, String companyCode, long variety) throws Exception {
		double amount0 = 0;
		double amount1 = 0;
		double amount2 = 0;
		double amount3 = 0;
		double amount4 = 0;
		double amount5 = 0;
		double amount6 = 0;
		String varieties = "";
		String companys = "";
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		String[] result = dao.getAssureAmount(id, companyCode, variety);
		if (result != null) {
			amount0 = Double.parseDouble(result[0]);
			companys = result[1];
			varieties = result[2];
		}

		if (varieties.indexOf(Long.toString(BankCreditVariety.SHORTTERMLOAN)) >= 0) {
			amount1 = dao.getShortTermAssureAmount(id, companys);
		}
		
		if (varieties.indexOf(Long.toString(BankCreditVariety.LONGTERMLOAN)) >= 0) {
			amount2 = dao.getLongTermAssureAmount(id, companys);
		}
		
		if (varieties.indexOf(Long.toString(BankCreditVariety.LETTEROFCREDIT)) >= 0) {
			amount3 = dao.getLetterCreditAssureAmount(id, companys);
		}
		
		if (varieties.indexOf(Long.toString(BankCreditVariety.LETTEROFIGUARANTEE)) >= 0) {
			amount4 = dao.getLetterGuaranteeAssureAmount(id, companys);
		}
		
		if (varieties.indexOf(Long.toString(BankCreditVariety.PROVEOFCREDIT)) >= 0) {
			amount5 = dao.getCreditProveAssureAmount(id, companys);
		}
		
		if (varieties.indexOf(Long.toString(BankCreditVariety.ACCEPTBILL)) >= 0) {
			amount6 = dao.getAcceptBillAssureAmount(id, companys);
		}
			
		return amount0 - amount1 - amount2 - amount3 - amount4 - amount5 - amount6;
	}
	
	//	 �õ����ڴ���ĵ����ܶ�(�ۺ������)
	public double getShortTermAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getShortTermAssureAmount(id, companyCode);
	}
	
	//	�õ��г��ڴ���ĵ����ܶ�(�ۺ������)
	public double getLongTermAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLongTermAssureAmount(id, companyCode);
	}
	
	//	�õ�����֤�ĵ����ܶ�(�ۺ������)
	public double getLetterCreditAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLetterCreditAssureAmount(id, companyCode);
	}
	
	//	�õ������ĵ����ܶ�(�ۺ������)
	public double getLetterGuaranteeAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLetterGuaranteeAssureAmount(id, companyCode);
	}
	
	//	�õ��Ŵ�֤���ĵ����ܶ�(�ۺ������)
	public double getCreditProveAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getCreditProveAssureAmount(id, companyCode);
	}
	
	//	�õ��жһ�Ʊ�ĵ����ܶ�(�ۺ������)
	public double getAcceptBillAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getAcceptBillAssureAmount(id, companyCode);
	}
}

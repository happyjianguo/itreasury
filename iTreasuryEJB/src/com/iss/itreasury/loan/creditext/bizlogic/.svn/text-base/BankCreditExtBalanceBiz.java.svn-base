package com.iss.itreasury.loan.creditext.bizlogic;

import com.iss.itreasury.loan.creditext.dao.BankCreditExtBalanceDAO;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;

public class BankCreditExtBalanceBiz {
	//计算当前可使用的授信余额（折合人民币值）
	// id:			银行授信ID
	// companyCode:	成员单位代码
	// variety:		授信品种，包括短期贷款、中长期贷款、信用证、保函、信贷证明和承兑汇票
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
	
	//得到综合授信分解额度(折合人民币)
	public double getSplitedListAmount(long id, String companyCode, long variety) throws Exception {
		return new BankCreditExtBalanceDAO().getSplitedListAmount(id, companyCode,variety);
	}
	
	//计算出某种授信品种可以使用（除以了风险系数）的混用额度（折合人民币）
	public double getMixBalance(long id, String companyCode, long variety, long[] varieties, double[] amount) throws Exception {
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		double[] splitedInfo = dao.getSplitedMixInfo(id, companyCode);
		double usedAmount = 0;
		
		if (splitedInfo != null && splitedInfo.length == 7 && varieties != null 
				&& amount != null && amount.length == varieties.length) {

			for (int i = 0; i < varieties.length; i++) {				
				//使用额度*风险系数，就可以还原出占用额度
				usedAmount += amount[i] * splitedInfo[(int)varieties[i]]/100;
			}
			//混用剩余额度除以风险系数，计算出可以使用额度
			return (splitedInfo[0]- usedAmount) / splitedInfo[(int)variety] * 100;
		}

		return 0;
	}
	
	//计算出某种授信品种可以使用的混用额度（折合人民币）
	public double getUsedMixAmount(long id, String companyCode, long[] varieties, double[] amount) throws Exception {
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();
		double[] splitedInfo = dao.getSplitedMixInfo(id, companyCode);
		double usedAmount = 0;
		
		if (splitedInfo != null && splitedInfo.length == 7 && varieties != null 
				&& amount != null && amount.length == varieties.length) {			
			for (int i = 0; i < varieties.length; i++) {
				//使用额度*风险系数，就可以还原出占用额度
				usedAmount += amount[i] * splitedInfo[(int)varieties[i]] / 100;
			}
			
			//混用剩余额度除以风险系数，计算出可以使用额度
			return  usedAmount;
		}

		return 0;
	}
	
	//	得到银行短期贷款已使用金额(折合人民币)
	public double getUsedShortTermLoan(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedShortTermLoan(id, companyCode);
	}
	
	//	得到银行中长期贷款已使用金额(折合人民币)
	public double getUsedLongTermLoan(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLongTermLoan(id, companyCode);
	}
	
	//	得到信用证已使用金额(折合人民币)
	public double getUsedLetterCredit(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLetterCredit(id, companyCode);
	}
	
	//得到保函已使用金额(折合人民币)
	public double getUsedLetterGuarantee(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedLetterGuarantee(id, companyCode);
	}
	
	//	得到信贷证明已使用金额(折合人民币)
	public double getUsedCreditProve(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedCreditProve(id, companyCode);
	}
	
	//	得到承兑汇票已使用金额(折合人民币)
	public double getUsedAcceptBill(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getUsedAcceptBill(id, companyCode);
	}
		
	//	计算当前担保余额（折合人民币）
	// id:			银行授信ID
	// companyCode:	成员单位代码
	// variety:		授信品种，包括短期贷款、中长期贷款、信用证、保函、信贷证明和承兑汇票
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
	
	//	 得到短期贷款的担保总额(折合人民币)
	public double getShortTermAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getShortTermAssureAmount(id, companyCode);
	}
	
	//	得到中长期贷款的担保总额(折合人民币)
	public double getLongTermAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLongTermAssureAmount(id, companyCode);
	}
	
	//	得到信用证的担保总额(折合人民币)
	public double getLetterCreditAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLetterCreditAssureAmount(id, companyCode);
	}
	
	//	得到保函的担保总额(折合人民币)
	public double getLetterGuaranteeAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getLetterGuaranteeAssureAmount(id, companyCode);
	}
	
	//	得到信贷证明的担保总额(折合人民币)
	public double getCreditProveAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getCreditProveAssureAmount(id, companyCode);
	}
	
	//	得到承兑汇票的担保总额(折合人民币)
	public double getAcceptBillAssureAmount(long id, String companyCode) throws Exception {
		return new BankCreditExtBalanceDAO().getAcceptBillAssureAmount(id, companyCode);
	}
}

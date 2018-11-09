package com.iss.itreasury.loan.creditext.bizlogic;

import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtComplexDAO;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexQueryInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexResultInfo;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtComplexInfo;

public class BankCreditExtComplexBiz {
	//通过查询条件查询出符合条件的银行授信ID
	public List getBankCreditExtInfo(BankCreditExtComplexQueryInfo info) throws Exception {
		BankCreditExtComplexDAO dao = new BankCreditExtComplexDAO();
		List resultInfo = new ArrayList();
		
		try{
			dao.init();
//			dao.getConnection();
			List temp = dao.getBankCreditExtInfo(info);
			
			if (temp != null && !temp.isEmpty()) {
				for (int i = 0; i < temp.size(); i++) {
					BankCreditExtComplexInfo info1 = (BankCreditExtComplexInfo)temp.get(i);
					BankCreditExtComplexResultInfo info2 = new BankCreditExtComplexResultInfo();
					info2.setBankName(info1.getBankName());
					info2.setCompanyCode(info1.getCompanyCode());
					info2.setCompanyName(info1.getCompanyName());
					info2.setContractNo(info1.getContractNo());
					
					dao.putSplitMixAmount(info1.getId(), info1.getCompanyCode(), info2);
					dao.putSplitListAmount(info1.getId(), info1.getCompanyCode(), info2);
					dao.putUseListAmount(info1.getId(), info1.getCompanyCode(), info1.getReuse(), info2);
					putUseMixAmount(info2);
					resultInfo.add(info2);
//					System.out.print("短期贷款：");
//					System.out.println("\t已分配："+info2.getSplitedSLAmount()+"\t  占有："+info2.getUsingSLAmount()+"\t已使用："+info2.getUsedSLAmount());
//					System.out.print("中长期贷款：");
//					System.out.println("\t已分配"+info2.getSplitedLLAmount()+"\t   占有："+info2.getUsingLLAmount()+"\t已使用："+info2.getUsedLLAmount());
//					System.out.print("信用证：");
//					System.out.println("\t      已分配"+info2.getSplitedLCAmount()+"\t   占有："+info2.getUsingLCAmount()+"\t已使用："+info2.getUsedLCAmount());
//					System.out.print("保函：");
//					System.out.println("\t       已分配"+info2.getSplitedLGAmount()+"\t   占有："+info2.getUsingLGAmount()+"\t已使用："+info2.getUsedLGAmount());

				}
			}			
		}		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dao.closeConnection();
		}
		
		return resultInfo;
	}
	
	private void putUseMixAmount(BankCreditExtComplexResultInfo info) {
		double amount1 = info.getUsingSLAmount() - info.getSplitedSLAmount();
		double amount2 = info.getUsingLLAmount() - info.getSplitedLLAmount();
		double amount3 = info.getUsingLCAmount() - info.getSplitedLCAmount();
		double amount4 = info.getUsingLGAmount() - info.getSplitedLGAmount();
		double amount5 = info.getUsingCPAmount() - info.getSplitedCPAmount();
		double amount6 = info.getUsingABAmount() - info.getSplitedABAmount();
		double amount7 = (amount1>0?amount1:0)*info.getMixRiskCoefficient1()/100
						+(amount2>0?amount2:0)*info.getMixRiskCoefficient2()/100
						+(amount3>0?amount3:0)*info.getMixRiskCoefficient3()/100
						+(amount4>0?amount4:0)*info.getMixRiskCoefficient4()/100
						+(amount5>0?amount5:0)*info.getMixRiskCoefficient5()/100
						+(amount6>0?amount6:0)*info.getMixRiskCoefficient6()/100;
		info.setUsingMixAmount(amount7);
	}
	
//	public static void main(String[] args) {
//		BankCreditExtComplexBiz biz = new BankCreditExtComplexBiz();
//		BankCreditExtComplexQueryInfo info = new BankCreditExtComplexQueryInfo();
////		info.setYear("2006");
//		info.setId(5);
////		info.setStatus(2);
//		info.setOfficeId(2);
////		info.setBankName("工商银行");
////		info.setCompany("川菜");
////		info.setStartDate1("2006-11-1");
////		info.setStartDate2("2006-11-8");
////		info.setEndDate1("2006-11-3");
////		info.setEndDate2("2006-11-25");
//		
//		try {
//			biz.getBankCreditExtInfo(info);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}

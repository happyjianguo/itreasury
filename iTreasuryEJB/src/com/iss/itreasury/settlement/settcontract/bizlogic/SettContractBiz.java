/**
 * 
 */
package com.iss.itreasury.settlement.settcontract.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.settcontract.dao.SettContractDAO;
import com.iss.itreasury.util.Database;

/**
 * @author weihuang
 *
 */
public class SettContractBiz {
	/**
	 * 查找是否有重复合同编码
	 * 合同资料新增时使用
	 */
	public long checkCode(String code, long clientid)throws Exception
	{ long result=-1;
	  try {
		SettContractDAO dao=new SettContractDAO("Loan_ContractForm");
		result=dao.checkCode(code,clientid);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}
	public long updateDiscountDate(String discountdate, long contractid)throws Exception
	{  long result=-1;
	  	   	 	
		try {
			SettContractDAO dao=new SettContractDAO("Loan_ContractForm");
			result=dao.updateDiscountDate(discountdate,contractid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}	
}

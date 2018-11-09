package com.iss.itreasury.loan.assureloan.bizlogic;
/**
 * @author yyhe
 * Created on 2006-10-30
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.util.List;

import com.iss.itreasury.loan.assureloan.dao.AssureDao;
import com.iss.itreasury.loan.assureloan.dao.AssureQueryDao;
import com.iss.itreasury.loan.assureloan.dataentity.AssureInfo;
import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtBalanceDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;


public class AssureBiz {
	
	/**
	 * 查询，按info里的信息查询表loan_Assure
	 * @param info
	 * @return  返回分页组件
	 * @throws Exception
	 */
	public PageLoader findByCondition(AssureInfo info) throws Exception
	{
		PageLoader loader = null;
		AssureDao dao = new AssureDao();
		try
		{
			loader = dao.findByCondition(info);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	/**
	 * 根据内部流水号查询担保信息
	 * @param info
	 * @return  返回成功与否，成功 1  失败  -1
	 * @throws IException
	 */
	public AssureInfo findByCode(AssureInfo info) throws IException
	{
		AssureInfo assureInfo = null;
		try
		{
			AssureDao dao = new AssureDao();
			assureInfo = dao.findByCode(info);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}	
		return assureInfo;
	}
	
	/**
	 * 根据用户编号查询用户名称
	 * @param codes是一个以逗号隔开的用户编号组成的字符串
	 * @return
	 * @throws IException
	 */
	public List getClient(String codes) throws IException
	{		
		String[] tempNames = codes.split(",");
		List clientInfos = null;
		try
		{
			AssureDao dao = new AssureDao();
			clientInfos = dao.getClient(tempNames);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return clientInfos;
	}
	/**
	 * 新增，将info里的信息insert到表loan_Assure里
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws Exception
	 */
	public long add(AssureInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			AssureDao dao = new AssureDao();
			info.setCode(dao.getNextCode());
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	/**
	 * 取得担保已使用的金额
	 * 
	 * @param id 传入担保的id 
	 * @return
	 * @throws Exception 
	 */
	public double  getUsedAmount(AssureInfo info)  throws Exception
	{
		
		double amount1 = 0;
		double amount2 = 0;
		double amount3 = 0;
		double amount4 = 0;
		double amount5 = 0;
		double amount6 = 0;
		long id = info.getConferContractNo0();
		String varieties = info.getAssureType0();
		String companys = info.getWarranteeName0();
		BankCreditExtBalanceDAO dao = new BankCreditExtBalanceDAO();

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
			
		return amount1 + amount2 + amount3 + amount4 + amount5 + amount6;
	}
	
	/**
	 * 修改，将info里的信息update到表loan_Assure里
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws Exception
	 */
	public String modify(AssureInfo info) throws Exception
	{
		String message = "";
		try
		{
			AssureDao dao = new AssureDao();					
			double usedAmount = getUsedAmount(info);
			if( info.getAmount() < usedAmount)
			{
				message = "修改后的金额不能小于已使用的担保金额(折合人民币 "+ usedAmount +" 元)，请重新填写。";	
			}
			else
			{
				dao.modify(info);
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return message;
	}
	
	/**
	 * 判断该担保有没有被别的地方引用
	 * @param info
	 * @return   
	 * @throws IException 
	 */
	public boolean isUsed(AssureInfo info) throws IException 
	{
		boolean yes = false;
		try
		{
			AssureDao dao = new AssureDao();
			yes = dao.isUsed(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return yes;
	}
	
	/**
	 * 删除，将info里的信息update到表loan_Assure里
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws Exception
	 */
	public long delete(AssureInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			if(!this.isUsed(info))
			{	
				AssureDao dao = new AssureDao();
				lret = dao.delete(info);
			}
			else
			{
				lret = 0;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	

}

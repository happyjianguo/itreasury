package com.iss.itreasury.loan.loancontract_test.bizlogic;
import com.iss.itreasury.loan.loancontract_test.dao.LoanContractDAO;
import com.iss.itreasury.loan.loancontract_test.dataentity.LoanContractFormInfo;
import com.iss.itreasury.loan.loancontract_test.dataentity.LoanContractQueryInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
public class LoanContractBiz {
	
	private LoanContractDAO loanContractDao;
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	public LoanContractBiz() throws IException
	{
		loanContractDao = new LoanContractDAO();
	}
	
	/**
	 * 贷款合同保存
	 * @param loanContractInfo
	 * @return
	 * @throws IException
	 */
	public long saveLoanContractForm(LoanContractFormInfo conInfo) throws IException
	{
		long lReturn = -1;
		try
			{
				if(conInfo.getId()>0)
				{
					loanContractDao.update(conInfo);					
					lReturn = conInfo.getId();
				}
				else
				{
					lReturn = loanContractDao.add(conInfo);					
				}									
		   }
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}		
		return lReturn;
	}
	/**
	 * 贷款合同查询
	 * @param queryInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader findByQueryInfo(LoanContractQueryInfo queryInfo) throws IException
	{
		PageLoader pageLoader = null;
		try
		{
			pageLoader = loanContractDao.findByConditions(queryInfo);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return pageLoader;
	}
	/**
	 * 贷款合同删除
	 * @param 
	 * @return
	 * @throws IException
	 */
	public boolean delContractInfos(String[] ck) throws IException{
		  
		boolean result = false;
		
		try {
			
			loanContractDao.deleteByIds(ck);
			return true;
			
		   } catch (Exception e) {

			e.printStackTrace();
		}
		  return result;
	}
	
	/**
	 * 贷款合同明细查询
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public LoanContractFormInfo findInfoByID(LoanContractFormInfo conInfo) throws Exception
	{
		LoanContractFormInfo info = null;
		try
		{
			info = loanContractDao.findByID(conInfo);
		}
		catch(IException e)
		{
			e.printStackTrace();
			throw new IException("");
		}
		
		return info;
	}
}

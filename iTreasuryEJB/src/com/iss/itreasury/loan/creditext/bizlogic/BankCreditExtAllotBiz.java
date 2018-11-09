package com.iss.itreasury.loan.creditext.bizlogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.sql.Date;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtAllotDAO;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotInfo;
import com.iss.itreasury.loan.creditprove.dao.CreditProveDao;
import com.iss.itreasury.util.IException;

/**
* 银行授信分配与调整维护业务逻辑处理
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotBiz 
{
	//通过授信年度和授信合同号查询授信合同号id，为下一步查找提授信合同id
	public long getContractId (String contractno, String year) throws Exception
	{
		long lret = -1;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			lret = dao.getContractId(contractno ,year);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
		
	}
	// 通过授信年度和授信合同号查询该授信已经分配给各结算中心的情况
	//v001.jsp 的查询
	public Collection getBankCreditAllot(long id) throws Exception 
	{
		Vector vret = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			vret = dao.getBankCreditAllot(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	// 通过授信合同号、授信品种和结算中心编号查询授信分配信息
	//v002.jsp，点击金额进入的查询
	public BankCreditExtAllotInfo getBankCreditAllotInfo(long id, long variety, long officId, String officeName) throws Exception
	{
		BankCreditExtAllotInfo info = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			info = dao.getBankCreditAllotInfo( id,variety,officId,officeName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	// 删除授信分配记录，删除之前要判断该品种授信是否已经被分解到结算中心下的成员单位了
	//v003.jsp 点击删除调用此函数
	public String delete(long id, long viriety, long officeId, long lastModifier, String lastModifyDate) throws Exception 
	{
		String lret = "isSplit"; 
		boolean isSplit = true;
		boolean isDele = false;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			isSplit = dao.isSplited(id,viriety,officeId);
			if(!isSplit)//
			{
				lret = "failed";
				isDele = dao.delete(id,viriety,officeId,lastModifier,lastModifyDate);
				if(isDele)
				{
					lret = "success";
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	 新增银行授信分配与调整记录，判断是否已经分配以及是否超过余额
	// 新增授信情况
	public String insert(BankCreditExtAllotInfo info) throws Exception
	{
		String lret = "exist";
		boolean exist = true;//判断是否存在
		boolean isOverBalance = true;//是否超过余额
		boolean isInsert = false;//是否插入成功
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			//判断该品种是否已经分配给了该结算中心
			exist = dao.exist(info.getId(),info.getVariety(),info.getOfficeid());
			if(!exist)
			{
				lret = "inOver";
				//若没有分配给该结算中心,则看是否超过余额
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),-1);
				if(!isOverBalance)
				{
					//如果没有超过余额就进行添加信息
					lret = "InsertFailed";
					isInsert = dao.insert(info);
					if(isInsert)
					{
						lret = "InsertSuccess";
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	通过授信合同号id得到授信信息
	//  v002。jsp点击新增进入，通过授信合同号id查询出该授信合同号的内容
	public BankCreditExtAllotInfo getBankCreditInfo(long id) throws Exception 
	{
		BankCreditExtAllotInfo info = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			info = dao.getBankCreditInfo(id);
			info.setBalances ( dao.getAllBalance(id) );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	//不同的授信品种，得到所有授信金额的币种
	public HashMap getAllCurrencytype (long id ) throws Exception 
	{
		HashMap hm = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			hm = dao.getAllCurrencytype(id);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return hm;
	}
	
	// v003.jsp 点击保存后调用此函数
	//	修改银行授信分配与调整记录，需要判断已经几点：
	//  授信分配信息修改前，判断分配金额是否超过了可使用余额
	//  授信分配信息修改前，判断分配金额一定要大于该结算中心该品种授信已经分解的金额总和，调用getHasSplit（）方法。
	public String modify(BankCreditExtAllotInfo info) throws Exception
	{
		String lret = "notEnough";//默认分配金额小于已经分解
		double hasSplitAmount = 0;
		boolean isOverBalance = true;
		boolean isModify = false;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();	
			hasSplitAmount = dao.getHasSplit(info.getId(),info.getVariety(),info.getOfficeid());
			if (info.getAmount() - hasSplitAmount >0 )
			{
				lret = "over";//默认输入金额大于余额
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),info.getOfficeid());
				if(!isOverBalance)
				{
					lret = "ModifyFailed";//修改失败
					isModify = dao.modify(info);
					if (isModify)
					{
						lret = "ModifySuccess";//修改成功
					}
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	判断分配的额度是否超过了余额
	// 传入参数，金额，授信合同id ，授信品种
	// 返回true 超过余额，返回false 没有超过余额
	private boolean isOverBalance(double amount, long id, long variety, long officeId) throws Exception 
	{
		boolean lret = true ;
		double balance = 0;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			balance = dao.getBalance( id,variety,officeId);
			if(balance - amount >= 0)
			{
				lret = false;
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

package com.iss.itreasury.project.gzbfcl.settlement.bizlogic;

import com.iss.itreasury.project.gzbfcl.settlement.dao.FD_BankAccountDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FD_OrganizationDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FoundsdispatchDetailDao;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_BankAccountInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_OrganizationInfo;
import com.iss.itreasury.settlement.account.dao.Sett_ExternalAccountDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class FD_BankAccountBiz {
	/*
	 * 开户行账户保存方法
	 * 新增时，id<0 比较数据库中是否有重复记录
	 * 					没有的话，新增这条开户行账户信息
	 * 					有的话，返回数据库已有的开户行账户的id
	 * 
	 * id>0  去数据库查出这个id对应的开户行开户信息，对比一下是否和要保存的信息是一样的，
	 * 								不是的话，将要保存的信息id置为0，新增这条开户行信息
	 * 								是的话，返回数据库中要保存的信息的id
	 * 
	 * */
	public long saveFD_BankAccountInfo(FD_BankAccountInfo binfo)throws Exception {
		
		FD_BankAccountDao dao=new FD_BankAccountDao();
		long FD_BankAccountID=-1;
		long bFD_BankAccountID=-1;
		if(binfo.getId()<0){
			bFD_BankAccountID=dao.findFD_BankAccountInfo(binfo.getBankAccountCode(), binfo.getBankName(), binfo.getOfficeID());
			
			if (bFD_BankAccountID<0){
				
				FD_BankAccountID=dao.add(binfo);
			}
			else{
				
				FD_BankAccountID=bFD_BankAccountID;
			}
			
		}
		else{
			FD_BankAccountInfo info=new FD_BankAccountInfo();
		
		info=(FD_BankAccountInfo)dao.findByID(binfo.getId(), FD_BankAccountInfo.class);
			
			if(!(binfo.getBankAccountCode().equals(info.getBankAccountCode())
					&& binfo.getBankName().equals(info.getBankName())
					&& binfo.getOfficeID()==info.getOfficeID())){
				
				binfo.setId(-1);
				FD_BankAccountID=dao.add(binfo);
	
			}
			else{
				
				FD_BankAccountID=binfo.getId();
				
			}
			
		}
		return FD_BankAccountID;
		
		
	}
	
	/*
	 * 开户行账户修改方法
	 * 将要修改的开户行对应id的信息的状态置为0
	 * 再将要修改的开户行信息id置为0
	 * 调用保存方法，新增一条开户行信息。
	 * 	返回新增的开户行信息的id				
	 * 
	 * */
	public long ModifyFD_BankAccountInfo(FD_BankAccountInfo binfo)throws Exception {
		
		FD_BankAccountDao dao=new FD_BankAccountDao();
		long FD_BankAccountID=-1;
		if(binfo.getId()>0){
			
			dao.updateStatus(binfo.getId(), Constant.RecordStatus.INVALID);
			binfo.setId(-1);
			FD_BankAccountID=this.saveFD_BankAccountInfo(binfo);
		}
		return FD_BankAccountID;
		
		
	}
	
	public FD_BankAccountInfo findbyID(long ID) throws Exception {
		
		
		FD_BankAccountDao dao=new FD_BankAccountDao();
		
		FD_BankAccountInfo info=new FD_BankAccountInfo();
		
		
		info=(FD_BankAccountInfo)dao.findByID(ID, FD_BankAccountInfo.class);
		
		return info;
		
		
	}
	
	
	/**
	 * 删除开户行账户信息
	 * 
	 * @param lID
	 *            开户行账户ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 * 
	 * 判断这条开户行账户信息是否被资金调度令使用
	 * 
	 * 如果已被使用，返回0
	 * 
	 * 如果没被使用，将这条开户行账户信息状态置为0,返回1
	 */
	public long deleteBankAccount(long ID) throws IException
	{
		long lReturn = -1;
		FD_BankAccountDao dao = new FD_BankAccountDao();
		try
		{
			FoundsdispatchDetailDao fdao=new FoundsdispatchDetailDao();
			
			long isUsed=fdao.findFoundsDetailbyBankAccountID(ID);
			if (isUsed>0)
			{
				
				lReturn=0;
			}
			else if(isUsed==0){
				dao.updateStatus(ID, Constant.RecordStatus.INVALID);
				lReturn=1;
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, e.getMessage(), e);
		}
		return lReturn;
	}
}

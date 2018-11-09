package com.iss.itreasury.project.gzbfcl.settlement.bizlogic;


import java.util.Collection;

import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_OrganizationInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FD_BankAccountDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FD_OrganizationDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FoundsdispatchDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FoundsdispatchDetailDao;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;



public class FD_OrganizationBiz {
	/*
	 * 单位名称保存方法
	 * 新增时，id<0 比较数据库中是否有重复记录
	 * 					没有的话，新增这条单位名称信息
	 * 					有的话，返回数据库已有的单位名称的id
	 * 
	 * id>0  去数据库查出这个id对应的单位名称信息，对比一下是否和要保存的信息是一样的，
	 * 								不是的话，将要保存的信息id置为0，新增这条单位名称信息
	 * 								是的话，返回数据库中要保存的信息的id
	 * 
	 * */
	
	public long saveFD_OrganizationInfo(FD_OrganizationInfo oinfo)throws Exception {
		
		FD_OrganizationDao dao=new FD_OrganizationDao();
		long FD_OrganizationID=-1;
		long bFD_OrganizationID=-1;
		if(oinfo.getId()<0){
			
			bFD_OrganizationID=dao.findFD_OrganizationInfo(oinfo.getOrganizationName(), oinfo.getOfficeID());
			if(bFD_OrganizationID<0){
				
				FD_OrganizationID=dao.add(oinfo);
			}
			else{
				
				FD_OrganizationID=bFD_OrganizationID;
			}
			
		}
		else{
		FD_OrganizationInfo info=new FD_OrganizationInfo();
		
		info=(FD_OrganizationInfo)dao.findByID(oinfo.getId(), FD_OrganizationInfo.class);
			
			if(!oinfo.getOrganizationName().equals(info.getOrganizationName())
				&& oinfo.getOfficeID()==info.getOfficeID()){
				
				oinfo.setId(-1);
				FD_OrganizationID=dao.add(oinfo);
	
			}
			else{
				
				FD_OrganizationID=oinfo.getId();
				
			}
			
		}
		return FD_OrganizationID;
		
		
	}
	
	/*
	 * 单位名称修改方法
	 * 将要修改的单位名称对应id的信息的状态置为0
	 * 再将要修改的单位名称信息id置为0
	 * 调用保存方法，新增一条单位名称信息。
	 * 	返回新增的单位名称信息的id				
	 * 
	 * */
	public long ModifyFD_OrganizationInfo(FD_OrganizationInfo oinfo)throws Exception {
		
		FD_OrganizationDao dao=new FD_OrganizationDao();
		long FD_OrganizationID=-1;
		if(oinfo.getId()>0){
			
			dao.updateStatus(oinfo.getId(), Constant.RecordStatus.INVALID);
			oinfo.setId(-1);
			FD_OrganizationID=this.saveFD_OrganizationInfo(oinfo);
		}
		return FD_OrganizationID;
		
		
	}
	
	/**
	 * 查找所有单位名称信息（此方法没被使用）
	 * 
	 */
	public  Collection findAllOrganization(long OfficeID) throws Exception{
		
		FD_OrganizationDao dao=new FD_OrganizationDao();
		Collection coll= dao.findAllOrganization(OfficeID);
		
		return coll;
		
	}
	/**
	 * 批量删除单位名称信息（此方法没被使用）前台将所有要删除的id，拼成了字符串，传到后台 in（？）中
	 * 
	 */
	public long deleteOrganizations(String lID,long OfficeID) throws Exception
	{
		try
		{
			FD_OrganizationDao dao=new FD_OrganizationDao();
			return dao.updateOrganizationStatus(lID,OfficeID,Constant.RecordStatus.INVALID);
		
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	
	public FD_OrganizationInfo findbyID(long ID) throws Exception {
		
		FD_OrganizationDao dao=new FD_OrganizationDao();
		FD_OrganizationInfo info=new FD_OrganizationInfo();
		
		info=(FD_OrganizationInfo)dao.findByID(ID, FD_OrganizationInfo.class);
		
		return info;
		
	}
	
	/**
	 * 删除单位名称信息
	 * 
	 * @param lID
	 *            单位名称ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 * 
	 * 判断这条单位名称信息是否被资金调度令使用
	 * 
	 * 如果已被使用，返回0
	 * 
	 * 如果没被使用，将这条单位名称信息状态置为0,返回1
	 */
	public long deleteOrganization(long ID) throws IException
	{
		long lReturn = -1;
		FD_OrganizationDao dao = new FD_OrganizationDao();
		try
		{
			
			FoundsdispatchDetailDao fdao=new FoundsdispatchDetailDao();
			long isUsed=fdao.findFoundsDetailbyOrganizationID(ID);
			
			if (isUsed>0){
				
				lReturn=0;
				
			}
			else {
				
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

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
	 * ��λ���Ʊ��淽��
	 * ����ʱ��id<0 �Ƚ����ݿ����Ƿ����ظ���¼
	 * 					û�еĻ�������������λ������Ϣ
	 * 					�еĻ����������ݿ����еĵ�λ���Ƶ�id
	 * 
	 * id>0  ȥ���ݿ������id��Ӧ�ĵ�λ������Ϣ���Ա�һ���Ƿ��Ҫ�������Ϣ��һ���ģ�
	 * 								���ǵĻ�����Ҫ�������Ϣid��Ϊ0������������λ������Ϣ
	 * 								�ǵĻ����������ݿ���Ҫ�������Ϣ��id
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
	 * ��λ�����޸ķ���
	 * ��Ҫ�޸ĵĵ�λ���ƶ�Ӧid����Ϣ��״̬��Ϊ0
	 * �ٽ�Ҫ�޸ĵĵ�λ������Ϣid��Ϊ0
	 * ���ñ��淽��������һ����λ������Ϣ��
	 * 	���������ĵ�λ������Ϣ��id				
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
	 * �������е�λ������Ϣ���˷���û��ʹ�ã�
	 * 
	 */
	public  Collection findAllOrganization(long OfficeID) throws Exception{
		
		FD_OrganizationDao dao=new FD_OrganizationDao();
		Collection coll= dao.findAllOrganization(OfficeID);
		
		return coll;
		
	}
	/**
	 * ����ɾ����λ������Ϣ���˷���û��ʹ�ã�ǰ̨������Ҫɾ����id��ƴ�����ַ�����������̨ in��������
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
	 * ɾ����λ������Ϣ
	 * 
	 * @param lID
	 *            ��λ����ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 * 
	 * �ж�������λ������Ϣ�Ƿ��ʽ������ʹ��
	 * 
	 * ����ѱ�ʹ�ã�����0
	 * 
	 * ���û��ʹ�ã���������λ������Ϣ״̬��Ϊ0,����1
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

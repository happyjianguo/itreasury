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
	 * �������˻����淽��
	 * ����ʱ��id<0 �Ƚ����ݿ����Ƿ����ظ���¼
	 * 					û�еĻ������������������˻���Ϣ
	 * 					�еĻ����������ݿ����еĿ������˻���id
	 * 
	 * id>0  ȥ���ݿ������id��Ӧ�Ŀ����п�����Ϣ���Ա�һ���Ƿ��Ҫ�������Ϣ��һ���ģ�
	 * 								���ǵĻ�����Ҫ�������Ϣid��Ϊ0������������������Ϣ
	 * 								�ǵĻ����������ݿ���Ҫ�������Ϣ��id
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
	 * �������˻��޸ķ���
	 * ��Ҫ�޸ĵĿ����ж�Ӧid����Ϣ��״̬��Ϊ0
	 * �ٽ�Ҫ�޸ĵĿ�������Ϣid��Ϊ0
	 * ���ñ��淽��������һ����������Ϣ��
	 * 	���������Ŀ�������Ϣ��id				
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
	 * ɾ���������˻���Ϣ
	 * 
	 * @param lID
	 *            �������˻�ID
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 * 
	 * �ж������������˻���Ϣ�Ƿ��ʽ������ʹ��
	 * 
	 * ����ѱ�ʹ�ã�����0
	 * 
	 * ���û��ʹ�ã��������������˻���Ϣ״̬��Ϊ0,����1
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

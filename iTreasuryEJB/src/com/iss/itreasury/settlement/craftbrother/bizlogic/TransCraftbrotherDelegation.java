package com.iss.itreasury.settlement.craftbrother.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.craftbrother.dao.TransCraInterestPreDrawDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.SumCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;


/**
 * ͬҵ���׽��㴦��
 * @author qhzhou
 *
 */
public class TransCraftbrotherDelegation {
	
	TransCraftbrother facede = null;
	
	public TransCraftbrotherDelegation() throws RemoteException
	{
		try
		{
			TransCraftbrotherHome home = (TransCraftbrotherHome) EJBHomeFactory.getFactory().lookUpHome(TransCraftbrotherHome.class);
			facede = (TransCraftbrother) home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	
	/** �����ݴ���޸��ݴ�ӿ� */
	public long tempSave(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.tempSave(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** �����������޸ı���ӿ� */
	public long save(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.save(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ����ɾ���ӿ� */
	public long delete(TransCraftbrotherInfo info) throws IException{
		
		try{
			return facede.delete(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ID���ҽ��� */
	public TransCraftbrotherInfo findByID(long lId) throws IException {
		try{
			return facede.findByID(lId);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ƥ����ҽ��� */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo info)throws IException {
		try{
			return facede.match(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ���Ӳ��ҽ��� */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws IException {
		try{
			return facede.linkSearch(lQueryPurpose,lTransactionTypeId,lStatusId, lUserId, nOrderIndex, lIsDesc);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ���׸��˽ӿ� */
	public long check(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.check(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ����ȡ�����˽ӿ� */
	public long cancelCheck(TransCraftbrotherInfo info) throws IException{
		try{
			return facede.cancelCheck(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/**ͬҵ��Ϣ����ӿ�*/
	public Collection calcInterest(CraInterestCalcInfo calcInfo) throws IException{
		try{
			return facede.calcInterest(calcInfo);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	
	/** ͬҵ���ύ����������ӿ� */
	public long saveInterestPreDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.saveInterestPreDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ͬҵ���ύ��ɾ���ӿ� */
	public long deleteInterestPreDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.deleteInterestPreDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}

	/** ����ͬҵ��Ϣ���ύ�ײ��ҽӿ� */
	public PageLoader searchTransInterestPerDraw(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return facede.searchTransInterestPerDraw(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** ����ͬҵ��Ϣ���ύ�ײ������Ϣ */
	public SumCraInterestPreDrawInfo getSumCraPreDrawInterest(TransCraInterestPreDrawInfo info) throws IException{
		try{
			return new TransCraInterestPreDrawDAO().getSumCraPreDrawInterest(info);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** ����ͬҵ��Ϣ���ύ�׵��ʲ�ѯ */
	public TransCraInterestPreDrawInfo findCraInterestPreDrawById(long id) throws IException{
		try{
			return (TransCraInterestPreDrawInfo)(new TransCraInterestPreDrawDAO().findByID(id, TransCraInterestPreDrawInfo.class));
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
	/** �������ҽ��� */
	public TransCraftbrotherInfo findByTransNo(String sTransNo) throws IException {
		try{
			return facede.findByTransNo(sTransNo);
		}catch(Exception e){
			throw new IException(e.getMessage(),e);
		}
	}
}

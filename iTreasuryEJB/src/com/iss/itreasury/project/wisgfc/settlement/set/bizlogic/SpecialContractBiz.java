package com.iss.itreasury.project.wisgfc.settlement.set.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.set.dao.SpecialContractDao;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SpecialContractInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xlchang 2010-11-15
 *  ��Լ��ͬά�����ⷽ��ʵ����
 */
public class SpecialContractBiz {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private SpecialContractDao dao = new SpecialContractDao();
	
	/**
	 * ��ѯ��Լ��ͬ��Ϣ
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition(SpecialContractInfo info) throws IException{
		try {
			return dao.findByCondition(info);
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("��ѯ��Լ��ͬʧ�ܣ�");
		}
	}	
	
	/**
	 * ����������ѯ��Լ��ͬ��Ϣ
	 * @param officeId     ���´�
	 * @param currencyId   ����
	 * @return
	 * @throws IException
	 */
	public Collection getSpecialContractInfos(long officeId, long currencyId) throws IException {
		SpecialContractInfo info = new SpecialContractInfo();			
		info.setNOfficeID(officeId);
		info.setNCurrencyID(currencyId);
		info.setNStatusID(Constant.RecordStatus.VALID);	
		return findByCondition(info);
		
	}
	/**
	 * ����������ѯ��Լ��ͬ��Ϣ�����ӽ������ȡһ����¼
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public SpecialContractInfo getSpecialContractInfo(SpecialContractInfo info) throws IException {
		SpecialContractInfo result = null;
		Collection c = null;
		Iterator it = null;		
		c = findByCondition(info);
		if (c!= null && c.size() > 0) {
			it = c.iterator();			
			if (it.hasNext()) {
				result = (SpecialContractInfo)it.next();				
			}			
		}		
		return result;
	}


	/**
	 * ������Լ��ͬ��Ϣ
	 * @param info
	 * @throws IException
	 */
	public void add(SpecialContractInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				dao.add(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("����ʧ�ܣ�");
		} 		
	}
	
	/**
	 * ������Լ��ͬ��Ϣ
	 * @param info
	 * @throws IException
	 */
	public void update(SpecialContractInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				dao.update(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("����ʧ�ܣ�");
		} 		
	}
	
	/**
	 * ������Լ��ͬ��Ϣ
	 * @param info
	 * @throws IException
	 */
	public void del(SpecialContractInfo info) throws IException {
		try {	
			//if (usedCheck(info)) {
				dao.update(info);
			//}			
		}catch (ITreasuryDAOException e) {
			throw new IException("ɾ��ʧ�ܣ�");
		} 		
	}
	
	/**
	 * ��Լ��ͬ��ź����Ƶ�У��
	 * @param info
	 * @return
	 * @throws IException
	 */
	public boolean repeatCheck(SpecialContractInfo info) throws IException{
		boolean flag = false;
		SpecialContractInfo queryInfo = new SpecialContractInfo();
		queryInfo.setNOfficeID(info.getNOfficeID());
		queryInfo.setNCurrencyID(info.getNCurrencyID());
		queryInfo.setSCode(info.getSCode());
		queryInfo.setNStatusID(Constant.RecordStatus.VALID);
		//У���Ų����ظ�	
		SpecialContractInfo rInfo = getSpecialContractInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("��ͬ���Ѵ��ڣ�������¼�룡");
		}
		
		flag = true;
		return flag;
	}
	
	/**
	 * ��Լ��ͬ��ź����Ƶ�У��
	 * @param info
	 * @return
	 * @throws IException
	 */
	public boolean usedCheck(SpecialContractInfo info) throws IException{
		boolean flag = false;
		SpecialContractInfo queryInfo = new SpecialContractInfo();
		queryInfo.setId(info.getId());		
		queryInfo.setNStatusID(Constant.RecordStatus.VALID);
		//У���Ų����ظ�	
		SpecialContractInfo rInfo = getSpecialContractInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("��������ʹ�ã�������ɾ����");
		}
		flag = true;
		return flag;
	}
	

	
	/**
	 * �����Լ��ͬ����
	 * @param info
	 * @return
	 * @throws IException
	 */
	public String getNewCode(SpecialContractInfo info) throws IException{
		try {
			return dao.getNewCode(info);		 
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("��������ͱ���ʧ�ܣ�");
		}
	}
	
	/**
	 * ���������Լ��ͬid
	 * @param officeId      ���´�id
	 * @param currencyId    ����id
	 * @return
	 * @throws IException
	 */
	public long[] getOPRMSubTypeIDs(long officeId, long currencyId) {
		Collection c = null;
		Iterator it = null;
		long[] ids = null;
		SpecialContractInfo info = null;
		
		try {
			c = getSpecialContractInfos(officeId,currencyId);
			if (c!= null && c.size() > 0) {
				ids = new long[c.size()];
				it = c.iterator();
				for(int i=0;it.hasNext();i++) {
					info = (SpecialContractInfo)it.next();
					ids[i] = info.getId();
				}
			}			
		} catch (IException e) {
			e.printStackTrace();
			log.error("��������ͱ���ʧ�ܣ�");
		}
		
		return ids;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @param info ��ѯ����
	 * @return
	 * @throws Exception
	 */
	public PageLoader querySpecialContractInfo(SpecialContractInfo info) throws IException
	{
		PageLoader pageLoader = null;
		
		try {	
			pageLoader = dao.querySpecialContractInfo(info);
		} catch (Exception e) {
			throw new IException("��ѯί���տ�ҵ��ʧ�ܣ�",e);
		}
		
		return pageLoader;
	}
}

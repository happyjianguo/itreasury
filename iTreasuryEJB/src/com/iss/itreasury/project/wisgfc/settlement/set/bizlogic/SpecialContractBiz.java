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
 *  特约合同维护对外方法实现类
 */
public class SpecialContractBiz {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private SpecialContractDao dao = new SpecialContractDao();
	
	/**
	 * 查询特约合同信息
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition(SpecialContractInfo info) throws IException{
		try {
			return dao.findByCondition(info);
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("查询特约合同失败！");
		}
	}	
	
	/**
	 * 根据条件查询特约合同信息
	 * @param officeId     办事处
	 * @param currencyId   币种
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
	 * 根据条件查询特约合同信息，并从结果集中取一条记录
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
	 * 保存特约合同信息
	 * @param info
	 * @throws IException
	 */
	public void add(SpecialContractInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				dao.add(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 保存特约合同信息
	 * @param info
	 * @throws IException
	 */
	public void update(SpecialContractInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				dao.update(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 保存特约合同信息
	 * @param info
	 * @throws IException
	 */
	public void del(SpecialContractInfo info) throws IException {
		try {	
			//if (usedCheck(info)) {
				dao.update(info);
			//}			
		}catch (ITreasuryDAOException e) {
			throw new IException("删除失败！");
		} 		
	}
	
	/**
	 * 特约合同编号和名称的校验
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
		//校验编号不能重复	
		SpecialContractInfo rInfo = getSpecialContractInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("合同号已存在，请重新录入！");
		}
		
		flag = true;
		return flag;
	}
	
	/**
	 * 特约合同编号和名称的校验
	 * @param info
	 * @return
	 * @throws IException
	 */
	public boolean usedCheck(SpecialContractInfo info) throws IException{
		boolean flag = false;
		SpecialContractInfo queryInfo = new SpecialContractInfo();
		queryInfo.setId(info.getId());		
		queryInfo.setNStatusID(Constant.RecordStatus.VALID);
		//校验编号不能重复	
		SpecialContractInfo rInfo = getSpecialContractInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("子类型已使用，不允许删除！");
		}
		flag = true;
		return flag;
	}
	

	
	/**
	 * 获得特约合同编码
	 * @param info
	 * @return
	 * @throws IException
	 */
	public String getNewCode(SpecialContractInfo info) throws IException{
		try {
			return dao.getNewCode(info);		 
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("获得子类型编码失败！");
		}
	}
	
	/**
	 * 获得所有特约合同id
	 * @param officeId      办事处id
	 * @param currencyId    币种id
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
			log.error("获得子类型编码失败！");
		}
		
		return ids;
	}
	
	/**
	 * 分页查询
	 * @param info 查询条件
	 * @return
	 * @throws Exception
	 */
	public PageLoader querySpecialContractInfo(SpecialContractInfo info) throws IException
	{
		PageLoader pageLoader = null;
		
		try {	
			pageLoader = dao.querySpecialContractInfo(info);
		} catch (Exception e) {
			throw new IException("查询委托收款业务失败！",e);
		}
		
		return pageLoader;
	}
}

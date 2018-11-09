package com.iss.itreasury.project.wisgfc.settlement.set.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.set.dao.OPRMSubTypeDao;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.OPRMSubTypeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author Administrator
 * 多借多贷子类型业务逻辑层
 */
public class OPRMSubTypeBiz {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private OPRMSubTypeDao dao = new OPRMSubTypeDao();
	
	/**
	 * 查询多借多贷子类型信息
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition(OPRMSubTypeInfo info) throws IException{
		try {
			return dao.findByCondition(info);
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("查询多借多贷子类型失败！");
		}
	}
	
	/**
	 * 根据页面上的查询条件查找多借多贷子类型信息
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByQueryCondition(OPRMSubTypeInfo info) throws IException{
		try {
			return dao.findByQueryCondition(info);
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("查询多借多贷子类型失败！");
		}
	}
	
	/**
	 * 根据条件查询多借多贷子类型信息
	 * @param officeId     办事处
	 * @param currencyId   币种
	 * @return
	 * @throws IException
	 */
	public Collection getOPRMSubTypeInfos(long officeId, long currencyId) throws IException {
		OPRMSubTypeInfo info = new OPRMSubTypeInfo();			
		info.setNOfficeID(officeId);
		info.setNCurrencyID(currencyId);
		info.setNStatusID(Constant.RecordStatus.VALID);	
		return findByCondition(info);
		
	}
	/**
	 * 根据条件查询多借多贷子类型信息，并从结果集中取一条记录
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public OPRMSubTypeInfo getOPRMSubTypeInfo(OPRMSubTypeInfo info) throws IException {
		OPRMSubTypeInfo result = null;
		Collection c = null;
		Iterator it = null;		
		c = findByCondition(info);
		if (c!= null && c.size() > 0) {
			it = c.iterator();			
			if (it.hasNext()) {
				result = (OPRMSubTypeInfo)it.next();				
			}			
		}		
		return result;
	}


	/**
	 * 保存多借多贷子类型信息
	 * @param info
	 * @throws IException
	 */
	public void add(OPRMSubTypeInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				dao.add(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 保存多借多贷子类型信息
	 * @param info
	 * @throws IException
	 */
	public void update(OPRMSubTypeInfo info) throws IException {
		try {	
			if (repeatCheck(info)) {
				if (usedCheck(info) == false) {
					throw new IException("子类型已使用，不允许修改！");
				}
				dao.update(info);
			}			
		}catch (ITreasuryDAOException e) {
			throw new IException("保存失败！");
		} 		
	}
	
	/**
	 * 保存多借多贷子类型信息
	 * @param info
	 * @throws IException
	 */
	public void del(OPRMSubTypeInfo info) throws IException {
		try {	
			if (usedCheck(info)) {
				dao.update(info);
			} else {
				throw new IException("子类型已使用，不允许删除！");
			}	
		}catch (ITreasuryDAOException e) {
			throw new IException("删除失败！");
		} 		
	}
	
	/**
	 * 多借多贷子类型编号和名称的校验
	 * @param info
	 * @return
	 * @throws IException
	 */
	public boolean repeatCheck(OPRMSubTypeInfo info) throws IException{
		boolean flag = false;
		OPRMSubTypeInfo queryInfo = new OPRMSubTypeInfo();
		queryInfo.setNOfficeID(info.getNOfficeID());
		queryInfo.setNCurrencyID(info.getNCurrencyID());
		queryInfo.setSCode(info.getSCode());
		queryInfo.setNStatusID(Constant.RecordStatus.VALID);
		//校验编号不能重复	
		OPRMSubTypeInfo rInfo = getOPRMSubTypeInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("子类型编号已存在，请重新录入！");
		}
		
		queryInfo = new OPRMSubTypeInfo();
		queryInfo.setNOfficeID(info.getNOfficeID());
		queryInfo.setNCurrencyID(info.getNCurrencyID());
		queryInfo.setSName(info.getSName());
		queryInfo.setNStatusID(Constant.RecordStatus.VALID);
		
		//校验名称不能重复		
		rInfo = getOPRMSubTypeInfo(queryInfo);
		if (rInfo != null && rInfo.getId() != info.getId()){
			throw new IException("子类型名称已存在，请重新录入！");
		}
		flag = true;
		return flag;
	}
	
	/**
	 * 多借多贷子类型编号和名称的校验
	 * @param info
	 * @return
	 * @throws IException
	 */
	public boolean usedCheck(OPRMSubTypeInfo info) throws IException{
		boolean flag = false;		
		try {
			flag = dao.usedCheck(info);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new IException("查询子类型是否已使用失败！");
		}
		return flag;
	}
	

	
	/**
	 * 获得多借多贷子类型编码
	 * @param info
	 * @return
	 * @throws IException
	 */
	public String getNewCode(OPRMSubTypeInfo info) throws IException{
		try {
			return dao.getNewCode(info);		 
		} catch (Exception e) {
			e.printStackTrace();			
			throw new IException("获得子类型编码失败！");
		}
	}
	
	/**
	 * 获得所有多借多贷子类型id
	 * @param officeId      办事处id
	 * @param currencyId    币种id
	 * @return
	 * @throws IException
	 */
	public long[] getOPRMSubTypeIDs(long officeId, long currencyId) {
		Collection c = null;
		Iterator it = null;
		long[] ids = null;
		OPRMSubTypeInfo info = null;
		
		try {
			c = getOPRMSubTypeInfos(officeId,currencyId);
			if (c!= null && c.size() > 0) {
				ids = new long[c.size()];
				it = c.iterator();
				for(int i=0;it.hasNext();i++) {
					info = (OPRMSubTypeInfo)it.next();
					ids[i] = info.getId();
				}
			}			
		} catch (IException e) {
			e.printStackTrace();
			log.error("获得子类型编码失败！");
		}
		
		return ids;
	}
	
}

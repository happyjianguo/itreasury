package com.iss.itreasury.craftbrother.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.craftbrother.setting.dao.CraSubSubjectSettingDAO;
import com.iss.itreasury.craftbrother.setting.dao.CraSubjectSettingDAO;
import com.iss.itreasury.craftbrother.setting.dataentity.CraSubSubjectSettingInfo;
import com.iss.itreasury.craftbrother.setting.dataentity.CraSubjectSettingInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class CraSubjectSettingBiz {
	
	CraSubjectSettingDAO subjectSettingDao  = null;
	CraSubSubjectSettingDAO subSubjectSettingDao = null;
	public CraSubjectSettingBiz()
	{
		super();
		subjectSettingDao = new CraSubjectSettingDAO();
		subSubjectSettingDao = new CraSubSubjectSettingDAO();
	}
	public Collection findAllSubjectSettingInfo(CraSubjectSettingInfo info) throws IException
	{
		try
		{
			return subjectSettingDao.findStandardByCondition(info); 
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
	
	public Collection findAllSubSubjectSettingInfo(CraSubSubjectSettingInfo info) throws IException
	{
		try
		{
			return subSubjectSettingDao.findStandardByCondition(info); 
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
	
	public CraSubjectSettingInfo findSubjectSettingInfoByID(long lID) throws IException
	{
		try {
			return (CraSubjectSettingInfo)subjectSettingDao.findByID(lID, CraSubjectSettingInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
	
	public CraSubSubjectSettingInfo findSubSubjectSettingInfoByID(long lID) throws IException
	{
		try {
			return (CraSubSubjectSettingInfo)subSubjectSettingDao.findByID(lID, CraSubSubjectSettingInfo.class);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
	
	public long saveSubjectSettingInfo(CraSubjectSettingInfo info) throws IException
	{
		try {
			info.setStatusID(Constant.RecordStatus.VALID);
			return subjectSettingDao.saveSubjectSettingInfo(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	
	public long saveSubSubjectSettingInfo(CraSubSubjectSettingInfo info) throws IException
	{
		try {
			info.setStatusID(Constant.RecordStatus.VALID);
			return subSubjectSettingDao.saveSubSubjectSettingInfo(info);
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	public long deleteSubSubjectSettingInfo(long lID) throws IException
	{
		try
		{
			return subSubjectSettingDao.updateStatus(lID,Constant.RecordStatus.INVALID);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
	
	

}

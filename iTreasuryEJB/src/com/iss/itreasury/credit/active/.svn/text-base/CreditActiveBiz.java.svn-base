package com.iss.itreasury.credit.active;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.credit.setting.dao.AmountFormDao;
import com.iss.itreasury.credit.setting.dao.AmountSetupDao;
import com.iss.itreasury.credit.setting.dao.SubAmountFormDao;
import com.iss.itreasury.credit.setting.dao.SubAmountSetupDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountSetupInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountSetupInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author leiyang
 * 
 * 处理授信激活功能的相关事宜
 *
 */
public class CreditActiveBiz {
	
	/**
	 * 激活授信
	 * 
	 * @param asInfo 待激活的授信信息
	 * @throws IException
	 */
	public void activeAmountSetup(AmountSetupInfo asInfo)
		throws IException
	{
		Connection conn = null;
		long lAmountFormId = -1;
		
		try {
			//进行批量的数据操作，手动提交Connection
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			AmountFormInfo afInfo = null;
			AmountFormDao afDao = new AmountFormDao(conn);
			
			if(asInfo.getOperationType() == LOANConstant.OperationType.NEW)
			{
				afInfo = new AmountFormInfo();
				afInfo.setClientId(asInfo.getClientId());
				afInfo.setCreditCode(asInfo.getCreditCode());
				afInfo.setCreditModel(asInfo.getCreditModel());
				afInfo.setGroupCreditId(asInfo.getGroupCreditId());
				afInfo.setCreditAmount(asInfo.getCreditAmount());
				afInfo.setControlType(asInfo.getControlType());
				afInfo.setStartDate(asInfo.getStartDate());
				afInfo.setEndDate(asInfo.getEndDate());
				afInfo.setOfficeId(asInfo.getOfficeId());
				afInfo.setCurrencyId(asInfo.getCurrencyId());
				afInfo.setInputUserId(asInfo.getInputUserId());
				afInfo.setInputDate(asInfo.getInputDate());
				afInfo.setState(LOANConstant.CreditFlowStatus.SAVE);
				
				lAmountFormId = afDao.add(afInfo);
			}
			else
			{
				afInfo = new AmountFormInfo();
				afInfo.setId(asInfo.getAmountFormId());
				afInfo.setOfficeId(asInfo.getOfficeId());
				afInfo.setCurrencyId(asInfo.getCurrencyId());
				afInfo = afDao.getAmountFormViewInfo(afInfo).getAmountFormInfo();
				
				afInfo.setCreditAmount(asInfo.getSumCreditAmount());
				afInfo.setControlType(asInfo.getControlType());
				afInfo.setEndDate(asInfo.getEndDate());
				
				afDao.updateAmountForm(afInfo);
				lAmountFormId = afInfo.getId();
			}
			
			SubAmountSetupDao sasDao = new SubAmountSetupDao(conn);
			SubAmountFormDao safDao = new SubAmountFormDao(conn);
			
			Collection asColl =  sasDao.getSubCreditAmountSetupColl(asInfo);
			Collection afColl = safDao.getSubCreditAmountFormColl(afInfo);
			Collection updateColl = new ArrayList();
			SubAmountFormInfo[] safInfos = new SubAmountFormInfo[0];
			SubAmountSetupInfo[] sasInfos = new SubAmountSetupInfo[0];
			
			if(asColl!=null)
			{
				sasInfos = (SubAmountSetupInfo[])asColl.toArray(new SubAmountSetupInfo[0]);
			}
			if(afColl!=null)
			{
				safInfos = (SubAmountFormInfo[])afColl.toArray(new SubAmountFormInfo[0]);	
			}
			
			boolean isExist = false;
			
			for(int i=0; i<safInfos.length; i++){
				for(int j=0; j<sasInfos.length; j++) 
				{
					if(sasInfos[j]!=null && safInfos[i].getCreditType() == sasInfos[j].getCreditType())
					{
						safInfos[i].setCreditShare(sasInfos[j].getCreditShare());
						safInfos[i].setCreditAmount(sasInfos[j].getSumCreditAmount());
						safInfos[i].setExcessPercent(sasInfos[j].getExcessPercent());
						
						isExist = true;
						sasInfos[j] = null;
						break;
					}
				}
				
				if(!isExist){
					safInfos[i].setState(LOANConstant.CreditFlowStatus.DELETE);
				}
			
				updateColl.add(safInfos[i]);
				isExist = false;
			}
			
			SubAmountFormInfo safInfo = null;
			for(int i=0; i<sasInfos.length; i++)
			{
				if(sasInfos[i]==null)
				{
					continue;
				}
				
				safInfo = new SubAmountFormInfo();
				safInfo.setAmountFormId(lAmountFormId);
				safInfo.setCreditType(sasInfos[i].getCreditType());
				safInfo.setCreditShare(sasInfos[i].getCreditShare());
				safInfo.setCreditAmount(sasInfos[i].getCreditAmount());
				safInfo.setExcessPercent(sasInfos[i].getExcessPercent());
				safInfo.setOfficeId(sasInfos[i].getOfficeId());
				safInfo.setCurrencyId(sasInfos[i].getCurrencyId());
				safInfo.setInputUserId(sasInfos[i].getInputUserId());
				safInfo.setInputDate(sasInfos[i].getInputDate());
				safInfo.setState(sasInfos[i].getState());
				
				updateColl.add(safInfo);
			}
			
			if(updateColl!=null && updateColl.size()>0){
				Iterator it = updateColl.iterator();
				while(it.hasNext()){
					SubAmountFormInfo subAmountFormInfo = (SubAmountFormInfo)it.next();
					if(subAmountFormInfo.getId()>0){
						safDao.updateSubAmountForm(subAmountFormInfo);
					}else{
						safDao.add(subAmountFormInfo);	
					}
				}
			}
			
			AmountSetupDao asDao = new AmountSetupDao(conn);
			asInfo.setAmountFormId(lAmountFormId);
			asDao.updateAmountSetup(asInfo);
			
			conn.commit();

		} catch (Exception e) {
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IException(e.getMessage() ,e);
			}
		}
	}
	
	
	public Collection findByCollection(AmountSetupInfo asInfo)
		throws IException
	{
		Collection coll = null;
		try {
			AmountSetupDao asDao = new AmountSetupDao();
			coll =  asDao.findByCollection(asInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return coll;
	}

}

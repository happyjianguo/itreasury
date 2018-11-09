package com.iss.itreasury.evoucher.setting.bizlogic;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.PrintBillSettingDao;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillSettingInfo;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillTemplateInfo;

public class PrintBillSettingBiz extends VoucherBaseBean implements java.io.Serializable{

	/**
	 * 增加新的单据
	 * @param info
	 * @return
	 * @throws VoucherException
	 */
	public long saveNewBillSetting (PrintBillSettingInfo info) throws VoucherException
	{
		long lReturn = -1;
		PrintBillSettingDao dao = new PrintBillSettingDao();
		try {
			lReturn = dao.saveNewBillSetting(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return lReturn;
	}
	
	/**
	 * 根据id查询单据信息
	 * @param lID
	 * @return
	 * @throws VoucherException
	 */
	public PrintBillSettingInfo findByID ( long lID ) throws VoucherException 
	{
		PrintBillSettingInfo resultInfo = new PrintBillSettingInfo();
		PrintBillSettingDao dao = new PrintBillSettingDao();
		try {
			resultInfo = dao.findByID(lID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return resultInfo;
	}
	
	/**
	 * 保存模版信息
	 * @param info
	 * @return
	 * @throws VoucherException
	 */
	public long saveNewBillTemplate (PrintBillTemplateInfo info) throws VoucherException 
	{
		long lReturn = -1;
		PrintBillSettingDao dao = new PrintBillSettingDao();
		
		try {
			lReturn = dao.saveNewBillTemplate(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return lReturn;
	}
	
	public PrintBillTemplateInfo findTemplateInfoByID (long templateID) throws VoucherException
	{
		PrintBillTemplateInfo returnInfo = new PrintBillTemplateInfo();
		PrintBillSettingDao dao = new PrintBillSettingDao();
		
		try {
			returnInfo = dao.findTemplateInfoByID(templateID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return returnInfo;
	}
	
	public PrintBillTemplateInfo findTemplateInfoByCouple (long lBillID,long lCoupleno) throws VoucherException
	{
		PrintBillTemplateInfo returnInfo = new PrintBillTemplateInfo();
		PrintBillSettingDao dao = new PrintBillSettingDao();
		
		try {
			returnInfo = dao.findTemplateInfoByCouple(lBillID,lCoupleno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return returnInfo;
	}
	
	public long deleteTemplateInfo (PrintBillTemplateInfo info) throws VoucherException
	{
		long lReturn = -1;
		PrintBillSettingDao dao = new PrintBillSettingDao();
		try {
			lReturn = dao.deleteTemplateInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		return lReturn;
	}
}

package com.iss.itreasury.evoucher.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.evoucher.base.VoucherBaseBean;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.UpdateBillTemplateDao;

public class UpdateBillTemplateBiz extends VoucherBaseBean implements java.io.Serializable{

	public Collection getTemplateInfos () throws VoucherException
	{
		Collection coll = null;
		UpdateBillTemplateDao dao = new UpdateBillTemplateDao();
		try {
			coll = dao.getTemplateInfos();
		} catch (Exception e) {
			e.printStackTrace();
			throw new VoucherException("Gen_E001", e);
		}
		
		return coll;
	}
}

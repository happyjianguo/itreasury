package com.iss.itreasury.credit.setting.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.credit.setting.dao.AmountFormDao;
import com.iss.itreasury.credit.setting.dao.SubAmountFormDao;
import com.iss.itreasury.credit.setting.dataentity.AmountFormInfo;
import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.credit.setting.dataentity.SubAmountFormInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.IException;

public class AmountFormBiz {

	/**
	 * 根据条件，查询有效的授信信息
	 * 
	 * @param afInfo
	 * @return
	 * @throws IException
	 */
	public Collection findByCollection(AmountFormInfo afInfo)
		throws IException
	{
		Collection coll = null;
		try {
			AmountFormDao afDao = new AmountFormDao();
			coll =  afDao.findByCollection(afInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return coll;
	}
	
	/**
	 * 查询某一授信信息
	 * 
	 * @param afInfo
	 * @return
	 * @throws IException
	 */
	public AmountFormViewInfo getCreditAmountFormView(AmountFormInfo afInfo)
		throws IException
	{
		AmountFormViewInfo afvInfo = null;
		try {
			AmountFormDao afDao = new AmountFormDao();
			afvInfo =  afDao.getAmountFormViewInfo(afInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException(e.getMessage() ,e);
		}
		return afvInfo;
	}
	
	/**
	 * 获得授信的品种授信信息
	 * 
	 * @param afInfo
	 * @return
	 * @throws IException
	 */
	public Collection getSubCreditAmountFormColl(AmountFormInfo afInfo)
		throws IException
	{
		Collection colResult = new ArrayList();
		try {
			SubAmountFormDao safDao = new SubAmountFormDao();
			Collection colSubAmountForm =  safDao.getSubCreditAmountFormColl(afInfo);
			HashMap subAmountFormMap = new HashMap();
			SubAmountFormInfo curSubFormInfo = null;
			
			if(colSubAmountForm!=null && colSubAmountForm.size()>0)
			{
				Iterator it = colSubAmountForm.iterator();
				while(it.hasNext()){
					curSubFormInfo = (SubAmountFormInfo)it.next();
					subAmountFormMap.put("" + curSubFormInfo.getCreditType(), curSubFormInfo);
				}
			}
			
			curSubFormInfo = null;
			
			long[] lCreditTypes = LOANConstant.CreditType.getAllCode(afInfo.getOfficeId(), afInfo.getCurrencyId());
			for(int i=0; i<lCreditTypes.length; i++)
			{
				if((curSubFormInfo=(SubAmountFormInfo)subAmountFormMap.get("" + lCreditTypes[i]))==null)
				{
					curSubFormInfo = new SubAmountFormInfo();
					curSubFormInfo.setCreditType(lCreditTypes[i]);
					curSubFormInfo.setCreditShare(LOANConstant.CreditShare.YES);
					curSubFormInfo.setExcessPercent(100.0);
					curSubFormInfo.setCreditAmount(0.0);
				}
				
				colResult.add(curSubFormInfo);
			}
			
		} catch (Exception e) {
			throw new IException(e.getMessage() ,e);
		}
		return colResult;
	}
}

package com.iss.itreasury.settlement.clearinterestplanSetting.bizlogic;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FD_BankAccountDao;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_BankAccountInfo;
import com.iss.itreasury.settlement.clearinterestplanSetting.dao.ClearinterestplanDao;
import com.iss.itreasury.settlement.clearinterestplanSetting.dataentity.ClearinterestplanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class ClearinterestplanBiz {
	
	private ClearinterestplanDao dao=new ClearinterestplanDao();
	public long saveClearinterestplanSetting(ClearinterestplanInfo cinfo) throws Exception{
		
		long clearinterestplanID=-1;
		long HasclearinterestplanID=-1;
		
		if(cinfo.getId()<=0){
			
			if(dao.findClearinterestplanInfoBySubLoanType(cinfo) == true)
			{
				clearinterestplanID = dao.add(cinfo);
				
			}
			else
			{
				clearinterestplanID = 0;
			}
		}
		else{
			
			if(dao.findClearinterestplanInfoBySubLoanType(cinfo) == true)
			{
				dao.update(cinfo);
				clearinterestplanID=cinfo.getId();
			}
			else
			{
				clearinterestplanID = 0;
			}
		}
				
		return clearinterestplanID;
				
	}
	
	
	public long deleteClearinterestplanSetting(ClearinterestplanInfo cinfo) throws Exception{
		
		long clearinterestplanID=-1;

		ClearinterestplanDao dao=new ClearinterestplanDao();
						
		dao.updateStatus(cinfo.getId(), Constant.RecordStatus.INVALID);
		
		clearinterestplanID=cinfo.getId();
				
		return clearinterestplanID;
		
		
	}
	
	public Collection findAllClearinterestplan(long OfficeID,long CurrencyID) throws Exception{

		Collection coll =null;
		
		ClearinterestplanDao dao=new ClearinterestplanDao();
		
		coll=dao.findAllClearinterestplanInfo(OfficeID,CurrencyID);
				
		return coll;
		
		
	}
	
	public ClearinterestplanInfo findbyID(long ID) throws Exception {
		
		
		ClearinterestplanDao dao=new ClearinterestplanDao();
		
		ClearinterestplanInfo info=new ClearinterestplanInfo();
		
		
		info=(ClearinterestplanInfo)dao.findByID(ID, ClearinterestplanInfo.class);
		
		return info;
		
		
	}


	public PagerInfo findClearinterestplan(long officeID, long currencyID) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = dao.findClearinterestplanInfo(officeID,currencyID);
			pagerInfo.setSqlString(sql);

			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SUBLOANTYPENAME");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[] { "SUBLOANTYPENAME", "ID" });
			baseInfo.setExtensionType(new long[] { PagerTypeConstant.STRING,
					PagerTypeConstant.STRING });
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + ","
					+ PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);

			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("LOANTERMTYPEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(LOANConstant.LoanTerm.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);

			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("OVERDUETYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.ClearInterestPlayOverDueType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);

			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CLEARTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.ClearInterestPlayClearType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			

			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CLEARTIME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING); 
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("INPUTUSERNAME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("INPUTTIME");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);

			pagerInfo.setDepictList(depictList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	

}

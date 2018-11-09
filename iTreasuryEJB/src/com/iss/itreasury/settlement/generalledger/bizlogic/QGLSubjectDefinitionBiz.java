package com.iss.itreasury.settlement.generalledger.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.generalledger.dao.QGLSubjectDefinitionDao;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 账户查询业务层
 * @author xiang
 *
 */
public class QGLSubjectDefinitionBiz {
	
	QGLSubjectDefinitionDao dao = new QGLSubjectDefinitionDao();

	public PagerInfo queryGLSubjectDefinition(GLSubjectDefinitionInfo qInfo) throws Exception{
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.queryLoanSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
		
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID1");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"ID1","ID","ID1"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING,PagerTypeConstant.STRING,PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE+","+PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SSEGMENTNAME2");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NSUBJECTTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.SubjectAttribute.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NISLEAF");
			baseInfo.setDisplayType(PagerTypeConstant.STRING); 
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NBALANCEDIRECTION");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.ControlDirection.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("NAMOUNTDIRECTION");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.ControlDirection.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			 
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	/* while (rset.next())
            {
                info = new GLSubjectDefinitionInfo();

                info.setOfficeID(rset.getLong("nOfficeID"));
                info.setCurrencyID(rset.getLong("nCurrencyID"));
                info.setSegmentCode1(rset.getString("sSegmentCode1"));
                info.setSegmentCode2(rset.getString("sSegmentCode2"));
                info.setSegmentCode3(rset.getString("sSegmentCode3"));
                info.setSegmentCode4(rset.getString("sSegmentCode4"));
                info.setSegmentName1(rset.getString("sSegmentName1"));
                //info.setSubjectName(rset.getString("sSubjectName"));
                info.setSegmentName2(rset.getString("sSegmentName2"));
                info.setSegmentName3(rset.getString("sSegmentName3"));
                info.setSegmentName4(rset.getString("sSegmentName4"));
                info.setSubjectType(rset.getLong("nSubjectType"));
                info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
                info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
                info.setParentSubjectID(rset.getLong("nParentSubjectID"));
                info.setBalanceDirection(rset.getLong("nBalanceDirection"));
                info.setAmountDirection(rset.getLong("nAmountDirection"));
                info.setStatusID(rset.getLong("nStatus"));
                info.setLederType(rset.getLong("nLederType"));
                info.setSecurityLevel(rset.getLong("nSecurityLevel"));
                info.setUseScope(rset.getLong("nUseScope"));
                info.setFlag(rset.getLong("nFlag"));
                info.setValidDate(rset.getTimestamp("dtValidDate"));
                info.setID(rset.getLong("id"));

                collection.add(info);
            }*/
	
}

}

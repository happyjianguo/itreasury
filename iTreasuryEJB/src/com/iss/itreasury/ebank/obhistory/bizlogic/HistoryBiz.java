package com.iss.itreasury.ebank.obhistory.bizlogic;

import java.util.ArrayList;
import java.util.List;


import com.iss.itreasury.ebank.obhistory.dao.HistoryDao;
import com.iss.itreasury.ebank.obhistory.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.util.Log4j;

public class HistoryBiz {
	private Log4j log = new Log4j( this);
	 public List queryByCondition(HistoryAdviseInfo qInfo) throws Exception
	 {
	 	HistoryDao dao = new HistoryDao(); 
	 	List list = new ArrayList();
        
        try
        {
        	list = dao.queryByCondition(qInfo);
        } 
        catch (Exception e)
        {
            throw new Exception("Gen_E001", e);
        }

	   return list;
	 }
	 
	 public long save(HistoryAdviseInfo info)throws Exception{
		 long returnID=-1;
		 HistoryDao dao = new HistoryDao();
		 if (info.getId() > 0)
		 {
			returnID=info.getId();
			log.info("----------修改信息-------------");
			dao.update(info);
		 }else
		 {
			log.info("----------增加信息-------------");
			dao.setUseMaxID();
			returnID = dao.add(info);
		 }
		 return returnID;		 
	 }
}

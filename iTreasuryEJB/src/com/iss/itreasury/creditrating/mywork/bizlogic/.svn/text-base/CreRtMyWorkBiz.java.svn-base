/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author              zcwang 
 * Company:             iSoftStone
 * @version				iTreasury4.0ÐÂÔö
 * Description:         
 */
package com.iss.itreasury.creditrating.mywork.bizlogic;

import com.iss.itreasury.creditrating.mywork.dao.CreRtMyWorkDao;
import com.iss.itreasury.creditrating.mywork.dao.CreRtTransActionDao;
import com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo;

public class CreRtMyWorkBiz implements java.io.Serializable {

	public Object queryCreRtTransActionWork(CreRtMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		CreRtMyWorkDao dao = new CreRtTransActionDao();
		objReturn = dao.queryMyWork(qInfo);	

		return objReturn;
	}
	
}

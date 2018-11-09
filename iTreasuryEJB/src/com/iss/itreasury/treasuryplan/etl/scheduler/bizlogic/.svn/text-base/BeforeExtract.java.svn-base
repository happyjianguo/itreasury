/*
 * Created on 2004-8-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yehuang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BeforeExtract {

	
	public static void main(String[] args){
		BeforeExtract beforeExtract = new BeforeExtract();
		try {
			beforeExtract.beforeExtract(null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void beforeExtract(Timestamp sDate,Timestamp eDate) throws Exception{
		//当前类当前函数为最先执行的程序，因此在此删除所有抽取数据
		Connection tpConn = Database.getConnection();
		Timestamp executeDate = DataFormat.getTreasuryPlanExecuteDate();
		Trea_ActualAmountDAO actualAmountDAO = new Trea_ActualAmountDAO(tpConn);
		Trea_ActualBalanceDAO actualBalanceDAO = new Trea_ActualBalanceDAO(tpConn);
		Trea_ForecastAmountDAO forecastAmountDAO = new Trea_ForecastAmountDAO(tpConn);
		Trea_ForecastBalanceDAO forecastBalanceDAO = new Trea_ForecastBalanceDAO(tpConn);
		actualAmountDAO.deleteExtractDataByDate(executeDate);
		actualBalanceDAO.deleteExtractDataByDate(executeDate);

		Sett_ExtractorUtilDAO extractorUtilDAO = new Sett_ExtractorUtilDAO();
		if(sDate == null && eDate == null){
			Collection c = extractorUtilDAO.findAllOfficeTime();
			Iterator it = c.iterator();			
			while(it.hasNext()){
				OfficeTimeInfo officeTimeInfo = (OfficeTimeInfo) it.next();
				System.out.println("---------正在处理的OfficeTimeInfo: "+ officeTimeInfo);
				Timestamp startDate = officeTimeInfo.getDTOPENDATE();
				sDate = DataFormat.getNextDate(startDate, 1);
				eDate = DataFormat.getNextYear(eDate, 1);			
				forecastAmountDAO.deleteExtractDataByDate(sDate,eDate);
				forecastBalanceDAO.deleteExtractDataByDate(sDate,eDate);
			}
		}else{
			forecastAmountDAO.deleteExtractDataByDate(sDate,eDate);
			forecastBalanceDAO.deleteExtractDataByDate(sDate,eDate);			
		}

		tpConn.close();
		
	}
}

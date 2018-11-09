/*
 * Created on 2004-12-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.extract.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualAmountDAO;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.util.Constant;



/**
 * @author yehuang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransInvestmentExtractor extends AbstractExtractor{

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractActualBalance(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastBalance(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractActualAmount(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualAmount(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception {
		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();
	
		sett_ExtractorUtilDAO.extractDataFromTransInvestmentToActualAmount(officeID, currencyID, currentDate,openDate);
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastAmount(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forcastDate) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

	static public void main(String[] args){
		TransInvestmentExtractor extractor = new TransInvestmentExtractor();
		try {
			//Timestamp today = Timestamp.valueOf("2004-11-20 00:00:00.000000000");
			//Timestamp opendate = Timestamp.valueOf("2004-11-20 00:00:00.000000000");
			if(!TPScheduler.isNeedExecute(null))
				return;
//			for(int i= 0;i<30;i++){
//				extractor.extractForcastBalance(1,1,today,opendate);;
//				opendate = DataFormat.getNextDate(opendate, 1);
//			}			
			extractor.startExtractData(args);
			//extractor.extractActualAmount(1,1,today,opendate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

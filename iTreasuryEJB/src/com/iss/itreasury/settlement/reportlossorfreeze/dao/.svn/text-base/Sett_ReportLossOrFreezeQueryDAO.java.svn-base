package com.iss.itreasury.settlement.reportlossorfreeze.dao;

import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeQueryInfo;
import com.iss.itreasury.util.DataFormat;

public class Sett_ReportLossOrFreezeQueryDAO {
	
	//主要是获得SQl
	public String getSQL(ReportLossOrFreezeQueryInfo qInfo) {
		String strCheckDate = "";
        String strInputDate = "";
        
        long lTransactionType = -1;
        long lInputUserId = -1;
        long lCheckUserId = -1;
        long lStatus = -1;
        lCheckUserId = qInfo.getCheckUserId();
        lInputUserId = qInfo.getInputUserId();
        lTransactionType = qInfo.getTransActionType();
        lStatus = qInfo.getStatus();
       

       
            strCheckDate = DataFormat.getDateString(qInfo.getCheckDate());
            strInputDate = DataFormat.getDateString(qInfo.getInputDate());
            
            StringBuffer sbSQL = new StringBuffer();
            sbSQL
                    .append("SELECT re.*,c.SCODE clientcode,u.sname checkUserName, sa.SACCOUNTNO accountNo FROM \n");
            sbSQL
                    .append(" sett_reportlossorfreeze re ,sett_account sa, client c, userinfo u"
                            + " \n");
            sbSQL.append("WHERE re.TransactionType=" + lTransactionType);
            /* if(lTransactionType == SETTConstant.TransactionType.REPORTLOSS || lTransactionType == SETTConstant.TransactionType.REPORTLOSS
             || lTransactionType == SETTConstant.TransactionType.CHANGECERTIFICATE)*/
            if (lCheckUserId > 0)
            {
                sbSQL.append(" and re.CHECKUSERID=" + lCheckUserId);
            }
            /* else if(lTransactionType == SETTConstant.TransactionType.FREEZE || 
             lTransactionType == SETTConstant.TransactionType.DEFREEZE)
             {
             if(lStatus == SETTConstant.TransactionStatus.SAVE)
             */
            else if (lInputUserId > 0)
            {
                sbSQL.append(" and re.INPUTUSERID=" + lInputUserId);
            }
            /*  else if(lStatus == SETTConstant.TransactionStatus.CHECK)
             {
             sbSQL.append(" and re.CHECKUSERID=" + lCheckUserId);
             }
             */
            sbSQL.append(" and re.status=" + lStatus);
            if (lCheckUserId > 0)
            {
                
            
            sbSQL.append(" and re.CHECKDATE=to_date('" + strCheckDate
                    + "','yyyy-mm-dd') ");
            }
            else if (lInputUserId > 0)
            {
                sbSQL.append(" and re.INPUTDATE=to_date('" + strInputDate
                        + "','yyyy-mm-dd') ");
            }
            sbSQL
                    .append(" and re.clientid=c.id(+) and re.checkuserid=u.id(+) and re.accountid=sa.id(+)");

            //sbSQL
            String strSQL = sbSQL.toString();

		return strSQL;
		
	}

}

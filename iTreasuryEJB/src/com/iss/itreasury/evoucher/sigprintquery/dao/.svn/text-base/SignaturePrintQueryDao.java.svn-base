package com.iss.itreasury.evoucher.sigprintquery.dao;

import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.itreasury.util.DataFormat;

public class SignaturePrintQueryDao {

	/**
	 * 电子签章打印查询 dao层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	 public String querySignaturePrintQuerySQL(SigPrintEntity spe){
			
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("SELECT *FROM (SELECT aa.*, ROWNUM r \n");
			
			sbSQL.append("FROM (SELECT a.id,a.nofficeid, a.ncurrencyid, a.stransno, a.nbilltypeid, a.printuser,  b.scode as clientcode, \n");
		
			sbSQL.append("a.dtprintdate, a.inputuserid,a.NPRINTCOUNT, a.dtinputdate, \n");
			sbSQL.append(" (select c.sname from ob_user c where c.id = a.printuser) printusername,a.nclientid \n");
			sbSQL.append("   FROM sett_signature_printinfo a, client b \n");
			sbSQL.append("  WHERE 1 = 1 and a.nclientid = b.id \n");
			if (spe.getClientcode() != null && !"".equals(spe.getClientcode()))
				 sbSQL.append(" and b.scode >= '"+spe.getClientcode()+"'");
				if (spe.getClientcode2() != null && !"".equals(spe.getClientcode2())) {
				 sbSQL.append("and b.scode<='" +spe.getClientcode2()+ "'");
				}
				if (spe.getClientcode() != null && !"".equals(spe.getClientcode()) && spe.getClientcode2() != null
						&& !"".equals(spe.getClientcode2())) {
					if (spe.getClientcode().compareTo(spe.getClientcode2()) <0)
						sbSQL.append( " and b.scode between  '" + spe.getClientcode() + "' and '"
								+ spe.getClientcode2() + "'");
					else
						sbSQL.append( " and b.scode between  '" + spe.getClientcode2() + "' and '"
								+ spe.getClientcode() + "'");
				}
				if (spe.getStartPrintDate()!= null) {
					sbSQL.append( "  and to_char(DTPRINTDATE,'yyyy-MM-dd') >= '"+DataFormat.getDateString(spe.getStartPrintDate())+"'");
				}
				if (spe.getEndPrintDate() != null) {
					sbSQL.append("  and to_char(DTPRINTDATE,'yyyy-MM-dd') <= '"+DataFormat.getDateString(spe.getEndPrintDate())+"'");
				}
				if (spe.getClientCode3() != null && !"".equals(spe.getClientCode3())) {
					sbSQL.append( "  and PRINTUSER =" + spe.getClientCode3() + "");
			    }
			sbSQL.append(" ) aa) \n");
			sbSQL.append("   WHERE r BETWEEN 1 AND 10 \n");
			
			
			return sbSQL.toString();
			
	 }
}

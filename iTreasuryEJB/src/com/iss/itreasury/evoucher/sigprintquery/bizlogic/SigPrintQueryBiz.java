package com.iss.itreasury.evoucher.sigprintquery.bizlogic;

import java.sql.Timestamp;
import java.util.Date;
import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class SigPrintQueryBiz {

	public PageLoader queryInfo(SigPrintEntity spe) {
		String startCode = spe.getStartCode();
		String endCode = spe.getEndCode();
		Timestamp startDate = spe.getQstartprintdate();
		Timestamp endDate = spe.getQendprintdate();
		String printUser = spe.getPrintuser();
		String condition = " 1=1 ";
		if (startCode != null && !"".equals(startCode))
			condition += "  and  b.scode>='" + startCode + "'";
		if (endCode != null && !"".equals(endCode)) {
			condition += "  and  b.scode<='" + endCode + "'";
		}
		if (startCode != null && !"".equals(startCode) && endCode != null
				&& !"".equals(endCode)) {
			if (startCode.compareTo(endCode) <0)
				condition = "  b.scode between  '" + startCode + "' and '"
						+ endCode + "'";
			else
				condition = "  b.scode between  '" + endCode + "' and '"
						+ startCode + "'";
		}
		if (startDate != null) {
			condition += "  and to_char(DTPRINTDATE,'yyyy-MM-dd') >= '"+DataFormat.getDateString(startDate)+"'";
		}
		if (endDate != null) {
			condition += "  and to_char(DTPRINTDATE,'yyyy-MM-dd') <= '"+DataFormat.getDateString(endDate)+"'";
		}
		if (printUser != null && !"".equals(printUser)) {
			condition += "  and PRINTUSER =" + printUser + "";
		}
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						" sett_signature_printinfo a,client b",
						"a.id,a.nofficeid,a.ncurrencyid,a.stransno,a.nbilltypeid,a.printuser,b.scode as clientcode,"
								+ " a.dtprintdate,a.inputuserid,a.NPRINTCOUNT,a.dtinputdate,(select c.sname from ob_user c where c.id=a.printuser) printusername, a.nclientid",
						condition+"  and a.nclientid=b.id",
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity",
						null);
		return pageLoader;
	}
}

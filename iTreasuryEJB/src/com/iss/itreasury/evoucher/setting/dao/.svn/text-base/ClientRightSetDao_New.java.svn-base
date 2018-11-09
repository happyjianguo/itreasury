package com.iss.itreasury.evoucher.setting.dao;

import com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity;
import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.itreasury.evoucher.util.VOUConstant.EleDocsSet;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class ClientRightSetDao_New {

	/**
	 * 客户授权设置 dao层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	
	 public String queryClientRightSetQuerySQL(ClientRightEntity ce){
		    long nissignature = ce.getNissignature();
		    String startClientCode = ce.getClientCode();
			String endClientCode = ce.getClientCode2();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("SELECT * FROM (SELECT aa.*, ROWNUM r \n");
			
			sbSQL.append("  FROM (SELECT a.id as nclientid, \n");
			sbSQL.append("   a.name as clientName, \n");
			sbSQL.append("    a.code as clientCode, \n");
			sbSQL.append("   a.officeid as nofficeid, \n");
			sbSQL.append("  "+ nissignature+ "as nissignature, \n");
			sbSQL.append("   c.sname as nofficename, \n");
			sbSQL.append("   '"+startClientCode+"' as startClientCode, \n");
			sbSQL.append("   '"+endClientCode+"' as endClientCode \n");
			sbSQL.append("   FROM client_clientinfo a, office c \n");
			if(nissignature == EleDocsSet.HASNOSETRIGHT){
				sbSQL.append("   WHERE not exists (select b.nclientid \n");
			}else{
				sbSQL.append("   WHERE exists (select b.nclientid \n");
			}
			sbSQL.append("   from sett_signature b \n");
			sbSQL.append("   where a.id = b.nclientid\n");
			sbSQL.append("   and b.NISSIGNATURE = "+ EleDocsSet.HASSETRIGHT + ") \n");
			sbSQL.append("   and a.officeid = c.id \n");
			sbSQL.append("   and a.statusid = 1 \n");
			sbSQL.append("   and a.officeid = "+ce.getNofficeid()+" \n");
			sbSQL.append("    and 1 = 1 \n");
			if (ce.getClientCode() != null&&!"".equals(ce.getClientCode()))
				sbSQL.append( "  and  a.code>='" + ce.getClientCode()+"'");
			if (ce.getClientCode2() != null&&!"".equals(ce.getClientCode2())) {
				sbSQL.append( "  and  a.code<='" + ce.getClientCode2()+"'");
			}
			if (ce.getClientCode() != null&&!"".equals(ce.getClientCode())&&ce.getClientCode2() != null&&!"".equals(ce.getClientCode2())) {
				if (ce.getClientCode().compareTo(ce.getClientCode2())<0)
					sbSQL.append("and  a.code between  '" + ce.getClientCode() + "' and '"
							+ ce.getClientCode2()+"'");
				else
					sbSQL.append( "and  a.code between  '" + ce.getClientCode2() + "' and '"
							+ ce.getClientCode()+"'");
			}
			
			sbSQL.append("   ) aa) \n");
			sbSQL.append("   WHERE r BETWEEN 1 AND 10 \n");
			
			
			return sbSQL.toString();
			
	 }
}

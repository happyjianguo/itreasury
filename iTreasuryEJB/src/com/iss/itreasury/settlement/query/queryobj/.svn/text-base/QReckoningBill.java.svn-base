package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QueryReckoningBillConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryReckoningBillResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
;
/**
 * @author gqfang
 * 清算表单的查询
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QReckoningBill extends BaseQueryObject
{
	public Collection queryReckoningBill(QueryReckoningBillConditionInfo paraInfo) throws Exception
	{
		Vector vector = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		strSQL = new StringBuffer();
		try
		{
			con = getConnection();
			
			strSQL.append(" SELECT * From SETT_VExtBankNoTransaction \n");
			strSQL.append(" WHERE nReckoningTypeID in("+SETTConstant.ReckoningType.RemitOutCredictFirst+","+SETTConstant.ReckoningType.RemitOutCredictSecond+") \n");
			if(paraInfo.getInputDate() != null)
			{
				strSQL.append(" And dtInput = TO_DATE('"+DataFormat.getDateString(paraInfo.getInputDate())+"','YYYY/MM/DD')"+" ---录入日期 \n");
			}
			if(paraInfo.getReckoningTypeID() != -1)
			{
				strSQL.append(" And nReckoningTypeID = "+paraInfo.getReckoningTypeID()+" ---清算类型 \n");
			}
			
			System.out.println(" 清算表单查询 SQL: "+strSQL);
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				QueryReckoningBillResultInfo resultInfo = new QueryReckoningBillResultInfo();
				
				resultInfo.setReckoningTypeID( rs.getLong("nReckoningTypeID") );//清算类型 2,贷方一次；3，贷方二次
				resultInfo.setInputDate( rs.getTimestamp("dtInput") );//用户输入的日期
				resultInfo.setReckoningBillTypeDesc( rs.getString("sReckoningBillTypeDesc") );//凭证种类
				resultInfo.setExtBankNo( rs.getString("sExtBankNo") );//提入行号
				resultInfo.setAmount( rs.getDouble("mAmount") );//金额
				resultInfo.setAccountNo( rs.getString("sAccountNo") );//账号
				
				vector.add(resultInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		return vector.size() > 0 ? vector : null;
	}
}
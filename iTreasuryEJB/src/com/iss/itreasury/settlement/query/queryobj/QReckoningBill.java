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
 * ������Ĳ�ѯ
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
				strSQL.append(" And dtInput = TO_DATE('"+DataFormat.getDateString(paraInfo.getInputDate())+"','YYYY/MM/DD')"+" ---¼������ \n");
			}
			if(paraInfo.getReckoningTypeID() != -1)
			{
				strSQL.append(" And nReckoningTypeID = "+paraInfo.getReckoningTypeID()+" ---�������� \n");
			}
			
			System.out.println(" �������ѯ SQL: "+strSQL);
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				QueryReckoningBillResultInfo resultInfo = new QueryReckoningBillResultInfo();
				
				resultInfo.setReckoningTypeID( rs.getLong("nReckoningTypeID") );//�������� 2,����һ�Σ�3����������
				resultInfo.setInputDate( rs.getTimestamp("dtInput") );//�û����������
				resultInfo.setReckoningBillTypeDesc( rs.getString("sReckoningBillTypeDesc") );//ƾ֤����
				resultInfo.setExtBankNo( rs.getString("sExtBankNo") );//�����к�
				resultInfo.setAmount( rs.getDouble("mAmount") );//���
				resultInfo.setAccountNo( rs.getString("sAccountNo") );//�˺�
				
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
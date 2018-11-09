/*
 * Created on 2004-3-1
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obprint.dao;

import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obprint.dataentity.*;
import com.iss.itreasury.settlement.util.NameRef;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBPrintDao extends OBBaseDao{

	public long getPrintTimes(OBPrintLogInfo printInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long hit=0;
		
		try {
			conn=Database.getConnection();
			
			strSQL="select * from OB_PrintLog where nTransID=? and nClientID=? and nAmountTypeID=? and nVoucherTypeID=?";
			ps=conn.prepareStatement( strSQL );
			int n=1;
			ps.setLong(n++,printInfo.getTransID());
			ps.setLong(n++,printInfo.getClientID() );
			ps.setLong(n++,printInfo.getAmountTypeID() );
			ps.setLong(n++,printInfo.getVouchTypeID() );
			rs=ps.executeQuery() ;
			if ( rs.next())
			{
				hit=rs.getLong("nHit");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			
		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return hit;
	}
	
	public void updatePrintTimes(OBPrintLogInfo printInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lID=-1;
		long maxID=-1;
		
		try {
			conn=Database.getConnection();
			
			strSQL="select * from OB_PrintLog where nTransID=? and nClientID=? and nAmountTypeID=? and nVoucherTypeID=?";
			ps=conn.prepareStatement( strSQL );
			int n=1;
			ps.setLong(n++,printInfo.getTransID());
			ps.setLong(n++,printInfo.getClientID() );
			ps.setLong(n++,printInfo.getAmountTypeID() );
			ps.setLong(n++,printInfo.getVouchTypeID() );
			rs=ps.executeQuery() ;
			if ( rs.next())
			{
				lID=rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			
			if (lID<=0)				//新增一条记录
			{
				strSQL = "select nvl(max(ID)+1,1) oID from OB_PrintLog";
				ps = conn.prepareStatement (strSQL);
				rs = ps.executeQuery ();
				if (rs.next ()) 
				{
					maxID = rs.getLong ("oID");
				}
				cleanup(rs);
				cleanup(ps);
								
				strSQL="insert into OB_PrintLog(ID,nTransID,nAmountTypeID,nVoucherTypeID,nClientID,nHit)";
				strSQL+=" values (?,?,?,?,?,?)";
				ps=conn.prepareStatement( strSQL );
				n=1;
				ps.setLong(n++,maxID);
				ps.setLong(n++,printInfo.getTransID() );
				ps.setLong(n++,printInfo.getAmountTypeID() );
				ps.setLong(n++,printInfo.getVouchTypeID() );
				ps.setLong(n++,printInfo.getClientID() );
				ps.setLong(n++,1);
				ps.execute();
				cleanup(ps);

			}
			else					//更新打印次数
			{
				strSQL="update OB_PrintLog set nHit=nHit+1 where ID=?";
				ps=conn.prepareStatement( strSQL );
				ps.setLong(1,lID);
				ps.execute();
				cleanup(ps);
			}
			cleanup(conn);
			
		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
	}
	
	public OBPrintInfo getSettInfoByTransNo(String sTransNo,long lTransType) throws Exception
	{
		OBPrintInfo obPrintInfo = new OBPrintInfo();
		long lInputUserID = -1;
		long lCheckUserID = -1;
		long lID = -1;
		long lTransActionID = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			switch((int)lTransType)
			{
				case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY:
				case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT:
				case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF:
				case (int)OBConstant.SettInstrType.CHILDCAPTRANSFER:
					sbSQL.append(" SELECT * FROM sett_TransCurrentDeposit ");
					break;
				case (int)OBConstant.SettInstrType.CAPTRANSFER_OTHER:
					sbSQL.append(" SELECT * FROM sett_transspecialoperation ");
					break;
				case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT:
				case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT:
					sbSQL.append(" SELECT * FROM SETT_TRANSOPENFIXEDDEPOSIT ");
					break;
				case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER:
				case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW:
					sbSQL.append(" SELECT * FROM SETT_TRANSFIXEDWITHDRAW ");
					break;
				case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE:
				case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT:
				case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE:
					sbSQL.append(" SELECT * FROM SETT_TRANSREPAYMENTLOAN ");
					break;
			}
			//sbSQL.append(" SELECT * FROM sett_TransCurrentDeposit ");
			sbSQL.append(" WHERE  STRANSNO = " + sTransNo);
			sbSQL.append(" ORDER BY STRANSNO");

			//log4j.info(sbSQL.toString());

			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();		
			if ( rs!=null && rs.next())
			{
				lInputUserID = rs.getLong("NINPUTUSERID");
				lCheckUserID = rs.getLong("NCHECKUSERID");
				lID = rs.getLong("ID");
				lTransActionID = rs.getLong("NTRANSACTIONTYPEID");
			}
			obPrintInfo.setInputUserName(NameRef.getUserNameByID(lInputUserID));
			obPrintInfo.setCheckUserName(NameRef.getUserNameByID(lCheckUserID));
			obPrintInfo.setSettID(lID);
			obPrintInfo.setSettTransActionTypeID(lTransActionID);	
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			
		} 
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return obPrintInfo;
	}
	
	public static void main(String args[])
	{
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(1);
		printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
		
		OBPrintDao dao=new OBPrintDao();
		try {
			long hit=dao.getPrintTimes(printInfo);
			System.out.println("----"+hit);
			//dao.updatePrintTimes( printInfo );
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}

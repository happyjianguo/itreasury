/*
 * Created on 2006-9-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obfinanceinstr.dao;

import com.iss.itreasury.bankportal.integration.client.BPClientAgent;
import com.iss.itreasury.bankportal.integration.info.ReqQueryInstructionInfo;
import com.iss.itreasury.bankportal.integration.info.RespQueryInstructionInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBBankPayInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**  
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBankPayDao  extends ITreasuryDAO 
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	private static Log4j log4j = null;
	private static String signTable = "OB_SignAmount";
	
    public OBBankPayDao()
	{
		super("OB_BANKPAY");
		super.setUseMaxID();
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			signTable = "OB_SignAmount_Curr";
		}
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
   
	public OBBankPayDao(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			signTable = "OB_SignAmount_Curr";
		}
	}
	public OBBankPayDao(Connection conn)
	{
		super("OB_BANKPAY",conn);
		super.setUseMaxID();
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			signTable = "OB_SignAmount_Curr";
		}
	}

	public OBBankPayDao(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			signTable = "OB_SignAmount_Curr";
		}
	}
	
	public boolean isStatus (long ID,long[] lStatus) throws Exception
	{
		String strStatusSql = "(";
		for(int i=0;i<lStatus.length;i++)
		{
			strStatusSql += String.valueOf(lStatus[i])+",";
		}
		strStatusSql = strStatusSql.substring(0,strStatusSql.length()-1)+")";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
	
		try
		{
			initDAO();
		    con = transConn;
		   
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_bankpay t where t.ID=? and t.NSTATUS in "+strStatusSql);
			log4j.print("参数:"+ID);
			log4j.print("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//	
		
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
	}
	//根据id删除对应的信息  逻辑删除
	public void deleteEbankByID(long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" update ob_bankpay ");
			sb.append(" set nstatus="+OBConstant.SettInstrStatus.DELETE);
			sb.append(" where id ="+lInstructionID);
			ps = con.prepareStatement(sb.toString());
			ps.execute();
			
			
			
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		
	}
	/**
	 * 修改财务交易值指令状态为已确认
	 * @param long lInstructionID  交易指令ID
	 * @param lStatus 状态
	 * @param long lUserID  确认人
	 * @param strAction 动作
	 * @return long , 大于0表示成功，等于0表示失败
	 * @exception Exception
	 */
	public long updateStatus(long lInstructionID, long lStatus, long lUserID, String strAction) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		String lSignUserID ="";
		//String lAuditingUserID = "";
		long lUpdateStatus = -1;
		lUpdateStatus = lStatus;
		
		try
		{
			
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE OB_BANKPAY SET NSTATUS = ? ");
			if (strAction.equals("check"))
			{
				//复核需要同时设置签认人信息
				//得到签认ID
				lSignUserID = getSignUserID(lInstructionID);
				//lAuditingUserID = getAuditingUserID(lInstructionID);
				//if (lAuditingUserID.trim().length() > 0  )
				//{
					//签认人ID>0，签认人存在，设置复核人、复核时间
					//sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 2 ");
				//}
				//else if(lSignUserID.trim().length() > 0)
				//{
					//签认人ID>0，签认人存在，设置复核人、复核时间
					//sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 3 ");
					
				//}
				if(lSignUserID != null && lSignUserID.trim().length() > 0)
				{
					//签认人ID>0，签认人存在，设置复核人、复核时间
					sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 3 ");
				}
				else
				{
					//签认人ID <0,签认不存在，复核后状态为签认，设置复核人、复核时间
					sb.append(" , NCHECKUSERID=?, dtCheck=sysdate, nIsCanAccept = 1 ");
				}
			}
			if (strAction.equals("cancelcheck"))
			{
				sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 2 ");
			}
			
			if (strAction.equals("auditing"))
			{
				
//				//得到签认ID
//				lSignUserID = getSignUserID(lInstructionID);
//				if (lSignUserID.trim().length() > 0)
//				{
//					//签认人ID>0，签认人存在，设置复核人、复核时间
//					sb.append(" , NAUDITINGUSERID=?, DTAUDITING=sysdate,nIsCanAccept = 3 ");
//				}
//				else
//				{
					//签认人ID <0,签认不存在，复核后状态为签认，设置复核人、复核时间
					sb.append(" , NAUDITINGUSERID=?, DTAUDITING=sysdate, nIsCanAccept = 1 ");
				//}
			}
			if (strAction.equals("cancelauditing"))
			{
				sb.append(" , NAUDITINGUSERID=?, dtCheck=sysdate,nIsCanAccept = 2 ");
			}
			
			if (strAction.equals("sign") )
			{
				sb.append(" , NSIGNUSERID=?, dtSign=sysdate,nIsCanAccept = 1 ");
			}
			
			if (strAction.equals("cancelSign") )
			{
				sb.append(" , NSIGNUSERID=?, dtSign=sysdate,nIsCanAccept = 3 ");
			}
			
			if (strAction.equals("delete"))
			{
				sb.append(" , NDELETEUSERID=?, dtDelete=sysdate ,nIsCanAccept=2");
			}
			sb.append(" where id = ?");
			//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
			//用途：获取开关机状态和开机时间
			
			initDAO();
		    con = transConn;
		    
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lUpdateStatus);
			ps.setLong(nIndex++, lUserID);
			ps.setLong(nIndex++, lInstructionID);
			
			lResult = ps.executeUpdate();
			if (lResult == 0 && strAction.equals("check"))
			{
				ps.close();
				ps = null;
				con.close();
				con = null;
				
				throw new IException("OB_EC17");
			}
			else if (lResult == 0 && strAction.equals("sign"))
			{
				ps.close();
				ps = null;
				con.close();
				con = null;
				
				throw new IException("OB_EC21");
			}
			else if (lResult == 0 && strAction.equals("auditing"))
			{
				ps.close();
				ps = null;
				con.close();
				con = null;
				
				throw new IException("OB_EC21");//不能审核
			}
			// 关闭数据库资源
			ps.close();
			ps = null;
			
		}
		catch (IException ie)
		{
			//log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lInstructionID;
	}
	
	/**
	 * 获得签认人ID，对交易指令复核时需要指定签认人，此方法返回签认人ID
	 * Create Date: 2003-8-13
	 * @param lInstructionID  交易指令ID
	 * @return long 签认人ID
	 * @exception Exception
	 */
	public String getSignUserID(long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		String lReturn ="";
		String lSignUserID[] = new String[3];
		double dSignAmount[] = new double[4];
		//OBBankPayInfo info = new OBBankPayInfo();
		//info =(OBBankPayInfo)super.findByID(lInstructionID,OBBankPayInfo.class);
		double dFinanceAmount = 0;
		try
		{
			initDAO();
		    con = transConn;
			String strSQL = "";
			strSQL = " SELECT a.nSignuserid,a.mAmount as SignAmount,(NVL(b.mAmount,0)) as FinanceAmount \n ";
			strSQL += " FROM "+signTable+" a,OB_BANKPAY  b";
			strSQL += " WHERE 1=1 ";
			strSQL += " and a.nclientid = b.nclientid ";
			strSQL += " AND a.ncurrencyid = b.ncurrencyid ";
			strSQL += " AND b.id = " + lInstructionID;
			strSQL += " ORDER BY a.mAmount ";
			//log4j.info(strSQL);
			log4j.print("***************查找签认人getSignUserID  sql:"+strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				lSignUserID[i] = rs.getString("nSignuserid");
				dSignAmount[i] = rs.getDouble("SignAmount");
				dFinanceAmount = rs.getDouble("FinanceAmount");
				i++;
			}
			
			dSignAmount[i] = 9999999999999999.999999;
			for (int n = 0; n < i; n++)
			{
				if (dSignAmount[n] <= dFinanceAmount && dFinanceAmount < dSignAmount[n + 1])
				{
					lReturn = lSignUserID[n];
					//log4j.info("得到签认人ID== " + lSignUserID[n]);
					break;
				}
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	/**
	 * update
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void update (OBBankPayInfo info)throws ITreasuryDAOException
	{
		try
		{ 
			super.initDAO();
			super.update(info);
			super.finalizeDAO();
		}catch(Exception ie)
		{
			ie.printStackTrace();
			super.finalizeDAO();
		}
		finally
		{
			super.finalizeDAO();
		}
		
	}
	
	/**判断记录是否是该人录入,是否是该人复核
	 * 
	 * @return
	 */
	public boolean isComfirmer(long ID,long User,String action)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		String subSql = "";
		if(action.equalsIgnoreCase("canclecheck"))
		{
			subSql = "NCHECKUSERID";
		}
		else if(action.equalsIgnoreCase(""))
		{
		    
		}
		else 
		{
			subSql = "NCONFIRMUSERID";
		}
		try
		{
			initDAO();
		    con = transConn;
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from OB_BANKPAY t where t.id=? and t."+subSql+"=?");
			log4j.print("参数ID:"+ID);
			log4j.print("参数User:"+User);
			log4j.print("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			ps.setLong(2,User);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
		
	}
	
	public long matchingBankPay (OBBankPayInfo info,long userid)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		int index = 1;
		 
		try
		{
			initDAO();
		    con = transConn;
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.id from OB_BANKPAY t where t.NCLIENTID=? and t.NCURRENCYID=? and t.MAMOUNT=? and t.DTEXECUTE=to_date('"
					+info.getDtexecute().toString().substring(0,10)+"','yyyy-mm-dd')" 
					+" and t.NPAYEEACCTID=? and t.NPAYERACCTID=? and t.NCONFIRMUSERID != ? and t.NTRANSTYPE = ? and t.NSTATUS in("+OBConstant.OBBankPayStatus.SAVE + ","+ OBConstant.OBBankPayStatus.LOADSAVE + ")");
			//sbSQL.append("  and t.nmodule=?");
			log4j.print("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(index++,info.getNclientid());
			ps.setLong(index++,info.getNcurrencyid());
			ps.setDouble(index++,info.getMamount());
		    //ps.setTimestamp(index++,info.getDtexecute());
			ps.setLong(index++,info.getNpayeeacctid());
		    ps.setLong(index++,info.getNpayeracctid());
			ps.setLong(index++,userid);
			ps.setLong(index++,info.getNtranstype());
			//ps.setLong(index++,info.getNmodule());
			rs = ps.executeQuery();
			if (rs.next())
			{
			    lReturn = rs.getLong("id");
			}
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return lReturn;	
	}
	
	public double getUseBalanceByAccountID(long lAccountID, long lCurrencyID, long lInstructionID) throws Exception
	{
        return  99999999;
    }
	
	public boolean isCanAcept(long ID) throws Exception
	{
	    Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		try
		{
			initDAO();
		    con = transConn;
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_bankpay t where t.ID=? and t.NISCANACCEPT=1 ");
			log4j.print("参数:"+ID);
			log4j.print("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalizeDAO();
	
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
	    
	}
	
	/**
	 * 修改指令的id 
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public long updateMaxID (long ID) throws Exception
	{
	    Connection con = null;
	    PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		int nIndex = 1;
		String insID = "";
		try
		{
		   // String insID = "";
		    //insID = "000"+ID;
		    //add 2006-12-14 生成流水ID
		    insID = OBOperation.createInstrCode(14);
		    
		    initDAO();
		    con = transConn;
			StringBuffer sb = new StringBuffer();
			/*
			sb.append("select to_char(sysdate,'yyyymmdd') day from dual ");
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
			    insID = rs.getString("day")+insID;
			}
			*/
//			关闭数据库资源
			sb.setLength(0);
			
			
			sb.append("UPDATE OB_BANKPAY SET ID = ? ");
			 
			sb.append(" where id = ?");
			log4j.print("参数ID:"+ID);
			log4j.print("updateStatus sql==="+sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, Long.parseLong(insID));
			 
			ps.setLong(nIndex++, ID);
			
			lResult = ps.executeUpdate();
			 
			// 关闭数据库资源
			ps.close();
			ps = null;
		}
 
		catch (Exception e)
		{
		 
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
			   finalizeDAO();
			}
			catch (Exception e)
			{
				 
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return lResult>0?Long.parseLong(insID):-1;
		
	}
	/**
	 * 修改指令的id 
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public long addExternalPayee (OBBankPayInfo info) throws Exception
	{
	    Connection con = null;
	    PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		int nIndex = 1;
		long payeeid=-1;
		try
		{
			initDAO();
		    con = transConn;
			StringBuffer sb = new StringBuffer();
			//判断该记录是否已经存在
			sb.append("select id from sett_externalaccount where SEXTACCTNO=? and NOFFICEID=?");
			ps = con.prepareStatement(sb.toString());
			ps.setString(1,info.getSpayeeacctno());
			ps.setLong(2,info.getNmodule());
			rs = ps.executeQuery();
			if (rs.next())
			{
				payeeid = rs.getLong("id");
			}
			log4j.print("##############是否存在 sql＝"+sb.toString());
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			if(payeeid==-1)//如果该外部账户不存在，添加
			{
				sb.append("select seq_Sett_ExternalAccountid.nextval id from dual");
				ps = con.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					payeeid = rs.getLong("id");
				}
				log4j.print("##############找id sql＝"+sb.toString()+"payeeid="+payeeid);
	//			关闭数据库资源
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);
				
				
				sb.append("insert into  sett_externalaccount(id,NOFFICEID,SEXTACCTNO,SEXTACCTNAME,SBANKNAME,SPROVINCE,SCITY) values (?,?,?,?,?,?,?)");
				log4j.print("updateStatus sql==="+sb.toString());
				ps = con.prepareStatement(sb.toString());
				nIndex = 1;
				ps.setLong(nIndex++,payeeid);
				ps.setLong(nIndex++,info.getNmodule());
				ps.setString(nIndex++,info.getSpayeeacctno());
				ps.setString(nIndex++,info.getSpayeeacctname());
				ps.setString(nIndex++,info.getSpayeebankname());
				ps.setString(nIndex++,info.getSpayeeprov());
				ps.setString(nIndex++,info.getSpayeecity());
				
				
				lResult = ps.executeUpdate();
				 
				// 关闭数据库资源
				ps.close();
				ps = null;
			}else{ //如果账户已存在，则更新账户
			   sb.append("update sett_externalaccount set SEXTACCTNAME=?,SBANKNAME=?,SPROVINCE=?,SCITY=? where SEXTACCTNO=? and NOFFICEID=?");	
			   log4j.print("更新账户信息 SQL = "+sb.toString());
			   ps = con.prepareStatement(sb.toString());
			   nIndex = 1;
			   ps.setString(nIndex++,info.getSpayeeacctname());
			   ps.setString(nIndex++,info.getSpayeebankname());
			   ps.setString(nIndex++,info.getSpayeeprov());
			   ps.setString(nIndex++,info.getSpayeecity());
			   ps.setString(nIndex++,info.getSpayeeacctno());
			   ps.setLong(nIndex++,info.getNmodule());
			   lResult = ps.executeUpdate();
			   
			   //关闭数据源
			   ps.close();
			   ps = null;
			}	
			
		}
 
		catch (Exception e)
		{
		 
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalizeDAO();
			   
			}
			catch (Exception e)
			{
				 
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return payeeid;
		
	}
	public static void main(String[] args){
			OBBankPayDao ob=new OBBankPayDao();
			OBBankPayInfo info=new OBBankPayInfo();
			info.setSpayeeacctno("100000");
			try {
				ob.addExternalPayee(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
//	/**
//	 * 根据查询条件表单类，查询出符合查询条件的指令信息
//	 * Create Date: 2003-8-13
//	 * @param QueryCapForm 查询条件表单类
//	 * @return Collection  所有符合条件交易指令信息
//	 * @exception Exception
//	 */
//	public Collection query(QueryCapForm qcf) throws Exception
//	{
//		
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Vector vReturn = new Vector();
//		StringBuffer sbSQL = new StringBuffer();
//		OBBankPayInfo payInfo = null;
//		try
//		{
//			initDAO();
//		    con = transConn;
//			sbSQL.append(" SELECT bank.*, \n");
//			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
//			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName \n");
//			sbSQL.append(" FROM ob_bankpay bank, OB_User cfmUser, \n");
//			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
//			sbSQL.append(" OB_User delUser \n");
//			sbSQL.append(" WHERE 1=1 ");
//			sbSQL.append(" AND bank.nconfirmuserid=cfmUser.id(+) \n");
//			sbSQL.append(" AND bank.nsignuserid=signuser.id(+) \n");
//			sbSQL.append(" AND bank.nDeleteuserid=delUser.id(+) \n");
//			sbSQL.append(" AND bank.nCheckUserID=checkUser.id(+) \n");
//			sbSQL.append(" AND bank.ntranstype="+OBConstant.SettInstrType.ONLINEBANK_BANKPAY+" \n");
//			if ( qcf.getCurrencyID() >0)
//				sbSQL.append(" AND bank.nCurrencyID=" + qcf.getCurrencyID() + " \n");
//			if(qcf.getNmodule()==-1)//网银指令查询
//			{	
//				sbSQL.append(" AND bank.nClientID=" + qcf.getClientID() + " \n");
//			}
//			if(qcf.getNmodule()!=-1)//结算中心查询
//			{
//				if(qcf.getScopemodule()==0)//只查询本结算中心网银客户的交易记录
//				{
//					sbSQL.append(" AND bank.nClientID in (select id from client_clientinfo where officeid="+qcf.getNmodule()+")");
//				}
//				if(qcf.getScopemodule()==1)//查询结算中心的交易记录
//				{
//					sbSQL.append(" AND bank.nmodule="+qcf.getNmodule());
//				}
//				if(qcf.getScopemodule()==-1)//查询结算中心及其网银客户的交易记录
//				{
//					sbSQL.append(" AND (bank.nClientID in (select id from client_clientinfo where officeid="+qcf.getNmodule()+")");
//					sbSQL.append(" 	OR bank.nmodule="+qcf.getNmodule());
//					sbSQL.append(")");
//				}
//			}	
//			//业务复核查询，查询未复核的记录：非登录人录入的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.OBBankPayStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
//			{
//				sbSQL.append(" AND bank.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
//				sbSQL.append(" AND bank.nConfirmUserID != " + qcf.getUserID() + " \n");
//			}
//			//业务复核查询，查询已复核的记录：登录人复核的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
//			{
//			
//					sbSQL.append(" AND bank.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
//					sbSQL.append(" AND bank.nCheckUserID = " + qcf.getUserID() + " \n");
//			
//			}
//			//业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK ) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
//			{
//			
//					sbSQL.append(" AND bank.nStatus in( " 
//							+ OBConstant.OBBankPayStatus.CHECK+","+OBConstant.OBBankPayStatus.AUDITING+") ");
//				double midSignAmount=getMidSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID());
//				//baihuili modify 2006.9.2
//				if(midSignAmount==-1)
//				{//无中间值
//					log4j.print("进入无间断处理部分－－－－－－－－－－－－－－");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))>= " + this.getMinSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1  AND bank.nIsCanAccept != 2 \n");
//				}
//				else
//				{
//					log4j.print("进入间断处理部分－－－－－－－－－－－－－－");
//					sbSQL.append(" AND(");
//					sbSQL.append("(");
//					sbSQL.append("(NVL(bank.mAmount,0))  >= " + this.getMinSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0)) < " + midSignAmount + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//					sbSQL.append(") or");
//					sbSQL.append("(");
//					sbSQL.append("(NVL(bank.mAmount,0))>= " + this.getMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//					sbSQL.append(")");
//					sbSQL.append(")");
//				}
//			}
//			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
//			{
//			
//					sbSQL.append(" AND bank.nstatus = " 
//							+ OBConstant.SettInstrStatus.SIGN 
//							+ " \n");
//					sbSQL.append(" AND bank.nSignUserID = " + qcf.getUserID() + " \n");
//				
//				sbSQL.append(" AND bank.nIsCanAccept = 1 \n");
//			}
//			
//			//=========================================================================================================
//			
////			业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK ) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.AUDITING))
//			{
//			 
//					sbSQL.append(" AND bank.nStatus =" 
//							+ OBConstant.OBBankPayStatus.CHECK);
//				double midSignAmount=getMidAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID());
//				//baihuili modify 2006.9.2
//				if(midSignAmount==-1)
//				{//无中间值
//					log4j.print("进入无间断处理部分－－－－－－1111－－－－－－－－");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))>= " + this.getMinAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//				}
//				else
//				{
//					log4j.print("进入间断处理部分－－－－－111－－－－－－－－－");
//					sbSQL.append(" AND(");
//					sbSQL.append("(");
//					sbSQL.append("(NVL(bank.mAmount,0))  >= " + this.getMinAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0)) < " + midSignAmount + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//					sbSQL.append(") or");
//					sbSQL.append("(");
//					sbSQL.append("(NVL(bank.mAmount,0))>= " + this.getMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
//					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
//					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//					sbSQL.append(")");
//					sbSQL.append(")");
//				}
//			}
//			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
//			if ((qcf.getStatus() == OBConstant.OBBankPayStatus.AUDITING) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.AUDITING))
//			{
//			
//					sbSQL.append(" AND bank.nstatus = " 
//							+ OBConstant.OBBankPayStatus.AUDITING 
//							+ " \n");
//					sbSQL.append(" AND bank.NAUDITINGUSERID = " + qcf.getUserID() + " \n");
//				
//				sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
//			}
//			
//			//============================================================================================================
//			
//			//交易申请查询，查询所有状态
//			if (qcf.getStatus() >= 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
//			{
//				
//			    //交易申请查询，查询所有状态
//			    sbSQL.append(" AND bank.nstatus = " + qcf.getStatus() + " \n");
//			
//			}
//			// 执行日期-从
//			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
//			{
//				sbSQL.append(" AND bank.DTEXECUTE >= ? \n");
//			}
//			// 执行日期-到
//			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
//			{
//				sbSQL.append(" AND bank.DTEXECUTE <= ? \n");
//			}
//			// 交易金额-从
//			if (qcf.getMinAmount() > 0.0)
//			{
//				sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
//			}
//			// 交易金额-到
//			if (qcf.getMaxAmount() > 0.0)
//			{
//				sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
//			}
//			
//		
//
//			if (qcf.isOrderBy())
//			{
//				sbSQL.append(" order by  TO_CHAR(bank.dtconfirm,'YYYY-MM-DD') desc ,bank.nPayerAcctID \n");
//			}
//			else
//			{
//				sbSQL.append(" ORDER BY bank.dtconfirm ASC ,bank.nPayerAcctID \n");
//			}
//			//log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
//			log4j.print("@@@@@@@@@@@@@@@@"+sbSQL.toString());
//			ps = con.prepareStatement(sbSQL.toString());
//			int nIndex = 1;
//
//			// 执行日期-从
//			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
//			{
//				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartExe().trim() + " 00:00:00"));
//			}
//			// 执行日期-到
//			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
//			{
//				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndExe().trim() + " 23:59:59"));
//			}
//			//方便调试 不发指令
//			BPClientAgent.init(Config.getProperty(Config.INTEGRATION_SERVICE_BPSERVICEIP),Config.getInteger(Config.INTEGRATION_SERVICE_BPSERVICEPORT,-1));
//	        
//	       
//			rs = ps.executeQuery();
//			while (rs.next())
//			{
//			    payInfo = new OBBankPayInfo();
//			    
//			    payInfo.setDtcheck(rs.getTimestamp("DTCHECK"));
//			    payInfo.setDtconfirm(rs.getTimestamp("DTCONFIRM"));
//			    payInfo.setDtdelete(rs.getTimestamp("DTDELETE"));
//			    payInfo.setDtexecute(rs.getTimestamp("DTEXECUTE"));
//			    payInfo.setDtsign(rs.getTimestamp("DTSIGN"));
//			    payInfo.setId(rs.getLong("ID"));
//			    payInfo.setNiscanaccept(rs.getLong("NISCANACCEPT"));
//			    payInfo.setMamount(rs.getDouble("MAMOUNT"));
//			    payInfo.setNcheckuserid(rs.getLong("NCHECKUSERID"));
//			    payInfo.setNclientid(rs.getLong("NCLIENTID"));
//			    payInfo.setNconfirmuserid(rs.getLong("NCONFIRMUSERID"));
//			    payInfo.setNcurrencyid(rs.getLong("NCURRENCYID"));
//			    payInfo.setNdeleteuserid(rs.getLong("NDELETEUSERID"));
//			    payInfo.setNpayeeacctid(rs.getLong("NPAYEEACCTID"));
//			    payInfo.setNpayeracctid(rs.getLong("NPAYERACCTID"));
//			    payInfo.setNsignuserid(rs.getLong("NSIGNUSERID"));
//			    payInfo.setNstatus(rs.getLong("NSTATUS"));
//			    payInfo.setNtranstype(rs.getLong("NTRANSTYPE"));
//			    payInfo.setSnote(rs.getString("SNOTE"));
//			    payInfo.setSpayeeacctname(rs.getString("SPAYEEACCTNAME"));
//			    payInfo.setSpayeeacctno(rs.getString("SPAYEEACCTNO"));
//			    payInfo.setSpayeebankname(rs.getString("SPAYEEBANKNAME"));
//			    payInfo.setSpayeecity(rs.getString("SPAYEECITY"));
//			    payInfo.setSpayeeprov(rs.getString("SPAYEEPROV"));
//			    payInfo.setBudgetItemID(rs.getLong("BudgetItemID"));
//			    payInfo.setBudgetSystemID(rs.getLong("BudgetSystemID"));
//			    payInfo.setNmodule(rs.getLong("NMODULE"));
//			    if(payInfo.getNiscanaccept()==1)
//			    {
//				    ReqQueryInstructionInfo reqQueInfo = new ReqQueryInstructionInfo();
//			        ReqQueryInstructionInfo.ConditionInfo conditioInfo=reqQueInfo.new ConditionInfo();
//			        
//			        conditioInfo.setTransNO(String.valueOf(payInfo.getId()));
//			        
//			         ReqQueryInstructionInfo.ConditionInfo[] conditionInfos=new ReqQueryInstructionInfo.ConditionInfo[1];
//					 conditionInfos[0]=conditioInfo;
//					 reqQueInfo.setSystemId(2);//用常量
//					 if(payInfo.getNmodule()>0)
//	 		          {	//结算
//					 	reqQueInfo.setSystemId(1);//用常量
//	 		          }else{
//	 		          	reqQueInfo.setSystemId(2);//网银
//	 		          }
//					 reqQueInfo.setOfficeID(payInfo.getNmodule());//办事处
//					 reqQueInfo.setConditions(conditionInfos);
//					 RespQueryInstructionInfo respinfo = BPClientAgent.queryInstruction(reqQueInfo);
//					 RespQueryInstructionInfo.ResultInfo[] resultInfo = respinfo.getResults();
//					 if(resultInfo != null)
//					 {
//					     payInfo.setBankPortalStatus(resultInfo[0].getStatus());
//					 }
//					 
//			    }
//			    else
//			    {
//			        	payInfo.setBankPortalStatus(-1);
//				}
//				vReturn.add(payInfo);
//				
//			}
//			//log4j.info("query result : " + vReturn.size());
//		}
//		catch (Exception e)
//		{
//			//log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
//			//log4j.error(e.toString());
//		    e.printStackTrace();
//			throw new IException("Gen_E001");
//		}
//		finally
//		{
//			try
//			{
//				finalizeDAO();
//			}
//			catch (Exception e)
//			{
//				//log4j.error(e.toString());
//				throw new IException("Gen_E001");
//			}
//		}
//		return vReturn.size() > 0 ? vReturn : null;
//	}
	
	/**
	* 根据签认人ID，获取该签认人的签认中间金额 
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMidSignAmount(long lUserID, long lOfficeID,long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = -1.0;
		double dMinAmount = this.getMinSignAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		double dMaxAmount = this.getMaxSignAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM "+signTable;
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " AND ncurrencyid = " + lCurrencyID;
				strSQL += " AND nSignuserid = " + lUserID;
				//strSQL+=" AND sSignUsers not Like '%," + lUserID+",%'";
				strSQL+=" AND mAmount >= "+dMinAmount;
				strSQL+=" AND mAmount < "+dMaxAmount;
				
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = -1.0;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					 finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	/**
	* 根据签认人ID，获取该签认人的签认金额最小值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMinSignAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//int i = 0;
		double dReturn = -1.0;
		try
		{
			initDAO();
		    con = transConn;
			String strSQL = "";
			//String strlUserID=lUserID+"";
			strSQL = " SELECT nSignuserid,mAmount ";
			strSQL += " FROM "+signTable;
			strSQL += " WHERE nclientid = " + lClientID;
			strSQL += " AND ncurrencyid = " + lCurrencyID;
			strSQL += " AND nSignuserid = " + lUserID;
			//strSQL += " AND sSignUsers Like '%," + strlUserID+",%'";
			strSQL += " ORDER BY mAmount ";
			
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble("mAmount");
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dReturn;
	}
	/**
	* 根据签认人ID，获取该签认人的签认金额最大值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMaxSignAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = -1.0;
		double dMinAmount = this.getMinSignAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM "+signTable;
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " AND ncurrencyid = " + lCurrencyID;
				strSQL += " AND nSignuserid = " + lUserID;
				strSQL += " AND mAmount = (select max(mamount) from "+signTable+" t  where t.nclientid="+lClientID
						+ " and t.ncurrencyid="+lCurrencyID+" and t.nSignuserid = "+lUserID+")"; 
				
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = 9999999999999999.999999;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	/**
	* 根据签认人ID，获取该签认人的大于最大签认金额的值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMoreMaxSignAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = -1.0;
		double dMinAmount = this.getMinSignAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT  min(mAmount) mAmount ";
				strSQL += " FROM "+signTable;
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " AND ncurrencyid = " + lCurrencyID;
				strSQL += " AND nSignuserid = " + lUserID;
				strSQL += " AND mAmount > (select max(mamount) from "+signTable+" t  where t.nclientid="+lClientID
						+" and t.ncurrencyid="+lCurrencyID+
						" and t.nSignuserid = "+lUserID+" )"; 
				//log4j.info(strSQL);
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next() && rs.getDouble("mAmount")>0)
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = 9999999999999999.999999;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	public long getOfficeIDbyClientID(long lClientID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		long officeid = -1;
		try
		{
			initDAO();
		    con = transConn;
			String strSQL = "";
			strSQL="select * from client_clientinfo where id="+lClientID;
			//log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				officeid = rs.getLong("officeid");
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return officeid;
	}

	
	/**
	 * 获得审核人ID，对交易指令复核时需要指定审核人，此方法返回签认人ID
	 * Create Date: 2003-8-13
	 * @param lInstructionID  交易指令ID
	 * @return long 签认人ID
	 * @exception Exception
	 */
	public String getAuditingUserID(long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		String lReturn ="";
		String lSignUserID[] = new String[3];
		double dSignAmount[] = new double[4];
		OBBankPayInfo info = new OBBankPayInfo();
		info =(OBBankPayInfo)super.findByID(lInstructionID,OBBankPayInfo.class);
		double dFinanceAmount = 0;
		try
		{
			initDAO();
		    con = transConn;
			String strSQL = "";
			strSQL = " SELECT a.sSignUsers,a.mAmount as SignAmount,(NVL(b.mAmount,0)) as FinanceAmount \n ";
			strSQL += " FROM ob_auditingamount  a,OB_BANKPAY  b";
			strSQL += " WHERE 1=1 ";
			//strSQL += "	and (a.nclientid = b.nmodule or  b.nclientid in (select id from client_clientinfo  c where c.officeid=a.nclientid)) ";
			//strSQL += "  and a.nclientid ="+info.getNclientid() ;
			strSQL += "  and  b.nclientid=a.nclientid ";
			strSQL += " AND a.ncurrencyid = b.ncurrencyid ";
			strSQL += " AND b.id = " + lInstructionID;
			strSQL += " ORDER BY a.mAmount ";
			//log4j.info(strSQL);
			log4j.print("***************查找签认人getSignUserID  sql:"+strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				lSignUserID[i] = rs.getString("sSignUsers");
				dSignAmount[i] = rs.getDouble("SignAmount");
				dFinanceAmount = rs.getDouble("FinanceAmount");
				i++;
			}
			
			dSignAmount[i] = 9999999999999999.999999;
			for (int n = 0; n < i; n++)
			{
				if (dSignAmount[n] <= dFinanceAmount && dFinanceAmount < dSignAmount[n + 1])
				{
					lReturn = lSignUserID[n];
					//log4j.info("得到签认人ID== " + lSignUserID[n]);
					break;
				}
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	
	
	/**
	* 根据签认人ID，获取该签认人的签认中间金额 
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMidAuditingAmount(long lUserID, long lOfficeID,long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		double dMinAmount = this.getMinAuditingAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		double dMaxAmount = this.getMaxAuditingAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM ob_auditingamount  ";
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " 	AND nofficeid = " + lOfficeID;
				strSQL += " 	AND ncurrencyid = " + lCurrencyID;
				//strSQL += " AND nSignuserid = " + lUserID;
				//bhli modify 2006.9.2
				//strSQL += " AND mAmount > " + dMinAmount;
				strSQL+=" AND sSignUsers not Like '%," + lUserID+",%'";
				strSQL+=" AND mAmount >"+dMinAmount+" AND mAmount<(select max(mamount) from ob_auditingamount t  where t.nclientid="+lClientID+" and t.nofficeid="+lOfficeID+" and t.ncurrencyid="+lCurrencyID+
						" and t.ssignusers like'%,"+lUserID+",%' )"; 
				//log4j.info(strSQL);
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = -1.0;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					 finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	
	/**
	* 根据签认人ID，获取该签认人的签认金额最小值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMinAuditingAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		try
		{
			initDAO();
		    con = transConn;
			String strSQL = "";
			String strlUserID=lUserID+"";
			strSQL = " SELECT sSignUsers,mAmount ";
			strSQL += " FROM ob_auditingamount  ";
			strSQL += " WHERE nclientid = " + lClientID;
			strSQL += " AND nofficeid = " + lOfficeID;
			strSQL += " AND ncurrencyid = " + lCurrencyID;
			strSQL += " AND sSignUsers Like '%," + strlUserID+",%'";
			strSQL += " ORDER BY mAmount ";
			//log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble("mAmount");
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dReturn;
	}
	
	/**
	* 根据签认人ID，获取该签认人的大于最大签认金额的值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMoreMaxAuditingAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		double dMinAmount = this.getMinAuditingAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT min(mAmount) mAmount ";
				strSQL += " FROM ob_auditingamount  ";
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " 	AND nofficeid = " + lOfficeID;
				strSQL += " 	AND ncurrencyid = " + lCurrencyID;
				//strSQL += " AND nSignuserid = " + lUserID;
				//bhli modify 2006.9.2
				//strSQL += " AND mAmount > " + dMinAmount;
				strSQL+=" AND mAmount > (select max(mamount) from ob_auditingamount t  where t.nclientid="+lClientID+" and t.nofficeid="+lOfficeID+" and t.ncurrencyid="+lCurrencyID+
						" and t.ssignusers like'%,"+lUserID+",%' )"; 
				//log4j.info(strSQL);
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next() && rs.getDouble("mAmount")>0)
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = 9999999999999999.999999;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	
	/**
	* 根据签认人ID，获取该签认人的签认金额最大值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMaxAuditingAmount(long lUserID,long lOfficeID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		double dMinAmount = this.getMinAuditingAmount(lUserID,lOfficeID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				initDAO();
			    con = transConn;
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM ob_auditingamount  ";
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " 	AND nofficeid = " + lOfficeID;
				strSQL += " 	AND ncurrencyid = " + lCurrencyID;
				//strSQL += " AND nSignuserid = " + lUserID;
				//bhli modify 2006.9.2
				//strSQL += " AND mAmount > " + dMinAmount;
				strSQL+=" AND mAmount = (select max(mamount) from ob_auditingamount t  where t.nclientid="+lClientID+" and t.nofficeid="+lOfficeID+" and t.ncurrencyid="+lCurrencyID+
						" and t.ssignusers like'%,"+lUserID+",%' )"; 
				//log4j.info(strSQL);
				log4j.print("####SQL----------"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = 9999999999999999.999999;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					//log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	
	
	public long addbankpay(OBBankPayInfo info)throws Exception
	{
	    Connection con = null;
		PreparedStatement ps = null;
		long lMaxID = -1;
		try{
		    
		    con = Database.getConnection();
		StringBuffer sb = new StringBuffer();
		 
		log4j.print("get max(id)+1 as PK");
		//调用BizCapital方法，得到最大流水号
		lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.BANKPAY));
		 
		// insert into  拼写插入语句 
		log4j.print("银行汇款指令插入语句= \n");
//		sb.append(" INSERT INTO ob_bankpay(  \n");
//		sb.append("ID, NCLIENTID, NCURRENCYID,NTRANSTYPE,");
//		sb.append("MAMOUNT,DTEXECUTE,SNOTE, ");
//		sb.append(" NSTATUS,NCONFIRMUSERID, \n");	
//		sb.append("DTCONFIRM, NPAYERACCTID, NPAYEEACCTID, SPAYEEACCTNO, ");
//		sb.append("SPAYEEACCTNAME,  SPAYEEBANKNAME, SPAYEEPROV,");
//		sb.append("SPAYEECITY,NMODULE,BUDGETSTATUS,BUDGETITEMID,BUDGETSYSTEMID,NAME,BANKCONNECTNUMBER," +
//				"DEPARTMENTNUMBER ) \n");
//		sb.append("VALUES \n");
//		sb.append("(?,?,?,?,?,?,?,?,?,to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)" );
		
		sb.append(" INSERT INTO ob_bankpay(  \n");
		sb.append("ID, NCLIENTID, NCURRENCYID,NTRANSTYPE,");
		sb.append("MAMOUNT,DTEXECUTE,SNOTE, ");
		sb.append(" NSTATUS,NCONFIRMUSERID, \n");	
		sb.append("DTCONFIRM, NPAYERACCTID, NPAYEEACCTID, SPAYEEACCTNO, ");
		sb.append("SPAYEEACCTNAME,  SPAYEEBANKNAME, SPAYEEPROV,");
		sb.append("SPAYEECITY,NAME,BANKCONNECTNUMBER," +
				"DEPARTMENTNUMBER,  \n");
		sb.append(" BANKCNAPSNO) ");
		sb.append("VALUES \n");
		sb.append("(?,?,?,?,?,?,?,?,?,to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,?)" );
		
		log4j.print("add插入语句=" + sb.toString());
		log4j.print("lMaxID=" + lMaxID);
		log4j.print("debug....."+info);
		ps = con.prepareStatement(sb.toString());
		int nIndex = 1;
		//////// 第1行 8个字段
		log4j.print("lMaxID=" + lMaxID);
		ps.setLong(nIndex++, lMaxID);
		ps.setLong(nIndex++, info.getNclientid()); // 网上银行客户ID
		ps.setLong(nIndex++, info.getNcurrencyid()); // 币种
		ps.setLong(nIndex++, info.getNtranstype()); // 网上银行交易类型
		ps.setDouble(nIndex++, info.getMamount()); // 金额
		ps.setTimestamp(nIndex++, info.getDtexecute()); // 执行日
		ps.setString(nIndex++, info.getSnote()); // 备注
		ps.setLong(nIndex++, info.getNstatus()); // 状态
		ps.setLong(nIndex++, info.getNconfirmuserid()); // 录入人
		//ps.setTimestamp(nIndex++, info.getDtconfirm()); // 录入时间
		ps.setLong(nIndex++, info.getNpayeracctid()); // 付款方账户id
		ps.setLong(nIndex++, info.getNpayeeacctid()); // 收款方账户id
		ps.setString(nIndex++, info.getSpayeeacctno()); // 收款账号
		ps.setString(nIndex++, info.getSpayeeacctname()); // 收款账号
		ps.setString(nIndex++, info.getSpayeebankname()); // 收款账户银行名称
		ps.setString(nIndex++, info.getSpayeeprov()); // 收款方所在省
		ps.setString(nIndex++, info.getSpayeecity()); // 收款方所在市
		
	//	ps.setLong(nIndex++, info.getNmodule()); // 模块id  网银为-1;结算中心为 2
	//	ps.setLong(nIndex++, info.getBudgetStatus()); // 预算状态
	//	ps.setLong(nIndex++, info.getBudgetItemID()); // 预算项目id
	//	ps.setLong(nIndex++, info.getBudgetSystemID()); // 预算体系id
		ps.setString(nIndex++, info.getName()); // 付款方名称
		ps.setString(nIndex++, info.getBankconnectnumber()); // 银行联行号
		ps.setString(nIndex++, info.getDepartmentnumber()); // 机构号
	    
		ps.setString(nIndex++, info.getBankCNAPSNo());  //CNAPS机构号

		
		ps.executeUpdate();
		ps.close();
		ps = null;
		con.close();
		con = null;
		}catch (IException e)
		{
			log4j.print(e.toString());
			throw e;
		}
		catch (Exception e)
		{
		    log4j.print(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
			    log4j.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		 
        return lMaxID;
	    
	}
	
	
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息
	 * Create Date: 2003-8-13
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection query(QueryCapForm qcf) throws Exception
	{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		OBBankPayInfo payInfo = null;
		try
		{
			
			sbSQL.append(" SELECT bank.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName \n");
			sbSQL.append(" FROM ob_bankpay bank, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser \n");
			sbSQL.append(" WHERE 1=1 ");
			sbSQL.append(" AND '2'||bank.ID not in (select b.s_transactionno from bs_bankinstructioninfo b ) \n");
			sbSQL.append(" AND bank.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND bank.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND bank.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND bank.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND bank.ntranstype="+OBConstant.SettInstrType.ONLINEBANK_BANKPAY+" \n");
			if ( qcf.getCurrencyID() >0)
				sbSQL.append(" AND bank.nCurrencyID=" + qcf.getCurrencyID() + " \n");
			if(qcf.getNmodule()==-1)//网银指令查询
			{	
				sbSQL.append(" AND bank.nClientID=" + qcf.getClientID() + " \n");
			}
			if(qcf.getNmodule()!=-1)//结算中心查询
			{
				if(qcf.getScopemodule()==0)//只查询本结算中心网银客户的交易记录
				{
					sbSQL.append(" AND bank.nClientID in (select id from client_clientinfo where officeid="+qcf.getNmodule()+")");
				}
				if(qcf.getScopemodule()==1)//查询结算中心的交易记录
				{
					sbSQL.append(" AND bank.nmodule="+qcf.getNmodule());
				}
				if(qcf.getScopemodule()==-1)//查询结算中心及其网银客户的交易记录
				{
					sbSQL.append(" AND (bank.nClientID in (select id from client_clientinfo where officeid="+qcf.getNmodule()+")");
					sbSQL.append(" 	OR bank.nmodule="+qcf.getNmodule());
					sbSQL.append(")");
				}
			}	
			//业务复核查询，查询未复核的记录：非登录人录入的本单位交易指令
			if ((qcf.getStatus() == OBConstant.OBBankPayStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				sbSQL.append(" AND bank.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
				sbSQL.append(" AND bank.nConfirmUserID != " + qcf.getUserID() + " \n");
			}
			//业务复核查询，查询已复核的记录：登录人复核的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
			
					sbSQL.append(" AND bank.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
					sbSQL.append(" AND bank.nCheckUserID = " + qcf.getUserID() + " \n");
			
			}
			//业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK ) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{
			
					sbSQL.append(" AND bank.nStatus in( " 
							+ OBConstant.OBBankPayStatus.CHECK+","+OBConstant.OBBankPayStatus.AUDITING+") ");
				double midSignAmount=getMidSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID());
				//baihuili modify 2006.9.2
				if(midSignAmount==-1)
				{//无中间值
					log4j.print("进入无间断处理部分－－－－－－－－－－－－－－");
					sbSQL.append(" AND (NVL(bank.mAmount,0))>= " + this.getMinSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1  AND bank.nIsCanAccept != 2 \n");
				}
				else
				{
					log4j.print("进入间断处理部分－－－－－－－－－－－－－－");
					sbSQL.append(" AND(");
					sbSQL.append("(");
					sbSQL.append("(NVL(bank.mAmount,0))  >= " + this.getMinSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0)) < " + midSignAmount + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
					sbSQL.append(") or");
					sbSQL.append("(");
					sbSQL.append("(NVL(bank.mAmount,0))>= " + this.getMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxSignAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
					sbSQL.append(")");
					sbSQL.append(")");
				}
			}
			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{
			
					sbSQL.append(" AND bank.nstatus = " 
							+ OBConstant.SettInstrStatus.SIGN 
							+ " \n");
					sbSQL.append(" AND bank.nSignUserID = " + qcf.getUserID() + " \n");
				
				sbSQL.append(" AND bank.nIsCanAccept = 1 \n");
			}
			
			//=========================================================================================================
			
//			业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK ) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.AUDITING))
			{
			 
					sbSQL.append(" AND bank.nStatus =" 
							+ OBConstant.OBBankPayStatus.CHECK);
				double midSignAmount=getMidAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID());
				//baihuili modify 2006.9.2
				if(midSignAmount==-1)
				{//无中间值
					log4j.print("进入无间断处理部分－－－－－－1111－－－－－－－－");
					sbSQL.append(" AND (NVL(bank.mAmount,0))>= " + this.getMinAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
				}
				else
				{
					log4j.print("进入间断处理部分－－－－－111－－－－－－－－－");
					sbSQL.append(" AND(");
					sbSQL.append("(");
					sbSQL.append("(NVL(bank.mAmount,0))  >= " + this.getMinAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0)) < " + midSignAmount + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
					sbSQL.append(") or");
					sbSQL.append("(");
					sbSQL.append("(NVL(bank.mAmount,0))>= " + this.getMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
					sbSQL.append(" AND (NVL(bank.mAmount,0))< " + this.getMoreMaxAuditingAmount(qcf.getUserID(),qcf.getNmodule(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
					sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
					sbSQL.append(")");
					sbSQL.append(")");
				}
			}
			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.OBBankPayStatus.AUDITING) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.AUDITING))
			{
			
					sbSQL.append(" AND bank.nstatus = " 
							+ OBConstant.OBBankPayStatus.AUDITING 
							+ " \n");
					sbSQL.append(" AND bank.NAUDITINGUSERID = " + qcf.getUserID() + " \n");
				
				sbSQL.append(" AND bank.nIsCanAccept != 1 \n");
			}
			
			//============================================================================================================
			
			//交易申请查询，查询所有状态
			if (qcf.getStatus() >= 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
			{
				
			    //交易申请查询，查询所有状态
			    sbSQL.append(" AND bank.nstatus = " + qcf.getStatus() + " \n");
			
			}
			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				sbSQL.append(" AND bank.DTEXECUTE >= ? \n");
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				sbSQL.append(" AND bank.DTEXECUTE <= ? \n");
			}
			// 交易金额-从
			if (qcf.getMinAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
			}
			// 交易金额-到
			if (qcf.getMaxAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
			}
			
		

			if (qcf.isOrderBy())
			{
				sbSQL.append(" order by  TO_CHAR(bank.dtconfirm,'YYYY-MM-DD') desc ,bank.nPayerAcctID \n");
			}
			else
			{
				sbSQL.append(" ORDER BY bank.dtconfirm ASC ,bank.nPayerAcctID \n");
			}
			
			initDAO();
		    con = transConn;
			
			//log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			log4j.print("@@@@@@@@@@@@@@@@"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;

			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartExe().trim() + " 00:00:00"));
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndExe().trim() + " 23:59:59"));
			}
	       
			rs = ps.executeQuery();
			while (rs.next())
			{
			    payInfo = new OBBankPayInfo();
			    
			    payInfo.setDtcheck(rs.getTimestamp("DTCHECK"));
			    payInfo.setDtconfirm(rs.getTimestamp("DTCONFIRM"));
			    payInfo.setDtdelete(rs.getTimestamp("DTDELETE"));
			    payInfo.setDtexecute(rs.getTimestamp("DTEXECUTE"));
			    payInfo.setDtsign(rs.getTimestamp("DTSIGN"));
			    payInfo.setId(rs.getLong("ID"));
			    payInfo.setNiscanaccept(rs.getLong("NISCANACCEPT"));
			    payInfo.setMamount(rs.getDouble("MAMOUNT"));
			    payInfo.setNcheckuserid(rs.getLong("NCHECKUSERID"));
			    payInfo.setNclientid(rs.getLong("NCLIENTID"));
			    payInfo.setNconfirmuserid(rs.getLong("NCONFIRMUSERID"));
			    payInfo.setNcurrencyid(rs.getLong("NCURRENCYID"));
			    payInfo.setNdeleteuserid(rs.getLong("NDELETEUSERID"));
			    payInfo.setNpayeeacctid(rs.getLong("NPAYEEACCTID"));
			    payInfo.setNpayeracctid(rs.getLong("NPAYERACCTID"));
			    payInfo.setNsignuserid(rs.getLong("NSIGNUSERID"));
			    payInfo.setNstatus(rs.getLong("NSTATUS"));
			    payInfo.setNtranstype(rs.getLong("NTRANSTYPE"));
			    payInfo.setSnote(rs.getString("SNOTE"));
			    payInfo.setSpayeeacctname(rs.getString("SPAYEEACCTNAME"));
			    payInfo.setSpayeeacctno(rs.getString("SPAYEEACCTNO"));
			    payInfo.setSpayeebankname(rs.getString("SPAYEEBANKNAME"));
			    payInfo.setSpayeecity(rs.getString("SPAYEECITY"));
			    payInfo.setSpayeeprov(rs.getString("SPAYEEPROV"));
			    //payInfo.setBudgetItemID(rs.getLong("BudgetItemID"));
			    //payInfo.setBudgetSystemID(rs.getLong("BudgetSystemID"));
			    //payInfo.setNmodule(rs.getLong("NMODULE"));
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				if (bIsValid) {
			    if(payInfo.getNiscanaccept()==1)
			    {
				    ReqQueryInstructionInfo reqQueInfo = new ReqQueryInstructionInfo();
			        ReqQueryInstructionInfo.ConditionInfo conditioInfo=reqQueInfo.new ConditionInfo();
			        
			        conditioInfo.setTransNO(String.valueOf(payInfo.getId()));
			        
			         ReqQueryInstructionInfo.ConditionInfo[] conditionInfos=new ReqQueryInstructionInfo.ConditionInfo[1];
					 conditionInfos[0]=conditioInfo;
					 reqQueInfo.setSystemId(2);//用常量
//					 if(payInfo.getNmodule()>0)
//	 		          {	//结算
//					 	reqQueInfo.setSystemId(1);//用常量
//	 		          }else{
//	 		          	reqQueInfo.setSystemId(2);//网银
//	 		          }
					// reqQueInfo.setOfficeID(payInfo.getNmodule());//办事处
					 reqQueInfo.setConditions(conditionInfos);
					 RespQueryInstructionInfo respinfo = BPClientAgent.queryInstruction(reqQueInfo);
					 RespQueryInstructionInfo.ResultInfo[] resultInfo = respinfo.getResults();
					 if(resultInfo != null)
					 {
					     payInfo.setBankPortalStatus(resultInfo[0].getStatus());
					 }
					 
			    }
			    else
			    {
			        	payInfo.setBankPortalStatus(1);
				}
				}
				vReturn.add(payInfo);
				
			}
			//log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			//log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			//log4j.error(e.toString());
		    e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	
	/**
	 * 修改指令交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_BANKPAY set nstatus=? where id=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	
	
	
//	银行直联批量复核翻页查询
	public PageLoader queryBankpayInfo(OBBankPayInfo info) throws Exception
	{
	 
		getBankpaySQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),   
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT1,
			"com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBBankPayInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public void getBankpaySQL(OBBankPayInfo info)
	{
		try{
				m_sbSelect = new StringBuffer();
				// select 
				m_sbSelect.append("O.ID ,O.NTRANSTYPE,u.s_accountno,O.MAMOUNT,O.SPAYEEBANKNAME ,O.SPAYEEACCTNO ,O.SPAYEEACCTNAME,O.DTEXECUTE,O.DTCONFIRM,O.nstatus,O.SNOTE");	
				// from 
				m_sbFrom = new StringBuffer();
				m_sbFrom.append("  OB_BANKPAY O,bs_bankaccountinfo u \n");
				//where
				m_sbWhere = new StringBuffer();
				m_sbWhere.append(" 1=1 ");
				m_sbWhere.append(" AND o.npayeracctid = u.N_ID(+) \n");
				m_sbWhere.append(" AND O.nclientid="+info.getNclientid()+ "\n");
				m_sbWhere.append(" AND O.nconfirmuserid != " + info.getNconfirmuserid() + " \n");
				
				 //提交日期-从
				if (info.getSStartSubmit() != null && info.getSStartSubmit().trim().length() > 0 && !info.getSStartSubmit().equals(""))
				{
					m_sbWhere.append(" AND O.DTCONFIRM >= to_date('"+info.getSStartSubmit()+"','yyyy-mm-dd') \n");
					
				}
				
				// 提交日期-到
				if (info.getSEndSubmit() != null && info.getSEndSubmit().trim().length() > 0)
				{
					m_sbWhere.append(" AND O.DTCONFIRM <= to_date('"+info.getSEndSubmit()+"','yyyy-mm-dd') \n");
				}
				// 交易金额-从
				if (info.getDMinAmount() > 0.0)
				{
					m_sbWhere.append(" AND O.mAmount >= " + info.getDMinAmount() + " \n");
				}
				// 交易金额-到
				if (info.getDMaxAmount() > 0.0)
				{
					m_sbWhere.append(" AND O.mAmount <= " + info.getDMaxAmount() + " \n");
				}
				
				if (info.getNstatus() ==-1)
				{
					
					m_sbWhere.append(" AND O.nstatus in ("+OBConstant.SettInstrStatus.SAVE+","+OBConstant.SettInstrStatus.APPROVALED+") \n");
					m_sbWhere.append(" and ((O.nstatus = 1 and O.ntranstype not in ( select Q.transtypeid from OB_APPROVALRELATIONNEW Q where  clientid ="+info.getNclientid()+ " and  officeid = "+info.getLOfficeID()+" and currencyid ="+info.getNcurrencyid()+" and Q.approvalid >0 and islowerunit = 2 group by Q.transtypeid))or O.nstatus = 20)\n");
				
				}
				if (info.getNstatus() ==OBConstant.SettInstrStatus.SAVE)
				{
					
					m_sbWhere.append(" AND O.nstatus ="+OBConstant.SettInstrStatus.SAVE+" \n");
					m_sbWhere.append(" and ((O.nstatus = 1 and O.ntranstype not in ( select Q.transtypeid from OB_APPROVALRELATIONNEW Q where  clientid ="+info.getNclientid()+ " and  officeid = "+info.getLOfficeID()+" and currencyid ="+info.getNcurrencyid()+" and Q.approvalid >0 and islowerunit = 2 group by Q.transtypeid))or O.nstatus = 20)\n");
				
				}
			
				if (info.getNstatus() == OBConstant.SettInstrStatus.APPROVALED)
				{
					
					m_sbWhere.append(" AND O.nstatus = " + OBConstant.SettInstrStatus.APPROVALED + " \n");
					
				
				}
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by O.DTCONFIRM desc,O.ID");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 根据查询条件查询表单类，查询出符合查询条件的银行指令信息
	 * Create Date: 2010-10-20
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryEbank(QueryCapForm qcf) throws Exception
	{
		OBBankPayInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Vector vReturn = new Vector();
		Collection vReturn = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append(" select fin.*,cfmUser.sname confirmUserName, checkUser.sname checkUserName, ");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, ");
			sbSQL.append(" t.s_branchname branchname,b.n_balance balance,t.s_accountno,bs.s_cancelcomments, ");
			sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid ");
			
			sbSQL.append(" from ob_bankpay fin,OB_User cfmUser,OB_User checkUser , OB_User signUser,OB_User delUser, ");
			sbSQL.append(" bs_bankaccountinfo t,BS_ACCTCURBALANCE b,");
			sbSQL.append(" BS_BANKINSTRUCTIONINFO bs");
			
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" and fin.npayeracctid = t.n_id ");
			sbSQL.append(" and fin.npayeracctid = b.n_accountid ");
			sbSQL.append(" and bs.s_transactionno(+)='2'||to_char(fin.id) ");
			
			if ( qcf.getCurrencyID() >0)
			{
				sbSQL.append(" AND fin.nCurrencyID=" + qcf.getCurrencyID() + " \n");
			}
			sbSQL.append(" AND fin.nClientID=" + qcf.getClientID() + " \n");
			//对银行账户权限的限制
			sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
			sbSQL.append(" select e.nAccountID \n");
			sbSQL.append(" from OB_EbankAccountByUserQuery e,bs_bankaccountinfo t \n");
			sbSQL.append(" where e.saccountno=t.s_accountno \n");
			sbSQL.append(" and t.n_accountstatus=1 \n");
			sbSQL.append(" and t.n_rdstatus=1 \n");
			sbSQL.append(" and t.n_ischeck=1 \n");
			sbSQL.append(" and e.nuserid="+qcf.getUserID()+"\n");
			sbSQL.append(" and t.n_currencytype="+qcf.getCurrencyID()+"\n");
			sbSQL.append(" ) \n");
			
			
			//业务状态查询
			//已保存
			if (qcf.getStatus() == OBConstant.SettInstrStatus.SAVE)
			{
				
				sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
			//	sbSQL.append(" AND fin.nConfirmUserID != " + qcf.getUserID() + " \n");
			}
			//已复核
			if (qcf.getStatus() == OBConstant.SettInstrStatus.CHECK)
			{
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
				//	sbSQL.append(" AND fin.nCheckUserID = " + qcf.getUserID() + " \n");
				
			}
			//已签认
			if (qcf.getStatus() == OBConstant.SettInstrStatus.SIGN)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SIGN + " \n");
				//	sbSQL.append(" AND fin.nSignUserID = " + qcf.getUserID() + " \n");
				
			
			}
			//处理中
			if (qcf.getStatus() == OBConstant.SettInstrStatus.DEAL)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.DEAL + " \n");
				
			}
			//审批中
			if (qcf.getStatus() == OBConstant.SettInstrStatus.APPROVALING)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.APPROVALING + " \n");
				
			}
			//已审批
			if (qcf.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.APPROVALED + " \n");
				
			}
			//已完成
			if (qcf.getStatus() == OBConstant.SettInstrStatus.FINISH)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
				
			}
			//已拒绝
			if (qcf.getStatus() == OBConstant.SettInstrStatus.REFUSE)
			{   
				
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.REFUSE + " \n");
				
			}
			//银行指令状态
			//撤销
			if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.CANCEL);
			}
			//以保存未发送
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SAVED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SAVED);
			}
			//支付处理中
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTING)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTING);
			}
			//支付成功
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTED);
			}
			//支付失败
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.FAILED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.FAILED);
			}
			//支付未知
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.UNKNOWENED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.UNKNOWENED);
			}
			//无状态
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.NONE)
			{
				sbSQL.append(" and nvl(n_statusid,-1) ="+OBConstant.BankInstructionStatus.UNSEND);
			}
			
			// 提交日期-从1
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
			}
			// 提交日期-到
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
			}
			
			
			// 交易金额-从
			if (qcf.getMinAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
			}
			// 交易金额-到
			
			if (qcf.getMaxAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
			}
			sbSQL.append("  order by TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID,fin.id desc ");
			ps = con.prepareStatement(sbSQL.toString());
			
			int nIndex = 1;
			//提交日期-从
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartSubmit().trim() + " 00:00:00"));
			}
			// 提交日期-到 
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndSubmit().trim() + " 23:59:59"));
			}
			
			rs = ps.executeQuery();
			log4j.print(sbSQL.toString());
			while(rs.next())
			{
				info = new OBBankPayInfo();
				info.setId(rs.getLong("ID"));
				info.setNclientid(rs.getLong("NCLIENTID"));
				info.setNcurrencyid(rs.getLong("NCURRENCYID"));
				info.setNtranstype(rs.getLong("NTRANSTYPE"));
				info.setMamount(rs.getDouble("MAMOUNT"));
				info.setDtexecute(rs.getTimestamp("DTEXECUTE"));
				info.setSnote(rs.getString("SNOTE"));
				info.setNstatus(rs.getLong("NSTATUS"));
				info.setNconfirmuserid(rs.getLong("NCONFIRMUSERID"));
				info.setDtconfirm(rs.getTimestamp("DTCONFIRM"));
				info.setNcheckuserid(rs.getLong("NCHECKUSERID"));
				info.setDtcheck(rs.getTimestamp("DTCHECK"));
				info.setNsignuserid(rs.getLong("NSIGNUSERID"));
				info.setDtsign(rs.getTimestamp("DTSIGN"));
				info.setNdeleteuserid(rs.getLong("NDELETEUSERID"));
				info.setDtdelete(rs.getTimestamp("DTDELETE"));
				info.setNiscanaccept(rs.getLong("NISCANACCEPT"));
				info.setNpayeracctid(rs.getLong("NPAYERACCTID"));
				info.setNpayeeacctid(rs.getLong("NPAYEEACCTID"));
				info.setSpayeeacctno(rs.getString("SPAYEEACCTNO"));
				info.setSpayeeacctname(rs.getString("SPAYEEACCTNAME"));
				info.setSpayeebankname(rs.getString("SPAYEEBANKNAME"));
				info.setSpayeeprov(rs.getString("SPAYEEPROV"));
				info.setSpayeecity(rs.getString("SPAYEECITY"));
				info.setBankconnectnumber(rs.getString("BANKCONNECTNUMBER"));
				info.setDepartmentnumber(rs.getString("DEPARTMENTNUMBER"));
				info.setName(rs.getString("NAME"));
				info.setBalance(rs.getDouble("balance"));
				info.setBranchname(rs.getString("branchname"));
				info.setS_accountno(rs.getString("s_accountno"));
				info.setCancelNote(rs.getString("s_cancelcomments"));
				info.setNEbankStatus(rs.getLong("n_statusid"));
				vReturn.add(info);
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			log4j.error(e.toString());
			throw new IException("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
		
		
	}
	//根据财务交易指令ID，查询银行账户指令信息
	public OBBankPayInfo findEbankById(long lInstructionID) throws Exception
	{
		OBBankPayInfo info =null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append(" SELECT fin.* ,");
			sbSQL.append(" cfmUser.sname confirmUserName,");
			sbSQL.append(" checkUser.sname checkUserName,");
			sbSQL.append(" signUser.sname signUserName,");
			sbSQL.append(" delUser.sname delUserName, ");
			sbSQL.append(" t.s_branchname branchname,b.n_balance balance,");
			sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid ");
			
			sbSQL.append(" FROM ob_bankpay fin,");
			sbSQL.append(" OB_USER cfmUser,");
			sbSQL.append(" OB_USER checkUser,");
			sbSQL.append(" OB_USER signUser,");
			sbSQL.append(" OB_USER delUser, ");
			sbSQL.append(" bs_bankaccountinfo t,BS_ACCTCURBALANCE b, ");
			sbSQL.append(" BS_BANKINSTRUCTIONINFO bs");
			
			sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+)");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
			sbSQL.append(" and bs.s_transactionno(+)='2'||to_char(fin.id) ");
			sbSQL.append(" and fin.npayeracctid = t.n_id ");
			sbSQL.append(" and fin.npayeracctid = b.n_accountid ");
			sbSQL.append(" AND fin.id="+lInstructionID);
			
			log4j.info("lInstructionID=" + lInstructionID);
			log4j.info("FinanceInstrEJB.findEbankByID()\n");
			log4j.info("SQL=\n" + sbSQL.toString());
			log4j.print("===================================sql="+sbSQL.toString());
			
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			info = new OBBankPayInfo();
			if(rs.next())
			{
				info.setId(rs.getLong("ID"));
				info.setNclientid(rs.getLong("NCLIENTID"));
				info.setNcurrencyid(rs.getLong("NCURRENCYID"));
				info.setNtranstype(rs.getLong("NTRANSTYPE"));
				info.setMamount(rs.getDouble("MAMOUNT"));
				info.setDtexecute(rs.getTimestamp("DTEXECUTE"));
				info.setSnote(rs.getString("SNOTE"));
				info.setNstatus(rs.getLong("NSTATUS"));
				info.setNconfirmuserid(rs.getLong("NCONFIRMUSERID"));
				info.setDtconfirm(rs.getTimestamp("DTCONFIRM"));
				info.setNcheckuserid(rs.getLong("NCHECKUSERID"));
				info.setDtcheck(rs.getTimestamp("DTCHECK"));
				info.setNsignuserid(rs.getLong("NSIGNUSERID"));
				info.setDtsign(rs.getTimestamp("DTSIGN"));
				info.setNdeleteuserid(rs.getLong("NDELETEUSERID"));
				info.setDtdelete(rs.getTimestamp("DTDELETE"));
				info.setNiscanaccept(rs.getLong("NISCANACCEPT"));
				info.setNpayeracctid(rs.getLong("NPAYERACCTID"));
				info.setNpayeeacctid(rs.getLong("NPAYEEACCTID"));
				info.setSpayeeacctno(rs.getString("SPAYEEACCTNO"));
				info.setSpayeeacctname(rs.getString("SPAYEEACCTNAME"));
				info.setSpayeebankname(rs.getString("SPAYEEBANKNAME"));
				info.setSpayeeprov(rs.getString("SPAYEEPROV"));
				info.setSpayeecity(rs.getString("SPAYEECITY"));
				info.setBankconnectnumber(rs.getString("BANKCONNECTNUMBER"));
				info.setDepartmentnumber(rs.getString("DEPARTMENTNUMBER"));
				info.setName(rs.getString("NAME"));
				info.setBalance(rs.getDouble("balance"));
				info.setBranchname(rs.getString("branchname"));
				info.setNEbankStatus(rs.getLong("n_statusid"));
				info.setBankCNAPSNo(rs.getString("bankcnapsno"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;


		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return info;
	}
	public ArrayList selectAuthority(long lUserID,long currencyType) throws Exception
	{
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" select e.saccountno ");
			sql.append(" from OB_EbankAccountByUserOperation e,bs_bankaccountinfo t ");
			sql.append(" where e.saccountno=t.s_accountno ");
			sql.append(" and t.n_accountstatus=1 ");
			sql.append(" and t.n_rdstatus=1 ");
			sql.append(" and t.n_ischeck=1 ");
			sql.append(" and e.nuserid="+lUserID);
			sql.append(" and t.n_currencytype="+currencyType);
			sql.append(" order by t.s_accountno ");
			
			log4j.info("lUserID=" + lUserID);
			log4j.info("currencyType=" + currencyType);
			log4j.info("SQL=\n" + sql.toString());
			
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			list = new ArrayList();
			String temp = "";
			while(rs.next())
			{
				temp=rs.getString("saccountno");
				
				list.add(temp);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
			
		}
		return list.size()>0?list:null;
	}
/*	public void updateEbank(OBBankPayInfo info,long lID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" update ob_bankpay ");
			sb.append(" set NCLIENTID="+info.getNclientid()+",");
			sb.append(" NCURRENCYID="+info.getNcurrencyid()+",");
		//	sb.append(" NTRANSTYPE="+info.getNtranstype()+",");
			sb.append(" MAMOUNT="+info.getMamount()+",");
			sb.append(" DTEXECUTE="+ info.getDtexecute()+",");
			sb.append(" SNOTE="+info.getSnote()+",");
			//sb.append(" NSTATUS="+);
			sb.append(" NCONFIRMUSERID="+info.getNconfirmuserid()+",");
			sb.append(" DTCONFIRM="+info.getDtconfirm()+",");
			sb.append(" NPAYERACCTID="+info.getNpayeracctid()+",");
			sb.append(" NPAYEEACCTID="+info.getNpayeeacctid()+",");
			sb.append(" SPAYEEACCTNO="+info.getSpayeeacctno()+",");
			sb.append(" SPAYEEACCTNAME="+info.getSpayeeacctname()+",");
			sb.append(" SPAYEEBANKNAME="+info.getSpayeebankname()+",");
			sb.append(" SPAYEEPROV="+info.getSpayeeprov()+",");
			sb.append(" SPAYEECITY="+info.getSpayeecity()+",");
			sb.append(" NAME='"+info.getName()+"',");
			sb.append(" BANKCONNECTNUMBER="+info.getBankconnectnumber()+",");
			sb.append(" DEPARTMENTNUMBER="+info.getDepartmentnumber());
			sb.append(" where id="+lID);
			log4j.print(sb.toString());
			ps = con.prepareStatement(sb.toString());
			ps.execute();
			
			}catch (IException e)
			{
				log4j.print(e.toString());
				throw e;
			}
			catch (Exception e)
			{
			    log4j.print(e.toString());
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
				    log4j.print(e.toString());
					throw new IException("Gen_E001");
				}
			}
			
	}*/
	
	
	//银行直联-业务处理-初始放大镜查询
	public OBBankPayInfo query_OBMagnifier(OBBankPayInfo info_bankpay) throws Exception
	{
		OBBankPayInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection vReturn = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append(" select t.n_id id1,t.s_accountno accountno,t.s_accountname name,t.s_branchname bankname,");
			sbSQL.append("NVL(to_char(a.n_usablebalance,'999,999,999,990.99'),TO_CHAR(0.00,'0.00')) currentbalance ");
			sbSQL.append("from bs_bankaccountinfo t,bs_acctcurbalance a,client_clientinfo cc ");
			sbSQL.append("where a.n_accountid(+)=t.n_id and t.n_clientid=cc.id  and t.n_accountstatus=1 and t.n_rdstatus=1 and t.n_ischeck=1  ");
			 
			if(info_bankpay.getNcurrencyid()>0)
			{
				sbSQL.append(" and t.n_currencytype=" + info_bankpay.getNcurrencyid() +"\n");
			}
			if(info_bankpay.getNclientid() > 0)
			{
				sbSQL.append( "  and t.n_clientid=" + info_bankpay.getNclientid()+" \n");
			}
			else{
				sbSQL.append("  AND t.n_officeid ="+info_bankpay.getLOfficeID()+"\n");
			}
			sbSQL.append(" and t.s_accountno in ");
			sbSQL.append( " (select e.saccountno ");
			sbSQL.append(" from OB_EbankAccountByUserOperation e,bs_bankaccountinfo t ");
			sbSQL.append( " where e.saccountno=t.s_accountno ");
			sbSQL.append(" and t.n_accountstatus=1 ");
			sbSQL.append(" and t.n_rdstatus=1 ");
			sbSQL.append( " and t.n_ischeck=1 ");
			sbSQL.append( " and e.nuserid="+info_bankpay.getNuserid()+" \n");
			sbSQL.append( " and t.n_currencytype="+info_bankpay.getNcurrencyid()+" ) \n");
			sbSQL.append( " order by t.s_accountno ");
			System.out.print("SQL="+sbSQL);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
		
			while(rs.next())
			{     
				info = new OBBankPayInfo();
				info.setName(rs.getString("name"));
				info.setBankname(rs.getString("bankname"));
				info.setCurrentbalance(rs.getString("currentbalance"));
				info.setAccountno(rs.getString("accountno"));
				info.setId1(rs.getLong("id1"));
				
				vReturn.add(info);	
			}
			if(vReturn!=null && vReturn.size()!=1){
				
				info = null;
				
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			log4j.info(sbSQL.toString());
			log4j.error(e.toString());
			throw new IException("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
		
		
	}
	
	
	//逐笔付款--付款方放大镜初始查询
	public FinanceInfo query_OBMagnifier1(FinanceInfo financeInfo) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection vReturn = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append(" select distinct b.saccountno displayAccountNo,b.saccountno||decode(baf.SBANKACCOUNTNO,null,'',' -- '||baf.SBANKACCOUNTNO) as saccountno_zhubi ,b.ID as nAccountID,b.sname, ");
			sbSQL.append(" NVL(to_char((nvl(k.mBalance, 0) - nvl(k.muncheckpaymentamount, 0)), '999,999,999,990.99'), TO_CHAR(0.00, '0.00') ) n_balance_zhubi ,");
			sbSQL.append("   NVL(to_char( nvl(k.mBalance, 0) -nvl(k.muncheckpaymentamount, 0) - (SELECT nvl(SUM(mAmount+MREALINTEREST+MREALCOMPOUNDINTEREST+MREALOVERDUEINTEREST+MREALSURETYFEE+MREALCOMMISSION) ,0)  aa  FROM ob_FinanceInStr \n");
			sbSQL.append("WHERE 1=1 AND ncurrencyid = "+financeInfo.getCurrencyID()+" AND npayeracctID =b.ID AND (nStatus = 1 OR nStatus = 10 OR nStatus = 20 \n");
			sbSQL.append("OR nStatus = 2 OR (nStatus = 3) OR (nStatus = 4 and  cpf_stransno is null)))  ,'999,999,999,990.99'    )    ,to_char(0.00, '0.00')   )  currentbalance_zhubi \n");
			sbSQL.append(" from SETT_ACCOUNTBANK  a,SETT_account b ,SETT_accounttype c,OB_AccountOwnedByUser oba,Sett_bankAccountOfFiliale baf, SETT_SubAccount k");
			sbSQL.append(" where   b.id=k.nAccountID(+) and oba.sAccountNo=b.sAccountNo and a.naccountid(+)=b.id and b.nAccountTypeID=c.id(+) and c.nAccountGroupID=1 ");
			sbSQL.append("and b.id = baf.NWITHINACCOUNTID(+) and b.nStatusID=1 and b.nCheckStatusID =4");
			sbSQL.append(" and oba.nUserID="+financeInfo.getNUserID()+" and b.nclientid ="+financeInfo.getClientID()+"  and b.ncurrencyid ="+financeInfo.getCurrencyID());
			sbSQL.append(" and b.saccountno in ");
			sbSQL.append(" (select a.saccountno ");
			sbSQL.append(" from OB_AccountOwnedByUser a, Sett_Account ai ");
			sbSQL.append(" where ai.nStatusID=1");
			sbSQL.append(" and a.saccountno=ai.saccountno");
			sbSQL.append(" and a.nUserID="+financeInfo.getNUserID());
			sbSQL.append(" and ai.ncurrencyid="+financeInfo.getCurrencyID()+")");		
			System.out.print("SQL="+sbSQL);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
		
			while(rs.next())
			{     
				info = new FinanceInfo();
				info.setSaccountno_zhubi(rs.getString("saccountno_zhubi"));	
				info.setN_balance_zhubi(rs.getString("n_balance_zhubi"));
				info.setCurrentbalance_zhubi(rs.getString("currentbalance_zhubi"));
				info.setNAccountID(rs.getLong("nAccountID"));
				
				vReturn.add(info);	
			}
			if(vReturn!=null && vReturn.size()!=1){
				
				info = null;
				
			}
			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			log4j.info(sbSQL.toString());
			log4j.error(e.toString());
			throw new IException("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
		
		
	}
	public void updateEbank(OBBankPayInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		StringBuffer sql = new StringBuffer();
		int index = 1;
		try{
			con = Database.getConnection();
			sql.append(" UPDATE OB_BANKPAY ");
			sql.append(" SET bankconnectnumber = ?, ");
			sql.append(" departmentnumber = ?, ");
			sql.append(" mamount = ?, ");
			sql.append(" nclientid = ?,");
			sql.append(" npayeeacctid = ?, ");
			sql.append(" spayeeacctname = ?,");
			sql.append(" spayeecity = ?,");//
			sql.append(" npayeracctid = ?,");//
			sql.append(" spayeebankname = ?,");
			sql.append(" ntranstype = ?,");//
			sql.append(" spayeeprov = ?,");//
			sql.append(" nconfirmuserid = ?,");//
			sql.append(" ncurrencyid = ?,");//
			sql.append(" dtconfirm = ?,");
			sql.append(" dtexecute = ?,");
			sql.append(" snote = ?,");
			sql.append(" nstatus = ?,");
			sql.append(" name = ?,");
			sql.append(" spayeeacctno = ?,");
			sql.append(" BANKCNAPSNO = ? ");
			sql.append(" where id = ? ");
			ps = con.prepareStatement(sql.toString());
			log4j.print("*********************************sql="+sql.toString());
			ps.setString(index++, info.getBankconnectnumber());
			ps.setString(index++, info.getDepartmentnumber());
			ps.setDouble(index++, info.getMamount());
			ps.setLong(index++, info.getNclientid());
			ps.setLong(index++, info.getNpayeeacctid());
			ps.setString(index++, info.getSpayeeacctname());
			ps.setString(index++, info.getSpayeecity());
			ps.setLong(index++, info.getNpayeracctid());
			ps.setString(index++, info.getSpayeebankname());
			ps.setLong(index++, info.getNtranstype());
			ps.setString(index++, info.getSpayeeprov());
			ps.setLong(index++, info.getNconfirmuserid());
			ps.setLong(index++, info.getNcurrencyid());
			ps.setTimestamp(index++, info.getDtconfirm());
			ps.setTimestamp(index++, info.getDtexecute());
			ps.setString(index++, info.getSnote());
			ps.setLong(index++, info.getNstatus());
			ps.setString(index++, info.getName());
			ps.setString(index++, info.getSpayeeacctno());
			ps.setString(index++, info.getBankCNAPSNo());
			ps.setLong(index++, info.getId());
			
			ps.executeUpdate();
			
			if(ps!=null)
			{
				ps.close();
			}
			if(con!=null)
			{
				con.close();
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(ps!=null)
			{
				ps.close();
			}
			if(con!=null)
			{
				con.close();
			}
		}
		
	}
}


    


/*
 * Created on 2006-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.creditprove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;
/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreditProveDao extends LoanDAO {
	
	public CreditProveDao()
	{
		super("loan_creditprove");
	}	
	

	//新增信贷证明，将info里的信息insert到表loan_CreditProve里
	public long add(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{
				con = Database.getConnection();
				strSQL = new StringBuffer();
				strSQL.append("INSERT INTO loan_creditprove VALUES((SELECT nvl(MAX(id)+1,1) ID FROM loan_creditProve ),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1) ");
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				
				ps.setString( nIndex++ , info.getCode());
				ps.setLong( nIndex++ , info.getConferContractNo());
				ps.setString( nIndex++ , info.getApplyClient());
				ps.setString( nIndex++ , info.getCertificateBank());
				ps.setString( nIndex++ , info.getCreditProveID());
				ps.setDouble( nIndex++ , info.getBalance());
				ps.setLong( nIndex++ , info.getBCurrencyID());
				ps.setString( nIndex++ , info.getBeneficiaryName());
				ps.setString( nIndex++ , info.getProjectName());
				ps.setDouble( nIndex++ , info.getMoney());
				ps.setLong( nIndex++ , info.getMCurrencyID());
				ps.setDouble( nIndex++ , info.getExchangeRate());
				ps.setDouble( nIndex++ , info.getConvertRMB());
				ps.setLong( nIndex++ , info.getApplyMonth());
				ps.setDate( nIndex++ , DataFormat.getDate(info.getStartDate()));
				ps.setDate( nIndex++ , DataFormat.getDate(info.getEndDate()));
				ps.setDouble( nIndex++ , info.getCharge());
				ps.setLong( nIndex++ , info.getStatusID());
				ps.setString( nIndex++ , info.getApplyPurpose());
				ps.setString( nIndex++ , info.getRemark());
				ps.setLong( nIndex++ , info.getOfficeId());
								
				lret = ps.executeUpdate();
				cleanup(ps);
				cleanup(con);
				
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		
		return lret;
	}
	
	//	修改信贷证明，将info里的信息update到表loan_CreditProve里
	public long modify(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{				
				con = Database.getConnection();
			
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE loan_creditprove ");
				strSQL.append(" SET ConferContractNo = ? , ");//受信合同号
				strSQL.append(" ApplyClient = ? ,");//申请单位
				strSQL.append(" CertificateBank = ? ,");//开证银行
				strSQL.append(" CreditProveID = ? ,");//信贷证明编号
				strSQL.append(" Balance = ? ,");//余额
				strSQL.append(" BCurrencyID = ? ,");//余额币种
				strSQL.append(" BeneficiaryName = ? ,");//受益人名称
				strSQL.append(" ProjectName = ? ,");//项目名称
				strSQL.append(" Money = ? ,");//金额
				strSQL.append(" MCurrencyID = ? ,");//金额币种
				strSQL.append(" ExchangeRate = ? ,");//与人民币汇率
				strSQL.append(" ConvertRMB = ? ,");//折合人民币
				strSQL.append(" ApplyMonth = ? ,");//申请期限
				strSQL.append(" StartDate = to_date(?,'yyyy-mm-dd') ,");//起始日期
				strSQL.append(" EndDate = to_date(?,'yyyy-mm-dd') ,");//到期日期
				strSQL.append(" Charge = ? ,");//手续费
				strSQL.append(" StatusID = ? ,");//状态
				strSQL.append(" ApplyPurpose = ? ,");//申请用途
				strSQL.append(" Remark = ? ");//备注
				strSQL.append(" where CODE = ? ");
				
				
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				
				ps.setLong( nIndex++ , info.getConferContractNo());
				ps.setString( nIndex++ , info.getApplyClient());
				ps.setString( nIndex++ , info.getCertificateBank());
				ps.setString( nIndex++ , info.getCreditProveID());
				ps.setDouble( nIndex++ , info.getBalance());
				ps.setLong( nIndex++ , info.getBCurrencyID());
				ps.setString( nIndex++ , info.getBeneficiaryName());
				ps.setString( nIndex++ , info.getProjectName());
				ps.setDouble( nIndex++ , info.getMoney());
				ps.setLong( nIndex++ , info.getMCurrencyID());
				ps.setDouble( nIndex++ , info.getExchangeRate());
				ps.setDouble( nIndex++ , info.getConvertRMB());
				ps.setLong( nIndex++ , info.getApplyMonth());
				ps.setString( nIndex++ , info.getStartDate());
				ps.setString( nIndex++ , info.getEndDate());
				ps.setDouble( nIndex++ , info.getCharge());
				ps.setLong( nIndex++ , info.getStatusID());
				ps.setString( nIndex++ , info.getApplyPurpose());
				ps.setString( nIndex++ , info.getRemark());
				ps.setString( nIndex , info.getCode());
				
				lret = ps.executeUpdate();				
				cleanup(ps);
				cleanup(con);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}		

		
		return lret;
	}
	
	//	删除信贷证明，将info里的信息update到表loan_CreditProve里 逻辑删除
	public long delete(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		Connection con = null;
		PreparedStatement ps = null;
		StringBuffer strSQL = null;
		
		try
		{
			if( info != null )
			{
				con = Database.getConnection();
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE loan_creditprove SET statusId = 0");
				strSQL.append(" WHERE code = ?");
				ps = con.prepareStatement(strSQL.toString());
				int nIndex = 1;
				ps.setString( nIndex++ , info.getCode());				
				lret = ps.executeUpdate();			
				cleanup(ps);
				cleanup(con);			
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}			
		return lret;
	}
	
	//	查询信贷证明，按info里的信息查询表loan_CreditProve
	//返回由CreditProveInfo组成的集合
	public Vector findByCreditProve(CreditProveInfo info) throws Exception
	{
		Vector vret = new Vector();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = null;
		
		try
		{
			con = Database.getConnection();
			strSQL = new StringBuffer();
					
			strSQL.append(" SELECT t.*,c.sname ClientName ,a.scontractno Contractno ");	
			strSQL.append(" FROM loan_creditprove t ,client c , loan_bank_creditext a  WHERE t.StatusID >0 " );
			strSQL.append(" AND t.officeid = " + info.getOfficeId());
			strSQL.append(" AND c.scode = t.applyclient AND a.id = t.confercontractno ");
	
			if (info != null && !info.getCode().equals(""))
			{
				strSQL.append(" AND t.code = '"+info.getCode()+"'");
			}
			
			if (info != null && info.getCode().equals(""))
			{
				if ( info.getApplyClient()!= null && !info.getApplyClient().equals(""))
				{
					strSQL.append(" AND t.ApplyClient = '"+info.getApplyClient()+"'");
				}
				if ( info.getConferContractNo()>0)
				{
					strSQL.append(" AND t.ConferContractNo = '"+info.getConferContractNo()+"'");
				}
				if ( info.getCertificateBank()!=null && !info.getCertificateBank().equals(""))
				{
					strSQL.append(" AND t.CertificateBank = '"+info.getCertificateBank()+"'");
				}
				if ( info.getApplyMonth()>0 )
				{
					strSQL.append(" AND t.ApplyMonth = "+info.getApplyMonth());
				}
				if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
				{
					strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");
					strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && !(info.getEndStartDate()!=null && !info.getEndStartDate().equals("")))
				{
					strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");				
				}
				if ( !(info.getFromStartDate()!=null && !info.getFromStartDate().equals("") )&& info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
				{				
					strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
				{
					strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");
					strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
				}
				if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && !(info.getEndEndDate()!=null && !info.getEndEndDate().equals("")))
				{
					strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");				
				}
				if ( !(info.getFromEndDate()!=null && !info.getFromEndDate().equals("") )&& info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
				{				
					strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
				}									
				if ( info.getStatusID()>0 )
				{
					strSQL.append(" AND t.StatusID = "+info.getStatusID());
				}
			}
			
			ps = con.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				CreditProveInfo cpInfo = new CreditProveInfo();
				
				cpInfo.setCode( rs.getString("Code")); //内部流水号
				cpInfo.setApplyClient( rs.getString("ApplyClient")); //申请单位编号
				cpInfo.setCertificateBank( rs.getString("CertificateBank")); //开证银行
				cpInfo.setCreditProveID( rs.getString("CreditProveID")); //信贷证明编号
				cpInfo.setMCurrencyID( rs.getLong("MCurrencyID"));//金额币种
				cpInfo.setMoney( rs.getDouble("Money")); //金额
				cpInfo.setStartDate( rs.getDate("StartDate").toString()); //起始日期
				cpInfo.setEndDate( rs.getDate("EndDate").toString()); //到期日期
				cpInfo.setApplyMonth( rs.getLong("ApplyMonth")); //期限
				cpInfo.setStatusID( rs.getLong("StatusID")); //状态
				
				cpInfo.setConferContractNo( rs.getLong("conferContractNo"));//受信合同号id
				cpInfo.setBalance( rs.getDouble("balance"));//余额
				cpInfo.setBCurrencyID( rs.getLong("BCurrencyID"));//余额币种 
				cpInfo.setBeneficiaryName( rs.getString("beneficiaryName"));//受益人
				cpInfo.setProjectName( rs.getString("projectName"));//项目名称
				cpInfo.setExchangeRate( rs.getDouble("exchangeRate"));//与人民币汇率
				cpInfo.setConvertRMB( rs.getDouble("convertRMB"));//折合人民币
				cpInfo.setCharge( rs.getDouble("charge"));//手续费
				cpInfo.setApplyPurpose( rs.getString("applyPurpose"));//申请用途
				cpInfo.setRemark( rs.getString("remark"));//备注
				cpInfo.setClientName( rs.getString("ClientName"));//申请单位名称
				cpInfo.setContractno( rs.getString("Contractno"));//授信合同号
				
				
				vret.add( cpInfo );
				
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);					
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}	
		
		return vret.size() >0 ? vret : null;
	}

	//  查询，用分页的方式
	public PageLoader findWithPage(CreditProveInfo info) throws Exception
	{
		String[] sql = getSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		return pageLoader;
		
	}
	//分页当中引用的得到sql语句的函数
	private String[] getSQL (CreditProveInfo info)
	{
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		
		//select
		sql[0] =" t.*,c.sname ClientName ,a.scontractno Contractno ";
		//from
		sql[1] =" loan_creditprove t ,client c , loan_bank_creditext a";
		//where
		sql[2] =" t.StatusID >0  AND t.officeid = " + info.getOfficeId() +" AND c.scode = t.applyclient AND a.id = t.confercontractno ";
		
		
		/**********处理查询条件*************/
		if (info != null && !info.getCode().equals(""))
		{
			strSQL.append(" AND t.code = '"+info.getCode()+"'");
		}
		
		if (info != null && info.getCode().equals(""))
		{
			if ( info.getApplyClient()!= null && !info.getApplyClient().equals(""))
			{
				strSQL.append(" AND t.ApplyClient = '"+info.getApplyClient()+"'");
			}
			if ( info.getConferContractNo() > 0 )
			{
				strSQL.append(" AND t.ConferContractNo = '"+info.getConferContractNo()+"'");
			}
			if ( info.getCertificateBank()!=null && !info.getCertificateBank().equals(""))
			{
				strSQL.append(" AND t.CertificateBank = '"+info.getCertificateBank()+"'");
			}
			if ( info.getApplyMonth()>0 )
			{
				strSQL.append(" AND t.ApplyMonth = "+info.getApplyMonth());
			}
			if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
			{
				strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");
				strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromStartDate()!=null && !info.getFromStartDate().equals("") && !(info.getEndStartDate()!=null && !info.getEndStartDate().equals("")))
			{
				strSQL.append(" AND t.StartDate >= to_date('"+info.getFromStartDate()+"','yyyy-mm-dd')");				
			}
			if ( !(info.getFromStartDate()!=null && !info.getFromStartDate().equals("") )&& info.getEndStartDate()!=null && !info.getEndStartDate().equals(""))
			{				
				strSQL.append(" AND t.StartDate <= to_date('"+info.getEndStartDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
			{
				strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");
				strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
			}
			if ( info.getFromEndDate()!=null && !info.getFromEndDate().equals("") && !(info.getEndEndDate()!=null && !info.getEndEndDate().equals("")))
			{
				strSQL.append(" AND t.EndDate >= to_date('"+info.getFromEndDate()+"','yyyy-mm-dd')");				
			}
			if ( !(info.getFromEndDate()!=null && !info.getFromEndDate().equals("") )&& info.getEndEndDate()!=null && !info.getEndEndDate().equals(""))
			{				
				strSQL.append(" AND t.EndDate <= to_date('"+info.getEndEndDate()+"','yyyy-mm-dd')");
			}									
			if ( info.getStatusID()>0 )
			{
				strSQL.append(" AND t.StatusID = "+info.getStatusID());
			}
		}
		
		sql[2] = sql[2] + strSQL.toString();
		//	order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" \n order by t.code" + strDesc);
		sql[3] = oBuf.toString();
		
		return sql;
			
	}
	//产生流水号
	
	public String getNextCode() throws Exception 
	{
		String strSQL = " SELECT code from loan_creditProve WHERE code LIKE ?";
		String strTemp = "BJBC";
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,strTemp + "%");
			rs = pstmt.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("code");
				scode = scode.substring(strTemp.length(),scode.length());
				if(scode != null && !scode.equals(""))
				{
					int iTemp = Integer.parseInt(scode);
					if(iCode < iTemp)
					{
						iCode = iTemp;
					}
				}
			}
			iCode += 1;
			newCode = "" +iCode;
			while(newCode.length() < 4)
			{
				newCode = "0" + newCode;
			}
			newCode = strTemp + newCode;
			
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(pstmt);
			cleanup(conn);
		}
		return newCode;
	}
	
	
    //释放结果集
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("进入关闭RS方法");
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	//释放 ps
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			//Log.print("进入关闭PS方法");
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	//释放连接
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("进入关闭连接方法");
			/**transConn　不为空表明这个数据库连接相关的事务不是容器维护的，因此不在此处关闭
			 * 即　Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false && transConn == null)
			{
				//Log.print("关闭连接--开始");
				con.close();
				con = null;
				//Log.print("关闭连接--结束");
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw sqle;
		}
	}
	
	
	
}

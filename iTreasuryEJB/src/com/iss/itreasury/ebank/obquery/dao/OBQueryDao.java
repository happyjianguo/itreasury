/*
 * Created on 2004-2-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obquery.dao;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.*;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.repayplan.dataentity.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.ebank.util.PagedStatement;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.obquery.dataentity.*;
import com.iss.itreasury.loan.freeapply.dataentity.*; 


public class OBQueryDao extends OBBaseDao
{
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	private String[] getLoanApplySQL(OBQueryTermInfo qInfo)
	{
		String[] sql=new String[4];
		StringBuffer sqlBuf=new StringBuffer();
		
		//select
		sql[0]=" l.ID,l.nTypeID,l.nStatusID,l.sApplyCode,"
			+" c.sName as borrowClientName,c2.sName as consignClientName,l.mLoanAmount,"
			+" l.nIntervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
			+" l.nBankInterestID,l.mAdjustRate,"  
			+" DECODE(l.nTypeID,"+LOANConstant.LoanType.TX+",mDiscountRate,mInterestRate) as interestRate ,"
			//add by fxzhang 2007-01-05
			+" l.mstaidadjustrate, "
			+" l.dtStartDate,l.dtEndDate, l.dtInputDate";

		//from
		sql[1]=" loan_loanform l,Client c,Client c2,userInfo u,userInfo u2";

		//where
		sql[2]=" c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID ";

		/**********处理查询条件*************/
		//币种
		if ( qInfo.getCurrencyID()>0 )
			sqlBuf.append(" and l.NCURRENCYID="+qInfo.getCurrencyID() );	
		//贷款种类
		if ( qInfo.getTypeID()!=99 )
			sqlBuf.append(" and l.nTypeID="+qInfo.getTypeID() );

		//申请书编号开始
		if ( (qInfo.getCodeBegin()!=null) && (qInfo.getCodeBegin().length()>0) )
			sqlBuf.append(" and l.sApplyCode<='"+qInfo.getCodeBegin()+"'" );

		//申请书编号结束
		if ( (qInfo.getCodeEnd()!=null) && (qInfo.getCodeEnd().length()>0) )
			sqlBuf.append(" and l.sApplyCode>='"+qInfo.getCodeEnd()+"'" );

		//申请书状态
		if ( (qInfo.getStatusID()!=99) )
			sqlBuf.append(" and l.nStatusID="+qInfo.getStatusID() );
/*
		//借款单位
		if ( qInfo.getBorrowClientID()>0 )
			sqlBuf.append(" and l.nBorrowClientID="+qInfo.getBorrowClientID() );
*/
		//借款单位账号
		if ( (qInfo.getBorrowClientAccount()!=null)&&(qInfo.getBorrowClientAccount().length()>0) )
			sqlBuf.append(" and c.sAccount='"+qInfo.getBorrowClientAccount()+"'");

		//客户分类
		if ( qInfo.getLoanClientTypeID()>0 )
			sqlBuf.append(" and c.nLoanClientTypeID="+qInfo.getLoanClientTypeID() );

		//主管单位
		if ( qInfo.getParentCorpID()>0 )
			sqlBuf.append(" and c.nParentCorpID1="+qInfo.getParentCorpID() );

		//委托单位
		if ( qInfo.getConsignClientID()>0 )
			sqlBuf.append(" and l.nConsignClientID="+qInfo.getConsignClientID() );

		//委托单位账号
		if ( (qInfo.getConsignClientAccount()!=null)&&(qInfo.getConsignClientAccount().length()>0 ))
			sqlBuf.append(" and c2.sAccount='"+qInfo.getConsignClientAccount()+"'");

		//贷款金额开始
		if ( qInfo.getAmountBegin()>0 )
			sqlBuf.append(" and l.mLoanAmount>="+DataFormat.formatAmount(qInfo.getAmountBegin()) );

		//贷款金额结束
		if ( qInfo.getAmountEnd()>0 )
			sqlBuf.append(" and l.mLoanAmount<="+DataFormat.formatAmount(qInfo.getAmountEnd()) );

		//贷款日期开始
		if ( qInfo.getLoanDateBegin()!=null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getLoanDateBegin())+"','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getLoanDateEnd()!=null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getLoanDateEnd())+"','yyyy-mm-dd') ");

		//贷款期限
		if (qInfo.getIntervalNum() >0 )
			sqlBuf.append(" and l.nIntervalNum="+qInfo.getIntervalNum() );

		//提交日期开始
		if (qInfo.getInputDateBegin() !=null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)>= To_Date('" + DataFormat.getDateString(qInfo.getInputDateBegin())+"','yyyy-mm-dd') ");

		//提交日期结束
		if (qInfo.getInputDateEnd()!=null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)<= To_Date('" + DataFormat.getDateString(qInfo.getInputDateEnd())+"','yyyy-mm-dd') ");

		//保证方式
		if ( qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT )
			sqlBuf.append(" and l.nIsCredit=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.ASSURE )
			sqlBuf.append(" and l.IsAssure=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.IMPAWN )
			sqlBuf.append(" and l.IsImpawn=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.PLEDGE )
			sqlBuf.append(" and l.IsPledge=1");

		//信用等级
		if ( qInfo.getCreditLevelID() >0)
			sqlBuf.append(" and c.NCREDITLEVELID="+qInfo.getCreditLevelID() );

		//是否股东
		if (qInfo.getIsPartner() >0)
			sqlBuf.append(" and c.NISPARTNER="+qInfo.getIsPartner() );

		//是否技改贷款
		if (qInfo.getIsTechnical() >0)
			sqlBuf.append(" and l.NISTECHNICAL="+qInfo.getIsTechnical() );

		//是否循环贷款
		if (qInfo.getIsCircle() >0)
			sqlBuf.append(" and l.NISCIRCLE="+qInfo.getIsCircle());

		//管理人
		if (qInfo.getInputUserID()>0)
			sqlBuf.append(" and l.nInputUserID="+qInfo.getInputUserID());
			
		if(qInfo.getClientID()>0)
		{
			sqlBuf.append(" and (l.nBorrowClientID="+qInfo.getClientID()+" or l.nConsignClientID="+qInfo.getClientID()+") ");
		}


		sql[2]=sql[2]+sqlBuf.toString();
		
	
		//order
		String strDesc = qInfo.getLDesc() == 1 ? " desc " : "";
		StringBuffer oBuf=new StringBuffer();
		switch ((int) qInfo.getLOrderParam())
		{
			case 1 :							//贷款种类
				oBuf.append(" \n order by l.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by l.sApplyCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c.sName"+strDesc);
				break;
			case 4:
				oBuf.append(" \n order by c2.sName"+strDesc);
				break;
			case 5:
				oBuf.append(" \n order by l.mLoanAmount"+strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by l.mInterestRate"+strDesc );
				break;
			case 7 :
				oBuf.append(" \n order by l.nIntervalNum"+strDesc );
				break;
			case 8 :
				oBuf.append(" \n order by l.dtStartDate"+strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by l.dtInputDate"+strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by l.nStatusID"+strDesc);
				break;
			case 11:
				oBuf.append( " \n order by u.sName" + strDesc ) ;
				break ;
			case 12:
				oBuf.append( " \n order by u2.sName" + strDesc ) ;
				break ;
			default:
				oBuf.append( " \n order by l.ID" + strDesc ) ;
				break;
		}
		sql[3]=oBuf.toString();
		return sql;
	}
	public Collection queryLoan(OBQueryTermInfo termInfo) throws Exception
	{
		ArrayList aList=new ArrayList();
		String strSQL="";
		Connection conn = null;
		PagedStatement ps = null;
		ResultSet rs = null;
	
		try {
			conn=Database.getConnection() ;
			String sql[]=getLoanApplySQL(termInfo);
			strSQL="select "+sql[0]+" from "+sql[1]+" where " +sql[2] + sql[3];
			log.print(strSQL);
			ps=new PagedStatement(conn,strSQL, termInfo.getTxtPageNo(),Constant.PageControl.CODE_PAGELINECOUNT);
			rs=ps.executeQuery() ;
			while (rs.next())
			{
				LoanApplyInfo applyInfo = new LoanApplyInfo ();
				applyInfo.setID(rs.getLong("ID"));
				applyInfo.setTypeID(rs.getLong("ntypeid"));
				applyInfo.setApplyCode(rs.getString("sapplycode"));
				applyInfo.setStatusID(rs.getLong("nStatusID"));
				applyInfo.setBorrowClientName( rs.getString("borrowClientName"));
				applyInfo.setConsignClientName( rs.getString("consignClientName"));
				applyInfo.setLoanAmount(rs.getDouble("mloanamount"));
				applyInfo.setIntervalNum(rs.getLong("nintervalnum"));
				applyInfo.setInputUserName( rs.getString("inputUserName"));
				applyInfo.setLastCheckUserName( rs.getString("lastCheckUserName"));
				applyInfo.setBankInterestID(rs.getLong("nbankinterestid"));
				applyInfo.setAdjustRate( rs.getDouble("mAdjustRate"));		
				applyInfo.setInterestRate( rs.getDouble("interestRate"));
				applyInfo.setStaidAdjustRate( rs.getDouble("mstaidadjustrate"));
				applyInfo.setStartDate(rs.getTimestamp("dtstartdate"));
				applyInfo.setEndDate(rs.getTimestamp("dtenddate"));		
				applyInfo.setInputDate(rs.getTimestamp("dtinputdate"));
				aList.add(applyInfo);					
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);				
		}
		return aList;
	}
	public QuerySumInfo queryLoanApplySum(OBQueryTermInfo qInfo) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QuerySumInfo sumInfo=new QuerySumInfo();
		String[] sql=getLoanApplySQL(qInfo);
		String strSQL="";

		try
		{
			sql[0]="count(*) as resultSize,sum(l.mLoanAmount) as sumLoanAmount";
			strSQL="select " + sql[0] + " from " + sql[1] +" where "+sql[2];
			log.print(strSQL);

			con=Database.getConnection() ;
			ps=con.prepareStatement( strSQL );
			rs=ps.executeQuery() ;
			if ( rs.next() )
			{
				sumInfo.setTotalApplyAmount( rs.getDouble("sumLoanAmount"));
				sumInfo.setAllRecord( rs.getLong("resultSize"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
		  cleanup(rs);
		  cleanup(ps);
		  cleanup(con);
		}
		return sumInfo;
	}

	public Collection queryInstr(QuerySumInfo sumInfo,OBQueryTermInfo termInfo) throws Exception
	{
		String strSQL="";
		Connection conn = null;
		PagedStatement ps = null;
		ResultSet rs = null;  
		ArrayList aList=new ArrayList();

		try
		{
			conn=Database.getConnection() ;

			strSQL=" select a.*,b.sName from OB_LOANINSTRVIEW a,OB_User b where b.id(+)=a.nInputuserID";
			//指令类型
			if (termInfo.getTypeID()>0)
				strSQL+=" and a.nInstrTypeID=?";
			//指令状态
			if (termInfo.getStatusID()!=99)
				strSQL+=" and a.nStatusID=?";
			//录入时间开始
			if ( termInfo.getInputDateBegin()!=null)
				strSQL+=" and TRUNC(a.dtInputDate)>= ?";
			//录入时间结束
			if ( termInfo.getInputDateEnd()!=null)
				strSQL+=" and TRUNC(a.dtInputDate)<= ?";
			//合同编号开始
			if ( (termInfo.getCodeBegin()!=null) && (termInfo.getCodeBegin().length()>0) )
				strSQL+=" and a.sContractCode>=?" ;
			//合同编号结束
			if ( (termInfo.getCodeEnd()!=null) && (termInfo.getCodeEnd().length()>0) )
				strSQL+=" and a.sContractCode<=?" ;
			//指令号开始
			if ( (termInfo.getInstrNoBegin()!=null) && (termInfo.getInstrNoBegin().length()>0) )
				strSQL+=" and a.sInstructionNo>=?" ;
			//指令号结束
			if ( (termInfo.getInstrNoEnd()!=null) && (termInfo.getInstrNoEnd().length()>0) )
				strSQL+=" and a.sInstructionNo<=?" ;
			//处理人
			if ( termInfo.getInputUserID()>0 )
				strSQL+=" and a.nInputUserID=?" ;
			//客户编号，只有统一客户的才能查看和修改
			if ( termInfo.getClientID()>0)
			{
				/*if ( termInfo.getTypeID() <=OBConstant.LoanInstrType.LOAN_ZGXEZCQ )
				{
					strSQL+=" and (a.nClientID=? or a.nConsignClientID=?) ";
				}
				else 
				if (termInfo.getTypeID()==OBConstant.LoanInstrType.FREE || termInfo.getTypeID()==OBConstant.LoanInstrType.LOAN_WTTJTH ||termInfo.getTypeID()==OBConstant.LoanInstrType.LOAN_WT)
				{
					strSQL+=" and  a.nConsignClientID=? ";
				}
				else
				{
					strSQL+=" and (a.nClientID=?  and type=1 ) or ()";
				}*/
				strSQL+=" and ((a.nConsignClientID=?  and decode(a.ninstrtypeid," + OBConstant.LoanInstrType.LOAN_WT + ",1," + OBConstant.LoanInstrType.FREE + ",1,0)=1 ) or (a.nClientID=? and decode(a.ninstrtypeid," + OBConstant.LoanInstrType.LOAN_WT + ",1," + OBConstant.LoanInstrType.FREE + ",1,0)=0 )) ";
			}
			ps=new PagedStatement(conn,strSQL, termInfo.getTxtPageNo(),Constant.PageControl.CODE_PAGELINECOUNT);			
			int n=1;
			//指令类型
			if (termInfo.getTypeID()>0)
				ps.setLong(n++,termInfo.getTypeID() );
			//指令状态
			if (termInfo.getStatusID()!=99)
				ps.setLong(n++,termInfo.getStatusID() );
			//录入时间开始
			if ( termInfo.getInputDateBegin()!=null)
				ps.setTimestamp(n++,termInfo.getInputDateBegin() );
			//录入时间结束
			if ( termInfo.getInputDateEnd()!=null)
				ps.setTimestamp(n++,termInfo.getInputDateEnd() );
			//合同编号开始
			System.out.println("codeBegin"+termInfo.getCodeBegin());
			if ( (termInfo.getCodeBegin()!=null) && (termInfo.getCodeBegin().length()>0) )
				ps.setString(n++,termInfo.getCodeBegin());
			//合同编号结束
			if ( (termInfo.getCodeEnd()!=null) && (termInfo.getCodeEnd().length()>0) )
				ps.setString(n++,termInfo.getCodeEnd());
			//指令号开始
			if ( (termInfo.getInstrNoBegin()!=null) && (termInfo.getInstrNoBegin().length()>0) )
				ps.setString(n++,termInfo.getInstrNoBegin());
			//指令号结束
			if ( (termInfo.getInstrNoEnd()!=null) && (termInfo.getInstrNoEnd().length()>0) )
				ps.setString(n++,termInfo.getInstrNoEnd());
			//处理人
			if ( termInfo.getInputUserID()>0 )
				ps.setLong(n++,termInfo.getInputUserID());
			//客户编号，只有统一客户的才能查看和修改
			
			if ( termInfo.getClientID()>0)
			{
				/*if ( termInfo.getTypeID() <=OBConstant.LoanInstrType.LOAN_ZGXEZCQ )
				{
					ps.setLong(n++,termInfo.getClientID());
					ps.setLong(n++,termInfo.getClientID());
				}
				else if (termInfo.getTypeID()==OBConstant.LoanInstrType.FREE )
				{
					ps.setLong(n++,termInfo.getClientID());
				}
				else
				{
					ps.setLong(n++,termInfo.getClientID());
				}*/
				ps.setLong(n++,termInfo.getClientID());
				ps.setLong(n++,termInfo.getClientID());
			}
							
			log.print( strSQL );
			rs=ps.executeQuery() ;
			while (rs.next())
			{
				OBInstrInfo instrInfo=new OBInstrInfo();
				instrInfo.setID(rs.getLong("ID"));
				instrInfo.setInstrTypeID( rs.getLong("nInstrTypeID"));
				instrInfo.setContractCode( rs.getString("sContractCode"));
				instrInfo.setInstrNo( rs.getString("sInstructionNo"));
				instrInfo.setStatusID( rs.getLong("nStatusID"));
				instrInfo.setInputUserID( rs.getLong("nInputUserID"));
				instrInfo.setInputDate(rs.getTimestamp("dtInputDate"));
				instrInfo.setInputUserName( rs.getString("sName") );
				instrInfo.setRecordCount( rs.getLong("ResultSize"));
				
				//test
				if (sumInfo!=null)
					sumInfo.setAllRecord( rs.getLong("ResultSize"));
				aList.add(instrInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		 }catch(IException ie){
			 cleanup(rs);
			 cleanup(ps);
			 cleanup(conn);
			 throw ie;			       
		 }catch(Exception e) {
			 cleanup(rs);
			 cleanup(ps);
			 cleanup(conn);
			 e.printStackTrace ();
			 throw e;
		 }finally{
			 cleanup(rs);
			 cleanup(ps);
			 cleanup(conn);
		 }
		
		return aList;
	}
	/*
	 *
	 * @author haoning
	 * @time 2003-11-20
	 * @param QueryDao
	 * function
	 */
   public String[] getExtendSQL(OBQueryTermInfo qInfo)
   throws Exception
   {
	   String[] strSQL={"","","",""};
	   StringBuffer sb=new StringBuffer();

	   //from
	   strSQL[0]=" loan_extendform a,loan_contractform b "
				+" ,client c,client d,loan_extenddetail e,userInfo u ";

	   //select
	   strSQL[1]=" a.id as ExtendID,b.ntypeid as TypeID "
				+" ,b.id as ContractID,b.scontractcode as ContractCode "
				+" ,c.sname as ClientName,d.sname as ConsignClientName "
				+" ,a.nserialno as ExtendNo "
				+" ,b.MEXAMINEAMOUNT as Amount "
				+" ,e.mextendamount as ExtendAmount "
				+" ,0 as ContractRate,0 as Rate "
				+" ,a.minterestadjust as ExtendRate "
				+" ,b.dtstartdate as DateFrom,b.dtenddate as DateTo "
				+" ,a.nstatusid as StatusID "
				+" ,u.sName as nextCheckUserName ";

	   //where
	   strSQL[2]=" a.nContractID=b.ID(+) and a.nStatusID > 0 "
				+" and b.nborrowclientid=c.id(+) "
				+" and b.nconsignclientid=d.id(+) "
							+" and e.nextendformid(+)=a.id "
				+" and u.id(+)=a.NNEXTCHECKUSERID";
	   sb.setLength(0);
	   //币种
	   if ( qInfo.getCurrencyID()>0 )
	   {
	   		sb.append(" and b.NCURRENCYID="+qInfo.getCurrencyID() );	
	   }
	   if(qInfo.getTypeID() != 99)
	   {
		   sb.append(" and b.nTypeID = "+qInfo.getTypeID()); 
	   }
	   if(qInfo.getStatusID() != 99)
	   {
		   sb.append(" and a.nStatusID = "+qInfo.getStatusID());
	   }
	   if( (qInfo.getCodeBegin() !=null)&&(qInfo.getCodeBegin().length()>0) )
	   {
		   sb.append(" and b.sContractCode >= '"+qInfo.getCodeBegin()+"'");
	   }
	   if( (qInfo.getCodeEnd() !=null)&&(qInfo.getCodeEnd().length()>0) )
	   {
		   sb.append(" and b.sContractCode <= '"+qInfo.getCodeEnd()+"'");
	   }
	   
	   if(qInfo.getBorrowClientID() > 0)
	   {
		   sb.append(" and c.ID = "+qInfo.getBorrowClientID());
	   }
	   if(qInfo.getConsignClientID() > 0)
	   {
		   sb.append(" and d.ID = "+qInfo.getConsignClientID());
	   }
	   if(qInfo.getAmountBegin() > 0)
	   {
		   sb.append(" and b.MEXAMINEAMOUNT >= "+qInfo.getAmountBegin());
	   }
	   if(qInfo.getAmountEnd() > 0)
	   {
		   sb.append(" and b.MEXAMINEAMOUNT <= "+qInfo.getAmountEnd());
	   }
	   if(qInfo.getAmountBegin2() > 0)
	   {
		   sb.append(" and e.mExtendAmount >= "+qInfo.getAmountBegin2());
	   }
	   if(qInfo.getAmountEnd2() > 0)
	   {
		   sb.append(" and e.mExtendAmount <= "+qInfo.getAmountEnd2());
	   }
	   if (qInfo.getLoanDateBegin() != null) //
	   {
		   sb.append( " and (b.DTSTARTDATE "
			   + " >= to_date('"+DataFormat.getDateString(qInfo.getLoanDateBegin())+"','yyyy-mm-dd') ) ");
	   }
	   if (qInfo.getLoanDateEnd() != null) //
	   {
		   sb.append( " and (b.DTENDDATE "
			   + " <= to_date('"+DataFormat.getDateString(qInfo.getLoanDateEnd())+"','yyyy-mm-dd') ) ");
	   }
	   /*
		if(qInfo.getClientID()>0)
		{ 
			strSQL[2] +=  " and (b.nBorrowClientID="+qInfo.getClientID()+" or b.nConsignClientID="+qInfo.getClientID()+") ";
		}
	   */
  	    if(qInfo.getClientID()>0)
		{ 
			strSQL[2] +=  " and b.nBorrowClientID="+qInfo.getClientID() ;
		}
	   
	   strSQL[2] += sb.toString();

	   //order by
	   strSQL[3]=" order by b.ntypeid,b.scontractcode ";

	   /**********处理查询条件*************/
	   return strSQL;
   }

   public QuerySumInfo queryExtendSum(OBQueryTermInfo qInfo) throws Exception
   {
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   QuerySumInfo sumInfo=new QuerySumInfo();
	   String[] sql=getExtendSQL(qInfo);
	   String strSQL="";

	   try
	   {
		   strSQL="select count(*) ,sum(b.MEXAMINEAMOUNT) as TotalAmount from "
		   + sql[0] +" where "+sql[2];
		   log.print(strSQL);

		   con=Database.getConnection() ;
		   ps=con.prepareStatement( strSQL );
		   rs=ps.executeQuery() ;
		   if ( rs.next() )
		   {
			   sumInfo.setAllRecord( rs.getLong(1));
			   sumInfo.setTotalApplyAmount( rs.getDouble("TotalAmount"));
		   }
		   cleanup(rs);
		   cleanup(ps);
		   cleanup(con);
	   }
	   catch (SQLException e)
	   {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   cleanup(rs);
		   cleanup(ps);
		   cleanup(con);
	   }
	   catch (Exception e)
	   {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   cleanup(rs);
		   cleanup(ps);
		   cleanup(con);

	   }
	   finally
	   {
		 cleanup(rs);
		 cleanup(ps);
		 cleanup(con);
	   }
	   return sumInfo;
   }
   public Collection queryExtend(OBQueryTermInfo qInfo) throws Exception
   {
		ArrayList aList=new ArrayList();
		String strSQL="";
		Connection conn = null;
		PagedStatement ps = null;
		ResultSet rs = null;
	
		try {
			conn=Database.getConnection() ;
			String sql[]=getExtendSQL(qInfo);
			strSQL="select "+sql[1]+" from "+sql[0]+" where " +sql[2] + sql[3];
			log.print(strSQL);
			ps=new PagedStatement(conn,strSQL, qInfo.getTxtPageNo(),Constant.PageControl.CODE_PAGELINECOUNT);
			rs=ps.executeQuery() ;
			while (rs.next())
			{
				QuestExtendInfo info=new QuestExtendInfo();
				info.setTypeID( rs.getLong("typeID"));
				info.setExtendID( rs.getLong("extendID"));
				info.setContractCode(rs.getString("contractCode"));
				info.setClientName(rs.getString("clientName"));
				info.setConsignClientName(rs.getString("ConsignClientName"));
				info.setExtendNo(rs.getLong("extendNo"));
				info.setAmount(rs.getDouble("amount"));
				info.setExtendAmount(rs.getDouble("extendAmount"));
				info.setContractID(rs.getLong("ContractID"));
				info.setExtendRate(rs.getDouble("extendRate"));
				info.setDateFrom(rs.getTimestamp("dateFrom"));
				info.setDateTo(rs.getTimestamp( "dateTo"));
				info.setStatusID( rs.getLong("statusID"));
				aList.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		  }
		  catch (Exception e)
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			  throw e;
		  }
		  finally
		  {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		  }
   		
   		return aList;
   }
   /*
	*
	* @author haoning
	* @time 2003-11-18
	* @param QuestContractPlanInfo
	* function
	* return String strSQL
	*/
	public String[] getContractPlanSQL(OBQueryTermInfo qInfo)
	throws Exception
	{
		String[] strSQL={"","","",""};


		//from 
		strSQL[0]=" loan_loancontractplan a,loan_contractform b,UserInfo c,LOAN_PLANMODIFYFORM d,UserInfo c1 ";

		//select
		strSQL[1]=" a.id as PlanID,a.nPlanVersion as PlanVersion "
				 +" ,c.sName as Modifier,c1.sName as nextCheckUserName "
				 +" ,b.sContractCode as ContractCode "
				 +" ,a.dtInputDate as InputDate ";

		//where
		strSQL[2]=" a.nContractID=b.ID(+) "
				 +" and b.nInputUserID=c.ID(+) "
				 +" and d.NNEXTCHECKUSERID = c1.ID(+) "
				 +" and d.NCONTRACTID(+) = b.ID "
				 +" and a.nStatusID = "+Constant.RecordStatus.VALID;
		if(qInfo.getContractID() > 0)
		{
			strSQL[2] +=  " and a.nContractID= " + qInfo.getContractID();
		}
		
		/*
		if(qInfo.getClientID()>0)
		{
			strSQL[2] +=  " and (b.nBorrowClientID="+qInfo.getClientID()+" or b.nConsignClientID="+qInfo.getClientID()+") ";
		}
		*/

		//order by
		strSQL[3]=" order by a.nPlanVersion ";

		/**********处理查询条件*************/
		return strSQL;
	}
	public Collection queryContractPlan(OBQueryTermInfo qInfo) throws Exception
	{
		Vector v=new Vector();
		String strSQL="";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con=Database.getConnection() ;
			String[] sql=getContractPlanSQL(qInfo);
			strSQL=" select "+sql[1]+" from "+sql[0]+" where "+sql[2]+sql[3];
			System.out.println(strSQL);
			ps=con.prepareStatement( strSQL );
			rs=ps.executeQuery();
			while ( rs.next() )
			{
				QuestContractPlanInfo info=new QuestContractPlanInfo();
				info.setPlanID( rs.getLong("planID"));
				info.setPlanVersion( rs.getLong("planVersion"));
				info.setContractCode( rs.getString("contractCode"));
				info.setModifier( rs.getString("modifier"));
				info.setInputDate( rs.getTimestamp( "InputDate"));

				v.add(info);
			}
		  cleanup(rs);
		  cleanup(ps);
		  cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		  cleanup(rs);
		  cleanup(ps);
		  cleanup(con);
			throw e;
		}
		finally
		{
		  cleanup(rs);
		  cleanup(ps);
		  cleanup(con);
		}


		return v;
	}	
	
	/**
	 * 根据ContractPayPlanVersion中的ID查找计划信息
	 * <p>    
	 * <b>&nbsp;</b>
	 * <ol><b>查找贷款信息</b>
	 * <ul>
	 * <li>不关心版本号
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param     long     ContractPayPlanVersionID    ContractPayPlanVersion中的ID
	 *
	 * @param     long     lPageLineCount         每页页行数条件
	 * @param     long     lPageNo                第几页条件
	 * @param     long     lOrderParam            排序条件，根据此参数决定结果集排序条件
	 * @param     long     lDesc                  升序或降序
	 *
	 * @param     long     lUserID               用户标示，选择使用，可以用于核对是否与loanInfo中的inputuser是同一人
	 * @param     long     lOfficeID             办事处标示，选择使用，可以用于核对是否与loanInfo中的办事处相同
	 *
	 * @return    Collection     
	 *
	 * @exception Exception
	**/
	public Collection findPlanByVer(
		long ContractPayPlanVersionID,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc,
		long lUserID,
		long lOfficeID)
		throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList alist = new ArrayList();
		String strCondition = "";
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long lContractID = 0;
		ContractDao contractdao = new ContractDao();

		try
		{
			conn = Database.getConnection();

			//查找银行利率
			double dInterestRate = 0;
			String sInterestRate = ""; // for Libor  因为Libor没有值

			//查询合同标识，为取得利率用
			sb.append("select nContractID from loan_loanContractPlan  where ID = ? ");
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, ContractPayPlanVersionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lContractID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			sb.append("select count(*)");
			strCondition =
				" from  loan_loanContractPlanDetail aa, "
					+ "(select sum(MAMOUNT) as TOTAL1 from  loan_loanContractPlanDetail where nContractPlanID = ? and npaytypeid = 1) bb, "
					+ "(select sum(MAMOUNT) as TOTAL2 from  loan_loanContractPlanDetail where nContractPlanID = ? and npaytypeid = 2) cc "
					+ "where aa.nContractPlanID = ? ";
			switch ((int) lOrderParam)
			{
				case 1 :
					strCondition += " order by DTPLANDATE";
					break;
				case 2 :
					strCondition += " order by NPAYTYPEID";
					break;
				case 3 :
					strCondition += " order by MAMOUNT";
					break;
				case 4 :
					strCondition += " order by STYPE";
					break;
				case 5 :
					strCondition += " order by mEXECUTEINTERESTRATE";
					break;
				case 6 :
					strCondition += " order by DTMODIFYDATE";
					break;
				default :
					strCondition += " order by DTPLANDATE";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strCondition += " desc";
			}
			sb.append(strCondition);
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, ContractPayPlanVersionID);
			ps.setLong(2, ContractPayPlanVersionID);
			ps.setLong(3, ContractPayPlanVersionID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("RecordCount: " + lRecordCount);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			lPageCount = lRecordCount / lPageLineCount;

			if (lRecordCount % lPageLineCount != 0)
			{
				lPageCount++;
			}

			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			sb.append("select * from ( select a.*, rownum num from (");
			sb.append("select aa.*,bb.TOTAL1,cc.TOTAL2 " + strCondition);
			sb.append(" )  a) where num between  " + lRowNumStart + " and " + lRowNumEnd);
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, ContractPayPlanVersionID);
			ps.setLong(2, ContractPayPlanVersionID);
			ps.setLong(3, ContractPayPlanVersionID);
			Log.print("mamamamamcontractpayplanversionid=" + ContractPayPlanVersionID);
			rs = ps.executeQuery();

			while ( rs!=null & rs.next())
			{
				Log.print("A...");
				RepayPlanInfo rp_info = new RepayPlanInfo();
				rp_info.lID = rs.getLong("ID");
				rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
				rp_info.fExecuteInterestRate = contractdao.getLatelyRate(0, lContractID, rp_info.tsPlanDate).getLateRate();
				rp_info.sExecuteInterestRate = getPlanRate(lContractID, rp_info.tsPlanDate);
				rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
				rp_info.dAmount = rs.getDouble("MAMOUNT");
				rp_info.sType = rs.getString("STYPE");
				rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
				rp_info.lCount = lPageCount;
				rp_info.lVersionNo = getPlanVersion(ContractPayPlanVersionID);
				rp_info.dPayCounter = rs.getDouble("TOTAL1");
				rp_info.dRePayCounter = rs.getDouble("TOTAL2");
				rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
				rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
				//rp_info.lisOverDue = rs.getLong("NISOVERDUE");
				rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");
				alist.add(rp_info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return (alist.size() > 0 ? alist : null);
	}
	// 查是否有版本，参数传contractpayplanversion.ID
	private long getPlanVersion(long lID) throws Exception
	{
		long lResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try
		{
			conn = Database.getConnection();
			sb.append("select max(bb.NPLANVERSION) from loan_loancontractplan aa,loan_loancontractplan bb where bb.NCONTRACTID = aa.NCONTRACTID and aa.ID = ?");
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			sb.setLength(0);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return lResult;
	}
	private String getPlanRate(long lContractID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		String sInterestRate = ""; // for Libor  因为Libor没有值
		//查找银行利率
		long lRateType = LOANConstant.InterestRateType.BANK; // bank rate
		double dInterestRate = 0;

		try
		{
			conn = Database.getConnection();

			sb.append("select NINTERESTTYPEID from loan_contractform where ID = " + lContractID);
			System.out.println(sb.toString());
			System.out.println("1-------------");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lRateType = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			if (lRateType == LOANConstant.InterestRateType.LIBOR)
			{ // is Libor Rate
				/*
							sb.append("select cc.SNAME,aa.FLIBORADJUST from LOANINFO aa,LIBORINTERESTRATE cc where aa.ID = ? and cc.ID = aa.NLIBORRATEID");
							System.out.println(sb.toString());
							ps = conn.prepareStatement(sb.toString());
							ps.setLong(1,lLoanID);
							rs = ps.executeQuery();
							if (rs.next()) {
								sInterestRate = rs.getString(1) + " ";
								if (rs.getDouble(2)>=0)
									sInterestRate = sInterestRate + "+" + rs.getDouble(2);
								else
									sInterestRate = sInterestRate + rs.getDouble(2);
							}
							System.out.println ("ejb---------------libor1.sInterestRate" + sInterestRate);
							rs.close(); rs = null;
							ps.close(); ps = null;
							sb.setLength(0);
				*/
			}
			else
			{ // is Bank Rate
				ContractDao contractDao = new ContractDao();
				dInterestRate = contractDao.getLatelyRate(0, lContractID, tsDate).getLateRate();
				sInterestRate = DataFormat.formatRate(dInterestRate);
			}

			// -----------------------------------------------------------end
		}
		catch (Exception e)
		{
			Log.print(e.toString());
			throw e;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		return sInterestRate;

	}

	public Collection findPlanByContract(long lContractID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList alist = new ArrayList();
		String strCondition = "";
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long lhasMAX = -1;

		try
		{
			conn = Database.getConnection();

			sb.append("select max(NPLANVERSION) from loan_loancontractplan where nContractID = ?");
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lhasMAX = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);
			System.out.println("------------------lhasMAX:" + lhasMAX);

			if (lhasMAX > 0)
			{
				strCondition =
					" from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = ? and aa.nContractPlanID = bb.ID and bb.NPLANVERSION in(select max(NPLANVERSION) from loan_loancontractplan where nContractID = "
						+ lContractID
						+ ")";
			}
			else
			{
				strCondition = " from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = ? and aa.nContractPlanID = bb.ID";
			}

			switch ((int) lOrderParam)
			{
				case 1 :
					strCondition += " order by DTPLANDATE";
					break;
				case 2 :
					strCondition += " order by NPAYTYPEID";
					break;
				case 3 :
					strCondition += " order by MAMOUNT";
					break;
				case 4 :
					strCondition += " order by STYPE";
					break;
				case 5 :
					//strCondition +=  " order by FEXECUTEINTERESTRATE"; 
					break;
				case 6 :
					strCondition += " order by DTMODIFYDATE";
					break;
				default :
					strCondition += " order by DTPLANDATE";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strCondition += " desc";
			}
			sb.append("select count(*)" + strCondition);
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			//ps.setLong(2,lLoanID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("RecordCount: " + lRecordCount);
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			lPageCount = lRecordCount / lPageLineCount;

			if (lRecordCount % lPageLineCount != 0)
			{
				lPageCount++;
			}

			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			sb.append("select * from ( select a.*, rownum num from (");
			sb.append("select aa.* " + strCondition);
			sb.append(" )  a) where num between  " + lRowNumStart + " and " + lRowNumEnd);
			Log.print(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lContractID);
			//ps.setLong(2,lLoanID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next())
			{
				RepayPlanInfo rp_info = new RepayPlanInfo();
				rp_info.lID = rs.getLong("ID");
				rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
				rp_info.sExecuteInterestRate = getPlanRate(lContractID, rp_info.tsPlanDate);
				System.out.println("he..........." + rp_info.sExecuteInterestRate);
				rp_info.fExecuteInterestRate = contractDao.getLatelyRate(0, lContractID, rp_info.tsPlanDate).getLateRate();
				rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
				rp_info.dAmount = rs.getDouble("MAMOUNT");
				rp_info.sType = rs.getString("STYPE");
				rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
				rp_info.lContractPayPlanVersionID = rs.getLong("nContractPlanID");
				rp_info.lCount = lPageCount;
				rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
				rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
				rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");

				alist.add(rp_info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return (alist.size() > 0 ? alist : null);
	}
	/**
	* 贷款免还申请查询
	* <p>
	* <b>&nbsp;</b>
	* <ol><b>贷款免还申请查询</b>
	* <ul>
	* <li>操作数据库表FreeApply　
	* <li>显示的贷款申请类型，只有委托流动资金贷款和委托固定资金贷款
	* <LI>逐笔查出满足条件的免还申请纪录
	* <li>和满足条件的总贷款金额、总免还金额
	* </ul>
	* </ol>
	* @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	*
	* @param     long        lTypeID                   贷款申请类型
	* @param     long        lContractIDFrom           起始合同标示
	* @param     long        lContractIDTo             截至合同标示
	* @param     long        lClientID                 借款单位
	* @param     long        lConsignClientID          委托单位
	* @param     int         nToCapital                是否转资本金
	*
	* @param     long       lCurrencyID              币种标示
	* @param     long       lOfficeID                办事处标示
	*
	* @param  long          lPageLineCount         每页页行数条件
	* @param  long          lPageNo                第几页条件
	* @param  long          lOrderParam            排序条件，根据此参数决定结果集排序条件
	* @param  long          lDesc                  升序或降序
	*
	* @return Collection（Collection of QuestFreeInfo）
	* @throws Exception
	**/
	public Collection questFree(OBQueryTermInfo qInfo) throws Exception
	{
		Vector vReturn = new Vector(); //当前页结果集
		Connection con = null;
		PagedStatement ps = null;
		ResultSet rs = null;
		int nIndex = 0;
		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		double dAllLoanAmount = 0; //总申请贷款金额
		double dfreeAmount = -1; //总免还金额
		long lRecordCount = -1; //总记录数
		long lPageCount = -1; //总页数
		long lRowNumStart = -1; //开始记录
		long lRowNumEnd = -1; //结束记录

		try
		{
			con = Database.getConnection();
			strSQL_Count = " select count(*),sum(b.MEXAMINEAMOUNT) "
						 + " ,sum(a.mfreeAmount) ";
			strSQL_Table =
				"    from loan_freeForm a "
					+ "      ,loan_contractForm b,CLIENT c,CLIENT c2 "
					+"       ,loan_payForm d "
					+ "      ,UserInfo u1,UserInfo u2 "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and b.NBORROWCLIENTID=c.ID(+) "
					+ "   and b.NConsignCLIENTID=c2.ID(+) "
					+"    and a.nPayFormID=d.ID(+) "
					+ "   and a.nInputUserID=u1.ID(+) "
					+ "   and a.nNextCheckUserID=u2.ID(+) "
					+ "";
			strSQL_Option = " ";
			if (qInfo.getCurrencyID() > 0)
			{
				strSQL_Option += " and b.NCURRENCYID = " + qInfo.getCurrencyID();
			}
			if (qInfo.getTypeID() > -1)
			{
				strSQL_Option += " and b.nTypeID = " + qInfo.getTypeID();
			}
			if ( (qInfo.getCodeBegin() !=null )&&(qInfo.getCodeBegin().length()>0) )
			{
				strSQL_Option += " and b.sContractCode >= '" + qInfo.getCodeBegin() +"'";
			}
			if ( (qInfo.getCodeEnd()!=null )&&(qInfo.getCodeEnd().length()>0) )
			{
				strSQL_Option += " and b.sContractCode <= '" + qInfo.getCodeEnd() +"'";
			}

/*			if (qInfo.getBorrowClientID()  > -1)
			{
				strSQL_Option += " and b.NBORROWCLIENTID = " + qInfo.getBorrowClientID();
			}
*/

			if(qInfo.getClientID()>0)
			{
				strSQL_Option +=  " and (b.nBorrowClientID="+qInfo.getClientID()+" or b.nConsignClientID="+qInfo.getClientID()+") ";
			}

/*			if(qInfo.getClientID()>0)
			{
				strSQL_Option +=  " and b.nConsignClientID="+qInfo.getClientID();
			}
*/			
			strSQL_Option += " and a.nStatusID > " + Constant.RecordStatus.INVALID;
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option;

			Log.print(strSQL);	
			ps = new PagedStatement(con,strSQL,1,Constant.PageControl.CODE_PAGELINECOUNT);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1); //得到总记录数
				dAllLoanAmount = rs.getDouble(2); //总申请贷款金额
				dfreeAmount = rs.getDouble(3); //总免还金额
			}
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
			if (lRecordCount > 0)
			{
				strSQL_Order += " order by b.sContractCode,d.sCode ";
				///////////////////////////////////////////////////////////
					strSQL_Select = " select a.ID as FreeApplyID " //ID
				+ " ,a.sCode as FreeCode "
				+ " ,a.nContractID as ContractID " //合同ID
				+ " ,b.sContractCode as ContractCode " //合同编号
				+ " ,b.nTypeID as LoanType "//贷款类型
				+ " ,d.ID as LoanPayID "
				+ " ,d.sCode as LoanPayCode "
				+ " ,b.mLoanAmount " //贷款金额
				+ " ,b.MEXAMINEAMOUNT"//批准金额
				+ " ,b.nIntervalNum "//贷款期限
				+ " ,b.dtStartDate,b.dtEndDate  "//贷款日期
				+ " ,a.mFreeAmount " //
				+ " ,a.dtFreeDate "
				+ " ,a.mInterest "
				+ " ,a.sAccountNo "
				+ " ,a.sFreeReason "
				+ " ,a.nStatusID "
				+ " ,a.nInputUserID "
				+ " ,u1.sName as InputUserName "
				+ " ,a.nNextCheckUserID "
				+ " ,u2.sName as CheckUserName "
				+ " ,c.sName as ClientName " //借款单位
				+ " ,c2.sName as ConsignName "
				+"  ";
				strSQL = strSQL_Select
					+ strSQL_Table
					+ strSQL_Option
					+ strSQL_Order;

				ps = new PagedStatement(con,strSQL,qInfo.getTxtPageNo(),Constant.PageControl.CODE_PAGELINECOUNT);
				rs = ps.executeQuery();
				while(rs != null && rs.next())
				{
					FreeApplyInfo Info = new FreeApplyInfo();
					//log4j.info("----------1------------");
					Info.setID(rs.getLong("FreeApplyID"));
					Info.setFreeCode(rs.getString("FreeCode"));
					Info.setContractID(rs.getLong("ContractID"));
					Info.setContractCode(rs.getString("ContractCode"));
					Info.setLoanTypeID(rs.getLong("LoanType"));
					Info.setLoanPayID(rs.getLong("LoanPayID"));
					Info.setLoanPayCode(rs.getString("LoanPayCode"));
					Info.setClientName(rs.getString("ClientName"));
					Info.setConsignClientName(rs.getString("ConsignName"));
					//Info.setConsignClientAccount(rs.getString("sAccountNo"));
					//Info.setAmount(rs.getDouble("mLoanAmount"));
					Info.setAmount(rs.getDouble("MEXAMINEAMOUNT"));
					//Info.setBalance(10000.00);
					//log4j.info("----------2------------");
					Info.setStartDate(rs.getTimestamp("dtStartDate"));
					Info.setEndDate(rs.getTimestamp("dtEndDate"));
					//Info.setIntervalNum(rs.getLong("nIntervalNum"));
					//log4j.info("----------3------------");
					Info.setFreeAmount(rs.getDouble("mFreeAmount"));
					Info.setFreeDate(rs.getTimestamp("dtFreeDate"));
					Info.setStatusID(rs.getLong("nStatusID"));
					Info.setInputUserName(rs.getString("InputUserName"));
					Info.setCheckUserName(rs.getString("CheckUserName"));
					Info.setAllLoanAmount(dAllLoanAmount);
					Info.setAllFreeAmount(dfreeAmount);
					Info.setRecordCount(lRecordCount);
					Info.setPageCount(lPageCount);
					//log4j.info("----------5------------");
					//-------------------------------------------------//
					vReturn.addElement(Info);
				}
			}
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
		catch (SQLException e)
		{
			throw new IException("Gen_E001");
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		finally
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
		//////////////////////////
		return vReturn.size() <= 0 ? null : vReturn;
	}
	public FreeApplyInfo findFreeApplyByID(long ID) throws Exception
	{
		FreeApplyInfo Info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = ""; //主SQL语句
		try
		{
			con = Database.getConnection();
			strSQL =
				" select a.*  "
					+ "  ,b.sName as InputUserName "
					+ "  ,c.sName as CheckUserName "
					+ " from loan_freeForm a, UserInfo b, UserInfo c "
					+ "  where  a.nInputUserID=b.ID(+) "
					+ "     and a.nNextCheckUserID = c.ID(+)   "
					+ "     and a.ID = "
					+ ID
					+ " ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				Info = new FreeApplyInfo();
				Info.setID(rs.getLong("ID"));
				Info.setFreeCode(rs.getString("sCode"));
				Info.setContractID(rs.getLong("nContractID"));
				Info.setLoanPayID(rs.getLong("nPayFormID"));
				Info.setFreeAmount(rs.getDouble("mFreeAmount"));
				Info.setFreeDate(rs.getTimestamp("dtFreeDate"));
				Info.setFreeRate(rs.getDouble("mInterest"));
				Info.setFreeReason(rs.getString("sFreeReason"));
				Info.setStatusID(rs.getLong("nStatusID"));
				Info.setInputUserID(rs.getLong("nInputUserID"));
				Info.setInputUserName(rs.getString("InputUserName"));
				Info.setInputDate(rs.getTimestamp("dtInputDate"));
				Info.setCheckUserID(rs.getLong("nNextCheckUserID"));
				Info.setCheckUserName(rs.getString("CheckUserName"));
				Info.setConsignClientAccount(rs.getString("sAccountNo"));
			}
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
		catch (SQLException e)
		{
			throw new IException("Gen_E001");
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		///////////////////////////////////////////
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
			catch (SQLException e)
			{
				throw new IException("Gen_E001");
			}
		}
		return Info;
	}

	private String[] getContractSQL(OBQueryTermInfo qInfo)
	{
		String[] sql=new String[4];
		StringBuffer buf=new StringBuffer();

		//select
		sql[0]=" c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,"
			+" c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,"
			+" c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
			+" c.nBankInterestID as bankInterestID ,c.mAdjustRate as adjustRate ,"
			+" c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
			+" c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
			+" d.sApplyCode as applyCode";

		//from
		sql[1]=" loan_contractForm c,client c1,client c2,userInfo u,userInfo u2,loan_loanForm d ";

		//where
		sql[2]=" c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID";
		if ( qInfo.getQueryPurpose()==OBQueryTermInfo.TX )
			sql[2]+=" and c.nTypeID=" +LOANConstant.LoanType.TX ;
		else
			sql[2]+=" and c.nTypeID<>" +LOANConstant.LoanType.TX ;
		
		//币种
		if ( qInfo.getCurrencyID()>0 )
			buf.append(" and c.NCURRENCYID="+qInfo.getCurrencyID() );	
		//贷款种类
		if ( qInfo.getTypeID() !=99 )
			buf.append(" and c.nTypeID="+qInfo.getTypeID() );

		//合同编号开始
		if ( qInfo.getCodeBegin()!=null && qInfo.getCodeBegin().length()>0 )
			buf.append(" and c.sContractCode>='"+qInfo.getCodeBegin()+"'" );

		//合同编号结束
		if ( qInfo.getCodeEnd()!=null && qInfo.getCodeEnd().length()>0 )
			buf.append(" and c.sContractCode<='"+qInfo.getCodeEnd()+"'" );

		//合同状态
		if (qInfo.getStatusID()!=99 )
			buf.append(" and c.nStatusID="+qInfo.getStatusID() );
/*
		//借款单位
		if ( qInfo.getBorrowClientID()>0 )
			buf.append(" and c.nBorrowClientID="+qInfo.getBorrowClientID() );
*/
		//借款单位账号
		if ( (qInfo.getBorrowClientAccount()!=null)&&(qInfo.getBorrowClientAccount().length()>0) )
			buf.append(" and c1.sAccount='"+qInfo.getBorrowClientAccount()+"'");

		//客户分类
		if ( qInfo.getLoanClientTypeID()>0 )
			buf.append(" and c1.nLoanClientTypeID="+qInfo.getLoanClientTypeID() );

		//主管单位
		if ( qInfo.getParentCorpID()>0 )
			buf.append(" and c1.nParentCorpID1="+qInfo.getParentCorpID() );
/*
		//委托单位
		if ( qInfo.getConsignClientID()>0 )
			buf.append(" and c.nConsignClientID="+qInfo.getConsignClientID() );
*/
		//委托单位账号
		if ( (qInfo.getConsignClientAccount()!=null)&&(qInfo.getConsignClientAccount().length()>0 ))
			buf.append(" and c2.sAccount='"+qInfo.getConsignClientAccount()+"'");

		//贷款金额开始
		if ( qInfo.getAmountBegin()>0 )
			buf.append(" and c.mExamineAmount>="+DataFormat.formatAmount(qInfo.getAmountBegin()) );

		//贷款金额结束
		if ( qInfo.getAmountEnd()>0 )
			buf.append(" and c.mExamineAmount<="+DataFormat.formatAmount(qInfo.getAmountEnd()) );

		//贷款日期开始
		if ( qInfo.getLoanDateBegin()!=null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getLoanDateBegin())+"','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getLoanDateEnd()!=null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getLoanDateEnd())+"','yyyy-mm-dd') ");

		//贷款期限
		if (qInfo.getIntervalNum() >0 )
			buf.append(" and c.nIntervalNum="+qInfo.getIntervalNum() );

		//保证方式
		if ( qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT )
			buf.append(" and c.nIsCredit=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.ASSURE )
			buf.append(" and c.IsAssure=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.IMPAWN )
			buf.append(" and c.IsImpawn=1");
		if ( qInfo.getAssureTypeID()==LOANConstant.AssureType.PLEDGE )
			buf.append(" and c.IsPledge=1");

		//信用等级
		if ( qInfo.getCreditLevelID() >0)
			buf.append(" and c1.NCREDITLEVELID="+qInfo.getCreditLevelID() );

		//是否股东
		if (qInfo.getIsPartner() >0)
			buf.append(" and c1.NISPARTNER="+qInfo.getIsPartner() );

		//是否技改贷款
		if (qInfo.getIsTechnical() >0)
			buf.append(" and c.NISTECHNICAL="+qInfo.getIsTechnical() );

		//是否循环贷款
		if (qInfo.getIsCircle() >0)
			buf.append(" and c.NISCIRCLE="+qInfo.getIsCircle());

		//贷款风险状态
		if (qInfo.getRiskLevelID() >0)
			buf.append(" and c.nRiskLevel="+qInfo.getRiskLevelID());

		//按地区分类
		if ( qInfo.getTypeID1()>0 )
			buf.append(" and c.nTypeID1="+qInfo.getTypeID1() );

		//按行业分类1
		if ( qInfo.getTypeID2()>0 )
			buf.append(" and c.nTypeID2="+qInfo.getTypeID2() );

		//按行业分类2
		if ( qInfo.getTypeID3()>0 )
			buf.append(" and c.nTypeID3="+qInfo.getTypeID3() );

		//管理人
		if (qInfo.getInputUserID()>0)
			buf.append(" and c.nInputUserID="+qInfo.getInputUserID());

		if(qInfo.getClientID()>0)
		{
			buf.append(" and (c.nBorrowClientID="+qInfo.getClientID()+" or c.nConsignClientID="+qInfo.getClientID()+") ");
		}
			

		sql[2]=sql[2]+buf.toString();

		//order
		String strDesc = qInfo.getLDesc() == 1 ? " desc " : "";
		StringBuffer oBuf=new StringBuffer();
		switch ((int) qInfo.getLOrderParam())
		{
			case 1 :
				oBuf.append(" \n order by c.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c1.sName"+strDesc);
				break;
			case 4:
				oBuf.append(" \n order by c2.sName"+strDesc);
				break;
			case 5:
				oBuf.append(" \n order by c.mLoanAmount"+strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by c.mInterestRate"+strDesc );
				break;
			case 7 :
				oBuf.append(" \n order by c.nIntervalNum"+strDesc );
				break;
			case 8 :
				oBuf.append(" \n order by c.dtStartDate"+strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by c.nStatusID"+strDesc);
				break;
			case 10:
				oBuf.append(" \n order by u.sName"+strDesc );
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode"+strDesc);
				break;
			case 12 :
				oBuf.append(" \n order by c.mExamineAmount"+strDesc);
				break;
			case 13 :
				oBuf.append(" \n order by c.mCheckAmount"+strDesc);
				break;
			case 14 :
				oBuf.append(" \n order by c.mDiscountRate"+strDesc);
				break;
			case 15:
				oBuf.append(" \n order by c.dtEndDate"+strDesc);
				break;
					case 20:
							oBuf.append(" \n order by u2.sName"+strDesc );
							break;
					default :
				oBuf.append(" \n order by c.ID"+strDesc);
				break;
		}
		sql[3]=oBuf.toString();
		log.print("get QuerySQL:\n"+sql[0]+"\n"+sql[1]+"\n"+sql[2]+"\n"+sql[3]+"\n");

		return sql;
	}
	
	public Collection queryContract(OBQueryTermInfo termInfo) throws Exception
	{
		ArrayList aList=new ArrayList();
		String strSQL="";
		Connection conn = null;
		PagedStatement ps = null;
		ResultSet rs = null;
		ContractDao conDao=new ContractDao();
	
		try {
			conn=Database.getConnection() ;
			String sql[]=getContractSQL(termInfo);
			strSQL="select "+sql[0]+" from "+sql[1]+" where " +sql[2] + sql[3];
			log.print(strSQL);
			ps=new PagedStatement(conn,strSQL, termInfo.getTxtPageNo(),Constant.PageControl.CODE_PAGELINECOUNT);
			rs=ps.executeQuery() ;
			while (rs.next())
			{
				ContractInfo info = new ContractInfo();
				info.setContractID( rs.getLong("contractID"));
				info.setLoanTypeID( rs.getLong("loanTypeID"));
				info.setContractCode( rs.getString("contractCode"));
				info.setBorrowClientName(rs.getString("borrowClientName"));
				info.setClientName( rs.getString("ClientName"));
				info.setLoanAmount( rs.getDouble("loanAmount"));
				info.setBankInterestID(rs.getLong("bankInterestID"));
				info.setInterestRate(rs.getDouble("interestRate"));
				info.setLoanStart(rs.getTimestamp("loanStart"));
				info.setLoanEnd(rs.getTimestamp("loanEnd"));
				info.setAdjustRate(rs.getDouble("adjustRate"));
				info.setIntervalNum(rs.getLong("intervalNum"));
				info.setStatusID(rs.getLong("statusID"));
				info.setInputUserName( rs.getString("inputUserName"));
				info.setDiscountRate( rs.getDouble("discountRate"));
				info.setCheckAmount(rs.getDouble("checkAmount"));
				info.setExamineAmount( rs.getDouble("examineAmount"));
				info.setApplyCode(rs.getString("applyCode"));
				aList.add(info);
			}	
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
		} finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);				
		}
		return aList;
			
	}
	
	public QuerySumInfo queryContractSum(OBQueryTermInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo=new QuerySumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String[] sql=getContractSQL(qInfo);
		String strSQL="";

		try
		{
			sql[0]="count(*) as recordCount,sum(c.mExamineAmount) as sumLoanAmount";
			strSQL="select " + sql[0] + " from " + sql[1] +" where "+sql[2];
			log.print(strSQL);

			con=Database.getConnection() ;
			ps=con.prepareStatement( strSQL );
			rs=ps.executeQuery() ;
			if ( rs.next() )
			{
				sumInfo.setAllRecord( rs.getLong("recordCount"));
				sumInfo.setTotalApplyAmount ( rs.getDouble("sumLoanAmount"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		finally
		{
		  cleanup(rs);
		  cleanup(ps);
		  cleanup(con);
		}
		return sumInfo;
	}

    /*
     * 由合同ID找到其所有的 用款、还款计划
     * @author haoning
     * @time 2004-3-9
     * @param lContractID
     * function
     */
    public Collection findPlanDetailByContractID(long lContractID) throws Exception
    {
        long lResult = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        ArrayList alist = new ArrayList();
        String strCondition = "";
        long lRecordCount = -1;
        long lPageCount = -1;
        long lRowNumStart = -1;
        long lRowNumEnd = -1;
        long lhasMAX = -1;

        try
        {
            conn = Database.getConnection();

            sb.append("select max(NPLANVERSION) from loan_loancontractplan where nContractID = ?");
            Log.print(sb.toString());
            ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, lContractID);
            rs = ps.executeQuery();
            if (rs.next())
            {
                lhasMAX = rs.getLong(1);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            sb.setLength(0);
            System.out.println("------------------lhasMAX:" + lhasMAX);

            if (lhasMAX > 0)
            {
                strCondition =
                    " from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = ? and aa.nContractPlanID = bb.ID and bb.NPLANVERSION in(select max(NPLANVERSION) from loan_loancontractplan where nContractID = "
                        + lContractID
                        + ")";
            }
            else
            {
                strCondition = " from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = ? and aa.nContractPlanID = bb.ID";
            }

            sb.append("select count(*)" + strCondition);
            Log.print(sb.toString());
            ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, lContractID);
            rs = ps.executeQuery();

            while (rs.next())
            {
                lRecordCount = rs.getLong(1);
                Log.print("RecordCount: " + lRecordCount);
            }

            rs.close();
            rs = null;
            ps.close();
            ps = null;
            sb.setLength(0);


            sb.append("select * from ( select a.*, rownum num from (");
            sb.append("select aa.* " + strCondition);
            sb.append(" )  a) where 1=1  ");
            Log.print(sb.toString());
            ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, lContractID);
            rs = ps.executeQuery();
            ContractDao contractDao = new ContractDao();
            while (rs.next())
            {
                RepayPlanInfo rp_info = new RepayPlanInfo();
                rp_info.lID = rs.getLong("ID");
                rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
                rp_info.sExecuteInterestRate = getPlanRate(lContractID, rp_info.tsPlanDate);
                System.out.println("he..........." + rp_info.sExecuteInterestRate);
                rp_info.fExecuteInterestRate = contractDao.getLatelyRate(0, lContractID, rp_info.tsPlanDate).getLateRate();
                rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
                rp_info.dAmount = rs.getDouble("MAMOUNT");
                rp_info.sType = rs.getString("STYPE");
                rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
                rp_info.lContractPayPlanVersionID = rs.getLong("nContractPlanID");
                rp_info.lCount = lPageCount;
                rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
                rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
                rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");

                alist.add(rp_info);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        return (alist.size() > 0 ? alist : null);
    }


	public static void main(String[] args)
	{
		ArrayList aList=null;
		OBQueryTermInfo qInfo=new OBQueryTermInfo();
		try
		{
			OBQueryDao dao=new OBQueryDao();
			aList=(ArrayList)dao.queryLoan( qInfo );
			System.out.println(aList.size());
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}
}

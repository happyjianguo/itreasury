package com.iss.itreasury.settlement.reportlossorfreeze.dao;

import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.QuerySubjectInfo;
import com.iss.itreasury.util.DataFormat;

public class QSubjectDetailDAO {
	
	//主要是获得SQl
	public String getSubjectDetailsSQL(QuerySubjectInfo info) {
		 StringBuffer		m_sbSelect					= null;

		 StringBuffer		m_sbFrom					= null;

		 StringBuffer		m_sbWhere					= null;

		 StringBuffer		m_sbOrderBy					= null;
		// TODO 自动生成方法存根
		m_sbSelect = new StringBuffer();
		// select
		m_sbSelect.append("* from (");
		m_sbSelect.append(" select distinct  sy.id,sy.ssubjectcode,sy.stransno,sy.ntransactiontypeid,sy.ntransdirection,sy.mamount,sn.execute Dtexecute ,sn.intereststart Dtintereststart,");
		m_sbSelect.append(" sy.sabstract,sy.ninputuserid,sy.ncheckuserid,''as OPPACCOUNTNO,'' as OPPACCOUNTNAME,u1.sname  InputUserName ,u2.sname CheckUserName,");
		m_sbSelect.append(" decode( sa.sname,'',sh.SENTERPRISENAME,null,sh.SENTERPRISENAME,sa.sname) AccountName ,");
		m_sbSelect.append(" decode( sa.saccountno,'',sh.SBANKACCOUNTCODE,null,sh.SBANKACCOUNTCODE, sa.saccountno) AccountNo,");
		m_sbSelect.append(" st.sextclientname,st.sextaccountno, st.nreceiveaccountid,st.npayaccountid,st.nbankid ,");
		m_sbSelect.append(" sh.SENTERPRISENAME, sh.SBANKACCOUNTCODE");
		// from
		m_sbFrom = new StringBuffer();

		m_sbFrom.append("  sett_glentry sy,sett_transaccountdetail sl ,userinfo u1 ,userinfo u2 , sett_account sa, sett_transcurrentdeposit st,\n");
		m_sbFrom.append("  sett_vtransaction  sn, sett_branch   sh ");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  sy.nstatusid = 3   and sy.stransno = sl.stransno(+) \n");
		m_sbWhere.append("  and sy.mamount = sl.mamount(+) \n");
		m_sbWhere.append("  and sy.ntransdirection=sl.ntransdirection(+) \n");
		m_sbWhere.append("  and sn.inputuserid=u1.id(+)  and sn.checkuserid=u2.id(+)  and sl.ntransaccountid=sa.id(+)\n");
		m_sbWhere.append("  and sl.stransno=st.stransno(+) \n");
		m_sbWhere.append("  and sy.stransno=sn.transno(+) and sn.BankID=sh.id(+)  \n");
		m_sbWhere.append("  and sy.ntransactiontypeid not in (1,2) \n");//不是银行付款或者银行收款

		
		m_sbWhere.append("   UNION select  distinct sy.id ,sy.ssubjectcode,sy.stransno,sy.ntransactiontypeid,sy.ntransdirection,sy.mamount,sy.dtexecute,sy.dtintereststart,");
		m_sbWhere.append("   sy.sabstract,sy.ninputuserid,sy.ncheckuserid,sl.OPPACCOUNTNO,sl.OPPACCOUNTNAME,u1.sname  InputUserName ,u2.sname CheckUserName,");
		m_sbWhere.append("   sa.sname  AccountName ,sa.saccountno  AccountNo, st.SEXTCLIENTNAME,st.SEXTACCOUNTNO, st.NRECEIVEACCOUNTID,st.NPAYACCOUNTID,st.NBANKID,");
		m_sbWhere.append("   sh.senterprisename,sh.sbankaccountcode");
		m_sbWhere.append("	 from  sett_glentry sy,sett_transaccountdetail sl ,userinfo u1 ,userinfo u2 , sett_account sa, sett_transcurrentdeposit st,\n");
		m_sbWhere.append("   sett_branch   sh");
		m_sbWhere.append("  where sy.nstatusid = 3 and sy.stransno = sl.stransno(+) \n");
		//m_sbWhere.append("  and sy.mamount = sl.mamount(+) \n");
		//m_sbWhere.append("  and sy.ntransdirection=sl.ntransdirection(+) \n");
		m_sbWhere.append("  and sy.ninputuserid=u1.id(+)  and sy.ncheckuserid=u2.id(+)  and sl.ntransaccountid=sa.id(+)\n");
		m_sbWhere.append("  and sl.stransno=st.stransno(+) \n");
		m_sbWhere.append("  and st.NBANKID=sh.id(+)\n");
		m_sbWhere.append("  and sy.ntransactiontypeid  in (1,2) \n");//银行收款或者银行付款

		m_sbWhere.append(") ");
		m_sbWhere.append(" where 1=1 ");
		if (info.getStartDate() != null)
		{
			m_sbWhere.append("     and   dtexecute >= to_date('" + DataFormat.getDateString(info.getStartDate())
					+ "','yyyy-mm-dd')   \n");
		}
		if (info.getEndDate() != null)
		{
			m_sbWhere.append("        and dtexecute <= to_date('" + DataFormat.getDateString(info.getEndDate())
					+ "','yyyy-mm-dd')   \n");
		}	
		if(info.getDtintereststartFrom()!=null)
		{
			m_sbWhere.append("     and   Dtintereststart >= to_date('" + DataFormat.getDateString(info.getDtintereststartFrom())
					+ "','yyyy-mm-dd')   \n");			
		}
		
		if(info.getDtintereststartTo()!=null)
		{
			m_sbWhere.append("     and   Dtintereststart <= to_date('" + DataFormat.getDateString(info.getDtintereststartTo())
					+ "','yyyy-mm-dd')   \n");			
		}		
		if(!info.getStrTransNoFrom().equals(""))
		{
			m_sbWhere.append(" and stransno >= '"+info.getStrTransNoFrom()+"'");
		}
		
		if(!info.getStrTransNoTo().equals(""))
		{
			m_sbWhere.append(" and stransno <= '"+info.getStrTransNoTo()+"'");
		}
		
		if(!info.getStrTransactionType().equals(""))
		{
			m_sbWhere.append(" and ntransactiontypeid in ("+info.getStrTransactionType()+")");
		}
		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("order by  stransno,ntransdirection,mamount\n " );
		
		String sql="select"+m_sbSelect+" from "+m_sbFrom+"where"+m_sbWhere+m_sbOrderBy;
		
		System.out.println("打印出sql------------"+"select"+m_sbSelect+" from "+m_sbFrom+"where"+m_sbWhere+m_sbOrderBy);

		return sql;
		
	}

}

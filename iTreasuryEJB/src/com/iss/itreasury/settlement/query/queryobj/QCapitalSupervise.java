/*
 * Created on 2004-4-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.query.paraminfo.QueryCapitalSuperviseWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryCapitalSuperviseResultInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;


/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QCapitalSupervise extends BaseQueryObject{

	public Collection queryCapitalSupervise(QueryCapitalSuperviseWhereInfo qcswi) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select distinct SuperAccount.ID SuperAccountID,SuperAccount.sAccountNo SuperAccountNo,SuperSubAccount.mBalance SuperAccountBalance, \n");
			m_sbSQL.append(" DebitInterestAccount.ID DebitInterestAccountID,DebitInterestAccount.sAccountNo DebitInterestAccountNo,DebitInterestSubAccount.mBalance DebitInterestAccountBalance \n");
			m_sbSQL.append(" from sett_CapitalSuperviseSetting,sett_Account SuperAccount,sett_Account DebitInterestAccount, \n");
			m_sbSQL.append(" sett_SubAccount SuperSubAccount,sett_SubAccount DebitInterestSubAccount \n");
			m_sbSQL.append(" where sett_CapitalSuperviseSetting.SuperAccountID = SuperAccount.ID \n");
			m_sbSQL.append(" and sett_CapitalSuperviseSetting.DebitInterestAccountID = DebitInterestAccount.ID \n");
			m_sbSQL.append(" \n ");
			m_sbSQL.append(" and SuperSubAccount.nAccountID = SuperAccount.ID and DebitInterestSubAccount.nAccountID = DebitInterestAccount.ID \n");
			m_sbSQL.append(" \n ");
			m_sbSQL.append(" and sett_CapitalSuperviseSetting.OfficeID = "+qcswi.getOfficeID()+" and sett_CapitalSuperviseSetting.CurrencyID = "+qcswi.getCurrencyID()+" \n");
			m_sbSQL.append(" and sett_CapitalSuperviseSetting.StatusID >0 \n");
			if(qcswi.getStartCurrentAccountNo() != null && qcswi.getStartCurrentAccountNo().length() >0
				&& qcswi.getEndCurrentAccountNo() != null && qcswi.getEndCurrentAccountNo().length() >0)
			{
				m_sbSQL.append(" and SuperAccount.sAccountNo between '"+qcswi.getStartCurrentAccountNo()+"' and '"+qcswi.getEndCurrentAccountNo()+"' \n");
			}
			if(qcswi.getStartDebitAccountNo() != null && qcswi.getStartDebitAccountNo().length() >0
				&& qcswi.getEndDebitAccountNo() != null && qcswi.getEndDebitAccountNo().length() >0)
			{
				m_sbSQL.append(" and DebitInterestAccount.sAccountNo between '"+qcswi.getStartDebitAccountNo()+"' and '"+qcswi.getEndDebitAccountNo()+"' \n");
			}

			conn = getConnection();
			log.print("资金集中管理资金处理查询 SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				QueryCapitalSuperviseResultInfo qcsri = new QueryCapitalSuperviseResultInfo();
				
				qcsri.setCurrentAccountID(rs.getLong("SuperAccountID"));
				qcsri.setCurrentAccountNo(rs.getString("SuperAccountNo"));
				qcsri.setCurrentAccountBalance(rs.getDouble("SuperAccountBalance"));
				
				qcsri.setDebitAccountID(rs.getLong("DebitInterestAccountID"));
				qcsri.setDebitAccountNo(rs.getString("DebitInterestAccountNo"));
				qcsri.setDebitAccountBalance(rs.getDouble("DebitInterestAccountBalance"));
				
				v.add(qcsri);
			}
				
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		    cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	
	public boolean doCapitalSupervise(QueryCapitalSuperviseWhereInfo qcswi) throws Exception
	{
		boolean rtnFlag = false;
		try 
		{
			Vector resultVec = new Vector();
			resultVec = (Vector)queryCapitalSupervise(qcswi);
			if(resultVec == null) //如果没有进行设置 则正常继续关机操作
				return true;
			Sett_SubAccountDAO dao=new Sett_SubAccountDAO();
			Vector currentVec = new Vector();	
				 for(int i = 0; i < resultVec.size(); i++)
				 {								
					QueryCapitalSuperviseResultInfo resultInfo = new QueryCapitalSuperviseResultInfo();
					resultInfo=(QueryCapitalSuperviseResultInfo)resultVec.elementAt(i);	
					if(dao.findByAccountID(resultInfo.getCurrentAccountID()).getSubAccountCurrenctInfo().getDailyUncheckAmount()>0)
					{
						log.info("*---------------子账户有未复核金额尚未处理--------------");
						rtnFlag= false;
						return rtnFlag;
					}
									
					TransCurrentDepositInfo depositInfo = new TransCurrentDepositInfo(); 
					if(resultInfo.getCurrentAccountBalance()<0)
					{//活期存款余额<0
						return false;
					}
					else if(resultInfo.getCurrentAccountBalance() != 0)
					{
						if(resultInfo.getDebitAccountBalance()<0)//活期存款余额>0 但是 负息账户余额<0
						{
							return false;
						}					
					}
				 }
				 
				rtnFlag= true;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;			
		}
		return rtnFlag;	
	}
	
}

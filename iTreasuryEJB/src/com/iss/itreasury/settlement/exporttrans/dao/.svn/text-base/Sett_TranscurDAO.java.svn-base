/*
 * Created on 2008-2-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.exporttrans.dataentity.ExportTransInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.ObTransInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.ObTransQueryInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.SettTransInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.SettTransQueryInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

/**
 * @author lenovo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sett_TranscurDAO {
    //  根据条件查询表sett_transcurrentdeposit和sett_exportTrans
    public Collection findByCondition(SettTransQueryInfo info) throws Exception {
        Collection rc = new ArrayList();
        
		Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.*,nvl(b.exporttimes,0) exporttimes  "+"\n");
		sqlBuffer.append(" from sett_transcurrentdeposit a,(select * from sett_exporttrans where 1=1\n");
        //判断导出记录所属模块（1结算 6网银）
		if(info.getMoudleID() > 0){
			sqlBuffer.append(" and moduleid="+info.getMoudleID());
		}
		sqlBuffer.append(") b ");
		sqlBuffer.append(" where 1=1 and  a.id=b.stransno(+) ");
		//过滤掉结算接收的网银指令
		sqlBuffer.append(" and a.id not in(select sett.id from ob_financeinstr obf,sett_transcurrentdeposit sett where obf.cpf_stransno=sett.stransno) ");
        //交易类型(银行付款)
		if(info.getLTransType() > 0){
		   sqlBuffer.append(" and a.ntransactiontypeid="+info.getLTransType()); 
		}
		//判断付款方的开户行
		if(info.getLBankID() > 0){
			sqlBuffer.append(" and a.nbankid ="+info.getLBankID());
		}
		//查询状态为已复核结算付款指令
		sqlBuffer.append(" and a.NSTATUSID in("+SETTConstant.TransactionStatus.CHECK+") ");
		
		//判断起息日
		if(info.getTsInterestStartDate() != null){
			sqlBuffer.append(" and a.dtintereststart <= to_date('"+DataFormat.formatDate(info.getTsInterestStartDate())+"','YYYY-MM-DD')");
		}
		if(info.getTsInterestEndDate() != null){
			sqlBuffer.append(" and a.dtintereststart >= to_date('"+DataFormat.formatDate(info.getTsInterestEndDate())+"','YYYY-MM-DD')");
		}
		//判断执行日
		if(info.getTsExecuteDate() != null){
			sqlBuffer.append(" and  a.dtexecute =to_date('"+DataFormat.formatDate(info.getTsExecuteDate())+"','YYYY-MM-DD')") ;
		}
		//判断记录导出的状态（0全部，7已导出，8未导出）
		if(info.getLStatus() == 7){
			sqlBuffer.append(" and b.exporttimes > 0 ");
		}else if(info.getLStatus() == 8){
			sqlBuffer.append(" and b.exporttimes is null ");
		}
		sqlBuffer.append(" order by a.stransno");
		System.out.println(sqlBuffer.toString());
		SettTransInfo  settTransInfo =  null;
		TransCurrentDepositInfo transCurrentDepositInfo = null;
		ExportTransInfo exportTransInfo = null;
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			while(rs.next()){
				settTransInfo = new SettTransInfo();
				transCurrentDepositInfo = new TransCurrentDepositInfo();
				exportTransInfo = new ExportTransInfo();
				
				settTransInfo.setId(rs.getLong("id"));
				
				transCurrentDepositInfo.setOfficeID(rs.getLong("NOFFICEID"));
				transCurrentDepositInfo.setCurrencyID(rs.getLong("NCURRENCYID"));
				transCurrentDepositInfo.setTransNo(rs.getString("STRANSNO"));
				transCurrentDepositInfo.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				transCurrentDepositInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				transCurrentDepositInfo.setExtAccountNo(rs.getString("SEXTACCOUNTNO"));
				transCurrentDepositInfo.setPayAccountID(rs.getLong("NPAYACCOUNTID"));
				transCurrentDepositInfo.setAmount(rs.getDouble("MAMOUNT"));
				transCurrentDepositInfo.setStatusID(rs.getLong("NSTATUSID"));
				transCurrentDepositInfo.setAbstractStr(rs.getString("SABSTRACT"));
				transCurrentDepositInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				transCurrentDepositInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				transCurrentDepositInfo.setBankID(info.getLBankID());
				settTransInfo.setTransCurrentDepositInfo(transCurrentDepositInfo);
				
				exportTransInfo.setExporttimes(rs.getLong("EXPORTTIMES"));
				settTransInfo.setExportTransInfo(exportTransInfo);
				rc.add(settTransInfo);
			}
		} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}

        return rc;
    }

    //  根据id查询表sett_transcurrentdeposit
    public TransCurrentDepositInfo findByID(long id) throws Exception {
        TransCurrentDepositInfo info = null;
        
        Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from sett_transcurrentdeposit ");
		sqlBuffer.append(" where ID = ? ");
		System.out.println(sqlBuffer.toString());
		
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			pstat.setLong(1, id);
			rs = pstat.executeQuery();
			while(rs.next()){
				info = new TransCurrentDepositInfo();
				
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setCurrencyID(rs.getLong("NCURRENCYID"));
				info.setTransNo(rs.getString("STRANSNO"));
			    info.setPayAccountID(rs.getLong("NPAYACCOUNTID"));
			    info.setExtAccountNo(rs.getString("SEXTACCOUNTNO"));
			    info.setExtClientName(rs.getString("SEXTCLIENTNAME"));
			    info.setRemitInBank(rs.getString("SREMITINBANK"));
			    info.setRemitInProvince(rs.getString("SREMITINPROVINCE"));
			    info.setRemitInCity(rs.getString("SREMITINCITY"));
			    info.setAmount(rs.getDouble("MAMOUNT"));
			    info.setAbstractStr(rs.getString("SABSTRACT"));
			    info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
			    info.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				
			}
		} finally{
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
		
        return info;
    }
}
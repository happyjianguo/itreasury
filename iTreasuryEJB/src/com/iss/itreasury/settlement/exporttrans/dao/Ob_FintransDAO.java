/*
 * Created on 2008-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.dao;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.conversion.db.SourceConn;
import com.iss.itreasury.settlement.exporttrans.dataentity.ExportTransInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.ObTransInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.ObTransQueryInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * @author lenovo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Ob_FintransDAO {
    //根据条件查询表ob_financeinstr和sett_exportTrans
    public Collection findByCondition(ObTransQueryInfo info) throws Exception {
        Collection rc = new ArrayList();
        
		Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.*,nvl(b.exporttimes,0) exporttimes  "+"\n");
		sqlBuffer.append(" from ob_financeinstr a,(select * from sett_exporttrans where 1=1\n");
        //判断导出记录所属模块（1结算 6网银）
		if(info.getMoudleID() > 0){
			sqlBuffer.append(" and moduleid="+info.getMoudleID());
		}
		sqlBuffer.append(") b ");
		sqlBuffer.append(" where 1=1 and  a.id=b.stransno(+) ");
		//查询状态为已复核和已签认的网银指令
		sqlBuffer.append(" and a.nstatus in("+OBConstant.SettInstrStatus.CHECK+","+OBConstant.SettInstrStatus.SIGN+") ");
		//汇款方式(银行汇款)
		sqlBuffer.append(" and a.nremittype="+OBConstant.SettRemitType.BANKPAY);
		//判断执行日
		if(info.getExecuteDate() != null){
			sqlBuffer.append(" and  to_date(to_char(a.dtexecute,'yyyy-mm-dd'),'yyyy-mm-dd') =to_date('"+DataFormat.formatDate(info.getExecuteDate())+"','YYYY-MM-DD')") ;
		}
		//判断客户
		if(info.getClientIDFrom() > 0){
			sqlBuffer.append(" and a.nclientid>="+info.getClientIDFrom());
		}
		if(info.getClientIDTo() > 0){
			sqlBuffer.append(" and a.nclientid<="+info.getClientIDTo());
		}
		//判断记录导出的状态（0全部，7已导出，8未导出）
		if(info.getExportStatus() == 7){
			sqlBuffer.append(" and b.exporttimes > 0 ");
		}else if(info.getExportStatus() == 8){
			sqlBuffer.append(" and b.exporttimes is null ");
		}
		System.out.println(sqlBuffer.toString());
		ObTransInfo  obTransInfo =  null;
		OBFinanceInfo obFinanceInfo = null;
		ExportTransInfo exportTransInfo = null;
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			while(rs.next()){
				obTransInfo = new ObTransInfo();
				obFinanceInfo = new OBFinanceInfo();
				exportTransInfo = new ExportTransInfo();
				
				obFinanceInfo.setID(rs.getLong("ID"));
				obFinanceInfo.setTransType(rs.getLong("NTRANSTYPE"));
				obFinanceInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				obFinanceInfo.setPayeeAcctID(rs.getLong("NPAYEEACCTID"));      //收款方账户ID
				obFinanceInfo.setPayerAcctID(rs.getLong("NPAYERACCTID"));      //付款方账户ID
				obFinanceInfo.setAmount(rs.getDouble("MAMOUNT"));
				obFinanceInfo.setStatus(rs.getLong("NSTATUS"));
				obFinanceInfo.setNote(rs.getString("SNOTE"));
				obTransInfo.setObFinanceInfo(obFinanceInfo);
				
				exportTransInfo.setExporttimes(rs.getLong("EXPORTTIMES"));
				obTransInfo.setExportTransInfo(exportTransInfo);
				
				obTransInfo.setClientID(rs.getLong("NCLIENTID"));
				obTransInfo.setCurrencyID(rs.getLong("NCURRENCYID"));
				rc.add(obTransInfo);
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

    //  根据id查询表ob_financeinstr
    public OBFinanceInfo findByID(long id) throws Exception {
    	OBFinanceInfo obFinanceInfo =  null;
        
        Connection conn = Database.getConnection();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from ob_financeinstr ");
		sqlBuffer.append(" where ID = ? ");
		System.out.println(sqlBuffer.toString());
		
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			pstat.setLong(1, id);
			rs = pstat.executeQuery();
			while(rs.next()){
				obFinanceInfo = new OBFinanceInfo();
				
				obFinanceInfo.setID(rs.getLong("ID"));
				obFinanceInfo.setTransType(rs.getLong("NTRANSTYPE"));
				obFinanceInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				obFinanceInfo.setPayeeAcctID(rs.getLong("NPAYEEACCTID"));      //收款方账户ID
				obFinanceInfo.setPayerAcctID(rs.getLong("NPAYERACCTID"));      //付款方账户ID
				obFinanceInfo.setAmount(rs.getDouble("MAMOUNT"));
				obFinanceInfo.setStatus(rs.getLong("NSTATUS"));
				obFinanceInfo.setNote(rs.getString("SNOTE"));
				obFinanceInfo.setInputUserID(rs.getLong("NCONFIRMUSERID"));   
				obFinanceInfo.setInputDate(rs.getTimestamp("DTCONFIRM"));
				obFinanceInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				obFinanceInfo.setCheckDate(rs.getTimestamp("DTCHECK"));
				obFinanceInfo.setSignUserID(rs.getLong("NSIGNUSERID"));
				obFinanceInfo.setSignSignDate(rs.getTimestamp("DTSIGN"));
				obFinanceInfo.setIsSameBank(rs.getLong("ISSAMEBANK"));
				obFinanceInfo.setIsDiffLocal(rs.getLong("ISDIFFLOCAL"));
				
				
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
		
        return obFinanceInfo;
    }
}
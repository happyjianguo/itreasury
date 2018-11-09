/*
 * Created on 2008-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.bizlogic;

import com.iss.itreasury.util.IException;
import java.util.Collection;
import com.iss.itreasury.settlement.exporttrans.dataentity.ObTransQueryInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.SettTransQueryInfo;
import com.iss.itreasury.settlement.exporttrans.dao.*;
import com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.exporttrans.dataentity.ExportTransInfo;
/**
 * @author lenovo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ExportTransBiz {

    //导出状态
    public static final long unexport = 0; // 未导出

    public static final long exported = 1; // 已导出


    //查询符合条件的网银指令。
    public Collection findObTransByCondition(ObTransQueryInfo info) throws IException,Exception {
        Collection c = null;
      
            Ob_FintransDAO dao = new Ob_FintransDAO();
            c = dao.findByCondition(info);
       
        return c;
    }

    //  查询符合条件的结算交易。
    public Collection findSettTransByCondition(SettTransQueryInfo info) throws IException,Exception {
        Collection c = null;
             
        Sett_TranscurDAO dao = new Sett_TranscurDAO();
        c = dao.findByCondition(info);
        
        return c;
    }
    
    //根据ID查询网银指令信息
    public OBFinanceInfo findOBTransByID(long id) throws IException {
    	OBFinanceInfo info = null;
        try {
        	Ob_FintransDAO dao = new Ob_FintransDAO();
        	info = dao.findByID(id);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return info;
    }
    //根据ID查询结算交易信息
    public TransCurrentDepositInfo findSettTransByID(long id) throws IException {
    	TransCurrentDepositInfo info = null;
        try {
        	Sett_TranscurDAO dao = new Sett_TranscurDAO();
        	info = dao.findByID(id);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return info;
    }

    //将导出的交易记录到表sett_exportTrans里
    //如果交易没有在表sett_exportTrans里存储过则新增记录
    //如果交易在表sett_exportTrans里已存在则将字段EXPORTTIMES加1
    public void saveExport(String transno,long modleid) throws Exception,IException{
    	Sett_ExportTransDAO dao = new Sett_ExportTransDAO();
    	ExportTransInfo info = new ExportTransInfo();
    	ExportTransInfo tempinfo = null;
    	info.setTransno(transno);
    	info.setModuleID(modleid);
    	tempinfo = dao.findByCondition(info);
    	if(tempinfo==null || tempinfo.getExporttimes()<0){
    		tempinfo = new ExportTransInfo();
    		tempinfo.setModuleID(info.getModuleID());    //模块ID
    		tempinfo.setTransno(transno);
    		tempinfo.setExporttimes(1);
    		
    		dao.add(tempinfo);
    		
    	}else{
    		long newtimes = tempinfo.getExporttimes()+1;
    		info.setExporttimes(newtimes);
    		dao.update(info);
    	}
    	
    }
    
    //将选中的交易按深发展银行格式导出成excel
    public void exportSettToSFZ(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按兴业银行格式导出成excel
    public void exportSettToXY(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按交通银行格式导出成excel
    public void exportSettToJT(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按工商银行格式导出成excel
    public void exportSettToGS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按招商银行格式导出成excel
    public void exportSettToZS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
    
//  将选中的交易按深发展银行格式导出成excel
    public void exportObToSFZ(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按兴业银行格式导出成excel
    public void exportObToXY(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按交通银行格式导出成excel
    public void exportObToJT(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按工商银行格式导出成excel
    public void exportObToGS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //将选中的交易按招商银行格式导出成excel
    public void exportObToZS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
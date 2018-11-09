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

    //����״̬
    public static final long unexport = 0; // δ����

    public static final long exported = 1; // �ѵ���


    //��ѯ��������������ָ�
    public Collection findObTransByCondition(ObTransQueryInfo info) throws IException,Exception {
        Collection c = null;
      
            Ob_FintransDAO dao = new Ob_FintransDAO();
            c = dao.findByCondition(info);
       
        return c;
    }

    //  ��ѯ���������Ľ��㽻�ס�
    public Collection findSettTransByCondition(SettTransQueryInfo info) throws IException,Exception {
        Collection c = null;
             
        Sett_TranscurDAO dao = new Sett_TranscurDAO();
        c = dao.findByCondition(info);
        
        return c;
    }
    
    //����ID��ѯ����ָ����Ϣ
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
    //����ID��ѯ���㽻����Ϣ
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

    //�������Ľ��׼�¼����sett_exportTrans��
    //�������û���ڱ�sett_exportTrans��洢����������¼
    //��������ڱ�sett_exportTrans���Ѵ������ֶ�EXPORTTIMES��1
    public void saveExport(String transno,long modleid) throws Exception,IException{
    	Sett_ExportTransDAO dao = new Sett_ExportTransDAO();
    	ExportTransInfo info = new ExportTransInfo();
    	ExportTransInfo tempinfo = null;
    	info.setTransno(transno);
    	info.setModuleID(modleid);
    	tempinfo = dao.findByCondition(info);
    	if(tempinfo==null || tempinfo.getExporttimes()<0){
    		tempinfo = new ExportTransInfo();
    		tempinfo.setModuleID(info.getModuleID());    //ģ��ID
    		tempinfo.setTransno(transno);
    		tempinfo.setExporttimes(1);
    		
    		dao.add(tempinfo);
    		
    	}else{
    		long newtimes = tempinfo.getExporttimes()+1;
    		info.setExporttimes(newtimes);
    		dao.update(info);
    	}
    	
    }
    
    //��ѡ�еĽ��װ��չ���и�ʽ������excel
    public void exportSettToSFZ(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ���ҵ���и�ʽ������excel
    public void exportSettToXY(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ���ͨ���и�ʽ������excel
    public void exportSettToJT(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ��������и�ʽ������excel
    public void exportSettToGS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ��������и�ʽ������excel
    public void exportSettToZS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
    
//  ��ѡ�еĽ��װ��չ���и�ʽ������excel
    public void exportObToSFZ(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ���ҵ���и�ʽ������excel
    public void exportObToXY(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ���ͨ���и�ʽ������excel
    public void exportObToJT(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ��������и�ʽ������excel
    public void exportObToGS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //��ѡ�еĽ��װ��������и�ʽ������excel
    public void exportObToZS(Collection c) throws IException {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
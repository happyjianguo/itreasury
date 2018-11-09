/*
 * Created on 2008-2-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.exporttrans.dataentity;


/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExportTransInfo {
    private String transno = "";//结算交易号或网银指令号
    private long moduleID = -1; //模块ID
    private long exporttimes = 0; //导出次数
    /**
     * @return Returns the exporttimes.
     */
    public long getExporttimes() {
        return exporttimes;
    }
    /**
     * @param exporttimes The exporttimes to set.
     */
    public void setExporttimes(long exporttimes) {
        this.exporttimes = exporttimes;
    }
    /**
     * @return Returns the moduleID.
     */
    public long getModuleID() {
        return moduleID;
    }
    /**
     * @param moduleID The moduleID to set.
     */
    public void setModuleID(long moduleID) {
        this.moduleID = moduleID;
    }
    /**
     * @return Returns the transno.
     */
    public String getTransno() {
        return transno;
    }
    /**
     * @param transno The transno to set.
     */
    public void setTransno(String transno) {
        this.transno = transno;
    }
}

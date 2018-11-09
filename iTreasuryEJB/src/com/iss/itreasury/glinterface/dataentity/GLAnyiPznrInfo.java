/*
 * Created on 2005-11-21
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 安易凭证内容表信息
 * @author liuqing
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLAnyiPznrInfo implements Serializable
{
	private String gsdm = "";         	//公司代码
	private String kjqj = "";          	//会计期间
	private String pzly = "";       	//凭证来源
	private String pzh = "";         	//凭证号
	private long flh = 1;     			//分录号
	private String zy = "";         	//摘要
	private String kmdm = "";          	//科目代码
	private String wbdm = "";       	//币种代码
	private double hl = 0;         		//汇率
	private String jdbz = "";     		//借贷标志 
	private double wbje = 0;       		//外币金额
	private double je = 0;	         	//本位币金额
	private String Spz = "";            //原始凭证号 
	private String wldrq = "";   	   	//往来业务收付款到期日
	private double sl = 1;       		//数量
	private double dj = 1;       		//单价
	private String bmdm = "";   	   	//部门代码
	private String wldm = "";   	   	//往来单位代码
	private String xmdm = "";   	   	//项目代码
	private String fplx = "";     			//发票类型 
	private String fprq = "";   	   	//发票日期
	
	
	
    /**
     * @return Returns the bmdm.
     */
    public String getBmdm() {
        return bmdm;
    }
    /**
     * @param bmdm The bmdm to set.
     */
    public void setBmdm(String bmdm) {
        this.bmdm = bmdm;
    }
    /**
     * @return Returns the dj.
     */
    public double getDj() {
        return dj;
    }
    /**
     * @param dj The dj to set.
     */
    public void setDj(double dj) {
        this.dj = dj;
    }
    /**
     * @return Returns the flh.
     */
    public long getFlh() {
        return flh;
    }
    /**
     * @param flh The flh to set.
     */
    public void setFlh(long flh) {
        this.flh = flh;
    }
    /**
     * @return Returns the fplx.
     */
    public String getFplx() {
        return fplx;
    }
    /**
     * @param fplx The fplx to set.
     */
    public void setFplx(String fplx) {
        this.fplx = fplx;
    }
    /**
     * @return Returns the fprq.
     */
    public String getFprq() {
        return fprq;
    }
    /**
     * @param fprq The fprq to set.
     */
    public void setFprq(String fprq) {
        this.fprq = fprq;
    }
    /**
     * @return Returns the gsdm.
     */
    public String getGsdm() {
        return gsdm;
    }
    /**
     * @param gsdm The gsdm to set.
     */
    public void setGsdm(String gsdm) {
        this.gsdm = gsdm;
    }
    /**
     * @return Returns the hl.
     */
    public double getHl() {
        return hl;
    }
    /**
     * @param hl The hl to set.
     */
    public void setHl(double hl) {
        this.hl = hl;
    }
    /**
     * @return Returns the jdbz.
     */
    public String getJdbz() {
        return jdbz;
    }
    /**
     * @param jdbz The jdbz to set.
     */
    public void setJdbz(String jdbz) {
        this.jdbz = jdbz;
    }
    /**
     * @return Returns the je.
     */
    public double getJe() {
        return je;
    }
    /**
     * @param je The je to set.
     */
    public void setJe(double je) {
        this.je = je;
    }
    /**
     * @return Returns the kjqj.
     */
    public String getKjqj() {
        return kjqj;
    }
    /**
     * @param kjqj The kjqj to set.
     */
    public void setKjqj(String kjqj) {
        this.kjqj = kjqj;
    }
    /**
     * @return Returns the kmdm.
     */
    public String getKmdm() {
        return kmdm;
    }
    /**
     * @param kmdm The kmdm to set.
     */
    public void setKmdm(String kmdm) {
        this.kmdm = kmdm;
    }
    /**
     * @return Returns the pzh.
     */
    public String getPzh() {
        return pzh;
    }
    /**
     * @param pzh The pzh to set.
     */
    public void setPzh(String pzh) {
        this.pzh = pzh;
    }
    /**
     * @return Returns the pzly.
     */
    public String getPzly() {
        return pzly;
    }
    /**
     * @param pzly The pzly to set.
     */
    public void setPzly(String pzly) {
        this.pzly = pzly;
    }
    /**
     * @return Returns the sl.
     */
    public double getSl() {
        return sl;
    }
    /**
     * @param sl The sl to set.
     */
    public void setSl(double sl) {
        this.sl = sl;
    }
    /**
     * @return Returns the spz.
     */
    public String getSpz() {
        return Spz;
    }
    /**
     * @param spz The spz to set.
     */
    public void setSpz(String spz) {
        Spz = spz;
    }
    /**
     * @return Returns the wbdm.
     */
    public String getWbdm() {
        return wbdm;
    }
    /**
     * @param wbdm The wbdm to set.
     */
    public void setWbdm(String wbdm) {
        this.wbdm = wbdm;
    }
    /**
     * @return Returns the wbje.
     */
    public double getWbje() {
        return wbje;
    }
    /**
     * @param wbje The wbje to set.
     */
    public void setWbje(double wbje) {
        this.wbje = wbje;
    }
    /**
     * @return Returns the wldm.
     */
    public String getWldm() {
        return wldm;
    }
    /**
     * @param wldm The wldm to set.
     */
    public void setWldm(String wldm) {
        this.wldm = wldm;
    }
    /**
     * @return Returns the wldrq.
     */
    public String getWldrq() {
        return wldrq;
    }
    /**
     * @param wldrq The wldrq to set.
     */
    public void setWldrq(String wldrq) {
        this.wldrq = wldrq;
    }
    /**
     * @return Returns the xmdm.
     */
    public String getXmdm() {
        return xmdm;
    }
    /**
     * @param xmdm The xmdm to set.
     */
    public void setXmdm(String xmdm) {
        this.xmdm = xmdm;
    }
    /**
     * @return Returns the zy.
     */
    public String getZy() {
        return zy;
    }
    /**
     * @param zy The zy to set.
     */
    public void setZy(String zy) {
        this.zy = zy;
    }
    
}
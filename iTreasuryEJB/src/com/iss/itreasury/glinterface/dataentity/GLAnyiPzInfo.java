/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * 安易凭证目录表信息
 * @author liuqing
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLAnyiPzInfo implements Serializable
{
	private String gsdm = "";   		//公司代码
	private String kjqj = "";   		//会计期间
	private String pzly = "";			//凭证来源
	private String pzh = "";			//凭证号
	private String pzrq = "";			//凭证日期
	private String fjzs = "";			//附件张数
	private int srID = -1;        		//输入人ID 
	private String sr = "";  			//输入人名称
	private int shID = -1;				//审核人ID
	private String sh = "";  			//审核人名称
	private String jsr = "";			//经手人
	private int jzrID = -1;				//记账人ID
	private String jzr = "";			//记账人名称
	private String srrq = "";			//制单日期	
	private String shrq = "";    		//审核日期
	private String jzrq = "";    		//记账日期
	private String pzhzkmdy = "";    	//凭证汇总科目打印标志
	private String pzhzbz = "";    		//自动回转凭证标志
	private int zt = 1;    				//状态 0=作废；1=未审核；2=已审核；3=已记账
	private String pzzy = "";    		//凭证摘要
	private double pzje = 0;			//凭证金额 
	private String CN = "";          	//出纳
	private String BZ = "";          	//标志
	private String kjzg = "";          	//会计主管
	private String Flag = "";          	//引入标志
	
	
	private ArrayList list = new ArrayList();  //分录数组
	
      
    /**
     * @return Returns the bZ.
     */
    public String getBZ() {
        return BZ;
    }
    /**
     * @param bz The bZ to set.
     */
    public void setBZ(String bz) {
        BZ = bz;
    }
    /**
     * @return Returns the cN.
     */
    public String getCN() {
        return CN;
    }
    /**
     * @param cn The cN to set.
     */
    public void setCN(String cn) {
        CN = cn;
    }
    /**
     * @return Returns the fjzs.
     */
    public String getFjzs() {
        return fjzs;
    }
    /**
     * @param fjzs The fjzs to set.
     */
    public void setFjzs(String fjzs) {
        this.fjzs = fjzs;
    }
    /**
     * @return Returns the flag.
     */
    public String getFlag() {
        return Flag;
    }
    /**
     * @param flag The flag to set.
     */
    public void setFlag(String flag) {
        Flag = flag;
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
     * @return Returns the jsr.
     */
    public String getJsr() {
        return jsr;
    }
    /**
     * @param jsr The jsr to set.
     */
    public void setJsr(String jsr) {
        this.jsr = jsr;
    }
    /**
     * @return Returns the jzr.
     */
    public String getJzr() {
        return jzr;
    }
    /**
     * @param jzr The jzr to set.
     */
    public void setJzr(String jzr) {
        this.jzr = jzr;
    }
    /**
     * @return Returns the jzrID.
     */
    public int getJzrID() {
        return jzrID;
    }
    /**
     * @param jzrID The jzrID to set.
     */
    public void setJzrID(int jzrID) {
        this.jzrID = jzrID;
    }
    /**
     * @return Returns the jzrq.
     */
    public String getJzrq() {
        return jzrq;
    }
    /**
     * @param jzrq The jzrq to set.
     */
    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
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
     * @return Returns the kjzg.
     */
    public String getKjzg() {
        return kjzg;
    }
    /**
     * @param kjzg The kjzg to set.
     */
    public void setKjzg(String kjzg) {
        this.kjzg = kjzg;
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
     * @return Returns the pzrq.
     */
    public String getPzrq() {
        return pzrq;
    }
    /**
     * @param pzrq The pzrq to set.
     */
    public void setPzrq(String pzrq) {
        this.pzrq = pzrq;
    }
    /**
     * @return Returns the pzhzbz.
     */
    public String getPzhzbz() {
        return pzhzbz;
    }
    /**
     * @param pzhzbz The pzhzbz to set.
     */
    public void setPzhzbz(String pzhzbz) {
        this.pzhzbz = pzhzbz;
    }
    /**
     * @return Returns the pzhzkmdy.
     */
    public String getPzhzkmdy() {
        return pzhzkmdy;
    }
    /**
     * @param pzhzkmdy The pzhzkmdy to set.
     */
    public void setPzhzkmdy(String pzhzkmdy) {
        this.pzhzkmdy = pzhzkmdy;
    }
    /**
     * @return Returns the pzje.
     */
    public double getPzje() {
        return pzje;
    }
    /**
     * @param pzje The pzje to set.
     */
    public void setPzje(double pzje) {
        this.pzje = pzje;
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
     * @return Returns the pzzy.
     */
    public String getPzzy() {
        return pzzy;
    }
    /**
     * @param pzzy The pzzy to set.
     */
    public void setPzzy(String pzzy) {
        this.pzzy = pzzy;
    }
    /**
     * @return Returns the sh.
     */
    public String getSh() {
        return sh;
    }
    /**
     * @param sh The sh to set.
     */
    public void setSh(String sh) {
        this.sh = sh;
    }
    /**
     * @return Returns the shID.
     */
    public int getShID() {
        return shID;
    }
    /**
     * @param shID The shID to set.
     */
    public void setShID(int shID) {
        this.shID = shID;
    }
    /**
     * @return Returns the shrq.
     */
    public String getShrq() {
        return shrq;
    }
    /**
     * @param shrq The shrq to set.
     */
    public void setShrq(String shrq) {
        this.shrq = shrq;
    }
    /**
     * @return Returns the sr.
     */
    public String getSr() {
        return sr;
    }
    /**
     * @param sr The sr to set.
     */
    public void setSr(String sr) {
        this.sr = sr;
    }
    /**
     * @return Returns the srID.
     */
    public int getSrID() {
        return srID;
    }
    /**
     * @param srID The srID to set.
     */
    public void setSrID(int srID) {
        this.srID = srID;
    }
    /**
     * @return Returns the srrq.
     */
    public String getSrrq() {
        return srrq;
    }
    /**
     * @param srrq The srrq to set.
     */
    public void setSrrq(String srrq) {
        this.srrq = srrq;
    }
    /**
     * @return Returns the zt.
     */
    public int getZt() {
        return zt;
    }
    /**
     * @param zt The zt to set.
     */
    public void setZt(int zt) {
        this.zt = zt;
    }
    
    /**
     * @return Returns the list.
     */
    public ArrayList getList() {
        return list;
    }
    /**
     * @param list The list to set.
     */
    public void setList(ArrayList list) {
        this.list = list;
    }
    
}
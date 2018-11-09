/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 安易凭证分录表信息
 * @author liuqing
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLAnyiPzflInfo implements Serializable
{
	private String gsdm = "";         	//公司代码
	private String kjqj = "";          	//会计期间
	private String pzly = "";       	//凭证来源
	private String pzh = "";         	//凭证号
	private long flh = 1;     			//分录号
	private int mxxh = 1;       		//明细序号
	private String mxlx = "";	        //明细类型
	private String fzdm = "";           //辅助代码
	private double je = 0;   	   		//金额
	
		
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
     * @return Returns the fzdm.
     */
    public String getFzdm() {
        return fzdm;
    }
    /**
     * @param fzdm The fzdm to set.
     */
    public void setFzdm(String fzdm) {
        this.fzdm = fzdm;
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
     * @return Returns the mxlx.
     */
    public String getMxlx() {
        return mxlx;
    }
    /**
     * @param mxlx The mxlx to set.
     */
    public void setMxlx(String mxlx) {
        this.mxlx = mxlx;
    }
    /**
     * @return Returns the mxxh.
     */
    public int getMxxh() {
        return mxxh;
    }
    /**
     * @param mxxh The mxxh to set.
     */
    public void setMxxh(int mxxh) {
        this.mxxh = mxxh;
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
}
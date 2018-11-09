package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 浪潮财务软件接口凭证分录Info
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class GLGenerSoftPzflInfo implements Serializable
{
	private String F_PZBH = "";          //凭证编号
	private String F_FLBH = "";          //分录编号
	private String F_KMBH = "";          //科目编号
	private String F_ZY = "";            //摘要
	private double F_JE = 0;             //金额  
	private long F_SL = 0;              //数量
	private double F_DJ = 0;             //单价
	private long F_WB = 0;              //外币 
	private double F_HL = 0;             //汇率
	private String F_WBBH = "";          //外币编号
	
	private String F_JZFX = "";	         //记账方向
	private String F_JSFS = "";          //结算方式
	private String F_JSH = "";           //结算号
	private Timestamp F_YWRQ = null;	 //业务日期
	private String F_TZXMBH = "";        //台账项目编号
	private String F_PZFL = "";          //凭证分录编号
	
	
	
	/**
	 * @return Returns the f_DJ.
	 */
	public double getF_DJ() {
		return F_DJ;
	}
	/**
	 * @param f_dj The f_DJ to set.
	 */
	public void setF_DJ(double f_dj) {
		F_DJ = f_dj;
	}
	/**
	 * @return Returns the f_FLBH.
	 */
	public String getF_FLBH() {
		return F_FLBH;
	}
	/**
	 * @param f_flbh The f_FLBH to set.
	 */
	public void setF_FLBH(String f_flbh) {
		F_FLBH = f_flbh;
	}
	/**
	 * @return Returns the f_HL.
	 */
	public double getF_HL() {
		return F_HL;
	}
	/**
	 * @param f_hl The f_HL to set.
	 */
	public void setF_HL(double f_hl) {
		F_HL = f_hl;
	}
	/**
	 * @return Returns the f_JE.
	 */
	public double getF_JE() {
		return F_JE;
	}
	/**
	 * @param f_je The f_JE to set.
	 */
	public void setF_JE(double f_je) {
		F_JE = f_je;
	}
	/**
	 * @return Returns the f_JSFS.
	 */
	public String getF_JSFS() {
		return F_JSFS;
	}
	/**
	 * @param f_jsfs The f_JSFS to set.
	 */
	public void setF_JSFS(String f_jsfs) {
		F_JSFS = f_jsfs;
	}
	/**
	 * @return Returns the f_JSH.
	 */
	public String getF_JSH() {
		return F_JSH;
	}
	/**
	 * @param f_jsh The f_JSH to set.
	 */
	public void setF_JSH(String f_jsh) {
		F_JSH = f_jsh;
	}
	/**
	 * @return Returns the f_JZFX.
	 */
	public String getF_JZFX() {
		return F_JZFX;
	}
	/**
	 * @param f_jzfx The f_JZFX to set.
	 */
	public void setF_JZFX(String f_jzfx) {
		F_JZFX = f_jzfx;
	}
	/**
	 * @return Returns the f_KMBH.
	 */
	public String getF_KMBH() {
		return F_KMBH;
	}
	/**
	 * @param f_kmbh The f_KMBH to set.
	 */
	public void setF_KMBH(String f_kmbh) {
		F_KMBH = f_kmbh;
	}
	/**
	 * @return Returns the f_PZBH.
	 */
	public String getF_PZBH() {
		return F_PZBH;
	}
	/**
	 * @param f_pzbh The f_PZBH to set.
	 */
	public void setF_PZBH(String f_pzbh) {
		F_PZBH = f_pzbh;
	}
	/**
	 * @return Returns the f_PZFL.
	 */
	public String getF_PZFL() {
		return F_PZFL;
	}
	/**
	 * @param f_pzfl The f_PZFL to set.
	 */
	public void setF_PZFL(String f_pzfl) {
		F_PZFL = f_pzfl;
	}
	/**
	 * @return Returns the f_SL.
	 */
	public long getF_SL() {
		return F_SL;
	}
	/**
	 * @param f_sl The f_SL to set.
	 */
	public void setF_SL(long f_sl) {
		F_SL = f_sl;
	}
	/**
	 * @return Returns the f_TZXMBH.
	 */
	public String getF_TZXMBH() {
		return F_TZXMBH;
	}
	/**
	 * @param f_tzxmbh The f_TZXMBH to set.
	 */
	public void setF_TZXMBH(String f_tzxmbh) {
		F_TZXMBH = f_tzxmbh;
	}
	/**
	 * @return Returns the f_WB.
	 */
	public long getF_WB() {
		return F_WB;
	}
	/**
	 * @param f_wb The f_WB to set.
	 */
	public void setF_WB(long f_wb) {
		F_WB = f_wb;
	}
	/**
	 * @return Returns the f_WBBH.
	 */
	public String getF_WBBH() {
		return F_WBBH;
	}
	/**
	 * @param f_wbbh The f_WBBH to set.
	 */
	public void setF_WBBH(String f_wbbh) {
		F_WBBH = f_wbbh;
	}
	/**
	 * @return Returns the f_YWRQ.
	 */
	public Timestamp getF_YWRQ() {
		return F_YWRQ;
	}
	/**
	 * @param f_ywrq The f_YWRQ to set.
	 */
	public void setF_YWRQ(Timestamp f_ywrq) {
		F_YWRQ = f_ywrq;
	}
	/**
	 * @return Returns the f_ZY.
	 */
	public String getF_ZY() {
		return F_ZY;
	}
	/**
	 * @param f_zy The f_ZY to set.
	 */
	public void setF_ZY(String f_zy) {
		F_ZY = f_zy;
	}
}
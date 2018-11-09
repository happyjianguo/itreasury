   
package com.iss.itreasury.glinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * �˳��ӿ��м��ƾ֤Info
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


public class GLGenerSoftPzInfo implements Serializable
{
	private Timestamp F_PZRQ = null;   //ƾ֤����
	private String F_PZBH = "";        //ƾ֤��� 
	private String F_PZLXBH = "";      //ƾ֤���ͱ��
	private long F_FJZS = 0;          //��������
	private String F_BZ = "";          //�����־
	private String F_PZNM = "";        //ƾ֤����
	private String F_YSBH = "";        //�����ƾ֤���
	
	private String F_ZDR = "";			//�Ƶ���		2005.9.19Ϊ���˳�9.0
	
	private ArrayList list = new ArrayList();  //��¼����
      
	/**
	 * @return Returns the f_BZ.
	 */
	public String getF_BZ() {
		return F_BZ;
	}
	/**
	 * @param f_bz The f_BZ to set.
	 */
	public void setF_BZ(String f_bz) {
		F_BZ = f_bz;
	}
	/**
	 * @return Returns the f_FJZS.
	 */
	public long getF_FJZS() {
		return F_FJZS;
	}
	/**
	 * @param f_fjzs The f_FJZS to set.
	 */
	public void setF_FJZS(long f_fjzs) {
		F_FJZS = f_fjzs;
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
	 * @return Returns the f_PZLXBH.
	 */
	public String getF_PZLXBH() {
		return F_PZLXBH;
	}
	/**
	 * @param f_pzlxbh The f_PZLXBH to set.
	 */
	public void setF_PZLXBH(String f_pzlxbh) {
		F_PZLXBH = f_pzlxbh;
	}
	/**
	 * @return Returns the f_PZNM.
	 */
	public String getF_PZNM() {
		return F_PZNM;
	}
	/**
	 * @param f_pznm The f_PZNM to set.
	 */
	public void setF_PZNM(String f_pznm) {
		F_PZNM = f_pznm;
	}
	/**
	 * @return Returns the f_PZRQ.
	 */
	public Timestamp getF_PZRQ() {
		return F_PZRQ;
	}
	/**
	 * @param f_pzrq The f_PZRQ to set.
	 */
	public void setF_PZRQ(Timestamp f_pzrq) {
		F_PZRQ = f_pzrq;
	}
	/**
	 * @return Returns the f_YSBH.
	 */
	public String getF_YSBH() {
		return F_YSBH;
	}
	/**
	 * @param f_ysbh The f_YSBH to set.
	 */
	public void setF_YSBH(String f_ysbh) {
		F_YSBH = f_ysbh;
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
	
	/**
	 * @return
	 */
	public GLGenerSoftPzflInfo getEntryInfo(long i)
	{
		if (this.list.size() > i && i >= 0)
		{
			return (GLGenerSoftPzflInfo) this.list.get((int) i);
		}
		else
		{
			return null;
		}
	}
	/**
	 * @param l
	 */
	public void addEntryInfo(GLGenerSoftPzflInfo entry)
	{
		this.list.add(entry);
	}
	
	/**
	 * @param f_pzrq The F_ZDR to set.
	 */
	public void setF_ZDR(String f_ZDR) {
		F_ZDR = f_ZDR;
	}
	/**
	 * @return Returns the F_ZDR.
	 */
	public String getF_ZDR() {
		return F_ZDR;
	}
}
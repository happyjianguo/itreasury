/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.configmanage.dataentity;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfigItemInfo implements java.io.Serializable{

	private String name="";//����������
	private String namemap="";//�������Ӧ����������
	private String type="";//����������
	private String desc="";//����
	private String vale="";//�������ֵ
	
	/**
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return Returns the vale.
	 */
	public String getVale() {
		return vale;
	}
	/**
	 * @param vale The vale to set.
	 */
	public void setVale(String vale) {
		this.vale = vale;
	}
	/**
	 * @return Returns the namemap.
	 */
	public String getNamemap() {
		return namemap;
	}
	/**
	 * @param namemap The namemap to set.
	 */
	public void setNamemap(String namemap) {
		this.namemap = namemap;
	}
}

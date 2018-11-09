package com.iss.sysframe.pager.dataentity;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class FlexiGridInfo extends BaseDataEntity {

	String flexigrid_page = "1";
	String flexigrid_rp = "10";
    String flexigrid_sortname = "";
    String flexigrid_sortorder = "";
    String flexigrid_query = "";
    String flexigrid_qtype = "";
    
	public String getFlexigrid_page() {
		return flexigrid_page;
	}
	public void setFlexigrid_page(String flexigrid_page) {
		this.flexigrid_page = flexigrid_page;
	}
	public String getFlexigrid_rp() {
		return flexigrid_rp;
	}
	public void setFlexigrid_rp(String flexigrid_rp) {
		this.flexigrid_rp = flexigrid_rp;
	}
	public String getFlexigrid_sortname() {
		return flexigrid_sortname;
	}
	public void setFlexigrid_sortname(String flexigrid_sortname) {
		this.flexigrid_sortname = flexigrid_sortname;
	}
	public String getFlexigrid_sortorder() {
		return flexigrid_sortorder;
	}
	public void setFlexigrid_sortorder(String flexigrid_sortorder) {
		this.flexigrid_sortorder = flexigrid_sortorder;
	}
	public String getFlexigrid_query() {
		return flexigrid_query;
	}
	public void setFlexigrid_query(String flexigrid_query) {
		this.flexigrid_query = flexigrid_query;
	}
	public String getFlexigrid_qtype() {
		return flexigrid_qtype;
	}
	public void setFlexigrid_qtype(String flexigrid_qtype) {
		this.flexigrid_qtype = flexigrid_qtype;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
}

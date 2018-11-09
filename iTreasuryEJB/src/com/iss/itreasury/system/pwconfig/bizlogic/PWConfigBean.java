package com.iss.itreasury.system.pwconfig.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.system.pwconfig.dao.Sys_PasswordSettingDAO;
import com.iss.itreasury.system.pwconfig.dataentity.PasswordInfo;
import com.iss.itreasury.util.Constant;

public class PWConfigBean {
	//获得密码设置信息
	public PasswordInfo getPasswordConfigInfo(long lOfficeId){
		PasswordInfo passwordInfo = new PasswordInfo();
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			passwordInfo.setStatus(1);
			passwordInfo.setType(Constant.FALSE);
			passwordInfo.setOfficeId(lOfficeId);
			Collection coll = dao.findByCondition(passwordInfo);
			if(coll.size()>0){
				Object[] array = coll.toArray();
				passwordInfo = (PasswordInfo)array[0];
			}else{
				passwordInfo.firstInit();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}			
		return passwordInfo;
	}
	
	//新增密码设置信息
	public void addPasswordInfo(PasswordInfo passwordInfo){
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			dao.addPasswordInfo(passwordInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//更新密码设置信息，包括取消设置
	public void updatePasswordInfo(PasswordInfo passwordInfo){
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			dao.updatePasswordInfo(passwordInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//获得用户密码和上次修改密码日期
	public List getUserPwdInfo(long id){
		List list = new ArrayList();
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			list = dao.getUserPwdInfo(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//修改用户密码
	public void changeUserPassword(long lUserID, String strPassword) throws Exception{
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		dao.changeUserPassword(lUserID, strPassword);
	}
}

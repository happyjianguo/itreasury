package com.iss.itreasury.system.pwconfig.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.system.pwconfig.dao.Sys_PasswordSettingDAO;
import com.iss.itreasury.system.pwconfig.dataentity.PasswordInfo;
import com.iss.itreasury.util.Constant;

public class PWConfigBean {
	//�������������Ϣ
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
	
	//��������������Ϣ
	public void addPasswordInfo(PasswordInfo passwordInfo){
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			dao.addPasswordInfo(passwordInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//��������������Ϣ������ȡ������
	public void updatePasswordInfo(PasswordInfo passwordInfo){
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			dao.updatePasswordInfo(passwordInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//����û�������ϴ��޸���������
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
	
	//�޸��û�����
	public void changeUserPassword(long lUserID, String strPassword) throws Exception{
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		dao.changeUserPassword(lUserID, strPassword);
	}
}

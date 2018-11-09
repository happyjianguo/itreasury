package com.iss.itreasury.ebank.pwconfig.bizlogic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
import com.iss.itreasury.ebank.pwconfig.dao.Sys_PasswordSettingDAO;
import com.iss.itreasury.ebank.pwconfig.dataentity.PasswordInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import java.util.regex.*;

public class PWConfigBean {
	String signatureValue = "";
	public static class PasswordAwokeMode {
		public static String NONE = "0";
		public static String ALERT = "1";
		public static String CONFIRM = "2";
		public static String CNERROR = "3";
	}
	
	public String[] passwordValidate(long lOfficeId, long lUserId) throws IException{
		 //提示信息
		StringBuffer strMessage = null;
		String[] strReturn = new String[2];
		
		try
		{
			strReturn[0] = PasswordAwokeMode.NONE;
			 //提示信息
			strMessage = new StringBuffer();
			//检验信息
			String strInfo = "";
			//取得获得密码设置信息
			PasswordInfo passwordInfo = this.getPasswordConfigInfo(lOfficeId);
			//取得用户信息
			OB_UserDAO userDao = new OB_UserDAO();
			OB_UserInfo userInfo = (OB_UserInfo)userDao.findByID(lUserId, OB_UserInfo.class);
			
			String regEx = null; //正则表达式模版
			boolean hasNumber = true; //是否包含数字
			boolean hasLowercase = true; //是否包含小写字母
			boolean hasCapital = true; //是否包含大写字母
			boolean hasSpecial = true; //是否包含特殊字符
			boolean hasLength = true; //长度是否符合规则
			
			//长度校验
			if(userInfo.getSPassword().length() < passwordInfo.getMinPassword()){ 
				hasLength = false;
			}
			//是否包含数字校验
			if(passwordInfo.getCompriseNumber()==1){
				regEx = "[0-9]";			
				hasNumber = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[数字]";
				regEx = null;
			}
			//是否包含小写字母校验
			if(passwordInfo.getCompriseLowercase()==1){
				regEx = "[a-z]";
				hasLowercase = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[小写字母]";
				regEx = null;
			}
			//是否包含大写字母校验		
			if(passwordInfo.getCompriseCapital()==1){
				regEx = "[A-Z]";
				hasCapital = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[大写字母]";
				regEx = null;
			}
			//是否包含特殊字符校验
			if(passwordInfo.getCompriseSpecial()==1){
				regEx = "[^0-9a-zA-Z]";
				hasSpecial  = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[特殊字符]";
				regEx = null;
			}
			
			//如果设置了密码过期验证走此流程
			if(passwordInfo.getCompriseTerm()==1){
				Timestamp currentDate = Env.getCurrentSystemDate();					//取得当前时间
				long termDate = passwordInfo.getTermDate();							//设置的过期天数
				long remindDate = passwordInfo.getRemindDate();						//设置的到期提醒天数
				Timestamp lastChangeDate = null;
				if(userInfo.getDtChangePassword() == null){					//上次修改密码时间
					lastChangeDate = Env.getSystemDate();
				}else{
					lastChangeDate = userInfo.getDtChangePassword();
				}
				long termTime = lastChangeDate.getTime() + termDate*24*3600*1000;	//密码应该到期的时间
				long remindTime = remindDate*24*3600*1000;							//密码到期提醒的时间
				long remainDate = (long)Math.ceil((termTime - currentDate.getTime())/1000/3600/24);		//还剩余多少天密码将过期
				if(currentDate.getTime()>=termTime){					//密码已过期
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("您的密码已过期，请联系系统管理员");
				}
				else if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)&&((termTime - remindTime)>currentDate.getTime())){			//如果设置了强制修改密码且密码不符合规则
					if(strInfo.length()>0){	//如果勾选了验证，下同
						strReturn[0] = PasswordAwokeMode.ALERT;
						strMessage.append("您的密码不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
					}
					else{	//如果未勾选任何验证，下同
						strReturn[0] = PasswordAwokeMode.ALERT;
						strMessage.append("您的密码不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
					}
				}
				else if((termTime - remindTime)<=currentDate.getTime()){  //如果处于到期提醒天数之中
					if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){		//如果不符合规则且强制修改
						if(strInfo.length()>0){
							strReturn[0] = PasswordAwokeMode.ALERT;
							strMessage.append("的密码还有"+ remainDate +"天过期，并且不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
						}
						else{
							strReturn[0] = PasswordAwokeMode.ALERT;
							strMessage.append("您的密码还有"+ remainDate +"天过期，并且不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
						}
					}
					else if((!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){							//如果不符合规则
						if(strInfo.length()>0){
							strReturn[0] = PasswordAwokeMode.CONFIRM;
							strMessage.append("您的密码还有"+ remainDate +"天过期，并且不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
						}
						else{
							strReturn[0] = PasswordAwokeMode.CONFIRM;
							strMessage.append("您的密码还有"+ remainDate +"天过期，并且不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
						}
					}
					else{
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("您的密码还有"+ remainDate +"天过期，请进行修改");
					}
				}
				else if((!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){ //未到提醒天数，并且密码不符合规则，不强制修改
					if(strInfo.length()>0){
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("您的密码不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
					}else{
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("您的密码不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
					}					
				}
				else {
					strMessage = new StringBuffer();
				}
			}
			else if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){	//如果未选择期限校验，强制修改且不符合规则
				if(strInfo.length()>0){
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("您的密码不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
				}else{
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("您的密码不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
				}		
			}
			else if(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength){									//如果未选择期限校验，不符合规则，不强制修改
				if(strInfo.length()>0){
					strReturn[0] = PasswordAwokeMode.CONFIRM;
					strMessage.append("您的密码不符合规则，必须包含"+ strInfo +",且长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
				}else{
					strReturn[0] = PasswordAwokeMode.CONFIRM;
					strMessage.append("您的密码不符合规则，长度至少为"+ passwordInfo.getMinPassword() +",请进行修改");
				}				
			}
			else {
				strMessage = new StringBuffer();
			}
			strReturn[1] = strMessage.toString();
			if(userInfo.getSCertCn()==null||userInfo.getSCertCn().equals("")){
				strReturn[0] = PasswordAwokeMode.CNERROR;
				strReturn[1] = "客户未绑定证书";
			}else if(this.signatureValue!=null&&!this.signatureValue.equals(userInfo.getSCertCn())){
				strReturn[0] = PasswordAwokeMode.CNERROR;
				strReturn[1] = "客户与key不匹配";
			}
		}
		catch(Exception e){
			new IException("密码校验出错",e);
		}
		return strReturn;
	}
	
	
	//获得密码设置信息
	public PasswordInfo getPasswordConfigInfo(long lOfficeId){
		PasswordInfo passwordInfo = new PasswordInfo();
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			passwordInfo.setStatus(1);					//查询为可用的状态
			passwordInfo.setType(Constant.TRUE);		//查询为网银使用的设置
			passwordInfo.setOfficeId(lOfficeId);		//查询本办事处的设置
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
	
	//获得用户的密码和上次修改密码时间
	public List getOBUserPwdInfo(long id){
		List list = new ArrayList();
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			list = dao.getOBUserPwdInfo(id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	//修改用户密码
	public void changeUserPassword(long lUserID, String strPassword) throws Exception{
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		dao.changeUserPassword(lUserID, strPassword);
	}


	public String getSignatureValue() {
		return signatureValue;
	}


	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
}

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
		 //��ʾ��Ϣ
		StringBuffer strMessage = null;
		String[] strReturn = new String[2];
		
		try
		{
			strReturn[0] = PasswordAwokeMode.NONE;
			 //��ʾ��Ϣ
			strMessage = new StringBuffer();
			//������Ϣ
			String strInfo = "";
			//ȡ�û������������Ϣ
			PasswordInfo passwordInfo = this.getPasswordConfigInfo(lOfficeId);
			//ȡ���û���Ϣ
			OB_UserDAO userDao = new OB_UserDAO();
			OB_UserInfo userInfo = (OB_UserInfo)userDao.findByID(lUserId, OB_UserInfo.class);
			
			String regEx = null; //������ʽģ��
			boolean hasNumber = true; //�Ƿ��������
			boolean hasLowercase = true; //�Ƿ����Сд��ĸ
			boolean hasCapital = true; //�Ƿ������д��ĸ
			boolean hasSpecial = true; //�Ƿ���������ַ�
			boolean hasLength = true; //�����Ƿ���Ϲ���
			
			//����У��
			if(userInfo.getSPassword().length() < passwordInfo.getMinPassword()){ 
				hasLength = false;
			}
			//�Ƿ��������У��
			if(passwordInfo.getCompriseNumber()==1){
				regEx = "[0-9]";			
				hasNumber = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[����]";
				regEx = null;
			}
			//�Ƿ����Сд��ĸУ��
			if(passwordInfo.getCompriseLowercase()==1){
				regEx = "[a-z]";
				hasLowercase = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[Сд��ĸ]";
				regEx = null;
			}
			//�Ƿ������д��ĸУ��		
			if(passwordInfo.getCompriseCapital()==1){
				regEx = "[A-Z]";
				hasCapital = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[��д��ĸ]";
				regEx = null;
			}
			//�Ƿ���������ַ�У��
			if(passwordInfo.getCompriseSpecial()==1){
				regEx = "[^0-9a-zA-Z]";
				hasSpecial  = Pattern.compile(regEx).matcher(userInfo.getSPassword()).find();
				strInfo = "[�����ַ�]";
				regEx = null;
			}
			
			//������������������֤�ߴ�����
			if(passwordInfo.getCompriseTerm()==1){
				Timestamp currentDate = Env.getCurrentSystemDate();					//ȡ�õ�ǰʱ��
				long termDate = passwordInfo.getTermDate();							//���õĹ�������
				long remindDate = passwordInfo.getRemindDate();						//���õĵ�����������
				Timestamp lastChangeDate = null;
				if(userInfo.getDtChangePassword() == null){					//�ϴ��޸�����ʱ��
					lastChangeDate = Env.getSystemDate();
				}else{
					lastChangeDate = userInfo.getDtChangePassword();
				}
				long termTime = lastChangeDate.getTime() + termDate*24*3600*1000;	//����Ӧ�õ��ڵ�ʱ��
				long remindTime = remindDate*24*3600*1000;							//���뵽�����ѵ�ʱ��
				long remainDate = (long)Math.ceil((termTime - currentDate.getTime())/1000/3600/24);		//��ʣ����������뽫����
				if(currentDate.getTime()>=termTime){					//�����ѹ���
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("���������ѹ��ڣ�����ϵϵͳ����Ա");
				}
				else if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)&&((termTime - remindTime)>currentDate.getTime())){			//���������ǿ���޸����������벻���Ϲ���
					if(strInfo.length()>0){	//�����ѡ����֤����ͬ
						strReturn[0] = PasswordAwokeMode.ALERT;
						strMessage.append("�������벻���Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
					}
					else{	//���δ��ѡ�κ���֤����ͬ
						strReturn[0] = PasswordAwokeMode.ALERT;
						strMessage.append("�������벻���Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
					}
				}
				else if((termTime - remindTime)<=currentDate.getTime()){  //������ڵ�����������֮��
					if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){		//��������Ϲ�����ǿ���޸�
						if(strInfo.length()>0){
							strReturn[0] = PasswordAwokeMode.ALERT;
							strMessage.append("�����뻹��"+ remainDate +"����ڣ����Ҳ����Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
						}
						else{
							strReturn[0] = PasswordAwokeMode.ALERT;
							strMessage.append("�������뻹��"+ remainDate +"����ڣ����Ҳ����Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
						}
					}
					else if((!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){							//��������Ϲ���
						if(strInfo.length()>0){
							strReturn[0] = PasswordAwokeMode.CONFIRM;
							strMessage.append("�������뻹��"+ remainDate +"����ڣ����Ҳ����Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
						}
						else{
							strReturn[0] = PasswordAwokeMode.CONFIRM;
							strMessage.append("�������뻹��"+ remainDate +"����ڣ����Ҳ����Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
						}
					}
					else{
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("�������뻹��"+ remainDate +"����ڣ�������޸�");
					}
				}
				else if((!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){ //δ�������������������벻���Ϲ��򣬲�ǿ���޸�
					if(strInfo.length()>0){
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("�������벻���Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
					}else{
						strReturn[0] = PasswordAwokeMode.CONFIRM;
						strMessage.append("�������벻���Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
					}					
				}
				else {
					strMessage = new StringBuffer();
				}
			}
			else if((passwordInfo.getForcePerfect()==1)&&(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength)){	//���δѡ������У�飬ǿ���޸��Ҳ����Ϲ���
				if(strInfo.length()>0){
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("�������벻���Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
				}else{
					strReturn[0] = PasswordAwokeMode.ALERT;
					strMessage.append("�������벻���Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
				}		
			}
			else if(!hasNumber||!hasLowercase||!hasCapital||!hasSpecial||!hasLength){									//���δѡ������У�飬�����Ϲ��򣬲�ǿ���޸�
				if(strInfo.length()>0){
					strReturn[0] = PasswordAwokeMode.CONFIRM;
					strMessage.append("�������벻���Ϲ��򣬱������"+ strInfo +",�ҳ�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
				}else{
					strReturn[0] = PasswordAwokeMode.CONFIRM;
					strMessage.append("�������벻���Ϲ��򣬳�������Ϊ"+ passwordInfo.getMinPassword() +",������޸�");
				}				
			}
			else {
				strMessage = new StringBuffer();
			}
			strReturn[1] = strMessage.toString();
			if(userInfo.getSCertCn()==null||userInfo.getSCertCn().equals("")){
				strReturn[0] = PasswordAwokeMode.CNERROR;
				strReturn[1] = "�ͻ�δ��֤��";
			}else if(this.signatureValue!=null&&!this.signatureValue.equals(userInfo.getSCertCn())){
				strReturn[0] = PasswordAwokeMode.CNERROR;
				strReturn[1] = "�ͻ���key��ƥ��";
			}
		}
		catch(Exception e){
			new IException("����У�����",e);
		}
		return strReturn;
	}
	
	
	//�������������Ϣ
	public PasswordInfo getPasswordConfigInfo(long lOfficeId){
		PasswordInfo passwordInfo = new PasswordInfo();
		Sys_PasswordSettingDAO dao = new Sys_PasswordSettingDAO();
		try{
			passwordInfo.setStatus(1);					//��ѯΪ���õ�״̬
			passwordInfo.setType(Constant.TRUE);		//��ѯΪ����ʹ�õ�����
			passwordInfo.setOfficeId(lOfficeId);		//��ѯ�����´�������
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
	
	//����û���������ϴ��޸�����ʱ��
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

	//�޸��û�����
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

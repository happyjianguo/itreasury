package com.iss.itreasury.ebank.util;

import com.datech.nc.DeanKeyServer;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.privilege.dao.OB_UserDAO;
import com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo;
//import com.iss.itreasury.safety.info.CertificateInfo;
//import com.iss.itreasury.safety.info.KeyStoreInfo;
//import com.iss.itreasury.safety.info.SignatureInfo;
//import com.iss.itreasury.safety.signature.SignatureAuthentication;
//import com.iss.itreasury.safety.util.SignatureUtil;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.OBFSWorkflowManager;

public class OBSignatureUtil {

	public static boolean isIdHaveNotRealValue(FinanceInfo financeInfo,
			boolean blnIsNeedApproval) {

		boolean blnReturn = false;

//		if (blnIsNeedApproval) {
//			if ((financeInfo.getNextLevel() == 1 || financeInfo.getNextLevel() == 0)
//					&& !financeInfo.isRefused()
//					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
//					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.DEAL
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.FINISH
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.REFUSE) {
//
//				// ���⴦��
//				blnReturn = true;
//			}
//		} else {
//			if (financeInfo.getStatus() != OBConstant.SettInstrStatus.CHECK
//					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.DEAL
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.FINISH
//					&& financeInfo.getStatus() != OBConstant.SettInstrStatus.REFUSE) {
//
//				blnReturn = true;
//			}
//		}
		/*add by bingliu 20110907 �����ǩ�������⡣֮ǰ��ҳ���������޸�����Ҳ����ʾ���۸ġ�*/
		//if(blnReturn == true)
//		//{//У���Ƿ��޸Ĺ�
//			System.out.println("======financeInfo.getDtModify()===========  "+financeInfo.getDtModify());
//			System.out.println("======financeInfo.getConfirmDate()===========  "+financeInfo.getConfirmDate());
//			if(financeInfo.getDtModify().after(financeInfo.getConfirmDate()))
//			{//�޸�ʱ�����ύʱ���˵�����޸�,��ǩ��ֵ��Ӧ�ð���ָ��ID
//				System.out.println("======financeInfo.getDtModify().after(financeInfo.getConfirmDate())===========  ");
//				blnReturn = false;
//			}
//			else
//			{//����˵���ύ���û�н����κβ�������ǩ��ֵ�в�Ӧ�ð���ָ��ID
//				blnReturn = true;
//			}
//		//}
		
		/*add by bingliu 20110907 �����ǩ�������⡣֮ǰ��ҳ���������޸�����Ҳ����ʾ���۸ġ�*/
		//̫������,ֱ�ӷ���true,������ָ��ID��
		blnReturn = true;
		return blnReturn;
	}

	public static boolean isNeedApproval(SessionOB sessionMng,
			FinanceInfo financeInfo) throws IException {

		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(financeInfo.getTransType());
		//����¼�������ڵĵ�λID���������ڲ�����ҵ���û��ĵ�λID���жϴ�ҵ���������ĸ�������������λ�������������¼���λ��������
		if(NameRef.getClientByUserID(financeInfo.getConfirmUserID())!=sessionMng.m_lClientID)
		{
			inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISYES);
		}
		else
		{
			inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		}
		return OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
	}

	/**
	 * ʹ�÷�����֤��˽Կ����ǩ��
	 * 
	 * @param originalData ��Ҫǩ��������
	 * @return ǩ��ֵ
	 * @throws IException
	 */
//	public static String doSignatureUseKeyStore(String originalData)
//			throws IException {
//
//		String strKeyStoreFilePathName = Config.getProperty(
//				ConfigConstant.GLOBAL_KEYSTORE_KEYSTOREFILEPATHNAME,
//				"./config/cafiles/keystore.jks");
//		String strKeyStorePassword = Config.getProperty(
//				ConfigConstant.GLOBAL_KEYSTORE_KEYSTOREPASSWORD, null);
//		String strKeyAlias = Config.getProperty(
//				ConfigConstant.GLOBAL_KEYSTORE_KEYALIAS, null);
//		String strKeyPassword = Config.getProperty(
//				ConfigConstant.GLOBAL_KEYSTORE_KEYPASSWORD, null);		
//
//		KeyStoreInfo keyStoreInfo = new KeyStoreInfo();
//		keyStoreInfo.setKeyStoreFilePathName(strKeyStoreFilePathName);
//		keyStoreInfo.setKeyStorePassword(strKeyStorePassword);
//		keyStoreInfo.setKeyAlias(strKeyAlias);
//		keyStoreInfo.setKeyPassword(strKeyPassword);
//
//		try {
//
//			return SignatureUtil.doSignatureUseKeyStore(originalData,
//					keyStoreInfo);
//			
//			/*return SignatureAuthentication.doSignatureUseKeyStore(
//					signatureInfo, keyStoreInfo);*/			
//
//		} catch (Exception e) {
//			throw new IException(e.getMessage());
//		}
//	}
	
//	public static String doCFCASignature(String originalData)throws Exception
//	{
//		String signatureValue = "";
//		try
//		{
//			if(originalData.equals(""))
//			{
//				throw new Exception("��ǩ������Ϊ��!");
//			}
//			String certicationPath = Config.getProperty(ConfigConstant.GLOBAL_CERTIFICATE_PATH,"");
//			if(certicationPath.equals(""))
//			{
//				throw new Exception("������ǩ��֤��·�����ô���!");
//			}
//			String certicationPassword = Config.getProperty(ConfigConstant.GLOBAL_CERTIFICATE_PASSWORD,"");
//			CertificateInfo info = new CertificateInfo();
//			info.setCertifiacatePassword(certicationPassword);
//			info.setCertifiacatePath(certicationPath);
//			signatureValue = SignatureUtil.doCFCASignature(info,originalData);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//		return signatureValue;
//	}
	
//	public static String doSDEANSignature(String originalData)throws Exception
//	{
//		DeanKeyServer server = new DeanKeyServer();
//		byte[] signature = server.sign(originalData.getBytes());
//		return new String(signature);
//	}
//	
//	public static String doNetSignSignature(String originalData) throws Exception
//	{
//		String signatureValue = "";
//		signatureValue = SignatureUtil.doNetSignSignature(originalData);
//		return signatureValue;
//	}
//	
//	public static FinanceInfo getSignatureInfo(FinanceInfo info,String signName) throws Exception
//	{
//		SignatureInfo signatureInfo = new SignatureInfo();
//		String signatureOriginalValue = "";
//		String signatureValue = "";
//    	String[] nameArray = new String[]{};
//    	String[] valueArray = new String[]{};
//    	nameArray = OBSignatureConstant.getNameArrayByAllOperation(info);
//    	valueArray = OBSignatureConstant.getValueArrayByAllOperation(info);   
//    	signatureInfo.setNameArray(nameArray);
//    	signatureInfo.setValueArray(valueArray);
//    	if(signName.equals(Constant.GlobalTroyName.CFCA))
//    	{
//    		signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//    		signatureValue = doCFCASignature(signatureOriginalValue);
//    	}else if(signName.equals(Constant.GlobalTroyName.NetSign))
//    	{
//    		signatureOriginalValue = SignatureUtil.formatDataNetSign(signatureInfo);
//    		signatureValue = doNetSignSignature(signatureOriginalValue);
//    	}else if(signName.equals(Constant.GlobalTroyName.ITrus))
//    	{
//    		signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//    		signatureValue = doSignatureUseKeyStore(signatureOriginalValue);
//    	}
//    	else if(signName.equals(Constant.GlobalTroyName.SDEAN))
//    	{
//    		signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//    		signatureValue = doSDEANSignature(signatureOriginalValue);
//    	}
//    	info.setSignatureOriginalValue(signatureOriginalValue);
//    	info.setSignatureValue(signatureValue);   
//		return info;
//	}
//	
//	public static boolean validateSignature(FinanceInfo info,String signName)throws Exception
//	{
//		boolean blnNotBeFalsified = false;
//		try
//		{
//	        String[] nameArray = null;
//			String[] valueArray = null;
//			String signatureOriginalValue;
//			if(info.getLSource()>SETTConstant.ExtSystemSource.EBANK)
//			{
//				blnNotBeFalsified = true;
//			}else
//			{
//	    		nameArray = OBSignatureConstant.getNameArrayByAllOperation(info);
//	    		valueArray = OBSignatureConstant.getValueArrayByAllOperation(info);
//				SignatureInfo signatureInfo = new SignatureInfo();
//				signatureInfo.setNameArray(nameArray);
//				signatureInfo.setValueArray(valueArray);    		
//				signatureInfo.setSignatureValue(info.getSignatureValue());
//				if(signName.equals(Constant.GlobalTroyName.CFCA))
//				{
//					signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//					signatureInfo.setOriginalData(signatureOriginalValue);
//					blnNotBeFalsified = SignatureAuthentication.validateFromCFCASign(signatureInfo);
//				}else if(signName.equals(Constant.GlobalTroyName.NetSign))
//				{
//					signatureOriginalValue = SignatureUtil.formatDataNetSign(signatureInfo);  
//					signatureInfo.setOriginalData(signatureOriginalValue);
//					blnNotBeFalsified = SignatureAuthentication.validateFromReqNetSign(signatureInfo);	
//				}else if(signName.equals(Constant.GlobalTroyName.ITrus))
//				{
//					signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//					signatureInfo.setOriginalData(signatureOriginalValue);
//					blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);	
//				}else if(signName.equals(Constant.GlobalTroyName.SDEAN))
//				{
//					OB_UserDAO userDao = new OB_UserDAO();
//					OB_UserInfo userInfo = new OB_UserInfo();
//					userInfo = (OB_UserInfo)userDao.findByID(info.getConfirmUserID(),OB_UserInfo.class);
//					signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
//					signatureInfo.setOriginalData(signatureOriginalValue);
//					signatureInfo.setStrLogin(userInfo.getSLoginNo());
//					boolean isClientVerify = (info.getSBatchNo()==null||info.getSBatchNo().equals(""))?true:false;
//					if(isClientVerify)
//					{
//						blnNotBeFalsified = SignatureAuthentication.validateFromSDean(signatureInfo);	
//					}else
//					{
//						blnNotBeFalsified = SignatureAuthentication.validateFromSDeanServer(signatureInfo);	
//					}
//			
//				}
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return blnNotBeFalsified;
//	}

}

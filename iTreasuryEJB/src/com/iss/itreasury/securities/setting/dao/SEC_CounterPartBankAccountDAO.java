/*
 * Created on 2004-3-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.setting.dao;

import java.util.*;
import java.sql.*;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.setting.dataentity.CounterPartBankAccountInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SEC_CounterPartBankAccountDAO extends SecuritiesDAO
{

	/**
	 * @param tableName
	 */
	public SEC_CounterPartBankAccountDAO()
	{
		super("SEC_CounterPartBankAccount");
		// TODO Auto-generated constructor stub
	}
	
	/**
		 * ���ݿ���²�������
		 * @param id������
		 * @param statusID ��Ҫ���µ���״̬
		 * @return
		 * @throws ITreasuryDAOException
		 */		
		public void updateCounterPartBankStatus(long counterPartId, long statusId) throws ITreasuryDAOException{
			try {
				initDAO();
				StringBuffer buffer = new StringBuffer();
				buffer.append("UPDATE \n");
				buffer.append(strTableName);
				buffer.append(" SET STATUSID = " + statusId);
				//TBD: maybe need add update execute date
				buffer.append("\n  WHERE COUNTERPARTID = " + counterPartId);
				String strSQL = buffer.toString();
				log.debug(strSQL);
				prepareStatement(strSQL);
				executeQuery();
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new ITreasuryDAOException("״̬�����쳣",e);
			}
		
		}	
		
		/**
		 * ���Ҳ���
		 * */
		public Collection findByCounterpartType(long counterpartType)throws ITreasuryDAOException{
			ArrayList list = new ArrayList();
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(COUNTERPARTTYPE, -1) COUNTERPARTTYPE,\n " );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE COUNTERPARTTYPE ="+counterpartType+"\n" );
			bufSQL.append("AND STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
					//ID	NOT NULL	NUMBER
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
					info.setCounterpartType(localRS.getLong("COUNTERPARTTYPE"));    //���׶�������				
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
					list.add(info);
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return list;
		}
		/**
		 * ���ݽ��׶���IDȡ�ý��׶��ֿ�������Ϣ
		 * 
		 * */
		
		public Collection findByCounterpartTypeAndCounterpartId(long counterpartType,long counterpartId)throws ITreasuryDAOException{
			ArrayList list = new ArrayList();
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(COUNTERPARTTYPE, -1) COUNTERPARTTYPE,\n " );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE COUNTERPARTTYPE ="+counterpartType+"\n" );
			bufSQL.append("AND STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			if(counterpartId==-1){}
			else{
				bufSQL.append("AND COUNTERPARTID ="+counterpartId+"\n" );
			}
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
					//ID	NOT NULL	NUMBER
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
					info.setCounterpartType(localRS.getLong("COUNTERPARTTYPE"));    //���׶�������				
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
					list.add(info);
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return list;
		}
	    /**
	     * �����뵥����֤��֤ȯ�Ƿ�ʹ����
	     * @param securitiesId ֤ȯID
	     * @return int 0:δ��ʹ�ã���0:�Ѿ�ʹ��
	     * */
		public int isSecuritiesUsed(long securitiesId)throws ITreasuryDAOException {

			PreparedStatement localPS = null;
			ResultSet localRS = null;

			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			 bufSQL.append(" SELECT * FROM SEC_APPLYFORM \n");
			 bufSQL.append(" WHERE SECURITIESID = "+securitiesId+" \n");
	        
			 log.info(bufSQL.toString());
			 int count=0;
			try{
				
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while(localRS.next()){
				  count++;
				}
			
			}
			catch(Exception ex){
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",ex);
			}
			
			this.finalizeDAO();

			return count;
		}
		
		/**
		 * ���Ҳ���
		 * */
		public Collection find()throws ITreasuryDAOException{
			ArrayList list = new ArrayList();
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
					//ID	NOT NULL	NUMBER
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
//					info.setCounterpartType(localRS.getLong("COUNTERPARTTYPE"));    //���׶�������				
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
					list.add(info);
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return list;
		}
		/**
		 * ���ݽ��׶���IDȡ�ý��׶��ֿ�������Ϣ
		 * 
		 * */
		
		public Collection findByCounterpartId(long counterpartId)throws ITreasuryDAOException{
			ArrayList list = new ArrayList();
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			if(counterpartId==-1){}
			else{
				bufSQL.append("AND COUNTERPARTID ="+counterpartId+"\n" );
			}
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
					//ID	NOT NULL	NUMBER
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
//					info.setCounterpartType(localRS.getLong("COUNTERPARTTYPE"));    //���׶�������				
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
					list.add(info);
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return list;
		}
		
		/**
		 * ���ݽ��׶��������˺�ȡ�ý��׶��ֿ�������Ϣ
		 * 
		 * */
		
		public CounterPartBankAccountInfo findByBankAccountCode(String bankAccountCode)throws ITreasuryDAOException{
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			bufSQL.append("AND BANKACCOUNTCODE ='"+bankAccountCode+"' \n" );
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return info;
		}
		
		/**
		 * ���ݽ��׶��ֿ�����IDȡ�ý��׶��ֿ�������Ϣ
		 * 
		 * */
		
		public CounterPartBankAccountInfo findByBankId(long bankId)throws ITreasuryDAOException{
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			CounterPartBankAccountInfo info = new CounterPartBankAccountInfo();
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT NVL(ID,-1) ID, \n");
			bufSQL.append("NVL(COUNTERPARTID,-1) COUNTERPARTID, \n" );
			bufSQL.append("NVL(BANKNAME,'') BANKNAME, \n" );
			bufSQL.append("NVL(BANKACCOUNTCODE,'') BANKACCOUNTCODE, \n" );
			bufSQL.append("NVL(BANKACCOUNTNAME,'') BANKACCOUNTNAME \n " );
			bufSQL.append("FROM SEC_COUNTERPARTBANKACCOUNT \n" );
			bufSQL.append("WHERE STATUSID ="+SECConstant.SettingStatus.CHECKED+"\n" );
			bufSQL.append("AND ID ='"+bankId+"' \n" );
			log.info("SQL="+bufSQL.toString());
			try {
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					info.setId(localRS.getLong("ID"));						        //����,Ψһ����
				    info.setCounterpartId(localRS.getLong("COUNTERPARTID"));        //���׶���ID
					info.setBankName(localRS.getString("BANKNAME"));                //���׶��ֿ���������
					info.setBankAccountCode(localRS.getString("BANKACCOUNTCODE"));	//�������ʺ�
					info.setBankAccountName(localRS.getString("BANKACCOUNTNAME"));	//�������ʺ�����
				}
			} catch (Exception e) {
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();

			return info;
		}
		
		public boolean isRepeatBankAccountCode(CounterPartBankAccountInfo info)throws ITreasuryDAOException{
			boolean isrepeat =false;
			PreparedStatement localPS = null;
			ResultSet localRS = null;
			this.initDAO();
			StringBuffer bufSQL = new StringBuffer();	
			bufSQL.append("SELECT * from SEC_COUNTERPARTBANKACCOUNT where BANKACCOUNTCODE='"+info.getBankAccountCode()+"' and ID <>"+info.getId()+" and STATUSID <> 0 \n");
			try{
				localPS = transConn.prepareStatement(bufSQL.toString());
				localRS = localPS.executeQuery();
				while (localRS.next()) {
					isrepeat=true;
				}
			}catch(Exception e){
				new ITreasuryDAOException("���ݿ��ȡ֤ȯ�������ݲ����쳣",e);
			}
			this.finalizeDAO();
			
			return isrepeat;
		}


}

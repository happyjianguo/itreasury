/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.conversion.dataentity.DestinationAccountInfo;
import com.iss.itreasury.settlement.conversion.db.*;
import com.iss.itreasury.settlement.conversion.util.ConversionConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConversionAccount {

	/**
	 * 
	 */
	public ConversionAccount() {

	}
	
	/**
	 * ȡ��������з�������˻�
	 * @return
	 */
	public Vector getSourceAccount(){
		SourceConn sc = new SourceConn();
		Connection conn = sc.getConn();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.*,b.sname clientname "+"\n");
		sqlBuffer.append(" from sett_account a,client b "+"\n");
		sqlBuffer.append(" where a.ncurrencyid >1 "+"\n");
		sqlBuffer.append(" and a.nstatusid ="+SETTConstant.AccountStatus.NORMAL+"\n");
		sqlBuffer.append(" and ncheckstatusid ="+SETTConstant.AccountCheckStatus.CHECK+"\n");
		sqlBuffer.append(" and a.nclientid = b.id " +"\n");
		System.out.println(sqlBuffer.toString());
		Vector rnt = new Vector();
		AccountInfo  accountInfo =  null;
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			while(rs.next()){
				accountInfo = new AccountInfo();
				accountInfo.setAccountID(rs.getLong("ID"));
				accountInfo.setAccountNo(rs.getString("SACCOUNTNO")==null?"":rs.getString("SACCOUNTNO").trim());
				accountInfo.setAccountName(rs.getString("clientname")==null?"":rs.getString("clientname").trim());
				accountInfo.setOpenDate(rs.getTimestamp("DTOPEN"));
				accountInfo.setCurrencyID(rs.getLong("NCURRENCYID"));
				rnt.add(accountInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
		
		return rnt;
	}
	
	/**
	 * ȡ�Ѿ�������ܾ��ϱ�ϵͳ���˻�
	 * @return
	 */
	public Vector getDestinationAccout(){
		DestinationConn dc = new DestinationConn();
		Connection conn = dc.getConn();
		PreparedStatement pstat = null ;
		ResultSet rs = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from a_a01");
		sqlBuffer.append("");
		System.out.println(sqlBuffer.toString());
		DestinationAccountInfo dAccountInfo = null;
		Vector rnt = new Vector();
		try {
			pstat = conn.prepareStatement(sqlBuffer.toString());
			rs = pstat.executeQuery();
			while(rs.next()){
				dAccountInfo = new DestinationAccountInfo();
				
//				dAccountInfo.setAccount(rs.getString("ACCOUNT"));
				dAccountInfo.setAccount(rs.getString(2)==null?"":rs.getString(2).trim());
//				dAccountInfo.setChinese_name(rs.getString("CHINESE_NAME"));
				dAccountInfo.setChinese_name(rs.getString(5)==null?"":rs.getString(5).trim());
				//dAccountInfo.setCurrence_code(rs.getString("CURRENCE_CODE"));
                dAccountInfo.setCurrence_code(rs.getString(7)==null?"":rs.getString(7).trim());
				//dAccountInfo.setOpen_date(rs.getTimestamp("OPEN_DATE"));
                dAccountInfo.setOpen_date(rs.getTimestamp(8));
				rnt.add(dAccountInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(pstat!=null)
						pstat.close();
					if(conn!=null)
					  conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
		}
		return rnt;
	}
	
	/**
	 * �Ƚϣ��õ��µ��˻�
	 * @param source  Դ  
	 * @param destination  Ŀ��
	 * @return
	 */
	public Vector getNewAccount(Vector source ,Vector destination){
		Vector rnt = new Vector();
		AccountInfo  accountInfo =  null;
		DestinationAccountInfo dAccountInfo = null;
		boolean isEx = true;
		if(source!=null){
			
				for(int i=0;i<source.size();i++){
					isEx = true;
					accountInfo = (AccountInfo)source.get(i);
					if(destination!=null){
						for(int j=0;j<destination.size();j++){
							dAccountInfo = (DestinationAccountInfo)destination.get(j);
							if(equalsSourceAndDestination(accountInfo,dAccountInfo)){
								isEx = false;
								break;
							}
						}
					}else{
					   rnt.add(this.getDestinationAccountInfo(accountInfo));
					}
					
					if(isEx){
						rnt.add(this.getDestinationAccountInfo(accountInfo));
					}
				}
			
		}
		return rnt;
	}
	
	/**
	 * �����˻�д���ϱ�ϵͳ��a_a01��
	 * @param newAccount
	 */
	public void startConversion(Vector newAccount){

		
	}
	
	
	public long insertDestinationAccount(Vector newAccount){
		long rnt=0;
		DestinationConn dc = new DestinationConn();
		Connection conn = dc.getConn();
		
		PreparedStatement pstat = null ;
		DestinationAccountInfo dAccountInfo = null;
		String insertSql = "insert into a_a01 (BANK_CODE," +"\n"+           //�������ź�
				                                "ACCOUNT," +"\n"+           //����˻��˺�
				                                "BANK_NAME," +"\n"+         //���л�������
												"EN_CODE,"+//������˾��λ����
				                                "CHINESE_NAME," +"\n"+      //��λ��������
												"ACCOUNT_TYPE,"+//����˻�������
				                                "CURRENCE_CODE," +"\n"+     //�˻�����
												"OPEN_DATE," +"\n"+         //��������
												"FILE_NUMBER,"+"\n"+        //�����������׼�����
				                                "TLRNO," +//¼���Ա
				                                "SUCCESSED) values(" +"\n"+ //¼���ʶ Ĭ��Ϊ0
				                                "";
		String strBankCode = "00000"; 
		String strBankName = "�������Ų���˾";
		
		try {
			conn.setAutoCommit(false);
		   if(newAccount!=null){
			  for(int i=0;i<newAccount.size();i++){
				  dAccountInfo = (DestinationAccountInfo)newAccount.get(i);
				  if(dAccountInfo!=null){
				  	String tmpinsertSql = insertSql+
				  	                      "'"+strBankCode+"',"+                  //�������ź�
										  "'"+dAccountInfo.getAccount()+"',"+    //����˻��˺�
										  "'"+strBankName+"',"+                  //���л�������
										  "'"+" "+"',"+                           //������˾��λ����
										  "'"+dAccountInfo.getChinese_name()+"',"+ //��λ��������
										  "'"+" "+"',"+                             //����˻�������
										  "'"+dAccountInfo.getCurrence_code()+"',"+ //�˻�����
										  //"to_date('"+DataFormat.getDateString(dAccountInfo.getOpen_date())+"','yyyy-mm-dd'),"+  //��������
										  //"'"+DataFormat.getDateString(dAccountInfo.getOpen_date())+"',"+
										  "?,"+
										  "'"+""+"',"+                                //�����������׼�����
										  "'"+""+"',"+                                //¼���Ա
										  "'"+"0"+"')"+                              //¼���ʶ Ĭ��Ϊ0        
										  "";
						pstat = conn.prepareStatement(tmpinsertSql);
						pstat.setDate(1,DataFormat.getDate(DataFormat.getDateString(dAccountInfo.getOpen_date())));
						System.out.println(tmpinsertSql);
						pstat.executeUpdate();
				     }
			   }
			}
		   conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("����ع�");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		} finally{
			
			try{
				if(pstat!=null)
					pstat.close();
				if(conn!=null)
				  conn.close();
			}catch(Exception e){
				
			}
		}
		return rnt;
	}

	public long insertDestinationAccount(Vector newAccount,Connection conn) throws SQLException{
		long rnt=0;

		PreparedStatement pstat = null ;
		DestinationAccountInfo dAccountInfo = null;
		String insertSql = "insert into a_a01 (BANK_CODE," +"\n"+           //�������ź�
				                                "ACCOUNT," +"\n"+           //����˻��˺�
				                                "BANK_NAME," +"\n"+         //���л�������
												"EN_CODE,"+//������˾��λ����
				                                "CHINESE_NAME," +"\n"+      //��λ��������
												"ACCOUNT_TYPE,"+//����˻�������
				                                "CURRENCE_CODE," +"\n"+     //�˻�����
												"OPEN_DATE," +"\n"+         //��������
												"FILE_NUMBER,"+"\n"+        //�����������׼�����
				                                "TLRNO," +//¼���Ա
				                                "SUCCESSED) values(" +"\n"+ //¼���ʶ Ĭ��Ϊ0
				                                "";
		String strBankCode = "00000"; 
		String strBankName = "�������Ų���˾";

		   if(newAccount!=null){
			  for(int i=0;i<newAccount.size();i++){
				  dAccountInfo = (DestinationAccountInfo)newAccount.get(i);
				  if(dAccountInfo!=null){
				  	String tmpinsertSql = insertSql+
				  	                      "'"+strBankCode+"',"+                  //�������ź�
										  "'"+dAccountInfo.getAccount()+"',"+    //����˻��˺�
										  "'"+strBankName+"',"+                  //���л�������
										  "'"+" "+"',"+                           //������˾��λ����
										  "'"+dAccountInfo.getChinese_name()+"',"+ //��λ��������
										  "'"+" "+"',"+                             //����˻�������
										  "'"+dAccountInfo.getCurrence_code()+"',"+ //�˻�����
										  //"to_date('"+DataFormat.getDateString(dAccountInfo.getOpen_date())+"','yyyy-mm-dd'),"+  //��������
										  //"'"+DataFormat.getDateString(dAccountInfo.getOpen_date())+"',"+
										  "?,"+
										  "'"+""+"',"+                                //�����������׼�����
										  "'"+""+"',"+                                //¼���Ա
										  "'"+"0"+"')"+                              //¼���ʶ Ĭ��Ϊ0        
										  "";
						pstat = conn.prepareStatement(tmpinsertSql);
				  	    //pstat = conn.createStatement();
						//pstat.setTimestamp(1,dAccountInfo.getOpen_date());
						pstat.setDate(1,DataFormat.getDate(DataFormat.getDateString(dAccountInfo.getOpen_date())));
				  	    //pstat.execute(tmpinsertSql);
						System.out.println(tmpinsertSql);
						pstat.execute();
						
				     }
			   }
			}
		return rnt;
	}
	public  DestinationAccountInfo getDestinationAccountInfo(AccountInfo  accountInfo){
		DestinationAccountInfo dAccountInfo = null;
		if(accountInfo!=null){
			dAccountInfo = new DestinationAccountInfo();
			dAccountInfo.setAccount(accountInfo.getAccountNo());
			dAccountInfo.setChinese_name(accountInfo.getAccountName());
			dAccountInfo.setCurrence_code(ConversionConstant.Currency.getNameByID(accountInfo.getCurrencyID()));
			dAccountInfo.setOpen_date(accountInfo.getOpenDate());
		}
		return dAccountInfo;
	}
	
	private boolean equalsSourceAndDestination(AccountInfo  accountInfo,DestinationAccountInfo dAccountInfo){
		boolean rnt = false;
		if(accountInfo==null || dAccountInfo==null){
			System.out.println("AccountInfo or DestinationAccountInfo is null");
		}else{
			if(dAccountInfo.getAccount().trim().equals(accountInfo.getAccountNo())){
				//System.out.println(dAccountInfo.getAccount().trim()+":"+accountInfo.getAccountNo()+":true");
				rnt = true;
			}
		}
		return rnt;
	}
	
	public static void main(String[] args) {
		
		ConversionAccount ca = new ConversionAccount();
		Vector dv = ca.getDestinationAccout();
		DestinationAccountInfo dAccountInfo = null;
		if(dv==null){
			
		}else{
			for(int i =0;i<dv.size();i++){
				dAccountInfo = (DestinationAccountInfo)dv.get(i);
				System.out.println("============:"+(i+1));
				System.out.println(dAccountInfo.getAccount());
				System.out.println(dAccountInfo.getChinese_name());
				System.out.println(dAccountInfo.getCurrence_code());
				System.out.println(dAccountInfo.getOpen_date());
			}
		}
	/*
		Vector v = new Vector();
		DestinationAccountInfo dAccountInfo = new DestinationAccountInfo();
		dAccountInfo.setAccount("01-01-1111-2");
		dAccountInfo.setBank_code("0000");
		dAccountInfo.setChinese_name("����ʲô����ѽ������");

			try {
				dAccountInfo.setOpen_date(DataFormat.getDateTime("2004-12-30"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dAccountInfo.setCurrence_code("USD");

		dAccountInfo.setSuccessed("0");
		v.add(dAccountInfo);
		ca.insertDestinationAccount(v);
		*/
		Vector sv = ca.getSourceAccount();
		AccountInfo  accountInfo =  null;
		if(sv==null){
			
		}else{
			for(int i=0;i<sv.size();i++){
				accountInfo = (AccountInfo)sv.get(i);
				dAccountInfo = ca.getDestinationAccountInfo(accountInfo);
				System.out.println("============:"+(i+1));
				System.out.println(dAccountInfo.getAccount());
				System.out.println(dAccountInfo.getChinese_name());
				System.out.println(dAccountInfo.getCurrence_code());
				System.out.println(dAccountInfo.getOpen_date());
			}
		}
		System.out.println("dddddddddddddd");
		Vector vNew = ca.getNewAccount(sv,dv);
		for(int i =0;i<vNew.size();i++){
			dAccountInfo = (DestinationAccountInfo)vNew.get(i);
			System.out.println("============:"+(i+1));
			System.out.println(dAccountInfo.getAccount());
			System.out.println(dAccountInfo.getChinese_name());
			System.out.println(dAccountInfo.getCurrence_code());
			System.out.println(dAccountInfo.getOpen_date());
		}
		ca.insertDestinationAccount(ca.getNewAccount(sv,dv));
}//
	
}

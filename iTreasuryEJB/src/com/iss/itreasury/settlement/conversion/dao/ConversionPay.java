/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.conversion.dataentity.DestinationReceivePayInfo;
import com.iss.itreasury.settlement.conversion.db.DestinationConn;
import com.iss.itreasury.settlement.conversion.db.SourceConn;
import com.iss.itreasury.settlement.conversion.util.ConversionConstant;
import com.iss.itreasury.settlement.conversion.util.ConversionUtil;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConversionPay {
	

	/**
	 * 
	 */
	public ConversionPay() {

	}
	
	public Vector getNewPay(){
		Vector rnt = new Vector();
		Timestamp currenDate = ConversionUtil.getCurrentDate();
		if(currenDate!=null){
			SourceConn sc = new SourceConn();
			Connection conn = sc.getConn();
			PreparedStatement pstat = null ;
			ResultSet rs = null;
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select a.ncurrencyid,a.ntransactiontypeid,a.dtexecute,a.mamount,d.sname clientname   , b.saccountno"+"\n");
			sqlBuffer.append(" from sett_transaccountdetail a,"+"\n");
			sqlBuffer.append(" sett_account b,"+"\n");
			sqlBuffer.append(" sett_subaccount c ,"+"\n");
			sqlBuffer.append("  client d "+"\n");
			sqlBuffer.append(" where  a.nstatusid>0 "+"\n");
			sqlBuffer.append(" and a.ntransaccountid=b.id "+"\n");
			sqlBuffer.append(" and a.nsubaccountid=c.id " +"\n");
			sqlBuffer.append(" and c.naccountid = b.id "+"\n");
			sqlBuffer.append(" and  b.nclientid = d.id "+"\n");
			sqlBuffer.append(" and a.ntransactiontypeid in ("+ConversionUtil.getStrPayTransactionType()+") \n");
			sqlBuffer.append(" and a.ncurrencyid >1"+"\n");
			sqlBuffer.append(" and a.dtexecute = to_date('"
					                  +DataFormat.formatDate(currenDate )
								           +"','yyyy-mm-dd')");		
	
			DestinationReceivePayInfo drpInfo = null;
			System.out.println(sqlBuffer.toString());
			
			//AccountInfo  accountInfo =  null;
			try {
				pstat = conn.prepareStatement(sqlBuffer.toString());
				rs = pstat.executeQuery();
				int i=1;
				while(rs.next()){
					drpInfo = new DestinationReceivePayInfo();
					drpInfo.setAccount(rs.getString("saccountno"));
					drpInfo.setAmount(rs.getDouble("mamount"));
					drpInfo.setCurrence_code(ConversionConstant.Currency.getNameByID(rs.getLong("ncurrencyid")));
					drpInfo.setDeal_date(rs.getTimestamp("dtexecute"));
					drpInfo.setRef_number(ConversionUtil.getREF(rs.getTimestamp("dtexecute"))+"1"+String.valueOf(i));
					drpInfo.setEn_name(rs.getString("clientname"));
					drpInfo.setPaymethod(ConversionUtil.getTransactCode(rs.getLong("ntransactiontypeid")));
					i++;
					rnt.add(drpInfo);
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
		}
		return rnt;
	}

	
	
	/**
	 * 
	 * @param receive
	 * @return
	 */
	public long insertPay(Vector receive){
		long rnt=0;
		DestinationConn dc = new DestinationConn();
		Connection conn = dc.getConn();
		
		PreparedStatement pstat = null ;
		DestinationReceivePayInfo drpInfo = null;
		String insertSql = "insert into a_a04 (BANK_CODE," +"\n"+           //�������ź�
		                                        "REF_NUMBER,"+"\n"+         //ҵ��ο��ţ�����Ψһ��
												"DEAL_DATE,"+"\n"+          //��������
												"EN_NAME,"+ "\n"+           //�տ�������
												"EN_CODE,"+
				                                "ACCOUNT," +"\n"+           //����˻��˺�
				                                "CURRENCE_CODE," +"\n"+     //�˻�����
												"AMOUNT," + "\n"+           //���
												"PAYMETHOD,"+"\n"+           //���㷽ʽ
												"TRANSACT_CODE,"+
												"AUTHORITY_CODE,"+
												"AP_NUMBER,"+
												"COUNTRY_CODE,"+
				                                "SUCCESSED) values(" +"\n"+ //¼���ʶ Ĭ��Ϊ0
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?," +"\n"+
				                                "?)" +"\n"+
				                                "";
		String strBankCode = "00000"; 
		//String strBankName = "�������Ų���˾";
		
		try {
			conn.setAutoCommit(false);
		   if(receive!=null){
			  for(int i=0;i<receive.size();i++){
			  	drpInfo = (DestinationReceivePayInfo)receive.get(i);
				  if(drpInfo!=null){
						pstat = conn.prepareStatement(insertSql);
						pstat.setString(1,strBankCode);                 //�������ź�
						System.out.println();
						pstat.setString(2,drpInfo.getRef_number());     //ҵ��ο��ţ�����Ψһ��
						//pstat.setTimestamp(3,drpInfo.getDeal_date());   //��������
						pstat.setDate(3,DataFormat.getDate(DataFormat.getDateString(drpInfo.getDeal_date())));
						System.out.println(DataFormat.getDate(DataFormat.getDateString(drpInfo.getDeal_date())));
						pstat.setString(4,drpInfo.getEn_name());        //�տ�������
						System.out.println(drpInfo.getEn_name());
						pstat.setString(5," ");
						pstat.setString(6,drpInfo.getAccount());        //����˻��˺�
						System.out.println(drpInfo.getAccount());
						pstat.setString(7,drpInfo.getCurrence_code());  //�˻�����
						System.out.println(drpInfo.getCurrence_code());
						pstat.setDouble(8,drpInfo.getAmount());         //���
						System.out.println(drpInfo.getAmount());
						pstat.setString(9,drpInfo.getPaymethod());  //���㷽ʽ
						System.out.println(drpInfo.getPaymethod());
						pstat.setString(10," ");
						pstat.setString(11," ");
						pstat.setString(12," ");
						pstat.setString(13," ");
						pstat.setString(14,"0");                         //¼���ʶ Ĭ��Ϊ0
						System.out.println(drpInfo.getRef_number());
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
	

	/**
	 * 
	 * @param receive
	 * @return
	 * @throws SQLException
	 */
	public long insertPay(Vector receive,Connection conn) throws SQLException{
		long rnt=0;
		
		PreparedStatement pstat = null ;
		DestinationReceivePayInfo drpInfo = null;
		String insertSql = "insert into a_a04 (BANK_CODE," +"\n"+           //�������ź�
										        "REF_NUMBER,"+"\n"+         //ҵ��ο��ţ�����Ψһ��
												"DEAL_DATE,"+"\n"+          //��������
												"EN_NAME,"+ "\n"+           //�տ�������
												"EN_CODE,"+
										        "ACCOUNT," +"\n"+           //����˻��˺�
										        "CURRENCE_CODE," +"\n"+     //�˻�����
												"AMOUNT," + "\n"+           //���
												"PAYMETHOD,"+"\n"+           //���㷽ʽ
												"TRANSACT_CODE,"+
												"AUTHORITY_CODE,"+
												"AP_NUMBER,"+
												"COUNTRY_CODE,"+
										        "SUCCESSED) values(" +"\n"+ //¼���ʶ Ĭ��Ϊ0
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?," +"\n"+
										        "?)" +"\n"+
										        "";
		String strBankCode = "00000"; 

		   if(receive!=null){
			  for(int i=0;i<receive.size();i++){
			  	drpInfo = (DestinationReceivePayInfo)receive.get(i);
				  if(drpInfo!=null){
						pstat = conn.prepareStatement(insertSql);
						pstat.setString(1,strBankCode);                 //�������ź�
						pstat.setString(2,drpInfo.getRef_number());     //ҵ��ο��ţ�����Ψһ��
						//pstat.setTimestamp(3,drpInfo.getDeal_date());   //��������
						pstat.setDate(3,DataFormat.getDate(DataFormat.getDateString(drpInfo.getDeal_date())));
						pstat.setString(4,drpInfo.getEn_name());        //�տ�������
						pstat.setString(5," ");
						pstat.setString(6,drpInfo.getAccount());        //����˻��˺�
						pstat.setString(7,drpInfo.getCurrence_code());  //�˻�����
						pstat.setDouble(8,drpInfo.getAmount());         //���
						pstat.setString(9,drpInfo.getPaymethod());      //���㷽ʽ
						pstat.setString(10," "); 
						pstat.setString(11," ");
						pstat.setString(12," ");
						pstat.setString(13," ");
						pstat.setString(14,"0");                         //¼���ʶ Ĭ��Ϊ0
						pstat.executeUpdate();
				     }
			   }
			}

		return rnt;
	}

	
	
	public static void main(String[] args) {
		DestinationReceivePayInfo drpInfo = new DestinationReceivePayInfo();
		
		ConversionPay cp = new ConversionPay();
		Vector newPay = cp.getNewPay();
		cp.insertPay(newPay);
		
	}
}

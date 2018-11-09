/*
 * Created on 2004-5-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.mywork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.mywork.dataentity.ColumnNode;
import com.iss.itreasury.securities.mywork.dataentity.MyWorkColumn;
import com.iss.itreasury.securities.mywork.dataentity.TransactionType;
import com.iss.itreasury.securities.mywork.dataentity.TransactionURL;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.system.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_MyWorkDAO extends SecuritiesDAO{

	
	/**
	 * �ҵĹ�������
	 */
	public final static int SEC_APPLYFORM 		= 1;				//������
	public final static int SEC_DELIVERORDER 	= 2;				//���
	public final static int SEC_CONTRACT 		= 3;				//��ͬ
	public final static int SEC_PLAN 			= 4;				//�ƻ�
	public final static int SEC_NOTICE			= 5;				//֪ͨ��
	
	
	public SEC_MyWorkDAO(){
		super("SEC_MyWorkDAO");
	}
	
	/**
	 * �õ��������͵Ŀ����Ϣ
	 * @param workTypeId
	 * @param nodeStatusId
	 * @param actionTypeId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public MyWorkColumn getBusinessType(long workTypeId,long nodeStatusId,long actionTypeId)throws SecuritiesDAOException{
		MyWorkColumn columns = new MyWorkColumn(workTypeId,nodeStatusId,actionTypeId);
		try{
			initDAO();
			
			String strSql = "select code nodeId,name nodeName,statusid nodeStatusId from sec_businessType where id <> 6 order by id";
			
			prepareStatement(strSql);
			ResultSet rs = this.executeQuery();
			
			while (rs.next()){
				
				columns.add(rs.getLong("nodeId"),						//���һ���½ڵ�
										rs.getString("nodeName"),
										nodeStatusId,
										0,
										"");							//����½ڵ�
				
				columns.upToParent();						//�ص����ڵ�
			}
			
			finalizeDAO();
		}catch(SQLException sqlE){
			throw new SecuritiesDAOException("�����ݿ��в����ҵĹ�����Ϣʱ�������ݿ����",sqlE);
		}
		catch(Exception e){
			throw new SecuritiesDAOException("�����ݿ��в����ҵĹ�����Ϣʱ������������",e);
		}
		
		return columns;
	}
	

	/**
	 * �õ�ĳһ��"�ҵĹ���"����Ϣ
	 * @param userId			�û�ID
	 * @param currencyId		����ID
	 * @param workTypeId		�ҵĹ�������ID
	 * @param statusId			״̬ID
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public MyWorkColumn getSingleWork(long userId,long currencyId,long officeId,int workTypeId,long statusId,long actionTypeId)throws SecuritiesDAOException{
		MyWorkColumn column = new MyWorkColumn(workTypeId,statusId,actionTypeId);

		TransactionURL transURL = new TransactionURL();
		
		ArrayList alTmp = new ArrayList();
		
		boolean isSpecialType = false;					//�������ͱ�־,��Ϊ�����֪ͨ��Ȩ��ת������,������Ӵ˱�־
		
		try{
			initDAO();
			ApprovalBiz appBiz = new ApprovalBiz();
			
			long[] transIds = null;
			
			String strSql = "";
			
			boolean needCurrency = true;		//�Ƿ���Ҫ���ֲ�ѯ����
			
			/**
			 * ���չ���������ȷ��SQL
			 */
			switch (workTypeId){
				case SEC_APPLYFORM:{				//������
					
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyform trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"and trans.nextCheckLevel = 1" + " \n" +
						
						//������9�ִ���
						" and trans.transactionTypeId in " +
						" ( " +

						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM+", " +
						" "+SECConstant.BusinessType.FUND_TRANSFER.FUND_TRANSFER+", " +
						
						" "+SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY+" " +
						
						" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					/**
					 * ��������
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						//transIds = TransactionType.getTransactionTypeCode(SEC_APPLYFORM);
						
						//������9�ִ���
						transIds = new long[40];
						transIds[0] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID;  //����Ʊ���깺
						transIds[1] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN;  //����Ʊ������
						transIds[2] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL;  //����Ʊ������
						transIds[3] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE;  //���ع�
						transIds[4] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE;  //��ع�
						transIds[5] = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.FUND_REPURCHASE;  //���ʻع�
						transIds[6] = SECConstant.BusinessType.EXCHANGECENTER_BOND_REPURCHASE.BOND_REPURCHASE;  //��ȯ�ع�
						transIds[7] = SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID;  //���м��ծ�깺
						transIds[8] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //���м��ծ����
						transIds[9] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //���м��ծ����
						transIds[10] = SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID;  //��������ծ�깺
						transIds[11] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //��������ծ����
						transIds[12] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //��������ծ����
						transIds[13] = SECConstant.BusinessType.ENCLOSED_FUND_BID_ONLINE.FUND_BID_ONLINE;  //���ʽ���������깺
						transIds[14] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;  //���ʽ���������깺
						transIds[15] = SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_BUYIN;  //���ʽ��������
						transIds[16] = SECConstant.BusinessType.ENCLOSED_FUND_TRANSACTION.FUND_SELL;  //���ʽ��������
						transIds[17] = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;  //����ʽ����һ���Ϲ�
						transIds[18] = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;  //����ʽ��������깺
						transIds[19] = SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM;  //����ʽ����������
						transIds[20] = SECConstant.BusinessType.FUND_TRANSFER.FUND_TRANSFER;  //����ת��

						transIds[21] = SECConstant.BusinessType.STOCK_BID_ONLINE.INITIAL_OFFERING_BID_ONLINE;  //��Ʊ�׷������깺
						transIds[22] = SECConstant.BusinessType.STOCK_BID_ONLINE.PROMOTION_BID_ONLINE;  //��Ʊ���������깺
						transIds[23] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;  //��Ʊ�׷������깺
						transIds[24] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;  //��Ʊ���������깺
						transIds[25] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_BUYIN;  //��Ʊ����
						transIds[26] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_SELL;  //��Ʊ����
						transIds[27] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION_ONLINE;  //�������
						transIds[28] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION;  //�������
						transIds[29] = SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID;  //����ծ�깺
						transIds[30] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN;  //����ծ����
						transIds[31] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL;  //����ծ����
						transIds[32] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID; //��ҵծ�깺
						transIds[33] = SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_BUYIN;  //��ҵծ����
						transIds[34] = SECConstant.BusinessType.ENTERPRISE_BOND_TRANSACTION.BOND_SELL;  //��ҵծ����
						transIds[35] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID_ONLINE.BOND_BID_ONLINE;  //��תծ�����깺
						transIds[36] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;  //��תծ�����깺
						transIds[37] = SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_BUYIN;  //��תծ����
						transIds[38] = SECConstant.BusinessType.TRANSFORMABLE_BOND_TRANSACTION.BOND_SELL;  //��תծ����
						transIds[39] = SECConstant.BusinessType.DEBT_TO_EQUITY.DEBT_TO_EQUITY;  //ծת��
						
						long lApprovalID[] = null;  //����ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++)  //ѭ������ÿһ�ֽ���
						{
							//ת�����Ͳ�ѯ����������
							long ActionID = 1;
							long lSubLoanTypeID = -1;
							long lActionID = -1;
							if ( ActionID == 1 )
							{
								long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transIds[n]);  //����
								lSubLoanTypeID=result[0];
								lActionID=result[1];
							}
							
							strSql=" select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
							
							strSql += " ( SELECT c.*,-1 moneysegment,-1 approvalid from SEC_ApplyForm c";
							strSql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
							strSql += " where (a.NNEXTUSERID="+userId+" ";
							strSql += " and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
							strSql += "  and a.NMODULEID="+Constant.ModuleType.SECURITIES+" and nstatusid="+Constant.RecordStatus.VALID+""; 
							strSql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
							strSql += " where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.ApplyFormStatus.APPROVALING+"";
							strSql += " and c.transactiontypeid = " + transIds[n];
						    
						    strSql += " ) union ( ";
						    
							strSql += " select d.* from (";
							strSql += " select aaa.* from (";
							strSql += " select aa.*,rr.moneysegment,rr.approvalid from SEC_ApplyForm aa,loan_approvalrelation rr";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " ) aaa,(";
							strSql += " select aa.id,max(rr.moneysegment) maxamount from SEC_ApplyForm aa,loan_approvalrelation rr";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and aa.amount>rr.moneysegment and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.ApplyFormStatus.SUBMITED;
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " group by aa.id ) bbb";
							strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
							strSql += " loan_approvalsetting e,loan_approvalitem f";
							strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
							strSql += " ) ";
							
							strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
							strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
							
							System.out.println("������˲�ѯSQL^^^^^^^^^^^===="+strSql);
							
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;

								if (rs.next()){
									node = new ColumnNode(null);
	
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("���ף�"+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//��������
	
									alTmp.add(node);
								}
						}
					}
					break;
				}
				case SEC_DELIVERORDER:{				//���
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_deliveryOrder trans,sec_transactionType tra \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and trans.currencyId= " + currencyId + " \n" +
						"and trans.inputUserId = " + userId + " \n" +
						"and trans.ISRELATEDBYNOTICEFORM <> " + SECConstant.TRUE + " \n" +
						
						//�������ѯ"�ʽ���"
						" and trans.transactionTypeId not in ( "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_IN_REPAY+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT+", "+SECConstant.BusinessType.CAPITAL_LANDING.CAPITAL_OUT_REPAY+" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_deliveryOrder trans,sec_transactionType tra \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and trans.currencyId= " + currencyId + " \n" +
						"and trans.inputUserId <> " + userId + " \n" +
						"and trans.ISRELATEDBYNOTICEFORM <> " + SECConstant.TRUE + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					break;
				}
				case SEC_NOTICE:{					//֪ͨ��
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						//��Ϊ֪ͨ��û�б����ֶΣ�����Ҫ������صĽ����
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_noticeForm trans,sec_transactionType tra,sec_deliveryOrder delivery \n" +
						"where trans.transactionTypeId = tra.id \n" +
						"and trans.deliveryOrderId = delivery.id \n" +
						"and trans.statusId= " + statusId + " \n" +
						"and (delivery.currencyId= " + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId = " + userId + " \n" +
						"and trans.NextCheckLevel = 1  \n " + 
						
						//ҵ��֪ͨ��ֻ��5�ִ���
						" and trans.transactionTypeId in " +
						" ( " +

						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE+", " +
						" "+SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID+", " +
						" "+SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM+", " +
						
						//�ʽ𻮲�
						" "+SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN+", " +
						" "+SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT+", " +
						
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN+", " +
						" "+SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY+", " +
						" "+SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY+", " +
						" "+SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY+", " +
						
						" "+SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING+" " +
						
						" ) " +
						
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId ";
					}
					/**
					 * ��������
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_NOTICE);
						
						transIds = new long[42];
						transIds[0] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_BID.NOTE_BID;  //����Ʊ���깺
						transIds[1] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_BUYIN;  //����Ʊ������
						transIds[2] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_SELL;  //����Ʊ������
						transIds[3] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_ACCRUAL_IN;  //��Ϣ
						transIds[4] = SECConstant.BusinessType.CENTRAL_BANK_NOTE_TRANSACTION.NOTE_MATURITY_REPAY;  //���ڻ���
						transIds[5] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPURCHASE;  //���ع�
						transIds[6] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.FUND_REPAY;  //���ع�����
						transIds[7] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPURCHASE;  //��ع�
						transIds[8] = SECConstant.BusinessType.BANK_BOND_REPURCHASE.BOND_REPAY;  //��ع�����
						transIds[9] = SECConstant.BusinessType.BANK_NATIONAL_BOND_BID.BOND_BID;  //���м��ծ�깺
						transIds[10] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_BUYIN;  //���м��ծ����
						transIds[11] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_SELL;  //���м��ծ����
						transIds[12] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_ACCRUAL_IN;  //��Ϣ
						transIds[13] = SECConstant.BusinessType.BANK_NATIONAL_BOND_TRANSACTION.BOND_MATURITY_REPAY;  //���ڻ���
						transIds[14] = SECConstant.BusinessType.EXCHANGECENTER_NATIONAL_BOND_BID.BOND_BID;  //��������ծ�깺
						transIds[15] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID;  //���ʽ���������깺
						transIds[16] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_SUPPLY;  //���ʽ�����깺����
						transIds[17] = SECConstant.BusinessType.ENCLOSED_FUND_BID.FUND_BID_MARGIN_REPAY;  //���ʽ�����깺����
						transIds[18] = SECConstant.BusinessType.MUTUAL_FUND_SUBSCRIBE.FUND_SUBSCRIBE;  //����ʽ����һ���Ϲ�
						transIds[19] = SECConstant.BusinessType.MUTUAL_FUND_BID.FUND_BID;  //����ʽ��������깺
						transIds[20] = SECConstant.BusinessType.MUTUAL_FUND_REDEEM.FUND_REDEEM;  //����ʽ����������
						transIds[21] = SECConstant.BusinessType.MUTUAL_FUND_MELON_CUTTING.FUND_CASH_MELON_CUTTING;  //����ʽ�����ֽ�ֺ�
						transIds[22] = SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN;  //�ʽ����
						transIds[23] = SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT;  //�ʽ�ȡ��
						
						transIds[24] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_BID;  //��Ʊ�׷������깺
						transIds[25] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_SUPPLY;  //��Ʊ�׷�����
						transIds[26] = SECConstant.BusinessType.STOCK_BID.INITIAL_OFFERING_MARGIN_REPAY;  //��Ʊ�׷�����
						transIds[27] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID;  //��Ʊ���������깺
						transIds[28] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_SUPPLY;  //��Ʊ��������
						transIds[29] = SECConstant.BusinessType.STOCK_BID.PROMOTION_BID_MARGIN_REPAY;  //��Ʊ��������
						transIds[30] = SECConstant.BusinessType.STOCK_TRANSACTION.STOCK_RATION;  //�������
						transIds[31] = SECConstant.BusinessType.FINANCIAL_BOND_BID.BOND_BID;  //����ծ�깺
						transIds[32] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_BUYIN;  //����ծ����
						transIds[33] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_SELL;  //����ծ����
						transIds[34] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_ACCRUAL_IN;  //��Ϣ
						transIds[35] = SECConstant.BusinessType.FINANCIAL_BOND_TRANSACTION.BOND_MATURITY_REPAY;  //���ڻ��� 
						transIds[36] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID;  //��ҵծ�깺
						transIds[37] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_SUPPLY;  //��ҵծ�깺����
						transIds[38] = SECConstant.BusinessType.ENTERPRISE_BOND_BID.BOND_BID_MARGIN_REPAY;  //��ҵծ�깺����  
						transIds[39] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID;  //��תծ�����깺
						transIds[40] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_SUPPAY;  //��תծ�깺����
						transIds[41] = SECConstant.BusinessType.TRANSFORMABLE_BOND_BID.BOND_BID_MARGIN_REPAY;  //��תծ�깺����
						
						long lApprovalID[] = null;  //����ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//ѭ������ÿһ�ֽ���
							//ת�����Ͳ�ѯ����������
							long ActionID = 2;
							long lSubLoanTypeID = -1;
							long lActionID = -1;
							if ( ActionID == 2 )
							{
								long[] result=NameRef.getLoantypidAndActionidByTransactionid(ActionID,transIds[n]);  //ҵ��
								lSubLoanTypeID=result[0];
								lActionID=result[1];
							}
							strSql  = " select count(ss1.transactionTypeId) dataCount,ss1.transactionTypeId nodeId,avg(ss1.statusId) nodeStatusId,tra.name nodeName from ( ";
							
							strSql += " ( SELECT c.*,-1 moneysegment,-1 approvalid ";
							strSql += " from SEC_NoticeForm c ";
							strSql += " ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
							strSql += " where (a.NNEXTUSERID="+userId+" ";
							strSql += " and a.nactionid="+lActionID+" and a.nloantypeid = "+lSubLoanTypeID+" ";
							strSql += "  and a.NMODULEID="+Constant.ModuleType.SECURITIES+" and nstatusid="+Constant.RecordStatus.VALID+""; 
							strSql += " and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where c.id =d.NAPPROVALCONTENTID and c.statusid="+SECConstant.NoticeFormStatus.APPROVALING+"";
							strSql += " and c.DeliveryOrderId = bb.id ";
							strSql += " and c.transactiontypeid = " + transIds[n];

						    strSql += " ) union ( ";
						    
							strSql += " select d.* from ( ";
							strSql += " select aaa.* ";
							strSql += " from ( ";
							strSql += " select aa.*,rr.moneysegment,rr.approvalid ";
							strSql += " from sec_noticeform aa,loan_approvalrelation rr ";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
							
							//������ʽ𻮲�ҵ���ѯ����ֶ�
							if ( transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
							{
								strSql += " and bb.netincome>rr.moneysegment ";
							}
							else 
							{
								strSql += " and bb.amount>rr.moneysegment ";
							}
							
							strSql += " and aa.DeliveryOrderId = bb.id ";
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " ) aaa,(";
							strSql += " select aa.id,max(rr.moneysegment) maxamount from sec_noticeform aa,loan_approvalrelation rr";
							strSql += " ,sec_DeliveryOrder bb ";
							strSql += " where rr.moduleid="+Constant.ModuleType.SECURITIES+" and rr.officeid = "+officeId+" and rr.currencyid =" +currencyId+ " and aa.statusid="+SECConstant.NoticeFormStatus.SUBMITED;
							
							//������ʽ𻮲�ҵ���ѯ����ֶ�
							if ( transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_IN || transIds[n] == SECConstant.BusinessType.CAPITAL_TRANSFER.CAPITAL_OUT )
							{
								strSql += " and bb.netincome>rr.moneysegment ";
							}
							else 
							{
								strSql += " and bb.amount>rr.moneysegment ";
							}
							
							strSql += " and aa.DeliveryOrderId = bb.id ";
							strSql += " and rr.actionid = " + lActionID ;
							strSql += " and rr.subloantypeid = " + lSubLoanTypeID ;
							strSql += " and aa.transactiontypeid = " + transIds[n];
							strSql += " group by aa.id ) bbb";
							strSql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
							strSql += " loan_approvalsetting e,loan_approvalitem f";
							strSql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userId;
							strSql += " ) ";
							
							strSql += " ) ss1,sec_transactionType tra where ss1.transactionTypeId = tra.id ";
							strSql +=" group by ss1.transactionTypeId,tra.name order by ss1.transactionTypeId ";
							
							System.out.println("��ѯ���SQL^^^^^^^^^^^===="+strSql);
							
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("���ף�"+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//��������
									
									alTmp.add(node);
								}
						}
					}
					break;
				
				}
				case SEC_CONTRACT:{					//��ͬ
					if (actionTypeId == SECConstant.Actions.LINKSEARCH){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyContract trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					/**
					 * ��������
					 */
					else if (actionTypeId == SECConstant.Actions.CHECKSEARCH){
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_CONTRACT);
						
						long lApprovalID[] = null;							//����ID
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//ѭ������ÿһ�ֽ���
							
							lApprovalID = NameRef.getApprovalIDByTransactionTypeID(transIds[n] , 3,true);
							/**
							 * ȡ����һ�������
							 */
							
//							zpli modify 2005-09-14
							//TODO: ֤ȯ lLoanTypeID ��ʱû��ȡ��
							strUser = appBiz.findTheVeryUser(
									Constant.ModuleType.SECURITIES, lApprovalID[0],
									actionTypeId, officeId, currencyId, userId,
									this.transConn);							
							//strUser = appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES,lApprovalID[0],lApprovalID[1],userId,this.transConn);
							
							/**
							 * �Ƿ���Ҫ ���� ,����ǽṹ��Ͷ�ʲ���Ҫ
							 */
							needCurrency = true;
							if (String.valueOf(transIds[n]).startsWith(""+SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID)){
								needCurrency = false;
							}
							
							
							if (!"".equals(strUser)){
								strSql = "select count(app.transactionTypeId) dataCount," + " \n " +
								"app.transactionTypeId nodeId," + "\n" +
								"avg(app.statusId) nodeStatusId," + "\n" +
								"trans.name nodeName" + "\n" +
								
								"from sec_applyContract app,sec_transactionType trans" + "\n" +
								"where app.transactionTypeId = " + transIds[n] + "\n" +
								"and trans.id = " + transIds[n] + "\n" +
								"and app.statusid=" + statusId + "\n" ;
								if (needCurrency){
									strSql += "and app.currencyId=" + currencyId + "\n" ;
								}
								
								strSql += "and app.nextCheckUserId in " + strUser + "\n" +
								"group by app.transactionTypeId,trans.name" + "\n";
								
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									Log.print("&^&^&^&^&@@@@@@��ͬ���ף�"+node.getNodeId() + " // " + node.getDataCount());
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//��������

									alTmp.add(node);
								}
							}
						}
						
					}
					else if(actionTypeId == SECConstant.Actions.COMMIT){
						strSql = "select count(trans.transactionTypeId) dataCount,trans.transactionTypeId nodeId,avg(trans.statusId) nodeStatusId,tra.name nodeName \n" +
						"from sec_applyContract trans,sec_transactionType tra" + " \n" +
						"where trans.transactionTypeId = tra.id" + " \n" +
						"and trans.statusId=" + statusId + " \n" +
						"and (trans.currencyId=" + currencyId + " \n" +
						"or trans.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" + 
						"and trans.inputUserId =" + userId + " \n" +
						"group by trans.transactionTypeId,tra.name order by trans.transactionTypeId";
					}
					break;
				}
				case SEC_PLAN:{						//�ƻ�
					if(actionTypeId == SECConstant.Actions.LINKSEARCH){			//���ύ
						strSql = "select count(bb.transactionTypeId) dataCount,bb.transactionTypeId nodeId,  \n" +
								"avg(bb.statusId) nodeStatusId,tra.name nodeName from \n" +
								"(select * from SEC_PLANMODIFY where statusid=1)cc,SEC_APPLYCONTRACT bb,SEC_transactionType tra \n" +
								"where cc.ContractID(+) = bb.ID \n" +
								"and bb.transactionTypeId = tra.id" + " \n" +
								"and cc.statusId=" + statusId + "\n" +
								"and (bb.currencyId=" + currencyId + " \n" +
								"or bb.transactionTypeId like '" + SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID +"%') \n" +
								"and cc.inputUserId =" + userId + " \n" +
								"and cc.nextCheckLevel = 1" + " \n" +
								"group by bb.transactionTypeId,tra.name order by bb.transactionTypeId";
					}
					else if(actionTypeId == SECConstant.Actions.CHECKSEARCH){	//�����
						isSpecialType = true;
						transIds = TransactionType.getTransactionTypeCode(SEC_PLAN);
						
						String strUser = "";
						for (int n=0;n<transIds.length;n++){			//ѭ������ÿһ�ֽ���

							/**
							 * ȡ����һ�������
							 */
							
							//zpli modify 2005-09-14
							//TODO: ֤ȯ lLoanTypeID ��ʱû��ȡ��							
							strUser = appBiz.findTheVeryUser(Constant.ModuleType.SECURITIES,
							Constant.ApprovalLoanType.OTHER,Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN,officeId, currencyId,userId,this.transConn);
							
							
							/**
							 * �Ƿ���Ҫ ���� ,����ǽṹ��Ͷ�ʲ���Ҫ
							 */
							needCurrency = true;
							if (String.valueOf(transIds[n]).startsWith(""+SECConstant.BusinessType.FOREIGN_CURRENCY_INVESTMENT.ID)){
								needCurrency = false;
							}
							
							
							if (!"".equals(strUser)){
								strSql = "select count(bb.transactionTypeId) dataCount,bb.transactionTypeId nodeId,  \n" +
								"avg(bb.statusId) nodeStatusId,tra.name nodeName from \n" +
								"(select * from SEC_PLANMODIFY where statusid=" + SECConstant.PlanModifyStatus.SUBMIT + ")cc,SEC_APPLYCONTRACT bb,SEC_transactionType tra \n" +
								"where cc.ContractID(+) = bb.ID \n" +
								"and bb.transactionTypeId = tra.id" + " \n" +
								"and cc.statusId=" + SECConstant.PlanModifyStatus.SUBMIT + " \n" +
								"and tra.id = " + transIds[n] + "\n" ;
								if (needCurrency){
									strSql += "and bb.currencyId=" + currencyId + "\n" ;
								}
								
								strSql += "and cc.nextCheckUserId in " + strUser + "\n" +
								"group by bb.transactionTypeId,tra.name" + "\n";
								
								prepareStatement(strSql);
								ResultSet rs = this.executeQuery();
								ColumnNode node = null;
								
								if (rs.next()){
									node = new ColumnNode(null);
									
									node.setNodeId(rs.getLong("nodeId"));
									node.setNodeName(rs.getString("nodeName"));
									node.setNodeStatusId(rs.getLong("nodeStatusId"));
									node.setDataCount(rs.getLong("dataCount"));
									node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//��������
									
									alTmp.add(node);
								}
							}
						}
					}
				}
			}
			
			
			if (!isSpecialType){
				Log.print(strSql);
				
				prepareStatement(strSql);
				ResultSet rs = this.executeQuery();
				ColumnNode node = null;
				
				
				
				while (rs.next()){
					node = new ColumnNode(null);
					
					node.setNodeId(rs.getLong("nodeId"));
					node.setNodeName(rs.getString("nodeName"));
					node.setNodeStatusId(rs.getLong("nodeStatusId"));
					node.setDataCount(rs.getLong("dataCount"));
					
					node.setNodeURL(transURL.getURL(userId,currencyId,officeId,node.getNodeId(),actionTypeId,workTypeId,statusId));			//��������
					
					alTmp.add(node);
				}
			}
			/**
			 * ת������
			 */
			ColumnNode[] nodes = new ColumnNode[alTmp.size()];

			for (int n=0;n<nodes.length;n++){
				nodes[n] = (ColumnNode)alTmp.get(n);
			}
			column = getBusinessType(workTypeId,statusId,actionTypeId);					//�õ��������ͽṹ
			MyWorkColumn copy = getBusinessType(workTypeId,statusId,actionTypeId);		//�õ��������ͽ����
			column.upToRoot();						//�ص���׼����ӽڵ�
			/**
			 * ��Զ�Ӧ�Ľ������ͽ��нڵ����
			 */
			column.addNodesToColumn(column,copy,nodes);

			column.upToRoot();
			
			
			
			finalizeDAO();
		}catch(SQLException sqlE){
			throw new SecuritiesDAOException("�����ݿ��в����ҵĹ�����Ϣʱ�������ݿ����",sqlE);
		 }
		 catch(Exception e){
		 	throw new SecuritiesDAOException("�����ݿ��в����ҵĹ�����Ϣʱ������������",e);
		 }
		
		return column;
	}
}

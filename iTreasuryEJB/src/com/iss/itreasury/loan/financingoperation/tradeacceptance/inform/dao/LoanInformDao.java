package com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.inform.dataentity.LoanInformInfo;
import com.iss.itreasury.loan.financingoperation.tradeacceptance.protocol.dataentity.LoanProtocolInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Database;


public class LoanInformDao extends SettlementDAO {


		//����ID�����û���Ϣ
		public LoanInformInfo findByIDInform(LoanInformInfo informInfo) throws SQLException
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			StringBuffer strSQL = new StringBuffer();
			LoanProtocolInfo info = new LoanProtocolInfo();
			long lID = informInfo.getId();
			
			try {
				conn = Database.getConnection();

				
				/* ��ʼִ�в�ѯ */
				//ί�е�λ��ʱ�洢 ��ҵ����
				strSQL.append("select a.id,");
				strSQL.append("a.sapplycode,");//--��������
				strSQL.append("a.discountclientid,");//--��Ʊ��
				strSQL.append("a.mloanamount,");//--��Ʊ�жҽ��2
				strSQL.append("a.commission,");
				strSQL.append("a.sloanreason,");//--����ԭ��
				strSQL.append("a.sloanpurpose,");//--������;
				strSQL.append("a.sother,");//--�ж���Դ����ʩ
				strSQL.append("a.sclientinfo,");//--�õ�λ���
				
				strSQL.append("d.creditcontroltype,");//���ſ��� ���ŷ�ʽ���������£����������£�
				
				strSQL.append("a.mchargerate,");//--�ж���������2
				strSQL.append("a.nintervalnum,");//--����2
				strSQL.append("a.nborrowclientid,");//--�տλ
				
				strSQL.append("d.gatheringportype gattype,");//--�տλ����
				
				strSQL.append("a.scontractcode,");//--Э����
				strSQL.append("a.sloanamountcode,");//--��Ʊ����
				strSQL.append("a.dtstartdate,");//--��ʼ����
				strSQL.append("a.dtenddate,");//--��������
				strSQL.append("a.ninputuserid,");//--¼����
				
				strSQL.append("a.nnextcheckuserid,");//--��һ��������
				strSQL.append("a.nnextchecklevel,");//--��һ����������
				strSQL.append("a.islowlevel,");//--��������
				strSQL.append("a.nstatusid,");//--״̬
				strSQL.append("b.sname,");//��λ����1
				strSQL.append("b.scode,");//��λ���1
				strSQL.append("(select lpad(count(*)+1,3,'0') from  LOAN_inform e where e.nContractID = a.id and nNoteTypeID = ?) ywcode, ");//--���ҵ��֪ͨ�����
				
				if(informInfo.getNnoteTypeId()==1 || informInfo.getNnoteTypeId()==2)
				{
					strSQL.append("(select a.mloanamount*a.mchargerate*a.nintervalnum/36000 from dual) poundage,");//�ж�������
					
					strSQL.append("(");
					strSQL.append("(select a.mloanamount*a.mchargerate*a.nintervalnum/36000 from dual) -");//--�ж�������
					strSQL.append(" decode");
					strSQL.append("(");
					strSQL.append("(select sum(namount) from (select * from LOAN_inform where Status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f where f.ncontractid = a.id and f.nNoteTypeID = 1 group by nNoteTypeID),");
					strSQL.append("null,");
					strSQL.append("0,");																																						  //ncontractid Э��ID
					strSQL.append("(select sum(namount) from (select * from LOAN_inform where Status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f where f.ncontractid = a.id and f.nNoteTypeID = 1 group by nNoteTypeID)");
					strSQL.append(")");//����������ѣ�if����null�͵����������ڱ���
					strSQL.append(") nAmountsum, ");//����δ��������
				}
				
				if(informInfo.getNnoteTypeId()==1 || informInfo.getNnoteTypeId()==3)
				{
					strSQL.append("(select f.saccountno from sett_account f where e.nPayAccountID = f.id) fsaccountno,");//���
					strSQL.append("(select f.sname from sett_account f where e.nPayAccountID = f.id) fsaccountnoname,");//����
				}
				
				
				if(informInfo.getNnoteTypeId()==2)
				{
					strSQL.append("(select f.saccountno from sett_account f where e.nPayAccountID = f.id) fsaccountno,");//���(���)
					strSQL.append("(select f.sname from sett_account f where e.nPayAccountID = f.id) fsaccountnoname,");//����(���)
				}
				
				strSQL.append("(select f.saccountno from sett_account f where e.nRecAccountID = f.id) fsaccountno1,");//���(�տ�)
				strSQL.append("(select f.sname from sett_account f where e.nRecAccountID = f.id) fsaccountnoname1,");//����(�տ�)
				
				strSQL.append("e.ID eID,");
				strSQL.append("e.nContractID enContractID,");//--Ʊ�ݳж�Э��ID
				strSQL.append("e.nNoteTypeID enNoteTypeID,");//--ҵ��֪ͨ������(1��ȡ������ 2���ڳж� 3�渶��Ϣ�ջ�)
				strSQL.append("e.sCode esCode,");//--ҵ��֪ͨ�����(����001)
				strSQL.append("e.dtExecuteDate edtExecuteDate,");//--ִ������
				strSQL.append("e.dtOutdate edtOutdate,");//--��Ʊ����
				strSQL.append("e.dtTodate edtTodate,");//--������
				strSQL.append("e.nAmount enAmount,");//--���׽��(1 ������ 2 �жҽ�� 3 ��Ϣ�ܶ�)
				strSQL.append("e.nPayAccountID enPayAccountID,");//--�����ڲ��˻�ID
				strSQL.append("e.sPayBankAccountNO esPayBankAccountNO,");//--���������˺�
				strSQL.append("e.sPayBankAccountName esPayBankAccountName,");//--���������˻�����
				strSQL.append("e.sPayBankName esPayBankName,");//--�����˻�������
				strSQL.append("e.nRecAccountID enRecAccountID,");//--�տ��ڲ��˻�ID
				strSQL.append("e.sRecBankAccountNO esRecBankAccountNO,");//--�տ������˺�
				strSQL.append("e.sRecBankAccountName esRecBankAccountName,");//--�տ������˻�����
				strSQL.append("e.sRecBankName esRecBankName,");//--�տ���������
				strSQL.append("e.sRecBankProvince esRecBankProvince,");//--�տ��������ʡ
				strSQL.append("e.sRecBankCity esRecBankCity,");//--�տ����������
				strSQL.append("e.ncapitalAmount encapitalAmount,");//--����
				strSQL.append("e.noverdueDay enoverdueDay,");//--��������
				strSQL.append("e.noverDueRate enoverDueRate,");//--��������
				strSQL.append("e.ninterestAmount eninterestAmount,");//--������ȡ��Ϣ	
				strSQL.append("e.nextcheckuserid enextcheckuserid,");//--��һ�������
				strSQL.append("e.nextchecklevel enextchecklevel,");//--��һ����������
				strSQL.append("e.islowlevel eislowlevel,");//--��������
				strSQL.append("e.officeid eofficeid,");//--���´�
				strSQL.append("e.currencyid ecurrencyid,");//--����
				strSQL.append("e.status estatus,");//--״̬
				strSQL.append("e.inputuserid einputuserid,");//--¼����
				strSQL.append("e.inputdate einputdate");//--¼��ʱ��
				
//				add dwj 20081028 informInfo.getNnoteTypeId()==3��ʾ���渶��Ϣ�ջ�
				if(informInfo.getNnoteTypeId()==3)
				{
					
					//����δ�ձ���
					strSQL.append(" ,(a.mloanamount-(select sum(ncapitalamount) ");// ---���ձ���
					strSQL.append(" from (select * ");
					strSQL.append(" from LOAN_inform ");
					strSQL.append(" where Status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f ");
					strSQL.append(" where f.ncontractid = a.id ");
					strSQL.append(" and f.nNoteTypeID = 3 ");
					strSQL.append(" group by nNoteTypeID)) nncapitalamount ");//--δ�ձ���
					
					//˵��������Ϣ��ȷ���Ͳ��ܱ��޸� 
					//��������
					strSQL.append(" ,(select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) maxRate ");// --������Ϣ
					
					//��������
					strSQL.append(" ,(select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3)  maxday "); //--��������
					
					//add dwj 20081115
					//��� ������Ϣ��id   
					strSQL.append(" ,(select max(id) from (select * from LOAN_inform where status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3 and (x.nOverDueRate*x.noverdueday)>0 and x.noverdueday>0)  noverdueid "); //--������Ϣid
					//end dwj 20081115
					
					//������Ϣ
					strSQL.append(" ,( ");
					strSQL.append(" ((select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" (select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" a.mloanamount )/36000 ");
					strSQL.append(" ) accrual ");//--������Ϣ
					
					
					
					//����δ�ջ���Ϣ	 
					strSQL.append(" ,( ");
					strSQL.append(" ((select max(x.nOverDueRate) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" (select max(x.noverdueday) from (select * from LOAN_inform where status not in("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) x where x.ncontractid = a.id and x.nNoteTypeID = 3) ");
					strSQL.append(" * ");
					strSQL.append(" a.mloanamount )/36000 ");
					strSQL.append(" -(select sum(nInterestAmount) ");// ---����������Ϣ
					strSQL.append(" from (select * ");
					strSQL.append(" from LOAN_inform ");
					strSQL.append(" where Status not in ("+LOANConstant.InformStatus.DELETE+","+LOANConstant.InformStatus.REFUSE+")) f ");
					strSQL.append(" where f.ncontractid = a.id ");
					strSQL.append(" and f.nNoteTypeID = 3 ");
					strSQL.append(" group by nNoteTypeID) ");
					strSQL.append(" ) naccrual ");//--naccrualδ��������Ϣ nninterestamountsum
					
				}
				
				
				strSQL.append(" from LOAN_CONTRACTFORM a,Client b, ");
				strSQL.append(" loan_loanform d, ");
				
				strSQL.append(" loan_inform e ");//֪ͨ��
				strSQL.append(" where a.ntypeid = 80");
				strSQL.append(" and b.id = a.discountclientid ");
				
				strSQL.append(" and a.nloanid = d.id");
				
				strSQL.append(" and e.ncontractid = a.id");//--e.ncontractidЭ��ID
				strSQL.append(" and e.id = ?");//--֪ͨ��ID 
				
				ps = conn.prepareStatement(strSQL.toString());
				System.out.println("***dwj*** id : " + lID);
				ps.setLong(1, informInfo.getNnoteTypeId());//֪ͨ����
				ps.setLong(2, lID);
				System.out.println("***dwj*** sql: " + strSQL.toString());
				rs = ps.executeQuery();
				if (rs!=null && rs.next()) {
					
					//info.setID(rs.getLong("id"));
					info.setId(rs.getLong("id"));//ID
					info.setApplicationCode(rs.getString("sapplycode"));//--��������
					info.setPaymentPorId(rs.getLong("discountclientid"));//--��Ʊ��
					info.setMOrder(rs.getDouble("mloanamount"));//�����
					info.setCommission(rs.getDouble("commission"));
					
					info.setApplicationReason(rs.getString("sloanreason"));//����ԭ��
					info.setApplicationPurpose(rs.getString("sloanpurpose"));//������;
					info.setApplicationRimea(rs.getString("sother"));//�ж���Դ����ʩ
					info.setCorporationcircs(rs.getString("sclientinfo"));//�õ�λ������
					
					info.setCreditcontrolType(rs.getLong("creditcontroltype"));//���ſ��� ���ŷ�ʽ
					info.setSname(rs.getString("sname"));//��λ����
					info.setScode(rs.getString("scode"));//��λ���
					//info.setCreditlevel(rs.getString("creditlevel"));//�ȼ�
					info.setScale(rs.getDouble("mchargerate"));//����  �ж���������
					info.setTerm(rs.getLong("nintervalnum"));//����
					info.setGatheringPorId(rs.getLong("nborrowclientid"));//--�տλ         ͨ�����ǵĵ���λ��Ϣ
					info.setGatheringPorType(rs.getLong("gattype"));//--�տλ����  ͨ�����ǵĵ���λ��Ϣ
					
					info.setNextCheckuserId(rs.getLong("nnextcheckuserid"));//--��һ��������
					info.setNextChecklevel(rs.getLong("nnextchecklevel"));//��һ����������
					info.setIsLowlevel(rs.getLong("islowlevel"));//--��������
					info.setStatus(rs.getLong("nstatusid"));//--״̬
					
					//��ͬ���е��ֶ�
					info.setScontractcode(rs.getString("scontractcode"));//��ͬ���
					info.setSloanamountcode(rs.getString("sloanamountcode"));//��Ʊ����
					info.setDtstartdate(rs.getTimestamp("dtstartdate"));//��ʼ����
					info.setDtenddate(rs.getTimestamp("dtenddate"));//��������
					
					System.out.println("�տλ����: " + info.getGatheringPorType());
					info = selName(info);//��� ��λ��Ϣ���ڲ����ⲿ��
					informInfo.setScode(rs.getString("ywcode"));//���ҵ��֪ͨ�����
					if(informInfo.getNnoteTypeId()!=3)
					{
						informInfo.setPoundage(rs.getDouble("poundage"));//�ж�������
						double npoundage = rs.getDouble("nAmountsum");
						informInfo.setNpoundage(npoundage);//δ��������
					}
					informInfo.setCurrencyid(rs.getLong("ecurrencyid"));// NUMBER,--����
					informInfo.setDtExecuteDate(rs.getTimestamp("edtExecuteDate"));//dtExecuteDate Date,--ִ������
					informInfo.setDtOutdate(rs.getTimestamp("edtOutdate"));//dtOutdate Date,--��Ʊ����
					informInfo.setDtTodate(rs.getTimestamp("edtTodate"));//dtTodate Date,--������
					informInfo.setId(rs.getLong("eID"));
					informInfo.setInputdate(rs.getTimestamp("eInputdate"));// DATE--¼��ʱ��
					informInfo.setInputuserid(rs.getLong("eInputuserid"));// NUMBER,--¼����
					informInfo.setIslowlevel(rs.getLong("eIslowlevel"));//// NUMBER,--��������
					informInfo.setNamount(rs.getDouble("eNamount"));//nAmount Number(21,6),--���׽��(1 ������ 2 �жҽ�� 3 ��Ϣ�ܶ�)
					informInfo.setNcapitalAmount(rs.getDouble("eNcapitalAmount"));// Number(21,6),--����
					informInfo.setNcontractId(rs.getLong("eNcontractId"));//nContractID Number,--Ʊ�ݳж�Э��ID
					informInfo.setNextchecklevel(rs.getLong("eNextchecklevel"));// NUMBER,--��һ����������
					informInfo.setNextcheckuserid(rs.getLong("eNextcheckuserid"));// NUMBER,--��һ�������
					informInfo.setNinterestAmount(rs.getDouble("eNinterestAmount"));// Number(21,6),--������ȡ��Ϣ
					informInfo.setNnoteTypeId(rs.getLong("eNnoteTypeId"));//nNoteTypeID Number,--ҵ��֪ͨ������(1��ȡ������ 2���ڳж� 3�渶��Ϣ�ջ�)
					
					if(informInfo.getNnoteTypeId()==3)
					{	
						informInfo.setNoverdueDay(rs.getLong("maxday"));// Number,--��������
						informInfo.setNoverDueRate(rs.getDouble("maxRate"));// Number(15,12),--��������
						informInfo.setNncapitalamount(rs.getDouble("nncapitalamount"));//δ�ձ���
						informInfo.setNninterestamountsum(rs.getDouble("naccrual"));//δ�ջ���Ϣ
						informInfo.setNoverdueratesum(rs.getDouble("accrual"));//������Ϣ
						
						informInfo.setNoverdueid(rs.getLong("noverdueid"));//������Ϣid
						
					}
					informInfo.setNpayAccountID(rs.getLong("enpayAccountID"));//nPayAccountID number,--�����ڲ��˻�ID
					informInfo.setNrecAccountID(rs.getLong("enrecAccountID"));//nRecAccountID number,--�տ��ڲ��˻�ID
					informInfo.setOfficeid(rs.getLong("eOfficeid"));//NUMBER,--���´�
					informInfo.setScode(rs.getString("escode"));//sCode Varchar2(10),--ҵ��֪ͨ�����(����001)
					informInfo.setSpayBankAccountName(rs.getString("espayBankAccountName"));//sPayBankAccountName Varchar2(100),--���������˻�����
					informInfo.setSpayBankAccountNO(rs.getString("espayBankAccountNO"));//sPayBankAccountNO Varchar2(30),--���������˺�
					informInfo.setSpayBankName(rs.getString("espayBankName"));//sPayBankName Varchar2(100),--�����˻�������
					informInfo.setSrecBankAccountName(rs.getString("esrecBankAccountName"));//sRecBankAccountName Varchar2(100),--�տ������˻�����
					informInfo.setSrecBankAccountNO(rs.getString("esrecBankAccountNO"));//sRecBankAccountNO Varchar2(30),--�տ������˺�
					informInfo.setSrecBankCity(rs.getString("esrecBankCity"));//sRecBankCity Varchar2(50),--�տ����������
					informInfo.setSrecBankName(rs.getString("esrecBankName"));//sRecBankName Varchar2(100),--�տ���������
					informInfo.setSrecBankProvince(rs.getString("esrecBankProvince"));//sRecBankProvince Varchar2(50),--�տ��������ʡ
					informInfo.setStatus(rs.getLong("estatus"));// NUMBER,--״̬
					informInfo.setFsaccountno(rs.getString("fsaccountno"));//�ڲ��ʺű��(���)
					informInfo.setFsaccountnoname(rs.getString("fsaccountnoname"));//�ڲ��ʺ�����(���)
					informInfo.setFsaccountno1(rs.getString("fsaccountno1"));//�ڲ��ʺű��(�տ�)
					informInfo.setFsaccountnoname1(rs.getString("fsaccountnoname1"));//�ڲ��ʺű��(�տ�)
				}
				
				informInfo.setLoanProtocolInfo(info);
				informInfo.setInputuserid(rs.getLong("ninputuserid"));//¼����
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			return informInfo;
		}

//		���� �տλ��Ϣ
		private LoanProtocolInfo selName(LoanProtocolInfo info)throws SQLException
		{
			long type = -1;
			long gatheringPorId = info.getGatheringPorId();
			type = info.getGatheringPorType();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			StringBuffer strSQL = new StringBuffer();
			try {
				conn = Database.getConnection();

				
				/* ��ʼִ�в�ѯ */
				if(type==1)//1���ڲ���λselect scode from loan_loanform a, Client b where a.ntypeid = 80 and b.id=a.nborrowclientid
				{
					strSQL.append(" select sname,scode ");
					strSQL.append(" from loan_loanform a, Client b where a.ntypeid = 80 and b.id=?");
				}
				
				if(type==2)//2���ⲿ��λ select slegalpersoncodecert from loan_loanform a, loan_exteriorunits c where a.ntypeid = 80 and c.id=a.nborrowclientid
				{
					strSQL.append(" select sname,slegalpersoncodecert ");
					strSQL.append(" from loan_loanform a, loan_exteriorunits c where a.ntypeid = 80 and c.id=?");
				}
				
				System.out.println("***dwj*** sql: " + strSQL.toString());
				
				ps = conn.prepareStatement(strSQL.toString());
				ps.setLong(1, gatheringPorId);
				rs = ps.executeQuery();
				if (rs.next()) {
					if(type==1)
					{
						info.setSname1(rs.getString("sname"));//��λ���ƣ��ڲ���
						info.setScode1(rs.getString("scode"));//��λ��ţ��ڲ���
					}
					if(type==2)
					{
						info.setSname2(rs.getString("sname"));//��λ���ƣ��ⲿ��
						info.setSlegalpersoncodecert2(rs.getString("slegalpersoncodecert"));//��λ���ˣ��ⲿ��
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			return info;
		}
}

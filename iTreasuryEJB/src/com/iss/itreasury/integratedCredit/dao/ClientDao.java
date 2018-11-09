package com.iss.itreasury.integratedCredit.dao;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.integratedCredit.dataentity.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.settlement.account.dao.Sett_ClientDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * @author ninghao 2004-2-27
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ClientDao
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	/**
		 * findClient �������пͻ�
		 * ���ݿͻ���Ų������пͻ������ؿͻ���ϸ����
		 * ����Client���ݱ�
		 * ��ѯ��¼
		 * haoning
		 * @param lClientID String  �ͻ���ŵ�ID
		 * @return ClientInfo  ��ϸ�Ŀͻ���Ϣ
		 * @throws RemoteException`
		 */
		public ClientInfo findClientByID(long lClientID) 
		throws Exception
		{
			String strSQL = null;
			ClientInfo ci = new ClientInfo();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			long lParentCorpID = -1;
			long lOfficeID = -1;
			long lMaxLoanID = -1; //�ͻ���������ı�ʾ
			long lMaxAssureID = -1; //�ͻ����������ı�ʾ
			String[] strQTClientName = new String[3];
			float[] fQTScale = new float[3];
			String[] strQTCardNo = new String[3];
			String[] strQTPwd = new String[3];
			try
			{
				//���ҿͻ���Ϣ(�ͻ�����
				con = Database.getConnection();
				//���ҿͻ���Ϣ
				strSQL =
					" select a.*  "
						+ " ,b.sName as ParentCorpName "
						+ " ,b2.sName as ParentCorpName2 "
						+ " ,c.sName as OfficeName "
						//+ " ,d.sName as LoanClientType "
						//+ " ,e.sName as EnterpriceTypeName "
						//+ " ,f.sName as SettClientType "
						+ " from Client a ,Client b,Client b2,Office c  "
						//+ " ,LOAN_ClientType d ,SETT_ENTERPRICETYPE e "
						//+ " ,SETT_ClientType f "
						+ " where a.NPARENTCORPID1 = b.ID(+) "
						+ " and a.NPARENTCORPID2 = b2.ID(+) "
						+ " and a.NOFFICEID = c.ID(+) "
						//+ " and a.NLOANCLIENTTYPEID=d.ID(+) "
						//+ " and a.NCORPNATUREID=e.ID(+) "
						//+ " and a.NSETTCLIENTTYPEID=f.ID(+) "
						+ " and a.id=? and a.nStatusID =? ";
				log4j.info("SQL=" + strSQL);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lClientID);
				ps.setLong(2, Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					log4j.info("ȡ�ͻ�������Ϣ");
					ci.setClientID(rs.getLong("ID"));
					lOfficeID = rs.getLong("NOFFICEID");
					//ci.setOfficeName(rs.getString("OfficeName")); //����˾����
					ci.setOfficeID(lOfficeID); //�ͻ����
					ci.setCode(rs.getString("SCODE")); //�ͻ�����
					ci.setName(rs.getString("SNAME"));
					ci.setLicenceCode(rs.getString("SLICENCECODE")); //Ӫҵִ��
					
					
					
//					ci.setStatutoryname(rs.getString("STATUTORYNAME"));// ��������������
//					ci
//							.setStatutoryotherduties(rs
//									.getString("STATUTORYOTHERDUTIES"));// ��������������ְ��
//					ci.setStatutorysphone(rs.getString("STATUTORYSPHONE"));// ���������˵绰
//					ci
//							.setSauthorizedagentname(rs
//									.getString("SAUTHORIZEDAGENTNAME"));// ��Ȩ����������
//					ci.setSauotherduties(rs.getString("SAUOTHERDUTIES"));// //
//					// ��Ȩ����������ְ��
//					ci.setNauphone(rs.getString("nauphone"));// ��Ȩ�����˵绰
//					ci.setNcorporateage(rs.getLong("ncorporateage"));// ����
//					ci.setStreasurername(rs.getString("streasurername"));// ������������
//					ci.setStreasurerduty(rs.getString("streasurerduty"));// ������������ְ��
//					ci.setNtreasurerphone(rs.getString("ntreasurerphone"));// ������������ְ��
//					ci.setScorporategender(rs.getLong("scorporategender"));// �Ա�
//					ci.setScorporatequalifications(rs
//							.getString("scorporatequalifications"));// ѧ��
//					ci.setScorporateorigin(rs.getString("scorporateorigin"));// ����
//					ci.setScorworkexperience(rs.getString("scorworkexperience"));// ��Ҫ��������
//					ci.setNcompanynature(rs.getLong("ncompanynature"));// ��������
//					ci.setNcompanynature(rs.getLong("ncompanynature"));// ��˾����
//					ci.setScorporatename(rs.getString("scorporatename"));// // ����
//					ci.setNsubsidiary(rs.getLong("nsubsidiary"));// ȫ���ӹ�˾
//					ci.setNholdingcompany(rs.getLong("nholdingcompany"));// �عɹ�˾
//					ci.setEquitycompany(rs.getLong("nequitycompany"));// �ιɹ�˾
//					ci.setSubsidiaryplant(rs.getLong("nsubsidiaryplant"));// ������
//					ci.setOrganizationalstructure(rs
//							.getString("sorganizationalstructure"));// ������֯�ṹ������Ҫ˵��������
//					ci.setQenterprisehistory(rs.getString("sqenterprisehistory"));// ��ҵ��ʷ�ظ�
//					ci.setMaindepositarybank(rs.getString("smaindepositarybank"));// ��Ҫ��������
//					ci.setCorporateculture(rs.getString("scorporateculture"));// ��ҵ�Ļ��ص�
//					ci.setEschargesector(rs.getString("eschargesector"));// ��ҵ�ϼ����ܲ��Ż�������
					
					ci.setEmail(rs.getString("SEMAIL")); //�����ʼ�
					if (rs.getString("SPROVINCE") == null)
					{
						log4j.info("ʡ�� is null");
					}
					else
					{
						ci.setProvince(rs.getString("SPROVINCE")); //ʡ��
						//ci.m_strAddress = ci.m_strProvince;
					}
					if (rs.getString("SCITY") != null)
					{
						ci.setCity(rs.getString("SCITY")); //����SCITY
						//ci.m_strAddress = ci.m_strAddress + ci.m_strCity;
					}
					if (rs.getString("SADDRESS") != null)
					{
						ci.setAddress(rs.getString("SADDRESS"));
					}
//					ci.setZipCode(rs.getString("SZIPCODE")); //�ʱ�
//					ci.setPhone(rs.getString("SPHONE")); //�绰
//					ci.setFax(rs.getString("SFAX")); //����
//					ci.setIsPartner(rs.getLong("NISPARTNER")); //�Ƿ��ǹɶ�
//					ci.setAccount(rs.getString("SACCOUNT")); //����˾�ʻ���
//					ci.setBank1(rs.getString("SBANK1")); //��������1
//					ci.setBank2(rs.getString("SBANK2")); //��������2
//					ci.setBank3(rs.getString("SBANK3")); //��������3
//					//log4j.info("----------2--------");
//					ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); //�����˻�1
//					ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); //�����˻�2
//					ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); //�����˻�3
//					ci.setLoanCardNo(rs.getString("SLOANCARDNO")); //�����
//					ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); //�������
//					ci.setCreditLevelID(rs.getString("NCREDITLEVELID")); //���õȼ�
//					ci.setContacter(rs.getString("SCONTACTER")); //��ϵ��
					//�ͻ�����
					//ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); //��Ӫ����
					//ci.setLoanClientType(rs.getString("LoanClientType"));//��������
					//log4j.info("��Ӫ����ͻ�����:"+rs.getString("LoanClientType"));
					//ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); //����
					//ci.setSettClientType(rs.getString("SettClientType"));//��������
//					ci.setRiskLevelID(rs.getString("NRISKLEVELID")); //���ռ���
//					ci.setLegalPerson(rs.getString("SLEGALPERSON")); //���˴���
					ci.setCaptial(rs.getString("SCAPITAL")); //ע���ʱ�
					//log4j.info("--------4---------");
					//������λ
//					ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
					//��Ӫ��Χ
//					ci.setDealScope(rs.getString("SDEALSCOPE"));
					//-------------------------------����-----------
					//�ֹ�¼���������(����ר��)
//					ci.setRiskLevel(rs.getString("SRISKLEVEL"));
					//��������
//					ci.setGeneratorCapacity(rs.getString("SGENERATORCAPACITY"));
//					ci.setIntelligenceLevel(rs.getString("STALENTLEVEL"));
//					ci.setLegalPersonCode(rs.getString("SLEGALPERSONCODECERT"));
//                    //ci.setInvestAmount(rs.getString("sInvestAmount"));
//                    //ci.setInvestTime(rs.getString("sInvestTime"));
//                    ci.setFinanceManager(rs.getString("FINANCIALCONTROLOR"));
//                    ci.setEschargesector(rs.getString("eschargesector"));
//                    ci.setNregistercapital(rs.getLong("nregistercapital"));
//                    ci.setNPaidCapital(rs.getLong("npaidcapital"));
//                    ci.setNmainBusiness(rs.getLong("nmainBusiness"));
//                    ci.setNCategory1(rs.getLong("ncategory1"));
//                    ci.setNCategory2(rs.getLong("ncategory2"));
//                    ci.setNCategory3(rs.getLong("ncategory3"));
//                    ci.setNCompanyRegion(rs.getLong("ncompanyRegion"));
//                    ci.setNCompanySize(rs.getLong("ncompanySize"));
//                    ci.setNCorporateLevel(rs.getLong("ncorporateLevel"));
//                    ci.setNcorporatenature(rs.getLong("ncorporatenature"));// ��������
//                    ci.setNupportthedevelopment(rs.getLong("nupportthedevelopment"));////��ҵ����Ӫҵ���Ƿ��Ǽ���֧�ַ�չ����Ŀ
//					ci.setNGuquanjiegou(rs.getLong("nguquanjiegou"));//��Ȩ�ṹ
                    log4j.info("=====ȡ�ͻ�������Ϣ����=====");
				}
				try
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
				//���ҿعɵ�λ��Ϣ
				
				//���������ɶ���λ
              //  Collection vCOS = null; //��ǰҳ�����
               // vCOS=findOtherShareByClientID(lClientID);
               // ci.setOthersStockHolder(vCOS);
                /*
				strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " 
                       + "       and  nPartnerID = -1 ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lClientID);
				rs = ps.executeQuery();
				int i = 0;
				while (rs != null && rs.next())
				{
					//ci.m_lQTClientID[i] = rs.getLong("nPartnerID"); //�����ɶ���λ1ID
					//strQTClientName[i] = rs.getString("SPARTNERNAME");
					//fQTScale[i] = rs.getFloat("MSTOCKRATE");
					//strQTCardNo[i] = rs.getString("SLOANCARDNO");
					//strQTPwd[i] = rs.getString("SLOANCARDPWD");
					//log4j.info("�õ������ɶ���λ��Ϣ" + (i + 1));
					//i++;
                    ClientOtherShareInfo cosinfo = new ClientOtherShareInfo();
                    cosinfo.setClientName(rs.getString("SPARTNERNAME"));
                    cosinfo.setScale(rs.getFloat("MSTOCKRATE"));
                    cosinfo.setCardNo(rs.getString("SLOANCARDNO"));
                    cosinfo.setPwd(rs.getString("SLOANCARDPWD"));
                    vCOS.addElement(cosinfo);
				}//*/
                /*
				ci.setQTClientName(strQTClientName);
				ci.setFQTScale(fQTScale);
				ci.setQTCardNo(strQTCardNo);
				ci.setQTPwd(strQTPwd);//*/
				try
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
				//log4j.info("���ҿͻ���Ϣ����");
				/*/ȡ�ô���������Ϣ
				//ȡ�õ����������Ϣ
				//*/
				//--------���Ҳ������ͳ�Ʊ���Ϣ-----------//
				long[] lFinanceID = {-1,-1,-1};
				String[] strName  = {"","",""};
				String[] strYear  = {"","",""};
				int iFinance=lFinanceID.length-1;
				strSQL =
					" select a.ID ContentID "
						+ " ,a.sDocName "
						+ " ,a.sCode "
						+ " from LOAN_ContractContent a, Client b "
						+ " where  a.nParentID=b.ID  "
						+ " and a.nContractTypeID =? "
						+ " and b.ID = ? "
						+ " order by a.ID desc ";
				log4j.info("�������ͳ��SQL:"+strSQL);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, LOANConstant.ContractType.FINANCETJB);
				ps.setLong(2, lClientID);
				rs = ps.executeQuery();
				while (rs != null && rs.next() && iFinance >= 0)
				{
					//log4j.info("�������ͳ��ContentID---------"+(iFinance+1));
					lFinanceID[iFinance] = rs.getLong("ContentID");
					strName[iFinance] = rs.getString("sDocName");
					//log4j.info("ContentID["+iFinance+"]="+lFinanceID[iFinance]);
					strYear[iFinance] = rs.getString("sCode");
					iFinance--;
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				//����˳���ǡ���ǰ�ꡢȥ�ꡢ����
				if(lFinanceID[0] <= 0)
				{
					if(lFinanceID[1] <= 0)
					{
						lFinanceID[0]=lFinanceID[2];
						strName[0]=strName[2];
						strYear[0]=strYear[2];
						lFinanceID[2]=-1;
						strName[2]="";
						strYear[2]="";
					}
					else
					{
						lFinanceID[0]=lFinanceID[1];
						strName[0]=strName[1];
						strYear[0]=strYear[1];
						lFinanceID[1]=lFinanceID[2];
						strName[1]=strName[2];
						strYear[1]=strYear[2];
						lFinanceID[2]=-1;
						strName[2]="";
						strYear[2]="";
					}
				}
				ci.setFINANCETJBID(lFinanceID) ;
				ci.setFINANCETJBName(strName) ;
				ci.setFINANCETJBYear(strYear) ;
				log4j.info(" ���Ҳ������ͳ�ƽ����� ");
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return ci;
		}
		
	/**
		 * �������޸ģ��ͻ���ϸ����
		 * saveClientInfo  �������޸ģ��ͻ�����ϸ����
		 * ����Client���ݱ�
		 * ������Ӧ�ֶ�
		 * lID=0,����  lID>0,�޸�
		 * Լ����long�Ͳ���=-1��string�Ͳ���=����,Ϊδʹ����������������޸�
		 *
		 * @param clientinfo �ͻ���Ϣ
		 * ��Ӧ�ֶΣ���������clientinfo���У�
		 * @param lID ��ʶ
		 * @param strClientName   ��˾����
		 * @param strClientNo,    �ͻ����
		 * @param strLicence,     Ӫҵִ��
		 * @param lOfficeID,      ���´�
		 * @param strAccount,     ����˾�˺�
		 * @param strBank,        ��������
		 * @param strAccount      ���������˺�
		 * @param strBank1,       ��������1
		 * @param strAccount1,    �˺�1
		 * @param strBank2,       ��������2
		 * @param strAccount2,    �˺�2
		 * @param strBank3,       ��������3
		 * @param strAccount3,    �˺�3
		 * @param strProvince,    ʡ
		 * @param strCity,        ��
		 * @param strAddress1,    ��ַ1
		 * @param strAddress2     ��ַ2
		 * @param strZipCode,     �ʱ�
		 * @param strDeputy,      ���˴���
		 * @param strTel,         �绰
		 * @param strFax,         ����
		 * @param strMailAddr,    ����
		 * @param strContact,     ��ϵ��
		 * @param strEconomic,    ��������
		 * @param lGovernmentID,  ���ܲ��ű�ʾ
		 * @param isShareHolder,  �Ƿ�ɷ�
		 * @param lClientTypeID,  �ͻ�����
		 * @param lCreditLevel,   ���õȼ�
		 * @param lVentureLevel   ��������
		 * @param strCapital      ע���ʱ�
		 *
		 * @return long �ɹ�����ID��Ϣ��ʧ�ܷ���0
		 * @throws RemoteException
		 */
		public long saveClientInfo(ClientInfo clientinfo) 
		throws Exception,IException
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSQL = "";
			int nIndex = 0;
			long lResult = -1; //�������޸ĳɹ�����־
			////////////////////////////
			long lInputUserID = -1;
			long lModifyUserID = clientinfo.getModifyUserID(); //�޸���ID
			long lClientID = clientinfo.getClientID(); //�ͻ���ʶ ==0--����; >0--�޸�
			//String strClientNo = clientinfo.m_strCode; //�ͻ����
			long lOfficeID = clientinfo.getOfficeID();//���´�
			String strClientName = clientinfo.getName();//��˾����
			String strLicence = clientinfo.getLicenceCode(); //Ӫҵִ��
			String strAccount = clientinfo.getAccount(); //����˾�˺�
			String strBank1 = clientinfo.getBank1(); //��������1
			String strAccount1 = clientinfo.getBankAccount1(); //�˺�1
			String strBank2 = clientinfo.getBank2(); //��������2
			String strAccount2 = clientinfo.getBankAccount2(); //�˺�2
			String strBank3 = clientinfo.getBank3(); //��������3
			String strAccount3 = clientinfo.getBankAccount3(); //�˺�3
			String strLoanCardNo = clientinfo.getLoanCardNo(); //�����
			String strLoanCardPwd = clientinfo.getLoanCardPwd(); //�������
			String strProvince = clientinfo.getProvince(); //ʡ
			String strCity = clientinfo.getCity(); //��
			String strAddress = clientinfo.getAddress(); //��ַ
			String strZipCode = clientinfo.getZipCode(); //�ʱ�
			String strTel = clientinfo.getPhone(); //�绰
			String strFax = clientinfo.getFax(); //����
			String strMailAddr = clientinfo.getEmail(); //����
			String strDeputy = clientinfo.getLegalPerson(); //���˴���
			String strContact = clientinfo.getContacter(); //��ϵ��
			//String strEconomic = clientinfo.getEconomyType(); //��������
			long lIsShareHolder = clientinfo.getIsPartner(); //�Ƿ�ɶ�
			long lLoanClientTypeID = clientinfo.getLoanClientTypeID(); //��Ӫ����ͻ�����
			long lSettClientTypeID = clientinfo.getSettClientTypeID(); //����ͻ�����
			String lCreditLevel = clientinfo.getCreditLevelID(); //���õȼ�
			String lVentureLevel = clientinfo.getRiskLevelID(); //��������
			String strRiskLevel = clientinfo.getRiskLevel();
			long lCorpNatureID = clientinfo.getCorpNatureID(); //��ҵ����
			long lManagerDeptID = clientinfo.getParentCorpID(); //���ܲ���ID
			long lManagerDeptID2 = clientinfo.getParentCorpID2(); //���ܲ���ID
			String strJudgeClient = clientinfo.getJudGelevelClient(); //������λ
			String dCapital = clientinfo.getCaptial(); //ע���ʱ�
			//String strCapital = clientinfo.getCaptial(); //ע���ʱ�
            String strFinanceManager = clientinfo.getFinanceManager();//��������
			String strDealScope = clientinfo.getDealScope(); //��Ӫ��Χ
			String strGeneratorCapacity = clientinfo.getGeneratorCapacity(); //��������
            String strInvestAmount = clientinfo.getInvestAmount();//��Ͷ�ʶ�
            String strInvestTime = clientinfo.getInvestTime();//Ͷ��ʱ��
			String strLegalPersonCode = clientinfo.getLegalPersonCode(); //���˴���֤��
			String strIntelligenceLevel = clientinfo.getIntelligenceLevel(); //���ʵȼ�
			//�عɵ�λ
			long lKGClientID = clientinfo.getKGClientID();
			String strKGClientName = clientinfo.getKGClientName();
			float fKGScale = clientinfo.getFKGScale();
			String strKGCardNo = clientinfo.getKGCardNo();
			String strKGPwd = clientinfo.getKGPwd();
			/*/�����ɶ���λ1-3
			String[] strQTClientName = clientinfo.getQTClientName();
			float[] fQTScale = clientinfo.getFQTScale();
			String[] strQTCardNo = clientinfo.getQTCardNo();
			String[] strQTPwd = clientinfo.getQTPwd();//*/
            Collection cQT = clientinfo.getOthersStockHolder();
			//////////////////////////////
			try
			{
				con = Database.getConnection();
				if (lClientID <= 0) //����
				{
					System.out.println("================�����ͻ�================");
                    /* qqgd add this code for add a new client */
					strSQL = "select SName from client where sName='" + clientinfo.getName() + "'";
                    strSQL += " and nStatusID = "+Constant.RecordStatus.VALID + "   \n";
                    System.out.println(" check clientName: "+strSQL);
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						con.close();
						con = null;
						throw new IException("Loan_E101");
					}
					strSQL = "select SName from client where sLicenceCode ='" + clientinfo.getLicenceCode() + "'";
                    strSQL += " and nStatusID = "+Constant.RecordStatus.VALID + "   \n";
                    System.out.println(" check licenceCode : "+strSQL);
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						con.close();
						con = null;
						throw new IException("Loan_E102");
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null; //���Ȼ�ÿͻ�����id
					strSQL = "select nvl(max(ID)+1,1) nID from client";
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						lClientID = rs.getLong("nID");
						log4j.info("�����µĿͻ�����ÿͻ�ID" + lClientID);
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					strSQL =
						"insert into client(ID, nOfficeID, sCode, sName, sLicenceCode "
							+ " ,nInputUserID, dtInput, nStatusID) "
							+ " values (?, ?, ?, ?, ?, ?, SYSDATE,?)";
					log4j.info(strSQL);
					//System.out.println(strSQL);strLoanCardNo
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lClientID);
					ps.setLong(2, lOfficeID);
				
				
					ps.setString(3, getNewClientCode(lOfficeID));
					ps.setString(4, strClientName);
					ps.setString(5, strLicence);
					ps.setLong(6, clientinfo.getInputUserID());
					//ps.setLong(6,9);
					ps.setLong(7, Constant.RecordStatus.VALID);
					lResult = ps.executeUpdate();
					ps.close();
					ps = null;
					con.close();
					con = null;
					if (lResult < 0)
					{
						log4j.info("������¼ʧ�ܣ�����ֵ��" + lResult);
						return -1;
					}
					return lClientID;
					/* end of qqgd's adding */
				}
				if (lClientID > 0) //�޸�
				{
                    System.out.println("================�޸Ŀͻ�================");
                    //�ͻ������Ѿ�����
                    strSQL = "select SName from client "
                           + " where sName='" + clientinfo.getName() + "'"
                           + "   and id <> "+clientinfo.getClientID()
                           + "   and nstatusid = "
                           + Constant.RecordStatus.VALID;
                    Log.print("У��ͻ�����"+strSQL);       
                    ps = con.prepareStatement(strSQL);
                    rs = ps.executeQuery();
                    if (rs.next())
                    {
                        rs.close();
                        rs = null;
                        ps.close();
                        ps = null;
                        con.close();
                        con = null;
                        throw new IException("Loan_E101");
                    }
					strSQL = "update Client set NMODIFYUSERID=? "; //1
					strSQL +=" ,DTMODIFY=SYSDATE " ;
					strSQL +=" ,SLICENCECODE=? "; //2
					strSQL +=" ,SACCOUNT=? "; //3
					strSQL +=" ,SBANK1=? "; //4
					strSQL +=" ,SBANK2=? "; //5
					strSQL +=" ,SBANK3=? "; //6
					strSQL +=" ,SEXTENDACCOUNT1=? "; //7
					strSQL +=" ,SEXTENDACCOUNT2=? "; //8
					strSQL +=" ,SEXTENDACCOUNT3=? "; //9
					strSQL +=" ,SPROVINCE=? "; //10
					strSQL +=" ,SCITY=? "; //11
					strSQL +=" ,SADDRESS=? "; //12
					strSQL +=" ,SZIPCODE=? "; //13
					strSQL +=" ,SPHONE=? "; //14
					strSQL +=" ,SFAX=? "; //15
					strSQL +=" ,SEMAIL=? "; //16
					strSQL +=" ,SLEGALPERSON=? "; //17
					strSQL +=" ,SCONTACTER=? "; //18
					strSQL +=" ,SJUDGELEVELCLIENT=? "; //19
					strSQL +=" ,SCAPITAL=? "; //20
					strSQL +=" ,SDEALSCOPE=? "; //21
					strSQL +=" ,SLOANCARDNO=? "; //22
					strSQL +=" ,SLOANCARDPWD=? "; //23
					strSQL +=" ,SRISKLEVEL=? "; //24
					if (lIsShareHolder > -1)
					{
						strSQL += " ,NISPARTNER=? "; //30
					}
					if (lLoanClientTypeID > -1)
					{
						strSQL += " ,NLOANCLIENTTYPEID=? "; //31
					} 
					if (!lCreditLevel.equals(""))
					{
						strSQL += " ,NCREDITLEVELID=? "; //32
					}
					if (lManagerDeptID > 0)
					{
						strSQL += " ,NPARENTCORPID1=? "; //33
					}
                    if (lManagerDeptID2 > 0)
                    {
                        strSQL += " ,NPARENTCORPID2=? "; //33
                    }
					if (lCorpNatureID > 0)
					{
						strSQL += " ,NCORPNATUREID=? "; //34
					}
                    strSQL +=" ,SNAME=? "; //41
                    
                    strSQL +=" ,FinancialControlor=? ";
                    strSQL +=" ,sTalentLevel=? ";
                    strSQL +=" ,sLegalPersonCodeCert=? ";
                    strSQL +=" ,nSettClientTypeID=? ";
                    strSQL +=" ,sInvestAmount=? ";
                     strSQL +=" ,sInvestTime=? ";
                    
					strSQL += " where ID =? ";
					log4j.info("�޸Ŀͻ�SQL=" + strSQL);
					ps = con.prepareStatement(strSQL);
					//��������ֵ
					nIndex = 1;
					ps.setLong(nIndex, lModifyUserID); //1
					nIndex++;
					ps.setString(nIndex, strLicence); //2
					nIndex++;
					ps.setString(nIndex, strAccount); //3
					nIndex++;
					ps.setString(nIndex, strBank1); //4
					nIndex++;
					ps.setString(nIndex, strBank2); //5
					nIndex++;
					ps.setString(nIndex, strBank3); //6
					nIndex++;
					ps.setString(nIndex, strAccount1); //7
					nIndex++;
					ps.setString(nIndex, strAccount2); //8
					nIndex++;
					ps.setString(nIndex, strAccount3); //9
					nIndex++;
					ps.setString(nIndex, strProvince); //10
					nIndex++;
					ps.setString(nIndex, strCity); //11
					nIndex++;
					ps.setString(nIndex, strAddress); //12
					nIndex++;
					ps.setString(nIndex, strZipCode); //13
					nIndex++;
					ps.setString(nIndex, strTel); //14
					nIndex++;
					ps.setString(nIndex, strFax); //15
					nIndex++;
					ps.setString(nIndex, strMailAddr); //16
					nIndex++;
					ps.setString(nIndex, strDeputy); //17
					nIndex++;
					ps.setString(nIndex, strContact); //18
					nIndex++;
					ps.setString(nIndex, strJudgeClient); //19
					nIndex++;
					ps.setString(nIndex, dCapital); //20
					nIndex++;
					ps.setString(nIndex, strDealScope); //21
					nIndex++;
					ps.setString(nIndex, strLoanCardNo); //22
					nIndex++;
					ps.setString(nIndex, strLoanCardPwd); //23
					nIndex++;
					ps.setString(nIndex, strRiskLevel); //24
					nIndex++;
					if (lIsShareHolder > -1)
					{
						ps.setLong(nIndex, lIsShareHolder); //30
						nIndex++;
					}
					if (lLoanClientTypeID > -1)
					{
						ps.setLong(nIndex, lLoanClientTypeID); //31
						nIndex++;
					} 
					if (!lCreditLevel.equals(""))
					{
						ps.setString(nIndex, lCreditLevel); //32
						nIndex++;
					}
					if (lManagerDeptID > 0)
					{
						ps.setLong(nIndex, lManagerDeptID); //33
						nIndex++;
					}
                    if (lManagerDeptID2 > 0)
                    {
                        ps.setLong(nIndex, lManagerDeptID2); //33
                        nIndex++;
                    }
					if (lCorpNatureID > 0)
					{
						ps.setLong(nIndex, lCorpNatureID); //34
						nIndex++;
					}
                    ps.setString(nIndex, strClientName); //41
                    nIndex++;
                    
                    ps.setString(nIndex, strFinanceManager);
                    nIndex++;
                    ps.setString(nIndex, strIntelligenceLevel);
                    nIndex++;
                    ps.setString(nIndex, strLegalPersonCode);
                    nIndex++;
                    ps.setLong(nIndex, lSettClientTypeID);
                    nIndex++;
                    ps.setString(nIndex, strInvestAmount);
                    nIndex++;
                    ps.setString(nIndex, strInvestTime);
                    
                    nIndex++;
					ps.setLong(nIndex, lClientID);
					lResult = ps.executeUpdate();
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (lResult < 0)
					{
						return lResult;
					}
					//��ɾ���ÿͻ����еĹɶ���Ϣ ����Ӹ��ĺ�Ĺɶ�
					strSQL = " delete PARTNEROFCLIENT where nClientID = ?";
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lClientID);
					ps.executeUpdate();
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					//���¿عɵ�λ��Ϣ
					if (lKGClientID > 0)
					{
						//������ӿعɹɶ�
						strSQL = " insert into  PARTNEROFCLIENT  values(?,?,?,?,?,?) ";
						log4j.info(" �عɵ�λ SQL=" + strSQL);
						ps = con.prepareStatement(strSQL);
						//��������ֵ
						nIndex = 1;
						ps.setLong(nIndex, lClientID);
						nIndex++;
						ps.setLong(nIndex, lKGClientID);
						nIndex++;
						ps.setFloat(nIndex, fKGScale);
						nIndex++;
						ps.setString(nIndex, strKGCardNo);
						nIndex++;
						ps.setString(nIndex, strKGPwd);
						nIndex++;
						ps.setString(nIndex, strKGClientName);
						lResult = ps.executeUpdate();
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						if (lResult < 0)
						{
							lResult = -1;
							return lResult;
						}
					}
					/*/������������ɶ���λ��Ϣ1-3
					for (int i = 0; i < strQTClientName.length; i++)
					{
						if (strQTClientName[i] != null && strQTClientName[i] != "")
						{
							strSQL = " insert into  PARTNEROFCLIENT  values(?,?,?,?,?,?)";
							log4j.info(" �����ɶ�" + i + " SQL=" + strSQL);
							ps = con.prepareStatement(strSQL);
							ps.setLong(1, lClientID);
							ps.setLong(2, -1);
							ps.setFloat(3, fQTScale[i]);
							ps.setString(4, strQTCardNo[i]);
							ps.setString(5, strQTPwd[i]);
							ps.setString(6, strQTClientName[i]);
							lResult = ps.executeUpdate();
							if (ps != null)
							{
								ps.close();
								ps = null;
							}
							if (lResult < 0)
							{
								lResult = -1;
								return lResult;
							}
						}
					}//*/
                    if((cQT != null) && (cQT.size() > 0))
                    {
                        Log.print("������������ɶ���λ��Ϣ");
                        ClientOtherShareInfo QTInfo=null;
                        Iterator it = cQT.iterator();
                        while(it.hasNext())
                        {
                            QTInfo = new ClientOtherShareInfo();//
                            QTInfo = (ClientOtherShareInfo)it.next();
                            saveOtherShareInfo(QTInfo);
                        }
                    }
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch(IException ie)
			{
			    if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
				throw ie; 
			}
			catch (Exception e)
			{
				//lResult = -1;
				log4j.error(e.toString());
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return lResult;
		}
    /**
         * findOtherShareByClientID ���ҿͻ������ɶ���Ϣ
         * ���ݿͻ���Ų������пͻ������ؿͻ���ϸ����
         * ����Client���ݱ�
         * ��ѯ��¼
         * haoning
         * @param lClientID String  �ͻ���ŵ�ID
         * @return ClientInfo  ��ϸ�Ŀͻ���Ϣ
         * @throws Exception`
         */
        public Collection findOtherShareByClientID(long lClientID) 
        throws Exception
        {
            String strSQL = null;
            ClientOtherShareInfo cosinfo = null;
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;            
            Vector vCOS = new Vector(); 

           try
            {
                 con = Database.getConnection();
                    
                    strSQL = " select * from PARTNEROFCLIENT " 
                           + " where nClientID = ? " 
                           + "       and  nPartnerID = -1 ";
                    Log.print(strSQL);       
                    ps = con.prepareStatement(strSQL);
                    ps.setLong(1, lClientID);
                    rs = ps.executeQuery();
                    int i = 0;
                    while (rs != null && rs.next())
                    {
                        i++;
                        Log.print("====="+i+"=====");
                        cosinfo = new ClientOtherShareInfo();
                        cosinfo.setClientID(lClientID);
                        cosinfo.setClientName(rs.getString("SPARTNERNAME"));
                        cosinfo.setScale(rs.getFloat("MSTOCKRATE"));
                        cosinfo.setCardNo(rs.getString("SLOANCARDNO"));
                        cosinfo.setPwd(rs.getString("SLOANCARDPWD"));
                        vCOS.addElement(cosinfo);
                    }
                    if (rs != null)
                    {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null)
                    {
                        ps.close();
                        ps = null;
                    }
                    if (con != null)
                    {
                        con.close();
                        con = null;
                    }
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
                throw new IException("Gen_E001");
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
                throw new IException("Gen_E001");
            }
            finally
            {
                try
                {
                    if (rs != null)
                    {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null)
                    {
                        ps.close();
                        ps = null;
                    }
                    if (con != null)
                    {
                        con.close();
                        con = null;
                    }
                }
                catch (Exception e)
                {
                    log4j.error(e.toString());
                    throw new IException("Gen_E001");
                }
            }
            return vCOS.size() <= 0 ? null : vCOS;
        }
    /**
         * �������޸ģ��ͻ������ɶ�
         * ����  ���ݱ�
         * lID=0,����  lID>0,�޸�
         * @param ClientOtherShareInfo �ͻ������ɶ���Ϣ
         * @return long �ɹ�����ID��Ϣ��ʧ�ܷ���0
         * @throws Exception
         */
        public long saveOtherShareInfo(ClientOtherShareInfo info) 
        throws IException,Exception
        {
            long lResult=-1;
            String strSQL = null;
            Connection con = null;
            PreparedStatement ps = null;

            try
            {
                con = Database.getConnection();
                
                strSQL = " insert into  PARTNEROFCLIENT " +
                    "(nclientid,npartnerid,mstockrate,sloancardno" +
                    ",sloancardpwd,spartnername) " +
                    " values(?,?,?,?,?,?)";
                log4j.info(" �����ɶ�" + strSQL);
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, info.getClientID());
                ps.setLong(2, -1);
                ps.setFloat(3, info.getScale());
                ps.setString(4, info.getCardNo());
                ps.setString(5, info.getPwd());
                ps.setString(6, info.getClientName());
                lResult = ps.executeUpdate();
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (lResult < 0)
                {
                    lResult = -1;
                    return lResult;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
                throw new IException("Gen_E001");
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
                throw new IException("Gen_E001");
            }
            finally
            {
                try
                {
                    if (ps != null)
                    {
                        ps.close();
                        ps = null;
                    }
                    if (con != null)
                    {
                        con.close();
                        con = null;
                    }
                }
                catch (Exception e)
                {
                    log4j.error(e.toString());
                    throw new IException("Gen_E001");
                }
            }
            return lResult;
        }

     

	
    private String getNewClientCode(long lOfficeID) throws RemoteException
    {
        String strCode = "";
        long lNewClientID = -1;
        try
        {
            Sett_ClientDAO dao=new Sett_ClientDAO();
            strCode = dao.getNewClientCode(lOfficeID);
            Log.print(" strCode 1 is " + strCode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        return strCode;
    }


    /*
     *
     *  @return ���ֵ   �����Ϊ�շ��� -1��
     *  @Exception
     */
    private long getMaxClientID(long lOfficeID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQL = null;
        String strMaxCode = "";
        String strCode = "";
        long lMaxID = -1;
        try
        {
            //
            //�ͻ���Ÿ�ʽ : ���´����-��ֵ��� (ex: 01-0001)
            //
            String strOfficeID = DataFormat.formatInt(lOfficeID, 2, true) + "-";
            conn = Database.getConnection();
            strSQL = "select max(scode) from client where scode like '" + strOfficeID + "%' and ASCII(substr(SCODE,length(scode))) <= 64";
            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            long lTempMax = -1;
            if (rs.next())
            {
                strCode = rs.getString(1);
                Log.print("strCode:" + strCode);
                //lMaxID = getTokenLong(strCode, "-", 2);
            }
            strMaxCode = strCode.substring(0, 1) + strCode.substring(3);
            Log.print("strMaxCode:" + strMaxCode);
            lMaxID = Long.parseLong(strMaxCode);
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
        }
        catch (Exception e)
        {
            //Common.log(e.getMessage());
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        //
        return lMaxID;
    }
    
    /**
     * ɾ���ͻ�
     *a.. У��ÿͻ��Ƿ��ڿͻ���Ϣ����ģ�������κ��ۺϻ���������Ϣ��
      b.. У��ÿͻ��Ƿ��ڽ���ģ�������κν��׻����˻���Ϣ��
      c.. У��ÿͻ��Ƿ��ڴ���ģ�������κδ���������ߺ�ͬ��
     * @param lClientID
     * @return >0 ɾ���ɹ� <0 ɾ��ʧ��
     * @throws Exception
     */
    public long deleteClientByID(long lClientID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String strSQLa = null;
        String strSQLb = null;
        String strSQLc = null;
        String strUpdateSQL = null;
        long lReturn = -1;
        try
        {
            conn = Database.getConnection();
            //У�� a.. У��ÿͻ��Ƿ��ڿͻ���Ϣ����ģ�������κ��ۺϻ���������Ϣ��
            strSQLa = " select ID from CENTER_DOCINFO where nClientID="+lClientID+" and nStatusID >0 ";
            ps = conn.prepareStatement(strSQLa);
            Log.print("У�� a:"+strSQLa);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	lReturn = 1;
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            
//          У�� b.. У��ÿͻ��Ƿ��ڽ���ģ�������κν��׻����˻���Ϣ��
            strSQLb = " select ID from sett_Account where nClientID="+lClientID+" and nStatusID >0 ";
            ps = conn.prepareStatement(strSQLb);
            Log.print("У�� b:"+strSQLb);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	lReturn = 2;
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            
//          У�� c.. У��ÿͻ��Ƿ��ڴ���ģ�������κδ���������ߺ�ͬ��
            strSQLc = " select * from loan_loanForm where (nBorrowClientID = "+lClientID+" or nConsignClientID = "+lClientID+") ";
            ps = conn.prepareStatement(strSQLc);
            Log.print("У�� c:"+strSQLc);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	lReturn = 3;
            }
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            
            //ɾ���ͻ�
            if(lReturn >0)
            {
            	Log.print("lReturn = "+lReturn+"���ÿͻ��Ѿ���ʹ�ã�����ִ��");
            	lReturn = -1;
            }
            else
            {
                strUpdateSQL = " update client set nStatusID = "+SETTConstant.RecordStatus.INVALID+" where ID = "+lClientID;
                ps = conn.prepareStatement(strUpdateSQL);
                Log.print("ɾ���ͻ�SQL :"+strUpdateSQL);
                lReturn = ps.executeUpdate();
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
            }
            
            //�ر�����
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
            throw new Exception(e.getMessage());
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
        }
        return lReturn;
    }


    
    


		
}


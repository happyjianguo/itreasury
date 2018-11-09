package com.iss.itreasury.integratedCredit.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;


import com.iss.itreasury.integratedCredit.dataentity.LoanClientInfo;
import  com.iss.itreasury.integratedCredit.dataentity.ClientOtherShareInfo;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author wxsu 20080525 16:02 ��Կͻ���������ʹ�õĿͻ�DAO �����������ܲ�Ҫʹ�� To change the
 *         template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoanClientDao extends SettlementDAO{

	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

	/**
	 * findClient �������пͻ� ���ݿͻ���Ų������пͻ������ؿͻ���ϸ���� ����Client���ݱ� ��ѯ��¼ haoning
	 * 
	 * @param lClientID
	 *            String �ͻ���ŵ�ID
	 * @return ClientInfo ��ϸ�Ŀͻ���Ϣ
	 * @throws RemoteException`
	 */
	public LoanClientInfo findClientByID(long lClientID) throws Exception {
		String strSQL = null;
		LoanClientInfo ci = new LoanClientInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lParentCorpID = -1;
		long lOfficeID = -1;
		long lMaxLoanID = -1; // �ͻ���������ı�ʾ
		long lMaxAssureID = -1; // �ͻ����������ı�ʾ
		String[] strQTClientName = new String[3];
		float[] fQTScale = new float[3];
		String[] strQTCardNo = new String[3];
		String[] strQTPwd = new String[3];
		try {
			// ���ҿͻ���Ϣ(�ͻ�����
			con = Database.getConnection();
			// ���ҿͻ���Ϣ
			strSQL = " select a.*  "
					+ " ,b.sName as ParentCorpName "
					+ " ,b2.sName as ParentCorpName2 "
					+ " ,c.sName as OfficeName "
					+ " ,d.sName as LoanClientType "
					+ " ,e.sName as EnterpriceTypeName "
					// + " ,f.sName as SettClientType "
					+ " from Client a ,Client b,Client b2,Office c  "
					+ " ,LOAN_ClientType d ,SETT_ENTERPRICETYPE e "
					// + " ,SETT_ClientType f "
					+ " where a.NPARENTCORPID1 = b.ID(+) "
					+ " and a.NPARENTCORPID2 = b2.ID(+) "
					+ " and a.NOFFICEID = c.ID(+) "
					+ " and a.NLOANCLIENTTYPEID=d.ID(+) "
					//+ " and a.NCORPNATUREID=e.ID(+) " modify by wangyirong 2009-11-23
					// + " and a.NSETTCLIENTTYPEID=f.ID(+) "
					+ " and a.id=? and a.nStatusID =? ";
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				log4j.info("ȡ�ͻ�������Ϣ");
				ci.setClientID(rs.getLong("ID"));
				lOfficeID = rs.getLong("NOFFICEID");
				ci.setOfficeName(rs.getString("OfficeName")); // ����˾����
				ci.setOfficeID(lOfficeID); // �ͻ����
				ci.setCode(rs.getString("SCODE")); // �ͻ�����
				ci.setName(rs.getString("SNAME"));
				ci.setLicenceCode(rs.getString("SLICENCECODE")); // Ӫҵִ��
				ci.setCorpNatureID(rs.getLong("NCORPNATUREID")); // ��ҵ����
				ci.setCorpNatureName(rs.getString("EnterpriceTypeName"));
				// �ϼ����ܲ��ţ�ĸ��˾��
				ci.setParentCorpID(rs.getLong("NPARENTCORPID1")); // ID
				ci.setParentCorpName(rs.getString("ParentCorpName")); // �ϼ���λ����
				// �ϼ����ܲ���2��ĸ��˾��
				ci.setParentCorpID2(rs.getLong("NPARENTCORPID2"));
				ci.setParentCorpName2(rs.getString("ParentCorpName2")); // �ϼ���λ2����
				// add wxsu 20080625 16:44
				ci.setSmainbusiness(rs.getString("SMAINBUSINESS"));// ��Ӫҵ��
				ci.setSotherbusiness(rs.getString("SOTHERBUSINESS"));// ��Ӫҵ��
				ci.setEschargesector(rs.getString("eschargesector"));// ��ҵ����Ӫҵ���Ƿ��Ǽ���֧�ַ�չ����Ŀ
				ci.setStatutoryname(rs.getString("STATUTORYNAME"));// ��������������
				ci
						.setStatutoryotherduties(rs
								.getString("STATUTORYOTHERDUTIES"));// ��������������ְ��
				ci.setStatutorysphone(rs.getString("STATUTORYSPHONE"));// ���������˵绰
				ci
						.setSauthorizedagentname(rs
								.getString("SAUTHORIZEDAGENTNAME"));// ��Ȩ����������
				ci.setSauotherduties(rs.getString("SAUOTHERDUTIES"));// //
				// ��Ȩ����������ְ��
				ci.setNauphone(rs.getString("nauphone"));// ��Ȩ�����˵绰
				ci.setNcorporateage(rs.getLong("ncorporateage"));// ����
				ci.setStreasurername(rs.getString("streasurername"));// ������������
				ci.setStreasurerduty(rs.getString("streasurerduty"));// ������������ְ��
				ci.setNtreasurerphone(rs.getString("ntreasurerphone"));// ������������ְ��
				ci.setScorporategender(rs.getLong("scorporategender"));// �Ա�
				ci.setScorporatequalifications(rs
						.getString("scorporatequalifications"));// ѧ��
				ci.setScorporateorigin(rs.getString("scorporateorigin"));// ����
				ci.setScorworkexperience(rs.getString("scorworkexperience"));// ��Ҫ��������
				ci.setNcompanynature(rs.getLong("ncompanynature"));// ��������
				ci.setMilitaryandciviliangoods(rs
						.getLong("militaryandciviliangoods"));// ����Ʒ
				ci.setNcompanynature(rs.getLong("ncompanynature"));// ��˾����
				//ci.setDpaidupcapital(rs.getDouble("dpaidupcapital"));
				ci.setScorporatename(rs.getString("scorporatename"));// // ����
				ci.setScustomerinvestors1(rs.getString("scustomerinvestors1"));// �ͻ���ҪͶ����
				ci.setScustomerinvestors2(rs.getString("scustomerinvestors2"));// �ͻ���ҪͶ����2
				ci.setScustomerinvestors3(rs.getString("scustomerinvestors3"));// �ͻ���ҪͶ����3
				ci.setDinvestmentamount1(rs.getDouble("dinvestmentamount1"));// ʵ��Ͷ�ʶ�1
				ci.setDinvestmentamount2(rs.getDouble("dinvestmentamount2"));// ʵ��Ͷ�ʶ�2
				ci.setDinvestmentamount3(rs.getDouble("dinvestmentamount3"));// ʵ��Ͷ�ʶ�3
				ci.setOfthepaidupcapital1(rs.getDouble("ofthepaidupcapital1"));// ռʵ���ʱ�1
				ci.setOfthepaidupcapital2(rs.getDouble("ofthepaidupcapital2"));// ռʵ���ʱ�2
				ci.setOfthepaidupcapital3(rs.getDouble("ofthepaidupcapital3"));// ռʵ���ʱ�3
				ci
						.setTheremaininginvestors(rs
								.getLong("theremaininginvestors"));// ����Ͷ����
				ci.setDsharesamount(rs.getDouble("dsharesamount"));// ��ɽ��
				ci.setNsubsidiary(rs.getLong("nsubsidiary"));// ȫ���ӹ�˾
				ci.setNholdingcompany(rs.getLong("nholdingcompany"));// �عɹ�˾
				ci.setEquitycompany(rs.getLong("equitycompany"));// �ιɹ�˾
				ci.setSubsidiaryplant(rs.getLong("subsidiaryplant"));// ������
				ci.setOrganizationalstructure(rs
						.getString("organizationalstructure"));// ������֯�ṹ������Ҫ˵��������
				ci.setQenterprisehistory(rs.getString("qenterprisehistory"));// ��ҵ��ʷ�ظ�
				ci.setMaindepositarybank(rs.getString("maindepositarybank"));// ��Ҫ��������
				ci.setCorporateculture(rs.getString("corporateculture"));// ��ҵ�Ļ��ص�
				ci.setEschargesector(rs.getString("eschargesector"));// ��ҵ�ϼ����ܲ��Ż�������
				//ci.setNindustrytypeidx(rs.getLong("nindustrytypeidx"));
				ci
						.setNupportthedevelopment(rs
								.getLong("nupportthedevelopment"));
				// ci.setNauphone(rs.getString("NAUPHONE"));

				ci.setEmail(rs.getString("SEMAIL")); // �����ʼ�
				if (rs.getString("SPROVINCE") == null) {
					log4j.info("ʡ�� is null");
				} else {
					ci.setProvince(rs.getString("SPROVINCE")); // ʡ��
					// ci.m_strAddress = ci.m_strProvince;
				}
				if (rs.getString("SCITY") != null) {
					ci.setCity(rs.getString("SCITY")); // ����SCITY
					// ci.m_strAddress = ci.m_strAddress + ci.m_strCity;
				}
				if (rs.getString("SADDRESS") != null) {
					ci.setAddress(rs.getString("SADDRESS"));
				}
				// log4j.info("---------1----------");
				ci.setZipCode(rs.getString("SZIPCODE")); // �ʱ�
				ci.setPhone(rs.getString("SPHONE")); // �绰
				ci.setFax(rs.getString("SFAX")); // ����
				ci.setIsPartner(rs.getLong("NISPARTNER")); // �Ƿ��ǹɶ�
				ci.setAccount(rs.getString("SACCOUNT")); // ����˾�ʻ���
				ci.setBank1(rs.getString("SBANK1")); // ��������1
				ci.setBank2(rs.getString("SBANK2")); // ��������2
				ci.setBank3(rs.getString("SBANK3")); // ��������3
				// log4j.info("----------2--------");
				ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); // �����˻�1
				ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); // �����˻�2
				ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); // �����˻�3
				ci.setLoanCardNo(rs.getString("SLOANCARDNO")); // �����
				ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); // �������
				ci.setCreditLevelID(rs.getLong("NCREDITLEVELID")); // ���õȼ�
				ci.setContacter(rs.getString("SCONTACTER")); // ��ϵ��
				// log4j.info("------3---------");
				// �ͻ�����
				ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); // ��Ӫ����
				ci.setLoanClientType(rs.getString("LoanClientType"));// ��������
				log4j.info("��Ӫ����ͻ�����:" + rs.getString("LoanClientType"));
				ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); // ����
				// ci.setSettClientType(rs.getString("SettClientType"));//��������

				ci.setRiskLevelID(rs.getLong("NRISKLEVELID")); // ���ռ���
				ci.setLegalPerson(rs.getString("SLEGALPERSON")); // ���˴���
				ci.setCaptial(rs.getString("SCAPITAL")); // ע���ʱ�
				// log4j.info("--------4---------");
				// ������λ
				ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
				// ��Ӫ��Χ
				ci.setDealScope(rs.getString("SDEALSCOPE"));
				// -------------------------------����-----------
				// �ֹ�¼���������(����ר��)
				ci.setRiskLevel(rs.getString("SRISKLEVEL"));
				// ��������
				ci.setGeneratorCapacity(rs.getString("SGENERATORCAPACITY"));
				ci.setIntelligenceLevel(rs.getString("STALENTLEVEL"));
				ci.setLegalPersonCode(rs.getString("SLEGALPERSONCODECERT"));
				ci.setInvestAmount(rs.getString("sInvestAmount"));
				ci.setInvestTime(rs.getString("sInvestTime"));
				ci.setFinanceManager(rs.getString("FINANCIALCONTROLOR"));
				log4j.info("=====ȡ�ͻ�������Ϣ����=====");
			}
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			// ���ҿعɵ�λ��Ϣ
			strSQL = " select * from PARTNEROFCLIENT "
					+ " where nClientID = ? " + "       and nPartnerID > 0 ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				ci.setKGClientID(rs.getLong("nPartnerID")); // �عɵ�λID
				ci.setKGClientName(rs.getString("SPARTNERNAME"));
				ci.setFKGScale(rs.getFloat("MSTOCKRATE"));
				ci.setKGCardNo(rs.getString("SLOANCARDNO"));
				ci.setKGPwd(rs.getString("SLOANCARDPWD"));
				// log4j.info("�õ��عɵ�λ��Ϣ");
			}
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			// ���������ɶ���λ
			Collection vCOS = null; // ��ǰҳ�����
			vCOS = findOtherShareByClientID(lClientID);
			ci.setOthersStockHolder(vCOS);
			/*
			 * strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "
			 * and nPartnerID = -1 "; ps = con.prepareStatement(strSQL);
			 * ps.setLong(1, lClientID); rs = ps.executeQuery(); int i = 0;
			 * while (rs != null && rs.next()) { //ci.m_lQTClientID[i] =
			 * rs.getLong("nPartnerID"); //�����ɶ���λ1ID //strQTClientName[i] =
			 * rs.getString("SPARTNERNAME"); //fQTScale[i] =
			 * rs.getFloat("MSTOCKRATE"); //strQTCardNo[i] =
			 * rs.getString("SLOANCARDNO"); //strQTPwd[i] =
			 * rs.getString("SLOANCARDPWD"); //log4j.info("�õ������ɶ���λ��Ϣ" + (i +
			 * 1)); //i++; ClientOtherShareInfo cosinfo = new
			 * ClientOtherShareInfo();
			 * cosinfo.setClientName(rs.getString("SPARTNERNAME"));
			 * cosinfo.setScale(rs.getFloat("MSTOCKRATE"));
			 * cosinfo.setCardNo(rs.getString("SLOANCARDNO"));
			 * cosinfo.setPwd(rs.getString("SLOANCARDPWD"));
			 * vCOS.addElement(cosinfo); }//
			 */
			/*
			 * ci.setQTClientName(strQTClientName); ci.setFQTScale(fQTScale);
			 * ci.setQTCardNo(strQTCardNo); ci.setQTPwd(strQTPwd);//
			 */
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
			// log4j.info("���ҿͻ���Ϣ����");
			/*
			 * /ȡ�ô���������Ϣ //ȡ�õ����������Ϣ //
			 */
			// --------���Ҳ������ͳ�Ʊ���Ϣ-----------//
			long[] lFinanceID = { -1, -1, -1 };
			String[] strName = { "", "", "" };
			String[] strYear = { "", "", "" };
			int iFinance = lFinanceID.length - 1;
			strSQL = " select a.ID ContentID " + " ,a.sDocName " + " ,a.sCode "
					+ " from LOAN_ContractContent a, Client b "
					+ " where  a.nParentID=b.ID  "
					+ " and a.nContractTypeID =? " + " and b.ID = ? "
					+ " order by a.ID desc ";
			log4j.info("�������ͳ��SQL:" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractType.FINANCETJB);
			ps.setLong(2, lClientID);
			rs = ps.executeQuery();
			while (rs != null && rs.next() && iFinance >= 0) {
				// log4j.info("�������ͳ��ContentID---------"+(iFinance+1));
				lFinanceID[iFinance] = rs.getLong("ContentID");
				strName[iFinance] = rs.getString("sDocName");
				// log4j.info("ContentID["+iFinance+"]="+lFinanceID[iFinance]);
				strYear[iFinance] = rs.getString("sCode");
				iFinance--;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			// ����˳���ǡ���ǰ�ꡢȥ�ꡢ����
			if (lFinanceID[0] <= 0) {
				if (lFinanceID[1] <= 0) {
					lFinanceID[0] = lFinanceID[2];
					strName[0] = strName[2];
					strYear[0] = strYear[2];
					lFinanceID[2] = -1;
					strName[2] = "";
					strYear[2] = "";
				} else {
					lFinanceID[0] = lFinanceID[1];
					strName[0] = strName[1];
					strYear[0] = strYear[1];
					lFinanceID[1] = lFinanceID[2];
					strName[1] = strName[2];
					strYear[1] = strYear[2];
					lFinanceID[2] = -1;
					strName[2] = "";
					strYear[2] = "";
				}
			}
			ci.setFINANCETJBID(lFinanceID);
			ci.setFINANCETJBName(strName);
			ci.setFINANCETJBYear(strYear);
			log4j.info(" ���Ҳ������ͳ�ƽ����� ");
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return ci;
	}

	/**
	 * findOtherShareByClientID ���ҿͻ������ɶ���Ϣ ���ݿͻ���Ų������пͻ������ؿͻ���ϸ���� ����Client���ݱ�
	 * ��ѯ��¼ haoning
	 * 
	 * @param lClientID
	 *            String �ͻ���ŵ�ID
	 * @return ClientInfo ��ϸ�Ŀͻ���Ϣ
	 * @throws Exception`
	 */
	public Collection findOtherShareByClientID(long lClientID) throws Exception {
		String strSQL = null;
		ClientOtherShareInfo cosinfo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vCOS = new Vector();

		try {
			con = Database.getConnection();

			strSQL = " select * from PARTNEROFCLIENT "
					+ " where nClientID = ? " + "       and  nPartnerID = -1 ";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			int i = 0;
			while (rs != null && rs.next()) {
				i++;
				Log.print("=====" + i + "=====");
				cosinfo = new ClientOtherShareInfo();
				cosinfo.setClientID(lClientID);
				cosinfo.setClientName(rs.getString("SPARTNERNAME"));
				cosinfo.setScale(rs.getFloat("MSTOCKRATE"));
				cosinfo.setCardNo(rs.getString("SLOANCARDNO"));
				cosinfo.setPwd(rs.getString("SLOANCARDPWD"));
				vCOS.addElement(cosinfo);
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
			throw new IException("Gen_E001");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vCOS.size() <= 0 ? null : vCOS;
	}

	public long updateClientInfo(LoanClientInfo pInfo) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try {
			conn = Database.getConnection();
			strSQL = " update client set SADDRESS=?,SZIPCODE=?, SLICENCECODE=?,SLOANCARDNO=?, SLOANCARDPWD=?, ";
			strSQL += " smainbusiness=?, sotherbusiness=? , statutoryname=?, NUPPORTTHEDEVELOPMENT=?,NINDUSTRYTYPEIDX=?,  " +
					"statutoryotherduties=?,statutorysphone=?,sauthorizedagentname=?,sauotherduties=?,  ";
			strSQL += " nauphone=?,streasurername=?,streasurerduty=?,ntreasurerphone=?,scorporatename=?," +
					"ncorporateage=?,scorporategender=?,scorporatequalifications=?,scorporateorigin=?," +
					"scorworkexperience=?, ";
			strSQL += " ncorporatenature=?,militaryandciviliangoods=?,ncompanynature=?,dpaidupcapital=?," +
					"scustomerinvestors1=?,scustomerinvestors2=?,scustomerinvestors3=?,dinvestmentamount1=?," +
					"dinvestmentamount2=?, ";
			strSQL += " dinvestmentamount3=?,ofthepaidupcapital1=?,ofthepaidupcapital2=?,ofthepaidupcapital3=?," +
					"theremaininginvestors=?,dsharesamount=?,nsubsidiary=?,nholdingcompany=?,equitycompany=?,subsidiaryplant=? ,";
			 strSQL +=" organizationalstructure=?,qenterprisehistory=?,maindepositarybank=?,corporateculture=?," +
			 " eschargesector=? ,slegalpersoncodecert=? ,scapital=? ,SLEGALPERSON=? where ID=?  ";

			ps = conn.prepareStatement(strSQL);

			int n = 1;
		
			ps.setString(n++, pInfo.getAddress());//
			ps.setString(n++, pInfo.getZipCode());//
			ps.setString(n++, pInfo.getLicenceCode());//
			ps.setString(n++, pInfo.getLoanCardNo());//
			ps.setString(n++, pInfo.getLoanCardPwd());//
			ps.setString(n++, pInfo.getSmainbusiness());
			ps.setString(n++, pInfo.getSotherbusiness());
			
			ps.setString(n++, pInfo.getStatutoryname());
			//add by wxsu 20080706
			ps.setLong(n++, pInfo.getNupportthedevelopment());
			//ps.setLong(n++, pInfo.getNindustrytypeidx());
			//end by wxsu 20080706
			ps.setString(n++, pInfo.getStatutoryotherduties());
			ps.setString(n++, pInfo.getStatutorysphone());
			ps.setString(n++, pInfo.getSauthorizedagentname());
			ps.setString(n++, pInfo.getSauotherduties());
			ps.setString(n++, pInfo.getNauphone());
			ps.setString(n++, pInfo.getStreasurername());
			ps.setString(n++, pInfo.getStreasurerduty());
			ps.setString(n++, pInfo.getNtreasurerphone());
			ps.setString(n++, pInfo.getScorporatename());
			ps.setLong(n++, pInfo.getNcorporateage());
			ps.setLong(n++, pInfo.getScorporategender());
			ps.setString(n++, pInfo.getScorporatequalifications());
			ps.setString(n++, pInfo.getScorporateorigin());
			ps.setString(n++, pInfo.getScorworkexperience());
			ps.setLong(n++, pInfo.getNcorporatenature());
			ps.setLong(n++, pInfo.getMilitaryandciviliangoods());
			ps.setLong(n++, pInfo.getNcompanynature());
			//ps.setDouble(n++, pInfo.getDpaidupcapital());
			ps.setString(n++, pInfo.getScustomerinvestors1());
			ps.setString(n++, pInfo.getScustomerinvestors2());
			ps.setString(n++, pInfo.getScustomerinvestors3());
			ps.setDouble(n++, pInfo.getDinvestmentamount1());
			ps.setDouble(n++, pInfo.getDinvestmentamount2());
			ps.setDouble(n++, pInfo.getDinvestmentamount3());
			ps.setDouble(n++, pInfo.getOfthepaidupcapital1());
			ps.setDouble(n++, pInfo.getOfthepaidupcapital2());
			ps.setDouble(n++, pInfo.getOfthepaidupcapital3());
			ps.setLong(n++, pInfo.getTheremaininginvestors());
			ps.setDouble(n++, pInfo.getDsharesamount());
			ps.setLong(n++, pInfo.getNsubsidiary());
			ps.setLong(n++, pInfo.getNholdingcompany());
			ps.setLong(n++, pInfo.getEquitycompany());
			ps.setLong(n++, pInfo.getSubsidiaryplant());
			ps.setString(n++, pInfo.getOrganizationalstructure());
			ps.setString(n++, pInfo.getQenterprisehistory());
			ps.setString(n++, pInfo.getMaindepositarybank());
			ps.setString(n++, pInfo.getCorporateculture());
			ps.setString(n++, pInfo.getEschargesector());
			ps.setString(n++, pInfo.getLegalPersonCode());
			ps.setString(n++, pInfo.getCaptial());
			ps.setString(n++, pInfo.getScorporatename());
			ps.setLong(n++, pInfo.getClientID());

			lResult = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
			if (lResult < 0) {
				log.info("update loan property info error:" + lResult);
				return -1;
			} else {
				return pInfo.getClientID();
			}
		} catch (Exception e) {

			cleanup(ps);
			cleanup(conn);
			e.printStackTrace();
			throw e;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}

	}

}

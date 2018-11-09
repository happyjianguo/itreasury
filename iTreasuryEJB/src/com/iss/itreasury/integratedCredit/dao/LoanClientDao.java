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
 * @author wxsu 20080525 16:02 针对客户信用评级使用的客户DAO 建议其他功能不要使用 To change the
 *         template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoanClientDao extends SettlementDAO{

	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);

	/**
	 * findClient 查找现有客户 根据客户编号查找现有客户，返回客户详细资料 操作Client数据表 查询记录 haoning
	 * 
	 * @param lClientID
	 *            String 客户编号的ID
	 * @return ClientInfo 详细的客户信息
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
		long lMaxLoanID = -1; // 客户贷款调查表的标示
		long lMaxAssureID = -1; // 客户担保调查表的标示
		String[] strQTClientName = new String[3];
		float[] fQTScale = new float[3];
		String[] strQTCardNo = new String[3];
		String[] strQTPwd = new String[3];
		try {
			// 查找客户信息(客户资料
			con = Database.getConnection();
			// 查找客户信息
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
				log4j.info("取客户资料信息");
				ci.setClientID(rs.getLong("ID"));
				lOfficeID = rs.getLong("NOFFICEID");
				ci.setOfficeName(rs.getString("OfficeName")); // 财务公司名称
				ci.setOfficeID(lOfficeID); // 客户编号
				ci.setCode(rs.getString("SCODE")); // 客户名称
				ci.setName(rs.getString("SNAME"));
				ci.setLicenceCode(rs.getString("SLICENCECODE")); // 营业执照
				ci.setCorpNatureID(rs.getLong("NCORPNATUREID")); // 企业性质
				ci.setCorpNatureName(rs.getString("EnterpriceTypeName"));
				// 上级主管部门（母公司）
				ci.setParentCorpID(rs.getLong("NPARENTCORPID1")); // ID
				ci.setParentCorpName(rs.getString("ParentCorpName")); // 上级单位名称
				// 上级主管部门2（母公司）
				ci.setParentCorpID2(rs.getLong("NPARENTCORPID2"));
				ci.setParentCorpName2(rs.getString("ParentCorpName2")); // 上级单位2名称
				// add wxsu 20080625 16:44
				ci.setSmainbusiness(rs.getString("SMAINBUSINESS"));// 主营业务
				ci.setSotherbusiness(rs.getString("SOTHERBUSINESS"));// 兼营业务
				ci.setEschargesector(rs.getString("eschargesector"));// 企业的主营业务是否是集团支持发展的项目
				ci.setStatutoryname(rs.getString("STATUTORYNAME"));// 法定代表人姓名
				ci
						.setStatutoryotherduties(rs
								.getString("STATUTORYOTHERDUTIES"));// 法定代表人其它职务
				ci.setStatutorysphone(rs.getString("STATUTORYSPHONE"));// 法定代表人电话
				ci
						.setSauthorizedagentname(rs
								.getString("SAUTHORIZEDAGENTNAME"));// 授权代理人姓名
				ci.setSauotherduties(rs.getString("SAUOTHERDUTIES"));// //
				// 授权代理人其它职务
				ci.setNauphone(rs.getString("nauphone"));// 授权代理人电话
				ci.setNcorporateage(rs.getLong("ncorporateage"));// 年龄
				ci.setStreasurername(rs.getString("streasurername"));// 财务主管姓名
				ci.setStreasurerduty(rs.getString("streasurerduty"));// 财务主管其它职务
				ci.setNtreasurerphone(rs.getString("ntreasurerphone"));// 财务主管其它职务
				ci.setScorporategender(rs.getLong("scorporategender"));// 性别
				ci.setScorporatequalifications(rs
						.getString("scorporatequalifications"));// 学历
				ci.setScorporateorigin(rs.getString("scorporateorigin"));// 籍贯
				ci.setScorworkexperience(rs.getString("scorworkexperience"));// 主要工作经历
				ci.setNcompanynature(rs.getLong("ncompanynature"));// 法人性质
				ci.setMilitaryandciviliangoods(rs
						.getLong("militaryandciviliangoods"));// 军民品
				ci.setNcompanynature(rs.getLong("ncompanynature"));// 公司性质
				//ci.setDpaidupcapital(rs.getDouble("dpaidupcapital"));
				ci.setScorporatename(rs.getString("scorporatename"));// // 姓名
				ci.setScustomerinvestors1(rs.getString("scustomerinvestors1"));// 客户主要投资人
				ci.setScustomerinvestors2(rs.getString("scustomerinvestors2"));// 客户主要投资人2
				ci.setScustomerinvestors3(rs.getString("scustomerinvestors3"));// 客户主要投资人3
				ci.setDinvestmentamount1(rs.getDouble("dinvestmentamount1"));// 实际投资额1
				ci.setDinvestmentamount2(rs.getDouble("dinvestmentamount2"));// 实际投资额2
				ci.setDinvestmentamount3(rs.getDouble("dinvestmentamount3"));// 实际投资额3
				ci.setOfthepaidupcapital1(rs.getDouble("ofthepaidupcapital1"));// 占实收资本1
				ci.setOfthepaidupcapital2(rs.getDouble("ofthepaidupcapital2"));// 占实收资本2
				ci.setOfthepaidupcapital3(rs.getDouble("ofthepaidupcapital3"));// 占实收资本3
				ci
						.setTheremaininginvestors(rs
								.getLong("theremaininginvestors"));// 其余投资人
				ci.setDsharesamount(rs.getDouble("dsharesamount"));// 入股金额
				ci.setNsubsidiary(rs.getLong("nsubsidiary"));// 全资子公司
				ci.setNholdingcompany(rs.getLong("nholdingcompany"));// 控股公司
				ci.setEquitycompany(rs.getLong("equitycompany"));// 参股公司
				ci.setSubsidiaryplant(rs.getLong("subsidiaryplant"));// 附属厂
				ci.setOrganizationalstructure(rs
						.getString("organizationalstructure"));// 关于组织结构其他需要说明的事项
				ci.setQenterprisehistory(rs.getString("qenterprisehistory"));// 企业历史沿革
				ci.setMaindepositarybank(rs.getString("maindepositarybank"));// 主要开户银行
				ci.setCorporateculture(rs.getString("corporateculture"));// 企业文化特点
				ci.setEschargesector(rs.getString("eschargesector"));// 企业上级主管部门或隶属于
				//ci.setNindustrytypeidx(rs.getLong("nindustrytypeidx"));
				ci
						.setNupportthedevelopment(rs
								.getLong("nupportthedevelopment"));
				// ci.setNauphone(rs.getString("NAUPHONE"));

				ci.setEmail(rs.getString("SEMAIL")); // 电子邮件
				if (rs.getString("SPROVINCE") == null) {
					log4j.info("省份 is null");
				} else {
					ci.setProvince(rs.getString("SPROVINCE")); // 省份
					// ci.m_strAddress = ci.m_strProvince;
				}
				if (rs.getString("SCITY") != null) {
					ci.setCity(rs.getString("SCITY")); // 城市SCITY
					// ci.m_strAddress = ci.m_strAddress + ci.m_strCity;
				}
				if (rs.getString("SADDRESS") != null) {
					ci.setAddress(rs.getString("SADDRESS"));
				}
				// log4j.info("---------1----------");
				ci.setZipCode(rs.getString("SZIPCODE")); // 邮编
				ci.setPhone(rs.getString("SPHONE")); // 电话
				ci.setFax(rs.getString("SFAX")); // 传真
				ci.setIsPartner(rs.getLong("NISPARTNER")); // 是否是股东
				ci.setAccount(rs.getString("SACCOUNT")); // 财务公司帐户号
				ci.setBank1(rs.getString("SBANK1")); // 开户银行1
				ci.setBank2(rs.getString("SBANK2")); // 开户银行2
				ci.setBank3(rs.getString("SBANK3")); // 开户银行3
				// log4j.info("----------2--------");
				ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); // 银行账户1
				ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); // 银行账户2
				ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); // 银行账户3
				ci.setLoanCardNo(rs.getString("SLOANCARDNO")); // 贷款卡号
				ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); // 贷款卡密码
				ci.setCreditLevelID(rs.getLong("NCREDITLEVELID")); // 信用等级
				ci.setContacter(rs.getString("SCONTACTER")); // 联系人
				// log4j.info("------3---------");
				// 客户分类
				ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); // 自营贷款
				ci.setLoanClientType(rs.getString("LoanClientType"));// 中文描述
				log4j.info("自营贷款客户分类:" + rs.getString("LoanClientType"));
				ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); // 结算
				// ci.setSettClientType(rs.getString("SettClientType"));//中文描述

				ci.setRiskLevelID(rs.getLong("NRISKLEVELID")); // 风险级别
				ci.setLegalPerson(rs.getString("SLEGALPERSON")); // 法人代表
				ci.setCaptial(rs.getString("SCAPITAL")); // 注册资本
				// log4j.info("--------4---------");
				// 评级单位
				ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
				// 经营范围
				ci.setDealScope(rs.getString("SDEALSCOPE"));
				// -------------------------------华能-----------
				// 手工录入风险评级(华能专用)
				ci.setRiskLevel(rs.getString("SRISKLEVEL"));
				// 机组容量
				ci.setGeneratorCapacity(rs.getString("SGENERATORCAPACITY"));
				ci.setIntelligenceLevel(rs.getString("STALENTLEVEL"));
				ci.setLegalPersonCode(rs.getString("SLEGALPERSONCODECERT"));
				ci.setInvestAmount(rs.getString("sInvestAmount"));
				ci.setInvestTime(rs.getString("sInvestTime"));
				ci.setFinanceManager(rs.getString("FINANCIALCONTROLOR"));
				log4j.info("=====取客户资料信息结束=====");
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
			// 查找控股单位信息
			strSQL = " select * from PARTNEROFCLIENT "
					+ " where nClientID = ? " + "       and nPartnerID > 0 ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				ci.setKGClientID(rs.getLong("nPartnerID")); // 控股单位ID
				ci.setKGClientName(rs.getString("SPARTNERNAME"));
				ci.setFKGScale(rs.getFloat("MSTOCKRATE"));
				ci.setKGCardNo(rs.getString("SLOANCARDNO"));
				ci.setKGPwd(rs.getString("SLOANCARDPWD"));
				// log4j.info("得到控股单位信息");
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
			// 查找其他股东单位
			Collection vCOS = null; // 当前页结果集
			vCOS = findOtherShareByClientID(lClientID);
			ci.setOthersStockHolder(vCOS);
			/*
			 * strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "
			 * and nPartnerID = -1 "; ps = con.prepareStatement(strSQL);
			 * ps.setLong(1, lClientID); rs = ps.executeQuery(); int i = 0;
			 * while (rs != null && rs.next()) { //ci.m_lQTClientID[i] =
			 * rs.getLong("nPartnerID"); //其他股东单位1ID //strQTClientName[i] =
			 * rs.getString("SPARTNERNAME"); //fQTScale[i] =
			 * rs.getFloat("MSTOCKRATE"); //strQTCardNo[i] =
			 * rs.getString("SLOANCARDNO"); //strQTPwd[i] =
			 * rs.getString("SLOANCARDPWD"); //log4j.info("得到其他股东单位信息" + (i +
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
			// log4j.info("查找客户信息结束");
			/*
			 * /取得贷款调查表信息 //取得担保调查表信息 //
			 */
			// --------查找财务情况统计表信息-----------//
			long[] lFinanceID = { -1, -1, -1 };
			String[] strName = { "", "", "" };
			String[] strYear = { "", "", "" };
			int iFinance = lFinanceID.length - 1;
			strSQL = " select a.ID ContentID " + " ,a.sDocName " + " ,a.sCode "
					+ " from LOAN_ContractContent a, Client b "
					+ " where  a.nParentID=b.ID  "
					+ " and a.nContractTypeID =? " + " and b.ID = ? "
					+ " order by a.ID desc ";
			log4j.info("财务情况统计SQL:" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.ContractType.FINANCETJB);
			ps.setLong(2, lClientID);
			rs = ps.executeQuery();
			while (rs != null && rs.next() && iFinance >= 0) {
				// log4j.info("财务情况统计ContentID---------"+(iFinance+1));
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
			// 新增顺序是——前年、去年、今年
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
			log4j.info(" 查找财务情况统计结束！ ");
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
	 * findOtherShareByClientID 查找客户其他股东信息 根据客户编号查找现有客户，返回客户详细资料 操作Client数据表
	 * 查询记录 haoning
	 * 
	 * @param lClientID
	 *            String 客户编号的ID
	 * @return ClientInfo 详细的客户信息
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

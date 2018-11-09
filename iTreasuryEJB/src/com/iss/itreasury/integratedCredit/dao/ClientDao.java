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
		 * findClient 查找现有客户
		 * 根据客户编号查找现有客户，返回客户详细资料
		 * 操作Client数据表
		 * 查询记录
		 * haoning
		 * @param lClientID String  客户编号的ID
		 * @return ClientInfo  详细的客户信息
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
			long lMaxLoanID = -1; //客户贷款调查表的标示
			long lMaxAssureID = -1; //客户担保调查表的标示
			String[] strQTClientName = new String[3];
			float[] fQTScale = new float[3];
			String[] strQTCardNo = new String[3];
			String[] strQTPwd = new String[3];
			try
			{
				//查找客户信息(客户资料
				con = Database.getConnection();
				//查找客户信息
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
					log4j.info("取客户资料信息");
					ci.setClientID(rs.getLong("ID"));
					lOfficeID = rs.getLong("NOFFICEID");
					//ci.setOfficeName(rs.getString("OfficeName")); //财务公司名称
					ci.setOfficeID(lOfficeID); //客户编号
					ci.setCode(rs.getString("SCODE")); //客户名称
					ci.setName(rs.getString("SNAME"));
					ci.setLicenceCode(rs.getString("SLICENCECODE")); //营业执照
					
					
					
//					ci.setStatutoryname(rs.getString("STATUTORYNAME"));// 法定代表人姓名
//					ci
//							.setStatutoryotherduties(rs
//									.getString("STATUTORYOTHERDUTIES"));// 法定代表人其它职务
//					ci.setStatutorysphone(rs.getString("STATUTORYSPHONE"));// 法定代表人电话
//					ci
//							.setSauthorizedagentname(rs
//									.getString("SAUTHORIZEDAGENTNAME"));// 授权代理人姓名
//					ci.setSauotherduties(rs.getString("SAUOTHERDUTIES"));// //
//					// 授权代理人其它职务
//					ci.setNauphone(rs.getString("nauphone"));// 授权代理人电话
//					ci.setNcorporateage(rs.getLong("ncorporateage"));// 年龄
//					ci.setStreasurername(rs.getString("streasurername"));// 财务主管姓名
//					ci.setStreasurerduty(rs.getString("streasurerduty"));// 财务主管其它职务
//					ci.setNtreasurerphone(rs.getString("ntreasurerphone"));// 财务主管其它职务
//					ci.setScorporategender(rs.getLong("scorporategender"));// 性别
//					ci.setScorporatequalifications(rs
//							.getString("scorporatequalifications"));// 学历
//					ci.setScorporateorigin(rs.getString("scorporateorigin"));// 籍贯
//					ci.setScorworkexperience(rs.getString("scorworkexperience"));// 主要工作经历
//					ci.setNcompanynature(rs.getLong("ncompanynature"));// 法人性质
//					ci.setNcompanynature(rs.getLong("ncompanynature"));// 公司性质
//					ci.setScorporatename(rs.getString("scorporatename"));// // 姓名
//					ci.setNsubsidiary(rs.getLong("nsubsidiary"));// 全资子公司
//					ci.setNholdingcompany(rs.getLong("nholdingcompany"));// 控股公司
//					ci.setEquitycompany(rs.getLong("nequitycompany"));// 参股公司
//					ci.setSubsidiaryplant(rs.getLong("nsubsidiaryplant"));// 附属厂
//					ci.setOrganizationalstructure(rs
//							.getString("sorganizationalstructure"));// 关于组织结构其他需要说明的事项
//					ci.setQenterprisehistory(rs.getString("sqenterprisehistory"));// 企业历史沿革
//					ci.setMaindepositarybank(rs.getString("smaindepositarybank"));// 主要开户银行
//					ci.setCorporateculture(rs.getString("scorporateculture"));// 企业文化特点
//					ci.setEschargesector(rs.getString("eschargesector"));// 企业上级主管部门或隶属于
					
					ci.setEmail(rs.getString("SEMAIL")); //电子邮件
					if (rs.getString("SPROVINCE") == null)
					{
						log4j.info("省份 is null");
					}
					else
					{
						ci.setProvince(rs.getString("SPROVINCE")); //省份
						//ci.m_strAddress = ci.m_strProvince;
					}
					if (rs.getString("SCITY") != null)
					{
						ci.setCity(rs.getString("SCITY")); //城市SCITY
						//ci.m_strAddress = ci.m_strAddress + ci.m_strCity;
					}
					if (rs.getString("SADDRESS") != null)
					{
						ci.setAddress(rs.getString("SADDRESS"));
					}
//					ci.setZipCode(rs.getString("SZIPCODE")); //邮编
//					ci.setPhone(rs.getString("SPHONE")); //电话
//					ci.setFax(rs.getString("SFAX")); //传真
//					ci.setIsPartner(rs.getLong("NISPARTNER")); //是否是股东
//					ci.setAccount(rs.getString("SACCOUNT")); //财务公司帐户号
//					ci.setBank1(rs.getString("SBANK1")); //开户银行1
//					ci.setBank2(rs.getString("SBANK2")); //开户银行2
//					ci.setBank3(rs.getString("SBANK3")); //开户银行3
//					//log4j.info("----------2--------");
//					ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); //银行账户1
//					ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); //银行账户2
//					ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); //银行账户3
//					ci.setLoanCardNo(rs.getString("SLOANCARDNO")); //贷款卡号
//					ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); //贷款卡密码
//					ci.setCreditLevelID(rs.getString("NCREDITLEVELID")); //信用等级
//					ci.setContacter(rs.getString("SCONTACTER")); //联系人
					//客户分类
					//ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); //自营贷款
					//ci.setLoanClientType(rs.getString("LoanClientType"));//中文描述
					//log4j.info("自营贷款客户分类:"+rs.getString("LoanClientType"));
					//ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); //结算
					//ci.setSettClientType(rs.getString("SettClientType"));//中文描述
//					ci.setRiskLevelID(rs.getString("NRISKLEVELID")); //风险级别
//					ci.setLegalPerson(rs.getString("SLEGALPERSON")); //法人代表
					ci.setCaptial(rs.getString("SCAPITAL")); //注册资本
					//log4j.info("--------4---------");
					//评级单位
//					ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
					//经营范围
//					ci.setDealScope(rs.getString("SDEALSCOPE"));
					//-------------------------------华能-----------
					//手工录入风险评级(华能专用)
//					ci.setRiskLevel(rs.getString("SRISKLEVEL"));
					//机组容量
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
//                    ci.setNcorporatenature(rs.getLong("ncorporatenature"));// 法人性质
//                    ci.setNupportthedevelopment(rs.getLong("nupportthedevelopment"));////企业的主营业务是否是集团支持发展的项目
//					ci.setNGuquanjiegou(rs.getLong("nguquanjiegou"));//股权结构
                    log4j.info("=====取客户资料信息结束=====");
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
				//查找控股单位信息
				
				//查找其他股东单位
              //  Collection vCOS = null; //当前页结果集
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
					//ci.m_lQTClientID[i] = rs.getLong("nPartnerID"); //其他股东单位1ID
					//strQTClientName[i] = rs.getString("SPARTNERNAME");
					//fQTScale[i] = rs.getFloat("MSTOCKRATE");
					//strQTCardNo[i] = rs.getString("SLOANCARDNO");
					//strQTPwd[i] = rs.getString("SLOANCARDPWD");
					//log4j.info("得到其他股东单位信息" + (i + 1));
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
				//log4j.info("查找客户信息结束");
				/*/取得贷款调查表信息
				//取得担保调查表信息
				//*/
				//--------查找财务情况统计表信息-----------//
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
				log4j.info("财务情况统计SQL:"+strSQL);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, LOANConstant.ContractType.FINANCETJB);
				ps.setLong(2, lClientID);
				rs = ps.executeQuery();
				while (rs != null && rs.next() && iFinance >= 0)
				{
					//log4j.info("财务情况统计ContentID---------"+(iFinance+1));
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
				//新增顺序是——前年、去年、今年
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
				log4j.info(" 查找财务情况统计结束！ ");
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
		 * 新增（修改）客户详细资料
		 * saveClientInfo  新增（修改）客户的详细资料
		 * 操作Client数据表
		 * 更新相应字段
		 * lID=0,新增  lID>0,修改
		 * 约定：long型参数=-1，string型参数=“”,为未使用项，不参与新增或修改
		 *
		 * @param clientinfo 客户信息
		 * 相应字段：（包含在clientinfo类中）
		 * @param lID 标识
		 * @param strClientName   公司名称
		 * @param strClientNo,    客户编号
		 * @param strLicence,     营业执照
		 * @param lOfficeID,      办事处
		 * @param strAccount,     财务公司账号
		 * @param strBank,        开户银行
		 * @param strAccount      开户银行账号
		 * @param strBank1,       开户银行1
		 * @param strAccount1,    账号1
		 * @param strBank2,       开户银行2
		 * @param strAccount2,    账号2
		 * @param strBank3,       开户银行3
		 * @param strAccount3,    账号3
		 * @param strProvince,    省
		 * @param strCity,        市
		 * @param strAddress1,    地址1
		 * @param strAddress2     地址2
		 * @param strZipCode,     邮编
		 * @param strDeputy,      法人代表
		 * @param strTel,         电话
		 * @param strFax,         传真
		 * @param strMailAddr,    电邮
		 * @param strContact,     联系人
		 * @param strEconomic,    经济性质
		 * @param lGovernmentID,  主管部门表示
		 * @param isShareHolder,  是否股份
		 * @param lClientTypeID,  客户分类
		 * @param lCreditLevel,   信用等级
		 * @param lVentureLevel   风险评级
		 * @param strCapital      注册资本
		 *
		 * @return long 成功返回ID信息，失败返回0
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
			long lResult = -1; //新增、修改成功与否标志
			////////////////////////////
			long lInputUserID = -1;
			long lModifyUserID = clientinfo.getModifyUserID(); //修改人ID
			long lClientID = clientinfo.getClientID(); //客户标识 ==0--新增; >0--修改
			//String strClientNo = clientinfo.m_strCode; //客户编号
			long lOfficeID = clientinfo.getOfficeID();//办事处
			String strClientName = clientinfo.getName();//公司名称
			String strLicence = clientinfo.getLicenceCode(); //营业执照
			String strAccount = clientinfo.getAccount(); //财务公司账号
			String strBank1 = clientinfo.getBank1(); //开户银行1
			String strAccount1 = clientinfo.getBankAccount1(); //账号1
			String strBank2 = clientinfo.getBank2(); //开户银行2
			String strAccount2 = clientinfo.getBankAccount2(); //账号2
			String strBank3 = clientinfo.getBank3(); //开户银行3
			String strAccount3 = clientinfo.getBankAccount3(); //账号3
			String strLoanCardNo = clientinfo.getLoanCardNo(); //贷款卡号
			String strLoanCardPwd = clientinfo.getLoanCardPwd(); //贷款卡密码
			String strProvince = clientinfo.getProvince(); //省
			String strCity = clientinfo.getCity(); //市
			String strAddress = clientinfo.getAddress(); //地址
			String strZipCode = clientinfo.getZipCode(); //邮编
			String strTel = clientinfo.getPhone(); //电话
			String strFax = clientinfo.getFax(); //传真
			String strMailAddr = clientinfo.getEmail(); //电邮
			String strDeputy = clientinfo.getLegalPerson(); //法人代表
			String strContact = clientinfo.getContacter(); //联系人
			//String strEconomic = clientinfo.getEconomyType(); //经济性质
			long lIsShareHolder = clientinfo.getIsPartner(); //是否股东
			long lLoanClientTypeID = clientinfo.getLoanClientTypeID(); //自营贷款客户分类
			long lSettClientTypeID = clientinfo.getSettClientTypeID(); //结算客户分类
			String lCreditLevel = clientinfo.getCreditLevelID(); //信用等级
			String lVentureLevel = clientinfo.getRiskLevelID(); //风险评级
			String strRiskLevel = clientinfo.getRiskLevel();
			long lCorpNatureID = clientinfo.getCorpNatureID(); //企业类型
			long lManagerDeptID = clientinfo.getParentCorpID(); //主管部门ID
			long lManagerDeptID2 = clientinfo.getParentCorpID2(); //主管部门ID
			String strJudgeClient = clientinfo.getJudGelevelClient(); //评级单位
			String dCapital = clientinfo.getCaptial(); //注册资本
			//String strCapital = clientinfo.getCaptial(); //注册资本
            String strFinanceManager = clientinfo.getFinanceManager();//财务主管
			String strDealScope = clientinfo.getDealScope(); //经营范围
			String strGeneratorCapacity = clientinfo.getGeneratorCapacity(); //机组容量
            String strInvestAmount = clientinfo.getInvestAmount();//总投资额
            String strInvestTime = clientinfo.getInvestTime();//投资时间
			String strLegalPersonCode = clientinfo.getLegalPersonCode(); //法人代码证号
			String strIntelligenceLevel = clientinfo.getIntelligenceLevel(); //资质等级
			//控股单位
			long lKGClientID = clientinfo.getKGClientID();
			String strKGClientName = clientinfo.getKGClientName();
			float fKGScale = clientinfo.getFKGScale();
			String strKGCardNo = clientinfo.getKGCardNo();
			String strKGPwd = clientinfo.getKGPwd();
			/*/其他股东单位1-3
			String[] strQTClientName = clientinfo.getQTClientName();
			float[] fQTScale = clientinfo.getFQTScale();
			String[] strQTCardNo = clientinfo.getQTCardNo();
			String[] strQTPwd = clientinfo.getQTPwd();//*/
            Collection cQT = clientinfo.getOthersStockHolder();
			//////////////////////////////
			try
			{
				con = Database.getConnection();
				if (lClientID <= 0) //新增
				{
					System.out.println("================新增客户================");
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
					ps = null; //首先获得客户的新id
					strSQL = "select nvl(max(ID)+1,1) nID from client";
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						lClientID = rs.getLong("nID");
						log4j.info("增加新的客户，获得客户ID" + lClientID);
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
						log4j.info("新增纪录失败，返回值：" + lResult);
						return -1;
					}
					return lClientID;
					/* end of qqgd's adding */
				}
				if (lClientID > 0) //修改
				{
                    System.out.println("================修改客户================");
                    //客户名称已经存在
                    strSQL = "select SName from client "
                           + " where sName='" + clientinfo.getName() + "'"
                           + "   and id <> "+clientinfo.getClientID()
                           + "   and nstatusid = "
                           + Constant.RecordStatus.VALID;
                    Log.print("校验客户名称"+strSQL);       
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
					log4j.info("修改客户SQL=" + strSQL);
					ps = con.prepareStatement(strSQL);
					//对条件赋值
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
					//先删除该客户所有的股东信息 再添加更改后的股东
					strSQL = " delete PARTNEROFCLIENT where nClientID = ?";
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lClientID);
					ps.executeUpdate();
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					//更新控股单位信息
					if (lKGClientID > 0)
					{
						//重新添加控股股东
						strSQL = " insert into  PARTNEROFCLIENT  values(?,?,?,?,?,?) ";
						log4j.info(" 控股单位 SQL=" + strSQL);
						ps = con.prepareStatement(strSQL);
						//对条件赋值
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
					/*/重新添加其它股东单位信息1-3
					for (int i = 0; i < strQTClientName.length; i++)
					{
						if (strQTClientName[i] != null && strQTClientName[i] != "")
						{
							strSQL = " insert into  PARTNEROFCLIENT  values(?,?,?,?,?,?)";
							log4j.info(" 其它股东" + i + " SQL=" + strSQL);
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
                        Log.print("重新添加其它股东单位信息");
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
         * findOtherShareByClientID 查找客户其他股东信息
         * 根据客户编号查找现有客户，返回客户详细资料
         * 操作Client数据表
         * 查询记录
         * haoning
         * @param lClientID String  客户编号的ID
         * @return ClientInfo  详细的客户信息
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
         * 新增（修改）客户其他股东
         * 操作  数据表
         * lID=0,新增  lID>0,修改
         * @param ClientOtherShareInfo 客户其他股东信息
         * @return long 成功返回ID信息，失败返回0
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
                log4j.info(" 其它股东" + strSQL);
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
     *  @return 最大值   如果表为空返回 -1；
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
            //客户编号格式 : 办事处编号-增值编号 (ex: 01-0001)
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
     * 删除客户
     *a.. 校验该客户是否在客户信息中心模块中有任何综合或者授信信息。
      b.. 校验该客户是否在结算模块中有任何交易或者账户信息。
      c.. 校验该客户是否在贷款模块中有任何贷款申请或者合同。
     * @param lClientID
     * @return >0 删除成功 <0 删除失败
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
            //校验 a.. 校验该客户是否在客户信息中心模块中有任何综合或者授信信息。
            strSQLa = " select ID from CENTER_DOCINFO where nClientID="+lClientID+" and nStatusID >0 ";
            ps = conn.prepareStatement(strSQLa);
            Log.print("校验 a:"+strSQLa);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	lReturn = 1;
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            
//          校验 b.. 校验该客户是否在结算模块中有任何交易或者账户信息。
            strSQLb = " select ID from sett_Account where nClientID="+lClientID+" and nStatusID >0 ";
            ps = conn.prepareStatement(strSQLb);
            Log.print("校验 b:"+strSQLb);
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
            
//          校验 c.. 校验该客户是否在贷款模块中有任何贷款申请或者合同。
            strSQLc = " select * from loan_loanForm where (nBorrowClientID = "+lClientID+" or nConsignClientID = "+lClientID+") ";
            ps = conn.prepareStatement(strSQLc);
            Log.print("校验 c:"+strSQLc);
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
            
            //删除客户
            if(lReturn >0)
            {
            	Log.print("lReturn = "+lReturn+"：该客户已经被使用，不能执行");
            	lReturn = -1;
            }
            else
            {
                strUpdateSQL = " update client set nStatusID = "+SETTConstant.RecordStatus.INVALID+" where ID = "+lClientID;
                ps = conn.prepareStatement(strUpdateSQL);
                Log.print("删除客户SQL :"+strUpdateSQL);
                lReturn = ps.executeUpdate();
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
            }
            
            //关闭连接
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


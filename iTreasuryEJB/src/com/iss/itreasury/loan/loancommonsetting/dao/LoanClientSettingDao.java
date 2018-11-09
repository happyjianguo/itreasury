/**
 * 
 */
package com.iss.itreasury.loan.loancommonsetting.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientOtherShareInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.QueryClientInfo;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_ClientDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author weihuang
 *
 */
public class LoanClientSettingDao {
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
//						+ " ,d.sName as LoanClientType "
//						+ " ,e.sName as EnterpriceTypeName "
						//+ " ,f.sName as SettClientType "
						+ " from Client a ,Client b,Client b2,Office c  "
//						+ " ,LOAN_ClientType d ," 
//						+ "SETT_ENTERPRICETYPE e "
						//+ " ,SETT_ClientType f "
						+ " where a.NPARENTCORPID1 = b.ID(+) "
						+ " and a.NPARENTCORPID2 = b2.ID(+) "
						+ " and a.NOFFICEID = c.ID(+) "
//						+ " and a.NLOANCLIENTTYPEID=d.ID(+) "
//						+ " and a.NCORPNATUREID=e.ID(+) "
						//+ " and a.NSETTCLIENTTYPEID=f.ID(+) "
						+ " and a.id=? and a.nStatusID =? ";
				log4j.info("SQL=" + strSQL);
				System.out.println("clinetid========================" + lClientID);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lClientID);
				ps.setLong(2, Constant.RecordStatus.VALID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					log4j.info("取客户资料信息");
					ci.setClientID(rs.getLong("ID"));
					lOfficeID = rs.getLong("NOFFICEID");
					ci.setOfficeName(rs.getString("OfficeName")); //财务公司名称
					ci.setOfficeID(lOfficeID); //客户编号
					ci.setCode(rs.getString("SCODE")); 
					ci.setName(rs.getString("SNAME"));//客户名称
					ci.setName1(rs.getString("SNAME1"));//客户名称1
					ci.setName2(rs.getString("SNAME2"));//客户名称2
					ci.setLicenceCode(rs.getString("SLICENCECODE")); //营业执照
//					ci.setCorpNatureID(rs.getLong("NCORPNATUREID")); //企业性质
//					ci.setCorpNatureName( rs.getString("EnterpriceTypeName"));
					//上级主管部门（母公司）
					ci.setParentCorpID(rs.getLong("NPARENTCORPID1")); //ID
					ci.setParentCorpName(rs.getString("ParentCorpName")); //上级单位名称				
					//上级主管部门2（母公司）
					ci.setParentCorpID2(rs.getLong("NPARENTCORPID2"));                 
					ci.setParentCorpName2(rs.getString("ParentCorpName2")); //上级单位2名称
					
					ci.setFinanceManager(rs.getString("financialcontrolor"));
                
                
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
					log4j.info("----------测试运行点--1----------");
					ci.setZipCode(rs.getString("SZIPCODE")); //邮编
					ci.setPhone(rs.getString("SPHONE")); //电话
					ci.setFax(rs.getString("SFAX")); //传真
					ci.setIsPartner(rs.getLong("NISPARTNER")); //是否是股东
					ci.setNIsfinancingcollection(rs.getLong("NISFINANCINGCOLLECTION"));//是否资金规集
					ci.setAccount(rs.getString("SACCOUNT")); //财务公司账户号
					ci.setBank1(rs.getString("SBANK1")); //开户银行1
					ci.setBank2(rs.getString("SBANK2")); //开户银行2
					ci.setBank3(rs.getString("SBANK3")); //开户银行3
					log4j.info("----------测试运行点----2--------");
					ci.setBankAccount1(rs.getString("SEXTENDACCOUNT1")); //银行账户1
					ci.setBankAccount2(rs.getString("SEXTENDACCOUNT2")); //银行账户2
					ci.setBankAccount3(rs.getString("SEXTENDACCOUNT3")); //银行账户3
					ci.setLoanCardNo(rs.getString("SLOANCARDNO")); //贷款卡号
					ci.setLoanCardPwd(rs.getString("SLOANCARDPWD")); //贷款卡密码
					ci.setStrCreditLevelID(rs.getString("NCREDITLEVELID")); //信用等级
					ci.setContacter(rs.getString("SCONTACTER")); //联系人
					log4j.info("----------测试运行点---3---------");
					//客户分类
					//ci.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID")); //自营贷款
					//ci.setLoanClientType(rs.getString("LoanClientType"));//中文描述
					//log4j.info("自营贷款客户分类:"+rs.getString("LoanClientType"));
					//ci.setSettClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); //结算
					//ci.setSettClientType(rs.getString("SettClientType"));//中文描述
                
					ci.setStrRiskLevelID(rs.getString("NRISKLEVELID")); //风险级别
					ci.setLegalPerson(rs.getString("SLEGALPERSON")); //法人代表
					ci.setCaptial(rs.getString("SCAPITAL")); //注册资本
					log4j.info("----------测试运行点---4---------");
					//评级单位
					ci.setJudGelevelClient(rs.getString("SJUDGELEVELCLIENT"));
					//经营范围
					ci.setDealScope(rs.getString("SDEALSCOPE"));
					//-------------------------------华能-----------
					//手工录入风险评级(华能专用)
					ci.setRiskLevel(rs.getString("SRISKLEVEL"));
					//机组容量
					ci.setGeneratorCapacity(rs.getString("SGENERATORCAPACITY"));
					ci.setIntelligenceLevel(rs.getString("STALENTLEVEL"));
					ci.setLegalPersonCode(rs.getString("SLEGALPERSONCODECERT"));
                    ci.setEnrol(rs.getTimestamp("dtEnrol"));
					ci.setSEFCString1(rs.getString("SEFCString1"));
					ci.setSEFCString2(rs.getString("SEFCString2"));
					ci.setSEFCString3(rs.getString("SEFCString3"));
					ci.setSEFCString4(rs.getString("SEFCString4"));
					log4j.info("nExtendAttribute1:"+rs.getLong("nExtendAttribute1"));
					ci.setExtendAttributeID1(rs.getLong("nExtendAttribute1"));
					ci.setExtendAttributeID2(rs.getLong("nExtendAttribute2"));
					ci.setExtendAttributeID3(rs.getLong("nExtendAttribute3"));
					ci.setExtendAttributeID4(rs.getLong("nExtendAttribute4"));
					//ci.setIndustryTypeID(rs.getLong("nIndustryTypeID"));
					log4j.info("----------测试运行点---5---------");
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
				strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "       and nPartnerID > 0 ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lClientID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					ci.setKGClientID(rs.getLong("nPartnerID")); //控股单位ID
					ci.setKGClientName(rs.getString("SPARTNERNAME"));
					ci.setFKGScale(rs.getFloat("MSTOCKRATE"));
					ci.setKGCardNo(rs.getString("SLOANCARDNO"));
					ci.setKGPwd(rs.getString("SLOANCARDPWD"));
					//log4j.info("得到控股单位信息");
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
				
				
				//查找其他股东单位(客户资料维护专用)
				Collection vCOS = null; //当前页结果集
				vCOS=findOtherShareByClientID(lClientID);
				ci.setOthersStockHolder(vCOS);
				
				
				//查找其他股东单位（除客户资料维护以外的其他功能用）
				strSQL = " select * from PARTNEROFCLIENT " + " where nClientID = ? " + "       and  nPartnerID = -1 ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lClientID);
				rs = ps.executeQuery();
				int i = 0;
				while (rs != null && rs.next() && i < ci.getQTClientName().length && i <4 )
				{
					//ci.m_lQTClientID[i] = rs.getLong("nPartnerID"); //其他股东单位1ID
					strQTClientName[i] = rs.getString("SPARTNERNAME");
					fQTScale[i] = rs.getFloat("MSTOCKRATE");
					strQTCardNo[i] = rs.getString("SLOANCARDNO");
					strQTPwd[i] = rs.getString("SLOANCARDPWD");
					//log4j.info("得到其他股东单位信息" + (i + 1));
					i++;
				}
				ci.setQTClientName(strQTClientName);
				ci.setFQTScale(fQTScale);
				ci.setQTCardNo(strQTCardNo);
				ci.setQTPwd(strQTPwd);
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
				
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
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
		throws Exception
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
			//String strClientName = clientinfo.m_strName; //公司名称
			//String strClientNo = clientinfo.m_strCode; //客户编号
			//long lOfficeID = clientinfo.m_lOfficeID; //办事处
			long lOfficeID = clientinfo.getOfficeID();
			String strClientName = clientinfo.getName();
			String strClientName1 = clientinfo.getName1();//客户别称1
			String strClientName2 = clientinfo.getName2();//客户别称2
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
			long lIszjgj=clientinfo.getNIsfinancingcollection();
			//long lLoanClientTypeID = clientinfo.getLoanClientTypeID(); //自营贷款客户分类
			//long lSettClientTypeID = clientinfo.getSettClientTypeID(); //结算客户分类
			String lCreditLevel = clientinfo.getStrCreditLevelID(); //信用等级
			String lVentureLevel = clientinfo.getStrRiskLevelID(); //风险评级
			String strRiskLevel = clientinfo.getRiskLevel();
			//long lCorpNatureID = clientinfo.getCorpNatureID(); //企业类型
			long lManagerDeptID = clientinfo.getParentCorpID(); //主管部门ID
			long lManagerDeptID2 = clientinfo.getParentCorpID2(); //主管部门ID
			String strJudgeClient = clientinfo.getJudGelevelClient(); //评级单位
			//double dCapital = clientinfo.getCaptial(); //注册资本
			String strCapital = clientinfo.getCaptial(); //注册资本
			String strDealScope = clientinfo.getDealScope(); //经营范围
			String strGeneratorCapacity = clientinfo.getGeneratorCapacity(); //机组容量
			String strLegalPersonCode = clientinfo.getLegalPersonCode(); //法人代码证号
			String strIntelligenceLevel = clientinfo.getIntelligenceLevel(); //资质等级
			//控股单位
			long lKGClientID = clientinfo.getKGClientID();
			String strKGClientName = clientinfo.getKGClientName();
			float fKGScale = clientinfo.getFKGScale();
			String strKGCardNo = clientinfo.getKGCardNo();
			String strKGPwd = clientinfo.getKGPwd();
			
			//其他股东单位1-3  非客户资料维护
			String[] strQTClientName = clientinfo.getQTClientName();
			float[] fQTScale = clientinfo.getFQTScale();
			String[] strQTCardNo = clientinfo.getQTCardNo();
			String[] strQTPwd = clientinfo.getQTPwd();

			//其他股东单位1-3  客户资料维护
			Collection cQT = clientinfo.getOthersStockHolder();
            
            //haier 注册时间
            Timestamp dtEnrol = clientinfo.getEnrol();
			String strSEFCString1 = DataFormat.formatString(clientinfo.getSEFCString1());
			String strSEFCString2 = DataFormat.formatString(clientinfo.getSEFCString2());
			String strSEFCString3 = DataFormat.formatString(clientinfo.getSEFCString3());
			String strSEFCString4 = DataFormat.formatString(clientinfo.getSEFCString4());
			//上海电气扩展属性 
			long lExtendAttributeID1 = clientinfo.getExtendAttributeID1();
			long lExtendAttributeID2 = clientinfo.getExtendAttributeID2();
			long lExtendAttributeID3 = clientinfo.getExtendAttributeID3();
			long lExtendAttributeID4 = clientinfo.getExtendAttributeID4();
			//上海电气 新增行业分类一 行业分类二 by zntan 2005-1-19
			//long lIndustryTypeID = clientinfo.getIndustryTypeID();
			//////////////////////////////
			
			/**
			 * 需求变更，modified by 张颜 
			 * 2004/03/29
			 * 客户新增‘财务主管’属性
			 */
			String strFinanceManager = DataFormat.formatString(clientinfo.getFinanceManager());
			try
			{
				con = Database.getConnection();
				if (lClientID <= 0) //新增
				{
//					/* qqgd add this code for add a new client */
//					strSQL = "select SName from client where sName='" + clientinfo.getName() + "'";
//					ps = con.prepareStatement(strSQL);
//					rs = ps.executeQuery();
//					if (rs.next())
//					{
//						rs.close();
//						rs = null;
//						ps.close();
//						ps = null;
//						con.close();
//						con = null;
//						throw new IException("Loan_E101");
//					}
//					rs.close();
//					rs = null;
//					ps.close();
//					ps = null;
//					strSQL = "select SName from client where sLicenceCode ='" + clientinfo.getLicenceCode() + "'";
//					ps = con.prepareStatement(strSQL);
//					rs = ps.executeQuery();
//					if (rs.next())
//					{
//						rs.close();
//						rs = null;
//						ps.close();
//						ps = null;
//						con.close();
//						con = null;
//						throw new IException("Loan_E102");
//					}
//					rs.close();
//					rs = null;
//					ps.close();
//					ps = null; 
					//首先获得客户的新id
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
							+ " ,nInputUserID, dtInput, nStatusID,sName1,sName2) "
							+ " values (?, ?, ?, ?, ?, ?, SYSDATE,?,?,?)";
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
					ps.setString(8,strClientName1);
					ps.setString(9,strClientName2);
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
					
//					/* qqgd add this code for add a new client */
//					strSQL = "select SName from client where sName='" + clientinfo.getName() + "' and ID != " + clientinfo.getClientID();
//					ps = con.prepareStatement(strSQL);
//					rs = ps.executeQuery();
//					if (rs.next())
//					{
//						rs.close();
//						rs = null;
//						ps.close();
//						ps = null;
//						con.close();
//						con = null;
//						throw new IException("Loan_E101");
//					}
//					rs.close();
//					rs = null;
//					ps.close();
//					ps = null;
//					strSQL = "select SName from client where sLicenceCode ='" + clientinfo.getLicenceCode() + "' and ID != " + clientinfo.getClientID();
//					ps = con.prepareStatement(strSQL);
//					rs = ps.executeQuery();
//					if (rs.next())
//					{
//						rs.close();
//						rs = null;
//						ps.close();
//						ps = null;
//						con.close();
//						con = null;
//						throw new IException("Loan_E102");
//					}
//					rs.close();
//					rs = null;
//					ps.close();
//					ps = null; 

					
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
					strSQL +=" ,FINANCIALCONTROLOR=?"; // modified by 张颜，2004/03/29
					strSQL +=" ,sName=?";// modified by 张颜，2004/03/29
					strSQL +=" ,sName1=?";//modified by weihuang
					strSQL +=" ,sName2=?";//modified by weihuang
					if (lIszjgj > -1)
					{
						strSQL += " ,NISFINANCINGCOLLECTION=? "; //29
					}
					if (lIsShareHolder > -1)
					{
						strSQL += " ,NISPARTNER=? "; //30
					}
					//if (lLoanClientTypeID > -1)
					//{
					//	strSQL += " ,NLOANCLIENTTYPEID=? "; //31
					//} 
					if (lCreditLevel != null && !lCreditLevel.equals(""))
					{
						strSQL += " ,NCREDITLEVELID=? "; //32
					}
					if (lManagerDeptID > 0)
					{
						strSQL += " ,NPARENTCORPID1=? "; //33
					}
//					if (lCorpNatureID > 0)
//					{
//						strSQL += " ,NCORPNATUREID=? "; //34
//					}

					/*  TOCONFIG—TODELETE  */
					/*
					 * 产品化不再区分项目 
					 * ninh 
					 * 2005-03-24
					 */

					//---------------------------大桥--------------------------
					//if (Env.getProjectName().equals(Constant.ProjectName.MBEC))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.MBEC)
//					{
//						//strLegalPersonCode//法人代码证号SLEGALPERSONCODECERT
//						//strIntelligenceLevel//资质等级STALENTLEVEL
//						strSQL += " ,sLegalPersonCodeCert=? "; //35
//						strSQL += " ,sTalentLevel=? "; //36
//					}///
//					//-----------------------------华能---------------------------
//					//if (Env.getProjectName().equals(Constant.ProjectName.HN))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN)
//					{//*
//						if (lManagerDeptID2 > 0)
//						{
//							strSQL += " ,NPARENTCORPID2=? "; //33
//						}
//						if (lSettClientTypeID > -1)
//						{
//							strSQL += " ,NSETTCLIENTTYPEID=? "; //31
//						} //*/
//						strSQL += " ,SGENERATORCAPACITY=? "; //37
//					}
//                    //---------------------------haier--------------------------
//                    //if (Env.getProjectName().equals(Constant.ProjectName.HAIER))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HAIER)
//                    {
//                        strSQL += " ,sLegalPersonCodeCert=? "; //35
//                        strSQL += " ,dtEnrol=? "; //38
//                    }///
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						if (lSettClientTypeID > -1)
//						{
//							strSQL += " ,NSETTCLIENTTYPEID=? "; //
//						}
//						strSQL += " ,sLegalPersonCodeCert=? "; //
//						strSQL += " ,SEFCString1=? "; //
//						strSQL += " ,SEFCString2=? "; //
//						strSQL += " ,SEFCString3=? "; //
//						strSQL += " ,SEFCString4=? "; //
//						
//						strSQL += " ,nExtendAttribute1=? "; //
//						strSQL += " ,nExtendAttribute2=? "; //
//						strSQL += " ,nExtendAttribute3=? "; //
//						strSQL += " ,nExtendAttribute4=? "; //
//						
//						//sefc 新增行业分类一、行业分类二 by zntan 2005-1-19
//						strSQL += " ,nIndustryTypeID=? ";
//					}

					if (lManagerDeptID2 > 0)
					{
						strSQL += " ,NPARENTCORPID2=? ";
					}
//					if (lSettClientTypeID > -1)
//					{
//						strSQL += " ,NSETTCLIENTTYPEID=? ";
//					}
					//法人代码证号SLEGALPERSONCODECERT
					//资质等级STALENTLEVEL
					strSQL += " ,sLegalPersonCodeCert=? ";
					strSQL += " ,sTalentLevel=? ";
					strSQL += " ,SGENERATORCAPACITY=? ";
					strSQL += " ,dtEnrol=? ";
					//strSQL += " ,nIndustryTypeID=? ";
			
					/*  TOCONFIG—END  */
					
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
					ps.setString(nIndex, strCapital); //20
					nIndex++;
					ps.setString(nIndex, strDealScope); //21
					nIndex++;
					ps.setString(nIndex, strLoanCardNo); //22
					nIndex++;
					ps.setString(nIndex, strLoanCardPwd); //23
					nIndex++;
					ps.setString(nIndex, strRiskLevel); //24
					nIndex++;
					ps.setString(nIndex, strFinanceManager); //24
					nIndex++;
					ps.setString(nIndex, strClientName); //24
					nIndex++;
					ps.setString(nIndex, strClientName1); //25
					nIndex++;
					ps.setString(nIndex, strClientName2); //26
					nIndex++;
					if (lIszjgj > -1)
					{
					    ps.setLong(nIndex, lIszjgj); //30
						nIndex++;
					}
					if (lIsShareHolder > -1)
					{
						ps.setLong(nIndex, lIsShareHolder); //30
						nIndex++;
					}
					//if (lLoanClientTypeID > -1)
					//{
					//	ps.setLong(nIndex, lLoanClientTypeID); //31
					//	nIndex++;
					//} 
					if (lCreditLevel !=null && !lCreditLevel.equals(""))
					{
						ps.setString(nIndex, lCreditLevel); //32
						nIndex++;
					}
					if (lManagerDeptID > 0)
					{
						ps.setLong(nIndex, lManagerDeptID); //33
						nIndex++;
					}
//					if (lCorpNatureID > 0)
//					{
//						ps.setLong(nIndex, lCorpNatureID); //34
//						nIndex++;
//					}

					/*  TOCONFIG—TODELETE  */
					/*
					 * 产品化不再区分项目 
					 * ninh 
					 * 2005-03-24
					 */

					//--------------------------大桥--------------------------
					//if (Env.getProjectName().equals(Constant.ProjectName.MBEC))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.MBEC)
//					{
//						//strLegalPersonCode//法人代码证号
//						//strIntelligenceLevel//资质等级
//						ps.setString(nIndex, strLegalPersonCode); //35
//						nIndex++;
//						ps.setString(nIndex, strIntelligenceLevel); //36
//						nIndex++;
//					}//*/
//					//-----------------------------华能-------------------------
//					//if (Env.getProjectName().equals(Constant.ProjectName.HN))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN)
//					{//*
//						if (lManagerDeptID2 > 0)
//						{
//							ps.setLong(nIndex, lManagerDeptID2); //33
//							nIndex++;
//						}
//						if (lSettClientTypeID > -1)
//						{
//							ps.setLong(nIndex, lSettClientTypeID); //31
//							nIndex++;
//						} //*/
//						ps.setString(nIndex, strGeneratorCapacity); //37
//						nIndex++;
//					}
//                    //--------------------------haier--------------------------
//                    //if (Env.getProjectName().equals(Constant.ProjectName.HAIER))
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HAIER)
//                    {
//                        ps.setString(nIndex, strLegalPersonCode); //35
//                        nIndex++;
//                        ps.setTimestamp(nIndex,dtEnrol); //38
//                        nIndex++;
//                    }///
//					if(Config.GLOBAL.getProjectType() == Constant.ProjectType.SEFC)
//					{
//						if (lSettClientTypeID > -1)
//						{
//							ps.setLong(nIndex, lSettClientTypeID); //31
//							nIndex++;
//						}
//						ps.setString(nIndex, strLegalPersonCode); //
//						nIndex++;
//						ps.setString(nIndex, strSEFCString1); //
//						nIndex++;
//						ps.setString(nIndex, strSEFCString2); //
//						nIndex++;
//						ps.setString(nIndex, strSEFCString3); //
//						nIndex++;
//						ps.setString(nIndex, strSEFCString4); //
//						nIndex++;
//						
//						ps.setLong(nIndex, lExtendAttributeID1); //
//						nIndex++;
//						ps.setLong(nIndex, lExtendAttributeID2); //
//						nIndex++;
//						ps.setLong(nIndex, lExtendAttributeID3); //
//						nIndex++;
//						ps.setLong(nIndex, lExtendAttributeID4); //
//						nIndex++;
//						
//						ps.setLong(nIndex, lIndustryTypeID);
//						nIndex++;
//					}

					if (lManagerDeptID2 > 0)
					{
						ps.setLong(nIndex, lManagerDeptID2);
						nIndex++;
					}
//					if (lSettClientTypeID > -1)
//					{
//						ps.setLong(nIndex, lSettClientTypeID);
//						nIndex++;
//					}
					ps.setString(nIndex, strLegalPersonCode);
					nIndex++;
					ps.setString(nIndex, strIntelligenceLevel);
					nIndex++;
					ps.setString(nIndex, strGeneratorCapacity);
					nIndex++;					
					ps.setTimestamp(nIndex,dtEnrol);
					nIndex++;
//					ps.setLong(nIndex, lIndustryTypeID);
//					nIndex++;
			
					/*  TOCONFIG—END  */
					
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
					
					//重新添加其它股东单位信息1-3   （客户资料设置专用）
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
					else
					{
						//重新添加其它股东单位信息1-3  （非客户资料设置）
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
				throw ie; 
			}
			catch (Exception e)
			{
				//lResult = -1;
				log4j.error(e.toString());
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
				
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
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
	 * 查找是否有重复用户名
	 * 客户资料新增时使用
	 */
	public long checkName(String name, long clientid)throws Exception
	{  long result=-1;
	   Connection con=null;
	   PreparedStatement ps=null;
	   ResultSet rs=null;
	   String sql="";
	
		try {
			con=Database.getConnection();
			sql="select count(id) from client where sname='"+name+"' and id!="+clientid;
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs!=null&& rs.next()) {
				result=rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rs!=null) {
				rs.close();
				rs=null;
				
			}
			if (ps!=null) {
				ps.close();
				ps=null;
				
			}
			if (con!=null) {
				con.close();
				con=null;
				
			}
		}
		return result;
	}
	/*
	 * 查找是否有重复用户编码
	 * 客户资料新增时使用
	 */
	public long checkCode(String code, long clientid)throws Exception
	{  long result=-1;
	   Connection con=null;
	   PreparedStatement ps=null;
	   ResultSet rs=null;
	   String sql="";
	
		try {
			con=Database.getConnection();
			sql="select count(id) from client where scode='"+code+"' and id!="+clientid;
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs!=null&& rs.next()) {
				result=rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rs!=null) {
				rs.close();
				rs=null;
				
			}
			if (ps!=null) {
				ps.close();
				ps=null;
				
			}
			if (con!=null) {
				con.close();
				con=null;
				
			}
		}
		return result;
	}	
	public Collection findByMultiOption(QueryClientInfo qInfo)
    throws LoanDAOException, SQLException
{
String strSelect = "";
String strSQL = "";
ArrayList v = new ArrayList();
Connection con=null;
ResultSet rs=null;
PreparedStatement ps=null;
long clientID = qInfo.getId();

long queryPurpose = qInfo.getQueryPurpose();


long pageLineCount = qInfo.getPageLineCount();
long pageNo = qInfo.getPageNo();

long desc = qInfo.getDesc();
String orderParamString = qInfo.getOrderParamString();
long recordCount = -1;
long pageCount = -1;
long rowNumStart = -1;
long rowNumEnd = -1;

try
{
   
    //计算记录总数
    if (queryPurpose == 1) //for modify
    {
        strSQL = "";
        strSelect = " select count(*) ";
        strSQL = " from client a " + " where 1 = 1 "
                + " and a.nStatusID = "
                + Constant.RecordStatus.VALID;
        //+ " and a.InputUserID = " + userID;
    }

    //////////////////////查询条件////////////////////////////////////////////////////
    if (clientID > 0)
    {
        strSQL += " and a.ID = " + clientID;
    }
   
    
    
    ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
    int nIndex = 0;
    nIndex = orderParamString.indexOf(".");
    if (nIndex > 0)
    {
        if (orderParamString.substring(0, nIndex).equalsIgnoreCase("Client"))
        {
            strSQL += " order by a."
                    + orderParamString.substring(nIndex + 1);
        }
    } else
    {
        strSQL += " order by a.ID";
    }
    if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
    {
        strSQL += " desc";
    }
    
    System.out.println(strSelect + strSQL);

    try
    {  con=Database.getConnection();
        ps=con.prepareStatement(strSelect + strSQL);
         rs =ps.executeQuery();
        if (rs != null && rs.next())
        {
            recordCount = rs.getLong(1);
        }
        if(rs != null)
		{
			rs.close();
			rs = null;
		}
        if(ps!=null)
        {
        	ps.close();
        	ps=null;
        }
    } catch (ITreasuryDAOException e)
    {
        throw new LoanDAOException("批量查询贷款类型分类设置笔数产生错误", e);
    } catch (SQLException e)
    {
        throw new LoanDAOException("批量查询贷款类型分类设置笔数产生错误", e);
    }
    pageCount = recordCount / pageLineCount;
    if ((recordCount % pageLineCount) != 0)
    {
        pageCount++;
    }
//System.out.println("$$$$$$$$$recordCount"+recordCount);
//System.out.println("$$$$$$$$$pageCount"+pageCount);
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //返回需求的结果集
    rowNumStart = (pageNo - 1) * pageLineCount + 1;
    rowNumEnd = rowNumStart + pageLineCount - 1;
    strSelect = " select a.* ";
    strSQL = " select * from ( select aa.*,rownum r from ( "
            + strSelect + strSQL;
    strSQL += " ) aa ) where r between " + rowNumStart + " and "
            + rowNumEnd;
    //System.out.println(strSQL);
    try
    {
        ps=con.prepareStatement(strSQL);
        ResultSet rs1 = ps.executeQuery();
        while (rs1 != null && rs1.next())
        {
        	QueryClientInfo info = new QueryClientInfo();
            info.setId(rs1.getLong("ID")); //id
            
            info.setCode(rs1.getString("sCode")); //编号
            info.setName(rs1.getString("sName")); //名称                    
            info.setName1(rs1.getString("sname1"));
            info.setName2(rs1.getString("sname2"));
            info.setLicenceCode(rs1.getString("SLICENCECODE"));
            //info.setIndustryTypeID(rs1.getLong("NINDUSTRYTYPEID"));
            info.setCreditLevelID(rs1.getLong("NCREDITLEVELID"));
            info.setExtendAttributeID1(rs1.getLong("NEXTENDATTRIBUTE1"));
            info.setExtendAttributeID2(rs1.getLong("NEXTENDATTRIBUTE2"));
            //info.setAccount(rs1.getString("SACCOUNT"));
           // info.setLoanCardNo(rs1.getString("SLOANCARDNO"));
           // info.setProvince(rs1.getString("SPROVINCE"));
           // info.setCity(rs1.getString("SCITY"));
           // info.setAddress(rs1.getString("SADDRESS"));
          //  info.setNIsfinancingcollection(rs1.getLong("NISFINANCINGCOLLECTION"));
           // info.setSettClientTypeID(rs1.getLong("NSETTCLIENTTYPEID"));
           // info.setLoanClientTypeID(rs1.getLong("NLOANCLIENTTYPEID"));
            //表中没有的字段
            info.setRecordCount(recordCount); //记录数
            info.setPageCount(pageCount); //页数
            v.add(info);
        }
        if(rs1 != null)
		{
			rs1.close();
			rs1 = null;
		}
    } catch (SQLException e)
    {
        throw new LoanDAOException("批量查询贷款类型分类设置产生错误", e);
    }
   
} catch (Exception e)
{
    // TODO Auto-generated catch block
    e.printStackTrace();
} finally
{
	 if(rs != null)
		{
			rs.close();
			rs = null;
		}
	 
     if(ps!=null)
     {
     	ps.close();
     	ps=null;
     }
     if(con!=null)
     {
    	 con.close();
    	 con=null;
     }
}
return (v.size() > 0 ? v : null);
}
	/** 根据id查结算或贷款客户分类名
	 * @throws SQLException 
	 * 
	 */
	public String findName(String tablename,long id) throws SQLException {
		String name="";
		String strSQL="";
		Connection con = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			strSQL = " select sName from "+tablename +" where nStatusID = " + Constant.RecordStatus.VALID + " and id="+id+" order by sCode "; 
			con = Database.getConnection();
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
           if (rs != null && rs.next())
            {
             name=rs.getString(1);
            
            }
           if(rs!=null)
           {
        	   rs.close();
        	   rs=null;
           }
           if(ps!=null)
           {
        	   ps.close();
        	   ps=null;
           }
           if(con!=null)
           {
        	   con.close();
        	   con=null;
           }
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			 if(rs!=null)
	           {
	        	   rs.close();
	        	   rs=null;
	           }
	           if(ps!=null)
	           {
	        	   ps.close();
	        	   ps=null;
	           }
	           if(con!=null)
	           {
	        	   con.close();
	        	   con=null;
	           }
		}
		return name;
	}
	/** 根据id查行业分类名
	 * @throws SQLException 
	 * 
	 */
	public String findIndustryTypeName(long id) throws SQLException {
		String name="";
		String strSQL="";
		Connection con = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			strSQL = " select sName from sett_IndustryType where nstatusid=" + Constant.RecordStatus.VALID + " and id="+id; 
			con = Database.getConnection();
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
           if (rs != null && rs.next())
            {
             name=rs.getString(1);
            
            }
           if(rs!=null)
           {
        	   rs.close();
        	   rs=null;
           }
           if(ps!=null)
           {
        	   ps.close();
        	   ps=null;
           }
           if(con!=null)
           {
        	   con.close();
        	   con=null;
           }
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			 if(rs!=null)
	           {
	        	   rs.close();
	        	   rs=null;
	           }
	           if(ps!=null)
	           {
	        	   ps.close();
	        	   ps=null;
	           }
	           if(con!=null)
	           {
	        	   con.close();
	        	   con=null;
	           }
		}
		return name;
	}
	/** 根据id,扩展属性(有4个)查行业分类名
	 * @throws SQLException 
	 * 
	 */
	public String findExtendAttributeName(long id,long ExtendAttribute) throws SQLException {
		String name="";
		String strSQL="";
		Connection con = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			strSQL = " select sName from Sett_ExtendAttribute where nAttributeID="+ExtendAttribute+ " and id="+id+" and nStatus="+ Constant.RecordStatus.VALID; 
			con = Database.getConnection();
            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
           if (rs != null && rs.next())
            {
             name=rs.getString(1);
            
            }
           if(rs!=null)
           {
        	   rs.close();
        	   rs=null;
           }
           if(ps!=null)
           {
        	   ps.close();
        	   ps=null;
           }
           if(con!=null)
           {
        	   con.close();
        	   con=null;
           }
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			 if(rs!=null)
	           {
	        	   rs.close();
	        	   rs=null;
	           }
	           if(ps!=null)
	           {
	        	   ps.close();
	        	   ps=null;
	           }
	           if(con!=null)
	           {
	        	   con.close();
	        	   con=null;
	           }
		}
		return name;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LoanClientSettingDao dao = new LoanClientSettingDao();
		//ClientInfo info = new ClientInfo();
		//info.setClientID(7);
		//info.setExtendAttributeID1(3);
		try
		{
			QueryClientInfo info=new QueryClientInfo();
			info.setId(3);
			info.setPageLineCount(20);
			info.setPageNo(1);
			info.setQueryPurpose(1);
			Collection c=null;
			c = dao.findByMultiOption(info);
			//System.out.println("name="+dao.findExtendAttributeName(6,2));
			//System.out.println("+++++++++"+info.getExtendAttributeID1());
			/*info.setName1("sun");
			info.setName2("amd");
			info.setNIsfisnancingcollection(1);
			dao.saveClientInfo(info);*/
			//System.out.println("result="+r);
		} catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}
}

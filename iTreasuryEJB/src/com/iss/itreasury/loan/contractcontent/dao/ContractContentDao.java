/*
 * Created on 2003-10-27
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contractcontent.dao;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendApplyInfo;
import com.iss.itreasury.loan.contractcontent.dataentity.ContractContentInfo;
import com.iss.itreasury.loan.contractcontent.dataentity.ContractContentQueryInfo;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.discount.dao.DiscountDao;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.util.LOANEnv;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.dataentity.AutoFileInfo;
import com.iss.itreasury.loan.repayplan.dao.RepayPlanDao;
import com.iss.itreasury.loan.repayplan.dataentity.*;
import com.iss.itreasury.loan.repayplan.bizlogic.*;
import java.rmi.RemoteException;

/**
 * @author hyzeng
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractContentDao {
	public static String strSeparator = java.io.File.separator;

	public static String TEMPLET_SEPERATOR = " &&& ";

	public static String PAGE_SEPERATOR = " :: ";

	public static String CONTENT_SEPERATOR = " ;; ";

	public static String SEPERATOR = "  ";

	private static Log4j log4j = null;

	private int arrLen = 10000;

	public ContractContentDao() {

		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	// ///////////////////////////////////////////////////////////////////////
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	private String formatString(String str) throws SQLException {
		return (str == null ? "" : str);
	}

	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(CallableStatement cs) throws SQLException {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}

	// ///////////////////////////////////////////////////////////////////////////
	/**
	 * 修改合同文件（只有浦发才用） Create Date: 2006-03-16
	 * 
	 * @author wangli
	 * @param strName
	 *            文件名（包括路径）
	 * @param SCONTRACTCODE
	 *            要更新的合同编号
	 * @return String 修改文件的名字，长度为0，不成功
	 * @exception Exception
	 */
	public String ChangeContent(String strName, long lStepNo,
			String SCONTRACTCODE) {
		int i = 0;
		long lResult = 0;
		String sTmp = new String(SCONTRACTCODE);
		String sOldContent = "";
		String sbTmp = new String();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String data = Env.getSystemDateString();
			String year = "";
			String month = "";
			String day = "";
			year = data.substring(0, 4);
			month = data.substring(5, 7);
			day = data.substring(8, 10);

			year = year + month + day;
			long nIndex = -1;

			if (strName != null && strName.length() > 0) {
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();
				while ((record = br.readLine()) != null) {
					// System.out.println(" record.substring(8,
					// record.length()-1)="+record.substring(8,
					// record.length()-1));
					nIndex = record.indexOf(sTmp);
					System.out.println("nIndex：" + nIndex);
					if (nIndex != -1) {
						// System.out.println("更新前的合同文本全内容："+record);
						SCONTRACTCODE = year + sTmp.substring(8, sTmp.length());
						// System.out.println("更新后的合同号："+SCONTRACTCODE);
						// sbTmp=record.substring(0,nIndex)+SCONTRACTCODE+record.substring(nIndex+sTmp.length(),record.length()-1);
						sbTmp = record.replaceAll(sTmp, SCONTRACTCODE);
						// System.out.println("更新后的合同文本全内容："+sbTmp);
					} else {
						sbTmp = record;
						// System.out.println("合同文本全内容里没有合同编号时的合同内容："+sbTmp);
					}
				}
				sOldContent = sbTmp;
				br.close();
				fr.close();
			}
			// update content
			java.io.File f = new java.io.File(strName);
			if (f.exists() && lStepNo == 1) {
				// f.delete();
			}
			FileWriter fw = new FileWriter(strName);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sOldContent);
			pw.close();
			fw.close();

		} catch (Exception e) {
			strName = "";
			e.printStackTrace();
			System.out.println(e.toString());
			// 异常不往外抛出，即使修改合同文本出错，要保证合同激活成功
			// throw e;
		} finally {

		}
		return strName;
	}

	/**
	 * 保存文件 Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            文件名（包括路径），新增时文件名为空
	 * @param lStepNo
	 *            页号
	 * @param strContent
	 *            文件内容
	 * @return String 新增或修改文件的名字，长度为0，不成功
	 * @exception Exception
	 */
	public String saveContent(String strName, long lStepNo, String strContent)
			throws Exception {
		int i = 0;
		long lResult = 0;
		String sTmp = new String(strContent);
		String sOldContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try {

			// get the old content
			if (strName != null && strName.length() > 0) {
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();
				while ((record = br.readLine()) != null) {
					sbTmp = sbTmp.append(record);

				}
				sOldContent = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			} else // get the file name if create a new file
			{
				AutoFileInfo fileInfo = new AutoFileInfo();
				fileInfo.setFileType("txt");
				strName = AutoFileBean
						.getDestPath(Constant.DocType.LOANCONTRACTCONTENT);
				strName += AutoFileBean.getFileName(fileInfo);
			}

			// get the new content
			int nSIndex = 0;
			int nEIndex = 0;
			int nIndex = 0;
			String strData = "";
			for (i = 0; i < lStepNo; i++) {

				nSIndex = nEIndex;
				if (i > 0) {
					nIndex = nSIndex + 4;
				}

				if (sOldContent != null && sOldContent.length() > 0) {
					nEIndex = sOldContent.indexOf(PAGE_SEPERATOR, nIndex);
				} else {
					nEIndex = 0;
				}

				if (nEIndex <= nSIndex) {
					i++;
					break;
				}
			}

			if (i < lStepNo) {
				sOldContent = sOldContent + PAGE_SEPERATOR + strContent;
			} else {
				if (nSIndex > 0) {
					nSIndex = nSIndex + 4;
				}

				if (nEIndex > 0) {
					sOldContent = sOldContent.substring(0, nSIndex)
							+ strContent + sOldContent.substring(nEIndex);
				} else if (sOldContent == null || sOldContent.length() <= 0) {
					sOldContent = strContent;
				} else {
					sOldContent = sOldContent.substring(0, nSIndex)
							+ strContent;
				}
			}

			// update content
			java.io.File f = new java.io.File(strName);
			if (f.exists() && lStepNo == 1) {
				// f.delete();
			}
			FileWriter fw = new FileWriter(strName);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sOldContent);
			pw.close();
			fw.close();

		} catch (Exception e) {
			strName = "";
			System.out.println(e.toString());
			throw e;
		} finally {

		}
		return strName;
	}

	/**
	 * 得到文件内容 Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            文件名（包括路径）
	 * @param lStepNo
	 *            页号
	 * @return String 文件内容
	 * @exception Exception
	 */
	public String getFileContent(String strName, long lStepNo) throws Exception {
		int i = 0;
		String sWholeContent = "";
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// get the whole content
			sWholeContent = getFileContent(strName);

			// get the content
			if (sWholeContent.length() > 0) {
				int nSIndex = 0;
				int nEIndex = 0;
				String strData = "";
				for (i = 0; i < lStepNo; i++) {
					nSIndex = nEIndex;
					nEIndex = sWholeContent
							.indexOf(PAGE_SEPERATOR, nSIndex + 4);
					if (nEIndex <= nSIndex) {
						i++;
						break;
					}
				}
				if (i < lStepNo) {
					sContractContent = "";
				} else {
					if (nSIndex > 0) {
						nSIndex = nSIndex + 4;
					}

					if (nEIndex > 0) {
						sContractContent = sWholeContent.substring(nSIndex,
								nEIndex);
					} else {
						sContractContent = sWholeContent.substring(nSIndex);
					}
				}
			} else {
				sContractContent = "";
			}
		} catch (Exception e) {
			sContractContent = "";
			System.out.println(e.toString());
			throw e;
		} finally {

		}
		return sContractContent;
	}

	/**
	 * 得到文件全部内容 Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            文件名（包括路径）
	 * @return String 文件内容
	 * @exception Exception
	 */
	public String getFileContent(String strName) throws IException {
		int i = 0;
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();

		BufferedReader br = null;
		System.out.println(" Content File Name : " + strName);
		try {
			// get the whole content
			if (strName != null && strName.length() > 0) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(strName)));
				int c;

				while ((c = br.read()) != -1) {
					sbTmp.append((char) c);
				}
				sContractContent = sbTmp.toString();
				br.close();

			} else {
				sContractContent = "";
			}
		} catch (Exception e) {
			sContractContent = "";
			log4j.error(e.getMessage());
			throw new IException("Loan_E106");
		}
		sContractContent = sContractContent != null ? sContractContent : "";
		// if( Env.getCPF_OS().equalsIgnoreCase("unix"))
		// {
		// try
		// {
		// sContractContent = new String ( sContractContent.trim().getBytes
		// ("ISO8859_1" ) , "GBK" ) ;
		// }
		// catch (UnsupportedEncodingException e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }
		System.out.println("Contract Content : \n");
		System.out.println(sContractContent);
		return sContractContent;
	}

	/**
	 * 合并文件模板和文件内容 Create Date: 2003-10-15
	 * 
	 * @param strTempletName
	 *            文件模板名
	 * @param strContentName
	 *            文件内容名
	 * @return String 合并的字符串
	 * @exception Exception
	 */
	public String mergeContractContent(String strContentName,
			String strTempletName) throws IException, Exception {
		StringBuffer sbTmp = new StringBuffer();
		String strTemplet = "";
		String strContent = "";
		String strUnite = "";
		String record = new String();
		String arrContent[] = new String[arrLen];
		FileReader fr = null;
		BufferedReader br = null;
		int nArrayLength = 0;
		int nIndex1; // PAGE_SEPERATOR???÷??????
		int nIndex2; // CONTENT_SEPERATOR???÷??????
		int nTmp = 0;

		try {
			// get strContent
			strContent = getFileContent(strContentName);

			// get the templet content:strTempletContent
			strTemplet = getFileContent(strTempletName);

			// String to Array
			String strPage = "";
			// ????????????????
			nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
			while (nIndex1 >= 0) {
				strPage = strContent.substring(0, nIndex1);
				// ??????????????????
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				while (nIndex2 >= 0) {
					if (nTmp < arrLen) {
						if (nIndex2 == 0) {
							arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						} else {
							arrContent[nTmp] = strPage.substring(0, nIndex2);
						}
					}
					strPage = strPage.substring(nIndex2 + 4);
					nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
					nTmp++;

				}

				if (nTmp < arrLen) {
					if (!strPage.equals("")) {
						arrContent[nTmp] = strPage;
					} else {
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
				strContent = strContent.substring(nIndex1 + 4);
				nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
				nTmp++;

			}
			// ×??ó????
			strPage = strContent.substring(0);
			// ??????????????????
			nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
			while (nIndex2 >= 0) {
				if (nTmp < arrLen) {
					if (nIndex2 == 0) {
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					} else {
						arrContent[nTmp] = strPage.substring(0, nIndex2);
					}
				}
				strPage = strPage.substring(nIndex2 + 4);
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				nTmp++;

			}
			if (nTmp < arrLen) {
				if (!strPage.equals("")) {
					arrContent[nTmp] = strPage;
				} else {
					arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			nArrayLength = nTmp + 1;

			nTmp = 0;
			nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			while (nIndex1 >= 0) {

				if (nTmp < nArrayLength) {
					strUnite = strUnite + strTemplet.substring(0, nIndex1)
							+ SEPERATOR + arrContent[nTmp] + SEPERATOR;
					nTmp++;
				} else {
					strUnite = strUnite + strTemplet.substring(0, nIndex1)
							+ SEPERATOR + SEPERATOR;
				}
				strTemplet = strTemplet.substring(nIndex1 + 4);
				nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			}
			strUnite = strUnite + strTemplet;

			if (strTempletName.equals(Env.UPLOAD_PATH
					+ "loan/template/yhchdhptxxy.txt")) {
				strContent = getFileContent(strContentName, 5);

				int nIndex = 0; // ","???÷??????
				String sTemp = strContent;
				nTmp = 0;
				if (sTemp.length() > 0) {
					nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0) {
						sTemp = sTemp.substring(nIndex + 4);
						nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					nTmp++;
				}
				String arrCont[] = new String[nTmp];

				if (strContent.length() > 0) {
					nIndex = 0; // ","???÷??????
					nTmp = 0;
					nIndex = strContent.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0) {
						arrCont[nTmp] = strContent.substring(0, nIndex);
						strContent = strContent.substring(nIndex + 4);
						nIndex = strContent.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					if (nTmp < arrLen) {
						arrCont[nTmp] = strContent;
					}
					nTmp++;
				}

				sTemp = "";
				nIndex = 0;
				for (int x = 0; x < nTmp / 10; x++) {
					sTemp += " <tr bgcolor=#FFFFFF > ";
					sTemp += "<td class=td-topright align=center >" + (x + 1)
							+ "</td>";
					for (int i = 0; i < 10; i++) {
						if (nIndex < arrLen) {
							if (i == 9) {
								sTemp += "<td class=td-top align=center ><font face=????>"
										+ arrCont[nIndex++] + "</font></td>";
							} else {
								sTemp += "<td class=td-topright align=center ><font face=????>"
										+ arrCont[nIndex++] + "</font></td>";
							}
						}
					}
					sTemp += " </tr> ";
				}

				sTemp += "</table></body></html>";
				strUnite += sTemp;
			} else if (strTempletName.equals(Env.UPLOAD_PATH
					+ "loan/template/shychdhptxxy.txt")) {
				strContent = getFileContent(strContentName, 6);

				int nIndex = 0; // ","???÷??????
				String sTemp = strContent;
				nTmp = 0;
				if (sTemp.length() > 0) {
					nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0) {
						sTemp = sTemp.substring(nIndex + 4);
						nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					nTmp++;
				}
				String arrCont[] = new String[nTmp];

				if (strContent.length() > 0) {
					nIndex = 0; // ","???÷??????
					nTmp = 0;
					nIndex = strContent.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0) {
						arrCont[nTmp] = strContent.substring(0, nIndex);
						strContent = strContent.substring(nIndex + 4);
						nIndex = strContent.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					if (nTmp < arrLen) {
						arrCont[nTmp] = strContent;
					}
					nTmp++;
				}

				sTemp = "";
				nIndex = 0;
				for (int x = 0; x < nTmp / 10; x++) {
					sTemp += " <tr bgcolor=#FFFFFF > ";
					sTemp += "<td class=td-topright align=center >" + (x + 1)
							+ "</td>";
					for (int i = 0; i < 10; i++) {
						if (nIndex < arrLen) {
							if (i == 9) {
								sTemp += "<td class=td-top align=center ><font face=????>"
										+ arrCont[nIndex++] + "</font></td>";
							} else {
								sTemp += "<td class=td-topright align=center ><font face=????>"
										+ arrCont[nIndex++] + "</font></td>";
							}
						}
					}
					sTemp += " </tr> ";
				}
				sTemp += "</table></body></html>";
				strUnite += sTemp;
			}
		} catch (IException ie) {
			log4j.error("3:" + ie.getMessage());
			throw new IException(ie.getMessage());
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strUnite;
	}

	/**
	 * 组合出正确的地址格式 Create Date: 2003-10-15
	 * 
	 * @param strProvince
	 *            省
	 * @param strCity
	 *            市
	 * @param strAddress
	 *            地址
	 * @return String 返回正确的地址格式
	 * @exception Exception
	 */
	private String getAddress(String strProvince, String strCity,
			String sAddress) throws Exception {
		String strAddress = "";
		try {
			if (strProvince != null && strProvince.trim().length() > 0) {
				strAddress = strProvince + "省";
			}
			if (strCity != null && strCity.trim().length() > 0) {
				strAddress = strAddress + strCity + "市";
			}
			if (sAddress == null) {
				sAddress = "";
			}
			strAddress = strAddress + sAddress;
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strAddress;
	}

	/**
	 * 得到合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT表中的ID
	 * @param lPageNo
	 *            页号
	 * @return String 合同文本内容
	 * @exception Exception
	 */
	public String getContractContent(long lID, long lPageNo) throws IException,
			Exception {
		ContractContentInfo info = new ContractContentInfo();
		String sContent = "";

		try {
			info = findByID(lID);
			String sFileName = info.getDocName();
			// modify by wjliu --2007/3/16 加一个为NULL的条件
			if (null != sFileName && !sFileName.equals(""))
			// if (!sFileName.equals(""))
			{
				sContent = getFileContent(sFileName, lPageNo);
			} else {
				// modified by mzh_fu 2007/03/21 
				throw new IException("没有指定合同文件！");
				//sContent = "";				
			}
		} catch (IException ie) {
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sContent;
	}

	/**
	 * 填写文本缺省值 人名币借款合同（自营 短期/中长期） Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addXTDK(long lID) throws RemoteException, Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 15;
		int THIRDPAGECOUNT = 13;
		int FOURTHPAGECOUNT = 3;
		int FIFTHPAGECOUNT = 5;
		int SIXPAGECOUNT = 3;
		int SEVENPAGECOUNT = 6;
		int ELEVENPAGECOUNT = 8;
		int TWELFTHPAGECOUNT = 10;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = (info.getLoanReason() == null) ? "" : info
					.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			String sTemp = "0";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}

			if (info.getLoanTypeID() == LOANConstant.LoanType.ZY)// 先用大类
			{
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ)
			// {
			// if (info.getIntervalNum() <= 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }

			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ)
			// {
			// if (info.getIntervalNum() > 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }

			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			sTemp = "0";
			if (info.getIntervalNum() > 0) {
				sTemp = String.valueOf(info.getIntervalNum());
			}
			saPageContent[index++] = sTemp;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			// saPageContent[index++] = "";
			// saPageContent[index++] = "";
			// saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate());
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			// 初始化EJB
			Collection cPlan = null;
			try {
				RepayPlanHome repayplanHome = (RepayPlanHome) EJBObject
						.getEJBHome("RepayPlanHome");
				RepayPlan repayplan = repayplanHome.create();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			String sDate = "";
			long lPAY = 0;
			long lRepay = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						lPAY++;
						sDate = DataFormat.formatDate(rp_info.tsPlanDate);
					}

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						lRepay++;
					}
				}
			}

			sContent = "";
			index = 0;

			if (lPAY == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lPAY == 1 && !sDate.equals("")) {
				saPageContent[index++] = sDate.substring(0, 4);
				saPageContent[index++] = sDate.substring(5, 7);
				saPageContent[index++] = sDate.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			if (lPAY > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			index = 0;
			saPageContent[index++] = (info.getOther() == null) ? "" : info
					.getOther();

			if (lRepay == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lRepay > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			String sAssure = "";
			String sPledge = "";
			String sImpawn = "";
			Collection c = contractDao.getContractContent(info.getContractID());
			if (c != null) {
				Iterator it = c.iterator();
				ContractContentInfo ccInfo = new ContractContentInfo();
				if (it != null) {
					while (it.hasNext()) {
						ccInfo = (ContractContentInfo) it.next();
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE) {
							sAssure = ccInfo.getCode();
						}
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE) {
							sPledge = ccInfo.getCode();
						}
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN) {
							sImpawn = ccInfo.getCode();
						}

					}
				}
			}

			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = sAssure;
			saPageContent[index++] = "";
			saPageContent[index++] = sPledge;
			saPageContent[index++] = "";
			saPageContent[index++] = sImpawn;

			for (i = 0; i < SEVENPAGECOUNT; i++) {
				if (i == SEVENPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// 合同第9页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// 合同第10页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 10, sContent);

			// 合同第11页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				if (i == ELEVENPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 11, sContent);

			// 合同第12页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < TWELFTHPAGECOUNT; i++) {
				if (i == TWELFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 12, sContent);

			// 合同第13页
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			/*
			 * if (cPlan != null) { sTemp += " <b>附件：</b> <p>&nbsp;</p> \n";
			 * sTemp += " <table border=0 width=100%> \n"; sTemp += " <tr> ";
			 * sTemp += " <td width=50% valign=top> \n"; sTemp += " <table
			 * border=1 width=100%> \n"; sTemp += " <tr> "; sTemp += "
			 * <td colspan=2 align=middle> 提款方式 </td> \n"; sTemp += " </tr> ";
			 * sTemp += " <tr> "; sTemp += " <td width=50% align=middle > 提款日期
			 * </td> \n"; sTemp += " <td width=50% align=middle > 提款金额 </td>
			 * \n"; sTemp += " </tr> \n";
			 * 
			 * iter = cPlan.iterator(); for (i = 0; iter.hasNext(); i++) {
			 * RepayPlanInfo rp_info = new RepayPlanInfo(); rp_info =
			 * (RepayPlanInfo) iter.next();
			 * 
			 * if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) { sTemp += "
			 * <tr> \n"; sTemp += " <td width=50% align=middle > " +
			 * DataFormat.formatDate(rp_info.tsPlanDate) + " </td> \n"; sTemp += "
			 * <td width=50% align=right > " +
			 * DataFormat.formatListAmount(rp_info.dAmount) + " </td> \n"; sTemp += "
			 * </tr> \n"; dTemp += rp_info.dAmount; } }
			 * 
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > 合计：
			 * </td> \n"; sTemp += " <td width=50%  align=right > " +
			 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n"; sTemp += "
			 * <td width=50% valign=top> \n"; sTemp += " <table border=1
			 * width=100%> \n"; sTemp += " <tr> \n"; sTemp += "
			 * <td colspan=2 align=middle> 还款方式 </td> \n"; sTemp += " </tr> \n";
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > 还款日期
			 * </td> \n"; sTemp += " <td width=50% align=middle > 还款金额 </td>
			 * \n"; sTemp += " </tr> \n"; }
			 * 
			 * dTemp = 0; if (cPlan != null) { iter = cPlan.iterator(); for (i =
			 * 0; iter.hasNext(); i++) { RepayPlanInfo rp_info = new
			 * RepayPlanInfo(); rp_info = (RepayPlanInfo) iter.next(); if
			 * (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) { sTemp += "
			 * <tr> \n"; sTemp += " <td width=50% align=middle > " +
			 * DataFormat.formatDate(rp_info.tsPlanDate) + " </td> \n"; sTemp += "
			 * <td width=50% align=right > " +
			 * DataFormat.formatListAmount(rp_info.dAmount) + " </td> \n"; sTemp += "
			 * </tr> \n"; dTemp += rp_info.dAmount; } }
			 * 
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > 合计：
			 * </td> \n"; sTemp += " <td width=50%  align=right > " +
			 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; }//
			 */

			if (cPlan != null) {
				sTemp = getPlanTab(cPlan);
			}

			sContent = sTemp + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 13, sContent);

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 保证合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            担保标示（LOAN_LOANCONTRACTASSURE表中的标示）
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addAssure(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 19;
		int SECONDPAGECOUNT = 13;
		int FOURTHPAGECOUNT = 1;
		int FIFTHPAGECOUNT = 8;
		int SIXPAGECOUNT = 10;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;

			if (info.getLoanTypeID() == LOANConstant.LoanType.ZY
					|| info.getLoanTypeID() == LOANConstant.LoanType.ZGXE) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ ||
			// info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() <= 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			//
			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ ||
			// info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() > 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }

			saPageContent[index++] = "";
			saPageContent[index++] = "人民币";

			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "0";
			if (info.getIntervalNum() > 0) {
				sTemp = String.valueOf(info.getIntervalNum());
			}
			saPageContent[index++] = sTemp;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 抵押合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            担保标示（LOAN_LOANCONTRACTASSURE表中的ID表中的标示）
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addPledge(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 16;
		int SECONDPAGECOUNT = 11;
		int SIXPAGECOUNT = 1;
		int SEVENTHPAGECOUNT = 7;
		int EIGHTHPAGECOUNT = 10;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;

			if (info.getLoanTypeID() == LOANConstant.LoanType.ZY
					|| info.getLoanTypeID() == LOANConstant.LoanType.ZGXE) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ ||
			// info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() <= 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			//
			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ ||
			// info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() > 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }

			saPageContent[index++] = "人民币";
			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = "";
			saPageContent[index++] = "人民币";
			sTemp = "";
			if (info.getAllAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getAllAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getFormatInterestRate();
			saPageContent[index++] = "人民币";
			sTemp = "";
			if (info.getAssureAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getAssureAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			index = 0;
			for (i = 0; i < SEVENTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < SEVENTHPAGECOUNT; i++) {
				if (i == SEVENTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < EIGHTHPAGECOUNT; i++) {
				if (i == EIGHTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 8, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 统借统还贷款合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addTJTH(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 20;
		int SECONDPAGECOUNT = 3;
		int THIRDPAGECOUNT = 8;
		int FOURTHPAGECOUNT = 6;
		int FIFTHPAGECOUNT = 4;
		int TENTHPAGECOUNT = 1;
		int ELEVENPAGECOUNT = 9; // 7;
		int TWELFTHPAGECOUNT = 15;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode");
			sb.append(" FROM loan_contractForm con,Client c,Client cc");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.nConsignClientID = cc.id");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码

				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间

				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? ""
					: consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? ""
					: consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? ""
					: consignInfo.getZipCode();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			// saPageContent[index++] = (info.getLoanReason() == null) ? "" :
			// info.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			sTemp = "0";
			if (info.getIntervalNum() > 0) {
				sTemp = String.valueOf(info.getIntervalNum());
			}
			saPageContent[index++] = sTemp;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			// 初始化EJB
			Collection cPlan = null;
			try {
				RepayPlanHome repayplanHome = (RepayPlanHome) EJBObject
						.getEJBHome("RepayPlanHome");
				RepayPlan repayplan = repayplanHome.create();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			String sDate = "";
			long lPAY = 0;
			long lRepay = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						lPAY++;
						sDate = DataFormat.formatDate(rp_info.tsPlanDate);
					}

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						lRepay++;
					}
				}
			}

			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			if (lPAY == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lPAY == 1 && !sDate.equals("")) {
				saPageContent[index++] = sDate.substring(0, 4);
				saPageContent[index++] = sDate.substring(5, 7);
				saPageContent[index++] = sDate.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			if (lPAY > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			saPageContent[index++] = (info.getOther() == null) ? "" : info
					.getOther();

			if (lRepay == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lRepay > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// 合同第9页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// 合同第10页
			sContent = "";
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 10, sContent);

			// 合同第11页
			sContent = "";
			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				if (i == ELEVENPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 11, sContent);

			// 合同第12页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;

			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < TWELFTHPAGECOUNT; i++) {
				if (i == TWELFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 12, sContent);

			// 合同第13页
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			// int nPlan = 0 ;

			if (cPlan != null) {
				sTemp = getPlanTab(cPlan);
			}

			sContent = sTemp + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 13, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 委托贷款合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addWTDK(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo assureInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 34;
		int SECONDPAGECOUNT = 13;
		int THIRDPAGECOUNT = 9;
		int FOURTHPAGECOUNT = 6;
		int FIFTHPAGECOUNT = 4;
		int EIGHTHPAGECOUNT = 3;
		int NINTHPAGECOUNT = 25;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");

			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount,");

			sb
					.append(" ac.sName as AssureName,ac.sLegalPerson as AssureLegalPerson,");
			sb
					.append(" ac.sProvince as AssureProvince,ac.sCity as AssureCity,");
			sb
					.append(" ac.sAddress as AssureAddress,ac.sPhone as AssurePhone,");
			sb.append(" ac.sFax as AssureFax,ac.sZipCode as AssureZipCode,");
			sb.append(" ac.sBank1 as AssureBank1,ac.sAccount as AssureAccount");

			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID(+)");
			sb.append(" AND con.nConsignClientID = cc.id(+)");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
				consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
				consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号
				assureInfo.setName(rs.getString("AssureName")); // 客户名称
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // 法人代表
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // 地址
				assureInfo.setPhone(rs.getString("AssurePhone")); // 电活
				assureInfo.setFax(rs.getString("AssureFax")); // 传真
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // 邮政编码
				assureInfo.setBank1(rs.getString("AssureBank1")); // 开户银行
				assureInfo.setAccount(rs.getString("AssureAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // 手续费率类型
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? ""
					: consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? ""
					: consignInfo.getAccount();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? ""
					: consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? ""
					: consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? ""
					: consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();

			saPageContent[index++] = (assureInfo.getName() == null) ? ""
					: assureInfo.getName();
			saPageContent[index++] = (assureInfo.getLegalPerson() == null) ? ""
					: assureInfo.getLegalPerson();
			saPageContent[index++] = (assureInfo.getBank1() == null) ? ""
					: assureInfo.getBank1();
			saPageContent[index++] = (assureInfo.getAccount() == null) ? ""
					: assureInfo.getAccount();
			saPageContent[index++] = (assureInfo.getAddress() == null) ? ""
					: assureInfo.getAddress();
			saPageContent[index++] = (assureInfo.getZipCode() == null) ? ""
					: assureInfo.getZipCode();
			saPageContent[index++] = (assureInfo.getPhone() == null) ? ""
					: assureInfo.getPhone();
			saPageContent[index++] = (assureInfo.getFax() == null) ? ""
					: assureInfo.getFax();
			// saPageContent[index++] = (info.getLoanReason() == null) ? "" :
			// info.getLoanReason();

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			sTemp = "0";
			if (info.getIntervalNum() > 0) {
				sTemp = String.valueOf(info.getIntervalNum());
			}
			saPageContent[index++] = sTemp;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? ""
					: consignInfo.getAccount();
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			// 初始化EJB
			Collection cPlan = null;
			try {
				RepayPlanHome repayplanHome = (RepayPlanHome) EJBObject
						.getEJBHome("RepayPlanHome");
				RepayPlan repayplan = repayplanHome.create();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			String sDate = "";
			long lPAY = 0;
			long lRepay = 0;
			Iterator iter;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						lPAY++;
						sDate = DataFormat.formatDate(rp_info.tsPlanDate);
					}

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						lRepay++;
					}
				}
			}

			sContent = "";
			index = 0;

			saPageContent[index++] = DataFormat
					.formatRate(info.getChargeRate());
			saPageContent[index++] = "";
			if (info.getChargeRateType() > 0) {
				saPageContent[index++] = String.valueOf(info
						.getChargeRateType());
			} else {
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			if (lPAY == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lPAY == 1 && !sDate.equals("")) {
				saPageContent[index++] = sDate.substring(0, 4);
				saPageContent[index++] = sDate.substring(5, 7);
				saPageContent[index++] = sDate.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			if (lPAY > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;

			if (lRepay == 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			if (lRepay > 1) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			saPageContent[index++] = info.getOther() == null ? "" : info
					.getOther();
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}
			// saPageContent[60] = (info.getOther() == null) ? "" :
			// info.getOther();
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			for (i = 0; i < EIGHTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}
			for (i = 0; i < EIGHTHPAGECOUNT; i++) {
				if (i == EIGHTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 8, sContent);

			// 合同第9页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (assureInfo.getName() == null) ? ""
					: assureInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (assureInfo.getLegalPerson() == null) ? ""
					: assureInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < NINTHPAGECOUNT; i++) {
				if (i == NINTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 9, sContent);

			// 合同第10页
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			int nPlan = 0;

			if (cPlan != null) {
				nPlan = cPlan.size();
				String[] strPayTime = new String[nPlan];
				String[] strRepayTime = new String[nPlan];
				String[] strPayAmount = new String[nPlan];
				String[] strRepayAmount = new String[nPlan];
				double dTotalPay = 0;
				double dTotalRepay = 0;
				int iPay = 0;
				int iRepay = 0;

				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) //
					{
						if (rp_info.tsPlanDate != null) {
							strPayTime[iPay] = DataFormat
									.getChineseDateString(rp_info.tsPlanDate);
						} else {
							strPayTime[iPay] = "&nbsp;";
						}
						if (rp_info.dAmount >= 0) {
							strPayAmount[iPay] = DataFormat
									.formatListAmount(rp_info.dAmount);
						} else {
							strPayAmount[iPay] = "&nbsp;";
						}
						dTotalPay = dTotalPay + rp_info.dAmount;
						iPay++;
					} else {
						if (rp_info.tsPlanDate != null) {
							strRepayTime[iRepay] = DataFormat
									.getChineseDateString(rp_info.tsPlanDate);
						} else {
							strRepayTime[iRepay] = "&nbsp;";
						}
						if (rp_info.dAmount >= 0) {
							strRepayAmount[iRepay] = DataFormat
									.formatListAmount(rp_info.dAmount);
						} else {
							strRepayAmount[iRepay] = "&nbsp;";
						}
						dTotalRepay = dTotalRepay + rp_info.dAmount;
						iRepay++;
					}
				}

				sTemp += " <b>附件：</b> <p>&nbsp;</p>";
				sTemp += " <p><h4 ALIGN=center><strong>提款、还款方式</strong></h4><br></p>";
				sTemp += " <table width=100% border=0 cellspacing=0 class=table1 >";
				sTemp += " <tr>";
				sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> 提款方式 </td>";
				sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> 还款方式 </td>";
				sTemp += " </tr>";
				sTemp += " <tr align=center>";
				sTemp += " <td width=25%  class=td-rightbottom> 提款日期 </td>";
				sTemp += " <td width=25%  class=td-rightbottom> 提款金额 </td>";
				sTemp += " <td width=25%  class=td-rightbottom> 还款日期 </td>";
				sTemp += " <td width=25%  class=td-rightbottom> 还款金额 </td>";
				sTemp += " </tr>";

				int iCount = iRepay > iPay ? iRepay : iPay;

				for (int j = 0; j < iCount; j++) {
					sTemp += " <TR>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayTime[j]) + " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayAmount[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayTime[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayAmount[j])
							+ " </TD>";
					sTemp += " </TR>";
				}
				/*
				 * sTemp += " <TR> \n"; sTemp += "
				 * <TD class=td-rightbottom align=middle></TD> \n"; sTemp += "
				 * <TD class=td-rightbottom align=middle></TD> \n"; sTemp += "
				 * <TD class=td-rightbottom align=middle></TD> \n"; sTemp += "
				 * <TD class=td-rightbottom align=middle></TD> \n"; sTemp += "
				 * </TR> \n"; //
				 */
				sTemp += " <tr align=center>";
				sTemp += " <td width=25%  class=td-rightbottom> 合计 </td>";
				sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
						+ DataFormat.formatListAmount(dTotalPay) + " </td>";
				sTemp += " <td width=25%  class=td-rightbottom> 合计 </td>";
				sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
						+ DataFormat.formatListAmount(dTotalRepay) + " </td>";
				sTemp += " </tr>";
				sTemp += " </table>";

				/*
				 * sTemp += " <table border=0 width=100% class=table1 > \n";
				 * sTemp += " <tr> "; sTemp += " <td width=50% valign=top> \n";
				 * sTemp += " <table border=0 width=100%> \n"; sTemp += " <tr> ";
				 * sTemp += " <td colspan=2 align=middle class=top> 提款方式 </td>
				 * \n"; sTemp += " </tr> "; sTemp += " <tr> "; sTemp += "
				 * <td width=50% align=middle > 提款日期 </td> \n"; sTemp += "
				 * <td width=50% align=middle > 提款金额 </td> \n"; sTemp += " </tr>
				 * \n";
				 * 
				 * iter = cPlan.iterator(); for (i = 0; iter.hasNext(); i++) {
				 * RepayPlanInfo rp_info = new RepayPlanInfo(); rp_info =
				 * (RepayPlanInfo) iter.next();
				 * 
				 * if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
				 * sTemp += " <tr> \n"; sTemp += "
				 * <td width=50% align=middle class=td-rightbottom> " +
				 * DataFormat.formatDate(rp_info.tsPlanDate) + " </td> \n";
				 * sTemp += " <td width=50% align=right class=td-rightbottom> " +
				 * DataFormat.formatListAmount(rp_info.dAmount) + " </td> \n";
				 * sTemp += " </tr> \n"; dTemp += rp_info.dAmount; } }
				 * 
				 * sTemp += " <tr> \n"; sTemp += "
				 * <td width=50% align=middle class=td-right> 合计： </td> \n";
				 * sTemp += " <td width=50%  align=right class=td-right> " +
				 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
				 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n";
				 * sTemp += " <td width=50% valign=top> \n"; sTemp += " <table
				 * border=1 width=100%> \n"; sTemp += " <tr> \n"; sTemp += "
				 * <td colspan=2 align=middle class=top> 还款方式 </td> \n"; sTemp += "
				 * </tr> \n"; sTemp += " <tr> \n"; sTemp += "
				 * <td width=50% align=middle class=td-rightbottom > 还款日期 </td>
				 * \n"; sTemp += "
				 * <td width=50% align=middle class=td-rightbottom > 还款金额 </td>
				 * \n"; sTemp += " </tr> \n";//
				 */

			}
			/*
			 * dTemp = 0; if (cPlan != null) { iter = cPlan.iterator(); for (i =
			 * 0; iter.hasNext(); i++) { RepayPlanInfo rp_info = new
			 * RepayPlanInfo(); rp_info = (RepayPlanInfo) iter.next(); if
			 * (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) { sTemp += "
			 * <tr> \n"; sTemp += "
			 * <td width=50% align=middle class=td-rightbottom > " +
			 * DataFormat.formatDate(rp_info.tsPlanDate) + " </td> \n"; sTemp += "
			 * <td width=50% align=right class=td-rightbottom > " +
			 * DataFormat.formatListAmount(rp_info.dAmount) + " </td> \n"; sTemp += "
			 * </tr> \n"; dTemp += rp_info.dAmount; } }
			 * 
			 * sTemp += " <tr> \n"; sTemp += "
			 * <td width=50% align=middle class=td-right> 合计： </td> \n"; sTemp += "
			 * <td width=50% align=right class=td-right> " +
			 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; }//
			 */

			sContent = sTemp + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 10, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 最高限额借款合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addZGXE(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 25;
		int SECONDPAGECOUNT = 11;
		int THIRDPAGECOUNT = 5;
		int FOURTHPAGECOUNT = 6;
		int NINTHPAGECOUNT = 21;
		int TENTHPAGECOUNT = 18;
		int ELEVENPAGECOUNT = 21;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();

			saPageContent[index++] = "";// 项目
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = (info.getLoanReason() == null) ? "" : info
					.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXE) {
				saPageContent[index++] = " checked ";
			} else {
				saPageContent[index++] = "";
			}

			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() <= 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			//
			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// if (info.getIntervalNum() > 36)
			// {
			// saPageContent[index++] = " checked ";
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }
			// }
			// else
			// {
			// saPageContent[index++] = "";
			// }

			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			sTemp = "";

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate());
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			String sAssure = "";
			String sPledge = "";
			String sImpawn = "";
			String sCode = "";
			Collection c = contractDao.getContractContent(info.getContractID());
			if (c != null) {
				Iterator it = c.iterator();
				ContractContentInfo ccInfo = new ContractContentInfo();
				if (it != null) {
					while (it.hasNext()) {
						ccInfo = (ContractContentInfo) it.next();
						sCode = ccInfo.getCode();
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE) {
							sAssure = ccInfo.getCode();
						}
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE) {
							sPledge = ccInfo.getCode();
						}
						if (ccInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN) {
							sImpawn = ccInfo.getCode();
						}

					}
				}
			}

			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = sAssure;
			saPageContent[index++] = "";
			saPageContent[index++] = sPledge;
			saPageContent[index++] = "";
			saPageContent[index++] = sImpawn;

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			// saPageContent[index++] = (info.getOther() == null) ? "" :
			// info.getOther();
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// 合同第9页
			sContent = "";
			index = 0;
			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sCode;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < NINTHPAGECOUNT; i++) {
				if (i == NINTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 9, sContent);

			// 合同第10页
			sContent = "";
			for (i = 0; i < TENTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}
			for (i = 0; i < TENTHPAGECOUNT; i++) {
				if (i == TENTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 10, sContent);

			// 合同第11页
			sContent = "";
			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				saPageContent[i] = "";
			}
			for (i = 0; i < ELEVENPAGECOUNT; i++) {
				if (i == ELEVENPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 11, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addTX(long lID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "";

		long lBankAcceptPO = -1;
		long lBizAcceptPO = -1;

		try {

			con = Database.getConnection();
			sb.append(" select a.nbankacceptpo,a.nbizacceptpo ");
			sb.append(" from loan_contractform a ");
			sb.append(" where a.id = ? ");
			log4j.info(sb.toString());

			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				lBankAcceptPO = rs.getLong("nbankacceptpo"); // 贴现-银行汇票张数
				lBizAcceptPO = rs.getLong("nbizacceptpo"); // 贴现-商业汇票张数
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			sb.setLength(0);

			/* TOCONFIG—TODELETE */
			/*
			 * 产品化不再区分项目,以中电子为参考; ninh 2005-03-24
			 */

			if (lBankAcceptPO > 0 || lBizAcceptPO <= 0) {
				// if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN
				// ||Config.GLOBAL.getProjectType() ==
				// Constant.ProjectType.CNMEF
				// ||Config.GLOBAL.getProjectType() ==
				// Constant.ProjectType.MBEC)
				// {
				sFileName = addHaierTXBank(lID);
				// }
				// else
				// {
				// sFileName = addHaierTXBank(lID);
				// }
			} else {
				// if(Config.GLOBAL.getProjectType() == Constant.ProjectType.HN
				// ||Config.GLOBAL.getProjectType() ==
				// Constant.ProjectType.CNMEF
				// ||Config.GLOBAL.getProjectType() ==
				// Constant.ProjectType.MBEC)
				// {
				sFileName = addHaierTXBiz(lID);
				// }
				// else
				// {
				// sFileName = addHaierTXBiz(lID);
				// }
			}

			/* TOCONFIG—END */

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
			e.printStackTrace();
			throw new IException("Gen_E001");
		} catch (Exception e) {
			e.printStackTrace();
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

		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addTXBank(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 3;
		int FOURTHPAGECOUNT = 20;

		int i;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入贴现合同文本（银行汇票）");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = info.getFormatDiscountRate();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			String sTemp = "";

			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			if (info.getCheckAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getCheckAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sb.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			index = 0;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				if (index < arrLen - 10) {
					saPageContent[index++] = rs.getString("sCode") == null ? ""
							: rs.getString("sCode"); // 汇票号码
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // 票面金额
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // 出票人
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // 出票日期
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // 承兑银行
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // 贴现日期
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // 到期日期

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // 贴现天数
					lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // 贴现利息
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // 实付贴现金额
				}
			}

			sContent = "";
			for (i = 0; i < index; i++) {
				if (i < arrLen) {
					if (i == index - 1) {
						sContent += formatString(saPageContent[i]);
					} else {
						sContent += formatString(saPageContent[i])
								+ CONTENT_SEPERATOR;
					}
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入贴现合同文本（银行汇票）结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addTXBiz(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 20;
		int SECONDPAGECOUNT = 9;
		int FIFTHPAGECOUNT = 13;

		int i;

		long lBizAcceptPO = 0;
		Timestamp DiscountDate = null;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入贴现合同文本(商业汇票)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //开户银行账号
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 录入时间
				lBizAcceptPO = rs.getLong("nbizacceptpo");// 贴现-商业汇票张数
				DiscountDate = rs.getTimestamp("dtDiscountDate");// 贴现日
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页
			String sTemp = "";

			// Log.print("-----test--3---");
			// 合同编号
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// 签订日期
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 签署地点
			saPageContent[index++] = "";

			// 甲方
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// 乙方
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());

			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			// 商业承兑汇票票面金额总额（大写）
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// 贴现利率
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// 贴现日
			sTemp = DataFormat.getDateString(DiscountDate);
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = "";

			// 贴现利息总额（大写）TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// 实付贴现金额总额（大写）TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// 贴现用途
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// 合同份数
			saPageContent[index++] = "";// 甲方持有份数
			saPageContent[index++] = "";// 乙方持有份数
			saPageContent[index++] = "";// 综合授信合同编号
			saPageContent[index++] = "";// 年
			saPageContent[index++] = "";// 字

			saPageContent[index++] = "";// 双方约定

			// 甲方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// 乙方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = (cInfo.getName() == null) ? "" :
			// cInfo.getName();
			// saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" :
			// cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// Log.print("-----test--7---");
			// 合同第6页
			sb.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			index = 0;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				if (index < arrLen - 10) {
					saPageContent[index++] = rs.getString("sCode") == null ? ""
							: rs.getString("sCode"); // 汇票号码
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // 票面金额
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // 出票人
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // 出票日期
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // 承兑银行
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // 贴现日期
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // 到期日期

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // 贴现天数
					lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // 贴现利息
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // 实付贴现金额
				}
			}

			sContent = "";
			for (i = 0; i < index; i++) {
				if (i < arrLen) {
					if (i == index - 1) {
						sContent += formatString(saPageContent[i]);
					} else {
						sContent += formatString(saPageContent[i])
								+ CONTENT_SEPERATOR;
					}
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);
			// Log.print("-----test--8---");

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入贴现合同文本(商业汇票)结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 大桥合同文本（自营 短期/中长期） Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addDQXTDK(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 35; // 31;
		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ac.sName as AssureName");
			sb.append(" FROM loan_contractForm con,Client c,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				cInfo.setCreditLevelID(rs.getLong("nCreditLevelID"));
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode")); // 合同编号
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setAdjustRate(rs.getDouble("mAdjustRate"));// 调整利率
				info.setBasicInterestRate(rInfo.getBasicRate());// 基准利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setAssureName(rs.getString("AssureName")); // 保证方名称
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			// saPageContent[index++] = "";
			saPageContent[index++] = info.getContractCode(); // "";
			saPageContent[index++] = Env.getClientName();
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			String sTemp = "";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			sTemp = "0";
			if (info.getIntervalNum() > 0) {
				sTemp = String.valueOf(info.getIntervalNum());
			}
			saPageContent[index++] = sTemp;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 4
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate());
			saPageContent[index++] = DataFormat.formatRate(info
					.getBasicInterestRate());
			saPageContent[index++] = LOANConstant.CreditLevel.getName(cInfo
					.getCreditLevelID());
			saPageContent[index++] = DataFormat
					.formatRate(info.getAdjustRate());

			// 5 担保方式
			// 信用
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 抵押
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 6
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 7
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 10
			saPageContent[index++] = "";
			// 11
			saPageContent[index++] = "";

			saPageContent[index++] = Env.getClientName();
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = info.getAssureName() == null ? "" : info
					.getAssureName();

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 财务情况统计表 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            客户标示（Client表中的标示）
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addFinanceTJB(long lID) throws Exception {

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[50];

		int PAGECOUNT = 35;

		try {
			// * 财务情况统计表
			// saPageContent[0] = ""+lID;
			// saPageContent[PAGECOUNT-1] = ""+lID;
			for (int i = 0; i < PAGECOUNT; i++) {
				if (i == 0) {
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
				if (i == PAGECOUNT - 1) {
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i]);
				} else {
					saPageContent[i] = "";
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);
			// */

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 借款展期协议 Create Date: 2003-11-12
	 * 
	 * @param lID
	 *            LOAN_EXTENDFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addJKZQXY(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();
		ExtendApplyInfo e_info = new ExtendApplyInfo();

		int FIRSTPAGECOUNT = 22;
		int SECONDPAGECOUNT = 33;
		int THIRDPAGECOUNT = 29;
		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb
					.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != "
					+ LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next()) {
				if (iFlag > 0) {
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); // 展期金额
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate")
							.before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate
							: rs.getTimestamp("ExtEndDate"); // 展期期限
				} else {
					cInfo.setName(rs.getString("sName")); // 客户名称
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
							.getString("sCity"), rs.getString("sAddress"))); // 地址
					cInfo.setPhone(rs.getString("sPhone")); // 电活
					cInfo.setFax(rs.getString("sFax")); // 传真
					cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
					cInfo.setBank1(rs.getString("sBank1")); // 开户银行
					cInfo.setAccount(rs.getString("sAccount")); // 账号

					consignInfo.setName(rs.getString("ConsignName")); // 客户名称
					consignInfo.setLegalPerson(rs
							.getString("ConsignLegalPerson")); // 法人代表
					consignInfo.setAddress(getAddress(rs
							.getString("ConsignProvince"), rs
							.getString("ConsignCity"), rs
							.getString("ConsignAddress"))); // 地址
					consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
					consignInfo.setFax(rs.getString("ConsignFax")); // 传真
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
					consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
					consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号

					info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
					info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
					info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
					info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
					info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
					info.setContractID(rs.getLong("contractID")); // 合同ID
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
							null);
					info.setInterestRate(rInfo.getLateRate()); // 执行利率
					info.setOther(rs.getString("sOther")); // 还款来源
					info.setContractCode(rs.getString("SCONTRACTCODE")); // 合同编号
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); // 录入时间

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); // 展期金额
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); // 展期利率
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); // 展期期限

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); // 保证类型
					e_info.m_sExCode = rs.getString("SASSURECODE"); // 担保合同编号*/
				}
				iFlag++;
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			// 贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			// 借款人
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? ""
					: consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? ""
					: consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? ""
					: consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? ""
					: consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? ""
					: consignInfo.getAccount();

			// 担保人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			// saPageContent[index++] = (info.getLoanReason() == null) ? "" :
			// info.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币";

			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate()); // 年息
			saPageContent[index++] = "人民币"; // 展期币种
			sTemp = "";
			if (e_info.m_dExtendAmount > 0) {
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat
					.formatRate(e_info.m_dInterestRate); // 年息

			long[] lAssureType = { LOANConstant.AssureType.ASSURE,
					LOANConstant.AssureType.PLEDGE,
					LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++) {
				if (e_info.lLoanTypeID == lAssureType[i]) {
					saPageContent[index++] = "checked"; // checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9) {
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					} else {
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? ""
							: e_info.m_sExCode;

				} else {
					saPageContent[index++] = ""; // checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			for (i = 0; i < lAssureType.length; i++) {
				if (e_info.lLoanTypeID == lAssureType[i]) {
					saPageContent[index++] = "checked"; // checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? ""
							: e_info.m_sExCode;

				} else {
					saPageContent[index++] = ""; // checkbox
					saPageContent[index++] = "";
				}
			}

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent); // */
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 委托贷款展期协议 Create Date: 2003-11-12
	 * 
	 * @param lID
	 *            LOAN_EXTENDFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addWTDKZQXY(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo WTconsignInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();
		ExtendApplyInfo e_info = new ExtendApplyInfo();

		int FIRSTPAGECOUNT = 22;
		int SECONDPAGECOUNT = 31; // 33;
		int THIRDPAGECOUNT = 28; // 25;
		int FOURTHPAGECOUNT = 24;
		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb
					.append(" ccc.sName as WTConsignName,ccc.sLegalPerson as WTConsignLegalPerson,");
			sb
					.append(" ccc.sProvince as WTConsignProvince,ccc.sCity as WTConsignCity,");
			sb
					.append(" ccc.sAddress as WTConsignAddress,ccc.sPhone as WTConsignPhone,");
			sb
					.append(" ccc.sFax as WTConsignFax,ccc.sZipCode as WTConsignZipCode,");
			sb
					.append(" ccc.sBank1 as WTConsignBank1,ccc.sAccount as WTConsignAccount,");
			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb
					.append(" FROM loan_contractForm con,Client c,Client cc,Client ccc,");
			sb
					.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND con.NCONSIGNCLIENTID = ccc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != "
					+ LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next()) {
				if (iFlag > 0) {
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); // 展期金额
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate")
							.before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate
							: rs.getTimestamp("ExtEndDate"); // 展期期限
				} else {
					cInfo.setName(rs.getString("sName")); // 客户名称
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
							.getString("sCity"), rs.getString("sAddress"))); // 地址
					cInfo.setPhone(rs.getString("sPhone")); // 电活
					cInfo.setFax(rs.getString("sFax")); // 传真
					cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
					cInfo.setBank1(rs.getString("sBank1")); // 开户银行
					cInfo.setAccount(rs.getString("sAccount")); // 账号

					WTconsignInfo.setName(rs.getString("WTConsignName")); // 客户名称
					WTconsignInfo.setLegalPerson(rs
							.getString("WTConsignLegalPerson")); // 法人代表
					WTconsignInfo.setAddress(getAddress(rs
							.getString("WTConsignProvince"), rs
							.getString("WTConsignCity"), rs
							.getString("WTConsignAddress"))); // 地址
					WTconsignInfo.setPhone(rs.getString("WTConsignPhone")); // 电活
					WTconsignInfo.setFax(rs.getString("WTConsignFax")); // 传真
					WTconsignInfo.setZipCode(rs.getString("WTConsignZipCode")); // 邮政编码
					WTconsignInfo.setBank1(rs.getString("WTConsignBank1")); // 开户银行
					WTconsignInfo.setAccount(rs.getString("WTConsignAccount")); // 账号

					consignInfo.setName(rs.getString("ConsignName")); // 客户名称
					consignInfo.setLegalPerson(rs
							.getString("ConsignLegalPerson")); // 法人代表
					consignInfo.setAddress(getAddress(rs
							.getString("ConsignProvince"), rs
							.getString("ConsignCity"), rs
							.getString("ConsignAddress"))); // 地址
					consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
					consignInfo.setFax(rs.getString("ConsignFax")); // 传真
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
					consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
					consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号

					info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
					info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
					info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
					info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
					info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, rs
							.getLong("contractID"), null);
					info.setInterestRate(rInfo.getLateRate()); // 执行利率
					info.setOther(rs.getString("sOther")); // 还款来源
					info.setContractID(rs.getLong("contractID")); // 合同ID
					info.setContractCode(rs.getString("SCONTRACTCODE")); // 合同编号
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); // 录入时间

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); // 展期金额
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); // 展期利率
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); // 展期期限

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); // 保证类型
					e_info.m_sExCode = rs.getString("SASSURECODE"); // 担保合同编号*/
				}
				iFlag++;
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			// 委托人
			saPageContent[index++] = (WTconsignInfo.getName() == null) ? ""
					: WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? ""
					: WTconsignInfo.getLegalPerson();
			saPageContent[index++] = (WTconsignInfo.getAddress() == null) ? ""
					: WTconsignInfo.getAddress();
			saPageContent[index++] = (WTconsignInfo.getPhone() == null) ? ""
					: WTconsignInfo.getPhone();
			saPageContent[index++] = (WTconsignInfo.getFax() == null) ? ""
					: WTconsignInfo.getFax();
			saPageContent[index++] = (WTconsignInfo.getZipCode() == null) ? ""
					: WTconsignInfo.getZipCode();
			saPageContent[index++] = (WTconsignInfo.getBank1() == null) ? ""
					: WTconsignInfo.getBank1();
			saPageContent[index++] = (WTconsignInfo.getAccount() == null) ? ""
					: WTconsignInfo.getAccount();

			// 贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			// 借款人
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? ""
					: consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? ""
					: consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? ""
					: consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? ""
					: consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? ""
					: consignInfo.getAccount();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;

			// 担保人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			// saPageContent[index++] = (info.getLoanReason() == null) ? "" :
			// info.getLoanReason();

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";
			saPageContent[index++] = "人民币";

			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate()); // 年息
			saPageContent[index++] = "人民币"; // 展期币种
			sTemp = "";
			if (e_info.m_dExtendAmount > 0) {
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			// saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat
					.formatRate(e_info.m_dInterestRate); // 年息
			saPageContent[index++] = ""; // 月手续费息//改为 年手续费息
			saPageContent[index++] = ""; // 手续费
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;

			long[] lAssureType = { LOANConstant.AssureType.ASSURE,
					LOANConstant.AssureType.PLEDGE,
					LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++) {
				if (e_info.lLoanTypeID == lAssureType[i]) {
					saPageContent[index++] = "checked"; // checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9) {
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					} else {
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? ""
							: e_info.m_sExCode;

				} else {
					saPageContent[index++] = ""; // checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";

			for (i = 0; i < lAssureType.length; i++) {
				if (e_info.lLoanTypeID == lAssureType[i]) {
					saPageContent[index++] = "checked"; // checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? ""
							: e_info.m_sExCode;

				} else {
					saPageContent[index++] = ""; // checkbox
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";

			saPageContent[index++] = (WTconsignInfo.getName() == null) ? ""
					: WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? ""
					: WTconsignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent); // */
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 根据合同内容标示、合同页码，修改指定页的合同内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            合同内容标示(LOAN_CONTRACTCONTENT表中的ID)
	 * @param lPageNo
	 *            修改合同第几页的内容
	 * @param strContent
	 *            合同待修改页的内容
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long update(long lID, long lPageNo, String strContent)
			throws Exception {
		long lResult = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String sDocName = "";

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.sDocName");
			sbSQL.append(" FROM loan_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				sDocName = rs.getString("sDocName"); // 合同文本文件名
			}

			String sTemp = saveContent(sDocName, lPageNo, strContent);
			if (sTemp != null && !sTemp.equals("")) {
				lResult = 1;
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
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	/**
	 * 得到合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT表中的ID
	 * @return ContractContentInfo 合同文本内容
	 * @exception Exception
	 */
	public ContractContentInfo findByID(long lID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		ContractContentInfo info = new ContractContentInfo();
		String sContent = "";

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*");
			sbSQL.append(" FROM loan_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				info.setParentID(rs.getLong("nParentID")); // 合同主体的ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); // 合同类型ID
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setDocName(rs.getString("sDocName")); // 合同文本文件名 ]

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
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return info;

	}

	/**
	 * 得到贷款调查表ID Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return 贷款调查表ID
	 * @exception Exception
	 */
	public long findLoanQuestinoaryByLoanID(long lID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM loan_ContractContent");
			sbSQL.append(" WHERE nParentID = ? and nContractTypeID=?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			ps.setLong(2, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next()) {
				ret = rs.getLong("ID");
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
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return ret;

	}

	/**
	 * 得到贷款调查表ID Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return 贷款调查表ID
	 * @exception Exception
	 */
	public long findLoanQuestinoaryByClient(long lID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM loan_ContractContent");
			sbSQL.append(" WHERE nContractTypeID=? ");
			sbSQL
					.append(" and nParentID in (select ID from Loan_Loanform where loan_loanForm.Nborrowclientid=?) ");
			sbSQL.append(" order by ID  asc ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(2, lID);
			ps.setLong(1, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next()) {
				ret = rs.getLong("ID");
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
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return ret;

	}

	/**
	 * 获取指定合同的合同模版 Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            文本ID
	 * @param lContractTypeID
	 *            文本类型ID
	 * @return String 返回合同的合同模版
	 * @exception Exception
	 */
	public String getContractTemplate(long lContentID, long lContractTypeID)
			throws Exception {
		String strTemplate = "";
		try {
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0) {
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(0, len);
			} else {
				strTemplate = "";
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	 * 获取指定合同的文本的JSP文件名 Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            文本ID
	 * @param lContractTypeID
	 *            文本类型ID
	 * @return String 返回文本的JSP文件名
	 * @exception Exception
	 */
	public String getContractJspName(long lContentID, long lContractTypeID)
			throws Exception {
		String strTemplate = "";
		try {
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0) {
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(len + 1, strTemplate
						.length());
			} else {
				strTemplate = "";
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	 * 获取指定合同的合同模版和文本的JSP文件名 Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            文本ID
	 * @param lContractTypeID
	 *            文本类型ID
	 * @return String 返回合同模版和文本的JSP文件名
	 * @exception Exception
	 */
	public String getDocumentName(long lContentID, long lContractTypeID)
			throws Exception {
		String strTemplate = "";
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		System.out.println("合同类型id:" + lContractTypeID);
		try {

			long lContractType = -1;
			if (lContractTypeID == LOANConstant.ContractType.ASSURE) {
				lContractType = LOANConstant.Template.SHPF_ASSURE;
			} else if (lContractTypeID == LOANConstant.ContractType.PLEDGE) {
				lContractType = LOANConstant.Template.SHPF_IMPAWN;
			} else if (lContractTypeID == LOANConstant.ContractType.IMPAWN) {
				lContractType = LOANConstant.Template.HN_IMPAWN;
			} else if (lContractTypeID == LOANConstant.ContractType.CONSIGN) {
				lContractType = LOANConstant.Template.SHPF_WTDK;
			} else if (lContractTypeID == LOANConstant.ContractType.GFIMPAWN) {
				lContractType = LOANConstant.Template.GFIMPAWN;
			} else if (lContractTypeID == LOANConstant.ContractType.GQIMPAWN) {
				lContractType = LOANConstant.Template.GQIMPAWN;
			} else if (lContractTypeID == LOANConstant.ContractType.CDIMPAWN) {
				lContractType = LOANConstant.Template.CDIMPAWN;
			} else if (lContractTypeID == LOANConstant.ContractType.ZGEASSURE) {
				lContractType = LOANConstant.Template.SHPF_ZGEASSURE;
			} else if (lContractTypeID == LOANConstant.ContractType.ZGEPLEDGE) {
				lContractType = LOANConstant.Template.SHPF_ZGEPLEDGE;
			} else if (lContractTypeID == LOANConstant.ContractType.DYWQD) {
				lContractType = LOANConstant.Template.DYWQD;
			} else if (lContractTypeID == LOANConstant.ContractType.SHPF_KLGNBHXY) {
				lContractType = LOANConstant.Template.SHPF_KLGNBHXY;
			} else if (lContractTypeID == LOANConstant.ContractType.SHPF_RZTenancy) {
				System.out.println("进入判断lContractTypeID:" + lContractTypeID);
				lContractType = LOANConstant.Template.SHPF_RZTenancy;
				System.out.println("取模板文件名:" + lContractType);
			} else if (lContractTypeID == LOANConstant.ContractType.SHPF_ZGXE) {
				System.out.println("进入判断lContractTypeID:" + lContractTypeID);
				lContractType = LOANConstant.Template.HN_ZGXE;
				System.out.println("取最高限额模板文件名:" + lContractType);
			} else if (lContractTypeID == LOANConstant.ContractType.EXTEND) {
				ContractContentInfo info = new ContractContentInfo();
				info = findByID(lContentID);
				long lLoanType = -1;

				con = Database.getConnection();
				sbSQL.append(" SELECT a.nTypeID");
				sbSQL.append(" FROM loan_contractform a");
				sbSQL.append(" WHERE a.ID = ?");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getContractID());
				rs1 = ps.executeQuery();

				while (rs1.next()) {
					lLoanType = rs1.getLong("nTypeID"); // 贷款类型ID
				}
				rs1.close();
				rs1 = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);

				// if (lLoanType == LOANConstant.LoanType.WT || lLoanType ==
				// LOANConstant.LoanType.WTTJTH)
				if (lLoanType == LOANConstant.LoanType.WT) {
					lContractType = LOANConstant.Template.HN_EXTENDWT;
				} else {
					lContractType = LOANConstant.Template.HN_EXTENDZY;
				}

			} else if (lContractTypeID == LOANConstant.ContractType.TX) {
				// lContractType = LOANConstant.Template.HN_TX;
				ContractContentInfo info = new ContractContentInfo();
				info = findByID(lContentID);
				long lBankAcceptPO = -1;
				long lBizAcceptPO = -1;

				con = Database.getConnection();
				sbSQL.append(" select a.nbankacceptpo,a.nbizacceptpo ");
				sbSQL.append(" from loan_contractform a ");
				sbSQL.append(" where a.id = ? ");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getContractID());
				rs1 = ps.executeQuery();

				while (rs1.next()) {
					lBankAcceptPO = rs1.getLong("nbankacceptpo"); // 贴现-银行汇票张数
					lBizAcceptPO = rs1.getLong("nbizacceptpo"); // 贴现-商业汇票张数
				}
				rs1.close();
				rs1 = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);

				if (lBankAcceptPO > 0 || lBizAcceptPO <= 0) {
					lContractType = LOANConstant.Template.HN_TX;
				} else {
					lContractType = LOANConstant.Template.HN_TXBIZ;
				}
			} else if (lContractTypeID == LOANConstant.ContractType.ZTX) {
				// lContractType = LOANConstant.Template.HN_TX;
				ContractContentInfo info = new ContractContentInfo();
				info = findByID(lContentID);
				long lBankAcceptPO = -1;
				long lBizAcceptPO = -1;

				con = Database.getConnection();
				sbSQL.append(" select a.nbankacceptpo,a.nbizacceptpo ");
				sbSQL.append(" from loan_contractform a ");
				sbSQL.append(" where a.id = ? ");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getContractID());
				rs1 = ps.executeQuery();

				while (rs1.next()) {
					lBankAcceptPO = rs1.getLong("nbankacceptpo"); // 贴现-银行汇票张数
					lBizAcceptPO = rs1.getLong("nbizacceptpo"); // 贴现-商业汇票张数
				}
				rs1.close();
				rs1 = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);

				if (lBankAcceptPO > 0 || lBizAcceptPO <= 0) {
					lContractType = LOANConstant.Template.HN_TX;
				} else {
					lContractType = LOANConstant.Template.HN_TXBIZ;
				}
			} else if (lContractTypeID == LOANConstant.ContractType.DKDCB) {
				lContractType = LOANConstant.Template.HN_DKDCB;
			}

			else if (lContractTypeID == LOANConstant.ContractType.LOAN) // 借款合同
			{
				ContractContentInfo info = new ContractContentInfo();
				info = findByID(lContentID);
				long lLoanType = -1;

				con = Database.getConnection();
				sbSQL.append(" SELECT a.nTypeID");
				sbSQL.append(" FROM loan_contractform a");
				sbSQL.append(" WHERE a.ID = ?");
				log4j.info(sbSQL.toString());

				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, info.getParentID());
				rs1 = ps.executeQuery();

				while (rs1.next()) {
					lLoanType = rs1.getLong("nTypeID"); // 贷款类型ID
				}

				if (lLoanType == LOANConstant.LoanType.ZY) {
					lContractType = LOANConstant.Template.SHPF_ZYDK;
				}
				// if (lLoanType == LOANConstant.LoanType.ZYZCQ)
				// {
				// lContractType = LOANConstant.Template.HN_ZYDK;
				// }
				if (lLoanType == LOANConstant.LoanType.WT) {
					lContractType = LOANConstant.Template.SHPF_WTDK;
				}
				// if (lLoanType == LOANConstant.LoanType.WTTJTH)
				// {
				// lContractType = LOANConstant.Template.HN_WTTJTH;
				// }
				if (lLoanType == LOANConstant.LoanType.TX) {
					lContractType = LOANConstant.Template.HN_TX;
				}
				if (lLoanType == LOANConstant.LoanType.ZGXE) {
					lContractType = LOANConstant.Template.SHPF_ZYDK;
				}
				// if (lLoanType == LOANConstant.LoanType.ZGXEZCQ)
				// {
				// lContractType = LOANConstant.Template.HN_ZGXE;
				// }
				if (lLoanType == LOANConstant.LoanType.YT)// DQ || lLoanType
															// ==
															// LOANConstant.LoanType.YTZCQ)
				{
					lContractType = LOANConstant.Template.HN_YTDK;
				}
			}
			strTemplate = LOANConstant.Template.getName(lContractType);
			System.out.println("strTemplate:" + strTemplate);
			if (rs1 != null) {
				rs1.close();
				rs1 = null;
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
		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
					rs1 = null;
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
		return strTemplate;
	}

	/**
	 * 保存合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ContractContentInfo合同文本内容(必须设置的内容有:ParentID,ContractID,ContractType,DocName)
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long saveContractContent(ContractContentInfo info) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;
		long lContentID = 1;

		try {
			conn = Database.getConnection();
			sbSQL.append(" SELECT MAX(NVL(con.id,0)) as contentID");
			sbSQL.append(" FROM loan_ContractContent con");
			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				lContentID = rs.getLong("contentID");
			}

			lContentID = lContentID + 1;

			sbSQL.setLength(0);
			sbSQL.append(" INSERT INTO loan_ContractContent");
			sbSQL
					.append(" (ID,nParentID,nContractID,nContractTypeID,sDocName,sCode)");
			sbSQL.append(" VALUES (?,?,?,?,?,?)");
			log4j.info(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContentID);
			ps.setLong(2, info.getParentID());
			ps.setLong(3, info.getContractID());
			ps.setLong(4, info.getContractTypeID());
			ps.setString(5, info.getDocName());
			ps.setString(6, info.getCode());

			lResult = ps.executeUpdate();
			if (lResult > 0) {
				lResult = lContentID;
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	 * 删除合同文本内容 Create Date: 2003-10-15
	 * 
	 * @param long
	 *            lContractID合同ID
	 * @return long 大于0表示成功，小于等于0表示失败
	 * @exception Exception
	 */
	public long deleteContractContent(long lContractID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();

			sbSQL.append(" DELETE loan_ContractContent");
			sbSQL.append(" WHERE nContractID = ? ");

			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			lResult = ps.executeUpdate();

			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	 * 填写文本缺省值 贷款调查表 Create Date: 2003-10-15
	 * 
	 * @param loanID
	 * @return String 返回调查表文件名及路径
	 * @exception Exception
	 */
	public String addLoanQuestionary(long loanID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long clientID = -1;

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		String[] sConsignInfo = new String[10];
		ClientInfo cInfo = new ClientInfo();

		int FIRSTPAGECOUNT = 64;

		// begin 2004-11-3 修改 by jinchen 中电子项目新加应收方
		/*
		 * if(Config.GLOBAL.getProjectType() == Constant.ProjectType.CEC) {
		 * 
		 * FIRSTPAGECOUNT = 68; }
		 */

		int SECONDPAGECOUNT = 48;
		int THIRDPAGECOUNT = 26;
		int FORTHPAGECOUNT = 16;

		int i;

		try {
			conn = Database.getConnection();

			sb.append("select c.* from loan_loanForm l,client c");
			sb.append(" where c.id=l.nBorrowClientID and l.id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, loanID);
			rs = ps.executeQuery();

			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); // 母公司编号
				// cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //企业性质
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
			}

			cleanup(rs);
			cleanup(ps);
			sb.setLength(0);

			if (cInfo.getParentCorpID() > 0) {
				sb.append("select * from client where id=?");
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, cInfo.getParentCorpID());
				rs = ps.executeQuery();
				if (rs.next())
					;
				cInfo.setParentCorpName(rs.getString("sName"));
				cleanup(rs);
				cleanup(ps);
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			int index = 0;

			// 第1页
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getParentCorpName() == null) ? ""
					: cInfo.getParentCorpName();
			// saPageContent[index++] =
			// (LOANConstant.ClientCorpIndustry.getName(cInfo.getCorpNatureID()));
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			while (index < FIRSTPAGECOUNT)
				saPageContent[index++] = "";

			System.out.println("**************第一页取值完成*************");

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 第2页,
			sContent = "";
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 第3页
			sContent = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 第四页
			sContent = "";
			for (i = 0; i < FORTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < FORTHPAGECOUNT; i++) {
				if (i == FORTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 合同的放款和还款计划 Create Date: 2003-12-20
	 * 
	 * @param Collection
	 *            cPlan 合同
	 * @return String 返回调查表文件名及路径
	 * @exception Exception
	 */
	public String getPlanTab(Collection cPlan) throws Exception {
		Iterator iter = cPlan.iterator();
		int nPlan = cPlan.size();
		String sTemp = "";
		String[] strPayTime = new String[nPlan];
		String[] strRepayTime = new String[nPlan];
		String[] strPayAmount = new String[nPlan];
		String[] strRepayAmount = new String[nPlan];
		double dTotalPay = 0;
		double dTotalRepay = 0;
		int iPay = 0;
		int iRepay = 0;
		try {

			iter = cPlan.iterator();
			for (int i = 0; iter.hasNext(); i++) {
				RepayPlanInfo rp_info = (RepayPlanInfo) iter.next();
				if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) //
				{
					if (rp_info.tsPlanDate != null) {
						strPayTime[iPay] = DataFormat
								.getChineseDateString(rp_info.tsPlanDate);
					} else {
						strPayTime[iPay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0) {
						strPayAmount[iPay] = DataFormat
								.formatListAmount(rp_info.dAmount);
					} else {
						strPayAmount[iPay] = "&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				} else {
					if (rp_info.tsPlanDate != null) {
						strRepayTime[iRepay] = DataFormat
								.getChineseDateString(rp_info.tsPlanDate);
					} else {
						strRepayTime[iRepay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0) {
						strRepayAmount[iRepay] = DataFormat
								.formatListAmount(rp_info.dAmount);
					} else {
						strRepayAmount[iRepay] = "&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}

			sTemp += " <b>附件：</b> <p>&nbsp;</p>";
			sTemp += " <p><h4 ALIGN=center><strong>提款、还款方式</strong></h4><br></p>";
			sTemp += " <table width=100% border=0 cellspacing=0 class=table1 >";
			sTemp += " <tr>";
			sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> 提款方式 </td>";
			sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> 还款方式 </td>";
			sTemp += " </tr>";
			sTemp += " <tr align=center>";
			sTemp += " <td width=25%  class=td-rightbottom> 提款日期 </td>";
			sTemp += " <td width=25%  class=td-rightbottom> 提款金额 </td>";
			sTemp += " <td width=25%  class=td-rightbottom> 还款日期 </td>";
			sTemp += " <td width=25%  class=td-rightbottom> 还款金额 </td>";
			sTemp += " </tr>";

			int iCount = iRepay > iPay ? iRepay : iPay;

			for (int j = 0; j < iCount; j++) {
				sTemp += " <TR>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
						+ DataFormat.formatString(strPayTime[j]) + " </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
						+ DataFormat.formatString(strPayAmount[j]) + " </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
						+ DataFormat.formatString(strRepayTime[j]) + " </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
						+ DataFormat.formatString(strRepayAmount[j]) + " </TD>";
				sTemp += " </TR>";
			}
			sTemp += " <tr align=center>";
			sTemp += " <td width=25%  class=td-rightbottom> 合计 </td>";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
					+ DataFormat.formatListAmount(dTotalPay) + " </td>";
			sTemp += " <td width=25%  class=td-rightbottom> 合计 </td>";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
					+ DataFormat.formatListAmount(dTotalRepay) + " </td>";
			sTemp += " </tr>";
			sTemp += " </table>";

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return sTemp;
	}

	public static void main(String[] args) {
		try {
			ContractContentDao dao = new ContractContentDao();

			String ss = dao.addCECLoan(42);
			System.out.println("*************ss=" + ss);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addZTX(long lID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "";

		long lBankAcceptPO = -1;
		long lBizAcceptPO = -1;

		try {

			con = Database.getConnection();
			sb.append(" select a.nbankacceptpo,a.nbizacceptpo ");
			sb.append(" from loan_contractform a ");
			sb.append(" where a.id = ? ");
			log4j.info(sb.toString());

			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				lBankAcceptPO = rs.getLong("nbankacceptpo"); // 贴现-银行汇票张数
				lBizAcceptPO = rs.getLong("nbizacceptpo"); // 贴现-商业汇票张数
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			sb.setLength(0);

			if (lBankAcceptPO > 0 || lBizAcceptPO <= 0) {
				sFileName = addZTXBank(lID);
			} else {
				sFileName = addZTXBiz(lID);
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
			e.printStackTrace();
			throw new IException("Gen_E001");
		} catch (Exception e) {
			e.printStackTrace();
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

		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addZTXBank(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 3;
		int FOURTHPAGECOUNT = 20;

		int i;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("===开始写入转贴现合同文本（银行汇票）===");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = info.getFormatDiscountRate();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			String sTemp = "";

			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			if (info.getCheckAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getCheckAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
            //modify by xwhe  修改日期为数据库时间
			sTemp = DataFormat.getDateString(Env.getSystemDateTime());
			
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sb.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			index = 0;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				if (index < arrLen - 10) {
					saPageContent[index++] = rs.getString("sCode") == null ? ""
							: rs.getString("sCode"); // 汇票号码
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // 票面金额
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // 出票人
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // 出票日期
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // 承兑银行
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // 贴现日期
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // 到期日期

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // 贴现天数
					lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // 贴现利息
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // 实付贴现金额
				}
			}

			sContent = "";
			for (i = 0; i < index; i++) {
				if (i < arrLen) {
					if (i == index - 1) {
						sContent += formatString(saPageContent[i]);
					} else {
						sContent += formatString(saPageContent[i])
								+ CONTENT_SEPERATOR;
					}
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入转贴现合同文本（银行汇票）结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addZTXBiz(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 20;
		int SECONDPAGECOUNT = 9;
		int FIFTHPAGECOUNT = 13;

		int i;

		long lBizAcceptPO = 0;
		Timestamp DiscountDate = null;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入转贴现合同文本(商业汇票)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //开户银行账号
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 录入时间
				lBizAcceptPO = rs.getLong("nbizacceptpo");// 贴现-商业汇票张数
				DiscountDate = rs.getTimestamp("dtDiscountDate");// 贴现日
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				// Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页
			String sTemp = "";

			// Log.print("-----test--3---");
			// 合同编号
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// 签订日期
		//	sTemp = DataFormat.getDateString(info.getInputDate());
        //	modify by xwhe  修改日期为数据库时间
			sTemp = DataFormat.getDateString(Env.getSystemDateTime());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 签署地点
			saPageContent[index++] = "";

			// 甲方
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// 乙方
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());

			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			// 商业承兑汇票票面金额总额（大写）
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// Log.print("-----test--4--1-");
			// 贴现利率
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// 贴现日
			sTemp = DataFormat.getDateString(DiscountDate);
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// Log.print("-----test--4--2-");
			sTemp = "";
			// 贴现利息总额（大写）TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// Log.print("-----test--4--3-");
			// 实付贴现金额总额（大写）TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// Log.print("-----test--4--4-");
			// 贴现用途
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			// Log.print("-----test--4--5-");
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// 合同份数
			saPageContent[index++] = "";// 甲方持有份数
			saPageContent[index++] = "";// 乙方持有份数
			saPageContent[index++] = "";// 综合授信合同编号
			saPageContent[index++] = "";// 年
			saPageContent[index++] = "";// 字

			saPageContent[index++] = "";// 双方约定

			// 甲方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// 乙方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = (cInfo.getName() == null) ? "" :
			// cInfo.getName();
			// saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" :
			// cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// Log.print("-----test--7---");
			// 合同第6页
			sb.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			index = 0;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				if (index < arrLen - 10) {
					saPageContent[index++] = rs.getString("sCode") == null ? ""
							: rs.getString("sCode"); // 汇票号码
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // 票面金额
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // 出票人
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // 出票日期
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // 承兑银行
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // 贴现日期
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // 到期日期

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // 贴现天数
					lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // 贴现利息
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // 实付贴现金额
				}
			}

			sContent = "";
			for (i = 0; i < index; i++) {
				if (i < arrLen) {
					if (i == index - 1) {
						sContent += formatString(saPageContent[i]);
					} else {
						sContent += formatString(saPageContent[i])
								+ CONTENT_SEPERATOR;
					}
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);
			// Log.print("-----test--8---");

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入转贴现合同文本(商业汇票)结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * Haier 填写文本缺省值 人民币借款合同（自营 短期/中长期） Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHaierXTDK(long lID) throws RemoteException, Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 15;
		int SECONDPAGECOUNT = 3;
		int THIRDPAGECOUNT = 7;
		int FOURTHPAGECOUNT = 3;
		int FIFTHPAGECOUNT = 1;
		int SIXPAGECOUNT = 1;
		int TENTHPAGECOUNT = 11;
		int ELEVENTHPAGECOUNT = 33;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {

				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractCode(rs.getString("sContractCode")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setCurrencyID(rs.getLong("nCurrencyID"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页 15 item
			// 合同编号
			saPageContent[index++] = info.getContractCode() == null ? "" : info
					.getContractCode();
			// 甲方 借款人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页 3 item
			sContent = "";
			index = 0;
			sTemp = "";
			// 借款原因
			saPageContent[index++] = info.getLoanReason() == null ? "" : info
					.getLoanReason();
			// 贷款类型
			saPageContent[index++] = LOANConstant.LoanType.getName(info
					.getLoanTypeID());
			// 贷款用途
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			/*
			 * if(info.getLoanTypeID() == LOANConstant.LoanType.ZYDQ ) {
			 * saPageContent[index++] = "短期贷款"; } else if(info.getLoanTypeID() ==
			 * LOANConstant.LoanType.ZYZCQ ) { saPageContent[index++] = "中长期贷款"; }
			 * else { saPageContent[index++] = "临时贷款"; }//
			 */
			// 贷款用途
			// saPageContent[index++] =
			// info.getLoanPurpose()==null?"":info.getLoanPurpose();
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页 7 item
			sContent = "";
			index = 0;
			sTemp = "0";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			// saPageContent[index++] = "人民币";
			// saPageContent[index++] =
			// Constant.CurrencyType.getName(info.getCurrencyID());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ "：" + ChineseCurrency.showChinese(sTemp);

			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(info.getExamineAmount());

			sTemp = "";
			// 借款期限 自 开始日期
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = "";
			// 借款期限 自 结束日期
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页 3 item
			sContent = "";
			index = 0;
			// saPageContent[index++] =
			// "中国人民银行公布的同期同档次贷款利率下浮"+DataFormat.formatRate(info.getInterestRate());
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate());
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页 1 item
			sContent = "";
			index = 0;

			// 还款来源
			saPageContent[0] = info.getOther() == null ? "" : info.getOther();
			// sContent += formatString(saPageContent[0]) + CONTENT_SEPERATOR +
			// PAGE_SEPERATOR;
			sContent += formatString(saPageContent[0]) + PAGE_SEPERATOR;
			/*
			 * for (i = 0; i < FIFTHPAGECOUNT; i++) { if (i == FIFTHPAGECOUNT -
			 * 1) { sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR; }
			 * else { sContent += formatString(saPageContent[i]) +
			 * CONTENT_SEPERATOR; } } //
			 */
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			index = 0;
			saPageContent[0] = " ";// 担保方式
			// sContent += formatString(saPageContent[0]) + CONTENT_SEPERATOR +
			// PAGE_SEPERATOR;
			sContent += formatString(saPageContent[0]) + PAGE_SEPERATOR;
			/*
			 * for (i = 0; i < SIXPAGECOUNT; i++) { if (i == SIXPAGECOUNT - 1) {
			 * sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR; }
			 * else { sContent += formatString(saPageContent[i]) +
			 * CONTENT_SEPERATOR; } } //
			 */
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// 合同第8页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// 合同第9页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// 合同第10页 11 item
			sContent = "";
			sTemp = "";
			index = 0;

			saPageContent[index++] = "";// 合同份数
			// 借款人（甲方）
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// 贷款人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_NAME;

			// 法定代表人（甲方）
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();

			// 法定代表人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// 委托代理人（甲方）
			saPageContent[index++] = "";
			// 委托代理人（乙方）
			saPageContent[index++] = "";

			// 签订时间
			sTemp = DataFormat.getDateString();
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 签订地点
			saPageContent[index++] = "";

			for (i = 0; i < TENTHPAGECOUNT; i++) {
				if (i == TENTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 10, sContent);

			// 合同11页 附件
			// *
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			// 初始化EJB
			Collection cPlan = null;
			try {
				RepayPlanHome repayplanHome = (RepayPlanHome) EJBObject
						.getEJBHome("RepayPlanHome");
				RepayPlan repayplan = repayplanHome.create();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			String sDate = "";
			long lPAY = 0;
			long lRepay = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						lPAY++;
						sDate = DataFormat.formatDate(rp_info.tsPlanDate);
					}

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						lRepay++;
					}
				}
			}

			sContent = "";
			index = 0;

			// *
			if (cPlan != null) {
				// sTemp += " <div style='page-break-before:always'> </div>";
				sTemp += " <b>附件：</b> <p>&nbsp;</p>";
				sTemp += " <table border=0 width=100%>";
				sTemp += " <tr> ";
				sTemp += " <td width=50% valign=top>";
				sTemp += " <table border=1 width=100%>";
				sTemp += " <tr> ";
				sTemp += " <td colspan=2 align=middle> 用款计划 </td>";
				sTemp += " </tr> ";
				sTemp += " <tr> ";
				sTemp += " <td width=50% align=middle > 提款日期 </td>";
				sTemp += " <td width=50% align=middle > 提款金额 </td>";
				sTemp += " </tr>";

				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						sTemp += " <tr>";
						sTemp += " <td width=50% align=middle > "
								+ DataFormat.formatDate(rp_info.tsPlanDate)
								+ " </td>";
						sTemp += " <td width=50% align=right > "
								+ DataFormat.formatListAmount(rp_info.dAmount)
								+ " </td>";
						sTemp += " </tr>";
						dTemp += rp_info.dAmount;
					}
				}

				sTemp += "  <tr>";
				sTemp += "  <td width=50% align=middle > 合计： </td>";
				sTemp += "  <td width=50%  align=right > "
						+ DataFormat.formatListAmount(dTemp) + " </td>";
				sTemp += "  </tr>";
				sTemp += "   </table>";
				sTemp += "  </td>";
				sTemp += "  <td width=50% valign=top>";
				sTemp += "  <table border=1 width=100%>";
				sTemp += "  <tr>";
				sTemp += "  <td colspan=2 align=middle> 还款计划 </td>";
				sTemp += "  </tr>";
				sTemp += "  <tr>";
				sTemp += "  <td width=50% align=middle > 还款日期 </td>";
				sTemp += "  <td width=50% align=middle > 还款金额 </td>";
				sTemp += "  </tr>";
			}

			dTemp = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						sTemp += " <tr>";
						sTemp += " <td width=50% align=middle > "
								+ DataFormat.formatDate(rp_info.tsPlanDate)
								+ " </td>";
						sTemp += " <td width=50% align=right > "
								+ DataFormat.formatListAmount(rp_info.dAmount)
								+ " </td>";
						sTemp += " </tr>";
						dTemp += rp_info.dAmount;
					}
				}

				sTemp += "  <tr>";
				sTemp += " <td width=50% align=middle > 合计： </td> ";
				sTemp += "  <td width=50%  align=right > "
						+ DataFormat.formatListAmount(dTemp) + " </td>";
				sTemp += "  </tr>";
				sTemp += "  </table>";
				sTemp += " </td>";
				sTemp += "  </tr>";
				sTemp += "  </table>";
			}

			if (cPlan != null) {
				sTemp = getPlanTab(cPlan);
			}

			sContent = sTemp + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 11, sContent);
			// */

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * Haier 填写文本缺省值 保证合同 Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            担保标示（LOAN_LOANCONTRACTASSURE表中的标示）
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHaierAssure(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 22;
		// int SECONDPAGECOUNT = 7;
		// int FOURTHPAGECOUNT = 1;
		// int FIFTHPAGECOUNT = 1;
		int SIXTHPAGECOUNT = 11;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount ");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
				// info.setExamineAmount(rs.getDouble("mExamineAmount")); //合同金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			// 合同编号
			saPageContent[index++] = info.getContractCode() == null ? "" : info
					.getContractCode();

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = info.getContractCode() == null ? "" : info
					.getContractCode();

			// saPageContent[index++] = "人民币";
			if (info.getCurrencyID() > 0) {
				saPageContent[index++] = Constant.CurrencyType.getName(info
						.getCurrencyID());
			} else {
				saPageContent[index++] = "人民币";
			}

			sTemp = "0";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			saPageContent[index++] = "";// 合同份数

			// 保证人（甲方）
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// 债权人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// 法定代表人（甲方）
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			// 法定代表人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = "";// 委托代理人（甲方）
			saPageContent[index++] = "";// 委托代理人（乙方）

			// 签订时间
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = "";// 签订地点

			for (i = 0; i < SIXTHPAGECOUNT; i++) {
				if (i == SIXTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * Haier 填写文本缺省值 质押合同 Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            担保标示（LOAN_LOANCONTRACTASSURE表中的标示）
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHaierImpawn(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo bInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 24;
		int SECONDPAGECOUNT = 1;
		int THIRDPAGECOUNT = 1;
		int FIFTHPAGECOUNT = 1;
		int SIXTHPAGECOUNT = 11;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cc.sLegalPerson sLegalPerson2,cc.sProvince sProvince2,");
			sb.append(" cc.sCity sCity2,cc.sAddress sAddress2,");
			sb.append(" cc.sPhone sPhone2,cc.sFax sFax2,");
			sb.append(" cc.sZipCode sZipCode2,cc.sBank1 sBank12,");
			sb.append(" cc.sAccount sAccount2,cc.sName sName2,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				// 担保
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				// 贷款客户
				bInfo.setName(rs.getString("sName2")); // 客户名称
				bInfo.setLegalPerson(rs.getString("sLegalPerson2")); // 法人代表
				bInfo.setAddress(getAddress(rs.getString("sProvince2"), rs
						.getString("sCity2"), rs.getString("sAddress2"))); // 地址
				bInfo.setPhone(rs.getString("sPhone2")); // 电活
				bInfo.setFax(rs.getString("sFax2")); // 传真
				bInfo.setZipCode(rs.getString("sZipCode2")); // 邮政编码
				bInfo.setBank1(rs.getString("sBank12")); // 开户银行
				bInfo.setAccount(rs.getString("sAccount2")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页

			// 借款人（甲方）
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();
			saPageContent[index++] = (bInfo.getAddress() == null) ? "" : bInfo
					.getAddress();
			// saPageContent[index++] = (bInfo.getZipCode() == null) ? "" :
			// cInfo.getZipCode();
			saPageContent[index++] = (bInfo.getLegalPerson() == null) ? ""
					: bInfo.getLegalPerson();
			saPageContent[index++] = (bInfo.getFinanceManager() == null) ? ""
					: bInfo.getFinanceManager();
			saPageContent[index++] = (bInfo.getPhone() == null) ? "" : bInfo
					.getPhone();
			// saPageContent[index++] = (cInfo.getFax() == null) ? "" :
			// cInfo.getFax();
			saPageContent[index++] = (bInfo.getBank1() == null) ? "" : bInfo
					.getBank1();
			saPageContent[index++] = (bInfo.getAccount() == null) ? "" : bInfo
					.getAccount();
			// 贷款人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			// saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// 保证人（丙方）
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			// saPageContent[index++] = (cInfo.getZipCode() == null) ? "" :
			// cInfo.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getFinanceManager() == null) ? ""
					: cInfo.getFinanceManager();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			// saPageContent[index++] = (cInfo.getFax() == null) ? "" :
			// cInfo.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = "0";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getContractCode();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getAssureAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// saPageContent[index++] = "";//金额
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {// 风险抵押金和本次贷款的担保数额（即人民币 万元整）之和
				sTemp = DataFormat.formatAmount(info.getAssureAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {// 丙方的作为担保的应收账款
				sTemp = DataFormat.formatAmount(info.getAssureAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// saPageContent[index++] = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			saPageContent[index++] = "";// 合同份数

			saPageContent[index++] = (bInfo.getName() == null) ? "" : bInfo
					.getName();
			saPageContent[index++] = (bInfo.getLegalPerson() == null) ? ""
					: bInfo.getLegalPerson();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();

			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = "";// 地点

			for (i = 0; i < SIXTHPAGECOUNT; i++) {
				if (i == SIXTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * Haier 填写文本缺省值 贷款调查表 Create Date: 2004-10-29
	 * 
	 * @param loanID
	 * @return String 返回调查表文件名及路径
	 * @exception Exception
	 */
	public String addHaierLoanQuestionary(long loanID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		long clientID = -1;

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		String[] sConsignInfo = new String[10];
		ClientInfo cInfo = new ClientInfo();

		int FIRSTPAGECOUNT = 4;
		int SECONDPAGECOUNT = 25;
		int THIRDPAGECOUNT = 58;
		int FOUTHPAGECOUNT = 6;
		int FIFTHPAGECOUNT = 17;

		int i;

		try {
			conn = Database.getConnection();

			sb.setLength(0);
			sb.append(" select c.*,parent.sName as ParentCorpName ");
			sb.append(" ,Office.sName as OfficeName ");
			// sb.append(" ,d.sName as LoanClientType,e.sName as
			// EnterpriceTypeName ");
			sb.append(" from loan_loanForm l,client c,client parent ");
			sb.append(" ,Office,");
			// sb.append( "LOAN_ClientType d,SETT_ENTERPRICETYPE e ");
			sb.append(" where c.id=l.nBorrowClientID ");
			sb.append(" and c.NPARENTCORPID1 = parent.ID(+) ");
			// sb.append(" and c.NLOANCLIENTTYPEID=d.ID(+) ");
			sb.append(" and c.NOFFICEID = Office.ID(+) ");
			// sb.append(" and c.NCORPNATUREID=e.ID(+) ");
			sb.append("  and l.id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, loanID);
			rs = ps.executeQuery();

			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setOfficeName(rs.getString("OfficeName")); // 财务公司名称
				cInfo.setCode(rs.getString("SCODE"));// 客户编号
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); // 母公司编号
				cInfo.setParentCorpName(rs.getString("ParentCorpName")); // 上级单位名称
				// cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //企业性质
				// cInfo.setCorpNatureName( rs.getString("EnterpriceTypeName"));
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				// cInfo.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID"));//客户分类
				// 自营贷款
				// cInfo.setLoanClientType(rs.getString("LoanClientType"));//中文描述
				cInfo.setCaptial(rs.getString("SCAPITAL")); // 注册资本
				cInfo.setDealScope(rs.getString("SDEALSCOPE"));// 经营范围
				cInfo.setFinanceManager(rs.getString("financialcontrolor"));// 财务负责人
			}

			cleanup(rs);
			cleanup(ps);

			// 第1页
			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 第2页,
			int index = 0;
			sContent = "";

			// 借款人申报情况
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			// 借款人基本情况
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = cInfo.getCaptial() == null ? "" : cInfo
					.getCaptial();// 注册资本
			// saPageContent[index++]
			// =cInfo.getCorpNatureName()==null?"":cInfo.getCorpNatureName();//企业性质
			saPageContent[index++] = cInfo.getDealScope() == null ? "" : cInfo
					.getDealScope();// 主营业务
			saPageContent[index++] = cInfo.getFinanceManager() == null ? ""
					: cInfo.getFinanceManager();// 财务负责人
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = "";// 基本情况（人员素质、生产销售、市场等情况）评价

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 第3页
			sContent = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 第4页
			sContent = "";
			for (i = 0; i < FOUTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < FOUTHPAGECOUNT; i++) {
				if (i == FOUTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 第5页
			sContent = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 中电子借款合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 */
	public String addCECLoan(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 13; // 31;
		int SECONDPAGECOUNT = 4;
		int THIRDPAGECOUNT = 0;
		int FOURTHPAGECOUNT = 3;
		int FIFTHPAGECOUNT = 5;
		int SIXTHPAGECOUNT = 8;
		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {

				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractCode(rs.getString("sContractCode")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setCurrencyID(rs.getLong("nCurrencyID"));
				info.setIsAssure(rs.getLong("nIsAssure"));
				info.setIsCredit(rs.getLong("nIsCredit"));
				info.setIsImpawn(rs.getLong("nIsImpawn"));
				info.setIsPledge(rs.getLong("nIsPledge"));
				info.setInputDate(rs.getTimestamp("dtInputDate"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			// saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getBankAccount1() == null) ? ""
					: cInfo.getBankAccount1();
			saPageContent[index++] = (info.getLoanTypeID() > 0) ? ""
					: LOANConstant.LoanType.getName(info.getLoanTypeID());
			saPageContent[index++] = (info.getLoanPurpose() == null) ? ""
					: info.getLoanPurpose();
			saPageContent[index++] = (info.getCurrencyID() > 0) ? ""
					: Constant.CurrencyType.getName(info.getCurrencyID());

			String sTemp = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 第二页
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = info.getInterestRate() > 0 ? "" : info
					.getFormatInterestRate();

			saPageContent[index++] = "季";
			saPageContent[index++] = "季";

			Collection cPlan = null;
			try {
				RepayPlanHome repayplanHome = (RepayPlanHome) EJBObject
						.getEJBHome("RepayPlanHome");
				RepayPlan repayplan = repayplanHome.create();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			saPageContent[index++] = getCECPlanTab(cPlan);

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 第三页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 第 4 页
			sContent = "";
			index = 0;
			sTemp = "";
			for (i = 0; i < FOURTHPAGECOUNT; i++)
				saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 第 5 页
			sContent = "";
			index = 0;
			sTemp = "";

			if (info.getIsCredit() == Constant.YesOrNo.YES)
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (info.getIsAssure() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE);
			}
			if (info.getIsImpawn() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN);
			}
			if (info.getIsPledge() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.PLEDGE);
			}
			saPageContent[index++] = sTemp;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 第 6 页
			sContent = "";
			index = 0;
			sTemp = "";

			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < SIXTHPAGECOUNT; i++) {
				if (i == SIXTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 中电子保证合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECAssure(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 13;
		int THIRDPAGECOUNT = 1;
		int FOURTHPAGECOUNT = 10;
		// int FIFTHPAGECOUNT = 1;
		// int SIXTHPAGECOUNT = 11;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyID as currencyID,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setCurrencyID(rs.getLong("currencyID"));

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp);

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页

			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 中电子股权质押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECImpawnGQ(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 5;
		int THIRDPAGECOUNT = 2;
		int FOURTHPAGECOUNT = 4;
		int FIFTHPAGECOUNT = 8;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as nCurrency,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额
				info.setCurrencyID(rs.getLong("nCurrency"));// 币种

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页

			// 借款人（甲方）
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			// saPageContent[index++] = (cInfo.getFinanceManager() == null) ? ""
			// : cInfo.getFinanceManager();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			// 为解决数值很大的时候取得的 getExamineAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = (info.getAssureAmount() > 0) ? Constant.CurrencyType
					.getName(info.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// 风险抵押金和本次贷款的担保数额（即人民币 万元整）之和？？？
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// 风险抵押金和本次贷款的担保数额（即人民币 万元整）之和？？？
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 中电子股份质押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECImpawnGF(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 4;
		int THIRDPAGECOUNT = 3;
		int FOURTHPAGECOUNT = 2;
		int FIFTHPAGECOUNT = 8;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as nCurrency,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额
				info.setCurrencyID(rs.getLong("nCurrency"));

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页

			// 借款人（甲方）
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			// saPageContent[index++] = (cInfo.getFinanceManager() == null) ? ""
			// : cInfo.getFinanceManager();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 为解决数值很大的时候取得的 getExamineAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = (info.getAssureAmount() > 0) ? Constant.CurrencyType
					.getName(info.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// 风险抵押金和本次贷款的担保数额（即人民币 万元整）之和？？？
			saPageContent[index++] = "";
			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 中电子存单质押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECImpawnCD(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 12;
		int SECONDPAGECOUNT = 7;
		// int THIRDPAGECOUNT = 2;
		int FOURTHPAGECOUNT = 2;
		int FIFTHPAGECOUNT = 10;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as nCurrency,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额
				info.setCurrencyID(rs.getLong("nCurrency"));// 币种

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页

			// 借款人（甲方）
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			// saPageContent[index++] = (cInfo.getFinanceManager() == null) ? ""
			// : cInfo.getFinanceManager();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";

			// 为解决数值很大的时候取得的 getExamineAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = (info.getAssureAmount() > 0) ? Constant.CurrencyType
					.getName(info.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp)
					: "";

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// 风险抵押金和本次贷款的担保数额（即人民币 万元整）之和？？？
			saPageContent[index++] = "";
			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 中电子抵押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECPledge(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 13;
		int SECONDPAGECOUNT = 6;
		int SIXPAGECOUNT = 8;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as currencyID,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额

				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额
				info.setCurrencyID(rs.getLong("currencyID"));

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = (info.getBorrowClientName() == null ? ""
					: info.getBorrowClientName());
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp);

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			df = new DecimalFormat("#.00");
			if (info.getAllAmount() > 0)
				tmp = df.format(info.getAllAmount());
			saPageContent[index++] = (info.getAllAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第 6 页
			sContent = "";
			index = 0;
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 中电子委托借款合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 * @author zntan
	 */
	public String addCECWTJK(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo assureInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 43;
		int SECONDPAGECOUNT = 10;
		int THIRDPAGECOUNT = 17;
		// int FOURTHPAGECOUNT = 6;
		// int FIFTHPAGECOUNT = 4;
		// int EIGHTHPAGECOUNT = 3;
		// int NINTHPAGECOUNT = 25;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");

			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount,");

			sb
					.append(" ac.sName as AssureName,ac.sLegalPerson as AssureLegalPerson,");
			sb
					.append(" ac.sProvince as AssureProvince,ac.sCity as AssureCity,");
			sb
					.append(" ac.sAddress as AssureAddress,ac.sPhone as AssurePhone,");
			sb.append(" ac.sFax as AssureFax,ac.sZipCode as AssureZipCode,");
			sb.append(" ac.sBank1 as AssureBank1,ac.sAccount as AssureAccount");

			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID(+)");
			sb.append(" AND con.nConsignClientID = cc.id(+)");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
				consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
				consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号
				assureInfo.setName(rs.getString("AssureName")); // 客户名称
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // 法人代表
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // 地址
				assureInfo.setPhone(rs.getString("AssurePhone")); // 电活
				assureInfo.setFax(rs.getString("AssureFax")); // 传真
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // 邮政编码
				assureInfo.setBank1(rs.getString("AssureBank1")); // 开户银行
				assureInfo.setAccount(rs.getString("AssureAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setCurrencyID(rs.getLong("nCurrencyId"));// 币种
				info.setLoanTypeID(rs.getLong("nTypeId"));// 贷款种类
				info.setInterestRate(rs.getDouble("mInterestRate"));// 利率
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // 手续费率类型
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;

			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			saPageContent[index++] = "";
			saPageContent[index++] = LOANConstant.LoanType.getName(info
					.getLoanTypeID());
			// 为解决数值很大的时候取得的 mLoanAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			saPageContent[index++] = (info.getLoanPurpose() == null) ? ""
					: info.getLoanPurpose();
			saPageContent[index++] = (info.getInterestRate() > 0) ? ""
					: DataFormat.formatRate(info.getInterestRate());
			long lYear = -1;
			long lMonth = -1;
			lYear = info.getIntervalNum() / 12;
			lMonth = info.getIntervalNum() % 12;
			saPageContent[index++] = String.valueOf(lYear);
			saPageContent[index++] = String.valueOf(lMonth);
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			// 初始化EJB

			sContent = "";
			index = 0;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 通过年份和doc类型获得contractcontentinfos
	 * 
	 * @param
	 * @return
	 * @exception Exception
	 */
	public Collection findContractContentInfos(
			ContractContentQueryInfo queryInfo) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		Vector v = new Vector();
		long ret = -1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT *");
			sbSQL.append(" FROM loan_ContractContent");
			sbSQL.append(" WHERE 1=1 ");
			if (queryInfo.getParentId() > 0) {
				sbSQL.append(" and NPARENTID =")
						.append(queryInfo.getParentId());
			}
			if (queryInfo.getDocType() != -1) {
				sbSQL.append(" and nContractTypeID =").append(
						queryInfo.getDocType());
			}
			if (queryInfo.getYear() != null && !queryInfo.getYear().equals("")) {
				// 如果有季度
				if (queryInfo.getSeason() != null
						&& !queryInfo.getSeason().equals("")) {
					sbSQL.append(" and scode = '").append(queryInfo.getYear())
							.append(queryInfo.getSeason()).append("'");
				} else {
					sbSQL.append(" and scode like '").append(
							queryInfo.getYear()).append("%'");
				}
			} else if (queryInfo.getSeason() != null
					&& !queryInfo.getSeason().equals("")) {
				sbSQL.append(" and scode like '%")
						.append(queryInfo.getSeason()).append("'");
			}

			sbSQL.append(" order by scode ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID"));
				info.setParentID(rs.getLong("nParentID")); // 合同主体的ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); // 合同类型ID
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setDocName(rs.getString("sDocName")); // 合同文本文件名
				info.setCode(rs.getString("scode"));// 文本code
				v.add(info);
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
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return v;
	}

	/**
	 * 通过年份和doc类型获得contractcontentinfos
	 * 
	 * @param
	 * @return
	 * @exception Exception
	 */
	public long getContractContentID(ContractContentQueryInfo queryInfo)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;
		long lReturn = -1;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT *");
			sbSQL.append(" FROM loan_ContractContent");
			sbSQL.append(" WHERE 1=1 ");
			if (queryInfo.getParentId() > 0) {
				sbSQL.append(" and NPARENTID =")
						.append(queryInfo.getParentId());
			}
			if (queryInfo.getDocType() != -1) {
				sbSQL.append(" and nContractTypeID =").append(
						queryInfo.getDocType());
			}
			if (queryInfo.getYear() != null && !queryInfo.getYear().equals("")) {
				// 如果有季度
				if (queryInfo.getSeason() != null
						&& !queryInfo.getSeason().equals("")) {
					sbSQL.append(" and scode = '").append(queryInfo.getYear())
							.append(queryInfo.getSeason()).append("'");
				} else {
					sbSQL.append(" and scode = '").append(queryInfo.getYear())
							.append("'");
				}
			} else if (queryInfo.getSeason() != null
					&& !queryInfo.getSeason().equals("")) {
				sbSQL.append(" and scode = '").append(queryInfo.getSeason())
						.append("'");
			}

			sbSQL.append(" order by scode ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				lReturn = rs.getLong("ID");
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
		} catch (Exception e) {
			log4j.error(e.toString());
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
		return lReturn;
	}

	/**
	 * 中电子委托贷款委托合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECWTDKWT(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo assureInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 31;
		int SECONDPAGECOUNT = 13;
		int THIRDPAGECOUNT = 13;
		// int FOURTHPAGECOUNT = 6;
		// int FIFTHPAGECOUNT = 4;
		// int EIGHTHPAGECOUNT = 3;
		// int NINTHPAGECOUNT = 25;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");

			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount,");

			sb
					.append(" ac.sName as AssureName,ac.sLegalPerson as AssureLegalPerson,");
			sb
					.append(" ac.sProvince as AssureProvince,ac.sCity as AssureCity,");
			sb
					.append(" ac.sAddress as AssureAddress,ac.sPhone as AssurePhone,");
			sb.append(" ac.sFax as AssureFax,ac.sZipCode as AssureZipCode,");
			sb.append(" ac.sBank1 as AssureBank1,ac.sAccount as AssureAccount");

			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID(+)");
			sb.append(" AND con.nConsignClientID = cc.id(+)");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
				consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
				consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号
				assureInfo.setName(rs.getString("AssureName")); // 客户名称
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // 法人代表
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // 地址
				assureInfo.setPhone(rs.getString("AssurePhone")); // 电活
				assureInfo.setFax(rs.getString("AssureFax")); // 传真
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // 邮政编码
				assureInfo.setBank1(rs.getString("AssureBank1")); // 开户银行
				assureInfo.setAccount(rs.getString("AssureAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setCurrencyID(rs.getLong("nCurrencyId"));// 币种
				info.setLoanTypeID(rs.getLong("nTypeId"));// 贷款种类
				info.setInterestRate(rs.getDouble("mInterestRate"));// 利率
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // 手续费率类型
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? ""
					: consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? ""
					: consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? ""
					: consignInfo.getAccount();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? ""
					: consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? ""
					: consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getFax() == null) ? ""
					: consignInfo.getFax();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			// 为解决数值很大的时候取得的 mLoanAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			saPageContent[index++] = "";
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			// 为解决数值很大的时候取得的 mLoanAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			df = new DecimalFormat("#.00");
			tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);

			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "季";
			saPageContent[index++] = "季";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate() / 10.000000);
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页

			sContent = "";
			index = 0;

			saPageContent[index++] = DataFormat.formatString(consignInfo
					.getName());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(consignInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 中电子贴现合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addCECTX(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 2;
		int THIRDPAGECOUNT = 4;
		int FOURTHPAGECOUNT = 9;
		int FIFTHPAGECOUNT = -1;// 根据票据张数 * 18

		int i;

		long lBizAcceptPO = 0;
		Timestamp DiscountDate = null;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入贴现合同文本(商业汇票)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //开户银行账号
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setContractID(rs.getLong("contractID"));
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setLoanAmount(rs.getDouble("mLoanAmount"));// 票面金额
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 录入时间
				lBizAcceptPO = rs.getLong("nbizacceptpo");// 贴现-商业汇票张数
				DiscountDate = rs.getTimestamp("dtDiscountDate");// 贴现日
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
				info.setDiscountClientName(rs.getString("DiscountClientName"));// 出票人名称
				info.setIsPurchaserInterest(rs.getLong("isPurchaserInterest"));// 是否买方付息
			}

			int index = 0;

			String sTemp = "";
			// 合同第1页
			// /*
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();

			// 如果买方付息，则贴现申请人应为出票人
			if (info.getIsPurchaserInterest() == Constant.YesOrNo.YES) {
				saPageContent[index++] = DataFormat.formatString(info
						.getDiscountClientName());
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			} else {
				saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
						.getName();
				saPageContent[index++] = (cInfo.getAddress() == null) ? ""
						: cInfo.getAddress();
				saPageContent[index++] = DataFormat.formatString(cInfo
						.getZipCode());
				saPageContent[index++] = DataFormat.formatString(cInfo
						.getLegalPerson());
				saPageContent[index++] = DataFormat.formatString(cInfo
						.getPhone());
				saPageContent[index++] = DataFormat
						.formatString(cInfo.getFax());
				saPageContent[index++] = DataFormat.formatString(cInfo
						.getBank1());
				saPageContent[index++] = DataFormat.formatString(cInfo
						.getAccount());
			}

			saPageContent[index++] = DataFormat
					.getChineseDateString(DiscountDate);

			String strStartDate = "";
			String strEndDate = "";
			Timestamp tsStart = null;
			Timestamp tsEnd = null;
			long nDays = -1;
			if (info.getLoanStart() != null && info.getLoanEnd() != null) {
				strStartDate = info.getLoanStart().toString();
				tsStart = new java.sql.Timestamp(new Integer(strStartDate
						.substring(0, 4)).intValue() - 1900, new Integer(
						strStartDate.substring(5, 7)).intValue() - 1,
						new Integer(strStartDate.substring(8, 10)).intValue(),
						0, 0, 0, 0);
				strEndDate = info.getLoanEnd().toString();
				tsEnd = new java.sql.Timestamp(new Integer(strEndDate
						.substring(0, 4)).intValue() - 1900, new Integer(
						strEndDate.substring(5, 7)).intValue() - 1,
						new Integer(strEndDate.substring(8, 10)).intValue(), 0,
						0, 0, 0);
				nDays = (int) java.lang.Math.ceil((tsStart.getTime() - tsEnd
						.getTime()) / 86400000);
			}

			saPageContent[index++] = (nDays > 0) ? String.valueOf(nDays) : "";
			saPageContent[index++] = DataFormat.formatRate(info
					.getDiscountRate() / 360);
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if ((info.getLoanAmount() - info.getExamineAmount()) > 0)
				tmp = df.format(info.getLoanAmount() - info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);
			// */
			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatString(info
					.getLoanPurpose());

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第 3 页
			sContent = "";
			index = 0;
			sTemp = "";

			for (i = 0; i < THIRDPAGECOUNT; i++)
				saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第 4 页
			sContent = "";
			index = 0;
			sTemp = "";

			saPageContent[index++] = "";
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第 5 页

			DiscountDao discountDao = new DiscountDao();

			Collection cBill = null;
			long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
			cBill = discountDao.findDiscountBillByContractID(info
					.getContractID(), 1000, 1, 1, lDesc);

			if (cBill != null)
				FIFTHPAGECOUNT = cBill.size() * 18 + 1;
			sContent = "";
			index = 0;
			sTemp = "";

			saPageContent[index++] = "在合同未审核完成之前尚无法打印贴现清单！";
			Iterator it = null;
			if (cBill != null)
				it = cBill.iterator();
			for (int j = 0; it.hasNext(); j++) {
				DiscountBillInfo billInfo = (DiscountBillInfo) it.next();
				saPageContent[index++] = DataFormat.formatString(billInfo
						.getCode());
				saPageContent[index++] = DataFormat
						.formatString(LOANConstant.DraftType.getName(billInfo
								.getAcceptPOTypeID()));
				saPageContent[index++] = DataFormat
						.getChineseDateString(billInfo.getCreate());
				saPageContent[index++] = DataFormat
						.getChineseDateString(billInfo.getEnd());
				saPageContent[index++] = "";
				saPageContent[index++] = "";

				saPageContent[index++] = DataFormat.formatString(billInfo
						.getUserName());
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";

				saPageContent[index++] = DataFormat.formatString(cInfo
						.getName());
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = DataFormat.formatString(billInfo
						.getBank());
				saPageContent[index++] = "";

				df = new DecimalFormat("#.00");
				tmp = "0.00";
				if (billInfo.getAmount() > 0)
					tmp = df.format(billInfo.getAmount());
				saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);
			// Log.print("-----test--8---");

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入贴现合同文本(商业汇票)结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 中电子取得放还款计划
	 * 
	 * @param cPlan
	 * @return
	 * @throws Exception
	 */
	public String getCECPlanTab(Collection cPlan) throws Exception {
		Iterator iter = cPlan.iterator();
		int nPlan = cPlan.size();
		String sTemp = "";
		String[] strPayYear = new String[nPlan];
		String[] strPayMonth = new String[nPlan];
		String[] strPayDay = new String[nPlan];
		String[] strRepayYear = new String[nPlan];
		String[] strRepayMonth = new String[nPlan];
		String[] strRepayDay = new String[nPlan];
		String[] strPayAmount = new String[nPlan];
		String[] strRepayAmount = new String[nPlan];
		double dTotalPay = 0;
		double dTotalRepay = 0;
		int iPay = 0;
		int iRepay = 0;
		try {

			iter = cPlan.iterator();
			for (int i = 0; iter.hasNext(); i++) {
				RepayPlanInfo rp_info = (RepayPlanInfo) iter.next();
				if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) //
				{
					if (rp_info.tsPlanDate != null) {
						sTemp = rp_info.tsPlanDate.toString();
						if (sTemp.length() > 9) {
							strPayYear[iPay] = sTemp.substring(0, 4);
							strPayMonth[iPay] = sTemp.substring(5, 7);
							strPayDay[iPay] = sTemp.substring(8, 10);
						} else {
							strPayYear[iPay] = "";
							strPayMonth[iPay] = "";
							strPayDay[iPay] = "";
						}
					} else {
						strPayYear[iPay] = "";
						strPayMonth[iPay] = "";
						strPayDay[iPay] = "";
					}
					if (rp_info.dAmount >= 0) {
						strPayAmount[iPay] = DataFormat
								.formatListAmount(rp_info.dAmount);
					} else {
						strPayAmount[iPay] = "&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				} else {
					if (rp_info.tsPlanDate != null) {
						sTemp = rp_info.tsPlanDate.toString();
						if (sTemp.length() > 9) {
							strRepayYear[iRepay] = sTemp.substring(0, 4);
							strRepayMonth[iRepay] = sTemp.substring(5, 7);
							strRepayDay[iRepay] = sTemp.substring(8, 10);
						} else {
							strRepayYear[iRepay] = "";
							strRepayMonth[iRepay] = "";
							strRepayDay[iRepay] = "";
						}

					} else {
						strRepayYear[iRepay] = "";
						strRepayMonth[iRepay] = "";
						strRepayDay[iRepay] = "";
					}
					if (rp_info.dAmount >= 0) {
						strRepayAmount[iRepay] = DataFormat
								.formatListAmount(rp_info.dAmount);
					} else {
						strRepayAmount[iRepay] = "&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}

			sTemp = "";
			sTemp += " <table width=100% border=0 cellspacing=0 class=table1 >";
			sTemp += " <tr>";
			sTemp += " <td colspan=4 align=center  width=50% class=td-rightbottom > 借  款</td>";
			sTemp += " <td colspan=4 align=center  class=td-bottom > 还  款</td>";
			sTemp += " </tr>";
			sTemp += " <tr align=center>";
			sTemp += " <td class=td-rightbottom width=10%>年</td>";
			sTemp += " <td class=td-rightbottom width=10%>月</td>";
			sTemp += " <td class=td-rightbottom width=10%>日</td>";
			sTemp += " <td class=td-rightbottom width=20%>金额</td>";
			sTemp += " <td class=td-rightbottom width=10%>年</td>";
			sTemp += " <td class=td-rightbottom width=10%>月</td>";
			sTemp += " <td class=td-rightbottom width=10%>日</td>";
			sTemp += " <td class=td-bottom width=20%>金额</td>";
			sTemp += " </tr>";

			int iCount = iRepay > iPay ? iRepay : iPay;

			if (iCount > 0) {
				for (int j = 0; j < iCount; j++) {
					sTemp += " <TR>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayYear[j]) + " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayMonth[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayDay[j]) + " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strPayAmount[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayYear[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayMonth[j])
							+ " </TD>";
					sTemp += " <TD class=td-rightbottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayDay[j])
							+ " </TD>";
					sTemp += " <TD class=td-bottom align=middle>&nbsp;"
							+ DataFormat.formatString(strRepayAmount[j])
							+ " </TD>";
					sTemp += " </TR>";
				}
			} else {
				sTemp += " <TR>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;</TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp; </TD>";
				sTemp += " <TD class=td-bottom align=middle>&nbsp; </TD>";
				sTemp += " </TR>";
			}

			sTemp += " </table>";

		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return sTemp;
	}

	/**
	 * 中电子贴现合同中取得贴现票据列表的方法 在合同审核完成以后把列表项的内容组合成一个 table 保存为合同文本的最后一个栏位
	 * 
	 * @param lContractID
	 * @return
	 * @throws Exception
	 */
	public String saveCECBillTab(long lContractID) throws Exception {
		System.out.println("====开始替换合同文本最后一页===");
		String strFileName = "";
		Collection contractContent = null;
		Iterator it = null;
		Iterator billIt = null;
		String sContent = "";
		String arrContent[] = new String[arrLen];
		int index = 0;
		Collection cBill = null;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

		// 查找贴现合同文本，取出最后一页（第五页）的内容
		ContractDao cDao = new ContractDao();
		contractContent = cDao.getContractContent(lContractID);
		if (contractContent != null)
			it = contractContent.iterator();
		if (it != null) {
			while (it.hasNext()) {
				ContractContentInfo cInfo = (ContractContentInfo) it.next();
				strFileName = cInfo.getDocName();
				System.out.println("ContractContent file name:" + strFileName);
			}
		}
		sContent = getFileContent(strFileName, 5);

		// 查出该合同下的票据张数
		DiscountDao discountDao = new DiscountDao();
		cBill = discountDao.findDiscountBillByContractID(lContractID, 1000, 1,
				1, lDesc);

		// 取得改页的内容，存入数组
		if (sContent.length() > 0) {
			int nIndex; // ";;"的索引位置
			int nTmp = 0;
			nIndex = sContent.indexOf(CONTENT_SEPERATOR);
			while (nIndex >= 0) {
				arrContent[nTmp] = sContent.substring(0, nIndex);
				sContent = sContent.substring(nIndex + 4);
				nIndex = sContent.indexOf(ContractContentDao.CONTENT_SEPERATOR);
				nTmp++;
			}
			arrContent[nTmp] = sContent;
		}

		// 取出数组内容写成一个 html table
		sContent = "";
		index++;// 因为默认第一个栏位存了非票据信息的值
		if (cBill != null)
			billIt = cBill.iterator();
		if (billIt != null) {
			System.out.println("====开始写票据信息====");
			for (int j = 0; billIt.hasNext(); j++) {
				billIt.next();
				sContent += "<table class=table1 align=center>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom>汇票号码</td>";
				sContent += "<td class=td-rightbottom>汇票种类</td>";
				sContent += "<td class=td-rightbottom >出票日期</td>";
				sContent += "<td class=td-rightbottom >到期日期</td>";
				sContent += "<td class=td-rightbottom >交易合同字号</td>";
				sContent += "<td class=td-bottom >增值税发票号</td>";
				sContent += "</tr>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom >&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom >&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom >&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom >&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom >&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-bottom>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "</tr>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom >出票人全称</td>";
				sContent += "<td class=td-rightbottom >账号</td>";
				sContent += "<td class=td-rightbottom >开户行及行号</td>";
				sContent += "<td class=td-rightbottom >付款人全称</td>";
				sContent += "<td class=td-rightbottom >账号</td>";
				sContent += "<td class=td-bottom >开户行及行号</td>";
				sContent += "</tr>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom>&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom>&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom>&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom>&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-rightbottom>&nbsp;"
						+ arrContent[index++] + "</td>";
				sContent += "<td class=td-bottom>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "</tr>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom >收款人全称</td>";
				sContent += "<td class=td-rightbottom >账号</td>";
				sContent += "<td class=td-rightbottom >开户行及行号</td>";
				sContent += "<td class=td-rightbottom >承兑人全称</td>";
				sContent += "<td class=td-rightbottom >开户行及行号</td>";
				sContent += "<td class=td-bottom >汇票金额（大写）</td>";
				sContent += "</tr>";
				sContent += "<tr>";
				sContent += "<td class=td-right>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "<td class=td-right>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "<td class=td-right>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "<td class=td-right>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "<td class=td-right>&nbsp;" + arrContent[index++]
						+ "</td>";
				sContent += "<td >&nbsp;" + arrContent[index++] + "</td>";
				sContent += "</tr>";
				sContent += "</table>";
			}
		}

		// 保存 table 到合同文本最后一项
		strFileName = saveContent(strFileName, 5, sContent);
		System.out.println("ContractContent file name:" + strFileName);
		return strFileName;

	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHaierTXBank(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbBill = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 3;
		int FOURTHPAGECOUNT = 17;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入贴现合同文本（银行汇票）");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo
					.getAccount();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			saPageContent[index++] = info.getFormatDiscountRate();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			String sTemp = "";

			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			if (info.getCheckAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getCheckAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// saPageContent[index++] = "";
			// saPageContent[index++] = "";
			// saPageContent[index++] = "";

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sTemp = "";
			sContent = "";
			sb.setLength(0);
			sbBill.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			// sbBill.append(" <b>附件：</b> <p>&nbsp;</p>";
			sbBill
					.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
			sbBill.append(" <tr align=\"center\"> ");
			sbBill
					.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">序号</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">汇票号码</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">票面金额</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票人</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">承兑银行</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">到期日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现天数</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现利息%</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">实付贴现金额</font></td> ");
			sbBill.append(" </tr>");

			int nIndexPage = 0;
			index = 1;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				lHoliday = rs.getLong("nAddDays");
				if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
					lIsLocal = 3;
				}
				lDiscount = rs.getLong("discountDays"); // 贴现天数
				lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数
				dTemp = rs.getDouble("mAmount")
						- (rs.getDouble("mAmount")
								* (rs.getDouble("mDiscountRate") / 360) * 0.01 * lDiscount);

				if (nIndexPage == 15) {
					nIndexPage = 0;
					sbBill.append(" </table>");
					sbBill
							.append("   <br clear=all style='page-break-before:always'>");
					sbBill
							.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
					sbBill.append(" <tr align=\"center\"> ");
					sbBill
							.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">序号</font></td>");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">汇票号码</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">票面金额</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票人</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">承兑银行</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">到期日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现天数</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现利息%</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">实付贴现金额</font></td> ");
					sbBill.append(" </tr>");
				}
				sbBill.append(" <tr align=\"center\"> ");
				sbBill
						.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">"
								+ index + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat
										.formatString(rs.getString("sCode"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatDisabledAmount(rs
										.getDouble("mAmount"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatString(rs
										.getString("sUserName"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtCreate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat
										.formatString(rs.getString("sbank"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtDisCountDate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtEnd"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ String.valueOf(lDiscount) + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatRate(rs
										.getDouble("mDiscountRate"))
								+ "%</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatDisabledAmount(dTemp)
								+ "</font></td> ");
				sbBill.append(" </tr>");

				index++;
				nIndexPage++;
			}
			sbBill.append(" </table>");

			sContent = sbBill.toString();
			sFileName = saveContent(sFileName, 5, sContent);

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入贴现合同文本（银行汇票）结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 填写文本缺省值 贴现合同 Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHaierTXBiz(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbBill = new StringBuffer();

		String sFileName = "", sContent = "";
		// int arrLen = 5000;
		String[] saPageContent = new String[arrLen];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 20;
		int SECONDPAGECOUNT = 9;
		int FIFTHPAGECOUNT = 10;

		int i;

		long lBizAcceptPO = 0;
		Timestamp DiscountDate = null;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("开始写入贴现合同文本(商业汇票)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("合同ID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //开户银行账号
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 录入时间
				lBizAcceptPO = rs.getLong("nbizacceptpo");// 贴现-商业汇票张数
				DiscountDate = rs.getTimestamp("dtDiscountDate");// 贴现日
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // 贴现利率
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // 汇总贴现核定金额
			}

			int index = 0;

			// 合同第1页
			String sTemp = "";

			// Log.print("-----test--3---");
			// 合同编号
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// 签订日期
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 签署地点
			saPageContent[index++] = "";

			// 甲方
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// 乙方
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());

			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// 贴现商业汇票张数

			// 商业承兑汇票票面金额总额（大写）
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// 贴现利率
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// 贴现日
			sTemp = DataFormat.getDateString(DiscountDate);
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = "";

			// 贴现利息总额（大写）TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// 实付贴现金额总额（大写）TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// 贴现用途
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// 合同份数
			saPageContent[index++] = "";// 甲方持有份数
			saPageContent[index++] = "";// 乙方持有份数
			// saPageContent[index++] = "";//综合授信合同编号
			// saPageContent[index++] = "";//年
			// saPageContent[index++] = "";//字

			saPageContent[index++] = "";// 双方约定

			// 甲方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// 乙方
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = (cInfo.getName() == null) ? "" :
			// cInfo.getName();
			// saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" :
			// cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// Log.print("-----test--7---");
			// 合同第6页
			sTemp = "";
			sContent = "";
			sb.setLength(0);
			sbBill.setLength(0);
			sb
					.append(" SELECT con.dtDisCountDate,con.mDiscountRate,con.mCheckAmount,b.*,");
			sb.append(" (b.dtEnd - con.dtDisCountDate) as discountDays");
			sb
					.append(" FROM loan_contractForm con,loan_discountcontractbill b");
			sb.append(" WHERE con.ID = b.nContractID(+)");
			sb.append(" AND con.ID = ?");
			sb.append(" AND b.nStatusID > 0");
			sb.append(" ORDER BY b.nSerialNo");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			// sbBill.append(" <b>附件：</b> <p>&nbsp;</p>";
			sbBill
					.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
			sbBill.append(" <tr align=\"center\"> ");
			sbBill
					.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">序号</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">汇票号码</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">票面金额</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票人</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">承兑银行</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">到期日期</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现天数</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现利息%</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">实付贴现金额</font></td> ");
			sbBill.append(" </tr>");

			int nIndexPage = 0;
			index = 1;
			long lHoliday = 0;
			long lIsLocal = 0;
			long lDiscount = 0;
			double dTemp = 0;
			while (rs.next()) {
				lHoliday = 0;
				lIsLocal = 0;
				lDiscount = 0;
				dTemp = 0;

				lHoliday = rs.getLong("nAddDays");
				if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
					lIsLocal = 3;
				}
				lDiscount = rs.getLong("discountDays"); // 贴现天数
				lDiscount = lDiscount + lHoliday + lIsLocal; // 贴现天数
				dTemp = rs.getDouble("mAmount")
						- (rs.getDouble("mAmount")
								* (rs.getDouble("mDiscountRate") / 360) * 0.01 * lDiscount);

				if (nIndexPage == 15) {
					nIndexPage = 0;
					sbBill.append(" </table>");
					sbBill
							.append("   <br clear=all style='page-break-before:always'>");
					sbBill
							.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
					sbBill.append(" <tr align=\"center\"> ");
					sbBill
							.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">序号</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">汇票号码</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">票面金额</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票人</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">出票日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">承兑银行</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">到期日期</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现天数</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">贴现利息%</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">实付贴现金额</font></td> ");
					sbBill.append(" </tr>");
				}
				sbBill.append(" <tr align=\"center\"> ");
				sbBill
						.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"宋体\">"
								+ index + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat
										.formatString(rs.getString("sCode"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatDisabledAmount(rs
										.getDouble("mAmount"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatString(rs
										.getString("sUserName"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtCreate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat
										.formatString(rs.getString("sbank"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtDisCountDate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtEnd"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ String.valueOf(lDiscount) + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatRate(rs
										.getDouble("mDiscountRate"))
								+ "%</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"宋体\">"
								+ DataFormat.formatDisabledAmount(dTemp)
								+ "</font></td> ");
				sbBill.append(" </tr>");

				index++;
				nIndexPage++;
			}
			sbBill.append(" </table>");

			sContent = sbBill.toString();
			sFileName = saveContent(sFileName, 6, sContent);
			// Log.print("-----test--8---");

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			log4j.info("写入贴现合同文本(商业汇票)结束");

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气借款合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSEFCLoan(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 17;
		int SECONDPAGECOUNT = 3;
		int THIRDPAGECOUNT = 2;
		int FOURTHPAGECOUNT = 3;
		int FIFTHPAGECOUNT = 24;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 合同录入日期
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setIsAssure(rs.getLong("nIsAssure"));
				info.setIsCredit(rs.getLong("nIsCredit"));
				info.setIsImpawn(rs.getLong("nIsImpawn"));
				info.setIsPledge(rs.getLong("nIsPledge"));
				info.setContractCode(rs.getString("sContractCode"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ)
			// {
			// saPageContent[index++] = "中长期借款合同";
			// }
			// else if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEDQ)
			// {
			// saPageContent[index++] = "最高限额短期借款合同";
			// }
			// else if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// saPageContent[index++] = "最高限额中长期借款合同";
			// }
			// else
			// {
			// saPageContent[index++] = "短期借款合同";
			// }
			// saPageContent[index++] =
			// LOANNameRef.getSubLoanTypeNameByID(lLoanType);
			saPageContent[index++] = LOANConstant.SubLoanType.getName(info
					.getLoanTypeID());// info.getSubLoanTypeID() TODO

			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			String sTemp = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			saPageContent[index++] = DataFormat.formatString(info
					.getLoanPurpose());
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = String.valueOf(info.getInterestRate());

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			sTemp = "";

			if (info.getIsCredit() == Constant.YesOrNo.YES)
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (info.getIsAssure() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE);
			}
			if (info.getIsImpawn() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN);
			}
			if (info.getIsPledge() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "、";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.PLEDGE);
			}
			saPageContent[index++] = sTemp;
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			sTemp = "0";

			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_TEL);
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_FAX);
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ZIP);
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 上海电气最高额保证合同 by zntan
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSEFCAssureZGE(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 11;
		int SECONDPAGECOUNT = 0;
		int FOURTHPAGECOUNT = 0;
		int FIFTHPAGECOUNT = 3;
		int SIXPAGECOUNT = 23;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0) {
				tmp = df.format(info.getExamineAmount());
				saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			} else {
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				saPageContent[i] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_TEL);
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_FAX);
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ZIP);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 上海电气最高额抵押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSEFCPledgeZGE(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 11;
		int SECONDPAGECOUNT = 0;
		int THIRDPAGECOUNT = 1;
		int SIXPAGECOUNT = 3;
		int SEVENTHPAGECOUNT = 19;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			/*
			 * if (info.getExamineAmount() > 0) tmp =
			 * df.format(info.getExamineAmount()); saPageContent[index++] =
			 * (info.getExamineAmount() > 0)?
			 * ChineseCurrency.showChinese(tmp):"";
			 */
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = (info.getAssureAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

			// 合同第7页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_TEL);
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_FAX);
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ZIP);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SEVENTHPAGECOUNT; i++) {
				if (i == SEVENTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 7, sContent);
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气保证合同 by zntan
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSEFCAssure(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 8;
		int SECONDPAGECOUNT = 7;
		int FOURTHPAGECOUNT = 1;
		int FIFTHPAGECOUNT = 23;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = (info.getIntervalNum() > 0) ? String
					.valueOf(info.getIntervalNum()) : "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_TEL);
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_FAX);
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ZIP);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气抵押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSEFCPledge(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 11;
		int SECONDPAGECOUNT = 7;
		int THIRDPAGECOUNT = 1;
		int FIFTHPAGECOUNT = 3;
		int SIXPAGECOUNT = 19;

		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = (info.getIntervalNum() > 0) ? String
					.valueOf(info.getIntervalNum()) : "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页

			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat.formatString(cInfo.getBank1());
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			saPageContent[index++] = DataFormat.formatString(cInfo.getPhone());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_TEL);
			saPageContent[index++] = DataFormat.formatString(cInfo.getFax());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_FAX);
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getZipCode());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ZIP);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SIXPAGECOUNT; i++) {
				if (i == SIXPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气委托贷款协议
	 * 
	 * @author zntan
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public String addSEFCWTDKXY(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo assureInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 16;
		int SECONDPAGECOUNT = 12;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");

			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount,");

			sb
					.append(" ac.sName as AssureName,ac.sLegalPerson as AssureLegalPerson,");
			sb
					.append(" ac.sProvince as AssureProvince,ac.sCity as AssureCity,");
			sb
					.append(" ac.sAddress as AssureAddress,ac.sPhone as AssurePhone,");
			sb.append(" ac.sFax as AssureFax,ac.sZipCode as AssureZipCode,");
			sb.append(" ac.sBank1 as AssureBank1,ac.sAccount as AssureAccount");

			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID(+)");
			sb.append(" AND con.nConsignClientID = cc.id(+)");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
				consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
				consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号
				assureInfo.setName(rs.getString("AssureName")); // 客户名称
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // 法人代表
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // 地址
				assureInfo.setPhone(rs.getString("AssurePhone")); // 电活
				assureInfo.setFax(rs.getString("AssureFax")); // 传真
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // 邮政编码
				assureInfo.setBank1(rs.getString("AssureBank1")); // 开户银行
				assureInfo.setAccount(rs.getString("AssureAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // 手续费率类型
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (assureInfo.getName() == null) ? ""
					: assureInfo.getName();
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			saPageContent[index++] = (info.getLoanPurpose() == null) ? ""
					: info.getLoanPurpose();
			saPageContent[index++] = (info.getIntervalNum() > 0) ? String
					.valueOf(info.getIntervalNum()) : "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = "";
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();

			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (assureInfo.getName() == null) ? ""
					: assureInfo.getName();
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat.formatString(assureInfo
					.getLegalPerson());

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气抵押物清单
	 * 
	 * @author zntan
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public String addSEFCPledgeList(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[200];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int PAGECOUNT = 136;

		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			for (int j = 1; j < 132; j++) {
				saPageContent[j] = "&nbsp;";
				index++;
			}
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());

			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());

			for (i = 0; i < PAGECOUNT; i++) {
				if (i == PAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海电气担保协议
	 * 
	 * @author zntan
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public String addSEFCDBXY(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 19;
		int SECONDPAGECOUNT = 8;
		int THIRDPAGECOUNT = 0;
		int FOURTHPAGECOUNT = 1;
		int FIFTHPAGECOUNT = 6;
		int SIXTHPAGECOUNT = 12;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 合同录入日期
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// 担保类型一
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// 担保类型二
				info.setBeneficiary(rs.getString("Beneficiary"));// 担保受益人
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// 担保费率

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAddress());
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_ADDRESS);
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			saPageContent[index++] = DataFormat.formatString(info
					.getLoanPurpose());
			saPageContent[index++] = LOANConstant.AssureType1.getName(info
					.getAssureTypeID1())
					+ "--"
					+ LOANConstant.AssureType2.getName(info.getAssureTypeID2());// 担保种类
			saPageContent[index++] = DataFormat.formatString(info
					.getBeneficiary());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			String sTemp = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			saPageContent[index++] = DataFormat.formatDisabledAmount(info
					.getExamineAmount());
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			double assureAmount = 0;
			assureAmount = info.getLoanAmount()
					* (info.getAssureChargeRate() / 1200)
					* info.getIntervalNum();
			saPageContent[index++] = DataFormat
					.formatDisabledAmount(assureAmount);// 担保费
			saPageContent[index++] = "由乙方从甲方账户一次性扣收";
			saPageContent[index++] = "甲方在开具保函前主动支付";
			saPageContent[index++] = "";
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

			// 合同第5页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIFTHPAGECOUNT; i++) {
				if (i == FIFTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 5, sContent);

			// 合同第6页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);
			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < SIXTHPAGECOUNT; i++) {
				if (i == SIXTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 6, sContent);

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 航天科工抵押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addHTKGPledge(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 19;
		int SECONDPAGECOUNT = 2;
		int THIRDPAGECOUNT = 10;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as currencyID,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额

				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额
				info.setCurrencyID(rs.getLong("currencyID"));

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			/* 合同第1页 */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// 抵押人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			// 抵押权人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// 合同内容
			saPageContent[index++] = (info.getLoanStart() == null) ? ""
					: DataFormat.getYearString(info.getLoanStart());
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = "";

			// 为解决数值很大的时候取得的 getExamineAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAllAmount() > 0) {
				tmp = df.format(info.getAllAmount());
			}

			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp);

			saPageContent[index++] = info.getFormatInterestRate();

			tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp);

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第 3 页
			sContent = "";
			index = 0;
			//
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]);
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 航天科工人民币资金借款合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 */
	public String addHTKGLoan(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 25; // 31;
		int SECONDPAGECOUNT = 6;
		int THIRDPAGECOUNT = 2;
		int FOURTHPAGECOUNT = 10;
		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {

				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractCode(rs.getString("sContractCode")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setCurrencyID(rs.getLong("nCurrencyID"));
				info.setIsAssure(rs.getLong("nIsAssure"));
				info.setIsCredit(rs.getLong("nIsCredit"));
				info.setIsImpawn(rs.getLong("nIsImpawn"));
				info.setIsPledge(rs.getLong("nIsPledge"));
				info.setInputDate(rs.getTimestamp("dtInputDate"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			/* 合同第1页 */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// 借款人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			// 贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// 合同内容
			saPageContent[index++] = (info.getLoanTypeID() > 0) ? ""
					: LOANConstant.LoanType.getName(info.getLoanTypeID());
			saPageContent[index++] = (info.getLoanPurpose() == null) ? ""
					: info.getLoanPurpose();
			// saPageContent[index++] = (info.getCurrencyID()>0) ? "":
			// Constant.CurrencyType.getName(info.getCurrencyID());

			String sTemp = "";
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			saPageContent[index++] = info.getInterestRate() >= 0 ? info
					.getFormatInterestRate() : "";
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = info.getIntervalNum() >= 0 ? Long
					.toString(info.getIntervalNum()) : "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 第 2 页
			sContent = "";
			index = 0;
			sTemp = "";
			for (i = 0; i < SECONDPAGECOUNT; i++)
				saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 第 3 页
			sContent = "";
			index = 0;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 第 4 页
			sContent = "";
			index = 0;
			//
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 航天科工质押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addHTKGImpawn(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 20;
		int SECONDPAGECOUNT = 2;
		int THIRDPAGECOUNT = 10;
		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,cf.nCurrencyId as nCurrency,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额
				info.setCurrencyID(rs.getLong("nCurrency"));

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			/* 合同第1页 */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// 质押人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo
					.getZipCode();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();
			// 质押权人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// 合同内容
			saPageContent[index++] = (info.getLoanStart() == null) ? ""
					: DataFormat.getYearString(info.getLoanStart());
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = "";

			// 为解决数值很大的时候取得的 getExamineAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getAssureAmount() > 0)
				tmp = df.format(info.getAssureAmount());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ ChineseCurrency.showChinese(tmp);

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 填写文本缺省值 最高限额借款合同 Create Date: 2005-8-19
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM表中的ID
	 * @param assclientid
	 *            TODO //担保客户id
	 * @return String 返回合同文件名及路径
	 * @exception Exception
	 */
	public String addHTKGZGEBZ(long lID, long assclientid) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String strsql = "";
		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo clinfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 16;
		int SECONDPAGECOUNT = 8;
		// int THIRDPAGECOUNT = 0;
		int FOURTHPAGECOUNT = 10;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				System.out.println("cInfo======================="
						+ cInfo.getName());
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setContractCode(rs.getString("SCONTRACTCODE"));// 合同码
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setInputDate(rs.getTimestamp("dtInputDate"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			strsql = "select * from Client where id=" + assclientid;
			ps = conn.prepareStatement(strsql);
			rs = ps.executeQuery();
			if (rs.next()) {
				clinfo.setName(rs.getString("sName"));
				clinfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				clinfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				clinfo.setPhone(rs.getString("sPhone")); // 电活
				clinfo.setFax(rs.getString("sFax")); // 传真

			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// 保证人
			saPageContent[index++] = (clinfo.getName() == null) ? "" : clinfo
					.getName();
			saPageContent[index++] = (clinfo.getAddress() == null) ? ""
					: clinfo.getAddress();
			saPageContent[index++] = (clinfo.getLegalPerson() == null) ? ""
					: clinfo.getLegalPerson();
			saPageContent[index++] = (clinfo.getPhone() == null) ? "" : clinfo
					.getPhone();
			saPageContent[index++] = (clinfo.getFax() == null) ? "" : clinfo
					.getFax();
			// 债权人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// 债务人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			System.out.println("%%%%%%%%%%%%%%name=" + cInfo.getName());
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo
					.getFax();

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海浦发人民币资金借款合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 * @author wangli @
	 */
	public String addSHPFLoan(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 9; // 31;
		int SECONDPAGECOUNT = 1;
		int THIRDPAGECOUNT = 4;
		int FOURTHPAGECOUNT = 12;
		int FIVEPAGECOUNT = 1;
		int i;
		Iterator iter;
		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {

				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractCode(rs.getString("sContractCode")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setCurrencyID(rs.getLong("nCurrencyID"));
				info.setIsAssure(rs.getLong("nIsAssure"));
				info.setIsCredit(rs.getLong("nIsCredit"));
				info.setIsImpawn(rs.getLong("nIsImpawn"));
				info.setIsPledge(rs.getLong("nIsPledge"));
				info.setIsRecognizance(rs.getLong("ISRECOGNIZANCE"));
				info.setInputDate(rs.getTimestamp("dtInputDate"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			int index = 0;
			/* 合同第1页 */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// 借款人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// 贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// 合同内容
			saPageContent[index++] = (info.getLoanTypeID() < 0) ? ""
					: LOANConstant.LoanType.getName(info.getLoanTypeID());// 借款种类

			System.out.println("贷款类型："
					+ LOANConstant.LoanType.getName(info.getLoanTypeID()));
			saPageContent[index++] = (info.getLoanPurpose() == null) ? ""
					: info.getLoanPurpose();
			String sTemp = "";
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			saPageContent[index++] = info.getInterestRate() >= 0 ? info
					.getFormatInterestRate() : "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			System.out.println("++++++++++++sContent(1):" + sContent);
			sFileName = saveContent(sFileName, 1, sContent);
			// 第 2 页
			sContent = "";

			index = 0;
			sTemp = "";
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo
					.getBank1();

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			System.out.println("++++++++++++sContent(2):" + sContent);

			sFileName = saveContent(sFileName, 2, sContent);
			// 第 3 页
			sContent = "";
			index = 0;
			String assuretype = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			if (info.getIsAssure() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE)
						+ ",";
			}
			if (info.getIsCredit() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT)
						+ ",";
			}
			if (info.getIsImpawn() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN)
						+ ",";
			}
			if (info.getIsPledge() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.PLEDGE)
						+ ",";
			}
			if (info.getIsRecognizance() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.RECOGNIZANCE);
			}
			if (assuretype.endsWith(",") == true) {
				assuretype = assuretype.substring(0, assuretype.length() - 1);
			}
			if (assuretype.equals("")) {
				assuretype = "&nbsp;";
			}
			saPageContent[index++] = assuretype;
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			System.out.println("++++++++++++sContent(3):" + sContent);
			sFileName = saveContent(sFileName, 3, sContent);

			// 第 4 页
			sContent = "";
			index = 0;
			//

			saPageContent[index++] = "";// 合同份数
			saPageContent[index++] = "";
			// 借款人（甲方）
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// 贷款人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// 法定代表人（甲方）
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();

			// 法定代表人（乙方）
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// 或授权代理（甲方）
			saPageContent[index++] = "";
			// 或授权代理（乙方）
			saPageContent[index++] = "";

			sTemp = DataFormat.getDateString(info.getInputDate());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);

			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";

			}
			// 签约地点
			saPageContent[index++] = "";

			for (i = 0; i < FOURTHPAGECOUNT; i++) {
				if (i == FOURTHPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			System.out.println("++++++++++++sContent(4):" + sContent);
			sFileName = saveContent(sFileName, 4, sContent);
			// 第 5 页
			// *
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			// 初始化EJB
			Collection cPlan = null;
			try {
				RepayPlanDao repayplan = new RepayPlanDao();

				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				cPlan = repayplan.findPlanByContract(lID, 1000, 1, 1, lDesc);
			} catch (IException e) {
				e.printStackTrace();
				throw e;
			} catch (RemoteException e) {
				e.printStackTrace();
				throw e;
			}

			String sDate = "";
			String tDate = "";
			long lPAY = 0;
			long lRepay = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						lPAY++;
						sDate = DataFormat.formatDate(rp_info.tsPlanDate);
						System.out.println("sDate:" + sDate);
						System.out.println("sDate:" + sDate);
						System.out.println("sDate:" + sDate);
					}

					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						lRepay++;

					}
				}
			}

			sContent = "";
			index = 0;

			// *
			String styleTopLeftRight = "style='border-top:1 solid #000000;border-left:1 solid #000000;border-right:1 solid #000000'";
			String styleRightBottom = "style='border-right:1 solid #000000;border-bottom:1 solid #000000'";
			String styleBottom = "style='border-bottom:1 solid #000000'";
			sTemp += " <b>附件：</b>";
			sTemp += " <table border=0 cellspacing=0 cellpadding=0 width=70% "
					+ styleTopLeftRight + ">";
			sTemp += " <tr>";
			sTemp += " <td width=50% valign=top >";
			sTemp += " <table border =0 width=100% cellspacing=0 cellpadding=0>";
			sTemp += " <tr> ";
			sTemp += " <td colspan=4 align=middle " + styleRightBottom
					+ "> 借    款 </td>";
			sTemp += " </tr> ";
			sTemp += " <tr>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 年  </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 月 </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 日 </td>";
			sTemp += " <td width=55% align=middle " + styleRightBottom
					+ "> 金   额 </td>";
			sTemp += " </tr>";
			if (cPlan != null) {
				// sTemp += " <div style='page-break-before:always'> </div>";

				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();

					//modified by mzh_fu 2007/03/26 为解决当某一项为空时,页面表格显示不全的问题
					
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) {
						sTemp += " <tr>";
						
						String strTempValue="";
						
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(0, 4);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;						
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+ strTempValue + " </td>";
						
						strTempValue="";
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(5, 7);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+ strTempValue + " </td>";
						
						strTempValue="";
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(8, 10);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+ strTempValue + " </td>";
						
						strTempValue="";
						strTempValue=DataFormat.formatListAmount(rp_info.dAmount);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;
						sTemp += " <td width=55% align=right "
								+ styleRightBottom + "> "
								+ strTempValue
								+ " </td>";
						sTemp += " </tr>";
						dTemp += rp_info.dAmount;
					}
				}

			} else {
				sTemp += " <tr>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ ">&nbsp; </td>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ ">&nbsp; </td>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ ">&nbsp;  </td>";
				sTemp += " <td width=55% align=right " + styleRightBottom
						+ ">&nbsp; </td>";
				sTemp += " </tr>";
			}

			sTemp += "   </table>";
			sTemp += "  </td>";
			sTemp += "  <td width=50% valign=top>";
			sTemp += "  <table border=0 width=100% cellspacing=0 cellpadding=0>";
			sTemp += "  <tr>";
			sTemp += "  <td colspan=4 align=middle " + styleBottom
					+ "> 还    款 </td>";
			sTemp += "  </tr>";
			sTemp += "  <tr>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 年  </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 月 </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> 日 </td>";
			sTemp += "  <td width=55% align=middle " + styleBottom
					+ "> 金   额 </td>";
			sTemp += "  </tr>";
			dTemp = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					
					//modified by mzh_fu 2007/03/26 为解决当某一项为空时,页面表格显示不全的问题
					
					if (rp_info.nLoanOrRepay == LOANConstant.PlanType.REPAY) {
						sTemp += " <tr>";
						
						String strTempValue="";
						
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(0, 4);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;						
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+ strTempValue + " </td>";
						
						strTempValue="";						
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(5, 7);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;	
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+strTempValue + " </td>";
						
						strTempValue="";						
						strTempValue=DataFormat.formatDate(rp_info.tsPlanDate).substring(8, 10);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;	
						sTemp += " <td width=15% align=middle "
								+ styleRightBottom
								+ "> "
								+strTempValue  + " </td>";
						
						strTempValue="";						
						strTempValue=DataFormat.formatListAmount(rp_info.dAmount);
						strTempValue=(null==strTempValue || "".equals(strTempValue))?"&nbsp;":strTempValue;	
						sTemp += " <td width=55% align=right " + styleBottom
								+ "> "
								+ strTempValue
								+ " </td>";
						sTemp += " </tr>";
						dTemp += rp_info.dAmount;
					}
				}

			} else {
				sTemp += " <tr>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ "> &nbsp;</td>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ ">&nbsp; </td>";
				sTemp += " <td width=15% align=middle " + styleRightBottom
						+ "> &nbsp; </td>";
				sTemp += " <td width=55% align=right " + styleBottom
						+ ">&nbsp;</td>";
				sTemp += " </tr>";
			}
			sTemp += "  </table>";
			sTemp += " </td>";
			sTemp += "  </tr>";
			sTemp += "  </table>";

			sContent = sTemp + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			System.out.println("++++++++++++sContent(5):" + sContent);

			sFileName = saveContent(sFileName, 5, sContent);
			// */

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海浦发最高额保证合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 * @author wangli
	 * @time 2006-03-21
	 */
	public String addSHPFAssureZGE(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo clinfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 16;
		int SECONDPAGECOUNT = 9;
		int THIRDPAGECOUNT = 7;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());// 合同编号
			// 债权人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// //保证人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			//			
			saPageContent[index++] = (clinfo.getName1() == null) ? "" : clinfo
					.getName1();
			// 债务人
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			/*
			 * if (info.getExamineAmount() > 0) { tmp =
			 * df.format(info.getExamineAmount()); saPageContent[index++] =
			 * ChineseCurrency.showChinese(tmp); } else { saPageContent[index++] =
			 * ""; }
			 */
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = LOANEnv.CLIENT_NAME;// 债权人;
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;// 法人代表;

			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";// 签约地点;

			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 上海浦发保证合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 * @author wangli
	 * @time 2006-03-21
	 */
	public String addSHPFAssure(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 9;
		int SECONDPAGECOUNT = 9;
		int THIRDPAGECOUNT = 10;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID

				info.setExamineAmount(rs.getDouble("mAmount")); // 担保金额
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());// 合同编号
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);// 债权人
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += saPageContent[i] + PAGE_SEPERATOR;
				} else {
					sContent += saPageContent[i] + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);// 债权人
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);
			// 合同第3页
			sContent = "";
			index = 0;
			String sTemp = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海浦发最高额抵押合同
	 * 
	 * @param loanID
	 * @return
	 * @throws Exception
	 * @author wangli
	 * @time 2006-03-22
	 */

	public String addSHPFPledgeZGE(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 18;
		int SECONDPAGECOUNT = 1;
		int THIRDPAGECOUNT = 18;

		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName1());

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";

			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			saPageContent[index++] = "";
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			DecimalFormat df1 = new DecimalFormat("#.00");
			String tmp1 = "0.00";

			if (info.getAllAmount() > 0)
				tmp1 = df1.format(info.getAllAmount());
			saPageContent[index++] = (info.getAllAmount() > 0) ? ChineseCurrency
					.showChinese(tmp1)
					: "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// saPageContent[index++] =
			// DataFormat.formatString(info.getBorrowClientName());
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			/*
			 * sContent = ""; index = 0; saPageContent[index++] = "";
			 * saPageContent[index++] = ""; saPageContent[index++] = "";
			 * saPageContent[index++] = "";
			 * 
			 * for (i = 0; i < FOURPAGECOUNT; i++) { if (i == FOURPAGECOUNT - 1) {
			 * sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR; }
			 * else { sContent += formatString(saPageContent[i]) +
			 * CONTENT_SEPERATOR; } } sFileName = saveContent(sFileName, 4,
			 * sContent);
			 * 
			 */
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海浦发抵押合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public String addSHPFPledge(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 11;
		int SECONDPAGECOUNT = 1;
		int THIRDPAGECOUNT = 15;

		int i = 0;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.*,c.*,");
			sb
					.append(" cf.nIntervalNum,cf.dtInputDate,cf.sContractCode,cf.nTypeID as loanType,");
			sb
					.append(" cf.dtStartDate,cf.dtEndDate,cc.sName as sClientName,cf.mExamineAmount");
			sb
					.append(" FROM loan_loanContractAssure con,Client c,loan_ContractForm cf,Client cc");
			sb.append(" WHERE con.nClientID = c.ID");
			sb.append(" AND con.nContractID = cf.id");
			sb.append(" AND cf.nBorrowClientID = cc.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setBorrowClientName(rs.getString("sClientName")); // 贷款客户名称
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractCode(rs.getString("sContractCode")); // 贷款合同号
				info.setLoanTypeID(rs.getLong("loanType")); // 贷款类型
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setContractID(rs.getLong("nContractID")); // 合同ID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 合同金额

				info.setAssureAmount(rs.getDouble("mAmount")); // 担保金额
				info.setInterestRate(rs.getDouble("mPledgeRate")); // 抵押率
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // 抵押额

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;
			String sTemp = "";

			// 合同第1页
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());

			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());

			saPageContent[index++] = DataFormat.formatString(cInfo.getName1());
			saPageContent[index++] = DataFormat.formatString(info
					.getBorrowClientName());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			// saPageContent[index++] = (info.getExamineAmount() > 0)?
			// ChineseCurrency.showChinese(tmp):"";
			saPageContent[index++] = "";
			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			saPageContent[0] = " ";// 其它事项
			// sContent += formatString(saPageContent[0]) + CONTENT_SEPERATOR +
			// PAGE_SEPERATOR;
			sContent += formatString(saPageContent[0]) + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	 * 上海浦发委托贷款委托合同
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */

	public String addSHPFWTDKWT(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ClientInfo consignInfo = new ClientInfo();
		ClientInfo assureInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 19;
		int SECONDPAGECOUNT = 13;
		int THIRDPAGECOUNT = 4;

		int i;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");

			sb
					.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb
					.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb
					.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb
					.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount,");
			sb
					.append(" c.sName as BorrowClientName,con.MLOANAMOUNT as loanamount,");
			sb
					.append(" ac.sName as AssureName,ac.sLegalPerson as AssureLegalPerson,");
			sb
					.append(" ac.sProvince as AssureProvince,ac.sCity as AssureCity,");
			sb
					.append(" ac.sAddress as AssureAddress,ac.sPhone as AssurePhone,");
			sb.append(" ac.sFax as AssureFax,ac.sZipCode as AssureZipCode,");
			sb.append(" ac.sBank1 as AssureBank1,ac.sAccount as AssureAccount");

			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" Client ac,loan_loanContractAssure a");
			sb.append(" WHERE con.nBorrowClientID = c.ID(+)");
			sb.append(" AND con.nConsignClientID = cc.id(+)");
			sb.append(" AND con.ID = a.nContractID(+)");
			sb.append(" AND a.nClientID = ac.ID(+)");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				consignInfo.setName(rs.getString("ConsignName")); // 客户名称
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // 地址
				consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
				consignInfo.setFax(rs.getString("ConsignFax")); // 传真
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
				consignInfo.setBank1(rs.getString("ConsignBank1")); // 开户银行
				consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号
				assureInfo.setName1(rs.getString("AssureName")); // 客户名称
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // 法人代表
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // 地址
				assureInfo.setPhone(rs.getString("AssurePhone")); // 电活
				assureInfo.setFax(rs.getString("AssureFax")); // 传真
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // 邮政编码
				assureInfo.setBank1(rs.getString("AssureBank1")); // 开户银行
				assureInfo.setAccount(rs.getString("AssureAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate")); // 合同录入时间
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setContractCode(rs.getString("sContractCode"));// 合同编号
				info.setCurrencyID(rs.getLong("nCurrencyId"));// 币种
				info.setLoanTypeID(rs.getLong("nTypeId"));// 贷款种类
				info.setInterestRate(rs.getDouble("mInterestRate"));// 利率
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setChargeRate(rs.getDouble("mChargeRate")); // 手续费率
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // 手续费率类型
				info.setBorrowClientName(rs.getString("BorrowClientName"));
				info.setLoanAmount(rs.getDouble("loanamount"));

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			// 合同编号
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();
			// 委托人
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			// 受托人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// 借款人：
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();
			// 担保人：
			saPageContent[index++] = (assureInfo.getName1() == null) ? ""
					: assureInfo.getName1();
			// 为解决数值很大的时候取得的 mLoanAmount
			// 是一个科学记数法表示的数值，因而首先将它格式化成带两位小数的字符串，再转化成大写的数额

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			// 用途
			saPageContent[index++] = DataFormat.formatString(info
					.getLoanPurpose());
			// 贷款金额
			if (info.getLoanAmount() > 0)
				tmp = "0.00";
			tmp = df.format(info.getLoanAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);

			// 贷款期限
			saPageContent[index++] = DataFormat.formatListLong(info
					.getIntervalNum());
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			// 贷款月利率
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate(), 2);
			// 手续费率
			saPageContent[index++] = DataFormat.formatRate(
					info.getChargeRate(), 2);
			// 资金账户
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			// 逾期罚息
			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";

			// saPageContent[index++] =
			// DataFormat.formatRate(info.getInterestRate()/10.000000);
			saPageContent[index++] = "";

			// 甲方
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			// 甲方法定代表人
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			// 甲方授权代表人
			saPageContent[index++] = "";
			// 乙方
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// 乙方法定代表人
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// 乙方授权代表人
			saPageContent[index++] = "";

			// 丙方
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();
			// 丙方法定代表人
			saPageContent[index++] = "";
			// 丙方授权代表人
			saPageContent[index++] = "";
			// 丁方
			saPageContent[index++] = (assureInfo.getName1() == null) ? ""
					: assureInfo.getName1();

			// 丁方法定代表人
			saPageContent[index++] = "";

			// 丁方授权代表人
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页

			sContent = "";
			index = 0;

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			// 签约地点
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	/**
	 * 上海浦发开立国内保函协议
	 * 
	 * @author zntan
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */

	public String addSHPFKLGNBHXY(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 14;
		int SECONDPAGECOUNT = 8;
		int THIRDPAGECOUNT = 12;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 合同录入日期
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// 担保类型一
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// 担保类型二
				info.setBeneficiary(rs.getString("Beneficiary"));// 担保受益人
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// 担保费率
				info.setIsAssure(rs.getLong("nIsAssure"));

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			// 合同编号
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// 申请人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// 担保人全称
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);

			// 保函种类
			saPageContent[index++] = LOANConstant.AssureType1.getName(info
					.getAssureTypeID1())
					+ "--"
					+ LOANConstant.AssureType2.getName(info.getAssureTypeID2());// 担保种类
			// 保函金额
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(info.getExamineAmount());
			// 担保受益人
			saPageContent[index++] = DataFormat.formatString(info
					.getBeneficiary());

			// saPageContent[index++] =
			// Constant.CurrencyType.getName(info.getCurrencyID());
			// //保函受益人注册地址
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 保函有效期
			saPageContent[index++] = DataFormat.formatInt(
					info.getIntervalNum(), 1);
			// 担保费率
			saPageContent[index++] = DataFormat.formatRate(info
					.getAssureChargeRate());
			// 担保费
			double assureAmount = 0;
			// 担保金额
			assureAmount = (info.getLoanAmount() * info.getAssureChargeRate()) / 100;
			tmp = "0.00";
//			modify by 何小文 2007-06-27	
			if(assureAmount < 1.00){
				
				tmp =DataFormat.formatNumber(assureAmount,2);
			}
			else
			{	
			tmp = df.format(assureAmount);
			}
					
			//tmp =DataFormat.formatNumber(assureAmount,2);
			saPageContent[index++] = (assureAmount > 0) ? ChineseCurrency
					.showChinese(tmp) : "";
			// 保函费
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			String assuretype = "";
			if (info.getIsAssure() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE)
						+ ",";
			}
			if (info.getIsCredit() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT)
						+ ",";
			}
			if (info.getIsImpawn() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN)
						+ ",";
			}
			if (info.getIsPledge() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.PLEDGE)
						+ ",";
			}
			if (info.getIsRecognizance() > 0) {
				assuretype += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.RECOGNIZANCE);
			}
			if (assuretype.endsWith(",") == true) {
				assuretype = assuretype.substring(0, assuretype.length() - 1);
			}
			if (assuretype.equals("")) {
				assuretype = "&nbsp;";
			}
			System.out.println("assuretype:" + assuretype);
			saPageContent[index++] = assuretype;

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;

			assureAmount = info.getLoanAmount()
					* (info.getAssureChargeRate() / 1200)
					* info.getIntervalNum();
			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(assureAmount);//担保费
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			String sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页
			sContent = "";
			index = 0;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 申请人盖章
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// 担保人盖章
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// 法定代表人
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;

	}

	public String addSHPFRZZLHT(long lID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[100];
		ClientInfo cInfo = new ClientInfo();
		ContractInfo info = new ContractInfo();

		int FIRSTPAGECOUNT = 15;
		int SECONDPAGECOUNT = 0;
		int THIRDPAGECOUNT = 9;
		int FOURPAGECOUNT = 9;

		int i;

		Iterator iter;

		try {
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // 客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // 地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setFax(rs.getString("sFax")); // 传真
				cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
				cInfo.setBank1(rs.getString("sBank1")); // 开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号

				info.setLoanReason(rs.getString("sLoanReason")); // 借款原因
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // 借款用途
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // 借款批准金额
				info.setIntervalNum(rs.getLong("nIntervalNum")); // 借款期限
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // 贷款开始时间
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // 贷款结束时间
				info.setInputDate(rs.getTimestamp("dtInputDate"));// 合同录入日期
				info.setContractID(rs.getLong("contractID")); // 合同ID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // 执行利率
				info.setOther(rs.getString("sOther")); // 还款来源
				info.setContractID(rs.getLong("contractID")); // 合同ID
				info.setLoanTypeID(rs.getLong("nTypeID")); // 贷款类型
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// 担保类型一
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// 担保类型二
				info.setBeneficiary(rs.getString("Beneficiary"));// 担保受益人
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// 担保费率
				info.setMatureNominalAmount(rs
						.getDouble("MMATURENOMINALAMOUNT"));
				info.setChargeAmount(rs.getDouble("MCHARGEAMOUNT"));

			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

			int index = 0;

			// 合同第1页
			// 合同编号
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";

			// 出租人
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// 承租人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// 成本价
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getLoanAmount() > 0)
				tmp = df.format(info.getLoanAmount());
			saPageContent[index++] = (info.getLoanAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			// 出租总价
			saPageContent[index++] = (info.getLoanAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			// 利率
			saPageContent[index++] = info.getInterestRate() >= 0 ? info
					.getFormatInterestRate() : "";
			// 期限
			saPageContent[index++] = DataFormat.formatInt(
					info.getIntervalNum(), 1);
			String sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// 合同第3页

			sContent = "";
			index = 0;
			// sureAmount = info.getLoanAmount() *
			// (info.getAssureChargeRate()/1200) * info.getIntervalNum();
			// PageContent[index++] =
			// DataFormat.formatDisabledAmount(assureAmount);//担保费
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// 手续费
			if (info.getChargeAmount() > 0)
				tmp = df.format(info.getChargeAmount());
			saPageContent[index++] = (info.getChargeAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			for (i = 0; i < THIRDPAGECOUNT; i++) {
				if (i == THIRDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent);

			// 合同第4页
			sContent = "";
			index = 0;

			// 名义货价
			if (info.getMatureNominalAmount() > 0)
				tmp = df.format(info.getMatureNominalAmount());
			saPageContent[index++] = (info.getMatureNominalAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			saPageContent[index++] = "";
			// 甲方盖章
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// 乙方盖章
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// 法定代表人
			saPageContent[index++] = DataFormat.formatString(cInfo
					.getLegalPerson());
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_LEGALPERSON);

			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9) {
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			} else {
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			saPageContent[index++] = "";
			for (i = 0; i < FOURPAGECOUNT; i++) {
				if (i == FOURPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent);

		} catch (IException e) {
			log4j.error(e.toString());
			throw e;
		} catch (RemoteException e) {
			log4j.error(e.toString());
			throw e;
		} catch (Exception e) {
			log4j.error(e.toString());
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
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}
}

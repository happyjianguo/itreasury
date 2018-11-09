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
	 * �޸ĺ�ͬ�ļ���ֻ���ַ����ã� Create Date: 2006-03-16
	 * 
	 * @author wangli
	 * @param strName
	 *            �ļ���������·����
	 * @param SCONTRACTCODE
	 *            Ҫ���µĺ�ͬ���
	 * @return String �޸��ļ������֣�����Ϊ0�����ɹ�
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
					System.out.println("nIndex��" + nIndex);
					if (nIndex != -1) {
						// System.out.println("����ǰ�ĺ�ͬ�ı�ȫ���ݣ�"+record);
						SCONTRACTCODE = year + sTmp.substring(8, sTmp.length());
						// System.out.println("���º�ĺ�ͬ�ţ�"+SCONTRACTCODE);
						// sbTmp=record.substring(0,nIndex)+SCONTRACTCODE+record.substring(nIndex+sTmp.length(),record.length()-1);
						sbTmp = record.replaceAll(sTmp, SCONTRACTCODE);
						// System.out.println("���º�ĺ�ͬ�ı�ȫ���ݣ�"+sbTmp);
					} else {
						sbTmp = record;
						// System.out.println("��ͬ�ı�ȫ������û�к�ͬ���ʱ�ĺ�ͬ���ݣ�"+sbTmp);
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
			// �쳣�������׳�����ʹ�޸ĺ�ͬ�ı�����Ҫ��֤��ͬ����ɹ�
			// throw e;
		} finally {

		}
		return strName;
	}

	/**
	 * �����ļ� Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            �ļ���������·����������ʱ�ļ���Ϊ��
	 * @param lStepNo
	 *            ҳ��
	 * @param strContent
	 *            �ļ�����
	 * @return String �������޸��ļ������֣�����Ϊ0�����ɹ�
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
	 * �õ��ļ����� Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            �ļ���������·����
	 * @param lStepNo
	 *            ҳ��
	 * @return String �ļ�����
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
	 * �õ��ļ�ȫ������ Create Date: 2003-10-15
	 * 
	 * @param strName
	 *            �ļ���������·����
	 * @return String �ļ�����
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
	 * �ϲ��ļ�ģ����ļ����� Create Date: 2003-10-15
	 * 
	 * @param strTempletName
	 *            �ļ�ģ����
	 * @param strContentName
	 *            �ļ�������
	 * @return String �ϲ����ַ���
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
		int nIndex1; // PAGE_SEPERATOR???��??????
		int nIndex2; // CONTENT_SEPERATOR???��??????
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
			// ��??��????
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

				int nIndex = 0; // ","???��??????
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
					nIndex = 0; // ","???��??????
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

				int nIndex = 0; // ","???��??????
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
					nIndex = 0; // ","???��??????
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
	 * ��ϳ���ȷ�ĵ�ַ��ʽ Create Date: 2003-10-15
	 * 
	 * @param strProvince
	 *            ʡ
	 * @param strCity
	 *            ��
	 * @param strAddress
	 *            ��ַ
	 * @return String ������ȷ�ĵ�ַ��ʽ
	 * @exception Exception
	 */
	private String getAddress(String strProvince, String strCity,
			String sAddress) throws Exception {
		String strAddress = "";
		try {
			if (strProvince != null && strProvince.trim().length() > 0) {
				strAddress = strProvince + "ʡ";
			}
			if (strCity != null && strCity.trim().length() > 0) {
				strAddress = strAddress + strCity + "��";
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
	 * �õ���ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT���е�ID
	 * @param lPageNo
	 *            ҳ��
	 * @return String ��ͬ�ı�����
	 * @exception Exception
	 */
	public String getContractContent(long lID, long lPageNo) throws IException,
			Exception {
		ContractContentInfo info = new ContractContentInfo();
		String sContent = "";

		try {
			info = findByID(lID);
			String sFileName = info.getDocName();
			// modify by wjliu --2007/3/16 ��һ��ΪNULL������
			if (null != sFileName && !sFileName.equals(""))
			// if (!sFileName.equals(""))
			{
				sContent = getFileContent(sFileName, lPageNo);
			} else {
				// modified by mzh_fu 2007/03/21 
				throw new IException("û��ָ����ͬ�ļ���");
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
	 * ��д�ı�ȱʡֵ �����ҽ���ͬ����Ӫ ����/�г��ڣ� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
			sContent = "";
			index = 0;
			String sTemp = "0";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}

			if (info.getLoanTypeID() == LOANConstant.LoanType.ZY)// ���ô���
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

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
			// ��ʼ��EJB
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

			// ��ͬ��6ҳ
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

			// ��ͬ��7ҳ
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

			// ��ͬ��8ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// ��ͬ��9ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// ��ͬ��10ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 10, sContent);

			// ��ͬ��11ҳ
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

			// ��ͬ��12ҳ
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

			// ��ͬ��13ҳ
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			/*
			 * if (cPlan != null) { sTemp += " <b>������</b> <p>&nbsp;</p> \n";
			 * sTemp += " <table border=0 width=100%> \n"; sTemp += " <tr> ";
			 * sTemp += " <td width=50% valign=top> \n"; sTemp += " <table
			 * border=1 width=100%> \n"; sTemp += " <tr> "; sTemp += "
			 * <td colspan=2 align=middle> ��ʽ </td> \n"; sTemp += " </tr> ";
			 * sTemp += " <tr> "; sTemp += " <td width=50% align=middle > �������
			 * </td> \n"; sTemp += " <td width=50% align=middle > ����� </td>
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
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > �ϼƣ�
			 * </td> \n"; sTemp += " <td width=50%  align=right > " +
			 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
			 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n"; sTemp += "
			 * <td width=50% valign=top> \n"; sTemp += " <table border=1
			 * width=100%> \n"; sTemp += " <tr> \n"; sTemp += "
			 * <td colspan=2 align=middle> ���ʽ </td> \n"; sTemp += " </tr> \n";
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > ��������
			 * </td> \n"; sTemp += " <td width=50% align=middle > ������ </td>
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
			 * sTemp += " <tr> \n"; sTemp += " <td width=50% align=middle > �ϼƣ�
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
	 * ��д�ı�ȱʡֵ ��֤��ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ������ʾ��LOAN_LOANCONTRACTASSURE���еı�ʾ��
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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
			saPageContent[index++] = "�����";

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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ
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
	 * ��д�ı�ȱʡֵ ��Ѻ��ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ������ʾ��LOAN_LOANCONTRACTASSURE���е�ID���еı�ʾ��
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			saPageContent[index++] = "�����";
			sTemp = "";
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = "";
			saPageContent[index++] = "�����";
			sTemp = "";
			if (info.getAllAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getAllAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			saPageContent[index++] = info.getFormatInterestRate();
			saPageContent[index++] = "�����";
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// ��ͬ��6ҳ
			sContent = "";
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// ��ͬ��7ҳ
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

			// ��ͬ��8ҳ
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
	 * ��д�ı�ȱʡֵ ͳ��ͳ�������ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������

				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��

				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
			// ��ʼ��EJB
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

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// ��ͬ��7ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// ��ͬ��8ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// ��ͬ��9ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// ��ͬ��10ҳ
			sContent = "";
			saPageContent[0] = "";
			sContent = saPageContent[0] + CONTENT_SEPERATOR + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 10, sContent);

			// ��ͬ��11ҳ
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

			// ��ͬ��12ҳ
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

			// ��ͬ��13ҳ
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
	 * ��д�ı�ȱʡֵ ί�д����ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
				consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
				consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�
				assureInfo.setName(rs.getString("AssureName")); // �ͻ�����
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // ���˴���
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // ��ַ
				assureInfo.setPhone(rs.getString("AssurePhone")); // ���
				assureInfo.setFax(rs.getString("AssureFax")); // ����
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // ��������
				assureInfo.setBank1(rs.getString("AssureBank1")); // ��������
				assureInfo.setAccount(rs.getString("AssureAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // ������������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			// ��ʼ��EJB
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

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// ��ͬ��7ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// ��ͬ��8ҳ
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

			// ��ͬ��9ҳ
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

			// ��ͬ��10ҳ
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

				sTemp += " <b>������</b> <p>&nbsp;</p>";
				sTemp += " <p><h4 ALIGN=center><strong>�����ʽ</strong></h4><br></p>";
				sTemp += " <table width=100% border=0 cellspacing=0 class=table1 >";
				sTemp += " <tr>";
				sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> ��ʽ </td>";
				sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> ���ʽ </td>";
				sTemp += " </tr>";
				sTemp += " <tr align=center>";
				sTemp += " <td width=25%  class=td-rightbottom> ������� </td>";
				sTemp += " <td width=25%  class=td-rightbottom> ����� </td>";
				sTemp += " <td width=25%  class=td-rightbottom> �������� </td>";
				sTemp += " <td width=25%  class=td-rightbottom> ������ </td>";
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
				sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td>";
				sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
						+ DataFormat.formatListAmount(dTotalPay) + " </td>";
				sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td>";
				sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
						+ DataFormat.formatListAmount(dTotalRepay) + " </td>";
				sTemp += " </tr>";
				sTemp += " </table>";

				/*
				 * sTemp += " <table border=0 width=100% class=table1 > \n";
				 * sTemp += " <tr> "; sTemp += " <td width=50% valign=top> \n";
				 * sTemp += " <table border=0 width=100%> \n"; sTemp += " <tr> ";
				 * sTemp += " <td colspan=2 align=middle class=top> ��ʽ </td>
				 * \n"; sTemp += " </tr> "; sTemp += " <tr> "; sTemp += "
				 * <td width=50% align=middle > ������� </td> \n"; sTemp += "
				 * <td width=50% align=middle > ����� </td> \n"; sTemp += " </tr>
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
				 * <td width=50% align=middle class=td-right> �ϼƣ� </td> \n";
				 * sTemp += " <td width=50%  align=right class=td-right> " +
				 * DataFormat.formatListAmount(dTemp) + " </td> \n"; sTemp += "
				 * </tr> \n"; sTemp += " </table> \n"; sTemp += " </td> \n";
				 * sTemp += " <td width=50% valign=top> \n"; sTemp += " <table
				 * border=1 width=100%> \n"; sTemp += " <tr> \n"; sTemp += "
				 * <td colspan=2 align=middle class=top> ���ʽ </td> \n"; sTemp += "
				 * </tr> \n"; sTemp += " <tr> \n"; sTemp += "
				 * <td width=50% align=middle class=td-rightbottom > �������� </td>
				 * \n"; sTemp += "
				 * <td width=50% align=middle class=td-rightbottom > ������ </td>
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
			 * <td width=50% align=middle class=td-right> �ϼƣ� </td> \n"; sTemp += "
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
	 * ��д�ı�ȱʡֵ ����޶����ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������

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

			// ��ͬ��1ҳ
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

			saPageContent[index++] = "";// ��Ŀ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// ��ͬ��6ҳ
			sContent = "";
			// saPageContent[index++] = (info.getOther() == null) ? "" :
			// info.getOther();
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 6, sContent);

			// ��ͬ��7ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// ��ͬ��8ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// ��ͬ��9ҳ
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

			// ��ͬ��10ҳ
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

			// ��ͬ��11ҳ
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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				lBankAcceptPO = rs.getLong("nbankacceptpo"); // ����-���л�Ʊ����
				lBizAcceptPO = rs.getLong("nbizacceptpo"); // ����-��ҵ��Ʊ����
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

			/* TOCONFIG��TODELETE */
			/*
			 * ��Ʒ������������Ŀ,���е���Ϊ�ο�; ninh 2005-03-24
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

			/* TOCONFIG��END */

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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("��ʼд�����ֺ�ͬ�ı������л�Ʊ��");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ

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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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
							: rs.getString("sCode"); // ��Ʊ����
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // Ʊ����
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // ��Ʊ��
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // ��Ʊ����
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // �ж�����
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // ��������
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // ��������

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // ��������
					lDiscount = lDiscount + lHoliday + lIsLocal; // ��������

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // ������Ϣ
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // ʵ�����ֽ��
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

			log4j.info("д�����ֺ�ͬ�ı������л�Ʊ������");

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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("��ʼд�����ֺ�ͬ�ı�(��ҵ��Ʊ)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //���������˺�
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ¼��ʱ��
				lBizAcceptPO = rs.getLong("nbizacceptpo");// ����-��ҵ��Ʊ����
				DiscountDate = rs.getTimestamp("dtDiscountDate");// ������
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ
			String sTemp = "";

			// Log.print("-----test--3---");
			// ��ͬ���
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// ǩ������
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
			// ǩ��ص�
			saPageContent[index++] = "";

			// �׷�
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// �ҷ�
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

			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			// ��ҵ�жһ�ƱƱ�����ܶ��д��
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// ��������
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// ������
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

			// ������Ϣ�ܶ��д��TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// ʵ�����ֽ���ܶ��д��TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// ������;
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

			// ��ͬ��3ҳ
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// ��ͬ����
			saPageContent[index++] = "";// �׷����з���
			saPageContent[index++] = "";// �ҷ����з���
			saPageContent[index++] = "";// �ۺ����ź�ͬ���
			saPageContent[index++] = "";// ��
			saPageContent[index++] = "";// ��

			saPageContent[index++] = "";// ˫��Լ��

			// �׷�
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// �ҷ�
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
			// ��ͬ��6ҳ
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
							: rs.getString("sCode"); // ��Ʊ����
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // Ʊ����
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // ��Ʊ��
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // ��Ʊ����
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // �ж�����
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // ��������
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // ��������

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // ��������
					lDiscount = lDiscount + lHoliday + lIsLocal; // ��������

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // ������Ϣ
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // ʵ�����ֽ��
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

			log4j.info("д�����ֺ�ͬ�ı�(��ҵ��Ʊ)����");

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
	 * ��д�ı�ȱʡֵ ���ź�ͬ�ı�����Ӫ ����/�г��ڣ� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				cInfo.setCreditLevelID(rs.getLong("nCreditLevelID"));
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode")); // ��ͬ���
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setAdjustRate(rs.getDouble("mAdjustRate"));// ��������
				info.setBasicInterestRate(rInfo.getBasicRate());// ��׼����
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
				info.setAssureName(rs.getString("AssureName")); // ��֤������
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

			// ��ͬ��1ҳ
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

			// 5 ������ʽ
			// ����
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// ��Ѻ
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
	 * ��д�ı�ȱʡֵ �������ͳ�Ʊ� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            �ͻ���ʾ��Client���еı�ʾ��
	 * @return String ���غ�ͬ�ļ�����·��
	 * @exception Exception
	 */
	public String addFinanceTJB(long lID) throws Exception {

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[50];

		int PAGECOUNT = 35;

		try {
			// * �������ͳ�Ʊ�
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
	 * ��д�ı�ȱʡֵ ���չ��Э�� Create Date: 2003-11-12
	 * 
	 * @param lID
	 *            LOAN_EXTENDFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); // չ�ڽ��
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate")
							.before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate
							: rs.getTimestamp("ExtEndDate"); // չ������
				} else {
					cInfo.setName(rs.getString("sName")); // �ͻ�����
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
							.getString("sCity"), rs.getString("sAddress"))); // ��ַ
					cInfo.setPhone(rs.getString("sPhone")); // ���
					cInfo.setFax(rs.getString("sFax")); // ����
					cInfo.setZipCode(rs.getString("sZipCode")); // ��������
					cInfo.setBank1(rs.getString("sBank1")); // ��������
					cInfo.setAccount(rs.getString("sAccount")); // �˺�

					consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
					consignInfo.setLegalPerson(rs
							.getString("ConsignLegalPerson")); // ���˴���
					consignInfo.setAddress(getAddress(rs
							.getString("ConsignProvince"), rs
							.getString("ConsignCity"), rs
							.getString("ConsignAddress"))); // ��ַ
					consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
					consignInfo.setFax(rs.getString("ConsignFax")); // ����
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
					consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
					consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�

					info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
					info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
					info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
					info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
					info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
					info.setContractID(rs.getLong("contractID")); // ��ͬID
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
							null);
					info.setInterestRate(rInfo.getLateRate()); // ִ������
					info.setOther(rs.getString("sOther")); // ������Դ
					info.setContractCode(rs.getString("SCONTRACTCODE")); // ��ͬ���
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); // ¼��ʱ��

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); // չ�ڽ��
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); // չ������
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); // չ������

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); // ��֤����
					e_info.m_sExCode = rs.getString("SASSURECODE"); // ������ͬ���*/
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

			// ��ͬ��1ҳ
			// ������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			// �����
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

			// ������
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

			// ��ͬ��2ҳ
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
			saPageContent[index++] = "�����";

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
			// saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate()); // ��Ϣ
			saPageContent[index++] = "�����"; // չ�ڱ���
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
			// saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat
					.formatRate(e_info.m_dInterestRate); // ��Ϣ

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
			saPageContent[index++] = "����ҽ���ͬ";

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
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
	 * ��д�ı�ȱʡֵ ί�д���չ��Э�� Create Date: 2003-11-12
	 * 
	 * @param lID
	 *            LOAN_EXTENDFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); // չ�ڽ��
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate")
							.before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate
							: rs.getTimestamp("ExtEndDate"); // չ������
				} else {
					cInfo.setName(rs.getString("sName")); // �ͻ�����
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
							.getString("sCity"), rs.getString("sAddress"))); // ��ַ
					cInfo.setPhone(rs.getString("sPhone")); // ���
					cInfo.setFax(rs.getString("sFax")); // ����
					cInfo.setZipCode(rs.getString("sZipCode")); // ��������
					cInfo.setBank1(rs.getString("sBank1")); // ��������
					cInfo.setAccount(rs.getString("sAccount")); // �˺�

					WTconsignInfo.setName(rs.getString("WTConsignName")); // �ͻ�����
					WTconsignInfo.setLegalPerson(rs
							.getString("WTConsignLegalPerson")); // ���˴���
					WTconsignInfo.setAddress(getAddress(rs
							.getString("WTConsignProvince"), rs
							.getString("WTConsignCity"), rs
							.getString("WTConsignAddress"))); // ��ַ
					WTconsignInfo.setPhone(rs.getString("WTConsignPhone")); // ���
					WTconsignInfo.setFax(rs.getString("WTConsignFax")); // ����
					WTconsignInfo.setZipCode(rs.getString("WTConsignZipCode")); // ��������
					WTconsignInfo.setBank1(rs.getString("WTConsignBank1")); // ��������
					WTconsignInfo.setAccount(rs.getString("WTConsignAccount")); // �˺�

					consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
					consignInfo.setLegalPerson(rs
							.getString("ConsignLegalPerson")); // ���˴���
					consignInfo.setAddress(getAddress(rs
							.getString("ConsignProvince"), rs
							.getString("ConsignCity"), rs
							.getString("ConsignAddress"))); // ��ַ
					consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
					consignInfo.setFax(rs.getString("ConsignFax")); // ����
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
					consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
					consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�

					info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
					info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
					info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
					info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
					info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, rs
							.getLong("contractID"), null);
					info.setInterestRate(rInfo.getLateRate()); // ִ������
					info.setOther(rs.getString("sOther")); // ������Դ
					info.setContractID(rs.getLong("contractID")); // ��ͬID
					info.setContractCode(rs.getString("SCONTRACTCODE")); // ��ͬ���
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); // ¼��ʱ��

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); // չ�ڽ��
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); // չ������
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); // չ������

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); // ��֤����
					e_info.m_sExCode = rs.getString("SASSURECODE"); // ������ͬ���*/
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

			// ��ͬ��1ҳ
			// ί����
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

			// ������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			// �����
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;

			// ������
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
			saPageContent[index++] = "����ҽ���ͬ";
			saPageContent[index++] = "�����";

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
			// saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate()); // ��Ϣ
			saPageContent[index++] = "�����"; // չ�ڱ���
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

			// saPageContent[index++] = ""; //��Ϣ
			saPageContent[index++] = DataFormat
					.formatRate(e_info.m_dInterestRate); // ��Ϣ
			saPageContent[index++] = ""; // ��������Ϣ//��Ϊ ��������Ϣ
			saPageContent[index++] = ""; // ������
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

			// ��ͬ��3ҳ
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
			saPageContent[index++] = "����ҽ���ͬ";

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

			// ��ͬ��4ҳ
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
	 * ���ݺ�ͬ���ݱ�ʾ����ͬҳ�룬�޸�ָ��ҳ�ĺ�ͬ���� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ��ͬ���ݱ�ʾ(LOAN_CONTRACTCONTENT���е�ID)
	 * @param lPageNo
	 *            �޸ĺ�ͬ�ڼ�ҳ������
	 * @param strContent
	 *            ��ͬ���޸�ҳ������
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
				sDocName = rs.getString("sDocName"); // ��ͬ�ı��ļ���
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
	 * �õ���ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTCONTENT���е�ID
	 * @return ContractContentInfo ��ͬ�ı�����
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
				info.setParentID(rs.getLong("nParentID")); // ��ͬ�����ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); // ��ͬ����ID
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setDocName(rs.getString("sDocName")); // ��ͬ�ı��ļ��� ]

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
	 * �õ���������ID Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return ��������ID
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
	 * �õ���������ID Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            loanID
	 * @return ��������ID
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
	 * ��ȡָ����ͬ�ĺ�ͬģ�� Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            �ı�ID
	 * @param lContractTypeID
	 *            �ı�����ID
	 * @return String ���غ�ͬ�ĺ�ͬģ��
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
	 * ��ȡָ����ͬ���ı���JSP�ļ��� Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            �ı�ID
	 * @param lContractTypeID
	 *            �ı�����ID
	 * @return String �����ı���JSP�ļ���
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
	 * ��ȡָ����ͬ�ĺ�ͬģ����ı���JSP�ļ��� Create Date: 2003-10-15
	 * 
	 * @param lContentID
	 *            �ı�ID
	 * @param lContractTypeID
	 *            �ı�����ID
	 * @return String ���غ�ͬģ����ı���JSP�ļ���
	 * @exception Exception
	 */
	public String getDocumentName(long lContentID, long lContractTypeID)
			throws Exception {
		String strTemplate = "";
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		System.out.println("��ͬ����id:" + lContractTypeID);
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
				System.out.println("�����ж�lContractTypeID:" + lContractTypeID);
				lContractType = LOANConstant.Template.SHPF_RZTenancy;
				System.out.println("ȡģ���ļ���:" + lContractType);
			} else if (lContractTypeID == LOANConstant.ContractType.SHPF_ZGXE) {
				System.out.println("�����ж�lContractTypeID:" + lContractTypeID);
				lContractType = LOANConstant.Template.HN_ZGXE;
				System.out.println("ȡ����޶�ģ���ļ���:" + lContractType);
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
					lLoanType = rs1.getLong("nTypeID"); // ��������ID
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
					lBankAcceptPO = rs1.getLong("nbankacceptpo"); // ����-���л�Ʊ����
					lBizAcceptPO = rs1.getLong("nbizacceptpo"); // ����-��ҵ��Ʊ����
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
					lBankAcceptPO = rs1.getLong("nbankacceptpo"); // ����-���л�Ʊ����
					lBizAcceptPO = rs1.getLong("nbizacceptpo"); // ����-��ҵ��Ʊ����
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

			else if (lContractTypeID == LOANConstant.ContractType.LOAN) // ����ͬ
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
					lLoanType = rs1.getLong("nTypeID"); // ��������ID
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
	 * �����ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            ContractContentInfo��ͬ�ı�����(�������õ�������:ParentID,ContractID,ContractType,DocName)
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * ɾ����ͬ�ı����� Create Date: 2003-10-15
	 * 
	 * @param long
	 *            lContractID��ͬID
	 * @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
	 * ��д�ı�ȱʡֵ �������� Create Date: 2003-10-15
	 * 
	 * @param loanID
	 * @return String ���ص�����ļ�����·��
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

		// begin 2004-11-3 �޸� by jinchen �е�����Ŀ�¼�Ӧ�շ�
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); // ĸ��˾���
				// cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //��ҵ����
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
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

			// ��1ҳ
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

			System.out.println("**************��һҳȡֵ���*************");

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// ��2ҳ,
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

			// ��3ҳ
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

			// ����ҳ
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
	 * ��ͬ�ķſ�ͻ���ƻ� Create Date: 2003-12-20
	 * 
	 * @param Collection
	 *            cPlan ��ͬ
	 * @return String ���ص�����ļ�����·��
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

			sTemp += " <b>������</b> <p>&nbsp;</p>";
			sTemp += " <p><h4 ALIGN=center><strong>�����ʽ</strong></h4><br></p>";
			sTemp += " <table width=100% border=0 cellspacing=0 class=table1 >";
			sTemp += " <tr>";
			sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> ��ʽ </td>";
			sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> ���ʽ </td>";
			sTemp += " </tr>";
			sTemp += " <tr align=center>";
			sTemp += " <td width=25%  class=td-rightbottom> ������� </td>";
			sTemp += " <td width=25%  class=td-rightbottom> ����� </td>";
			sTemp += " <td width=25%  class=td-rightbottom> �������� </td>";
			sTemp += " <td width=25%  class=td-rightbottom> ������ </td>";
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
			sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td>";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;"
					+ DataFormat.formatListAmount(dTotalPay) + " </td>";
			sTemp += " <td width=25%  class=td-rightbottom> �ϼ� </td>";
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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
				lBankAcceptPO = rs.getLong("nbankacceptpo"); // ����-���л�Ʊ����
				lBizAcceptPO = rs.getLong("nbizacceptpo"); // ����-��ҵ��Ʊ����
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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("===��ʼд��ת���ֺ�ͬ�ı������л�Ʊ��===");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ

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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
            //modify by xwhe  �޸�����Ϊ���ݿ�ʱ��
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

			// ��ͬ��5ҳ
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
							: rs.getString("sCode"); // ��Ʊ����
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // Ʊ����
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // ��Ʊ��
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // ��Ʊ����
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // �ж�����
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // ��������
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // ��������

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // ��������
					lDiscount = lDiscount + lHoliday + lIsLocal; // ��������

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // ������Ϣ
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // ʵ�����ֽ��
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

			log4j.info("д��ת���ֺ�ͬ�ı������л�Ʊ������");

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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("��ʼд��ת���ֺ�ͬ�ı�(��ҵ��Ʊ)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //���������˺�
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ¼��ʱ��
				lBizAcceptPO = rs.getLong("nbizacceptpo");// ����-��ҵ��Ʊ����
				DiscountDate = rs.getTimestamp("dtDiscountDate");// ������
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				// Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ
			String sTemp = "";

			// Log.print("-----test--3---");
			// ��ͬ���
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// ǩ������
		//	sTemp = DataFormat.getDateString(info.getInputDate());
        //	modify by xwhe  �޸�����Ϊ���ݿ�ʱ��
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
			// ǩ��ص�
			saPageContent[index++] = "";

			// �׷�
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// �ҷ�
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

			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			// ��ҵ�жһ�ƱƱ�����ܶ��д��
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// Log.print("-----test--4--1-");
			// ��������
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// ������
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
			// ������Ϣ�ܶ��д��TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// Log.print("-----test--4--3-");
			// ʵ�����ֽ���ܶ��д��TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// Log.print("-----test--4--4-");
			// ������;
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

			// ��ͬ��3ҳ
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// ��ͬ����
			saPageContent[index++] = "";// �׷����з���
			saPageContent[index++] = "";// �ҷ����з���
			saPageContent[index++] = "";// �ۺ����ź�ͬ���
			saPageContent[index++] = "";// ��
			saPageContent[index++] = "";// ��

			saPageContent[index++] = "";// ˫��Լ��

			// �׷�
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// �ҷ�
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
			// ��ͬ��6ҳ
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
							: rs.getString("sCode"); // ��Ʊ����
					saPageContent[index++] = DataFormat.formatDisabledAmount(rs
							.getDouble("mAmount")); // Ʊ����
					saPageContent[index++] = rs.getString("sUserName") == null ? ""
							: rs.getString("sUserName"); // ��Ʊ��
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtCreate")); // ��Ʊ����
					saPageContent[index++] = rs.getString("sBank") == null ? ""
							: rs.getString("sBank"); // �ж�����
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtDisCountDate")); // ��������
					saPageContent[index++] = DataFormat.getDateString(rs
							.getTimestamp("dtEnd")); // ��������

					lHoliday = rs.getLong("nAddDays");
					if (rs.getLong("nIsLocal") == Constant.YesOrNo.NO) {
						lIsLocal = 3;
					}
					lDiscount = rs.getLong("discountDays"); // ��������
					lDiscount = lDiscount + lHoliday + lIsLocal; // ��������

					saPageContent[index++] = String.valueOf(lDiscount);
					saPageContent[index++] = DataFormat.formatRate(rs
							.getDouble("mDiscountRate")); // ������Ϣ
					dTemp = rs.getDouble("mAmount")
							- (rs.getDouble("mAmount")
									* (rs.getDouble("mDiscountRate") / 360)
									* 0.01 * lDiscount);
					saPageContent[index++] = DataFormat
							.formatDisabledAmount(dTemp); // ʵ�����ֽ��
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

			log4j.info("д��ת���ֺ�ͬ�ı�(��ҵ��Ʊ)����");

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
	 * Haier ��д�ı�ȱʡֵ ����ҽ���ͬ����Ӫ ����/�г��ڣ� Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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

				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractCode(rs.getString("sContractCode")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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

			// ��ͬ��1ҳ 15 item
			// ��ͬ���
			saPageContent[index++] = info.getContractCode() == null ? "" : info
					.getContractCode();
			// �׷� �����
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

			// ��ͬ��2ҳ 3 item
			sContent = "";
			index = 0;
			sTemp = "";
			// ���ԭ��
			saPageContent[index++] = info.getLoanReason() == null ? "" : info
					.getLoanReason();
			// ��������
			saPageContent[index++] = LOANConstant.LoanType.getName(info
					.getLoanTypeID());
			// ������;
			saPageContent[index++] = info.getLoanPurpose() == null ? "" : info
					.getLoanPurpose();
			/*
			 * if(info.getLoanTypeID() == LOANConstant.LoanType.ZYDQ ) {
			 * saPageContent[index++] = "���ڴ���"; } else if(info.getLoanTypeID() ==
			 * LOANConstant.LoanType.ZYZCQ ) { saPageContent[index++] = "�г��ڴ���"; }
			 * else { saPageContent[index++] = "��ʱ����"; }//
			 */
			// ������;
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

			// ��ͬ��3ҳ 7 item
			sContent = "";
			index = 0;
			sTemp = "0";

			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			// saPageContent[index++] = "�����";
			// saPageContent[index++] =
			// Constant.CurrencyType.getName(info.getCurrencyID());
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID())
					+ "��" + ChineseCurrency.showChinese(sTemp);

			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(info.getExamineAmount());

			sTemp = "";
			// ������� �� ��ʼ����
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
			// ������� �� ��������
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

			// ��ͬ��4ҳ 3 item
			sContent = "";
			index = 0;
			// saPageContent[index++] =
			// "�й��������й�����ͬ��ͬ���δ��������¸�"+DataFormat.formatRate(info.getInterestRate());
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

			// ��ͬ��5ҳ 1 item
			sContent = "";
			index = 0;

			// ������Դ
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

			// ��ͬ��6ҳ
			sContent = "";
			index = 0;
			saPageContent[0] = " ";// ������ʽ
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

			// ��ͬ��7ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 7, sContent);

			// ��ͬ��8ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 8, sContent);

			// ��ͬ��9ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 9, sContent);

			// ��ͬ��10ҳ 11 item
			sContent = "";
			sTemp = "";
			index = 0;

			saPageContent[index++] = "";// ��ͬ����
			// ����ˣ��׷���
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// �����ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_NAME;

			// ���������ˣ��׷���
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();

			// ���������ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// ί�д����ˣ��׷���
			saPageContent[index++] = "";
			// ί�д����ˣ��ҷ���
			saPageContent[index++] = "";

			// ǩ��ʱ��
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
			// ǩ���ص�
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

			// ��ͬ11ҳ ����
			// *
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			// ��ʼ��EJB
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
				sTemp += " <b>������</b> <p>&nbsp;</p>";
				sTemp += " <table border=0 width=100%>";
				sTemp += " <tr> ";
				sTemp += " <td width=50% valign=top>";
				sTemp += " <table border=1 width=100%>";
				sTemp += " <tr> ";
				sTemp += " <td colspan=2 align=middle> �ÿ�ƻ� </td>";
				sTemp += " </tr> ";
				sTemp += " <tr> ";
				sTemp += " <td width=50% align=middle > ������� </td>";
				sTemp += " <td width=50% align=middle > ����� </td>";
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
				sTemp += "  <td width=50% align=middle > �ϼƣ� </td>";
				sTemp += "  <td width=50%  align=right > "
						+ DataFormat.formatListAmount(dTemp) + " </td>";
				sTemp += "  </tr>";
				sTemp += "   </table>";
				sTemp += "  </td>";
				sTemp += "  <td width=50% valign=top>";
				sTemp += "  <table border=1 width=100%>";
				sTemp += "  <tr>";
				sTemp += "  <td colspan=2 align=middle> ����ƻ� </td>";
				sTemp += "  </tr>";
				sTemp += "  <tr>";
				sTemp += "  <td width=50% align=middle > �������� </td>";
				sTemp += "  <td width=50% align=middle > ������ </td>";
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
				sTemp += " <td width=50% align=middle > �ϼƣ� </td> ";
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
	 * Haier ��д�ı�ȱʡֵ ��֤��ͬ Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            ������ʾ��LOAN_LOANCONTRACTASSURE���еı�ʾ��
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
				// info.setExamineAmount(rs.getDouble("mExamineAmount")); //��ͬ���
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

			// ��ͬ��1ҳ
			// ��ͬ���
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

			// saPageContent[index++] = "�����";
			if (info.getCurrencyID() > 0) {
				saPageContent[index++] = Constant.CurrencyType.getName(info
						.getCurrencyID());
			} else {
				saPageContent[index++] = "�����";
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

			// ��ͬ��2ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// ��ͬ��6ҳ
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			saPageContent[index++] = "";// ��ͬ����

			// ��֤�ˣ��׷���
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// ծȨ�ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// ���������ˣ��׷���
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			// ���������ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = "";// ί�д����ˣ��׷���
			saPageContent[index++] = "";// ί�д����ˣ��ҷ���

			// ǩ��ʱ��
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = "";// ǩ���ص�

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
	 * Haier ��д�ı�ȱʡֵ ��Ѻ��ͬ Create Date: 2004-10-29
	 * 
	 * @param lID
	 *            ������ʾ��LOAN_LOANCONTRACTASSURE���еı�ʾ��
	 * @return String ���غ�ͬ�ļ�����·��
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
				// ����
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				// ����ͻ�
				bInfo.setName(rs.getString("sName2")); // �ͻ�����
				bInfo.setLegalPerson(rs.getString("sLegalPerson2")); // ���˴���
				bInfo.setAddress(getAddress(rs.getString("sProvince2"), rs
						.getString("sCity2"), rs.getString("sAddress2"))); // ��ַ
				bInfo.setPhone(rs.getString("sPhone2")); // ���
				bInfo.setFax(rs.getString("sFax2")); // ����
				bInfo.setZipCode(rs.getString("sZipCode2")); // ��������
				bInfo.setBank1(rs.getString("sBank12")); // ��������
				bInfo.setAccount(rs.getString("sAccount2")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ

			// ����ˣ��׷���
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
			// �����ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			// saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// ��֤�ˣ�������
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

			// ��ͬ��2ҳ
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getAssureAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// saPageContent[index++] = "";//���
			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {// ���յ�Ѻ��ͱ��δ���ĵ������������� ��Ԫ����֮��
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

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			sTemp = "0";
			if (info.getAssureAmount() > 0) {// ��������Ϊ������Ӧ���˿�
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

			// ��ͬ��6ҳ
			sContent = "";
			sTemp = DataFormat.getDateString();
			index = 0;
			saPageContent[index++] = "";// ��ͬ����

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

			saPageContent[index++] = "";// �ص�

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
	 * Haier ��д�ı�ȱʡֵ �������� Create Date: 2004-10-29
	 * 
	 * @param loanID
	 * @return String ���ص�����ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setOfficeName(rs.getString("OfficeName")); // ����˾����
				cInfo.setCode(rs.getString("SCODE"));// �ͻ����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); // ĸ��˾���
				cInfo.setParentCorpName(rs.getString("ParentCorpName")); // �ϼ���λ����
				// cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //��ҵ����
				// cInfo.setCorpNatureName( rs.getString("EnterpriceTypeName"));
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				// cInfo.setLoanClientTypeID(rs.getLong("NLOANCLIENTTYPEID"));//�ͻ�����
				// ��Ӫ����
				// cInfo.setLoanClientType(rs.getString("LoanClientType"));//��������
				cInfo.setCaptial(rs.getString("SCAPITAL")); // ע���ʱ�
				cInfo.setDealScope(rs.getString("SDEALSCOPE"));// ��Ӫ��Χ
				cInfo.setFinanceManager(rs.getString("financialcontrolor"));// ��������
			}

			cleanup(rs);
			cleanup(ps);

			// ��1ҳ
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

			// ��2ҳ,
			int index = 0;
			sContent = "";

			// ������걨���
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

			// ����˻������
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo
					.getAddress();
			saPageContent[index++] = cInfo.getCaptial() == null ? "" : cInfo
					.getCaptial();// ע���ʱ�
			// saPageContent[index++]
			// =cInfo.getCorpNatureName()==null?"":cInfo.getCorpNatureName();//��ҵ����
			saPageContent[index++] = cInfo.getDealScope() == null ? "" : cInfo
					.getDealScope();// ��Ӫҵ��
			saPageContent[index++] = cInfo.getFinanceManager() == null ? ""
					: cInfo.getFinanceManager();// ��������
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo
					.getPhone();
			saPageContent[index++] = "";// �����������Ա���ʡ��������ۡ��г������������

			for (i = 0; i < SECONDPAGECOUNT; i++) {
				if (i == SECONDPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			// ��3ҳ
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

			// ��4ҳ
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

			// ��5ҳ
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
	 * �е��ӽ���ͬ
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

				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractCode(rs.getString("sContractCode")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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

			// ��ͬ��1ҳ
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

			// �ڶ�ҳ
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = info.getInterestRate() > 0 ? "" : info
					.getFormatInterestRate();

			saPageContent[index++] = "��";
			saPageContent[index++] = "��";

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

			// ����ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// �� 4 ҳ
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

			// �� 5 ҳ
			sContent = "";
			index = 0;
			sTemp = "";

			if (info.getIsCredit() == Constant.YesOrNo.YES)
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (info.getIsAssure() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE);
			}
			if (info.getIsImpawn() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN);
			}
			if (info.getIsPledge() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
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

			// �� 6 ҳ
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
	 * �е��ӱ�֤��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setCurrencyID(rs.getLong("currencyID"));

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ

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
	 * �е��ӹ�Ȩ��Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrency"));// ����

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ

			// ����ˣ��׷���
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

			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� getExamineAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// ���յ�Ѻ��ͱ��δ���ĵ������������� ��Ԫ����֮�ͣ�����
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

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// ���յ�Ѻ��ͱ��δ���ĵ������������� ��Ԫ����֮�ͣ�����
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

			// ��ͬ��5ҳ
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
	 * �е��ӹɷ���Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrency"));

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ

			// ����ˣ��׷���
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
			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� getExamineAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// ���յ�Ѻ��ͱ��δ���ĵ������������� ��Ԫ����֮�ͣ�����
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

			// ��ͬ��5ҳ
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
	 * �е��Ӵ浥��Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrency"));// ����

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ

			// ����ˣ��׷���
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

			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� getExamineAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";// ���յ�Ѻ��ͱ��δ���ĵ������������� ��Ԫ����֮�ͣ�����
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

			// ��ͬ��5ҳ
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
	 * �е��ӵ�Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������

				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// ��ͬ�� 6 ҳ
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
	 * �е���ί�н���ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
				consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
				consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�
				assureInfo.setName(rs.getString("AssureName")); // �ͻ�����
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // ���˴���
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // ��ַ
				assureInfo.setPhone(rs.getString("AssurePhone")); // ���
				assureInfo.setFax(rs.getString("AssureFax")); // ����
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // ��������
				assureInfo.setBank1(rs.getString("AssureBank1")); // ��������
				assureInfo.setAccount(rs.getString("AssureAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrencyId"));// ����
				info.setLoanTypeID(rs.getLong("nTypeId"));// ��������
				info.setInterestRate(rs.getDouble("mInterestRate"));// ����
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // ������������
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

			// ��ͬ��1ҳ
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
			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� mLoanAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			// ��ʼ��EJB

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
	 * ͨ����ݺ�doc���ͻ��contractcontentinfos
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
				// ����м���
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
				info.setParentID(rs.getLong("nParentID")); // ��ͬ�����ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); // ��ͬ����ID
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setDocName(rs.getString("sDocName")); // ��ͬ�ı��ļ���
				info.setCode(rs.getString("scode"));// �ı�code
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
	 * ͨ����ݺ�doc���ͻ��contractcontentinfos
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
				// ����м���
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
	 * �е���ί�д���ί�к�ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
				consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
				consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�
				assureInfo.setName(rs.getString("AssureName")); // �ͻ�����
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // ���˴���
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // ��ַ
				assureInfo.setPhone(rs.getString("AssurePhone")); // ���
				assureInfo.setFax(rs.getString("AssureFax")); // ����
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // ��������
				assureInfo.setBank1(rs.getString("AssureBank1")); // ��������
				assureInfo.setAccount(rs.getString("AssureAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrencyId"));// ����
				info.setLoanTypeID(rs.getLong("nTypeId"));// ��������
				info.setInterestRate(rs.getDouble("mInterestRate"));// ����
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // ������������
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

			// ��ͬ��1ҳ
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
			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� mLoanAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			saPageContent[index++] = "";
			saPageContent[index++] = Constant.CurrencyType.getName(info
					.getCurrencyID());
			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� mLoanAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "��";
			saPageContent[index++] = "��";
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

			// ��ͬ��3ҳ

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
	 * �е������ֺ�ͬ
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
		int FIFTHPAGECOUNT = -1;// ����Ʊ������ * 18

		int i;

		long lBizAcceptPO = 0;
		Timestamp DiscountDate = null;

		try {

			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*");
			sb.append(" FROM loan_contractForm con,Client c");
			sb.append(" WHERE con.nBorrowClientID = c.ID");
			sb.append(" AND con.ID = ?");
			log4j.info("��ʼд�����ֺ�ͬ�ı�(��ҵ��Ʊ)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //���������˺�
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setContractID(rs.getLong("contractID"));
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setLoanAmount(rs.getDouble("mLoanAmount"));// Ʊ����
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ¼��ʱ��
				lBizAcceptPO = rs.getLong("nbizacceptpo");// ����-��ҵ��Ʊ����
				DiscountDate = rs.getTimestamp("dtDiscountDate");// ������
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
				info.setDiscountClientName(rs.getString("DiscountClientName"));// ��Ʊ������
				info.setIsPurchaserInterest(rs.getLong("isPurchaserInterest"));// �Ƿ��򷽸�Ϣ
			}

			int index = 0;

			String sTemp = "";
			// ��ͬ��1ҳ
			// /*
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();

			// ����򷽸�Ϣ��������������ӦΪ��Ʊ��
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
			// ��ͬ��2ҳ
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

			// ��ͬ�� 3 ҳ
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

			// ��ͬ�� 4 ҳ
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

			// ��ͬ�� 5 ҳ

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

			saPageContent[index++] = "�ں�ͬδ������֮ǰ���޷���ӡ�����嵥��";
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

			log4j.info("д�����ֺ�ͬ�ı�(��ҵ��Ʊ)����");

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
	 * �е���ȡ�÷Ż���ƻ�
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
			sTemp += " <td colspan=4 align=center  width=50% class=td-rightbottom > ��  ��</td>";
			sTemp += " <td colspan=4 align=center  class=td-bottom > ��  ��</td>";
			sTemp += " </tr>";
			sTemp += " <tr align=center>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-rightbottom width=20%>���</td>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-rightbottom width=10%>��</td>";
			sTemp += " <td class=td-bottom width=20%>���</td>";
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
	 * �е������ֺ�ͬ��ȡ������Ʊ���б�ķ��� �ں�ͬ�������Ժ���б����������ϳ�һ�� table ����Ϊ��ͬ�ı������һ����λ
	 * 
	 * @param lContractID
	 * @return
	 * @throws Exception
	 */
	public String saveCECBillTab(long lContractID) throws Exception {
		System.out.println("====��ʼ�滻��ͬ�ı����һҳ===");
		String strFileName = "";
		Collection contractContent = null;
		Iterator it = null;
		Iterator billIt = null;
		String sContent = "";
		String arrContent[] = new String[arrLen];
		int index = 0;
		Collection cBill = null;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

		// �������ֺ�ͬ�ı���ȡ�����һҳ������ҳ��������
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

		// ����ú�ͬ�µ�Ʊ������
		DiscountDao discountDao = new DiscountDao();
		cBill = discountDao.findDiscountBillByContractID(lContractID, 1000, 1,
				1, lDesc);

		// ȡ�ø�ҳ�����ݣ���������
		if (sContent.length() > 0) {
			int nIndex; // ";;"������λ��
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

		// ȡ����������д��һ�� html table
		sContent = "";
		index++;// ��ΪĬ�ϵ�һ����λ���˷�Ʊ����Ϣ��ֵ
		if (cBill != null)
			billIt = cBill.iterator();
		if (billIt != null) {
			System.out.println("====��ʼдƱ����Ϣ====");
			for (int j = 0; billIt.hasNext(); j++) {
				billIt.next();
				sContent += "<table class=table1 align=center>";
				sContent += "<tr>";
				sContent += "<td class=td-rightbottom>��Ʊ����</td>";
				sContent += "<td class=td-rightbottom>��Ʊ����</td>";
				sContent += "<td class=td-rightbottom >��Ʊ����</td>";
				sContent += "<td class=td-rightbottom >��������</td>";
				sContent += "<td class=td-rightbottom >���׺�ͬ�ֺ�</td>";
				sContent += "<td class=td-bottom >��ֵ˰��Ʊ��</td>";
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
				sContent += "<td class=td-rightbottom >��Ʊ��ȫ��</td>";
				sContent += "<td class=td-rightbottom >�˺�</td>";
				sContent += "<td class=td-rightbottom >�����м��к�</td>";
				sContent += "<td class=td-rightbottom >������ȫ��</td>";
				sContent += "<td class=td-rightbottom >�˺�</td>";
				sContent += "<td class=td-bottom >�����м��к�</td>";
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
				sContent += "<td class=td-rightbottom >�տ���ȫ��</td>";
				sContent += "<td class=td-rightbottom >�˺�</td>";
				sContent += "<td class=td-rightbottom >�����м��к�</td>";
				sContent += "<td class=td-rightbottom >�ж���ȫ��</td>";
				sContent += "<td class=td-rightbottom >�����м��к�</td>";
				sContent += "<td class=td-bottom >��Ʊ����д��</td>";
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

		// ���� table ����ͬ�ı����һ��
		strFileName = saveContent(strFileName, 5, sContent);
		System.out.println("ContractContent file name:" + strFileName);
		return strFileName;

	}

	/**
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("��ʼд�����ֺ�ͬ�ı������л�Ʊ��");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ

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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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

			// sbBill.append(" <b>������</b> <p>&nbsp;</p>";
			sbBill
					.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
			sbBill.append(" <tr align=\"center\"> ");
			sbBill
					.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">���</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ��</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">�ж�����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">������Ϣ%</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">ʵ�����ֽ��</font></td> ");
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
				lDiscount = rs.getLong("discountDays"); // ��������
				lDiscount = lDiscount + lHoliday + lIsLocal; // ��������
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
							.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">���</font></td>");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ��</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">�ж�����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">������Ϣ%</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">ʵ�����ֽ��</font></td> ");
					sbBill.append(" </tr>");
				}
				sbBill.append(" <tr align=\"center\"> ");
				sbBill
						.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">"
								+ index + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat
										.formatString(rs.getString("sCode"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatDisabledAmount(rs
										.getDouble("mAmount"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatString(rs
										.getString("sUserName"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtCreate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat
										.formatString(rs.getString("sbank"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtDisCountDate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtEnd"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ String.valueOf(lDiscount) + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatRate(rs
										.getDouble("mDiscountRate"))
								+ "%</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
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

			log4j.info("д�����ֺ�ͬ�ı������л�Ʊ������");

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
	 * ��д�ı�ȱʡֵ ���ֺ�ͬ Create Date: 2003-10-15
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @return String ���غ�ͬ�ļ�����·��
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
			log4j.info("��ʼд�����ֺ�ͬ�ı�(��ҵ��Ʊ)");
			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			log4j.info("��ͬID=" + lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			while (rs.next()) {
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				// cInfo.setBankAccount1(rs.getString("sExtendAccount1"));
				// //���������˺�
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ¼��ʱ��
				lBizAcceptPO = rs.getLong("nbizacceptpo");// ����-��ҵ��Ʊ����
				DiscountDate = rs.getTimestamp("dtDiscountDate");// ������
				RateInfo rInfo = new RateInfo();
				// Log.print("-----test--1---");
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				Log.print("-----test--2---");
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setDiscountRate(rs.getDouble("mDiscountRate")); // ��������
				info.setCheckAmount(rs.getDouble("mCheckAmount")); // �������ֺ˶����
			}

			int index = 0;

			// ��ͬ��1ҳ
			String sTemp = "";

			// Log.print("-----test--3---");
			// ��ͬ���
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// ǩ������
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
			// ǩ��ص�
			saPageContent[index++] = "";

			// �׷�
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// �ҷ�
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

			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			for (i = 0; i < FIRSTPAGECOUNT; i++) {
				if (i == FIRSTPAGECOUNT - 1) {
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				} else {
					sContent += formatString(saPageContent[i])
							+ CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";

			// Log.print("-----test--4---");
			saPageContent[index++] = "" + lBizAcceptPO;// ������ҵ��Ʊ����

			// ��ҵ�жһ�ƱƱ�����ܶ��д��
			if (info.getExamineAmount() > 0) {
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			// ��������
			saPageContent[index++] = info.getFormatDiscountRate();

			sTemp = "";
			// ������
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

			// ������Ϣ�ܶ��д��TODO
			if (info.getDiscountInterest() > 0) {
				sTemp = DataFormat.formatAmount(info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// ʵ�����ֽ���ܶ��д��TODO
			if (info.getCheckAmount() > 0)// -info.getDiscountInterest()) > 0)
			{
				sTemp = DataFormat.formatAmount(info.getCheckAmount());// -info.getDiscountInterest());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			// ������;
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

			// ��ͬ��3ҳ
			// Log.print("-----test--5---");
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			index = 0;

			// Log.print("-----test--6---");
			saPageContent[index++] = "";// ��ͬ����
			saPageContent[index++] = "";// �׷����з���
			saPageContent[index++] = "";// �ҷ����з���
			// saPageContent[index++] = "";//�ۺ����ź�ͬ���
			// saPageContent[index++] = "";//��
			// saPageContent[index++] = "";//��

			saPageContent[index++] = "";// ˫��Լ��

			// �׷�
			sTemp = DataFormat.getDateString();
			// saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			// �ҷ�
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
			// ��ͬ��6ҳ
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

			// sbBill.append(" <b>������</b> <p>&nbsp;</p>";
			sbBill
					.append(" <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" class=\"table1\">");
			sbBill.append(" <tr align=\"center\"> ");
			sbBill
					.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">���</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ��</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">�ж�����</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">������Ϣ%</font></td> ");
			sbBill
					.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">ʵ�����ֽ��</font></td> ");
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
				lDiscount = rs.getLong("discountDays"); // ��������
				lDiscount = lDiscount + lHoliday + lIsLocal; // ��������
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
							.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">���</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ��</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��Ʊ����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">�ж�����</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">��������</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">������Ϣ%</font></td> ");
					sbBill
							.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">ʵ�����ֽ��</font></td> ");
					sbBill.append(" </tr>");
				}
				sbBill.append(" <tr align=\"center\"> ");
				sbBill
						.append(" 	<td width=\"5%\" class=\"td-rightbottom\"><font face=\"����\">"
								+ index + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat
										.formatString(rs.getString("sCode"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatDisabledAmount(rs
										.getDouble("mAmount"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatString(rs
										.getString("sUserName"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtCreate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat
										.formatString(rs.getString("sbank"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtDisCountDate"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.getDateString(rs
										.getTimestamp("dtEnd"))
								+ "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ String.valueOf(lDiscount) + "</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
								+ DataFormat.formatRate(rs
										.getDouble("mDiscountRate"))
								+ "%</font></td> ");
				sbBill
						.append(" 	<td class=\"td-rightbottom\"><font face=\"����\">"
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

			log4j.info("д�����ֺ�ͬ�ı�(��ҵ��Ʊ)����");

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
	 * �Ϻ���������ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ��ͬ¼������
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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

			// ��ͬ��1ҳ
			// if (info.getLoanTypeID() == LOANConstant.LoanType.ZYZCQ)
			// {
			// saPageContent[index++] = "�г��ڽ���ͬ";
			// }
			// else if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEDQ)
			// {
			// saPageContent[index++] = "����޶���ڽ���ͬ";
			// }
			// else if (info.getLoanTypeID() == LOANConstant.LoanType.ZGXEZCQ)
			// {
			// saPageContent[index++] = "����޶��г��ڽ���ͬ";
			// }
			// else
			// {
			// saPageContent[index++] = "���ڽ���ͬ";
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			sTemp = "";

			if (info.getIsCredit() == Constant.YesOrNo.YES)
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (info.getIsAssure() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.ASSURE);
			}
			if (info.getIsImpawn() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
				sTemp += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.IMPAWN);
			}
			if (info.getIsPledge() == Constant.YesOrNo.YES) {
				if (sTemp.length() > 0)
					sTemp += "��";
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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
	 * �Ϻ�������߶֤��ͬ by zntan
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ
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
	 * �Ϻ�������߶��Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 5, sContent);

			// ��ͬ��6ҳ
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

			// ��ͬ��7ҳ
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
	 * �Ϻ�������֤��ͬ by zntan
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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
	 * �Ϻ�������Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 4, sContent);

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ

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
	 * �Ϻ�����ί�д���Э��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
				consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
				consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�
				assureInfo.setName(rs.getString("AssureName")); // �ͻ�����
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // ���˴���
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // ��ַ
				assureInfo.setPhone(rs.getString("AssurePhone")); // ���
				assureInfo.setFax(rs.getString("AssureFax")); // ����
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // ��������
				assureInfo.setBank1(rs.getString("AssureBank1")); // ��������
				assureInfo.setAccount(rs.getString("AssureAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // ������������
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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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
	 * �Ϻ�������Ѻ���嵥
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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
	 * �Ϻ���������Э��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ��ͬ¼������
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// ��������һ
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// �������Ͷ�
				info.setBeneficiary(rs.getString("Beneficiary"));// ����������
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// ��������

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

			// ��ͬ��1ҳ
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
					+ LOANConstant.AssureType2.getName(info.getAssureTypeID2());// ��������
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			double assureAmount = 0;
			assureAmount = info.getLoanAmount()
					* (info.getAssureChargeRate() / 1200)
					* info.getIntervalNum();
			saPageContent[index++] = DataFormat
					.formatDisabledAmount(assureAmount);// ������
			saPageContent[index++] = "���ҷ��Ӽ׷��˻�һ���Կ���";
			saPageContent[index++] = "�׷��ڿ��߱���ǰ����֧��";
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
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

			// ��ͬ��5ҳ
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

			// ��ͬ��6ҳ
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
	 * ����ƹ���Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������

				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��
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

			/* ��ͬ��1ҳ */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// ��Ѻ��
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
			// ��ѺȨ��
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// ��ͬ����
			saPageContent[index++] = (info.getLoanStart() == null) ? ""
					: DataFormat.getYearString(info.getLoanStart());
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = "";

			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� getExamineAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
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

			// ��ͬ�� 3 ҳ
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
	 * ����ƹ�������ʽ����ͬ
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

				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractCode(rs.getString("sContractCode")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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

			/* ��ͬ��1ҳ */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// �����
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
			// ������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// ��ͬ����
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

			// �� 2 ҳ
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

			// �� 3 ҳ
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

			// �� 4 ҳ
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
	 * ����ƹ���Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrency"));

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			/* ��ͬ��1ҳ */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// ��Ѻ��
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
			// ��ѺȨ��
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;

			// ��ͬ����
			saPageContent[index++] = (info.getLoanStart() == null) ? ""
					: DataFormat.getYearString(info.getLoanStart());
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			saPageContent[index++] = "";

			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� getExamineAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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
	 * ��д�ı�ȱʡֵ ����޶����ͬ Create Date: 2005-8-19
	 * 
	 * @param lID
	 *            LOAN_CONTRACTFORM���е�ID
	 * @param assclientid
	 *            TODO //�����ͻ�id
	 * @return String ���غ�ͬ�ļ�����·��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				System.out.println("cInfo======================="
						+ cInfo.getName());
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setContractCode(rs.getString("SCONTRACTCODE"));// ��ͬ��
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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
				clinfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				clinfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				clinfo.setPhone(rs.getString("sPhone")); // ���
				clinfo.setFax(rs.getString("sFax")); // ����

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

			// ��ͬ��1ҳ
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// ��֤��
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
			// ծȨ��
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			// ծ����
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
			sContent = "";
			sContent += PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 3, sContent);

			// ��ͬ��4ҳ
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
	 * �Ϻ��ַ�������ʽ����ͬ
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

				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractCode(rs.getString("sContractCode")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
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
			/* ��ͬ��1ҳ */
			saPageContent[index++] = (info.getContractCode() == null ? ""
					: info.getContractCode());
			// �����
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// ������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// ��ͬ����
			saPageContent[index++] = (info.getLoanTypeID() < 0) ? ""
					: LOANConstant.LoanType.getName(info.getLoanTypeID());// �������

			System.out.println("�������ͣ�"
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
			// �� 2 ҳ
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
			// �� 3 ҳ
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

			// �� 4 ҳ
			sContent = "";
			index = 0;
			//

			saPageContent[index++] = "";// ��ͬ����
			saPageContent[index++] = "";
			// ����ˣ��׷���
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// �����ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// ���������ˣ��׷���
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? ""
					: cInfo.getLegalPerson();

			// ���������ˣ��ҷ���
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// ����Ȩ�����׷���
			saPageContent[index++] = "";
			// ����Ȩ�����ҷ���
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
			// ǩԼ�ص�
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
			// �� 5 ҳ
			// *
			sContent = "";
			sTemp = "";
			double dTemp = 0;
			// ��ʼ��EJB
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
			sTemp += " <b>������</b>";
			sTemp += " <table border=0 cellspacing=0 cellpadding=0 width=70% "
					+ styleTopLeftRight + ">";
			sTemp += " <tr>";
			sTemp += " <td width=50% valign=top >";
			sTemp += " <table border =0 width=100% cellspacing=0 cellpadding=0>";
			sTemp += " <tr> ";
			sTemp += " <td colspan=4 align=middle " + styleRightBottom
					+ "> ��    �� </td>";
			sTemp += " </tr> ";
			sTemp += " <tr>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> ��  </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> �� </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> �� </td>";
			sTemp += " <td width=55% align=middle " + styleRightBottom
					+ "> ��   �� </td>";
			sTemp += " </tr>";
			if (cPlan != null) {
				// sTemp += " <div style='page-break-before:always'> </div>";

				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();

					//modified by mzh_fu 2007/03/26 Ϊ�����ĳһ��Ϊ��ʱ,ҳ������ʾ��ȫ������
					
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
					+ "> ��    �� </td>";
			sTemp += "  </tr>";
			sTemp += "  <tr>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> ��  </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> �� </td>";
			sTemp += " <td width=15% align=middle " + styleRightBottom
					+ "> �� </td>";
			sTemp += "  <td width=55% align=middle " + styleBottom
					+ "> ��   �� </td>";
			sTemp += "  </tr>";
			dTemp = 0;
			if (cPlan != null) {
				iter = cPlan.iterator();
				for (i = 0; iter.hasNext(); i++) {
					RepayPlanInfo rp_info = new RepayPlanInfo();
					rp_info = (RepayPlanInfo) iter.next();
					
					//modified by mzh_fu 2007/03/26 Ϊ�����ĳһ��Ϊ��ʱ,ҳ������ʾ��ȫ������
					
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
	 * �Ϻ��ַ���߶֤��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());// ��ͬ���
			// ծȨ��
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// //��֤��
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			//			
			saPageContent[index++] = (clinfo.getName1() == null) ? "" : clinfo
					.getName1();
			// ծ����
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = LOANEnv.CLIENT_NAME;// ծȨ��;
			saPageContent[index++] = DataFormat.formatString(cInfo.getName());
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;// ���˴���;

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

			// ��ͬ��3ҳ
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
			saPageContent[index++] = "";// ǩԼ�ص�;

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
	 * �Ϻ��ַ���֤��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID

				info.setExamineAmount(rs.getDouble("mAmount")); // �������
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

			// ��ͬ��1ҳ
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());// ��ͬ���
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);// ծȨ��
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);// ծȨ��
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
			// ��ͬ��3ҳ
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
	 * �Ϻ��ַ���߶��Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
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

			// ��ͬ��3ҳ
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

			// ��ͬ��4ҳ
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
	 * �Ϻ��ַ���Ѻ��ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setBorrowClientName(rs.getString("sClientName")); // ����ͻ�����
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractCode(rs.getString("sContractCode")); // �����ͬ��
				info.setLoanTypeID(rs.getLong("loanType")); // ��������
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setContractID(rs.getLong("nContractID")); // ��ͬID
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // ��ͬ���

				info.setAssureAmount(rs.getDouble("mAmount")); // �������
				info.setInterestRate(rs.getDouble("mPledgeRate")); // ��Ѻ��
				info.setAllAmount(rs.getDouble("mPledgeAmount")); // ��Ѻ��

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

			// ��ͬ��1ҳ
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			saPageContent[0] = " ";// ��������
			// sContent += formatString(saPageContent[0]) + CONTENT_SEPERATOR +
			// PAGE_SEPERATOR;
			sContent += formatString(saPageContent[0]) + PAGE_SEPERATOR;
			sFileName = saveContent(sFileName, 2, sContent);

			// ��ͬ��3ҳ
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
	 * �Ϻ��ַ�ί�д���ί�к�ͬ
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				consignInfo.setName(rs.getString("ConsignName")); // �ͻ�����
				consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // ���˴���
				consignInfo.setAddress(getAddress(rs
						.getString("ConsignProvince"), rs
						.getString("ConsignCity"), rs
						.getString("ConsignAddress"))); // ��ַ
				consignInfo.setPhone(rs.getString("ConsignPhone")); // ���
				consignInfo.setFax(rs.getString("ConsignFax")); // ����
				consignInfo.setZipCode(rs.getString("ConsignZipCode")); // ��������
				consignInfo.setBank1(rs.getString("ConsignBank1")); // ��������
				consignInfo.setAccount(rs.getString("ConsignAccount")); // �˺�
				assureInfo.setName1(rs.getString("AssureName")); // �ͻ�����
				assureInfo.setLegalPerson(rs.getString("AssureLegalPerson")); // ���˴���
				assureInfo.setAddress(getAddress(
						rs.getString("AssureProvince"), rs
								.getString("AssureCity"), rs
								.getString("AssureAddress"))); // ��ַ
				assureInfo.setPhone(rs.getString("AssurePhone")); // ���
				assureInfo.setFax(rs.getString("AssureFax")); // ����
				assureInfo.setZipCode(rs.getString("AssureZipCode")); // ��������
				assureInfo.setBank1(rs.getString("AssureBank1")); // ��������
				assureInfo.setAccount(rs.getString("AssureAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setExamineAmount(rs.getDouble("mExamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate")); // ��ͬ¼��ʱ��
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setContractCode(rs.getString("sContractCode"));// ��ͬ���
				info.setCurrencyID(rs.getLong("nCurrencyId"));// ����
				info.setLoanTypeID(rs.getLong("nTypeId"));// ��������
				info.setInterestRate(rs.getDouble("mInterestRate"));// ����
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setChargeRate(rs.getDouble("mChargeRate")); // ��������
				info.setChargeRateType(rs.getLong("nChargeRateTypeID")); // ������������
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

			// ��ͬ��1ҳ
			// ��ͬ���
			saPageContent[index++] = (info.getContractCode() == null) ? ""
					: info.getContractCode();
			// ί����
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			// ������
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// ����ˣ�
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();
			// �����ˣ�
			saPageContent[index++] = (assureInfo.getName1() == null) ? ""
					: assureInfo.getName1();
			// Ϊ�����ֵ�ܴ��ʱ��ȡ�õ� mLoanAmount
			// ��һ����ѧ��������ʾ����ֵ��������Ƚ�����ʽ���ɴ���λС�����ַ�������ת���ɴ�д������

			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);
			// ��;
			saPageContent[index++] = DataFormat.formatString(info
					.getLoanPurpose());
			// ������
			if (info.getLoanAmount() > 0)
				tmp = "0.00";
			tmp = df.format(info.getLoanAmount());
			saPageContent[index++] = ChineseCurrency.showChinese(tmp);

			// ��������
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

			// ����������
			saPageContent[index++] = DataFormat.formatRate(info
					.getInterestRate(), 2);
			// ��������
			saPageContent[index++] = DataFormat.formatRate(
					info.getChargeRate(), 2);
			// �ʽ��˻�
			saPageContent[index++] = DataFormat
					.formatString(cInfo.getAccount());
			// ���ڷ�Ϣ
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;
			sTemp = "";

			// saPageContent[index++] =
			// DataFormat.formatRate(info.getInterestRate()/10.000000);
			saPageContent[index++] = "";

			// �׷�
			saPageContent[index++] = (consignInfo.getName() == null) ? ""
					: consignInfo.getName();
			// �׷�����������
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? ""
					: consignInfo.getLegalPerson();
			// �׷���Ȩ������
			saPageContent[index++] = "";
			// �ҷ�
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			// �ҷ�����������
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;

			// �ҷ���Ȩ������
			saPageContent[index++] = "";

			// ����
			saPageContent[index++] = (info.getBorrowClientName() == null) ? ""
					: info.getBorrowClientName();
			// ��������������
			saPageContent[index++] = "";
			// ������Ȩ������
			saPageContent[index++] = "";
			// ����
			saPageContent[index++] = (assureInfo.getName1() == null) ? ""
					: assureInfo.getName1();

			// ��������������
			saPageContent[index++] = "";

			// ������Ȩ������
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

			// ��ͬ��3ҳ

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
			// ǩԼ�ص�
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
	 * �Ϻ��ַ��������ڱ���Э��
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�
				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ��ͬ¼������
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// ��������һ
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// �������Ͷ�
				info.setBeneficiary(rs.getString("Beneficiary"));// ����������
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// ��������
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

			// ��ͬ��1ҳ
			// ��ͬ���
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			// ������
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// ������ȫ��
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);

			// ��������
			saPageContent[index++] = LOANConstant.AssureType1.getName(info
					.getAssureTypeID1())
					+ "--"
					+ LOANConstant.AssureType2.getName(info.getAssureTypeID2());// ��������
			// �������
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getExamineAmount() > 0)
				tmp = df.format(info.getExamineAmount());
			saPageContent[index++] = (info.getExamineAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(info.getExamineAmount());
			// ����������
			saPageContent[index++] = DataFormat.formatString(info
					.getBeneficiary());

			// saPageContent[index++] =
			// Constant.CurrencyType.getName(info.getCurrencyID());
			// //����������ע���ַ
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// ������Ч��
			saPageContent[index++] = DataFormat.formatInt(
					info.getIntervalNum(), 1);
			// ��������
			saPageContent[index++] = DataFormat.formatRate(info
					.getAssureChargeRate());
			// ������
			double assureAmount = 0;
			// �������
			assureAmount = (info.getLoanAmount() * info.getAssureChargeRate()) / 100;
			tmp = "0.00";
//			modify by ��С�� 2007-06-27	
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
			// ������
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

			// ��ͬ��2ҳ
			sContent = "";
			index = 0;

			assureAmount = info.getLoanAmount()
					* (info.getAssureChargeRate() / 1200)
					* info.getIntervalNum();
			// saPageContent[index++] =
			// DataFormat.formatDisabledAmount(assureAmount);//������
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

			// ��ͬ��3ҳ
			sContent = "";
			index = 0;

			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// �����˸���
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();
			// �����˸���
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// ����������
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
				cInfo.setName(rs.getString("sName")); // �ͻ�����
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // ���˴���
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs
						.getString("sCity"), rs.getString("sAddress"))); // ��ַ
				cInfo.setPhone(rs.getString("sPhone")); // ���
				cInfo.setFax(rs.getString("sFax")); // ����
				cInfo.setZipCode(rs.getString("sZipCode")); // ��������
				cInfo.setBank1(rs.getString("sBank1")); // ��������
				cInfo.setAccount(rs.getString("sAccount")); // �˺�

				info.setLoanReason(rs.getString("sLoanReason")); // ���ԭ��
				info.setLoanPurpose(rs.getString("sLoanPurpose")); // �����;
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanAmount(rs.getLong("mLoanAmount"));//
				info.setExamineAmount(rs.getDouble("MexamineAmount")); // �����׼���
				info.setIntervalNum(rs.getLong("nIntervalNum")); // �������
				info.setLoanStart(rs.getTimestamp("dtStartDate")); // ���ʼʱ��
				info.setLoanEnd(rs.getTimestamp("dtEndDate")); // �������ʱ��
				info.setInputDate(rs.getTimestamp("dtInputDate"));// ��ͬ¼������
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				RateInfo rInfo = new RateInfo();
				rInfo = contractDao.getLatelyRate(-1, info.getContractID(),
						null);
				info.setInterestRate(rInfo.getLateRate()); // ִ������
				info.setOther(rs.getString("sOther")); // ������Դ
				info.setContractID(rs.getLong("contractID")); // ��ͬID
				info.setLoanTypeID(rs.getLong("nTypeID")); // ��������
				info.setContractCode(rs.getString("sContractCode"));
				info.setAssureTypeID1(rs.getLong("AssureTypeID1"));// ��������һ
				info.setAssureTypeID2(rs.getLong("AssureTypeID2"));// �������Ͷ�
				info.setBeneficiary(rs.getString("Beneficiary"));// ����������
				info.setCurrencyID(rs.getLong("nCurrencyID"));//
				info.setAssureChargeRate(rs.getDouble("AssureChargeRate"));// ��������
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

			// ��ͬ��1ҳ
			// ��ͬ���
			saPageContent[index++] = DataFormat.formatString(info
					.getContractCode());
			saPageContent[index++] = "";

			// ������
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// ������
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// �ɱ���
			DecimalFormat df = new DecimalFormat("#.00");
			String tmp = "0.00";
			if (info.getLoanAmount() > 0)
				tmp = df.format(info.getLoanAmount());
			saPageContent[index++] = (info.getLoanAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			// �����ܼ�
			saPageContent[index++] = (info.getLoanAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";
			// ����
			saPageContent[index++] = info.getInterestRate() >= 0 ? info
					.getFormatInterestRate() : "";
			// ����
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

			// ��ͬ��3ҳ

			sContent = "";
			index = 0;
			// sureAmount = info.getLoanAmount() *
			// (info.getAssureChargeRate()/1200) * info.getIntervalNum();
			// PageContent[index++] =
			// DataFormat.formatDisabledAmount(assureAmount);//������
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			// ������
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

			// ��ͬ��4ҳ
			sContent = "";
			index = 0;

			// �������
			if (info.getMatureNominalAmount() > 0)
				tmp = df.format(info.getMatureNominalAmount());
			saPageContent[index++] = (info.getMatureNominalAmount() > 0) ? ChineseCurrency
					.showChinese(tmp)
					: "";

			saPageContent[index++] = "";
			// �׷�����
			saPageContent[index++] = DataFormat
					.formatString(LOANEnv.CLIENT_NAME);
			// �ҷ�����
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo
					.getName();

			// ����������
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

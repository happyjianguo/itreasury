/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obcontent.dao;


import java.sql.*;
import java.util.*;
import java.io.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendApplyInfo;
import com.iss.itreasury.ebank.obcontent.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
import com.iss.itreasury.loan.util.LOANEnv;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.dataentity.AutoFileInfo;
import com.iss.itreasury.loan.repayplan.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBContentDao
{

	public static String strSeparator = java.io.File.separator;
	public static String TEMPLET_SEPERATOR = " &&& ";
	public static String PAGE_SEPERATOR = " :: ";
	public static String CONTENT_SEPERATOR = " ;; ";
	public static String SEPERATOR = "  ";

	private static Log4j log4j = null;

	private int arrLen = 5010;

	public OBContentDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	//	///////////////////////////////////////////////////////////////////////
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	private String formatString(String str) throws SQLException
	{
		return (str == null ? "" : str);
	}

	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(CallableStatement cs) throws SQLException
	{
		try
		{
			if (cs != null)
			{
				cs.close();
				cs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	//	///////////////////////////////////////////////////////////////////////////

	/**
	* 保存文件
	* Create Date: 2003-10-15
	* @param strName 文件名（包括路径），新增时文件名为空
	* @param lStepNo 页号
	* @param strContent 文件内容
	* @return String 新增或修改文件的名字，长度为0，不成功
	* @exception Exception
	*/
	public String saveContent(String strName, long lStepNo, String strContent) throws Exception
	{
		int i = 0;
		long lResult = 0;
		String sTmp = new String(strContent);
		String sOldContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{

			// get the old content
			if (strName != null && strName.length() > 0)
			{
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();
				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}
				sOldContent = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			else // get the file name if create a new file
				{
				AutoFileInfo fileInfo = new AutoFileInfo();
				fileInfo.setFileType("txt");
				strName = AutoFileBean.getDestPath(Constant.DocType.LOANCONTRACTCONTENT);
				strName += AutoFileBean.getFileName(fileInfo);
			}

			//get the new content
			int nSIndex = 0;
			int nEIndex = 0;
			int nIndex = 0;
			String strData = "";
			for (i = 0; i < lStepNo; i++)
			{

				nSIndex = nEIndex;
				if (i > 0)
				{
					nIndex = nSIndex + 4;
				}

				if (sOldContent != null && sOldContent.length() > 0)
				{
					nEIndex = sOldContent.indexOf(PAGE_SEPERATOR, nIndex);
				}
				else
				{
					nEIndex = 0;
				}

				if (nEIndex <= nSIndex)
				{
					i++;
					break;
				}
			}

			if (i < lStepNo)
			{
				sOldContent = sOldContent + PAGE_SEPERATOR + strContent;
			}
			else
			{
				if (nSIndex > 0)
				{
					nSIndex = nSIndex + 4;
				}

				if (nEIndex > 0)
				{
					sOldContent = sOldContent.substring(0, nSIndex) + strContent + sOldContent.substring(nEIndex);
				}
				else if (sOldContent == null || sOldContent.length() <= 0)
				{
					sOldContent = strContent;
				}
				else
				{
					sOldContent = sOldContent.substring(0, nSIndex) + strContent;
				}
			}

			//update content
			java.io.File f = new java.io.File(strName);
			if (f.exists() && lStepNo ==1)
			{
				//f.delete();
			}
			FileWriter fw = new FileWriter(strName);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sOldContent);
			pw.close();
			fw.close();

		}
		catch (Exception e)
		{
			strName = "";
			System.out.println(e.toString());
			throw e;
		}
		finally
		{

		}
		return strName;
	}

	/**
	* 得到文件内容
	* Create Date: 2003-10-15
	* @param strName 文件名（包括路径）
	* @param lStepNo 页号
	* @return String 文件内容
	* @exception Exception
	*/
	public String getFileContent(String strName, long lStepNo) throws Exception
	{
		int i = 0;
		String sWholeContent = "";
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{
			// get the whole content
			sWholeContent = getFileContent(strName);

			//get the content
			if (sWholeContent.length() > 0)
			{
				int nSIndex = 0;
				int nEIndex = 0;
				String strData = "";
				for (i = 0; i < lStepNo; i++)
				{
					nSIndex = nEIndex;
					nEIndex = sWholeContent.indexOf(PAGE_SEPERATOR, nSIndex + 4);
					if (nEIndex <= nSIndex)
					{
						i++;
						break;
					}
				}
				if (i < lStepNo)
				{
					sContractContent = "";
				}
				else
				{
					if (nSIndex > 0)
					{
						nSIndex = nSIndex + 4;
					}

					if (nEIndex > 0)
					{
						sContractContent = sWholeContent.substring(nSIndex, nEIndex);
					}
					else
					{
						sContractContent = sWholeContent.substring(nSIndex);
					}
				}
			}
			else
			{
				sContractContent = "";
			}
		}
		catch (Exception e)
		{
			sContractContent = "";
			System.out.println(e.toString());
			throw e;
		}
		finally
		{

		}
		return sContractContent;
	}

	/**
	* 得到文件全部内容
	* Create Date: 2003-10-15
	* @param strName 文件名（包括路径）
	* @return String 文件内容
	* @exception Exception
	*/
	public String getFileContent(String strName) throws IException
	{
		int i = 0;
		String sContractContent = "";
		StringBuffer sbTmp = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;

		try
		{
			// get the whole content
			if (strName != null && strName.length() > 0)
			{
				fr = new FileReader(strName);
				br = new BufferedReader(fr);
				String record = new String();

				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}

				sContractContent = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			else
			{
				sContractContent = "";
			}
		}
		catch (Exception e)
		{
			sContractContent = "";
			log4j.error(e.getMessage());
			throw new IException("Loan_E106");
		}
		return sContractContent;
	}

	/**
	* 合并文件模板和文件内容
	* Create Date: 2003-10-15
	* @param strTempletName    文件模板名
	* @param strContentName    文件内容名
	* @return String   合并的字符串
	* @exception Exception
	*/
	public String mergeContent(String strContentName, String strTempletName) throws IException, Exception
	{
		StringBuffer sbTmp = new StringBuffer();
		String strTemplet = "";
		String strContent = "";
		String strUnite = "";
		String record = new String();
		String arrContent[] = new String[arrLen];
		FileReader fr = null;
		BufferedReader br = null;
		int nArrayLength = 0;
		int nIndex1; //PAGE_SEPERATOR的索引位置
		int nIndex2; //CONTENT_SEPERATOR的索引位置
		int nTmp = 0;

		try
		{
			// get strContent
			if (strContentName == null || strContentName.length() == 0)
			{
				strContent = "";
			}
			else
			{
				try
				{
					fr = new FileReader(strContentName);
					br = new BufferedReader(fr);
					while ((record = br.readLine()) != null)
					{
						sbTmp = sbTmp.append(record);
					}
					strContent = sbTmp.toString();
					sbTmp.setLength(0);
					br.close();
					fr.close();
				}
				catch (Exception e)
				{
					log4j.error("1:" + e.getMessage());
					throw new IException("Loan_E106");
				}
			}

			// get the templet content:strTempletContent

			try
			{
				fr = new FileReader(strTempletName);
				br = new BufferedReader(fr);
				while ((record = br.readLine()) != null)
				{
					sbTmp = sbTmp.append(record);
				}
				strTemplet = sbTmp.toString();
				sbTmp.setLength(0);
				br.close();
				fr.close();
			}
			catch (Exception e)
			{
				log4j.error("2:" + e.getMessage());
				throw new IException("Loan_E106");
			}

			// String to Array
			String strPage = "";
			//取的某一页的内容
			nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
			while (nIndex1 >= 0)
			{
				strPage = strContent.substring(0, nIndex1);
				//取得每一项填空内容
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				while (nIndex2 >= 0)
				{
					if (nTmp < arrLen)
					{
						if (nIndex2 == 0)
						{
							arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else
						{
							arrContent[nTmp] = strPage.substring(0, nIndex2);
						}
					}
					strPage = strPage.substring(nIndex2 + 4);
					nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
					nTmp++;

				}

				if (nTmp < arrLen)
				{
					if (!strPage.equals(""))
					{
						arrContent[nTmp] = strPage;
					}
					else
					{
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
				strContent = strContent.substring(nIndex1 + 4);
				nIndex1 = strContent.indexOf(PAGE_SEPERATOR);
				nTmp++;

			}
			//最后一页
			strPage = strContent.substring(0);
			//取得每一项填空内容
			nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
			while (nIndex2 >= 0)
			{
				if (nTmp < arrLen)
				{
					if (nIndex2 == 0)
					{
						arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					else
					{
						arrContent[nTmp] = strPage.substring(0, nIndex2);
					}
				}
				strPage = strPage.substring(nIndex2 + 4);
				nIndex2 = strPage.indexOf(CONTENT_SEPERATOR);
				nTmp++;

			}
			if (nTmp < arrLen)
			{
				if (!strPage.equals(""))
				{
					arrContent[nTmp] = strPage;
				}
				else
				{
					arrContent[nTmp] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			nArrayLength = nTmp + 1;

			nTmp = 0;
			nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			while (nIndex1 >= 0)
			{

				if (nTmp < nArrayLength)
				{
					strUnite = strUnite + strTemplet.substring(0, nIndex1) + SEPERATOR + arrContent[nTmp] + SEPERATOR;
					nTmp++;
				}
				else
				{
					strUnite = strUnite + strTemplet.substring(0, nIndex1) + SEPERATOR + SEPERATOR;
				}
				strTemplet = strTemplet.substring(nIndex1 + 4);
				nIndex1 = strTemplet.indexOf(TEMPLET_SEPERATOR);

			}
			strUnite = strUnite + strTemplet;

			if (strTempletName.equals(Env.UPLOAD_PATH+"loan/template/yhchdhptxxy.txt"))
			{
				strContent = getFileContent(strContentName, 5);

				int nIndex = 0; //","的索引位置
				String sTemp = strContent;
				nTmp = 0;
				if (sTemp.length() > 0)
				{
					nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0)
					{
						sTemp = sTemp.substring(nIndex + 4);
						nIndex = sTemp.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					nTmp++;
				}
				String arrCont[] = new String[nTmp];

				if (strContent.length() > 0)
				{
					nIndex = 0; //","的索引位置
					nTmp = 0;
					nIndex = strContent.indexOf(CONTENT_SEPERATOR);
					while (nIndex >= 0)
					{
						arrCont[nTmp] = strContent.substring(0, nIndex);
						strContent = strContent.substring(nIndex + 4);
						nIndex = strContent.indexOf(CONTENT_SEPERATOR);
						nTmp++;
					}
					if (nTmp < arrLen)
					{
						arrCont[nTmp] = strContent;
					}
					nTmp++;
				}

				sTemp = "";
				nIndex = 0;
				for (int x = 0; x < nTmp / 10; x++)
				{
					sTemp += " <tr bgcolor=#FFFFFF > ";
					sTemp += "<td class=td-topright align=center >" + (x + 1) + "</td>";
					for (int i = 0; i < 10; i++)
					{
						if (nIndex < arrLen)
						{
							if (i == 9)
							{
								sTemp += "<td class=td-top align=center ><font face=宋体>" + arrCont[nIndex++] + "</font></td>";
							}
							else
							{
								sTemp += "<td class=td-topright align=center ><font face=宋体>" + arrCont[nIndex++] + "</font></td>";
							}
						}
					}
					sTemp += " </tr> ";
				}

				sTemp += "</table></body></html>";
				strUnite += sTemp;
			}
		}
		catch (IException ie)
		{
			log4j.error("3:" + ie.getMessage());
			throw new IException(ie.getMessage());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strUnite;
	}

	/**
	* 组合出正确的地址格式
	* Create Date: 2003-10-15
	* @param  strProvince 省
	* @param  strCity 市
	* @param  strAddress 地址
	* @return String   返回正确的地址格式
	* @exception Exception
	*/
	private String getAddress(String strProvince, String strCity, String sAddress) throws Exception
	{
		String strAddress = "";
		try
		{
			if (strProvince != null && strProvince.trim().length() > 0)
			{
				strAddress = strProvince + "省";
			}
			if (strCity != null && strCity.trim().length() > 0)
			{
				strAddress = strAddress + strCity + "市";
			}
			if (sAddress == null)
			{
				sAddress = "";
			}
			strAddress = strAddress + sAddress;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strAddress;
	}

	/**
	* 得到合同文本内容
	* Create Date: 2003-10-15
	* @param lID LOAN_CONTRACTCONTENT表中的ID
	* @param lPageNo 页号
	* @return String 合同文本内容
	* @exception Exception
	*/
	public String getContent(long lID, long lPageNo) throws IException, Exception
	{
		OBContentInfo info = new OBContentInfo();
		String sContent = "";

		try
		{
			info = findByID(lID);
			String sFileName = info.getDocName();
			if (!sFileName.equals(""))
			{

				sContent = getFileContent(sFileName, lPageNo);
			}
			else
			{
				sContent = "";
			}
		}
		catch (IException ie)
		{
			log4j.error(ie.getMessage());
			throw new IException(ie.getMessage());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sContent;
	}


	/**
	* 填写文本缺省值  财务情况统计表
	* Create Date: 2003-10-15
	* @param lID    客户标示（Client表中的标示）
	* @return String   返回合同文件名及路径
	* @exception Exception
	*/
	public String addFinanceTJB(long lID) throws Exception
	{

		String sFileName = "", sContent = "";
		String[] saPageContent = new String[50];

		int PAGECOUNT = 35;

		try
		{
			//*  财务情况统计表
			//saPageContent[0] = ""+lID;
			//saPageContent[PAGECOUNT-1] = ""+lID;
			for (int i = 0; i < PAGECOUNT; i++)
			{
				if (i == 0)
				{
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
				if (i == PAGECOUNT - 1)
				{
					saPageContent[i] = "" + lID;
					sContent += formatString(saPageContent[i]);
				}
				else
				{
					saPageContent[i] = "";
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);
			//*/

		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return sFileName;
	}
	/**
	* 填写文本缺省值  借款展期协议
	* Create Date: 2003-11-12
	* @param lID    LOAN_EXTENDFORM表中的ID
	* @return String   返回合同文件名及路径
	* @exception Exception
	*/
	public String addJKZQXY(long lID) throws Exception
	{
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

		try
		{
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb.append(" FROM loan_contractForm con,Client c,Client cc,");
			sb.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != " + LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next())
			{
				if (iFlag > 0)
				{
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); //展期金额
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate").before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate : rs.getTimestamp("ExtEndDate"); //展期期限
				}
				else
				{
					cInfo.setName(rs.getString("sName")); //  客户名称
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  地址
					cInfo.setPhone(rs.getString("sPhone")); // 电活
					cInfo.setFax(rs.getString("sFax")); // 传真
					cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
					cInfo.setBank1(rs.getString("sBank1")); //开户银行
					cInfo.setAccount(rs.getString("sAccount")); // 账号

					consignInfo.setName(rs.getString("ConsignName")); //  客户名称
					consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
					consignInfo.setAddress(getAddress(rs.getString("ConsignProvince"), rs.getString("ConsignCity"), rs.getString("ConsignAddress"))); //  地址
					consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
					consignInfo.setFax(rs.getString("ConsignFax")); // 传真
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
					consignInfo.setBank1(rs.getString("ConsignBank1")); //开户银行
					consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号

					info.setLoanReason(rs.getString("sLoanReason")); //借款原因
					info.setLoanPurpose(rs.getString("sLoanPurpose")); //借款用途
					info.setExamineAmount(rs.getDouble("mExamineAmount")); //借款批准金额
					info.setIntervalNum(rs.getLong("nIntervalNum")); //借款期限
					info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款开始时间
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款结束时间
					info.setContractID(rs.getLong("contractID")); //合同ID
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, info.getContractID(), null);
					info.setInterestRate(rInfo.getLateRate()); //执行利率
					info.setOther(rs.getString("sOther")); //还款来源
					info.setContractCode(rs.getString("SCONTRACTCODE")); //合同编号
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); //录入时间

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); //展期金额
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); //展期利率
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); //展期期限

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); //保证类型
					e_info.m_sExCode = rs.getString("SASSURECODE"); //担保合同编号*/
				}
				iFlag++;
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			//贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			//借款人
			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? "" : consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? "" : consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? "" : consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? "" : consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? "" : consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? "" : consignInfo.getAccount();

			//担保人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			//saPageContent[index++] = (info.getLoanReason() == null) ? "" : info.getLoanReason();

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币";

			sTemp = "";
			if (info.getExamineAmount() > 0)
			{
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(info.getInterestRate()); //年息
			saPageContent[index++] = "人民币"; //展期币种
			sTemp = "";
			if (e_info.m_dExtendAmount > 0)
			{
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(e_info.m_dInterestRate); //年息

			long[] lAssureType = { LOANConstant.AssureType.ASSURE, LOANConstant.AssureType.PLEDGE, LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9)
					{
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					}
					else
					{
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";

			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			//合同第3页
			sContent = "";
			index = 0;
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
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

			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 3, sContent); //*/
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}
	/**
	* 填写文本缺省值  委托贷款展期协议
	* Create Date: 2003-11-12
	* @param lID    LOAN_EXTENDFORM表中的ID
	* @return String   返回合同文件名及路径
	* @exception Exception
	*/
	public String addWTDKZQXY(long lID) throws Exception
	{
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
		int SECONDPAGECOUNT = 31; //33;
		int THIRDPAGECOUNT = 28; //25;
		int FOURTHPAGECOUNT = 24;
		int i;

		try
		{
			conn = Database.getConnection();
			sb.append(" SELECT con.id as contractID,con.*,c.*,");
			sb.append(" ass.NASSURETYPEID,ass.SASSURECODE,");
			sb.append(" extail.MEXTENDAMOUNT as ExtAmount,");
			sb.append(" extail.DTEXTENDENDDATE as ExtEndDate,");
			sb.append(" ext.MINTERESTADJUST as ExtInterestRate,");
			sb.append(" ccc.sName as WTConsignName,ccc.sLegalPerson as WTConsignLegalPerson,");
			sb.append(" ccc.sProvince as WTConsignProvince,ccc.sCity as WTConsignCity,");
			sb.append(" ccc.sAddress as WTConsignAddress,ccc.sPhone as WTConsignPhone,");
			sb.append(" ccc.sFax as WTConsignFax,ccc.sZipCode as WTConsignZipCode,");
			sb.append(" ccc.sBank1 as WTConsignBank1,ccc.sAccount as WTConsignAccount,");
			sb.append(" cc.sName as ConsignName,cc.sLegalPerson as ConsignLegalPerson,");
			sb.append(" cc.sProvince as ConsignProvince,cc.sCity as ConsignCity,");
			sb.append(" cc.sAddress as ConsignAddress,cc.sPhone as ConsignPhone,");
			sb.append(" cc.sFax as ConsignFax,cc.sZipCode as ConsignZipCode,");
			sb.append(" cc.sBank1 as ConsignBank1,cc.sAccount as ConsignAccount");
			sb.append(" FROM loan_contractForm con,Client c,Client cc,Client ccc,");
			sb.append(" loan_ExtendForm ext,loan_ExtendDetail extail,loan_loanContractAssure ass");
			sb.append(" WHERE con.nBorrowClientID = cc.ID");
			sb.append(" AND con.NCONSIGNCLIENTID = ccc.ID");
			sb.append(" AND ext.NCONTRACTID = con.id");
			sb.append(" AND ext.id = extail.NEXTENDFORMID");
			sb.append(" AND ass.NCONTRACTID(+) = con.id");
			sb.append(" AND ass.nClientID = c.id(+)");
			sb.append(" AND ass.nAssureTypeID(+) != " + LOANConstant.AssureType.CREDIT);
			sb.append(" AND ext.ID = ?");

			log4j.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			ContractDao contractDao = new ContractDao();
			int iFlag = 0;
			while (rs.next())
			{
				if (iFlag > 0)
				{
					e_info.m_dExtendAmount += rs.getDouble("ExtAmount"); //展期金额
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate").before(e_info.m_tsExtendEndDate) ? e_info.m_tsExtendEndDate : rs.getTimestamp("ExtEndDate"); //展期期限
				}
				else
				{
					cInfo.setName(rs.getString("sName")); //  客户名称
					cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
					cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  地址
					cInfo.setPhone(rs.getString("sPhone")); // 电活
					cInfo.setFax(rs.getString("sFax")); // 传真
					cInfo.setZipCode(rs.getString("sZipCode")); // 邮政编码
					cInfo.setBank1(rs.getString("sBank1")); //开户银行
					cInfo.setAccount(rs.getString("sAccount")); // 账号

					WTconsignInfo.setName(rs.getString("WTConsignName")); //  客户名称
					WTconsignInfo.setLegalPerson(rs.getString("WTConsignLegalPerson")); // 法人代表
					WTconsignInfo.setAddress(getAddress(rs.getString("WTConsignProvince"), rs.getString("WTConsignCity"), rs.getString("WTConsignAddress"))); //  地址
					WTconsignInfo.setPhone(rs.getString("WTConsignPhone")); // 电活
					WTconsignInfo.setFax(rs.getString("WTConsignFax")); // 传真
					WTconsignInfo.setZipCode(rs.getString("WTConsignZipCode")); // 邮政编码
					WTconsignInfo.setBank1(rs.getString("WTConsignBank1")); //开户银行
					WTconsignInfo.setAccount(rs.getString("WTConsignAccount")); // 账号

					consignInfo.setName(rs.getString("ConsignName")); //  客户名称
					consignInfo.setLegalPerson(rs.getString("ConsignLegalPerson")); // 法人代表
					consignInfo.setAddress(getAddress(rs.getString("ConsignProvince"), rs.getString("ConsignCity"), rs.getString("ConsignAddress"))); //  地址
					consignInfo.setPhone(rs.getString("ConsignPhone")); // 电活
					consignInfo.setFax(rs.getString("ConsignFax")); // 传真
					consignInfo.setZipCode(rs.getString("ConsignZipCode")); // 邮政编码
					consignInfo.setBank1(rs.getString("ConsignBank1")); //开户银行
					consignInfo.setAccount(rs.getString("ConsignAccount")); // 账号

					info.setLoanReason(rs.getString("sLoanReason")); //借款原因
					info.setLoanPurpose(rs.getString("sLoanPurpose")); //借款用途
					info.setExamineAmount(rs.getDouble("mExamineAmount")); //借款批准金额
					info.setIntervalNum(rs.getLong("nIntervalNum")); //借款期限
					info.setLoanStart(rs.getTimestamp("dtStartDate")); //贷款开始时间
					info.setLoanEnd(rs.getTimestamp("dtEndDate")); //贷款结束时间
					RateInfo rInfo = new RateInfo();
					rInfo = contractDao.getLatelyRate(-1, rs.getLong("contractID"), null);
					info.setInterestRate(rInfo.getLateRate()); //执行利率
					info.setOther(rs.getString("sOther")); //还款来源
					info.setContractID(rs.getLong("contractID")); //合同ID
					info.setContractCode(rs.getString("SCONTRACTCODE")); //合同编号
					info.setInputDate(rs.getTimestamp("DTINPUTDATE")); //录入时间

					e_info.m_dExtendAmount = rs.getDouble("ExtAmount"); //展期金额
					e_info.m_dInterestRate = rs.getDouble("ExtInterestRate"); //展期利率
					e_info.m_tsExtendEndDate = rs.getTimestamp("ExtEndDate"); //展期期限

					e_info.lLoanTypeID = rs.getLong("NASSURETYPEID"); //保证类型
					e_info.m_sExCode = rs.getString("SASSURECODE"); //担保合同编号*/
				}
				iFlag++;
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}

			String sTemp = "";
			int index = 0;

			// 合同第1页
			//委托人
			saPageContent[index++] = (WTconsignInfo.getName() == null) ? "" : WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? "" : WTconsignInfo.getLegalPerson();
			saPageContent[index++] = (WTconsignInfo.getAddress() == null) ? "" : WTconsignInfo.getAddress();
			saPageContent[index++] = (WTconsignInfo.getPhone() == null) ? "" : WTconsignInfo.getPhone();
			saPageContent[index++] = (WTconsignInfo.getFax() == null) ? "" : WTconsignInfo.getFax();
			saPageContent[index++] = (WTconsignInfo.getZipCode() == null) ? "" : WTconsignInfo.getZipCode();
			saPageContent[index++] = (WTconsignInfo.getBank1() == null) ? "" : WTconsignInfo.getBank1();
			saPageContent[index++] = (WTconsignInfo.getAccount() == null) ? "" : WTconsignInfo.getAccount();

			//贷款人
			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = LOANEnv.CLIENT_ADDRESS;
			saPageContent[index++] = LOANEnv.CLIENT_TEL;
			saPageContent[index++] = LOANEnv.CLIENT_FAX;
			saPageContent[index++] = LOANEnv.CLIENT_ZIP;

			//借款人
			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = (consignInfo.getAddress() == null) ? "" : consignInfo.getAddress();
			saPageContent[index++] = (consignInfo.getPhone() == null) ? "" : consignInfo.getPhone();
			saPageContent[index++] = (consignInfo.getFax() == null) ? "" : consignInfo.getFax();
			saPageContent[index++] = (consignInfo.getZipCode() == null) ? "" : consignInfo.getZipCode();
			saPageContent[index++] = (consignInfo.getBank1() == null) ? "" : consignInfo.getBank1();
			saPageContent[index++] = (consignInfo.getAccount() == null) ? "" : consignInfo.getAccount();

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 1, sContent);

			// 合同第2页
			sContent = "";
			index = 0;

			//担保人
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getFax() == null) ? "" : cInfo.getFax();
			saPageContent[index++] = (cInfo.getZipCode() == null) ? "" : cInfo.getZipCode();
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			//saPageContent[index++] = (info.getLoanReason() == null) ? "" : info.getLoanReason();

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanStart());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";
			saPageContent[index++] = "人民币";

			sTemp = "";
			if (info.getExamineAmount() > 0)
			{
				sTemp = DataFormat.formatAmount(info.getExamineAmount());
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);

			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			//saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(info.getInterestRate()); //年息
			saPageContent[index++] = "人民币"; //展期币种
			sTemp = "";
			if (e_info.m_dExtendAmount > 0)
			{
				sTemp = DataFormat.formatAmount(e_info.m_dExtendAmount);
			}
			saPageContent[index++] = ChineseCurrency.showChinese(sTemp);
			sTemp = "";
			sTemp = DataFormat.getDateString(info.getLoanEnd());
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}
			sTemp = DataFormat.getDateString(e_info.m_tsExtendEndDate);
			if (sTemp.length() > 9)
			{
				saPageContent[index++] = sTemp.substring(0, 4);
				saPageContent[index++] = sTemp.substring(5, 7);
				saPageContent[index++] = sTemp.substring(8, 10);
			}
			else
			{
				saPageContent[index++] = "";
				saPageContent[index++] = "";
				saPageContent[index++] = "";
			}

			//saPageContent[index++] = ""; //月息
			saPageContent[index++] = DataFormat.formatRate(e_info.m_dInterestRate); //年息
			saPageContent[index++] = ""; //月手续费息//改为  年手续费息
			saPageContent[index++] = ""; //手续费
			saPageContent[index++] = "";

			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 2, sContent);

			//合同第3页
			sContent = "";
			index = 0;

			long[] lAssureType = { LOANConstant.AssureType.ASSURE, LOANConstant.AssureType.PLEDGE, LOANConstant.AssureType.IMPAWN };
			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					sTemp = "";
					sTemp = DataFormat.getDateString(info.getInputDate());
					if (sTemp.length() > 9)
					{
						saPageContent[index++] = sTemp.substring(0, 4);
						saPageContent[index++] = sTemp.substring(5, 7);
						saPageContent[index++] = sTemp.substring(8, 10);
					}
					else
					{
						saPageContent[index++] = "";
						saPageContent[index++] = "";
						saPageContent[index++] = "";
					}

					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = info.getContractCode();
			saPageContent[index++] = "人民币借款合同";

			for (i = 0; i < lAssureType.length; i++)
			{
				if (e_info.lLoanTypeID == lAssureType[i])
				{
					saPageContent[index++] = "checked"; //checkbox
					saPageContent[index++] = (e_info.m_sExCode == null) ? "" : e_info.m_sExCode;

				}
				else
				{
					saPageContent[index++] = ""; //checkbox
					saPageContent[index++] = "";
				}
			}
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";
			saPageContent[index++] = "";

			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}

			sFileName = saveContent(sFileName, 3, sContent);

			//合同第4页
			sContent = "";
			index = 0;

			sTemp = DataFormat.getDateString();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);
			saPageContent[index++] = "";

			saPageContent[index++] = (WTconsignInfo.getName() == null) ? "" : WTconsignInfo.getName();
			saPageContent[index++] = (WTconsignInfo.getLegalPerson() == null) ? "" : WTconsignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = LOANEnv.CLIENT_NAME;
			saPageContent[index++] = LOANEnv.CLIENT_LEGALPERSON;
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (consignInfo.getName() == null) ? "" : consignInfo.getName();
			saPageContent[index++] = (consignInfo.getLegalPerson() == null) ? "" : consignInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = sTemp.substring(0, 4);
			saPageContent[index++] = sTemp.substring(5, 7);
			saPageContent[index++] = sTemp.substring(8, 10);

			for (i = 0; i < FOURTHPAGECOUNT; i++)
			{
				if (i == FOURTHPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 4, sContent); //*/
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	* 根据合同内容标示、合同页码，修改指定页的合同内容
	* Create Date: 2003-10-15
	* @param  lID 合同内容标示(LOAN_CONTRACTCONTENT表中的ID)
	* @param  lPageNo 修改合同第几页的内容
	* @param  strContent 合同待修改页的内容
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long update(long lID, long lPageNo, String strContent) throws Exception
	{
		long lResult = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String sDocName = "";

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.sDocName");
			sbSQL.append(" FROM OB_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				sDocName = rs.getString("sDocName"); //合同文本文件名
			}

			String sTemp = saveContent(sDocName, lPageNo, strContent);
			if (sTemp != null && !sTemp.equals(""))
			{
				lResult = 1;
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
		return lResult;
	}

	/**
	* 得到合同文本内容
	* Create Date: 2003-10-15
	* @param lID LOAN_CONTRACTCONTENT表中的ID
	* @return OBContentInfo 合同文本内容
	* @exception Exception
	*/
	public OBContentInfo findByID(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		OBContentInfo info = new OBContentInfo();
		String sContent = "";

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*");
			sbSQL.append(" FROM OB_ContractContent a");
			sbSQL.append(" WHERE a.ID = ?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				info.setParentID(rs.getLong("nParentID")); //合同主体的ID
				info.setContractTypeID(rs.getLong("nContractTypeID")); //合同类型ID
				info.setContractID(rs.getLong("nContractID")); //合同ID
				info.setDocName(rs.getString("sDocName")); //合同文本文件名		]

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
		return info;

	}
	/**
	* 得到贷款调查表ID
	* Create Date: 2003-10-15
	* @param lID loanID
	* @return 贷款调查表ID
	* @exception Exception
	*/
	public long findLoanQuestinoaryByLoanID(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM OB_ContractContent");
			sbSQL.append(" WHERE nParentID = ? and nContractTypeID=?");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			ps.setLong(2, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ret = rs.getLong("ID");
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
		return ret;

	}

	/**
	* 得到贷款调查表ID
	* Create Date: 2003-10-15
	* @param lID loanID
	* @return 贷款调查表ID
	* @exception Exception
	*/
	public long findLoanQuestinoaryByClient(long lID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long ret = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.append(" SELECT ID");
			sbSQL.append(" FROM OB_ContractContent");
			sbSQL.append(" WHERE nContractTypeID=? ");
			sbSQL.append(" and nParentID in (select ID from Loan_Loanform where loan_loanForm.Nborrowclientid=?) ");
			sbSQL.append(" order by ID  desc ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(2, lID);
			ps.setLong(1, LOANConstant.ContractType.DKDCB);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ret = rs.getLong("ID");
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
		return ret;

	}

	/**
	* 获取指定合同的合同模版
	* Create Date: 2003-10-15
	* @param  lContentID 文本ID
	* @param  lContractTypeID 文本类型ID
	* @return String   返回合同的合同模版
	* @exception Exception
	*/
	public String getTemplate(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		try
		{
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0)
			{
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(0, len);
			}
			else
			{
				strTemplate = "";
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	* 获取指定合同的文本的JSP文件名
	* Create Date: 2003-10-15
	* @param  lContentID 文本ID
	* @param  lContractTypeID 文本类型ID
	* @return String   返回文本的JSP文件名
	* @exception Exception
	*/
	public String getJspName(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		try
		{
			strTemplate = getDocumentName(lContentID, lContractTypeID);
			int len = 0;
			if (strTemplate.length() > 0)
			{
				len = strTemplate.indexOf(";");
				strTemplate = strTemplate.substring(len + 1, strTemplate.length());
			}
			else
			{
				strTemplate = "";
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return strTemplate;
	}

	/**
	* 获取指定合同的合同模版和文本的JSP文件名
	* Create Date: 2003-10-15
	* @param  lContentID 文本ID
	* @param  lContractTypeID 文本类型ID
	* @return String   返回合同模版和文本的JSP文件名
	* @exception Exception
	*/
	public String getDocumentName(long lContentID, long lContractTypeID) throws Exception
	{
		String strTemplate = "";
		PreparedStatement ps = null;
		ResultSet rs1 = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();

		try
		{
			long lContractType = -1;
			if (lContractTypeID == LOANConstant.ContractType.ASSURE)
			{
				lContractType = LOANConstant.Template.HN_ASSURE;
			}
			else if (lContractTypeID == LOANConstant.ContractType.PLEDGE)
			{
				lContractType = LOANConstant.Template.HN_PLEDGE;
			}
			else if (lContractTypeID == LOANConstant.ContractType.IMPAWN)
			{
				lContractType = LOANConstant.Template.HN_IMPAWN;
			}
			else if (lContractTypeID == LOANConstant.ContractType.EXTEND)
			{
				OBContentInfo info = new OBContentInfo();
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

				while (rs1.next())
				{
					lLoanType = rs1.getLong("nTypeID"); //贷款类型ID
				}
				rs1.close();
				rs1 = null;
				ps.close();
				ps = null;
				sbSQL.setLength(0);

				if (lLoanType == LOANConstant.LoanType.WT)
				{
					lContractType = LOANConstant.Template.HN_EXTENDWT;
				}
				else
				{
					lContractType = LOANConstant.Template.HN_EXTENDZY;
				}

			}
			else if (lContractTypeID == LOANConstant.ContractType.TX)
			{
				lContractType = LOANConstant.Template.HN_TX;
			}
			else if (lContractTypeID == LOANConstant.ContractType.DKDCB)
			{
				lContractType = LOANConstant.Template.HN_DKDCB;
			}

			else if (lContractTypeID == LOANConstant.ContractType.LOAN) //借款合同
			{
				OBContentInfo info = new OBContentInfo();
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

				while (rs1.next())
				{
					lLoanType = rs1.getLong("nTypeID"); //贷款类型ID
				}

				if (lLoanType == LOANConstant.LoanType.ZY)
				{
					lContractType = LOANConstant.Template.HN_ZYDK;
				}
				if (lLoanType == LOANConstant.LoanType.WT)
				{
					lContractType = LOANConstant.Template.HN_WTDK;
				}
				if (lLoanType == LOANConstant.LoanType.TX)
				{
					lContractType = LOANConstant.Template.HN_TX;
				}
				if (lLoanType == LOANConstant.LoanType.ZGXE)
				{
					lContractType = LOANConstant.Template.HN_ZGXE;
				}
				if (lLoanType == LOANConstant.LoanType.YT)
				{
					lContractType = LOANConstant.Template.HN_YTDK;
				}
			}
			strTemplate = LOANConstant.Template.getName(lContractType);

			if (rs1 != null)
			{
				rs1.close();
				rs1 = null;
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
				if (rs1 != null)
				{
					rs1.close();
					rs1 = null;
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
		return strTemplate;
	}

	/**
	* 保存合同文本内容
	* Create Date: 2003-10-15
	* @param lID OBContentInfo合同文本内容(必须设置的内容有:ParentID,ContractID,ContractType,DocName)
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long saveContent(OBContentInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;
		long lContentID = 1;

		try
		{
			conn = Database.getConnection();
			sbSQL.append(" SELECT MAX(NVL(con.id,0)) as contentID");
			sbSQL.append(" FROM OB_ContractContent con");
			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				lContentID = rs.getLong("contentID");
			}

			lContentID = lContentID + 1;

			sbSQL.setLength(0);
			sbSQL.append(" INSERT INTO OB_ContractContent");
			sbSQL.append(" (ID,nParentID,nContractID,nContractTypeID,sDocName,sCode)");
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
			if (lResult > 0)
			{
				lResult = lContentID;
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
			if (conn != null)
			{
				conn.close();
				conn = null;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
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
	* 删除合同文本内容
	* Create Date: 2003-10-15
	* @param long lContractID合同ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteContent(long lContractID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = new StringBuffer();
		long lResult = -1;

		try
		{
			conn = Database.getConnection();

			sbSQL.append(" DELETE OB_ContractContent");
			sbSQL.append(" WHERE nContractID = ? ");

			log4j.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			lResult = ps.executeUpdate();

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
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
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
	* 填写文本缺省值  贷款调查表
	* Create Date: 2003-10-15
	* @param loanID
	* @return String   返回调查表文件名及路径
	* @exception Exception
	*/
	public String addLoanQuestionary(long loanID) throws Exception
	{
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
		int SECONDPAGECOUNT = 48;
		int THIRDPAGECOUNT = 26;
		int FORTHPAGECOUNT = 16;

		int i;

		try
		{
			conn = Database.getConnection();

			sb.append("select c.* from loan_loanForm l,client c");
			sb.append(" where c.id=l.nBorrowClientID and l.id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, loanID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				cInfo.setName(rs.getString("sName")); //  客户名称
				cInfo.setLegalPerson(rs.getString("sLegalPerson")); // 法人代表
				cInfo.setAddress(getAddress(rs.getString("sProvince"), rs.getString("sCity"), rs.getString("sAddress"))); //  地址
				cInfo.setPhone(rs.getString("sPhone")); // 电活
				cInfo.setParentCorpID(rs.getLong("NPARENTCORPID1")); //母公司编号
				//cInfo.setCorpNatureID(rs.getLong("NCORPNATUREID")); //企业性质
				cInfo.setBank1(rs.getString("sBank1")); //开户银行
				cInfo.setAccount(rs.getString("sAccount")); // 账号
			}

			cleanup(rs);
			cleanup(ps);
			sb.setLength(0);

			if (cInfo.getParentCorpID() > 0)
			{
				sb.append("select * from client where id=?");
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, cInfo.getParentCorpID());
				rs = ps.executeQuery();
				if (rs.next());
				cInfo.setParentCorpName(rs.getString("sName"));
				cleanup(rs);
				cleanup(ps);
			}

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			int index = 0;

			// 第1页
			saPageContent[index++] = (cInfo.getName() == null) ? "" : cInfo.getName();
			saPageContent[index++] = (cInfo.getLegalPerson() == null) ? "" : cInfo.getLegalPerson();
			saPageContent[index++] = (cInfo.getAddress() == null) ? "" : cInfo.getAddress();
			saPageContent[index++] = (cInfo.getPhone() == null) ? "" : cInfo.getPhone();
			saPageContent[index++] = (cInfo.getParentCorpName() == null) ? "" : cInfo.getParentCorpName();
			saPageContent[index++] = (LOANConstant.ClientCorpIndustry.getName(cInfo.getCorpNatureID()));
			saPageContent[index++] = (cInfo.getBank1() == null) ? "" : cInfo.getBank1();
			saPageContent[index++] = (cInfo.getAccount() == null) ? "" : cInfo.getAccount();
			while (index < FIRSTPAGECOUNT)
				saPageContent[index++] = "";

			System.out.println("**************第一页取值完成*************");

			for (i = 0; i < FIRSTPAGECOUNT; i++)
			{
				if (i == FIRSTPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 1, sContent);

			// 第2页,
			sContent = "";
			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < SECONDPAGECOUNT; i++)
			{
				if (i == SECONDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			//第3页
			sContent = "";
			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < THIRDPAGECOUNT; i++)
			{
				if (i == THIRDPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

			//第四页
			sContent = "";
			for (i = 0; i < FORTHPAGECOUNT; i++)
			{
				saPageContent[i] = "";
			}

			index = 0;
			for (i = 0; i < FORTHPAGECOUNT; i++)
			{
				if (i == FORTHPAGECOUNT - 1)
				{
					sContent += formatString(saPageContent[i]) + PAGE_SEPERATOR;
				}
				else
				{
					sContent += formatString(saPageContent[i]) + CONTENT_SEPERATOR;
				}
			}
			sFileName = saveContent(sFileName, 2, sContent);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.toString());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return sFileName;
	}

	/**
	* 合同的放款和还款计划
	* Create Date: 2003-12-20
	* @param Collection cPlan 合同
	* @return String   返回调查表文件名及路径
	* @exception Exception
	*/
	public String getPlanTab(Collection cPlan) throws Exception
	{
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
		try
		{

			iter = cPlan.iterator();
			for (int i = 0; iter.hasNext(); i++)
			{
				RepayPlanInfo rp_info = (RepayPlanInfo) iter.next();
				if (rp_info.nLoanOrRepay == LOANConstant.PlanType.PAY) //
				{
					if (rp_info.tsPlanDate != null)
					{ 
						strPayTime[iPay] = DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strPayTime[iPay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0)
					{
						strPayAmount[iPay] = DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strPayAmount[iPay] = "&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				}
				else
				{
					if (rp_info.tsPlanDate != null)
					{
						strRepayTime[iRepay] = DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strRepayTime[iRepay] = "&nbsp;";
					}
					if (rp_info.dAmount >= 0)
					{
						strRepayAmount[iRepay] = DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strRepayAmount[iRepay] = "&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}

			sTemp += " <b>附件：</b> <p>&nbsp;</p> \n";
			sTemp += " <p><h4 ALIGN=center><strong>提款、还款方式</strong></h4><br></p> \n";
			sTemp += " <table width=100% border=0 cellspacing=0 class=table1 > \n";
			sTemp += " <tr> \n";
			sTemp += " <td colspan=2 ALIGN=center width=50%  class=td-rightbottom> 提款方式 </td> \n";
			sTemp += " <td colspan=2 ALIGN=center width=50% class=td-rightbottom> 还款方式 </td> \n";
			sTemp += " </tr> \n";
			sTemp += " <tr align=center> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 提款日期 </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 提款金额 </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 还款日期 </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 还款金额 </td> \n";
			sTemp += " </tr> \n";

			int iCount = iRepay > iPay ? iRepay : iPay;

			for (int j = 0; j < iCount; j++)
			{
				sTemp += " <TR> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strPayTime[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strPayAmount[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strRepayTime[j]) + " </TD> \n";
				sTemp += " <TD class=td-rightbottom align=middle>&nbsp;" + DataFormat.formatString(strRepayAmount[j]) + " </TD> \n";
				sTemp += " </TR> \n";
			}
			sTemp += " <tr align=center> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 合计 </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;" + DataFormat.formatListAmount(dTotalPay) + " </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom> 合计 </td> \n";
			sTemp += " <td width=25%  class=td-rightbottom>&nbsp;" + DataFormat.formatListAmount(dTotalRepay) + " </td> \n";
			sTemp += " </tr> \n";
			sTemp += " </table> \n";

		}
		catch (RuntimeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sTemp;
	}

	public static void main(String[] args)
	{
		try
		{
			OBContentDao dao = new OBContentDao();

			String ss = dao.addJKZQXY(8);
			System.out.println("*************ss=" + ss);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}

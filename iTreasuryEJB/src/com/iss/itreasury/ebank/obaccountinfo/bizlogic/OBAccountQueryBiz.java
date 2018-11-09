package com.iss.itreasury.ebank.obaccountinfo.bizlogic;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceCurrentInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceFixedInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountBalanceLoanInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryInfo;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountResultInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo;
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao;
import com.iss.itreasury.ebank.obquery.bizlogic.OBQueryTransAccountBiz;
import com.iss.itreasury.ebank.obquery.dao.OBQueryTransAccountDao;

public class OBAccountQueryBiz {

	
	/**
	 * ��ѯ�����˻���Ϣ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryCurrentBalace(long lClientID, long lCurrencyID, long lOfficeID, long lUserID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			paramMap.put("lCurrencyID", lCurrencyID);
			paramMap.put("lOfficeID", lOfficeID);
			paramMap.put("lUserID", lUserID);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "currentBalaceResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList currentBalaceResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		
		long lClientID = Long.parseLong(map.get("lClientID").toString());
		long lCurrencyID = Long.parseLong(map.get("lCurrencyID").toString());
		long lOfficeID = Long.parseLong(map.get("lOfficeID").toString()); 
		long lUserID = Long.parseLong(map.get("lUserID").toString());
		
		Collection resultColl = dao.seekCurrentBalace2(lClientID, lCurrencyID, lOfficeID, lUserID);
		Iterator it = null;
		
		String sAcctType = "";
		String sAcctNo = "";
		String sAmount = "";
		String sLimited = "";
		//�����˻�
		double crt_dAc_mcapitallimitamount = 0.0;
		double crt_dMbalance = 0.0;
		long crt_lNaccounttypeid = -1;
		String crt_strSaccountno = "";
		double crt_dSubSum = 0.0;
		double crt_dSum = 0.0;
		double currentSum = 0.0; ///���ںϼ�
		
		try {
			
			//�˻�����
			if(resultColl != null)
			{
				it = resultColl.iterator();
			}

			while(it != null && it.hasNext())
			{
				//��ȡ����
				OBAccountBalanceCurrentInfo crt_info = (OBAccountBalanceCurrentInfo) it.next();
				crt_dAc_mcapitallimitamount = crt_info.getAc_mcapitallimitamount();
				crt_dMbalance = crt_info.getMbalance();
				crt_lNaccounttypeid = crt_info.getNaccounttypeid();
				crt_strSaccountno = crt_info.getSaccountno();
				crt_dSubSum = crt_info.getSubSum();
				crt_dSum = crt_info.getSum();

				sAcctType = SETTConstant.AccountType.getName(crt_lNaccounttypeid);
				
				if (crt_lNaccounttypeid == 0)
					sAcctType = "";
				//crt_strSaccountno -1 ��ʾ"С��"  -2 ��ʾ"����С��"
				if (crt_strSaccountno.equals("-1")) {
					sAcctNo = "С��";
				} else if (crt_strSaccountno.equals("-2")) {
					//sAcctNo="����С��";
					sAcctNo = "�ϼ�";
					currentSum = currentSum + crt_info.getMbalance();
				} else  {
					//�ж��Ƿ���"ס����������"��12
					sAcctNo=crt_strSaccountno;
				}
				//�ʽ����
				sAmount = DataFormat.formatListAmount(crt_dMbalance);
				if (sAmount.equals("&nbsp;"))
					sAmount = "0.00";
				//����������
				sLimited = DataFormat.formatListAmount(crt_dAc_mcapitallimitamount);
				if (sLimited.equals("&nbsp;"))
					sLimited = "0.00";
				if (crt_strSaccountno.equals("-1") || crt_strSaccountno.equals("-2"))
					sLimited = "";
				
				//�洢������
				cellList = new ArrayList();
				
				if (sAcctNo.equals("С��") || sAcctNo.equals("�ϼ�")) {
					
					PagerTools.returnCellList(cellList,(sAcctType == null ? "" : sAcctType));
					
					PagerTools.returnCellList(cellList,"<B>"+(sAcctNo == null ? "" : sAcctNo)+"</B>");
					
					PagerTools.returnCellList(cellList,"<B>"+(sAmount == null ? "" : sAmount)+"</B>");
					
					PagerTools.returnCellList(cellList,(sLimited == null ? "" : sLimited));
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
					
				} else {
					
					PagerTools.returnCellList(cellList,(sAcctType == null ? "" : sAcctType));
					
					PagerTools.returnCellList(cellList,"<B>"+(sAcctNo == null ? "" : sAcctNo)+"</B>"+","+crt_info.getAccountID());
					
					PagerTools.returnCellList(cellList,(sAmount == null ? "" : sAmount));
					
					PagerTools.returnCellList(cellList,(sLimited == null ? "" : sLimited));
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}

	/**
	 * ��ѯ�����˻���Ϣ��ϸ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryCurrentBalaceDetail(OBAccountQueryInfo info,OBAccountQueryInfo tempinfo) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("info", info);
			paramMap.put("tempinfo", tempinfo);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "currentBalaceDetailResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList currentBalaceDetailResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		String strEarlyBanlance = ""; //�ڳ����
		double mEarlyBanlance = 0.0;
		double mEveryBanlance = 0.0;//ÿ���Ժ�����
		String strExecuteDate = "";    //ִ����
		
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		OBAccountQueryInfo info1 = (OBAccountQueryInfo)map.get("info");
		OBAccountQueryInfo tempinfo = (OBAccountQueryInfo)map.get("tempinfo");
		strExecuteDate = Env.getSystemDateString(info1.getOfficeID(), info1.getCurrencyID());
		mEarlyBanlance = dao.findEarlyBalance(info1.getAccountID(),com.iss.itreasury.settlement.util.UtilOperation.getNextNDay(Env.getSystemDate(tempinfo.getOfficeID(),tempinfo.getCurrencyID()),-1));
		mEveryBanlance = mEarlyBanlance;
		strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
		
		Collection resultColl = dao.findByCurrentAccountID(info1);
		Iterator itResult = null;
		
		try {
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			
			//�洢������
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>�ڳ����</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<B>"+strEarlyBanlance+"</B>");
			//�洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			//��������
			resultList.add(rowInfo);
			
			if(itResult != null && itResult.hasNext())
			{
				double dbtAmountSum=0.0;   //�跽�ϼ�
				double cdtAmountSum=0.0;   //�����ϼ�
				
				String strDbtAmountSum = "";	 	
				String strCdtAmountSum = "";
				strEarlyBanlance = ""; //�ڳ����
				strExecuteDate = "";    //ִ����
				
				int i=0;
				
				while(itResult != null && itResult.hasNext())
				{
					i++;
					OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
					String strTransNo = "";   //���׺�
					String strTransactionType = "";  //��������	
					String strAbstract = "";	       //ժҪ
					String strOppAccountNo = "";   //�Է��˺�
					String strOppAccountName = "";   //�Է��˻�����
					String strBillNo = "";    //ƾ֤��/֧Ʊ��
					String strDebitAmount = "" ;         //�跽���
					String strCreditAmount = "" ;         //�������
					String strBanlance = "";  //���
					long lSerialNo=-1;
					lSerialNo = info.getSerialNo();
					strTransNo = DataFormat.formatEmptyString(info.getTransNo());
					strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
					strAbstract = DataFormat.formatString(info.getAbstract());
					strOppAccountNo = NameRef.getAccountNoByID(info.getOppAccountID());
					strOppAccountName = DataFormat.formatString(NameRef.getAccountNameByID(info.getOppAccountID()));			
					strBillNo = DataFormat.formatString(info.getBillNo());	
					if(info.getTransDirection()==SETTConstant.DebitOrCredit.DEBIT)
					{
						strDebitAmount=DataFormat.formatListAmount(info.getAmount());
						dbtAmountSum = dbtAmountSum+info.getAmount();
						mEveryBanlance = mEveryBanlance-info.getAmount();
					}
					if(info.getTransDirection()==SETTConstant.DebitOrCredit.CREDIT)
					{
						strCreditAmount=DataFormat.formatListAmount(info.getAmount());
						cdtAmountSum = cdtAmountSum+info.getAmount();
						mEveryBanlance = mEveryBanlance+info.getAmount();
					}
					strBanlance = DataFormat.formatListAmount(mEveryBanlance);	
					strEarlyBanlance = DataFormat.formatListAmount(info.getEarlyBanlance());
					strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));			
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,strTransNo);
					PagerTools.returnCellList(cellList,strTransactionType);
					PagerTools.returnCellList(cellList,strAbstract);
					PagerTools.returnCellList(cellList,strOppAccountNo);
					PagerTools.returnCellList(cellList,strOppAccountName);
					PagerTools.returnCellList(cellList,strBillNo);
					PagerTools.returnCellList(cellList,strDebitAmount);
					PagerTools.returnCellList(cellList,strCreditAmount);
					PagerTools.returnCellList(cellList,strBanlance);
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
					
				}
				double banlance = 0.0;     //���
				banlance = (mEarlyBanlance - dbtAmountSum + cdtAmountSum);
				strDbtAmountSum=DataFormat.formatListAmount(dbtAmountSum);			
				strCdtAmountSum=DataFormat.formatListAmount(cdtAmountSum);
				String strBan = DataFormat.formatListAmount(banlance);	
				
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>���պϼ�</B>");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>"+strDbtAmountSum+"</B>");
				PagerTools.returnCellList(cellList,"<B>"+strCdtAmountSum+"</B>");
				PagerTools.returnCellList(cellList,"<B>"+strBan+"</B>");
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryFixedBalace(long lClientID, long lCurrencyID, long lOfficeID, long lStatusID, long lUserID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			paramMap.put("lCurrencyID", lCurrencyID);
			paramMap.put("lOfficeID", lOfficeID);
			paramMap.put("lUserID", lUserID);
			paramMap.put("lStatusID", lStatusID);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "fixedBalaceResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList fixedBalaceResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		
		long lClientID = Long.parseLong(map.get("lClientID").toString());
		long lCurrencyID = Long.parseLong(map.get("lCurrencyID").toString());
		long lOfficeID = Long.parseLong(map.get("lOfficeID").toString()); 
		long lUserID = Long.parseLong(map.get("lUserID").toString());
		long lStatusID = Long.parseLong(map.get("lStatusID").toString());
		
		Collection resultColl = dao.seekFixedBalace(lClientID, lCurrencyID, lOfficeID, lStatusID, lUserID);
		Iterator it = null;
		
		//�˻�����
		String sAcctType = "";
		String sAcctNo = "";
		String sAmount = "";
		String sLimited = "";
		//�����
		String sFixedAmount = "";
		//������
		String sFixedBalance = "";
		//������
		String sTerm = "";
		//����
		String sRate = "";
		//��ע
		String sTatus = "";
		//�����˻�
		java.sql.Timestamp fixed_tsAf_dtend = null;
		java.sql.Timestamp fixed_tsAf_dtstart = null;
		double fixed_dAf_mrate = 0.0;
		long fixed_lAf_ndepositterm = -1;
		double fixed_dMbalance = 0.0;
		double fixed_dMopenamount = 0.0;
		long fixed_lNaccounttypeid = -1;
		long fixed_lNstatusid = -1;
		String fixed_strSaccountno = "";
		double fixed_dSubSum = 0.0;
		double fixed_dSum = 0.0;
		long fixed_lNaccountID = -1;
		long fixed_lNtype = -1;
		//�������  ��Ӧ��̨��DataEntity
		double currentSum = 0.0; ///���ںϼ�
		double depositSum = 0.0; //������ϼ�
		double loanSum = 0.0; //������ϼ�
		
		try {
			
			//�˻�����
			if(resultColl != null)
			{
				it = resultColl.iterator();
			}
			int j=0;
			while(it != null && it.hasNext())
			{
				j++;
				OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it.next();
				fixed_tsAf_dtend = fixed_info.getAf_dtend();
				fixed_tsAf_dtstart = fixed_info.getAf_dtstart();
				fixed_dAf_mrate = fixed_info.getAf_mrate();
				fixed_lAf_ndepositterm = fixed_info.getAf_ndepositterm();
				fixed_dMbalance = fixed_info.getMbalance();
				fixed_dMopenamount = fixed_info.getMopenamount();
				fixed_lNaccounttypeid = fixed_info.getNaccounttypeid();
				fixed_lNstatusid = fixed_info.getNstatusid();
				fixed_strSaccountno = fixed_info.getSaccountno();
				fixed_dSubSum = fixed_info.getSubsum();
				fixed_dSum = fixed_info.getSum();
				fixed_lNtype = fixed_info.getNtype();
				System.out.println("fixed_info.getNtype()��ֵ��:" + fixed_info.getNtype());
				//�˻�����
				sAcctType = SETTConstant.AccountType.getName(fixed_lNaccounttypeid);
				if (fixed_lNaccounttypeid == 0 || fixed_lNaccounttypeid == -1)
					sAcctType = "";

				//�˺�
				sAcctNo = fixed_strSaccountno;
				if (fixed_strSaccountno.equals("-2")) {
					sAcctNo = "С��";
				}
				else {
					//ȥ���˻��еġ�����
					fixed_strSaccountno=fixed_strSaccountno;
				}
				//�����
				if (fixed_dMopenamount == -1)
					sFixedAmount = "";
				else
					sFixedAmount = DataFormat.formatListAmount(fixed_dMopenamount);
				//������
				if (fixed_dMbalance == -1)
					sFixedBalance = "";
				else
					sFixedBalance = DataFormat.formatListAmount(fixed_dMbalance);
				if (sFixedBalance.equals("&nbsp;") && fixed_tsAf_dtstart != null)
					sFixedBalance = "0.00";
				//����
				if (fixed_lAf_ndepositterm == -1 || fixed_lAf_ndepositterm == -2) {
					sTerm = "";
				} else if (fixed_lNtype == 6) {
					fixed_lAf_ndepositterm = fixed_lAf_ndepositterm - 10000;
					sTerm = fixed_lAf_ndepositterm + "��";
				} else if (fixed_lNtype == 5) {
					sTerm = fixed_lAf_ndepositterm + "����";
				}

				//����
				if (fixed_dAf_mrate == -1) {
					sRate = "";
				} else {
					if (fixed_dAf_mrate > 0) {
						sRate = DataFormat.formatListAmount(fixed_dAf_mrate) + "%";
					} else {
						sRate = DataFormat.formatListAmount(fixed_dAf_mrate);
					}
				}
				if (fixed_dAf_mrate == -2) {
					sRate = "<B>С��</B>";
					depositSum = depositSum + fixed_dMbalance;
				}
				if (fixed_lNstatusid == -1) {
					sTatus = "";
				} else {
					if (SETTConstant.AccountType.isFixAccountType(fixed_lNtype)) {
						if (fixed_lNstatusid == SETTConstant.SubAccountStatus.FINISH) {
							sTatus = SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
						} else {
							if(fixed_info.getAf_dtend()!=null)
							{
								if (fixed_info.getAf_dtend().after(Env.getSystemDate(lOfficeID, lCurrencyID))) {
									sTatus = "δ����";
								} else {
									sTatus = "�ѵ���";
								}
							}
						}
					} else {
						sTatus = SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
					}
				}
				
				//�洢������
				cellList = new ArrayList();
				
				PagerTools.returnCellList(cellList,"<B>"+(sAcctType == null ? "" : sAcctType)+"</B>");
				
				if (fixed_tsAf_dtstart != null){
					PagerTools.returnCellList(cellList,"<B>"+(fixed_strSaccountno.equals("0") ? "" : fixed_strSaccountno)+"</B>"+","+fixed_lNtype+","+fixed_strSaccountno+","+fixed_info.getSubAccountID()+","+fixed_info.getAccountID());
				} else {
					PagerTools.returnCellList(cellList,"<B>"+(fixed_strSaccountno.equals("0") ? "" : fixed_strSaccountno)+"</B>");
				}
				
				PagerTools.returnCellList(cellList,(fixed_tsAf_dtstart == null ? "" : DataFormat.formatDate(fixed_tsAf_dtstart)));
				
				PagerTools.returnCellList(cellList,((fixed_tsAf_dtend == null||(SETTConstant.AccountType.isNotifyAccountType(fixed_lNtype)))? "" : DataFormat.formatDate(fixed_tsAf_dtend)));
				
				PagerTools.returnCellList(cellList,sTerm);
				
				PagerTools.returnCellList(cellList,sRate);
				
				if("<B>С��</B>".equals(sRate)){
					
					PagerTools.returnCellList(cellList,"<B>"+sFixedAmount+"</B>");
					
					PagerTools.returnCellList(cellList,"<B>"+sFixedBalance+"</B>");
					
				}else{
					
					PagerTools.returnCellList(cellList,sFixedAmount);
					
					PagerTools.returnCellList(cellList,sFixedBalance);
					
				}
				
				
				PagerTools.returnCellList(cellList,sTatus);
				
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
				
			}
			
			//�洢������
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<strong>�������ܼƣ�</strong>");
			PagerTools.returnCellList(cellList,"<B>"+DataFormat.formatListAmount(depositSum)+"</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			//�洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			//��������
			resultList.add(rowInfo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ��ϸ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryFixedBalaceDetail(OBAccountQueryInfo info) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("info", info);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "fixedBalaceDetailResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList fixedBalaceDetailResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		OBAccountQueryInfo info1 = (OBAccountQueryInfo)map.get("info");
		
		Collection resultColl = dao.findByFixedAccountID(info1);
		Iterator itResult = null;
		
		try {
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			
			if(itResult != null && itResult.hasNext())
			{
				String strDepositNo="";
				
				int i=0;
				
				while(itResult != null && itResult.hasNext())
				{
					i++;
					OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
					
					String strTransNo = "";   //���׺�
					String strTransactionType = "";  //��������	
					strDepositNo = "";	       //���ݺ�
					String strAmount = "" ;         //���
					String strExecuteDate = "";    //������
					
					strTransNo = DataFormat.formatEmptyString(info.getTransNo());
					strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
					strDepositNo = DataFormat.formatString(info.getDepositNo());
					strAmount=DataFormat.formatListAmount(info.getAmount());
					strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));		
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,strTransNo);
					PagerTools.returnCellList(cellList,strTransactionType);
					PagerTools.returnCellList(cellList,strDepositNo);
					PagerTools.returnCellList(cellList,strExecuteDate);
					PagerTools.returnCellList(cellList,strAmount);
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ��ϸ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryNoticeBalaceDetail(OBAccountQueryInfo info) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("info", info);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "noticeBalaceDetailResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList noticeBalaceDetailResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		OBAccountQueryInfo info1 = (OBAccountQueryInfo)map.get("info");
		
		Collection resultColl = dao.findByFixedAccountID(info1);
		Iterator itResult = null;
		
		try {
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			
			if(itResult != null && itResult.hasNext())
			{
				String strDepositNo="";
				
				int i=0;
				
				while(itResult != null && itResult.hasNext())
				{
					i++;
					OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
					
					String strTransNo = "";   //���׺�
					String strTransactionType = "";  //��������	
					strDepositNo = "";	       //���ݺ�
					String strAmount = "" ;         //���
					String strExecuteDate = "";    //������
					String strRate = "";       //����
					String strInterest = "";    //��Ϣ
					
					strTransNo = DataFormat.formatEmptyString(info.getTransNo());
					strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
					strDepositNo = DataFormat.formatString(info.getDepositNo());
					strAmount=DataFormat.formatListAmount(info.getAmount());
					strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));		
					
					if(info.getTransactionTypeID()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						strInterest = DataFormat.formatListAmount(info.getInterest());			
						strRate =  DataFormat.formatRate(info.getRate())+"%";	
					}
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,strTransNo);
					PagerTools.returnCellList(cellList,strTransactionType);
					PagerTools.returnCellList(cellList,strDepositNo);
					PagerTools.returnCellList(cellList,strExecuteDate);
					PagerTools.returnCellList(cellList,strAmount);
					PagerTools.returnCellList(cellList,strRate);
					PagerTools.returnCellList(cellList,strInterest);
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryLoanBalace(long lClientID, long lCurrencyID, long lOfficeID, long lUserID) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("lClientID", lClientID);
			paramMap.put("lCurrencyID", lCurrencyID);
			paramMap.put("lOfficeID", lOfficeID);
			paramMap.put("lUserID", lUserID);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "loanBalaceResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList loanBalaceResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		
		long lClientID = Long.parseLong(map.get("lClientID").toString());
		long lCurrencyID = Long.parseLong(map.get("lCurrencyID").toString());
		long lOfficeID = Long.parseLong(map.get("lOfficeID").toString()); 
		long lUserID = Long.parseLong(map.get("lUserID").toString());
		
		Collection resultColl = dao.seekLoanBalace(lClientID, lCurrencyID, lOfficeID, lUserID);
		Iterator it = null;
		
		String sAcctType = "";
		String sAcctNo = "";
		String sAmount = "";
		String sLimited = "";
		//�����˻�
		//������
		String sLoanAmount = "";
		//�������
		String sLoanBalance = "";
		//��λ
		String sBrwClient = "";
		//�����˻�
		java.sql.Timestamp loan_tsDtEndDate = null;
		java.sql.Timestamp loan_tsDtStartDate = null;
		double loan_dLoanBalance = 0.0;
		double loan_dMAmount = 0.0;
		long loan_lNaccounttypeid = -1;
		long loan_lNborrowclientid = -1;
		long loan_lNIntervalNum = -1;
		long loan_lNstatusid = -1;
		double loan_dRate = 0.0;
		String loan_strSaccountno = "";
		String loan_strSCONTRACTCODE = "";
		double loan_dSubSum = 0.0;
		//�����
		String sFixedAmount = "";
		//������
		String sFixedBalance = "";
		//��ͬ״̬
		String sContractStatus = "";
		//������
		String sTerm = "";
		//����
		String sRate = "";
		//��ע
		String sTatus = "";
		//�������  ��Ӧ��̨��DataEntity
		double currentSum = 0.0; ///���ںϼ�
		double depositSum = 0.0; //������ϼ�
		double loanSum = 0.0; //������ϼ�
		
		try {
			
			//�˻�����
			if(resultColl != null)
			{
				it = resultColl.iterator();
			}

			while(it != null && it.hasNext())
			{
				//��ȡ����
				OBAccountBalanceLoanInfo loan_info = (OBAccountBalanceLoanInfo) it.next();
				loan_tsDtEndDate = loan_info.getDtEndDate();
				loan_tsDtStartDate = loan_info.getDtStartDate();
				loan_dLoanBalance = loan_info.getLoanBalance();
				loan_dMAmount = loan_info.getMAmount();
				loan_lNaccounttypeid = loan_info.getNaccounttypeid();
				loan_lNborrowclientid = loan_info.getNborrowclientid();
				loan_lNIntervalNum = loan_info.getNIntervalNum();
				loan_lNstatusid = loan_info.getNstatusid();
				loan_dRate = loan_info.getRate();
				loan_strSaccountno = loan_info.getSaccountno();
				loan_strSCONTRACTCODE = loan_info.getSCONTRACTCODE();
				loan_dSubSum = loan_info.getSubSum();
				
				//�˻�����
				sAcctType = SETTConstant.AccountType.getName(loan_lNaccounttypeid);
				if (loan_lNaccounttypeid == 0 || loan_lNaccounttypeid == -1)
					sAcctType = "";
				//��λ
				if (loan_lNborrowclientid == -1)
					sBrwClient = "";
				else
					sBrwClient = NameRef.getClientNameByID(loan_lNborrowclientid);

				//������
				if (loan_dMAmount == -1)
					sLoanAmount = "";
				else
					sLoanAmount = DataFormat.formatListAmount(loan_dMAmount);
				//�������
				if (loan_dLoanBalance == -1)
					sLoanBalance = "";
				else
					sLoanBalance = DataFormat.formatListAmount(loan_dLoanBalance);
				if (sLoanBalance.equals("&nbsp;") && loan_tsDtStartDate != null && loan_tsDtEndDate != null)
					sLoanBalance = "0.00";
				//����
				if (loan_lNIntervalNum == -1)
					sTerm = "";
				else if (loan_lNIntervalNum == -2) {
					sTerm = "<B>С��</B>";
					loanSum = loanSum + loan_dLoanBalance;
				} else
					sTerm = loan_lNIntervalNum + "����";

				if (sTerm.equals("<B>С��</B>") && !loan_strSaccountno.equals(""))
					sTerm = "";
				//����
				if (loan_dRate == -1 || loan_tsDtStartDate == null || loan_tsDtEndDate == null)
					sRate = "";
				else
					sRate = DataFormat.formatRate(loan_dRate) + "%";
				if (sRate.equals("&nbsp;%"))
					sRate = "0.00%";

				//��ͬ״̬  
				if (loan_lNstatusid == -1)
					sContractStatus = "";
				else
					sContractStatus = "" + LOANConstant.ContractStatus.getName(loan_lNstatusid);
				
				//�洢������
				cellList = new ArrayList();
				
				PagerTools.returnCellList(cellList,"<B>"+sAcctType+"</B>");
				
				if (loan_tsDtStartDate != null && loan_tsDtEndDate != null) {
					PagerTools.returnCellList(cellList,"<B>"+(loan_strSaccountno == null ? "" : loan_strSaccountno)+"</B>"+","+loan_info.getAccountID()+","+loan_info.getContractID());
				} else {
					PagerTools.returnCellList(cellList,"<B>"+(loan_strSaccountno == null ? "" : loan_strSaccountno)+"</B>");
				}
				
				PagerTools.returnCellList(cellList,sBrwClient);
				
				PagerTools.returnCellList(cellList,(loan_tsDtStartDate == null ? "" : DataFormat.formatDate(loan_tsDtStartDate)));
				
				PagerTools.returnCellList(cellList,(loan_tsDtEndDate == null ? "" : DataFormat.formatDate(loan_tsDtEndDate)));
				
				PagerTools.returnCellList(cellList,sTerm);
				
				if("<B>С��</B>".equals(sTerm)){
					
					PagerTools.returnCellList(cellList,"<B>"+sLoanAmount+"</B>");
					
					PagerTools.returnCellList(cellList,"<B>"+sLoanBalance+"</B>");
					
				}else{
					
					PagerTools.returnCellList(cellList,sLoanAmount);
					
					PagerTools.returnCellList(cellList,sLoanBalance);
					
				}
				
				PagerTools.returnCellList(cellList,sRate);
				
				PagerTools.returnCellList(cellList,sContractStatus);
				
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
			}
			
			//�洢������
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"<strong>��������ܼƣ�</strong>");
			PagerTools.returnCellList(cellList,"<B>"+DataFormat.formatListAmount(loanSum)+"</B>");
			PagerTools.returnCellList(cellList,"&nbsp;");
			PagerTools.returnCellList(cellList,"&nbsp;");
			//�洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			//��������
			resultList.add(rowInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ��ϸ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryLoanBalaceDetail(OBAccountQueryInfo info) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			Map paramMap = new HashMap();
			paramMap.put("info", info);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "loanBalaceDetailResultSetHandle", paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList loanBalaceDetailResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		String strEarlyBanlance = ""; //�ڳ����
		double mEarlyBanlance = 0.0;
		double mEveryBanlance = 0.0;//ÿ���Ժ�����
		String strExecuteDate = "";    //ִ����
		
		OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
		OBAccountQueryInfo info1 = (OBAccountQueryInfo)map.get("info");
		//OBAccountQueryInfo tempinfo = (OBAccountQueryInfo)map.get("tempinfo");
		//strExecuteDate = Env.getSystemDateString(info1.getOfficeID(), info1.getCurrencyID());
		//mEarlyBanlance = dao.findEarlyBalance(info1.getAccountID(),com.iss.itreasury.settlement.util.UtilOperation.getNextNDay(Env.getSystemDate(tempinfo.getOfficeID(),tempinfo.getCurrencyID()),-1));
		mEveryBanlance = mEarlyBanlance;
		strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
		
		Collection resultColl = dao.findByLoanAccountID(info1);
		Iterator itResult = null;
		
		try {
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			
			if(itResult != null && itResult.hasNext())
			{
				double dbtAmountSum=0.0;   //�跽�ϼ�
				double cdtAmountSum=0.0;   //�����ϼ�
				String strDbtAmountSum = "";	 	
				String strCdtAmountSum = "";
				//strEarlyBanlance = ""; //�ڳ����
				strExecuteDate = "";    //ִ����
				
				while(itResult.hasNext())
				{
					OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
					
					String strTransNo = "";   //���׺�
					String strTransactionType = "";  //��������	
					String strAbstract = "";	       //ժҪ
					String strPayFormNo = "";   //�ſ��

					String strDebitAmount = "" ;         //�跽���
					String strCreditAmount = "" ;         //�������
					
					String strBanlance = "";  //���
					String strInterst = "";       //��Ϣ
					
					
					strTransNo = DataFormat.formatEmptyString(info.getTransNo());
					strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
					
					strAbstract = DataFormat.formatString(info.getAbstract());
					strPayFormNo = DataFormat.formatString(NameRef.getPayFormNoByID(info.getPayFormID()));			
					if(info.getTransDirection()==SETTConstant.DebitOrCredit.DEBIT)
					{
						strDebitAmount=DataFormat.formatListAmount(info.getAmount());
						dbtAmountSum = dbtAmountSum+info.getAmount();
					}
					if(info.getTransDirection()==SETTConstant.DebitOrCredit.CREDIT)
					{
						strCreditAmount=DataFormat.formatListAmount(info.getAmount());
						cdtAmountSum = cdtAmountSum+info.getAmount();
					}
					strBanlance = DataFormat.formatListAmount(info.getBalance());	
					strInterst = DataFormat.formatListAmount(info.getInterest());				
					//strEarlyBanlance = DataFormat.formatListAmount(info.getEarlyBanlance());
					strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));			
					
					String strURL = "/accountinfo/querycontrol.jsp?TransactionTypeID="+info.getTransactionTypeID()+"&TransNo="+strTransNo+"";	
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,strTransNo+","+info.getTransactionTypeID()+","+strTransNo);
					PagerTools.returnCellList(cellList,strTransactionType);
					PagerTools.returnCellList(cellList,strPayFormNo);
					PagerTools.returnCellList(cellList,strAbstract);
					PagerTools.returnCellList(cellList,strDebitAmount);
					PagerTools.returnCellList(cellList,strCreditAmount);
					PagerTools.returnCellList(cellList,strInterst);
					PagerTools.returnCellList(cellList,strBanlance);
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					//��������
					resultList.add(rowInfo);
					
				}
				strDbtAmountSum=DataFormat.formatListAmount(dbtAmountSum);			
				strCdtAmountSum=DataFormat.formatListAmount(cdtAmountSum);
				
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,strExecuteDate);
				PagerTools.returnCellList(cellList,"�ڳ����");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,strEarlyBanlance);
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
				
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"���պϼ�");
				PagerTools.returnCellList(cellList,strDbtAmountSum);
				PagerTools.returnCellList(cellList,strCdtAmountSum);
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
				
			}else{
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,strExecuteDate);
				PagerTools.returnCellList(cellList,"�ڳ����");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,strEarlyBanlance);
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
				
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
				//��������
				resultList.add(rowInfo);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ��ѯ�����˻���Ϣ��ϸ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryOBAccountQueryAmountInfo(Map map) throws Exception{

		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = "select 1 from userinfo";
			pagerInfo.setSqlString(sql);
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "oBAccountQueryAmountInfoResultSetHandle", map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	
	}
	
	public ArrayList oBAccountQueryAmountInfoResultSetHandle(ResultSet rs, Map map) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		String strEarlyBanlance = ""; //�ڳ����
		double mEarlyBanlance = 0.0;
		double mEveryBanlance = 0.0;//ÿ���Ժ�����
		String strExecuteDate = "";    //ִ����
		Connection conn = null;
	    OBAccountQueryAmountDao obaqad = new OBAccountQueryAmountDao(conn);
	    String fromStr = (String)map.get("fromAccountType");
	    OBAccountQueryWhere info = (OBAccountQueryWhere)map.get("info");
		long lAccountGroupID = Long.valueOf(map.get("lAccountGroupID").toString());
		long lOfficeID = Long.valueOf(map.get("lOfficeID").toString());
		long lCurrencyID = Long.valueOf(map.get("lCurrencyID").toString());
		long lParentCorpID = Long.valueOf(map.get("lParentCorpID").toString());
		long lClientID = Long.valueOf(map.get("lClientID").toString());
		long lAccountID = Long.valueOf(map.get("lAccountID").toString());
		try {
			if (fromStr != null && !fromStr.equals("yes")) {
			    int iItem = 0;
			    double Balance = 0.0;
			    double SubSumBalance = 0.0;
			    double SumBalance = 0.0;
			    double Total = 0.0;
			    Collection clientList = null;
			    Iterator iterList = null;
			    Iterator iterClient = null;
			    Iterator iterAccount = null;
			    boolean bLoop = false;
			    boolean  isNotnull = false;
			    
			    //modified by xfma 2009-02-18 
			    if(lAccountGroupID == -1){
			        bLoop = true;
			    }
			    long[] allCode=SETTConstant.AccountGroupType.getAllCode(lOfficeID,lCurrencyID);
				if(allCode!=null){
				    for(int i=1; i<=(bLoop?allCode.length:1); i++) 
				    {
				        Collection list = new ArrayList();
				        if(bLoop) lAccountGroupID = (long) i;
				        if (lClientID == -1) {
				            if (lAccountID == -1) {
				                clientList = obaqad.getChildClientID(info,lParentCorpID,lOfficeID,true);
				                if (clientList != null) {
				                    iterClient = clientList.iterator();
				                }
				                boolean self=false;
				                while (iterClient != null && iterClient.hasNext()) {
				                    OBAccountQueryAmountInfo infoClient = (OBAccountQueryAmountInfo) iterClient.next();
				                    if(lParentCorpID==infoClient.getClientID()) self=true;
				                    Collection accountList = obaqad.getAccountInfoByClientID(info, lAccountGroupID, lParentCorpID,lParentCorpID,self);
				                    if (accountList != null) {
				                        iterAccount = accountList.iterator();
				                        while (iterAccount != null && iterAccount.hasNext()) {
				                            OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
				                            list.add(infoAccount);
				                        }
				                    }
				                    self=false;
				                }
				                list = obaqad.compositByAccountTypeID(list);
				                list = obaqad.compositByLoanClientTypeID(list);
				            } else {
				           		Collection accountList = obaqad.getAccountInfoByAccountID(info, lAccountGroupID, lAccountID);
				                if (accountList != null) {
				                    iterAccount = accountList.iterator();
				                    while (iterAccount != null && iterAccount.hasNext()) {
				                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
				                        list.add(infoAccount);
				                    }
				                }
				            }
				        } else {
				         	boolean self=false;
				             	if(lParentCorpID==lClientID) self=true;
				            if (lAccountID == -1) {
				                Collection accountList = obaqad.getAccountInfoByClientID(info, lAccountGroupID, lClientID,lParentCorpID,self);
				                if (accountList != null) {
				                    iterAccount = accountList.iterator();
				                    while (iterAccount != null && iterAccount.hasNext()) {
				                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
				                        list.add(infoAccount);
				                     self=false;
				                    }
				                }
				                list = obaqad.compositByAccountTypeID(list);
				                list = obaqad.compositByLoanClientTypeID(list);
				            } else {
				                Collection accountList = obaqad.getAccountInfoByAccountID(info, lAccountGroupID, lAccountID);
				                if (accountList != null) {
				                    iterAccount = accountList.iterator();
				                    while (iterAccount != null && iterAccount.hasNext()) {
				                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
				                        list.add(infoAccount);
				                    }
				                }
				            }
				        }
				        
				        if(list!=null && list.size()>0)
				        {
					        isNotnull = true;
					             
					        long lLoanClientTypeID = 0;
					        long lAccountTypeID = 0;
					        String strLoanClientTypeName = "";
					        Balance = 0.0;
					        SubSumBalance = 0.0;
					        Total = 0.0;
					        if (list != null) {
					            iterList = list.iterator();
					        }
					        int k=0;
					        while (iterList != null && iterList.hasNext()) {
					        	k++;
					        	OBAccountQueryAmountInfo info2 = (OBAccountQueryAmountInfo) iterList.next();
				           
					        	String strSefcBankAccountNo = NameRef.getBankAccountNOByCurrenctAccountID(info2.getOpenAccountNo());
					        	// liuguang 2007-11-13 �ж��Ǵ����˻������Ǵ���˻�
					        	if(SETTConstant.AccountType.isLoanAccountType(info2.getAccountTypeID())){
					        		Balance = obaqad.getBalanceByAccountID(info, info2.getOpenAccountID(), lAccountGroupID) - obaqad.getAmountByAccountID(info, info2.getOpenAccountID(), lAccountGroupID);  
					        	}else{
					        		Balance = obaqad.getBalanceByAccountID(info, info2.getOpenAccountID(), lAccountGroupID) + obaqad.getAmountByAccountID(info, info2.getOpenAccountID(), lAccountGroupID);
					        	}
					        	if (lAccountTypeID == 0 || lAccountTypeID != info2.getAccountTypeID()) {
					        		lAccountTypeID = info2.getAccountTypeID();
					        		if(lLoanClientTypeID != 0) {
					        			//�洢������
					    				cellList = new ArrayList();
					    				PagerTools.returnCellList(cellList,"&nbsp;");
					        			PagerTools.returnCellList(cellList,strLoanClientTypeName==null?"&nbsp;":strLoanClientTypeName);
					        			PagerTools.returnCellList(cellList,SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2));
					        			PagerTools.returnCellList(cellList,"&nbsp;");
					        			PagerTools.returnCellList(cellList,"&nbsp;");
					    				//�洢������
					    				rowInfo = new ResultPagerRowInfo();
					    				rowInfo.setCell(cellList);
					    				//��������
					    				resultList.add(rowInfo);
					        			
					        			SubSumBalance = 0.0;
					        		}
					        		

				        			//�洢������
				    				cellList = new ArrayList();
				    				PagerTools.returnCellList(cellList,"<B>"+SETTConstant.AccountType.getName(lAccountTypeID)+"</B>");
				        			PagerTools.returnCellList(cellList,"<B>�ܼ�</B>");
//					                <td  valign="top" align="right"   height="18">
	//				                    <span id="sum<%=++iItem%>"></span>
	//				                    <%
	//				                		if (iItem > 1) {
	//				                    %>
	//				                    		<script language="JavaScript">
	//				                        		sum<%=iItem-1%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
	//				                    		</script>
	//				                    <%
	//				                		}		
	//				                    %>
//				                	</td>
				        			PagerTools.returnCellList(cellList,"<B>"+obaqad.formatAmount(SumBalance)+"</B>");
				        			PagerTools.returnCellList(cellList,"&nbsp;");
				        			PagerTools.returnCellList(cellList,"&nbsp;");
				    				//�洢������
				    				rowInfo = new ResultPagerRowInfo();
				    				rowInfo.setCell(cellList);
				    				//��������
				    				resultList.add(rowInfo);
				    				
				        			SubSumBalance = 0.0;
				        		
				        			//�洢������
				    				cellList = new ArrayList();
				    				PagerTools.returnCellList(cellList,"&nbsp;");
				        			PagerTools.returnCellList(cellList,info2.getOpenAccountName());
				        			PagerTools.returnCellList(cellList,Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2));
				        			PagerTools.returnCellList(cellList,"����˾");
				        			PagerTools.returnCellList(cellList,NameRef.getNoLineAccountNo(info2.getOpenAccountNo())+","
				        					+lCurrencyID+","
				        					+lAccountGroupID+","
				        					+lAccountTypeID+","
				        					+info2.getOpenAccountID()+","
				        					+info2.getOpenAccountNo()+","
				        					+DataFormat.getDateString(info.getDateTo()));
				    				//�洢������
				    				rowInfo = new ResultPagerRowInfo();
				    				rowInfo.setCell(cellList);
				    				//��������
				    				resultList.add(rowInfo);

				    				lLoanClientTypeID = info2.getLoanClientTypeID();
				    				strLoanClientTypeName = info2.getLoanClientTypeName();
				    				SumBalance = 0.0;
				    				
					        	} else {
					        		if(lLoanClientTypeID != 0 && lLoanClientTypeID != info2.getLoanClientTypeID()) {
					        			
					        			//�洢������
					    				cellList = new ArrayList();
					    				PagerTools.returnCellList(cellList,"&nbsp;");
					        			PagerTools.returnCellList(cellList,strLoanClientTypeName);
					        			PagerTools.returnCellList(cellList,SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2));
					        			PagerTools.returnCellList(cellList,"&nbsp;");
					        			PagerTools.returnCellList(cellList,"&nbsp;");
					    				//�洢������
					    				rowInfo = new ResultPagerRowInfo();
					    				rowInfo.setCell(cellList);
					    				//��������
					    				resultList.add(rowInfo);
				                
					    				SubSumBalance = 0.0;
					        		}
					        		
					        		//�洢������
				    				cellList = new ArrayList();
				    				PagerTools.returnCellList(cellList,"&nbsp;");
				        			PagerTools.returnCellList(cellList,info2.getOpenAccountName());
				        			PagerTools.returnCellList(cellList,Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2));
				        			PagerTools.returnCellList(cellList,"����˾");
				        			PagerTools.returnCellList(cellList,NameRef.getNoLineAccountNo(info2.getOpenAccountNo())+","
				        					+lCurrencyID+","
				        					+lAccountGroupID+","
				        					+lAccountTypeID+","
				        					+info2.getOpenAccountID()+","
				        					+info2.getOpenAccountNo()+","
				        					+DataFormat.getDateString(info.getDateTo()));
				    				//�洢������
				    				rowInfo = new ResultPagerRowInfo();
				    				rowInfo.setCell(cellList);
				    				//��������
				    				resultList.add(rowInfo);
				 
				    				lLoanClientTypeID = info2.getLoanClientTypeID();
				    				strLoanClientTypeName = info2.getLoanClientTypeName();
					        	
					        	}
					        	
					        	SubSumBalance += Balance;
					        	SumBalance += Balance;
					        	Total += Balance;
					        	
				                SubSumBalance = 0.0;
					        }
					        
					      //�洢������
		    				cellList = new ArrayList();
		    				PagerTools.returnCellList(cellList,"<B>"+SETTConstant.AccountGroupType.getName(lAccountGroupID)+"�ܼƣ�</B>");
		        			PagerTools.returnCellList(cellList,"<B>"+(Total==0.0?"0.00":DataFormat.formatDisabledAmount(Total,2))+"</B>");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		    				//�洢������
		    				rowInfo = new ResultPagerRowInfo();
		    				rowInfo.setCell(cellList);
		    				//��������
		    				resultList.add(rowInfo);
		    				

		        			//�洢������
		    				cellList = new ArrayList();
		    				PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		    				//�洢������
		    				rowInfo = new ResultPagerRowInfo();
		    				rowInfo.setCell(cellList);
		    				//��������
		    				resultList.add(rowInfo);
		    				
		    				//�洢������
		    				cellList = new ArrayList();
		    				PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		        			PagerTools.returnCellList(cellList,"&nbsp;");
		    				//�洢������
		    				rowInfo = new ResultPagerRowInfo();
		    				rowInfo.setCell(cellList);
		    				//��������
		    				resultList.add(rowInfo);
		        			
		        			SubSumBalance = 0.0;
		        		
		        			
		    				Total = 0.0;
				        }
			    	}
				}
			    // �Ͽ�����
			    obaqad.closeConn();
			    //end of FOR
				    
//			    if (iItem > 0) {
//					<script language="JavaScript">
//					    sum<%=iItem%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
//					</script>
//			    }
				    
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return resultList;
	}
	
	/**
	 * ������λ�˻������ϸ��ѯ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PagerInfo queryOBAccountQueryAmountDetailInfo(QueryTransAccountDetailWhereInfo qInfo) throws Exception{
		
		OBQueryTransAccountDao oBQueryTransAccountDao = new OBQueryTransAccountDao();
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			//sql = "select 1 from sys_user where 1=2";
			sql = oBQueryTransAccountDao.queryTransAccountDetailSQL(qInfo);
			//�õ���ѯSQL
			pagerInfo.setSqlString(sql);
			
			double dBalance = 0.0;
			QTransAccountDetail qobj = new QTransAccountDetail();
			OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
			
			dBalance = qobj.queryTransAccountBalance(qInfo);//�˻����ڳ����
			if(qInfo.getContractID() > 0){
				dBalance = bqDao.getLoanHisBalanceByContractID(qInfo);//��ͬ���ڳ����
			}
			if(qInfo.getLoanNoteID() > 0)
			{
				dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qInfo);//�ſ���ڳ����
			}
			paramMap.put("qInfo", qInfo);
			paramMap.put("dBalance",dBalance+"");
			pagerInfo.setExtensionMothod(OBAccountQueryBiz.class, "oBAccountAmountDetailResultSetHandle" , paramMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	/**
	 * ������λ�˻������ϸ��ѯ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList oBAccountAmountDetailResultSetHandle(ResultSet rs , Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		long isIntrDate = -1;
		Timestamp interestStartDate = null;
		Timestamp executeDate = null;
		String sAbstract = "";
		long transTypeId = -1;
		String transNo = "";
		long groupId = -1;
		String billNo = "";
		String oppAccountNo = "";
		String oppAccountName = "";
		double payAmount = 0.0;
		double receiveAmount = 0.0;
		double balance = 0.0;
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String executeMonth = "";
		String executeYear = "";
		double dDayPayBalance = 0.0;
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dDayReceiveBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		
		QueryTransAccountDetailWhereInfo qInfo = (QueryTransAccountDetailWhereInfo)paramMap.get("qInfo");
		isIntrDate = qInfo.getIsIntrDate();
		QTransAccountDetail qobj = new QTransAccountDetail();
		Collection coll = qobj.queryTransAccountDetail(qInfo);
		Iterator it = null;
		QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
		
		try{
			
			tsStartDate = qInfo.getStartDate();
			tsEndDate = qInfo.getEndDate();
			
			double dBalance = Double.valueOf(paramMap.get("dBalance").toString()).doubleValue();
			
			if(coll!=null)
			{
				//�洢������
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,DataFormat.formatDate(tsStartDate));
				PagerTools.returnCellList(cellList,"<B>�ڳ����</B>");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"&nbsp;");
				PagerTools.returnCellList(cellList,"<B>"+(dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance))+"</B>");
				//�洢������
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				//��������
				resultList.add(rowInfo);
				
				Timestamp tsTempDate = null;
				Timestamp tsLastDate = null;//Ϊ��ϼ��õ�
				//Timestamp tsYearLoopDate = tsStartDate;
			
				for(tsTempDate = tsStartDate ; (tsTempDate.before(tsEndDate)||tsTempDate.equals(tsEndDate)) ; tsTempDate = DataFormat.getNextMonth(tsTempDate,12))
				{
				
					dYearPayBalance = 0.0;
					dYearReceiveBalance = 0.0;
					//int nYears = Integer.valueOf(DataFormat.getYearString(tsYearLoopDate)).intValue();
					for(tsTempDate = tsStartDate ; (tsTempDate.before(tsEndDate)||tsTempDate.equals(tsEndDate)) ; tsTempDate = DataFormat.getNextMonth(tsTempDate,1))
					{//���·ݴӿ�ʼ���ڿ�ʼѭ�� ��ȡ��û�н��׵��µĺϼ�
						dMonthPayBalance = 0.0;
						dMonthReceiveBalance = 0.0;
						
						if(coll != null)
						{
							it = coll.iterator();
						}
						if(it != null && it.hasNext())
						{
							//int i=0;
							while(it.hasNext())
							{
								//i++;
								qtri = (QueryTransAccountDetailResultInfo)it.next();
								dBalance = qtri.getBalance();
								
								if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue()+1 != qtri.getExecuteMonth())
								{//������Ǳ��µ�����ʾ�ڱ��·�Χ֮��
									continue;
								}
								if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != qtri.getExecuteYear())
								{//������Ǳ��������ʾ�ڱ��귶Χ֮��
									continue;
								}
								if(qtri.getTransTypeID() > -1000)//��������պϼƵ� ���
								{
									dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
									dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
									dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
									dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);					
								}
								
								//�洢������
								cellList = new ArrayList();
								PagerTools.returnCellList(cellList,qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getAbstract() != null?("<b>���պϼ�</b>".equals(qtri.getAbstract())?"<b>���պϼ�</b>":qtri.getAbstract()) : "");
								PagerTools.returnCellList(cellList,qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getBillNo() != null ? qtri.getBillNo() : "&nbsp;");
								PagerTools.returnCellList(cellList,qtri.getBankChequeNo() != null ? qtri.getBankChequeNo() : "&nbsp;");
								if(qtri.getTransTypeID() == -1000)//�պϼ���Ҫ�Ӵ���ʾ
								{
									PagerTools.returnCellList(cellList,"<B>"+(qtri.getPayAmount() >0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00")+"</B>");
									PagerTools.returnCellList(cellList,"<B>"+(qtri.getReceiveAmount() >0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00")+"</B>");
									PagerTools.returnCellList(cellList,"<B>"+(DataFormat.formatDisabledAmount(qtri.getBalance()))+"</B>");
								}else{
									PagerTools.returnCellList(cellList,qtri.getPayAmount() >0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "&nbsp;");
									PagerTools.returnCellList(cellList,qtri.getReceiveAmount() >0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "&nbsp;");
									PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(qtri.getBalance()));
								}
								//�洢������
								rowInfo = new ResultPagerRowInfo();
								rowInfo.setCell(cellList);
								//��������
								resultList.add(rowInfo);
								
								//dBalance = qtri.getBalance();
							}
						}
					
						//�洢������
						cellList = new ArrayList();
						PagerTools.returnCellList(cellList,DataFormat.getLastDateString(tsTempDate));
						PagerTools.returnCellList(cellList,"<B>���ºϼ�</B>");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"&nbsp;");
						PagerTools.returnCellList(cellList,"<B>"+(dMonthPayBalance >0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00") + "</B>");
						PagerTools.returnCellList(cellList,"<B>"+(dMonthReceiveBalance >0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00") + "</B>");
						PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
						//�洢������
						rowInfo = new ResultPagerRowInfo();
						rowInfo.setCell(cellList);
						//��������
						resultList.add(rowInfo);
						
					}
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,DataFormat.getYearString(tsTempDate));
					PagerTools.returnCellList(cellList,"<B>����ϼ�</B>");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"&nbsp;");
					PagerTools.returnCellList(cellList,"<B>"+(dYearPayBalance >0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dYearReceiveBalance >0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00") + "</B>");
					PagerTools.returnCellList(cellList,"<B>"+(dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00") + "</B>");
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//��������
					resultList.add(rowInfo);
					
				}
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
}

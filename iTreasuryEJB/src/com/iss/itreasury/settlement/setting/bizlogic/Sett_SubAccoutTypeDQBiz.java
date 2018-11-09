package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.bizdelegation.AccountTypeSettingDelegation;
import com.iss.itreasury.settlement.setting.dao.Sett_AccountTypeSettingQueryDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class Sett_SubAccoutTypeDQBiz {
	
	Sett_AccountTypeSettingQueryDAO dao =new Sett_AccountTypeSettingQueryDAO();
	
	public PagerInfo queryBranchInfo(long lAccountGroupID, long lOfficeID, long lCurrencyID) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//�õ���ѯ��SQL
			sql=dao.queryAccountTypeSql(lAccountGroupID, lOfficeID, lCurrencyID);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			map.put("accountgroupid", lAccountGroupID);
			
			 pagerInfo.setExtensionMothod(Sett_SubAccoutTypeDQBiz.class, "resultSetHandle",map);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣:"+e.getMessage());
		}
		
		return pagerInfo;
		
	}
	
	  public ArrayList resultSetHandle(ResultSet rs,Map map) throws Exception
	  {
	    ArrayList resultList = new ArrayList();
	    ArrayList cellList = null;
	    ResultPagerRowInfo rowInfo = null;
	    
	    long lAccountGroup= (Long) map.get("accountgroupid");
	    boolean blnIsIntegration = com.iss.itreasury.util.Config.getBoolean(com.iss.itreasury.util.Config.INTEGRATION_SERVICE_ISVALID, false); 
	    boolean blnIsCurrent = SETTConstant.AccountGroupType.CURRENT == lAccountGroup ? true:false;

	    String AccountTypeCode = "";
	    String AccountType = "";
	    long IsExistSubClass = -1;
	    long IsClient = -1;
	    long isAccount = -1;
	    long isDeposit = -1;
	    long accountModule = -1;// �˻�ģʽ
	    long payModule = -1;// ���ʽ  
	    String SubjectCode = "";//��Ŀ��
	    long Id = -1;
	    

	    try
	    {
	      if (rs != null)
	      {
	        while (rs.next())
	        {
	        	
	          Id=rs.getLong("ID");
	          AccountTypeCode = rs.getString("sAccountTypeCode");
	          AccountType = rs.getString("sAccountType");
	          IsExistSubClass= rs.getLong("nIsExistSubClass");
	          IsClient=rs.getLong("nIsClient");
	          isAccount=rs.getLong("nIsAccount");
	          isDeposit=rs.getLong("nIsDeposit");
	          accountModule=rs.getLong("AccountModule");
	          payModule=rs.getLong("PayModule");
	          SubjectCode=rs.getString("sSubjectCode");
	          
	          AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
				
				accountTypeInfo.setId(rs.getLong("ID"));
				accountTypeInfo.setAccountTypeCode(rs.getString("sAccountTypeCode"));
				accountTypeInfo.setAccountType( rs.getString("sAccountType"));
				accountTypeInfo.setIsExistSubClass( rs.getLong("nIsExistSubClass"));
				accountTypeInfo.setIsLoanType(rs.getLong("nIsLoanType"));
				accountTypeInfo.setIsLoanMonth( rs.getLong("nIsLoanMonth"));
				accountTypeInfo.setIsLoanYear(rs.getLong("nIsLoanYear"));
				accountTypeInfo.setIsConsign( rs.getLong("nIsConsign"));
				accountTypeInfo.setIsDraftType(rs.getLong("nIsDraftType"));
				accountTypeInfo.setDefaultDocCode(rs.getString("sDefaultDocCode"));
				accountTypeInfo.setAutoClearAccount( rs.getLong("nAutoClearAccount"));
				accountTypeInfo.setAccountGroupId(rs.getLong("nAccountGroupID"));
				accountTypeInfo.setIsClient( rs.getLong("nIsClient"));
				accountTypeInfo.setIsAccount(rs.getLong("nIsAccount"));
				accountTypeInfo.setIsDeposit(rs.getLong("nIsDeposit"));
				accountTypeInfo.setIsContract(rs.getLong("nIsContract"));
				accountTypeInfo.setSubjectCode(rs.getString("sSubjectCode"));
				accountTypeInfo.setInterestSubjectCode( rs.getString("sInterestSubjectCode"));
				accountTypeInfo.setNegotiateInterestSubjectCode(rs.getString("sNegotiateInterestSubjectCode"));
				accountTypeInfo.setBookedInterestSubjectCode(rs.getString("sBookedInterestSubjectCode"));
				accountTypeInfo.setAccountModule(rs.getLong("AccountModule"));
				accountTypeInfo.setInterestCalculationMode(rs.getLong("interestCalculationMode"));
				accountTypeInfo.setpayModule( rs.getLong("PayModule")); 
	          
	          //��������
	          
	          //��һ�� �˻����ͱ���
	          String strAccountFormNo = accountTypeInfo.getAccountTypeCode();
				if(strAccountFormNo.length()<2)
				{
				     strAccountFormNo = "0"+strAccountFormNo;
				}
				else
				{
				     strAccountFormNo = strAccountFormNo.substring(0,2);
				}
				//�ڶ��� �˻�����
				String accountType2 = accountTypeInfo.getAccountType();
				//������ �¼�����
				String isExistSubClass="";
				String url2="";
				 if(accountTypeInfo.getIsExistSubClass() == 1)
				 {
					 isExistSubClass="��";
				 
				 }
				 else
				 {
					 isExistSubClass="��";
				 }
				 
				 //������ ����
				 AccountTypeSettingDelegation delegation = new AccountTypeSettingDelegation();
				 String fixAccountLabelByID = delegation.getFixAccountLabelByID(accountTypeInfo);
				 String accountLabelByID = delegation.getAccountLabelByID(accountTypeInfo);
				 //Ĭ�ϵ��ݺ�
				 String DefaultDocCode= accountTypeInfo.getDefaultDocCode();
				 //�Զ��廧
				 String AutoClearAccount="";
				 if(accountTypeInfo.getAutoClearAccount()==1)
				 {
					 AutoClearAccount="��";
				 }
					else
					{
					 AutoClearAccount="��";
					}
				
				 //������ �˻���
				 String AccountGroup = SETTConstant.AccountGroupType.getName(lAccountGroup);
				 //������ ��Ӧ��Ŀ��
				String subjectString= accountTypeInfo.getSubjectCode();
				
				String interestCalculationMode="";
				if(accountTypeInfo.getInterestCalculationMode() == -1){
					interestCalculationMode="δ����";
				}
				else {
					interestCalculationMode=SETTConstant.InterestCalculationMode.getName(accountTypeInfo.getInterestCalculationMode());
				}

	          cellList = new ArrayList();
	          PagerTools.returnCellList(cellList, strAccountFormNo + "," +accountTypeInfo.getId()+","+accountTypeInfo.getIsExistSubClass()+","+accountTypeInfo.getAutoClearAccount());
	          PagerTools.returnCellList(cellList, accountType2);
	          if(accountTypeInfo.getIsExistSubClass() == 1)
				 {
					 PagerTools.returnCellList(cellList, isExistSubClass + ","+accountTypeInfo.getId()+","+accountTypeInfo.getIsExistSubClass());
				 
				 }
				 else
				 {
					 PagerTools.returnCellList(cellList, isExistSubClass);
				 }
	          PagerTools.returnCellList(cellList, fixAccountLabelByID);
	          PagerTools.returnCellList(cellList, DefaultDocCode);
	          PagerTools.returnCellList(cellList, AutoClearAccount);
	          PagerTools.returnCellList(cellList, AccountGroup);
	          PagerTools.returnCellList(cellList, subjectString);
	          PagerTools.returnCellList(cellList, interestCalculationMode);

	          rowInfo = new ResultPagerRowInfo();
	          rowInfo.setCell(cellList);
	          rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

	          resultList.add(rowInfo);
	        }
	      }
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
			throw new Exception("====>��ѯ�쳣:"+e.getMessage());
	    }

	    return resultList;
	  }

}

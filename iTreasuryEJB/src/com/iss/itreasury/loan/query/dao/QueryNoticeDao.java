package com.iss.itreasury.loan.query.dao;

import com.iss.itreasury.loan.query.dataentity.QueryNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestRepayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

public class QueryNoticeDao {
	
	QueryDao queryDao = new QueryDao();
	
	/**
	 * �ſ�֪ͨ����ѯ
	 * @param QueryNoticeInfo nInfo
	 * @return
	 */
	public String queryPayNoticeInfo(QueryNoticeInfo nInfo){
	   
		String[] sql = getPayNoticeSQL1(nInfo);//��Ӫ�����Ŵ���
		String[] sql2 = getPayNoticeSQL2(nInfo);//ί�д���
		String querySql = "";
	   
		boolean flag = queryDao.isLoan_WT(nInfo.getNLoanType(), nInfo.getNOfficeID(), nInfo.getNCurrencyID());
		
		//�������Ͳ�����ί�д����Ҳ�ѡί�е�λ ��getPayNoticeSQL1(payNInfo)
		if(!flag && nInfo.getConsignIDFrom()<0 && nInfo.getConsignIDTo()<0){
		   
			querySql = "select " + sql[0] +" from " +sql[1] +" where " + sql[2];
		}else if(nInfo.getConsignIDFrom()>0 || nInfo.getConsignIDTo()>0){
			
			querySql = "select " + sql2[0] +" from " +sql2[1] +" where " + sql2[2];
		}else{
			
			querySql = "select " + sql[0] +" from " +sql[1] +" where "  + sql[2] + " union "+"select " + sql2[0] +" from " +sql2[1] +" where "  + sql2[2];
		}
		
		return querySql;
		
   }
   private String[] getPayNoticeSQL1(QueryNoticeInfo payNoticeInfo){
	   	
	   	String[] sql = new String[4];
	   	StringBuffer buf = new StringBuffer();
		
	   	//�����ͬ��ҵ����ܲ�ѯ �ſ�֪ͨ����ϸ�ĺ�ͬID
	   	if(payNoticeInfo.getContractID()>0){
			
	   		buf.append(" and c.id="+payNoticeInfo.getContractID());
	   	}
		//��ͬ���
	   	if(payNoticeInfo.getContractCodeFrom()!=null && payNoticeInfo.getContractCodeFrom().length()>0 ){
		
	   		buf.append(" and c.sContractCode>='"+payNoticeInfo.getContractCodeFrom()+"'");
	   	}
	   	if(payNoticeInfo.getContractCodeTo()!=null && payNoticeInfo.getContractCodeTo().length()>0 ){
		
	   		buf.append(" and c.sContractCode<='"+payNoticeInfo.getContractCodeTo()+"'");
	   	}
	   	//��λ
	   	if(payNoticeInfo.getLoanClientIDFrom()>0){
		
	   		buf.append(" and c.nBorrowClientID>="+payNoticeInfo.getLoanClientIDFrom());
	   	}
	   	if(payNoticeInfo.getLoanClientIDTo()>0){
			
	   		buf.append(" and c.nBorrowClientID<="+payNoticeInfo.getLoanClientIDTo());
		}
	   	/*//ί�е�λ
		if(payNoticeInfo.getConsignIDFrom()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID>="+payNoticeInfo.getConsignIDFrom());
	    }
	   	if(payNoticeInfo.getConsignIDTo()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID<="+payNoticeInfo.getConsignIDTo());
	    }*/
	   	//�ſ���
	   	if(payNoticeInfo.getMPayAmountFrom()>0){
			
	   		buf.append(" and l.mAmount>="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountFrom()));	
	    }
	   	if(payNoticeInfo.getMPayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));
	    }
	   	//�ſ�����
	   	if(payNoticeInfo.getMPayInterestFrom()>0){
			
			buf.append(" and l.minterestrate>="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestFrom()));
	    }
	   	if(payNoticeInfo.getMPayInterestTo()>0){
			
			buf.append(" and l.minterestrate<="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestTo()));
	    }
	   	//�Ŵ����ڿ�ʼ
	   	if (payNoticeInfo.getDtLoanPayDateFrom()!= null){
	   		
	   		buf.append(" and TRUNC(l.DTOUTDATE) >= To_Date('" + payNoticeInfo.getDtLoanPayDateFrom() + "','yyyy-mm-dd') ");
	   	}
	   	if (payNoticeInfo.getDtLoanPayDateTo() != null){
		
	   		buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('" +payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
	   	}
	   	//��������
	   	if (payNoticeInfo.getDtRepayDateFrom()!= null){
		
	   		buf.append(" and TRUNC(l.dtEnd) >= To_Date('" + payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
	   	}
	   	if (payNoticeInfo.getDtRepayDateTo() != null){
		
	   		buf.append(" and TRUNC(l.dtEnd) <= To_Date('" +payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}
	   	//¼������
	   	if (payNoticeInfo.getDtInputDateFrom()!= null){
		
	   		buf.append(" and TRUNC(l.dtInputDate) >= To_Date('" + payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
	   	}
	   	if (payNoticeInfo.getDtInputDateTo() != null){
		
	   		buf.append(" and TRUNC(l.dtInputDate) <= To_Date('" +payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}
	   	//��������
	   	if(payNoticeInfo.getNLoanType()==null || payNoticeInfo.getNLoanType().trim().equals("-1") ){
	   
	   		buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b ");
	   		buf.append(" where a.statusid=1 and a.loantypeid= b.loantypeid and a.loantypeid in (1,5) and a.id = b.subloantypeid ");
	   		buf.append(" and b.currencyid="+payNoticeInfo.getNCurrencyID()+" and b.officeid="+payNoticeInfo.getNOfficeID()+") ");
	   	}else{
		  
			buf.append(" and c.nSubTypeID in (" + payNoticeInfo.getNLoanType() + ") and c.nSubTypeID not in (select a.id as subTypeID from loan_loantypesetting a, loan_loantyperelation b ");
			buf.append(" where a.statusid = 1 and a.loantypeid = b.loantypeid and a.loantypeid = 2 and a.id = b.subloantypeid ");
			buf.append(" and b.currencyid = "+payNoticeInfo.getNCurrencyID()+" and b.officeid = "+payNoticeInfo.getNOfficeID()+") ");
	   	}
	   	//�ſ�֪ͨ��״̬
	   	long loanPayNoticeStatus[]=LOANConstant.LoanPayNoticeStatus.getAllCode(payNoticeInfo.getNOfficeID(),payNoticeInfo.getNCurrencyID());
	   	String loanTypeList	= "";
	   	for( int i=0;i<loanPayNoticeStatus.length;i++ ){
		
	   		if(loanPayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
	   			continue;
	   		}
	   		loanTypeList+=loanPayNoticeStatus[i];
	   		if((loanPayNoticeStatus.length-i)>1){
			
	   			loanTypeList+=",";
	   		}
	   	}
	   	if(payNoticeInfo.getNPayNoticeStatus()==null ||payNoticeInfo.getNPayNoticeStatus().trim().equals("-1")){
	   		
	   		buf.append(" and l.nStatusID in (" + loanTypeList + ")");
	   	}else{
		
	   		buf.append(" and l.nStatusID in (" + payNoticeInfo.getNPayNoticeStatus() + ")");
	   	}
	   	//select
	   	sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,"
				+ " d.name  as loanClientName,'' as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName, "
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate, "
				+ " l.minterestrate as InterestRate,l.nContractid as contractID, "
				+ " l.nDrawNoticeID as DrawNoticeID, "
				+ " c.nTypeID as LoanTypeID, "
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID ";
	   	//from
	   	sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt ";
	   	//where
	   	sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id =c.nBorrowClientID and l.NOFFICEID="+payNoticeInfo.getNOfficeID()+" and l.NCURRENCYID="+payNoticeInfo.getNCurrencyID()+buf.toString();
	
		return sql;
	}
   	private String[] getPayNoticeSQL2(QueryNoticeInfo payNoticeInfo){
	   
   		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		
		//�����ͬ��ҵ����ܲ�ѯ �ſ�֪ͨ����ϸ�ĺ�ͬID
		if(payNoticeInfo.getContractID()>0){
			
			buf.append(" and c.id="+payNoticeInfo.getContractID());
		}
		//��ͬ���
		if(payNoticeInfo.getContractCodeFrom()!=null && payNoticeInfo.getContractCodeFrom().length()>0 ){
			
			buf.append(" and c.sContractCode>='"+payNoticeInfo.getContractCodeFrom()+"'");
		}
		if(payNoticeInfo.getContractCodeTo()!=null && payNoticeInfo.getContractCodeTo().length()>0 ){
			
			buf.append(" and c.sContractCode<='"+payNoticeInfo.getContractCodeTo()+"'");
	    }
		//��λ
		if(payNoticeInfo.getLoanClientIDFrom()>0){
			
			buf.append(" and c.nBorrowClientID>="+payNoticeInfo.getLoanClientIDFrom());
	    }
		if(payNoticeInfo.getLoanClientIDTo()>0){
			
			buf.append(" and c.nBorrowClientID<="+payNoticeInfo.getLoanClientIDTo());
		}
		//ί�е�λ
		if(payNoticeInfo.getConsignIDFrom()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID>="+payNoticeInfo.getConsignIDFrom());
	    }
		if(payNoticeInfo.getConsignIDTo()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID<="+payNoticeInfo.getConsignIDTo());
	    }
		//�ſ���
		if(payNoticeInfo.getMPayAmountFrom()>0){
			
			buf.append(" and l.mAmount>="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountFrom()));
	    }
		if(payNoticeInfo.getMPayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));
	    }
		//�ſ�����
		if(payNoticeInfo.getMPayInterestFrom()>0){
			
			buf.append(" and l.minterestrate>="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestFrom()));
	    }
		if(payNoticeInfo.getMPayInterestTo()>0){
			
			buf.append(" and l.minterestrate<="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestTo()));
	    }
		//�Ŵ����ڿ�ʼ
 		if (payNoticeInfo.getDtLoanPayDateFrom()!= null){
 		
 			buf.append(" and TRUNC(l.DTOUTDATE) >= To_Date('" + payNoticeInfo.getDtLoanPayDateFrom() + "','yyyy-mm-dd') ");
 		}
 		if (payNoticeInfo.getDtLoanPayDateTo() != null){
 		
 			buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('" +payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
 		}
 		//��������
 		if (payNoticeInfo.getDtRepayDateFrom()!= null){
 		
 			buf.append(" and TRUNC(l.dtEnd) >= To_Date('" + payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
 		}
 		if (payNoticeInfo.getDtRepayDateTo() != null){
 		
 			buf.append(" and TRUNC(l.dtEnd) <= To_Date('" +payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
 		}
 		//¼������
 		if (payNoticeInfo.getDtInputDateFrom()!= null){
 			
 			buf.append(" and TRUNC(l.dtInputDate) >= To_Date('" + payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
 		}
 		
 		if (payNoticeInfo.getDtInputDateTo() != null){
 			
 			buf.append(" and TRUNC(l.dtInputDate) <= To_Date('" +payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
 		}
		//��������
 		if(payNoticeInfo.getNLoanType()==null || payNoticeInfo.getNLoanType().trim().equals("-1") ){
 		
 			buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b ");
 			buf.append(" where a.statusid=1 and a.loantypeid= b.loantypeid and a.loantypeid in (2) and a.id = b.subloantypeid ");
 			buf.append(" and b.currencyid="+payNoticeInfo.getNCurrencyID()+" and b.officeid="+payNoticeInfo.getNOfficeID()+") ");
 		}else{
 			
 			buf.append(" and c.nSubTypeID in (" + payNoticeInfo.getNLoanType() + ")");	
 		}
 		//�ſ�֪ͨ��״̬
 	    long loanPayNoticeStatus[]=LOANConstant.LoanPayNoticeStatus.getAllCode(payNoticeInfo.getNOfficeID(),payNoticeInfo.getNCurrencyID());
 		String loanTypeList = "";
 		for( int i=0;i<loanPayNoticeStatus.length;i++ ){
 			
 			if(loanPayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
 				continue;
 			}
 			loanTypeList+=loanPayNoticeStatus[i];
 			if((loanPayNoticeStatus.length-i)>1){
 				
 				loanTypeList+=",";
 			}
 		}
 		if(payNoticeInfo.getNPayNoticeStatus()==null ||payNoticeInfo.getNPayNoticeStatus().equals("-1") ){
 				
 			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
 		}else{
 			
 			buf.append(" and l.nStatusID in (" + payNoticeInfo.getNPayNoticeStatus() + ")");
 		}
		//select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,d.name as LoanClientName, "
				+ " e.name as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName, "
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate, "
				+ " l.minterestrate as InterestRate,l.nContractid as contractID, "
				+ " l.nDrawNoticeID as DrawNoticeID, "
				+ " c.nTypeID as LoanTypeID, "
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID ";
		//from
		sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt,client_clientinfo e ";
		//where
		sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id=c.nborrowclientID and e.id =c.nConsignClientID and l.NOFFICEID="+payNoticeInfo.getNOfficeID()+" and l.NCURRENCYID="+payNoticeInfo.getNCurrencyID()+buf.toString();
		
		return sql;
	}
   	/**
     * ��ѯ����֪ͨ��
     * @param QueryNoticeInfo rePayInfo
     * @return
     * @throws Exception
     */
	public String queryRePayNoticeInfo(QueryNoticeInfo rePayInfo) throws Exception {
		
		String[] sql1 = getRepayNoticeSQl1(rePayInfo);
		String[] sql2 = getRepayNoticeSQl2(rePayInfo);
		String rePayNoticeSql = "";
			
		boolean flag = queryDao.isLoan_WT(rePayInfo.getNLoanType(), rePayInfo.getNOfficeID(), rePayInfo.getNCurrencyID());
			
		//�������Ͳ�����ί�д����Ҳ�ѡί�е�λ ��getPayNoticeSQL1(payNInfo)
		if(!flag && rePayInfo.getConsignIDFrom()<0 && rePayInfo.getConsignIDTo()<0){
			
			rePayNoticeSql = "select " + sql1[0] +" from " +sql1[1] +" where " + sql1[2];
		}else if(rePayInfo.getConsignIDFrom()>0 || rePayInfo.getConsignIDTo()>0){
			
			rePayNoticeSql = "select " + sql2[0] +" from " +sql2[1] +" where " + sql2[2];
		}else{
			
			rePayNoticeSql = "select " + sql1[0] +" from " +sql1[1] +" where "  + sql1[2] + " union "+"select " + sql2[0] +" from " +sql2[1] +" where "  + sql2[2];
		  
		}
		return rePayNoticeSql;	
			
	}
	private String[] getRepayNoticeSQl1(QueryNoticeInfo repayInfo) {
		
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		
		//��ѯ��ͬ���»���֪ͨ����ϸ
		if(repayInfo.getContractID()>0){
			
			buf.append(" and l.NCONTRACTID ="+repayInfo.getContractID());
	    }
		//��ͬ���
		if(repayInfo.getContractCodeFrom()!=null && repayInfo.getContractCodeFrom().length()>0 ){
			
			buf.append(" and c.sContractCode>='"+repayInfo.getContractCodeFrom()+"'");
		}
		if(repayInfo.getContractCodeTo()!=null && repayInfo.getContractCodeTo().length()>0 ){
			
			buf.append(" and c.sContractCode<='"+repayInfo.getContractCodeTo()+"'");
	    }
		//��λ
		if(repayInfo.getLoanClientIDFrom()>0){
			
			buf.append(" and c.nBorrowClientID>="+repayInfo.getLoanClientIDFrom());
	    }
        if(repayInfo.getLoanClientIDTo()>0){
			
			buf.append(" and c.nBorrowClientID<="+repayInfo.getLoanClientIDTo());
	    }
        //������
        if(repayInfo.getMRePayAmountFrom()>0){
			
			buf.append(" and l.mAmount>="+DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));
	    }
        if(repayInfo.getMRePayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));
	    }
        //�黹��Ϣ
        if(repayInfo.getDrawAmountInterestFrom()>0){
			
			buf.append(" and l.INTERESTAMOUNT>="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestFrom()));
	    }
        if(repayInfo.getDrawAmountInterestTo()>0){
			
			buf.append(" and l.INTERESTAMOUNT<="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestTo()));
	    }
		//�������ڿ�ʼ
		if (repayInfo.getDtRepayDateFrom() != null){
		
			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('" + repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}
		if (repayInfo.getDtRepayDateTo() != null){
		
			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('" + repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}
		//¼�����ڿ�ʼ
		if (repayInfo.getDtInputDateFrom() != null){
		
			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('" + repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}
		if (repayInfo.getDtInputDateTo() != null){
		
			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('" + repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}
		//��������
	    if(repayInfo.getNLoanType()==null || repayInfo.getNLoanType().trim().equals("-1") ){
	    
	    	buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b ");
	    	buf.append(" where a.statusid=1 and a.loantypeid= b.loantypeid and a.loantypeid in (1,5) and a.id = b.subloantypeid ");
	    	buf.append(" and b.currencyid = "+repayInfo.getNCurrencyID()+" and b.officeid = "+repayInfo.getNOfficeID()+") ");
	    }else{		
	  		
	    	buf.append(" and c.nSubTypeID in (" + repayInfo.getNLoanType() + ") and c.nSubTypeID not in (select a.id as subTypeID ");
	    	buf.append(" from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid ");
	    	buf.append(" and a.loantypeid = 2 and a.id = b.subloantypeid ");
	    	buf.append(" and b.currencyid = "+repayInfo.getNCurrencyID()+" and b.officeid = "+repayInfo.getNOfficeID()+") ");	
	  	}
  		//����֪ͨ��״̬
  	    long loanRePayNoticeStatus[]=LOANConstant.AheadRepayStatus.getAllCode(repayInfo.getNOfficeID(),repayInfo.getNCurrencyID());
  		String loanTypeList="";
  		for ( int i=0;i<loanRePayNoticeStatus.length;i++ ){
  			
  			if(loanRePayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
  				continue;
  			}
  			loanTypeList+=loanRePayNoticeStatus[i];
  			if((loanRePayNoticeStatus.length-i)>1){
  				
  				loanTypeList+=",";
  			}
  		}
    	if(repayInfo.getNRePayNoticeStatus()==null ||repayInfo.getNRePayNoticeStatus().trim().equals("-1")){
    		
  		   buf.append(" and l.nStatusID in (" + loanTypeList + ")");
  		}else{
  			
  			buf.append(" and l.nStatusID in (" + repayInfo.getNRePayNoticeStatus() + ")");	
  		}
  		//�Ƿ���ǰ����
  		if(repayInfo.getIsHead()>0){
  			
  			buf.append("and l.NISAHEAD="+String.valueOf(repayInfo.getIsHead()));
  		}
		//select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
				+ " d.name  ClientName ,'' as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
				+ " c.nTypeID as loanType,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";
		//from
		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,loan_loantypesetting lt";
		//where
		sql[2] = " lt.id = c.nsubtypeid and u.id=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="+repayInfo.getNOfficeID()+" and l.NCURRENCYID="+repayInfo.getNCurrencyID()+buf.toString();

		sql[3] = " order by l.ID ";
		
		return sql;
		
	}
	private String[] getRepayNoticeSQl2 (QueryNoticeInfo repayInfo) {
		
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		
		//��ѯ��ͬ���»���֪ͨ����ϸ
		if(repayInfo.getContractID()>0){
			
			buf.append(" and l.NCONTRACTID="+repayInfo.getContractID());
	    }
		//��ͬ���
		if(repayInfo.getContractCodeFrom()!=null && repayInfo.getContractCodeFrom().length()>0 ){
			
			buf.append(" and c.sContractCode>='"+repayInfo.getContractCodeFrom()+"'");
		}
		if(repayInfo.getContractCodeTo()!=null && repayInfo.getContractCodeTo().length()>0 ){
			
			buf.append(" and c.sContractCode<='"+repayInfo.getContractCodeTo()+"'");
	    }
		//��λ
		if(repayInfo.getLoanClientIDFrom()>0){
			
			buf.append(" and c.nBorrowClientID>="+repayInfo.getLoanClientIDFrom());
	    }
        if(repayInfo.getLoanClientIDTo()>0){
			
			buf.append(" and c.nBorrowClientID<="+repayInfo.getLoanClientIDTo());
	    }
        //ί�е�λ
		if(repayInfo.getConsignIDFrom()>0){
			
			buf.append(" and c.nConsignClientid>="+repayInfo.getConsignIDFrom());
	    }
        if(repayInfo.getConsignIDTo()>0){
			
			buf.append(" and c.nConsignClientid<="+repayInfo.getConsignIDTo());
	    }
        //������
        if(repayInfo.getMRePayAmountFrom()>0){
			
			buf.append(" and l.mAmount>="+DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));
	    }
        if(repayInfo.getMRePayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));
	    }
        //�黹��Ϣ
        if(repayInfo.getDrawAmountInterestFrom()>0){
			
			buf.append(" and l.INTERESTAMOUNT>="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestFrom()));
	    }
        if(repayInfo.getDrawAmountInterestTo()>0){
			
			buf.append(" and l.INTERESTAMOUNT<="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestTo()));
	    }
		//�������ڿ�ʼ
		if (repayInfo.getDtRepayDateFrom() != null){
		
			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('" + repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}
		if (repayInfo.getDtRepayDateTo() != null){
		
			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('" + repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}
		//¼�����ڿ�ʼ
		if (repayInfo.getDtInputDateFrom() != null){
		
			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('" + repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}
		if (repayInfo.getDtInputDateTo() != null){
		
			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('" + repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}
		//��������
	    if(repayInfo.getNLoanType()==null || repayInfo.getNLoanType().trim().equals("-1") ){
	    
	    	buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b ");
	    	buf.append(" where a.statusid=1 and a.loantypeid= b.loantypeid and a.loantypeid  in (2) and a.id=b.subloantypeid ");
	    	buf.append(" and b.currencyid = "+repayInfo.getNCurrencyID()+" and b.officeid = "+repayInfo.getNOfficeID()+") ");
	    }else{
	  			
	  		buf.append(" and c.nSubTypeID in (" + repayInfo.getNLoanType() + ")");		
	  	}
  		//����֪ͨ��״̬
  	    long loanRePayNoticeStatus[]=LOANConstant.AheadRepayStatus.getAllCode(repayInfo.getNOfficeID(),repayInfo.getNCurrencyID());
  		String loanTypeList="";
  		for ( int i=0;i<loanRePayNoticeStatus.length;i++ ){
  			
  			if(loanRePayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
  				continue;
  			}
  			loanTypeList+=loanRePayNoticeStatus[i];
  			if((loanRePayNoticeStatus.length-i)>1){
  				
  				loanTypeList+=",";
  			}
  		}
  		if(repayInfo.getNRePayNoticeStatus()!=null && repayInfo.getNRePayNoticeStatus().length()>0){
  			
  			buf.append(" and l.nStatusID in (" + repayInfo.getNRePayNoticeStatus() + ")");
  		}else{
  			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
  		}
  		//�Ƿ���ǰ����
  		if(repayInfo.getIsHead()>0){
  			
  			buf.append("and l.NISAHEAD="+String.valueOf(repayInfo.getIsHead()));
  		}
		//select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
				+ " d.name as ClientName, e.name as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
				+ " c.nTypeID as loanType,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";
		//from
		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,client_clientinfo e,loan_loantypesetting lt";
		//where
		sql[2] = " lt.id = c.nsubtypeid and e.id=c.nConsignClientID and u.id(+)=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="+repayInfo.getNOfficeID()+" and l.NCURRENCYID="+repayInfo.getNCurrencyID()+buf.toString();

		sql[3] = " order by l.ID ";
		
		return sql;
		
	}

}

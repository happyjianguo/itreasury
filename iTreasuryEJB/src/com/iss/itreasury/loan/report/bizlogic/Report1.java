// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Report1.java

package com.iss.itreasury.loan.report.bizlogic;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.report.dao.Report1Dao;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.loan.report.bizlogic.CommonBiz;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.util.Env;
public class Report1
{
	/**
     * ���ز�ѯ�����
     * 
     * @param year
     * @param month
     * @param qInfo
     * @return result
     * @throws Exception
     */ 
    public Vector queryLoanOperationBalanceDetail(ReportWhereInfo wInfo)
        throws Exception
    {
        ReportResultInfo infosFrom[] = (new Report1Dao()).queryLoanOperationBalanceDetail(wInfo);
        if (infosFrom==null || infosFrom.length==0)
        {
        return new Vector();	
        }
        
        Vector result = new Vector();
        int count=infosFrom.length;
        int j=0,k=0;
        ContractOperation contractOperation = new ContractOperation();
        double BalanceAmountTemp0=0,InterestAmountTemp0=0;
        double BalanceAmountTemp1=0,InterestAmountTemp1=0;//ƽ̨����С����ʱ��¼
        double BalanceAmountTemp2=0,InterestAmountTemp2=0;//��������С����ʱ��¼
        double BalanceAmount=0,InterestAmount=0;//�ܼ�        
        for(int i = 0; i < infosFrom.length; i++)
        { ReportResultInfo infoTo = new ReportResultInfo();
        	if (infosFrom[i].getLongColumn8()==1)
        	{  BalanceAmountTemp0=contractOperation.getLateAmount(infosFrom[i].getLongColumn1()).getBalanceAmount()/10000;
        		InterestAmountTemp0=contractOperation.getLateAmount(infosFrom[i].getLongColumn1()).getInterestAmount()/10000;
        		infoTo = transferReportResultInfo(wInfo.getDate(),infosFrom[i],infosFrom,BalanceAmountTemp0,InterestAmountTemp0);
                result.addElement(infoTo);
                BalanceAmountTemp1+=BalanceAmountTemp0;
                InterestAmountTemp1+=InterestAmountTemp0;
                j++;
        	}
        }
        for(int i = 0; i < infosFrom.length; i++)
        { ReportResultInfo infoTo = new ReportResultInfo();
        	
        	if (infosFrom[i].getLongColumn8()==2)
        	{
        		BalanceAmountTemp0=contractOperation.getLateAmount(infosFrom[i].getLongColumn1()).getBalanceAmount()/10000;
        		InterestAmountTemp0=contractOperation.getLateAmount(infosFrom[i].getLongColumn1()).getInterestAmount()/10000;
        		infoTo = transferReportResultInfo(wInfo.getDate(),infosFrom[i],infosFrom,BalanceAmountTemp0,InterestAmountTemp0);
                result.addElement(infoTo);
                BalanceAmountTemp2+=BalanceAmountTemp0;
                InterestAmountTemp2+=InterestAmountTemp0;
               k++;
        	}
        }
        BalanceAmount=BalanceAmountTemp1+BalanceAmountTemp2;
        InterestAmount=InterestAmountTemp1+InterestAmountTemp2;
        ReportResultInfo commonInfo1 = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,"ƽ̨С��",1,BalanceAmountTemp1,InterestAmountTemp1);
    	ReportResultInfo commonInfo2 = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,"����С��",2,BalanceAmountTemp2,InterestAmountTemp2);
    	ReportResultInfo commonInfo3 = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,"�����ܼ�",0,BalanceAmount,InterestAmount);
    	ReportResultInfo commonInfo4 = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,"ƽ̨����",1,0,0);
    	ReportResultInfo commonInfo5 = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,"��������",1,0,0);
    	result.insertElementAt(commonInfo4,0);
    	result.insertElementAt(commonInfo1,j+1);
    	result.insertElementAt(commonInfo5,j+2);
    	result.insertElementAt(commonInfo2,j+k+3);
    	result.insertElementAt(commonInfo3,j+k+4);
    	/*Vector queryResults=new Vector();
    	for (int i=0;i<result.size();i++)
    	{
    		ReportResultInfo info1=(ReportResultInfo)result.elementAt(i);	
    	    CommonBiz.devideByTenThousandsForAllDoubleColumn(info1);
    	    queryResults.addElement(info1);   
    	}*/
    	
        return result;
    }

    private ReportResultInfo transferReportResultInfo(Timestamp firstDay,ReportResultInfo reportResultInfo,
            ReportResultInfo[] infos,double BalanceAmount,double InterestAmount)
        throws Exception
    {
        ReportResultInfo result = new ReportResultInfo();        
        
        ContractOperation contractOperation = new ContractOperation();
        result.setLongColumn1(reportResultInfo.getLongColumn1());//��¼id
        result.setLongColumn2(reportResultInfo.getLongColumn2());//����
        result.setLongColumn3(reportResultInfo.getLongColumn3());//��λ����ID
        result.setLongColumn4(reportResultInfo.getLongColumn8());//������ʽ
        result.setStringColumn1(reportResultInfo.getStringColumn1());//��ͬ���
        result.setStringColumn2(reportResultInfo.getStringColumn2());//��λ����
        result.setStringColumn3(transTypes(reportResultInfo));//������ʽ
        result.setDoubleColumn1(reportResultInfo.getDoubleColumn1()/10000);//��ͬ���
        result.setDoubleColumn2(BalanceAmount);//�������
        result.setDoubleColumn3(contractOperation.getLatelyRate(-1,reportResultInfo.getLongColumn1(),firstDay).getLateRate());//����
        result.setDoubleColumn4(InterestAmount);//Ӧ����Ϣ
        result.setTsColumn1(reportResultInfo.getTsColumn1());//������Ϣ��
        result.setTsColumn2(reportResultInfo.getTsColumn2());//�����ֹ��
        return result;
    }
    
    private int querySumOfClientsOfOneClientType(
            ReportResultInfo[] reportResultInfos, long clientTypeId) {
        int result = 0;
        for (int i = 0; i < reportResultInfos.length; i++) {
            if (clientTypeId == reportResultInfos[i].getLongColumn1()) {
                result++;
            }
        }
        return result;
    }
    private ReportResultInfo buildCommonInfo1(ReportResultInfo info,
            ReportResultInfo[] infos,String str,long log,double BalanceAmount,double InterestAmount)
    throws Exception
   {
    	//System.out.println("infos"+infos);
    ReportResultInfo result = new ReportResultInfo();
    long LongColumn1=0;
    double DoubleColumn1=0;
    String StringColumn1=str;
    String StringColumn2="&nbsp;";
    Timestamp TsColumn1=null;
    if (str.equals("��������"))
    {	
    result.setStringColumn1("��");
    result.setStringColumn2(str);
    }
    else if (str.equals("ƽ̨����"))
    {
    result.setStringColumn1("һ");
    result.setStringColumn2(str);
    }
    else 
    {
    result.setStringColumn1(str);//ƽ̨����С��
    result.setStringColumn2(StringColumn2);//���������	
    }
    
    	result.setDoubleColumn1(calcTotal(infos, "DoubleColumn1",str));//��ͬ���
        result.setDoubleColumn2(BalanceAmount);//�������
        result.setTsColumn1(TsColumn1);
        result.setTsColumn2(TsColumn1);
        result.setDoubleColumn3(DoubleColumn1);
        result.setDoubleColumn4(InterestAmount);//Ӧ����Ϣ
        result.setStringColumn3(StringColumn2);
        result.setLongColumn2(LongColumn1);
        result.setLongColumn3(LongColumn1);
        result.setLongColumn4(log);
    return result;
   }
    
    private  String transTypes(ReportResultInfo reportResultInfo)
    {
       
String assuretype="";
		
		if (reportResultInfo.getLongColumn4()>0)
		{	
		assuretype+=LOANConstant.AssureType.getName(LOANConstant.AssureType.CREDIT)+",";
		}
		if (reportResultInfo.getLongColumn5()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(LOANConstant.AssureType.ASSURE)+",";
		}
		if (reportResultInfo.getLongColumn6()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(LOANConstant.AssureType.PLEDGE)+",";
		}
		if (reportResultInfo.getLongColumn7()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(LOANConstant.AssureType.IMPAWN)+",";
		}
		if (reportResultInfo.getLongColumn9()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(LOANConstant.AssureType.RECOGNIZANCE);
		}
		
		if (assuretype.endsWith(",")==true)
			{
			 assuretype=assuretype.substring(0,assuretype.length()-1);
			}
			if (assuretype.equals(""))
			{
				assuretype="&nbsp;";
			}
         return assuretype;
        
		
    } 
    public static double calcTotal(Object[] dataGather, String paraName,String str)
    throws Exception {
    double result = 0;
    for (int i = 0; i < dataGather.length; i++) {
    	ReportResultInfo tempInfo = (ReportResultInfo) dataGather[i];
    	if (str.equals("�����ܼ�"))
    	{
    		result += Double.parseDouble(CommonBiz.getReadMethodByParaName(paraName)
                    .invoke(tempInfo, new Object[]{}).toString())/10000;	
    	}
    	if (str.equals("ƽ̨С��"))
    	{
    		if (tempInfo.getLongColumn8()==1)
        	{
            
            result += Double.parseDouble(CommonBiz.getReadMethodByParaName(paraName)
                .invoke(tempInfo, new Object[]{}).toString())/10000;
        	}
        	
    	}
    	if (str.equals("����С��"))
    	{
    	if (tempInfo.getLongColumn8()==2)
    	{
        result += Double.parseDouble(CommonBiz.getReadMethodByParaName(paraName)
            .invoke(tempInfo, new Object[]{}).toString())/10000;
    	}
    	}
    }
    
    return result;
}
    public static void main(String args[])
    {
    	
    }
}

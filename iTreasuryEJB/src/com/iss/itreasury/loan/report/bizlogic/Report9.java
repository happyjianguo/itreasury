/*
 * Created on 2005-11-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.report.dao.Report9Dao;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import java.util.LinkedList;
import com.iss.itreasury.loan.report.bizlogic.CommonBiz;

import com.iss.itreasury.util.DataFormat;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.util.Env;
/**
 * @author liwang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report9 {
    /**
     * ���ز�ѯ�����
     * 
     * @param year
     * @param month
     * @param qInfo
     * @return result
     * @throws Exception
     */
	public Vector QueryConsignLoanDetail(ReportWhereInfo wInfo) throws Exception
            {
		    ReportResultInfo[] infosFrom = (new Report9Dao()
                .QueryConsignLoanDetail(wInfo));
		    if (infosFrom.length>=1)
		    {	
	        Vector result = new Vector();
	        double BalanceAmount=0,BalanceAmount1=0;
	        ReportResultInfo infoTo = new ReportResultInfo();
	        ContractOperation contractOperation = new ContractOperation();
	        int j=0;
	        for (int i=0;i<infosFrom.length;i++)
	        {
	        	BalanceAmount=contractOperation.getLateAmount(infosFrom[i].getLongColumn1()).getBalanceAmount()/10000;	
	        	infoTo = transferReportResultInfo(wInfo.getDate(),infosFrom[i],infosFrom,BalanceAmount);
                result.addElement(infoTo); 
                BalanceAmount1+=BalanceAmount;
                j++;
	        }
	        
	        ReportResultInfo commonInfo = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom,BalanceAmount1);   
	        result.addElement(commonInfo);
	        return result;
		    }
		    else
		    {
		     return new Vector();
		    }	
            }
	private ReportResultInfo transferReportResultInfo(
			Timestamp firstDay,ReportResultInfo reportResultInfo,
            ReportResultInfo[] infos,double BalanceAmount)
    throws Exception {			
		 ReportResultInfo result = new ReportResultInfo();        
	        
	       ContractOperation contractOperation = new ContractOperation();	                
	        result.setStringColumn1(reportResultInfo.getStringColumn1());//��ͬ���
	        result.setStringColumn2(reportResultInfo.getStringColumn2());//��λ����
	        result.setStringColumn3(reportResultInfo.getStringColumn3());//ί�е�λ����
	        result.setStringColumn4(transTypes(reportResultInfo));//������ʽ
	        result.setDoubleColumn1(reportResultInfo.getDoubleColumn1()/10000);//��ͬ���
	        result.setDoubleColumn2(BalanceAmount);//�������
	        result.setDoubleColumn3(contractOperation.getLatelyRate(-1,reportResultInfo.getLongColumn1(),firstDay).getLateRate());//����
	        result.setTsColumn1(reportResultInfo.getTsColumn1());//������Ϣ��
	        result.setTsColumn2(reportResultInfo.getTsColumn2());//�����ֹ��
	        return result;
	}
	private  String transTypes(ReportResultInfo reportResultInfo)
    {
        int Num = 0;
        int Num1 = 0;
        int Num2 = 0;
        int Num3 = 0;
        String types = "";
        Num=Integer.parseInt(DataFormat.formatInt(reportResultInfo.getLongColumn4(),1));
	     switch (Num)
	     {
	     case 1 :types+="���ñ�֤,";break;				     
	     default:break;
	     }
	     
	     Num1=Integer.parseInt(DataFormat.formatInt(reportResultInfo.getLongColumn5(),1));
	     switch (Num1)
	     {
	     case 1 :types+="����,";break;				     
	     default:break;				     
	     }
	     
	     Num2=Integer.parseInt(DataFormat.formatInt(reportResultInfo.getLongColumn6(),1));
	     switch (Num2)
	     {
	     case 1 :types+="��Ѻ,";break;				     
	     default:break;				     
	     }
	     Num3=Integer.parseInt(DataFormat.formatInt(reportResultInfo.getLongColumn7(),1));
	     switch (Num3)
	     {
	     case 1 :types+="��Ѻ";break;				     
	     default:break;				     
	     }
	     if (types.endsWith(",")==true)
			{
				types=types.substring(0,types.length()-1);
			}
			if (types.equals(""))
			{
				types="&nbsp;";
				}
        return types;
    } 
	private ReportResultInfo buildCommonInfo1(ReportResultInfo info,
            ReportResultInfo[] infos,double BalanceAmount)throws Exception
	{double DoubleColumn1=0;
		Timestamp TsColumn1=null;	
		
	ReportResultInfo result = new ReportResultInfo();
	String StringColumn1="�ϼ�";
	String StringColumn2="&nbsp;";
	ContractOperation contractOperation = new ContractOperation();
	result.setStringColumn1(StringColumn1);//��ͬ��� 
	
	
   
    result.setDoubleColumn1(calcTotal(infos, "DoubleColumn1"));//��ͬ���
    result.setDoubleColumn2(BalanceAmount);//�������  
    
	return result;	
	}
	public static double calcTotal(Object[] dataGather, String paraName)
    throws Exception {
    double result = 0;
    
    for (int i=0;i<dataGather.length;i++)
    {
    	ReportResultInfo tempInfo = (ReportResultInfo) dataGather[i];
    	result+=Double.parseDouble(CommonBiz.getReadMethodByParaName(paraName).invoke(tempInfo,new Object[]{}).toString())/10000;
    }
    
    return result;
    }
	public static void main(String[] args) {
		
    }
	
}
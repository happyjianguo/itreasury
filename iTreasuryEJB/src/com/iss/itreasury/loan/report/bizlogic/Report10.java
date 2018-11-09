/*
 * Created on 2005-12-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.report.bizlogic;
import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.report.dao.Report10Dao;
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
import com.iss.itreasury.loan.util.LOANConstant;
/**
 * @author liwang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Report10 {
    /**
     * 返回查询结果集
     * 
     
     * @param wInfo
     * @return result
     * @throws Exception
     */
	public Vector queryLoanOperationBalanceDetail(ReportWhereInfo wInfo) throws Exception
            {
		ReportResultInfo[] infosFrom = (new Report10Dao()
                .queryAssureOperationBalanceDetail(wInfo));
		if (infosFrom==null || infosFrom.length==0)
        {
        return new Vector();	
        }
	        Vector result = new Vector(); 
	        ReportResultInfo infoTo = new ReportResultInfo();
	        ContractOperation contractOperation = new ContractOperation();
	        long j=0;
	        for (int i = 0; i < infosFrom.length; i++) {
	        	 j++;
	        	 infoTo=transferReportResultInfo(wInfo.getDate(),infosFrom[i],infosFrom,j);
	        	 result.addElement(infoTo); 	        	 
	        }
	        ReportResultInfo commonInfo = buildCommonInfo1(infosFrom[infosFrom.length-1], infosFrom);   
	        result.addElement(commonInfo);
        return result;
            }
	private ReportResultInfo transferReportResultInfo(
			Timestamp firstDay,ReportResultInfo reportResultInfo,
            ReportResultInfo[] infos,long j)
    throws Exception {			
		ReportResultInfo result = new ReportResultInfo();
		String assuretype="";
		if (reportResultInfo.getLongColumn3()>0)
		{	
		assuretype+=LOANConstant.AssureType.getName(reportResultInfo.getLongColumn3())+",";
		}
		if (reportResultInfo.getLongColumn4()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(reportResultInfo.getLongColumn4())+",";
		}
		if (reportResultInfo.getLongColumn5()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(reportResultInfo.getLongColumn5())+",";
		}
		if (reportResultInfo.getLongColumn6()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(reportResultInfo.getLongColumn6())+",";
		}
		if (reportResultInfo.getLongColumn7()>0)
		{
		assuretype+=LOANConstant.AssureType.getName(reportResultInfo.getLongColumn7());
		}
		if (assuretype.endsWith(",")==true)
			{
			 assuretype=assuretype.substring(0,assuretype.length()-1);
			}
			if (assuretype.equals(""))
			{
				assuretype="&nbsp;";
			}
		result.setLongColumn1(j);//序号
		result.setStringColumn4(reportResultInfo.getStringColumn3());//合同编号
		result.setStringColumn1(reportResultInfo.getStringColumn1());//借款单位名称
		result.setDoubleColumn1(reportResultInfo.getDoubleColumn1()/10000);//合同金额
		result.setLongColumn2(reportResultInfo.getLongColumn8());//贷款期限
		result.setTsColumn1(reportResultInfo.getTsColumn1());//贷款起始日期
		result.setTsColumn2(reportResultInfo.getTsColumn2());//贷款结止始日期
		result.setDoubleColumn2(reportResultInfo.getDoubleColumn2());//手续费率
		result.setStringColumn2(assuretype);//反担保方式
		result.setStringColumn3(reportResultInfo.getStringColumn2());//贷款银行
		return result;
	}
	private ReportResultInfo buildCommonInfo1(ReportResultInfo info,
            ReportResultInfo[] infos)throws Exception
	{
	ReportResultInfo result = new ReportResultInfo();
	String StringColumn1="合计";		
	result.setStringColumn4(StringColumn1);//合计
    result.setDoubleColumn1(calcTotal(infos, "DoubleColumn1"));//合同金额
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
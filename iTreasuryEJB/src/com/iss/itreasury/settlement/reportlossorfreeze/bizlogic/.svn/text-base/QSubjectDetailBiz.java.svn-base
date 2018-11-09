package com.iss.itreasury.settlement.reportlossorfreeze.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.settlement.reportlossorfreeze.dao.QSubjectDetailDAO;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.QuerySubjectInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QSubjectDetailBiz {
	
	QSubjectDetailDAO dao=new QSubjectDetailDAO();
	
	/**
	 * 科目明细查询
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public PagerInfo findSubjectDetail( QuerySubjectInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.getSubjectDetailsSQL(info);
			pagerInfo.setSqlString(sql);
			//合计信息	
			//pagerInfo.setUsertext("余额合计{Balance};利息合计{Interest}");
			pagerInfo.setUsertext("借方合计:{mamount}[where ntransdirection = 1];贷方合计:{mamount}[where ntransdirection = 2]");
			pagerInfo.setExtensionMothod(QSubjectDetailBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		return pagerInfo;
}
	
	public ArrayList resultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		Timestamp Dtexecute = null;	//执行日
		Timestamp Dtintereststart = null;	//起息日
		String sTransNo = "";	//交易号
		long Ntransactiontypeid= -1;	//交易类型
		String Ssubjectcode= "";	//科目号
		double mAmount = 0.0;	//金额
		long Ntransdirection=-1;//借贷方向
		String sbankaccountcode="" ;//账户编号用于银行收款的借方和银行付款的贷方
		String senterprisename="";//账户名称用于银行收款的借方和银行付款的贷方
		
		long nreceiveaccountid=-1;
		long npayaccountid=-1;
		
		String AccountNo = "";	//账户号  
		String AccountName = "";	//账户名  
		String SEXTACCOUNTNO= "";//第三方账户号  
		String  SEXTCLIENTNAME="";	//第三方账户名  
		String Sabstract = "";	//摘要
		String InputUserName ="";	//录入人
		String CheckUserName ="";	//复核人
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					Dtexecute = rs.getTimestamp("Dtexecute");
					Dtintereststart=rs.getTimestamp("Dtintereststart");
					sTransNo=rs.getString("stransno");
					Ntransactiontypeid=rs.getLong("ntransactiontypeid");
					Ssubjectcode=rs.getString("ssubjectcode");
					mAmount=rs.getDouble("mamount");
					
					Ntransdirection=rs.getLong("ntransdirection");
					sbankaccountcode=rs.getString("SBANKACCOUNTCODE");
					senterprisename=rs.getString("SENTERPRISENAME");
					
					nreceiveaccountid=rs.getLong("nreceiveaccountid");
					npayaccountid=rs.getLong("npayaccountid");
					
					AccountNo=rs.getString("AccountNo");
					AccountName=rs.getString("AccountName");
					SEXTACCOUNTNO=rs.getString("SEXTACCOUNTNO");
					SEXTCLIENTNAME=rs.getString("SEXTCLIENTNAME");
					Sabstract=rs.getString("sabstract");
					InputUserName=rs.getString("InputUserName");
					CheckUserName=rs.getString("CheckUserName");
					
					//处理数据
					String strDtexecute = DataFormat.getDateString(Dtexecute);
					String strDtintereststart = DataFormat.getDateString(Dtintereststart);
					sTransNo = DataFormat.formatString(sTransNo)+" ";
					String Ntransactiontype = SETTConstant.TransactionType.getName(Ntransactiontypeid);
					String subjectcode = DataFormat.formatString(Ssubjectcode);
					String Amount="";
					if (mAmount>0.0) {
						
						Amount ="￥ "+ DataFormat.formatDisabledAmount(mAmount);
					}
					else {
						Amount="￥ "+"0.00";
						
					}
					//借贷方向
					String transdirection="";
					if(Ntransdirection==2)
					{
						transdirection =" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ SETTConstant.DebitOrCredit.getName(Ntransdirection);
					}
					else {
						transdirection =SETTConstant.DebitOrCredit.getName(Ntransdirection);
					}
					///银行收款借款方
					String accountNoByID = NameRef.getAccountNoByID(nreceiveaccountid);
					String accountNameByID = NameRef.getAccountNameByID(nreceiveaccountid);
					//银行付收款借款方
					String payAccountNoByID = NameRef.getAccountNoByID(npayaccountid);
					String payAccountNameByID = NameRef.getAccountNameByID(npayaccountid);
					
					//账户号
					String accountCode="";
					//账户名
					String accountName="";
					
					//银行收款贷方款方
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKRECEIVE&&Ntransdirection==SETTConstant.DebitOrCredit.DEBIT) {
						accountCode=sbankaccountcode;
						accountName=senterprisename;
						
					}
					//银行收款借款方
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKRECEIVE&&Ntransdirection==SETTConstant.DebitOrCredit.CREDIT) {
						
						accountCode=accountNoByID;
						accountName=accountNameByID;
						
					}
					//银行付收款借款方
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKPAY&&Ntransdirection==SETTConstant.DebitOrCredit.DEBIT) {
						
						accountCode=payAccountNoByID;
						accountName=payAccountNameByID;
					}
					//银行付收款贷款方
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKPAY&&Ntransdirection==SETTConstant.DebitOrCredit.CREDIT) {
						accountCode=sbankaccountcode;
						accountName=senterprisename;
					}
					else {
						
						accountCode=accountNoByID;
						accountName=accountNameByID;
						
					}
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,strDtexecute); 
					PagerTools.returnCellList(cellList,strDtintereststart); 
					PagerTools.returnCellList(cellList,sTransNo);
					PagerTools.returnCellList(cellList,Ntransactiontype);
					PagerTools.returnCellList(cellList,subjectcode);
					PagerTools.returnCellList(cellList,Amount);
					PagerTools.returnCellList(cellList,transdirection);
					PagerTools.returnCellList(cellList,accountCode);
					PagerTools.returnCellList(cellList,accountName);
					PagerTools.returnCellList(cellList,SEXTACCOUNTNO);
					PagerTools.returnCellList(cellList,SEXTCLIENTNAME);
					PagerTools.returnCellList(cellList,Sabstract);
					PagerTools.returnCellList(cellList,InputUserName);
					PagerTools.returnCellList(cellList,CheckUserName);
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	}
	
}

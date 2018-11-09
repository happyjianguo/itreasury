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
	 * ��Ŀ��ϸ��ѯ
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
			//�õ���ѯ��SQL
			sql=dao.getSubjectDetailsSQL(info);
			pagerInfo.setSqlString(sql);
			//�ϼ���Ϣ	
			//pagerInfo.setUsertext("���ϼ�{Balance};��Ϣ�ϼ�{Interest}");
			pagerInfo.setUsertext("�跽�ϼ�:{mamount}[where ntransdirection = 1];�����ϼ�:{mamount}[where ntransdirection = 2]");
			pagerInfo.setExtensionMothod(QSubjectDetailBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣:"+e.getMessage());
		}
		return pagerInfo;
}
	
	public ArrayList resultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		Timestamp Dtexecute = null;	//ִ����
		Timestamp Dtintereststart = null;	//��Ϣ��
		String sTransNo = "";	//���׺�
		long Ntransactiontypeid= -1;	//��������
		String Ssubjectcode= "";	//��Ŀ��
		double mAmount = 0.0;	//���
		long Ntransdirection=-1;//�������
		String sbankaccountcode="" ;//�˻�������������տ�Ľ跽�����и���Ĵ���
		String senterprisename="";//�˻��������������տ�Ľ跽�����и���Ĵ���
		
		long nreceiveaccountid=-1;
		long npayaccountid=-1;
		
		String AccountNo = "";	//�˻���  
		String AccountName = "";	//�˻���  
		String SEXTACCOUNTNO= "";//�������˻���  
		String  SEXTCLIENTNAME="";	//�������˻���  
		String Sabstract = "";	//ժҪ
		String InputUserName ="";	//¼����
		String CheckUserName ="";	//������
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//��ȡ����
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
					
					//��������
					String strDtexecute = DataFormat.getDateString(Dtexecute);
					String strDtintereststart = DataFormat.getDateString(Dtintereststart);
					sTransNo = DataFormat.formatString(sTransNo)+" ";
					String Ntransactiontype = SETTConstant.TransactionType.getName(Ntransactiontypeid);
					String subjectcode = DataFormat.formatString(Ssubjectcode);
					String Amount="";
					if (mAmount>0.0) {
						
						Amount ="�� "+ DataFormat.formatDisabledAmount(mAmount);
					}
					else {
						Amount="�� "+"0.00";
						
					}
					//�������
					String transdirection="";
					if(Ntransdirection==2)
					{
						transdirection =" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ SETTConstant.DebitOrCredit.getName(Ntransdirection);
					}
					else {
						transdirection =SETTConstant.DebitOrCredit.getName(Ntransdirection);
					}
					///�����տ��
					String accountNoByID = NameRef.getAccountNoByID(nreceiveaccountid);
					String accountNameByID = NameRef.getAccountNameByID(nreceiveaccountid);
					//���и��տ��
					String payAccountNoByID = NameRef.getAccountNoByID(npayaccountid);
					String payAccountNameByID = NameRef.getAccountNameByID(npayaccountid);
					
					//�˻���
					String accountCode="";
					//�˻���
					String accountName="";
					
					//�����տ�����
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKRECEIVE&&Ntransdirection==SETTConstant.DebitOrCredit.DEBIT) {
						accountCode=sbankaccountcode;
						accountName=senterprisename;
						
					}
					//�����տ��
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKRECEIVE&&Ntransdirection==SETTConstant.DebitOrCredit.CREDIT) {
						
						accountCode=accountNoByID;
						accountName=accountNameByID;
						
					}
					//���и��տ��
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKPAY&&Ntransdirection==SETTConstant.DebitOrCredit.DEBIT) {
						
						accountCode=payAccountNoByID;
						accountName=payAccountNameByID;
					}
					//���и��տ���
					if (Ntransactiontypeid==SETTConstant.TransactionType.BANKPAY&&Ntransdirection==SETTConstant.DebitOrCredit.CREDIT) {
						accountCode=sbankaccountcode;
						accountName=senterprisename;
					}
					else {
						
						accountCode=accountNoByID;
						accountName=accountNameByID;
						
					}
					
					//�洢������
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
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
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

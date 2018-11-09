package com.iss.itreasury.craftbrother.credit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.credit.dataentity.CreditQueryInfo;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentContractDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentContractInfo;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.util.NameRef;

public class CreditSettingDAO extends SettlementDAO{
	
	public CreditSettingDAO()
	{
		super("CRA_CREDITLIMIT");
	}
	public CreditSettingDAO(Connection conn)
	{
		super("CRA_CREDITLIMIT",conn);
	}
	
	private Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
/**zcq��2006-12-19������
 * �������Ŷ������
 * @param creditInfo
 * @return
 * @throws Exception
 */
	public long saveCredit(CreditSettingInfo creditInfo) throws Exception
	{
		long lMaxID=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			conn=Database.getConnection();
//			boolean validateOverlap=findCreditByDate(creditInfo);
//			if(validateOverlap==true)
//			{
//				throw new IException("Cra_E001");
//			}
			/*��ȡ��ǰ���ݿ�����ID+1*/
			sql="select nvl(max(id)+1,1) lID from CRA_CREDITLIMIT";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				lMaxID=rs.getLong("lID");
			}
			cleanup(rs);
			cleanup(ps);
		    sql="insert into CRA_CREDITLIMIT(id,creditclientid,creditedclientid,creditdirection,creditamount,statusid,";
			sql+=" STARTDATE,ENDDATE,TRANSACTIONTYPE,INPUTUSERID,INPUTDATE,REMARK,CURRENCYID,OFFICEID,attachid) values(?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?)";
			System.out.println(sql);
		    ps=conn.prepareStatement(sql);
		    int index=1;
		    ps.setLong(index++,lMaxID);
		    ps.setLong(index++,creditInfo.getCreditClientID());
		    ps.setLong(index++,creditInfo.getCreditedClientID());
		    ps.setLong(index++,creditInfo.getCreditDirection());
		    ps.setDouble(index++,creditInfo.getAmount());
		    ps.setLong(index++,CRAconstant.TransactionStatus.SAVE);
		    ps.setTimestamp(index++,creditInfo.getStartDate());
		    ps.setTimestamp(index++,creditInfo.getEndDate());
		    ps.setLong(index++,creditInfo.getTransactionType());
		    ps.setLong(index++,creditInfo.getInputUserID());
		    ps.setString(index++,creditInfo.getRemark());
		    ps.setLong(index++,creditInfo.getCurrencyID());
		    ps.setLong(index++,creditInfo.getOfficeID());
		    ps.setLong(index++,creditInfo.getAttachId());
		    ps.executeUpdate();
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lMaxID;
	}
	public long updateCredit(CreditSettingInfo creditInfo) throws Exception
	{
		long lReturn=-1;
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="";
		try
		{
			conn=Database.getConnection();
//			boolean validateOverlap=findCreditByDate(creditInfo);
//			if(validateOverlap==true)
//			{
//				throw new IException("Cra_E001");
//			}
			int index=1;
			sql = "update cra_creditlimit set CREDITAMOUNT=?,STARTDATE=?,ENDDATE=?,TRANSACTIONTYPE=?,REMARK=?,attachid=? where id=?";
			ps=conn.prepareStatement(sql);
			ps.setDouble(index++,creditInfo.getAmount());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setString(index++,creditInfo.getRemark());
			ps.setLong(index++,creditInfo.getAttachId());
		    ps.setLong(index++,creditInfo.getID());
		    int resultPointer = ps.executeUpdate();
			if(resultPointer>0)
			{
			   lReturn = creditInfo.getID();
			}
			cleanup(ps);
			cleanup(conn);
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public  boolean findCreditByDate(CreditSettingInfo creditInfo) throws ITreasuryDAOException, SQLException
	{
		boolean bReturn=false;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try 
		{
			conn = Database.getConnection();
			String sql="";
	        sql = "select * from cra_creditlimit where CREDITCLIENTID ="+creditInfo.getCreditClientID();
	        sql +=" and CREDITEDCLIENTID ="+creditInfo.getCreditedClientID();
	        sql +=" and TRANSACTIONTYPE ="+creditInfo.getTransactionType();
	        sql +=" and CREDITDIRECTION ="+creditInfo.getCreditDirection();
	        sql += " and statusID != "+CRAconstant.TransactionStatus.DELETED;
	        sql += " and statusID !=" + CRAconstant.TransactionStatus.REFUSED;
	        sql += " and ((STARTDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and ENDDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd'))";
	        
	        sql += " or (STARTDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and STARTDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd'))";
	        
	        sql += " or (ENDDATE>=To_Date('" + DataFormat.getDateString(creditInfo.getStartDate())+ "','yyyy-mm-dd')";
	        sql +=  " and ENDDATE<=To_Date('" + DataFormat.getDateString(creditInfo.getEndDate())+ "','yyyy-mm-dd')))";
	        if (creditInfo.getID() > 0) 
	        {
	          sql += " and id !=" + creditInfo.getID();
	        }  
	        System.out.println("����Ƿ��Ѿ�����ĳ�ֽ���ĳ��ʱ��ε�ʱ�����õ�SQL:"+sql);
		    ps=conn.prepareStatement(sql);
		    rs=ps.executeQuery();
		    while( rs.next())
		    {
		    	bReturn = true;
		    }
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		} catch (Exception e) 
		{
	        throw new ITreasuryDAOException("���ݽ������ͺ�ʱ��β�ѯ��������ʱ����", e);
	    }finally
		{
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}
		return bReturn;
	}
	
	
	/**
	 * �����������õ�״̬����ȡ�����û�����˵�ʱ���õ�
	 * @param lID
	 * @param statusID
	 * @return
	 * @throws Exception
	 */
	public long updateStatus(long lID,long statusID) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="";
		long lReturn = -1;
		try
		{
			conn = Database.getConnection();
			sql =" update cra_creditlimit set STATUSID = ? where id=?";
			ps 	= conn.prepareStatement(sql);
			int index = 1;
			ps.setLong(index++,statusID);
			ps.setLong(index++,lID);
			int resultPointer = ps.executeUpdate();
			System.out.println(resultPointer);
			if(resultPointer>0)
			{
				lReturn = lID;
			}
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e){
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	public long updateAllStatus(long lID,long statusID,long checkStatus) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		//String sql="";
		long lReturn = -1;
		//long creditID= -1;
		try
		{
			CreditSettingInfo creditInfo =null;
			CreditSettingDAO creditSettingDao = new CreditSettingDAO();
			creditInfo = creditSettingDao.findCreditByID(lID);
			conn = Database.getConnection();
			String strSQL = "update cra_creditlimit";
				strSQL += " set STATUSID = ?";
				strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=?";
				strSQL += " and TRANSACTIONTYPE=? and STARTDATE=?";
				strSQL += " and ENDDATE=? and STATUSID=?";
			//String strSQL = "select id  from cra_creditlimit";
			//strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=? and TRANSACTIONTYPE=? and STARTDATE=? and ENDDATE=? and STATUSID=?";
			ps 	= conn.prepareStatement(strSQL);
			int index=1;
			ps.setLong(index++,statusID);
			ps.setLong(index++,creditInfo.getCreditClientID());
			ps.setLong(index++,creditInfo.getCreditedClientID());//�޶������ŷ� �ǲ���˾
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			ps.setLong(index++,checkStatus);
			
			lReturn =ps.executeUpdate();
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e){
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	
	/**
	 * ����ID��ѯ���Ŷ���������
	 * @param lID
	 * @return
	 * @throws IException
	 * @throws SQLException
	 */
	public CreditSettingInfo findCreditByID(long lID) throws IException, SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		CreditSettingInfo creditInfo=new CreditSettingInfo();
		try
		{
			conn=Database.getConnection();
			sql = "select * from CRA_CREDITLIMIT where id="+lID;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				creditInfo = new CreditSettingInfo();
				creditInfo.setID(rs.getLong("id"));
				creditInfo.setCreditClientID(rs.getLong("creditclientid"));
				creditInfo.setCreditedClientID(rs.getLong("creditedclientid"));
				creditInfo.setCreditDirection(rs.getLong("creditdirection"));
				creditInfo.setAmount(rs.getDouble("creditamount"));
				creditInfo.setStatusID(rs.getLong("statusid"));
				creditInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				creditInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				creditInfo.setTransactionType(rs.getLong("TRANSACTIONTYPE"));
				creditInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				creditInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				creditInfo.setRemark(rs.getString("REMARK"));
				creditInfo.setCurrencyID(rs.getLong("CURRENCYID"));
				creditInfo.setOfficeID(rs.getLong("OFFICEID"));
				creditInfo.setAttachId(rs.getLong("attachid"));
			}
		    cleanup(rs);
		    cleanup(ps);
		    cleanup(conn);
		}catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return creditInfo;
	}

	public Collection findCreditByCondition(CreditQueryInfo queryInfo) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Vector vec = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		CreditSettingInfo creditInfo = null;
		try
		{
			conn = Database.getConnection();
			sbSQL.append(" select * from CRA_CREDITLIMIT where CURRENCYID="+queryInfo.getCurrencyID()+" and OFFICEID="+queryInfo.getOfficeID());
			if(queryInfo.getTransactionType()>=0)
			{
				sbSQL.append(" and TRANSACTIONTYPE = "+queryInfo.getTransactionType());
			}
			if(queryInfo.getStartDate()!=null )
			{
				sbSQL.append(" and TRUNC(InputDate)>= To_Date('" + DataFormat.getDateString(queryInfo.getStartDate()) + "','yyyy-mm-dd')");
			}
			if(queryInfo.getEndDate()!=null)
			{
				sbSQL.append(" and TRUNC(InputDate) <=To_Date('" + DataFormat.getDateString(queryInfo.getEndDate()) + "','yyyy-mm-dd')");
			}
			if(queryInfo.getCounterParterID()>0)
			{
				sbSQL.append(" and( CREDITDIRECTION = '1' and CREDITEDCLIENTID ="+queryInfo.getCounterParterID());
				sbSQL.append(" or CREDITDIRECTION=2 and CREDITCLIENTID ="+queryInfo.getCounterParterID()+")");
			}
			if(queryInfo.getMinAmount()>0)
			{
				sbSQL.append(" and CREDITAMOUNT >= "+queryInfo.getMinAmount());
			}
			if( queryInfo.getMaxAmount()>0)
			{
				sbSQL.append(" and CREDITAMOUNT<="+queryInfo.getMaxAmount());
			}
			if( queryInfo.getStatusID()>=0)
			{
				sbSQL.append(" and STATUSID = "+queryInfo.getStatusID());
			}
			sbSQL.append(" order by id ");
			System.out.println(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while( rs.next())
			{
				creditInfo = new CreditSettingInfo();
				creditInfo.setID(rs.getLong("id"));
				creditInfo.setCreditClientID(rs.getLong("creditclientid"));
				creditInfo.setCreditedClientID(rs.getLong("creditedclientid"));
				creditInfo.setCreditDirection(rs.getLong("creditdirection"));
				creditInfo.setAmount(rs.getDouble("creditamount"));
				creditInfo.setStatusID(rs.getLong("statusid"));
				creditInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				creditInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				creditInfo.setTransactionType(rs.getLong("TRANSACTIONTYPE"));
				creditInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				creditInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				creditInfo.setRemark(rs.getString("REMARK"));
				creditInfo.setCurrencyID(rs.getLong("CURRENCYID"));
				creditInfo.setOfficeID(rs.getLong("OFFICEID"));
				creditInfo.setAttachId(rs.getLong("attachid"));
				creditInfo.setTerm(DataFormat.getIntervalDays(creditInfo.getStartDate(),creditInfo.getEndDate()));
				long counterpartID=-1;
				long clientID=-1;
				if(creditInfo.getCreditDirection()==1)
				{
				   clientID = creditInfo.getCreditClientID();
				   counterpartID = creditInfo.getCreditedClientID();
				}else if(creditInfo.getCreditDirection()==2)
				{
				   clientID = creditInfo.getCreditedClientID();
				   counterpartID = creditInfo.getCreditClientID();
				}	
				String counterpartCode=com.iss.itreasury.securities.util.NameRef.getCounterpartCodeByID(counterpartID);
				String counterpartName =com.iss.itreasury.securities.util.NameRef.getCounterpartNameByID(counterpartID);
				String clientCode=com.iss.itreasury.settlement.util.NameRef.getClientCodeByID(clientID);
				String clientName=com.iss.itreasury.settlement.util.NameRef.getClientNameByID(clientID);
			    
				if(creditInfo.getCreditDirection()==1)
				{
				    creditInfo.setCreditClientCode(clientCode);
				    creditInfo.setCreditClientName(clientName);
				    creditInfo.setCreditedClientCode(counterpartCode);
				    creditInfo.setCreditedClientName(counterpartName);
				}
				else if(creditInfo.getCreditDirection()==2)
				{
				    creditInfo.setCreditClientCode(counterpartCode);
				    creditInfo.setCreditClientName(counterpartName);
				    creditInfo.setCreditedClientCode(clientCode);
				    creditInfo.setCreditedClientName(clientName);
				}
				vec.add(creditInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return vec.size()>0?vec:null;
	}
    /**
     * @��֤ĳ�����ͬת�������Ƿ����㽻�׶��ֶԲ���˾����Ա��λ������Ҫ��
     * @param info
     * @param conInfo
     * @return
     * @throws Exception 
     */
	public boolean checkUseableCreditAmount(AttornmentApplyInfo info,AttornmentContractInfo[] conInfo,long transactionType) throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Vector vec = new Vector();
		String strSQL = "";
		boolean bReturn = false;
		
		long financeID = -1;//����˾��Ӧ�Ŀͻ�ID
		long counterPartID = -1;//���׶���ID
		String counterPartName ="";//���׶�������
		long clientID = -1; //�ͻ�ID
		String clientName = ""; //�ͻ����� 
		double financeUseableCredit = 0.00;  //��ͬ�жԲ���˾�Ŀ������Ŷ��
		double clientUseableCredit = 0.00; //��ͬ�ж�ĳ�ͻ��Ŀ������Ŷ��
		double creditAmount = 0.00;//���Ŷ��
		double usedCreditAmount = 0.00; //��ʹ�����Ŷ��
		Timestamp transactionStartDate = null;//������ʼʱ��
		Timestamp transactionEndDate = null;//���׽���ʱ��
		Timestamp creditStartDate = null;//������ʱ����������ĳ�������ŵ�ʱ�������У����ſ�ʼʱ��
		Timestamp creditEndDate = null;//������ʱ����������ĳ�����ŵ�ʱ�������У����Ž���ʱ��
		long lID = info.getRepurchaseApplyId();//�ʲ�ת������ID
		CreditSettingInfo creditInfo =null;
		try
		{
			conn = Database.getConnection();
        	//�����ʲ�ת������ID�齻�׶�����Ϣ
        	strSQL = "select b.id ID,b.Name NAME,a.TRANSACTIONSTARTDATE,a.TRANSACTIONENDDATE  from sec_applyform a,SEC_Counterpart b  where a.COUNTERPARTID=b.id  and a.id ="+lID;
        	System.out.println("�����ʲ�ת������齻�׶�����Ϣ��SQL:"+strSQL);
        	ps=conn.prepareStatement( strSQL );
        	rs = ps.executeQuery();
        	if (rs.next())
        	{
         		counterPartID = rs.getLong("ID");	
         		counterPartName = rs.getString("NAME");
         		transactionStartDate = rs.getTimestamp("TRANSACTIONSTARTDATE");
         		transactionEndDate = rs.getTimestamp("TRANSACTIONENDDATE");
        	}
           	cleanup(rs);
        	cleanup(ps);
        	//��ȡ����˾��Ӧ�Ŀͻ�ID
        	creditInfo = new CreditSettingInfo();
        	financeID = CRANameRef.getPartenerInfo(1).getClientID();
        	//��ѯ��ͬ�жԲ���˾���������
        	creditInfo = findCreditAmountByDate(transactionStartDate,transactionEndDate,counterPartID,financeID,2,transactionType,counterPartName,"����˾");
        	creditStartDate = creditInfo.getStartDate();
        	creditEndDate = creditInfo.getEndDate();
        	creditAmount = creditInfo.getAmount()*10000;//���Ŷ�ȵĵ�λ����

			//�������Ĵ����ͬת���ܽ���Ƿ���ͬ�жԲ���˾�Ŀ������Ŷ�ȷ�Χ֮��
        	System.out.println("-----------��ʼ�Բ���˾�������ż��---------------------��");
        	strSQL = "select nvl(sum(a.ATTORNMENTAMOUNT),0) usedCreditAmount"
        		+" from sec_attornmentapplyform a,sec_applyform b"
        		+" where b.TRANSACTIONTYPEID ="+transactionType
        		+" and a.REPURCHASEAPPLYID = b.id "
        		+" and( a.STATUSID ="+LOANConstant.AttornmentStatus.SUBMIT
        		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.CHECK
        		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.USED+")"
        		+" and b.COUNTERPARTID ="+counterPartID
          		+" and b.TRANSACTIONSTARTDATE>= To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
        	if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
        	{
        		strSQL += " and b.TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
        	}else
        	{
        		strSQL += " and b.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
        	}
        	if(info.getId()>0)
        	{
        		//���ID>0(��Updateʱ)���ۻ���ʹ�����Ŷ�ȵ�ʱ��Ӧ��ȥ���ıȴ����ͬת������
        		strSQL+=" and b.id!="+info.getId();
        	}
        	System.out.println("�����ʲ�ת������id������ͬת������ID��SQL:"+strSQL);
        	ps=conn.prepareStatement( strSQL );
        	rs=ps.executeQuery() ;
        	if (rs.next())
        	{
        		usedCreditAmount = rs.getDouble("usedCreditAmount");
        	}
        	cleanup(rs);
        	cleanup(ps);
        	
        	financeUseableCredit = creditAmount-usedCreditAmount;
        	if(info.getAttornmentAmount()>financeUseableCredit)
        	{
        		String exceptionMess= " �ô����ͬת�������ת�ý��"+DataFormat.formatDisabledAmount(info.getAttornmentAmount())+"Ԫ������\""
        			   + counterPartName+"\"�Բ���˾����"+CRAconstant.TransactionType.getName(transactionType)+"�����������ŵĿ��ö�ȣ�Ŀǰ����˾�Ŀ������Ŷ��Ϊ"+DataFormat.formatDisabledAmount(financeUseableCredit,2)+"Ԫ"
        		       +" ���޸ĺ����ύ";
        		throw new IException(exceptionMess);	
        	}
        	System.out.println("-----------�Բ���˾���ż�����---------------------��");

        	
        	// ���ݺ�ͬ����Ӧ�ĳ�Ա��λ�������ż��
        	System.out.println("------------------��ʼ���ݺ�ͬ����Ӧ�ĳ�Ա��λ�������ż��---------------------------");
        	for (int i=0;i<conInfo.length ;i++)
        	{	
        		AttornmentContractDao dao=new AttornmentContractDao();
    		    AttornmentApplyDao applyDao = new AttornmentApplyDao();
    		    ContractDao conDao = new ContractDao();
    		    String contractCode ="";//��ͬ��ţ�����������Ϣ��
    		    long attContractID =-1;//�����ͬת�����룭��ͬ��ID
    		    attContractID = conInfo[i].getId();//�����ͬת�����룭��ͬ��ID
        		if (attContractID>0)
				{
					AttornmentContractInfo attContractInfo=(AttornmentContractInfo)dao.findByID( attContractID,AttornmentContractInfo.class );
					clientID = attContractInfo.getClientId();
					contractCode = attContractInfo.getContractCode();
				}	
        		else
        		{
        			//�����ݺ�ͬID�ҳ���Ӧ�Ŀͻ�ID
					ContractInfo cInfo = conDao.findByID(conInfo[i].getContractId());
					clientID = cInfo.getBorrowClientID();
					contractCode = cInfo.getContractCode();
        		}
        		System.out.println("------------------------------clientID:"+clientID);
				clientName =NameRef.getClientNameByID(clientID);//��ȡ�ͻ����ƣ����쳣��Ϣ��ʹ��
        		//��ѯ�ý��׶��ֶԺ�ͬ��Ӧ�ĳ�Ա��λ���������
            	creditInfo = findCreditAmountByDate(transactionStartDate,transactionEndDate,counterPartID,clientID,2,transactionType,counterPartName,clientName);
            	creditStartDate = creditInfo.getStartDate();
            	creditEndDate = creditInfo.getEndDate();
            	creditAmount = creditInfo.getAmount()*10000;//���Ŷ�ȵĵ�λ����
				
            	strSQL = "select nvl(sum(a.ATTORNMENTAMOUNT),0) usedCreditAmount"
            		+" from sec_attornmentapplyform a,sec_applyform b,SEC_AttornmentContract c"
            		+" where b.TRANSACTIONTYPEID ="+transactionType
            		+" and a.REPURCHASEAPPLYID = b.id and c.ATTORNMENTAPPLYID=a.id"
            		+" and( a.STATUSID ="+LOANConstant.AttornmentStatus.SUBMIT
            		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.CHECK
            		+" or a.STATUSID ="+LOANConstant.AttornmentStatus.USED+")"
            		+" and b.COUNTERPARTID ="+counterPartID
            	    +" and c.CLIENTID="+clientID
            		+" and b.TRANSACTIONSTARTDATE>= To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
            	if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY)
            	{
            		strSQL += " and b.TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
            	}else
            	{
            		strSQL += " and b.TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
            	}
            	if(attContractID>0)
            	{
            		strSQL+=" and c.id!="+attContractID;
            	}
            	System.out.println("�����Ա��λ"+clientName+"���Ŷ�ȵ�SQL:"+strSQL);
             	ps=conn.prepareStatement( strSQL );
            	rs=ps.executeQuery() ;
            	if (rs.next())
            	{
            		usedCreditAmount = rs.getDouble("usedCreditAmount");
            	}
            	cleanup(rs);
            	cleanup(ps);
            	
            	clientUseableCredit = creditAmount-usedCreditAmount;
            	if(conInfo[i].getAttornmentAmount()>clientUseableCredit)
            	{
            		String exceptionMess= " �ô����ͬת�������еı��Ϊ"+contractCode+"�ĺ�ͬ��ת�ý��"+DataFormat.formatDisabledAmount(info.getAttornmentAmount())+"Ԫ������\""
            			   + counterPartName+"\"�Ըú�ͬ�Ľ��ͻ�\""+clientName+"\"����"+CRAconstant.TransactionType.getName(transactionType)+"�����������ŵĿ��ö�ȣ�Ŀǰ�ͻ�\""+clientName+"\"�Ŀ������Ŷ��Ϊ"+DataFormat.formatDisabledAmount(clientUseableCredit,2)+"Ԫ"
            		       +" ���޸ĺ����ύ";
            		throw new IException(exceptionMess);	
            	}
        	}
        	System.out.println("-----------�Գ�Ա��λ���ż�����---------------------��");
        	bReturn = true;
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
			throw e;
		}finally
		{
		  	cleanup(rs);
        	cleanup(ps);
			cleanup(conn);
		}
		return bReturn;
	}
	/** 
	 *  ����ʱ�䣬���׶���ID���ͻ�ID��ѯ�Ľ��׶��ֶԸĿͻ������Ŷ��
	 * @param startDate  ���׿�ʼʱ��
	 * @param endDate    ���׽���ʱ��
	 * @param clientID   �ͻ�ID���ڲ���λ��
	 * @param counterpartID  ���׶��ֱ��
	 * @return amountReturn �������Ŷ��
	 * @throws Exception 
	 */
	public CreditSettingInfo findCreditAmountByDate(Timestamp startDate,Timestamp endDate,long counterpartID,long clientID,long creditDirection,long transactionType,String counterPartName,String clientName)
	     throws Exception
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CreditSettingInfo returnInfo =null;
		try
		{ 
			conn = Database.getConnection();
			String strSQL = "select ID,CREDITAMOUNT creditAmount,STARTDATE,ENDDATE from cra_creditlimit ";
			if(creditDirection==1)
			{
				strSQL += " where CREDITEDCLIENTID ="+counterpartID;
			}
			else if(creditDirection==2)
			{
				strSQL +="where CREDITCLIENTID ="+counterpartID+" and CREDITEDCLIENTID ="+clientID;
			}
			strSQL +=" and STARTDATE<=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
			//��Ϊ��ͬ��������ϣ������ڽ�������һ˵������ֻ��ͨ����ʼ������ȷ�����ŵ�ʱ������
//			if(transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY ||transactionType==SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY)
//			{
//				strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
//			}else
//			{
//			    strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(endDate) + "','yyyy-mm-dd')";
//			}
			
			//modified by qhzhou 2009-04-16 ֻͨ����ʼ������ȷ�����ŵ�ʱ������
			strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(startDate) + "','yyyy-mm-dd')";
			
			strSQL += " and TRANSACTIONTYPE ="+transactionType;
			strSQL += " and STATUSID in("+CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED+")";
			//added by qhzhou 2009-04-03ȡ����������Զ����������
			strSQL += " order by ENDDATE desc";
			
			System.out.println("����ʱ������ͽ��׶��ֲ�ѯ���������SQL:"+strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if( rs.next())
			{ 
				returnInfo = new CreditSettingInfo();
				returnInfo.setAmount( rs.getDouble("creditAmount"));
				returnInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				returnInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				returnInfo.setID(rs.getLong("ID"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			if(returnInfo==null)
			{
				String exceptionMess ="";
				if(creditDirection==1)
				{
					 exceptionMess= "�Բ���û���ҵ�����˾��\""+counterPartName+"\"����"+CRAconstant.TransactionType.getName(transactionType)+
			         "�������͵Ĳ����뽻��ʱ�������ϵ����Ŷ�����ü�¼���뵽ͬҵ����ģ�����������Ϣ";
				}
			    else if(creditDirection==2)
			    {	
				 exceptionMess= "�Բ���û���ҵ�"+counterPartName+"��"+clientName+"����"+CRAconstant.TransactionType.getName(transactionType)+
				         "�������͵Ĳ����뽻��ʱ�������ϵ����Ŷ�����ü�¼���뵽ͬҵ����ģ�����������Ϣ";
			    }		
				throw new IException(exceptionMess);	
			}
		}catch(ITreasuryDAOException  e)
		{
			log4j.error(e.toString());
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return returnInfo;
	}

	public String checkRepurchaseCredit(long creditID) throws SQLException, IException
	{
		String sReturn="";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CreditApprovalDAO creditApprovalDao = null;
		CreditSettingDAO creditSettingDao = null;
		CreditSettingInfo creditInfo =null;
		double clientCreditTotal = 0.00;//�ý��׶��ֶ����г�Ա��λ���ڴ��ֽ������͵����Ŷ���ܺ�
		try
		{
			conn = Database.getConnection();
			creditSettingDao = new CreditSettingDAO();
			creditInfo = creditSettingDao.findCreditByID(creditID);
			String strSQL = "select  nvl(sum(CREDITAMOUNT),0) clientCreditTotal from cra_creditlimit";
			strSQL += " where CREDITCLIENTID= ? and CREDITEDCLIENTID!=? and TRANSACTIONTYPE=? and STARTDATE=? and ENDDATE=? and STATUSID=1";
			System.out.println(strSQL);
			ps = conn.prepareStatement(strSQL);
			int index=1;
			ps.setLong(index++,creditInfo.getCreditClientID());
			ps.setLong(index++,creditInfo.getCreditedClientID());//�޶������ŷ� �ǲ���˾
			ps.setLong(index++,creditInfo.getTransactionType());
			ps.setTimestamp(index++,creditInfo.getStartDate());
			ps.setTimestamp(index++,creditInfo.getEndDate());
			rs = ps.executeQuery();
			if(rs.next())
			{
				clientCreditTotal = rs.getDouble("clientCreditTotal");
			}
			//�˴��ϸ�����˵��������⣬����double���͵�ֵ������ڶ����ƴ洢������ĵĹ�ϵ�п��ܵó��Ľ����ϸ΢��ƫ�
			//���±�����ȵģ��������ȣ����������Ҫ�Ļ���Ӧ�ý���һЩ���⴦��
			if(creditInfo.getAmount()!=clientCreditTotal)
			{
				sReturn = "\\\""+NameRef.getCounterpartNameByID(creditInfo.getCreditClientID())
				+"\"����"+CRAconstant.TransactionType.getName(creditInfo.getTransactionType())
				+"�������ͣ��Բ���˾���ŵ����Ŷ��"+"Ӧ�õ���������ͬ���������ڶ����г�Ա��λ�����Ŷ�ȵ��ܺ�";
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return sReturn;
	}
}

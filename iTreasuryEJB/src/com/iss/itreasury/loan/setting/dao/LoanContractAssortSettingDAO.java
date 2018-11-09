/* Generated by Together */

package com.iss.itreasury.loan.setting.dao;

import com.iss.itreasury.clientmanage.dao.CimsBaseDao;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.setting.dataentity.LoanContractAssortSettingInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
/**
*�û��Զ����ֶ�DAO����Ӧ�ڱ�Loan_ContractAssortSetting
* */
public class LoanContractAssortSettingDAO extends CimsBaseDao {
	
	public LoanContractAssortSettingDAO() {
		super("Loan_ContractAssortSetting");
	}
    public LoanContractAssortSettingDAO(Connection con) {
    	super("Loan_ContractAssortSetting",con);
    }

    
    /**
     *����û��Զ����ֶζ����е����е����Զ�����Ϣ��
     * @author
     * @return    LoanContractAssortSettingInfo
     * */
    public Vector findAttributeInfo(long officeID,long currencyID) throws SQLException, ITreasuryDAOException{
    	StringBuffer strSQL = new StringBuffer();
    	
		strSQL.append("SELECT * FROM Loan_ContractAssortSetting \n");
		strSQL.append("WHERE OfficeID = "+officeID+" and currencyID ="+currencyID+" order by FieldID\n");
		
		Vector v = new Vector();
		try {
			transPS = transConn.prepareStatement(strSQL.toString());
			
			transRS = transPS.executeQuery();
			while(transRS.next())
			{
				LoanContractAssortSettingInfo Info = new LoanContractAssortSettingInfo();
				Info.setId(transRS.getLong("ID"));
				Info.setFieldID(transRS.getLong("FieldID"));
				Info.setName(transRS.getString("name"));
				v.add(Info);	
			} 
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}

		return (v);

    }
    
    /**
     * ����
     * @param dataEntity
     * @throws Exception
     * @return     �ɹ�������ֵ=1 �� ʧ�ܣ�����ֵ=-1 ��
     */
    /*
    public long insert(LoanContractAssortSettingInfo dataEntity) throws SQLException, ITreasuryDAOException
    {
    	long id = 0;
    	long lReturn = -1;
    	StringBuffer strSQL = new StringBuffer();
    	StringBuffer ssql = new StringBuffer();
    	try {
    		//��ȡ���id
    		ssql.append("select nvl(max(id),0)+1 as id from Loan_ContractAssortSetting");
    		transPS = transConn.prepareStatement(ssql.toString());
    		transRS = transPS.executeQuery();
    		while(transRS.next())
    		{
    			id = transRS.getLong("id");
    		}
    		dataEntity.setID(id);
    		strSQL.append(" insert into Loan_ContractAssortSetting \n");
    		strSQL.append(" (FieldID,name,InputUserID,InputDate,StatusID) \n");
    		strSQL.append(" values(?,?,?,?,?,?) \n");
		
			transPS = transConn.prepareStatement(strSQL.toString());
			transPS.setLong(1,id);
    		transPS.setLong(2,dataEntity.getFieldID());
    		transPS.setString(3,dataEntity.getName());
    		transPS.setLong(4,dataEntity.getInputUserID());
    		transPS.setTimestamp(5,dataEntity.getInputDate());
    		transPS.setLong(6,dataEntity.getStatusID());
    		
			lReturn = transPS.executeUpdate();
			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}
    	
		return (lReturn);
			   	
    }
    */
    /**
     * ����
     * @param dataEntity
     * @return		�ɹ�������ֵ=1 �� ʧ�ܣ�����ֵ=-1 ��
     * @throws Exception
     * @throws Exception
     */
    /*
    public long update(LoanContractAssortSettingInfo dataEntity) throws SQLException, ITreasuryDAOException
    {
    	long lReturn = -1;
    	StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("UPDATE Loan_ContractAssortSetting set \n");
//		strSQL.append(" FieldID = " + dataEntity.getFieldID()+" \n");
		strSQL.append(" name = '" + dataEntity.getName() + "'  \n");
		strSQL.append(" , InputUserID = " + dataEntity.getInputUserID() + " \n");
		strSQL.append(" , InputDate = " + dataEntity.getInputDate() + "  \n");
		strSQL.append(" , StatusID = " + dataEntity.getStatusID() + "  \n");
		strSQL.append(" where FieldID = " + dataEntity.getFieldID()+" \n");
		
		try {			
			transPS = transConn.prepareStatement(strSQL.toString());
			lReturn = transPS.executeUpdate();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

    	return (lReturn);
    }
    */
	/**
	 * @param ����FieldID�Ƿ��Ѵ���
	 * @return	 �Ѵ��ڣ�����ֵ=1 �� ���򣬷���ֵ=-1 ��
	 */
	public long checkByFieldInfo(LoanContractAssortSettingInfo dataEntity) throws SQLException, ITreasuryDAOException
	{
		long lReturn = -1;
    	StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT * FROM Loan_ContractAssortSetting \n");
		strSQL.append(" WHERE name = '"+dataEntity.getName()+"' AND OFFICEID='"+dataEntity.getOfficeID()+"\n");
		if(dataEntity.getFieldID() > 0)
		{
			strSQL.append(" AND FieldID != "+dataEntity.getFieldID()+" \n");
		}

		try {
			transPS = transConn.prepareStatement(strSQL.toString());
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next()) {
				lReturn = 1;
			}
			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}

		return (lReturn);

	}
	
	/**
	 * �����Զ����ֶ�id����������
	 * @param FieldID
	 * @return strName;
	 * @throws SQLException
	 * @author huiyu
	 */
	public String getFieldname(int FieldID) throws SQLException
	{	
		String strName = "";
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT name FROM Loan_ContractAssortSetting \n");
		strSQL.append(" WHERE fieldid = ? \n");
		
		transPS = transConn.prepareStatement(strSQL.toString());
		transPS.setLong(1, FieldID);
		transRS = transPS.executeQuery();
		
		while (transRS.next())
		{
			strName = transRS.getString("name");
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {			
			e.printStackTrace();
		}
		
		return strName;
		}

	public String getFieldname(long FieldID, long officeID,long currencyID) throws SQLException
	{	
		String strName = "";
		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT name FROM Loan_ContractAssortSetting \n");
		strSQL.append(" WHERE fieldid = ? \n");
		strSQL.append(" and officeid = ? \n");
		strSQL.append(" and currencyID = ? \n");
		
		transPS = transConn.prepareStatement(strSQL.toString());
		transPS.setLong(1, FieldID);
		transPS.setLong(2, officeID);
		transPS.setLong(3, currencyID);
		
		transRS = transPS.executeQuery();
		
		while (transRS.next())
		{
			strName = transRS.getString("name");
		}
		try {
			finalizeDAO();
		} catch (ITreasuryDAOException e) {			
			e.printStackTrace();
		}
		
		return strName;
	}
}
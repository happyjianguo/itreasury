/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transadjustinterest.dao;

import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfoQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
/**
 * @author feiye
 * 
 * �ۼƷ��ü���Ϣ����DAO
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class Sett_AdjustInterestDao extends SettlementDAO{
	//���캯��
	public Sett_AdjustInterestDao()
    {
        super.strTableName = "Sett_AdjustInterest";
    }
	//��̨����������
    public static void main(String[] args)
    {
 
    }
	
	//����ID���в���һ��������Ϣ
	public AdjustInterestInfo findAdjustByID(long lID) throws SettlementDAOException,ITreasuryDAOException{
		
		System.out.println("����findAdjustByID����");
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AdjustInterestInfo adjustInfo=null;
        try
        {
            conn = this.getConnection();
            StringBuffer buffer = new StringBuffer("");
            //ƴ�ղ�ѯ���
            buffer.append(" select * ");

            buffer.append(" from " + this.strTableName + " \n");
            buffer.append(" where nStatusID != " + Constant.RecordStatus.INVALID + " \n");
            buffer.append(" and ID=" + lID);
            
         
            String sql = buffer.toString();

            Log.print("---SQL:"+sql);

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rs.next()){
            	adjustInfo=new AdjustInterestInfo();
            	//
            	adjustInfo.setLID(rs.getLong("id"));						//��¼ID
            	adjustInfo.setLOfficeID(rs.getLong("nOfficeID"));			//���´�ID
            	adjustInfo.setLCurrencyID(rs.getLong("nCurrencyID"));		//����ID
            	//
            	adjustInfo.setLAccountID(rs.getLong("nAccountID"));		//�˻�ID
            	adjustInfo.setLContractID(rs.getLong("nContractID"));	//��ͬID
            	adjustInfo.setLDueBillID(rs.getLong("nDueBillID"));		//�ſ�֪ͨ��ID
            	//
            	adjustInfo.setLAddOrReduce(rs.getLong("nAddOrRedueID"));	//�ӻ�� 1:�� 2:��
            	adjustInfo.setDInterest(rs.getDouble("mInterest"));			//�ۼ���Ϣ������
            	adjustInfo.setDCommission(rs.getDouble("mCommission"));		//�ۼ������ѵ�����
            	adjustInfo.setDSuretyFee(rs.getDouble("mSuretyFee"));		//�ۼƵ����ѵ�����
            	adjustInfo.setDInterestTax(rs.getDouble("mInterestTax"));	//�ۼ���Ϣ˰�ѵ�����
            	
            	//
            	adjustInfo.setLInputUserID(rs.getLong("nInputUserID"));		//������ID
            	adjustInfo.setTsInput(rs.getTimestamp("dtInput"));			//��������
            	adjustInfo.setLCheckUserID(rs.getLong("nCheckUserID"));		//������ID
            	adjustInfo.setTsCheck(rs.getTimestamp("dtCheck"));			//��������
            	//
            	adjustInfo.setSAdjustReason(rs.getString("sAdjustReason"));	//��ע
            	adjustInfo.setLStatusID(rs.getLong("nStatusID"));			//��¼״̬(0:ɾ��,1:����,2:����)
            	adjustInfo.setTsExecute(rs.getTimestamp("dtExecute"));		//ִ����
            	//
            	adjustInfo.setLDepositTypeID(rs.getLong("NDEPOSITTYPE"));		//�������
            	//
            	adjustInfo.setLSubAccountID(rs.getLong("NSUBACCOUNTID"));		//���˻�ID

            	//
            	System.out.println("======�õ�һ����¼(adjustInfo)  DInterest:"+adjustInfo.getDInterest());
            }
        }
        catch(SQLException sqle){
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return adjustInfo;
	}
	
	//����������������ҵ�����Ϣ����
	public Collection findAdjustByCondition(AdjustInterestInfoQuery info) throws SettlementDAOException,ITreasuryDAOException{
		Vector vct=new Vector();
		System.out.println("����findAdjustByCondition����");
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AdjustInterestInfo adjustInfo=null;
        try
        {
            conn = this.getConnection();
            StringBuffer buffer = new StringBuffer("");
            //ƴ�ղ�ѯ���
            buffer.append(" select * ");

            buffer.append(" from " + this.strTableName + " \n");
            buffer.append(" where 1=1 \n");
            
            if(info.getLStatusID()==-1){
            	//ȫ��(������ɾ����)
            	buffer.append(" and nStatusID !=" + Constant.RecordStatus.INVALID+ " \n");
            	
            }else {
            	//����״̬��ֵ 0:ɾ�� 1:���� 2:����
            	buffer.append(" and nStatusID=" + info.getLStatusID()+ " \n");
            }
            
            if(info.getSAccountNoStart()!=-1)	//�˻�ID
            	buffer.append(" and nAccountID =" + info.getSAccountNoStart()+ " \n");
            
            if(info.getSContractNoStart()!=-1)	//��ͬID
            	buffer.append(" and nContractID =" + info.getSContractNoStart()+ " \n");
            
            if(info.getSDueBillCodeStart()!=-1)	//�ſ�֪ͨ��ID
            	buffer.append(" and nDueBillID =" + info.getSDueBillCodeStart()+ " \n");
            
            
            //��Ϣ����
            if (info.getTsDateStart()!=null)	
            {
            	buffer.append(" and dtExecute >=  to_date('" + DataFormat.getDateString( info.getTsDateStart() ) + "','yyyy-mm-dd')");
            }
            if (info.getTsDateEnd()!=null)	
            {
            	buffer.append(" and dtExecute <= to_date('" + DataFormat.getDateString( info.getTsDateEnd() ) + "','yyyy-mm-dd')");
            }
            
            buffer.append(" and nOfficeID=" + info.getLOfficeID());
            buffer.append(" and nCurrencyID=" + info.getLCurrencyID());
            
            String strSQL = buffer.toString();
            
            //����ĸ��ֶν�������
			switch ((int) info.getLOrderParam()) {
			case 1:
				strSQL += " order by ID ";
				break;
			case 2:
				strSQL += " order by mInterest ";
				break;
			case 3:
				strSQL += " order by mCommission ";
				break;
			case 4:
				strSQL += " order by mSuretyFee ";
				break;
			case 5:
				strSQL += " order by mInterestTax ";
				break;
			case 6:
				strSQL += " order by nStatusID ";
				break;
			case 7:
				strSQL += " order by nInputUserID ";
				break;
			case 8:
				strSQL += " order by dtInput ";
				break;

			case 11:
				strSQL += " order by nAccountID ";
				break;
			case 12:
				strSQL += " order by nContractID ";
				break;
			case 13:
				strSQL += " order by nDueBillID ";
				break;
			case 14:
				strSQL += " order by dtExecute ";
				break;
				
			default:
				strSQL += " order by id "; ////ID��
			}

			//�������ǽ���
			if (info.getSDesc().equals("asc"))
				strSQL += " asc ";
			else
				strSQL += " desc ";
            
            Log.print("---SQL:"+strSQL);

            ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
            
            int i=1;
            while(rs.next()){
            	adjustInfo=new AdjustInterestInfo();
            	//
            	adjustInfo.setLID(rs.getLong("id"));						//��¼ID
            	adjustInfo.setLOfficeID(rs.getLong("nOfficeID"));			//���´�ID
            	adjustInfo.setLCurrencyID(rs.getLong("nCurrencyID"));		//����ID
            	//
            	adjustInfo.setLAccountID(rs.getLong("nAccountID"));		//�˻�ID
            	adjustInfo.setLContractID(rs.getLong("nContractID"));	//��ͬID
            	adjustInfo.setLDueBillID(rs.getLong("nDueBillID"));		//�ſ�֪ͨ��ID
            	//
            	adjustInfo.setLAddOrReduce(rs.getLong("nAddOrRedueID"));	//�ӻ�� 1:�� 2:��
            	adjustInfo.setDInterest(rs.getDouble("mInterest"));			//�ۼ���Ϣ������
            	adjustInfo.setDCommission(rs.getDouble("mCommission"));		//�ۼ������ѵ�����
            	adjustInfo.setDSuretyFee(rs.getDouble("mSuretyFee"));		//�ۼƵ����ѵ�����
            	adjustInfo.setDInterestTax(rs.getDouble("mInterestTax"));	//�ۼ���Ϣ˰�ѵ�����
            	//
            	adjustInfo.setLInputUserID(rs.getLong("nInputUserID"));		//������ID
            	adjustInfo.setTsInput(rs.getTimestamp("dtInput"));			//��������
            	adjustInfo.setLCheckUserID(rs.getLong("nCheckUserID"));		//������ID
            	adjustInfo.setTsCheck(rs.getTimestamp("dtCheck"));			//��������
            	//
            	adjustInfo.setSAdjustReason(rs.getString("sAdjustReason"));	//��ע
            	adjustInfo.setLStatusID(rs.getLong("nStatusID"));			//��¼״̬(0:ɾ��,1:����,2:����)
            	adjustInfo.setTsExecute(rs.getTimestamp("dtExecute"));		//ִ����
            	//
            	adjustInfo.setLDepositTypeID(rs.getLong("NDEPOSITTYPE"));		//�������
            	//
            	adjustInfo.setLSubAccountID(rs.getLong("NSUBACCOUNTID"));		//���˻�ID
            	//
            	Log.print("�õ���¼,����Ϊ:"+i++);
            	vct.add(adjustInfo);
            }
        }
        catch(SQLException sqle){
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(rs);
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
		return vct;
	}
	
	//����IDɾ������һ����Ϣ
	public long delAdjust(long nID)	throws SettlementDAOException,ITreasuryDAOException{
		long lRtn = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            conn = this.getConnection();
            String sql = " update " + this.strTableName + " set nStatusID=? where id=? ";
            ps = conn.prepareStatement(sql);
            Log.print(sql);
            ps.setLong(1, Constant.RecordStatus.INVALID);
            ps.setLong(2, nID);
            //����״̬
            ps.executeUpdate();
            //����IDֵ
            lRtn = nID;
        }
        catch(SQLException sqle){
        	lRtn=-1;
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }
        return lRtn;
	
	}
	//����һ��������Ϣ�����ӵ�����Ϣ
	public long addAdjust(AdjustInterestInfo info) throws Exception{

		long ret = 0;
		
		PreparedStatement ps = null; //
		Connection conn = null; //
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer(); //��ѯ��
		
		long id_Max=0;
		try
		{
			//��ʼ�����ݿ���Դ
			conn = this.getConnection();
			//�õ�����ID��
			String maxId_sql=" select nvl(max(id),0)+1 maxid from " + this.strTableName ;
			//
	        ps = conn.prepareStatement(maxId_sql);
	        //��ӡSQL
			System.out.println("add->strSQL:"+maxId_sql);
	        //ִ��SQL
			rs=ps.executeQuery(maxId_sql);
			if(rs.next())
			{
				id_Max=rs.getLong("maxid");	//�õ�����IDֵ
			}
			System.out.println("*************����IDֵΪ:"+id_Max);
			
			//��Ӳ�������SQL���
			buffer.append("insert into \n");
	        buffer.append(" "+ this.strTableName +"\n");
	        buffer.append("\n (ID, \n");
	        //
	        buffer.append("nOfficeID,\n");
	        buffer.append("nCurrencyID,\n");
	        buffer.append("nAccountID,\n");
	        buffer.append("nContractID,\n");
	        buffer.append("nDueBillID,\n");
	        //
	        buffer.append("nAddOrRedueID,\n");
	        buffer.append("mInterest,\n");
	        buffer.append("mCommission,\n");
	        buffer.append("mSuretyFee,\n");
	        buffer.append("mInterestTax,\n");
	        //
	        buffer.append("nInputUserID,\n");
	        buffer.append("dtInput,\n");
	        buffer.append("sAdjustReason,\n");
	        buffer.append("nStatusID,\n");
	        //
	        buffer.append("NDEPOSITTYPE,\n");		//�������ID
	        //
	        buffer.append("NSUBACCOUNTID,\n");		//�������ID
	        //
	        buffer.append("dtExecute)\n");
	        buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        
			ps = conn.prepareStatement(buffer.toString());
			
			//��ʼ����ֵ��SQL����
			int nIndex = 1;
			ps.setLong(nIndex++, id_Max);
			//
			ps.setLong(nIndex++, info.getLOfficeID());
			ps.setLong(nIndex++, info.getLCurrencyID());
			ps.setLong(nIndex++, info.getLAccountID());
			ps.setLong(nIndex++, info.getLContractID());
			ps.setLong(nIndex++, info.getLDueBillID());
			//
			ps.setLong(nIndex++, info.getLAddOrReduce());
			ps.setDouble(nIndex++, info.getDInterest());
			ps.setDouble(nIndex++, info.getDCommission());
			ps.setDouble(nIndex++, info.getDSuretyFee());
			ps.setDouble(nIndex++, info.getDInterestTax());
			//
			ps.setLong(nIndex++, info.getLInputUserID());
			ps.setTimestamp(nIndex++, info.getTsInput());
			ps.setString(nIndex++, info.getSAdjustReason());
			ps.setLong(nIndex++, 2);		//δ����״̬
			//
			ps.setLong(nIndex++, info.getLDepositTypeID());		//�������ID��
			//
			ps.setLong(nIndex++, info.getLSubAccountID());		//���˻�ID��
			//
			ps.setTimestamp(nIndex++, info.getTsExecute());		//ִ������
			
			//��ӡSQL
			System.out.println("add->strSQL:"+buffer.toString());
			//ִ��SQL
			ps.execute();
			ret=id_Max;
			
			//Added by zwsun ,2007/7/6, ����������
			if(info.getInutParameterInfo()!=null)
			{
				log.debug("------�ύ����--------");
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+ret);
				   System.out.println("-------url-----:"+tempInfo.getUrl());
				tempInfo.setTransID(String.valueOf(ret));
				   System.out.println("-------setTransID-----:"+tempInfo.getTransID());
				tempInfo.setDataEntity(info);
				//�ύ����
				try {
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					this.upStatus(ret,SETTConstant.TransactionStatus.APPROVALING);
				} catch (IException e) {
					e.printStackTrace();
				}
				
				//����״̬��������
				
				log.debug("------�ύ�����ɹ�--------");
			}
		}
        catch(SQLException sqle){
        	ret = -1;
        	sqle.printStackTrace();
        }
        finally
        {
        	try
    		{
        		this.cleanup(ps);
        		this.cleanup(conn);
    		}
    		catch (SQLException sqle)
    		{
    			sqle.printStackTrace();
    		}
        }

		return ret;
	}
	/**
	 * ����״̬��Added by zwsun, 2007/7/7
	 */
	public long upStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);				
		}
		return id;
	}
	
	//���ݵ�����Ϣ�޸ĵ�����Ϣ(�����˸�����Ϣ˰�ѵ�ֵ)
	public long updateAdjustForValue(AdjustInterestInfo info)  throws SettlementDAOException,ITreasuryDAOException{
		long ret = 0;	//-1:ʧ��  0:����
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //��ѯ��
		
		try
		{
			//����SQL���
			buffer.append(" update "+this.strTableName+ " set \n");
			//
			buffer.append(" nAddOrRedueID = ? ,");
			buffer.append(" mInterest = ? ,");
			buffer.append(" mCommission = ? ,");
			buffer.append(" mSuretyFee = ? ,");
			buffer.append(" mInterestTax = ? ,"); 
			//
			buffer.append(" nInputUserID = ? ,"); 
			buffer.append(" dtInput = ? ,"); 
			buffer.append(" sAdjustReason = ? ,"); 
			buffer.append(" dtExecute = ?  \n");	//ִ����
			//
			buffer.append(" where  id = ? ");
			
	        //��ʼ�����ݿ���Դ
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//��ʼ����ֵ��SQL����
			int nIndex = 1; 
			pstmt.setLong(nIndex++, info.getLAddOrReduce());
			pstmt.setDouble(nIndex++, info.getDInterest());
			pstmt.setDouble(nIndex++, info.getDCommission());
			pstmt.setDouble(nIndex++, info.getDSuretyFee());
			pstmt.setDouble(nIndex++, info.getDInterestTax());
			//
			pstmt.setLong(nIndex++, info.getLInputUserID());
			pstmt.setTimestamp(nIndex++, info.getTsInput());
			pstmt.setString(nIndex++, info.getSAdjustReason());
			pstmt.setTimestamp(nIndex++, info.getTsExecute());
			//
			pstmt.setLong(nIndex++, info.getLID());	//����IDֵ
			
			//��ӡSQL
			System.out.println("update->strSQL:"+buffer.toString());
			//ִ��SQL
			pstmt.executeUpdate();
			//���زο���
			ret=info.getLID();
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
	}
	
	//���ݵ�����Ϣ�޸ĵ�����Ϣ(����)
	public long updateAdjustForCheck(AdjustInterestInfo info)  throws SettlementDAOException,ITreasuryDAOException{
		long ret = 0;	//-1:ʧ��  0:����
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //��ѯ��
		
		try
		{
			//����SQL���
			buffer.append(" update "+this.strTableName+ " set \n");

			buffer.append(" nStatusID = ? ,"); 			//��¼״̬
			buffer.append(" nCheckUserID = ? ,"); 		//������ID
			buffer.append(" dtCheck = ? "); 			//��������
			//����IDֵ
			buffer.append(" where  id = ? ");			//����IDֵ
			
	        //��ʼ�����ݿ���Դ
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//��ʼ����ֵ��SQL����
			int nIndex = 1; 
			//pstmt.setLong(nIndex++, 2);		//��¼״̬
			//Modified by zwsun, 2007/7/6
			pstmt.setLong(nIndex++, info.getLStatusID());
			pstmt.setLong(nIndex++, info.getLCheckUserID());	//������ID
			pstmt.setTimestamp(nIndex++, info.getTsCheck());	//��������
			
			pstmt.setLong(nIndex++, info.getLID());	//����IDֵ
			//��ӡSQL���
			System.out.println("update->strSQL:"+buffer.toString());
			pstmt.executeUpdate();
		}
	    catch(SQLException sqle){
	    	ret = -1;
	    	sqle.printStackTrace();
	    }
	    finally
	    {
	    	try
			{
	    		this.cleanup(pstmt);
	    		this.cleanup(conn);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
	    }
		return ret;
	}
}
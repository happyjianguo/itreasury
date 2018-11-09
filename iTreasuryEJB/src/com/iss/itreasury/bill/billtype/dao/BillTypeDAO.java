/* Generated by Together */

package com.iss.itreasury.bill.billtype.dao;

//import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.bill.billtype.dataentity.BillTypeInfo;
import com.iss.itreasury.bill.billtype.dataentity.BillTypeQueryInfo;
import com.iss.itreasury.bill.util.BillDAO;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;

public class BillTypeDAO extends BillDAO {
    public BillTypeDAO() {
        super("Bill_BillType");
    }

    /**
     *����Ʊ����������ø����Ʊ������
     * @param abstractTypeID Ʊ������ID�����Ʊ������С�ڵ���0����ȡ��������
     * @return Collection�еĶ���ΪBillTypeInfo
     * @throws BillException
     */
    public Collection findByAbstractType(long abstractTypeID) throws BillException {
        
		try 
		{
			initDAO();
            
			String strSQL = "select * from "+this.strTableName+" where 1=1 and abstractTypeID = " + abstractTypeID;
			BillTypeInfo info = new BillTypeInfo();
            
             
			prepareStatement(strSQL);
			executeQuery();
    
			Collection c = getDataEntitiesFromResultSet(info.getClass());
    
			finalizeDAO();
			
			return c;
			
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new BillException("Bill_E012", e);
		}

		finally
		{
			try 
			{				
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
		}
    }

    /**
     *���ݲ�ѯ������ѯƱ������
     * @param qInfo ��ѯ������Ϣ
     * @return Collection�еĶ���ΪBillTypeInfo
     * @throws BillException
     */
    public Collection findByMultiOption(BillTypeQueryInfo qInfo) throws BillException {

		Collection c = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		long lStartNum = -1;
		long lEndNum = -1;
		long lRecordCount = 0;
		long lPageCount = 0;
		Vector v = new Vector();
        
		if((qInfo.getOrderParamString() != null)&&(qInfo.getOrderParamString().length() > 0))
		{
			sb.append(" order by "+qInfo.getOrderParamString());
			if(qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				sb.append(" desc ");
			}
		}

		try 
		{
			initDAO();

			String strSQL1 = " select count(*) from "+this.strTableName+" where statusId=1 ";
			prepareStatement(strSQL1);
			rs = executeQuery();
			try 
			{
				
				if (rs.next() && rs != null) 
				{
					lRecordCount = rs.getLong(1);
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
			} 
			catch (SQLException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
			
			//������ҳ��			
			lPageCount = lRecordCount/qInfo.getPageLineCount();
			if(lRecordCount % qInfo.getPageLineCount() != 0 )
			{
				lPageCount = lPageCount +1;
			}
			//���㿪ʼ�кͽ�����
			lStartNum = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lEndNum = qInfo.getPageNo() * qInfo.getPageLineCount() ;
			if(lEndNum > lRecordCount )
			{
				lEndNum = lRecordCount;
			}
			
			qInfo.setPageCount(lPageCount);
			
			
			String strSQL = " select * from ( select t.*,rownum rnum " +
				" from "+this.strTableName+" t " +
				" where statusId=1 "+sb.toString()+
				" ) where rnum between "+ lStartNum +" and " + lEndNum;
			System.out.println("Ʊ������sql="+strSQL);
			BillTypeInfo info = new BillTypeInfo();
            
             
			prepareStatement(strSQL);
			executeQuery();
//			rs = executeQuery();
			//*    
			c = getDataEntitiesFromResultSet(info.getClass());
    		//*/
    		/*
    		try 
    		{				
				while (rs.next() && rs != null) 
				{
					BillTypeInfo info = new BillTypeInfo();
					info.setId(rs.getLong("ID"));
					info.setAbstractTypeID(rs.getLong("AbstractTypeID"));
					info.setSubjectID(rs.getLong("SubjectID"));
					info.setBank(rs.getString("Bank"));
					info.setCode(rs.getString("Code"));
					info.setName(rs.getString("Name"));
					
					v.add(info);
				}
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
			} catch (SQLException e2) 
			{
				throw new BillException("Bill_E012", e2);
			}
			//*/
    		
			finalizeDAO();
			
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new BillException("Bill_E012", e);
		}

		finally
		{
			try 
			{				
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
		}
			
//		return v.size() < 1 ? null : v;
		return c;
    }
    
	/**
	 *����Ʊ����������ø����Ʊ������
	 * @param abstractTypeID Ʊ������ID�����Ʊ������С�ڵ���0����ȡ��������
	 * @return Collection�еĶ���ΪBillTypeInfo
	 * @throws BillException
	 */
	public long findIdByCode(String code,long lId) throws BillException {

		ResultSet rs = null;
//		PreparedStatement ps = null;
		long id = -1;
		try 
		{
			
			initDAO();
            
			String strSQL = "select id from "+this.strTableName+" where statusId=1 and code = '" + code +"'";
//			BillTypeInfo info = new BillTypeInfo();
			if(lId > 0)
			{
				strSQL += " and id != "+lId;
			}
            
             try 
             {
				
				prepareStatement(strSQL);
				
				rs=executeQuery();
				
				//id = getDataEntitiesFromResultSet(info.getClass());
				if(rs !=null && rs.next())
				{
					id = rs.getLong(1);
				}
			} 
			catch (SQLException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
    
			finalizeDAO();
			
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new BillException("Bill_E012", e);
		}

		finally
		{
			try 
			{				
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
		}
			
		return id;
	}

	/**
	 *����Ʊ����������ø����Ʊ������
	 * @param abstractTypeID Ʊ������ID�����Ʊ������С�ڵ���0����ȡ��������
	 * @return Collection�еĶ���ΪBillTypeInfo
	 * @throws BillException
	 */
	public long findIdByName(String name,long lId) throws BillException {

		ResultSet rs = null;
//		PreparedStatement ps = null;
		long id = -1;
		try 
		{
			
			initDAO();
            
			String strSQL = "select id from "+this.strTableName+" where statusId=1 and name = '" + name +"'";

			if(lId > 0)
			{
				strSQL += " and id != "+lId;
			}
			
			 try 
			 {
				
				prepareStatement(strSQL);
				
				rs=executeQuery();
				
				if(rs !=null && rs.next())
				{
					id = rs.getLong(1);
				}
			} 
			catch (SQLException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
    
			finalizeDAO();
			
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new BillException("Bill_E012", e);
		}

		finally
		{
			try 
			{				
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
		}
			
		return id;
	}
	/**
	 *����Ʊ����������ø����Ʊ������
	 * @param abstractTypeID Ʊ������ID�����Ʊ������С�ڵ���0����ȡ��������
	 * @return Collection�еĶ���ΪBillTypeInfo
	 * @throws BillException
	 */
	public long findMaxBillTypeCode() throws BillException {

		ResultSet rs = null;
//		PreparedStatement ps = null;
		long code = -1;
		
		try 
		{
			
			initDAO();
            
			String strSQL = " select nvl(max(to_number(code)),0)+1 as nMaxCode " +
				" from "+this.strTableName+" where statusId=1 ";
          
			 try 
			 {
				
				prepareStatement(strSQL);
				
				rs=executeQuery();
				
				//id = getDataEntitiesFromResultSet(info.getClass());
				if(rs !=null && rs.next())
				{
					code = rs.getLong(1);
				}
			} 
			catch (SQLException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
    
			finalizeDAO();
			
		} 
		catch (ITreasuryDAOException e) 
		{
			throw new BillException("Bill_E012", e);
		}

		finally
		{
			try 
			{				
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				throw new BillException("Bill_E012", e1);
			}
		}
			
		return code;
	}



}
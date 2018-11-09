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
 * 累计费用及利息调整DAO
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class Sett_AdjustInterestDao extends SettlementDAO{
	//构造函数
	public Sett_AdjustInterestDao()
    {
        super.strTableName = "Sett_AdjustInterest";
    }
	//后台测试主函数
    public static void main(String[] args)
    {
 
    }
	
	//根据ID进行查找一条调整信息
	public AdjustInterestInfo findAdjustByID(long lID) throws SettlementDAOException,ITreasuryDAOException{
		
		System.out.println("调用findAdjustByID方法");
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AdjustInterestInfo adjustInfo=null;
        try
        {
            conn = this.getConnection();
            StringBuffer buffer = new StringBuffer("");
            //拼凑查询语句
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
            	adjustInfo.setLID(rs.getLong("id"));						//记录ID
            	adjustInfo.setLOfficeID(rs.getLong("nOfficeID"));			//办事处ID
            	adjustInfo.setLCurrencyID(rs.getLong("nCurrencyID"));		//币种ID
            	//
            	adjustInfo.setLAccountID(rs.getLong("nAccountID"));		//账户ID
            	adjustInfo.setLContractID(rs.getLong("nContractID"));	//合同ID
            	adjustInfo.setLDueBillID(rs.getLong("nDueBillID"));		//放款通知单ID
            	//
            	adjustInfo.setLAddOrReduce(rs.getLong("nAddOrRedueID"));	//加或减 1:加 2:减
            	adjustInfo.setDInterest(rs.getDouble("mInterest"));			//累计利息调整量
            	adjustInfo.setDCommission(rs.getDouble("mCommission"));		//累计手续费调整量
            	adjustInfo.setDSuretyFee(rs.getDouble("mSuretyFee"));		//累计担保费调整量
            	adjustInfo.setDInterestTax(rs.getDouble("mInterestTax"));	//累计利息税费调整量
            	
            	//
            	adjustInfo.setLInputUserID(rs.getLong("nInputUserID"));		//输入人ID
            	adjustInfo.setTsInput(rs.getTimestamp("dtInput"));			//输入日期
            	adjustInfo.setLCheckUserID(rs.getLong("nCheckUserID"));		//复核人ID
            	adjustInfo.setTsCheck(rs.getTimestamp("dtCheck"));			//复核日期
            	//
            	adjustInfo.setSAdjustReason(rs.getString("sAdjustReason"));	//备注
            	adjustInfo.setLStatusID(rs.getLong("nStatusID"));			//记录状态(0:删除,1:保存,2:复核)
            	adjustInfo.setTsExecute(rs.getTimestamp("dtExecute"));		//执行日
            	//
            	adjustInfo.setLDepositTypeID(rs.getLong("NDEPOSITTYPE"));		//存款类型
            	//
            	adjustInfo.setLSubAccountID(rs.getLong("NSUBACCOUNTID"));		//子账户ID

            	//
            	System.out.println("======得到一条记录(adjustInfo)  DInterest:"+adjustInfo.getDInterest());
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
	
	//根据组合条件来查找调整信息集合
	public Collection findAdjustByCondition(AdjustInterestInfoQuery info) throws SettlementDAOException,ITreasuryDAOException{
		Vector vct=new Vector();
		System.out.println("调用findAdjustByCondition方法");
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AdjustInterestInfo adjustInfo=null;
        try
        {
            conn = this.getConnection();
            StringBuffer buffer = new StringBuffer("");
            //拼凑查询语句
            buffer.append(" select * ");

            buffer.append(" from " + this.strTableName + " \n");
            buffer.append(" where 1=1 \n");
            
            if(info.getLStatusID()==-1){
            	//全部(不包括删除的)
            	buffer.append(" and nStatusID !=" + Constant.RecordStatus.INVALID+ " \n");
            	
            }else {
            	//各个状态的值 0:删除 1:保存 2:复核
            	buffer.append(" and nStatusID=" + info.getLStatusID()+ " \n");
            }
            
            if(info.getSAccountNoStart()!=-1)	//账户ID
            	buffer.append(" and nAccountID =" + info.getSAccountNoStart()+ " \n");
            
            if(info.getSContractNoStart()!=-1)	//合同ID
            	buffer.append(" and nContractID =" + info.getSContractNoStart()+ " \n");
            
            if(info.getSDueBillCodeStart()!=-1)	//放款通知单ID
            	buffer.append(" and nDueBillID =" + info.getSDueBillCodeStart()+ " \n");
            
            
            //结息日期
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
            
            //针对哪个字段进行排序
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
				strSQL += " order by id "; ////ID号
			}

			//是升序还是降序
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
            	adjustInfo.setLID(rs.getLong("id"));						//记录ID
            	adjustInfo.setLOfficeID(rs.getLong("nOfficeID"));			//办事处ID
            	adjustInfo.setLCurrencyID(rs.getLong("nCurrencyID"));		//币种ID
            	//
            	adjustInfo.setLAccountID(rs.getLong("nAccountID"));		//账户ID
            	adjustInfo.setLContractID(rs.getLong("nContractID"));	//合同ID
            	adjustInfo.setLDueBillID(rs.getLong("nDueBillID"));		//放款通知单ID
            	//
            	adjustInfo.setLAddOrReduce(rs.getLong("nAddOrRedueID"));	//加或减 1:加 2:减
            	adjustInfo.setDInterest(rs.getDouble("mInterest"));			//累计利息调整量
            	adjustInfo.setDCommission(rs.getDouble("mCommission"));		//累计手续费调整量
            	adjustInfo.setDSuretyFee(rs.getDouble("mSuretyFee"));		//累计担保费调整量
            	adjustInfo.setDInterestTax(rs.getDouble("mInterestTax"));	//累计利息税费调整量
            	//
            	adjustInfo.setLInputUserID(rs.getLong("nInputUserID"));		//输入人ID
            	adjustInfo.setTsInput(rs.getTimestamp("dtInput"));			//输入日期
            	adjustInfo.setLCheckUserID(rs.getLong("nCheckUserID"));		//复核人ID
            	adjustInfo.setTsCheck(rs.getTimestamp("dtCheck"));			//复核日期
            	//
            	adjustInfo.setSAdjustReason(rs.getString("sAdjustReason"));	//备注
            	adjustInfo.setLStatusID(rs.getLong("nStatusID"));			//记录状态(0:删除,1:保存,2:复核)
            	adjustInfo.setTsExecute(rs.getTimestamp("dtExecute"));		//执行日
            	//
            	adjustInfo.setLDepositTypeID(rs.getLong("NDEPOSITTYPE"));		//存款类型
            	//
            	adjustInfo.setLSubAccountID(rs.getLong("NSUBACCOUNTID"));		//子账户ID
            	//
            	Log.print("得到记录,个数为:"+i++);
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
	
	//根据ID删除调整一条信息
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
            //更新状态
            ps.executeUpdate();
            //返回ID值
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
	//根据一条调整信息来增加调整信息
	public long addAdjust(AdjustInterestInfo info) throws Exception{

		long ret = 0;
		
		PreparedStatement ps = null; //
		Connection conn = null; //
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer(); //查询串
		
		long id_Max=0;
		try
		{
			//初始化数据库资源
			conn = this.getConnection();
			//得到最大的ID号
			String maxId_sql=" select nvl(max(id),0)+1 maxid from " + this.strTableName ;
			//
	        ps = conn.prepareStatement(maxId_sql);
	        //打印SQL
			System.out.println("add->strSQL:"+maxId_sql);
	        //执行SQL
			rs=ps.executeQuery(maxId_sql);
			if(rs.next())
			{
				id_Max=rs.getLong("maxid");	//得到最大的ID值
			}
			System.out.println("*************最大的ID值为:"+id_Max);
			
			//添加操作生成SQL语句
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
	        buffer.append("NDEPOSITTYPE,\n");		//存款类型ID
	        //
	        buffer.append("NSUBACCOUNTID,\n");		//存款类型ID
	        //
	        buffer.append("dtExecute)\n");
	        buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        
			ps = conn.prepareStatement(buffer.toString());
			
			//开始设置值到SQL里面
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
			ps.setLong(nIndex++, 2);		//未复核状态
			//
			ps.setLong(nIndex++, info.getLDepositTypeID());		//存款类型ID号
			//
			ps.setLong(nIndex++, info.getLSubAccountID());		//子账户ID号
			//
			ps.setTimestamp(nIndex++, info.getTsExecute());		//执行日期
			
			//打印SQL
			System.out.println("add->strSQL:"+buffer.toString());
			//执行SQL
			ps.execute();
			ret=id_Max;
			
			//Added by zwsun ,2007/7/6, 增加审批流
			if(info.getInutParameterInfo()!=null)
			{
				log.debug("------提交审批--------");
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+ret);
				   System.out.println("-------url-----:"+tempInfo.getUrl());
				tempInfo.setTransID(String.valueOf(ret));
				   System.out.println("-------setTransID-----:"+tempInfo.getTransID());
				tempInfo.setDataEntity(info);
				//提交审批
				try {
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					this.upStatus(ret,SETTConstant.TransactionStatus.APPROVALING);
				} catch (IException e) {
					e.printStackTrace();
				}
				
				//更新状态到审批中
				
				log.debug("------提交审批成功--------");
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
	 * 更新状态，Added by zwsun, 2007/7/7
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
	
	//根据调整信息修改调整信息(调整了各项利息税费的值)
	public long updateAdjustForValue(AdjustInterestInfo info)  throws SettlementDAOException,ITreasuryDAOException{
		long ret = 0;	//-1:失败  0:正常
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //查询串
		
		try
		{
			//生成SQL语句
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
			buffer.append(" dtExecute = ?  \n");	//执行日
			//
			buffer.append(" where  id = ? ");
			
	        //初始化数据库资源
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//开始设置值到SQL里面
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
			pstmt.setLong(nIndex++, info.getLID());	//根据ID值
			
			//打印SQL
			System.out.println("update->strSQL:"+buffer.toString());
			//执行SQL
			pstmt.executeUpdate();
			//返回参考号
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
	
	//根据调整信息修改调整信息(复核)
	public long updateAdjustForCheck(AdjustInterestInfo info)  throws SettlementDAOException,ITreasuryDAOException{
		long ret = 0;	//-1:失败  0:正常
		PreparedStatement pstmt = null; //
		Connection conn = null; //
		StringBuffer buffer = new StringBuffer(); //查询串
		
		try
		{
			//生成SQL语句
			buffer.append(" update "+this.strTableName+ " set \n");

			buffer.append(" nStatusID = ? ,"); 			//记录状态
			buffer.append(" nCheckUserID = ? ,"); 		//复核人ID
			buffer.append(" dtCheck = ? "); 			//复核日期
			//根据ID值
			buffer.append(" where  id = ? ");			//根据ID值
			
	        //初始化数据库资源
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());
			
			//开始设置值到SQL里面
			int nIndex = 1; 
			//pstmt.setLong(nIndex++, 2);		//记录状态
			//Modified by zwsun, 2007/7/6
			pstmt.setLong(nIndex++, info.getLStatusID());
			pstmt.setLong(nIndex++, info.getLCheckUserID());	//复核人ID
			pstmt.setTimestamp(nIndex++, info.getTsCheck());	//复核日期
			
			pstmt.setLong(nIndex++, info.getLID());	//根据ID值
			//打印SQL语句
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
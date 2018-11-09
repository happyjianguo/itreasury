/*
 * Created on 2004-7-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.compatibilitysetting.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingWhereInfo;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author yychen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sett_CompatibilityTypeSettingDao extends SettlementDAO
{
	public Sett_CompatibilityTypeSettingDao()
	{
		super("Sett_CompatibilityTypeSetting");
	}
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * Method findTypeSettingByID.
	 * 查找兼容业务类型设置内容
	 * @param lID
	 * @return CompatibilityTypeSettingInfo
	 * @throws Exception
	 * created by gqzhang
	 */
	public CompatibilityTypeSettingInfo findTypeSettingByID(long lID) throws SettlementDAOException
	{
		CompatibilityTypeSettingInfo rtnInfo = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			log.print("====Sett_CompatibilityTypeSettingDao：进入查找兼容业务类型设置内容===");
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(" select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where id=" + lID);
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			Vector vctReturn = getInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				rtnInfo = (CompatibilityTypeSettingInfo) vctReturn.elementAt(0);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		log.print("====Sett_CompatibilityTypeSettingDao：结束查找兼容业务类型设置内容===");
		return rtnInfo;
	}
	
	/**
	 * Method findTypeSettingByName.
	 * 查找兼容业务类型设置内容
	 * @param TypeName
	 * @return CompatibilityTypeSettingInfo
	 * @throws Exception
	 * created by ygniu
	 */
	public CompatibilityTypeSettingInfo findTypeSettingByName(String TypeName) throws SettlementDAOException
	{
		CompatibilityTypeSettingInfo rtnInfo = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			log.print("====Sett_CompatibilityTypeSettingDao：进入查找兼容业务类型设置内容===");
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(" select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where NAME='" + TypeName+"'");
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			Vector vctReturn = getInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				rtnInfo = (CompatibilityTypeSettingInfo) vctReturn.elementAt(0);
			}
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		log.print("====Sett_CompatibilityTypeSettingDao：结束查找兼容业务类型设置内容===");
		return rtnInfo;
	}
	
	/**
	 * Method findAllTypeSetting.
	 * 查询所有有效的兼容业务类型设置
	 * @return CompatibilityTypeSettingInfo
	 * created by gqzhang，if modify needed please inform me.
	 */
	public Vector findAllTypeSetting(long lOfficeID, long lCurrencyID) throws SettlementDAOException
	{
		log.print("====Sett_CompatibilityTypeSettingDao：查询所有有效的兼容业务类型设置===");
		Vector vctReturn = null;
		ResultSet rs = null;
		StringBuffer strSQLBuffer = new StringBuffer();
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			log.print("====111111111111111===");
			strSQLBuffer.append(" select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where STATUSID>0");
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getInfoFromResultSet(rs);
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		log.print("====Sett_CompatibilityTypeSettingDao：结束查询所有有效的兼容业务类型设置===");
		return vctReturn;
	}
	/**
	 * Method getInfoFromResultSet.
	 * @param rs
	 * @return Vector
	 * @throws Exception
	 * created by gqzhang
	 */
	private Vector getInfoFromResultSet(ResultSet rs) throws SettlementDAOException
	{
		Vector vctReturn = new Vector();
		CompatibilityTypeSettingInfo info = null;
		try
		{
			while (rs.next())
			{
				log.print("==============1=============");
				info = new CompatibilityTypeSettingInfo();
				info.setId(rs.getLong("ID")); //	Number	ID	主键，兼容业务类型ID
				info.setName(rs.getString("Name")); //	//Abstract	名称	兼容业务名称
				info.setOfficeID(rs.getLong("OfficeID")); //	Number	办事处	设置该兼容业务类型的办事处
				info.setCurrencyID(rs.getLong("CurrencyID")); //	Number	币种	设置该兼容业务类型的币种
				info.setAmountSource1(rs.getLong("AmountSource1")); //	Number	是否需要资金来源信息1	取值范围：有效、无效
				info.setVoucher1(rs.getLong("Voucher1")); //	Number	是否需要凭证号信息1	取值范围：有效、无效
				info.setBankInfo1(rs.getLong("BankInfo1")); //	Number	是否需要银行信息1	取值范围：有效、无效
				info.setAmountSource2(rs.getLong("AmountSource2")); //	Number	是否需要资金来源信息2	取值范围：有效、无效
				info.setVoucher2(rs.getLong("Voucher2")); //	Number	是否需要凭证号信息2	取值范围：有效、无效
				info.setBankInfo2(rs.getLong("BankInfo2")); //	Number	是否需要银行信息2	取值范围：有效、无效
				info.setAmountSource3(rs.getLong("AmountSource3")); //	Number	是否需要资金来源信息3	取值范围：有效、无效
				info.setVoucher3(rs.getLong("Voucher3")); //	Number	是否需要凭证号信息3	取值范围：有效、无效
				info.setBankInfo3(rs.getLong("BankInfo3")); //	Number	是否需要银行信息3	取值范围：有效、无效
				info.setAmountSource4(rs.getLong("AmountSource4")); //	Number	是否需要资金来源信息4	取值范围：有效、无效
				info.setVoucher4(rs.getLong("Voucher4")); //	Number	是否需要凭证号信息4	取值范围：有效、无效
				info.setBankInfo4(rs.getLong("BankInfo4")); //	Number	是否需要银行信息4	取值范围：有效、无效
				info.setInputUserID(rs.getLong("InputUserID")); //	Number	录入人	录入人
				info.setInputDate(rs.getTimestamp("InputDate")); //	录入时间	录入时间
				info.setStatusID(rs.getLong("StatusID")); //	Number	记录状态	取值范围：有效、无效
				vctReturn.add(info);
			}
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("获取兼容业务类型设置产生错误", e);
		}
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	
	
	/**
	 * 根据查询条件对象中设置的查询条件进行查询
	 * @param conditionInfo 查询条件对象
	 * @return
	 */
	public Vector findByConditionInfo(CompatibilityTypeSettingWhereInfo conditionInfo)throws SettlementDAOException
	{
	    Vector vctReturn = null;
		ResultSet rs = null;
		StringBuffer strSQLBuffer = new StringBuffer();
		//查询体中设置的查询条件
		long Id = conditionInfo.getId();
		long lOfficeId = conditionInfo.getOfficeId();
		long lCurrencyId = conditionInfo.getCurrencyId();
		String strStartCode = conditionInfo.getStartCode();
		String strEndCode = conditionInfo.getEndCode();
		String strOrderById=conditionInfo.getOrderById();
		boolean bDesc=conditionInfo.isDesc();
		String TypeName=conditionInfo.getTypeName();
		long statusId=conditionInfo.getStatusId();
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			strSQLBuffer.append(" select * from ");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where STATUSID>0 ");
			if(Id!=-1) strSQLBuffer.append(" and ID="+Id+" ");
			if(lOfficeId!=-1) strSQLBuffer.append(" and OFFICEID="+lOfficeId+" ");
			if(lCurrencyId!=-1) strSQLBuffer.append(" and CURRENCYID="+lCurrencyId+" ");
			if(strStartCode != null && strStartCode.trim().length() > 0 
			   && strEndCode != null && strEndCode.trim().length() >0)
			{
			    strSQLBuffer.append(" and ID between "+strStartCode+" and "+strEndCode+" \n");
			}
			if(TypeName!=null&&TypeName.trim().length()>0)
			{
			    strSQLBuffer.append(" and NAME like '"+TypeName+"' ");
			}
			if(statusId!=-1)
			{
			    strSQLBuffer.append(" and STATUSID >= "+statusId+" ");
			}
			if(strOrderById!=null&&strOrderById.trim().length()>0)
			{
			    strSQLBuffer.append(" order by "+strOrderById);
			    if(bDesc) strSQLBuffer.append(" desc");
			    else strSQLBuffer.append(" asc");
			}
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getInfoFromResultSet(rs);
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("查找兼容业务类型设置产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
	    return vctReturn;
	}
	
	/**
	 * Method main.
	 * @param args
	 * @throws Exception
	 */
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			Sett_CompatibilityTypeSettingDao dao = new Sett_CompatibilityTypeSettingDao();
			/*Vector vctResult = dao.findAllTypeSetting();
			if (vctResult != null && vctResult.size() > 0)
			{
				System.out.print("1111111111111");
			}
			else
			{
				System.out.print("2222222222222222");
			
			}
			
			CompatibilityTypeSettingInfo info =dao.findTypeSettingByID(1);
			if(info != null)
			{
				System.out.print("==============="+info.getName());
			
			}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

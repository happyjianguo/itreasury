package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.BankpayCommissionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.VoucherTypeInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

public class Sett_CommissionSettingDAO extends SettlementDAO {

	/**
	 * Constructor for Sett_CommissionSettingDAO
	 */
	public Sett_CommissionSettingDAO() {
		super("Sett_CommissionSetting", false);
		super.setUseMaxID();
	}

	public void clearDAO() throws ITreasuryDAOException {
		super.finalizeDAO();
	}

	/**
	 * @description: boolean
	 * @param CommissionSettingInfo
	 * @return boolean
	 * @throws IException
	 */
	public boolean isAllow(CommissionSettingInfo info) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		long recordNumber = -1;
		try {
			conn = getConnection();

			// check that if can add or update
			sb
					.append(" select count(*) recordNumber from Sett_CommissionSetting ");
			sb.append(" where  \n");
			sb.append("   (  \n");
			sb.append("       ( amountFrom < " + info.getAmountFrom()
					+ " and  amountTo > " + info.getAmountFrom() + ")  \n");
			sb.append("      or ");
			sb.append("       ( amountFrom < " + info.getAmountTo()
					+ " and  amountTo >= " + info.getAmountTo() + ")  \n");
			sb.append("      or ");
			sb.append("       ( amountFrom >= " + info.getAmountFrom()
					+ " and  amountTo < " + info.getAmountTo() + ")  \n");
			sb.append("   )  \n");
			sb.append(" and commissionType = " + info.getCommissionType());
			sb.append(" and isUrgent = " + info.getIsUrgent());
			sb.append(" and statusId = " + Constant.RecordStatus.VALID);
			if(info.getOfficeID()>0)
			{
				sb.append(" and officeid = " + info.getOfficeID());
			}
			if(info.getCurrencyID()>0)
			{
				sb.append(" and currencyid = " + info.getCurrencyID());
			}
			if (info.getId() > 0) {
				// if update
				sb.append("  and id <>  " + info.getId());
			}

			log.info(" SQL : \n" + sb.toString());

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			if (rs != null && rs.next()) {
				recordNumber = rs.getLong("recordNumber");
			}

			if (recordNumber <= 0) {
				flag = true;
			}

			sb.setLength(0);
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		System.out
				.println("======================= in isAllow() recordNumber ="
						+ recordNumber);
		System.out.println("======================= in isAllow() flag() ="
				+ flag);

		return flag;

	}

	
	
	/**
	 * 查询主表
	 */
	public long searchCommission(long l, long l1, long l2) throws Exception {
		long l3 = -1L;
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		Object obj = null;
		try {
			connection = getConnection();
			StringBuffer stringbuffer = new StringBuffer();
			stringbuffer.append("select id from sett_commissionrule where "
					+ "SETTLEMENTCLASS=? " + "and currencyid=? a"
					+ "nd officeid=? ");
			preparedstatement = connection.prepareStatement(stringbuffer
					.toString());
			preparedstatement.setLong(1, l2);
			preparedstatement.setLong(2, l);
			preparedstatement.setLong(3, l1);
			resultset = preparedstatement.executeQuery();
			if (resultset.next() && resultset != null) {
				l3 = resultset.getLong("id");
			}
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		} catch (Exception exception) {
			exception.getMessage();
		} finally {
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return l3;
	}

	/**
	 * 查询子表
	 */
	public Collection searchDetailByCommissionID(long l) throws Exception {
		Vector vector = new Vector();
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		StringBuffer stringbuffer = new StringBuffer();
		try {
			connection = getConnection();
			int i = 0;
			stringbuffer
					.append(" select * from  sett_commissionruleDetail where COMMISSIONSETTINGID="
							+ l + "order by MONEYSEGMENT");
			System.out.println(stringbuffer.toString());
			preparedstatement = connection.prepareStatement(stringbuffer
					.toString());
			BankpayCommissionSettingInfo bankpaycommissionsettinginfo;
			for (resultset = preparedstatement.executeQuery(); resultset.next(); vector
					.add(bankpaycommissionsettinginfo)) {
				bankpaycommissionsettinginfo = new BankpayCommissionSettingInfo();
				bankpaycommissionsettinginfo.setId(resultset.getLong("id"));
				bankpaycommissionsettinginfo.setCommissionsettingid(resultset
						.getLong("COMMISSIONSETTINGID"));
				bankpaycommissionsettinginfo.setAmountFrom(resultset
						.getDouble("MONEYSEGMENT"));
				bankpaycommissionsettinginfo.setCommissionRate(resultset
						.getDouble("AMOUNTPERCENT"));
				bankpaycommissionsettinginfo.setCommissionAmount(resultset
						.getDouble("STAIDAMOUNT"));
				bankpaycommissionsettinginfo.setMincommissionamount(resultset
						.getDouble("MINAMOUNT"));
				bankpaycommissionsettinginfo.setMaxcommissionamount(resultset
						.getDouble("MAXAMOUNT"));
				i++;
			}

			System.out
					.print("&777777777777777777777777777777777777777777777777--length:"
							+ i);
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		} catch (Exception exception) {
			exception.getMessage();
		} finally {
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return vector;
	}

	/**
	 * 从数据库中根据id物理删除一条记录
	 */
	public long delete(long l) throws Exception {
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		Object obj = null;
		Object obj1 = null;
		long l1;
		try {
			connection = getConnection();
			StringBuffer stringbuffer = new StringBuffer();
			stringbuffer
					.append("delete from sett_commissionruleDetail where ID="
							+ l);
			preparedstatement = connection.prepareStatement(stringbuffer
					.toString());
			l1 = preparedstatement.executeUpdate();
			cleanup(preparedstatement);
			cleanup(connection);
		} catch (Exception exception) {
			throw exception;
		} finally {
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return l1;
	}

	/**
	 * 修改保存该结算种类的手续费计算方法(子表)
	 */
	public long update(BankpayCommissionSettingInfo bankpaycommissionsettinginfo)
			throws Exception {
		long l = -1L;
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		Object obj = null;
		Object obj1 = null;
		try {
			connection = getConnection();
			String s = "update sett_commissionruleDetail set MONEYSEGMENT=?, STAIDAMOUNT=?,AMOUNTPERC"
					+ "ENT=?,MINAMOUNT=?,MAXAMOUNT=? where id=?";
			preparedstatement = connection.prepareStatement(s);
			int i = 1;
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getAmountFrom());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getCommissionAmount());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getCommissionRate());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getMincommissionamount());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getMaxcommissionamount());
			preparedstatement
					.setLong(i++, bankpaycommissionsettinginfo.getId());
			preparedstatement.executeUpdate();
			cleanup(preparedstatement);
			cleanup(connection);
		} catch (Exception exception) {
			throw exception;
		} finally {
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return l;
	}

	/**
	 * 保存该结算种类的手续费计算方法(子表)
	 */
	public long SaveDetail(
			BankpayCommissionSettingInfo bankpaycommissionsettinginfo)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		Object obj = null;
		long l = -1L;
		try {
			connection = getConnection();
			StringBuffer stringbuffer = new StringBuffer();
			long l1 = -1L;
			String s = "select nvl(max(id)+1,1) subMaxID from sett_commissionruleDetail";
			preparedstatement = connection.prepareStatement(s);
			resultset = preparedstatement.executeQuery();
			if (resultset.next() && resultset != null) {
				l1 = resultset.getLong(1);
			}
			stringbuffer
					.append("insert into sett_commissionruleDetail(ID,COMMISSIONSETTINGID, MONEYSEGMENT, S"
							+ "TAIDAMOUNT,AMOUNTPERCENT,MINAMOUNT,MAXAMOUNT) values(?,?,?,?,?,?,?) ");
			preparedstatement = connection.prepareStatement(stringbuffer
					.toString());
			int i = 1;
			preparedstatement.setLong(i++, l1);
			preparedstatement.setLong(i++, bankpaycommissionsettinginfo
					.getCommissionsettingid());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getAmountFrom());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getCommissionAmount());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getCommissionRate());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getMincommissionamount());
			preparedstatement.setDouble(i++, bankpaycommissionsettinginfo
					.getMaxcommissionamount());
			preparedstatement.executeUpdate();
			l = l1;
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		} catch (Exception exception) {
			throw exception;
		} finally {
			cleanup(resultset);
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return l;
	}

	/**
	 * 保存该结算种类的手续费计算方法(主表)
	 */
	public long Save(BankpayCommissionSettingInfo bankpaycommissionsettinginfo)
			throws Exception {
		long l = -1L;
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		Object obj = null;
		try {
			long l1 = -1L;
			connection = getConnection();
			String s = " select nvl(max(id)+1,1) from sett_commissionrule ";
			preparedstatement = connection.prepareStatement(s);
			ResultSet resultset = preparedstatement.executeQuery();
			if (resultset.next() && resultset != null) {
				l1 = resultset.getLong(1);
			}
			String s1 = " insert into sett_commissionrule(id,officeid,currencyid,settlementclass,statu"
					+ "sid,inputuserid,inputdate) values(?,?,?,?,?,?,sysdate)";
			preparedstatement = connection.prepareStatement(s1);
			int i = 1;
			preparedstatement.setLong(i++, l1);
			preparedstatement.setLong(i++, bankpaycommissionsettinginfo
					.getOfficeId());
			preparedstatement.setLong(i++, bankpaycommissionsettinginfo
					.getCurrencyId());
			preparedstatement.setLong(i++, bankpaycommissionsettinginfo
					.getSetttype());
			preparedstatement.setLong(i++, 1L);
			preparedstatement.setLong(i++, bankpaycommissionsettinginfo
					.getInputUserId());
			preparedstatement.executeUpdate();
			l = l1;
		} catch (Exception exception) {
			throw exception;
		} finally {
			cleanup(preparedstatement);
			cleanup(connection);
		}
		return l;
	}

	/**
	 * 计算手续费
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lSettlementclass
	 * @param lMoney
	 * @return
	 * @throws Exception
	 */
	public double findbyPoundage(long l, long l1, long l2, double d)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		double d1 = 0.0D;
		byte byte0 = -1;
		String s = ">";
		try {
			connection = getConnection();
			int i = findbyPoundageCount(l, l1, l2, d);
			if (i < 0) {
				s = " < " + d;
			}
			if (i >= 0) {
				s = " > " + d;
			}
			if (i == -1) {
				s = " in ((select max(moneysegment) from sett_commissionruledetail where commissio"
						+ "nsettingid=(select id from sett_commissionrule where officeid="
						+ l
						+ " and currencyid="
						+ l1
						+ " and settlementclass=1)))";
			}
			String s1 = "select moneysegment,staidamount,minamount,maxamount,staidamount+"
					+ d
					+ "*amountpercent exes,"
					+ "case when (staidamount+"
					+ d
					+ "*amountpercent)<minamount then minamount else -1 end min_money,"
					+ "case when (staidamount+"
					+ d
					+ "*amountpercent)>maxamount then maxamount else -1 end max_money"
					+ " from sett_commissionruledetail "
					+ "where moneysegment"
					+ s
					+ " and commissionsettingid=(select id from sett_commissionrule "
					+ "where officeid="
					+ l
					+ " and currencyid="
					+ l1
					+ " and settlementclass="
					+ l2
					+ ") order by moneysegment desc";
			preparedstatement = connection.prepareStatement(s1);
			resultset = preparedstatement.executeQuery();
			int j = 1;
			while (resultset.next()) {
				if (j++ > i) {
					if (resultset.getLong("min_money") != -1L) {
						d1 = resultset.getLong("min_money");
					} else if (resultset.getLong("max_money") != -1L) {
						d1 = resultset.getLong("max_money");
					} else {
						d1 = resultset.getLong("exes");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("Gen_E001");
		} finally {
			try {
				if (resultset != null) {
					resultset.close();
					resultset = null;
				}
				if (preparedstatement != null) {
					preparedstatement.close();
					preparedstatement = null;
				}
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}
		return d1;
	}

	/**
	 * 手续费总数
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lSettlementclass
	 * @return int
	 * @throws Exception
	 */
	private int findbyPoundageCount(long l, long l1, long l2, double d)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		int i = -1;
		try {
			connection = getConnection();
			String s = "select count(*)-1 from sett_commissionruledetail where moneysegment>"
					+ d
					+ " and commissionsettingid="
					+ "(select id from sett_commissionrule where officeid="
					+ l
					+ " and currencyid="
					+ l1
					+ " and settlementclass="
					+ l2 + ") " + "order by moneysegment";
			System.out.println(s);
			preparedstatement = connection.prepareStatement(s);
			resultset = preparedstatement.executeQuery();
			if (resultset.next()) {
				i = resultset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("Gen_E001");
		} finally {
			try {
				if (resultset != null) {
					resultset.close();
					resultset = null;
				}
				if (preparedstatement != null) {
					preparedstatement.close();
					preparedstatement = null;
				}
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}
		return i;
	}
	
	
	
	public Collection getAllVouher_Type()throws Exception{
		
        String strSQL = "";
        Vector v = new Vector();

        long recordCount = -1;

        try
        {  
          

            initDAO();

            strSQL = " Select * from IBVF_TYPE_SET vt where vt.status = '1'   ";

            prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while (rs != null && rs.next())
            {
            	VoucherTypeInfo info = new VoucherTypeInfo();
            	
                info.setID(rs.getString("ID"));
                info.setVoucher_Type_Code(rs.getString("voucher_Type_Code"));
                info.setVoucher_Type_Name(rs.getString("voucher_Type_Name"));
                //info.setBuy_Unit_Price(rs.getDouble("buy_Unit_Price"));
                //info.setExpenses_fees(rs.getDouble("expenses_fees"));
                //info.setIs_Support_Renew(rs.getString("is_Support_Renew"));
                //info.setModifierId(rs.getString("modifierId"));
                //info.setModTime(rs.getTimestamp("modTime"));
                //info.setPdf_Participant_BankNoId(rs.getString("pdf_Participant_BankNoId"));
                //info.setProcedure_fee(rs.getDouble("procedure_fee"));
                //info.setSell_Unit_Price(rs.getDouble("sell_Unit_Price"));
                //info.setStatus(rs.getString("status"));
                //info.setVouching_Status(rs.getString("vouching_Status"));
                
                //info.setExtension_Field1(rs.getString("extension_Field1"));
                //info.setExtension_Field1(rs.getString("extension_Field2"));
                //info.setExtension_Field1(rs.getString("extension_Field3"));
                //info.setExtension_Field1(rs.getString("extension_Field4"));
                //info.setExtension_Field1(rs.getString("extension_Field5"));
                

                v.add(info);
            }
            if(rs != null)
    		{
    			rs.close();
    			rs = null;
    		}
            
            finalizeDAO();
     
        } catch (Exception e)
        {
            e.printStackTrace();
            throw e ;
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
                throw e1;
            }
        }
        return (v.size() > 0 ? v : null);
	}
	
//
//	public static void main(String args[]) throws Exception {
//		BankpayCommissionSettingInfo bankpaycommissionsettinginfo = new BankpayCommissionSettingInfo();
//		Sett_CommissionSettingDAO sett_commissionsettingdao = new Sett_CommissionSettingDAO();
//		bankpaycommissionsettinginfo.setOfficeId(1L);
//		bankpaycommissionsettinginfo.setCurrencyId(1L);
//		bankpaycommissionsettinginfo.setSetttype(1L);
//		bankpaycommissionsettinginfo.setInputUserId(1L);
//		sett_commissionsettingdao.Save(bankpaycommissionsettinginfo);
//	}
}

/*
 * Created on 2006-2-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.clientmanage.util;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.loancommonsetting.dao.AtTermAwakeSettingDao;
import com.iss.itreasury.loan.loancommonsetting.dao.LoanCommonSettingDao;
import com.iss.itreasury.loan.loancommonsetting.dataentity.AtTermAwakeInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientTypeInfo;
import com.iss.itreasury.loan.loancommonsetting.dataentity.CurrencyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_ClientDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author chuanliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CMBean {
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this); //
	
	/**
	 * findContractByMultiOption 查找合同信息
	 * 根据所给定的条件，查找合同信息
	 * 操作LoanInfo、ContractInfo数据表
	 * 查询记录
	 * haoning 2003-09-01
	 * @param lLoanType 贷款种类
	 * @param lContractIDBeg 需查找的合同编号下限
	 * @param lContractIDEnd 需查找的合同编号上限
	 * @param lLoanClientID 贷款单位
	 * @param dLoanSumBeg 贷款金额的下限
	 * @param dLoanSumEnd 贷款金额的上限
	 * @param lContractStatus 合同状态
	 * @param lContractManagerID 合同管理人
	 * @param lCurrencyID 币种
	 * @param lOfficeID 办事处ID
	 * @param long           lPageLineCount        每页页行数条件
	 * @param long           lPageNo               第几页条件
	 * @param long           lOrderParam           排序条件，根据此参数决定结果集排序条件
	 * @param long           lDesc                 升序或降序
	 * @return Collection  合同详细信息
	 * @throws RemoteException
	 */
	public Collection findContractByMultiOption(
		long lLoanType,
		long lContractIDBeg,
		long lContractIDEnd,
		long lLoanClientID,
		double dLoanSumBeg,
		double dLoanSumEnd,
		long lContractStatus,
		long lContractManagerID,
		long lCurrencyID,
		long lOfficeID,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc)
		throws IException,RemoteException
	{
		Vector vReturn = new Vector(); //当前页结果集
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		int iIndex = 0;
		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		long[] lLoanTypeTmp = null; //贷款类型值字符串
		String strLoanTypeTmp = "";
        //long[] lStatusTmp=null;
        String strStatusTmp = "";
		double dLoanSum = 0.0; //满足条件的合同贷款总金额
		long lRecordCount = -1; //总记录数
		long lPageCount = -1; //总页数
		long lRowNumStart = -1; //开始记录
		long lRowNumEnd = -1; //结束记录
		try
		{
			con = Database.getConnection();
			//取所有贷款类型的值
			lLoanTypeTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			for (int i = 0; i < lLoanTypeTmp.length; i++)
			{
				if (i == 0)
					strLoanTypeTmp += "" + lLoanTypeTmp[i];
				else
					strLoanTypeTmp += "," + lLoanTypeTmp[i];
			}
            //取合同状态
            strStatusTmp  = ""+LOANConstant.ContractStatus.SAVE;
            strStatusTmp += ","+LOANConstant.ContractStatus.SUBMIT;
            strStatusTmp += ","+LOANConstant.ContractStatus.CHECK;
            strStatusTmp += ","+LOANConstant.ContractStatus.NOTACTIVE;
            strStatusTmp += ","+LOANConstant.ContractStatus.ACTIVE;
            strStatusTmp += ","+LOANConstant.ContractStatus.EXTEND;
            strStatusTmp += ","+LOANConstant.ContractStatus.OVERDUE;
            strStatusTmp += ","+LOANConstant.ContractStatus.DELAYDEBT;
            strStatusTmp += ","+LOANConstant.ContractStatus.BADDEBT;
            strStatusTmp += ","+LOANConstant.ContractStatus.FINISH;
            
			/********** 查找满足条件的--合同数目--和--总金额**********/
			strSQL_Count = " select count(*),sum(a.MEXAMINEAMOUNT) dsum ";
			strSQL_Table =
				"       from LOAN_CONTRACTFORM a,CLIENT c "
					+ "      ,USERINFO u ,Client c2 "
					+ "      , LOAN_INTERESTRATE r "
					+ " where c.ID(+)=a.NBORROWCLIENTID "
					+ "   and a.NCONSIGNCLIENTID=c2.ID(+) "
					+ "   and u.ID(+)=a.NINPUTUSERID "
					+ "   and a.NBANKINTERESTID=r.ID(+) ";
			/**************设置查找条件*****************/
			//加入项目的贷款类型（大桥只有自营短期贷款）
			strSQL_Table += " and a.nTypeID in (" + strLoanTypeTmp + ")";
			if (lOfficeID > -1)
			{
				strSQL_Option += " and a.nOfficeID= " + lOfficeID;
			}
			if (lLoanType > -1) //合同贷款类型
			{
				strSQL_Option += " and a.nTypeID = " + lLoanType;
			}
			if (lCurrencyID > -1)
			{
				strSQL_Option += " and a.nCurrencyID= " + lCurrencyID;
			}
			if (lContractIDBeg > -1) //合同起始ID
			{
				strSQL_Option += " and a.ID>= " + lContractIDBeg;
			}
			if (lContractIDEnd > -1) //合同结束ID
			{
				strSQL_Option += " and a.ID<= " + lContractIDEnd;
			}
			if (lLoanClientID > -1) //贷款单位
			{
				strSQL_Option += " and a.NBORROWCLIENTID= " + lLoanClientID;
			}
			if (dLoanSumBeg > 0) //最小金额
			{
				strSQL_Option += " and a.MEXAMINEAMOUNT>=? ";
			}
			if (dLoanSumEnd > 0) //最大金额
			{
				strSQL_Option += " and a.MEXAMINEAMOUNT<=? ";
			}
			if (lContractStatus > -1) //合同状态
			{
				strSQL_Option += " and a.nStatusID= " + lContractStatus;
			}
			else
			{
				strSQL_Option += " and a.nStatusID in ("+strStatusTmp+")";
			}
			if (lContractManagerID > -1) //合同管理人
			{
				strSQL_Option += " and a.nInputUserID= " + lContractManagerID;
			}
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option;
			log4j.info("  SQL =  " + strSQL);
			ps = con.prepareStatement(strSQL);
			/***********对条件赋值***********/
			iIndex = 1;
			if (dLoanSumBeg > 0)
			{
				ps.setDouble(iIndex, dLoanSumBeg);
				iIndex++;
			}
			if (dLoanSumEnd > 0)
			{
				ps.setDouble(iIndex, dLoanSumEnd);
				iIndex++;
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1); //得到总记录数
				dLoanSum = rs.getLong(2); //得到总金额
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
			//计算总页数
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			log4j.info("符合条件的总记录数=" + lRecordCount);
			log4j.info("总金额=" + dLoanSum);
			if (lRecordCount > 0)
			{
				//返回结果集， 分页显示，显示下一页
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1; //开始
				lRowNumEnd = lRowNumStart + lPageLineCount - 1; //结束
				///////////////////////////////////////////////////////////
					strSQL_Select = " select a.ID as ContractID " //合同ID
		+" ,a.SCONTRACTCODE as ContractCode " //合同编号
		+" ,a.NTYPEID " //贷款种类
		+" ,c.sName as BorrowClientName " //借款单位 
		+" ,c2.sName as ConsignClientName " //委托单位 
		+" ,a.NCONSIGNCLIENTID " 
        +" ,a.MEXAMINEAMOUNT"//批准金额
        +" ,a.MLOANAMOUNT " //金额
		+" ,a.NBANKINTERESTID as RateID " //利率ID
		+" ,r.MRATE as fRate " //利率
        +" ,a.mdiscountrate "
		+" ,a.NINTERVALNUM as RateName " //利率期限
		+" ,a.NSTATUSID " //状态
		+" ,a.NINPUTUSERID as InputUserID " //合同管理人ID
		+" ,u.sName as InputUserName " //合同管理人
	+" ,a.NCURRENCYID " + " ,a.NOFFICEID ";
				//--------------排序---------------//
				switch ((int) lOrderParam)
				{
					case 1 : //按合同编号排序
						strSQL_Order += " order by a.SCONTRACTCODE ";
						break;
					case 2 : //按贷款种类排序
						strSQL_Order += " order by a.nTypeID";
						break;
					case 3 : //按贷款单位排序
						strSQL_Order += " order by a.nBorrowClientID";
						break;
					case 4 : //按委托单位排序，须单独访问委托合同表
						strSQL_Order += " order by a.nConsignClientID";
						break;
					case 5 : //按金额排序
						strSQL_Order += " order by a.MEXAMINEAMOUNT";
						break;
					case 6 : //按执行利率排序
						strSQL_Order += " order by a.mInterestRate,a.mAdjustRate,a.mDiscountRate ";
						break;
					case 7 : //按期限排序
						strSQL_Order += " order by a.NINTERVALNUM ";
						break;
					case 8 : //按合同状态排序
						strSQL_Order += " order by a.nStatusID";
						break;
					case 9 : //按合同管理人排序
						strSQL_Order += " order by a.NINPUTUSERID";
						break;
					default :
						strSQL_Order += "";
				}
				//判断是升序还是降序，升序是系统默认的，降序是desc
				if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
				{
					strSQL_Order += " desc";
				}
				//加上查找限制条件  加上排序条件  排序方式
				strSQL =
					"select * from ( select b.*, rownum num from "
						+ " ( "
						+ strSQL_Select
						+ strSQL_Table
						+ strSQL_Option
						+ strSQL_Order
						+ " ) b )"
						+ " WHERE num BETWEEN ? AND ? ";
				//当前页行记录范围
				log4j.info("  SQL = " + strSQL);
				ps = con.prepareStatement(strSQL);
				/***********对条件赋值***********/
				iIndex = 1;
				if (dLoanSumBeg > 0)
				{
					ps.setDouble(iIndex, dLoanSumBeg);
					iIndex++;
				}
				if (dLoanSumEnd > 0)
				{
					ps.setDouble(iIndex, dLoanSumEnd);
					iIndex++;
				}
				ps.setLong(iIndex, lRowNumStart); //给入起始行号
				iIndex++;
				ps.setLong(iIndex, lRowNumEnd); //给入结束行号
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					//返回ContractInfo
					ContractInfo ci = new ContractInfo();
					ci.setContractID(rs.getLong("ContractID")); //
					ci.setContractCode(rs.getString("ContractCode")); //合同编号
					ci.setLoanTypeID(rs.getLong("nTypeID")); //贷款类型
					ci.setLoanTypeName(LOANConstant.LoanType.getName(ci.getLoanTypeID()));
					//贷款单位
					ci.setBorrowClientName(rs.getString("BorrowClientName"));
					//委托单位ID
					ci.setClientID(rs.getLong("NCONSIGNCLIENTID"));
					//ci.setLoanAmount(rs.getDouble("mLoanAmount")); //金额
                    ci.setLoanAmount(rs.getDouble("MEXAMINEAMOUNT")); //批准金额
                    ci.setExamineAmount(rs.getDouble("MEXAMINEAMOUNT"));
					//ci.setInterestRate(rs.getLong("RateID")); //利率
					ci.setIntervalNum(rs.getLong("RateName")); //利率期限
					ci.setInputUserName(rs.getString("InputUserName"));
					//合同管理人
					ci.setStatusID(rs.getLong("NSTATUSID")); //状态
					//状态描述
					ci.setStatus(LOANConstant.ContractStatus.getName(ci.getStatusID()));
					ci.setAllAmount(dLoanSum); //总金额
					ci.setPageCount(lPageCount); //总页数
					ci.setAllRecord(lRecordCount); //总记录
					//--------------委托单位名称--------------//  
					if (rs.getString("ConsignClientName") == null)
					{
						ci.setClientName("");
					}
					else
					{
						ci.setClientName(rs.getString("ConsignClientName"));
					}
					//--------------得到合同执行利率--------------//
                    if(ci.getLoanTypeID()==LOANConstant.LoanType.TX)
                    {
                        ci.setInterestRate(rs.getDouble("mdiscountrate"));
                    }
                    else
                    {
                        RateInfo ri = new RateInfo();
                        ContractDao dao = new ContractDao();
                        ri=dao.getLatelyRate(-1,ci.getContractID(),null);
                        ci.setInterestRate(ri.getLateRate());
                    }
                    //----------------------------------------//
					vReturn.addElement(ci);
				}
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
		}
		return (vReturn.size() > 0 ? vReturn : null);
	}
	/**
	 * changeContractManager  合同管理人变更
	 * 变更合同的管理人
	 * 操作ContractInfo数据表
	 * 更新表中的nInputUserID字段
	 * 只对当前页中的合同进行变更。全选即当前页种的内容被全部选择。
	 * haoning
	 * @param lContractID 合同标示
	 * @param lContractManagerID 合同管理人标识
	 * @return long 成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public long changeContractManager(long lContractID[], long lContractManagerID) 
    throws IException,RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = "";
        String strSQL1 = "";
        long lStatusID = -1;
        long lInputUsetID = -1;
        long lCheckUserID = -1;
        long lNextCheckLevel = -1;
		long lResult = -1;
        int nIndex=0;
		int i = 0;
		try
		{
			con = Database.getConnection();
			log4j.info("更改合同管理人");
			//更改合同信息
            strSQL1=" select nStatusID,nInputUserID,nNextCheckUserID,nNextCheckLevel "
                   +" from LOAN_ContractFORM where id = ? ";
			int nLength = 0;
			nLength = lContractID.length;
			while (i < nLength) //如果合同数组的长度>0
			{
                ps = con.prepareStatement(strSQL1);
                ps.setLong(1, lContractID[i]);
                rs=ps.executeQuery();
                if(rs != null && rs.next())
                {
                    lStatusID = rs.getLong(1);
                    lInputUsetID = rs.getLong(2);
                    lCheckUserID = rs.getLong(3);
                    lNextCheckLevel = rs.getLong(4);
                }
                if(rs != null)
                {
                    rs.close();
                    rs=null;
                }
                if(ps != null)
                {
                    ps.close();
                    ps=null;
                }
                //撰写状态，修改录入人和审核人
                //已提交状态，录入人==审核人，两个都修改，否则只修改录入人 
                if((lStatusID == LOANConstant.ContractStatus.SAVE)
                    ||((lStatusID == LOANConstant.ContractStatus.SUBMIT)
                        &&(lInputUsetID==lCheckUserID)&&(lNextCheckLevel==1)))
                {
                    strSQL = " update LOAN_ContractFORM "
                           + " set NINPUTUSERID = ? " 
                           + " ,nNextCheckUserID=? "
                           + " where id = ? ";
                }
                //其它状态，只修改录入人
                else
                {
                    strSQL = " update LOAN_ContractFORM "
                           + " set NINPUTUSERID = ? " 
                           + " where id = ? ";
                }
                log4j.info("SQL:"+strSQL);
				ps = con.prepareStatement(strSQL);
                nIndex=1;
				ps.setLong(nIndex, lContractManagerID);
                nIndex++;
                //*
                if((lStatusID == LOANConstant.ContractStatus.SAVE)
                    ||((lStatusID == LOANConstant.ContractStatus.SUBMIT)
                        &&(lInputUsetID==lCheckUserID)&&(lNextCheckLevel==1)))
                {
                    ps.setLong(nIndex, lContractManagerID);
                    nIndex++;
                }//*/
				ps.setLong(nIndex, lContractID[i]);
				lResult = ps.executeUpdate();
				//关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps=null;
                }
				i++;
			}
			//更改贷款信息
			log4j.info("修改申请管理人");
			i = 0;
			strSQL = " update LOAN_LoanFORM set NINPUTUSERID = ? " 
                   + " where id = (select distinct nLoanID " 
                   + " from LOAN_ContractFORM where id= ?)";
			nLength = lContractID.length;
            log4j.info("SQL:"+strSQL);
			while (i < nLength)
			{
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lContractManagerID);
				ps.setLong(2, lContractID[i]);
				lResult = ps.executeUpdate();
				//关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
				i++;
			}
            
            //更改合同相应的放款管理人
			log4j.info("修改放款单管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_payform set nInputUserID = ? " 
                   + " ,nNextCheckUserID = ? "
                   + " where 1 = 1 "
                   + " and nInputUserID = nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + " and nContractID =  ? "
                   + " and nStatusID > 0 ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_payform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的逾期管理人
            log4j.info("修改逾期申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_overdueform set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_overdueform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            
            //更改合同相应的展期申请管理人 
            log4j.info("修改展期申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_extendform set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_extendform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的免还管理人
            log4j.info("修改免还申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_freeform set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_freeform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            
            //更改合同相应的提前还款管理人
            log4j.info("修改提前还款申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_aheadrepayform set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_aheadrepayform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的合同风险状态管理人
            log4j.info("修改风险状态变更申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_risklevel set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_risklevel set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的合同状态管理人
            log4j.info("修改合同状态变更申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_contractstatus set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_contractstatus set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的提款管理人
            log4j.info("修改银团体款通知单管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_YT_drawform set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_YT_drawform set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的合同执行利率调整管理人
            log4j.info("修改合同执行利率调整申请管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update loan_rateadjustpaycondition set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update loan_rateadjustpaycondition set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //更改合同相应的贴现凭证管理人
            log4j.info("修改贴现凭证管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update Loan_DiscountCredence set nInputUserID = ? " 
                   + "    ,nNextCheckUserID = ? "
                   + " where nInputUserID =  nNextCheckUserID "
                   + " and nNextCheckLevel = 1 "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update Loan_DiscountCredence set nInputUserID = ? " 
                   + " where nInputUserID <>  nNextCheckUserID "
                   + "        and nContractID =  ? "
                   + "        and nStatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            
            //更改合同相应的担保收款通知单管理人
            log4j.info("修改担保收款通知单管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update Loan_AssureChargeForm set InputUserID = ? " 
                   + "    ,NextCheckUserID = ? "
                   + " where InputUserID = NextCheckUserID "
                   + " and NextCheckLevel = 1 "
                   + "        and ContractID =  ? "
                   + "        and StatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update Loan_AssureChargeForm set InputUserID = ? " 
                   + " where InputUserID <>  NextCheckUserID "
                   + "        and ContractID =  ? "
                   + "        and StatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            
            //更改合同相应的保后处理管理人
            log4j.info("修改保后处理管理人");
            //录入人=审核人，两个都改
            i = 0;
            strSQL = " update Loan_AssureManagementForm set InputUserID = ? " 
                   + "    ,NextCheckUserID = ? "
                   + " where InputUserID = NextCheckUserID "
                   + " and NextCheckLevel = 1 "
                   + "        and ContractID =  ? "
                   + "        and StatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractManagerID);
                ps.setLong(3, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            //录入人 ！= 审核人 ，只改录入人
            i = 0;
            strSQL = " update Loan_AssureChargeForm set InputUserID = ? " 
                   + " where InputUserID <>  NextCheckUserID "
                   + "        and ContractID =  ? "
                   + "        and StatusID > 0 "
                   +" ";
            nLength = lContractID.length;
            while (i < nLength)
            {
                ps = con.prepareStatement(strSQL);
                ps.setLong(1, lContractManagerID);
                ps.setLong(2, lContractID[i]);
                lResult = ps.executeUpdate();
                //关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps = null;
                }
                i++;
            }
            
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
			//lResult = -1;
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * findLoanByMultiOption 查找申请书信息
	 * 根据所给定的条件，查找申请书信息
	 * 操作LoanInfo、ContractInfo数据表
	 * 查询记录
	 * haoning 2003-09-01
	 * @param lLoanType 贷款种类
	 * @param lLoanIDBeg 需查找的申请书编号下限
	 * @param lLoanIDEnd 需查找的申请书编号上限
	 * @param lLoanClientID 贷款单位
	 * @param dLoanSumBeg 贷款金额的下限
	 * @param dLoanSumEnd 贷款金额的上限
	 * @param lLoanStatus 申请书状态
	 * @param lLoanManagerID 申请书管理人
	 * @param lCurrencyID 币种
	 * @param lOfficeID 办事处ID
	 * @param lPageLineCount 每页页行数条件
	 * @param lPageNo 第几页条件
	 * @param lOrderParam  排序条件，根据此参数决定结果集排序条件
	 * @param lDesc    升序或降序
	 * @return Collection  申请书详细信息
	 * @throws RemoteException
	 */
	public Collection findLoanByMultiOption(
		long lLoanType,
		long lLoanIDBeg,
		long lLoanIDEnd,
		long lLoanClientID,
		double dLoanSumBeg,
		double dLoanSumEnd,
		long lLoanStatus,
		long lLoanManagerID,
		long lCurrencyID,
		long lOfficeID,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc)
		throws IException,RemoteException
	{
		Vector vReturn = new Vector(); //当前页结果集
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iIndex = 0;
		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		long[] lLoanTypeTmp = null;
		String strLoanTypeTmp = "";
		String strLoanStatus = "";
		long lRecordCount = -1; //总记录数
		double dLoanSum = 0.0; //满足条件的合同贷款总金额
		long lPageCount = -1; //总页数
		long lRowNumStart = -1; //开始记录
		long lRowNumEnd = -1; //结束记录
		try
		{
			//查找申请书信息
			con = Database.getConnection();
			//取所有贷款类型的值
			lLoanTypeTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			for (int i = 0; i < lLoanTypeTmp.length; i++)
			{
				if (i == 0)
					strLoanTypeTmp += "" + lLoanTypeTmp[i];
				else
					strLoanTypeTmp += "," + lLoanTypeTmp[i];
			}
			strLoanStatus = "" + LOANConstant.LoanStatus.SAVE;
			strLoanStatus += "," + LOANConstant.LoanStatus.SUBMIT;
			//strLoanStatus += ","+LOANConstant.LoanStatus.CHECK;
			//strLoanStatus += ","+LOANConstant.LoanStatus.REFUSE;
			/********** 查找满足条件的--申请书数目--和--总金额**********/
			strSQL_Count = " select count(*),sum(a.MLOANAMOUNT) dsum ";
			strSQL_Table = " from LOAN_LOANFORM a,CLIENT c " 
                         + "      ,Client d,USERINFO u"
                         + " where a.NBORROWCLIENTID=c.ID(+) " 
                         + "      and a.NCONSIGNCLIENTID=d.ID(+) "
                         + "      and a.NINPUTUSERID=u.ID(+) ";
			/**************设置查找条件*****************/
			//加入项目的贷款类型（大桥只有自营短期贷款）
			strSQL_Table += " and a.nTypeID in (" + strLoanTypeTmp + ")";
			strSQL_Table += " and a.nStatusID in(" + strLoanStatus + ")";
			if (lOfficeID > -1)
			{
				strSQL_Option += " and a.nOfficeID= " + lOfficeID;
			}
			if (lLoanType > -1) //贷款类型
			{
				strSQL_Option += " and a.nTypeID = " + lLoanType;
			}
			if (lCurrencyID > -1)
			{
				strSQL_Option += " and a.nCurrencyID= " + lCurrencyID;
			}
			if (lLoanIDBeg > -1) //需查找的申请书编号下限
			{
				strSQL_Option += " and a.ID>= " + lLoanIDBeg;
			}
			if (lLoanIDEnd > -1) //需查找的申请书编号上限
			{
				strSQL_Option += " and a.ID<= " + lLoanIDEnd;
			}
			if (lLoanClientID > -1) //贷款单位
			{
				strSQL_Option += " and a.NBORROWCLIENTID= " + lLoanClientID;
			}
			if (dLoanSumBeg > 0) //贷款金额的下限
			{
				strSQL_Option += " and a.MLOANAMOUNT>=? ";
			}
			if (dLoanSumEnd > 0) //贷款金额的上限--最大金额
			{
				strSQL_Option += " and a.MLOANAMOUNT<=? ";
			}
			if (lLoanStatus > -1) //申请书状态
			{
				strSQL_Option += " and a.nStatusID= " + lLoanStatus;
			}
			if (lLoanManagerID > -1) //申请书管理人
			{
				strSQL_Option += " and a.nInputUserID= " + lLoanManagerID;
			}
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option;
			log4j.info("申请书SQL=  " + strSQL);
			ps = con.prepareStatement(strSQL);
			/***********对条件赋值***********/
			iIndex = 1;
			if (dLoanSumBeg > 0)
			{
				ps.setDouble(iIndex, dLoanSumBeg);
				iIndex++;
			}
			if (dLoanSumEnd > 0)
			{
				ps.setDouble(iIndex, dLoanSumEnd);
				iIndex++;
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1); //得到总记录数
				dLoanSum = rs.getLong(2); //得到总金额
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
			//计算总页数
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			log4j.info("符合条件的总记录数=" + lRecordCount);
			//log4j.info("总金额=" + dLoanSum);
			if (lRecordCount > 0)
			{
				//返回结果集， 分页显示，显示下一页
				lRowNumStart = (lPageNo - 1) * lPageLineCount + 1; //开始
				lRowNumEnd = lRowNumStart + lPageLineCount - 1; //结束
				///////////////////////////////////////////////////////////
					strSQL_Select = " select a.ID as LoanID " //申请书ID
		+" ,a.SAPPLYCODE as ApplyCode " //申请书编号
		+" ,a.NTYPEID " //贷款种类
		+" ,a.NBORROWCLIENTID,c.sName as BorrowClientName " //借款单位
        +" ,d.sName as ConsignClientName "//委托单位
		+" ,a.NCONSIGNCLIENTID " //委托单位ID
		+" ,a.MLOANAMOUNT " //金额
		+" ,a.NBANKINTERESTID as RateID " 
        +" ,a.mdiscountrate " //贴现利率
		+" ,a.NINTERVALNUM as RateName " //利率期限
		+" ,a.NSTATUSID " //申请书状态
		+" ,a.NINPUTUSERID as InputUserID " //申请书管理人ID
		+" ,u.sName as InputUserName " //申请书管理人
	    +" ,a.NCURRENCYID " + " ,a.NOFFICEID ";
				//--------------排序---------------//
				switch ((int) lOrderParam)
				{
					case 1 : //按申请书编号排序
						strSQL_Order += " order by a.SAPPLYCODE ";
						break;
					case 2 : //按贷款种类排序
						strSQL_Order += " order by a.nTypeID";
						break;
					case 3 : //按贷款单位排序
						strSQL_Order += " order by a.nBorrowClientID";
						break;
					case 4 : //按委托单位排序，须单独访问委托合同表
						strSQL_Order += " order by a.nConsignClientID";
						break;
					case 5 : //按金额排序
						strSQL_Order += " order by a.mLoanAmount";
						break;
					case 6 : //按执行利率排序
						strSQL_Order += " order by a.mInterestRate,a.mAdjustRate,a.mDiscountRate ";
						break;
					case 7 : //按期限排序
						strSQL_Order += " order by a.NINTERVALNUM ";
						break;
					case 8 : //按申请书状态排序
						strSQL_Order += " order by a.nStatusID";
						break;
					case 9 : //按申请书管理人排序
						strSQL_Order += " order by a.NINPUTUSERID";
						break;
					default :
						strSQL_Order += "";
				}
				//判断是升序还是降序，升序是系统默认的，降序是desc
				if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
				{
					strSQL_Order += " desc";
				}
				//加上查找限制条件  加上排序条件  排序方式
				strSQL = "select * from ( select b.*, rownum num from " 
                       + " ( " + strSQL_Select + strSQL_Table 
                               + strSQL_Option + strSQL_Order 
                       + " ) b ) ";
				strSQL += " WHERE num BETWEEN ? AND ? "; //当前页行记录范围
				log4j.info("  SQL = " + strSQL);
				ps = con.prepareStatement(strSQL);
				/***********对条件赋值***********/
				iIndex = 1;
				if (dLoanSumBeg > 0)
				{
					ps.setDouble(iIndex, dLoanSumBeg);
					iIndex++;
				}
				if (dLoanSumEnd > 0)
				{
					ps.setDouble(iIndex, dLoanSumEnd);
					iIndex++;
				}
				ps.setLong(iIndex, lRowNumStart); //给入起始行号
				iIndex++;
				ps.setLong(iIndex, lRowNumEnd); //给入结束行号
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					//返回ContractInfo
					ContractInfo ci = new ContractInfo();
					ci.setContractID(rs.getLong("LoanID"));
					ci.setApplyCode(rs.getString("ApplyCode")); //申请书编号
					ci.setLoanTypeID(rs.getLong("nTypeID")); //贷款类型
					ci.setLoanTypeName(LOANConstant.LoanType.getName(ci.getLoanTypeID()));
					//贷款单位
					//ci.setBorrowClientID(rs.getLong("NBORROWCLIENTID"));
					ci.setBorrowClientName(rs.getString("BorrowClientName"));
					//委托单位
					ci.setClientID(rs.getLong("NCONSIGNCLIENTID"));
                    //委托单位名称
                    ci.setClientName(rs.getString("ConsignClientName"));
					ci.setLoanAmount(rs.getDouble("mLoanAmount")); //金额
					//ci.setInterestRate(rs.getDouble("fRate")); //利率
					ci.setIntervalNum(rs.getLong("RateName")); //利率期限
					//ci.setInputUserID(rs.getLong("InputUserID"));
					ci.setInputUserName(rs.getString("InputUserName"));
					//申请书管理人
					ci.setStatusID(rs.getLong("NSTATUSID")); //申请书状态
					//状态描述
					ci.setStatus(LOANConstant.ContractStatus.getName(ci.getStatusID()));
					ci.setAllAmount(dLoanSum); //总金额                    
					ci.setPageCount(lPageCount); //总页数
					ci.setAllRecord(lRecordCount); //总记录
					//--------------得到合同利率--------------//
                    if(ci.getLoanTypeID()==LOANConstant.LoanType.TX)
                    {
                        ci.setInterestRate(rs.getDouble("mdiscountrate"));
                    }
                    else
                    {
                        RateInfo ri = new RateInfo();
                        ContractDao dao = new ContractDao();
                        ri=dao.getLatelyRate(ci.getContractID(),-1,null);
                        ci.setInterestRate(ri.getLateRate());
                    }
					vReturn.addElement(ci);
				}
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return (vReturn.size() > 0 ? vReturn : null);
	}
	/**
	 * changeLoanManager  申请书管理人变更
	 * 变更申请书的管理人
	 * 操作ContractInfo数据表
	 * 更新表中的nInputUserID字段
	 * 只对当前页中的申请书进行变更。全选即当前页种的内容被全部选择。
	 * Author: haoning 
	 * time  : 2003-09-15
	 * @param lLoanID 申请书标示
	 * @param lLoanManagerID 申请书管理人标识
	 * @return long 成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public long changeLoanManager(long lLoanID[], long lLoanManagerID) 
    throws IException,RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = "";
        String strSQL1 = "";
        long lStatusID = -1;
        long lInputUsetID = -1;
        long lCheckUserID = -1;
		long lResult = -1;
		int i = 0;
        int nIndex = 0;
		try
		{
			//更改申请书管理人
			con = Database.getConnection();
            strSQL1=" select nStatusID,nInputUserID,nNextCheckUserID "
                   +" from LOAN_LoanFORM where id = ? ";
			int nLength = lLoanID.length;
			while (i < nLength)
			{
				//log4j.info("SQL=" + strSQL);
                ps = con.prepareStatement(strSQL1);
                ps.setLong(1, lLoanID[i]);
                rs=ps.executeQuery();
                if(rs != null && rs.next())
                {
                    lStatusID = rs.getLong(1);
                    lInputUsetID = rs.getLong(2);
                    lCheckUserID = rs.getLong(3);
                }
                if(rs != null)
                {
                    rs.close();
                    rs=null;
                }
                if(ps != null)
                {
                    ps.close();
                    ps=null;
                }
                //撰写状态，修改录入人和审核人
                //已提交状态，录入人==审核人，两个都修改，否则只修改录入人 
                if((lStatusID == LOANConstant.LoanStatus.SAVE)
                    ||((lStatusID == LOANConstant.LoanStatus.SUBMIT)
                        &&(lInputUsetID==lCheckUserID)))
                {
                    strSQL = " update LOAN_LoanFORM "
                           + " set NINPUTUSERID = ? " 
                           + " ,nNextCheckUserID=? "
                           + " where id = ? ";
                }
                //其它状态，只修改录入人
                else
                {
                    strSQL = " update LOAN_LoanFORM "
                           + " set NINPUTUSERID = ? " 
                           + " where id = ? ";
                }
				log4j.info("LoanSQL[" + i + "]= " + strSQL);
				ps = con.prepareStatement(strSQL);
                nIndex=1;
				ps.setLong(nIndex, lLoanManagerID);
                nIndex++;
                //*
                if((lStatusID == LOANConstant.LoanStatus.SAVE)
                    ||((lStatusID == LOANConstant.LoanStatus.SUBMIT)
                        &&(lInputUsetID==lCheckUserID)))
                {
                    ps.setLong(nIndex, lLoanManagerID);
                    nIndex++;
                 }//*/
				ps.setLong(nIndex, lLoanID[i]);
				lResult = ps.executeUpdate();
				//关闭资源
                if(ps != null)
                {
                    ps.close();
                    ps=null;
                }
				i++;
			}
		}
		catch (Exception e)
		{
			//lResult = -1;
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * findClient 查找现有客户
	 * 根据客户编号查找现有客户，返回客户详细资料
	 * 操作Client数据表
	 * 查询记录
	 * haoning
	 * @param lClientID String  客户编号的ID
	 * @return ClientInfo  详细的客户信息
	 * @throws RemoteException`
	 */
	public ClientInfo findClientByID(long lClientID) 
    throws IException,RemoteException
	{ 
                ClientInfo o = null;
		LoanCommonSettingDao loanCommonSettingDao = new LoanCommonSettingDao();
		
		try
                {
                    o = loanCommonSettingDao.findClientByID(lClientID);
              }
                catch(Exception e)
                {		throw new RemoteException(e.getMessage());
                }
                return o;
	}
	/**
	 * 新增（修改）客户详细资料
	 * saveClientInfo  新增（修改）客户的详细资料
	 * 操作Client数据表
	 * 更新相应字段
	 * lID=0,新增  lID>0,修改
	 * 约定：long型参数=-1，string型参数=“”,为未使用项，不参与新增或修改
	 *
	 * @param clientinfo 客户信息
	 * 相应字段：（包含在clientinfo类中）
	 * @param lID 标识
	 * @param strClientName   公司名称
	 * @param strClientNo,    客户编号
	 * @param strLicence,     营业执照
	 * @param lOfficeID,      办事处
	 * @param strAccount,     财务公司账号
	 * @param strBank,        开户银行
	 * @param strAccount      开户银行账号
	 * @param strBank1,       开户银行1
	 * @param strAccount1,    账号1
	 * @param strBank2,       开户银行2
	 * @param strAccount2,    账号2
	 * @param strBank3,       开户银行3
	 * @param strAccount3,    账号3
	 * @param strProvince,    省
	 * @param strCity,        市
	 * @param strAddress1,    地址1
	 * @param strAddress2     地址2
	 * @param strZipCode,     邮编
	 * @param strDeputy,      法人代表
	 * @param strTel,         电话
	 * @param strFax,         传真
	 * @param strMailAddr,    电邮
	 * @param strContact,     联系人
	 * @param strEconomic,    经济性质
	 * @param lGovernmentID,  主管部门表示
	 * @param isShareHolder,  是否股份
	 * @param lClientTypeID,  客户分类
	 * @param lCreditLevel,   信用等级
	 * @param lVentureLevel   风险评级
	 * @param strCapital      注册资本
	 *
	 * @return long 成功返回ID信息，失败返回0
	 * @throws RemoteException
	 */
	public long saveClientInfo(ClientInfo clientinfo) 
    throws IException,RemoteException
	{
                long lReturn = -1;
		LoanCommonSettingDao loanCommonSettingDao = new LoanCommonSettingDao();
                try 
                {
                    lReturn = loanCommonSettingDao.saveClientInfo(clientinfo);
                }
                catch(Exception e)
                {
                    		throw new RemoteException(e.getMessage());
                }
		return lReturn;
	}
	/**
	 * saveAtTermAwake  信贷部分到期业务提醒
	 * 进行到期业务提醒设置，提前XX天提醒客户,提醒XX天。
	 * 操作loan_attermawakesetting 数据表
	 * 更新相应字段
	 * 与办事处相关，与币种无关
	 *
	 * @param lTypeID 提醒类型
	 * @param lAheadDays 提前天数
	 * @param lAwakeDays 提醒天数
	 * @param lOfficeID 办事处ID
	 * @return long 成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public long saveAtTermAwake(long lTypeID[], long lAheadDays[], long lAwakeDays[], long lOfficeID) throws java.rmi.RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		long lResult = -1;
		long lMaxID = 0;
		try
		{
			con = Database.getConnection();
			for (int i = 0; i < lTypeID.length; i++)
			{
				strSQL = " select count(*) from Loan_AtTermAwake where nOfficeID = ? and nAwakeTypeID = ? ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lOfficeID);
				ps.setLong(2, lTypeID[i]);
				rs = ps.executeQuery();
				if (rs.next())
				{
					strSQL = " update Loan_AtTermAwake set nAwakeDays = ?, nAheadDays = ? where nOfficeID = ? and nAwakeTypeID = ? ";
					ps = con.prepareStatement(strSQL);
					//System.out.println(strSQL);
					ps.setLong(1, lAwakeDays[i]);
					ps.setLong(2, lAheadDays[i]);
					ps.setLong(3, lOfficeID);
					ps.setLong(4, lTypeID[i]);
					lResult = ps.executeUpdate();
					//关闭资源
					ps.close();
					ps = null;
				}
				else
				{
					strSQL = "insert into Loan_AtTermAwake (nOfficeID,nAheadDays,nAwakeDays,nAwakeTypeID) values (?,?,?,?)";
					ps = con.prepareStatement(strSQL);
					//System.out.println(strSQL);
					ps.setLong(1, lOfficeID);
					ps.setLong(2, lAheadDays[i]);
					ps.setLong(3, lAwakeDays[i]);
					ps.setLong(4, lTypeID[i]);
					lResult = ps.executeUpdate();
					//关闭资源
					ps.close();
					ps = null;
				}
				if (rs != null)
				{
					rs.close();
				}
				if (ps != null)
				{
					ps.close();
				}
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
			System.out.println("catch a error" + e.toString());
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return lResult;
	}
	/**
	 * findAtTermAwake  到期业务提醒查找
	 * 根据类型（信贷/外汇）进行到期业务提醒查找
	 * 操作AtTermAwake数据表
	 *
	 * @param lTypeID 提醒类型
	 * @param lOfficeID 办事处ID
	 * @return long 成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public AtTermAwakeInfo findAtTermAwake(long lTypeID, long lOfficeID) throws IException,RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSQL = null;
		AtTermAwakeInfo info = new AtTermAwakeInfo();
		try
		{
			con = Database.getConnection();
			strSQL = " select * from loan_AttermAwakeSetting where nOfficeID = ? and nAwakeTypeID = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lTypeID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info.setAwakeTypeID(rs.getLong("nAwakeTypeID"));
				info.setAheadDays(rs.getLong("nAheadDays"));
				info.setAwakeDays(rs.getLong("nAwakeDays"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			System.out.println("catch a error" + e.toString());
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException(ex.getMessage());
			}
		}
		return info;
	}
	/**
	 * addClientType  新增普通贷款客户类型
	 * 新增客户类型
	 * 操作XXXXX
	 * 新增记录
	 *
	 * @param lInputUserID 录入人
	 * @param tsInputDate 录入日期
	 * @param strClientTypeCode 客户类型编号
	 * @param strClientTypeName 客户类型名称
	 * @param lID 标识 新增 0/修改 ！=0
	 * @return long 成功返回ID标识，失败返回0
	 * @throws RemoteException
	 */
	public long addClientType(long lInputUserID, Timestamp tsInputDate, String sClientTypeCode, String sClientTypeName, long lID,long lOfficeID)
		throws IException,RemoteException
	{
		//操作步骤
		//  1、取得批量号
		//  2、在ClientType增加记录
		Connection conn = null;
		ResultSet rsBatch = null;
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		String strSQL = null;
		String strSQLBatch = null;
		long lMaxBatchID = -1; //批量号ID
		long lResult = 1; //查询结果
		try
		{
			conn = Database.getConnection();
			if (lID == 0) //新增
			{
				//验证客户分类编号的唯一
				strSQL = " select sCode from LOAN_ClientType ";
				strSQL += " where sCode=? and nStatusID=? and nOfficeID = ?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, sClientTypeCode);
				ps.setLong(2, Constant.RecordStatus.VALID);
				ps.setLong(3,lOfficeID);
				rsBatch = ps.executeQuery();
				if (rsBatch != null && rsBatch.next())
				{
					lResult = -1; //表示客户分类编号不唯一
				}
				if (rsBatch != null)
				{
					rsBatch.close();
					rsBatch = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (lResult > 0)
				{
					//验证客户分类名称的唯一
					strSQL = " select sCode from LOAN_ClientType ";
					strSQL += " where sName=? and nStatusID=? and nOfficeID= ? ";
					ps = conn.prepareStatement(strSQL);
					ps.setString(1, sClientTypeName);
					ps.setLong(2, Constant.RecordStatus.VALID);
					ps.setLong(3,lOfficeID);
					rsBatch = ps.executeQuery();
					if (rsBatch != null && rsBatch.next())
					{
						lResult = -1; //表示客户分类名称不唯一
					}
					if (rsBatch != null)
					{
						rsBatch.close();
						rsBatch = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
				}
				if (lResult > 0) //客户分类编号 名称唯一
				{
					//取得批量号
					strSQLBatch = "select nvl(max(id)+1,1) from LOAN_ClientType";
					psBatch = conn.prepareStatement(strSQLBatch);
					rsBatch = psBatch.executeQuery();
					if (rsBatch != null && rsBatch.next())
					{
						lMaxBatchID = rsBatch.getLong(1);
					}
					if (rsBatch != null)
					{
						rsBatch.close();
						rsBatch = null;
					}
					if (psBatch != null)
					{
						psBatch.close();
						psBatch = null;
					}
					//增加记录
					strSQL = "insert into LOAN_ClientType (ID, SCODE, SNAME, NINPUTUSERID, DTINPUT, NSTATUSID,nOfficeID) " + "values( ?, ?, ?, ?, SYSDATE, ?,?)";
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, lMaxBatchID);
					ps.setString(2, sClientTypeCode);
					ps.setString(3, sClientTypeName);
					ps.setLong(4, lInputUserID);
					//ps.setTimestamp(5, tsInputDate);
					ps.setLong(5, Constant.RecordStatus.VALID); //有效
					ps.setLong(6,lOfficeID);
					lResult = ps.executeUpdate();
					if (lResult <= 0)
					{
						lResult = 0; //失败
					}
					else
					{
						lResult = lMaxBatchID; //成功
					}
				}
			}
			else //if(lID!=0) 修改记录
				{
				//验证客户分类名称的唯一
				strSQL = " select sCode from LOAN_ClientType ";
				strSQL += " where sName=? and nStatusID=? and id != ? and nOfficeID = ?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, sClientTypeName);
				ps.setLong(2, Constant.RecordStatus.VALID);
				ps.setLong(3, lID);
				ps.setLong(4,lOfficeID);
				rsBatch = ps.executeQuery();
				if (rsBatch != null && rsBatch.next())
				{
					lResult = -1; //表示客户分类名称不唯一
				}
				if (rsBatch != null)
				{
					rsBatch.close();
					rsBatch = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (lResult > 0)
				{
					strSQL = "UPDATE LOAN_ClientType SET  SNAME=?, NUPDATEUSERID=?," + " DTUPDATE=SYSDATE " + " WHERE ID=?";
					ps = conn.prepareStatement(strSQL);
					ps.setString(1, sClientTypeName);
					ps.setLong(2, lInputUserID);
					ps.setLong(3, lID);
					lResult = ps.executeUpdate();
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (lResult <= 0)
					{
						lResult = 0; //失败
					}
					else
					{
						lResult = lID; //成功
					}
				}
			} //end if
            if (rsBatch != null)
            {
                rsBatch.close();
                rsBatch = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (psBatch != null)
            {
                psBatch.close();
                psBatch = null;
            }
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rsBatch != null)
				{
					rsBatch.close();
					rsBatch = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (psBatch != null)
				{
					psBatch.close();
					psBatch = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * findClientTypeByID  查询普通贷款客户分类
	 * 客户分类查询
	 * 操作XXXXX
	 * 查询相应记录
	 *
	 * @param lID 客户分类编号
	 * @return CostomerTypeInfo  客户分类信息
	 * @throws RemoteException
	 */
	public ClientTypeInfo findClientTypeByID(long lID) 
    throws IException,RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		ClientTypeInfo info = new ClientTypeInfo();
		try
		{
			conn = Database.getConnection();
			strSQL = " select * ";
			strSQL += " from LOAN_ClientType ";
			strSQL += " where ID=? ";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info.setClientTypeID(rs.getLong("ID"));
				info.setCode(rs.getString("SCODE"));
				info.setName(rs.getString("SNAME"));
			}
			else
			{
				info = null;
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return info;
	}
	/**
	  * findClientTypeByID  查询最大普通贷款客户分类标示
	  * 操作XXXXX
	  * 查询相应记录
	  *
	  * @return long  客户分类信息
	  * @throws RemoteException
	  */
	public long findMaxClientTypeID(long lOfficeID) throws IException,RemoteException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		long lResult = -1;
		try
		{
			con = Database.getConnection();
			// 如果有跳过的scode，通过下面方sql获取被跳过的scode；如果没有跳过的scode，数据库查询结果返回0。
			strSQL = " select nvl(min(id),0) maxcode ";
			strSQL += " from (select id from LOAN_ClientType  ";
			strSQL += " minus ";
			strSQL += " select to_number(scode) scode from LOAN_ClientType ";
			strSQL += " where nStatusID =? and nOfficeID= ?";
			strSQL += " ) ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2,lOfficeID);
			
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			// 没有跳过的scode,通过下面sql获取最大的scode。
			if (lResult == 0)
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				strSQL = " select nvl(max(to_number(scode)),0)+1 as nMaxClientType ";
				strSQL += " from LOAN_ClientType ";
				strSQL += " where nStatusID =? and nOfficeID = ?";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, Constant.RecordStatus.VALID);
				ps.setLong(2,lOfficeID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lResult = rs.getLong(1);
				}
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * findClientTypeByMultiOption  查询普通贷款客户分类
	 * 客户分类查询
	 * 操作XXXXX
	 * 查询相应记录
	 *
	 * @param strClientTypeCode 客户分类编号
	 * @param strClientTypeName 客户分类名称
	 * @return CostomerTypeInfo  客户分类信息
	 * @param long           lPageLineCount        每页页行数条件
	 * @param long           lPageNo               第几页条件
	 * @param long           lOrderParam           排序条件，根据此参数决定结果集排序条件
	 * @param long           lDesc                 升序或降序
	 * @param long           lOfficeID             办事处ID
	 * @throws RemoteException
	 */
	public Collection findClientTypeByMultiOption(
		String sClientTypeCode,
		String sClientTypeName,
		long lPageLineCount,
		long lPageNo,
		long lOrderParam,
		long lDesc,
		long lOfficeID)
		throws IException,RemoteException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		//分页变量
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		String strSQL = "";
		String strSQL_Select = "";
		String strSQL_Table = "";
		String strSQL_where = "";
		String strSQL_order = "";
		try
		{
			con = Database.getConnection();
			//计算记录总数
			strSQL_Select = "select count(*) ";
			strSQL_Table = " from LOAN_ClientType c,UserInfo u ";
			strSQL_where = " where c.nInputUserID=u.ID(+) and c.nOfficeID = ? and c.nStatusID = ? ";
			
			// 客户分类编号
			if (sClientTypeCode != null && sClientTypeCode.length() > 0)
			{
				strSQL_where += " and c.sCode like '%" + sClientTypeCode + "%' ";
			}
			//客户分类名称
			if (sClientTypeName != null && sClientTypeName.length() > 0)
			{
				strSQL_where += " and c.sName like '%" + sClientTypeName + "%' ";
			}
			strSQL = strSQL_Select + strSQL_Table + strSQL_where;
			//log4j.info("SQL=" + strSQL);
		
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lOfficeID);
			ps.setLong(2,Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			//计算总页数
			//log4j.info("总记录数=" + lRecordCount);
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			//返回需求的结果集
			//分页显示，显示下一页
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;
			if (lRowNumEnd > lRecordCount)
			{
				lRowNumEnd = lRecordCount;
			}
			//log4j.info("start=" + lRowNumStart);
			//log4j.info("end=" + lRowNumEnd);
			//-----------------------------------------------------
			strSQL_Select = " SELECT c.ID ID, c.SCODE SCODE " + " ,c.SNAME SNAME, u.SNAME InputUserName " + " ,c.DTINPUT ";
			switch ((int) lOrderParam)
			{
				case 1 : //按客户分类编号排序
					strSQL_order += " order by c.sCode";
					break;
				case 2 : //按客户分类名称排序
					strSQL_order += " order by c.sName";
					break;
				case 3 : //按录入人排序
					strSQL_order += " order by c.nInputUserID";
					break;
				case 4 : //按录入日期排序
					strSQL_order += " order by c.dtInput";
					break;
				default :
					strSQL_order += "";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL_order += " desc ";
			}
			strSQL = " select * from ";
			strSQL += " ( select all_record.*,rownum num from ( ";
			strSQL += strSQL_Select + strSQL_Table + strSQL_where + strSQL_order;
			strSQL += ") all_record )";
			strSQL += " WHERE num BETWEEN ? AND ?";
			//log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lOfficeID);
			ps.setLong(2,Constant.RecordStatus.VALID);
			ps.setLong(3, lRowNumStart); //给入起始行号
			ps.setLong(4, lRowNumEnd); //给入结束行号
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				//log4j.info("----------客户分类-----------");
				ClientTypeInfo clientType = new ClientTypeInfo();
				clientType.setClientTypeID(rs.getLong("ID"));
				clientType.setCode(rs.getString("SCODE")); //客户分类编号
				clientType.setName(rs.getString("SNAME")); //客户分类名称
				clientType.setInputUserName(rs.getString("InputUserName"));
				// 录入人姓名
				clientType.setDtInput(rs.getTimestamp("DTINPUT"));
				clientType.setPageCount(lPageCount); //记录总的页面数
				v.addElement(clientType);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
                log4j.error(e.toString());
                throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
	}
	/**
	 * deleteClientType  删除客户类型
	 * 删除客户类型
	 * 操作LOAN_ClientType
	 * 删除记录，（逻辑删除）
	 *
	 * @param lID  客户类型标示
	 * @param lUserID 删除人
	 * @param tsDate 删除日期
	 * @return long  成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public long deleteClientType(long lID, long lUserID, Timestamp tsDate) 
    throws IException,RemoteException
	{
		String strSQL = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			con = Database.getConnection();
			//先由ID查找是否存在该客户类型
			strSQL = " select sCode from LOAN_ClientType where ID=? ";
            strSQL += " and nStatusId = "+Constant.RecordStatus.VALID;
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lResult = 0; //表示客户分类编号存在
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
            
            // 如果客户类型已经被使用（新增客户时选择了该客户类型），则客户类型不允许被删除
            boolean isUsedByClient = false;
            isUsedByClient = CheckClientType(lID);
            if(isUsedByClient)
            {
                System.out.println("=====  客户类型已被使用，不允许被删除   =====");
                lResult = -100;
            }
            
			if (lResult == 0 && !isUsedByClient)
			{
				strSQL =
					"Update LOAN_ClientType SET nStatusID=?,NUPDATEUSERID=?,"
						+ "DTUPDATE=TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD') "
						+ " where ID=? ";
				ps = con.prepareStatement(strSQL);
                System.out.println("=====  strSQL = "+strSQL);
				ps.setLong(1, Constant.RecordStatus.INVALID);
				ps.setLong(2, lUserID);
				ps.setLong(3, lID);
				lResult = ps.executeUpdate();
			}
			if (lResult <= 0 && lResult != -100)
			{
				lResult = 0; //失败
			}
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}
		catch (Exception e)
		{
			lResult = -1; //出错
            log4j.error(e.toString());
            throw new IException("Gen_E001");
		}
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
		return lResult;
	}
	/**
	 * 保存到期业务提醒设置信息
	 * 操作数据库表Loan_AtTermAwakeSetting
	 * @param       ASInfo  到期业务提醒设置信息
	 * @return      long    成功，返回值=1；失败，返回值=-1
	 */
	public long saveAtTermAwakeSetting(long lTypeID[], long lAheadDays[], long lAwakeDays[], long lOfficeID) throws RemoteException
	{
		Connection con = null;
		AtTermAwakeSettingDao awakedao = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			//
			awakedao = new AtTermAwakeSettingDao(con);
			//
			lReturn = awakedao.saveAtTermAwakeSetting(lTypeID, lAheadDays, lAwakeDays, lOfficeID);
			if (lReturn > 0)
			{
				lReturn = 1;
			}
			else
			{
				lReturn = -1;
			}
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println("saveAtTermAwakeSetting() failed.  Exception is " + e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException("remote exception : " + ex.toString());
			}
		}
		return (lReturn);
	}
	
	/**
	 * 查询到期业务提醒设置信息
	 * 查询条件为办事处标示和到期业务提醒类型
	 * 操作数据库表Loan_AtTermAwakeSetting
	 * @param       lOfficeID            办事处标示
	 * @param       lAwakeTypeID         到期业务提醒类型
	 * @return      AtTermAwakeInfo      返回到期业务提醒设置信息
	 */
	public AtTermAwakeInfo findAtTermAwakeSetting(long lOfficeID, long lAwakeTypeID) throws IException,RemoteException
	{
		Connection con = null;
		AtTermAwakeSettingDao awakedao = null;
		long lReturn = -1;
		AtTermAwakeInfo ATAInfo = null;
		try
		{
			con = Database.getConnection();
			//
			awakedao = new AtTermAwakeSettingDao(con);
			//查询审批设置信息
			ATAInfo = awakedao.findAtTermAwakeSetting(lOfficeID, lAwakeTypeID);
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			System.out.println("findAtTermAwakeSetting() failed.  Exception is " + e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex)
			{
				throw new RemoteException("remote exception : " + ex.toString());
			}
		}
		return (ATAInfo);
	}
	/**
	 * addCurrency  增加外汇贷款币种
	 * 增加外币种类
	 * 操作XXXXX
	 * 更新XXXX
	 *
	 * @param strCurrencyCode 币种编号
	 * @param strCurrencyName 币种名称
	 * @param nInputUserID 录入人
	 * @param tsInputDate 录入日期
	 * @param lInputUserID 录入人
	 * @return long  成功返回1，失败返回0
	 * @throws RemoteException
	 */
	public long addCurrency(String strCurrencyCode, String strCurrencyName, long lInputUserID, Timestamp tsInputDate) throws IException,RemoteException
	{
		return -1;
	}
	/**
	 * findCurrencyByID  查询外汇贷款币种设置
	 * 查询外汇贷款币种设置
	 * 操作XXXXX
	 * 更新XXXX
	 *
	 * @param lID 币种标示
	 * @throws RemoteException
	 */
	public CurrencyInfo findCurrencyByID(long lID) throws IException,RemoteException
	{
		return null;
	}
	/**
	 * findCurrencyByMultiOption  查询外汇贷款币种设置
	 * 查询外汇贷款币种设置
	 * 操作XXXXX
	 * 更新XXXX
	 *
	 * @param strCurrencyCode 币种编号
	 * @param lCurrencyID 币种标示
	 * @return Collection  外汇品种信息集合
	 * @param long           lPageLineCount        每页页行数条件
	 * @param long           lPageNo               第几页条件
	 * @param long           lOrderParam           排序条件，根据此参数决定结果集排序条件
	 * @param long           lDesc                 升序或降序
	 * @throws RemoteException
	 */
	public Collection findCurrencyByMultiOption(String strCurrencyCode, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
		throws IException,RemoteException
	{
		return null;
	}
	public static void main(String args[])
	{
	}
	private String getNewClientCode(long lOfficeID) throws RemoteException
	{
		String strCode = "";
		long lNewClientID = -1;
		try
		{
			Sett_ClientDAO dao=new Sett_ClientDAO();
			strCode = dao.getNewClientCode(lOfficeID);
			Log.print(" strCode 1 is " + strCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return strCode;
	}
	/*
	 *
	 *  @return 最大值   如果表为空返回 -1；
	 *  @Exception
	 */
	private long getMaxClientID(long lOfficeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String strMaxCode = "";
		String strCode = "";
		long lMaxID = -1;
		try
		{
			//
			//客户编号格式 : 办事处编号-增值编号 (ex: 01-0001)
			//
			String strOfficeID = DataFormat.formatInt(lOfficeID, 2, true) + "-";
			conn = Database.getConnection();
			strSQL = "select max(scode) from client where scode like '" + strOfficeID + "%' and ASCII(substr(SCODE,length(scode))) <= 64";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			long lTempMax = -1;
			if (rs.next())
			{
				strCode = rs.getString(1);
				Log.print("strCode:" + strCode);
				//lMaxID = getTokenLong(strCode, "-", 2);
			}
			strMaxCode = strCode.substring(0, 1) + strCode.substring(3);
			Log.print("strMaxCode:" + strMaxCode);
			lMaxID = Long.parseLong(strMaxCode);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			//Common.log(e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		//
		return lMaxID;
	}
    
    /**
     *@description: 检查客户类型是否已经被使用
     *@param clientId
     *@return 已经被使用，返回true;没被使用，返回flase
     */
    private boolean CheckClientType(long clientTypeId) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbSQL = new StringBuffer();

        boolean flag = false;
        
        sbSQL.append(" select a.* from  client a,loan_clienttype b   \n");
        sbSQL.append("     where a.nloanclienttypeid = b.id   \n");
        sbSQL.append("         and a.nstatusid = "+Constant.RecordStatus.VALID + "  \n");
        sbSQL.append("         and b.nstatusid = "+Constant.RecordStatus.VALID + "  \n");
        sbSQL.append("         and b.id = "+clientTypeId + "  \n");
        
        Log.print(" 检查客户类型(信贷)是否已经被使用 : \n"+sbSQL.toString());
        
        try 
        {
            conn = Database.getConnection();
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            if( rs != null && rs.next() )
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            //Common.log(e.getMessage());
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        
        return flag;
    }
}

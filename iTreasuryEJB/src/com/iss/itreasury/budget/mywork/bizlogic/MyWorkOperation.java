/* Generated by Together */

package com.iss.itreasury.budget.mywork.bizlogic;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.budget.bizdelegation.ConstituteDelegation;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.mywork.dao.MyWorkDAO;
import com.iss.itreasury.budget.mywork.dataentity.MyWorkInfo;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class MyWorkOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.BUDGET, this);
	protected MyWorkDAO dao = null;

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public MyWorkOperation() {
		dao = new MyWorkDAO();
	}

	/**
	 * 通过状态取得预算数量(待审核)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getNoCheckCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		long budgetCount = 0;
		try {
			ConstituteDelegation cg = new ConstituteDelegation();
			Collection col = cg.findUnCheckBudget(info.getClientID(),info.getInputUserID(),info.getOfficeID(),info.getCurrencyID());
			if(col!=null)
			{
				budgetCount= col.size();
			}
		} catch (BudgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return budgetCount;
	}

	/**
	 * 通过状态取得预算数量(撰写)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSaveCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getSaveCountByStatusID(info);
	}

	/**
	 * 通过状态取得预算数量(已提交)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSubmitCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getSubmitCountByStatusID(info);
	}

	

	/**
	 * 通过状态取得预算数量(已拒绝)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getRefuseCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getRefuseCountByStatusID(info);
	}
}

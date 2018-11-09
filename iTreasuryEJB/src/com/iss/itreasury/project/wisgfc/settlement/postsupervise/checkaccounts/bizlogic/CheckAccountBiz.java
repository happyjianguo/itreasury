package com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.bizlogic;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dao.CheckAccountDao;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckInAccountInfo;

import com.iss.system.dao.PageLoader;


public class CheckAccountBiz
{
	CheckAccountDao dao = null;
    public CheckAccountBiz(){
        dao = new CheckAccountDao();
    }
	
	/**
	 * 分页显示未核对记录
	 * @param CheckAccountInfo conditionInfo
	 * @return PageLoader
	 */
	public PageLoader getCheckAccountInfo(CheckAccountInfo conditionInfo){
		PageLoader pageLoader=dao.getCheckAccountInfo(conditionInfo);		   
		return pageLoader;
	}
	/**
	 * 分页显示已核对记录
	 * @param CheckAccountInfo conditionInfo
	 * @return PageLoader
	 */
	public PageLoader getDoCheckAccountInfo(CheckAccountInfo conditionInfo){
		PageLoader pageLoader=dao.getDoCheckAccountInfo(conditionInfo);		   
		return pageLoader;
	}
	
	
	/**
	 * 核对数据
	 * @param CheckAccountInfo conditionInfo
	 * @return int
	 * @throws Exception
	 */
	public int addAccountCheck(CheckAccountInfo conditionInfo,String startDate) throws Exception{
		int flag =dao.addAccountCheck(conditionInfo,startDate);		   
		return flag;
	}
	
	/**
	 * 分页显示未核对记录
	 * @param CheckInAccountInfo conditionInfo
	 * @return PageLoader
	 */
	public PageLoader getCheckInAccountInfo(CheckInAccountInfo conditionInfo){
		PageLoader pageLoader=dao.getCheckInAccountInfo(conditionInfo);		   
		return pageLoader;
	}
	
	/**
	 * 分页显示核对记录
	 * @param CheckInAccountInfo conditionInfo
	 * @return PageLoader
	 */
	public PageLoader getDoCheckInAccountInfo(CheckInAccountInfo conditionInfo){
		PageLoader pageLoader=dao.getDoCheckInAccountInfo(conditionInfo);		   
		return pageLoader;
	}
	
	/**
	 * 核对数据
	 * @param CheckAccountInfo conditionInfo
	 * @return int
	 * @throws Exception
	 */
	public int addInAccountCheck(CheckInAccountInfo conditionInfo,String startDate) throws Exception{
		int flag =dao.addInAccountCheck(conditionInfo,startDate);		   
		return flag;
	}
	
	
	
}
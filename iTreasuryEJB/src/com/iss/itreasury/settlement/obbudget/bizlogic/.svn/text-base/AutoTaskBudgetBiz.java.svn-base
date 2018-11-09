package com.iss.itreasury.settlement.obbudget.bizlogic;

import com.iss.itreasury.settlement.obbudget.dao.AutoTaskBudgetDao;
import com.iss.itreasury.settlement.obbudget.dataentity.AutoTaskBudgetInfo;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class AutoTaskBudgetBiz {
	//翻页查询
	public PageLoader queryTransactionInfo(AutoTaskBudgetInfo autoTaskBudgetInfo) throws Exception{
		PageLoader pageLoader = null;
		AutoTaskBudgetDao dao = new AutoTaskBudgetDao();
		pageLoader = dao.queryTransactionInfo(autoTaskBudgetInfo);
		return pageLoader;
	}
	
	//取消设置
	public long deleteAutoTask(long id) throws IException{
		AutoTaskBudgetDao dao = new AutoTaskBudgetDao();
		try{
			id = dao.deleteAutoTask(id);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return id;
	}
	
	//批量取消设置
	public long deleteAutoTask(String[] id) throws IException{
		AutoTaskBudgetDao dao = new AutoTaskBudgetDao();
		long returnId = -1;
		try{
			returnId = dao.deleteAutoTask(id);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return returnId;
	}
	
	//增加自动任务
	public long addAutoTask(AutoTaskBudgetInfo autoTaskBudgetInfo) throws IException{
		long id = -1;
		AutoTaskBudgetDao dao = new AutoTaskBudgetDao();
		try{
			id = dao.add(autoTaskBudgetInfo);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
        return id;
	}
}

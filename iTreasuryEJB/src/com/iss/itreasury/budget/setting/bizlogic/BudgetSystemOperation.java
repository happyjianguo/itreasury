/* Generated by Together */

package com.iss.itreasury.budget.setting.bizlogic;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.setting.dataentity.BudgetSystemInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.util.Env;

import com.iss.itreasury.budget.setting.dao.Budget_SystemDAO;
import com.iss.itreasury.budget.templet.dao.Budget_TempletDAO;
import com.iss.itreasury.budget.templet.dataentity.BudgetTempletInfo;
import com.iss.itreasury.util.Constant;

public class BudgetSystemOperation {
	
	/**
	 * 预算体系设置。新建或修改，状态正常的情况下判断周期代码不能重复
	 * @param info
	 * @return
	 * @throws BudgetException
	 */
    public long save(BudgetSystemInfo info)   throws BudgetException{   	
    	long returnLong = -1;
    	double suppleScale=0.0;
    	double WarnScale=0.0;   	
		try{
			Budget_SystemDAO dao=new Budget_SystemDAO();
			
			//先根据预算体系代码和状态判断此体系代码是否已经存在
			BudgetSystemInfo tmpInfo = new BudgetSystemInfo();
			tmpInfo.setBudgetSystemNo(info.getBudgetSystemNo());
			tmpInfo.setStatusID(Constant.RecordStatus.VALID);
			Collection c = dao.findByCondition(tmpInfo);
			if (c!=null && c.size()>0){
				//如果是新增操作,直接抛出异常
				if (info.getId()==-1){ 
					return 0;
				}else{
					//如果查出的信息的ID不是要修改的此条信息
					for(Iterator iter=c.iterator();iter.hasNext();){
						BudgetSystemInfo resu=(BudgetSystemInfo)iter.next();
						if(resu.getId()!=info.getId()){
							return 0;							
						}						
					}					
				}
			}
			//判断结束				
			if (info.getId()!=-1){//更新操作						
					suppleScale=(new BigDecimal(info.getSuppleScale()).divide(new BigDecimal(100),5,4)).doubleValue();
					WarnScale=(new BigDecimal(info.getWarnScale()).divide(new BigDecimal(100),5,4)).doubleValue();
					info.setUpdateDate(Env.getSystemDateTime());//最新修改时间入库 
					info.setWarnScale(WarnScale);
					info.setSuppleScale(suppleScale);
					dao.update(info);
					returnLong = info.getId();
			}else{//新增
				suppleScale=(new BigDecimal(info.getSuppleScale()).divide(new BigDecimal(100),5,4)).doubleValue();
				WarnScale=(new BigDecimal(info.getWarnScale()).divide(new BigDecimal(100),5,4)).doubleValue();
				info.setWarnScale(WarnScale);
				info.setSuppleScale(suppleScale);
				info.setUpdateDate(Env.getSystemDateTime());//最新修改时间入库 
				info.setInputDate(Env.getSystemDateTime());//最新新建时间				
				returnLong = dao.add(info);
			}
		}catch(Exception e){
			e.printStackTrace();			
		}		
		return returnLong;
    }
    
    /**
     * 根据ID删除一条记录(不是真正的删除，只改变它的状态)
     * 如果此体系ID已经存在于项目模板中不允许删除
     * @param id
     * @return
     * @throws BudgetException
     */
    public long delete(long id)   throws BudgetException{
    	Budget_SystemDAO dao = new Budget_SystemDAO();	    	
    	long retlong=-1;
		try{
			Budget_TempletDAO tempdao=new Budget_TempletDAO();
			BudgetTempletInfo tempinfo=new BudgetTempletInfo();
			tempinfo.setBudgetSystemID(id);
			tempinfo.setStatusID(1);
			Collection temCol=tempdao.findByCondition(tempinfo);
			if(temCol.isEmpty()){
				dao.delete(id);
				retlong=id;				
			}else{
				retlong=-2;				
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
		return retlong;
    }
    
    /**
     * 查询预算体系表的所有记录 ，返回一个集合
     * @return
     * @throws BudgetException
     */
    public Collection findAll()   throws BudgetException{
    	Budget_SystemDAO dao = new Budget_SystemDAO();	
    	BudgetSystemInfo tmpInfo = new BudgetSystemInfo ( ) ;	
    	Collection coll=null;    		
		tmpInfo.setStatusID(Constant.RecordStatus.VALID);
		try{
			coll = dao.findByCondition(tmpInfo);
    	}catch(Exception ec){ec.printStackTrace();}
        return coll;
    }    
    /**
     * 根据ID查询预算体系表的一条记录，返回一个对象
     * @param id
     * @return
     * @throws BudgetException
     */
      public BudgetSystemInfo findByID(long id)   throws BudgetException{
    	  Budget_SystemDAO dao = new Budget_SystemDAO();	
    	  BudgetSystemInfo info = new BudgetSystemInfo();
    	  try{
     		 info=(BudgetSystemInfo)dao.findByID(id,(new BudgetSystemInfo()).getClass());
  			}catch (Exception e){
  				e.printStackTrace();			
  			}
  		return info;         
    }
}

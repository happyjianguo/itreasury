/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_PeriodSetDao;
import com.iss.itreasury.settlement.setting.dataentity.PeriodSetInfo;
import com.iss.itreasury.util.IException;

/**
 * @author yangliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPeriodSetBiz
{
	Sett_PeriodSetDao dao = null;

    /**
     * Constructor for SettPeriodSetBiz
     */
    public SettPeriodSetBiz()
    {
        dao = new Sett_PeriodSetDao();
    }
    
	/**
	 * 新健或修改记录
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long save(PeriodSetInfo info)throws  IException{
		long retlong=-1;
		try{
			if(info.getId()>0){
				retlong=dao.add(info);				
			}else{
				dao.update(info);
				retlong=info.getId();				
			}
		}catch(Exception ex){throw new IException("Gen_E001");}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * 根据ID查询记录详细信息
	 * @param id
	 * @return
	 * @throws IException
	 */
	public PeriodSetInfo findById(long id) throws IException
	{
		PeriodSetInfo info=null;
		try{
			info = (PeriodSetInfo)dao.findByID(id,PeriodSetInfo.class);
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		
		return info;
	}
	
	/**
	 * 根据查询条件查询记录
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection queryByCondition(PeriodSetInfo info)throws IException{	
		Collection coll=null;
		try{
			
			coll = dao.findByCondition(info);
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return coll;
	}
	
	/**
	 * 根据查询条件查询记录，不使用公共方法
	 * @param info
	 * @return
	 * @throws IException
	 */
	public PeriodSetInfo findByCondition(PeriodSetInfo info)throws IException{
		List list = null;
		PeriodSetInfo periodSetInfo = new PeriodSetInfo();
		try{
			list = dao.findByCondition(info);
			if(list != null && list.size() > 0){
				periodSetInfo = (PeriodSetInfo)list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return periodSetInfo;
	}
	
	/**
	 * 根据记录ID删除记录
	 * @param id
	 * @return
	 * @throws IException
	 */
	public long deleteById(long id)throws IException{	
		long retlong =-1;
		try{
			PeriodSetInfo info=new PeriodSetInfo();
			info.setId(id);
			info.setStatusId(0);//正式程序用变量表示
			dao.update(info);
			retlong = id;
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * 根据记录ID删除记录
	 * @param id
	 * @return
	 * @throws IException
	 */
	public long update(PeriodSetInfo info)throws IException{	
		long retlong =-1;
		try{
			dao.update(info);		
			retlong = 1;
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * 新增记录
	 */
	public long addPeropdSet(PeriodSetInfo info)throws IException{
		long id = -1;
		try{
			id = dao.addPeropdSet(info);
		}catch(IException ie){
			throw new IException("Gen_E001");
		}
		return id;
	}
	
	/**
	public static void main(String[] args) throws Exception {
		PeriodSetInfo info = new PeriodSetInfo();
		info.setId(1);//id
		
		info.setInputDate(Env.getSystemDateTime());//输入日期
		
		info.setInputUserId(1);//输入人
		
		info.setModifyDate(Env.getSystemDateTime() );//修改日期
		
		info.setModifyUserId(1);//修改人
		
		info.setOfficeId(1);//办事处;
		
		info.setOldPeriod(30);//原周期
		
		info.setOldStartDate(Env.getSystemDateTime());//原开始日期
		
		info.setPeriod(30);//新周期
		
		info.setStartDate(Env.getSystemDateTime());//新开始日期 
					
		info.setPeriodName("11");//计划名
		
		info.setStatusId(1);//状态
		
		info.setCurrencyId(1);//计划货币号
		
		SettPeriodSetBiz biz = new SettPeriodSetBiz();
		
		long lReturn = biz.update(info);
		
		System.out.println("lReturn========"+lReturn);
	}
	**/

}

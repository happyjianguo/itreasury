package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_ManagerSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.CommissionSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.MaganerSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

/**
 * 经理查询设置
 * 实现经理查询模块的设置功能
 * @author gqfang
 */
public class ManagerSettingBiz
{
	Sett_ManagerSettingDAO dao = null;
	
	/**
	 * Constrcutor of class
	 */
	public ManagerSettingBiz()
	{
		dao = new Sett_ManagerSettingDAO();
	}
	public static void main( String[] str ) throws SettlementException
	{
		System.out.println("TTTTTTTTTTTTTT");
		ManagerSettingBiz biz = new ManagerSettingBiz();
		MaganerSettingInfo info = new MaganerSettingInfo();
		info = biz.findById(4);
		System.out.println("info is : "+info);
	}
	/**
	 * 查询符合条件的设置项
	 * 
	 * @return Collection
	 * info.getStatusId() = -1 ：查询全部设置
	 * info.getStatusId() =  0 ：查询无效设置
	 * info.getStatusId() =  1 ：查询有效设置
	 * @throws SettlementException
	 */
	public Collection findAll( MaganerSettingInfo info ) throws SettlementException
	{
		Collection coll = null;
		try
		{
			coll = dao.findAllSettings(info);
		}
		catch (SettlementException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coll;
	}
	
	
	/**
	 * 单笔查询
	 * @param id
	 * @return MaganerSettingInfo
	 * @throws SettlementException
	 */
	public MaganerSettingInfo findById( long id ) throws SettlementException
	{
		MaganerSettingInfo info = null;
		
		try 
		{
			info = new MaganerSettingInfo();
			
			info = (MaganerSettingInfo) dao.findByID(id , (new MaganerSettingInfo()).getClass());
			
			System.out.println("TTTTTTTTTTTTT3232TTTTTTTTTTT             info = "+info);
		}
		catch (ITreasuryDAOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  info;
	}
	
	
	/**
	 * 保存所有已勾选、未勾选的设置
	 * 1.update all records' status with value '0';
	 * 2.update the checked records's status whith value '1'.
	 * @return boolean
	 */
	public boolean saveAll( Collection coll )
	{
		boolean flag = false;
		System.out.println("aa	========================= coll =  "+coll);
		Iterator iterator = null;
		MaganerSettingInfo maganerSettingInfo = null;
		System.out.println("BB	========================= lContentIdList[i] = ");
		try 
		{System.out.println("CC	========================= lContentIdList[i] = ");
		
		    //step 1
		    dao.unCheckedAll();
		    
		    //step 2
			if( coll != null && coll.size() > 0 )
			{
				iterator = coll.iterator();
				System.out.println("DD	========================= lContentIdList[i] = ");
				while( iterator.hasNext() )
				{
					maganerSettingInfo = (MaganerSettingInfo) iterator.next();
					System.out.println("EE	========================= lContentIdList[i] = ");
					dao.update( maganerSettingInfo );
					
					flag = true;
				}
				
				
			}
			else
			{
				flag = true;
			}
		}
		catch (ITreasuryDAOException e) 
		{
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	
	/**
	 * 新增
	 * @param info
	 * @throws SettlementException
	 */
	public long add( MaganerSettingInfo info )throws SettlementException
	{
		long lReturn = -1;
		try 
		{
			//判断是否已经存在相同的设置
			if( info != null )
			{
				if( !dao.isExitRecord(info) )
				{
					dao.add(info);
					lReturn = info.getId();
				}
			}
		}
		catch (ITreasuryDAOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lReturn;
	}
	
	/**
	 * 修改
	 * @param info
	 * @throws SettlementException
	 */
	public void update ( MaganerSettingInfo info )throws SettlementException
	{

		try 
		{
			dao.update(info);
		}
		catch (ITreasuryDAOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 物理删除
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void delete ( MaganerSettingInfo info )throws ITreasuryDAOException
	{        
        try
        {
            if( info != null )
            {
            	dao.deleteItem(info.getId());
            }
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();throw new ITreasuryDAOException("删除操作异常", e);
        }
    
	}
}
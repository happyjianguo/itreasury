package com.iss.itreasury.treasuryplan.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;


/**
 *
 * @name: Trea_TPTemplateSetting
 * @description: 资金计划模板设置类
 * @author: jason
 * @create: 2005-11-15
 *
 */
public class Trea_TPTemplateSetting
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);
    
    /**
     *@Description:资金计划模板查找
     *     step 1 :find data from Table Trea_TPTemplate;
     *     step 2 :return a Collection.
     *@param Trea_TPTemplateInfo
     *@return Collection
     */
    public Collection queryTemplateByCondition( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        try 
        {
            Vector dataList   = new Vector();
            Vector tempList   = new Vector();
            Vector returnList = new Vector();
            
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            
            //查询资金计划模板数据
            dataList = dao.queryTemplateData( paramInfo );
             
            return dataList;
        }
        catch ( Exception e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     *@Description: 得到模板项目的最大级别
     *@param Trea_TPTemplateInfo
     *@return maxLevel
     *@throws Exception
     */
    public long getMaxLevel( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        long maxLevel = -1;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            maxLevel = dao.getMaxLevel(paramInfo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        
        return maxLevel;
    }
    
    /**
     *@Description:根据Id,查找一条行项目
     *@param lineId
     *@return Trea_TPTemplateInfo
     *@throws Exception
     */
    public Trea_TPTemplateInfo findTemplateById( long lineId ) throws Exception
    {
        Trea_TPTemplateInfo trea_TPTemplateInfo = null;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO(); 
            trea_TPTemplateInfo = (Trea_TPTemplateInfo) dao.findByID( lineId , (new Trea_TPTemplateInfo()).getClass() );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return trea_TPTemplateInfo;
    }
    
    
    /**
     *@Description:行项目取数逻辑设置 - 新增、修改
     *@param Trea_TPTemplateInfo
     *@return templateId
     *@throws IException
     */
    public long saveTemplateItem(Trea_TPTemplateItemInfo itmeInfo) throws Exception
    {
        long itemId = -1;
        
        try
        {
            log4j.info("行项目取数逻辑设置..............");
            //定义DAO
            com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO itemDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
            
            
            if( itmeInfo != null )
            {
                if( itmeInfo.getId() > 0 )
                {
                    //修改保存
                    log4j.info("行项目取数逻辑设置  修改保存..............");
                    itemDao.updateItem(itmeInfo);
                }
                else
                {
                    //新增保存
                    log4j.info("行项目取数逻辑设置 新增保存..............");
                    itemDao.add(itmeInfo);
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("保存行项目取数逻辑异常！");
        }
            
        return itemId;
     }
    
    
    /**
     *@Description: 删除资金计划模板行项目的一条取数逻辑
     *@param itemId
     *@return itemId
     *@throws Exception
     */
    public  void deleteTemplateItem( long itemId ) throws ITreasuryException,Exception
    {

        try 
        {
            //实例化对象 - 子项目
            com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO  itemDao   = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
            Trea_TPTemplateItemInfo itemInfo  = new Trea_TPTemplateItemInfo();
            
            //删除行项目
            log4j.info("删除行项目的一条取数逻辑..............");
            if( itemId > 0 )
            {
                itemDao.deleteOneItem(itemId);
            }
        }
        catch ( Exception e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
    }
    
    
    /**
     *@Description:检查资金计划模板的行项目 - 是否允许保存
     *@param Trea_TPTemplateInfo
     *@return boolean
     *               true : 可以进行保存操作；
     *               false: 待保存的信息和数据库的信息冲突，不允许进行保存操作。
     *@throws Exception
     */
    private boolean isAllowSave(Trea_TPTemplateInfo info) throws Exception
    {
        boolean flag    = true;
        Collection coll = null;
        
        try
        {
           //判断是行项目编号是否重复
            Trea_TPTemplateDAO  dao  = new Trea_TPTemplateDAO();
            Trea_TPTemplateInfo para = new Trea_TPTemplateInfo();
            
            para.setLineNo(info.getLineNo());
            para.setStatusId(Constant.RecordStatus.VALID);
            para.setOfficeId(info.getOfficeId());
            coll = dao.findByCondition(para);
            
            if( coll != null && coll.size() > 0)
            {
                log4j.info("重复的行项目 =================================== :  "+para.getLineNo());
                flag = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(" 检查资金计划模板的行项目 - 是否允许保存 异常！");
        }
        
        return flag;
    }
    
    /**
     *@Description:检查资金计划模板的行项目 - 是否允许删除
     *             i.如果还有下级项目，则上级项目不能删除
     *            ii.如果一个项目权限已经分配给某部门使用，则上级项目不能删除
     *@param Trea_TPTemplateInfo
     *@return boolean
     *@throws Exception
     */
    private boolean isAllowDelete(Trea_TPTemplateInfo info) throws Exception
    {
        boolean flag = true;
        
        try
        {
            Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO();
            
            //判断是否还有下级项目
            if( dao.isHaveSubItem(info) )
            {
                log4j.info("有下级行项目..............");
                flag = false;
                return false;
            }
            
            //判断项目是否分配给某部门使用
            if( info.getAuthorizedDepartment() != null || info.getAuthorizedUser() != null  )
            {
                log4j.info("项目已经分配给某部门使用..............");
                flag = false;
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(" 检查资金计划模板的行项目 - 是否允许删除 异常！");
        }
        
        return flag;
    }
    
    /**
     *@Description: 删除资金计划模板的一条行项目
     *              删除项目的同时，该项目的取数逻辑也同时删除
     *@param lineId
     *@return lineId
     *@throws Exception
     */
    public  void deleteTemplateLine( long lineId ) throws ITreasuryException,Exception
    {
        //实例化对象 - 行项目
        Trea_TPTemplateDAO  dao           = new Trea_TPTemplateDAO();
        Trea_TPTemplateInfo info          = new Trea_TPTemplateInfo();
        
        //实例化对象 - 子项目
        Trea_TPTemplateItemDAO  itemDao   = new Trea_TPTemplateItemDAO();
        Trea_TPTemplateItemInfo itemInfo  = new Trea_TPTemplateItemInfo();
        
        //得到将要被删除的行项目信息
        if( lineId > 0 )
        {
            log4j.info("得到将要被删除的行项目信息..............");
            info = (Trea_TPTemplateInfo)dao.findByID( lineId , (new Trea_TPTemplateInfo()).getClass() );
        }
        
        //判断是否允许删除资金计划模板的行项目
        if( info != null && isAllowDelete(info) )
        {
            
            try 
            {
                //删除行项目
                log4j.info("删除行项目..............");
                dao.updateStatus(info.getId(),Constant.RecordStatus.INVALID);
                
                
                //删除行项目的取数逻辑
                log4j.info("查找与行项目对应的子项目.............. lineId = "+lineId);
                Collection coll = itemDao.findAllByLineID(lineId);
                
                if( coll != null && coll.size() > 0 )
                {
                    
                    Iterator iterator = coll.iterator();
                    
                    com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO  deleteDao   = null;
                    com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo   forDelInfo = null;
                    
                    while( iterator.hasNext() )
                    {
                        forDelInfo = (com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo )iterator.next();
                        deleteDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO();
                       
                        log4j.info("删除行项目的取数逻辑(物理删除)..............");
                        
                        deleteDao.deleteOneItem(forDelInfo.getId());
                    }
                }
            }
            catch ( Exception e ) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        else
        {
            throw new ITreasuryException("该行项目还有下级行项目或者已经分配给某部门使用，不允许删除！","",null);
        }
    }
    
    
    /**
     *@Description:资金计划模板的行项目设置 - 新增、修改行项目
     *@param Trea_TPTemplateInfo
     *@return templateId
     *@throws IException
     */
    public long saveTemplateLine(Trea_TPTemplateInfo info) throws Exception
    {
        long templateId = -1;
            
        Trea_TPTemplateDAO dao = new Trea_TPTemplateDAO();
            
        if( info.getId() > 0 )
        {
            //修改保存
        	//取数逻辑的金额标志与行项目的项目类型有关，因此在行项目的类型被修改的时候，需要判断是否需要进行取数逻辑的修改
        	//涉及到多个表，这里需要手工控制事务
        	Connection transConn = null;
            try 
            {
        		try
				{
            		transConn =Database.getConnection();
            		transConn.setAutoCommit(false);
                }
        		catch (Exception e)
				{
                   throw new ITreasuryDAOException("数据库初使化异常发生", e);
                }
                Trea_TPTemplateDAO tpDao = new Trea_TPTemplateDAO(transConn);
                com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO tpItemDao = new com.iss.itreasury.treasuryplan.setting.dao.Trea_TPTemplateItemDAO(transConn);
				tpDao.update(info);
				if (info.getMaintenanceFlag() == TPConstant.LineType.Balance_Begin)  
				{
					tpItemDao.updateItemByMaintenanceFlag(info.getId(), TPConstant.AmountFlagForSetting.Yesterday_Balance);
				}
				if (info.getMaintenanceFlag() == TPConstant.LineType.Balance_End)
				{
					tpItemDao.updateItemByMaintenanceFlag(info.getId(), TPConstant.AmountFlagForSetting.Today_Balance);
				}
				try
				{
	                if (transConn != null)
	                {
	                    transConn.commit();
						transConn.close();
	                    transConn = null;
	                }
	            }
				catch (SQLException e)
				{
	                e.printStackTrace();
	                throw new ITreasuryDAOException("数据库关闭异常发生", e);
	            }
	        }
            catch (Exception e)
			{
	            try
				{
	                if (transConn != null)
	                {
	                    transConn.rollback();
						transConn.close();
	                    transConn = null;
	                }
	            }
	            catch (Exception es)
				{
	                es.printStackTrace();
	            }
	        }
            finally
			{
	            try
				{
	                if (transConn != null)
	                {
	                    transConn.close();
	                    transConn = null;
	                }
	            }
	            catch (Exception e)
				{
	                e.printStackTrace();
	            }
	        }
        }
        else
        {
            //新增保存
            if( isAllowSave(info) )//检查行项目是否重复
            {
                try 
                {
                    dao.add(info);
                }
                catch ( ITreasuryDAOException e ) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else
            {
                throw new ITreasuryException("重复设置,行项目: "+info.getLineNo()+" 已经存在!","",null);
            }
        }
            
        return templateId;
     }

    
}
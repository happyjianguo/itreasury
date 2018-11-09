package com.iss.itreasury.budget.setting.bizlogic;

import java.util.*;

import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.setting.dao.Budget_ClientRelationDAO;
import com.iss.itreasury.budget.setting.dataentity.BudgetClientRelationInfo;

/**
 * @author shantao
 * 
 * @version 1.0 2005-12-19
 */
public class BudgetClientRelationBiz
{
    /**
     * 用户编码设置，新建或修改。新增状态的情况下判断用户编码不能重复
     * 
     * @param info
     * @return
     * @throws BudgetException
     */
    public long save(BudgetClientRelationInfo info) throws Exception
    {
        long returnLong = -1; //-2：新增用户编码已经存在。-3:用户选择的上级为本身或用户的下级。
        try
        {
            Budget_ClientRelationDAO dao = new Budget_ClientRelationDAO();
            //add operation
            if (info.getId() == -1)
            {
                //先根据代码判断此用户是否已经存在
                BudgetClientRelationInfo tmpInfo = new BudgetClientRelationInfo();
                tmpInfo.setClientId(info.getClientId());
                Collection c = dao.findByCondition(tmpInfo);

                if (c.size() < 1)
                {
                    //新增客户的上级不能是此客户原来的下级。
                    if (this.isValid(String.valueOf(info.getClientId()), String
                            .valueOf(info.getParentClientId())))
                    {
                        returnLong = dao.add(info);
                    }
                    else
                    {
                        returnLong = -5;//-5:新增客户的上级不能是此客户原来的下级。
                    }
                }
                else
                {
                    returnLong = -2;//-2：新增用户编码已经存在。
                }
            }
            //modify operation
            else
            {
                if (this.isValid(String.valueOf(info.getClientId()), String
                        .valueOf(info.getParentClientId())))
                {
                    dao.update(info);
                    returnLong = info.getId();
                }
                else
                {
                    returnLong = -3;//-3:用户选择的上级为本身或用户的下级。
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return returnLong;
    }

    /**
     * 根据ID删除一条记录
     * 
     * @param id
     * @return
     * @throws BudgetException
     */
    public long delete(String[] id) throws Exception
    {
        Budget_ClientRelationDAO dao = new Budget_ClientRelationDAO();
        long retlong = -1;
        try
        {
            retlong = dao.delete(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return retlong;
    }

    /**
     * 查询所有记录 ，返回一个集合
     * 
     * @return
     * @throws BudgetException
     */
    public Collection findAll() throws Exception
    {
        Budget_ClientRelationDAO dao = new Budget_ClientRelationDAO();
        BudgetClientRelationInfo tmpInfo = new BudgetClientRelationInfo();

        Collection coll = null;
        try
        {
            coll = dao.findAll();
        }
        catch (Exception ec)
        {
            ec.printStackTrace();
        }
        return coll;
    }

    /**
     * 根据ID查询一条记录，返回一个对象
     * 
     * @param id
     * @return
     * @throws BudgetException
     */
    public BudgetClientRelationInfo findByID(long id) throws Exception
    {
        Budget_ClientRelationDAO dao = new Budget_ClientRelationDAO();
        BudgetClientRelationInfo info = new BudgetClientRelationInfo();
        try
        {
            info = (BudgetClientRelationInfo) dao.findByID(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 判断选择的节点是否是自己或自己的下级节点。
     * 
     * @param id
     * @param parentId
     * @return
     * @throws Exception
     */
    public boolean isValid(String id, String parentId) throws Exception
    {
        if (id.equals(parentId))
            return false;

        boolean flag = true;
        BudgetClientRelationInfo info = null;
        ArrayList al = null;
        HashMap hm = new HashMap();

        Collection c = this.findAll();
        Iterator it = c.iterator();
        while (it.hasNext())
        {
            info = (BudgetClientRelationInfo) it.next();
            if (hm.containsKey(String.valueOf(info.getParentClientId())))
            {
                al = (ArrayList) hm.get(String
                        .valueOf(info.getParentClientId()));
                al.add(String.valueOf(info.getClientId()));
            }
            else
            {
                al = new ArrayList();
                al.add(String.valueOf(info.getClientId()));
                hm.put(String.valueOf(info.getParentClientId()), al);
            }
        }
        
        flag = !this.isChild(hm, id, parentId);
        return flag;
    }

    public boolean isChild(Map map, String id, String parentId)
        throws Exception
    {
        boolean flag = false;
        ArrayList al = null;
        if (map.containsKey(id))
        {
            al = (ArrayList) map.get(id);
            for (int i = 0; i < al.size(); i++)
            {
                if (((String) al.get(i)).equals(parentId))
                    return true;
                else if (isChild(map, (String) al.get(i), parentId))
                    return true;
            }
        }
        else
        {
            return false;
        }
        return flag;
    }

}
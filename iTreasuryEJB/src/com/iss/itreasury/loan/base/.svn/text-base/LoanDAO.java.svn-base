package com.iss.itreasury.loan.base;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;


/**
 * Title:               iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-26
 */
/**
 * 以下DAO的所有通用操作有以下假设： <p>
 * 1.数据库字段名与DataEntity中属性名一一对应且相同 <p>
 * 2.所有被操作的DataEntity继承抽象类SECBaseDataEntity<p>
 * 3.所有操作假设操作的数据库表的主键名为ID且类型是long<p>
 * 
 * 如果不符合以上假设的特殊操作，请自定义各种操作，但仍需要继承SecuritiesDAO，<p>
 * 1.并在操作开始前调用initDAO<p>
 * 2.在结束前使用finalizeDAO<p>
 * 3.子类中不对PrepareStatement和ResultSet直接进行操作，即它们的生命周期由父类维护使用SecuritiesDAO定义的方法：<p> 
 * PreparedStatement prepareStatement(String sql)       <p>
 * ResultSet　executeQuery()                                    <p>
 * void      executeUpdate()                                <p>
 * 对它们进行操作<p>
 * 
 * 如果只针对setPrepareStatementByDataEntity或<p>
 *getDataEntityFromResultSet有特殊操作，可以在子类中重载并且实现该方法而继续使用通用的数据库操作方法<p>
 *              
 * 
 *      
 */
public abstract class LoanDAO extends ITreasuryDAO
{
    
    public LoanDAO(String tableName)
    {
        super(tableName);
    }

    public LoanDAO(String tableName, String sequence)
    {
        super(tableName, sequence);
    }

    public LoanDAO(String tableName,Connection conn)
    {
        super(tableName,conn);
    }

    public LoanDAO(String tableName,String sequence,Connection conn)
    {
        super(tableName,sequence,conn);
    }

    //  get方法不用前缀
    public LoanDAO(String tableName,boolean isNeedPrefix){
        super(tableName,true);
    }

    //  get方法不用前缀
    public LoanDAO(String tableName,String sequence,boolean isNeedPrefix){
        super(tableName,sequence,true);
    }
}

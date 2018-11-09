package com.iss.system.dao;
/**
 * <p>DirectDataSourceInfo 用于存储当程序直接配置访问数据源的信息。</p>
 * 在很多情况下，我们需要直接访问数据库，但是JNDI服务却没有启动，所以只好在程序里HARD CODE
 * 数据源配置信息，建议使用这个类。<br>
 * <strong>不建议发布给客户的程序使用HARD CODE的数据源信息，大多情况下这样的信息在发布前要转成
 * 配置资源。</strong><br>
 * 这样的配置信息可能更多是用在JUNIT测试过程中。
 * @author pliu
 */
public class DirectDataSourceInfo
{
    private String m_strServer = null;
    private String m_strDatabase = null;
    private int m_nPort = 0;
    private String m_strUser = null;
    private String m_strPassword = null;
    /**
     * 
     * @param strServer
     * @param strDatabase
     * @param nPort
     * @param strUser
     * @param strPassword
     */
    public DirectDataSourceInfo(String strServer, String strDatabase, int nPort, String strUser, String strPassword)
    {
        this.m_strServer = strServer;
        this.m_strDatabase = strDatabase;
        this.m_nPort = nPort;
        this.m_strUser = strUser;
        this.m_strPassword = strPassword;
    }
    /**
     * Returns the port.
     * @return int
     */
    public int getPort()
    {
        return m_nPort;
    }
    /**
     * Returns the password.
     * @return String
     */
    public String getPassword()
    {
        return m_strPassword;
    }
    /**
     * Returns the server.
     * @return String
     */
    public String getServer()
    {
        return m_strServer;
    }
    /**
     * Returns the user.
     * @return String
     */
    public String getUser()
    {
        return m_strUser;
    }
    /**
     * Sets the port.
     * @param port The port to set
     */
    public void setPort(int port)
    {
        m_nPort = port;
    }
    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password)
    {
        m_strPassword = password;
    }
    /**
     * Sets the server.
     * @param server The server to set
     */
    public void setServer(String server)
    {
        m_strServer = server;
    }
    /**
     * Sets the user.
     * @param user The user to set
     */
    public void setUser(String user)
    {
        m_strUser = user;
    }
    /**
     * Returns the database.
     * @return String
     */
    public String getDatabase()
    {
        return m_strDatabase;
    }
    /**
     * Sets the database.
     * @param database The database to set
     */
    public void setDatabase(String database)
    {
        m_strDatabase = database;
    }
}
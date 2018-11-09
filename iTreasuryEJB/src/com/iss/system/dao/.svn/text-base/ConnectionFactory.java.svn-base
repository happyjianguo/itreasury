package com.iss.system.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
//import org.gjt.mm.mysql.MysqlDataSource;
import oracle.jdbc.pool.OracleDataSource;
/**
 * 数据源连接工厂类。<br>
 * 该类提供取得系统数据源及连接方法，在系统中的任何程序模块中不要使用私有的数据源访
 * 问方法，而要通过为里的方法统一数据源及数据源连接。<br>
 * <p>在这个工厂类中的方法分为两组：一组用直接连接到MYSQL和oracle数据库系统,被称为DIRECT方法； 另外一组 要经过系统 的JNDI 资 源
 * 连 接 到 MYSQL或者oracle数据库系统，被称为JNDI方法。<br> 当我们进行JUNIT测试时， 要使用DIRECT方法，而在我们真正的程序运
 * 行 时 要 使 用 JNDI方 法， 从而 使用系统 的连接池，优化系统 连接性能。</p>
 * <p>
 * 在使用这个工厂类时，可以使用getConnection和getDataSource方法取得数据源或到数据源的连接。在使
 * 用这两个方法前应该设置Connection Context. 建议在应用系统起动时设置Connection Context.<br>
 * 但是这两个方法由于使用了静态的Connection Context而有一定的局限性，建议使用另外一对带有Connection
 * Context参数的方法。
 * </p>
 * <p>在这个类的方法中没有指定连接到的数据源参数，因为每个实现系统都会有自己的唯一数据源，如果这
 * 个类方法只用于一个系统就没必要再指定数据源参数。只是每个系统在使用这个类时要修改这个类中的部分
 * 参数，并没有什么不方便的。
 * </p>
 * @author pliu
 */
public class ConnectionFactory
{
	/**
	 * 一个默认的连接环境设置，对于不同的系统可能不同实现，这里指使用m_nConnectionContext值。
	 */
	public static final int CONNECT_CONTEXT_DEFAULT = 0;
	/**
	 * 连接环境设置标识。使用这个连接环境用于直接连接到MYSQL数据库系统。
	 */
	public static final int CONNECT_CONTEXT_DIRECT2MYSQL = 1;
	/**
	 * 连接环境设置标识。使用这个连接环境用于JNDI资源连接到MYSQL数据库系统。
	 */
	public static final int CONNECT_CONTEXT_JNDI2MYSQL = 2;
	/**
	 * 连接环境设置标识。使用这个连接环境用于直接连接到SQL SERVER数据库系统。
	 */
	public static final int CONNECT_CONTEXT_DIRECT2SQLSERVER = 3;
	/**
	 * 连接环境设置标识。使用这个连接环境用于JNDI资源连接到ORACLE数据库系统。
	 */
	public static final int CONNECT_CONTEXT_JNDI2SQLSERVER = 4;
	/**
	 * 连接环境设置标识。使用这个连接环境用于直接连接到ORACLE数据库系统。
	 */
	public static final int CONNECT_CONTEXT_DIRECT2ORACLE = 5;
	/**
	 * 连接环境设置标识。使用这个连接环境用于JNDI资源连接到ORACLE数据库系统。
	 */
	public static final int CONNECT_CONTEXT_JNDI2ORACLE = 6;
	/**
	 * 默认的连接环境标识，在不同的应用系统中这个参数可以初始化为不同的值，初始值与初始方法
	 * 请参见类静态初始化模块。
	 */
	private static int m_nConnectionContext = CONNECT_CONTEXT_JNDI2ORACLE;
	private static Properties m_propsDatasources = new Properties();
	/**
	 * Constructor for ConnectionFactory.
	 * 该构造方法的目的是防止构造这个类的实例。
	 */
	protected ConnectionFactory()
	{
		super();
	}
	/**
	 * 能过JNDI资源连接到MYSQL数据库系统。
	 * @return Connection
	 * @throws Exception
	 */
	private static Connection getJNDIConnection2MySQL(String strJNDIName) throws Exception
	{
		Connection conn = null;
		DataSource datasource = ConnectionFactory.getJNDIDataSource2MySQL(strJNDIName);
		if (datasource != null)
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conn = datasource.getConnection();
		}
		return conn;
	}
	/**
	 * 通过JNDI资源取得到MYSQL数据库系统的DataSource.
	 * @return DataSource
	 * @throws Exception
	 */
	private static javax.sql.DataSource getJNDIDataSource2MySQL(String strJNDIName) throws Exception
	{
		Context ctx = new InitialContext();
		if (ctx == null)
			throw new Exception("iampro - No Context");
		DataSource datasource = (DataSource) ctx.lookup(strJNDIName);
		if (datasource instanceof BasicDataSource)
		{
			BasicDataSource bds = (BasicDataSource) datasource;
			bds.addConnectionProperty("useUnicode", "true");
			bds.addConnectionProperty("characterEncoding", "gb2312");
		}
		return datasource;
	}
	/**
	 * 直接连接到MYSQL数据库系统。
	 * @return Connection
	 * @throws Exception
	 */
	private static Connection getDirectConnection2MySQL(String strResourceName) throws Exception
	{
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		DirectDataSourceInfo datasourceInfo =
			(DirectDataSourceInfo) ConnectionFactory.m_propsDatasources.get(strResourceName);
		String strURI =
			MessageFormat.format(
				"jdbc:mysql:{0}?user={1}&password={2}&useUnicode=true&characterEncoding=gb2312",
				new Object[] { datasourceInfo.getServer(), datasourceInfo.getUser(), datasourceInfo.getPassword()});
		return DriverManager.getConnection(strURI);
	}
	/**
	 * 直接取到到MYSQL数据库系统的DataSource.
	 * @return DataSource
	 * @throws Exception
	 */
	private static javax.sql.DataSource getDirectDataSource2MySQL(String strResourceName) throws Exception
	{
//		DirectDataSourceInfo datasourceInfo =
//			(DirectDataSourceInfo) ConnectionFactory.m_propsDatasources.get(strResourceName);
//		MysqlDataSource datasource = new MysqlDataSource();
//		datasource.setServerName(datasourceInfo.getServer());
//		datasource.setUser(datasourceInfo.getUser());
//		datasource.setPassword(datasourceInfo.getPassword());
//		datasource.setPort(datasourceInfo.getPort());
//		return datasource;
		return null;
	}
	/**
		 * 能过JNDI资源连接到ORACLE数据库系统。
		 * @return Connection
		 * @throws Exception
		 */
	private static Connection getJNDIConnection2Oracle(String strJNDIName) throws Exception
	{
		Connection conn = null;
		DataSource datasource = ConnectionFactory.getJNDIDataSource2Oracle(strJNDIName);
		if (datasource != null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = datasource.getConnection();
			conn.setAutoCommit(false);
            //禁止脏数据读写(dirty reads)和重复读写(repeatable reads)，允许影象读写（phntom reads）
            //conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		}
		return conn;
	}
	/**
	 * 通过JNDI资源取得到ORACLE数据库系统的DataSource.
	 * @return DataSource
	 * @throws Exception
	 */
	private static javax.sql.DataSource getJNDIDataSource2Oracle(String strJNDIName) throws Exception
	{
		Context ctx = new InitialContext();
		if (ctx == null)
			throw new Exception("slof - No Context");
		DataSource datasource = (DataSource) ctx.lookup(strJNDIName);
		if (datasource instanceof BasicDataSource)
		{
			BasicDataSource bds = (BasicDataSource) datasource;
			bds.addConnectionProperty("useUnicode", "true");
			bds.addConnectionProperty("characterEncoding", "gb2312");
		}
		return datasource;
	}
	/**
	  * 直接连接到ORACLE数据库系统。
	  * @return Connection
	  * @throws Exception
	  */
	private static Connection getDirectConnection2Oracle(String strResourceName) throws Exception
	{
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		DirectDataSourceInfo datasourceInfo =
			(DirectDataSourceInfo) ConnectionFactory.m_propsDatasources.get(strResourceName);
		String strURI =
			MessageFormat.format(
				"jdbc:oracle:thin:@{0}:{1}:{2}",
				new Object[] {
					datasourceInfo.getServer(),
					String.valueOf(datasourceInfo.getPort()),
					datasourceInfo.getDatabase()});
		conn = DriverManager.getConnection(strURI, datasourceInfo.getUser(), datasourceInfo.getPassword());
		conn.setAutoCommit(false);
		return conn;
	}
	/**
	 * 直接取到到Oralce数据库系统的DataSource.
	 * @return DataSource
	 * @throws Exception
	 */
	private static javax.sql.DataSource getDirectDataSource2Oracle(String strResourceName) throws Exception
	{
		DirectDataSourceInfo datasourceInfo =
			(DirectDataSourceInfo) ConnectionFactory.m_propsDatasources.get(strResourceName);
		OracleDataSource datasource = new OracleDataSource();
		datasource.setDatabaseName(datasourceInfo.getDatabase());
		datasource.setServerName(datasourceInfo.getServer());
		datasource.setUser(datasourceInfo.getUser());
		datasource.setPassword(datasourceInfo.getPassword());
		datasource.setPortNumber(datasourceInfo.getPort());
		datasource.setURL(
			MessageFormat.format(
				"jdbc:oracle:thin:@{0}:{1}:{2}",
				new Object[] {
					datasourceInfo.getServer(),
					String.valueOf(datasourceInfo.getPort()),
					datasourceInfo.getDatabase()}));
		return datasource;
	}
	/**
	 * 根据Connection Context设置取得到数据库的数据源DataSource。
	 * @return DataSource
	 * @throws Exception
	 */
	public static DataSource getDataSource(String strResourceName) throws Exception
	{
		return getDataSource(strResourceName, ConnectionFactory.m_nConnectionContext);
	}
	/**
	 * 根据指定的Connection Context设置取得到数据库的数据源DataSource。
	 * @param nConnectionContext 指定以什么样的方式连接到哪一类数据库系统。
	 * @return DataSource
	 * @throws Exception
	 */
	public static DataSource getDataSource(String strResourceName, int nConnectionContext) throws Exception
	{
		if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DEFAULT)
		{
			nConnectionContext = ConnectionFactory.m_nConnectionContext;
		}
		if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DIRECT2MYSQL)
		{
			return ConnectionFactory.getDirectDataSource2MySQL(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_JNDI2MYSQL)
		{
			return ConnectionFactory.getJNDIDataSource2MySQL(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DIRECT2ORACLE)
		{
			return ConnectionFactory.getDirectDataSource2Oracle(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_JNDI2ORACLE)
		{
			return ConnectionFactory.getJNDIDataSource2Oracle(strResourceName);
		}
		else
		{
			throw new SQLException("unsupported connection type!");
		}
	}
	/**
	 * 根据Connection Context设置取得到数据库的连接。
	 * @return Connection
	 * @throws Exception
	 */
	public static Connection getConnection(String strResourceName) throws Exception
	{
		return getConnection(strResourceName, ConnectionFactory.m_nConnectionContext);
	}
	/**
	 * 释放掉不用的数据源连接。
	 * @param conn
	 */
	public static void releaseConnection(Connection conn)
	{
		if (null != conn)
		{
			try
			{
				conn.close();
			}
			catch (Exception e)
			{
				System.out.println("Failed to close datasource connection.");
			}
		}
	}
	/**
	 * 根据指定的Connection Context设置取得到数据库的连接。
	 * @param nConnectionContext 指定以什么样的方式连接到哪一类数据库系统。
	 * @return Connection
	 * @throws Exception
	 */
	public static Connection getConnection(String strResourceName, int nConnectionContext) throws Exception
	{
		if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DEFAULT)
		{
			nConnectionContext = ConnectionFactory.m_nConnectionContext;
		}
		if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DIRECT2MYSQL)
		{
			return ConnectionFactory.getDirectConnection2MySQL(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_JNDI2MYSQL)
		{
			return ConnectionFactory.getJNDIConnection2MySQL(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_DIRECT2ORACLE)
		{
			return ConnectionFactory.getDirectConnection2Oracle(strResourceName);
		}
		else if (nConnectionContext == ConnectionFactory.CONNECT_CONTEXT_JNDI2ORACLE)
		{
			return ConnectionFactory.getJNDIConnection2Oracle(strResourceName);
		}
		else
		{
			throw new SQLException("unsupported connection type!");
		}
	}
	/**
	 * 设置默认连接一环境.<br>
	 * Returns  the connectContext.
	 * @return int
	 */
	public static int getConnectionContext()
	{
		return m_nConnectionContext;
	}
	/**
	 * 读取默认连接环境设置.<br>
	 * Sets the connectContext.
	 * @param connectContext The connectContext to set
	 */
	public static void setConnectionContext(int connectionContext)
	{
		m_nConnectionContext = connectionContext;
	}
	/**
	 * 以下的静态初始化块是为了从配置文件中读取默认设置而加入的。
	 * 但是在最初加入时，这个初始化模块没有实现。
	 */
	static {
		System.out.println("Connection Factory is implemented!");
	}
	/**
	 * Sets the propsDatasources.
	 * @param propsDatasources The propsDatasources to set
	 */
	public static void addDirectDatasourceInfo(String strResourceName, DirectDataSourceInfo datasourceInfo)
	{
		m_propsDatasources.put(strResourceName, datasourceInfo);
	}
	/**
	 * 提交当前事物
	 * @author xrzhang
	 */
	public static void commit(Connection conn)
	{
		try
		{
			if (conn != null)
			{
				conn.commit();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 回滚当前事物
	 * @author xrzhang
		 */
	public static void rollback(Connection conn)
	{
		try
		{
			if (conn != null)
			{
				conn.rollback();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
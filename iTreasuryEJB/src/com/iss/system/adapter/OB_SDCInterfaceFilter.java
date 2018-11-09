package com.iss.system.adapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.iss.itreasury.ebank.privilege.bizlogic.UserBean;
import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.safety.util.CertificateConstant;
import com.iss.itreasury.system.loginlog.bizlogic.LoginLogBean;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.toft.core2.UserInfo;
import com.toft.core2.transfer.util.TransferClientUtil;
import com.toft.core2.user.UnitModel;
import com.toft.merge.frame.core.bean.MergeBaseUserInfo;

public class OB_SDCInterfaceFilter implements Filter {

	private Map info;
	private Logger log = Logger.getLogger(this.getClass().getName());

	public void destroy() {
	}

	/**
	 * 初始化sdc过滤器, 获取sdc那边的信息
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void initSDCInterface(HttpServletRequest request,Map info) throws Exception {

		String identifer = request.getParameter("SDCsessionId");
		SessionOB sessionMng = new SessionOB();
		String login = (String) info.get("loginName");
		String password = (String) info.get("loginPassword");
		String currencyId = (String) info.get("currencyId");
		if (currencyId == null) {
			currencyId = "1";
		}
		long m_lCurrencyID = Long.parseLong(currencyId);
		String clientIp = request.getRemoteHost();
		//登录
		sessionMng.init(login, password, clientIp, "SDC");
		//添加登录日志
		LoginLogBean loginBean = new LoginLogBean();
		loginBean.addLoginLog(sessionMng,Constant.EBANK_USER,request.getRemoteHost(),Constant.SUCCESSFUL,"用户登录成功");	
		
		sessionMng.m_strLogin = login;
		sessionMng.m_lCurrencyID = m_lCurrencyID;
		request.getSession().setAttribute("sessionMng", sessionMng);
		request.getSession().setAttribute("SDCsessionId", identifer);
		request.getSession().setAttribute("Toft_SessionKey_UserData", info.get("userInfo"));
		//从SDC集成平台取得证书的序列号放到session中。（使用SDC集成平台前不需要如此，网银系统登录时会将证书的序列号放到session中）
		UserInfo userInfo = (UserInfo)info.get("userInfo");
		if(userInfo != null)
		{
			String toftCertSN = (String) userInfo.getAttribute("toftCertSN");
			String toftCertCN = (String) userInfo.getAttribute("toftCertCN");
			String staticCfgFilePath = ".config/SafetyConfig.xml";
			//CertificateUtil.init(staticCfgFilePath);
			request.getSession().setAttribute(CertificateConstant.CERTIFICATEINFO_SERIALNUMBER.getName(), toftCertSN);
			request.getSession().setAttribute(CertificateConstant.CERTIFICATEINFO_COMMONNAME.getName(), toftCertCN);
			
			log.info("=========================toftCertSN==============  "+toftCertSN);
			log.info("=========================toftCertCN==============  "+toftCertCN);
		}
	}

	/**
	 * 过滤所有的请求 *.jsp, action
	 */
	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) paramServletRequest;	
			HttpServletResponse response = (HttpServletResponse) paramServletResponse;
			String reqURL = request.getRequestURL().toString();
			String identifer = request.getParameter("SDCsessionId");
			sdc_transfer_service_url = request.getParameter("MGHessianURL");
			SessionOB mng =null ;
			// 登录页面，消息展现页面不进行过滤器判断
			if ((reqURL.indexOf("noLogin.jsp") != -1) || (reqURL.indexOf("RemindTop.jsp") != -1) || (reqURL.indexOf("RemindBottom.jsp") != -1)||(reqURL.indexOf("iTreasury-configtool")!=-1)) {
				paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			} else {
				// 请求都是从SDC过来的。
				if (identifer != null && !identifer.equals("")) {
					// 判断SDC系统session是否失效
					info = (Map) TransferClientUtil.accessHessianTransferObject(sdc_transfer_service_url, identifer);
					String SDCAppName = request.getParameter("MGAppName");
					if(SDCAppName==null||SDCAppName.equals("")){
						SDCAppName = "toftmerge/ebank/welcome.do";
					}
					else if(SDCAppName.equals("toftmerge")){
						SDCAppName = "toftmerge/ebank/welcome.do";
					}
					Constant.SDCURL="http://"+request.getLocalAddr()+":"+request.getServerPort()+"/"+SDCAppName;
					if (info == null) {
						// 如果失效，返回登录页面
						response.sendRedirect(request.getContextPath() + "/noLogin.jsp");
					} else {
						mng = (SessionOB) request.getSession().getAttribute("sessionMng");
						// 过滤器是否启用
						if (filter_enable) {
							// 判断sessionMng是否存在, 如果存在就不需要过滤
							if (mng == null) {// 不存在的情况, 初始化session
								log.info("开始初始化session");
								initSDCInterface(request,info);
								mng = (SessionOB) request.getSession().getAttribute("sessionMng");
								request.getSession().setAttribute("MGHessianURL", sdc_transfer_service_url);
							} 
							else { // 存在的情况校验session id
								// 是否一致(此情况可能是没有点击注销按钮，直接关闭页面，但是服务器中的session并没有失效)
								String now_sessionId = request.getParameter("SDCsessionId");
								String old_sessionId = (String) request.getSession().getAttribute("SDCsessionId");
								if (now_sessionId != null && !now_sessionId.equals(old_sessionId)) {
									// 不一致
									initSDCInterface(request,info);
									request.getSession().setAttribute("SDCsessionId", now_sessionId);
									request.getSession().setAttribute("MGHessianURL", sdc_transfer_service_url);
								}
								// 是否切换单位
								String now_lClientID = request.getParameter("lClientID");
								String old_lClientID = String.valueOf(mng.m_lClientID);
								if(now_lClientID != null && !now_lClientID.equals(old_lClientID)){
									mng.m_lClientID = Long.parseLong(now_lClientID);
								}
								mng = (SessionOB) request.getSession().getAttribute("sessionMng");
							}
						}
					}
				}
				
				// 从request参数中取得单位ID，并将其放入到UserInfo中
				String unitId = request.getParameter("UnitId");
				long lOfficeId = -1;
				if(unitId!=null&&!unitId.equals(""))
				{
					UserBean bean = new UserBean();
					lOfficeId = bean.getOfficeByClient(Long.parseLong(unitId));
					mng.m_lOfficeID = lOfficeId;
				}
				UserInfo userInfo = (UserInfo) request.getSession().getAttribute("Toft_SessionKey_UserData");
				if (null != userInfo && unitId != null && !unitId.equals(userInfo.getUnitId())) {
					
					List units = userInfo.getUnits();
					for (int i = 0; i < units.size(); i++) {
						UnitModel unitModel = (UnitModel)units.get(i);
						if (unitId.equals(unitModel.getId())) {
							userInfo.setUnitId(unitId);
							userInfo.setUnitCode(unitModel.getCode());
							userInfo.setUnitLevelCode(unitModel.getLevelCode());
							userInfo.setUnitName(unitModel.getName());
							break;
						}
					}
				}
				
				// 路径校验
				try {
					if (!this.checkUrl(request)) {
						this.printError(response);
					}else {
						paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
				if(sdc_transfer_service_url!=null)
				{
					info = (Map) TransferClientUtil.accessHessianTransferObject(sdc_transfer_service_url, identifer);
				}
				//UserInfo userInfo1 = (UserInfo) request.getSession().getAttribute("Toft_SessionKey_UserData");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String sdc_transfer_service_url;
	private static boolean filter_enable;

	public void init(FilterConfig paramFilterConfig) throws ServletException {
		filter_enable = "true".equals(paramFilterConfig.getInitParameter("filter.enable"));
	}
	
	/**
	 * 检查URL
	 * @param request
	 * @param url
	 * @return
	 * @throws SQLException 
	 */
	private boolean checkUrl(HttpServletRequest request) throws Exception {
		String url = this.getServletPath(request);
		log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝");
		log.info("正在校验URL:"+url);
		log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝");
		boolean flag = false;
		// TODO:待修改，如果不是SDC系统，如何取得数据库连接？
		// Connection conn = DbUtils.getConnection("1");
		Connection conn = Database.getConnection();
		if (isNeedPathVal(url, conn)) {
//			List list = (List) request.getSession().getAttribute("VAL_PATH");
//			if (list == null) {
//				MergeBaseUserInfo userInfo = (MergeBaseUserInfo) request.getSession().getAttribute(ToftConst.Toft_SessionKey_UserData);
//				list = this.getAllMenuPath(userInfo.getUserId(), userInfo.getUserType(), userInfo.getAgencyType(), conn);
//				request.getSession().setAttribute("VAL_PATH",list);
//			}
			MergeBaseUserInfo userInfo = (MergeBaseUserInfo) request.getSession().getAttribute("Toft_SessionKey_UserData");
			List list = this.getAllMenuPath(userInfo.getUserId(), userInfo.getUserType(), userInfo.getAgencyType(), conn);
			for (int i = 0; i < list.size(); i++ ) {
				Map map = (Map) list.get(i);
				String path = (String) map.get("PATH");
				if (url != null && path != null && path.indexOf(url) != -1) {
					log.info("========================");
					log.info("路径存在，并且该用户有访问权限");
					log.info("========================");
					flag = true;
					break;
				}
			}
		}else {
			log.info("============");
			log.info("这个URL没有注册，不需要校验");
			log.info("============");
			flag = true;
		}
		conn.close();
		return flag;
	}
	/**
	 * 利用服务器响应向页面输出错误信息。
	 * @param response			：服务器响应
	 * @throws IOException
	 */
	private void printError(HttpServletResponse response) throws IOException {
		//response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write("<script type=\"text/javascript\">");
		pw.write("alert('\u60a8\u6ca1\u6709\u6743\u9650\u8bbf\u95ee\u8be5\u8def\u5f84\uff01\uff01')");
		pw.write("</script>");
		pw.close();
	}
	
	/**
	 * 检验URL是否需要过滤
	 * @param url
	 * @return
	 * @throws SQLException 
	 * @throws DBException 
	 */
	private boolean isNeedPathVal(String url, Connection conn) throws SQLException {
		log.info("=====================");
		log.info("正在判断是否需要校验");
		log.info("=====================");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT 1 ");
		sqlBuffer.append(" FROM MG_MENU_DIRECTORY ");
		sqlBuffer.append(" WHERE RESOURCE_ID IN ");
		sqlBuffer.append(" 	(SELECT ID FROM MG_MENU_RESOURCE WHERE MENU_PATH like '%").append(url).append("%')");
		
		String sql = sqlBuffer.toString();
		log.info("输出查询SQL : "+sql);
		List result = this.select(sql, conn);
		if (result != null && result.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 取得该用户所有可用的路径
	 * @param userId			：用户ID
	 * @param userType			：用户类型
	 * @param agencyType		：机构类型
	 * @param conn				：数据库连接
	 * @return					：返回结果集List&lt;Map&lt;"PATH",""&gt;&gt;
	 * @throws SQLException		：抛出SQLException
	 */
	private List getAllMenuPath(String userId, String userType, String agencyType, Connection conn) throws SQLException{
		
		log.info("===========================");
		log.info("正在取得该用户的所有路径");
		log.info("===========================");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT menu_path as PATH ");
		sqlBuffer.append(" FROM mg_menu_resource");
		sqlBuffer.append(" WHERE id IN");
		sqlBuffer.append("   (SELECT resource_id");
		sqlBuffer.append("   FROM mg_menu_directory");
		sqlBuffer.append("   WHERE id IN");
		sqlBuffer.append("     (SELECT dir_id");
		sqlBuffer.append("     FROM mg_r_duty_dir");
		sqlBuffer.append("     WHERE duty_id IN");
		sqlBuffer.append("       (SELECT id");
		sqlBuffer.append("       FROM mg_duty");
		sqlBuffer.append("       WHERE (id IN");
		sqlBuffer.append("         (SELECT dutyid");
		sqlBuffer.append("         FROM mg_r_duty_agency");
		sqlBuffer.append("         WHERE id IN");
		sqlBuffer.append("           (SELECT agency_duty_id AS depid");
		sqlBuffer.append("           FROM mg_r_user_duty_agency");
		sqlBuffer.append("           WHERE userId = '").append(userId).append("'");
		sqlBuffer.append("           )");
		sqlBuffer.append("         ) OR is_default_duty = '1' )");
		sqlBuffer.append("       AND agency_type  ='").append(agencyType).append("'");
		sqlBuffer.append("       AND status       ='1'");
		sqlBuffer.append("       AND flag         ='1'");
		sqlBuffer.append("       AND for_user_type='").append(userType).append("'");
		sqlBuffer.append("       )");
		sqlBuffer.append("     )");
		sqlBuffer.append("   AND resource_id IS NOT NULL");
		sqlBuffer.append("   )");
		sqlBuffer.append(" AND for_user_type   ='").append(userType).append("'");
		sqlBuffer.append(" AND integration_id IN");
		sqlBuffer.append("   (SELECT real_system_id");
		sqlBuffer.append("   FROM mg_r_realsystem_agencytype");
		sqlBuffer.append("   WHERE agency_type='").append(agencyType).append("'");
		sqlBuffer.append("   )");
		return this.select(sqlBuffer.toString(), conn);
	}
	/**
	 * 取得ServletPath
	 * @param request
	 * @return
	 */
	private String getServletPath(HttpServletRequest request){
		String contextPath = request.getContextPath();
		String url = request.getRequestURL().toString();
		int startPos = url.indexOf(contextPath) + contextPath.length();
		int endPos = url.indexOf("?");
		String servletPath = null;
		if (endPos != -1) {
			servletPath = url.substring(startPos, endPos);
		} else {
			servletPath = url.substring(startPos);
		}
		return servletPath;
	}
	
	/**
	   * 执行查询
	   * @param sql
	   * @param conn
	   * @return
	   * @throws SQLException
	   */
	  public List select(String sql, Connection conn)
	    throws SQLException
	  {
	    log.info(sql);
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    ResultSetMetaData rsmd = rs.getMetaData();
	    int count = rsmd.getColumnCount();

	    String[] colNames = new String[count];
	    for (int icount = 1; icount <= count; ++icount) {
	      colNames[(icount - 1)] = rsmd.getColumnName(icount);
	    }

	    List result = new ArrayList();

	    Map map = null;
	    String value = null;
	    while (rs.next()) {
	      map = new HashMap();
	      for (int i = 0; i < colNames.length; ++i) {
	        value = rs.getString(colNames[i]);
	        value = (value == null) ? "" : value;
	        map.put(colNames[i], value);
	      }
	      result.add(map);
	    }
	    rs.close();
	    stmt.close();

	    return result;
	  }
	  /**
	   * 执行更新、插入和删除操作
	   * @param sql				：SQL语句
	   * @param conn			：数据库连接
	   * @return	
	   * @throws SQLException	：抛出异常
	   */
	  public int execute(String sql, Connection conn)
	    throws SQLException
	  {
	    log.info(sql);
	    Statement stmt = conn.createStatement();
	    int count = stmt.executeUpdate(sql);
	    stmt.close();
	    return count;
	  }
}
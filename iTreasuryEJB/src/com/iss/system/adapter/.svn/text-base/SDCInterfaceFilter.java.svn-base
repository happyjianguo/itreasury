package com.iss.system.adapter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.system.loginlog.bizlogic.LoginLogBean;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.SessionMng;
import com.toft.core2.transfer.util.TransferClientUtil;

public class SDCInterfaceFilter implements Filter {

	private Map info;

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
		com.iss.itreasury.util.SessionMng sessionMng = new com.iss.itreasury.util.SessionMng();
		String login = (String) info.get("loginName");
		String password = (String) info.get("loginPassword");
		String currencyId = (String) info.get("currencyId");
		String agencyId = (String)info.get("agencyId");
		if (currencyId == null) {
			currencyId = "1";
		}
		long m_lCurrencyID = Long.parseLong(currencyId);
		String clientIp = request.getRemoteHost();
		//登录
		sessionMng.login(login,Long.parseLong(agencyId),password, clientIp, "SDC");
		//添加登录日志
		LoginLogBean loginBean = new LoginLogBean();	
		loginBean.addLoginLog(sessionMng,Constant.SETT_USER,request.getRemoteHost(),Constant.SUCCESSFUL,"用户登录成功");	
		sessionMng.m_strLogin = login;
		sessionMng.m_lCurrencyID = m_lCurrencyID;
		request.getSession().setAttribute("sessionMng", sessionMng);
		request.getSession().setAttribute("SDCsessionId", identifer);
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
			SessionMng mng =null ;
			// 登录页面，消息展现页面不进行过滤器判断
			if ((reqURL.indexOf("noLogin.jsp") != -1) || (reqURL.indexOf("RemindTop.jsp") != -1) || (reqURL.indexOf("RemindBottom.jsp") != -1)||(reqURL.indexOf("iTreasury-configtool")!=-1)) {
				paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			} else {
				// 请求都是从SDC过来的。
				if (identifer != null && !identifer.equals("")) {
					// 判断SDC系统session是否失效
					info = (Map) TransferClientUtil.accessHessianTransferObject(sdc_transfer_service_url, identifer);
					String agencyId = (String)info.get("agencyId");
					String SDCAppName = request.getParameter("MGAppName");
					if(SDCAppName==null||SDCAppName.equals("")){
						SDCAppName = "toftmerge/finance/welcome.do";
					}else if(SDCAppName.equals("toftmerge")){
						SDCAppName = "toftmerge/finance/welcome.do";
						}
					
					Constant.SDCURL="http://"+request.getLocalAddr()+":"+request.getServerPort()+"/"+SDCAppName;
					if (info == null) {
						// 如果失效，返回登录页面
//						System.out.println("===========================获取SDC平台为空！");
						response.sendRedirect(request.getContextPath() + "/noLogin.jsp");
					} else {
//						System.out.println("==============" + request.getRequestURL() + "?" + request.getQueryString());

						String now_module_id = request.getParameter("module_id");
						mng = (SessionMng) request.getSession().getAttribute("sessionMng");
//						System.out.println("获取sessionMng是否为空？=" + mng);
						// 过滤器是否启用
						if (filter_enable) {
							boolean sessionIdIsChange = false;
							// 判断sessionMng是否存在, 如果存在就不需要过滤
							if (mng == null) {// 不存在的情况, 初始化session
								System.out.println("开始初始化session");
								initSDCInterface(request,info);
								mng = (SessionMng) request.getSession().getAttribute("sessionMng");
							} else { // 存在的情况校验session id
								// 是否一致(此情况可能是没有点击注销按钮，直接关闭页面，但是服务器中的session并没有失效)
								String now_sessionId = request.getParameter("SDCsessionId");
								String old_sessionId = (String) request.getSession().getAttribute("SDCsessionId");
								if (now_sessionId != null && !now_sessionId.equals(old_sessionId)) {
									// 不一致
									initSDCInterface(request,info);
									request.getSession().setAttribute("SDCsessionId", now_sessionId);
									sessionIdIsChange = true;
								}
								mng = (SessionMng) request.getSession().getAttribute("sessionMng");

								mng.initForSDC(Long.parseLong(now_module_id), mng.m_lCurrencyID,Long.parseLong(agencyId));

							}
							mng.isValidate = 1;
							if (sessionIdIsChange || null != now_module_id) {// 检查是否切换模块,
								// 切换模块后要重新初始化模块
								String old_module_id = (String) request.getSession().getAttribute("module_id");
								if (!now_module_id.equals(old_module_id)) { 
									// 不一致
									request.getSession().setAttribute("module_id", now_module_id);
									mng.initForSDC(Long.parseLong(now_module_id), mng.m_lCurrencyID,Long.parseLong(agencyId));
								}
							}

							// 更新币种
							String currencyId = (String) info.get("currencyId");
							boolean flag = isCurrencyModule(now_module_id);
							if (currencyId == null||!flag) {
								currencyId = "1";
							}
							long m_lCurrencyID = Long.parseLong(currencyId);
							if (m_lCurrencyID != mng.m_lCurrencyID) {
								mng.initForSDC(Long.parseLong(now_module_id), m_lCurrencyID,Long.parseLong(agencyId));
							}
							mng.m_lCurrencyID = m_lCurrencyID;
							mng.m_strCurrencySymbol = Constant.CurrencyType.getSymbol(mng.m_lCurrencyID);
						}
					}
				}
				paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isCurrencyModule(String moduleId){
		if(moduleId.endsWith("1")||moduleId.endsWith("2")||moduleId.endsWith("16")||moduleId.endsWith("18")||moduleId.endsWith("21")||moduleId.endsWith("1279620666726")){
			return true;
		}else{
			return false ;
		}
	}
	
	private static String sdc_transfer_service_url;
	private static boolean filter_enable;

	public void init(FilterConfig paramFilterConfig) throws ServletException {
		filter_enable = "true".equals(paramFilterConfig.getInitParameter("filter.enable"));

	}
}
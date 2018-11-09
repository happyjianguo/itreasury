package com.iss.itreasury.servlet;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.util.SessionMng;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.sysframe.pager.bizlogic.Suggest;
import com.iss.sysframe.pager.dao.FlexiGridDao;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;


public class MagnifierServlet extends HttpServlet
{
	private ServletConfig config;
	
	private SessionMng sessionMng = null;
	
	private SessionOB sessionOB = null;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		this.config = config;
		this.sessionMng = new SessionMng();
		//sessionMng.m_strCurrencySymbol = "￥";
		sessionMng.m_strCurrencySymbol = "";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		if(req.getSession().getAttribute("sessionMng") != null)
		{
			sessionOB = (SessionOB)req.getSession().getAttribute("sessionMng");
			//sessionMng.m_strCurrencySymbol = sessionOB.m_strCurrencySymbol;
			sessionMng.m_strCurrencySymbol = "";
		}
		queryData(req, res, sessionMng);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException
	{
		this.doPost(req, res);
	}

	public void queryData(HttpServletRequest req, HttpServletResponse res, SessionMng sessionMng) throws ServletException
	{
		try
		{
			List rowList = new ArrayList();
			FlexiGridDao fgDao = new FlexiGridDao();
			Suggest suggest = new Suggest();
			Map queryMap = this.getData(req);
			PagerInfo pagerInfo = suggest.doQuery(queryMap);	
			if(pagerInfo.getSqlString() != null && !pagerInfo.getSqlString().equals(""))
			{
				rowList = fgDao.getResultList(pagerInfo,queryMap);
			}else
			{
				throw new Exception("获取查询语句失败!");
			}
			long page = Integer.parseInt((String)queryMap.get("page"));
			ResultPagerInfo resultPagerinfo = new ResultPagerInfo();
			resultPagerinfo.setPage(page);
			resultPagerinfo.setTotal(fgDao.getCountNum());
			resultPagerinfo.setRows(rowList);		
			String strJSON = PagerTools.getJSONString(resultPagerinfo);
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out =res.getWriter();
			out.print(strJSON);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	public Map getData(HttpServletRequest req) throws ServletException
	{
		Map queryMap = new HashMap(); 
		try
		{

			String colModel = req.getParameter("colModel");
			String form = req.getParameter("form");
			String name = req.getParameter("name");
			String colModelType = req.getParameter("colModelType");
			String elementNames = req.getParameter("elementNames");
			String elements = req.getParameter("elements");
			String elementsType = req.getParameter("elementsType");
			String sql = req.getParameter("sql");	
			String colModelLink = req.getParameter("colModelLink");	
			String query = req.getParameter("query");	
			String qtype = req.getParameter("qtype");	
			String nextFocus = req.getParameter("nextFocus");	
			String callback = req.getParameter("callback");	
			
			String page = req.getParameter("page");// 得到当前页数,flexigrid的参数
			String order = req.getParameter("sortname");// 排序字段,flexigrid的参数
			String orderMark = req.getParameter("sortorder");// 排序方式,flexigrid的参数
			String lRp = req.getParameter("rp");// 得到每页显示行数,flexigrid的参数
			
			queryMap.put("colModel", this.translate(colModel));
			queryMap.put("form", this.translate(form));
			queryMap.put("name", this.translate(name));
			queryMap.put("colModelType", this.translate(colModelType));
			queryMap.put("elementNames", this.translate(elementNames));
			queryMap.put("elements", this.translate(elements));
			queryMap.put("elementsType", this.translate(elementsType));
			queryMap.put("sql", this.translate(sql));			
			queryMap.put("colModelLink", this.translate(colModelLink));			
			queryMap.put("query", this.translate(query));			
			queryMap.put("qtype", this.translate(qtype));			
			queryMap.put("nextFocus", this.translate(nextFocus));			
			queryMap.put("callback", this.translate(callback));	
			
			queryMap.put("page", this.translate(page));		
			queryMap.put("order", this.translate(order));		
			queryMap.put("orderMark", this.translate(orderMark));		
			queryMap.put("lRp", this.translate(lRp));		
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new ServletException("获取数据失败!");
		}
		return queryMap;
	}
	
	public String translate(String data) throws UnsupportedEncodingException
	{
		String newData = "";
		newData = URLDecoder.decode(data, "UTF-8");
		return newData;
	}
}
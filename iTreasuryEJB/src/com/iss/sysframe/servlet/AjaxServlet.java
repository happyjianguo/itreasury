package com.iss.sysframe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.log4j.Logger;

import com.iss.itreasury.util.SessionMng;
import com.iss.sysframe.excel.bizlogic.ExcelBiz;
import com.iss.sysframe.excel.dataentity.ExcelFlexigridInfo;
import com.iss.sysframe.pager.bizlogic.Suggest;
import com.iss.sysframe.pager.dao.FlexiGridDao;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerSort;
import com.iss.sysframe.pager.tools.PagerTools;

public class AjaxServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7987212091490138533L;

	private Logger logger = Logger.getLogger(AjaxServlet.class);
	
	static final private String strClassMethod = "strClassMethod";
	static final private String strAutoJson = "strAutoJson";
	static final private String strParseXml = "strParseXml";
	static final private String strUsePager = "strUsePager";
	static final private String strIsEncode = "strIsEncode";
	static final private String strIsPrint = "strIsPrint";
	static final private String strIsExcel = "strIsExcel";
	static final private String isDraftDetil = "isDraftDetil";
	static final private String isExportDetail = "isExportDetail";
	static final private String istag = "istag";
	static final private String isBackExportDetail = "isBackExportDetail";
	static final private String isdraftcode = "isdraftcode";
	static final private String isdisplayDate = "isdisplayDate";
	
	String autoJson = null;
	String parseXml = null;
	String usePager = null;
	String isEncode = null;
	String isPrint = null;
	String isExcel = null;
	String DraftDetil = null;
	String ExportDetail =null;
	String tag =null;
	String backExprot =null;
	String draftcode =null;
	String displayDate =null;
	
	/**
	 * Constructor of the object.
	 */
	public AjaxServlet()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] classMethods = null;
		//String message = "";
		
		// 将request里的所有内容转化为map
		Map queryMap = new HashMap();
		Map initMap = new HashMap();
		
		Map parameterMap = null;
		String _name = null;
		Object _objValue = null;
		String[] _strValue = null;
		
		try
		{
			classMethods = request.getParameterValues(strClassMethod);// 类方法路径
			autoJson = request.getParameter(strAutoJson);
			parseXml = request.getParameter(strParseXml);
			usePager = request.getParameter(strUsePager);
			isEncode = request.getParameter(strIsEncode);
			isPrint = request.getParameter(strIsPrint);
			isExcel = request.getParameter(strIsExcel);
			DraftDetil = request.getParameter(isDraftDetil);
			ExportDetail=request.getParameter(isExportDetail);
			
			tag=request.getParameter(istag);
			backExprot =request.getParameter(isBackExportDetail);
			draftcode=request.getParameter(isdraftcode);
			displayDate=request.getParameter(isdisplayDate);
		}
		catch(Exception e)
		{
			logger.debug("接收到传入的字串符解析错误");
			e.printStackTrace();
			return;
		}
		
		
		try
		{
			parameterMap = request.getParameterMap();
			Iterator iter = parameterMap.keySet().iterator();
			
			while (iter.hasNext())
			{
				_name = (String) iter.next();
				_objValue = parameterMap.get(_name);
				
				if(_objValue.getClass().getName().equals(String[].class.getName()))
				{
					_strValue = (String[])_objValue;
					
					if(_strValue.length > 1)
					{
						if(isEncode != null && isEncode.equalsIgnoreCase("true"))
						{
							for(int i = 0; i < _strValue.length; i ++)
							{
								_strValue[i] = URLDecoder.decode(_strValue[i], "UTF-8");
							}
						}
						queryMap.put(_name.toLowerCase(), _strValue);
					}
					else
					{
						if(isEncode != null && isEncode.equalsIgnoreCase("true"))
						{
							queryMap.put(_name.toLowerCase(), URLDecoder.decode(_strValue[0], "UTF-8"));
						}
						else
						{
							queryMap.put(_name.toLowerCase(), _strValue[0]);
						}
					}
				}
				else
				{
					logger.debug("接收到传入的非正常数据类型");
					logger.debug(_objValue.getClass().getName());
				}
			}
		}
		catch(Exception e)
		{
			logger.debug("接收到传入的字串符解析错误");
			e.printStackTrace();
			return;
		}

		//如果存在提交处理函数
		if(classMethods!=null && classMethods.length>0)
		{
			//执行提交处理函数
			try {
				executeClassMethods(request, response, queryMap, initMap, classMethods);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.responseWriter(response,"error:"+e.getMessage());
			}
		}
	}
	
	/**
	 * 执行提交、成功、失败处理方法
	 * 该处理方法将request需要设置的值已经置入
	 * @param request
	 * @param queryMap
	 * @param classMethods
	 * @return
	 * @throws Exception 
	 */
	private void executeClassMethods(HttpServletRequest request, HttpServletResponse response, Map queryMap, Map initMap, String[] classMethods) throws Exception
	{
		String methodName = null;
		Class actionClazz = null;
		Object actionObj = null;
		Class[] classList = null;
		Method execMethod = null;
		
		Object returnObj = null;
		
		if(classMethods[0].equals("")){
			
			try {
				queryData(request, response);//放大镜
			} catch (ServletException e) {
				e.printStackTrace();
			}
		
		}else{
				
			for(int i=0; i<classMethods.length; i++)
			{
				
				try {
					methodName = classMethods[i].substring(classMethods[i].lastIndexOf(".") + 1, classMethods[i].length());//取得方法名
					actionClazz = Class.forName(classMethods[i].substring(0, classMethods[i].lastIndexOf(".")));//得到被调用类的Class对象
					actionObj = actionClazz.newInstance();
				}
				catch(Exception e)
				{
					logger.debug("接收到传入的字串符解析错误");
					logger.debug("执行方法：" + classMethods[i]);
					//sessionMng.getM_actionMessages().addMessage("接收到传入的字串符解析错误");
					e.printStackTrace();
					throw e;
				}			
				try {
					
					classList = new Class[]{Map.class};
					execMethod = actionClazz.getMethod(methodName, classList); //得到被调用方法的method对象
					returnObj = execMethod.invoke(actionObj, new Object[]{queryMap}); //调用执行方法，得到结果
					
					/**
					 * 直接将LIST、INFO类等转化为JSON对象
					 */
					if (autoJson != null && autoJson.equalsIgnoreCase("true"))
					{
						//使用json返回串
						if(returnObj instanceof List)
						{
							this.responseWriter(response, JSONArray.fromObject(returnObj).toString());
						}
						
						//使用json返回串
						if(returnObj instanceof Map)
						{
							this.responseWriter(response, JSONArray.fromObject(returnObj).toString());
						}
						
						//使用json返回串
						if(returnObj instanceof String)
						{
							this.responseWriter(response, (String)returnObj);
						}
					} 
					
					/**
					 * 将XML解析成为JSON对象
					 */
					else if (parseXml != null &&  parseXml.equalsIgnoreCase("true"))
					{
						//将XML解析成为JSON对象
						if(returnObj instanceof String)
						{
							XMLSerializer serializer = new XMLSerializer();
							JSON json = serializer.read((String)returnObj);
							this.responseWriter(response, json.toString());
						}
					} 
					
					/**
					 * 进行分页查询
					 */
					else if (returnObj instanceof PagerInfo)
					{
						ResultPagerInfo resultPagerinfo = new ResultPagerInfo();
						PagerInfo pagerInfo = (PagerInfo)returnObj;
						List rowList = null;
						String order = request.getParameter("sortname");// 排序字段,flexigrid的参数
						String orderMark = request.getParameter("sortorder");// 排序方式,flexigrid的参数
						String pfFlag = "false";		//2010年3月9日	rczhan 追加判断（打印或导出）记录
						//2010年3月9日	rczhan 追加判断（打印或导出）全部记录	Start
						if((isPrint != null && isPrint.equalsIgnoreCase("true"))||
								(isExcel != null && isExcel.equalsIgnoreCase("true"))){
							pfFlag = "true";
						}
						//是否分页
						if(usePager != null && usePager.equalsIgnoreCase("true"))
						{
		
							long page = Integer.parseInt(request.getParameter("page"));// 得到当前页数,flexigrid的参数
							long lRp = Integer.parseInt(request.getParameter("rp"));// 得到每页显示行数,flexigrid的参数
							long startNum = (page-1)*lRp;// 计算查询开始数据下标
							long finishNum = startNum + lRp;
		
							//返回的是SQL语句
							if(pagerInfo.getSqlString() != null && !pagerInfo.getSqlString().equals(""))
							{
								FlexiGridDao fgDao = null;
								
								try
								{
									fgDao = new FlexiGridDao();
							
									if("all".equals(request.getParameter("pageChoose"))){
										rowList = fgDao.getAllResultList(pagerInfo.getSqlString(), order, orderMark, pagerInfo,pfFlag);
	
									}else{
										rowList = fgDao.getResultList(startNum, finishNum, pagerInfo.getSqlString(), order, orderMark, pagerInfo,pfFlag);
									}
									//2010年3月9日	rczhan 追加判断（打印或导出）全部记录 End 
									resultPagerinfo.setPage(page);
									resultPagerinfo.setTotal(fgDao.getCountNum());
									resultPagerinfo.setRows(rowList);
									resultPagerinfo.setUsertext(pagerInfo.getUsertext());
									resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
								}
								catch(Exception e)
								{
									logger.debug("处理分页时产生异常");
								    throw e;
								}
		
							}
							//返回的是结果集
							else if(pagerInfo.getResultInfo() != null)
							{
								//对List结果进行排序
								if("all".equals(request.getParameter("pageChoose"))){
									startNum = 0;
									PagerSort.sortAllPagerInfo(order, orderMark, pagerInfo);	
								}else{
									PagerSort.sortPagerInfo(startNum, finishNum, order, orderMark, pagerInfo);	
								}
								rowList = PagerTools.convertResultInfoToJSONList(pagerInfo,startNum,pfFlag);
								resultPagerinfo.setPage(page);
								resultPagerinfo.setTotal(pagerInfo.getResultInfo().getTotal());
								resultPagerinfo.setRows(rowList);
								resultPagerinfo.setUsertext(pagerInfo.getUsertext());
								resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
							}
							
						
						}	else{
							
							if(pagerInfo.getResultList()!=null){
								PagerSort.sortAllPagerInfo(order, orderMark, pagerInfo);	
						//		rowList = PagerTools.convertResultInfoToJSONList(pagerInfo,0,pfFlag);
								
								resultPagerinfo.setRows(rowList);
								resultPagerinfo.setUsertext(pagerInfo.getUsertext());
								resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
							}
							//返回的是SQL语句
							else if(pagerInfo.getSqlString()!= null && !pagerInfo.getSqlString().equals(""))
							{
								FlexiGridDao fgDao = null;
								
								try
								{
									fgDao = new FlexiGridDao();
								
										rowList = fgDao.getAllResultList(pagerInfo.getSqlString(), order, orderMark, pagerInfo,pfFlag);
									//2010年3月9日	rczhan 追加判断（打印或导出）全部记录 End 
									resultPagerinfo.setTotal(fgDao.getCountNum());
									resultPagerinfo.setRows(rowList);
									resultPagerinfo.setUsertext(pagerInfo.getUsertext());
									resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
								}
								catch(Exception e)
								{
									logger.debug("处理分页时产生异常");
									e.printStackTrace();
									return;
								}
		
							}
							//返回的是结果集
							else if(pagerInfo.getResultInfo() == null)
							{
								//
//								ResultPagerRowInfo info = new ResultPagerRowInfo();
//								info.setId("1");
//								List cell = new ArrayList();
//								cell.add('1');
//								cell.add('2');
//								cell.add('3');
//								cell.add('4');
//								cell.add('5');cell.add('5');
//								info.setCell(cell);
//								ArrayList res = new ArrayList();
//								res.add(info);
								//对List结果进行排序
								PagerSort.sortAllPagerInfo(order, orderMark, pagerInfo);	
								rowList = PagerTools.convertResultInfoToJSONList(pagerInfo,0,pfFlag);
								resultPagerinfo.setTotal(1);
								resultPagerinfo.setRows(rowList);
								resultPagerinfo.setUsertext(pagerInfo.getUsertext());
								resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
							}
							
						}
						
						if(isExcel != null && isExcel.equalsIgnoreCase("true"))
						{
							ExcelFlexigridInfo excelInfo = new ExcelFlexigridInfo();
							excelInfo.convertRequestToDataEntity(request);
							excelInfo.setTitles((String)queryMap.get("titles"));
							ExcelBiz excelBiz = new ExcelBiz();
							String strFileName = excelBiz.buildFlexigridToExcel(resultPagerinfo, excelInfo);
							this.responseWriter(response, strFileName);
						}
						else
						{
							 //使用json返回串
							JSONObject jsonObject = JSONObject.fromObject(resultPagerinfo);
							this.responseWriter(response, jsonObject.toString());
						}
					}
					/**
					 * 直接返回（toString）
					 */
					else
					{
						String strMessage = "";//sessionMng.getActionMessages().toString();
	
						if(strMessage != null && !strMessage.equals(""))
						{
							this.responseWriter(response, "$.Message.setData('','" + strMessage + "');");
						}
						else if(returnObj != null && returnObj instanceof String)
						{
							this.responseWriter(response, (String)returnObj);
						}
					}
				} 
				catch (InvocationTargetException e)
				{
					
					e.printStackTrace();
					this.responseWriter(response,e.getTargetException().getMessage());
				} 
				catch (Exception e)
				{
					logger.debug("处理AJAX请求时产生异常");
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	/**
	 * 通用的response writer方法
	 * @param response
	 * @param strInfo
	 */
	public void responseWriter(HttpServletResponse response, String strInfo)
	{
		Writer writer = null;
		try
		{
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control","no-cache, must-revalidate");
			
			writer = response.getWriter();
			logger.debug("AJAX请求：" + strInfo);
			
			writer.write(strInfo);
			writer.flush();
			writer.close();
			writer = null;
		}
		catch(Exception e)
		{
			logger.debug("response向客户端返回消息时异常");
			logger.debug("消息：" + strInfo);
			e.printStackTrace();
		}
		finally
		{
			if(writer != null)
			{
				try {
					writer.flush();
					writer.close();
					writer = null;
				} catch (IOException e) {
					logger.debug("response向客户端返回消息时异常");
					logger.debug("消息：" + strInfo);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException
	{
		super.init();
	}
	
	////////////
	public void queryData(HttpServletRequest req, HttpServletResponse res) throws ServletException
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
			
			String clazzs = req.getParameter("clazzs");	
			String methods = req.getParameter("methods");	
			String paramTypes = req.getParameter("paramTypes");	
			String paramValues = req.getParameter("paramValues");	
			
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
			
			queryMap.put("clazzs", this.translate(clazzs));	
			queryMap.put("methods", this.translate(methods));	
			queryMap.put("paramTypes", this.translate(paramTypes));	
			queryMap.put("paramValues", this.translate(paramValues));	

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

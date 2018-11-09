package com.iss.sysframe.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

import com.iss.sysframe.jasperPrint.bizlogic.JasperBiz;
import com.iss.sysframe.jasperPrint.dataentity.JasperFlexigridInfo;
import com.iss.sysframe.jasperPrint.util.JasperReportInfo;
import com.iss.sysframe.pager.dao.FlexiGridDao;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerInfo;
import com.iss.sysframe.pager.tools.PagerSort;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.SessionMng;

public class JasperPrintServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4208421318838046601L;

	private Logger logger = Logger.getLogger(JasperPrintServlet.class);
	
	static final private String strClassMethod = "strClassMethod";		//提交处理方法
	static final private String strFailPageURL = "strFailPageURL";		//失败后续处理页面
	static final private String strIsSuccess = "strIsSuccess";			//处理结果成功与否标志
	static final private String strJumpManner = "strJumpManner";		//跳转方式标志
	private SessionMng sessionMng = null;
	
	/**
	 * Constructor of the object.
	 */
	public JasperPrintServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");
		
		String classMethods = null;//提交处理方法路径
		String failPageURL = null;//失败页面
		String jumpManner = null;//跳转方式
		try
		{
			classMethods = request.getParameter(strClassMethod);//提交处理方法路径
			failPageURL = "/JasperPrintError.jsp";//失败页面
			jumpManner = request.getParameter(strJumpManner);//跳转方式
		}
		catch(Exception e)
		{
			logger.debug("接收到传入的字串符解析错误");
			classMethods = null;
			e.printStackTrace();
		}
		
		//将request里的所有内容转化为map
		Map queryMap = new HashMap();
		Map initMap = new HashMap();

		Map parameterMap = null;
		String _name = null;
		Object _objValue = null;
		String[] _strValue = null;
		
		String nextPageURL = null;	//下个处理页面
		
		if(request.getSession().getAttribute("sessionMng") != null)
		{
			sessionMng = (SessionMng)request.getSession().getAttribute("sessionMng");
		}
		initMap.put("sessionMng", sessionMng);
		
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
						queryMap.put(_name.toLowerCase(), (String[])_objValue);
					}
					else
					{
						if(_name.indexOf("operateLog_") > -1){
							initMap.put(_name.substring("operateLog_".length(), _name.length()).toLowerCase(), _strValue[0]);
						}
						else{
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
			//sessionMng.getM_actionMessages().clear();
			//sessionMng.getM_actionMessages().addMessage("接收到传入的字串符解析错误");
			classMethods = null;
			e.printStackTrace();
		}
		
		//如果存在提交处理函数
		if(classMethods!=null && !classMethods.equals(""))
		{
			String methodName = null;
			Class actionClazz = null;
			Object actionObj = null;
			Class[] classList = null;
			Method execMethod = null;
			
			Object returnObj = null;
					
			try {
				methodName = classMethods.substring(classMethods.lastIndexOf(".") + 1, classMethods.length());//取得方法名
				actionClazz = Class.forName(classMethods.substring(0, classMethods.lastIndexOf(".")));//得到被调用类的Class对象
				actionObj = actionClazz.newInstance();
			}
			catch (Exception e)
			{
				logger.debug("调用Class产生异常");
				logger.debug("执行方法：" + classMethods);
				//sessionMng.getM_actionMessages().clear();
				//sessionMng.getM_actionMessages().addMessage("系统运行产生异常，请稍候再试！");
				e.printStackTrace();
			}
				
			Connection conn = null;
			
			try {
				classList = new Class[]{Map.class};
				execMethod = actionClazz.getMethod(methodName, classList); //得到被调用方法的method对象
				returnObj = execMethod.invoke(actionObj, new Object[]{queryMap}); //调用执行方法，得到结果]
				
				if(returnObj instanceof PagerInfo)
				{

					ResultPagerInfo resultPagerinfo = new ResultPagerInfo();
					PagerInfo pagerInfo = (PagerInfo)returnObj;
					List rowList = null;

					long page = Integer.parseInt(request.getParameter("page"));// 得到当前页数,flexigrid的参数
					String order = request.getParameter("sortname");// 排序字段,flexigrid的参数
					String orderMark = request.getParameter("sortorder");// 排序方式,flexigrid的参数
					long lRp = Integer.parseInt(request.getParameter("rp"));// 得到每页显示行数,flexigrid的参数
					long startNum = (page-1)*lRp;// 计算查询开始数据下标
					long finishNum = startNum + lRp;
					String pfFlag = "true";		//2010年3月9日	rczhan 追加判断（打印或导出）记录

					//返回的是SQL语句
					if(pagerInfo.getSqlString() != null && !pagerInfo.getSqlString().equals(""))
					{
						FlexiGridDao fgDao = null;
						
						try
						{
							fgDao = new FlexiGridDao();
							String usePager = request.getParameter("usepager");

							if("all".equals(request.getParameter("pageChoose"))||usePager.equalsIgnoreCase("false")){
								rowList = fgDao.getAllResultList(pagerInfo.getSqlString(), order, orderMark, pagerInfo, pfFlag);

							}else{
								rowList = fgDao.getResultList(startNum, finishNum, pagerInfo.getSqlString(), order, orderMark, pagerInfo, pfFlag);
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
							e.printStackTrace();
							return;
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
						
						rowList = PagerTools.convertResultInfoToJSONList(pagerInfo,startNum, pfFlag);
						resultPagerinfo.setPage(page);
						resultPagerinfo.setTotal(pagerInfo.getResultInfo().getTotal());
						resultPagerinfo.setRows(rowList);
						resultPagerinfo.setUsertext(pagerInfo.getUsertext());
						resultPagerinfo.setUserNoHtmlText(pagerInfo.getUserNoHtmlText());
					}
					
					JasperFlexigridInfo jasperInfo = new JasperFlexigridInfo();
					jasperInfo.convertRequestToDataEntity(request);
					jasperInfo.setTableTitle((String)queryMap.get("tabletitle"));
					
					jasperInfo.setTitles((String)queryMap.get("titles"));
					jasperInfo.setUsername(sessionMng.m_strUserName);
					JasperBiz jasperBiz = new JasperBiz();
					
					JasperReportInfo jInfo= jasperBiz.buildFlexigridToDynamicJasper(resultPagerinfo, jasperInfo);
					
					response.setContentType("text/html; charset=UTF-8");
					Map parameters = new HashMap();

					String jrxmlPath =jInfo.getOutFilePath()+jInfo.getReportName() + ".jrxml";
					String jasperPath = jInfo.getOutFilePath()+jInfo.getReportName() + ".jasper";
					
					//1.输出Jasper文件
					JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
					//2.输出
					File reportFile = new File(jasperPath);
					if (!reportFile.exists()){
						throw new JRRuntimeException("无法找到报表: " + jInfo.getReportName() + "的路径， 路径：" + jasperPath);
					}
					JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
					
					if(jInfo.getSubReportName()!=null){
						for (int j=0;j<jInfo.getSubReportName().length;j++){
							String jrxmlSubPath = jInfo.getOutFilePath()+jInfo.getSubReportName()[j]  + ".jrxml";
							String jasperSubPath =jInfo.getOutFilePath()+jInfo.getSubReportName()[j]  + ".jasper";
							
							//1.输出Jasper文件
							JasperCompileManager.compileReportToFile(jrxmlSubPath, jasperSubPath);
							
							File subReportFile = new File(jasperSubPath);
							if (!subReportFile.exists()){
								throw new JRRuntimeException("无法找到报表: " + jInfo.getSubReportName()[j] + "的路径， 路径：" + jasperPath);
							}
						}
					}
					
					JasperPrint jasperPrint = null;
					
					parameters.put("BaseDir", reportFile.getParentFile());
					if(jInfo.getParameters() != null && jInfo.getParameters().isEmpty() == false) {
						parameters.putAll(jInfo.getParameters());
					}
					
					if(jInfo.getReportType() == JasperReportInfo.ReportType.SQL_REPORT) {
						conn = Database.getConnection();
						jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
					}
					else {
						jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jInfo.getDataSource());
					}
								
					JRHtmlExporter exporter = new JRHtmlExporter();
					
					request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
					
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
					exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "JasperReportImage?image=");
					
					exporter.exportReport();
				}	
				
				return;
			}
			catch (InvocationTargetException e) 
			{
				logger.debug("执行Action方法时产生异常");
				logger.debug("执行方法：" + classMethods);
				//sessionMng.getM_actionMessages().clear();
				
				Throwable throwable = e.getTargetException();
				if(throwable instanceof Exception){
					//sessionMng.getM_actionMessages().addMessage(throwable.getMessage());
				}
				else{
					//sessionMng.getM_actionMessages().addMessage("系统运行产生异常，请稍候再试！");
				}
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				logger.debug("执行Action方法时产生异常");
				logger.debug("执行方法：" + classMethods);
				//sessionMng.getM_actionMessages().clear();
				//sessionMng.getM_actionMessages().addMessage("系统运行产生异常，请稍候再试！");
				e.printStackTrace();
			}
			finally
			{

				try {
					if(conn != null)
					{
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		request.setAttribute(strIsSuccess, "false"); //置入执行结果失败标志
		nextPageURL = failPageURL;//跳转失败页面
		
		//跳转到下一页
		logger.debug("next page : " + nextPageURL);
		if(jumpManner != null && jumpManner.equalsIgnoreCase("sendRedirect"))
		{
			response.sendRedirect(nextPageURL);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher(nextPageURL);
			rd.forward(request, response);
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
	
}

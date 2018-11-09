package com.iss.itreasury.safety.fingercert;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.SDCUtil;

public class FingerPrintCret {
	
	protected static final Log logger = LogFactory.getLog(FingerPrintCret.class);

	private String serverURL = "";
	public String fingerValue; // 指纹特征

	private String Check_Results="<?xml version=\"1.0\" encoding=\"GBK\"?>\n"		
		+ "<root><row>0</row></root>";// 验证指纹结果  //结果相对固定，不增加解析xml代码
	// 查询认证模式的URL地址  http://219.143.139.127:18080/viewLogin.action
	public static final String POST_URL_QUERY = "login!queryModeByID.action";
	// 验证指纹的URL地址            http://192.168.0.6:8080
	public static final String POST_URL_CHECK = "login!checkFingerByID.action";
	
	/**
	 * 实例方法
	 */
	static FingerPrintCret printCret=null;
	static public FingerPrintCret getFingerPrintCret(){
		if(printCret==null){
			printCret =new FingerPrintCret();
		}
		return printCret;
	}
	/**
	 * 是否需要指纹认证
	 * @throws ToftException
	 */
	public boolean isNeedFingerPrintCret(HttpServletRequest request) throws IException {
		//根据密码策略判断，将来可能改成
		//可能变动成根据用户判断，思考“策略模式”
		return SDCUtil.isNeedFingerPrint(request);
		
	}
	/**
	 * 验证指纹
	 * @param Ver  输入指纹值
	 * @param sys_id  机构id
	 * @param tlr_no  用户id
	 * @return
	 * @throws IOException
	 * @throws ToftException
	 */
	public String validateFingerPrint(String Ver, String sys_id, String tlr_no)
			throws  IException {

		// IOException {
		// 产生加密随机数randomNum
		Random random = new Random();
		int randNum = random.nextInt(1000);
		String randomNum = String.valueOf(randNum);// 加密随机数
		/*
		 * 查询员工认证模式 FpDemo demo = new FpDemo(tlr_no, sys_id, randomNum);
		 * Mode_Results = demo.queryMode(get_queryURL()); // 1--指纹 0--密码
		 * 没有这个用户 是 乱码 System.out.println("Mode_Results:" + Mode_Results);
		 */
		// 认证指纹】
		FpDemo fp = new FpDemo(tlr_no, sys_id, randomNum, Ver);
		String tempResult="";
		try{
			tempResult =fp.checkFinger(get_checkURL());// ：0--验证通过  非0--验证失败
		}
		catch(Exception e){
			logger.error("验证指纹访问服务器时出错.:url地址为："+get_checkURL(),e);
			throw new IException("访问指纹服务器时出错",e);
		}
		if(!tempResult.trim().equals(Check_Results)){
			logger.debug("验证指纹时出错 用户名为："+tlr_no+"机构为："+sys_id);
			throw new IException("fpiswrong");
		}else{
			System.out.println("指纹验证成功");
		}
		return "success";
	}
	/**
	 * 
	 * @param Ver		指纹值
	 * @param tlr_no 	 机构id
	 * @return
	 * @throws IOException
	 * @throws ToftException
	 */
	public String validateFingerPrint(String Ver, String tlr_no)
			throws  IException {
		String sys_id = "1";
		validateFingerPrint(Ver, sys_id, tlr_no);
		return "success";
	}
	/**
	 * 通过服务器ip，拼写，得到查询的的地址acion
	 * @return
	 */
	private String get_queryURL() {
		return getServerURL() + POST_URL_QUERY; 
	}
	/**
	 * 通过服务器ip，拼写，得到检查的地址action
	 * @return
	 */
	private String get_checkURL() {
		return getServerURL() + POST_URL_CHECK;
	}
	/**
	 * 得到当前系统配置的 指纹服务器url地址
	 * @return
	 */
	private String getServerURL() {
		// TODO 访问数据库得到性能比较差，每次都查询数据库
		//serverURL ="http://ncndc1fpi1.addom.xinaogroup.com/finger/";
		serverURL = Config.getProperty(ConfigConstant.GLOBAL_FINGERPRINT_SERVER_URL);
		return serverURL;
	}
	
	/**
	 * 校验指纹
	 * @param fingerPrint
	 * @throws ToftException
	 */
	public String checkFingerPrint(HttpServletRequest request,String fingerPrintValue,String userID) throws IException {
		boolean flag = this.isNeedFingerPrintCret(request);
		String res = null;
		// 如果指纹管理员开启了指纹认证，则进行指纹校验
		if (flag) {
			//1.指纹认证相关 start 
			//1.1查询密码策略，是否需要指纹认证

			//String fingerPrintValue = (String) getContext().getParam("Ver");
			//String userID =(String) getContext().getParam("userId");
			//TODO 1.2没有输入指纹的话，重新输入指纹
			if(StringUtils.isEmpty(fingerPrintValue)){
				throw new IException("needFPCert");//前端处理
			} 
			//1.3验证指纹方法	
			//1.4从数据库中获得机构编号
			String sys_id = Config.getProperty(ConfigConstant.GLOBAL_FINGERPRINT_SYS_ID);
			if(null==sys_id||"".equals(sys_id)){
				sys_id="1";
			}
			res = FingerPrintCret.getFingerPrintCret().validateFingerPrint(fingerPrintValue, sys_id, userID); 
			logger.debug("指纹验证成功"+"用户名:"+userID+"机构"+sys_id);
		
			//1.指纹认证end
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException, IException {
		String Ver = "FIWLGCwC0QAAElCbjAACghgDgAAVAwhAAAjQ6uAAAAwe+IAAKqQQqAAE5Il0AAAjEWIAABKKa3wAAh9VLAAAUmqiMAAJ1VgmAAE2LwiAACFF/oAAANbKTAAAnlqnIAARK4OAAAIwcHCAABEuEwAAAX3WxAABKLy1gAApZ80oAAEM/KsAAA0t";
		FingerPrintCret.getFingerPrintCret().validateFingerPrint(Ver, "1", "zhaoxiaoxue"); //验证指纹方法
	}
}

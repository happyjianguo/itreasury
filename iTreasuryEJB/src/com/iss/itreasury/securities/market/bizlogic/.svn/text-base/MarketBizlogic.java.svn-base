/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.bizlogic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.securities.market.dao.MarketDAO;
import com.iss.itreasury.securities.market.dataentity.MarketInfo;
import com.iss.itreasury.securities.market.dataentity.MarketConditionInfo;
import com.iss.itreasury.securities.market.exception.MarketException;
import com.iss.itreasury.securities.market.exception.MarketRuntimeException;
import com.jspsmart.upload.SmartUpload;
import com.iss.system.dao.PageLoader;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MarketBizlogic {
	private static String strFileServerRoot = Env.UPLOAD_PATH;
	public static final String SEC_UPLOAD_PATH = "securities/"; // 证券上传路径
	private MarketDAO marketDAO = null;

	/**
	 * 构造函数(不带参数)
	 *
	 * @param  nothing
	 * @return nothing
	 * @throws Exception
	 */
	public MarketBizlogic() throws Exception{
		marketDAO = new MarketDAO(Database.getConnection());
	}

	/**
	 * 构造函数(带参数)
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
	public MarketBizlogic(Connection conn) throws Exception{
		marketDAO = new MarketDAO(conn);
	}

	/**
	 * 析构函数(不带参数)
	 *
	 * @param  nothing
	 * @return nothing
	 * @throws Exception
	 */
	public void CloseMarketBizlogic() throws Exception{
		this.marketDAO.CloseMarketDAO();
	}

	/**
	 * 析构函数(带参数)
	 *
	 * @param  conn
	 * @return nothing
	 * @throws Exception
	 */
	public void CloseMarketBizlogic(Connection conn) throws Exception{
		this.marketDAO.CloseMarketDAO(conn);
	}

	/**
	 * 将txt文件上传到服务器，并将其中的行情数据导入到证券系统的数据库中
	 * 
	 * @return boolean true 导入成功，false 导入失败
	 * @throws Exception
	 */
	public boolean saveUploadFileToDataBase(javax.servlet.jsp.PageContext pagecontext, long lUserID) throws Exception {
		String strUpLoadPath = ""; // 文件上传到服务器路径

		// 变量的声明
		StringBuffer buffer = new StringBuffer();
		// Retreive the current file
		Log.print("Enter MarketBizlogic.saveUploadFileToDataBase()...");
		SmartUpload mySmartUpload = new SmartUpload();
		com.jspsmart.upload.File myFile = null;
		java.io.File file = null;
		try {
			mySmartUpload.initialize(pagecontext);
			Log.print("Start to upload file to server");
			mySmartUpload.upload();
			strUpLoadPath = Env.UPLOAD_PATH + SEC_UPLOAD_PATH;
			file = new java.io.File(strUpLoadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			Log.print("Prepare to send file to folder:" + strUpLoadPath);
			mySmartUpload.save(strUpLoadPath);
			Log.print("Successfully send file to folder:" + strUpLoadPath);
			myFile = mySmartUpload.getFiles().getFile(0);
			Log.print("The uploaded file name =" + myFile.getFileName());

			if (myFile == null || myFile.getSize() == 0) {
				throw new MarketRuntimeException("E039");
			}

			FileInputStream fis = new FileInputStream(strUpLoadPath + myFile.getFileName());
			InputStream is = new BufferedInputStream(fis, 1024);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String s;
			String str;
			String tmpStr = "";
			int index;
			int len;
			int tmp;
			int right;
			Vector vctLineDatas = new Vector();

			do {
				s = br.readLine();
				if (s != null) {
					s.trim();
					s = s.substring(0, s.length());
					s = s.concat("#");
					buffer.append(s);
				}
			} while (s != null);
			br.close();
			str = buffer.toString();
			index = str.indexOf("#");
			while (index != -1 && index < str.length()) {
				tmpStr = str.substring(0, index).trim();
				str = str.substring(index + 1);
				index = str.indexOf("#");
				vctLineDatas.add(tmpStr);
			}
			Log.print("Read the uploaded file for lines = " + vctLineDatas.size());
			//
			//if (Env.getProjectName().equals(Constant.ProjectName.CNMEF)) {
			//	this.marketDAO.addForCnmef(vctLineDatas, lUserID);
			//} else {
				this.marketDAO.add(vctLineDatas, lUserID);
			//}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (myFile != null) {
					myFile = null;
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return true;
	}

	/**
	 * 删除当天本次股票导入的所有债券行情数据！！！！
	 * 
	 * @return void
	 * @throws MarketException
	 */
	public void deleteBondMarketInfo() throws MarketException {
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = new StringBuffer();

		try {
			conn = Database.getConnection();
			sbSQL.append(" DELETE FROM SEC_SecuritiesMarket where   \n ");
			sbSQL.append(" CloseDate = (select max(CloseDate) from sec_securitiesMarket)");
			sbSQL.append(" and  securitiesCode in ( \n ");
			sbSQL.append("    SELECT  \n");
			sbSQL.append("        a.securitiesCode  \n");
			sbSQL.append("    FROM  \n");
			sbSQL.append("        SEC_SECURITIESMARKET a, \n");
			sbSQL.append("        (   SELECT DISTINCT securitiesCode1 FROM SEC_SECURITIES  \n");
			sbSQL.append("            WHERE TypeID IN (5, 6, 7, 8)  \n");
			sbSQL.append("        ) b  \n");
			sbSQL.append("    WHERE \n");
			sbSQL.append("        a.securitiesCode = b.securitiesCode1 \n");
			sbSQL.append("        AND a.StatusID = 1 \n");
			sbSQL.append("    ) \n");

			Log.print("SQL="+sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			int deleteSum = ps.executeUpdate();
			Log.print("删除了 [" + deleteSum + "]条债券记录!!!!");
		} catch (Exception e) {
			throw new MarketException(e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new MarketException(e.getMessage());
			}
		}
	}

	/**
	 * 实现从数据表中导出债券的相关信息及其净价和全价 如果交净价没有数值则不导出全价
	 * 
	 * @param strDate 收盘日期
	 * @throws Exception 异常
	 */
	public void exportBondPrice(String strDate) throws Exception {
		//Excel对象
		HSSFWorkbook wb = null; //工作薄
		HSSFSheet sheet = null; //表单
		HSSFRow row = null; //行
		HSSFCell cell = null; //单元格

		//字体
		HSSFFont fontTitle = null;
		HSSFFont fontColumn = null;

		//样式
		HSSFCellStyle styleTitle = null;
		HSSFCellStyle styleColumn = null;
		HSSFCellStyle styleLeft = null;
		HSSFCellStyle styleRight = null;
		HSSFCellStyle styleCenter = null;
		HSSFCellStyle styleDate = null;

		//初始化工作薄及表单
		wb = new HSSFWorkbook();
		sheet = wb.createSheet("Bond");

		//设置字体
		fontTitle = wb.createFont();
		fontTitle.setFontHeight((short) 400);
		fontColumn = wb.createFont();
		fontColumn.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		//取得工作薄样式
		styleTitle = wb.createCellStyle();
		styleColumn = wb.createCellStyle();
		styleLeft = wb.createCellStyle();
		styleRight = wb.createCellStyle();
		styleCenter = wb.createCellStyle();
		styleDate = wb.createCellStyle();

		//设置样式
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTitle.setFont(fontTitle);
		styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleColumn.setFont(fontColumn);
		styleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleDate.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		//zpli 2005-09-14 
		//TODO: 证券 待完善
		styleDate.setDataFormat(Short.valueOf("14").shortValue());
		//styleDate.setDataFormat(HSSFDataFormat.getFormat("m/d/yy"));

		//显示标题
		row = sheet.createRow((short) 0);
		row.setHeight((short) 440);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleTitle);
		HSSFRichTextString htr = new HSSFRichTextString("债券行情数据表");
		cell.setCellValue(htr);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 4));

		//显示栏位标题
		String[] column = { "证券编码", "证券名称", "收盘日", "净价收盘价", "全价收盘价" };
		row = sheet.createRow((short) 1);
		for (short i = 0; i < column.length; i++) {
			sheet.setColumnWidth(i, (short) 4000);
			cell = row.createCell(i);
			cell.setCellStyle(styleColumn);
			htr = new HSSFRichTextString(column[i]);
			cell.setCellValue(htr);
		}

		//从数据库中取得最后日期的债券行情数据
		ArrayList listResult = this.marketDAO.selectBond(strDate);
		MarketInfo marketInfo = null;

		//填充单元格数据
		if (listResult != null) {
			short rowNo;
			//每个实体对象对应Excel中的一行数据
			for (int i = 0; i < listResult.size(); i++) {
				marketInfo = (MarketInfo) listResult.get(i);
				rowNo = (short) (i + 2);
				row = sheet.createRow(rowNo);
				//每行单元格数据填充
				for (short j = 0; j < column.length; j++) {
					cell = row.createCell(j);
					
					cell.setCellStyle(styleCenter);
					switch (j) {
						case 0 :
							htr = new HSSFRichTextString(marketInfo.getSecuritiesCode());
							cell.setCellValue(htr);
							break;
						case 1 :
							htr = new HSSFRichTextString(marketInfo.getSecuritiesName());
							cell.setCellValue(htr);
							break;
						case 2 :
							
							cell.setCellStyle(styleDate);
							cell.setCellValue(marketInfo.getCloseDate());
							break;
						case 3 :
							cell.setCellStyle(styleRight);
							cell.setCellValue(marketInfo.getNetClosePrice());
							break;
						case 4 :
							cell.setCellStyle(styleRight);
							cell.setCellValue(marketInfo.getClosePrice());
							break;
						default :
							cell.setCellValue("");
					}
				}
			}
		}

		//输出Excel文件
		java.io.File file = new java.io.File(Env.UPLOAD_PATH + SEC_UPLOAD_PATH + "bond.xls");
		if (file.exists()) {
			file.delete();
		} else if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 实现将Excel文件中的债券净价和全价数据导入数据表中
	 * 
	 * @throws Exception 异常
	 */
	public void importBondPrice() throws Exception {
		//文件操作对象
		java.io.File file = null;
		FileInputStream fileIn = null;

		//Excel对象
		HSSFWorkbook wb = null; //工作薄
		HSSFSheet sheet = null; //表单
		HSSFRow row = null; //行
		HSSFCell cell = null; //单元格

		//数据库操作对象
		List listMarketInfo = null;
		MarketInfo marketInfo = null;

		//读文件
		file = new java.io.File(Env.UPLOAD_PATH + SEC_UPLOAD_PATH + "bond.xls");
		if (!file.exists()) { //指定的文件不存在
			new MarketException("E305");
		}
		fileIn = new FileInputStream(file);
		try {
			wb = new HSSFWorkbook(fileIn);
		} catch (java.io.IOException ioe) {
			throw new MarketException("E306");
		}

		//获取表单对象
		sheet = wb.getSheetAt(0);

		//取出单元格中的数据，每一行对应一个实体对象
		for (int i = 2; sheet.getRow(i) != null; i++) {
			row = sheet.getRow(i);
			marketInfo = new MarketInfo();
			cell = row.getCell((short) 0);
			marketInfo.setSecuritiesCode(cell.getStringCellValue().trim());
			//证券代码为空
			if ((marketInfo.getSecuritiesCode() == null) || (marketInfo.getSecuritiesCode().length() == 0)) {
				throw new MarketException("E301");
			}
			cell = row.getCell((short) 1);
			marketInfo.setSecuritiesName(cell.getStringCellValue().trim());
			//证券名称为空
			if ((marketInfo.getSecuritiesName() == null) || (marketInfo.getSecuritiesName().length() == 0)) {
				throw new MarketException("E302");
			}
			//日期格式不正确
			try {
				cell = row.getCell((short) 2);
				marketInfo.setCloseDate(new Timestamp(cell.getDateCellValue().getTime()));
			} catch (Exception e) {
				throw new MarketException("E303");
			}
			//收盘价格格式不正确
			try {
				cell = row.getCell((short) 3);
				marketInfo.setNetClosePrice(cell.getNumericCellValue());
				cell = row.getCell((short) 4);
				marketInfo.setClosePrice(cell.getNumericCellValue());
			} catch (Exception e) {
				throw new MarketException("E304");
			}
			if (listMarketInfo == null) {
				listMarketInfo = new ArrayList();
			}
			listMarketInfo.add(marketInfo);
		}

		//根据导入文件内容修改数据库相应行情数据
		this.marketDAO.updateBondPrice(listMarketInfo);
	}

	/**
	 * 查询行情数据
	 * 
	 * @return PageLoader 返回满足条件的行情数据集合
	 * @throws Exception
	 */
	public PageLoader select(MarketConditionInfo marketConditionInfo) throws Exception {
		return this.marketDAO.select(marketConditionInfo);
	}

    /**
     * Returns the orderBy.
     * @return StringBuffer
     */
    public StringBuffer getOrderBy() throws Exception {
        return this.marketDAO.getOrderBy();
    }

    /**
     * Sets the orderBy.
     * @param orderBy The orderBy to set
     */
    public void setOrderBy(MarketConditionInfo marketConditionInfo) throws Exception {
    	this.marketDAO.setOrderBy(marketConditionInfo);
    }

	/**
	 * 查询某一行情数据的详细信息
	 * 
	 * @return MarketInfo 返回该行情数据详细信息
	 * @throws Exception
	 */
	public MarketInfo selectDetail(MarketInfo objConditionInfo) throws Exception {
		return this.marketDAO.selectDetail(objConditionInfo);
	}

	/**
	 * 修改行情数据
	 * 
	 * @param  marketInfo
	 * @return int
	 * @throws Exception
	 */
	public int update(MarketInfo marketInfo) throws Exception {
		return this.marketDAO.update(marketInfo);
	}

	/**
	 * 修改债券信息
	 *
	 * @param listMarketInfo 债券信息
	 * @exception throws Exception
	 */
	public void updateBondMarket(List listMarketInfo) throws Exception {
		this.marketDAO.updateNetPrice(listMarketInfo);
	}

	/**
	 * 保存开放式基金行情数据
	 * 
	 * @param  MarketInfo marketInfo
	 * @return int
	 * @throws Exception
	 */
	public int saveFundMarket(MarketInfo marketInfo) throws Exception {
		return this.marketDAO.saveFundMarket(marketInfo);
	}

	/**
	 * 查询债券信息
	 * 
	 * @param strDate 收盘日期
	 * @return List
	 * @throws Exception 异常
	 */
	public List selectBondMarket(String strDate) throws Exception {
		return this.marketDAO.selectBond(strDate);
	}

	/**
	 * 将xls文件上传到服务器，并将其中的债券数据导入到证券系统的数据库中
	 *
	 * @param  javax.servlet.jsp.PageContext pagecontext,long lUserID
	 * @return boolean true 导入成功，false 导入失败
	 * @throws throws Exception
	 */
	public boolean saveBondFileToDataBase(javax.servlet.jsp.PageContext pagecontext,long lUserID) throws Exception {
		String strUpLoadPath = ""; // 文件上传到服务器路径

		// 变量的声明
		StringBuffer buffer = new StringBuffer();
		// Retreive the current file
		SmartUpload mySmartUpload = new SmartUpload();
		com.jspsmart.upload.File myFile = null;
		// 文件操作对象
		java.io.File file = null;
		FileInputStream fileIn = null;
		try {
			mySmartUpload.initialize(pagecontext);
			mySmartUpload.upload();
			strUpLoadPath = Env.UPLOAD_PATH + SEC_UPLOAD_PATH;
			file = new java.io.File(strUpLoadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			Log.print("Prepare to send file to folder:" + strUpLoadPath);
			mySmartUpload.save(strUpLoadPath);
			Log.print("Successfully send file to folder:" + strUpLoadPath);
			myFile = mySmartUpload.getFiles().getFile(0);
			Log.print("The uploaded file name =" + myFile.getFileName());

			if (myFile == null || myFile.getSize() == 0) {
				throw new MarketRuntimeException("E039");
			}

            // Excel对象
			HSSFWorkbook wb = null; //工作薄
			HSSFSheet sheet = null; //表单
			HSSFRow row = null; //行
			HSSFCell cell = null; //单元格

			// 数据库操作对象
			List listMarketInfo = null;
			MarketInfo objMarketInfo = null;
			fileIn =new FileInputStream(strUpLoadPath + myFile.getFileName());
			try {
			  wb = new HSSFWorkbook(fileIn);
			} catch (java.io.IOException ioe) {
				  throw new MarketException("E306");
			}

			// 获取表单对象
			sheet = wb.getSheetAt(0);
			// 取日期
			// 第一行，第二个cell
			row = sheet.getRow(0);
			Timestamp closeDate = null;
			try {
			cell = row.getCell((short)1);
			closeDate = new Timestamp(cell.getDateCellValue().getTime());
			} catch (Exception e) {
				throw new MarketException("E303");
			}
            // 删除该收盘日期的已经导入的数据
            // 根据导入文件内容修改数据库相应行情数据
			this.marketDAO.deleteMarketInfo(closeDate,2);

			// 取出单元格中的数据，每一行对应一个实体对象
			for (int i = 2; sheet.getRow(i) != null; i++) {
				row = sheet.getRow(i);
				objMarketInfo = new MarketInfo();
				cell = row.getCell((short)1);
				objMarketInfo.setSecuritiesCode(cell.getStringCellValue().trim());
				// 证券代码为空
				if ((objMarketInfo.getSecuritiesCode() == null) || (objMarketInfo.getSecuritiesCode().length() == 0)) {
					break;
				}
				cell = row.getCell((short)2);
				objMarketInfo.setSecuritiesName(cell.getStringCellValue().trim());
				// 证券名称为空
				if ((objMarketInfo.getSecuritiesName() == null) || (objMarketInfo.getSecuritiesName().length() == 0)) {
					throw new MarketException("E302");
				}
				// 日期格式不正确
				objMarketInfo.setCloseDate(closeDate);
				// 收盘价格格式不正确
				try {
					cell = row.getCell((short)3);
					objMarketInfo.setNetClosePrice(cell.getNumericCellValue());
					cell = row.getCell((short)4);
					objMarketInfo.setClosePrice(cell.getNumericCellValue());
				} catch (Exception e) {
					throw new MarketException("E304");
				}	
				objMarketInfo.setInputUserID(lUserID);
				if (listMarketInfo == null) {
					listMarketInfo = new ArrayList();
				}
				listMarketInfo.add(objMarketInfo);
			}
			// 以批处理的方式进行数据库表：Sec_securitiesMarket的insert
			this.marketDAO.saveBondMarket(listMarketInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (myFile != null) {
					myFile = null;
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}

		return true;
	}
	
	/**
		 * 将xls文件上传到服务器，并将开放式基金行情数据导入到证券系统的数据库中
		 * @author hjliu
		 * @param  javax.servlet.jsp.PageContext pagecontext,long lUserID
		 * @return boolean true 导入成功，false 导入失败
		 * @throws throws Exception
		 * 
		 */
		public boolean saveOpenFundFileToDataBase(javax.servlet.jsp.PageContext pagecontext,long lUserID) throws Exception {
			String strUpLoadPath = ""; // 文件上传到服务器路径

			// 变量的声明
			StringBuffer buffer = new StringBuffer();
			// Retreive the current file
			SmartUpload mySmartUpload = new SmartUpload();
			com.jspsmart.upload.File myFile = null;
			// 文件操作对象
			java.io.File file = null;
			FileInputStream fileIn = null;
			try {
				mySmartUpload.initialize(pagecontext);
				mySmartUpload.upload();
				strUpLoadPath = Env.UPLOAD_PATH + SEC_UPLOAD_PATH;
				file = new java.io.File(strUpLoadPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				Log.print("Prepare to send file to folder:" + strUpLoadPath);
				mySmartUpload.save(strUpLoadPath);
				Log.print("Successfully send file to folder:" + strUpLoadPath);
				myFile = mySmartUpload.getFiles().getFile(0);
				Log.print("The uploaded file name =" + myFile.getFileName());

				if (myFile == null || myFile.getSize() == 0) {
					throw new MarketRuntimeException("E039");
				}

				// Excel对象
				HSSFWorkbook wb = null; //工作薄
				HSSFSheet sheet = null; //表单
				HSSFRow row = null; //行
				HSSFCell cell = null; //单元格

				// 数据库操作对象
				List listMarketInfo = null;
				MarketInfo objMarketInfo = null;
				fileIn =new FileInputStream(strUpLoadPath + myFile.getFileName());
				try {
				  wb = new HSSFWorkbook(fileIn);
				} catch (java.io.IOException ioe) {
					  throw new MarketException("E306");
				}

				// 获取表单对象
				sheet = wb.getSheetAt(0);
				// 取日期
				// 第一行，第二个cell
				row = sheet.getRow(0);
				Timestamp closeDate = null;
				try {
				cell = row.getCell((short)1);
				closeDate = new Timestamp(cell.getDateCellValue().getTime());
				} catch (Exception e) {
					throw new MarketException("E303");
				}
				// 删除该收盘日期的已经导入的数据
				// 根据导入文件内容修改数据库相应行情数据
				this.marketDAO.deleteMarketInfo(closeDate,SECConstant.SecuritiesType.MUTUAL_FUND);

				// 取出单元格中的数据，每一行对应一个实体对象
				for (int i = 2; sheet.getRow(i) != null; i++) {
					row = sheet.getRow(i);
					objMarketInfo = new MarketInfo();
					//基金管理公司ID
					cell = row.getCell((short)0);
					
					if(cell.getStringCellValue() == null || (cell.getStringCellValue() != null && cell.getStringCellValue().toString().trim().equals(""))){
						//Log.print("第一列不是null，但是是['']!!!");	
							break;
					}
					//Log.print("ID = "+cell.getStringCellValue().trim());
					objMarketInfo.setCounterpartID(Long.parseLong(cell.getStringCellValue().trim()));
					
					//Log.print("基金公司ID = "+Long.parseLong(cell.getStringCellValue().trim()));
					cell = row.getCell((short)1);
					//Log.print("code = "+cell.getStringCellValue().trim());
					objMarketInfo.setSecuritiesCode(cell.getStringCellValue().trim());
					// 证券代码为空
					if ((objMarketInfo.getSecuritiesCode() == null) || (objMarketInfo.getSecuritiesCode().length() == 0)) {
						throw new MarketException("E301");
					}
					//Log.print("基金代码 = "+cell.getStringCellValue().trim());
					cell = row.getCell((short)2);
					objMarketInfo.setSecuritiesName(cell.getStringCellValue().trim());
					// 证券名称为空
					if ((objMarketInfo.getSecuritiesName() == null) || (objMarketInfo.getSecuritiesName().length() == 0)) {
						throw new MarketException("E302");
					}
					//Log.print("基金名称 = "+cell.getStringCellValue().trim());
					// 日期格式不正确
					objMarketInfo.setCloseDate(closeDate);
					// 收盘价格格式不正确
					try {
						cell = row.getCell((short)4);
						objMarketInfo.setNetClosePrice(cell.getNumericCellValue());						
					} catch (Exception e) {
						throw new MarketException("E304");
					}
					//Log.print("基金净价 = "+cell.getNumericCellValue());
					objMarketInfo.setInputUserID(lUserID);
					if (listMarketInfo == null) {
						listMarketInfo = new ArrayList();
					}
					listMarketInfo.add(objMarketInfo);
				}
				// 以批处理的方式进行数据库表：Sec_securitiesMarket的insert
				this.marketDAO.saveOpenFundMarket(listMarketInfo);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				try {
					if (myFile != null) {
						myFile = null;
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}

			return true;
		}
}
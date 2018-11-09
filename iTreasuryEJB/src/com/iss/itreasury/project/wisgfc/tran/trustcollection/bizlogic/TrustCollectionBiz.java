package com.iss.itreasury.project.wisgfc.tran.trustcollection.bizlogic;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.UpLoanReturnInfo;
import com.iss.itreasury.project.wisgfc.loan.settcontract.util.NameRef;
import com.iss.itreasury.project.wisgfc.tran.trustcollection.dao.TrustCollectionDao;
import com.iss.itreasury.project.wisgfc.tran.trustcollection.dataentity.TrustCollectionentity;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.SessionMng;
import com.iss.system.dao.PageLoader;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;

public class TrustCollectionBiz {
	public Map importExcel(SmartUpload mySmartUpload, String uploadPath,
			SessionMng sessionMng) throws Exception {

		Vector vector = new Vector();
		Map map = new HashMap();
		Vector returnVector = new Vector();
		String strAdd = "";// 每次从上传文件中读到的一个单元。
		short index = 0;
		boolean bIsValid = false;
		File myfile = mySmartUpload.getFiles().getFile(0);
		System.out.println(uploadPath);
		System.out.println(uploadPath + myfile.getFileName());
		myfile.saveAs(uploadPath + myfile.getFileName());
		Connection conn = null;
		FileInputStream fis = new FileInputStream(Env.UPLOAD_PATH+myfile.getFileName());
		System.out.println(Env.UPLOAD_PATH+myfile.getFileName());
		try {
			// Workbook[] wbs = new Workbook[] { new HSSFWorkbook(fis)};
			// for(int i =0; i<wbs.length;i++){
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			String strPrefix = sdf1.format(new Date());
			String Num = NameRef.getMaxNumForexportTrustCollection(strPrefix);
			if (workbook != null) {
				sheet = workbook.getSheetAt(0);
			}
			if (sheet == null) {
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
				returnVector.addElement(upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(true);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("不能导入空的Excel文件！");
			}
			if (sheet != null) {
				row = sheet.getRow(1);
			}
			for (int j = 1; row != null; j++, row = sheet.getRow(j)) {
				bIsValid = true;
				TrustCollectionentity trustCollectionentity = new TrustCollectionentity();
				// 1.合同编号 2.账号 3.记账日期 5.摘要 6 托收金额
				long subTypeId = -1;
				for (index = 1; index <= 5; index++) {
					strAdd = "";
					cell = row.getCell((short) (index - 1));
					if (cell != null) {
						// 在Excel中的类型是文本、常规
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							strAdd = cell.getStringCellValue();
						}
						// 在Excel中的类型是数值
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							strAdd = String.valueOf(cell.getNumericCellValue());
						}
						System.out.println(strAdd);
					}
					if (strAdd == null) {
						strAdd = "";
					}
					if (index == 1) {
						DecimalFormat  df = new DecimalFormat("##############################");
						Double d = Double.valueOf(strAdd);
						strAdd = df.format(d);
						if (strAdd.matches("^\\s*\\w{1,50}\\s*$")) {
							trustCollectionentity.setContractNO(strAdd);
						} else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("合同号格式不正确,应为1-50个字符");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if (index == 2) {
						if (strAdd.matches("^\\s*(\\d+(-)?)*\\d+\\s*$")) {
							trustCollectionentity.setAccountNO(strAdd);
						} else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("账户号格式不正确");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					if (index == 3) {
						if (strAdd
								.matches("^\\s*((((19|20)\\d{2})-(0?[13-9]|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))\\s*$")) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date d = sdf.parse(strAdd);
							Timestamp accountingDate = new Timestamp(d.getTime());
							trustCollectionentity.setAccountingDate(accountingDate);
						} else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("记账日期格式不正确");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
					
					if (index == 4) {
							trustCollectionentity.setSabstract(strAdd);
					}
					if (index == 5) {
						if (strAdd
								.matches("^\\s*([1-9]\\d*|0)(\\.(\\d){1,2})?\\s*$")) {
							trustCollectionentity.setCollectionAmount(Double.valueOf(
									strAdd.replaceAll(",", "")).doubleValue());
						} else {
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setReason("金额格式不正确");
							returnVector.addElement(upLoanReturnInfo);
						}
					}
				}
				trustCollectionentity.setBatchEntity(strPrefix+Num);
				vector.add(trustCollectionentity);
			}
			// 判断格式是否正确 如果正确 则进行数据库操作
			// 数据库操作

			if (returnVector.size() == 0) {
				int length = vector.size();
				TrustCollectionentity[] infos = new TrustCollectionentity[length];
				if (length > 0) {
					for (int i = 0; i < length; i++) {
						TrustCollectionentity info = (TrustCollectionentity) vector
								.get(i);
						infos[i] = info;
					}
					//插入操作
				}
				//删除已经插入的操作
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				TrustCollectionDao TrustCollectionDao = new TrustCollectionDao(conn);
				TrustCollectionDao.importCollectionFromExcel(infos);
			} else {
				map.put("returnVector", returnVector);
			}
		} finally {
			fis.close();
			if (conn != null) {
				conn.close();
			}
		}
		return map;
	}
	
	//查询记录 按批次号过滤
	public PageLoader queryTrustCollection(long batchEntity) throws Exception{
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		return TrustCollectionDao.queryTrustCollection(batchEntity);
	}
	public PageLoader queryTrustCollection() throws Exception{
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		return TrustCollectionDao.queryTrustCollection();
	}
	
	public void delete(long id) throws ITreasuryDAOException{
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		TrustCollectionDao.deletePhysically(id);
	}
	
	public void save(TrustCollectionentity trustCollectionentity) throws ITreasuryDAOException{
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		TrustCollectionDao.update(trustCollectionentity);
	}
	
	public long deleteTrustCollection(long batchEntity) throws Exception{
		long lReturn = -1;
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		lReturn = TrustCollectionDao.deleteTrustCollection (batchEntity);
		return lReturn;
	}
	
	public Map getCountInfo(long batchEntity) throws Exception{
		Map lReturn = null;
		TrustCollectionDao TrustCollectionDao = new TrustCollectionDao();
		lReturn = TrustCollectionDao.getCountInfo (batchEntity);
		return lReturn;
	}
}

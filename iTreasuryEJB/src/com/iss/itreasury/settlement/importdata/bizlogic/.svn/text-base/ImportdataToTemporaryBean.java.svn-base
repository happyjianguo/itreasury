/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.importdata.bizlogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.importdata.dao.ImportdataDAO;
import com.iss.itreasury.settlement.importdata.dataentity.ImportdataInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author yinghu 将数据导入到临时表 TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class ImportdataToTemporaryBean {

    /**
     * 读历史交易生成结果集
     * 
     * @param fileUrl
     * @return
     * @throws Exception
     * @throws Exception
     * @throws Exception
     * @throws Exception
     */
    public long readHistoryTransData(String fileUrl, String fileType,
            char devideChar) throws Exception {
        Vector vResult = new Vector();
        String sRecorder = "";//存储单条记录
        String[] sArray = new String[30];//存储分解以后的记录集
        ImportdataInfo ib1 = null;
        BufferedReader in = null;
        long pos = 0;//当前记录位置

        Connection conn = null;
        try {
            in = new BufferedReader(new FileReader(fileUrl));

            conn = Database.getConnection();
            conn.setAutoCommit(false);
            // TODO Auto-generated catch block

            while ((sRecorder = in.readLine()) != null) {
                if (sRecorder.compareTo("") == 0
                        || sRecorder.substring(0, 1).compareTo(" ") == 0)
                    continue;//如果碰到空行，跳过
                pos++;
                if (fileType.equalsIgnoreCase("FROM DOWNLOAD") || fileType.equalsIgnoreCase("FROM DATABASE")) {
                	sRecorder = sRecorder.replaceAll("	","|") + "|";
                }
            	System.out.println("==========================sRecorder="+sRecorder);
                sArray = devideString(sRecorder, devideChar);
                System.out.println("==========================sArray[29]="+sArray[29]);
                if (fileType.equalsIgnoreCase("FROM DOWNLOAD")) {
                    if (Integer.parseInt(sArray[29]) != 20)
                        throw new Exception("记录字段数目不匹配<br>错误记录： 源文件中第" + pos + "行 "
                                + sRecorder);
                    try {
                        ib1 = packForDataFromDownload(sArray);
                    } catch (Exception e) {
                        throw new Exception("某字段数据有错误<br>错误记录：  源文件中第" + pos + "行 "
                                + sRecorder);
                    }
                } else if (fileType.equalsIgnoreCase("FROM INTERFACE")) {
                    if (Integer.parseInt(sArray[29]) != 21)
                        throw new Exception("记录字段数目不匹配<br>错误记录：  源文件中第" + pos + "行 "
                                + sRecorder);
                    try {
                        ib1 = packForDataFromInterface(sArray);
                    } catch (Exception e) {
                        throw new Exception("某字段数据有错误<br>错误记录：  源文件中第" + pos + "行 "
                                + sRecorder);
                    }
                } else if (fileType.equalsIgnoreCase("FROM DATABASE")) {
                    if (Integer.parseInt(sArray[29]) != 23)
                        throw new Exception("记录字段数目不匹配<br>错误记录：  源文件中第" + pos + "行 "
                                + sRecorder);
                    try {
                        ib1 = packForDataFromDatabase(sArray);
                    } catch (Exception e) {
                        throw new Exception("某字段数据有错误<br>错误记录：  源文件中第" + pos + "行 "
                                + sRecorder);
                    }
                }
                saveDataToTempDatabase(ib1, conn);
            }
            conn.commit();
        } catch (IOException e) {
            throw new Exception("读取文件错误");
        } finally {
            in.close();
            conn.close();
        }
         return pos;
    }

    /**
     * 把从文件读上来数据打包成结果集 从接口得到的文件
     * 
     * @param v1
     * @return
     */
    private ImportdataInfo packForDataFromInterface(String[] sArray) {

        ImportdataInfo ib1 = new ImportdataInfo();
        ib1.setCurrentNo(sArray[0]);
        ib1.setAccoutDate(DataFormat.getDateTime(transferDate(sArray[1])));
        ib1.setUse(sArray[2]);
        ib1.setAmount(Double.parseDouble(commerCut(sArray[3])));
        //ib1.setCurrencyId(Long.parseLong(sArray[4]));
        ib1.setCurrencyId(1);
        ib1.setCreditAccountNo(sArray[5]);
        ib1.setCreditAccountName(sArray[6]);
        ib1.setDebitAccountNo(sArray[8]);
        ib1.setDebitAccountName(sArray[9]);
        ib1.setTransactionType(sArray[13]);
        ib1.setInputUserName(sArray[15]);
        ib1.setCheckUserName(sArray[16]);
        ib1.setInputDate(DataFormat.getDateTime(transferDate(sArray[18])));
        ib1.setCheckDate(DataFormat.getDateTime(transferDate(sArray[19])));
        ib1.setRemark(sArray[20]);
        ib1.setColumnNumer(Long.parseLong(sArray[29]));
        return ib1;
    }

    /**
     * 把从文件读上来数据打包成结果集 从下载得到的文件
     * 
     * @param v1
     * @return
     */
    private ImportdataInfo packForDataFromDownload(String[] sArray) {
        ImportdataInfo ib1 = new ImportdataInfo();
        ib1.setCurrentNo(sArray[0]);
        ib1.setAccoutDate(DataFormat.getDateTime(transferDate(sArray[1])));
        ib1.setUse(sArray[2]);
        ib1.setAmount(Double.parseDouble(commerCut(sArray[3])));
        //ib1.setCurrencyId(Long.parseLong(sArray[4]));
        ib1.setCurrencyId(1);
        ib1.setCreditAccountNo(sArray[5]);
        ib1.setCreditAccountName(sArray[6]);
        ib1.setDebitAccountNo(sArray[8]);
        ib1.setDebitAccountName(sArray[9]);
        ib1.setTransactionType(sArray[13]);
        ib1.setInputUserName(sArray[15]);
        ib1.setCheckUserName(sArray[16]);
        ib1.setInputDate(DataFormat.getDateTime(transferDate(sArray[18])));
        ib1.setCheckDate(DataFormat.getDateTime(transferDate(sArray[19])));
        ib1.setRemark("");
        ib1.setColumnNumer(Long.parseLong(sArray[29]));

        return ib1;
    }

    /**
     * 把从文件读上来数据打包成结果集 从数据库得到的文件
     * 
     * @param ib1
     * @param sign
     * @return
     * @throws Exception
     */
    private ImportdataInfo packForDataFromDatabase(String[] sArray) {
        ImportdataInfo ib1 = new ImportdataInfo();
        ib1.setCurrentNo(sArray[0]);
        ib1.setInputDate(DataFormat.getDateTime(sArray[1]));
        ib1.setCheckDate(DataFormat.getDateTime(sArray[2]));
        ib1.setRemark(sArray[3]);
        ib1.setAccoutDate(DataFormat.getDateTime(sArray[4]));
        ib1.setCreditAccountNo(sArray[8]);
        ib1.setDebitAccountNo(sArray[10]);
        ib1.setDebitAccountName(sArray[11]);
        ib1.setAmount(Double.parseDouble(commerCut(sArray[13])));
        //ib1.setCurrencyId(Long.parseLong(sArray[14]));
        ib1.setCurrencyId(1);
        ib1.setUse(sArray[15]);
        ib1.setInputUserName(sArray[16]);
        ib1.setCheckUserName(sArray[17]);
        ib1.setTransactionType("");
        ib1.setColumnNumer(Long.parseLong(sArray[29]));
        return ib1;
    }

    /**
     * 日期转换，转换成标准格式
     * 
     * @param v1
     * @return
     */
    private String transferDate(String s1) {
        if (s1.equals("") || s1.equals(null))
            return "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == '-')
                return s1;
        }
        String sResult = "";
        sResult = s1.substring(0, 4) + "-" + s1.substring(4, 6) + "-"
                + s1.substring(6, 8);
        return sResult;
    }

    /**
     * 分解记录字串
     * 
     * @return
     * @throws IException
     */
    private String[] devideString(String s1, char devideChar) {
        String[] sResult = new String[30];
        int j = 0;
        int pos = 0;
        s1 = s1.replaceAll(" ","");//过滤空格
        for (int i = 0; i < s1.length(); i++) {//分割字串
            if (s1.charAt(i) == devideChar) {
                sResult[j] = s1.substring(pos, i);
                pos = i + 1;
                j++;
            }
        }
        sResult[29] = Integer.toString(j);
        return sResult;
    }

    /**
     * 清除金额中的逗号
     * 
     * @return
     * @throws IOException
     */
    private String commerCut(String s1) {
        String sResult = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != ',')
                sResult = sResult + s1.substring(i, i + 1);
        }
        return sResult;
    }

    /////////////////上面是读取文件部分///////////////////////
    /**
     * 将读取的数据集合写入临时表
     * 
     * @throws Exception
     * 
     * @throws Exception
     * 
     * @throws IOException
     */
    private void saveDataToTempDatabase(ImportdataInfo info1, Connection conn)
            throws Exception {

        //金额为0的交易数据状态应该被设置成不导入
        try {
            if(info1.getAmount()==0){
                info1.setRecordStatus(3);
            }
            else{
                info1.setRecordStatus(1);
            }
            System.out.println("=============step1");
            ImportdataDAO dao1 = new ImportdataDAO(conn);
            ImportdataInfo info2 = new ImportdataInfo();//查询用bean
            ImportdataInfo info3 = null;//查询结果
            Iterator it1 = null;
            info2.setCurrentNo(info1.getCurrentNo());
            it1 = dao1.findByCondition(info2).iterator();
            System.out.println("=============step2");
            while (it1 != null && it1.hasNext()) {
                info3 = (ImportdataInfo) it1.next();//根据info2传递的内容查询
            } 
            if (info3 == null) {
                dao1.add(info1);
            } else if (info3.getRecordStatus() == 5) {
                dao1.deletePhysically(info3.getId());//通过查到的info的id删除
                dao1.add(info1);
            }
            System.out.println("=============step3");
            // conn.commit();
        } catch (Exception e) {
            throw e;
        }
    }
 
    /**
     * 从临时表读取所有状态是未导入stausid=1的数据
     * @return
     * @throws Exception
     */
    public Collection getAllFromTempDatabase() throws Exception{
        ImportdataDAO dao1 = new ImportdataDAO();
        ImportdataInfo info1=new ImportdataInfo();//查询用info
        info1.setRecordStatus(1);
        Collection c=null;
        try {
            c=dao1.findByCondition(info1);
        } catch (ITreasuryDAOException e) {
            // TODO Auto-generated catch block
            throw new Exception("读取数据库过程中发生错误");
        }
        return c;
        
    }

    public static void main(String[] args)  {
        ImportdataInfo ii=null;
        try {
            Collection c=(new ImportdataToTemporaryBean()).getAllFromTempDatabase();
            Iterator it=c.iterator();
            System.out.println(c.size());
            while(it!=null && it.hasNext()){
                ii=(ImportdataInfo)it.next();
                (new ImportdataToTemporaryBean()).saveDataToTempDatabase(ii,Database.getConnection());
                System.out.println(ii.getCurrentNo());
                
                
                }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        System.out.println("ok");

    }
}
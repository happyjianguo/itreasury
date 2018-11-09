/*
 * Created on 2006-3-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dao;

import java.math.BigDecimal;

import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ClientInfo;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_ClientInfoDao extends DataTransplantBaseDao{
    /**
     * 产生新的code
     * @return
     */
    public String generateNewCode(){
        return ((BigDecimal)this.fieldGenerator.generateValue(this.strTableName,"code")).toString();
    }
    
    /**
     * 存入一条记录中母公司id的值
     * @param trfInfo
     */
    public void updateParentCorpIdForOneRecord(TRF_ClientInfo trfInfo){
        //先查询出要进行update的记录的id
        StringBuffer strSql1=new StringBuffer();
        strSql1.append("select Client_CorporationInfo.id from \n");
        strSql1.append("Client_ClientInfo,Client_CorporationInfo \n");
        strSql1.append("where Client_CorporationInfo.clientId=Client_ClientInfo.id \n");
        strSql1.append("and Client_ClientInfo.code='"+trfInfo.getCode()+"' \n");
        this.initDAO();
        this.prepareStatement(strSql1.toString());
        this.executeQuery();
        long recordId=-1;
        try{
	        if(this.resultSet.next()){
	            recordId=this.resultSet.getLong(1);
	        }
	        this.finalizeDAO();
	        //对相应的记录进行update
	        StringBuffer strSql2=new StringBuffer();
	        strSql2.append("update Client_CorporationInfo \n");
	        strSql2.append("set parentCorpId1="+NameRef.getClientIdByClientCode(trfInfo.getParentCorp1())+", \n" );
	        strSql2.append("parentCorpId2="+NameRef.getClientIdByClientCode(trfInfo.getParentCorp2())+" \n" );
	        strSql2.append("where id="+recordId+" \n");
	        this.initDAO();
	        this.prepareStatement(strSql2.toString());
	        this.preparedStatement.executeUpdate();
        }
        catch(Exception e){
            throw new TRF_Exception("error occurs where updating",e);
        }
        finally{
            this.finalizeDAO();
        }
    }
    
    /**
     * 产生新的一级级别码
     * @return
     */
    public String generateNewLevelOneCode(){
        StringBuffer strSql=new StringBuffer();
        strSql.append("select nvl(max(to_number(levelCode))+1,1) \n");
        strSql.append("from Client_clientInfo \n");
        strSql.append("where levelId=1 \n");
        this.initDAO();
        this.prepareStatement(strSql.toString());
        this.executeQuery();
        long maxCodeLong=0;
        try{
	        this.resultSet.next();
	        maxCodeLong=this.resultSet.getLong(1);
        }
        catch(Exception e){
            throw new TRF_Exception("error when creating new code",e);
        }
        finally{
            this.finalizeDAO();
        }
        return DataFormat.formatInt(maxCodeLong,5);
    }
    
    /**
     * 查找母公司级别码
     * @param clientId
     * @return
     */
    public String queryParentCorpLevelCode(long clientId){
        StringBuffer strSql=new StringBuffer();
        strSql.append("select client_ClientInfo.levelCode from \n");
        strSql.append("client_ClientInfo,Client_CorporationInfo \n");
        strSql.append("where 1=1 \n");
        strSql.append("and client_ClientInfo.id=Client_CorporationInfo.parentCorpId1 \n");
        strSql.append("and Client_CorporationInfo.clientId="+clientId+" \n");
        this.initDAO();
        this.prepareStatement(strSql.toString());
        this.executeQuery();
        String result="";
        try{
	        if(this.resultSet.next()){
	            result=this.resultSet.getString(1);
	        }
        }
        catch(Exception e){
            throw new TRF_Exception("error when querying errorCode",e);
        }
        finally{
            this.finalizeDAO();
        }
        return result;
    }
    
    /**
     * 产生一个母公司下对应的新的级别码
     * @param parentCorpId
     * @return
     */
    public String generateNewLevelCodeOfSpecifiedParentCorpId(long parentCorpId){
        StringBuffer strSql=new StringBuffer();
        strSql.append("select nvl(max(levelCode),'00000') \n");
        strSql.append("from Client_clientInfo,Client_CorporationInfo \n");
        strSql.append("where parentCorpId1="+parentCorpId+" \n");
        strSql.append("and Client_CorporationInfo.clientId=Client_clientInfo.id \n");
        this.initDAO();
        this.prepareStatement(strSql.toString());
        this.executeQuery();
        String maxCode="00000";
        try{
	        this.resultSet.next();
	        maxCode=this.resultSet.getString(1);
        }
        catch(Exception e){
            throw new TRF_Exception("error when creating new code",e);
        }
        finally{
            this.finalizeDAO();
        }
        String last5Chars=DataFormat.formatInt(Long.parseLong(maxCode.substring(maxCode.length()-5))+1,5);
        return last5Chars;
    }
    
}

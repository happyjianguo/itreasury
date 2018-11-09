package com.iss.itreasury.craftbrother.query.dao;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.craftbrother.query.dataentity.ContractbillInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.bizlogic.TransDiscountApplyBiz;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountContractContentDao;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class ContractbillDAO extends ITreasuryDAO{
	
	public TransDiscountContractInfo findByContract(long nloanid) throws java.rmi.RemoteException, LoanException{
		TransDiscountContractInfo info = new TransDiscountContractInfo();
	    ResultSet rs = null;
	    String strSQL = "";

	    try
	    {
	        initDAO();
	            
	        strSQL = " select * from  loan_contractform "
	            + " where nloanid=" + nloanid;
	            
	        Log.print(strSQL);
	        prepareStatement(strSQL);
	        rs = executeQuery();
	            
	        if (rs != null && rs.next())
	        {
	            //��������
	            info.setId(rs.getLong("id")); //���������ʶ
	            info.setLoanId(rs.getLong("nLoanId"));
	            info.setApplyCode(rs.getString("sApplyCode")); //����������
	            info.setContractCode(rs.getString("sContractCode")); //����������
	            info.setTypeId(rs.getLong("nTypeId"));//��������
	            info.setNSubtypeid(rs.getLong("NSUBTYPEID"));
	            info.setCurrencyId(rs.getLong("nCurrencyId"));//���ֱ�ʶ
	            info.setOfficeId(rs.getLong("nOfficeId"));//���´���ʶ
	            //���뵥λ
	            info.setBorrowClientId(rs.getLong("nBorrowClientID")); //���뵥λId
	            info.setExamineAmount(rs.getDouble("mExamineAmount")); //��׼���(����)
	            info.setCheckAmount(rs.getDouble("mCheckAmount")); //�˶������룩
	            info.setDiscountRate(rs.getDouble("mDiscountRate")); //�������ʣ����룩
	            info.setDiscountDate(rs.getTimestamp("DTDISCOUNTDATE"));//ת������
	            info.setDiscountTypeId(rs.getLong("NDISCOUNTTYPEID"));//ת��������
	            info.setRepurchaseTypeId(rs.getLong("NREPURCHASETYPEID"));//�ع�����
	            info.setInOrOut(rs.getLong("NINOROUT"));            
	            info.setStatusId(rs.getLong("nStatusID")); //��������״̬            
	            info.setLoanAmount(rs.getDouble("mLoanAmount")); //����������
	            info.setInputUserID(rs.getLong("nInputUserID"));
	            info.setInputDate(rs.getTimestamp("dtInputDate"));
	            info.setNextCheckUserId(rs.getLong("nNextCheckUserID")); //��һ������˱�ʾ
	            info.setNextCheckLevel(rs.getLong("nNextCheckLevel")); //��һ����˼���
	            info.setBankAcceptPO(rs.getLong("nBankAcceptPO"));
	            info.setBizAcceptPO(rs.getLong("nBizAcceptPO"));
	            info.setLoanReason(rs.getString("sLoanReason"));
	            info.setLoanPurpose(rs.getString("sLoanPurpose"));
	            info.setStartDate(rs.getTimestamp("dtStartDate"));//start��
	            info.setEndDate(rs.getTimestamp("dtEndDate"));//start��
	            try {
	            	TransDiscountContractContentDao ccDao = new TransDiscountContractContentDao();
					Collection contractContent = ccDao.getContractContentByContractId(info.getId());
					info.setContractContent(contractContent);
				} catch (LoanDAOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	            
	        finalizeDAO();
	            
	    }
	    catch (ITreasuryDAOException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    catch (SQLException e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	    return info;
	}
	
	public Collection findByID(TransDiscountContractQueryInfo cInfo) throws java.rmi.RemoteException, LoanException{
		Vector vList=null;
        ResultSet rs = null;
        StringBuffer strSQL=new StringBuffer();

        try
        {
            initDAO();
            
            strSQL.setLength(0);
            strSQL.append(" select a.nserialno Serialno , a.nacceptpotypeid Acceptpotypeid , a.scode Scode , a.mamount Amount , b.dtdiscountdate dtdiscountdate , a.dtend Dtend , b.dtrepurchasedate DtrepurchaseDate ,");
            strSQL.append(" b.mdiscountrate Discountrate , b.mcheckamount CheckAmout , b.ninorout Inorout , b.ndiscounttypeid Discounttypeid ,c.nstatusid queryStatusId, c.sloanreason Loanpurpose ,  ");
            strSQL.append(" c.scontractcode ContractCode , b.nrepurchaseterm rterm ,r.islocal local , r.adddays adddays , c.ninputuserid inputUserId ,c.nborrowclientid nborrowclientid ");
            strSQL.append(" from loan_discountcontractbill a,Rtransdiscountcontractbill r,cra_loanform b,loan_contractform c where b.id = c.nloanid and a.id = r.discountcontractbillid and c.id = r.transdiscountcontractid and a.nstatusid="+Constant.RecordStatus.VALID+" and c.ntypeid="+LOANConstant.LoanType.ZTX+"");
            if(cInfo.getOfficeId()> 0){
            	strSQL.append(" and b.nofficeid ="+cInfo.getOfficeId());
            }
            if(cInfo.getCurrencyId()> 0){
            	strSQL.append(" and b.ncurrencyid ="+cInfo.getCurrencyId());
            }
            if(cInfo.getStartTransDiscountContractId()>0){
            	strSQL.append(" and c.id >= "+cInfo.getStartTransDiscountContractId());
            }
            if(cInfo.getEndTransDiscountContractId()>0){
            	strSQL.append(" and c.id <= "+cInfo.getEndTransDiscountContractId());
            }
            if(cInfo.getStartSContractDate()!=null){
            	strSQL.append(" and to_char(c.dtstartdate,'yyyy-mm-dd') >= '"+DataFormat.getDateString(cInfo.getStartSContractDate())+"'");
            }
            if(cInfo.getStartEContractDate()!=null){
            	strSQL.append(" and to_char(c.dtstartdate,'yyyy-mm-dd') <= '"+DataFormat.getDateString(cInfo.getStartEContractDate())+"'");
            }
            if(cInfo.getEndSContractDate()!=null){
            	strSQL.append(" and to_char(c.dtenddate,'yyyy-mm-dd') >= '"+DataFormat.getDateString(cInfo.getEndSContractDate())+"'");
            }
            if(cInfo.getEndEContractDate()!=null){
            	strSQL.append(" and to_char(c.dtenddate,'yyyy-mm-dd') <= '"+DataFormat.getDateString(cInfo.getEndEContractDate())+"'");
            }            
            if(cInfo.getInputUserId()>0){
            	strSQL.append(" and c.ninputuserid = "+cInfo.getInputUserId()+" ");
            } 
            //���׶���
            if(cInfo.getClientId() > 0){
            	strSQL.append(" and c.nborrowclientid = "+cInfo.getClientId());
            }
            if(cInfo.getInnorout() > 0){
            	strSQL.append(" and b.ninorout = "+cInfo.getInnorout());
            }
            if(cInfo.getDiscounttype() > 0){
            	strSQL.append(" and b.ndiscounttypeid = "+cInfo.getDiscounttype());
            }
            if(cInfo.getQueryStatusId()!= -1){
            	strSQL.append(" and c.nstatusid = "+cInfo.getQueryStatusId());
            }
            
            /** ������
            strSQL.append(" union ");
            
            strSQL.append(" select a.nserialno Serialno , a.nacceptpotypeid Acceptpotypeid , a.scode Scode , a.mamount Amount , b.dtdiscountdate dtdiscountdate , a.dtend Dtend , r.repurchasedate  DtrepurchaseDate , ");
            strSQL.append(" b.mdiscountrate Discountrate , b.mcheckamount CheckAmout , b.ninorout Inorout , b.ndiscounttypeid Discounttypeid , c.sloanreason Loanpurpose , ");
            strSQL.append(" c.scontractcode ContractCode , r.repurchaseTerm rterm , r.isLocal local , r.adddays adddays , c.ninputuserid inputUserId ,c.nborrowclientid nborrowclientid ");
            strSQL.append(" from cra_transdiscountbill a,RTransDiscountApplyBill r,cra_loanform b , loan_contractform c  ");
            strSQL.append(" where a.nstatusid="+Constant.RecordStatus.VALID+"  and b.id=c.nloanid and a.id = r.discountcontractbillid and b.id = r.transdiscountapplyid and c.ntypeid="+LOANConstant.LoanType.ZTX+" and c.nstatusid>"+LOANConstant.ContractStatus.SAVE+" ");
            
            if(cInfo.getOfficeId()> 0){
            	strSQL.append(" and b.nofficeid ="+cInfo.getOfficeId());
            }
            if(cInfo.getCurrencyId()> 0){
            	strSQL.append(" and b.ncurrencyid ="+cInfo.getCurrencyId());
            }
            if(cInfo.getStartTransDiscountContractId()>0){
            	strSQL.append(" and c.id >= "+cInfo.getStartTransDiscountContractId());
            }
            if(cInfo.getEndTransDiscountContractId()>0){
            	strSQL.append(" and c.id <= "+cInfo.getEndTransDiscountContractId());
            }
            if(cInfo.getStartSContractDate()!=null){
            	strSQL.append(" and to_char(c.dtstartdate,'yyyy-mm-dd') >= '"+DataFormat.getDateString(cInfo.getStartSContractDate())+"'");
            }
            if(cInfo.getStartEContractDate()!=null){
            	strSQL.append(" and to_char(c.dtstartdate,'yyyy-mm-dd') <= '"+DataFormat.getDateString(cInfo.getStartEContractDate())+"'");
            }
            if(cInfo.getEndSContractDate()!=null){
            	strSQL.append(" and to_char(c.dtenddate,'yyyy-mm-dd') >= '"+DataFormat.getDateString(cInfo.getEndSContractDate())+"'");
            }
            if(cInfo.getEndEContractDate()!=null){
            	strSQL.append(" and to_char(c.dtenddate,'yyyy-mm-dd') <= '"+DataFormat.getDateString(cInfo.getEndEContractDate())+"'");
            }            
            if(cInfo.getInputUserId()>0){
            	strSQL.append(" and c.ninputuserid = "+cInfo.getInputUserId()+" ");
            } 
            //���׶���
            if(cInfo.getClientId() > 0){
            	strSQL.append(" and c.nborrowclientid = "+cInfo.getClientId());
            }
            */
            System.out.println("ת���ֲ�ѯSQL:"+strSQL.toString());
            
            Log.print(strSQL.toString());
            prepareStatement(strSQL.toString());
            
            rs = executeQuery();
            
            vList=new Vector();
            
            while(rs.next()){
            	ContractbillInfo info=new ContractbillInfo();
            	info.setSerialNo(rs.getLong("Serialno"));
            	info.setAcceptpotypeid(rs.getLong("Acceptpotypeid"));
            	info.setCode(rs.getString("Scode"));
            	info.setAmount(rs.getDouble("Amount"));
            	info.setDtcreate(rs.getTimestamp("dtdiscountdate"));
            	info.setDtend(rs.getTimestamp("Dtend"));
            	info.setTrepurchasedate(rs.getTimestamp("DtrepurchaseDate"));
            	info.setDiscountRate(rs.getDouble("Discountrate"));
            	info.setDiscountAmount(rs.getDouble("CheckAmout"));
            	info.setInOrout(rs.getLong("Inorout"));
            	info.setDiscountType(rs.getLong("Discounttypeid"));
            	info.setLoanpurpose(rs.getString("Loanpurpose"));
            	info.setContractcode(rs.getString("ContractCode"));
            	info.setRepurchaseTerm(rs.getLong("rterm"));
            	info.setIslocal(rs.getLong("local"));
            	info.setAdddays(rs.getLong("adddays"));
            	info.setInputUserid(rs.getLong("inputUserId"));
            	info.setNborrowclientid(rs.getLong("nborrowclientid"));
            	info.setQueryStatusId(rs.getLong("queryStatusId"));
            	vList.add(info);
            }
            //System.out.println("+++++++++++++backend++++++++++++:"+vList.size());
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        return vList.size()<=0 ? null : vList;
    }
	
	public static void calInOrOutBill(ContractbillInfo cInfo){
		int nDays=0;
        Timestamp tsEnd = null; //ת��������
        Timestamp tsDate=cInfo.getDtcreate();
        String strEnd = null;
        if(cInfo.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
        	 strEnd = cInfo.getTrepurchasedate().toString();
        }else{
        	 strEnd = cInfo.getDtend().toString();
        }
        double dAmount=cInfo.getAmount();
        double dRate=cInfo.getDiscountRate();
        double dAccrual = 0; //ת������Ϣ
        double dRealAmount = 0; //ʵ�����ֽ��
        tsEnd =
            new java.sql.Timestamp(
                new Integer(strEnd.substring(0, 4)).intValue() - 1900,
                new Integer(strEnd.substring(5, 7)).intValue() - 1,
                new Integer(strEnd.substring(8, 10)).intValue(),
                0,
                0,
                0,
                0);
		
		if(cInfo.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
			// nDays = (int) rs.getLong("rterm");//+ rs.getInt("AddDays"); //�ع�����        
			if(null!=cInfo.getTrepurchasedate()){
          	//nDays = (int) java.lang.Math.ceil((cInfo.getTrepurchasedate().getTime() 
				//update by gdlian 2012-01-09 bug11013 ��Ҫ��ع���ʵ��ת����������(Ʊ�ݵ�����-��ʼ��+�ڼ���+�Ǳ���)����
				nDays = (int) java.lang.Math.ceil((tsEnd.getTime()
                      - tsDate.getTime()) / 86400000) 
                      + (int) cInfo.getAdddays(); //���Ͻڼ������Ӽ�Ϣ����
			}
          	//�Ǳ��ؼ�����
          //	if (cInfo.getIslocal() == Constant.YesOrNo.NO)
          //        nDays = nDays + 3;
		}else{
				if(tsEnd!=null && tsDate!=null){
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() 
							- tsDate.getTime()) / 86400000) 
							+ (int)cInfo.getAdddays(); //���Ͻڼ������Ӽ�Ϣ����
				}
		}
			
		if (nDays >= 0){
			if(cInfo.getDiscountType() == LOANConstant.TransDiscountType.BUYBREAK)//�����
			{
				if (cInfo.getIslocal() == Constant.YesOrNo.NO)
                        nDays = nDays + 3;
             }
        
            dAccrual = dAmount * (dRate / 360) * 0.01 * nDays;
            
            dRealAmount = dAmount - dAccrual;
            //info.setDiscountAmount(dRealAmount);
        }
		cInfo.setAccrual(dAccrual);
		cInfo.setRepurchaseDays(nDays); // ʵ������
	}
	
	public Collection findCancelByID() throws java.rmi.RemoteException, LoanException{
		Vector vList=null;
        ResultSet rs = null;
        StringBuffer strSQL=new StringBuffer();

        try
        {
            initDAO();
            
            strSQL.setLength(0);
            strSQL.append(" select a.nserialno Serialno , a.nacceptpotypeid Acceptpotypeid , a.scode Scode , a.mamount Amount , b.dtdiscountdate dtdiscountdate , a.dtend Dtend , b.dtrepurchasedate DtrepurchaseDate ,");
            strSQL.append(" b.mdiscountrate Discountrate , b.mcheckamount CheckAmout , b.ninorout Inorout , b.ndiscounttypeid Discounttypeid , c.sloanreason Loanpurpose ,c.nstatusid queryStatusId ,  ");
            strSQL.append(" c.scontractcode ContractCode , b.nrepurchaseterm rterm ,r.islocal local , r.adddays adddays , c.ninputuserid inputUserId ,c.nborrowclientid nborrowclientid ");
            strSQL.append(" from loan_discountcontractbill a,Rtransdiscountcontractbill r,cra_loanform b,loan_contractform c where b.id = c.nloanid and a.id = r.discountcontractbillid and c.id = r.transdiscountcontractid and a.nstatusid="+Constant.RecordStatus.VALID+" and c.ntypeid="+LOANConstant.LoanType.ZTX+" and c.nstatusid = "+ LOANConstant.ContractStatus.CANCEL);
            
            
            System.out.println("ת���ֲ�ѯSQL:"+strSQL.toString());
            
            Log.print(strSQL.toString());
            prepareStatement(strSQL.toString());
            
            rs = executeQuery();
            
            vList=new Vector();
            
            while(rs.next()){
            	ContractbillInfo info=new ContractbillInfo();
            	info.setSerialNo(rs.getLong("Serialno"));
            	info.setAcceptpotypeid(rs.getLong("Acceptpotypeid"));
            	info.setCode(rs.getString("Scode"));
            	info.setAmount(rs.getDouble("Amount"));
            	info.setDtcreate(rs.getTimestamp("dtdiscountdate"));
            	info.setDtend(rs.getTimestamp("Dtend"));
            	info.setTrepurchasedate(rs.getTimestamp("DtrepurchaseDate"));
            	info.setDiscountRate(rs.getDouble("Discountrate"));
            	info.setDiscountAmount(rs.getDouble("CheckAmout"));
            	info.setInOrout(rs.getLong("Inorout"));
            	info.setDiscountType(rs.getLong("Discounttypeid"));
            	info.setLoanpurpose(rs.getString("Loanpurpose"));
            	info.setContractcode(rs.getString("ContractCode"));
            	info.setRepurchaseTerm(rs.getLong("rterm"));
            	info.setIslocal(rs.getLong("local"));
            	info.setAdddays(rs.getLong("adddays"));
            	info.setInputUserid(rs.getLong("inputUserId"));
            	info.setNborrowclientid(rs.getLong("nborrowclientid"));
            	info.setQueryStatusId(rs.getLong("queryStatusId"));
            	vList.add(info);
            }
            //System.out.println("+++++++++++++backend++++++++++++:"+vList.size());
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            
            finalizeDAO();
            
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        return vList.size()<=0 ? null : vList;
    }
	
}

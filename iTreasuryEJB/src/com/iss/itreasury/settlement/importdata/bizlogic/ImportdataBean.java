/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.importdata.bizlogic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.importdata.dao.ImportdataDAO;
import com.iss.itreasury.settlement.importdata.dataentity.ImportdataInfo;
import com.iss.itreasury.settlement.importdata.dataentity.ProcessInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.CloseConnectionTask;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportdataBean {
 
    private ProcessInfo infoSaver = new ProcessInfo();

    private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

    private int readCapacity = 50;//每次读取的记录容量，达到该容量就必须释放连接

    private String errorPath = "";
   
    /**
     * 设置总账类单体数据
     *  
     */
    private TransGeneralLedgerInfo setTotalBean(ImportdataInfo ib1, long sign)
            throws Exception {
        TransGeneralLedgerInfo b1 = new TransGeneralLedgerInfo();
        try {
            //   设置id
            b1.setOfficeID(1); //设置办事处编号id
            b1.setCurrencyID(1); //设置货币种类
            b1.setTransActionTypeID(sign);//设置交易种类

            long clientId = -1;//客户Id
            long accountId = -1;//账户Id
            long direction = -1;//借贷方向
            long dirOne = -1;//总账类型1的借贷方向
            long generalLedgerOne = -1;//总账类型1
            long dirTwo = -1;//总账类型2的借贷方向
            long generalLedgerTwo = -1;//总账类型2
            generalLedgerOne = NameRef
            .getGeneralledgerTypeByOldAccountNo(ib1
                    .getCreditAccountNo());
            generalLedgerTwo = NameRef
            .getGeneralledgerTypeByOldAccountNo(ib1
                    .getDebitAccountNo());
            if (generalLedgerOne < 0 && generalLedgerTwo > 0) {//收款方为活期账户
                direction = SETTConstant.DebitOrCredit.DEBIT;
                clientId = NameRef.getClientIdByOldAccountNo(ib1
                        .getCreditAccountNo());
                accountId = NameRef.getAccountIdByOldAccountNo(ib1
                        .getCreditAccountNo());
                dirOne = SETTConstant.DebitOrCredit.CREDIT;
                generalLedgerOne = generalLedgerTwo;
                generalLedgerTwo = -1;
            }else if (generalLedgerOne > 0 && generalLedgerTwo < 0) {//付款方为活期账户
                direction = SETTConstant.DebitOrCredit.CREDIT;
                clientId = NameRef.getClientIdByOldAccountNo(ib1
                        .getDebitAccountNo());
                accountId = NameRef.getAccountIdByOldAccountNo(ib1
                        .getDebitAccountNo());
                dirOne = SETTConstant.DebitOrCredit.DEBIT;
                //generalLedgerOne = generalLedgerOne;
            } else if (generalLedgerOne > 0 && generalLedgerTwo > 0)  {
                dirOne = SETTConstant.DebitOrCredit.DEBIT;
                //generalLedgerOne = generalLedgerOne;
                dirTwo = SETTConstant.DebitOrCredit.CREDIT;
                //generalLedgerTwo = generalLedgerTwo;
            } else if (generalLedgerOne <= 0 && generalLedgerTwo <= 0) {
                throw new Exception("总账类型有错误");
            }
            //设置客户Id
            b1.setClientID(clientId);
            //设置账户Id
            b1.setAccountID(accountId);
            b1.setDirection(direction); //设置借贷方向
            if (accountId > 0) {
                b1.setAmount(ib1.getAmount()); //设置金额
            }

            //设置总账类型1
            b1.setDirOne(dirOne); //设置借贷方向1
            b1.setAmountOne(ib1.getAmount()); //设置金额
            b1.setGeneralLedgerOne(generalLedgerOne);
            //设置总账类型2
            b1.setDirTwo(dirTwo); //设置借贷方向2
            if (generalLedgerTwo > 0) {
                b1.setAmountTwo(ib1.getAmount()); //设置金额
            }
            b1.setGeneralLedgerTwo(generalLedgerTwo);

            Timestamp accountDate = ib1.getAccoutDate();
            Timestamp checkDate = ib1.getCheckDate();
            Timestamp systemDate = Env.getSystemDate(b1.getOfficeID(),b1.getCurrencyID()); 
            //起息日<=开机日 把开机日期作为执行日
            //否则不做处理，把状态设置为导入失败
            if(!accountDate.after(systemDate)){
                b1.setExecuteDate(systemDate);
            }
            else{
                throw new Exception("起息日>开机日 不做处理");
            }
            //设置起息日
            b1.setInterestStartDate(accountDate);
 
            b1.setInputUserID(NameRef.getUserIdByUserName(ib1
                    .getInputUserName()));//设置录入人id
            b1.setInputDate(ib1.getInputDate()); //设置制单日期
            b1.setCheckUserID(NameRef.getUserIdByUserName(ib1
                    .getCheckUserName()));//设置复核人id
            //b1.setSignUserID(NameRef.getUserIdByUserName(ib1.getDirector()));//设置签认人id
            b1.setStatusID(1);//设置交易状态
            b1.setAbstract("网银流水号:" + ib1.getCurrentNo() + "\n" + "用途:"
                    + ib1.getUse() + "\n" + "备注:" + ib1.getRemark() + "\n"
                    + "历史交易类型:" + ib1.getTransactionType()); //设置摘要

        } catch (Exception e) {
            throw e;
        }
        return b1;
    }

    /**
     * 设置非总账类单体数据
     * 
     * @param sArray
     * @param sign
     * @return
     * @throws Exception
     */
    private TransCurrentDepositInfo setNotTotalBean(ImportdataInfo ib1,
            long sign) throws Exception {
        TransCurrentDepositInfo b1 = new TransCurrentDepositInfo();

        try {
            b1.setOfficeID(1); //设置办事处编号id
            b1.setCurrencyID(1); //设置货币种类

            b1.setTransactionTypeID(sign);//设置交易种类
            log.info("=================交易种类=" + b1.getTransactionTypeID());
            if (sign == SETTConstant.TransactionType.BANKRECEIVE
                    || sign == SETTConstant.TransactionType.BANKPAY
                    || sign == SETTConstant.TransactionType.INTERNALVIREMENT) {
                log.info("=================贷方账户号=" + ib1.getDebitAccountNo());
                b1.setReceiveClientID(NameRef.getClientIdByOldAccountNo(ib1
                        .getDebitAccountNo()));
                //log.info("=================收款客户id=" +
                // b1.getReceiveClientID());
                b1.setReceiveAccountID(NameRef.getAccountIdByOldAccountNo(ib1
                        .getDebitAccountNo()));
                log.info("=================收款账户id=" + b1.getReceiveAccountID());
                log.info("=================借方账户号=" + ib1.getCreditAccountNo());
                b1.setPayClientID(NameRef.getClientIdByOldAccountNo(ib1
                        .getCreditAccountNo()));
                //log.info("=================付款客户id=" + b1.getPayClientID());
                b1.setPayAccountID(NameRef.getAccountIdByOldAccountNo(ib1
                        .getCreditAccountNo()));
                log.info("=================付款账户id=" + b1.getPayAccountID());
            }
            Timestamp accountDate = ib1.getAccoutDate();
            Timestamp checkDate = ib1.getCheckDate();
            
            //设置银行账户id
            long bankId = -1;
            if (b1.getReceiveAccountID() < 0) {
                if (ib1.getDebitAccountNo().indexOf("VB") >= 0) {
                	bankId = NameRef.getBankAccountIdByEnterpriseName(ib1
                            .getDebitAccountNo());
                } else {
                	if (ib1.getCreditAccountNo().substring(12,13).equalsIgnoreCase("0")) {
                    	bankId = NameRef.getBankAccountIdByEnterpriseName("VB1100131020001731");//基建存款建行
                	} else {
                    	bankId = NameRef.getBankAccountIdByEnterpriseName("VB1100131020000014");//建行存款
                	}
                }
            } else if (b1.getPayAccountID() < 0) {
            	bankId = NameRef.getBankAccountIdByEnterpriseName(ib1
                        .getCreditAccountNo());
            }
        	b1.setBankID(bankId);
            log.info("=================银行账户id=" + b1.getBankID());
            b1.setAmount(ib1.getAmount()); //设置金额
            Timestamp systemDate = Env.getSystemDate(b1.getOfficeID(),b1.getCurrencyID()); 
            //起息日<=开机日 把开机日期作为执行日
            //否则不做处理，把状态设置为导入失败
            if(!accountDate.after(systemDate)){
                b1.setExecuteDate(systemDate);
            }
            else{
                throw new Exception("起息日>开机日 不做处理");
            }
            b1.setInterestStartDate(accountDate);
            b1.setInputDate(ib1.getInputDate()); //设置制单日期
            //设置录入人id
            b1.setInputUserID(NameRef.getUserIdByUserName(ib1
                    .getInputUserName()));
            //设置复核人id
            b1.setCheckUserID(NameRef.getUserIdByUserName(ib1
                    .getCheckUserName()));
            b1.setAbstractStr("网银流水号:" + ib1.getCurrentNo() + "\n" + "用途:"
                    + ib1.getUse() + "\n" + "备注:" + ib1.getRemark() + "\n"
                    + "历史交易类型:" + ib1.getTransactionType()); //设置摘要

            b1.setStatusID(1);//设置交易状态
        } catch (Exception e) {
            throw e;
        }
        return b1;
    }
    
    /**
     * 设置特殊交易类单体数据
     * @param cRecord
     * @param recordType
     * @return
     * @throws IOException
     */
    private TransSpecialOperationInfo setSpecialBean(ImportdataInfo ib1,
            long sign) throws Exception {
        TransSpecialOperationInfo b1 = new TransSpecialOperationInfo();

        try {
            b1.setNofficeid(1); //设置办事处编号id
            b1.setNcurrencyid(1); //设置货币种类

            b1.setNtransactiontypeid(sign);//设置交易种类
            b1.setNoperationtypeid(1001);//设置交易种类
            log.info("=================交易种类=" + b1.getNtransactiontypeid());

            log.info("=================贷方账户号=" + ib1.getDebitAccountNo());
            long receiveBankId = NameRef.getBankAccountIdByEnterpriseName(ib1
                    .getDebitAccountNo());
            long receiveGeneralledgerTypeId = -1;
            b1.setNreceivebankid(receiveBankId);
            if (receiveBankId < 0) {
            	receiveGeneralledgerTypeId = NameRef.getGeneralledgerTypeByOldAccountNo(ib1
                        .getDebitAccountNo());
            }
            b1.setNreceivegeneralledgertypeid(receiveGeneralledgerTypeId);
            b1.setNreceivedirection(SETTConstant.DebitOrCredit.CREDIT);
            b1.setMreceiveamount(ib1.getAmount());
            log.info("=================借方账户号=" + ib1.getCreditAccountNo());
            long payBankId = NameRef.getBankAccountIdByEnterpriseName(ib1
                    .getCreditAccountNo());
            long payGeneralledgerTypeId = -1;
            b1.setNpaybankid(payBankId);
            if (payBankId < 0) {
            	payGeneralledgerTypeId = NameRef.getGeneralledgerTypeByOldAccountNo(ib1
                    .getCreditAccountNo());
            }
            b1.setNpaygeneralledgertypeid(payGeneralledgerTypeId);
            b1.setNpaydirection(SETTConstant.DebitOrCredit.DEBIT);
            b1.setMpayamount(ib1.getAmount());

            Timestamp accountDate = ib1.getAccoutDate();
            Timestamp checkDate = ib1.getCheckDate();
            Timestamp systemDate = Env.getSystemDate(1,1); 
            //起息日<=开机日 把开机日期作为执行日
            //否则不做处理，把状态设置为导入失败
            if(!accountDate.after(systemDate)){
                b1.setDtexecute(systemDate);
            }
            else{
                throw new Exception("起息日>开机日 不做处理");
            }
            b1.setDtintereststart(accountDate);
            //设置录入人id
            b1.setNinputuserid(NameRef.getUserIdByUserName(ib1
                    .getInputUserName()));
            //设置复核人id
            b1.setNcheckuserid(NameRef.getUserIdByUserName(ib1
                    .getCheckUserName()));
            b1.setSabstract("网银流水号:" + ib1.getCurrentNo() + "\n" + "用途:"
                    + ib1.getUse() + "\n" + "备注:" + ib1.getRemark() + "\n"
                    + "历史交易类型:" + ib1.getTransactionType()); //设置摘要

            b1.setNstatusid(2);//设置交易状态
        } catch (Exception e) {
            throw e;
        }
        return b1;
    }    

    /**
     * 数据转换并且存储复核
     * 
     * @param
     * @return
     * @throws IOException
     * @throws IOException
     * @throws Exception
     * @throws Exception
     * @exception
     */
    public ProcessInfo transferAndSaveData(Collection cRecord, String recordType)
            throws IOException {
        ImportdataInfo ib1 = null;//存储分解以后的记录集
        ImportdataDAO dao1 = new ImportdataDAO();
        int count = 0;//存储记录条数的记数器
        infoSaver.reset();
        long sign1 = -1;//分支选择号
        StringBuffer sbMessage = new StringBuffer();
        infoSaver.setStartTime(Env.getSystemDateTime().getTime());//设置开始时间
        infoSaver.setTotalRecorder(cRecord.size());//设置总记录数量
        Iterator it1 = null;
        Iterator it2 = null;
        if (cRecord != null)
            it1 = cRecord.iterator();

        while (it1 != null && it1.hasNext()) {
            ib1 = (ImportdataInfo) it1.next();
            sign1 = (new TypeSearcher()).searchType(ib1.getCreditAccountNo(),
                    ib1.getDebitAccountNo(), recordType);//查出交易种类
            //如果是不导入的数据
            if (sign1 == -1) {
                infoSaver.setNotInput(infoSaver.getNotInput() + 1);
                try {
                    dao1.updateStatus(ib1.getId(), 3);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("回写临时表状态时出错--不导入的数据" + e.toString());
                    infoSaver.setErrInfo(v1);
                    continue;
                }
            }
            //如果是待定数据
         /*   if (sign1 == 9999) {
                infoSaver.setNotDecide(infoSaver.getNotDecide() + 1);//未决定的类型
                try {
                    dao1.updateStatus(ib1.getId(), 4);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("回写临时表状态时出错--类型待定的数据" + e.toString());
                    infoSaver.setErrInfo(v1);
                    continue;
                }
            } */
            //如果是非总账类
            if (sign1 == SETTConstant.TransactionType.BANKPAY

            || sign1 == SETTConstant.TransactionType.BANKRECEIVE

            || sign1 == SETTConstant.TransactionType.INTERNALVIREMENT) {
                TransCurrentDepositInfo b1 = null;
                try {
                    b1 = setNotTotalBean(ib1, sign1);
                } catch (Exception e) {
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1); 
                    v1.addElement("数据转换出错--非总账类"+e.toString());
                    infoSaver.setErrInfo(v1);
                    infoSaver.setTransError(infoSaver.getTransError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e1) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--非总账类" + e.toString());
                        infoSaver.setErrInfo(v2);
                    }
                    continue;
                }

                if (sign1 == SETTConstant.TransactionType.BANKPAY)
                    infoSaver.setBankPay1(infoSaver.getBankPay1() + 1);
                if (sign1 == SETTConstant.TransactionType.BANKRECEIVE)
                    infoSaver.setBankRecieve1(infoSaver.getBankRecieve1() + 1);
                if (sign1 == SETTConstant.TransactionType.INTERNALVIREMENT)
                    infoSaver.setInter1(infoSaver.getInter1() + 1);
                //////////数据转换结束 开始保存复核////////
                TransCurrentDepositDelegation transCurrentDepositDelegation;
                try {
                    transCurrentDepositDelegation = new TransCurrentDepositDelegation();

                    TransCurrentDepositAssembler transCurrentDepositAssembler = new TransCurrentDepositAssembler(
                            b1);
                    transCurrentDepositDelegation
                            .saveAndCheckAutomatically(transCurrentDepositAssembler);
                } catch (Exception e1) {
                    String sMessage = null;
            		if (e1 != null) {
            		    sMessage = IExceptionMessage.getExceptionMSG(e1);
            		}
            		sbMessage.append(ib1.getCurrentNo()+":"+sMessage+"\n");
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("复核保存过程出错:" + sMessage);
                    infoSaver.setErrInfo(v1);
                    infoSaver.setSaveError(infoSaver.getSaveError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e2) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--非总账类" + e2.toString());
                        infoSaver.setErrInfo(v2);
                    }

                    continue;
                }
                try {
                    dao1.updateStatus(ib1.getId(), 2);
                } catch (Exception e2) {
                    Vector v2 = infoSaver.getErrInfo();
                    v2.addElement(ib1);
                    v2.addElement("回写临时表状态时出错--非总账类" + e2.toString());
                    infoSaver.setErrInfo(v2);
                    // break;
                }
                if (sign1 == SETTConstant.TransactionType.BANKPAY)
                    infoSaver.setBankPay2(infoSaver.getBankPay2() + 1);
                if (sign1 == SETTConstant.TransactionType.BANKRECEIVE)
                    infoSaver.setBankRecieve2(infoSaver.getBankRecieve2() + 1);
                if (sign1 == SETTConstant.TransactionType.INTERNALVIREMENT)
                    infoSaver.setInter2(infoSaver.getInter2() + 1);

            }
            //如果是总账类
            if (sign1 == SETTConstant.TransactionType.GENERALLEDGER) {
                TransGeneralLedgerInfo b1 = null;
                try {
                    b1 = setTotalBean(ib1, sign1);
                } catch (Exception e) {
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("数据转换出错--总账类"+e.toString());
                    infoSaver.setTransError(infoSaver.getTransError() + 1);
                    infoSaver.setErrInfo(v1);
                    infoSaver.setSaveError(infoSaver.getSaveError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e1) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--总账类" + e1.toString());
                        infoSaver.setErrInfo(v2);
                    }
                    continue;
                }

                infoSaver.setGenaral1(infoSaver.getGenaral1() + 1);
                /////////////////数据转换结束 开始复核储存////////////
                TransGeneralLedgerDelegation transGeneralLedgerDelegation;
                try {
                    transGeneralLedgerDelegation = new TransGeneralLedgerDelegation();

                    long id = transGeneralLedgerDelegation.save(b1);
                    TransGeneralLedgerInfo transGeneralLedgerInfo = transGeneralLedgerDelegation
                            .findByID(id);
                    transGeneralLedgerDelegation.check(transGeneralLedgerInfo);
                } catch (Exception e1) {
                    String sMessage = null;
            		if (e1 != null) {
            		    sMessage = IExceptionMessage.getExceptionMSG(e1);
            		}
            		sbMessage.append(ib1.getCurrentNo()+":"+sMessage+"\n");
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("复核保存过程出错:" + sMessage);
                    infoSaver.setErrInfo(v1);
                    infoSaver.setSaveError(infoSaver.getSaveError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e2) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--总账类" + e2.toString());
                        infoSaver.setErrInfo(v2);
                        //   break;
                    }
                    continue;
                }
                try {
                    dao1.updateStatus(ib1.getId(), 2);
                } catch (Exception e2) {
                    Vector v2 = infoSaver.getErrInfo();
                    v2.addElement(ib1);
                    v2.addElement("回写临时表状态时出错--总账类" + e2.toString());
                    infoSaver.setErrInfo(v2);
                }
                infoSaver.setGenaral2(infoSaver.getGenaral2() + 1);

            }
            
            //如果是特殊交易
            if (sign1 == SETTConstant.TransactionType.SPECIALOPERATION) {
                TransSpecialOperationInfo b1 = null;
                try {
                    b1 = setSpecialBean(ib1, sign1);
                } catch (Exception e) {
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("数据转换出错--特殊类"+e.toString());
                    infoSaver.setTransError(infoSaver.getTransError() + 1);
                    infoSaver.setErrInfo(v1);
                    infoSaver.setSaveError(infoSaver.getSaveError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e1) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--特殊类" + e1.toString());
                        infoSaver.setErrInfo(v2);
                    }
                    continue;
                }

                infoSaver.setSpecial1(infoSaver.getSpecial1() + 1);
                /////////////////数据转换结束 开始复核储存////////////
                TransSpecialOperationDelegation transSpecialOperationDelegation;
                try {
                    transSpecialOperationDelegation = new TransSpecialOperationDelegation();
                    long id = transSpecialOperationDelegation.save(b1);
                    TransSpecialOperationInfo transSpecialOperationInfo = transSpecialOperationDelegation
                           .findDetailByID(id);
                    
                    transSpecialOperationDelegation.check(transSpecialOperationInfo);
                } catch (Exception e1) {
                    String sMessage = null;
            		if (e1 != null) {
            		    sMessage = IExceptionMessage.getExceptionMSG(e1);
            		}
            		sbMessage.append(ib1.getCurrentNo()+":"+sMessage+"\n");
                    Vector v1 = infoSaver.getErrInfo();
                    v1.addElement(ib1);
                    v1.addElement("复核保存过程出错:" + sMessage);
                    infoSaver.setErrInfo(v1);
                    infoSaver.setSaveError(infoSaver.getSaveError() + 1);
                    try {
                        dao1.updateStatus(ib1.getId(), 5);
                    } catch (Exception e2) {
                        Vector v2 = infoSaver.getErrInfo();
                        v2.addElement(ib1);
                        v2.addElement("回写临时表状态时出错--总账类" + e2.toString());
                        infoSaver.setErrInfo(v2);
                        //   break;
                    }
                    continue;
                }
                try {
                    dao1.updateStatus(ib1.getId(), 2);
                } catch (Exception e2) {
                    Vector v2 = infoSaver.getErrInfo();
                    v2.addElement(ib1);
                    v2.addElement("回写临时表状态时出错--总账类" + e2.toString());
                    infoSaver.setErrInfo(v2);
                }
                infoSaver.setSpecial2(infoSaver.getSpecial2() + 1);

            }
            ///////////各种类型处理结束
            count++;
            if (count % readCapacity == 0) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CloseConnectionTask closeConnectionTask = new CloseConnectionTask();
                closeConnectionTask.run();
            }

        }
        infoSaver.setEndTime(Env.getSystemDateTime().getTime());//设置结束时间
        infoSaver.saveToFile(Env.UPLOAD_PATH+errorPath);//保存各种信息
        return infoSaver;

    }
    
    /**
     * 手动回写临时表的状态 状态写为已复核
     * @param args
     * @throws Exception
     */
    public void setRecordstatusManually(Collection collection) throws Exception{
        Connection conn=null;
        Iterator iterator = null;
        ImportdataInfo importdatainfo=null;
        try {
            conn=Database.getConnection();
       
        conn.setAutoCommit(false);
        ImportdataDAO importdatadao=new ImportdataDAO(conn);
        if(collection!=null){
          iterator=collection.iterator();    
        }
        while(iterator!=null && iterator.hasNext()){
            importdatainfo=(ImportdataInfo)iterator.next();
            long id=importdatainfo.getId();
            importdatadao.updateStatus(id,2);
        }
        conn.commit();
        } catch (Exception e) {
           e.printStackTrace();throw new Exception("发生数据库错误");
        }
    }

    public static void main(String[] args) {
        String str = "VB1100132123900000";
        System.out.println("str.substring(12,13)="+str.substring(12,13));
    }

}
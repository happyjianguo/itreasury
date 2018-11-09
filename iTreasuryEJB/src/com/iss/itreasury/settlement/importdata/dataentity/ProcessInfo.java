/*
 * Created on 2005-8-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.importdata.dataentity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProcessInfo {
    private long startTime = 0;

    private long endTime = 0;

    private long totalRecorder = 0;

    //   private long totalRecorderToImport = 0;

    private long bankPay1 = 0;//����ǰ5�ֽ��׵�����

    private long bankRecieve1 = 0;

    private long genaral1 = 0;

    private long inter1 = 0;

    private long special1 = 0;

    private long bankPay2 = 0;//�����5�ֽ��׵�����

    private long bankRecieve2 = 0;

    private long genaral2 = 0;

    private long inter2 = 0;

    private long special2 = 0;

    private long transError = 0;

    private long saveError = 0;

    private long notInput = 0;

    private long notDecide = 0;

    private Vector errInfo = null;

    //  private String message = "";

    /**
     * �������ݼ�
     *  
     */
    public void reset() {
        startTime = 0;
        endTime = 0;
        totalRecorder = 0;
        //   totalRecorderToImport = 0;
        bankPay1 = 0;//����ǰ���ֽ��׵�����
        bankRecieve1 = 0;
        genaral1 = 0;
        inter1 = 0;
        special1 = 0;
        bankPay2 = 0;//��������ֽ��׵�����
        bankRecieve2 = 0;
        genaral2 = 0;
        inter2 = 0;
        special2 = 0;
        //     fileError = 0;
        transError = 0;
        saveError = 0;
        notInput = 0;
        notDecide = 0;
        errInfo = new Vector();
    }

    /**
     * @return Returns the bankPay1.
     */
    public long getBankPay1() {
        return bankPay1;
    }

    /**
     * @param bankPay1
     *            The bankPay1 to set.
     */
    public void setBankPay1(long bankPay1) {

        this.bankPay1 = bankPay1;
    }

    /**
     * @return Returns the bankPay2.
     */
    public long getBankPay2() {
        return bankPay2;
    }

    /**
     * @param bankPay2
     *            The bankPay2 to set.
     */
    public void setBankPay2(long bankPay2) {

        this.bankPay2 = bankPay2;
    }

    /**
     * @return Returns the bankRecieve1.
     */
    public long getBankRecieve1() {
        return bankRecieve1;
    }

    /**
     * @param bankRecieve1
     *            The bankRecieve1 to set.
     */
    public void setBankRecieve1(long bankRecieve1) {

        this.bankRecieve1 = bankRecieve1;
    }

    /**
     * @return Returns the bankRecieve2.
     */
    public long getBankRecieve2() {
        return bankRecieve2;
    }

    /**
     * @param bankRecieve2
     *            The bankRecieve2 to set.
     */
    public void setBankRecieve2(long bankRecieve2) {

        this.bankRecieve2 = bankRecieve2;
    }

    /**
     * @return Returns the endTime.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            The endTime to set.
     */
    public void setEndTime(long endTime) {

        this.endTime = endTime;
    }

    /**
     * @return Returns the errInfo.
     */
    public Vector getErrInfo() {
        return errInfo;
    }

    /**
     * @param errInfo
     *            The errInfo to set.
     */
    public void setErrInfo(Vector errInfo) {

        this.errInfo = errInfo;
    }

    /**
     * @return Returns the genaral1.
     */
    public long getGenaral1() {
        return genaral1;
    }

    /**
     * @param genaral1
     *            The genaral1 to set.
     */
    public void setGenaral1(long genaral1) {

        this.genaral1 = genaral1;
    }

    /**
     * @return Returns the genaral2.
     */
    public long getGenaral2() {
        return genaral2;
    }

    /**
     * @param genaral2
     *            The genaral2 to set.
     */
    public void setGenaral2(long genaral2) {

        this.genaral2 = genaral2;
    }

    /**
     * @return Returns the inter1.
     */
    public long getInter1() {
        return inter1;
    }

    /**
     * @param inter1
     *            The inter1 to set.
     */
    public void setInter1(long inter1) {

        this.inter1 = inter1;
    }

    /**
     * @return Returns the inter2.
     */
    public long getInter2() {
        return inter2;
    }

    /**
     * @param inter2
     *            The inter2 to set.
     */
    public void setInter2(long inter2) {

        this.inter2 = inter2;
    }

    /**
     * @return Returns the notDecide.
     */
    public long getNotDecide() {
        return notDecide;
    }

    /**
     * @param notDecide
     *            The notDecide to set.
     */
    public void setNotDecide(long notDecide) {

        this.notDecide = notDecide;
    }

    /**
     * @return Returns the notInput.
     */
    public long getNotInput() {
        return notInput;
    }

    /**
     * @param notInput
     *            The notInput to set.
     */
    public void setNotInput(long notInput) {

        this.notInput = notInput;
    }

    /**
     * @return Returns the saveError.
     */
    public long getSaveError() {
        return saveError;
    }

    /**
     * @param saveError
     *            The saveError to set.
     */
    public void setSaveError(long saveError) {

        this.saveError = saveError;
    }

    /**
     * @return Returns the startTime.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            The startTime to set.
     */
    public void setStartTime(long startTime) {

        this.startTime = startTime;
    }

    /**
     * @return Returns the totalRecorder.
     */
    public long getTotalRecorder() {
        return totalRecorder;
    }

    /**
     * @param totalRecorder
     *            The totalRecorder to set.
     */
    public void setTotalRecorder(long totalRecorder) {

        this.totalRecorder = totalRecorder;
    }

    /**
     * @return Returns the transError.
     */
    public long getTransError() {
        return transError;
    }

    /**
     * @param transError
     *            The transError to set.
     */
    public void setTransError(long transError) {
        this.transError = transError;
    }

    /**
     * ��ȡ��ǰʱ��
     * 
     * @param fileDir
     * @throws IOException
     */
    private String getCurrentTime() {
        String result = "";
        
        result = Env.getSystemDateTime().toLocaleString();
        return result;
    }

    public void saveToFile(String fileDir) throws IOException {
        String sRecorder = "";//�洢������¼
        ImportdataInfo ib1 = new ImportdataInfo();
        BufferedWriter out = null;
        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new BufferedWriter(new FileWriter(fileDir + "processLog.txt",
                    true));

            // TODO Auto-generated catch block
            out.write(getCurrentTime() + "--��ʼ��������");
            out.newLine();
            out.write("�洢���ݿ⹲����ʱ��:"
                    + Long.toString((endTime - startTime) / 1000) + "��");
            out.newLine();
            out.write("����ȡ����" + Long.toString(totalRecorder) + "��");
            out.newLine();
            out.write("����ת����������" + Long.toString(transError) + "��");
            out.newLine();
            out.write("����������" + Long.toString(notInput) + "��");
            out.newLine();
            out.write("ת�������и�������" + Long.toString(bankPay1) + "��");
            out.newLine();
            out.write("ת���������տ�����" + Long.toString(bankRecieve1) + "��");
            out.newLine();
            out.write("ת�����ڲ�ת������" + Long.toString(inter1) + "��");
            out.newLine();
            out.write("ת��������������" + Long.toString(genaral1) + "��");
            out.newLine();
            out.write("ת��������������"+Long.toString(special1)+"��");
            out.newLine();
            out.write("��������з������������" + Long.toString(saveError) + "��");
            out.newLine();
            out.write("�������и�������" + Long.toString(bankPay2) + "��");
            out.newLine();
            out.write("���������տ�����" + Long.toString(bankRecieve2) + "��");
            out.newLine();
            out.write("�����ڲ�ת������" + Long.toString(inter2) + "��");
            out.newLine();
            out.write("��������������" + Long.toString(genaral2) + "��");
            out.newLine();
            out.write("��������������"+Long.toString(special2)+"��");
            out.newLine();
            out.write("������Ϣ��ϸ��");
            out.newLine();

            for (int i = 0; i < errInfo.size(); i++) {
                if (errInfo.elementAt(i).getClass().getName().equalsIgnoreCase(
                        "com.iss.itreasury.settlement.importdata.dataentity.ImportdataInfo")) {
                    ib1 = (ImportdataInfo) errInfo.elementAt(i);
                    out.write("������ˮ��:" + ib1.getCurrentNo());
                    out.write("   ");
                } else {
                    out.write("������Ϣ:" + (String) errInfo.elementAt(i));
                    out.newLine();
                }
            }
        } catch (IOException e) {
            throw e;

        } finally {
            try {
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                throw e1;
            }
        }
    }
    /**
     * @return Returns the special1.
     */
    public long getSpecial1() {
        return special1;
    }
    /**
     * @param special1 The special1 to set.
     */
    public void setSpecial1(long special1) {
        //putUsedField("special1", special1);
        this.special1 = special1;
    }
    /**
     * @return Returns the special2.
     */
    public long getSpecial2() {
        return special2;
    }
    /**
     * @param special2 The special2 to set.
     */
    public void setSpecial2(long special2) {
        //putUsedField("special2", special2);
        this.special2 = special2;
    }
    
    
  public static void main(String[] args){
     System.out.println(ImportdataInfo.class.getName()); 
  }

}
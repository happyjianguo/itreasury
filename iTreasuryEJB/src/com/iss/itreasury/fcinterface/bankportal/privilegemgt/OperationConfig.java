package com.iss.itreasury.fcinterface.bankportal.privilegemgt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.fcinterface.bankportal.constant.BooleanValue;

public class OperationConfig
{
	//�������Ͷ�Ӧ�����ҳ��URL
	public static final HashMap hmPageURL = new HashMap();
	//�������ͼ���
	public static HashMap hmOperation = new HashMap();
	public static Operation ACCOUNTQUERY = new Operation(1);//�˻����ݲ�ѯ��������	
	public static Operation VIREMENT = new Operation(2);//ָ��ά��
	public static Operation GATHER = new Operation(3);//����ҵ��
	public static Operation BANKSETTING = new Operation(4);//��������
	public static Operation CLIENTSETTING = new Operation(5);//�ͻ�����
	public static Operation ACCOUNTMNG = new Operation(6);//�˻�����
	public static Operation DATAMAINTAIN = new Operation(7);//����ά��
	public static Operation ACCTPROPERTY1SETTING = new Operation(8);//�˻�����һ����	
	public static Operation ACCTPROPERTY2SETTING = new Operation(9);//�˻����Զ�����
	public static Operation ACCTPROPERTY3SETTING = new Operation(10);//�˻�����������
	
	static
	{
		/**
		 * ACCOUNTQUERY �˻����ݲ�ѯ�������Ͷ���
		 */
		hmOperation.put(String.valueOf(ACCOUNTQUERY.getType()),ACCOUNTQUERY);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		ACCOUNTQUERY.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		ACCOUNTQUERY.setIsContainSub(BooleanValue.TRUE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		Collection colTables = new ArrayList();
		ACCOUNTQUERY.setTables(colTables);
		//�ͻ���
		Table tbClient = new Table("bs_clientsetting");
		//Ҫ���˻���ͼ��������
		tbClient.setIsJoinAccountView(BooleanValue.TRUE);
		//�ͻ�����˻���Ĺ�����������������ǣ������ֶ�(��)����>�˻����ֶ�(ֵ)��������Ҳһ��
		HashMap hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_clientid");	
		tbClient.setAndCondition(hmAndCondition);			
		colTables.add(tbClient);
		//���б�
		Table tbBank = new Table("bs_banksetting");
		//Ҫ���˻���ͼ��������
		tbBank.setIsJoinAccountView(BooleanValue.TRUE);
		//���б���˻���Ĺ�����������������ǣ������ֶ�(��)����>�˻����ֶ�(ֵ)��������Ҳһ��
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_bankid");	
		tbBank.setAndCondition(hmAndCondition);			
		colTables.add(tbBank);
		//���ұ�
		Table tbCountry = new Table("bs_countrysetting");		
		tbCountry.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_countryid");	
		tbCountry.setAndCondition(hmAndCondition);			
		colTables.add(tbCountry);
		//���ֱ�
		Table tbCurrency = new Table("bs_currencysetting");		
		tbCurrency.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_currencytype");	
		tbCurrency.setAndCondition(hmAndCondition);			
		colTables.add(tbCurrency);		
		//�˻�����һ��
		Table tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");		
		tbAcctPropertyOne.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertyone");	
		tbAcctPropertyOne.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyOne);
		//�˻����Զ���
		Table tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");		
		tbAcctPropertyTwo.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertytwo");	
		tbAcctPropertyTwo.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyTwo);
		//�˻���������
		Table tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");		
		tbAcctPropertyThree.setIsJoinAccountView(BooleanValue.TRUE);		
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_accountpropertythree");	
		tbAcctPropertyThree.setAndCondition(hmAndCondition);			
		colTables.add(tbAcctPropertyThree);
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/account/view/v030.jsp",ACCOUNTQUERY);//��ǰ����ѯ
		hmPageURL.put("/account/view/v040.jsp",ACCOUNTQUERY);//��ʷ����ѯ
		hmPageURL.put("/account/view/v050.jsp",ACCOUNTQUERY);//���ս��ײ�ѯ
		hmPageURL.put("/account/view/v060.jsp",ACCOUNTQUERY);//��ʷ���ײ�ѯ	
		hmPageURL.put("/account/view/v090.jsp",ACCOUNTQUERY);//���˵�����
		hmPageURL.put("/account/view/v111.jsp",ACCOUNTQUERY);//���׻����嵥
		hmPageURL.put("/account/view/v211.jsp",ACCOUNTQUERY);//���˽����嵥
		hmPageURL.put("/account/view/v070.jsp",ACCOUNTQUERY);//�˻�������Ϣ��ѯ
		hmPageURL.put("/account/view/v080.jsp",ACCOUNTQUERY);//�˻��鼯��ϵ��ѯ
		hmPageURL.put("/account/view/v230.jsp",ACCOUNTQUERY);//������Ϣ��¼
		hmPageURL.put("/query/view/v001.jsp",ACCOUNTQUERY);//�˻�ÿ������
		hmPageURL.put("/query/view/v010.jsp",ACCOUNTQUERY);//�˻��վ�����
		hmPageURL.put("/query/view/v040.jsp",ACCOUNTQUERY);//�˻���֧ͳ�Ʊ�
		hmPageURL.put("/query/view/v020.jsp",ACCOUNTQUERY);//�˻��վ����䶯��
		hmPageURL.put("/query/view/v050.jsp",ACCOUNTQUERY);//�ͻ�ÿ�������ܱ�
		hmPageURL.put("/query/view/v060.jsp",ACCOUNTQUERY);//����ÿ�������ܱ�
		hmPageURL.put("/query/view/v070.jsp",ACCOUNTQUERY);//�ͻ�ÿ�����������ܱ�
		hmPageURL.put("/query/view/v080.jsp",ACCOUNTQUERY);//����ÿ�����������ܱ�
		hmPageURL.put("/datamaintain/view/v201.jsp",ACCOUNTQUERY);//ֱ���˻���������ѯ
		hmPageURL.put("/datamaintain/view/v210.jsp",ACCOUNTQUERY);//ֱ���˻����ս�����ϸ��ѯ
		hmPageURL.put("/account/view/v070.jsp",ACCOUNTQUERY);//�˻�������Ϣ��ѯ
		hmPageURL.put("/account/view/v080.jsp",ACCOUNTQUERY);//�˻��鼯��ϵ��ѯ	
		
//*********************************************************************************************************************//	
		/**
		 * VIREMENT ָ��ά���������Ͷ���
		 */
		hmOperation.put(String.valueOf(VIREMENT.getType()),VIREMENT);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		VIREMENT.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		VIREMENT.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		VIREMENT.setTables(colTables);
		//ָ���
		Table tbInstruction = new Table("bs_bankinstructioninfo");
		//Ҫ���˻���ͼ��������
		tbInstruction.setIsJoinAccountView(BooleanValue.TRUE);
		//ָ����˻���Ĺ�������
		hmAndCondition = new HashMap();
		hmAndCondition.put("s_payaccountno","s_accountno");	
		tbInstruction.setAndCondition(hmAndCondition);
		HashMap hmOrCondition = new HashMap();
		hmOrCondition.put("s_agentacctnoofpay","s_accountno");	
		tbInstruction.setOrCondition(hmOrCondition);
		colTables.add(tbInstruction);		
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/bankinterface/view/v001.jsp",VIREMENT);//ָ���ѯ
		hmPageURL.put("/bankinterface/view/v010.jsp",VIREMENT);//ָ��ά��
		hmPageURL.put("/bankinterface/view/v020.jsp",VIREMENT);//ָ���
		
//**********************************************************************************************************************//		
		/**
		 * GATHER ����ҵ��������Ͷ���
		 */
		hmOperation.put(String.valueOf(GATHER.getType()),GATHER);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		GATHER.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		GATHER.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		GATHER.setTables(colTables);
		//�ͻ���
		tbClient = new Table("bs_clientsetting");
		//Ҫ���˻���ͼ��������
		tbClient.setIsJoinAccountView(BooleanValue.TRUE);
		//�ͻ�����˻���Ĺ�������
		hmAndCondition = new HashMap();
		hmAndCondition.put("n_id","n_clientid");	
		tbClient.setAndCondition(hmAndCondition);			
		colTables.add(tbClient);	
		//�鼯���Ա�
		Table tbTactic = new Table("bs_acctgathertactic");
		colTables.add(tbTactic);
		//�˻��鼯�������ݱ�
		//����ʹ�øñ�,��bs_acctgathertactic���㹻
//		Table tbTacticContent = new Table("bs_acctgathertacticcontent");
//		colTables.add(tbTacticContent);
		//�˻��Զ��鼯�������ñ�
		Table tbTacticCondition = new Table("bs_acctgathercondition");
		colTables.add(tbTacticCondition);		
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/gather/view/v001.jsp",GATHER);//�˻��鼯���Զ���
		hmPageURL.put("/gather/view/v020.jsp",GATHER);//���е���
		hmPageURL.put("/gather/view/v030.jsp",GATHER);//�ֶ��鼯(��)
		hmPageURL.put("/bankinterface/view/v110.jsp",GATHER);//�ֶ��鼯
		hmPageURL.put("/gather/view/v010.jsp",GATHER);//�ʽ��ϻ�����
//**********************************************************************************************************************//		
		/**
		 * BANKSETTING �������ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(BANKSETTING.getType()),BANKSETTING);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		BANKSETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		BANKSETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		BANKSETTING.setTables(colTables);
		//���б�
		tbBank = new Table("bs_banksetting");		
		colTables.add(tbBank);				
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/setting/view/v008.jsp",BANKSETTING);//��������
		
//**********************************************************************************************************************//		
		/**
		 * CLIENTSETTING �ͻ����ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(CLIENTSETTING.getType()),CLIENTSETTING);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		CLIENTSETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		CLIENTSETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		CLIENTSETTING.setTables(colTables);
		//�ͻ���
		tbClient = new Table("bs_clientsetting");		
		colTables.add(tbClient);				
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/setting/view/v002.jsp",CLIENTSETTING);//�ͻ�����	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY1SETTING �˻�����һ���ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY1SETTING.getType()),ACCTPROPERTY1SETTING);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		ACCTPROPERTY1SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		ACCTPROPERTY1SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		ACCTPROPERTY1SETTING.setTables(colTables);
		//�˻�����һ��
		tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");		
		colTables.add(tbAcctPropertyOne);				
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/setting/view/v014.jsp",ACCTPROPERTY1SETTING);//�˻�����һ����	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY2SETTING �˻����Զ����ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY2SETTING.getType()),ACCTPROPERTY2SETTING);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		ACCTPROPERTY2SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		ACCTPROPERTY2SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		ACCTPROPERTY2SETTING.setTables(colTables);
		//�˻����Զ���
		tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");		
		colTables.add(tbAcctPropertyTwo);				
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/setting/view/v017.jsp",ACCTPROPERTY2SETTING);//�˻����Զ�����	
//**********************************************************************************************************************//
		
		/**
		 * ACCTPROPERTY3SETTING �˻����������ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(ACCTPROPERTY3SETTING.getType()),ACCTPROPERTY3SETTING);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		ACCTPROPERTY3SETTING.setIsNeedAccount(BooleanValue.FALSE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		ACCTPROPERTY3SETTING.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		ACCTPROPERTY3SETTING.setTables(colTables);
		//�˻���������
		tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");		
		colTables.add(tbAcctPropertyThree);				
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/setting/view/v020.jsp",ACCTPROPERTY3SETTING);//�˻�����������	
//**********************************************************************************************************************//
		
		/**
		 * ACCOUNTMNG �˻����ò������Ͷ���
		 */
		hmOperation.put(String.valueOf(ACCOUNTMNG.getType()),ACCOUNTMNG);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		ACCOUNTMNG.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		ACCOUNTMNG.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		colTables = new ArrayList();
		ACCOUNTMNG.setTables(colTables);
		//�ͻ���
		tbClient = new Table("bs_clientsetting");		
		colTables.add(tbClient);
		//���б�
		tbBank = new Table("bs_banksetting");
		colTables.add(tbBank);
		//�����ű�
		Table tbSubject = new Table("bs_subjectsetting");
		colTables.add(tbSubject);
		//�˻�����һ��
		tbAcctPropertyOne = new Table("bs_accountpropertyonesetting");				
		colTables.add(tbAcctPropertyOne);
		//�˻����Զ���
		tbAcctPropertyTwo = new Table("bs_accountpropertytwosetting");					
		colTables.add(tbAcctPropertyTwo);
		//�˻���������
		tbAcctPropertyThree = new Table("bs_accountpropertythreesetting");					
		colTables.add(tbAcctPropertyThree);
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/account/view/v001.jsp",ACCOUNTMNG);//�˻�����
		hmPageURL.put("/account/view/v010.jsp",ACCOUNTMNG);//�˻����
		hmPageURL.put("/account/view/v020.jsp",ACCOUNTMNG);//�˻�����
		hmPageURL.put("/account/view/v240.jsp",ACCOUNTMNG);//�˺�������ѯ
//**********************************************************************************************************************//		
		/**
		 * DATAMAINTAIN ����ά���������Ͷ���
		 */
		hmOperation.put(String.valueOf(DATAMAINTAIN.getType()),DATAMAINTAIN);
		/**1 �Ƿ���Ҫ�����˻�,���Ƿ���Ҫ�����˻���ͼ**/
		DATAMAINTAIN.setIsNeedAccount(BooleanValue.TRUE);
		
		/**2 Ĭ�����Ƿ�����¼����´�**/
		DATAMAINTAIN.setIsContainSub(BooleanValue.FALSE);
		
		/**3 ��Ҫ������ͼ�ı�**/
		//û����Ҫ���⽨������ͼ
		
		/**4 ��Ӧ�����ҳ��**/
		hmPageURL.put("/datamaintain/view/v061.jsp",DATAMAINTAIN);//�ֹ��˶�����
		hmPageURL.put("/datamaintain/view/v071.jsp",DATAMAINTAIN);//�ֹ�������ʷ����
		hmPageURL.put("/datamaintain/view/v021.jsp",DATAMAINTAIN);//�˶Խ����ѯ		
	}
	
	public static Operation getOperation(long operationType)
	{
		return (Operation)hmOperation.get(String.valueOf(operationType));
	}
	public static Operation getOperationByPageURL(String pageURL)
	{
		return (Operation)hmPageURL.get(pageURL);
	}
	/**
	 * ��������
	 */
	public static class Operation
	{				
	    //ҵ������
		private long type = -1;
		
		//Ĭ���Ƿ�����¼����´�
		private long isContainSub = -1;
		
		//�Ƿ���Ҫ�����˻���ͼ
		private long isNeedAccount = -1;
		
		//��Ҫ������ͼ�ı�
		private Collection tables = null;
		
		private Operation(long type)
		{
			this.type = type;
		}		

		public long getIsContainSub()
		{
			return isContainSub;
		}

		public void setIsContainSub(long isContainSub)
		{
			this.isContainSub = isContainSub;
		}

		public long getIsNeedAccount()
		{
			return isNeedAccount;
		}

		public void setIsNeedAccount(long isNeedAccount)
		{
			this.isNeedAccount = isNeedAccount;
		}

		public Collection getTables()
		{
			return tables;
		}		

		public long getType()
		{
			return type;
		}		

		public void setTables(Collection tables)
		{
			this.tables = tables;
		}

		public void setType(long type)
		{
			this.type = type;
		}	
	}
	/**
	 * Ҫ������ͼ�ı�
	 */
	public static class Table
	{		
		//����
		private String name = null;	
		private long isJoinAccountView = -1;
		//��������
		private HashMap andCondition = null;//and�Ĺ�������
		private HashMap orCondition = null;//or�Ĺ�������
		
		private Table(String name)
		{
			this.name = name;
		}
		
		public long getIsJoinAccountView()
		{
			return isJoinAccountView;
		}

		public void setIsJoinAccountView(long isJoinAccountView)
		{
			this.isJoinAccountView = isJoinAccountView;
		}

		public HashMap getAndCondition()
		{
			return andCondition;
		}
		public void setAndCondition(HashMap andCondition)
		{
			this.andCondition = andCondition;
		}		
		public void setName(String name)
		{
			this.name = name;
		}
		public void setOrCondition(HashMap orCondition)
		{
			this.orCondition = orCondition;
		}		
		public String getName()
		{
			return name;
		}
		public HashMap getOrCondition()
		{
			return orCondition;
		}	
	}
}

package com.iss.itreasury.safety.fingercert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FpDemo {

	public String tlr_no; // �û�ID
	public String sys_id; // ����ID
	public String randomNum; // ���������
	public String fingerValue; // ָ������

	public FpDemo() {
	}

	public FpDemo(String tlr_no, String sys_id, String randomNum) {
		this.tlr_no = tlr_no;
		this.sys_id = sys_id;
		this.randomNum = randomNum;
	}

	public FpDemo(String tlr_no, String sys_id, String randomNum,
			String fingerValue) {
		this.tlr_no = tlr_no;
		this.sys_id = sys_id;
		this.randomNum = randomNum;
		this.fingerValue = fingerValue;
	}

	/**
	 *����: ģ��ҵ���̨��Ա����֤ģʽ�ӿڷ���HTTP����
     *����: Ա����֤�ӿڵ�URL
     *����ֵ: ��Ӧ�ַ�������֤ģʽ�����1--ָ�� 0--����
     *������� randomNum     ���������
     *           tlr_no	�û������û���Ψһ��ʶ
     *           sys_id	ϵͳID��һ�㴫Ĭ��ֵ 1
	 * ���ѯԱ����֤ģʽ�ӿڷ���http���� �������Ӧ���ַ���
	 */
	public String queryMode(String POST_URL) throws IOException {

		/*
		 * ����DES3�ļ��ܺ�����DES3Encrypt(data, randomNum) Ϊ������
		 * 
		 * tlr_no, sys_id ���м���,
		 * 
		 * ������ƴ�ӳ����������Query_Param
		 */
		tlr_no = DES3.DES3Encrypt(this.tlr_no, this.randomNum);

		sys_id = DES3.DES3Encrypt(this.sys_id, this.randomNum);

		String Query_Param = "randomNum=" + randomNum + "&tlr_no=" + tlr_no
				+ "&sys_id=" + sys_id;

		// Post�����url
		URL postUrl = new URL(POST_URL);
		// ������
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ����
		// http�����ڣ������Ҫ��Ϊtrue
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		// Post ������ʹ�û���
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects��static���������������е�URLConnection����
		// URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ����
		connection.setInstanceFollowRedirects(true);
		// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
		// ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode
		// ���б���
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
		// Ҫע�����connection.getOutputStream�������Ľ���connect��
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection
				.getOutputStream());
		// DataOutputStream.writeBytes���ַ����е�16λ��unicode�ַ���8λ���ַ���ʽд��������
		out.writeBytes(Query_Param);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));// ���ñ���,������������
		String line = "";
		String results = "";// �洢��Ӧhttp�����ķ���ֵ

		// ���������Ϊԭ���������+1
		randomNum = String.valueOf(Integer.parseInt(randomNum) + 1);

		System.out.println("=========��֤ģʽ============");
		while ((line = reader.readLine()) != null) {
			// ����DES3.DES3Decrypt(data, randomNum)��������Ӧ�ַ������н���
			line = DES3.DES3Decrypt(line, randomNum);
			results += line;
			System.out.println(line);
		}
		System.out.println("=============================");
		reader.close();
		connection.disconnect();

		return results;
	}
	
	
	
	

	/**
	 * ���ѯԱ��ָ����֤�ӿڷ���http���� �������Ӧ���ַ���
	 * ����: ģ��ҵ���̨��ָ����֤�ӿڷ���HTTP����
     * ����: ָ����֤�ӿڵ�URL
     * ����ֵ: ��Ӧ�ַ�������ָ֤�ƽ����0--��֤ͨ�� ��0--��֤ʧ��
     * ������� randomNum     ���������
     *              tlr_no	�û������û���Ψһ��ʶ
     *              sys_id	ϵͳID��һ�㴫Ĭ��ֵ 1
     *              fingerValue   ָ������
	 */
	public String checkFinger(String POST_URL) throws IOException {

		/*
		 * ����DES3�ļ��ܺ�����DES3Encrypt(data, randomNum) Ϊ������
		 * 
		 * tlr_no, sys_id, fingerValue ���м���,
		 * 
		 * ������ƴ�ӳ����������Check_Param
		 */
		System.out.println("tlr_no:"+tlr_no);
		tlr_no = DES3.DES3Encrypt(this.tlr_no, this.randomNum);

		sys_id = DES3.DES3Encrypt(this.sys_id, this.randomNum);

		fingerValue = DES3.DES3Encrypt(this.fingerValue, this.randomNum);

		String Check_Param = "randomNum=" + randomNum + "&tlr_no=" + tlr_no
				+ "&sys_id=" + sys_id + "&fingerValue=" + fingerValue;

		// Post�����url����get��ͬ���ǲ���Ҫ������
		
		System.out.println(POST_URL);
		URL postUrl = new URL(POST_URL);
		// ������
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ����
		// http�����ڣ������Ҫ��Ϊtrue
		connection.setDoOutput(true);
		// Read from the connection. Default is true.
		connection.setDoInput(true);
		// Set the post method. Default is GET
		connection.setRequestMethod("POST");
		// Post cannot use caches
		// Post ������ʹ�û���
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects��static���������������е�URLConnection����
		// URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ����
		connection.setInstanceFollowRedirects(true);
		// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
		// ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode
		// ���б���
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
		// Ҫע�����connection.getOutputStream�������Ľ���connect��

		DataOutputStream out = new DataOutputStream(connection
				.getOutputStream());
		// DataOutputStream.writeBytes���ַ����е�16λ��unicode�ַ���8λ���ַ���ʽд��������
		out.writeBytes(Check_Param);
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "US-ASCII"));// ���ñ���,������������ISO-8859-1
		String line = "";
		String results = "";// �洢��Ӧhttp�����ķ���ֵ

		// ���������Ϊԭ���������+1
		randomNum = String.valueOf(Integer.parseInt(randomNum) + 1);

		System.out.println("===========��ָ֤�� ==========");
		while ((line = reader.readLine()) != null) {
			// ����DES3.DES3Decrypt(data, randomNum)��������Ӧ�ַ������н���
			line = DES3.DES3Decrypt(line, randomNum);
			results += line;
			System.out.println(line);
		}
		System.out.println("=============================");

		reader.close();
		connection.disconnect();

		return results;
	}

}

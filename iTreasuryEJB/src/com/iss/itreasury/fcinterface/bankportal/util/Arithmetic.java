/*
 * Created on 2005-6-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.math.BigDecimal;

/**
 * ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ������������㣬����������ṩ�� ȷ�ĸ��������㣬�����Ӽ��˳����������롣 From CSDN
 * 
 * @author mxzhou
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Arithmetic {
	// Ĭ�ϳ������㾫��
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������ĺ�
	 * 
	 */
	public static double add(double v1, double v2) {
		if (Double.isNaN(v1) || Double.isNaN(v2)) {
			return Double.NaN;
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/** double �ۼ� 
     * 1. NaN + NaN=NaN
     * 2. NaN + v = v
     * 3. v1 + v2 = v3
     * */

	public static double add(double v1, double v2, boolean flag) {
		BigDecimal b1 = null;
		BigDecimal b2 = null;

		if (Double.isNaN(v1) && Double.isNaN(v2))
			return Double.NaN;
		else if ((!Double.isNaN(v1)) && Double.isNaN(v2)) {
			b1 = new BigDecimal(Double.toString(v1));
			return b1.doubleValue();
		} else if (Double.isNaN(v1) && (!Double.isNaN(v2))) {
			b2 = new BigDecimal(Double.toString(v2));
			return v2;
		} else {
			b1 = new BigDecimal(Double.toString(v1));

			b2 = new BigDecimal(Double.toString(v2));

			return b1.add(b2).doubleValue();
		}
	}

	/**
	 * 
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������Ĳ�
	 * 
	 */
	public static double sub(double v1, double v2) {
		if (Double.isNaN(v1) || Double.isNaN(v2)) {
			return Double.NaN;
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}
    
    /**
     * ��������
     * 1.NaN - NaN = NaN
     * 2.NaN - v = NaN
     * 3.v - NaN = v
     * 4.v1-v2 = v3
     * 
     * @param v1 ������
     * @param v2 ����
     * @param flag
     * @return
     */
    public static double sub(double v1, double v2, boolean flag) {
        if (Double.isNaN(v1)) {
            return Double.NaN;
        }else if(Double.isNaN(v2))
        {
            return v1;
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();

    }    

	/**
	 * 
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������Ļ�
	 * 
	 */
	public static double mul(double v1, double v2) {
		if (Double.isNaN(v1) || Double.isNaN(v2)) {
			return Double.NaN;
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 
	 * �ṩ��ȷ���������㡣
	 * 
	 * @param inputs
	 *            ���˲���
	 * 
	 * @return ��
	 * 
	 */
	public static double mul(double[] inputs) {

		double res = 0.0;
		for (int i = 0; i < inputs.length - 1; i++) {
			double v1 = 0.0;
			double v2 = 0.0;
			if (i == 0)
				v1 = inputs[i];
			else
				v1 = res;
			v2 = inputs[i + 1];

			if (Double.isNaN(v1) || Double.isNaN(v2)) {
				return Double.NaN;
			}

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			res = b1.multiply(b2).doubleValue();
		}
		return res;

	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
	 * 
	 * С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ������������
	 * 
	 */
	public static double div(double v1, double v2) {
		if (Double.isNaN(v1) || Double.isNaN(v2)) {
			return Double.NaN;
		}

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
	 * 
	 * �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * 
	 * @return ������������
	 * 
	 */
	public static double div(double v1, double v2, int scale) {
		if (Double.isNaN(v1) || Double.isNaN(v2)) {
			return Double.NaN;
		}

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
	 * 
	 * �����ȣ��Ժ�������������롣
	 * 
	 * @param inputs
	 *            �������������뱣֤˳��
	 * 
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * 
	 * @return ��������
	 * 
	 * @author Add by Huang Ye
	 * 
	 */
	public static double div(double[] inputs, int scale) {
		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}
		double res = 0.0;
		for (int i = 0; i < inputs.length - 1; i++) {
			double v1 = 0.0;
			double v2 = 0.0;
			if (i == 0)
				v1 = inputs[i];
			else
				v1 = res;
			v2 = inputs[i + 1];

			if (Double.isNaN(v1) || Double.isNaN(v2)) {
				return Double.NaN;
			}

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			res = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return res;

	}

	/**
	 * 
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * 
	 * @param scale
	 *            С���������λ
	 * 
	 * @return ���������Ľ��
	 * 
	 */
	public static double round(double v, int scale) {
		if (Double.isNaN(v)) {
			return Double.NaN;
		}

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 
	 * �ṩ�������ıȽ� by Huang Ye
	 * 
	 * @param d1
	 *            ��Ҫ�Ƚϵ�����1
	 * @param d2
	 *            ��Ҫ�Ƚϵ�����2
	 * @param scale
	 *            ��֮�Ƚϵ����ֵ�С��λ��
	 * 
	 * @return �Ƿ����
	 * 
	 */
	public static boolean equal(double d1, double d2, int scale) {
		if (Double.isNaN(d1) || Double.isNaN(d2)) {
			return false;
		}

		double difference = Math.abs(d1 - d2);
		double minNum = Math.pow(10, (-1) * scale);
		if (difference < minNum)
			return true;
		else
			return false;
	}
}

/*
 * Created on 2005-6-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.math.BigDecimal;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。 From CSDN
 * 
 * @author mxzhou
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Arithmetic {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
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

	/** double 累加 
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
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
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
     * 减法运算
     * 1.NaN - NaN = NaN
     * 2.NaN - v = NaN
     * 3.v - NaN = v
     * 4.v1-v2 = v3
     * 
     * @param v1 被减数
     * @param v2 减数
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
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
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
	 * 提供精确的连乘运算。
	 * 
	 * @param inputs
	 *            连乘参数
	 * 
	 * @return 积
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
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
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
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
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
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param inputs
	 *            连除参数，必须保证顺序
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 参数的商
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
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
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
	 * 提供两个数的比较 by Huang Ye
	 * 
	 * @param d1
	 *            需要比较的数字1
	 * @param d2
	 *            需要比较的数字2
	 * @param scale
	 *            与之比较的数字的小数位数
	 * 
	 * @return 是否相等
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

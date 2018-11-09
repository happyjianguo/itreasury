package com.iss.itreasury.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import com.iss.itreasury.util.Encryptiontools;

public class GetPasswordServlet extends HttpServlet {
	private ServletConfig config;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.config = config;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
	//	GetPassword(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
	//	GetPassword(req, res);
	}

	/**
	 * 获取一个密钥<br/>
	 * 需要密钥校验
	 * @param req
	 * @param res
	 * @throws ServletException
	 */
	public void GetPassword(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
		java.io.Writer writer = null;
		try {
			res.setContentType("text/html; charset=UTF-8");
			res.setHeader("Pragma", "no-cache");
			res.setHeader("Cache-Control", "no-cache, must-revalidate");
				writer = res.getWriter();
				writer.write(Encryptiontools.getEncryption());
				writer.flush();
				writer.close();
				writer = null;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.flush();
						writer.close();
						writer = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	}
}
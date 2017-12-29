package cn.et.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.MyNews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Servlet implementation class NewsController
 */
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NewsController() {
		// TODO Auto-generated constructor stub
	}

	private static final String HTML_DIR = "e:\\html";
	MyNews myNews = new MyNews();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 这里不单单只是往数据库写入数据 还要生成静态（freemarker）模板
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		// 获取当前时间
		Date date = new Date();
		String dateStr = sdf.format(date);
		String uuid = UUID.randomUUID().toString();
		try {
			String lastId = myNews.queryLastId();
			// 生成html
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
			// 配置ftl查找目录
			cfg.setDirectoryForTemplateLoading(new File("E:/JavaEEeclipse/news_manager/src/main/resources"));
			// 设置数据的抓取模式
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));

			Map root = new HashMap();
			root.put("title", title);
			root.put("content", content);
			root.put("createtime", dateStr);
			// 数据与模板关联
			// 实例化模板对象
			Template temp = cfg.getTemplate("newsdetail.ftl");

			String saveFile = HTML_DIR + "/" + uuid + ".html";
			// 生成html 输出到目标
			Writer out = new OutputStreamWriter(new FileOutputStream(saveFile));
			temp.process(root, out);
			out.flush();
			out.close();
			// 标题，内容，html路径，时间
			myNews.inserNews(title, content, uuid + ".html", dateStr);
			PrintWriter pw = response.getWriter();
			pw.print("新闻发布成功");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package cn.et.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbUtils {
	//jdbj�Ĺؼ�
	/*public static Connection getConnection(){
		try {
			//��һ������drivet��
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ȡurl��ַ     jdbc:mysql://[ip]:[�˿�]/���ݿ���
		String url = "jdbc:mysql://localhost:3306/easytop";
		
		//��ȡ�������ݿ������      Connection
		Connection conn = null ;
		try {
			conn = DriverManager.getConnection(url, "root", "123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}*/
	/**
	 * ��װ��
	 */
	static Properties p = new Properties();
	static{
		//�����
		//getResourceAsStream���Ҿ��и������Ƶ���Դ
		InputStream is = DbUtils.class.getResourceAsStream("/jdbc.properties");
		try {
			//���������ж�ȡ�����б�����Ԫ�ضԣ�
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ����
	 * 	ͨ��·��url
	 * 	��������
	 * 	���ݿ��˺�
	 * 	���ݿ�����
	 */
	public static Connection getConnection()throws Exception {
		String url = p.getProperty("url");
		String driverClass = p.getProperty("driverClass");
		String name = p.getProperty("username");
		String password = p.getProperty("password");
		
		Class.forName(driverClass);
		//��ȡ���ݵ�����
		Connection conn = DriverManager.getConnection(url, name, password);
		return conn;
	}
	
	//��ѯ����
	public static List<Map> query(String sql)throws Exception {
		Connection conn = getConnection();
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		List list = new ArrayList();
		//��ȡ�еĸ���
		int columnCount = rsmd.getColumnCount();
		while(rs.next()) {
			Map map = new HashMap();
			//sql�б���Ҫ����1
			for (int i = 1; i <= columnCount; i++) {
				String colName = rsmd.getColumnName(i);
				String colValue = rs.getString(i);
				map.put(colName, colValue);
			}
			list.add(map);
		}
		conn.close();
		pst.cancel();
		rs.close();
		return list;
	}
	/*public static void main(String[] args) throws Exception {
		List<Map> result = query("select * from eclass");
		System.out.println(result);
	}*/
	//����sql���
	public static int execute(String sql) throws Exception {
		//�������ݿ�
		Connection conn = getConnection();
		PreparedStatement pst = conn.prepareStatement(sql);
		int i = pst.executeUpdate();
		conn.close();
		pst.cancel();
		return i;
	}
}

package cn.et.model;

import java.util.List;
import java.util.Map;

public class MyNews {
	/**
	 * ��������
	 * @param titile
	 * @param content
	 * @param newsPath
	 * @throws Exception
	 */
	public void inserNews(String titile,String content,String newsPath,String createTime) throws Exception{
		String sql="insert into mynews(title,content,htmlpath,createtime) values('"+titile+"','"+content+"','"+newsPath+"','"+createTime+"')";
		DbUtils.execute(sql);
	}
	
	/**
	 * �����������
	 * @param titile
	 * @param content
	 * @param newsPath
	 * @throws Exception
	 */
	public List<Map> queryNews() throws Exception{
		String sql="select * from mynews";
		return DbUtils.query(sql);
	}
	
	/**
	 * ��ȡ���һ�δ�����ID
	 * select last_insert_id() ��ȡ����󴴽���id
	 * @param titile
	 * @param content
	 * @param newsPath
	 * @throws Exception
	 */
	public String queryLastId() throws Exception{
		String sql="select LAST_INSERT_ID() as id";
		return DbUtils.query(sql).get(0).get("id").toString();
	}
}

package Beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SearchDBPTM 
{
	public static List resultSetToList(ResultSet rs) throws java.sql.SQLException
	{
        if (rs==null) return Collections.EMPTY_LIST;

        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        List list = new ArrayList();
        Map rowData;
        while (rs.next()){
            rowData = new HashMap(columnCount);
            for (int i=1; i<=columnCount; i++){
                rowData.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
	
	public List getResult(String ptm) throws Exception
	{
		String driver = "com.mysql.jdbc.Driver";
		
		String url = "jdbc:mysql://127.0.0.1:3306/polyq2";
		
		String user = "root";
		
		String password = "tanhao";
		
		try
		{
			Class.forName(driver);
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
				
		Connection conn = DriverManager.getConnection(url, user, password);
			
		if(!conn.isClosed())
		{
			System.out.println("Succeeded connecting to the database!");
			System.out.println("*******************************");
		}
			
		Statement stat = conn.createStatement();
		String sqlSel1 = "select protein from polyq2.ptms where ptm = '" + ptm + "';";
		System.out.println(sqlSel1);
		ResultSet rs = stat.executeQuery(sqlSel1);
		HashMap <Integer, String> map = new HashMap <Integer, String>();
		while(rs.next())
		{
			System.out.println(rs.getString(1));
			map.put(Integer.parseInt(rs.getString(1)), "a");
		}
		
		SortedSet<Integer> keys = new TreeSet<Integer>(map.keySet());
		String sqlSel4 = "select * from polyq2.protein where";
		for(int key:keys)
		{
			sqlSel4 = sqlSel4 + " idprotein = '" + key + "' or";
		}
		
		
		sqlSel4 = sqlSel4.substring(0, sqlSel4.length()-2) + ";";
		System.out.println(sqlSel4);
		ResultSet rs4 = stat.executeQuery(sqlSel4);
		
		List list = resultSetToList(rs4);
		for(int i = 0 ; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
			
		conn.close();
		return list;
	}
}

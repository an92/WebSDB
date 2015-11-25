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

public class SearchDBSpecies 
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
	
	public List getResult(String species) throws Exception
	{
		String driver = "com.mysql.jdbc.Driver";
		
		String url = "jdbc:mysql://127.0.0.1:3306/kinetochoreNEWDB";
		
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
		String sqlSel1 = "select * from kinetochoreNEWDB.protein where Organism like '%" + species + "%';";
		System.out.println(sqlSel1);
		ResultSet rs = stat.executeQuery(sqlSel1);

		List list = resultSetToList(rs);
			
		conn.close();
		return list;
	}}

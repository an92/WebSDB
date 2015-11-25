package BeansForDetailSearch;

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

public class SearchDetailReference 
{
	public static List resultSetToList(ResultSet rs) throws java.sql.SQLException{
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
	public List getResult(int proteinID, List ptmList) throws Exception
	{
		String driver = "com.mysql.jdbc.Driver";
		
		String url = "jdbc:mysql://127.0.0.1:3306/kinetochoredatabase";
		
		String user = "root";
		
		String password = "monash0905";
		
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
		
		HashMap <Integer, String> referenceIndex = new HashMap<Integer, String>();
		
		Map colMap5;
		for (int i=0; i< ptmList.size(); i++)
		{
		    colMap5 = (Map) ptmList.get(i);
		    String references = (String)colMap5.get("Indices");
		    references = references.trim();
		    if(!references.contains(";"))
		    {
		    	referenceIndex.put(Integer.parseInt(references.trim()), "a");
		    }
		    else
		    {
		    	String strs[] = references.split(";");
		    	for(int j = 0 ; j < strs.length; j++)
		    	{
		    		referenceIndex.put(Integer.parseInt(strs[j].trim()), "a");
		    	}
		    }
		    
		}
		
		
		Statement stat = conn.createStatement();
		SortedSet<Integer> keys = new TreeSet<Integer>(referenceIndex.keySet());
		String sqlSel1 = "";
		for(int key : keys)
		{
			System.out.println(key); 
			sqlSel1 = "select * from kinetochoredatabase.reference where ReferenceProteinID ='" + proteinID + "' and ReferencePTMIndex = '" + key + "' or ";
		}
		
		sqlSel1 = sqlSel1.substring(0, sqlSel1.length()-3) + ";";
		
		System.out.println(sqlSel1);
		ResultSet rs = stat.executeQuery(sqlSel1);		
		
		List list = resultSetToList(rs);
//		for(int i = 0 ; i < list.size(); i++)
//		{
//			System.out.println(list.get(i));
//		}
//		
//		HashMap <String, Map> result = new HashMap<String, Map>();
//		int count = 0;
//		for(int key: keys)
//		{
//			count++;
//			result.put(count+ "-"+ key, (Map)list.get(count-1));
//			System.out.println(result.get(count+"-"+key));
//		}
//		System.out.println(result.size());
		
		conn.close();
		return list;

	}
}

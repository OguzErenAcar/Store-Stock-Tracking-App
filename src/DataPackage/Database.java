package DataPackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Database {

	private String driver = "org.sqlite.JDBC";
//	private String driver = "com.mysql.cj.jdbc.Driver";
	private String path;
	private String url;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private DatabaseMetaData md;
	
	public Database(String path) {
		this.path=path;
		url = "jdbc:sqlite:"+path+".db";
//		url = "jdbc:mysql://localhost:3306/deneme";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Driver Connection Yok.", "MyDB", JOptionPane.WARNING_MESSAGE);
		}		

		openDB();
		closeDB();
	}
	
	public final void openDB() {
		try {
			conn = DriverManager.getConnection(url);
//			conn = DriverManager.getConnection(url,"root","1234");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Baglanti Saglanamadi.", "MyDB", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public final void closeDB() {
		try {
			conn.close();  
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "DB kapatma sirasinda hata.", "MyDB", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public final void executeSQL(String sql, String message) {
		openDB();
		try {
			stmt = conn.createStatement();  
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, message, "MyDB", JOptionPane.WARNING_MESSAGE);
		}
		closeDB();
	}

	public final void createTable(String tableName) {
		String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" (id INTEGER PRIMARY KEY AUTOINCREMENT);";
//		String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" (id INTEGER PRIMARY KEY AUTO_INCREMENT);";
		String message = "Tablo olusturulamadi.";
		executeSQL(sql, message);	
	}
	
	public final void dropTable(String tableName) {
		String sql = "DROP TABLE IF EXISTS "+tableName+" ;";
		String message = "Tablo silinemedi.";
		executeSQL(sql, message);
	}
	
	public final void addColumn(String columnName, String tableName) {		
		String sql = "ALTER TABLE "+tableName+" ADD "+columnName+" varchar;";
		String message = "Kolon eklenemedi.";
		if(!(isColumnExists(tableName, columnName)) && isTableExists(tableName))
			executeSQL(sql, message);	
	}
		
	public final ArrayList<String> getTableList() {  
		ArrayList<String> tableList = new ArrayList<String>();
		openDB();
		try {
			md = conn.getMetaData();
			rs = md.getTables(null, null, null, null);
			while (rs.next()) {
				tableList.add(rs.getString(3));
			}
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "tablo liste olusturma sirasinda hata", "MyDB", JOptionPane.WARNING_MESSAGE);
		}		
		closeDB();
		return tableList;
	}
	
	public final ArrayList<String> getColumnList(String tableName) {
		ArrayList<String> columnList = new ArrayList<String>();
		openDB();
		try {
			md = conn.getMetaData();
			rs = md.getColumns(null, null, tableName, null);
			while (rs.next()) {
				columnList.add(rs.getString(4));
			}
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "kolon liste olusturma sirasinda hata", "MyDB", JOptionPane.WARNING_MESSAGE);
		}		
		closeDB();
		return columnList;
	}
	
	public final boolean isTableExists(String tableName) {
		return isExists(tableName, getTableList());
	}
	
	public final boolean isColumnExists(String tableName, String columnName) {
		return isExists(columnName, getColumnList(tableName));
	}
	
	public final boolean isExists(String name, ArrayList<String> list) {
		boolean exists=false;
		for(String s:list) {
			if (s.equals(name)) {
				exists=true;
				break;
			}
		}
		return exists;
	}
	
	public final void showList(ArrayList<String> list) {
		for(String s:list) {
			System.out.println(s);
		}
	}
	
	public final void executeUpdateSQL(String sql, String message) {
		openDB();
		try {
			stmt = conn.createStatement();  
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, message, "MyDB", JOptionPane.WARNING_MESSAGE);
		}
		closeDB();
	}
	
	public final void insertDefaultRow(String tableName) {
		if (!isTableExists(tableName)) {
			JOptionPane.showMessageDialog(null, "tablo mevcut degil", "MyDB", JOptionPane.WARNING_MESSAGE);
			return;
		}
		ArrayList<String> newList = getColumnList(tableName);
		newList.remove(0);
		String columnListStr=String.join(",", newList);
		String valueListStr="\""+String.join("\",\"", newList)+"\"";
		String sql = "INSERT INTO "+tableName+" ("+columnListStr+") VALUES ("+valueListStr+");";
		String message = "Veri eklenamedi.";
		executeSQL(sql, message);
		return;
	}
	
	public final void deleteRow(String tableName, int id) {
		if (!isTableExists(tableName)) {
			JOptionPane.showMessageDialog(null, "tablo mevcut degil", "MyDB", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String sql = "DELETE FROM "+tableName+" WHERE id= "+id+";";
		String message = "Veri silinemedi.";
		executeSQL(sql, message);
	}
	
	public final void updateCell(String tableName, int id, String columnName, String columnValue) {
		if (!isTableExists(tableName)) {
			JOptionPane.showMessageDialog(null, "tablo mevcut degil", "MyDB", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (!isColumnExists(tableName,columnName)) {
			JOptionPane.showMessageDialog(null, "kolon adi tabloda mevcut degil", "MyDB", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String sql = "Update "+tableName+" SET "+columnName+" = \""+columnValue+"\" WHERE id = "+id+";";
		String message = "Veri guncellenemedi.";
		executeSQL(sql, message);
	}
	
		public final ArrayList<ArrayList<String>> executeQuerySQL(String sql, String message, String columnName) {
		ArrayList<ArrayList<String>> queryList = new ArrayList<ArrayList<String>>();
		openDB();
		try {
			stmt = conn.createStatement();  
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				ArrayList<String> valueList = new ArrayList<String>();
				if (columnName==null) {
					for (int i=1;i<=rs.getMetaData().getColumnCount();i++) {
						valueList.add(rs.getString(i));
					}	
				}
				else {
					valueList.add(rs.getString(columnName));
				}
				queryList.add(valueList);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, message, "MyDB", JOptionPane.WARNING_MESSAGE);
		}
		closeDB();
		return queryList;
	}
	
	public final Object[][] getObjectArray(ArrayList<ArrayList<String>> list) {
		if (list.size()==0)
			return null;
		Object [][]objectArray= new Object[list.size()][list.get(0).size()];
		for (int i=0;i<list.size();i++) {
			for (int j=0;j<list.get(i).size();j++) {
				objectArray[i][j]=list.get(i).get(j);
			}
		}
		return objectArray;	
	}
	
	public final void queryTest() {
		String sql;
		sql = "Select * FROM tablo3;";
		sql = "Select * FROM tablo3 where id=17;";
		sql = "Select * FROM tablo3 where colon1='yenideger';";
		sql = "Select * FROM tablo3 where colon1='yenideger' order by id desc;";// asc|desc
		sql = "Select * FROM tablo3 where colon1='yenideger' and  colon2='colon2'  order by id desc;";
		sql = "Select * FROM tablo3 where colon1 like '%deger%';";
		sql = "Select * FROM tablo3 where colon1 like '%en%';";
		sql = "Select * FROM tablo3;";
//		sql = "Select * FROM tablo3 where colon1 like '%deger%';";
		String message = "sorgu yapilamadi.";
//		ArrayList<ArrayList<String>> queryList=executeQuerySQL(sql, message,null);
		ArrayList<ArrayList<String>> queryList=executeQuerySQL(sql, message,"Ad");
		System.out.println(queryList);		
	}
	
	public final int getLastId(String tableName) {
		int id=-1;
		String sql;
		sql = "Select * From "+tableName;
		String message = "id alinamadi.";
		ArrayList<ArrayList<String>> queryList=executeQuerySQL(sql, message,"id");
		id = Integer.valueOf(queryList.get(queryList.size()-1).get(0));
		return id;
	}
          public final ArrayList<ArrayList<String>> getDefaultTable(String TableName){
		String sql="Select * FROM "+TableName+" ;";
		String message="Sorgu yapılmadı";
		ArrayList<ArrayList<String>> queryList= executeQuerySQL(sql, message, null);
		return queryList;
	}
              
 
	
}

 
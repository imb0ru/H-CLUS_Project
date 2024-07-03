package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Schema di una tabella */
public class TableSchema {
	/** Connessione al database */
	private DbAccess db;

	/** Classe interna che rappresenta un attributo della tabella */
	public class Column{
		/** Nome dell'attributo */
		private String name;
		/** Tipo dell'attributo */
		private String type;
		/**
		 * Costruttore
		 * @param name Nome dell'attributo
		 * @param type Tipo dell'attributo
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * Restituisce il nome dell'attributo
		 * @return Nome dell'attributo
		 */
		public String getColumnName(){
			return name;
		}
		/**
		 * Restituisce il tipo dell'attributo
		 * @return Tipo dell'attributo
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		/**
		 * Restituisce una rappresentazione testuale dell'attributo
		 * @return Rappresentazione testuale dell'attributo
		 */
		public String toString(){
			return name+":"+type;
		}
	}

	/**
	 * Lista degli attributi della tabella
	 */
	List<Column> tableSchema=new ArrayList<Column>();

	/**
	 * Costruttore
	 * @param db Connessione al database
	 * @param tableName Nome della tabella
	 * @throws SQLException in caso di errore SQL
	 * @throws DatabaseConnectionException in caso di errore di connessione al database
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		Connection con=db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
	    ResultSet res = meta.getColumns(null, null, tableName, null);

	    while (res.next()) {
	        if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	       		 tableSchema.add(new Column(
	       				 res.getString("COLUMN_NAME"),
	       				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	       				 );
	    }
	    res.close();
	}

	/**
	 * Restituisce il numero di attributi della tabella
	 * @return Numero di attributi
	 */
	public int getNumberOfAttributes(){
			return tableSchema.size();
		}

	/**
	 * Restituisce l'attributo in posizione index
	 * @param index Indice dell'attributo
	 * @return Attributo in posizione index
	 */
	public Column getColumn(int index){
			return tableSchema.get(index);
		}

}





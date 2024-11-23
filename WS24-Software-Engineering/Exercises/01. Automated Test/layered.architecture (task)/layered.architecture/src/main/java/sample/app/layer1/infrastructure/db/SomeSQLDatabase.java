package sample.app.layer1.infrastructure.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sample.app.common.domain.model.Something;

public class SomeSQLDatabase {
	
	@SuppressWarnings("serial")
	public static class DatabaseException extends RuntimeException {

		public DatabaseException(Throwable throwable) {
			super(throwable);
		}

		public DatabaseException(String message) {
			super(message);
		}
	}
	
	private static enum Database {
		
		MYSQL("mysql", "INT AUTO_INCREMENT PRIMARY KEY"), 
		POSTGRES("postgresql", "SERIAL PRIMARY KEY"), 
		SQLITE("sqlite", "INTEGER PRIMARY KEY AUTOINCREMENT"), 
		SQLSERVER("sqlserver", "INT IDENTITY(1,1) PRIMARY KEY"), 
		H2("h2", "INT AUTO_INCREMENT PRIMARY KEY");
		
		private String id, primaryKey;

		private Database(String id, String primaryKey) {
			this.id = "jdbc:" + id + ":";
			this.primaryKey = primaryKey;
		}
		
		static String primaryKey(String url) {
			for(Database db : Database.values())
				if(url.startsWith(db.id))
					return db.primaryKey;
			
			throw new DatabaseException("Unknown DB-Type " + url);
		}
	}
	
	private final String dbUrl;
	
	public SomeSQLDatabase() {
		this.dbUrl = "jdbc:sqlite:sample.db";
		init();
	}
	
	private void init()	{
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
        	
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            
            boolean tableExists = false;
            while(!tableExists && resultSet.next()) { 
                String tableName = resultSet.getString("TABLE_NAME");
                tableExists = tableName.equalsIgnoreCase("Somethings");
             }           

            if(!tableExists)                       
	            try (Statement statement = connection.createStatement()) {
	               String stmt = String.format(
	            		   "CREATE TABLE %s (%s %s, %s INTEGER)",
	            		   "Somethings", "id", Database.primaryKey(dbUrl),"data");
	                statement.executeUpdate(stmt);
	            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

    public Something create(Something thing) {
		if(thing.id() != 0) // already stored
			throw new DatabaseException("Creation failed: " + thing + " already exists.");
		
		int data = thing.data();

        try (Connection connection = DriverManager.getConnection(dbUrl);
        	 Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Somethings (data) VALUES (" + data + ")", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return new Something(id, data);
            } else
                throw new DatabaseException("Failed to retrieve the generated ID");
        } catch (SQLException e) {
        	throw new DatabaseException(e);
		}
    }
	
	public Something read(int id) {
		try (Connection connection = DriverManager.getConnection(dbUrl);
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery("SELECT * FROM Somethings WHERE id = " + id);
			if (resultSet.next()) {
				int data = resultSet.getInt("data");
				return new Something(id, data); 
			} else 
				return null;
		} catch (SQLException e) {
        	throw new DatabaseException(e);
		}
	}

	public boolean update(Something thing) {
        String sql = "UPDATE Somethings SET data = ? WHERE id = ?";
		try (Connection connection = DriverManager.getConnection(dbUrl);
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, thing.data());
            statement.setInt(2, thing.id());
            statement.executeUpdate();
    		return true;
       } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Something thing) {
        String sql = "DELETE FROM Somethings WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl);
        	 PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, thing.id());
            statement.executeUpdate();
    		return true;
        } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
    public int count()  {
        String sql = "SELECT COUNT(*) FROM Somethings";
        try (Connection connection = DriverManager.getConnection(dbUrl);
        	 Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next())
                return resultSet.getInt(1);
            else
            	return 0; // No records found
        } catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
    }
	
	public void reset() {		
		try (Connection connection = DriverManager.getConnection(dbUrl);
			 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS Somethings");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		init();
	}

}
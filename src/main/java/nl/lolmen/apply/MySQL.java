package nl.lolmen.apply;

import java.sql.*;

public class MySQL {

    private String host, username, password, database, table;
    private int port;
    private boolean fault;
    private Statement st;
    private Connection con;

    public MySQL(String host, int port, String username, String password, String database, String table) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
        this.port = port;
        this.connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
            System.out.println("[Apply] Connecting to database on " + url);
            this.con = DriverManager.getConnection(url, this.username, this.password);
            this.st = con.createStatement();
            this.setupDatabase();
            System.out.println("[Apply] MySQL initiated succesfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            this.setFault(true);
        } catch (SQLException e) {
            e.printStackTrace();
            this.setFault(true);
        } finally {
            if (this.fault) {
                System.out.println("[Apply] MySQL initialisation failed!");
            }
        }
    }

    private void setupDatabase() {
        this.executeStatement("CREATE TABLE IF NOT EXISTS " + this.table
                + "(player varchar(255) PRIMARY KEY, "
                + "goodat varchar(255), "
                + "banned varchar(255), "
                + "name varchar(255), "
                + "age varchar(255), "
                + "country varchar(255), "
                + "promoted tinyint DEFAULT 0, "
                + "promoter varchar(255), promotedTime TIMESTAMP)");
    }

    public boolean isFault() {
        return fault;
    }

    private void setFault(boolean fault) {
        this.fault = fault;
    }

    public int executeStatement(String statement) {
       // String statement = statemen.replace("'", "\'");
        if (isFault()) {
            System.out.println("[Apply] Can't execute statement, something wrong with connection");
            return 0;
        }
        try {
            this.st = this.con.createStatement();
            int re = this.st.executeUpdate(statement);
            this.st.close();
            return re;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet executeQuery(String statement) {
        if (isFault()) {
            System.out.println("[Apply] Can't execute query, something wrong with connection");
            return null;
        }
        if (statement.toLowerCase().startsWith("update") || statement.toLowerCase().startsWith("insert") || statement.toLowerCase().startsWith("delete")) {
            this.executeStatement(statement);
            return null;
        }
        //String state = statement.replace("'", "\'");
        try {
            this.st = this.con.createStatement();
            ResultSet set = this.st.executeQuery(statement);
            //this.st.close();
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        if (isFault()) {
            System.out.println("[Apply] Can't close connection, something wrong with it");
            return;
        }
        try {
            this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
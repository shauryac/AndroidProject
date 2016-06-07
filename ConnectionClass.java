package com.example.shauryachawla.bankingapp;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Created by shauryachawla on 11/29/15.
 */
public class ConnectionClass {
    String ip = "10.0.0.57";
    String classa  = "net.sourceforge.jtds.jdbc.Driver";
    String db = "customers";
    String un = "schawla";
    String password = “xxxx”;

   /* @SuppressLint("NewApi")*/
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classa);

            ConnURL = "jdbc:jtds:sqlserver://customers.chx6gnecooof.us-west-2.rds.amazonaws.com:1433;"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}

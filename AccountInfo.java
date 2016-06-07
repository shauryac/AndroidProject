package com.example.shauryachawla.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountInfo extends AppCompatActivity
{
    ConnectionClass con;
    EditText customerId, Name, Address, AccNo, Balance, Withdrawal, Deposit;
    Button buttonSubmit;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        con = new ConnectionClass();
         ShowDisplay();
    }

    public void OnSubmit(View a)
    {
        withdrawDeposit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowDisplay()
    {
        customerId = (EditText)findViewById(R.id.CustIDText);
        Name = (EditText)findViewById(R.id.NameText);
        Address=(EditText)findViewById(R.id.addressText);
        AccNo = (EditText)findViewById(R.id.AccountText);
        Balance=(EditText)findViewById(R.id.BalanceText);

        String z = "";
        Boolean isSuccess = false;

                try {

                    Connection cn = con.CONN();
                    if (cn == null)
                    {
                        z = "Error in connection with SQL server";
                    } else
                    {


                        String query = "select* from CustomerInfo";
                        PreparedStatement preparedStatement = cn.prepareStatement(query);
                        ResultSet rs = preparedStatement.executeQuery();
                        while(rs.next()) {


                           customerId.setText(rs.getString("CustomerId"));
                            Name.setText(rs.getString("Name"));
                            Address.setText(rs.getString("HomeAddress"));
                          AccNo.setText(rs.getString("AccountNo"));
                            Balance.setText(rs.getString("Balance"));
                            total= Double.parseDouble(Balance.getText().toString());
                        }

                        z = "Added Successfully";
                        isSuccess = true;
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                }
    }

    public void withdrawDeposit()
    {

        Withdrawal = (EditText)findViewById(R.id.WithdrawText);
        Deposit = (EditText)findViewById(R.id.DepositText);
        double withdraw, deposit;


        String z = "";
        Boolean isSuccess = false;

        try {

            Connection cn = con.CONN();
            if (cn == null)
            {
                z = "Error in connection with SQL server";
            } else
            {

                if(!Withdrawal.getText().toString().isEmpty())
                {
                    //gets the value for withdraw
                    withdraw= Double.parseDouble(Withdrawal.getText().toString());
                    total = total-withdraw;
                    Withdrawal.setText("");
                }


                if(!Deposit.getText().toString().isEmpty())
                {
                    deposit = Double.parseDouble(Deposit.getText().toString());
                    total = total+deposit;
                    Deposit.setText("");
                }


                String query = " update CustomerInfo set balance ="+ total;
                PreparedStatement preparedStatement = cn.prepareStatement(query);
                preparedStatement.executeUpdate();
                z = "Updated Successfully";
                isSuccess = true;
                long startTime = System.nanoTime();
                ShowDisplay();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Execution Time");

                String Text=Long.toString(duration);
                alertDialog.setMessage(Text +"nanoseconds");

                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            z = "Exceptions";
        }
    }

}

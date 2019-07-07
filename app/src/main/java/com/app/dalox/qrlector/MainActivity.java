package com.app.dalox.qrlector;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText nombre, telefono, email, codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText)findViewById(R.id.etName);
        telefono = (EditText)findViewById(R.id.etPhone);
        email = (EditText)findViewById(R.id.etMail);
        codigo = (EditText)findViewById(R.id.etCode);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null){
            String scanContent = scanningResult.getContents();
            StringTokenizer t = new StringTokenizer(scanContent,"*");
            final String nom = t.nextToken();
            final String tel = t.nextToken();
            final String mail = t.nextToken();

            nombre.setText(""+nom);
            telefono.setText(""+tel);
            email.setText(""+mail);

        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_LONG);
            toast.show();
        }
    }



    public  void onClick(View view){


        AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
        SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();

        switch (view.getId()){
            case R.id.btScan:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                    break;
            case R.id.btSave:
                /*AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
                SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();*/

                ContentValues contentValues = new ContentValues();

                contentValues.put("nombre",nombre.getText().toString());
                contentValues.put("nombre",nombre.getText().toString());
                contentValues.put("nombre",nombre.getText().toString());

                database.insert("obras",null,contentValues);
                database.close();

                Toast.makeText(MainActivity.this, "Se guardaron los datos de la obra satisfactoriamente",Toast.LENGTH_LONG).show();
                break;

            case R.id.btVer:
                /*AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
                SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();*/
                String code = codigo.getText().toString();
                Cursor cursor = database.rawQuery("select nombre,telefono,email from obras where codigo="+code,null);
                if (cursor.moveToFirst()){
                    nombre.setText(cursor.getString(0));
                    telefono.setText(cursor.getString(1));
                    email.setText(cursor.getString(2));

                }else{
                    Toast.makeText(MainActivity.this,"No existe una obra con ese codigo",Toast.LENGTH_LONG).show();
                }
                database.close();
                break;

            case R.id.btDelete:
                /*AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
                SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();*/
                String cod = codigo.getText().toString();
                int cant = database.delete("obras","codigo=" + cod,null);
                database.close();;
                codigo.setText("");
                nombre.setText("");
                telefono.setText("");
                email.setText("");

                if (cant==1){
                    Toast.makeText(MainActivity.this, "Los datos se borraron satisfactoriamente", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "No existe una obra con ese codigo", Toast.LENGTH_LONG).show();
                }
                break;


        }

        /*
        if (view.getId()==R.id.btScan){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }else if (view.getId() == R.id.btSave){
            AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
            SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("nombre",nombre.getText().toString());
            contentValues.put("nombre",nombre.getText().toString());
            contentValues.put("nombre",nombre.getText().toString());

            database.insert("obras",null,contentValues);
            database.close();

            Toast.makeText(MainActivity.this, "Se guardaron los datos de la obra satisfactoriamente",Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.btVer){
            AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
            SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();
            String code = codigo.getText().toString();
            Cursor cursor = database.rawQuery("select nombre,telefono,email from obras where codigo="+code,null);
            if (cursor.moveToFirst()){
                nombre.setText(cursor.getString(0));
                telefono.setText(cursor.getString(1));
                email.setText(cursor.getString(2));

            }else{
                Toast.makeText(MainActivity.this,"No existe una obra con ese codigo",Toast.LENGTH_LONG).show();
            }
            database.close();
        }else if(view.getId() == R.id.btDelete){
            AdminSQLiteHelper adminSQLiteHelper = new AdminSQLiteHelper(MainActivity.this,"Museo",null,1);
            SQLiteDatabase database = adminSQLiteHelper.getWritableDatabase();
            String code = codigo.getText().toString();
            int cant = database.delete("obras","codigo=" + code,null);
            database.close();;
            codigo.setText("");
            nombre.setText("");
            telefono.setText("");
            email.setText("");

            if (cant==1){
                Toast.makeText(MainActivity.this, "Los datos se borraron satisfactoriamente", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this, "No existe una obra con ese codigo", Toast.LENGTH_LONG).show();
            }
        }*/
    }
}

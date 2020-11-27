package com.example.tugas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextId, editNama, editAlamat, editHp, editProduk;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editTextId = (EditText) findViewById(R.id.editText_id);
        editNama = (EditText) findViewById(R.id.editText_nama);
        editAlamat = (EditText) findViewById(R.id.editText_alamat);
        editHp = (EditText) findViewById(R.id.editText_hp);
        editProduk = (EditText) findViewById(R.id.editText_produk);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText() .toString() ,
                                editNama.getText() .toString() ,
                                editAlamat.getText() .toString() ,
                                editHp.getText() .toString() ,
                                editProduk.getText() .toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editNama.getText().toString(),
                                editAlamat.getText().toString(),
                                editHp.getText().toString() ,
                                editProduk.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }


    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getallData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Tidak ada yg ditemukan");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Nama :" + res.getString(1) + "\n");
                            buffer.append("Alamat :" + res.getString(2) + "\n");
                            buffer.append("No. Hp :" + res.getString(3) + "\n");
                            buffer.append("Jenis Produk :" + res.getString(4) + "\n\n");
                        }

                        // Show all Data
                        showMessage("Ini Datanya", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

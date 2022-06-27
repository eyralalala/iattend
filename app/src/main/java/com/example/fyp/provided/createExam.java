package com.example.fyp.provided;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class createExam extends AppCompatActivity {

    Button btn_min, btn_max, search;
    EditText input_min, input_max;
    ArrayList<dataUser>list = new ArrayList<> ();
    AdapterItem adapterItem;
    RecyclerView recyclerView;
    DatabaseReference database =FirebaseDatabase.getInstance ().getReference ();
    FloatingActionButton fab_add;
    AlertDialog builderAlert;
    Context context;
    LayoutInflater layoutInflater;
    View showInput;
    Calendar calendar = Calendar.getInstance ();
    Locale id = new Locale ("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd-MMM-yyyy",id);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_exam);
//
//        context = this;
//        fab_add = findViewById (R.id.fab_add);
//        btn_min = findViewById (R.id.btn_min);
//        btn_max = findViewById (R.id.btn_max);
//        input_max = findViewById (R.id.input_max);
//        input_min = findViewById (R.id.input_min);
//        recyclerView = findViewById (R.id.recyclerView);

        fab_add.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                inputData();

            }
        });

        showData();
    }

    EditText et_coursecode, et_coursename, et_examtype, et_examdate;
    Button btn_datecreate, btn_save;
    RadioGroup rb_group;
    RadioButton radioButton;
    Date et_examdate_date;

    private void inputData() {
        builderAlert = new AlertDialog.Builder (context).create ();
        layoutInflater = getLayoutInflater();
        showInput = layoutInflater.inflate(R.layout.createexam_input_layout, null);
        builderAlert.setView(showInput);

//        et_coursecode = showInput.findViewById (R.id.et_coursecode);
//        et_coursename = showInput.findViewById (R.id.et_coursename);
//        et_examdate = showInput.findViewById (R.id.et_examdate);
//        btn_datecreate = showInput.findViewById (R.id.btn_datecreate);
//        btn_save = showInput.findViewById (R.id.btn_save);
//        rb_group = showInput.findViewById (R.id.rb_group);
        builderAlert.show ();

        btn_save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String Coursecode = et_coursecode.getText ().toString ();
                String Coursename = et_coursename.getText ().toString ();
                String Examdate = et_examdate.getText ().toString ();

                if (Coursecode.isEmpty ()) {
                    et_coursecode.setError ("Cannot be empty");

                } else if (Coursename.isEmpty ()) {
                    et_coursename.setError ("Cannot be empty");

                } else if (Examdate.isEmpty ()) {
                    et_examdate.setError ("Cannot be empty");
                } else {
                    int selected = rb_group.getCheckedRadioButtonId ();
                    radioButton = findViewById (selected);

                    database.child ("user").child (Coursecode).setValue (new dataUser (
                            Coursecode,
                            radioButton.getText ().toString (),
                            Coursename,
                            et_examdate_date.getTime ()
                    )).addOnSuccessListener (new OnSuccessListener<Void> () {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText (context, "Data have been added successfully", Toast.LENGTH_LONG).show ();
                            builderAlert.dismiss ();
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText (context, "Data failed to be added", Toast.LENGTH_LONG).show ();
                            builderAlert.dismiss ();
                        }
                    });

                }

            }
        });

        btn_datecreate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set (year, month, dayOfMonth);
                        et_examdate.setText (simpleDateFormat.format (calendar.getTime ()));
                        et_examdate_date = calendar.getTime ();
                    }
                },calendar.get (Calendar.YEAR),calendar.get (Calendar.MONTH), calendar.get (Calendar.DAY_OF_MONTH));
                datePickerDialog.show ();

            }
        });
    }

    private void showData() {
        database.child ("user").addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showListener(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showListener(DataSnapshot snapshot) {
        list.clear ();
        for (DataSnapshot item :snapshot.getChildren ()){
            dataUser user = item.getValue (dataUser.class);
            list.add (user);
        }
        adapterItem = new AdapterItem (context, list);
        recyclerView.setAdapter (adapterItem);
    }
}
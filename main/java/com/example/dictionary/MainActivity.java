package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button btnSearch;
    TextView txtViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        btnSearch = (Button) findViewById(R.id.btnSubmit);
        txtViewResults = (TextView) findViewById(R.id.txtViewResults);
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(editTextSearch.getText().toString()))
                {
                    Toast.makeText(MainActivity.this,"No empty keyword allowed",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference mref= FirebaseDatabase.getInstance().getReference("meaning");
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            String searchkeyword = editTextSearch.getText().toString();
                            if (snapshot.child(searchkeyword).exists())
                            {
                                txtViewResults.setText(snapshot.child(searchkeyword).getValue().toString());
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "No search results found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

        });

    }
}
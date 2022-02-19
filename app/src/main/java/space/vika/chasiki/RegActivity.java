package space.vika.chasiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegActivity extends AppCompatActivity {
    static DBManager dbManager;
    EditText mail;
    EditText name;
    Button con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        con=findViewById(R.id.Continue);
        mail=findViewById(R.id.Mail);
        name.findViewById(R.id.Name);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.addResult(RegActivity.this.name.getText().toString(),
                        RegActivity.this.mail.getText().toString());
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
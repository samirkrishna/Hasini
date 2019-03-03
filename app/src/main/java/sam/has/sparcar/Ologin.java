package sam.has.sparcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ologin extends AppCompatActivity {

    Button oologin;
    EditText ousername,opassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ologin);
        ousername = findViewById(R.id.ousername);
        opassword = findViewById(R.id.opassword);
        oologin = findViewById(R.id.oologin);


            checkCredentials();

    }

    public void checkCredentials()
    {

        oologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ousername.getText().toString().trim().length()!=0 && opassword.getText().toString().trim().length()!=0)
                startActivity(new Intent(Ologin.this,OwnerPage.class));
                else
                    Toast.makeText(Ologin.this,"Please provide Credentials",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

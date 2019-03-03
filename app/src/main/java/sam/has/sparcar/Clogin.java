package sam.has.sparcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Clogin extends AppCompatActivity {

    Button cclogin;
    EditText cusername,cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clogin);
        cusername = findViewById(R.id.cusername);
        cpassword = findViewById(R.id.cpassword);
        cclogin = findViewById(R.id.cclogin);

        checkCredentials();
    }

    private void checkCredentials() {

        cclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cusername.getText().toString().trim().length()!=0 && cpassword.getText().toString().trim().length()!=0)
                    startActivity(new Intent(Clogin.this,MapsActivity.class));
                else
                    Toast.makeText(Clogin.this,"Please provide Credentials",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

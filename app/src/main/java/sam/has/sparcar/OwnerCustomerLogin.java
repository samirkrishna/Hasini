package sam.has.sparcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerCustomerLogin extends AppCompatActivity {

    Button clogin,ologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oowner_customer_login);
        clogin=(Button)findViewById(R.id.ologin);
        ologin=(Button)findViewById(R.id.clogin);

        clogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerCustomerLogin.this,Ologin.class));
            }
        });
        ologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerCustomerLogin.this,Clogin.class));
            }
        });
    }
}

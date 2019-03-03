package sam.has.sparcar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button getlocation,register;
    EditText lati, longi,ename,eaddress,egovtid,ecostperhour,eslottype;
    LocationManager lManager;

    FirebaseDatabase db;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getlocation = findViewById(R.id.getlocation);
        register = findViewById(R.id.register);
        ename = findViewById(R.id.name);
        eaddress = findViewById(R.id.address);
        egovtid = findViewById(R.id.govtid);
        lati = findViewById(R.id.lati);
        longi = findViewById(R.id.longi);
        ecostperhour = findViewById(R.id.costperhour);
        eslottype = findViewById(R.id.slottype);

        lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(OwnerPage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OwnerPage.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latii=location.getLatitude();
                        double longii=location.getLongitude();
                        String la=String.valueOf(latii);
                        String lo=String.valueOf(longii);
                        lati.setText(la);
                        longi.setText(lo);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Owners");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(ename.getText().toString().trim().length()!=0 && eaddress.getText().toString().trim().length()!=0 && egovtid.getText().toString().trim().length()!=0 && longi.getText().toString().trim().length()!=0 &&
                    lati.getText().toString().trim().length()!=0 && ecostperhour.getText().toString().trim().length()!=0 && eslottype.getText().toString().trim().length()!=0) {
                saveOwnerInformation();
                ename.getText().clear();
                eaddress.getText().clear();
                egovtid.getText().clear();
                lati.getText().clear();
                longi.getText().clear();
                ecostperhour.getText().clear();
                eslottype.getText().clear();
                lati.getText().clear();
                longi.getText().clear();

                Toast.makeText(OwnerPage.this,"Enter All Credentials",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(OwnerPage.this,"Enter All Credentials",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOwnerInformation() {
        String name = ename.getText().toString().trim();
        String address = eaddress.getText().toString().trim();
        String govtid = egovtid.getText().toString().trim();
        double latitude = Double.parseDouble(lati.getText().toString().trim());
        double longitude = Double.parseDouble(longi.getText().toString().trim());
        String costperhour = ecostperhour.getText().toString().trim();
        String slottype = eslottype.getText().toString().trim();
        String id = mDatabase.push().getKey();
        String status="true";
        OwnerInformation ownerInformation = new OwnerInformation(id,name,address,govtid,latitude,longitude,costperhour,slottype,status);
        mDatabase.child(id).setValue(ownerInformation);
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.owner_page, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

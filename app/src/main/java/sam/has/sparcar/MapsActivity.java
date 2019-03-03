package sam.has.sparcar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;

    Dialog myDialog;
    private Location lastlocation;

    double latitude, longitude;
    LocationManager locationManager;

    String name, lati, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ChildEventListener mChildEventListener;
        mUsers = FirebaseDatabase.getInstance().getReference("Owners");
        mUsers.push().setValue(marker);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1000, this);

        myDialog=new Dialog(this);
        createCustomizeDialog();


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    OwnerInformation userInformation = s.getValue(OwnerInformation.class);
                    LatLng location = new LatLng(userInformation.lati, userInformation.longi);
                    String status = userInformation.status;
                    Boolean status1 = Boolean.valueOf(status);
                    String sammmmmm=userInformation.name;
                    if(status1) {
                        if (sammmmmm.length() <= 4)
                            mMap.addMarker(new MarkerOptions().position(location).snippet(userInformation.name + "\n" + userInformation.slottype + "\n" + userInformation.costperhour+ "\n" + userInformation.id).title(userInformation.name)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.markergg));
                        else
                            mMap.addMarker(new MarkerOptions().position(location).snippet(userInformation.name + "\n" + userInformation.slottype + "\n" + userInformation.costperhour+ "\n" + userInformation.id).title(userInformation.name)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                    }
                    else
                        mMap.addMarker(new MarkerOptions().position(location).snippet(userInformation.name + "\n" + userInformation.slottype + "\n" + userInformation.costperhour+ "\n" + userInformation.id).title(userInformation.name)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parkingred));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Snackbar.make((View) findViewById(R.id.map), "Click here to book the slot" , Snackbar.LENGTH_LONG).show();
              /* Snackbar.make(findViewById(R.id.map),"Click here to book a slot", Snackbar.LENGTH_LONG)
                        .setAction("Book Now", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i= new Intent(MapsActivity.this,PaymentActivity.class);
                                startActivity(i);
                            }
                        }).show();*/


                final String s=marker.getSnippet();
                final String sam[]=s.split("\n");
                final String samid=sam[3];
                Toast.makeText(MapsActivity.this,s,Toast.LENGTH_SHORT).show();

                TextView tclose;
                Button confirm,cancel;
                myDialog.setContentView(R.layout.dialogbox);
                tclose = (TextView)myDialog.findViewById(R.id.close);
                confirm = (Button)myDialog.findViewById(R.id.confirm);
                cancel = (Button)myDialog.findViewById(R.id.cancel);


                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().child("Owners").child(samid).child("status").setValue("false");
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                tclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                TextView tv1,tv2,tv3,tv4;
                tv2 = (TextView)myDialog.findViewById(R.id.aid);
                tv1 = (TextView)myDialog.findViewById(R.id.pid);
                tv3 = (TextView)myDialog.findViewById(R.id.tv3);
                tv4 = (TextView)myDialog.findViewById(R.id.tv4);
                tv1.setText("Parking slot ID: "+sam[0]);
                tv3.setText("Car Type: "+sam[1]);
                tv4.setText("Cost per hour: "+sam[2]);

                //int sammm= Integer.parseInt(sam[1]);



                /*button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                        mUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                TextView tv1;
                                tv1 = (TextView)myDialog.findViewById(R.id.pid);
                                //String name1 = dataSnapshot.child("name").getValue().toString();
                                //String latii = dataSnapshot.child("lati").getValue().toString();
                                tv1.setText(s);

                                //Toast.makeText(MapsActivity.this,name1,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });*/
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                return true;
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;

        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),16f));
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    public  void createCustomizeDialog() {
        TextView tclose;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = MapsActivity.this.getLayoutInflater();
        @SuppressLint("InflateParams") final View alertLayout = inflater.inflate(R.layout.popup, null);
        tclose = (TextView)alertLayout.findViewById(R.id.close);
        builder.setView(alertLayout);
        AlertDialog alertDialog=builder.create();
        //noinspection ConstantConditions
        alertDialog.show();

    }
    }

package com.digimasters.rev_comsgh;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private EditText f_name,l_name,address,email,mobile_no,dob,id_no,profiletype_no,tele,comment,bin_no;
private Spinner region,district,suburb,pf_day,pf_time,profile_type,id_type;
private Button date_pick,current_date,current_loc;
    private ProgressDialog spinner;
private ArrayAdapter<CharSequence> adapter;

Button next;
String Ghana_pc ,first_n,last_n,Address,Email,MmNo,final_dob,Bin_No;
Calendar mycalendar;
public static Activity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;

        //Location Manager instance
       LocationManager locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        //Initializing the Progress Dialog
        spinner = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);

        //Mapping Spinners
        region = (Spinner)findViewById(R.id.region);
        district =(Spinner)findViewById(R.id.district);
        profile_type = (Spinner)findViewById(R.id.profile_type);
        pf_day = (Spinner)findViewById(R.id.pickup_day);
        pf_time = (Spinner)findViewById(R.id.pickup_time);
        suburb = (Spinner)findViewById(R.id.suburb);
        id_type = (Spinner)findViewById(R.id.id_type);





        //Mapping get current location button
        current_loc = (Button) findViewById(R.id.getlocation_btn);


        //Current loc button onclick
        current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setMessage("Retrieving Location ...");
                spinner.setCanceledOnTouchOutside(false);
                spinner.setCancelable(false);
                spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                spinner.show();
               getcurrentloc();

                spinner.dismiss();
            }
        });

        //Setting apt/store No edittext readonly at oncreate
        final EditText apt_no= (EditText)findViewById(R.id.apt_store_no);
        apt_no.setEnabled(false);

        //Enable edittext on the Apartment selection
       //Setting on item select listener on the profile type spinner
       profile_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position==1){
                   apt_no.setEnabled(true);
               }else{
                   apt_no.setEnabled(false);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        //Setting on item selected listener on region spinner
        //To populate the district spinner
        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.A_district,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==1){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.BA_districts,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==2){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.GA_districts,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                }else if (position==3){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.C_districts,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);

                }else if (position==4){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.E_district,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==5){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.N_districts,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                }else if (position==6){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.W_district,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==7){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.UE_districts,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==8) {
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.UW_districts, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                } else if (position==9) {
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.V_districts, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Setting on item selected listener on district spinner
        // To populate the suburb spinner
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (district.getSelectedItem().equals("Adansi North")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.adansi_north_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);

                } else if (district.getSelectedItem().equals("Afigya-Kwabre")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.afigya_kwabre_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Asunafo North Municipal")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.asunafo_north_muni_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Asunafo South")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.asunafo_south_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("GA Central")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.GA_central_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("Accra Metropolitan")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Accra_Metro_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Ashaiman Municipal")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Ashiaman_Muni_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Abura/Asebu/Kwamankese")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.abura_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Agona East")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.agona_east_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("Jirapa")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.jirapa_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Lambussie Karni")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.lambussie_karni_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Bawku Municipal")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.bawku_muni_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Bolgatanga Municipal")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Bolga_muni_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("Bunkpurugu-Yunyo")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.bunkp_yunyoo_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }   else if (district.getSelectedItem().equals("Central Gonja")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.central_gonja_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("Akuapim South")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.akuapim_south_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }  else if (district.getSelectedItem().equals("Akuapim North")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.akuapim_north_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Ahanta West")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.ahanta_west_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Aowin/Suaman")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.aowin_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Adaklu")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.adaklu_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                } else if (district.getSelectedItem().equals("Afadjato South")){
                    adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.afadjato_suburbs, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    suburb.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Initializing the calendar
        mycalendar = Calendar.getInstance();


        //Date Time picker Dialog button
         date_pick = (Button) findViewById(R.id.pick_date);



       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
              final_dob = updatelabel();
            }
        };

        //setting onclick on edittext
        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this,date,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Context c =this;


        //Onclick listener for next button
        next = (Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the date of birth in string


                //Mapping Edittext fields
                f_name= (EditText) findViewById(R.id.f_name);
                l_name = (EditText) findViewById(R.id.l_name);
                address = (EditText) findViewById(R.id.address);
                email = (EditText) findViewById(R.id.email);
                bin_no = (EditText) findViewById(R.id.bin_no);
                mobile_no = (EditText) findViewById(R.id.Mmno);
                dob = (EditText) findViewById(R.id.dob);
                id_no =(EditText) findViewById(R.id.n_id);
                profiletype_no = (EditText) findViewById(R.id.apt_store_no);
                tele = (EditText) findViewById(R.id.tele2);
                comment = (EditText) findViewById(R.id.comment);

                //Getting Strings from EditText Fields
                first_n= f_name.getText().toString();
                last_n = l_name.getText().toString();
                Address= address.getText().toString();
                Email = email.getText().toString();
                MmNo = mobile_no.getText().toString();
                Bin_No = bin_no.getText().toString();

                String selected_item_profile= profile_type.getSelectedItem().toString();



                String loc_complete = getcurrentloc();
                String[] loc_split =  loc_complete.split(",");




                if (TextUtils.isEmpty(first_n)|| TextUtils.isEmpty(last_n)|| TextUtils.isEmpty(Address)||TextUtils.isEmpty(Email)){

                    Toast.makeText(getApplicationContext(),"Please Fill All Fields",Toast.LENGTH_LONG).show();
                }else {
                    Intent s = new Intent(v.getContext(), summary.class);
                    s.putExtra("First_name", first_n);
                    s.putExtra("Last_name", last_n);
                    s.putExtra("Nation_id_type", id_type.getSelectedItem().toString());
                    s.putExtra("Nation_id_no", id_no.getText().toString());
                    s.putExtra("address", Address);
                    s.putExtra("region",region.getSelectedItem().toString() );
                    s.putExtra("district", district.getSelectedItem().toString());
                    s.putExtra("bin",Bin_No);
                    s.putExtra("suburb", suburb.getSelectedItem().toString());
                    s.putExtra("profile_type", profile_type.getSelectedItem().toString());
                    s.putExtra("Apt_store_No", profiletype_no.getText().toString());
                    s.putExtra("Telephone", tele.getText().toString());
                    s.putExtra("pf_day", pf_day.getSelectedItem().toString());
                    s.putExtra("pf_time", pf_time.getSelectedItem().toString());
                    s.putExtra("Comment", comment.getText().toString());
                    s.putExtra("email", Email);
                    s.putExtra("loc_string",loc_complete);
                    s.putExtra("Longitude",loc_split[0]);
                    s.putExtra("Latitude",loc_split[1]);
                    s.putExtra("date_of_birth",dob.getText().toString());
                    s.putExtra("mobile_no", MmNo);

                    startActivity(s);
                }

            }
        });














    }

    private String getcurrentloc() {



        EditText loc = (EditText) findViewById(R.id.location);

        //Getting GPS tracker Location
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        String lat = Double.toString(latitude);
        double longitude = gps.getLongitude();
        String longi = Double.toString(longitude);

        String loc_shownin_app = longi+","+lat;
        loc.setText(loc_shownin_app);

        return loc_shownin_app;






    }

    private String updatelabel() {
        dob = (EditText) findViewById(R.id.dob);

        //Formatting date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        String final_date = dateFormat.format(mycalendar.getTime());
        dob.setText(final_date);
        return final_date;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }
}

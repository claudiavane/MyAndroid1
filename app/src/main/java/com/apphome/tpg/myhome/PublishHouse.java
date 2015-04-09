package com.apphome.tpg.myhome;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.apphome.tpg.myhome.data.PublishContract;


public class PublishHouse extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_house);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publish_house, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        View rootView;
        private static final Uri URI_PUBLISH = Uri.parse("content://com.apphome.tpg.myhome/publish");

        private Uri uri;
        private Cursor c;

        Spinner spCities;
        Spinner spTypeHouse;
        Spinner spTypeRental;
        Spinner spZone;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_publish_house, container, false);
            loadSpinner();

            Button b = (Button) rootView.findViewById(R.id.btn_register);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(getActivity(), PublishHouse.class);
                    //getActivity().startActivity(intent);
                    insertData();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            return rootView;
        }

        private void insertData(){
            //Insertar datos
            ContentResolver CR = getActivity().getContentResolver();

            String rentalType= spTypeRental.getSelectedItem().toString();
            String houseType= spTypeHouse.getSelectedItem().toString();
            String city= spCities.getSelectedItem().toString();
            String zone= spZone.getSelectedItem().toString();

            EditText addr = (EditText) rootView.findViewById(R.id.text_address);
            String address = addr.getText().toString();

            EditText pr = (EditText) rootView.findViewById(R.id.text_price);
            String price = pr.getText().toString();

            EditText la = (EditText) rootView.findViewById(R.id.text_landArea);
            String landArea = la.getText().toString();

            EditText ba = (EditText) rootView.findViewById(R.id.text_buildArea);
            String buildArea = ba.getText().toString();

            EditText desc = (EditText) rootView.findViewById(R.id.text_description);
            String description = desc.getText().toString();

            // Insertamos
            uri = CR.insert(URI_PUBLISH, setValues(houseType, rentalType, city, zone, Integer.parseInt(price), address,
                    Integer.parseInt(landArea), Integer.parseInt(buildArea), description));
            Log.d("REGISTRO INSERTADO", uri.toString());
        }

        private ContentValues setValues(String houseType, String rentalType,
                                        String city, String zone, Integer price, String address, Integer landArea,
                                        Integer buildArea, String description) {
            ContentValues valores = new ContentValues();
            valores.put(PublishContract.Publish.COL_HOUSE_TYPE, houseType);
            valores.put(PublishContract.Publish.COL_RENTAL_TYPE, rentalType);
            valores.put(PublishContract.Publish.COL_CITY,city);
            valores.put(PublishContract.Publish.COL_ZONE, zone);
            valores.put(PublishContract.Publish.COL_ADDRESS, address);
            valores.put(PublishContract.Publish.COL_PRICE, price);
            valores.put(PublishContract.Publish.COL_LAND_AREA, landArea);
            valores.put(PublishContract.Publish.COL_BUILD_AREA, buildArea);
            valores.put(PublishContract.Publish.COL_DESCRIPTION, description);

            return valores;
        }

        public void loadSpinner(){
            spCities = (Spinner) rootView.findViewById(R.id.sp_city );
            spTypeHouse = (Spinner) rootView.findViewById(R.id.sp_typeHouse );
            spTypeRental = (Spinner) rootView.findViewById(R.id.sp_typeRental);
            spZone = (Spinner) rootView.findViewById(R.id.sp_zone);

            // Create an ArrayAdapter using the string array and a default spinner layout
            //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_selectable_list_item , R.array.cities);
            ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapterTypeHouse = ArrayAdapter.createFromResource(getActivity(), R.array.houseType, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapterTypeRental = ArrayAdapter.createFromResource(getActivity(), R.array.rentalType, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapterZone = ArrayAdapter.createFromResource(getActivity(), R.array.zone, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTypeHouse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTypeRental.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spCities.setAdapter(adapterCities);
            spTypeHouse.setAdapter(adapterTypeHouse);
            spTypeRental.setAdapter(adapterTypeRental);
            spZone.setAdapter(adapterZone);

        }
    }
}

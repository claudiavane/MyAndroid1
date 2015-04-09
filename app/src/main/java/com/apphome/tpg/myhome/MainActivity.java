package com.apphome.tpg.myhome;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_money) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        View rootView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            loadSpinner();

            Button b = (Button) rootView.findViewById(R.id.btn_publish);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PublishHouse.class);
                    getActivity().startActivity(intent);
                }
            });

            Button btnSearch = (Button) rootView.findViewById(R.id.btn_search);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ResultSearchListActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            return rootView;
        }

        public void loadSpinner(){
            Spinner spCities = (Spinner) rootView.findViewById(R.id.citySpinner);
            Spinner spTypeHouse = (Spinner) rootView.findViewById(R.id.typeHouseSpinner);

            // Create an ArrayAdapter using the string array and a default spinner layout
            //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_selectable_list_item , R.array.cities);
            ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapterTypeHouse = ArrayAdapter.createFromResource(getActivity(), R.array.houseType, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTypeHouse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spCities.setAdapter(adapterCities);
            spTypeHouse.setAdapter(adapterTypeHouse);

        }
    }
}

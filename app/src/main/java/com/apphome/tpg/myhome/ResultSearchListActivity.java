package com.apphome.tpg.myhome;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.apphome.tpg.myhome.data.PublishContract;


public class ResultSearchListActivity extends ListActivity {

    private Cursor cursor;
    private static final Uri URI_PUBLISH = Uri.parse("content://com.apphome.tpg.myhome/publish");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_result_search_list);

        ContentResolver CR = getContentResolver();

        // Recuperamos todos los registros de la tabla
        String[] valores_recuperar = {PublishContract.Publish._ID,
                PublishContract.Publish.COL_HOUSE_TYPE,
                PublishContract.Publish.COL_RENTAL_TYPE,
                PublishContract.Publish.COL_CITY,
                PublishContract.Publish.COL_ZONE,
                PublishContract.Publish.COL_PRICE,
                PublishContract.Publish.COL_ADDRESS,
                PublishContract.Publish.COL_LAND_AREA,
                PublishContract.Publish.COL_BUILD_AREA,
                PublishContract.Publish.COL_DESCRIPTION
        };
        cursor = CR.query(URI_PUBLISH, valores_recuperar, null, null, null);
        cursor.moveToFirst();

        //Añadimos los datos al Adapter y le indicamos donde dibujar cada dato en la fila del Layout
        String[] desdeEstasColumnas = {PublishContract.Publish._ID, PublishContract.Publish.COL_HOUSE_TYPE, PublishContract.Publish.COL_ADDRESS};
        int[] aEstasViews = {R.id.imageView_imagen, R.id.textView_superior, R.id.textView_inferior};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_result_search_list, cursor, desdeEstasColumnas, aEstasViews, 0);

        ListView listado = getListView();
        listado.setAdapter(adapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_search_list, menu);
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


    @Override
    public void onListItemClick(ListView lista, View view, int posicion, long id) {
        // Hacer algo cuando un elemento de la lista es seleccionado
        //TextView textoTitulo = (TextView) view.findViewById(R.id.textView_superior);

        CharSequence texto = "Costo del inmueble: " + cursor.getString(5)+ "$us. \n" +
                "Descripcion: " + cursor.getString(9);
        Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG).show();
    }


}

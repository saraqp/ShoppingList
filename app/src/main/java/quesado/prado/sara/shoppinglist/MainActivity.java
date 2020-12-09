package quesado.prado.sara.shoppinglist;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import quesado.prado.sara.shoppinglist.adapters.ProductAdapter;
import quesado.prado.sara.shoppinglist.data.DataBaseHandler;
import quesado.prado.sara.shoppinglist.model.Product;

public class MainActivity extends AppCompatActivity  implements ProductAdapter.OnButtonClickedListener {
    private DataBaseHandler db;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp();
            }
        });
        db=new DataBaseHandler(this);
        products=db.getAllProducts();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new ProductAdapter(this,products,this);
        recyclerView.setAdapter(adapter);
    }
    private void createPopUp(){
        builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.popup,null);
        builder.setView(view);

        dialog=builder.create();
        dialog.show();

        final EditText productName=view.findViewById(R.id.popupProductName);
        final EditText productQuantity=view.findViewById(R.id.popupProductQuantity);
        Button saveButton=view.findViewById(R.id.popupSaveProductButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(productName.getText()) && !TextUtils.isEmpty(productQuantity.getText())){
                    saveProductDb(productName.getText().toString(),productQuantity.getText().toString());
                }
            }
        });
    }

    private void saveProductDb(String name, String quantity) {
        Product p=new Product(name,quantity);
        long id=db.addProduct(p);
        p.setId(id);
        if (id==-1){
            Snackbar.make(recyclerView,"Error al añadir producto",Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(recyclerView,"Producto añadido",Snackbar.LENGTH_LONG).show();
            products.add(0,p);
            adapter.notifyItemInserted(0);
        }
        dialog.dismiss();
    }


    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public void onButtonClicked(View view, int position) {
        if (view.getId()==R.id.editProductIcon){
            Toast.makeText(this,"Edit",Toast.LENGTH_SHORT).show();
            editProduct(position);
        } else if (view.getId()==R.id.deleteProductIcon){
            deleteProduct(position);
        }
    }

    private void deleteProduct(int position) {
        Product p=products.get(position);
        db.deleteProduct(p);
        products.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeRemoved(position,products.size());
        Toast.makeText(this,"Deleted "+p.getNombre(),Toast.LENGTH_SHORT).show();
    }

    private void editProduct(int position) {
        final Product p=products.get(position);

        builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.popup,null);

        builder.setView(view);
        dialog=builder.create();
        dialog.show();

        final EditText productName=view.findViewById(R.id.popupProductName);
        final EditText productQuantity=view.findViewById(R.id.popupProductQuantity);

        Button saveButton=view.findViewById(R.id.popupSaveProductButton);
        productName.setText(p.getNombre());
        productQuantity.setText(p.getCantidad());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(productName.getText())&& !TextUtils.isEmpty(productQuantity.getText())){
                    p.setNombre(productName.getText().toString());
                    p.setCantidad(productQuantity.getText().toString());
                    db.updateProduct(p);
                    adapter.notifyItemChanged(position);
                }

                dialog.dismiss();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
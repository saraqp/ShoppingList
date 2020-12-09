package quesado.prado.sara.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import quesado.prado.sara.shoppinglist.model.Product;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME="shoppingList.db";

    private static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+ProductContract.ProductEntry.TABLE_NAME+" ("+
                    ProductContract.ProductEntry._ID+" INTEGER PRIMARY KEY,"+
                    ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME+" TEXT,"+
                    ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY+" TEXT)";
    private static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS "+ ProductContract.ProductEntry.TABLE_NAME;

    private Context context;

    public DataBaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long addProduct(Product p){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,p.getNombre());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,p.getCantidad());

        long id=db.insert(ProductContract.ProductEntry.TABLE_NAME,null,contentValues);
        db.close();
        return id;
    }
    public  Product getProduct(long id){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection={
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY
        };
        String selection= ProductContract.ProductEntry._ID+" =?";
        String[] selectionArgs={String.valueOf(id)};

        Cursor cursor=db.query(
                ProductContract.ProductEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        Product p=null;
        if (cursor.moveToFirst()){
            String name=cursor.getString(0);
            String quantity=cursor.getString(1);
            p=new Product(name,quantity,id);
        }

        cursor.close();
        db.close();
        return p;
    }
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> products=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String[] projection={
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY
        };

        Cursor cursor=db.query(
                ProductContract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()){
            do {
                long id=cursor.getLong(0);
                String name=cursor.getString(1);
                String quantity=cursor.getString(2);

                Product p=new Product(name,quantity,id);
                products.add(p);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    public int updateProduct(Product p){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,p.getNombre());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,p.getCantidad());

        String selection= ProductContract.ProductEntry._ID+"= ?";
        String[] selectionArgs={String.valueOf(p.getId())};

        int res=db.update(ProductContract.ProductEntry.TABLE_NAME,contentValues,selection,selectionArgs);

        db.close();
        return res;
    }
    public int deleteProduct(Product p){
        SQLiteDatabase db=getWritableDatabase();
        String selection= ProductContract.ProductEntry._ID+"=?";
        String[] selectionArgs={String.valueOf(p.getId())};

        int res=db.delete(ProductContract.ProductEntry.TABLE_NAME,selection,selectionArgs);

        db.close();
        return res;
    }
}


package quesado.prado.sara.shoppinglist.data;

import android.provider.BaseColumns;

public class ProductContract {
    public ProductContract() {
    }
    public static class ProductEntry implements BaseColumns{
        public static final String TABLE_NAME="products";
        public static final String COLUMN_NAME_PRODUCT_NAME="name";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY="quantity";
    }
}

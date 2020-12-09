package quesado.prado.sara.shoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import quesado.prado.sara.shoppinglist.R;
import quesado.prado.sara.shoppinglist.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> products;
    private OnButtonClickedListener listener;

    public ProductAdapter(Context context, List<Product> products, OnButtonClickedListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_row,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        final Product product=products.get(position);
        holder.productName.setText(product.getNombre());
        holder.productQuantity.setText(product.getCantidad());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,position);
            }
        });
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName,productQuantity;
        public ImageView editProduct,deleteProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productNameTextView);
            productQuantity=itemView.findViewById(R.id.productQuantityTextView);

            editProduct=itemView.findViewById(R.id.editProductIcon);
            deleteProduct=itemView.findViewById(R.id.deleteProductIcon);
        }
    }
    public interface OnButtonClickedListener{
        void onButtonClicked(View view,int position);
    }
}

package com.example.productprojectprm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productprojectprm.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private static List<Product> mListProduct;
    private List<Product> mListProductOld;

    public ProductAdapter(List<Product> mListProduct  ) {
        this.mListProduct = mListProduct;
        this.mListProductOld = mListProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false   );
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
            return;
        }
        holder.productName.setText(String.valueOf(product.getProductName()));
        holder.productPrice.setText(String.valueOf(product.getPrice()) + "đ");
        Glide.with(holder.itemView.getContext())
                .load(product.getImgId())
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        if(mListProduct != null){
            return mListProduct.size();
        }
        return 0;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        private final TextView productName, productPrice;
        private final ImageView productImage;

        public ProductViewHolder(@NonNull View itemView ) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.price);
            productImage = itemView.findViewById(R.id.img_product);

        }
    }

}

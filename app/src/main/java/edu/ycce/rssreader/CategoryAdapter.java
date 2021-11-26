package edu.ycce.rssreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import retrofit.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> mListCategory;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private IClickItemCategoryListener iClickItemCategoryListener;

    public CategoryAdapter(  List<Category> mListCategory,IClickItemCategoryListener listener1 ){

        this.mListCategory = mListCategory;
        this.iClickItemCategoryListener = listener1;

    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_category, viewGroup,false )  ;
        return new CategoryHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position){
        final Category category = mListCategory.get( position );
        final String url = category.getRssLink().toString();
        if(category == null){
            return;
        }

        viewBinderHelper.bind( categoryHolder.swipeRevealLayout, String.valueOf( category.getCategoriesId() ) );
        categoryHolder.categoryName.setText(category.getCategoriesTitle());
        categoryHolder.layoutDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mListCategory.remove( categoryHolder.getAdapterPosition() );
                notifyItemRemoved(categoryHolder.getAdapterPosition()  );
            }

        } );
        categoryHolder.layoutCategoryName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                iClickItemCategoryListener.onClickItemCategory( url );

            }


        } );

    }


    @Override
    public int getItemCount(){
        if(mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }



    public class CategoryHolder extends RecyclerView.ViewHolder{
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layoutDelete;
        private LinearLayout layoutCategoryName;
        private TextView categoryName;
        public CategoryHolder(@NonNull View itemView){
            super( itemView );
            swipeRevealLayout = itemView.findViewById( R.id.swipeRevealLayout );
            layoutDelete = itemView.findViewById( R.id.layout_delete );
            categoryName = itemView.findViewById( R.id.tv_name );
            layoutCategoryName = itemView.findViewById( R.id.layout_name_category );
        }
    }
}

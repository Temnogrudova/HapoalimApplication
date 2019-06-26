package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.R;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.FilmsInterface;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.SharedPreference;
import java.util.List;
import jp.wasabeef.glide.transformations.CropTransformation;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.BASE_IMAGE_URL;

/**
 * Created by lirons on 11/09/2017.
 */

public class FilmsListAdapter extends RecyclerView.Adapter<FilmsListAdapter.BindingHolder> implements FilmsInterface {
    private List<Film> mFilms;
    private IFilmClicked mListener;
    private SharedPreference sharedPreference;
    private Context mContext;
    private Constants.SCREEN mType;
    public interface IFilmClicked {
        void onFilmClick(Film currentFilm);
    }

    public FilmsListAdapter(List<Film> films, Context context, IFilmClicked filmListener, Constants.SCREEN type) {
        mFilms = films;
        mContext = context;
        mListener = filmListener;
        sharedPreference = new SharedPreference();
        mType = type;
    }

    @Override
    public FilmsListAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_film_view_item, parent, false);

        FilmsListAdapter.BindingHolder holder = new FilmsListAdapter.BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        holder.setClickedListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FilmsListAdapter.BindingHolder holder, final int position) {
        Film film = mFilms.get(position);
        ImageView image = holder.itemView.findViewById(R.id.image);
        Glide.with(image.getContext())
                .load(BASE_IMAGE_URL + film.getPosterPath()).bitmapTransform(new CropTransformation(image.getContext()))
                .into(image);
        TextView title = holder.itemView.findViewById(R.id.title);
        title.setText(film.getTitle());
        final ImageButton ibLike = holder.itemView.findViewById(R.id.ib_like);
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer) ibLike.getTag() == R.drawable.ic_unlike) {
                        sharedPreference.addFavorite(mContext, mFilms.get(position));
                        setIbLikeResource(ibLike, R.drawable.ic_like);
                    } else {
                        sharedPreference.removeFavorite(mContext, mFilms.get(position));
                        setIbLikeResource(ibLike, R.drawable.ic_unlike);
                        if (mType.name().equals(Constants.SCREEN.ACTIVITY_FAVOURITE_FILMS.name()))
                        {
                            mFilms.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
            if (checkFavoriteItem(mFilms.get(position))) {
                setIbLikeResource(ibLike, R.drawable.ic_like);

            } else {
                setIbLikeResource(ibLike, R.drawable.ic_unlike);
            }

        holder.getBinding().executePendingBindings();
    }

    private void setIbLikeResource(ImageButton imageButton, int resId) {
        imageButton.setImageResource(resId);
        imageButton.setTag(resId);
    }

    private boolean checkFavoriteItem(Film checkImage) {
        boolean check = false;
        List<Film> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            for (Film image : favorites) {
                if (image.equals(checkImage)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public int getItemCount() {
        if (mFilms == null) {
            return 0;
        }
        return mFilms.size();
    }

    @Override
    public void onFilmClick(int position) {
        Film filmItem = mFilms.get(position);
        if (mListener != null) {
            mListener.onFilmClick(filmItem);
        }
        notifyDataSetChanged();
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewDataBinding binding;
        private FilmsInterface mClickedListener;

        public BindingHolder(View rowView) {
            super(rowView);
            rowView.setOnClickListener(this);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {

            mClickedListener.onFilmClick(getAdapterPosition());

        }

        public void setClickedListener(FilmsInterface clickedListener) {
            mClickedListener = clickedListener;
        }
    }
}

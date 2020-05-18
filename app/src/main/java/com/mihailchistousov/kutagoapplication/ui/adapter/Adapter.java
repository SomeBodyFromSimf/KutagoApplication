package com.mihailchistousov.kutagoapplication.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.mvp.model.Film;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Film> films = new ArrayList<>();
    private List<Film> savedList = new ArrayList<>();
    public boolean isFilter = false;
    private final MainView mainView;
    private Context context;
    private MyViewHolder footer = null;

    public Adapter(Context context, MainView mainView) {
        this.mainView = mainView;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Film currentBindFilm = films.get(position);
        holder.bind(currentBindFilm);
        if(currentBindFilm.getId()<0)
            mainView.getFilms();

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void addFilms(List<Film> newFilms) {
        deleteFooter();
        films.addAll(newFilms);
        addFooter();
        notifyDataSetChanged();
    }

    public void clearList() {
        films.clear();
    }

    public void addFooter() {
        Film progressFilm = new Film();
        progressFilm.setId(-1);
        films.add(progressFilm);
    }

    public void deleteFooter() {
        if(getItemCount()<20) return;
        Film progressFilm = films.get(getItemCount()-1);
        if(progressFilm.getId() <0) {
            films.remove(progressFilm);
            footer = null;
        }

    }

    public void changeProgressItem() {
        if(footer != null) {
            Film footerFilm = footer.getCurrent_film();
            footerFilm.setId(-2);
            footer.bind(footerFilm);
        }
    }

    public void returnAll() {
        if(!isFilter) return;
        films.clear();
        films.addAll(savedList);
        isFilter = false;
        savedList.clear();
        Completable.fromAction(this::notifyDataSetChanged).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }

    @SuppressLint("CheckResult")
    public void filterListByTitle(String title) {
        List<Film> filer_list = new ArrayList<>();
        Observable.fromIterable((!isFilter)?films:savedList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(film -> film.getTitle()!=null && !film.getTitle().isEmpty() && film.getTitle().toLowerCase().contains(title.toLowerCase()))
                .doOnComplete(() -> {
                    if(!isFilter) savedList.addAll(films);
                    films.clear();
                    isFilter = true;
                    if(!filer_list.isEmpty()) {
                        films.addAll(filer_list);
                        mainView.hideNotFoundLayout();
                    } else
                        mainView.showNotFoundLayout(title);
                    notifyDataSetChanged();
                })
                .subscribe(filer_list::add);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Film current_film;

        @BindView(R.id.item_container)
        public ConstraintLayout itemContainer;
        @BindView(R.id.photo_film)
        public ImageView image;
        @BindView(R.id.title_film)
        public TextView title;
        @BindView(R.id.progress_bar)
        public ProgressBar progressBar;
        @BindView(R.id.repeat_button)
        public Button repeatButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        private void bind(Film film) {
            setCurrent_film(film);
            if(film.getId() == -1) {
                itemContainer.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                footer = this;
            } else if(film.getId() == -2) {
                itemContainer.setVisibility(View.GONE);
                repeatButton.setVisibility(View.VISIBLE);
                repeatButton.setOnClickListener(this::repeat);
                footer = this;
            } else {
                progressBar.setVisibility(View.GONE);
                repeatButton.setVisibility(View.GONE);
                itemContainer.setVisibility(View.VISIBLE);
                itemContainer.setOnClickListener(this::click);
                itemContainer.setOnLongClickListener(this::longClick);
                Glide.with(context)
                        .load(film.getPoster().getImage_url())
                        .into(image);
                title.setText(film.getTitle());
            }

        }
        private void click(View view) {
            Toast.makeText(context,current_film.getTitle(),Toast.LENGTH_SHORT).show();
        }

        private void repeat(View v) {
            mainView.getFilms();
        }

        private boolean longClick(View view) {
            //TODO Add to LikeListInDB
            return false;
        }

        public Film getCurrent_film() {
            return current_film;
        }

        public void setCurrent_film(Film current_film) {
            this.current_film = current_film;
        }
    }
}

package br.g3.piapp2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MateriaViewHolder extends RecyclerView.ViewHolder {

    public ImageView materiaIconImageView;
    public TextView nomeListaTextView;
    public TextView horarioListaTextView;
    public MateriaViewHolder(View raiz){
        super(raiz);
        materiaIconImageView = raiz.findViewById(R.id.materiaIconImageView);
        nomeListaTextView = raiz.findViewById(R.id.nomeListaTextView);
        horarioListaTextView = raiz.findViewById(R.id.horarioListaTextView);
    }

    /*
    public void bind(final Materia materia, final OnItemClickListener listener){

    }
    */

}

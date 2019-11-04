package br.g3.piapp2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PresencaViewHolder extends RecyclerView.ViewHolder{
    public TextView materiaTextView;
    public TextView dataTextView;
    public TextView estadoTextView;
    public PresencaViewHolder(View raiz){
        super(raiz);
        materiaTextView = raiz.findViewById(R.id.materiaTextView);
        dataTextView = raiz.findViewById(R.id.dataTextView);
        estadoTextView = raiz.findViewById(R.id.estadoTextView);
    }
}

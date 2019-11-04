package br.g3.piapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaViewHolder> {
    private Context context;

    private List<Materia> materias;
    public MateriaAdapter(Context context, List<Materia> materias) {
        this.context = context;
        this.materias = materias;
    }

    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.list_item,parent,false);
        return new MateriaViewHolder(raiz);
    }

    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        Materia materia = this.materias.get(position);
        holder.materiaIconImageView.setId(materia.getIconId());
        holder.nomeListaTextView.setText(materia.getNome());
        holder.horarioListaTextView.setText(null);
    }
    public int getItemCount() {
        return materias.size();
    }
}

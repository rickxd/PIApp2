package br.g3.piapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PresencaAdapter extends RecyclerView.Adapter<PresencaViewHolder> {
    private Context context;

    private List<Presenca> presencas;
    public PresencaAdapter(Context context, List<Presenca> presencas) {
        this.context = context;
        this.presencas = presencas;
    }

    public PresencaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.presencas_item,parent,false);
        return new PresencaViewHolder(raiz);
    }

    public void onBindViewHolder(@NonNull PresencaViewHolder holder, int position) {
        Presenca presenca = this.presencas.get(position);
        holder.materiaTextView.setText(presenca.getMateria().getNome());
        holder.dataTextView.setText(presenca.getData().toString());
        holder.estadoTextView.setText(presenca.getPresente());
    }
    public int getItemCount() {
        return presencas.size();
    }
}

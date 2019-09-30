package br.g3.piapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MateriaArrayAdapter extends ArrayAdapter<Materia> {
    public MateriaArrayAdapter (Context context, List<Materia> materias){
        super (context, -1, materias);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Materia materiaDaVez = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.list_item, parent, false);
        ImageView materiaIconImageView = view.findViewById(R.id.materiaIconImageView);
        TextView nomeListaTextView = view.findViewById(R.id.nomeListaTextView);
        TextView horarioListaTextView = view.findViewById(R.id.horarioListaTextView);
        materiaIconImageView.setImageResource(materiaDaVez.getIconId());
        nomeListaTextView.setText(materiaDaVez.getNome());
        horarioListaTextView.setText(materiaDaVez.getHorario());
        return view;
    }
}

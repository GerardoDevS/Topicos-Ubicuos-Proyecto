package com.lics.proyectou2;

import static com.lics.proyectou2.R.id.apellidoP;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private int resource;
    private ArrayList<Usuarios> usuariosList;

    public Adapter(ArrayList<Usuarios> usuariosList, int resource) {
        this.usuariosList = usuariosList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Usuarios usuario = usuariosList.get(position);
        holder.textViewApellidoP.setText(usuario.getApellidoP());
        holder.textViewApellidoM.setText(usuario.getApellidoM());
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewNC.setText(usuario.getNc());
        holder.textViewEstadoActual.setText(usuario.getEstadoActual());

    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewApellidoP;
        private TextView textViewApellidoM;
        private TextView textViewNombre;
        private TextView textViewNC;
        private TextView textViewEstadoActual;

        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.textViewApellidoP = (TextView) view.findViewById(R.id.apellidoP);
            this.textViewApellidoM = (TextView) view.findViewById(R.id.apellidoM);
            this.textViewNombre = (TextView) view.findViewById(R.id.nombreAlumno);
            this.textViewNC = (TextView) view.findViewById(R.id.numeroControl);
            this.textViewEstadoActual = (TextView) view.findViewById(R.id.estado);

        }


    }
}

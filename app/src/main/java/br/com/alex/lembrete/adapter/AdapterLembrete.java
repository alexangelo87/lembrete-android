package br.com.alex.lembrete.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alex.lembrete.R;
import br.com.alex.lembrete.model.Lembrete;

public class AdapterLembrete extends RecyclerView.Adapter<AdapterLembrete.MyViewHolder> {
    List<Lembrete> lembretes = new ArrayList<>();
    public AdapterLembrete(List<Lembrete> lembretes){
        this.lembretes = lembretes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_lembrete,viewGroup,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Lembrete lembrete = lembretes.get(i);
        myViewHolder.tarefa.setText(lembrete.getTarefa());
        myViewHolder.status.setText(lembrete.getStatus());
    }

    @Override
    public int getItemCount() {
        return this.lembretes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tarefa;
        TextView status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tarefa = (TextView) itemView.findViewById(R.id.textViewTarefa);
            status = (TextView) itemView.findViewById(R.id.textViewStatus);
        }
    }
}

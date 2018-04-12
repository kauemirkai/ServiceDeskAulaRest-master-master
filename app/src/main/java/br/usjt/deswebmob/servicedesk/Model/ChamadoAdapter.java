package br.usjt.deswebmob.servicedesk.Model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.deswebmob.servicedesk.R;

/**
 * Created by arqdsis on 21/03/2018.
 */

public class ChamadoAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Chamado> chamados;
    private ArrayList<Fila> filas;

    public ChamadoAdapter(Context context, ArrayList<Chamado> chamados) {
        this.context = context;
        this.chamados = chamados;
        try {
            this.filas=ChamadoNetwork.getFilas(null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return chamados.size();
    }

    @Override
    public Object getItem(int position) {
        return chamados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view==null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_chamado, parent, false);


            ImageView imagem = (ImageView) view.findViewById(R.id.imagem_fila);
            TextView numero = (TextView) view.findViewById((R.id.numero_status_chamado));
            TextView descricao = (TextView) view.findViewById((R.id.descricao_chamado));
            TextView datas = (TextView) view.findViewById((R.id.abertura_fechamento_chamado));
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.setDatas(datas);
            viewHolder.setDescricao(descricao);
            viewHolder.setImagem(imagem);
            viewHolder.setNumero(numero);
            view.setTag(viewHolder);

        }
        Chamado chamado = chamados.get(position);

        ViewHolder viewHolder= (ViewHolder) view.getTag();

        //viewHolder.getImagem().setImageDrawable(Util.getDrawabkeDinamic(context,chamado.getFila().getFigura()));
        viewHolder.getImagem().setImageBitmap(filas.get(filas.indexOf(getFila(chamado.getFila().getId()))).getImagem());
        viewHolder.getNumero().setText(String.format("numero: %d - status: %s",chamado.getNumero(),chamado.getStatus()));
        viewHolder.getDatas().setText(String.format("abertura:%tD - fechamento:%tD", chamado.getDataAbertura(), chamado.getDataFechamento()));
        viewHolder.getDescricao().setText(chamado.getDescricao());






        return view;

    }
    private int getFila(int id){
        for(int i = 0 ; i <filas.size(); i++){
            if(filas.get(i).getId()==id){
                return i;

            }

        }
        return -1;
    }

}

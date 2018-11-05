package iuh.edu.vn.navigationdrawertest1.custom_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class Truyen_List_Custom_Adapter  extends ArrayAdapter<Truyen> {
    private Context context;
    private int resource;
    private List<Truyen> objects;

    public Truyen_List_Custom_Adapter( @NonNull Context context, int resource, @NonNull List<Truyen> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(getContext()).inflate(R.layout.truyen_list_custom_adapter,parent,false);
        Truyen truyen=objects.get(position);
        TextView txtTenTruyen=(TextView)convertView.findViewById(R.id.txtTenTruyen);
        TextView txtNoiDung=(TextView)convertView.findViewById(R.id.txtNoiDung);
        TextView txtTacGia=(TextView)convertView.findViewById(R.id.txtTacGia);
        TextView txtNgayTao = (TextView)convertView.findViewById(R.id.txtNgayTao);
        txtTenTruyen.setText(truyen.getTieuDe());
        txtNoiDung.setText(truyen.getNoiDung());
        txtTacGia.setText(truyen.getTacGia());
        txtNgayTao.setText(truyen.getNgayTao());
        return convertView;
    }
}

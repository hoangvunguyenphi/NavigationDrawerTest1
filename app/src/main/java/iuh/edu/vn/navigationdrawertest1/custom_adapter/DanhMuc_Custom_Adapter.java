package iuh.edu.vn.navigationdrawertest1.custom_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class DanhMuc_Custom_Adapter extends ArrayAdapter<DanhMuc>{
    private Context context;
    private int resource;
    private List<DanhMuc> objects;

    public DanhMuc_Custom_Adapter( @NonNull Context context, int resource,  @NonNull List<DanhMuc> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView,  @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.danhmuc_custom_adapter,parent,false);
        TextView txtTenDanhMuc=(TextView) convertView.findViewById(R.id.txtTenDanhMuc);
        DanhMuc dm= objects.get(position);
        txtTenDanhMuc.setText(dm.getTenDanhMuc());
        return convertView;
    }
}

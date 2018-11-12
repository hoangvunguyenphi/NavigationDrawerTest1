package iuh.edu.vn.navigationdrawertest1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.ChiTietTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;

public class Bookmark_List_Fragment  extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private List<Truyen> lisTruyen=null;
    Truyen_List_Custom_Adapter truyen_list_custom_adapter;
    SearchView searchView;
    private static final String BOOKMARK_TABLE ="BookmarkTB";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_bookmark,container,false);
        lisTruyen=new ArrayList<>();
        MyDatabaseHelper databaseHelper=new MyDatabaseHelper(getContext());
//        long d = databaseHelper.addStory(truyen);
//        if (d > 0) {
//            Toast.makeText(getContext(), "Thêm thành công !!", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(getContext(), "Đã tồn tại trong danh sách !!", Toast.LENGTH_SHORT).show();
//        }
//        databaseHelper.deleteStory(truyen.get_id());
        List<Truyen> list=databaseHelper.getAllStory(BOOKMARK_TABLE);
        lisTruyen.addAll(list);
        Log.d("LOGGGGGGG",lisTruyen.toString());
        truyen_list_custom_adapter=new Truyen_List_Custom_Adapter(getContext(), R.layout.truyen_list_custom_adapter,lisTruyen);
        setListAdapter(truyen_list_custom_adapter);
        return view;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Truyen tr = lisTruyen.get(position);
        Intent intent = new Intent(getActivity(), ChiTietTruyenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedTruyen",tr);
        bundle.putString("activityTruoc","DSBookmark");
        intent.putExtra("dataTruyen",bundle);
        startActivity(intent);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_history,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Nhập tên truyện..");
        searchView.clearFocus();
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        if(newText==null || newText.trim().isEmpty()){
            resetSearch();
            return false;
        }
        ArrayList<Truyen> filterList = new ArrayList<Truyen>(lisTruyen);
        for(Truyen tr : lisTruyen){
            if(!tr.getTieuDe().trim().toLowerCase().contains(newText.toLowerCase().trim())){
                filterList.remove(tr);
            }
        }
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(getContext(),R.layout.truyen_list_custom_adapter,filterList);
        setListAdapter(truyen_list_custom_adapter);
        return true;
    }
    public  void  resetSearch(){
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(getContext(),R.layout.truyen_list_custom_adapter,lisTruyen);
        truyen_list_custom_adapter.notifyDataSetChanged();
        this.setListAdapter(truyen_list_custom_adapter);
    }
}
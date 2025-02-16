package com.labproject.keepmoneyv3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.labproject.keepmoneyv3.R;
import com.labproject.keepmoneyv3.utility.DefaultListViewItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is used to manage the building of the ExpandableListView,
 * used to display confirmed WishLists (and their items) of the logged user.
 *
 * @author Giacomo Detomaso
 * */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final HashMap<String, ArrayList<DefaultListViewItems>> map;
    private final ArrayList<String> titles;

    public ExpandableListViewAdapter(Context context, HashMap <String, ArrayList<DefaultListViewItems>> map, ArrayList<String> titles){
        this.context = context;
        this.map = map;
        this.titles = titles;
    }

    /**
     * This method, that overrides the standard one, is used to get the number of the groups.
     *
     * @return      the number of elements in the group
     * */
    @Override
    public int getGroupCount() {
        return titles.size(); // each title in the ArrayList "titles" technically corresponds to the presence of a wishlist in the database
    }

    /**
     * This method, that overrides the standard one, is used get the number of children of a group.
     *
     * @param groupPosition     the position of a group
     *
     * @return the number of elements of a group's childrrn
     * */
    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(map.get(titles.get(groupPosition))).size(); // the number of children in a title corresponds to how many items there are in the corresponding wishlist
    }

    /**
     * This method, that overrides the standard one, is used to get a specified group in its entirety, from a specified group position.
     *
     * @param groupPosition     the position of a group
     *
     * @return a specific group title
     * */
    @Override
    public String getGroup(int groupPosition) {
        return titles.get(groupPosition);
    }

    /**
     * This method, that overrides the standard one, is used to get a child from a specified group position.
     *
     * @param groupPosition     the position of a group
     * @param childPosition     the position of the child
     *
     * @return a specific children of a group
     * */
    @Override
    public DefaultListViewItems getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.map.get(this.titles.get(groupPosition))).get(childPosition);

    }

    /**
     * This method, that overrides the standard one, is used to get the id of a group from its position in the list.
     * */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * This method, that overrides the standard one, is used to get the position in the list of a child of a group.
     * */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * This method, that overrides the standard one, is used to build the group view of the ExpandableListView row by row.
     * It gets the view as a parameter, it adds all the elements that needs to be displayed and then it return it back.
     *
     * @param groupPosition     the position of a group
     * @param view              the view
     * @param viewGroup         the viewGroup
     *
     * @return      the view of the group
     * */
    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        String title = getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(inflater).inflate(R.layout.fragment_movements_wish_lists_expandable_list_view_title, null);

        }

        TextView txtTile = view.findViewById(R.id.listTitle);
        txtTile.setText(title);


        return view;
    }

    /**
     * This method, that overrides the standard one, is used to build the child view of the ExpandableListView row by row.
     *
     * @param groupPosition     the position of a group
     * @param childPosition     the position of the child
     * @param view              the view
     * @param viewGroup         the viewGroup
     *
     * @return the view of the children
     * */
    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        DefaultListViewItems defaultListViewItemsArrayList = getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(inflater).inflate(R.layout.default_list_view_item, null);
        }

        ImageView imageView = view.findViewById(R.id.firstImageview2); // image of the object
        TextView txtObj = view.findViewById(R.id.textView); // object
        TextView txtPrice = view.findViewById(R.id.txtPrice); // price of the object


        // set the proper values to the elements
        imageView.setImageResource(defaultListViewItemsArrayList.getImage());
        txtObj.setText(Objects.requireNonNull(defaultListViewItemsArrayList.getItemName()));
        String price = "" + defaultListViewItemsArrayList.getPrice() + " €";
        txtPrice.setText(price);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

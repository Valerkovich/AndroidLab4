package stu.cn.ua.androidlab4.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import stu.cn.ua.androidlab4.R;
import stu.cn.ua.androidlab4.model.Ship;

public class ShipsAdapter extends RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>
        implements View.OnClickListener{

    private List<Ship> ships = Collections.emptyList();
    private ShipListener listener;

    public ShipsAdapter(ShipListener listener) {
        setHasStableIds(true);
        this.listener = listener;
    }

    public void setShips(List<Ship> ships){
        this.ships = ships;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Ship ship = (Ship) v.getTag();
        listener.onShipChosen(ship);
    }

    @Override
    public long getItemId(int position) {
        return ships.get(position).getId().hashCode();
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @NonNull
    @Override
    public ShipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_ship, parent, false);
        return new ShipViewHolder(root, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipViewHolder holder, int position) {
        Ship ship = ships.get(position);
        holder.nameTextView.setText(ship.getName());
        holder.typeTextView.setText(ship.getShipType());
        holder.itemView.setTag(ship);
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

    static class ShipViewHolder extends RecyclerView.ViewHolder{
        private TextView typeTextView;
        private TextView nameTextView;
        public ShipViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(listener);
        }
    }

    public interface ShipListener {
        void onShipChosen(Ship ship);
    }
}

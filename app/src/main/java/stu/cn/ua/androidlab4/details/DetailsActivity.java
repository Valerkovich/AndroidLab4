package stu.cn.ua.androidlab4.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import stu.cn.ua.androidlab4.App;
import stu.cn.ua.androidlab4.R;
import stu.cn.ua.androidlab4.model.Ship;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SHIP_ID = "SHIP_ID";

    private TextView nameTextView;
    private TextView descriptionTextView;
    private ProgressBar progress;

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        progress = findViewById(R.id.progress);
        String shipsId = getIntent().getStringExtra(EXTRA_SHIP_ID);
        if(shipsId.equals("")){
            throw new RuntimeException("There is no ship ID");
        }

        App app = (App) getApplication();

        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(DetailsViewModel.class);

        viewModel.loadShipById(shipsId);

        viewModel.getResults().observe(this, result -> {
            switch (result.getStatus()){
                case SUCCESS:
                    Ship ship = result.getData();
                    nameTextView.setText(ship.getName());
                    descriptionTextView.setText(ship.toString());
                    progress.setVisibility(View.GONE);
                    break;
                case EMPTY:
                    nameTextView.setText("");
                    descriptionTextView.setText("");
                    progress.setVisibility(View.GONE);
                    break;
                case LOADING:
                    nameTextView.setText("");
                    descriptionTextView.setText("");
                    progress.setVisibility(View.VISIBLE);
                break;
            }
        });
    }
}

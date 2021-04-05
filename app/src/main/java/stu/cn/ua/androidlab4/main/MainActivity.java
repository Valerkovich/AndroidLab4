package stu.cn.ua.androidlab4.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import stu.cn.ua.androidlab4.App;
import stu.cn.ua.androidlab4.R;
import stu.cn.ua.androidlab4.details.DetailsActivity;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private RecyclerView shipsList;
    private EditText nameEditText;
    private ProgressBar progress;
    private TextView emptyTextView;
    private TextView errorTextView;

    private MainViewModel viewModel;

    private ShipsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.searchButton);
        shipsList = findViewById(R.id.shipsList);
        nameEditText = findViewById(R.id.nameEditText);
        progress = findViewById(R.id.progress);
        emptyTextView = findViewById(R.id.emptyTextView);
        errorTextView = findViewById(R.id.errorTextView);

        App app = (App) getApplication();

        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(MainViewModel.class);

        viewModel.getViewState().observe(this, state -> {
            searchButton.setEnabled(state.isEnableSearchButton());
            shipsList.setVisibility(toVisibility(state.isShowList()));
            progress.setVisibility(toVisibility(state.isShowProgress()));
            emptyTextView.setVisibility(toVisibility(state.isShowEmptyHint()));
            errorTextView.setVisibility(toVisibility(state.isShowError()));

            adapter.setShips(state.getShips());
        });

        searchButton.setOnClickListener(v -> {
            StringBuilder name = new StringBuilder(nameEditText.getText().toString());
            name.insert(0,"%");
            name.append("%");
            viewModel.getShips(name.toString(), hasConnection(this));
        });

        viewModel.getShips("", hasConnection(this));

        initShipsList();
    }


    private void initShipsList(){
        LayoutManager layoutManager = new LinearLayoutManager(this);
        shipsList.setLayoutManager(layoutManager);

        adapter = new ShipsAdapter(repository -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_SHIP_ID, repository.getId());
            startActivity(intent);
        });
        shipsList.setAdapter(adapter);
    }

    static int toVisibility(boolean show){
        return show ? View.VISIBLE : View.GONE;
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
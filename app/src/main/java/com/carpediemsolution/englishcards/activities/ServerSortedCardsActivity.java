package com.carpediemsolution.englishcards.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.carpediemsolution.englishcards.R;
import com.carpediemsolution.englishcards.activities.presenters.ServerSortedPresenter;
import com.carpediemsolution.englishcards.activities.views.CardsView;
import com.carpediemsolution.englishcards.general.BaseAdapter;
import com.carpediemsolution.englishcards.general.BaseView;
import com.carpediemsolution.englishcards.general.CardsAdapter;
import com.carpediemsolution.englishcards.general.EmptyRecyclerView;
import com.carpediemsolution.englishcards.general.LoadingDialog;
import com.carpediemsolution.englishcards.general.LoadingView;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.UIutils;
import com.carpediemsolution.englishcards.utils.DBSchema;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Юлия on 18.05.2017.
 */

public class ServerSortedCardsActivity extends MvpAppCompatActivity implements BaseView,
        BaseAdapter.OnItemClickListener<Card>, NavigationView.OnNavigationItemSelectedListener, CardsView {
    @InjectPresenter
    ServerSortedPresenter presenter;
    @BindView(R.id.card_recycler_view)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private LoadingView loadingView;
    private CardsAdapter adapter;

    // private static final String LOG_TAG = "ServerListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_server);
        ButterKnife.bind(this);
        loadingView = LoadingDialog.view(getSupportFragmentManager());
        presenter.init(returnTheme());

        initRecyclerView();
        initToolbar();
        initDrawer();
    }


    @Override
    public void onItemClick(@NonNull Card item) {
        presenter.onItemClick(item);
    }

    @Override
    public void showError() {
        Toast.makeText(ServerSortedCardsActivity.this, R.string.error_inet,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCards(@NonNull List<Card> cards) {
        adapter.changeDataSet(cards);
    }

    @Override
    public void showLoading() {
        loadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        loadingView.hideLoading();
    }

    @Override
    public void showDetails(@NonNull Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyTheme_Dark_Dialog);
        String dialogMessage = UIutils.dialogMessage(card);
        builder.setTitle(card.getWord() + " ~ " + UIutils.returnTheme(card))
                .setMessage(card.getTranslate() + "\n\n" + dialogMessage)
                .setPositiveButton(getString(R.string.add_card), (DialogInterface dialog, int which)-> {
                        presenter.addCard(card);
                })
                .show();
    }

    private String returnTheme() {
        Bundle bundle = getIntent().getExtras();
        String theme = "";
        if (bundle != null)
            theme = bundle.getString("card");
        return theme;
    }

    @Override
    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(ServerSortedCardsActivity.this, 3));
        recyclerView.setEmptyView(mEmptyView);
        adapter = new CardsAdapter(new ArrayList<>());
        adapter.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initToolbar() {
        toolbar.setTitleTextColor(Color.parseColor(getString(R.string.color_primary)));
        setSupportActionBar(toolbar);
    }

    @Override
    public void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.all_items): {
                Intent intentAllCards = new Intent(ServerSortedCardsActivity.this, ServerCardsActivity.class);
                startActivity(intentAllCards);
                break;
            }
            case (R.id.culture_art): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_CULTURE_ART);
                break;
            }
            case (R.id.modern_technologies): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES);
                break;
            }
            case (R.id.society_politics): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS);
                break;
            }
            case (R.id.adventure_travel): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL);
                break;
            }
            case (R.id.nature_weather): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_NATURE_WEATHER);
                break;
            }
            case (R.id.education_profession): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION);
                break;
            }
            case (R.id.appearance_character): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER);
                break;
            }
            case (R.id.clothes_fashion): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION);
                break;
            }
            case (R.id.sport): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_SPORT);
                break;
            }
            case (R.id.family_relationship): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP);
                break;
            }
            case (R.id.order_of_day): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY);
                break;
            }
            case (R.id.hobbies_free_time): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME);
                break;
            }
            case (R.id.customs_traditions): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS);
                break;
            }
            case (R.id.shopping): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_SHOPPING);
                break;
            }
            case (R.id.food_drinks): {
                adapter.clear();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                presenter.init(DBSchema.CardTable.Themes.THEME_FOOD_DRINKS);
                break;
            }
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

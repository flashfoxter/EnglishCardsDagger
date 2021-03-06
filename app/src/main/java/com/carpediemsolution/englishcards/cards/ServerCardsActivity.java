package com.carpediemsolution.englishcards.cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.carpediemsolution.englishcards.R;
import com.carpediemsolution.englishcards.cards.presenters.CardsPresenter;
import com.carpediemsolution.englishcards.cards.views.CardsView;
import com.carpediemsolution.englishcards.general.BaseAdapter;
import com.carpediemsolution.englishcards.general.BaseView;
import com.carpediemsolution.englishcards.general.CardsAdapter;
import com.carpediemsolution.englishcards.general.EmptyRecyclerView;
import com.carpediemsolution.englishcards.general.LoadingDialog;
import com.carpediemsolution.englishcards.general.LoadingView;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.utils.DBSchema;
import com.carpediemsolution.englishcards.utils.StringUtils;
import com.carpediemsolution.englishcards.utils.CardUtils;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerCardsActivity extends MvpAppCompatActivity implements CardsView,BaseView,
        BaseAdapter.OnItemClickListener<Card>, NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    CardsPresenter cardsPresenter;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private LoadingView loadingView;
    private CardsAdapter adapter;
    //  private static final String LOG_TAG = "ServerCardsActivity";

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(ServerCardsActivity.this, InsertNewCardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);
        ButterKnife.bind(this);

        loadingView = LoadingDialog.view(getSupportFragmentManager());
        initRecyclerView();
        initToolbar();
        initDrawer();

    }

    @Override
    public void onItemClick(@NonNull Card item) {
        cardsPresenter.onItemClick(item);
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
    public void showError() {
        Toast.makeText(ServerCardsActivity.this, R.string.error_inet,
                Toast.LENGTH_SHORT).show();
        adapter.clear();
    }

    @Override
    public void showCards(@NonNull List<Card> cards) {
        adapter.changeDataSet(cards);
    }

    @Override
    public void showDetails(@NonNull Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyTheme_Dark_Dialog);
        String dialogMessage = StringUtils.dialogMessage(card);
        builder.setTitle(card.getWord() + " ~ " + StringUtils.returnTheme(card))
                .setMessage(card.getTranslate() + "\n\n" + dialogMessage)
                .setPositiveButton(getString(R.string.add_card), (DialogInterface dialog, int which)-> {
                        cardsPresenter.addCard(card);
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cards_main, menu);
        return true;
    }

    @Override
    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(ServerCardsActivity.this, 3));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_stock): {
                Intent intent = new Intent(ServerCardsActivity.this, ServerCardsActivity.class);
                startActivity(intent);
                return true;
            }
            case (R.id.action_my_cards): {
                Intent intent = new Intent(ServerCardsActivity.this, UserCardsActivity.class);
                startActivity(intent);
                return true;
            }

            case (R.id.action_line): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerCardsActivity.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                return true;
            }
            case (R.id.action_frame): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                return true;
            }
            case (R.id.action_settings): {
                String token = PrefUtils.getTokenPrefs().getString(Preferences.TOKEN, "");
                if (CardUtils.isEmptyToken(token)) {
                } else {
                    //to do...
                    //  Intent intent = new Intent(ServerCardsActivity.this, UserAuthorizedActivity.class);
                    // startActivity(intent);
                }
                return true;
            }
            case (R.id.action_about_app): {
                //to do...
                // Intent intent = new Intent(ServerCardsActivity.this, InformationActivity.class);
                //  startActivity(intent);
                return true;
            }
            case (R.id.action_sync_cards): {
                //to do...
                // Intent intent = new Intent(ServerCardsActivity.this, CardsSyncActivity.class);
                //  startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent(ServerCardsActivity.this, ServerSortedCardsActivity.class);
        Bundle b = new Bundle();
        switch (item.getItemId()) {
            case (R.id.all_items): {
                Intent intentAll = new Intent(ServerCardsActivity.this, ServerCardsActivity.class);
                startActivity(intentAll);
                break;
            }
            case (R.id.culture_art): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CULTURE_ART);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.modern_technologies): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.society_politics): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.adventure_travel): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.nature_weather): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_NATURE_WEATHER);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.education_profession): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.appearance_character): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.clothes_fashion): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.sport): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SPORT);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.family_relationship): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.order_of_day): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.hobbies_free_time): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.customs_traditions): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.shopping): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SHOPPING);
                intent.putExtras(b);
                startActivity(intent);
                break;
            }
            case (R.id.food_drinks): {
                b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_FOOD_DRINKS);
                intent.putExtras(b);
                startActivity(intent);
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

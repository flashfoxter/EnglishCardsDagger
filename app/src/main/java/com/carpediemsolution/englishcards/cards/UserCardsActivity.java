package com.carpediemsolution.englishcards.cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
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
import com.carpediemsolution.englishcards.general.BaseAdapter;
import com.carpediemsolution.englishcards.general.BaseView;
import com.carpediemsolution.englishcards.general.CardsAdapter;
import com.carpediemsolution.englishcards.general.EmptyRecyclerView;
import com.carpediemsolution.englishcards.general.LoadingDialog;
import com.carpediemsolution.englishcards.general.LoadingView;
import com.carpediemsolution.englishcards.model.Card;
import com.carpediemsolution.englishcards.cards.presenters.UserCardsPresenter;
import com.carpediemsolution.englishcards.utils.DBSchema;
import com.carpediemsolution.englishcards.utils.StringUtils;
import com.carpediemsolution.englishcards.utils.CardUtils;
import com.carpediemsolution.englishcards.utils.PrefUtils;
import com.carpediemsolution.englishcards.utils.Preferences;
import com.carpediemsolution.englishcards.cards.views.UserCardsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCardsActivity extends MvpAppCompatActivity implements BaseView, UserCardsView,
        BaseAdapter.OnItemClickListener<Card>, NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    UserCardsPresenter cardsPresenter;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CardsAdapter adapter;
    private LoadingView loadingView;
  //  private static final String LOG_TAG = "ServerCardsActivity";

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(UserCardsActivity.this, InsertNewCardActivity.class);
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
        initNestedScrollView();
    }

    @Override
    public void onItemClick(@NonNull Card item) {
        cardsPresenter.onItemClick(item);
    }

    @Override
    public void showError() {
        Toast.makeText(UserCardsActivity.this, R.string.delete_cancel,
                Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
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
    public void showSuccess(Card card) {
        adapter.remove(card);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccess() {
     //to do
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
                .setPositiveButton(getString(R.string.remove),(DialogInterface dialog, int which)-> {
                        openDeleteDialog(card);
                })
                .setNegativeButton(getString(R.string.edit), (DialogInterface dialog, int which)-> {
                     /**  как передавать данные от презентера к презентеру? */
                        /*   Intent intent = new Intent(UserCardsActivity.this, EditCardActivity.class);
                        Bundle b = new Bundle();
                        b.putString("card", card.getId()); //Your id
                        intent.putExtras(b);
                        startActivity(intent);*/
                }).show();
    }

    @Override
    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(UserCardsActivity.this, 3));
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initNestedScrollView(){
        NestedScrollView nsv = (NestedScrollView) findViewById(R.id.nest_scrollview_main);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }

    protected void openDeleteDialog(@NonNull final Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserCardsActivity.this, R.style.MyTheme_Dark_Dialog);
        builder.setMessage(getString(R.string.are_you_sure));
        builder.setPositiveButton(getString(R.string.remove), (DialogInterface dialog, int which)-> {
                cardsPresenter.deleteCard(card);
            })
                .setNegativeButton(getString(R.string.cancel), (DialogInterface dialog, int which)-> {
                        dialog.dismiss();
                    }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cards_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_stock): {
                Intent intent = new Intent(UserCardsActivity.this, ServerCardsActivity.class);
                startActivity(intent);
                return true;
            }
            case (R.id.action_my_cards): {
                Intent intent = new Intent(UserCardsActivity.this, UserCardsActivity.class);
                startActivity(intent);
                return  true;
            }

            case (R.id.action_line): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(UserCardsActivity.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                return true;
            }
            case (R.id.action_frame): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(UserCardsActivity.this, 3);
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
        switch (item.getItemId()) {
            case (R.id.culture_art): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_CULTURE_ART);
                break;
            }
            case (R.id.modern_technologies): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES);
                break;
            }
            case (R.id.society_politics): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS);
                break;
            }
            case (R.id.adventure_travel): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL);
                break;
            }
            case (R.id.nature_weather): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_NATURE_WEATHER);
                break;
            }
            case (R.id.education_profession): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION);
                break;
            }
            case (R.id.appearance_character): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER);
                break;
            }
            case (R.id.clothes_fashion): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION);
                break;
            }
            case (R.id.sport): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_SPORT);
                break;
            }
            case (R.id.family_relationship): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP);
                break;
            }
            case (R.id.order_of_day): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY);
                break;
            }
            case (R.id.hobbies_free_time): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME);
                break;
            }
            case (R.id.customs_traditions): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS);
                break;
            }
            case (R.id.shopping): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_SHOPPING);
                break;
            }
            case (R.id.food_drinks): {
                cardsPresenter.loadCardsByTheme(DBSchema.CardTable.Themes.THEME_FOOD_DRINKS);
                break;
            }
            case (R.id.all_items): {
               cardsPresenter.loadData();
                break;
            }
            default:break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}





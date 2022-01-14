package com.hrushikesh.Bosshomes;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hrushikesh.Bosshomes.adapters.CustomAdapter_filter;
import com.hrushikesh.Bosshomes.adapters.CustomAdapter_main_search;
import com.hrushikesh.Bosshomes.modals.FilterModal;
import com.hrushikesh.Bosshomes.modals.MainItemModal;
import com.hrushikesh.Bosshomes.modals.MyData_cart;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mukesh.tinydb.TinyDB;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    MaterialSearchView materialSearchView;

    //for back button
    Drawable upArrow;
    ////ar
    TextView t1, t2, t3, t4,t5;
    ImageView img1, img2, img3, img4,img5;

    int searchOption;
    RelativeLayout searchView;

    private RecyclerView recyclerView_main_search;
    private GridLayoutManager gridLayoutManager_main_search; //for displaying more than one card in one line
    private CustomAdapter_main_search adapter_main_search;
    public static List<MainItemModal> listData_main_search = new ArrayList<>();
    public static List<MainItemModal> listData_main_search_copy = new ArrayList<>();

    ImageView orderEmptyImage;

    ////////////////////////////////////////////////////////////////
    //Customized Side Menu
    ////////////////////////////////////////////////////////////////
    RelativeLayout notificationsLayout, summaryLayout, settingsLayout, shareLayout;
    TextView userName;
    ImageView userImage;
    NavigationView navigationView_header;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //ONLY ONE INSTANCE OF THIS WILL BE USED THROUGHOUT THE PROGRAMME
    public static ArrayList<MyData_cart> my_data_list_cart = new ArrayList<>();

    static final Comparator<MainItemModal> SORT_BY_PRICE_ASCENDING = (t1, t2) -> Integer.parseInt(t1.getPrice()) - Integer.parseInt(t2.getPrice());

    //Camera permission
    final int CAMERA_REQUEST_CODE = 100;

    String alreadyDetected = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (RelativeLayout) findViewById(R.id.searchView);
        orderEmptyImage = (ImageView) findViewById(R.id.orderEmptyImage);
        orderEmptyImage.setVisibility(View.INVISIBLE);


        ///arcoreinterface
        t1 = (TextView) findViewById(R.id.nav_ARcoretxt);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
        img1 = (ImageView) findViewById(R.id.nav_ARcore);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });


        ///locationinterface
        t2 = (TextView) findViewById(R.id.nav_locationtxt);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivitytwo();
            }
        });
        img2 = (ImageView) findViewById(R.id.nav_login);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivitytwo();
            }
        });

        ///logininterface
        t3 = (TextView) findViewById(R.id.nav_logintxt);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivitythree();
            }
        });
        img3 = (ImageView) findViewById(R.id.nav_login);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivitythree();
            }
        });
        ///bloginterface
        t4 = (TextView) findViewById(R.id.nav_blogread);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityfour();
            }
        });
        img4 = (ImageView) findViewById(R.id.nav_blog);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityfour();
            }
        });
        ///bloginterface
        t5 = (TextView) findViewById(R.id.nav_postread);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityfive();
            }
        });
        img5 = (ImageView) findViewById(R.id.nav_post);
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityfive();
            }
        });


        //LOAD THE CART ITEMS
        getCartItems();

        //Load data
        loadData();

        //FOR SIDE DRAWER
        sideDrawerInitialisation();

        //FOR SEARCH VIEW
        searchViewInitialisation();

        recyclerView_main_search = (RecyclerView) findViewById(R.id.recyclerView_orders);
        gridLayoutManager_main_search = new GridLayoutManager(MainActivity.this, 2);
        recyclerView_main_search.setLayoutManager(gridLayoutManager_main_search);
        adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
        recyclerView_main_search.setAdapter(adapter_main_search);
    }



    private void loadData() {
        listData_main_search.clear();
        listData_main_search_copy.clear();

        //Load Default data
        MainItemModal mainItemModal1 = new MainItemModal(
                "fs0001",
                "table",
                "very strong table made from oak wood",
                "120",
                "table",
                "5",
                "black",
                "https://www.drivenbydecor.com/wp-content/uploads/2015/07/IKEAs-Mockelby-Table-new-for-2016.jpg");
        listData_main_search.add(mainItemModal1);
        listData_main_search_copy.add(mainItemModal1);

        MainItemModal mainItemModal2 = new MainItemModal(
                "fs0002",
                "chair",
                "very strong chair made from oak wood",
                "30",
                "chair",
                "8",
                "blue",
                "https://i.pinimg.com/originals/11/fb/c7/11fbc7f319d3f6de24ee29f2fccd3c64.jpg");
        listData_main_search.add(mainItemModal2);
        listData_main_search_copy.add(mainItemModal2);

        MainItemModal mainItemModal3 = new MainItemModal(
                "fs0003",
                "Cougar Armor-S Ergonomic Comfortable Gaming Chair",
                "Extra comfort, with six axis rotation and relaxed posture, built in micro USB cable for charging controllers",
                "680",
                "chair",
                "8",
                "blue",
                "https://cf4.s3.souqcdn.com/item/2018/02/15/30/69/34/51/item_XXL_30693451_110809689.jpg");
        listData_main_search.add(mainItemModal3);
        listData_main_search_copy.add(mainItemModal3);

        MainItemModal mainItemModal4 = new MainItemModal(
                "fs0004",
                "ABS Mesh Office Chair",
                "Pure leather made, authentic italian product",
                "890",
                "chair",
                "8",
                "blue",
                "https://cf1.s3.souqcdn.com/item/2017/07/10/23/35/64/97/item_XXL_23356497_33244287.jpg");
        listData_main_search.add(mainItemModal4);
        listData_main_search_copy.add(mainItemModal4);

        MainItemModal mainItemModal5 = new MainItemModal(
                "fs0005",
                "Coffee Table, Wooden",
                "Length: 55 cm" + "Width: 55 cm" + "Height: 45 cm" + "Max. load: 25 kg" + "Requires simple assembly",
                "85",
                "chair",
                "8",
                "blue",
                "https://cf3.s3.souqcdn.com/item/2015/11/10/95/10/61/1/item_XXL_9510611_10714437.jpg");
        listData_main_search.add(mainItemModal5);
        listData_main_search_copy.add(mainItemModal5);

        MainItemModal mainItemModal6 = new MainItemModal(
                "fs0006",
                "White TV Table, Size 90cm x 26cm x 45cm, with Shelf, Acrylic paint",
                "Rectangle-shaped TV table, white color, acrylic coated, easy to install" + "The opening at the back allows you to easily gather and organise all wires",
                "30",
                "table",
                "8",
                "blue",
                "https://images.yaoota.com/UTaYsuFW3C8hiABZvFncQ7gN6l8=/trim/yaootaweb-production-sa/media/crawledproductimages/2e071c00439003a69de95287d4e5ee496585f617.jpg");
        listData_main_search.add(mainItemModal6);
        listData_main_search_copy.add(mainItemModal6);

        MainItemModal mainItemModal7 = new MainItemModal(
                "fs0007",
                "Sheepskin Carpet",
                "Authentic sheep skin,Soil repellant,Durable material that last long",
                "800",
                "carpet",
                "8",
                "blue",
                "https://cf3.s3.souqcdn.com/item/2015/10/28/93/92/84/0/item_XXL_9392840_10350528.jpg");
        listData_main_search.add(mainItemModal7);
        listData_main_search_copy.add(mainItemModal7);

        MainItemModal mainItemModal8 = new MainItemModal(
                "fs0008",
                "Castanho Touch Computer Table With Two Drawers - 54 x 110 x 81 cm",
                "This computer desk has a smooth and sleek finish. Ideal for small rooms or offices. Made from good quality material to make it strong and long lasting. Pair it with other modular components to create an office or work space that is both stylish and filled with functionality. It is easy to assemble.",
                "90",
                "table",
                "8",
                "blue",
                "https://cf5.s3.souqcdn.com/item/2017/07/06/22/06/85/74/item_XL_22068574_33133892.jpg");
        listData_main_search.add(mainItemModal8);
        listData_main_search_copy.add(mainItemModal8);

        MainItemModal mainItemModal9 = new MainItemModal(
                "fs0009",
                "Castanho Touch Computer Table With Two Drawers - 54 x 110 x 81 cm",
                "This computer desk has a smooth and sleek finish. Ideal for small rooms or offices. Made from good quality material to make it strong and long lasting. Pair it with other modular components to create an office or work space that is both stylish and filled with functionality. It is easy to assemble.",
                "90",
                "table",
                "9",
                "brown",
                "https://firebasestorage.googleapis.com/v0/b/chsver2.appspot.com/o/dressingtable.jpg?alt=media&token=dadebe86-57e4-4c3a-9c11-227819252a17");
        listData_main_search.add(mainItemModal9);
        listData_main_search_copy.add(mainItemModal9);

    }

    public void sideDrawerInitialisation() {
        //for Navigation View initialisation
        navigationView_header = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        userImage = (ImageView) findViewById(R.id.userImage);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //for navigation button
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialise the Side Menu Elements
        notificationsLayout = (RelativeLayout) findViewById(R.id.notificationsLayout);
        userName = (TextView) findViewById(R.id.userName);

        userName.setText("hrushi");

        notificationsLayout.setOnClickListener(view ->
        {
            Intent intent = new Intent(MainActivity.this, CartView.class);
            startActivity(intent);
        });

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/chsver2.appspot.com/o/bosshomes.png?alt=media&token=07df9ced-ad9a-4105-8bf0-aa80b79cfa6b")
                .apply(new RequestOptions().transform(new CircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(getResources().getDrawable(R.drawable.error_image)).placeholder(getResources()
                                .getDrawable(R.drawable.error_image))).into(userImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        item.setOnMenuItemClickListener(menuItem ->
        {
            searchView.setVisibility(View.VISIBLE);
            materialSearchView.showSearch(true);
            return true;
        });

        MenuItem item_filter = menu.findItem(R.id.action_filter);
        item_filter.setOnMenuItemClickListener(menuItem ->
        {
            filterDataPopUp(recyclerView_main_search, adapter_main_search, listData_main_search);
            return false;
        });

        MenuItem item_barcodeScanner = menu.findItem(R.id.action_barcodeScanner);
        item_barcodeScanner.setOnMenuItemClickListener(menuItem ->
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                checkCameraPermission();
            } else {
                barcodeScannerDialog();
            }
            return false;
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //for nav drawer button to work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            if (materialSearchView.isSearchOpen()) {
                materialSearchView.closeSearch();
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

//        @Override public String getId()
//        {
//            return getClass().getName();
//        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }

    private void searchViewInitialisation() {
        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view_main_search);
        searchView.setVisibility(View.GONE);
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                //if search view closed
                adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
                recyclerView_main_search.setAdapter(adapter_main_search);

                if (listData_main_search_copy.size() == 0) {
                    orderEmptyImage.setVisibility(View.VISIBLE);
                } else {
                    orderEmptyImage.setVisibility(View.INVISIBLE);
                }

            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    int size = newText.length();
                    List<MainItemModal> new_list = new ArrayList<>();
                    for (MainItemModal item : listData_main_search_copy) {
                        if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                            new_list.add(item);
                        }
                        adapter_main_search = new CustomAdapter_main_search(new_list, MainActivity.this);
                        recyclerView_main_search.setAdapter(adapter_main_search);

                        if (new_list.size() == 0) {
                            orderEmptyImage.setVisibility(View.VISIBLE);
                        } else {
                            orderEmptyImage.setVisibility(View.INVISIBLE);
                        }
                    }

                } else {
                    //if search text is null
                    adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
                    recyclerView_main_search.setAdapter(adapter_main_search);

                    if (listData_main_search_copy.size() == 0) {
                        orderEmptyImage.setVisibility(View.VISIBLE);
                    } else {
                        orderEmptyImage.setVisibility(View.INVISIBLE);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void getCartItems() {
        //initialise Gson
        Gson gson;
        String tiny_text = "";
        gson = new Gson();

        TinyDB tinydb = new TinyDB(this);
        tiny_text = tinydb.getString("cart_list");

        if (!tiny_text.equals("")) {
            //Type type = new TypeToken<List<MyData_cart>>(){}.getType();
            Type type = new TypeToken<ArrayList<MyData_cart>>() {
            }.getType();
            my_data_list_cart = gson.fromJson(tiny_text, type);
        }
    }

    private void filterDataPopUp(RecyclerView recyclerView_mainSearch, CustomAdapter_main_search customAdapter_main_search, List<MainItemModal> data_list) {
        List<String> type_filter = new ArrayList<>();

        for (int i = 0; i < listData_main_search_copy.size(); ++i) {
            boolean typeAlreadyPresent = false;

            if (type_filter.size() == 0) {
                type_filter.add(listData_main_search_copy.get(i).getType());
            } else {
                for (int j = 0; j < type_filter.size(); ++j) {
                    if (type_filter.get(j).equals(listData_main_search_copy.get(i).getType())) {
                        //make a boolean true
                        typeAlreadyPresent = true;
                        break;
                    }
                }

                if (!typeAlreadyPresent) {
                    type_filter.add(listData_main_search_copy.get(i).getType());
                }
            }
        }

        //now finding out how many items in each type_filter
        List<String> already_checked_filter = new ArrayList<>();
        List<FilterModal> filters = new ArrayList<>();
        for (int v = 0; v < type_filter.size(); ++v) {
            if (already_checked_filter.size() == 0) {
                int count = 0;
                for (int k = 0; k < listData_main_search_copy.size(); ++k) {
                    if (type_filter.get(v).equals(listData_main_search_copy.get(k).getType())) {
                        ++count;
                    }
                }

                FilterModal filterModal = new FilterModal(type_filter.get(v), String.valueOf(count));
                filters.add(filterModal);
                already_checked_filter.add(type_filter.get(v));
            } else {
                boolean present = false;
                for (int g = 0; g < already_checked_filter.size(); ++g) {
                    if (type_filter.get(v).equals(already_checked_filter.get(g))) {
                        present = true;
                        break;
                    }
                }

                if (!present) {
                    int count = 0;
                    for (int k = 0; k < listData_main_search_copy.size(); ++k) {
                        if (type_filter.get(v).equals(listData_main_search_copy.get(k).getType())) {
                            ++count;
                        }
                    }

                    FilterModal filterModal = new FilterModal(type_filter.get(v), String.valueOf(count));
                    filters.add(filterModal);
                    already_checked_filter.add(type_filter.get(v));
                }

            }
        }

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.filter_layout, null);
        dialogBuilder.setView(dialogView);

        RelativeLayout categoryFilterLayout = (RelativeLayout) dialogView.findViewById(R.id.categoryFilterLayout);
        RelativeLayout priceFilterLayout = (RelativeLayout) dialogView.findViewById(R.id.priceFilterLayout);
        TextView resetFilter = (TextView) dialogView.findViewById(R.id.resetFilter);
        RecyclerView filter_recycler_view = (RecyclerView) dialogView.findViewById(R.id.filter_recycler_view);
        TextView priceLowToHigh = (TextView) dialogView.findViewById(R.id.priceLowToHigh);
        TextView priceHighToLow = (TextView) dialogView.findViewById(R.id.priceHighToLow);

        //by default
        priceLowToHigh.setVisibility(View.GONE);
        priceHighToLow.setVisibility(View.GONE);
        filter_recycler_view.setVisibility(View.VISIBLE);

        categoryFilterLayout.setOnClickListener(view ->
        {
            priceLowToHigh.setVisibility(View.GONE);
            priceHighToLow.setVisibility(View.GONE);
            filter_recycler_view.setVisibility(View.VISIBLE);
        });

        priceFilterLayout.setOnClickListener(view ->
        {
            priceLowToHigh.setVisibility(View.VISIBLE);
            priceHighToLow.setVisibility(View.VISIBLE);
            filter_recycler_view.setVisibility(View.GONE);
        });

        priceLowToHigh.setOnClickListener(view ->
        {
            Collections.sort(listData_main_search_copy, SORT_BY_PRICE_ASCENDING);
            adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
            recyclerView_main_search.setAdapter(adapter_main_search);

        });

        priceHighToLow.setOnClickListener(view ->
        {
            Collections.sort(listData_main_search_copy, SORT_BY_PRICE_ASCENDING);
            List<MainItemModal> reversedAscendingList = new ArrayList<>();

            for (int j = listData_main_search_copy.size() - 1; j >= 0; --j) {
                reversedAscendingList.add(listData_main_search_copy.get(j));
            }

            listData_main_search_copy = reversedAscendingList;

            adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
            recyclerView_main_search.setAdapter(adapter_main_search);

        });

        GridLayoutManager gridLayoutManager_main_search = new GridLayoutManager(MainActivity.this, 2);
        filter_recycler_view.setLayoutManager(gridLayoutManager_main_search);
        CustomAdapter_filter adapter_filter = new CustomAdapter_filter(filters, MainActivity.this, recyclerView_mainSearch, customAdapter_main_search, data_list);
        filter_recycler_view.setAdapter(adapter_filter);

        resetFilter.setOnClickListener(view ->
        {
            listData_main_search_copy = listData_main_search;
            adapter_main_search = new CustomAdapter_main_search(listData_main_search_copy, MainActivity.this);
            recyclerView_main_search.setAdapter(adapter_main_search);
        });

        final AlertDialog alertDialog = dialogBuilder.create();


        //segmentedButtonGroup.setPosition(1,0);
        alertDialog.show();
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Camera Permission")
                        .setMessage("Please allow permission to access the device camera for scanning barcodes")
                        .setPositiveButton("ok", (dialogInterface, i) ->
                        {
                            //Prompt the user once explanation has been shown
                            android.support.v4.app.ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    CAMERA_REQUEST_CODE);
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                android.support.v4.app.ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Do the exact thing it does when we click the top button
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                    barcodeScannerDialog();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void barcodeScannerDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.TransparentDialog);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.barcode_scanner_dialog, null);

        dialogBuilder.setView(dialogView);


        RelativeLayout itemLayout = (RelativeLayout) dialogView.findViewById(R.id.itemLayout);
        TextView searchItemDescription = (TextView) dialogView.findViewById(R.id.searchItemDescription);
        ImageView searchItemImage = (ImageView) dialogView.findViewById(R.id.searchItemImage);
        TextView searchItemName = (TextView) dialogView.findViewById(R.id.searchItemName);
        SurfaceView cameraPreview = (SurfaceView) dialogView.findViewById(R.id.cameraPreview);
        TextView textData = (TextView) dialogView.findViewById(R.id.textData);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(290, 370).build();

        final AlertDialog alertDialog = dialogBuilder.create();
        itemLayout.setVisibility(View.GONE);

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //NO PERMISSION TO START, SO ASK PERMISSION
                    Toast.makeText(MainActivity.this, "Please enable permission to read camera", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> codes = detections.getDetectedItems();
                if (codes.size() > 0) {
                    if (alreadyDetected.equals("")) {
                        //new detection
                        textData.post(() ->
                        {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            assert vibrator != null;
                            vibrator.vibrate(1000);
                            alreadyDetected = codes.valueAt(0).displayValue;
                            textData.setText(codes.valueAt(0).displayValue);

                            for (int vv = 0; vv < listData_main_search.size(); ++vv) {
                                if (listData_main_search.get(vv).getItem_id().equals(codes.valueAt(0).displayValue)) {
                                    itemLayout.setVisibility(View.VISIBLE);
                                    searchItemName.setText(listData_main_search.get(vv).getName());
                                    Glide.with(MainActivity.this).load(listData_main_search.get(vv).getImage())
                                            .apply(new RequestOptions().transform(new CircleTransform(MainActivity.this)).diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .error(getResources().getDrawable(R.drawable.error_image)).placeholder(getResources()
                                                            .getDrawable(R.drawable.error_image))).into(searchItemImage);
                                }
                            }
                        });
                    } else if (alreadyDetected.equals(codes.valueAt(0).displayValue)) {
                        //already detected
                    } else if (!alreadyDetected.equals(codes.valueAt(0).displayValue)) {
                        //different one
                        textData.post(() ->
                        {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            assert vibrator != null;
                            vibrator.vibrate(1000);
                            alreadyDetected = codes.valueAt(0).displayValue;
                            textData.setText(codes.valueAt(0).displayValue);

                            for (int vv = 0; vv < listData_main_search.size(); ++vv) {
                                if (listData_main_search.get(vv).getItem_id().equals(codes.valueAt(0).displayValue)) {
                                    itemLayout.setVisibility(View.VISIBLE);
                                    searchItemName.setText(listData_main_search.get(vv).getName());
                                    Glide.with(MainActivity.this).load(listData_main_search.get(vv).getImage())
                                            .apply(new RequestOptions().transform(new CircleTransform(MainActivity.this)).diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .error(getResources().getDrawable(R.drawable.error_image)).placeholder(getResources()
                                                            .getDrawable(R.drawable.error_image))).into(searchItemImage);
                                    searchItemDescription.setText(listData_main_search.get(vv).getDescription());
                                }
                            }
                        });
                    }
                }
            }
        });

        itemLayout.setOnClickListener(view ->
        {
            for (int i = 0; i < listData_main_search.size(); ++i) {
                if (alreadyDetected.equals(listData_main_search.get(i).getItem_id())) {
                    quickViewDialog(listData_main_search.get(i).getItem_id(),
                            listData_main_search.get(i).getName(),
                            listData_main_search.get(i).getDescription(),
                            listData_main_search.get(i).getImage(),
                            listData_main_search.get(i).getPrice(),
                            listData_main_search.get(i).getColour(),
                            listData_main_search.get(i).getQuantity(),
                            listData_main_search.get(i).getType());

                    alertDialog.dismiss();
                }
            }
        });


        //segmentedButtonGroup.setPosition(1,0);
        alertDialog.show();
    }

    public void quickViewDialog(String item_id, String name, String Description, String image, String price, String colour, String item_stock, String type) {
        //create alert dialog for asking
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.quick_view_items, null);
        dialogBuilder.setView(dialogView);
        final ImageView itemImage = (ImageView) dialogView.findViewById(R.id.itemImage);
        final TextView itemName = (TextView) dialogView.findViewById(R.id.itemName);
        final TextView itemPriceValue = (TextView) dialogView.findViewById(R.id.itemPriceValue);
        final TextView itemDescription = (TextView) dialogView.findViewById(R.id.itemDescription);
        final TextView addtoCart = (TextView) dialogView.findViewById(R.id.addtoCart);
        final RelativeLayout fullLayout = (RelativeLayout) dialogView.findViewById(R.id.fullLayout);
        final AlertDialog alertDialog = dialogBuilder.create();

        itemName.setText(name);
        itemPriceValue.setText(price);
        itemDescription.setText(Description);

        Glide.with(MainActivity.this)
                .load(image)
                .apply(new RequestOptions().placeholder(R.drawable.error_image).error(R.drawable.error_image))
                .into(itemImage);

        fullLayout.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, ItemPage.class);
            intent.putExtra("item_id", item_id);
            startActivity(intent);
        });

        addtoCart.setOnClickListener(v ->
        {
            Toast.makeText(MainActivity.this, "added to cart", Toast.LENGTH_SHORT).show();
            loadItemToCart(item_id, name, Description, image, price, colour, item_stock, type);
            alertDialog.dismiss();

        });

        //segmentedButtonGroup.setPosition(1,0);
        alertDialog.show();
    }

    private void loadItemToCart(String item_id, String name, String Description, String image, String price, String colour, String item_stock, String type) {
        MyData_cart m = new MyData_cart(item_id, name, Description, price, type, "1", colour, image, "special request", item_stock);


        if (my_data_list_cart.size() == 0) {
            my_data_list_cart.add(m);
        } else {
            //Before adding check if the item has the same id,
            //if same id then check if it has messages or no messages
            //if cart has an item with no message, and adding same item with no message then just increment the quantity
            //if cart has an item with message, and adding item with same message then just increment the quantity
            //same principle for Attributes

            int size_before_adding = my_data_list_cart.size();
            for (int v = 0; v < size_before_adding; ++v) {
                // same item id
                if (m.getItem_id().equals(my_data_list_cart.get(v).getItem_id())) {
                    //same message (even if no message)
                    if (m.getSpecial_request().trim().equals(my_data_list_cart.get(v).getSpecial_request().trim())) {
                        //same variation (even if no variation)
                        if (m.getSpecial_request().trim().equals(my_data_list_cart.get(v).getSpecial_request().trim())) {
                            //just increment the item quantity
                            int quantity = Integer.parseInt(my_data_list_cart.get(v).getQuantity());
                            quantity += Integer.parseInt(m.getQuantity());
                            my_data_list_cart.get(v).setQuantity(String.valueOf(quantity));
                            break;
                        }
                        //different variation
                        else {
                            if (v + 1 == my_data_list_cart.size())
                                my_data_list_cart.add(m);
                        }
                    }
                    //different message
                    else {
                        if (v + 1 == my_data_list_cart.size())
                            my_data_list_cart.add(m);
                    }

                }
                //different item id
                else {
                    if (v + 1 == my_data_list_cart.size())
                        my_data_list_cart.add(m);
                }
            }
        }

        //adding to gson
        Gson gson;
        gson = new Gson();
        String jsonCart = gson.toJson(my_data_list_cart);
        Log.d("TAG", "jsonCART = " + jsonCart);
        //storing into TinyDB
        TinyDB tinyDB;
        tinyDB = new TinyDB(MainActivity.this);
        tinyDB.putString("cart_list", jsonCart);
    }

    ///arcore interfece intent
    public void openNewActivity() {
        Intent intent = new Intent(this, MainActivityar.class);
        startActivity(intent);
    }

    ///locationinterface intent
    public void openNewActivitytwo() {
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
    }

    ///locgininterface intent
    public void openNewActivitythree() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openNewActivityfour() {
        Intent intent = new Intent(this, BlogActivity.class);
        startActivity(intent);
    }
    public void openNewActivityfive() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }
}

package food.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import food.bean.Product;
import food.util.db.DBUtil;
import food.util.SPUtils;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    FragmentManager fManager;
    FragmentTransaction fTransaction;
    MyFragment f1, f2, f3;

    final static int show_product_detail = 0;
    final static int confirm_purchase = 1;
    public Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);	//删除默认的选中效果

        setTitle("登录成功：" + SPUtils.getString(this, "userName"));
        fManager = getSupportFragmentManager();
        fTransaction = fManager.beginTransaction();
        f1 = new MyFragment(0);
        f2 = new MyFragment(1);
        f3 = new MyFragment(2);
        fTransaction.add(R.id.FramePage, f1);
        fTransaction.add(R.id.FramePage, f2);
        fTransaction.add(R.id.FramePage, f3);
        fTransaction.show(f1).hide(f2).hide(f3);
        fTransaction.commit();
        navView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_item1:
                    showFragment(f1);
                    item.setIcon(R.drawable.register);
                    return true;
                case R.id.navigation_item2:
                    showFragment(f2);
                    item.setIcon(R.drawable.register);
                    return true;
                case R.id.navigation_item3:
                    showFragment(f3);
                    item.setIcon(R.drawable.register);
                    return true;
            }
            return false;
        }
    };


    public void showFragment(MyFragment f) {
        MenuItem item1 = navView.getMenu().findItem(R.id.navigation_item1);
        MenuItem item2 = navView.getMenu().findItem(R.id.navigation_item2);
        MenuItem item3 = navView.getMenu().findItem(R.id.navigation_item3);
        item1.setIcon(R.mipmap.tab_yazi);
        item2.setIcon(R.mipmap.tab_baozi);
        item3.setIcon(R.mipmap.tab_xiancai);

        fTransaction = fManager.beginTransaction();
        fTransaction.hide(f1).hide(f2).hide(f3).show(f);
        fTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(1, show_product_detail, 1, "菜品详情").setIcon(R.mipmap.menu_detail);
        menu.add(1, confirm_purchase, 2, "加入购物车").setIcon(R.mipmap.add_shopcart);
        return true;
    }

    //监听用户选中事件___________________________________________________________
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case show_product_detail:
                //跳转到详情显示页面
                Intent intent = new Intent(this, ProductDetail.class);
                intent.putExtra("food", product);
                startActivity(intent);
                break;
            case confirm_purchase:
                if (product.id != 0) {
                    //跳转到购物车界面,并且提示用户输入要几份菜品
                    final EditText et_number = new EditText(this);
                    et_number.setHint("订购数量");
                    //提示用户输入数量
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("购物车")
                            .setIcon(R.mipmap.add_shopcart)
                            .setView(et_number)
                            .setPositiveButton("订购", (dialog, which) -> {
                                String orderNumber = et_number.getText().toString().trim();
                                if (!TextUtils.isEmpty(orderNumber)) {
                                    int quantity = Integer.parseInt(orderNumber);
                                    product.setQuantity(quantity);
                                    DBUtil.add2Cart(MainActivity.this, product);
                                    Intent intent1 = new Intent(MainActivity.this, CartActivity.class);
                                    startActivity(intent1);
                                } else {
                                    Toast.makeText(MainActivity.this, "请输入数量", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Toast.makeText(MainActivity.this, "没有选中菜品", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }
//监听用户选中事件___________________________________________________________

}

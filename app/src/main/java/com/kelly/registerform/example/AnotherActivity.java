package com.kelly.registerform.example;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.kelly.registerform.R;
import com.kelly.registerform.adapters.FragmentFarmingPageAdapter;
import com.kelly.registerform.fragments.AnotherFragment;
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.partner.ValidationActivity;



public class AnotherActivity extends AppCompatActivity {
    private Button b_next,b_back;
    private String listAcvities = null;
    private ViewPager pager=null;
    private Context context=this;
    private Spinner s_numberFarm;
    private FragmentFarmingPageAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        setElements();
        setActions();
        // Instantiate a ViewPager
        pager = (ViewPager) this.findViewById(R.id.pager);

        // Create an adapter with the fragments we show on the ViewPager
        adapter = new FragmentFarmingPageAdapter(
                getSupportFragmentManager());
        adapter.addFragment(AnotherFragment.newInstance(getResources()
                .getColor(R.color.colorWhite)));
        this.pager.setAdapter(adapter);
    }
    private  void setElements(){
        b_next =findViewById(R.id.b_next);
        b_back =findViewById(R.id.b_back);
        listAcvities = getIntent().getStringExtra("list");
        s_numberFarm=findViewById(R.id.s_numberFarm);


    }
    private void setActions(){
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listAcvities.equals("")){
                    Intent intent =new Intent(context,ValidationActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent =new Intent(context,InformationActivity.class);
                    startActivity(intent);
                }

            }
        });
        final ViewPager pager;
        s_numberFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!((String)adapterView.getItemAtPosition(i)).equals("Elija")){
                    int val = Integer.parseInt((String)adapterView.getItemAtPosition(i));
                    System.out.println(val);
                    if(val==1)beforeOne();
                    else {
                        beforeOne();//limpiamos
                        updateViews(val);
                    }
                }else{

                    beforeOne();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    /*public void removeView (View defunctPage)
    {
        int pageIndex = pagerAdapter.removeView (pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        pager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
    }*/
    @Override
    public void onBackPressed() {

        // Return to previous page when we press back button
        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);

    }
    public void updateViews(int val){
        System.out.println(adapter.getCount()+"");
        for (int i=0;i<val-1;i++){
            AnotherFragment anotherFragment = AnotherFragment.newInstance(getResources()
                    .getColor(R.color.colorWhite));
            anotherFragment.indexPage=i+1;
            adapter.addFragment(anotherFragment);
            System.out.println("Agregado");
        }
        System.out.println(adapter.getCount()+"");
        this.pager.setAdapter(adapter);
    }
    public void beforeOne(){
        adapter.beforeOne();
        adapter.addFragment(AnotherFragment.newInstance(getResources()
                .getColor(R.color.colorWhite)));
        this.pager.setAdapter(adapter);
    }
}

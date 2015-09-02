package com.example.com.criteo.sdktestapp;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.criteo.events.AppLaunchEvent;
import com.criteo.events.BasketViewEvent;
import com.criteo.events.DeeplinkEvent;
import com.criteo.events.EventService;
import com.criteo.events.HomeViewEvent;
import com.criteo.events.ProductListViewEvent;
import com.criteo.events.ProductViewEvent;
import com.criteo.events.TransactionConfirmationEvent;
import com.criteo.events.product.BasketProduct;
import com.criteo.events.product.Product;
import com.mobileapptracker.MATEvent;
import com.mobileapptracker.MATEventItem;
import com.mobileapptracker.MobileAppTracker;
//import com.example.com.criteo.testapp.R;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import java.io.IOException;     
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


/**
 * @description This sample code is only for understanding of how to use Criteo Advertiser SDK library.
 * @description It needs to refer to API reference in Javadoc for detailed methods description.
 */
public class MainActivity extends Activity{
	   String m_My_Advertiser_ID = null;
	   String m_My_Conversion_Key = null;
	   public EventService m_criteoEvent=null;
	   
	   Button m_ViewHome = null;
	   Button m_ViewProduct = null;
	   Button m_ViewList = null;
	   Button m_Purchase = null;
	   Button m_ViewCart = null;
	   Button m_Search = null;
	     
	   // Criteo data object
	   CriteoDO m_CriteoDO = null;
	
	   
	   //Make date to YYYY-MM-DD type
	   Date m_StartDate;
	   Date m_EndDate;
	   
	   // Date type for v1.0 Beta candidate
	   //GregorianCalendar m_StartDate;
	   //GregorianCalendar m_EndDate;
	   
	   // For SDK beta-candidate version 
		BasketProduct basketProduct1;
		BasketProduct basketProduct2;
		BasketProduct basketProduct3;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
             
        //Create Criteo data object
        m_CriteoDO = new CriteoDO();
        
        // Assign Button resources  
        onButtonInit();
          
        // Test2
        m_criteoEvent = new EventService(getApplicationContext(), "KR", "ko" );   // Important to set correct codes here. 
        //m_criteoEvent.setCustomerId(m_CriteoDO.m_Advertiser_ID);
        
        //Create the app launch event
        AppLaunchEvent appLaunch = new AppLaunchEvent();
        
        // deeplink URL checking .
        Uri data = getIntent().getData();
        Log.d("Criteo deeplink Launch", "deeplink");
        if (data != null) {
        	//String aid = data.getQueryParameter("AID");
        	String deeplink_url = data.toString();
        	Log.d(deeplink_url, "deeplink");
        	if (deeplink_url != null && deeplink_url.length() > 0) {
        		
        		DeeplinkEvent t_deeplink = new DeeplinkEvent(deeplink_url);
        		
        		//appLaunch.setGoogleReferrer(deeplink_url);
        	}
        }

        // Send AppLaunch event
        m_criteoEvent.send(appLaunch);
        
        // Collect Google Play Advertising ID; REQUIRED for attribution of Android apps distributed via Google Play
        new Thread(new Runnable() {
            @Override public void run() {
                // See sample code at http://developer.android.com/google/play-services/id.html
  
            }
        }).start();
        
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
        
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //Not implemented anything here
        
        return true;
    }
   
	 /**
     * @description This method initializes resources and Criteo retargeting events.
     * @param void
     * @return void
     */
	public void onButtonInit() {
		
		//Event for Criteo ViewHome event
		m_ViewHome =(Button)findViewById(R.id.button1);
		m_ViewHome.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewHome onClick", "Button1");
				
				//Create HomeViewEvent 
				HomeViewEvent homeView = new HomeViewEvent();
		
				//Set additional parameters to HomeViewEvent
				homeView.addExtraData("DateExtraData", new GregorianCalendar(2015, Calendar.JANUARY, 1));
				homeView.addExtraData("FloatExtraData", (float) 55.34);
				homeView.addExtraData("StringExtraData", (String)"222extradata");
				homeView.addExtraData("IntExtraData", 100);
				
				//Send HomeViewEvent
				m_criteoEvent.send(homeView);
			}
		});
		
		//Event for Criteo ViewProduct event
		m_ViewProduct =(Button)findViewById(R.id.button2);
		m_ViewProduct.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewProduct onClick", "Button2");
								
				//Test 3 
				Product product = new Product("11262072", 999.85);   // Do not cast (float)
				
				//Set checkin, checkout dates for Travel business
				ProductViewEvent pvEvent = new ProductViewEvent(product);
	
				//Test case. Set dates to ProductViewEvent
				pvEvent.setStartDate(new GregorianCalendar(2015, Calendar.DECEMBER, 21));
				pvEvent.setEndDate(new GregorianCalendar(2015, Calendar.DECEMBER, 31));
				pvEvent.setCurrency(Currency.getInstance("USD"));
				
				m_criteoEvent.send(pvEvent);				
			}
		});
		
		// Event for Criteo ViewList event
		m_ViewList =(Button)findViewById(R.id.button3);
		m_ViewList.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewList onClick", "Button3");
				Product product1, product2, product3;
				
				// These product IDs should be replaced with IDs in advertiser's feed
				product1 = new Product("11262072", 47900.05);
				product2 = new Product("6987408", 213010.34);
				product3 = new Product("12766277", 3500.34);
				
				ProductListViewEvent pvlEvent = new ProductListViewEvent(product1, product2, product3);
				pvlEvent.setStartDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 20));
				pvlEvent.setEndDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 30));
				m_criteoEvent.send(pvlEvent);
			}
		});
		
		// Event for Criteo ViewBasket event
		m_ViewCart =(Button)findViewById(R.id.button4);
		m_ViewCart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo ViewCart onClick", "Button4");
				
				// These product IDs should be replaced with IDs in advertiser's feed
				basketProduct1 = new BasketProduct("11262072", 47900.05, 1);
				basketProduct2 = new BasketProduct("6987408", 213010.34, 2);
				basketProduct3 = new BasketProduct("12766277", 3500.34, 3);
				
				BasketViewEvent bvEvent = new BasketViewEvent(basketProduct1, basketProduct2, basketProduct3);
				
				// Set additional parameters to BasketViewEvent
				bvEvent.setCurrency(Currency.getInstance("USD"));
				bvEvent.setStartDate(new GregorianCalendar(2015, Calendar.JANUARY, 21));
				bvEvent.setEndDate(new GregorianCalendar(2015, Calendar.JANUARY, 29));
				m_criteoEvent.send(bvEvent);			
			}
		});
		
		// Event for Criteo trackTransaction event
		m_Purchase =(Button)findViewById(R.id.button5);
		m_Purchase.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Criteo Purchase onClick", "Button5");
				
				// These product IDs should be replaced with IDs in advertiser's feed
				basketProduct1 = new BasketProduct("11262072", 47900.05, 1);
				basketProduct2 = new BasketProduct("6987408", 213010.34, 2);
				basketProduct3 = new BasketProduct("12766277", 3500.34, 3);
				
				TransactionConfirmationEvent tcEvent = new TransactionConfirmationEvent("123456789999", basketProduct1, basketProduct2, basketProduct3);
				tcEvent.setCurrency(Currency.getInstance("USD"));
				m_criteoEvent.send(tcEvent);	
				
			}
		});
		
		// Event for Criteo TransactionConfirmation with dates
		m_Search =(Button)findViewById(R.id.button6);
		m_Search.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				Log.d("Criteo Search onClick", "Button6");
				
				// These product IDs should be replaced with IDs in advertiser's feed
				basketProduct1 = new BasketProduct("11262072", 47900.05, 1);
				basketProduct2 = new BasketProduct("6987408", 213010.34, 2);
				basketProduct3 = new BasketProduct("12766277", 3500.34, 3);
				
				TransactionConfirmationEvent tcEvent = new TransactionConfirmationEvent("5678901234", basketProduct1 , basketProduct2, basketProduct3);
				
				// Set additional parameters to TransactionConfirmationEvent  
				tcEvent.setStartDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 21));
				tcEvent.setEndDate(new GregorianCalendar(2015, Calendar.NOVEMBER, 29));				
				tcEvent.setCurrency(Currency.getInstance("USD"));
				tcEvent.setNewCustomer(true);
				
				m_criteoEvent.send(tcEvent);				
			}
		});
		
				// Event for Criteo User Level event
				m_Search =(Button)findViewById(R.id.button7);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button7");
						
						// Not supported yet	
					}
				});
				
				
				// Event for Criteo User Status event
				m_Search =(Button)findViewById(R.id.button8);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button8");
						
						// Not supported yet
					}
				});
				
				// Event for Criteo Achievement Unlocked event
				m_Search =(Button)findViewById(R.id.button9);
				m_Search.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						Log.d("Criteo Search onClick", "Button9");
						// Not supported yet
					}
				});
				
        return ;
    }
	

}

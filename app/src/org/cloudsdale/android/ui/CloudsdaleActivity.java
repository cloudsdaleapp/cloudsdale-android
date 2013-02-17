package org.cloudsdale.android.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.merge.MergeAdapter;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import de.keyboardsurfer.android.widget.crouton.Style;

import org.cloudsdale.android.Cloudsdale;
import org.cloudsdale.android.R;
import org.cloudsdale.android.models.api.Cloud;
import org.cloudsdale.android.models.api.User;
import org.cloudsdale.android.ui.fragments.AboutFragment;
import org.cloudsdale.android.ui.fragments.CloudFragment;
import org.cloudsdale.android.ui.fragments.HomeFragment;
import org.cloudsdale.android.ui.fragments.SlidingMenuFragment;

/**
 * Base activity to do core setup. <br/>
 * Copyright (c) 2012 Cloudsdale.org
 * 
 * @author Jamison Greeley (berwyn.codeweaver@gmail.com)
 * 
 */
public class CloudsdaleActivity extends SlidingFragmentActivity implements
		SlidingMenuFragment.ISlidingMenuFragmentCallbacks, OnItemClickListener {

	@SuppressLint("ResourceAsColor")
	public static final Style	INFINITE			= new Style.Builder()
															.setBackgroundColor(
																	R.color.holo_red_light)
															.setDuration(
																	Style.DURATION_INFINITE)
															.build();

	private static final String	SAVED_FRAGMENT_KEY	= "savedFragment";

	private SlidingMenu			slidingMenu;
	private SlidingMenuFragment	slidingFragment;
	private boolean				isOnTablet;
	private HomeFragment		homeFragment;
	private AboutFragment		aboutFragment;
	private Cloudsdale			mAppInstance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		isOnTablet = findViewById(R.id.menu_frame) != null;
		homeFragment = new HomeFragment();
		aboutFragment = new AboutFragment();
		slidingFragment = new SlidingMenuFragment();
		mAppInstance = (Cloudsdale) getApplication();

		if (isOnTablet) {
			setupTablet();
		} else {
			setupPhone();
		}

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(SAVED_FRAGMENT_KEY)) {

		} else {
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, homeFragment).commit();
		}
	}

	private void setupTablet() {
		setBehindContentView(new View(this));
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		slidingMenu = getSlidingMenu();
		slidingMenu.setSlidingEnabled(false);
		slidingFragment.setCallback(this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, slidingFragment).commit();
	}

	private void setupPhone() {
		setBehindContentView(R.layout.widget_sliding_menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setSlidingActionBarEnabled(true);
		showContent();
		Context context = new ContextThemeWrapper(this,
				R.style.Theme_CloudsdaleDark);
		slidingMenu = getSlidingMenu();
		slidingMenu.setBehindOffsetRes(R.dimen.actionbar_home_width);
		slidingMenu.setSlidingEnabled(true);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
		ListView navList = (ListView) findViewById(android.R.id.list);
		MergeAdapter listAdapter = slidingFragment.generateAdapter(context);
		navList.setAdapter(listAdapter);
		navList.setOnItemClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (!isOnTablet) {
					toggle();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}

	@Override
	public void listItemClicked(String id, Class<?> clazz) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (clazz == StaticNavigation.class) {
			if (id.equals("home")) {
				ft.replace(R.id.content_frame, homeFragment);
				ft.commit();
			} else if (id.equals("about")) {
				if (aboutFragment == null) {
					aboutFragment = new AboutFragment();
				}
				ft.replace(R.id.content_frame, aboutFragment);
				ft.commit();
			}
		} else if (clazz == Cloud.class) {
			ft.replace(R.id.content_frame, new CloudFragment());
		}
		showContent();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		MergeAdapter mergeAdapter = (MergeAdapter) ((ListView) adapter)
				.getAdapter();
		if (mergeAdapter.getItem(position) instanceof StaticNavigation) {
			listItemClicked(
					((StaticNavigation) mergeAdapter.getItem(position))
							.getTextId(),
					StaticNavigation.class);
		} else if (mergeAdapter.getItem(position) instanceof Cloud) {
			listItemClicked(((Cloud) mergeAdapter.getItem(position)).getId(),
					Cloud.class);
		}

	}

	public void refreshSlidingMenuClouds(User user) {
	}
}
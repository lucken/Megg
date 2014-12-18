package com.radiumdigital.meggnify.megg;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.radiumdigital.meggnify.R;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AssignmentMapFragment extends Fragment {

    private String mUserToken;

	private SupportMapFragment mapFragment;
	
	private GoogleMap map;
	
	public AssignmentMapFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignment_map, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        FragmentManager fm = getActivity().getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_fragment);
        Log.i("Map", "Fragment: " + mapFragment);

        setupMap();
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();

		setupMap();
	}
	
	@Override
	public void onDestroy() {
		destroyMap();
		
		super.onDestroy();
	}
	
	void setupMap() {
		if (mapFragment != null && map == null) {
			map = mapFragment.getMap();
			if (map != null) {
				map.setMyLocationEnabled(true);
				map.getUiSettings().setMyLocationButtonEnabled(false);
				map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
					
					@Override
					public void onMyLocationChange(Location location) {
						// TODO Auto-generated method stub
						CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(location.getLatitude(), location.getLongitude()))
							.zoom(15)
							.build();
						map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
					}
					
				});
			} else {
				
			}
		}
	}
	
	void destroyMap() {
		getActivity().getSupportFragmentManager().beginTransaction()
			.remove(mapFragment)
			.commit();
		
		map = null;
	}

}

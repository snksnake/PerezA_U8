package org.example.permisosenmarshmallow;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsUtils {

    public List<Address> resolveAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            if(strAddress == null || strAddress.isEmpty()){
                addresses  = geocoder.getFromLocationName("Roger de Lauria, 28019, Madrid", 1);
            }
            else {
                addresses = geocoder.getFromLocationName(strAddress, 1);
            }
        } catch (IOException e) {
            return null;
        }

        return addresses;
    }

    public List<Address> resolveLongLat(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            return null;
        }
        return addresses;
    }
}

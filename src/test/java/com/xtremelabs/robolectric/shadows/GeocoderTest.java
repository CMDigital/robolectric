package com.xtremelabs.robolectric.shadows;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import com.xtremelabs.robolectric.WithTestDefaultsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(WithTestDefaultsRunner.class)
public class GeocoderTest {

    @Test
    public void shouldRecordLastLocationName() throws Exception {
        Geocoder geocoder = new Geocoder(new Activity());
        geocoder.getFromLocationName("731 Market St, San Francisco, CA 94103", 1);
        String lastLocationName = shadowOf(geocoder).getLastLocationName();
        
        assertEquals("731 Market St, San Francisco, CA 94103", lastLocationName);
    }

    @Test
    public void setsUpHasLocationInAddressFromLocationName() throws Exception {
        Geocoder geocoder = new Geocoder(new Activity());
        shadowOf(geocoder).setSimulatedHasLatLong(true, true);
        Address address = geocoder.getFromLocationName("731 Market St, San Francisco, CA 94103", 1).get(0);
        assertTrue(address.hasLatitude());
        assertTrue(address.hasLongitude());
        shadowOf(geocoder).setSimulatedHasLatLong(false, false);
        address = geocoder.getFromLocationName("731 Market St, San Francisco, CA 94103", 1).get(0);
        assertFalse(address.hasLatitude());
        assertFalse(address.hasLongitude());
    }

    @Test
    public void canReturnNoAddressesOnRequest() throws Exception {
        Geocoder geocoder = new Geocoder(new Activity());
        shadowOf(geocoder).setReturnNoResults(true);
        List<Address> result = geocoder.getFromLocationName("731 Market St, San Francisco, CA 94103", 1);
        assertEquals(0, result.size());
    }
}

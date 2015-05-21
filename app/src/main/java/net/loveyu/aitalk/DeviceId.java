package net.loveyu.aitalk;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class DeviceId {
    private static String id = null;

    public static String getId(Context context) {
        if (id != null) {
            return id;
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        id = deviceUuid.toString();
        return id;
    }
}

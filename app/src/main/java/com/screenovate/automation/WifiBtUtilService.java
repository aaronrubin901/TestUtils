package com.screenovate.automation;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class WifiBtUtilService extends Service {

    private static final String TAG = "WifiBtUtilService";
    private WifiManager mWifiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mWifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("enable_wifi_and_connect")) {
                enableWifi(new Runnable() {

                    @Override
                    public void run() {
                        connect(intent.getStringExtra("ssid"), intent.getStringExtra("password"),
                                new NotifyCompletedRunnable("Connected to wifi network " + intent.getStringExtra("ssid")));
                    }
                });
            } else if (intent.getAction().equals("enable_bt_and_unpair_all_bt_devices")) {
                setBluetoothState(true, new Runnable() {
                    @Override
                    public void run() {
                        unpairAllBtDevices();
                        new NotifyCompletedRunnable("Unpaired all BT devices").run();
                    }
                });
            } else if (intent.getAction().equals("enable_bt")) {
                setBluetoothState(true, new Runnable() {
                    @Override
                    public void run() {
                        new NotifyCompletedRunnable("BT enabled").run();
                    }
                });
            } else if (intent.getAction().equals("disable_bt")) {
                setBluetoothState(false, new Runnable() {
                    @Override
                    public void run() {
                        new NotifyCompletedRunnable("BT disabled").run();
                    }
                });
            } else if (intent.getAction().equals("enable_and_disconnect_from_wifi_networks")) {
                enableWifi(new Runnable() {

                    @Override
                    public void run() {
                        disconnectAndRemoveWifiNetworks();
                        new NotifyCompletedRunnable("Disconnected from all wifi networks").run();
                    }
                });
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void disconnectAndRemoveWifiNetworks() {
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for(WifiConfiguration currentConfig : list ) {
            wifiManager.removeNetwork(currentConfig.networkId);
            wifiManager.disconnect();
        }
    }

    private void setBluetoothState(boolean enabled, Runnable onCompleteRunnable) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled() == enabled) {
            onCompleteRunnable.run();
            return;
        }

        registerReceiver(new BtReceiver(onCompleteRunnable, enabled ? BluetoothAdapter.STATE_ON : BluetoothAdapter.STATE_OFF), new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        if (enabled) {
            adapter.enable();
        } else {
            adapter.disable();
        }
    }

    private class BtReceiver extends  BroadcastReceiver {
        private final Runnable mOnCompleteRunnable;
        private final int mTargetState;

        public BtReceiver(Runnable onCompleteRunanble, int targetState) {
            mOnCompleteRunnable = onCompleteRunanble;
            mTargetState = targetState;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if (state == mTargetState) {
                    mOnCompleteRunnable.run();
                    unregisterReceiver(this);
                }
            }
        }
    };

    private void unpairAllBtDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG, "unpairAllBtDevices: " + adapter.getBondedDevices().size());
        for (BluetoothDevice device : adapter.getBondedDevices()) {
            Log.d(TAG, "unbonding device " + device.getName());
            try {
                Method m = device.getClass()
                        .getMethod("removeBond", (Class[]) null);
                m.invoke(device, (Object[]) null);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.toString());
            } catch (InvocationTargetException e) {
                Log.e(TAG, e.toString());
            } catch (NoSuchMethodException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private Runnable mEmptyRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };

    private void enableWifi(Runnable onCompleteRunnable) {
        int state = mWifiManager.getWifiState();

        if (state == WifiManager.WIFI_STATE_DISABLING || state == WifiManager.WIFI_STATE_UNKNOWN) {
            throw new RuntimeException("Unsupported wifi state: " + state);
        }

        if (state == WifiManager.WIFI_STATE_ENABLING || state == WifiManager.WIFI_STATE_DISABLED) {
            IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(new WifiStateReceiver(onCompleteRunnable), filter);
            mWifiManager.setWifiEnabled(true);
        } else if (state == WifiManager.WIFI_STATE_ENABLED) {
            onCompleteRunnable.run();
        }
    }

    private void connect(final String ssid, String password, final Runnable onCompleteRunnable) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo.isConnected() &&  wifiInfo.getExtraInfo() != null && wifiInfo.getExtraInfo().replace("\"", "").equals(ssid)) {
            onCompleteRunnable.run();
            return;
        }

        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(new WifiConnectionReceiver(ssid, new Runnable() {
            @Override
            public void run() {
                onCompleteRunnable.run();
            }
        }), filter);


        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssid + "\"";
        if (password != null) {
            conf.preSharedKey = "\"" + password + "\"";
        } else {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        //remove others first
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for(WifiConfiguration currentConfig : list ) {
            wifiManager.removeNetwork(currentConfig.networkId);
        }

        wifiManager.addNetwork(conf);

        list = wifiManager.getConfiguredNetworks();
        for(WifiConfiguration currentConfig : list ) {
            if(currentConfig.SSID != null && currentConfig.SSID.equals("\"" + ssid + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(currentConfig.networkId, true);
                wifiManager.reconnect();

                break;
            }
        }
    }

    private class WifiStateReceiver extends BroadcastReceiver {
        private final Runnable mOnCompleteCallback;

        public WifiStateReceiver(Runnable onCompleteCallback) {
            mOnCompleteCallback = onCompleteCallback;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                Log.v(TAG, "wifi state changed, state=" + state);
                if (state == WifiManager.WIFI_STATE_ENABLED) {
                    unregisterReceiver(this);
                    mOnCompleteCallback.run();
                }
            }
        }
    };

    private class WifiConnectionReceiver extends  BroadcastReceiver {
        private final Runnable mOnCompleteListener;
        private final String mSsid;

        public WifiConnectionReceiver(String ssid, Runnable onCompleteListener) {
            mSsid = ssid;
            mOnCompleteListener = onCompleteListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                Log.v(TAG, "Network connection changed: " + wifiInfo);
                if (wifiInfo.isConnected() && wifiInfo.getExtraInfo() != null && wifiInfo.getExtraInfo().replace("\"", "").equals(mSsid)) {
                    unregisterReceiver(this);
                    mOnCompleteListener.run();
                }
            }
        }
    };

    private class NotifyCompletedRunnable implements Runnable {
        private final String mMessage;

        public NotifyCompletedRunnable(String message) {
            mMessage = message;
        }

        @Override
        public void run() {
            Log.d(TAG, mMessage);
        }
    }
}

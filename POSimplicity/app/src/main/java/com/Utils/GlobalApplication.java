package com.Utils;

import android.app.Application;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.Bluetooths.BluetoothConnector;
import com.Fragments.BaseFragment;
import com.SetupPrinter.BasePR;
import com.Socket.SocketIO;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.http.socketio.SocketIOClient;

public class GlobalApplication extends Application {	

	public static final String TAG = GlobalApplication.class.getSimpleName();	
	private static GlobalApplication singleton;
	private int deviceWidth,deviceHeight;
	private float deviceDensity;
	private SocketIOClient socketIOClient;
	private SocketIO socketIo;	
	private BaseFragment visibleFragment;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private BasePR mBasePrinterBT,mBasePrinterWF;
	public  BluetoothConnector bluetoothConnector = new BluetoothConnector();

	/**
	 * @return the mBasePrinterWF
	 */
	public BasePR getmBasePrinterWF() {
		return mBasePrinterWF;
	}

	/**
	 * @param mBasePrinterWF the mBasePrinterWF to set
	 */
	public void setmBasePrinterWF(BasePR mBasePrinterWF) {
		this.mBasePrinterWF = mBasePrinterWF;
	}

	/**
	 * @return the mBasePrinterBT
	 */
	public BasePR getmBasePrinterBT() {
		return mBasePrinterBT;
	}

	/**
	 * @param mBasePrinterBT the mBasePrinterBT to set
	 */
	public void setmBasePrinterBT(BasePR mBasePrinterBT) {
		this.mBasePrinterBT = mBasePrinterBT;
	}

	public void onScreenSize()
	{	
		DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
		deviceWidth   = displaymetrics.widthPixels;
		deviceHeight  = displaymetrics.heightPixels;
		deviceDensity = displaymetrics.density;
	}

	public BaseFragment getVisibleFragment() {
		return visibleFragment;
	}




	public void setVisibleFragment(BaseFragment fragment) {
		this.visibleFragment = fragment;
	}




	public SocketIO getSocketIo() {
		return socketIo;
	}

	public void setSocketIo(SocketIO socketIo) {
		this.socketIo = socketIo;
	}

	public float getDeviceDensity() {
		return deviceDensity;
	}


	public void setDeviceDensity(float deviceDensity) {
		this.deviceDensity = deviceDensity;
	}


	public int getDeviceWidth() {
		return deviceWidth;
	}


	public void setDeviceWidth(int deviceWidth) {
		this.deviceWidth = deviceWidth;
	}


	public int getDeviceHeight() {
		return deviceHeight;
	}



	public void setDeviceHeight(int deviceHeight) {
		this.deviceHeight = deviceHeight;
	}



	public static GlobalApplication getInstance() {
		return singleton;
	}

	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}


	public void setSocketIOClient(SocketIOClient socketIOClient) {
		this.socketIOClient = socketIOClient;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
	}
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
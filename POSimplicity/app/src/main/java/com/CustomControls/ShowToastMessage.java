package com.CustomControls;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.posimplicity.R;

public class ShowToastMessage {

	public static void showApprovedToast(Context context) {
		
		Toast ImageToast = new Toast(context);
        LinearLayout toastLayout = new LinearLayout(context);
        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView image = new ImageView(context);
        image.setImageResource(R.drawable.approved);
        toastLayout.addView(image);
        ImageToast.setGravity(Gravity.CENTER, 0, 0);
        ImageToast.setView(toastLayout);
        ImageToast.setDuration(Toast.LENGTH_SHORT);
        ImageToast.show();	
    }
	
	public static void showCCSwipeToast(Context context) {
		Toast ImageToast = new Toast(context);
        LinearLayout toastLayout = new LinearLayout(context);
        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView image = new ImageView(context);
        image.setImageResource(R.drawable.swipecard_toast_bg);
        toastLayout.addView(image);
        ImageToast.setGravity(Gravity.CENTER, 0, 0);
        ImageToast.setView(toastLayout);
        ImageToast.setDuration(Toast.LENGTH_SHORT);
        ImageToast.show();
		
    }
}

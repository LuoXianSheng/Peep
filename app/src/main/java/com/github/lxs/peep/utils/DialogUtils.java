package com.github.lxs.peep.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.lxs.peep.R;

public class DialogUtils {

	public static AlertDialog getLodingDialog(Context mContext) {
		Builder builder = new Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(R.layout.live_loding_dialog, null);
		builder.setView(view);
		// builder.setCancelable(false);
		return builder.create();
	}
}

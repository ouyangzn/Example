package com.ouyang.example.utils;

import android.app.ProgressDialog;
import android.content.Context;


/**
 * 等待进度圈
 * @author ouyangzn
 */
public class ProgressDialogUtil {

    /**
     * 显示一个进度圈
     * @param context 
     * @param Message 对话框中的信息
     * @param cancelable 对话框能否取消
     * @return ProgressDialog
     */
    public static synchronized ProgressDialog showProgressDialog(Context context, 
        String message, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        // show需要在setContentView之前，否则会报requestFeature() must be called before adding content
        dialog.show();
//        dialog.setContentView(R.layout.progress_dialog);
//        ((TextView)dialog.findViewById(R.id.tv_progressbar_tips)).setText(Message);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        return dialog;
    }
    /**
     * 隐藏进度对话框
     */
    public static synchronized void dismissProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 取消进度对话框,cancel时会回调OnCancelListener中的onCancel()
     */
    public static synchronized void cancelProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}

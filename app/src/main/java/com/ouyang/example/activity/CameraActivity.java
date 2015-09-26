package com.ouyang.example.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ouyang.example.R;
import com.ouyang.example.utils.SDCardUtils;

import java.io.File;
import java.io.IOException;

/**
 * 视频界面
 */
public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder surfaceHolder;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private boolean isRecording;

    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
        videoPath = initVideoPath();
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = mSurfaceView.getHolder();
        Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(this);
        Button end = (Button) findViewById(R.id.btn_end);
        end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                File file = new File(videoPath, "视频" + System.currentTimeMillis() + ".mp4");
                startRecord(file.getAbsolutePath());
                break;
            case R.id.btn_end:
                stopRecord();
                break;

        }

    }

    @NonNull
    private String initVideoPath() {
        String path = SDCardUtils.getAppRootPath(this) + "视频";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    private void stopRecord() {
        if (isRecording) {
            releaseMediaRecorder();
            releaseCamera();
        }
    }

    /**
     * 开始录制视频
     * @param path 视频保存路径
     */
    private void startRecord(String path) {
        if (isRecording) {
            return;
        }
        mMediaRecorder = new MediaRecorder();
        mCamera = Camera.open();
        setCameraDisplayOrientation(this, 0, mCamera);
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.reset();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 从照相机采集视频
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mMediaRecorder.setOutputFile(path);
        mMediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        // 预期准备
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();// 开始刻录
            isRecording = true;
        } catch (IllegalStateException e) {
            Toast.makeText(this, "录制视频出错!", Toast.LENGTH_LONG).show();
            releaseMediaRecorder();
            releaseCamera();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "录制视频出错!", Toast.LENGTH_LONG).show();
            releaseMediaRecorder();
            releaseCamera();
            e.printStackTrace();
        }
    }

    /**
     * 释放MediaRecorder
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
    /**
     * 释放Camera
     */
    private void releaseCamera() {
        if (mCamera != null){
            mCamera.lock();
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    /**
     * 设置摄像头方向与实际一致
     * @param activity
     * @param cameraId 摄像头id（即哪一个摄像头）
     * @param camera 摄像头对象
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        }
        else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

}

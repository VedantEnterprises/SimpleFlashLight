package com.rilixtech.simpleflashlight;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FlashLightActivity extends Activity implements ToggleButton.OnClickListener {
  //private Camera cam = Camera.open();          // used to open
  //private Parameters params = cam.getParameters();  // the camera and flashlight
  private Camera cam;          // used to open
  private Parameters params;  // the camera and flashlight
  private boolean hasFlashCamera;
  private Window mWindow;
  private WindowManager.LayoutParams mLayoutParams;
  private WindowManager.LayoutParams mOldLayoutParams;

  protected void onCreate(Bundle savedInstanceState) {
    //boolean flash = params.getFlashMode().equals(Parameters.FLASH_MODE_TORCH);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_window);
    ToggleButton tb = (ToggleButton) findViewById(R.id.main_toggle_btn);
    tb.setOnClickListener(this);

    // check flash camera, is there is one?
    hasDeviceFlashCamera();
    tb.setChecked(false);

    Button aboutBtn = (Button) findViewById(R.id.main_about_btn);
    aboutBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AlertDialog(getString(R.string.app_name), getString(R.string.about_content), "OK");
      }
    });


  }

  /**
   * Check if device have a flash camera
   * @return
   */
  private void hasDeviceFlashCamera() {
    try {
      cam = Camera.open();
      params = cam.getParameters();
      // try set a flash mode to camera, if success then device support flash camera.
      params.setFlashMode(Parameters.FLASH_MODE_OFF);
      hasFlashCamera = true;
    } catch (RuntimeException ex) {
      mWindow = getWindow();
      mLayoutParams = mWindow.getAttributes();
      mOldLayoutParams = mLayoutParams;
      hasFlashCamera = false;
    }
  }

  private void turnDisplayOn() {
    // Flashlights should be bright.
    mLayoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
    // params.buttonBrightness was added in API level 8. The additional brightness is not worth the added code size.
    // The screen will go to max brightness even without the following line, but the API doesn't guarantee it.
    mWindow.setAttributes(mLayoutParams);
    mWindow.addFlags(
        // Use the power button to turn flashlight off and on, even if you have a lock screen.
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            // A flashlight that turns itself off isn't a good flashlight.
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            // Turn the screen on if it isn't already on when launching (e.g., from ADB).
            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

  }

  private void turnDisplayOff() {
    mLayoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
    mWindow.setAttributes(mLayoutParams);
    mWindow.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    mWindow.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
  }

  // turns the flashlight on:
  private void turnOn() {
    if(hasFlashCamera) {
      cam.startPreview();
      params.setFlashMode(Parameters.FLASH_MODE_TORCH);
      cam.setParameters(params);
    } else {
      turnDisplayOn();
    }
  }

  // turns the flashlight off:
  private void turnOff() {
    if(hasFlashCamera) {
      params.setFlashMode(Parameters.FLASH_MODE_OFF);
      cam.setParameters(params);
    } else {
      turnDisplayOff();
    }
  }

  private void AlertDialog(String title, String msg, String btn) {
    SpannableString txt = new SpannableString(msg);
    Linkify.addLinks(txt, Linkify.WEB_URLS);
    final AlertDialog ad = new AlertDialog.Builder(this).setTitle(title)
        .setMessage(txt)
        .setIcon(R.mipmap.ic_launcher)
        .setPositiveButton(btn, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
          }
        })
        .create();
    ad.show();
    ((TextView) ad.findViewById(android.R.id.message)).setMovementMethod(
        LinkMovementMethod.getInstance());
  }

  // when the app is closed:
  protected void onDestroy() {
    turnOff();
    if(hasFlashCamera) {
      cam.stopPreview();
      cam.setPreviewCallback(null);
      cam.release();
    }
    super.onDestroy();
  }

  @Override public void onClick(View v) {
    boolean on = ((ToggleButton) v).isChecked();
    if (on) {
      turnOn();
    } else {
      turnOff();
    }
  }
}

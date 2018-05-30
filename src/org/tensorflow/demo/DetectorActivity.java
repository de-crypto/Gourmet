/*
 * Copyright 2016 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.demo;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.tensorflow.demo.OverlayView.DrawCallback;
import org.tensorflow.demo.env.BorderedText;
import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.tracking.MultiBoxTracker;
import org.tensorflow.demo.R; // Explicit import needed for internal Google builds.

import static org.tensorflow.demo.R.color.colorDarkbloodred;

/**
 * An activity that uses a TensorFlowMultiBoxDetector and ObjectTracker to detect and then track
 * objects.
 */
public class DetectorActivity extends CameraActivity implements OnImageAvailableListener {
  private static final Logger LOGGER = new Logger();

  //Initialize Database
  DBhandler mydatabase ;
  String[] starray,imgarray;

  private static final int ACTIVITY_NUM = 1;

  private static Context DetectorContext;
  // Configuration values for the prepackaged multibox model.
  private static final int MB_INPUT_SIZE = 224;
  private static final int MB_IMAGE_MEAN = 128;
  private static final float MB_IMAGE_STD = 128;
  private static final String MB_INPUT_NAME = "ResizeBilinear";
  private static final String MB_OUTPUT_LOCATIONS_NAME = "output_locations/Reshape";
  private static final String MB_OUTPUT_SCORES_NAME = "output_scores/Reshape";
  private static final String MB_MODEL_FILE = "file:///android_asset/multibox_model.pb";
  private static final String MB_LOCATION_FILE =
      "file:///android_asset/multibox_location_priors.txt";

  private static final int TF_OD_API_INPUT_SIZE = 300;
 // private static final String TF_OD_API_MODEL_FILE = "file:///android_asset/ssd_mobilenet_v1_android_export.pb";
 // private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/coco_labels_list.txt";

  //private static final String TF_OD_API_MODEL_FILE = "file:///android_asset/pizza_model.pb";
  //private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/pizza_labels.txt";

  private static final String TF_OD_API_MODEL_FILE = "file:///android_asset/22food_model.pb";
  private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/22food_labels.txt";

  //private static final String TF_OD_API_MODEL_FILE = "file:///android_asset/4food_1200_model.pb";
  //private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/4food_labels.txt";

  // Configuration values for tiny-yolo-voc. Note that the graph is not included with TensorFlow and
  // must be manually placed in the assets/ directory by the user.
  // Graphs and models downloaded from http://pjreddie.com/darknet/yolo/ may be converted e.g. via
  // DarkFlow (https://github.com/thtrieu/darkflow). Sample command:
  // ./flow --model cfg/tiny-yolo-voc.cfg --load bin/tiny-yolo-voc.weights --savepb --verbalise
  private static final String YOLO_MODEL_FILE = "file:///android_asset/graph-tiny-yolo-voc.pb";
  private static final int YOLO_INPUT_SIZE = 416;
  private static final String YOLO_INPUT_NAME = "input";
  private static final String YOLO_OUTPUT_NAMES = "output";
  private static final int YOLO_BLOCK_SIZE = 32;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // set the context
    DetectorContext = this;

    //Create Database
    mydatabase = new DBhandler(DetectorActivity.this,null,null,1);

    //Delete the Detectedfood table if already exists
    mydatabase.Delete_detectedfoodtable();

    // Now create the fresh detectedfood table
    mydatabase.Create_detectedfoodtable();

    // Extracting string array from food info
    Intent i = getIntent();
    starray = i.getStringArrayExtra("stringarray");
    imgarray = i.getStringArrayExtra("imagearray");

    if(starray!=null) {
      // Add food items to detectedfood database
      for (int index = 0; index < starray.length; index++) {
        mydatabase.addDetectedFooditem(starray[index]);
      }
    }
    if(imgarray!=null && starray!=null){
      // Add image of items to detectedfood database
      for (int index = 0; index < imgarray.length; index++){
        mydatabase.addimage(starray[index],imgarray[index]);
      }
    }

    BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx)
            findViewById(R.id.bottom_navigation);

    Menu menu = bottomNavigationView.getMenu();
    MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
    menuItem.setChecked(true);

    bottomNavigationView.enableAnimation(false);
    bottomNavigationView.enableItemShiftingMode(false);
    bottomNavigationView.enableShiftingMode(false);

    //bottomNavigationView.setIconSize(20,20);

    bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                  case R.id.action_collection:
                    Intent i = new Intent(DetectorActivity.this,MyCollection.class);
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(DetectorActivity.this, R.anim.slide_in_right, R.anim.slide_out_right);
                    if(starray!=null) {
                      i.putExtra("stringarray", starray);
                    }
                    if(imgarray!=null){
                      i.putExtra("imagearray",imgarray);
                    }
                    DetectorActivity.this.startActivity(i, options.toBundle());
                    DetectorActivity.this.finish();
                    return true;
                  case R.id.action_detect:
                    return true;
                  case R.id.action_detectedfood:

                    String[] temparray = mydatabase.get_DetectedFoodname();
                    if(st!=null && temparray!=null){
                      if(temparray.length>st.length){
                        st = temparray;
                      }
                    }

                    Intent i2 = new Intent(DetectorActivity.this,DetectedFooditems.class);
                    ActivityOptions options2 =
                            ActivityOptions.makeCustomAnimation(DetectorActivity.this, R.anim.slide_in_left, R.anim.slide_out_left);
                    if(starray!=null) {
                      i2.putExtra("stringarray", starray);
                    }
                    if(imgarray!=null){
                      i2.putExtra("imagearray",imgarray);
                    }
                    DetectorActivity.this.startActivity(i2, options2.toBundle());
                    DetectorActivity.this.finish();
                    return true;
                  case R.id.action_mydiet:
                    Intent i1 = new Intent(DetectorActivity.this,MyDiet.class);
                    ActivityOptions options1 =
                            ActivityOptions.makeCustomAnimation(DetectorActivity.this, R.anim.slide_in_left, R.anim.slide_out_left);
                    if(st!=null) {
                      i1.putExtra("stringarray", st);
                    }
                    if(imgarray!=null){
                      i1.putExtra("imagearray",imgarray);
                    }
                    DetectorActivity.this.startActivity(i1, options1.toBundle());
                    DetectorActivity.this.finish();
                    return true;
                }
                return true;
              }
            });

    /*temparray = mydatabase.get_DetectedFoodname();

    if(temparray!=null){
      //showbuttonwiggle();
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          // Do something after 5s = 5000ms
          Button button = (Button)findViewById(R.id.btn_detect);
          final Animation myAnim = AnimationUtils.loadAnimation(DetectorActivity.this, R.anim.bounce);

          // Use bounce interpolator with amplitude 0.2 and frequency 20
          MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
          myAnim.setInterpolator(interpolator);

          button.startAnimation(myAnim);
        }
      }, 2000);
    }*/

  }

  // Which detection model to use: by default uses Tensorflow Object Detection API frozen
  // checkpoints.  Optionally use legacy Multibox (trained using an older version of the API)
  // or YOLO.
  private enum DetectorMode {
    TF_OD_API, MULTIBOX, YOLO;
  }
  private static final DetectorMode MODE = DetectorMode.TF_OD_API;

  // Minimum detection confidence to track a detection.
  private static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;
  private static final float MINIMUM_CONFIDENCE_MULTIBOX = 0.1f;
  private static final float MINIMUM_CONFIDENCE_YOLO = 0.25f;

  private static final boolean MAINTAIN_ASPECT = MODE == DetectorMode.YOLO;

  private static final Size DESIRED_PREVIEW_SIZE = new Size(1280, 960);

  private static final boolean SAVE_PREVIEW_BITMAP = false;
  private static final float TEXT_SIZE_DIP = 10;

  private Integer sensorOrientation;

  private Classifier detector;

  private long lastProcessingTimeMs;
  private Bitmap rgbFrameBitmap = null;
  private Bitmap croppedBitmap = null;
  private Bitmap cropCopyBitmap = null;

  private boolean computingDetection = false;

  private long timestamp = 0;

  private Matrix frameToCropTransform;
  private Matrix cropToFrameTransform;

  private MultiBoxTracker tracker;

  private byte[] luminanceCopy;

  private BorderedText borderedText;
  @Override
  public void onPreviewSizeChosen(final Size size, final int rotation) {
    final float textSizePx =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
    borderedText = new BorderedText(textSizePx);
    borderedText.setTypeface(Typeface.MONOSPACE);

    tracker = new MultiBoxTracker(this);

    int cropSize = TF_OD_API_INPUT_SIZE;
    if (MODE == DetectorMode.YOLO) {
      detector =
          TensorFlowYoloDetector.create(
              getAssets(),
              YOLO_MODEL_FILE,
              YOLO_INPUT_SIZE,
              YOLO_INPUT_NAME,
              YOLO_OUTPUT_NAMES,
              YOLO_BLOCK_SIZE);
      cropSize = YOLO_INPUT_SIZE;
    } else if (MODE == DetectorMode.MULTIBOX) {
      detector =
          TensorFlowMultiBoxDetector.create(
              getAssets(),
              MB_MODEL_FILE,
              MB_LOCATION_FILE,
              MB_IMAGE_MEAN,
              MB_IMAGE_STD,
              MB_INPUT_NAME,
              MB_OUTPUT_LOCATIONS_NAME,
              MB_OUTPUT_SCORES_NAME);
      cropSize = MB_INPUT_SIZE;
    } else {
      try {
        detector = TensorFlowObjectDetectionAPIModel.create(
            getAssets(), TF_OD_API_MODEL_FILE, TF_OD_API_LABELS_FILE, TF_OD_API_INPUT_SIZE);
        cropSize = TF_OD_API_INPUT_SIZE;
      } catch (final IOException e) {
        LOGGER.e("Exception initializing classifier!", e);
        Toast toast =
            Toast.makeText(
                getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
        toast.show();
        finish();
      }
    }

    previewWidth = size.getWidth();
    previewHeight = size.getHeight();

    sensorOrientation = rotation - getScreenOrientation();
    LOGGER.i("Camera orientation relative to screen canvas: %d", sensorOrientation);

    LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
    rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
    croppedBitmap = Bitmap.createBitmap(cropSize, cropSize, Config.ARGB_8888);

    frameToCropTransform =
        ImageUtils.getTransformationMatrix(
            previewWidth, previewHeight,
            cropSize, cropSize,
            sensorOrientation, MAINTAIN_ASPECT);

    cropToFrameTransform = new Matrix();
    frameToCropTransform.invert(cropToFrameTransform);

    trackingOverlay = (OverlayView) findViewById(R.id.tracking_overlay);
    trackingOverlay.addCallback(
        new DrawCallback() {
          @Override
          public void drawCallback(final Canvas canvas) {
            tracker.draw(canvas);
            if (isDebug()) {
              tracker.drawDebug(canvas);
            }
          }
        });

    addCallback(
        new DrawCallback() {
          @Override
          public void drawCallback(final Canvas canvas) {
            if (!isDebug()) {
              return;
            }
            final Bitmap copy = cropCopyBitmap;
            if (copy == null) {
              return;
            }

            final int backgroundColor = Color.argb(100, 0, 0, 0);
            canvas.drawColor(backgroundColor);

            final Matrix matrix = new Matrix();
            final float scaleFactor = 2;
            matrix.postScale(scaleFactor, scaleFactor);
            matrix.postTranslate(
                canvas.getWidth() - copy.getWidth() * scaleFactor,
                canvas.getHeight() - copy.getHeight() * scaleFactor);
            canvas.drawBitmap(copy, matrix, new Paint());

            final Vector<String> lines = new Vector<String>();
            if (detector != null) {
              final String statString = detector.getStatString();
              final String[] statLines = statString.split("\n");
              for (final String line : statLines) {
                lines.add(line);
              }
            }
            lines.add("");

            lines.add("Frame: " + previewWidth + "x" + previewHeight);
            lines.add("Crop: " + copy.getWidth() + "x" + copy.getHeight());
            lines.add("View: " + canvas.getWidth() + "x" + canvas.getHeight());
            lines.add("Rotation: " + sensorOrientation);
            lines.add("Inference time: " + lastProcessingTimeMs + "ms");

            borderedText.drawLines(canvas, 10, canvas.getHeight() - 10, lines);
          }
        });
  }

  OverlayView trackingOverlay;

  // overriding processImage function from CameraActivity
  @Override
  protected void processImage() {
    ++timestamp;
    final long currTimestamp = timestamp;
    byte[] originalLuminance = getLuminance();
    tracker.onFrame(
        previewWidth,
        previewHeight,
        getLuminanceStride(),
        sensorOrientation,
        originalLuminance,
        timestamp);
    trackingOverlay.postInvalidate();

    // No mutex needed as this method is not reentrant.
    if (computingDetection) {
      readyForNextImage();
      return;
    }
    computingDetection = true;
    LOGGER.i("Preparing image " + currTimestamp + " for detection in bg thread.");

    rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);

    if (luminanceCopy == null) {
      luminanceCopy = new byte[originalLuminance.length];
    }
    System.arraycopy(originalLuminance, 0, luminanceCopy, 0, originalLuminance.length);
    readyForNextImage();

    final Canvas canvas = new Canvas(croppedBitmap);
    canvas.drawBitmap(rgbFrameBitmap, frameToCropTransform, null);
    // For examining the actual TF input.
    if (SAVE_PREVIEW_BITMAP) {
      ImageUtils.saveBitmap(croppedBitmap);
    }

    runInBackground(
        new Runnable() {
          @Override
          public void run() {
            LOGGER.i("Running detection on image " + currTimestamp);
            final long startTime = SystemClock.uptimeMillis();
            final List<Classifier.Recognition> results = detector.recognizeImage(croppedBitmap);
            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;

            cropCopyBitmap = Bitmap.createBitmap(croppedBitmap);
            final Canvas canvas = new Canvas(cropCopyBitmap);
            final Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(0.0f);

            float minimumConfidence = MINIMUM_CONFIDENCE_TF_OD_API;
            switch (MODE) {
              case TF_OD_API:
                minimumConfidence = MINIMUM_CONFIDENCE_TF_OD_API;
                break;
              case MULTIBOX:
                minimumConfidence = MINIMUM_CONFIDENCE_MULTIBOX;
                break;
              case YOLO:
                minimumConfidence = MINIMUM_CONFIDENCE_YOLO;
                break;
            }

            final List<Classifier.Recognition> mappedRecognitions =
                new LinkedList<Classifier.Recognition>();

            for (final Classifier.Recognition result : results) {
              final RectF location = result.getLocation();
              if (location != null && result.getConfidence() >= minimumConfidence) {
                canvas.drawRect(location, paint);

                // Add detected object to the database
                Boolean check = mydatabase.CheckIsDataAlreadyInDBorNot(result.getTitle());
                if (!check) {
                  mydatabase.addDetectedFooditem(result.getTitle());
                }
                // Add detected food to mycollection
                Boolean flag = mydatabase.CheckIsDataAlreadyIn_mycollection(result.getTitle());
                if (!flag) {
                  mydatabase.addFoodto_mycollection(result.getTitle());
                }
                /*if (result.getConfidence() >= 0.75f) {
                  try {
                    View rootView = getWindow().getDecorView().findViewById(R.id.container);

                    View screenView = rootView.getRootView();
                    screenView.setDrawingCacheEnabled(true);
                    Bitmap thumbnail = Bitmap.createBitmap(screenView.getDrawingCache());
                    screenView.setDrawingCacheEnabled(false);


                    // storing image the image of foods
                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");
                    FileOutputStream outputStream = new FileOutputStream(destination);
                    int quality = 90;
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    //add image path in the user's database
                    String path = destination.toString();
                    mydatabase.addimage(result.getTitle(), path);
                  } catch (Throwable e) {
                    e.printStackTrace();
                  }*/

                  //Toast.makeText(DetectorActivity.this,result.getTitle(),Toast.LENGTH_LONG).show();
                  cropToFrameTransform.mapRect(location);
                  result.setLocation(location);
                  mappedRecognitions.add(result);

              }
            }

            tracker.trackResults(mappedRecognitions, luminanceCopy, currTimestamp);
            trackingOverlay.postInvalidate();

            requestRender();
            computingDetection = false;
          }
        });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.camera_connection_fragment_tracking;
  }

  @Override
  protected Size getDesiredPreviewFrameSize() {
    return DESIRED_PREVIEW_SIZE;
  }

  @Override
  public void onSetDebug(final boolean debug) {
    detector.enableStatLogging(debug);
  }
}

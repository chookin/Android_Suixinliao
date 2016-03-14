package com.tencent.qcloud.timchat.ui.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.opengl.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.hardware.Camera.Size;

import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.utils.LogUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/** 摄像头预览界面控件 */
public class CameraPreview extends GLSurfaceView implements GLSurfaceView.Renderer,SurfaceTexture.OnFrameAvailableListener {

    private Camera mCamera;
    private Context mContext;
    private SurfaceTexture mSurfaceTexture;
    private int texture;
    private final Shader mOffscreenShader = new Shader();
    private int mWidth, mHeight;
    private boolean updateTexture = false;
    /**
     * OpenGL params
     */
    private ByteBuffer mFullQuadVertices;
    private float[] mTransformM = new float[16];
    private float[] mOrientationM = new float[16];
    private float[] mRatio = new float[2];

    private SurfaceTexture surface;
    private int orgPreviewWidth;
    private int orgPreviewHeight;
    private static final String TAG = "CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mContext = context;
        mCamera = camera;
        init();
    }


    private void init(){
        //Create full scene quad buffer
        final byte FULL_QUAD_COORDS[] = {-1, 1, -1, -1, 1, 1, 1, -1};
        mFullQuadVertices = ByteBuffer.allocateDirect(4 * 2);
        mFullQuadVertices.put(FULL_QUAD_COORDS).position(0);

        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /**
     * Called when the surface is created or recreated.
     * <p>
     * Called when the rendering thread
     * starts and whenever the EGL context is lost. The EGL context will typically
     * be lost when the Android device awakes after going to sleep.
     * <p>
     * Since this method is called at the beginning of rendering, as well as
     * every time the EGL context is lost, this method is a convenient place to put
     * code to create resources that need to be created when the rendering
     * starts, and that need to be recreated when the EGL context is lost.
     * Textures are an example of a resource that you might want to create
     * here.
     * <p>
     * Note that when the EGL context is lost, all OpenGL resources associated
     * with that context will be automatically deleted. You do not need to call
     * the corresponding "glDelete" methods such as glDeleteTextures to
     * manually delete these lost resources.
     * <p>
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param config the EGLConfig of the created surface. Can be used
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        texture = createTexture();
//        surface = new SurfaceTexture(texture);
//        mDirectVideo = new DirectVideo(texture);
//        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
//        surface.setOnFrameAvailableListener(this);
//        try {
//            mCamera.setPreviewTexture(surface);
//            mCamera.startPreview();
//        } catch (IOException e) {
//            LogUtils.e(TAG, "Error setting camera preview: " + e.getMessage());
//        }
        try {
            mOffscreenShader.setProgram(R.raw.vshader, R.raw.fshader, mContext);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Called when the surface changed size.
     * <p>
     * Called after the surface is created and whenever
     * the OpenGL ES surface size changes.
     * <p>
     * Typically you will set your viewport here. If your camera
     * is fixed then you could also set your projection matrix here:
     * <pre class="prettyprint">
     * void onSurfaceChanged(GL10 gl, int width, int height) {
     * gl.glViewport(0, 0, width, height);
     * // for a fixed camera, set the projection too
     * float ratio = (float) width / height;
     * gl.glMatrixMode(GL10.GL_PROJECTION);
     * gl.glLoadIdentity();
     * gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
     * }
     * </pre>
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight= height;


        //set up surfacetexture------------------
        SurfaceTexture oldSurfaceTexture = mSurfaceTexture;
        mSurfaceTexture = new SurfaceTexture(texture);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        if(oldSurfaceTexture != null){
            oldSurfaceTexture.release();
        }


        //set camera para-----------------------------------
        int camera_width =0;
        int camera_height =0;
        try{
            mCamera.setPreviewTexture(mSurfaceTexture);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        Camera.Parameters param = mCamera.getParameters();
        List<Size> psize = param.getSupportedPreviewSizes();
        if(psize.size() > 0 ){
            int i;
            for (i = 0; i < psize.size(); i++){
                if(psize.get(i).width < width || psize.get(i).height < height)
                    break;
            }
            if(i>0)
                i--;
            param.setPreviewSize(psize.get(i).width, psize.get(i).height);

            camera_width = psize.get(i).width;
            camera_height= psize.get(i).height;

        }

        //get the camera orientation and display dimension------------
        if(mContext.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT){
            Matrix.setRotateM(mOrientationM, 0, 90.0f, 0f, 0f, 1f);
            mRatio[1] = camera_width*1.0f/height;
            mRatio[0] = camera_height*1.0f/width;
        }
        else{
            Matrix.setRotateM(mOrientationM, 0, 0.0f, 0f, 0f, 1f);
            mRatio[1] = camera_height*1.0f/height;
            mRatio[0] = camera_width*1.0f/width;
        }

        //start camera-----------------------------------------
        mCamera.setParameters(param);
        mCamera.startPreview();

        //start render---------------------
        requestRender();
    }

    /**
     * Called to draw the current frame.
     * <p>
     * This method is responsible for drawing the current frame.
     * <p>
     * The implementation of this method typically looks like this:
     * <pre class="prettyprint">
     * void onDrawFrame(GL10 gl) {
     * gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
     * //... other gl calls to render the scene ...
     * }
     * </pre>
     *
     * @param gl the GL interface. Use <code>instanceof</code> to
     *           test if the interface supports GL11 or higher interfaces.
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //render the texture to FBO if new frame is available
        if(updateTexture){
            mSurfaceTexture.updateTexImage();
            mSurfaceTexture.getTransformMatrix(mTransformM);

            updateTexture = false;

            GLES20.glViewport(0, 0, mWidth, mHeight);

            mOffscreenShader.useProgram();

            int uTransformM = mOffscreenShader.getHandle("uTransformM");
            int uOrientationM = mOffscreenShader.getHandle("uOrientationM");
            int uRatioV = mOffscreenShader.getHandle("ratios");

            GLES20.glUniformMatrix4fv(uTransformM, 1, false, mTransformM, 0);
            GLES20.glUniformMatrix4fv(uOrientationM, 1, false, mOrientationM, 0);
            GLES20.glUniform2fv(uRatioV, 1, mRatio, 0);

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

            renderQuad(mOffscreenShader.getHandle("aPosition"));
        }
    }

    private void renderQuad(int aPosition){
        GLES20.glVertexAttribPointer(aPosition, 2, GLES20.GL_BYTE, false, 0, mFullQuadVertices);
        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }



    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        updateTexture = true;
        requestRender();
    }

    static private int createTexture()
    {
        int[] texture = new int[1];
        GLES20.glGenTextures(1,texture, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return texture[0];
    }

    static public int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }


//    /**
//     * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
//     *
//     * @param surface The surface returned by
//     *                {@link TextureView#getSurfaceTexture()}
//     * @param width   The width of the surface
//     * @param height  The height of the surface
//     */
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        try
//        {
////            Camera.Parameters parameters = mCamera.getParameters();
////            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
////            Pair<Integer, Integer> size = getMaxSize(parameters.getSupportedPreviewSizes());
////            parameters.setPreviewSize(size.first, size.second);
////            orgPreviewWidth = size.first;
////            orgPreviewHeight = size.second;
////            mCamera.setParameters(parameters);
////            updateTextureMatrix(2000,1500);
//            mCamera.setDisplayOrientation(90);
//            GLSurfaceView surfaceView;
//            surfaceView.set
//            mCamera.setPreviewTexture(surface);
//            mCamera.startPreview();
//        } catch (IOException ioe) {
//            // Something bad happened
//        }
//    }
//
//    /**
//     * Invoked when the {@link SurfaceTexture}'s buffers size changed.
//     *
//     * @param surface The surface returned by
//     *                {@link TextureView#getSurfaceTexture()}
//     * @param width   The new width of the surface
//     * @param height  The new height of the surface
//     */
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//    }
//
//    /**
//     * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
//     * If returns true, no rendering should happen inside the surface texture after this method
//     * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
//     * Most applications should return true.
//     *
//     * @param surface The surface about to be destroyed
//     */
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        return false;
//    }
//
//    /**
//     * Invoked when the specified {@link SurfaceTexture} is updated through
//     * {@link SurfaceTexture#updateTexImage()}.
//     *
//     * @param surface The surface just updated
//     */
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
////        setCameraDisplayOrientation(getContext())
//    }
//
//    private void updateTextureMatrix(int width, int height)
//    {
//        boolean isPortrait = false;
//        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
//        if (display.getRotation() == Surface.ROTATION_0 || display.getRotation() == Surface.ROTATION_180) isPortrait = true;
//        else if (display.getRotation() == Surface.ROTATION_90 || display.getRotation() == Surface.ROTATION_270) isPortrait = false;
//
//        int previewWidth = orgPreviewWidth;
//        int previewHeight = orgPreviewHeight;
//        if (isPortrait)
//        {
//            previewWidth = orgPreviewHeight;
//            previewHeight = orgPreviewWidth;
//        }
//        float ratioSurface = (float) width / height;
//        float ratioPreview = (float) previewWidth / previewHeight;
//        float scaleX;
//        float scaleY;
//        if (ratioSurface > ratioPreview)
//        {
//            scaleX = (float) height / previewHeight;
//            scaleY = 1;
//        }
//        else
//        {
//            scaleX = 1;
//            scaleY = (float) width / previewWidth;
//        }
//        Matrix matrix = new Matrix();
//        matrix.setScale(scaleX, scaleY);
//        setTransform(matrix);
//        float scaledWidth = width * scaleX;
//        float scaledHeight = height * scaleY;
//        float dx = (width - scaledWidth) / 2;
//        float dy = (height - scaledHeight) / 2;
////        setTranslationX(dx);
////        setTranslationY(dy);
//    }
//
//    private static Pair<Integer, Integer> getMaxSize(List<Camera.Size> list)
//    {
//        int width = 0;
//        int height = 0;
//
//        for (Camera.Size size : list) {
//            if (size.width * size.height > width * height)
//            {
//                width = size.width;
//                height = size.height;
//            }
//        }
//
//        return new Pair<Integer, Integer>(width, height);
//    }
//
//    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera)
//    {
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(cameraId, info);
//        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
//        int degrees = 0;
//        switch (rotation)
//        {
//            case Surface.ROTATION_0:
//                degrees = 0;
//                break;
//            case Surface.ROTATION_90:
//                degrees = 90;
//                break;
//            case Surface.ROTATION_180:
//                degrees = 180;
//                break;
//            case Surface.ROTATION_270:
//                degrees = 270;
//                break;
//        }
//
//        int result;
//        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
//        {
//            result = (info.orientation + degrees) % 360;
//            result = (360 - result) % 360;  // compensate the mirror
//        }
//        else
//        {  // back-facing
//            result = (info.orientation - degrees + 360) % 360;
//        }
//        camera.setDisplayOrientation(result);
//
//        Camera.Parameters params = camera.getParameters();
//        params.setRotation(result);
//        camera.setParameters(params);
//    }


}

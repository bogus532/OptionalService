package com.xprime.CameraPW;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//----------------------------------------------------------------------
/*
 * 액티비티의 화면에 카메라 촬영 동영상을 재생시킬 객체입니다. SurfaceHolder.Callback 의 메소드를 구현합니다. 카메라가
 * 촬영하는 영상이 생성되고, 변경되고, 소멸될때 시스템이 호출할 메소드들을 구현하고 있습니다.
 */
public class PreviewCamera extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder; // 카메라 객체 제어자
	Camera mCamera; // 카메라 객체

	/*
	 * 생성자에서 카메라 촬영 준비(초기화)를 합니다.
	 */
	PreviewCamera(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		// 카메라 객체 제어자 객체를 얻어와, 콜백을 등록하여 현재 Preview 객체의
		// surfaceCreated, surfaceDestroyed, surfaceChanged
		// 메소드가 시스템에 의해서 호출될 수 있도록 설정합니다.
		// 이벤트가 발생할때마다 위의 메소드들이 호출될 수 있도록 콜백을 등록합니다.
		// 현재 클래스 객체가 콜백으로 등록될 수 있는것은, 현재 클래스가 SurfaceHolder.Callback
		// 를 구현하고 있기 때문입니다. 이 인터페이스에 정의된 메소드를 시스템이 호출하게 됩니다.
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * ) 카메라가 촬영을 최초 시작할때 호출됩니다. 카메라는 스마트폰 전역적으로 하나만 존재하기 때문에 동시에 여러 프로세스에서 공유하여
	 * 사용할 수 가 없습니다. 그래서 여기서 카메라 전역 클래스 Camera 클래스를 open 메소드로 얻어와서, 카메라 사용을 시작하게
	 * 됩니다. 카메라 객체의 setPreviewDisplay 메소드에 카메라 제어자 객체를 넘겨서, 미리보기 기능이 시작되도록
	 * 설정합니다. 이때부터 surfaceChanged 메소드가 호출이 계속해서 되면서, 실제로 액티비티에 촬영 동영상이 출력이
	 * 시작됩니다.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
			// TODO: add more exception handling logic here
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder) 카메라 촬영이 종료될때, 이 메소드가 호출되고, 이때 카메라 미리보기를 중지하고, 반드시 release
	 * 메소드로 현재 프로세스가 카메라를 점유하고 있는 것을 해지해야, 다른 프로그램에서 카메라를 사용할 수 가 있게됩니다.
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	/*
	 * 최적화 사이즈를 반환합니다.
	 */
	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;
		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int) surfaceCreated 메소드 호출 이후부터 이 메소드가 카메라 촬영영상이 바뀔때마다 호출되면서,
	 * 액티비티에 미리보기 영상을 출력합니다.
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();
		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width, optimalSize.height);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

}

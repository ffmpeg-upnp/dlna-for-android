package com.jcn.dlna.sdk;

import android.content.Context;

import com.jcn.dlna.sdk.ServiceManager.ServiceState;
import com.jcn.dlna.sdk.dmc.DeviceManager;
import com.jcn.dlna.sdk.dms.content.MediaStoreContent;

/**
 * dlna���ܿ�����
 */
/**
 * @author daoben
 * 
 */
public class Dlna {

	public static final int TYPE_DMC = 1;
	public static final int TYPE_DMS = 2;
	public static final int TYPE_DMC_DMS = 3;

	/**
	 * dlna�������ؽ��
	 * <p>
	 * SUCCESS �����ɹ�<br>
	 * TOO_FREQUENTLY ����̫Ƶ����ȡ����ǰ����<br>
	 * FAIL ����ʧ��
	 */
	public static enum ActionResult {
		SUCCESS, TOO_FREQUENTLY, FAIL
	}

	private static Context context;
	private static OnDlnaStateChangeListener listener;

	public static Context getContext() {
		return Dlna.context;
	}

	public static Context getApplicationContext() {
		return context == null ? null : context.getApplicationContext();
	}

	public static OnDlnaStateChangeListener getListener() {
		return listener;
	}

	public static void init(Context context, OnDlnaStateChangeListener listener) {
		Dlna.context = context;
		Dlna.listener = listener;
	}

	/**
	 * ����dlna����ѡ���Եؿ���dmc,dms��ͬʱ����dmc��dms
	 * 
	 * @param type
	 *            :Dlna.DMC\DMS\DMC_DMS
	 * @param context
	 * @return
	 */
	public static void start(int type) {
		ServiceManager.getInstance().start(type);
	}

	/**
	 * �ر�dlna����
	 */
	public static void stop() {
		ServiceManager.getInstance().stop();
	}

	public static ServiceState getState() {
		return ServiceManager.getInstance().getState();
	}

	/**
	 * DMS�������
	 */
	public static class DMS {

		public static void setShareEnable(boolean enable) {
			MediaStoreContent.getInstance().setShareEnable(enable);
		}

		public static boolean addShareFilePath(String path) {
			return MediaStoreContent.getInstance().addShareFilePath(path);
		}

		public static boolean removeShareFilePath(String path) {
			return MediaStoreContent.getInstance().removeShareFilePath(path);
		}

		public static boolean removeAllShareFile() {
			return MediaStoreContent.getInstance().removeAllShareFile();
		}

		public static void shareExternalPhotos() {
			MediaStoreContent.getInstance().shareExternalPhotos();
		}

		public static void shareExternalMusics() {
			MediaStoreContent.getInstance().shareExternalMusics();
		}

		public static void shareExternalMovies() {
			MediaStoreContent.getInstance().shareExternalMovies();
		}

		public static void shareAllExternalMedia() {
			MediaStoreContent.getInstance().shareAllExternalMedia();
		}
	}

	/**
	 * ʹ��Ĭ�Ϸ�ʽ����������
	 */
	public static DeviceManager getDeviceManager() {
		return DeviceManager.getInstance();
	}

	public interface OnDlnaStateChangeListener {
		public void onStateChange(ServiceState state);
	}

}

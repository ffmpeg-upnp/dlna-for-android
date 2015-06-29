package com.jcn.dlna.sdk.dmc;

import java.io.UnsupportedEncodingException;

import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.UDAServiceType;

import android.text.TextUtils;

import com.jcn.dlna.sdk.ActionHandler;
import com.jcn.dlna.sdk.MediaInfo;

public class DmrDevice implements Comparable<DmrDevice> {

	private Device<?, ?, ?> device;
	private String friendlyName = "";

	public static enum PlayState {
		PLAYING, LOADING, STOPPED, PAUSED, FALSE
	}

	protected DmrDevice(Device<?, ?, ?> device) {
		this.device = device;
		String friendlyName = device.getDetails().getFriendlyName();
		if (!TextUtils.isEmpty(friendlyName)) {
			try {
				byte[] bytes = new byte[friendlyName.length()];
				for (int i = 0; i < bytes.length; ++i) {
					bytes[i] = (byte) (friendlyName.charAt(i) & 0x00FF);
				}
				this.friendlyName = new String(bytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public String getName() {
		return friendlyName;
	}

	public String getType() {
		return device.getType().toString();
	}

	protected Service<?, ?> findService(UDAServiceType udaServiceType) {
		return device.findService(udaServiceType);
	}

	public String getUdn() {
		return device.getIdentity().getUdn().toString();
	}

	public String getIp() {
		String http = "http://";
		int firstIndex = device.getIdentity().toString().indexOf(http)
				+ http.length();
		String remain = device.getIdentity().toString().substring(firstIndex);
		String ip = remain.substring(0, remain.indexOf("/"));
		int secondIndex = ip.indexOf(":");
		String finalIp = ip.substring(0, secondIndex);
		return finalIp;
	}

	/**
	 * ���Ͷ�ý��
	 * 
	 * @param media
	 * @return boolean
	 */
	public boolean push(MediaInfo media) {
		return ActionManager.getInstance().push(this, media,
				ActionHandler.getHandler());
	}

	/**
	 * ���Ͷ�ý��ͬʱ���ý���
	 * 
	 * @param media
	 * @param position
	 * @return
	 */
	public boolean push(MediaInfo media, long position) {
		return ActionManager.getInstance().push(this, media, position,
				ActionHandler.getHandler());
	}

	/**
	 * ��ͣ����
	 * 
	 * @return boolean
	 */
	public boolean pause() {
		return ActionManager.getInstance().pause(this,
				ActionHandler.getHandler());
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	public boolean resume() {
		return ActionManager.getInstance().resume(this,
				ActionHandler.getHandler());
	}

	/**
	 * ֹͣ����
	 * 
	 * @return boolean
	 */
	public boolean stop() {
		return ActionManager.getInstance().stop(this,
				ActionHandler.getHandler());
	}

	/**
	 * ���ý���
	 * 
	 * @return boolean
	 */
	public boolean seek(long millisecond) {
		return ActionManager.getInstance().seek(this, millisecond,
				ActionHandler.getHandler());
	}

	/**
	 * ��ȡ���Ž���
	 * 
	 * @return String
	 */
	public long getPositionInfo() {
		return ActionManager.getInstance().getPositionInfo(this,
				ActionHandler.getHandler());
	}

	/**
	 * ��ȡ�豸��ǰ�Ĳ�������
	 * 
	 * @return {@link PlayState}
	 */
	public PlayState getCurrentAction() {
		return ActionManager.getInstance().getCurrentAction(this,
				ActionHandler.getHandler());
	}

	/**
	 * ���þ���/ȡ������
	 * 
	 * @param desiredMute
	 * @return boolean
	 */
	public boolean setMute(boolean desiredMute) {
		return ActionManager.getInstance().setMute(this, desiredMute,
				ActionHandler.getHandler());
	}

	/**
	 * ��ȡ�豸��ǰ�Ƿ���
	 * 
	 * @return boolean
	 */
	public boolean getMute() {
		return ActionManager.getInstance().getMute(this,
				ActionHandler.getHandler());
	}

	/**
	 * ��������
	 * 
	 * @param volume
	 * @return boolean
	 */
	public boolean setVolume(long volume) {
		return ActionManager.getInstance().setVolume(this, volume,
				ActionHandler.getHandler());
	}

	/**
	 * ��������
	 * 
	 * @param volume
	 * @return boolean
	 */
	public int getVolume() {
		return ActionManager.getInstance().getVolume(this,
				ActionHandler.getHandler());
	}

	/**
	 * �����豸��״̬�仯
	 * 
	 * @param listener
	 */
	public void startSync(OnDeviceStateChangeListener listener) {
		ActionManager.getInstance().startSync(this, listener);
	}

	/**
	 * ֹͣ����
	 */
	public void stopSync() {
		ActionManager.getInstance().stopSync(this);
	}

	/**
	 * @return �Ƿ�����ͬ��
	 */
	public boolean isSyncing() {
		return ActionManager.getInstance().isSyncing(this);
	}

	public interface OnDeviceStateChangeListener {

		public void onPlayStateChanged(PlayState state);

		public void onPositionChanged(long progress, long duration);

		public void onVolumeChanged(int volume);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(DmrDevice another) {
		if (another == null) {
			return 1;
		}
		if (TextUtils.isEmpty(this.getUdn())) {
			if (TextUtils.isEmpty(another.getUdn())) {
				return 0;
			} else {
				return -1;
			}
		}
		if (TextUtils.isEmpty(another.getUdn())) {
			return 1;
		}
		return this.getUdn().compareTo(another.getUdn());
	}

}

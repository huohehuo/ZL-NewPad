package com.fangzuo.assist.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.posapi.PosApi;
import android.util.Log;

import java.util.LinkedList;

public class PrintQueue {

	private static  final String TAG = PrintQueue.class.getSimpleName();
	private static  final int  PRINT_TYPE_TEXT = 1;
	private static  final int  PRINT_TYPE_BMP  = 2;

	private PosApi mApi = null;
	private Context mContext = null;


	private ControlThread mControlThread = null;
	private Looper mSendLooper = null;
	private Handler mSendHandler = null;
	private LinkedList<PrintData> mSendList = null;
	private static final int MSG_PRINT_NEXT = 11;
	private OnPrintListener mListener = null;


	public interface  OnPrintListener{
		void onFailed(int state);
		void onFinish();

		/**
		 * Get paper state
		 * @param state  0：Has paper   1：No paper
		 */
		void onGetState(int state);

		/**
		 * Set paper state
		 * @param state 0：Has paper   1：No paper
		 */
		void onPrinterSetting(int state);
	}


	public PrintQueue(Context context, PosApi api){
		this.mApi = api;
		this.mContext = context;
		mSendList = new LinkedList<PrintData>();
	}


	public void setOnPrintListener(OnPrintListener listener){
		this.mListener = listener;
	}

	private class ControlThread extends Thread{

		@Override
		public void run() {
			log("Print Control thread["+getId()+"] run...");
			Looper.prepare();
			mSendLooper = Looper.myLooper();
			mSendHandler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					switch(msg.what){

					case MSG_PRINT_NEXT:
						log("PRINT_NEXT_DATA");
						print();
						break;

					}
				}
			};
			//mSendHandler.sendMessage(mSendHandler.obtainMessage(MSG_CONNECT_SERVER));
			Looper.loop();
			log("Print Control thread exit!!!!!!!!!");
		}
	}


	public void addText(int concentration,  byte[] textData){
		log("Print Queue addText");
		PrintData mData = new PrintData();
		mData.setConcentration(concentration);
		mData.setPrintType(PRINT_TYPE_TEXT);
		mData.setData(textData);
		mSendList.add(mData);
	}

	public void addBmp(int concentration, int left ,int width,int height , byte[] bmpData){
		log("Print Queue addBmp");
		PrintData mData = new PrintData();
		mData.setConcentration(concentration);
		mData.setPrintType(PRINT_TYPE_BMP);
		mData.setLeft(left);
		mData.setWidth(width);
		mData.setHeight(height);
		mData.setData(bmpData);
		mSendList.add(mData);
	}



	public void init(){
		log("Print Queue start");
		mControlThread = new ControlThread();
		mControlThread.start();

		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(PosApi.ACTION_POS_COMM_STATUS);
		mContext.registerReceiver(mReceiver, mFilter);
	}
	public void  printStart(){
		mSendHandler.sendEmptyMessage(MSG_PRINT_NEXT);
	}

	public void  printStop(int state){
		log("Print Queue stop");
		if(mSendList==null) return ;
		mSendHandler.removeMessages(MSG_PRINT_NEXT);
		mSendList.clear();
		if(mListener!=null){
			mListener.onFailed(state);
		}
	}

	public void  printNext(){
		log("Print Queue printNext");
		if(mSendList==null) return ;
		if(!mSendList.isEmpty()){
			mSendList.removeFirst();
		}
		mSendHandler.sendEmptyMessage(MSG_PRINT_NEXT);
	}

	public void close(){
		log("Print Queue close");
		if(mContext!=null){
			mContext.unregisterReceiver(mReceiver);
		}
		if(mSendList==null||mSendList.size() == 0) return ;
		mSendHandler.removeMessages(MSG_PRINT_NEXT);
		mSendList.clear();
		if (mControlThread != null){
			mSendLooper.quit();
			mSendHandler = null;
			mControlThread = null;
		}

	}

	private void print() {
		log("Print Queue do print...");
		if(mSendList==null) return ;
		if(mSendList.size() == 0){
			if(mListener!=null){
				mListener.onFinish();
			}
			return;
		}

		PrintData mData = mSendList.getFirst();
		int type = mData.getPrintType();
		switch (type) {
		case PRINT_TYPE_TEXT:

			mApi.printText(mData.getConcentration(), mData.getData(), mData.getData().length);

			break;
		case PRINT_TYPE_BMP:

			mApi.printImage(mData.getConcentration(), mData.getLeft(), mData.getWidth(), mData.getHeight(), mData.getData());

			break;

		default:
			break;
		}
	}


	public class PrintData{
		//1 text   2 bmp
		int mPrintType = 1;
		int mConcentration  = 25;
		int mLeft = 0 ;
		byte[] mData = null;
		int mHeight = 0;
		int mWidth  = 0;
		public int getPrintType() {
			return mPrintType;
		}
		public void setPrintType(int mPrintType) {
			this.mPrintType = mPrintType;
		}
		public int getConcentration() {
			return mConcentration;
		}
		public void setConcentration(int mConcentration) {
			this.mConcentration = mConcentration;
		}
		public int getLeft() {
			return mLeft;
		}
		public void setLeft(int mLeft) {
			this.mLeft = mLeft;
		}
		public byte[] getData() {
			return mData;
		}
		public void setData(byte[] mData) {
			this.mData = mData;
		}
		public int getHeight() {
			return mHeight;
		}
		public void setHeight(int mHeight) {
			this.mHeight = mHeight;
		}
		public int getWidth() {
			return mWidth;
		}
		public void setWidth(int mWidth) {
			this.mWidth = mWidth;
		}


	}


	private BroadcastReceiver  mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action =  intent.getAction();
			if(action.equalsIgnoreCase(PosApi.ACTION_POS_COMM_STATUS)){
				int cmdFlag =intent.getIntExtra(PosApi.KEY_CMD_FLAG, -1);
				int status	=intent.getIntExtra(PosApi.KEY_CMD_STATUS , -1);

				if(cmdFlag== PosApi.POS_PRINT_PICTURE || cmdFlag == PosApi.POS_PRINT_TEXT){

					switch(status){
					case PosApi.ERR_POS_PRINT_SUCC:
						//Print Success
						printNext();
						break;
					case PosApi.ERR_POS_PRINT_NO_PAPER:
						//No paper
						printStop(status);
						break;
					case PosApi.ERR_POS_PRINT_FAILED:
						//Print Failed
						printStop(status);
						break;
					case PosApi.ERR_POS_PRINT_VOLTAGE_LOW:
						//Low Power
						printStop(status);
						break;
					case PosApi.ERR_POS_PRINT_VOLTAGE_HIGH:
						//Hight Power
						printStop(status);
						break;

					}

				}
				if(cmdFlag == PosApi.POS_PRINT_GET_STATE){
					if(mListener==null){
						return;
					}
					mListener.onGetState(status);
				}

				if(cmdFlag == PosApi.POS_PRINT_SETTING){
					if(mListener==null){
						return;
					}
					mListener.onPrinterSetting(status);
				}

			}
		}

	};




	private void log(String msg){
		Log.d(TAG, msg);
	}



}

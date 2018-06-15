package com.fangzuo.assist.Beans;

import com.fangzuo.assist.Dao.InStorageNum;

public class InStoreNumBean {
	public String FItemID;
	public String FStockID;
	public String FStockPlaceID;
	public String FBatchNo;

	public String FKFDate;
	public String FKFPeriod;

	public InStoreNumBean(){}

	public InStoreNumBean(String FItemID, String FStockID, String FStockPlaceID) {
		this.FItemID = FItemID;
		this.FStockID = FStockID;
		this.FStockPlaceID = FStockPlaceID;
	}
}

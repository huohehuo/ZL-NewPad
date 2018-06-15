package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.Dao.T_Detail;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "T__DETAIL".
*/
public class T_DetailDao extends AbstractDao<T_Detail, String> {

    public static final String TABLENAME = "T__DETAIL";

    /**
     * Properties of entity T_Detail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FOrderId = new Property(0, long.class, "FOrderId", false, "FORDER_ID");
        public final static Property FIndex = new Property(1, String.class, "FIndex", true, "FINDEX");
        public final static Property Diameter = new Property(2, int.class, "diameter", false, "DIAMETER");
        public final static Property Length = new Property(3, String.class, "length", false, "LENGTH");
        public final static Property FBarcode = new Property(4, String.class, "FBarcode", false, "FBARCODE");
        public final static Property FStorageId = new Property(5, String.class, "FStorageId", false, "FSTORAGE_ID");
        public final static Property FStorage = new Property(6, String.class, "FStorage", false, "FSTORAGE");
        public final static Property FProductCode = new Property(7, String.class, "FProductCode", false, "FPRODUCT_CODE");
        public final static Property Radical = new Property(8, String.class, "radical", false, "RADICAL");
        public final static Property FRedBlue = new Property(9, String.class, "FRedBlue", false, "FRED_BLUE");
        public final static Property FProductId = new Property(10, String.class, "FProductId", false, "FPRODUCT_ID");
        public final static Property FProductName = new Property(11, String.class, "FProductName", false, "FPRODUCT_NAME");
        public final static Property FStandard = new Property(12, String.class, "FStandard", false, "FSTANDARD");
        public final static Property FUnitId = new Property(13, String.class, "FUnitId", false, "FUNIT_ID");
        public final static Property FUnit = new Property(14, String.class, "FUnit", false, "FUNIT");
        public final static Property FQuantity = new Property(15, String.class, "FQuantity", false, "FQUANTITY");
        public final static Property FTaxUnitPrice = new Property(16, String.class, "FTaxUnitPrice", false, "FTAX_UNIT_PRICE");
        public final static Property FDiscount = new Property(17, String.class, "FDiscount", false, "FDISCOUNT");
        public final static Property FIdentity = new Property(18, String.class, "FIdentity", false, "FIDENTITY");
        public final static Property FDateDelivery = new Property(19, String.class, "FDateDelivery", false, "FDATE_DELIVERY");
        public final static Property FBatch = new Property(20, String.class, "FBatch", false, "FBATCH");
        public final static Property FBillNo = new Property(21, String.class, "FBillNo", false, "FBILL_NO");
        public final static Property FRate = new Property(22, String.class, "FRate", false, "FRATE");
        public final static Property FoutStoragename = new Property(23, String.class, "FoutStoragename", false, "FOUT_STORAGENAME");
        public final static Property FoutStorageid = new Property(24, String.class, "FoutStorageid", false, "FOUT_STORAGEID");
        public final static Property Foutwavehouseid = new Property(25, String.class, "Foutwavehouseid", false, "FOUTWAVEHOUSEID");
        public final static Property Foutwavehousename = new Property(26, String.class, "Foutwavehousename", false, "FOUTWAVEHOUSENAME");
        public final static Property FEntryID = new Property(27, String.class, "FEntryID", false, "FENTRY_ID");
        public final static Property FPositionId = new Property(28, String.class, "FPositionId", false, "FPOSITION_ID");
        public final static Property FPosition = new Property(29, String.class, "FPosition", false, "FPOSITION");
        public final static Property FInterID = new Property(30, String.class, "FInterID", false, "FINTER_ID");
        public final static Property OutStorage = new Property(31, String.class, "outStorage", false, "OUT_STORAGE");
        public final static Property InStorage = new Property(32, String.class, "inStorage", false, "IN_STORAGE");
        public final static Property Unitrate = new Property(33, double.class, "unitrate", false, "UNITRATE");
        public final static Property Activity = new Property(34, int.class, "activity", false, "ACTIVITY");
        public final static Property Model = new Property(35, String.class, "model", false, "MODEL");
        public final static Property ClientName = new Property(36, String.class, "clientName", false, "CLIENT_NAME");
        public final static Property FRemark = new Property(37, String.class, "FRemark", false, "FREMARK");
    }


    public T_DetailDao(DaoConfig config) {
        super(config);
    }
    
    public T_DetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"T__DETAIL\" (" + //
                "\"FORDER_ID\" INTEGER NOT NULL ," + // 0: FOrderId
                "\"FINDEX\" TEXT PRIMARY KEY NOT NULL ," + // 1: FIndex
                "\"DIAMETER\" INTEGER NOT NULL ," + // 2: diameter
                "\"LENGTH\" TEXT," + // 3: length
                "\"FBARCODE\" TEXT," + // 4: FBarcode
                "\"FSTORAGE_ID\" TEXT," + // 5: FStorageId
                "\"FSTORAGE\" TEXT," + // 6: FStorage
                "\"FPRODUCT_CODE\" TEXT," + // 7: FProductCode
                "\"RADICAL\" TEXT," + // 8: radical
                "\"FRED_BLUE\" TEXT," + // 9: FRedBlue
                "\"FPRODUCT_ID\" TEXT," + // 10: FProductId
                "\"FPRODUCT_NAME\" TEXT," + // 11: FProductName
                "\"FSTANDARD\" TEXT," + // 12: FStandard
                "\"FUNIT_ID\" TEXT," + // 13: FUnitId
                "\"FUNIT\" TEXT," + // 14: FUnit
                "\"FQUANTITY\" TEXT," + // 15: FQuantity
                "\"FTAX_UNIT_PRICE\" TEXT," + // 16: FTaxUnitPrice
                "\"FDISCOUNT\" TEXT," + // 17: FDiscount
                "\"FIDENTITY\" TEXT," + // 18: FIdentity
                "\"FDATE_DELIVERY\" TEXT," + // 19: FDateDelivery
                "\"FBATCH\" TEXT," + // 20: FBatch
                "\"FBILL_NO\" TEXT," + // 21: FBillNo
                "\"FRATE\" TEXT," + // 22: FRate
                "\"FOUT_STORAGENAME\" TEXT," + // 23: FoutStoragename
                "\"FOUT_STORAGEID\" TEXT," + // 24: FoutStorageid
                "\"FOUTWAVEHOUSEID\" TEXT," + // 25: Foutwavehouseid
                "\"FOUTWAVEHOUSENAME\" TEXT," + // 26: Foutwavehousename
                "\"FENTRY_ID\" TEXT," + // 27: FEntryID
                "\"FPOSITION_ID\" TEXT," + // 28: FPositionId
                "\"FPOSITION\" TEXT," + // 29: FPosition
                "\"FINTER_ID\" TEXT," + // 30: FInterID
                "\"OUT_STORAGE\" TEXT," + // 31: outStorage
                "\"IN_STORAGE\" TEXT," + // 32: inStorage
                "\"UNITRATE\" REAL NOT NULL ," + // 33: unitrate
                "\"ACTIVITY\" INTEGER NOT NULL ," + // 34: activity
                "\"MODEL\" TEXT," + // 35: model
                "\"CLIENT_NAME\" TEXT," + // 36: clientName
                "\"FREMARK\" TEXT);"); // 37: FRemark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"T__DETAIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, T_Detail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getFOrderId());
 
        String FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindString(2, FIndex);
        }
        stmt.bindLong(3, entity.getDiameter());
 
        String length = entity.getLength();
        if (length != null) {
            stmt.bindString(4, length);
        }
 
        String FBarcode = entity.getFBarcode();
        if (FBarcode != null) {
            stmt.bindString(5, FBarcode);
        }
 
        String FStorageId = entity.getFStorageId();
        if (FStorageId != null) {
            stmt.bindString(6, FStorageId);
        }
 
        String FStorage = entity.getFStorage();
        if (FStorage != null) {
            stmt.bindString(7, FStorage);
        }
 
        String FProductCode = entity.getFProductCode();
        if (FProductCode != null) {
            stmt.bindString(8, FProductCode);
        }
 
        String radical = entity.getRadical();
        if (radical != null) {
            stmt.bindString(9, radical);
        }
 
        String FRedBlue = entity.getFRedBlue();
        if (FRedBlue != null) {
            stmt.bindString(10, FRedBlue);
        }
 
        String FProductId = entity.getFProductId();
        if (FProductId != null) {
            stmt.bindString(11, FProductId);
        }
 
        String FProductName = entity.getFProductName();
        if (FProductName != null) {
            stmt.bindString(12, FProductName);
        }
 
        String FStandard = entity.getFStandard();
        if (FStandard != null) {
            stmt.bindString(13, FStandard);
        }
 
        String FUnitId = entity.getFUnitId();
        if (FUnitId != null) {
            stmt.bindString(14, FUnitId);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FQuantity = entity.getFQuantity();
        if (FQuantity != null) {
            stmt.bindString(16, FQuantity);
        }
 
        String FTaxUnitPrice = entity.getFTaxUnitPrice();
        if (FTaxUnitPrice != null) {
            stmt.bindString(17, FTaxUnitPrice);
        }
 
        String FDiscount = entity.getFDiscount();
        if (FDiscount != null) {
            stmt.bindString(18, FDiscount);
        }
 
        String FIdentity = entity.getFIdentity();
        if (FIdentity != null) {
            stmt.bindString(19, FIdentity);
        }
 
        String FDateDelivery = entity.getFDateDelivery();
        if (FDateDelivery != null) {
            stmt.bindString(20, FDateDelivery);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(21, FBatch);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(22, FBillNo);
        }
 
        String FRate = entity.getFRate();
        if (FRate != null) {
            stmt.bindString(23, FRate);
        }
 
        String FoutStoragename = entity.getFoutStoragename();
        if (FoutStoragename != null) {
            stmt.bindString(24, FoutStoragename);
        }
 
        String FoutStorageid = entity.getFoutStorageid();
        if (FoutStorageid != null) {
            stmt.bindString(25, FoutStorageid);
        }
 
        String Foutwavehouseid = entity.getFoutwavehouseid();
        if (Foutwavehouseid != null) {
            stmt.bindString(26, Foutwavehouseid);
        }
 
        String Foutwavehousename = entity.getFoutwavehousename();
        if (Foutwavehousename != null) {
            stmt.bindString(27, Foutwavehousename);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(28, FEntryID);
        }
 
        String FPositionId = entity.getFPositionId();
        if (FPositionId != null) {
            stmt.bindString(29, FPositionId);
        }
 
        String FPosition = entity.getFPosition();
        if (FPosition != null) {
            stmt.bindString(30, FPosition);
        }
 
        String FInterID = entity.getFInterID();
        if (FInterID != null) {
            stmt.bindString(31, FInterID);
        }
 
        String outStorage = entity.getOutStorage();
        if (outStorage != null) {
            stmt.bindString(32, outStorage);
        }
 
        String inStorage = entity.getInStorage();
        if (inStorage != null) {
            stmt.bindString(33, inStorage);
        }
        stmt.bindDouble(34, entity.getUnitrate());
        stmt.bindLong(35, entity.getActivity());
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(36, model);
        }
 
        String clientName = entity.getClientName();
        if (clientName != null) {
            stmt.bindString(37, clientName);
        }
 
        String FRemark = entity.getFRemark();
        if (FRemark != null) {
            stmt.bindString(38, FRemark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, T_Detail entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getFOrderId());
 
        String FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindString(2, FIndex);
        }
        stmt.bindLong(3, entity.getDiameter());
 
        String length = entity.getLength();
        if (length != null) {
            stmt.bindString(4, length);
        }
 
        String FBarcode = entity.getFBarcode();
        if (FBarcode != null) {
            stmt.bindString(5, FBarcode);
        }
 
        String FStorageId = entity.getFStorageId();
        if (FStorageId != null) {
            stmt.bindString(6, FStorageId);
        }
 
        String FStorage = entity.getFStorage();
        if (FStorage != null) {
            stmt.bindString(7, FStorage);
        }
 
        String FProductCode = entity.getFProductCode();
        if (FProductCode != null) {
            stmt.bindString(8, FProductCode);
        }
 
        String radical = entity.getRadical();
        if (radical != null) {
            stmt.bindString(9, radical);
        }
 
        String FRedBlue = entity.getFRedBlue();
        if (FRedBlue != null) {
            stmt.bindString(10, FRedBlue);
        }
 
        String FProductId = entity.getFProductId();
        if (FProductId != null) {
            stmt.bindString(11, FProductId);
        }
 
        String FProductName = entity.getFProductName();
        if (FProductName != null) {
            stmt.bindString(12, FProductName);
        }
 
        String FStandard = entity.getFStandard();
        if (FStandard != null) {
            stmt.bindString(13, FStandard);
        }
 
        String FUnitId = entity.getFUnitId();
        if (FUnitId != null) {
            stmt.bindString(14, FUnitId);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FQuantity = entity.getFQuantity();
        if (FQuantity != null) {
            stmt.bindString(16, FQuantity);
        }
 
        String FTaxUnitPrice = entity.getFTaxUnitPrice();
        if (FTaxUnitPrice != null) {
            stmt.bindString(17, FTaxUnitPrice);
        }
 
        String FDiscount = entity.getFDiscount();
        if (FDiscount != null) {
            stmt.bindString(18, FDiscount);
        }
 
        String FIdentity = entity.getFIdentity();
        if (FIdentity != null) {
            stmt.bindString(19, FIdentity);
        }
 
        String FDateDelivery = entity.getFDateDelivery();
        if (FDateDelivery != null) {
            stmt.bindString(20, FDateDelivery);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(21, FBatch);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(22, FBillNo);
        }
 
        String FRate = entity.getFRate();
        if (FRate != null) {
            stmt.bindString(23, FRate);
        }
 
        String FoutStoragename = entity.getFoutStoragename();
        if (FoutStoragename != null) {
            stmt.bindString(24, FoutStoragename);
        }
 
        String FoutStorageid = entity.getFoutStorageid();
        if (FoutStorageid != null) {
            stmt.bindString(25, FoutStorageid);
        }
 
        String Foutwavehouseid = entity.getFoutwavehouseid();
        if (Foutwavehouseid != null) {
            stmt.bindString(26, Foutwavehouseid);
        }
 
        String Foutwavehousename = entity.getFoutwavehousename();
        if (Foutwavehousename != null) {
            stmt.bindString(27, Foutwavehousename);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(28, FEntryID);
        }
 
        String FPositionId = entity.getFPositionId();
        if (FPositionId != null) {
            stmt.bindString(29, FPositionId);
        }
 
        String FPosition = entity.getFPosition();
        if (FPosition != null) {
            stmt.bindString(30, FPosition);
        }
 
        String FInterID = entity.getFInterID();
        if (FInterID != null) {
            stmt.bindString(31, FInterID);
        }
 
        String outStorage = entity.getOutStorage();
        if (outStorage != null) {
            stmt.bindString(32, outStorage);
        }
 
        String inStorage = entity.getInStorage();
        if (inStorage != null) {
            stmt.bindString(33, inStorage);
        }
        stmt.bindDouble(34, entity.getUnitrate());
        stmt.bindLong(35, entity.getActivity());
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(36, model);
        }
 
        String clientName = entity.getClientName();
        if (clientName != null) {
            stmt.bindString(37, clientName);
        }
 
        String FRemark = entity.getFRemark();
        if (FRemark != null) {
            stmt.bindString(38, FRemark);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    @Override
    public T_Detail readEntity(Cursor cursor, int offset) {
        T_Detail entity = new T_Detail( //
            cursor.getLong(offset + 0), // FOrderId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FIndex
            cursor.getInt(offset + 2), // diameter
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // length
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FBarcode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FStorageId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // FStorage
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // FProductCode
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // radical
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // FRedBlue
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // FProductId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // FProductName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // FStandard
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // FUnitId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // FUnit
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // FQuantity
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // FTaxUnitPrice
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // FDiscount
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // FIdentity
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // FDateDelivery
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // FBatch
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // FBillNo
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // FRate
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // FoutStoragename
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // FoutStorageid
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // Foutwavehouseid
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // Foutwavehousename
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // FEntryID
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // FPositionId
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // FPosition
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // FInterID
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // outStorage
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // inStorage
            cursor.getDouble(offset + 33), // unitrate
            cursor.getInt(offset + 34), // activity
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // model
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // clientName
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37) // FRemark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, T_Detail entity, int offset) {
        entity.setFOrderId(cursor.getLong(offset + 0));
        entity.setFIndex(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDiameter(cursor.getInt(offset + 2));
        entity.setLength(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFBarcode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFStorageId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFStorage(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFProductCode(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRadical(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFRedBlue(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFProductId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFProductName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFStandard(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFUnitId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFUnit(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFQuantity(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFTaxUnitPrice(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFDiscount(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setFIdentity(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFDateDelivery(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setFBatch(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setFBillNo(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFRate(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setFoutStoragename(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setFoutStorageid(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setFoutwavehouseid(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setFoutwavehousename(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setFEntryID(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setFPositionId(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setFPosition(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setFInterID(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setOutStorage(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setInStorage(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setUnitrate(cursor.getDouble(offset + 33));
        entity.setActivity(cursor.getInt(offset + 34));
        entity.setModel(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setClientName(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setFRemark(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
     }
    
    @Override
    protected final String updateKeyAfterInsert(T_Detail entity, long rowId) {
        return entity.getFIndex();
    }
    
    @Override
    public String getKey(T_Detail entity) {
        if(entity != null) {
            return entity.getFIndex();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(T_Detail entity) {
        return entity.getFIndex() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
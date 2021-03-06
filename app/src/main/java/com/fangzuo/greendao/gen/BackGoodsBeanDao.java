package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.Dao.BackGoodsBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BACK_GOODS_BEAN".
*/
public class BackGoodsBeanDao extends AbstractDao<BackGoodsBean, String> {

    public static final String TABLENAME = "BACK_GOODS_BEAN";

    /**
     * Properties of entity BackGoodsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FIndex = new Property(0, String.class, "FIndex", true, "FINDEX");
        public final static Property FOrderId = new Property(1, long.class, "FOrderId", false, "FORDER_ID");
        public final static Property Diameter = new Property(2, int.class, "diameter", false, "DIAMETER");
        public final static Property FQuantity = new Property(3, String.class, "FQuantity", false, "FQUANTITY");
        public final static Property Radical = new Property(4, String.class, "radical", false, "RADICAL");
        public final static Property FIdentity = new Property(5, String.class, "FIdentity", false, "FIDENTITY");
        public final static Property Length = new Property(6, String.class, "length", false, "LENGTH");
        public final static Property FStorageId = new Property(7, String.class, "FStorageId", false, "FSTORAGE_ID");
        public final static Property FStorage = new Property(8, String.class, "FStorage", false, "FSTORAGE");
        public final static Property FProductCode = new Property(9, String.class, "FProductCode", false, "FPRODUCT_CODE");
        public final static Property FRedBlue = new Property(10, String.class, "FRedBlue", false, "FRED_BLUE");
        public final static Property FProductId = new Property(11, String.class, "FProductId", false, "FPRODUCT_ID");
        public final static Property FProductName = new Property(12, String.class, "FProductName", false, "FPRODUCT_NAME");
        public final static Property FUnitId = new Property(13, String.class, "FUnitId", false, "FUNIT_ID");
        public final static Property FUnit = new Property(14, String.class, "FUnit", false, "FUNIT");
        public final static Property FTaxUnitPrice = new Property(15, String.class, "FTaxUnitPrice", false, "FTAX_UNIT_PRICE");
        public final static Property FDiscount = new Property(16, String.class, "FDiscount", false, "FDISCOUNT");
        public final static Property FBatch = new Property(17, String.class, "FBatch", false, "FBATCH");
        public final static Property FBillNo = new Property(18, String.class, "FBillNo", false, "FBILL_NO");
        public final static Property FEntryID = new Property(19, String.class, "FEntryID", false, "FENTRY_ID");
        public final static Property FPositionId = new Property(20, String.class, "FPositionId", false, "FPOSITION_ID");
        public final static Property FPosition = new Property(21, String.class, "FPosition", false, "FPOSITION");
        public final static Property FInterID = new Property(22, String.class, "FInterID", false, "FINTER_ID");
        public final static Property Unitrate = new Property(23, double.class, "unitrate", false, "UNITRATE");
        public final static Property Activity = new Property(24, int.class, "activity", false, "ACTIVITY");
        public final static Property Model = new Property(25, String.class, "model", false, "MODEL");
        public final static Property ClientName = new Property(26, String.class, "clientName", false, "CLIENT_NAME");
        public final static Property FRemark = new Property(27, String.class, "FRemark", false, "FREMARK");
    }


    public BackGoodsBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BackGoodsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BACK_GOODS_BEAN\" (" + //
                "\"FINDEX\" TEXT PRIMARY KEY NOT NULL ," + // 0: FIndex
                "\"FORDER_ID\" INTEGER NOT NULL ," + // 1: FOrderId
                "\"DIAMETER\" INTEGER NOT NULL ," + // 2: diameter
                "\"FQUANTITY\" TEXT," + // 3: FQuantity
                "\"RADICAL\" TEXT," + // 4: radical
                "\"FIDENTITY\" TEXT," + // 5: FIdentity
                "\"LENGTH\" TEXT," + // 6: length
                "\"FSTORAGE_ID\" TEXT," + // 7: FStorageId
                "\"FSTORAGE\" TEXT," + // 8: FStorage
                "\"FPRODUCT_CODE\" TEXT," + // 9: FProductCode
                "\"FRED_BLUE\" TEXT," + // 10: FRedBlue
                "\"FPRODUCT_ID\" TEXT," + // 11: FProductId
                "\"FPRODUCT_NAME\" TEXT," + // 12: FProductName
                "\"FUNIT_ID\" TEXT," + // 13: FUnitId
                "\"FUNIT\" TEXT," + // 14: FUnit
                "\"FTAX_UNIT_PRICE\" TEXT," + // 15: FTaxUnitPrice
                "\"FDISCOUNT\" TEXT," + // 16: FDiscount
                "\"FBATCH\" TEXT," + // 17: FBatch
                "\"FBILL_NO\" TEXT," + // 18: FBillNo
                "\"FENTRY_ID\" TEXT," + // 19: FEntryID
                "\"FPOSITION_ID\" TEXT," + // 20: FPositionId
                "\"FPOSITION\" TEXT," + // 21: FPosition
                "\"FINTER_ID\" TEXT," + // 22: FInterID
                "\"UNITRATE\" REAL NOT NULL ," + // 23: unitrate
                "\"ACTIVITY\" INTEGER NOT NULL ," + // 24: activity
                "\"MODEL\" TEXT," + // 25: model
                "\"CLIENT_NAME\" TEXT," + // 26: clientName
                "\"FREMARK\" TEXT);"); // 27: FRemark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BACK_GOODS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BackGoodsBean entity) {
        stmt.clearBindings();
 
        String FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindString(1, FIndex);
        }
        stmt.bindLong(2, entity.getFOrderId());
        stmt.bindLong(3, entity.getDiameter());
 
        String FQuantity = entity.getFQuantity();
        if (FQuantity != null) {
            stmt.bindString(4, FQuantity);
        }
 
        String radical = entity.getRadical();
        if (radical != null) {
            stmt.bindString(5, radical);
        }
 
        String FIdentity = entity.getFIdentity();
        if (FIdentity != null) {
            stmt.bindString(6, FIdentity);
        }
 
        String length = entity.getLength();
        if (length != null) {
            stmt.bindString(7, length);
        }
 
        String FStorageId = entity.getFStorageId();
        if (FStorageId != null) {
            stmt.bindString(8, FStorageId);
        }
 
        String FStorage = entity.getFStorage();
        if (FStorage != null) {
            stmt.bindString(9, FStorage);
        }
 
        String FProductCode = entity.getFProductCode();
        if (FProductCode != null) {
            stmt.bindString(10, FProductCode);
        }
 
        String FRedBlue = entity.getFRedBlue();
        if (FRedBlue != null) {
            stmt.bindString(11, FRedBlue);
        }
 
        String FProductId = entity.getFProductId();
        if (FProductId != null) {
            stmt.bindString(12, FProductId);
        }
 
        String FProductName = entity.getFProductName();
        if (FProductName != null) {
            stmt.bindString(13, FProductName);
        }
 
        String FUnitId = entity.getFUnitId();
        if (FUnitId != null) {
            stmt.bindString(14, FUnitId);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FTaxUnitPrice = entity.getFTaxUnitPrice();
        if (FTaxUnitPrice != null) {
            stmt.bindString(16, FTaxUnitPrice);
        }
 
        String FDiscount = entity.getFDiscount();
        if (FDiscount != null) {
            stmt.bindString(17, FDiscount);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(18, FBatch);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(19, FBillNo);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(20, FEntryID);
        }
 
        String FPositionId = entity.getFPositionId();
        if (FPositionId != null) {
            stmt.bindString(21, FPositionId);
        }
 
        String FPosition = entity.getFPosition();
        if (FPosition != null) {
            stmt.bindString(22, FPosition);
        }
 
        String FInterID = entity.getFInterID();
        if (FInterID != null) {
            stmt.bindString(23, FInterID);
        }
        stmt.bindDouble(24, entity.getUnitrate());
        stmt.bindLong(25, entity.getActivity());
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(26, model);
        }
 
        String clientName = entity.getClientName();
        if (clientName != null) {
            stmt.bindString(27, clientName);
        }
 
        String FRemark = entity.getFRemark();
        if (FRemark != null) {
            stmt.bindString(28, FRemark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BackGoodsBean entity) {
        stmt.clearBindings();
 
        String FIndex = entity.getFIndex();
        if (FIndex != null) {
            stmt.bindString(1, FIndex);
        }
        stmt.bindLong(2, entity.getFOrderId());
        stmt.bindLong(3, entity.getDiameter());
 
        String FQuantity = entity.getFQuantity();
        if (FQuantity != null) {
            stmt.bindString(4, FQuantity);
        }
 
        String radical = entity.getRadical();
        if (radical != null) {
            stmt.bindString(5, radical);
        }
 
        String FIdentity = entity.getFIdentity();
        if (FIdentity != null) {
            stmt.bindString(6, FIdentity);
        }
 
        String length = entity.getLength();
        if (length != null) {
            stmt.bindString(7, length);
        }
 
        String FStorageId = entity.getFStorageId();
        if (FStorageId != null) {
            stmt.bindString(8, FStorageId);
        }
 
        String FStorage = entity.getFStorage();
        if (FStorage != null) {
            stmt.bindString(9, FStorage);
        }
 
        String FProductCode = entity.getFProductCode();
        if (FProductCode != null) {
            stmt.bindString(10, FProductCode);
        }
 
        String FRedBlue = entity.getFRedBlue();
        if (FRedBlue != null) {
            stmt.bindString(11, FRedBlue);
        }
 
        String FProductId = entity.getFProductId();
        if (FProductId != null) {
            stmt.bindString(12, FProductId);
        }
 
        String FProductName = entity.getFProductName();
        if (FProductName != null) {
            stmt.bindString(13, FProductName);
        }
 
        String FUnitId = entity.getFUnitId();
        if (FUnitId != null) {
            stmt.bindString(14, FUnitId);
        }
 
        String FUnit = entity.getFUnit();
        if (FUnit != null) {
            stmt.bindString(15, FUnit);
        }
 
        String FTaxUnitPrice = entity.getFTaxUnitPrice();
        if (FTaxUnitPrice != null) {
            stmt.bindString(16, FTaxUnitPrice);
        }
 
        String FDiscount = entity.getFDiscount();
        if (FDiscount != null) {
            stmt.bindString(17, FDiscount);
        }
 
        String FBatch = entity.getFBatch();
        if (FBatch != null) {
            stmt.bindString(18, FBatch);
        }
 
        String FBillNo = entity.getFBillNo();
        if (FBillNo != null) {
            stmt.bindString(19, FBillNo);
        }
 
        String FEntryID = entity.getFEntryID();
        if (FEntryID != null) {
            stmt.bindString(20, FEntryID);
        }
 
        String FPositionId = entity.getFPositionId();
        if (FPositionId != null) {
            stmt.bindString(21, FPositionId);
        }
 
        String FPosition = entity.getFPosition();
        if (FPosition != null) {
            stmt.bindString(22, FPosition);
        }
 
        String FInterID = entity.getFInterID();
        if (FInterID != null) {
            stmt.bindString(23, FInterID);
        }
        stmt.bindDouble(24, entity.getUnitrate());
        stmt.bindLong(25, entity.getActivity());
 
        String model = entity.getModel();
        if (model != null) {
            stmt.bindString(26, model);
        }
 
        String clientName = entity.getClientName();
        if (clientName != null) {
            stmt.bindString(27, clientName);
        }
 
        String FRemark = entity.getFRemark();
        if (FRemark != null) {
            stmt.bindString(28, FRemark);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BackGoodsBean readEntity(Cursor cursor, int offset) {
        BackGoodsBean entity = new BackGoodsBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // FIndex
            cursor.getLong(offset + 1), // FOrderId
            cursor.getInt(offset + 2), // diameter
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FQuantity
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // radical
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FIdentity
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // length
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // FStorageId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // FStorage
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // FProductCode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // FRedBlue
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // FProductId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // FProductName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // FUnitId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // FUnit
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // FTaxUnitPrice
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // FDiscount
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // FBatch
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // FBillNo
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // FEntryID
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // FPositionId
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // FPosition
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // FInterID
            cursor.getDouble(offset + 23), // unitrate
            cursor.getInt(offset + 24), // activity
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // model
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // clientName
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27) // FRemark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BackGoodsBean entity, int offset) {
        entity.setFIndex(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFOrderId(cursor.getLong(offset + 1));
        entity.setDiameter(cursor.getInt(offset + 2));
        entity.setFQuantity(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRadical(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFIdentity(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLength(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFStorageId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFStorage(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFProductCode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFRedBlue(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFProductId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFProductName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFUnitId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFUnit(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFTaxUnitPrice(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFDiscount(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFBatch(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setFBillNo(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFEntryID(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setFPositionId(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setFPosition(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFInterID(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setUnitrate(cursor.getDouble(offset + 23));
        entity.setActivity(cursor.getInt(offset + 24));
        entity.setModel(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setClientName(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setFRemark(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BackGoodsBean entity, long rowId) {
        return entity.getFIndex();
    }
    
    @Override
    public String getKey(BackGoodsBean entity) {
        if(entity != null) {
            return entity.getFIndex();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BackGoodsBean entity) {
        return entity.getFIndex() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

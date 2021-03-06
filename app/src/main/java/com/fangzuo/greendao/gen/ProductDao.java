package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.Dao.Product;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PRODUCT".
*/
public class ProductDao extends AbstractDao<Product, Void> {

    public static final String TABLENAME = "PRODUCT";

    /**
     * Properties of entity Product.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FItemID = new Property(0, String.class, "FItemID", false, "FITEM_ID");
        public final static Property FNumber = new Property(1, String.class, "FNumber", false, "FNUMBER");
        public final static Property FModel = new Property(2, String.class, "FModel", false, "FMODEL");
        public final static Property FName = new Property(3, String.class, "FName", false, "FNAME");
        public final static Property FFullName = new Property(4, String.class, "FFullName", false, "FFULL_NAME");
        public final static Property FUnitID = new Property(5, String.class, "FUnitID", false, "FUNIT_ID");
        public final static Property FUnitGroupID = new Property(6, String.class, "FUnitGroupID", false, "FUNIT_GROUP_ID");
        public final static Property FDefaultLoc = new Property(7, String.class, "FDefaultLoc", false, "FDEFAULT_LOC");
        public final static Property FProfitRate = new Property(8, String.class, "FProfitRate", false, "FPROFIT_RATE");
        public final static Property FTaxRate = new Property(9, String.class, "FTaxRate", false, "FTAX_RATE");
        public final static Property FOrderPrice = new Property(10, String.class, "FOrderPrice", false, "FORDER_PRICE");
        public final static Property FSalePrice = new Property(11, String.class, "FSalePrice", false, "FSALE_PRICE");
        public final static Property FPlanPrice = new Property(12, String.class, "FPlanPrice", false, "FPLAN_PRICE");
        public final static Property FBarcode = new Property(13, String.class, "FBarcode", false, "FBARCODE");
        public final static Property FSPID = new Property(14, String.class, "FSPID", false, "FSPID");
        public final static Property FBatchManager = new Property(15, String.class, "FBatchManager", false, "FBATCH_MANAGER");
        public final static Property FM = new Property(16, String.class, "FM", false, "FM");
        public final static Property FMindiameter = new Property(17, String.class, "FMindiameter", false, "FMINDIAMETER");
        public final static Property FMaxdiameter = new Property(18, String.class, "FMaxdiameter", false, "FMAXDIAMETER");
        public final static Property FISKFPeriod = new Property(19, String.class, "FISKFPeriod", false, "FISKFPERIOD");
        public final static Property FKFPeriod = new Property(20, String.class, "FKFPeriod", false, "FKFPERIOD");
    }


    public ProductDao(DaoConfig config) {
        super(config);
    }
    
    public ProductDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRODUCT\" (" + //
                "\"FITEM_ID\" TEXT," + // 0: FItemID
                "\"FNUMBER\" TEXT," + // 1: FNumber
                "\"FMODEL\" TEXT," + // 2: FModel
                "\"FNAME\" TEXT," + // 3: FName
                "\"FFULL_NAME\" TEXT," + // 4: FFullName
                "\"FUNIT_ID\" TEXT," + // 5: FUnitID
                "\"FUNIT_GROUP_ID\" TEXT," + // 6: FUnitGroupID
                "\"FDEFAULT_LOC\" TEXT," + // 7: FDefaultLoc
                "\"FPROFIT_RATE\" TEXT," + // 8: FProfitRate
                "\"FTAX_RATE\" TEXT," + // 9: FTaxRate
                "\"FORDER_PRICE\" TEXT," + // 10: FOrderPrice
                "\"FSALE_PRICE\" TEXT," + // 11: FSalePrice
                "\"FPLAN_PRICE\" TEXT," + // 12: FPlanPrice
                "\"FBARCODE\" TEXT," + // 13: FBarcode
                "\"FSPID\" TEXT," + // 14: FSPID
                "\"FBATCH_MANAGER\" TEXT," + // 15: FBatchManager
                "\"FM\" TEXT," + // 16: FM
                "\"FMINDIAMETER\" TEXT," + // 17: FMindiameter
                "\"FMAXDIAMETER\" TEXT," + // 18: FMaxdiameter
                "\"FISKFPERIOD\" TEXT," + // 19: FISKFPeriod
                "\"FKFPERIOD\" TEXT);"); // 20: FKFPeriod
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRODUCT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Product entity) {
        stmt.clearBindings();
 
        String FItemID = entity.getFItemID();
        if (FItemID != null) {
            stmt.bindString(1, FItemID);
        }
 
        String FNumber = entity.getFNumber();
        if (FNumber != null) {
            stmt.bindString(2, FNumber);
        }
 
        String FModel = entity.getFModel();
        if (FModel != null) {
            stmt.bindString(3, FModel);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(4, FName);
        }
 
        String FFullName = entity.getFFullName();
        if (FFullName != null) {
            stmt.bindString(5, FFullName);
        }
 
        String FUnitID = entity.getFUnitID();
        if (FUnitID != null) {
            stmt.bindString(6, FUnitID);
        }
 
        String FUnitGroupID = entity.getFUnitGroupID();
        if (FUnitGroupID != null) {
            stmt.bindString(7, FUnitGroupID);
        }
 
        String FDefaultLoc = entity.getFDefaultLoc();
        if (FDefaultLoc != null) {
            stmt.bindString(8, FDefaultLoc);
        }
 
        String FProfitRate = entity.getFProfitRate();
        if (FProfitRate != null) {
            stmt.bindString(9, FProfitRate);
        }
 
        String FTaxRate = entity.getFTaxRate();
        if (FTaxRate != null) {
            stmt.bindString(10, FTaxRate);
        }
 
        String FOrderPrice = entity.getFOrderPrice();
        if (FOrderPrice != null) {
            stmt.bindString(11, FOrderPrice);
        }
 
        String FSalePrice = entity.getFSalePrice();
        if (FSalePrice != null) {
            stmt.bindString(12, FSalePrice);
        }
 
        String FPlanPrice = entity.getFPlanPrice();
        if (FPlanPrice != null) {
            stmt.bindString(13, FPlanPrice);
        }
 
        String FBarcode = entity.getFBarcode();
        if (FBarcode != null) {
            stmt.bindString(14, FBarcode);
        }
 
        String FSPID = entity.getFSPID();
        if (FSPID != null) {
            stmt.bindString(15, FSPID);
        }
 
        String FBatchManager = entity.getFBatchManager();
        if (FBatchManager != null) {
            stmt.bindString(16, FBatchManager);
        }
 
        String FM = entity.getFM();
        if (FM != null) {
            stmt.bindString(17, FM);
        }
 
        String FMindiameter = entity.getFMindiameter();
        if (FMindiameter != null) {
            stmt.bindString(18, FMindiameter);
        }
 
        String FMaxdiameter = entity.getFMaxdiameter();
        if (FMaxdiameter != null) {
            stmt.bindString(19, FMaxdiameter);
        }
 
        String FISKFPeriod = entity.getFISKFPeriod();
        if (FISKFPeriod != null) {
            stmt.bindString(20, FISKFPeriod);
        }
 
        String FKFPeriod = entity.getFKFPeriod();
        if (FKFPeriod != null) {
            stmt.bindString(21, FKFPeriod);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Product entity) {
        stmt.clearBindings();
 
        String FItemID = entity.getFItemID();
        if (FItemID != null) {
            stmt.bindString(1, FItemID);
        }
 
        String FNumber = entity.getFNumber();
        if (FNumber != null) {
            stmt.bindString(2, FNumber);
        }
 
        String FModel = entity.getFModel();
        if (FModel != null) {
            stmt.bindString(3, FModel);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(4, FName);
        }
 
        String FFullName = entity.getFFullName();
        if (FFullName != null) {
            stmt.bindString(5, FFullName);
        }
 
        String FUnitID = entity.getFUnitID();
        if (FUnitID != null) {
            stmt.bindString(6, FUnitID);
        }
 
        String FUnitGroupID = entity.getFUnitGroupID();
        if (FUnitGroupID != null) {
            stmt.bindString(7, FUnitGroupID);
        }
 
        String FDefaultLoc = entity.getFDefaultLoc();
        if (FDefaultLoc != null) {
            stmt.bindString(8, FDefaultLoc);
        }
 
        String FProfitRate = entity.getFProfitRate();
        if (FProfitRate != null) {
            stmt.bindString(9, FProfitRate);
        }
 
        String FTaxRate = entity.getFTaxRate();
        if (FTaxRate != null) {
            stmt.bindString(10, FTaxRate);
        }
 
        String FOrderPrice = entity.getFOrderPrice();
        if (FOrderPrice != null) {
            stmt.bindString(11, FOrderPrice);
        }
 
        String FSalePrice = entity.getFSalePrice();
        if (FSalePrice != null) {
            stmt.bindString(12, FSalePrice);
        }
 
        String FPlanPrice = entity.getFPlanPrice();
        if (FPlanPrice != null) {
            stmt.bindString(13, FPlanPrice);
        }
 
        String FBarcode = entity.getFBarcode();
        if (FBarcode != null) {
            stmt.bindString(14, FBarcode);
        }
 
        String FSPID = entity.getFSPID();
        if (FSPID != null) {
            stmt.bindString(15, FSPID);
        }
 
        String FBatchManager = entity.getFBatchManager();
        if (FBatchManager != null) {
            stmt.bindString(16, FBatchManager);
        }
 
        String FM = entity.getFM();
        if (FM != null) {
            stmt.bindString(17, FM);
        }
 
        String FMindiameter = entity.getFMindiameter();
        if (FMindiameter != null) {
            stmt.bindString(18, FMindiameter);
        }
 
        String FMaxdiameter = entity.getFMaxdiameter();
        if (FMaxdiameter != null) {
            stmt.bindString(19, FMaxdiameter);
        }
 
        String FISKFPeriod = entity.getFISKFPeriod();
        if (FISKFPeriod != null) {
            stmt.bindString(20, FISKFPeriod);
        }
 
        String FKFPeriod = entity.getFKFPeriod();
        if (FKFPeriod != null) {
            stmt.bindString(21, FKFPeriod);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Product readEntity(Cursor cursor, int offset) {
        Product entity = new Product( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // FItemID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FNumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // FModel
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FFullName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FUnitID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // FUnitGroupID
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // FDefaultLoc
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // FProfitRate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // FTaxRate
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // FOrderPrice
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // FSalePrice
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // FPlanPrice
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // FBarcode
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // FSPID
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // FBatchManager
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // FM
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // FMindiameter
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // FMaxdiameter
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // FISKFPeriod
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // FKFPeriod
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Product entity, int offset) {
        entity.setFItemID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFModel(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFFullName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFUnitID(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFUnitGroupID(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFDefaultLoc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFProfitRate(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFTaxRate(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFOrderPrice(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFSalePrice(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFPlanPrice(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFBarcode(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFSPID(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFBatchManager(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setFM(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setFMindiameter(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setFMaxdiameter(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFISKFPeriod(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setFKFPeriod(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Product entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Product entity) {
        return null;
    }

    @Override
    public boolean hasKey(Product entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

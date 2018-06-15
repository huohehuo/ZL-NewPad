package com.fangzuo.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.fangzuo.assist.Dao.BibieTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BIBIE_TABLE".
*/
public class BibieTableDao extends AbstractDao<BibieTable, Long> {

    public static final String TABLENAME = "BIBIE_TABLE";

    /**
     * Properties of entity BibieTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property FCurrencyID = new Property(1, String.class, "FCurrencyID", false, "FCURRENCY_ID");
        public final static Property FNumber = new Property(2, String.class, "FNumber", false, "FNUMBER");
        public final static Property FName = new Property(3, String.class, "FName", false, "FNAME");
        public final static Property FExChangeRate = new Property(4, String.class, "FExChangeRate", false, "FEX_CHANGE_RATE");
        public final static Property FClassTypeId = new Property(5, String.class, "fClassTypeId", false, "F_CLASS_TYPE_ID");
    }


    public BibieTableDao(DaoConfig config) {
        super(config);
    }
    
    public BibieTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BIBIE_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"FCURRENCY_ID\" TEXT," + // 1: FCurrencyID
                "\"FNUMBER\" TEXT," + // 2: FNumber
                "\"FNAME\" TEXT," + // 3: FName
                "\"FEX_CHANGE_RATE\" TEXT," + // 4: FExChangeRate
                "\"F_CLASS_TYPE_ID\" TEXT);"); // 5: fClassTypeId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BIBIE_TABLE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BibieTable entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String FCurrencyID = entity.getFCurrencyID();
        if (FCurrencyID != null) {
            stmt.bindString(2, FCurrencyID);
        }
 
        String FNumber = entity.getFNumber();
        if (FNumber != null) {
            stmt.bindString(3, FNumber);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(4, FName);
        }
 
        String FExChangeRate = entity.getFExChangeRate();
        if (FExChangeRate != null) {
            stmt.bindString(5, FExChangeRate);
        }
 
        String fClassTypeId = entity.getFClassTypeId();
        if (fClassTypeId != null) {
            stmt.bindString(6, fClassTypeId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BibieTable entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String FCurrencyID = entity.getFCurrencyID();
        if (FCurrencyID != null) {
            stmt.bindString(2, FCurrencyID);
        }
 
        String FNumber = entity.getFNumber();
        if (FNumber != null) {
            stmt.bindString(3, FNumber);
        }
 
        String FName = entity.getFName();
        if (FName != null) {
            stmt.bindString(4, FName);
        }
 
        String FExChangeRate = entity.getFExChangeRate();
        if (FExChangeRate != null) {
            stmt.bindString(5, FExChangeRate);
        }
 
        String fClassTypeId = entity.getFClassTypeId();
        if (fClassTypeId != null) {
            stmt.bindString(6, fClassTypeId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public BibieTable readEntity(Cursor cursor, int offset) {
        BibieTable entity = new BibieTable( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FCurrencyID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // FNumber
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // FName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FExChangeRate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // fClassTypeId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BibieTable entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFCurrencyID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFNumber(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFExChangeRate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFClassTypeId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BibieTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BibieTable entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BibieTable entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
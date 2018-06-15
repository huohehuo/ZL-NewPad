package com.fangzuo.assist.Utils;

import android.content.Context;

import com.fangzuo.assist.Adapter.SearchClientAdapter;
import com.fangzuo.assist.Dao.Client;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class DataGetUtil {
    public static DataGetUtil commonMethod=null;
    Context context;
    private final DaoSession daoSession;
    private static BasicShareUtil share;

    public static DataGetUtil getMethod(Context context){
        if(commonMethod==null){
            commonMethod = new DataGetUtil(context);
        }
        return commonMethod;
    }
    public DataGetUtil(Context context) {
        this.context = context;
        share = BasicShareUtil.getInstance(context);
        daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
    }


    public ArrayList<Client> getClient(String searchString){
        ArrayList<Client> clientList = new ArrayList<>();
        ClientDao clientDao = GreenDaoManager.getmInstance(context).getDaoSession().getClientDao();
        List<Client> clients = clientDao.queryBuilder().where(
                ClientDao.Properties.FName.like("%" + searchString + "%")
//                ClientDao.Properties.FItemID.like("%" + searchString + "%")
        ).orderAsc(ClientDao.Properties.FItemID).build().list();
        if (clients.size() > 0) {
            clientList.addAll(clients);
            return clientList;
        } else {
           return null;
        }
    }

}

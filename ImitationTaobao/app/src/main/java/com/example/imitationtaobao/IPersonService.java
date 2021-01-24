package com.example.imitationtaobao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class IPersonService extends Service {
    private ArrayList<BaseTitle> baseTitles;
    public IPersonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        baseTitles = new ArrayList<>();
        return iBinder;
    }
    private IBinder iBinder = new IPersonManager.Stub() {
        @Override
        public void addBaseTitle(BaseTitle base) throws RemoteException {
            baseTitles.add(base);
        }

        @Override
        public List<BaseTitle> getBaseTitle() throws RemoteException {
            return baseTitles;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

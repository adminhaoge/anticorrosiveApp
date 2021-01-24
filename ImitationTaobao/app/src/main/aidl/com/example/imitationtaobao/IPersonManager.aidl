// IPersonManager.aidl
package com.example.imitationtaobao;

// Declare any non-default types here with import statements
import com.example.imitationtaobao.BaseTitle;
interface IPersonManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void addBaseTitle(in BaseTitle base);
     List<BaseTitle> getBaseTitle();
}

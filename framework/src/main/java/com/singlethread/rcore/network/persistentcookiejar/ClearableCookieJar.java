package com.singlethread.rcore.network.persistentcookiejar;

import okhttp3.CookieJar;

/**
 * This interface extends {@link CookieJar} and adds methods to clear the cookies.
 * Created by litianyuan on 2017/9/6.
 */

public interface ClearableCookieJar extends CookieJar {

    /**
     * Clear all the session cookies while maintaining the persisted ones.
     */
    void clearSession();

    /**
     * Clear all the cookies from persistence and from the cache.
     */
    void clear();
}

package com.singlethread.rcore.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.util.List;

public class FileUtil {
    private static final long LOW_STORAGE_THRESHOLD = 1024 * 1024 * 15;


    public static double sizeOfDiskCache(Context context) {
        return IoUtil.sizeOf(getDiskCachePath(context,null));
    }

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCachePath(Context context,String uniqueName) {

        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        final String cachePath = FileUtil.isExternalStorageMounted() ? FileUtil.getExternalCacheDir(context).getPath() : context.
                getCacheDir().getPath();
        if (uniqueName != null) {
            return new File(cachePath + File.separator + uniqueName);
        } else {
            return new File(cachePath);
        }
    }

    /*
     * 外部存储设备是否就绪
     */
    public static boolean isExternalStorageMounted() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.getExternalStorageDirectory().canWrite()) {
            // No SD card found.
            return false;
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @return The external cache dir
     */

    @SuppressLint("NewApi")
    public static File getExternalCacheDir(Context context) {
        if (VersionUtils.hasFroyo()) {
            if (context != null && context.getExternalCacheDir() != null) {
                return context.getExternalCacheDir();
            }

        }
        // Before Froyo we need to construct the external cache dir ourselves

        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";

        File file = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public static String getExternalStoragePath() {
        // 获取SdCard状态
        String state = Environment.getExternalStorageState();
        // 判断SdCard是否存在并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Environment.getExternalStorageDirectory().canWrite()) {
                return Environment.getExternalStorageDirectory().getPath();
            }
        }
        return null;
    }

    public static boolean checkSdCardAvailable() {

        String state = Environment.getExternalStorageState();
        File sdCardDir = Environment.getExternalStorageDirectory();
        if (Environment.MEDIA_MOUNTED.equals(state) && sdCardDir.canWrite()) {
            if (getAvailableStore(sdCardDir.toString()) > LOW_STORAGE_THRESHOLD) {
                try {
                    // File f = new
                    // File(Environment.getExternalStorageDirectory(), "_temp");
                    // f.createNewFile();
                    // f.delete();
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;

    }

    /**
     * 获取存储卡的剩余容量，单位为字节
     *
     * @param filePath
     * @return availableSpare
     */

    private static long getAvailableStore(String filePath) {
        // 取得sdcard文件路径
        StatFs statFs = new StatFs(filePath);
        // 获取block的SIZE
        long blocSize = statFs.getBlockSize();
        // 获取BLOCK数量
        long totalBlocks = statFs.getBlockCount();
        // 可使用的Block的数量
        long availaBlock = statFs.getAvailableBlocks();
        long availableSpare = availaBlock * blocSize;
        return availableSpare;

    }





    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= 19;// Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static void installApk(Context mContext, File path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(path), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public static String getPachageName(Context context,String fileName) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(fileName, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = info.versionName;       //得到版本信息
            //Toast.makeText(test4.this, "packageName:"+packageName+";version:"+version, Toast.LENGTH_LONG).show();
            return packageName;
        }
        return null;
    }

    public static Uri getUriFromResource(Context context,int resId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + context.getResources().getResourcePackageName(resId) + "/"
                + context.getResources().getResourceTypeName(resId) + "/"
                + context.getResources().getResourceEntryName(resId));
    }


    public interface Callback {
        void callBack(boolean exist);
    }

    public static void isAppInstalled(final Context context, final String packageName, final Callback callback) {

        new AsyncTask<Void, Void, Void>() {
            private boolean result = false;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
                    List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
                    if (pinfo != null) {
                        for (int i = 0; i < pinfo.size(); i++) {
                            String pn = pinfo.get(i).packageName;
                            if (packageName.equals(pn)) {
                                result = true;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void r) {
                callback.callBack(result);
            }
        }.execute();

    }
}

package com.wu.net.down;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:10/19/22
 * <p>
 * 用途:
 */


import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.wu.net.util.NetConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 文件下载
 */
public class DownloadUtil {
    private static final String TAG = "DownloadUtil";
    private static String PATH_CHALLENGE_VIDEO = "";
    //视频下载相关
    private static DownLoadFileInterface mApi;
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mVideoPath; //下载到本地的视频路径

    private static volatile DownloadUtil sInstance;


    public static DownloadUtil getInstance() {
        if (sInstance == null) {
            synchronized (DownloadUtil.class) {
                sInstance = new DownloadUtil();
                mApi = ApiHelper.getInstance().buildRetrofit("https://www.baidu.com/")
                        .createService(DownLoadFileInterface.class);
            }
        }
        return sInstance;
    }


    public DownloadUtil() {
        if (mApi == null) {
            //初始化网络请求接口

        }
    }


    public void cancel() {
        if (mCall != null)
            mCall.cancel();
    }

    public void downloadFile(Context context, String fileName, String url, final DownloadListener downloadListener) {

        //通过Url得到文件并创建本地文件
        PATH_CHALLENGE_VIDEO = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ? context.getExternalFilesDir("") : Environment.getExternalStorageDirectory()) + "/DownloadFile";

        if (createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
            mVideoPath = PATH_CHALLENGE_VIDEO + "/" + fileName;
        }
        if (TextUtils.isEmpty(mVideoPath)) {
            Log.e(TAG, "downloadVideo: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mVideoPath);
        if (!isFileExists(mFile) && createOrExistsFile(mFile)) {
            if (mApi == null) {
                Log.e(TAG, "downloadVideo: 下载接口为空了");
                return;
            }
            mCall = mApi.downloadFile(url);
            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                    //下载文件放在子线程
                    mThread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //保存到本地
                            writeFile2Disk(response, mFile, downloadListener);
                        }
                    };
                    mThread.start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    downloadListener.onFailure(); //下载失败
                }
            });
        } else {
            downloadListener.onFinish(mVideoPath); //下载完成
        }
    }

    //将下载的文件写入本地存储
    private void writeFile2Disk(Response<ResponseBody> response, File file, DownloadListener downloadListener) {

        start(downloadListener);
        long currentLength = 0;
        OutputStream os = null;

        InputStream is = response.body().byteStream(); //获取下载输入流
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                //计算当前下载百分比，并经由回调传出

                changeThread(downloadListener, (int) (100 * currentLength / totalLength));
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
                if ((int) (100 * currentLength / totalLength) == 100) {
                    changeFinish(downloadListener, mVideoPath);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            changeFail(downloadListener);

        } catch (IOException e) {
            e.printStackTrace();
            changeFail(downloadListener);
        } finally {
            if (os != null) {
                try {
                    os.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void changeThread(DownloadListener listener, int process) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(process);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer process) {
                        listener.onProgress(process);
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void changeFinish(DownloadListener listener, String path) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(path);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String path) {
                        listener.onFinish(path);
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void changeFail(DownloadListener listener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String path) {
                        listener.onFailure();
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void start(DownloadListener listener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String path) {
                        listener.onStart();
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    private boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * Return whether the file exists.
     *
     * @param file The file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    private boolean isFileExists(final File file) {
        return file != null && file.exists();
    }


    private boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    private boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
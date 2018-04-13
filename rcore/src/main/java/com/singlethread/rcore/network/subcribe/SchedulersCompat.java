package com.singlethread.rcore.network.subcribe;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observable;

/**
 * Created with Android Studio
 * PackageName: com.singlethread.rcore.network.subcribe
 * Created by litianyuan on 2017/9/6.
 */

public class SchedulersCompat {

    private static final ObservableTransformer computationTransformer=new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    private static final ObservableTransformer ioTransformer=new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };


    private static final ObservableTransformer newTransformer=new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(io.reactivex.schedulers.Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    private static final ObservableTransformer trampolineTransformer=new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(io.reactivex.schedulers.Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    private static final ObservableTransformer executorTransformer=new ObservableTransformer() {
        @Override
        public ObservableSource apply(io.reactivex.Observable observable) {
            return observable.subscribeOn(io.reactivex.schedulers.Schedulers.from(ExecutorManager.eventExecutor)).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * Don't break the chain: use RxJava's compose() operator
     */
    public static <T> Observable.Transformer<T, T> applyComputationSchedulers() {
        return (Observable.Transformer<T, T>) computationTransformer;
    }
    public static <T> Observable.Transformer<T, T> applyIoSchedulers() {
        return (Observable.Transformer<T, T>) ioTransformer;
    }
    public static <T> Observable.Transformer<T, T> applyNewSchedulers() {
        return (Observable.Transformer<T, T>) newTransformer;
    }
    public static <T> Observable.Transformer<T, T> applyTrampolineSchedulers() {
        return (Observable.Transformer<T, T>) trampolineTransformer;
    }
    public static <T> Observable.Transformer<T, T> applyExecutorSchedulers() {
        return (Observable.Transformer<T, T>) executorTransformer;
    }
}

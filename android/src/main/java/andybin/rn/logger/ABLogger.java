package andybin.rn.logger;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ABLogger extends ReactContextBaseJavaModule {
    public ABLogger(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ABLogger";
    }

    @ReactMethod
    public void configeLogger(Map<String, Object> configs) {
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter());
        if(configs == null) {
            return;
        }
        if(configs.get("localStorageEnable") != "true") {
            return;
        }
        String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder = diskPath + File.separatorChar + "logger";
        if(configs.get("filePath") != null) {
            folder = diskPath + configs.get("filePath");
        }
        int MAX_BYTES = 500 * 1024;
        try {
            MAX_BYTES = Integer.parseInt(configs.get("MaxBytes").toString());
        } finally {

        }
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        Handler handler = new WriteHandler(ht.getLooper(), folder, MAX_BYTES);
        DiskLogStrategy logStrategy = new DiskLogStrategy(handler);
        CsvFormatStrategy.Builder stotegyBuild = CsvFormatStrategy.newBuilder();
        stotegyBuild.logStrategy(logStrategy);
        DiskLogAdapter disLogAdapterBuild = new DiskLogAdapter(stotegyBuild.build());
        Logger.addLogAdapter(disLogAdapterBuild);
    }

    @ReactMethod
    public void d(String msg, @Nullable Object... args) {
        Logger.d(msg, args);
    }
    @ReactMethod
    public void e(String msg, @Nullable Object... args) {
        Logger.e(msg, args);
    }
    @ReactMethod
    public void w(String msg, @Nullable Object... args) {
        Logger.w(msg, args);
    }
    @ReactMethod
    public void v(String msg, @Nullable Object... args) {
        Logger.v(msg, args);
    }
    @ReactMethod
    public void i(String msg, @Nullable Object... args) {
        Logger.i(msg, args);
    }
    @ReactMethod
    public void wtf(String msg, @Nullable Object... args) {
        Logger.wtf(msg, args);
    }
    @ReactMethod
    public void json(String json) {
        Logger.json(json);
    }
    @ReactMethod
    public void xml(String xml) {
        Logger.xml(xml);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }
}

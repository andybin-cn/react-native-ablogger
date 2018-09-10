package andybin.rn.logger;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    public void configLogger(ReadableMap configs, Callback resultCallback) {
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter(CsvFormatStrategy.newBuilder()
                .tag("ReactNativeJS")
                .logStrategy(new LogcatLogStrategy())
                .build()));
        if(configs == null) {
            resultCallback.invoke("failed");
            return;
        }

        if(!configs.getBoolean("localStorageEnable")) {
            resultCallback.invoke("localStorageEnable not enable");
            return;
        }
//        String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String diskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String folder = diskPath + File.separatorChar + "logger";
        if(configs.getString("filePath") != null) {
            folder = diskPath + File.separatorChar + configs.getString("filePath");
        }
        int MAX_BYTES = 500 * 1024;
        try {
            MAX_BYTES = configs.getInt("MaxBytes");
        } finally {

        }
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        Handler handler = new WriteHandler(ht.getLooper(), folder, MAX_BYTES);
        DiskLogStrategy logStrategy = new DiskLogStrategy(handler);

        CsvFormatStrategy stotegy = CsvFormatStrategy.newBuilder()
                .tag("ReactNativeJS")
                .logStrategy(logStrategy)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(stotegy));
    }

    private String[] convertToArguments(@Nullable ReadableArray args) {
        if(args == null) {
            return null;
        }
        List<Object> argsList = args.toArrayList();
        String[] params = new String[argsList.size()];
        for (int i = 0; i < argsList.size(); i++) {
          if(argsList.get(i) != null) {
            params[i] = argsList.get(i).toString();
          } else {
            params[i] = "null";
          }
        }
        return params;
    }

    @ReactMethod
    public void d(String msg, @Nullable ReadableArray args) {
        Logger.d(msg, convertToArguments(args));
    }
    @ReactMethod
    public void e(String msg, @Nullable ReadableArray args) {
        Logger.e(msg, convertToArguments(args));
    }
    @ReactMethod
    public void w(String msg, @Nullable ReadableArray args) {
        Logger.w(msg, convertToArguments(args));
    }
    @ReactMethod
    public void v(String msg, @Nullable ReadableArray args) {
        Logger.v(msg, convertToArguments(args));
    }
    @ReactMethod
    public void i(String msg, @Nullable ReadableArray args) {
        Logger.i(msg, convertToArguments(args));
    }
    @ReactMethod
    public void wtf(String msg, @Nullable ReadableArray args) {
        Logger.wtf(msg, convertToArguments(args));
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

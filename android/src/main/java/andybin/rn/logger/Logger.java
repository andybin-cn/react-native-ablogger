package andybin.rn.logger;

import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.util.HashMap;
import java.util.Map;

public class Logger extends ReactContextBaseJavaModule {
    public Logger(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
//        constants.put(Storage.BACKED_UP.toString(), baseDirForStorage(Storage.BACKED_UP));
//        constants.put(Storage.IMPORTANT.toString(), baseDirForStorage(Storage.IMPORTANT));
//        constants.put(Storage.AUXILIARY.toString(), baseDirForStorage(Storage.AUXILIARY));
//        constants.put(Storage.TEMPORARY.toString(), baseDirForStorage(Storage.TEMPORARY));
        return constants;
    }
}

import ReactNative from 'react-native'

const RNABLogger = ReactNative.NativeModules.RNABLogger

class ABLogger {
  configeLogger({localStorageEnable, filePath, MaxBytes}) {
    RNABLogger.configeLogger({localStorageEnable, filePath, MaxBytes})
  }
  d() {
    RNABLogger.d(...arguments)
  }
  e() {
    RNABLogger.e(...arguments)
  }
  w() {
    RNABLogger.w(...arguments)
  }
  v() {
    RNABLogger.v(...arguments)
  }
  i() {
    RNABLogger.i(...arguments)
  }
  wtf() {
    RNABLogger.wtf(...arguments)
  }
  json(json) {
    RNABLogger.json(json)
  }
  xml() {
    RNABLogger.xml(xml)
  }
}

export default new ABLogger()
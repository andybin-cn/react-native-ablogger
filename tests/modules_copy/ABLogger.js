import { NativeModules } from 'react-native'

const RNABLogger = NativeModules.ABLogger

class ABLogger {
  configLogger({localStorageEnable, filePath, MaxBytes}) {
    RNABLogger.configLogger({localStorageEnable, filePath, MaxBytes}, (folder) => {
      console.info('RNABLogger.configLogger result:', folder)
    })
  }
  formatArguments(originArgs) {
    let msg, args
    if(originArgs && originArgs.length > 0) {
      msg = originArgs[0]
    }
    if(originArgs && originArgs.length > 1) {
      args = Array.prototype.slice.call(originArgs).slice(1)
      msg = msg + args.map(item => ' %s').join('')
    }
    return [msg, args]
  }
  d() {
    RNABLogger.d(...this.formatArguments(arguments))
  }
  e() {
    RNABLogger.e(...this.formatArguments(arguments))
  }
  w() {
    RNABLogger.w(...this.formatArguments(arguments))
  }
  v() {
    RNABLogger.v(...this.formatArguments(arguments))
  }
  i() {
    RNABLogger.i(...this.formatArguments(arguments))
  }
  wtf() {
    RNABLogger.wtf(...this.formatArguments(arguments))
  }
  json(json) {
    RNABLogger.json(json)
  }
  xml() {
    RNABLogger.xml(xml)
  }
}

export default new ABLogger()
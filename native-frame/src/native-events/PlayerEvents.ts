// import { NativeModules, EventEmitter } from 'react-native'
// import { Platform, DeviceEventEmitter, NativeEventEmitter } from 'react-native'

// export class PlayerEvents {
//   emitter!: EventEmitter

//   subscribeEvents() {
//     this.emitter =
//       Platform.OS === 'android'
//         ? DeviceEventEmitter
//         : new NativeEventEmitter(NativeModules.EncoderEvents)
//   }

//   clear() {
//     this.emitter.removeAllListeners()
//   }

//   onStreamAdded(streamId: string, userId: string) {
//     if (NativeModules.PlayerEvents)
//       NativeModules.PlayerEvents.onStreamAdded(streamId, userId)
//   }

//   onStreamRemoved(streamId: string, userId: string) {
//     if (NativeModules.PlayerEvents)
//       NativeModules.PlayerEvents.onStreamRemoved(streamId, userId)
//   }
// }

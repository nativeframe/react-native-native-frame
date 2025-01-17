// import { NativeModules, EventEmitter } from 'react-native'
// import { Platform, DeviceEventEmitter, NativeEventEmitter } from 'react-native'

// export interface CallEventProperties {
//   callId: string | undefined
// }

// export interface CallRequestListener {
//   onRequestJoinCall(properties: CallEventProperties): void

//   onRequestStartCall(properties: CallEventProperties): void

//   onRequestLeaveCall(properties: CallEventProperties): void
// }

// export class CallEvents {
//   readonly requestsListener: CallRequestListener

//   constructor(requestsListener: CallRequestListener) {
//     this.requestsListener = requestsListener
//   }

//   emitter!: EventEmitter

//   subscribeEvents() {
//     this.emitter =
//       Platform.OS === 'android'
//         ? DeviceEventEmitter
//         : new NativeEventEmitter(NativeModules.CallEvents)

//     this.emitter.addListener('call.join', (properties: CallEventProperties) => {
//       console.info('event: call.join - ' + properties?.callId)
//       this.requestsListener.onRequestJoinCall(properties)
//     })

//     this.emitter.addListener(
//       'call.start',
//       (properties: CallEventProperties) => {
//         console.info('event: call.start')
//         this.requestsListener.onRequestStartCall(properties)
//       }
//     )

//     this.emitter.addListener(
//       'call.leave',
//       (properties: CallEventProperties) => {
//         console.info('event: call.leave')
//         this.requestsListener.onRequestLeaveCall(properties)
//       }
//     )
//   }

//   clear() {
//     this.emitter.removeAllListeners()
//   }

//   onCallReady() {
//     console.log('onCallReady')
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onCallReady()
//   }

//   onCallJoined() {
//     console.log('onCallJoined')
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onCallJoined()
//   }

//   onUnableToJoinCall() {
//     console.log('onUnableToJoinCall')
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onUnableToJoinCall()
//   }

//   onCallCreated() {
//     console.log('onCallCreated')
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onCallCreated()
//   }

//   onUnableToCreateCall() {
//     console.log('onUnableToCreateCall')
//     if (NativeModules.CallEvents)
//       NativeModules.CallEvents.onUnableToCreateCall()
//   }

//   onCallLeft() {
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onCallLeft()
//   }

//   onUnableToLeaveCall() {
//     if (NativeModules.CallEvents) NativeModules.CallEvents.onUnableToLeaveCall()
//   }
// }

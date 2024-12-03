// import { NativeModules, EventEmitter } from 'react-native'
// import { Platform, DeviceEventEmitter, NativeEventEmitter } from 'react-native'

// export interface EncoderRequestsListener {
//   onRequestEncoderStop(): void

//   onRequestEncoderStart(): void

//   onRequestVideoStop(): void

//   onRequestVideoStart(): void

//   onRequestAudioStop(): void

//   onRequestAudioStart(): void

//   onRequestCameraFront(): void

//   onRequestCameraRear(): void
// }

// export class EncoderEvents {
//   readonly requestsListener: EncoderRequestsListener

//   constructor(requestsListener: EncoderRequestsListener) {
//     this.requestsListener = requestsListener
//   }

//   emitter!: EventEmitter

//   subscribeEvents() {
//     this.emitter =
//       Platform.OS === 'android'
//         ? DeviceEventEmitter
//         : new NativeEventEmitter(NativeModules.EncoderEvents)

//     this.emitter.addListener('encoder.stop', () => {
//       this.requestsListener.onRequestEncoderStop()
//     })

//     this.emitter.addListener('encoder.start', () => {
//       this.requestsListener.onRequestEncoderStart()
//     })

//     this.emitter.addListener('video.stop', () => {
//       this.requestsListener.onRequestVideoStop()
//     })

//     this.emitter.addListener('video.start', () => {
//       this.requestsListener.onRequestVideoStart()
//     })

//     this.emitter.addListener('audio.stop', () => {
//       this.requestsListener.onRequestAudioStop()
//     })

//     this.emitter.addListener('audio.start', () => {
//       this.requestsListener.onRequestAudioStart()
//     })

//     this.emitter.addListener('camera.front', () => {
//       this.requestsListener.onRequestCameraFront()
//     })

//     this.emitter.addListener('camera.rear', () => {
//       this.requestsListener.onRequestCameraRear()
//     })
//   }

//   clear() {
//     this.emitter.removeAllListeners()
//   }

//   onEncoderStarted() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onEncoderStarted()
//   }

//   onEncoderStopped() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onEncoderStopped()
//   }

//   onVideoStarted() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onVideoStarted()
//   }

//   onVideoStopped() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onVideoStopped()
//   }

//   onAudioStarted() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onAudioStarted()
//   }

//   onAudioStopped() {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onAudioStopped()
//   }

//   onCameraFront() {
//     if (NativeModules.EncoderEvents) NativeModules.EncoderEvents.onCameraFront()
//   }

//   onCameraRear() {
//     if (NativeModules.EncoderEvents) NativeModules.EncoderEvents.onCameraRear()
//   }

//   onSourceStream(streamId: string) {
//     if (NativeModules.EncoderEvents)
//       NativeModules.EncoderEvents.onSourceStream(streamId)
//   }

//   onDemoReadyToView(demoUrl: string) {
//     NativeModules.EncoderEvents?.onDemoReadyToView(demoUrl)
//   }
// }

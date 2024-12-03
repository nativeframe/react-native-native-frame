// import '@video/video-client-reactnative'
// import { Component } from 'react'

// import {
//   EncoderEvents,
//   EncoderRequestsListener,
// } from '../native-events/EncoderEvents'
// import { types, mediaController } from '@video/video-client-core'
// import { getRandomDecimal } from '../util/StringUtil'

// class BaseEncoderProperties {
//   componentProps: any

//   readonly call: types.BaseCall

//   readonly isEncodeOnStart: boolean

//   constructor(call: types.BaseCall, isEncodeOnStart: boolean) {
//     this.call = call
//     this.isEncodeOnStart = isEncodeOnStart
//   }
// }

// class BaseEncoder extends Component implements EncoderRequestsListener {
//   readonly TAG = 'BaseEncoder'
//   readonly state: any

//   baseBroadcast?: types.BaseBroadcast
//   mediaStreamController?: types.MediaStreamController
//   call?: types.BaseCall = undefined //set in NativeCall

//   readonly encoderProps: BaseEncoderProperties

//   readonly encoderEvents = new EncoderEvents(this)

//   constructor(encoderProps: BaseEncoderProperties) {
//     super(encoderProps.componentProps)
//     this.state = {
//       localStream: undefined,
//     }

//     this.encoderProps = encoderProps
//   }

//   log(msg: string, p: any = {}) {
//     console.log(`[${this.TAG}] ${msg}`, p)
//   }

//   logError(msg: string, p: any = {}) {
//     console.error(`[${this.TAG}] ${msg}`, p)
//   }

//   //#region EncoderRequestsListener
//   onRequestEncoderStop() {
//     if (this.isBroadcasting()) {
//       this.stopBroadcast()
//     }
//   }

//   onRequestEncoderStart() {
//     if (!this.isBroadcasting()) {
//       this.broadcast()
//     }
//   }

//   onRequestVideoStop() {
//     if (this.mediaStreamController && !this.mediaStreamController.videoPaused) {
//       this.mediaStreamController.videoPaused = true

//       this.encoderEvents.onVideoStopped()
//     }
//   }

//   onRequestVideoStart() {
//     if (this.mediaStreamController?.videoPaused) {
//       this.mediaStreamController.videoPaused = false

//       this.encoderEvents.onVideoStarted()
//     }
//   }

//   onRequestAudioStop() {
//     if (this.mediaStreamController && !this.mediaStreamController.audioMuted) {
//       this.mediaStreamController.audioMuted = true

//       this.encoderEvents.onAudioStopped()
//     }
//   }

//   onRequestAudioStart() {
//     if (this.mediaStreamController?.audioMuted) {
//       this.mediaStreamController.audioMuted = false

//       this.encoderEvents.onAudioStarted()
//     }
//   }

//   onRequestCameraFront() {
//     this.setCamera('front')
//     this.encoderEvents.onCameraFront()
//   }

//   onRequestCameraRear() {
//     this.setCamera('environment')
//     this.encoderEvents.onCameraRear()
//   }
//   //#endregion

//   setCamera(facing: string) {
//     const [device] = mediaController
//       .videoDevices()
//       .filter((d) => (d as any).facing === facing)
//     if (this.mediaStreamController) {
//       this.mediaStreamController.videoDeviceId = device.deviceId
//     }
//   }

//   // In future iterations we will add the ability to switch audio source
//   setMic() {
//     const audioDevices = mediaController.audioDevices()
//     if (this.mediaStreamController) {
//       this.mediaStreamController.audioDeviceId = audioDevices[0].deviceId
//     }
//   }

//   //#region react native hooks
//   componentDidMount() {
//     if (this.encoderProps.isEncodeOnStart) {
//       this.broadcast()
//     }

//     this.encoderEvents.subscribeEvents()
//   }

//   isSetup() {
//     return this.mediaStreamController !== undefined
//   }

//   async setup() {
//     if (this.isSetup()) return

//     const doSetup = async () => {
//       this.log('setup starting...')
//       try {
//         await mediaController.init()
//       } catch (error) {
//         this.logError('unable to init mediaController', error)
//       }

//       try {
//         this.mediaStreamController = await mediaController.requestController()
//       } catch (error) {
//         this.logError('unable to get mediaStreamController', error)
//       }

//       this.mediaStreamController?.on('source', (stream) => {
//         this.log('stream ', stream)
//         this.setState({ localStream: stream })
//         //toURL() is not defined in MediaStream, so casting to any
//         this.encoderEvents.onSourceStream((stream as any).toURL())
//       })

//       try {
//         if (this.mediaStreamController) {
//           this.mediaStreamController.audioMuted = false
//           this.mediaStreamController.videoPaused = false
//         }

//         this.setCamera('front')
//         this.setMic()
//       } catch (error) {
//         this.logError('error settin up mediaStreamController', error)
//       }
//     }

//     await doSetup()
//   }

//   componentWillUnmount() {
//     this.encoderEvents.clear()
//     if (this.mediaStreamController) {
//       this.mediaStreamController.dispose('react native component will unmount')
//     }
//   }

//   //#endregion

//   isBroadcasting(): boolean {
//     return this.baseBroadcast?.state == 'active' ?? false
//   }

//   stopBroadcast = async () => {
//     if (!this.baseBroadcast) {
//       this.log(
//         "not currently broadcasting so there's nothing to stop. skipping..."
//       )
//       return false
//     }

//     this.log('stopping broadcast...')

//     this.baseBroadcast.dispose('stop broadcast call')
//     this.baseBroadcast = undefined

//     this.log('broadcast stopped')

//     this.encoderEvents.onEncoderStopped()
//     return true
//   }

//   updateState() {
//     this.setState({
//       localStream: this.state.localStream,
//       isBroadcasting: this.isBroadcasting(),
//     })
//   }

//   /**
//    * toggle function used for the sample's "Start/Stop Broadcast button"
//    * this is used for quick testing with react native + VDC, not intented for production use
//    */
//   toggleBroadcast = async () => {
//     await this.setup()

//     if (this.baseBroadcast) {
//       this.log('found broadcast to stop')

//       this.stopBroadcast().then(() => {
//         this.updateState()
//       })
//     } else if (this.mediaStreamController) {
//       this.log("not currently broadcasting, so let's start broadcasting")

//       this.broadcast().then(() => {
//         this.updateState()
//       })
//     } else {
//       this.logError('no broadcaster and no mediaStreamController to broadcast')
//     }
//   }

//   broadcast = async (): Promise<void> => {
//     const doBroadcast = async () => {
//       await this.setup()

//       this.log('broadcast starting...')

//       try {
//         this.baseBroadcast = await this.call?.broadcast(
//           this.mediaStreamController!,
//           {
//             streamName: `mobile-${getRandomDecimal()}`,
//           }
//         )
//       } catch (error) {
//         this.logError('unable to broadcast to call', error)
//       }

//       this.log('currently broadcasting...')

//       const onStart = () => {
//         this.encoderEvents.onEncoderStarted()

//         //signal the encoder state
//         if (this.mediaStreamController) {
//           if (!this.mediaStreamController.videoPaused) {
//             this.encoderEvents.onVideoStarted()
//           } else {
//             this.encoderEvents.onVideoStopped()
//           }

//           if (!this.mediaStreamController.audioMuted) {
//             this.encoderEvents.onAudioStarted()
//           } else {
//             this.encoderEvents.onAudioStopped()
//           }
//         }
//       }

//       onStart()
//     }

//     await doBroadcast()
//   }

//   render() {
//     return false
//   }
// }

// export default BaseEncoder

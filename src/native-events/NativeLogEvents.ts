// import { NativeModules, Platform, DeviceEventEmitter, NativeEventEmitter, EventEmitter } from "react-native";
// import { types } from "@video/video-client-core";

// /**
//  * Mobile native specifc log codes
//  */
// export enum NativeLogCode {
//     General = "log"
// }

// /**
//  * Mobile native specifc error codes
//  */
// export enum NativeErrorCode {
//     General = "general-error",
//     ParseError = "json-error"
// }

// /**
//  * Log request properties, sent from native apps (helpful for testing)
//  */
// export interface NativeLogRequestProperties {
//     code?: string;
//     props?: any;
// }

// /**
//  * Log request listener
//  */
// export interface NativeLogRequestsListener {
//     /**
//      * When an error is triggered (from native app)
//      * 
//      * @param code Error code
//      * @param props Properties
//      */
//     onTriggerError(code: string, props: any): void;
// }

// /**
//  * Bridge for sending errors from react-native to native iOS/Android
//  */
// export class NativeLogEvents {
//     private readonly nativeLogRequestsListener?: NativeLogRequestsListener
//     private emitter?: EventEmitter;

//     constructor(nativeLogRequestsListener?: NativeLogRequestsListener) {
//         this.nativeLogRequestsListener = nativeLogRequestsListener;
//     }

//     expand(code: string, props: any): string {
//         let res = "{}";

//         try {
//             res = JSON.stringify(props);
//         } catch (error) {
//             NativeModules.NativeLogEvents?.onWarn(NativeErrorCode.ParseError, JSON.stringify({
//                 message: `error expanding properties for log code: ${code}`
//             }));
//         }

//         return res;
//     }

//     onError(code: string, props: any) {
//         NativeModules.NativeLogEvents?.onError(code, this.expand(code, props));
//     }

//     onWarn(code: string, props: any) {
//         NativeModules.NativeLogEvents?.onWarn(code, this.expand(code, props));
//     }

//     onInfo(code: string, props: any) {
//         NativeModules.NativeLogEvents?.onInfo(code, this.expand(code, props));
//     }

//     subscribeEvents() {
//         this.emitter = Platform.OS === "android" ? DeviceEventEmitter : new NativeEventEmitter(NativeModules.NativeLogEvents);

//         this.emitter?.addListener("trigger.error", (properties?: NativeLogRequestProperties) => {
//             const code = properties?.code;
//             if (code) {
//                 this.nativeLogRequestsListener?.onTriggerError(code, properties.props);
//             }
//         });
//     }

//     clear() {
//         this.emitter?.removeAllListeners();
//     }

//     /**
//      * Listen for error/log events on given objects. 
//      * @param opts Objects to listen to. Only providing 1 of the objects is suggested; probably [videoClient]
//      */
//     attach(opts: {
//         videoClient?: types.VideoClientAPI,
//         broadcast?: types.BroadcastAPI,
//         call?: types.CallAPI
//     }) {
//         opts.videoClient?.on("error", (err) => {
//             this.onError(err.code, err);
//         });
//         opts.broadcast?.on("error", (err) => {
//             this.onError(err.code, err);
//         });
//         opts.call?.on("error", (err) => {
//             this.onError(err.code, err);
//         });
//     }
// }
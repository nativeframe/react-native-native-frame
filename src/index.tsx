const NativeFrame = require('./NativeNativeFrame').default;

export function multiply(a: number, b: number): number {
  return NativeFrame.multiply(a, b);
}

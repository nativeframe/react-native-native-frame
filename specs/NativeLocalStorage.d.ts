import type { TurboModule } from 'react-native';
export interface Spec extends TurboModule {
    setItem(value: string, key: string): void;
    getItem(key: string): string | null;
    removeItem(key: string): void;
    clear(): void;
}
declare const _default: Spec;
export default _default;
//# sourceMappingURL=NativeLocalStorage.d.ts.map
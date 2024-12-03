import { StyleSheet, View, Text } from 'react-native';
import { multiply } from 'react-native-native-frame';
import NativeFrameStream from '../../src/NativeFrameStream';

const result = multiply(3, 7);

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>

      <NativeFrameStream />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

import { StyleSheet, View, Text } from 'react-native';
import NativeFrame from './NativeFrame';

export default function NativeFrameStream() {
  return (
    <View style={styles.container}>
      <NativeFrame />
      <Text style={styles.text}>Native Frame Streaming</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
    color: 'black',
  },
  text: {
    color: 'black',
  },
});

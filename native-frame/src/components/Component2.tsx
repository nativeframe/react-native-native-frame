import { Text, StyleSheet } from 'react-native';
import Video from 'react-native-video';

export default function Component2() {
  return (
    <>
      <Text>HLS Player</Text>
      <Video
        source={{
          uri: 'https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8',
        }}
        style={styles.backgroundVideo}
      />
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingBottom: 20,
  },
  label: {
    fontWeight: '600',
  },
  input: {
    height: 40,
    borderWidth: 1,
    padding: 10,
    borderRadius: 3,
  },
  backgroundVideo: {
    marginTop: 20,
    borderColor: 'black',
    borderWidth: 1,
    width: '100%',
    height: 280,
    marginBottom: 20,
  },
  qualityPicker: {
    borderColor: 'black',
    borderWidth: 1,
    height: 50,
    width: 280,
  },
});

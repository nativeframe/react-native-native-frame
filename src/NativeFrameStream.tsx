import { VideoClient, type types } from '@video/video-client-core';
import { StyleSheet, View, Text } from 'react-native';

export default function NativeFrameStream() {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>Native Frame Streaming</Text>
    </View>
  );
}

export const initVideoClient = async (token: string) => {
  const videoClientOptions: types.VideoClientOptions = {
    backendEndpoints: ['https://platform.nativeframe.com'],
    token: token,
    userId: 'aj',
  };
  return new VideoClient(videoClientOptions);
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    color: '#ffffff',
  },
  text: {
    color: '#ffffff',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

import React from 'react';
import {StyleSheet, View, Text} from 'react-native';
import NFWebView from './specs/NFWebViewNativeComponent';
import NFButton from './specs/NFButtonNativeComponent';

function App(): React.JSX.Element {
  return (
    <View style={styles.container}>
      <Text style={styles.label}>-- Web View --</Text>
      <NFWebView
        sourceURL="https://react.dev/"
        style={styles.webview}
        onScriptLoaded={() => {
          console.log('Page Loaded');
        }}
      />

      <Text style={styles.label}>-- Button --</Text>
      <NFButton style={styles.button}  text="My custom button"/>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    alignContent: 'center',
  },
  label: {
    color: '#ffffff',
    paddingTop: 20,
    paddingBottom: 5,
  },
  webview: {
    width: '100%',
    height: '50%',
  },
  button: {
    width: 200,
    height: 50,
  },
});

export default App;

import { StatusBar } from 'expo-status-bar';
import { Fragment, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';



export default function App() {
const [area, setArea] = useState({test:1});

  return <Fragment>
    {area.test}
    <button onClick={()=>{setArea({test:area.test+1})}}></button>
    <p>{area.test}</p>
  </Fragment>
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { Routes, Route, useNavigate } from "react-router-dom";
import test from '/test';
export default function App() {
  const goHome = () => {
    setText('Home 페이지 입니다.')
    navigate('/test');
  }
  return (

    <View style={styles.container}>
      
      <Text>테스트</Text>
      <button type="button" onClick={goHome}>Home</button>
      
      <StatusBar style="auto" />

    </View>

  );
}

function testfunc(){
  <div className="App">
  <BrowserRouter>
      <Routes>
        <Route path="/" element={<TestHome />} />
        <Route path="test" element={<Test />} />
      </Routes>
  </BrowserRouter>
</div>
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

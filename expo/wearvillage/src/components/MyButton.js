import { Fragment } from "react";
import { Pressable } from "react-native";
import { Text } from "react-native";

const MyButton = () => {
    return (
        <Pressable
        style={{
        color: 'black',
        backgroundColor:'red',
        }}
        onPress={()=>alert("click!!!~~")}
        >
            버튼
            </Pressable>
    );
};
export default MyButton;
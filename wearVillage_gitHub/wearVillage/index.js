const person = {
    name: '홍길동',
    age: 30,
    blood: 'A',
    gender: '남',
    smile : function() {
    console.log("웃다")
    },
    eat(){
    console.log('먹다');
    }
}

const {name,age,blood} = person;
console.log(name);
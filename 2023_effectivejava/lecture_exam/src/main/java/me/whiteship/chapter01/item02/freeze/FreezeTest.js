// 'use strict' // freeze 동작에서 변경하려고할 경우 오류 발생을 위해 선언

const keesun = { // 새로운 값 할당 불가능
    'name': 'Keesun',
    'age': 40
};

// 이렇게하면 어느시점에 freeze 할지를 찾아야하고, 인지하고있어야하는 불편함이 있다.
Object.freeze(keesun); // 객체 얼리기

keesun.kids = ["서연"]; // 수정 불가능

console.info(keesun.name);
console.info(keesun.kids);
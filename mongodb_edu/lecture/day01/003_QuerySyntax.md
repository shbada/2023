####  nested documents
- address.city
```
db.people.insertOne( {"name": "John Doe",
    "email": "john.doe@mongodb.com",
    "address": {"country": "USA", "city": "New York", "zipcode": "10005"}
})

db.people.findOne({})
db.people.findOne({"address.city" : "New York"})
db.people.findOne({"name": "John Doe", "address.city" : "New York"})
```

- 오류 (.city를 메서드로 인식할 수 있음)
```
db.people.findOne({address.city : "New York"})
```
- This will only work if there are no other fields in the address
- 하위필드를 오브젝트 형식으로 넣기 때문에 country, city, zipcode 모두 포함해야한다.
```
db.people.findOne({address : { city: "New York"}})
```

####  by ranges of values
```
for(x=0;x<200;x++) { db.taxis.insertOne({plate:x})}
db.taxis.find({plate : { $gt : 25 }}) // >25
```
- Type "it" for more
```
db.taxis.find({plate: { $gte: 25 }}) // >= 25
db.taxis.find({plate: { $lt: 25 }}) // < 25
db.taxis.find({plate: { $gt: 25 , $lt:30 }}) // between 25 and 30
db.taxis.find({plate: { $ne: 3 }}) // Not 3
db.taxis.find({plate: { $in: [1,3,6] }}) // 1,3 or 6
db.taxis.find({plate: { $nin: [2,4,7] }})// Not 2,4 or 7
db.taxis.find({plate: { $eq: 6 }})// Same as { plate:6 }
```

####  Boolean Logic Operators
```
db.pets.insertMany([
    { species: "cat", color: "brown"},
    { species: "cat", color: "black"},
    { species: "dog", color: "black"},
    { species: "dog", color: "brown"},
])

db.pets.find({ $or : [ {species:"cat",color:"black"}, {species:"dog",color:"brown"} ]})
```

####  Black pets that are not less than cats (alphabetically)
```
db.pets.find({species: {$not: {$lte: "cat" }}, color: "black" })
```

####  Arrays
```
db.fun.insertOne({ "name": "John", hobbies: ["cars","robots","gardens"]})
```

- Find by ANY member of the array
```db.fun.find({hobbies:"gardens"})```
- Find by matching the array itself
```db.fun.find({hobbies:["cars","robots","gardens"]})```
- Not found - order doesn't match
```db.fun.find({hobbies:["robots","cars","gardens"]})```
- Not found - missing element
```db.fun.find({hobbies:["cars","robots"]})```

####  Array specific query
```
db.fun.insertOne({ "name": "John", hobbies: ["cars","robots","gardens"]})
db.fun.find({ hobbies : { $all : ["robots","cars"] }})
db.fun.find({ hobbies : { $all : ["robots","cars","bikes"] }})
```
- No result as bikes is not in the array
```
db.fun.find({ hobbies : { $size : 3}})
db.fun.find({ hobbies : { $size : 4}})
```

####  Array Match
- 잘못된 매칭 (grade "C" 인것 + date 조건에 맞는것 UNION ALL과 같이 모두 가져와버림)
```
- db.restaurants.find({
    "grades.grade":"C",
    "grades.date":{$gt:ISODate("2013-12-31")}
})
```

####  $elemMatch
```
db.restaurants.find({
    { grades : {$elemMatch: { "grades.grade":"C", "grades.date":{$gt:ISODate("2013-12-31")} } }}
})
```

####  sort
```
let rnd = (x)=>Math.floor(Math.random()*x)

for(let x=0;x<100;x++) { db.scores.insertOne({ride:rnd(40),swim:rnd(40),run:rnd(40)})}
- Unsorted
db.scores.find({},{_id:0})
- Sorted by ride increasing
db.scores.find({},{_id:0}).sort({ride:1})
- Sorted by swim increasing then ride decreasing
db.scores.find({},{_id:0}).sort({swim:1, ride:-1})
```

####  Expressive Queries
- find문 조회할때 Aggregation Expression 사용
- $expr 사용
- document 내부의 value들을 직접 비교하는 방법
- $tomatoes 필드의 viewer필드 밑의 rating의 값을 가져오기 (array 형태라 $ 써야됨)
- $imdb 필드의 rating
- $tomatoes.viewer.rating > $imdb.rating
```
db.movies.find({$expr: { $gt: [ "$tomatoes.viewer.rating" ,"$imdb.rating" ] }})

use sample_training
```

####  Grades where average score < 50
```
db.grades.find({
    $expr: { $lt: [
        { $avg: "$scores.score" }, -- score의 avg 계산
        50
    ]}
})
```


####  Updating Documents
```
db.players.insertMany([
{ _id: "mary", points: 150, wins: 25, highscore: 60 },
{ _id: "tom", points: 95, wins: 18, highscore: 110 }])
```

####  id가 mary인 것의 points, wins를 업데이트해라
```
db.players.updateOne({_id:"mary"},{$set : { points :160, wins: 26}})

db.players.find({_id:"mary"})
```

####  points가 200 이하인것을 level을 beginner로 셋팅해라
```
db.players.updateMany({points : {$lt:200}}, {$set:{level:"beginner"}})

db.players.find()
```

####  $unset
```
db.bands.insertOne({ _id: "genesis", Singer: "Peter", Drums: "Phil",Keyboard:"Tony",Guitar:"Mike"})
db.bands.findOne()
{
"_id" : "genesis",
"Singer" : "Peter",
"Drums" : "Phil",
"Keyboard" : "Tony",
"Guitar" : "Mike"
}
```

####  Singer 날리기
```
db.bands.updateOne({ _id :"genesis" }, { $unset: {"Singer":true}})
db.bands.findOne()
{
"_id" : "genesis",
"Drums" : "Phil",
"Keyboard" : "Tony",
"Guitar" : "Mike"
}
```

####  $inc, $mul
```
db.employees.insertOne({name: "Carol", salary: 10000, bonus: 500})
```
- Give everyone a 10% payrise
```
db.employees.updateMany({},{$mul : {salary: 1.1}})

db.employees.find({},{_id:0})
```
- { "name" : "Carol", "salary" : 11000, "bonus" : 500 }
- Give Carol 1000 more bonus too

####  만약 bonus 필드가 없으면, 0으로 하여 1000으로 넣어줌 ($mul 은 0 계산이므로 처리가 안됨)
```
db.employees.updateOne({name:"Carol"}, {$inc:{bonus:1000}})
db.employees.find({},{_id:0})
```
- { "name" : "Carol", "salary" : 11000, "bonus" : 1500 }

####  $max, $min
```db.gamescores.insertOne({name: "pacman", highscore: 10000 })```

-  This finds the record but does not change it as 9000 < 10000
```db.gamescores.updateOne({name:"pacman"},{$max: { "highscore": 9000}})```

-  This finds and changes highscore as 12000 > 10000
```db.gamescores.updateOne({name:"pacman"},{$max: { "highscore": 12000}})```

```db.gamescores.find({})```
- { "_id" : ObjectId("609bf0f8cf0c3aa225ce9314"), "name" : "pacman","highscore" : 12000 }


####  $push
```
db.playlists.insertOne(
    {name: "funky",
    tracks : [
    { artist:"queen",track:"Liar"},
    {artist:"abba",track:"Chiquitita"},
]})
```

####  set 을 쓰면 데이터 전체가 바뀌기 때문에 push로 추가하자.
```
db.playlists.updateOne({name:"funky"},
{ $push : {tracks : { artist: "AC/DC", track: "Hells Bells"} }})

db.playlists.find({}).pretty()
{ "_id" : ObjectId(""),
  "name" : "funky",
  "tracks" : [
    {"artist" : "queen", "track" : "Liar" },
    {"artist" : "abba", "track" : "Chiquitita" },
    {"artist" : "AC/DC", "track" : "Hells Bells" } ] -- 추가됨을 알수있다.
}
```

####  $pop
```db.playlists.find({},{_id:0}).pretty()```
{ "name" : "funky",
"tracks" : [ {"artist" : "queen", "track" : "Liar" },
{"artist" : "abba", "track" : "Chiquitita" },
{"artist" : "AC/DC", "track" : "Hells Bells" }]
}

####  뒤쪽에 추가된 1개를 제거
```
db.playlists.updateOne({name:"funky"},{ $pop: {tracks: 1}})
db.playlists.find({},{_id:0}).pretty()
```
{ "name" : "funky",
"tracks" : [{"artist" : "queen", "track" : "Liar" },
{"artist" : "abba", "track" : "Chiquitita" }]
}

####  앞쪽에 추가된 1개를 제거
```db.playlists.updateOne({name: "funky"},{ $pop: {tracks: -1}})```

```db.playlists.find({},{_id:0}).pretty()```
{ "name" : "funky",
"tracks" : [{"artist" : "abba", "track" : "Chiquitita" }]
}

####  $pull
```
db.playlists.insertOne(
{name: "funky",
tracks : [
{ artist:"queen",track:"Liar"},
{artist:"abba",track:"Chiquitita"},
{ artist:"queen",track:"Under Pressure"},
{artist:"AC/DC",track:"Hells Bells"},
]})
```

####  queen 을 모두 빼라 (index로 빼는 pop과 다르게 쉽게 빼낼 수 잇음)
```
db.playlists.updateOne({name:"funky"},
{ $pull : { tracks : { artist: "queen" }}})

db.playlists.find({},{_id:0}).pretty()
{ "name" : "funky",
  "tracks" : [{"artist" : "abba", "track" : "Chiquitita" },
  {"artist" : "AC/DC", "track" : "Hells Bells" }]}
```

####  $pullAll
```
db.playlists.drop()
db.playlists.insertOne(
{name: "funky",
tracks : [
{ artist:"queen",track:"Liar"},
{artist:"abba",track:"Chiquitita"},
{ artist:"queen",track:"Under Pressure"},
{artist:"AC/DC",track:"Hells Bells"},
]})
```

####  artist 뿐만 아니라 track 까지 볼때 pullAll 사용
```
db.playlists.updateOne({name:"funky"},
{ $pullAll : {"tracks" : [
{artist:"abba",track:"Chiquitita"},
{artist:"queen",track:"Under Pressure"}
]
}})

db.playlists.find({},{_id:0}).pretty()
{ "name" : "funky",
  "tracks" : [{"artist" : "queen", "track" : "Liar" },
  {"artist" : "AC/DC", "track" : "Hells Bells" }]}
```

####  $addToSet (중복을 체크하고, 이미 있다면 등록하지 않음)
```
db.sports.insertOne( {name: "fives",
players : ["Ravi", "Jon", "Niyati", "John" ]})

db.sports.find({name:"fives"},{_id:0})
{ "name" : "fives", "players" : [ "Ravi", "Jon", "Niyati",
"John" ] }
- Ravi is not added, as he is already there

db.sports.updateOne({name:"fives"},{ $addToSet : { players: "Ravi"}})
- { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 0 }
- Kim is added as they are not in the array currently

db.sports.updateOne({name:"fives"},{ $addToSet : { players: "Kim"}})
- { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }

db.sports.find({name:"fives"},{_id:0})
- { "name" : "fives", "players" : [ "Ravi", "Jon", "Niyati", "John", "Kim" ] }
```

####  Updating, Locking and Concurrency.
```db.lockdemo.insertOne({ _id: "Tom", votes: 0 } )```
- { "acknowledged" : true, "insertedId" : "Tom" }

####  아래 2개를 동시에! 입력
- 한쪽은 업데이트가 됬고 다른 한쪽은 되지 않음
```db.lockdemo.updateOne({_id:"Tom",votes:0}, {$inc:{votes:1}})```
- { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
```db.lockdemo.findOne({_id:"Tom"})```
- { "_id" : "Tom", "votes" : 1 }

```db.lockdemo.updateOne({_id:"Tom",votes:0},{$inc:{votes:1}})```
- { "acknowledged" : true, "matchedCount" : 0, "modifiedCount" : 0 }
```db.lockdemo.findOne({_id:"Tom"})```
{ "_id" : "Tom", "votes" : 1 }

- This is True even if updates come in parallel from different
- clients - all updates to a single document are serialized.

####  조건을 제거하자. 그럼 둘다 업데이트된다.
```db.lockdemo.updateOne({_id:"Tom"}, {$inc:{votes:1}})```
- { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
```db.lockdemo.findOne({_id:"Tom"})```
- { "_id" : "Tom", "votes" : 3 }

```db.lockdemo.updateOne({_id:"Tom"}, {$inc:{votes:1}})```
- { "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
```db.lockdemo.findOne({_id:"Tom"})```
{ "_id" : "Tom", "votes" : 3 }

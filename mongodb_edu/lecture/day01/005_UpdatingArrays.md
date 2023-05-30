####  Update Specific Element
```
> db.a.drop()
> db.a.insertOne({ name: "Tom", hrs: [ 0, 0, 1, 0, 0 ] } )
> db.a.find({},{_id:0})
```
{ "name" : "Tom", "hrs" : [ 0, 0, 1, 0, 0 ] }

//Add an extra hour to Tom on Thursday (index는 0부터 시작이므로 4번째 index를 증가)
```db.a.updateOne({name: "Tom"},{ $inc : { "hrs.3" : 1 }})```
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }

```db.a.find({},{_id:0})```
{ "name" : "Tom", "hrs" : [ 0, 0, 1, 1, 0 ] }

####  Update Matched Element
```db.a.drop()```
//Tom works one hour on wednesday
```db.a.insertOne({ name: "Tom", hrs: [ 0, 0, 1, 0, 0 ] } )```
//Find the first day tom has no hours and add two to that day

// 1 미만의 것들 중에서 .$는 딱 1개의 element만 바꾸라는 말
```db.a.updateOne({name:"Tom",hrs:{$lt:1}},{ $inc: { "hrs.$": 2 }})```
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }

```db.a.find({},{_id:0})```
{ "name" : "Tom", "hrs" : [ 2, 0, 1, 0, 0 ] }


####  Update All Elements
```
> db.a.drop()
> db.a.insertOne({ name: "Tom", hrs: [ 0, 0, 1, 0, 0 ] } )
```

//Give Tom two hore hours work every day ($[] : 전체 변경)
```db.a.updateOne({name:"Tom"},{$inc : { "hrs.$[]" : 2 }})```
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }

```db.a.find({},{_id:0})```
{ "name" : "Tom", "hrs" : [ 2, 2, 3, 2, 2 ] }

####  Update All Matched Elements
```
> db.a.drop()
> db.a.insertOne({ name: "Tom", hrs: [ 0, 0, 1, 0, 0 ] } )
// Find a week where tom has a day with no hours (query)
// for each day Tom has no hours and add 2 to those days
// (arrayFilter) - assume there might be multiple records for Tom
// so we can cannot use JUST arrayfilters.
> db.a.updateOne({name:"Tom",hrs:{$lt:1}},
{$inc : { "hrs.$[nohrs]" : 2 }},
{"arrayFilters": [ {"nohrs":{"$lt":1}}]}) -- 이 조건에 해당하는 것만 변경
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }

> db.a.find({},{_id:0})
{ "name" : "Tom", "hrs" : [ 2, 2, 1, 2, 2 ] }
```

####  $each (array에 array를 추가)
```
> db.a.drop()
//Set Toms hours for Monday to Wednesday
> db.a.insertOne({ "name" : "Tom", "hrs" : [ 4, 1, 3 ] })
//Add hours for Thursday and Friday Incorrectly
> db.a.updateOne({name:"Tom"}, {$push:{hrs:[2,9]}})
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
> db.a.find({},{_id:0})
{ "name" : "Tom", "hrs" : [ 4, 1, 3, [ 2, 9 ] ] }
```

####  $each
- 넣고 정렬
```
> db.a.drop()
> db.a.insertOne({ "name" : "Tom", "hrs" : [ 4, 1, 3 ] })
//Add hours for Thursday and Friday but then rearrange the work to
make the early days of the week the longest
> db.a.updateOne({name:"Tom"},
{$push:{hrs: {$each: [2,9], $sort: -1}}})
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
>db.a.find({},{_id:0})
{ "name" : "Tom", "hrs" : [ 9, 4, 3, 2, 1 ] }
```

####  $each
- 넣고 slice
```
> db.a.drop()
> db.a.insertOne({ "name" : "Tom", "hrs" : [ 4, 1, 3 ] })
//Tom wants a day off - so Add Thursday and Friday, then sort to
make the longest days first and give him Friday off
> db.a.updateOne({name: "Tom"},
{$push: {hrs: {$each : [2,9], $sort: -1, $slice: 4}}})
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
> db.a.find({},{_id:0})
{ "name" : "Tom", "hrs" : [ 9, 4, 3, 2 ] }
```

####  Expressive Updates (현재 document 안의 값을 이용해서 update)
```
db.shapes.updateMany(
{ shapename: {$in : ["rectangle","square"]} }, -- 여기해당하는 데이터만
[ { $set: { area: { $multiply:[ "$width","$height" ] } } } ] -- 이렇게 update 수행
)
```

####  Upsert (있으면 update, 없으면 insert)
```
> db.players.drop()
> db.players.updateOne({name:"joe"},{$inc:{games:1}})
{ "acknowledged" : true, "matchedCount" : 0, "modifiedCount" : 0 }
>//Nothing found to update - we have no player "joe"
>db.players.updateOne({name:"joe"},{$inc:{games:1}},{upsert:true})
{ "acknowledged" : true,"matchedCount" : 0, "modifiedCount" : 0, "upsertedId"ObjectId("6093b3a9c07da") }
>db.players.find()
{ "_id" : ObjectId("6093b419c07da"), "name" : "joe", "games" : 1 }
>//Document created because of upsert
>db.players.updateOne({name:"joe"},{$inc:{games:1}},{upsert:true})
{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
> db.players.find()
{ "_id" : ObjectId("6093b419c07da"), "name" : "joe", "games" : 2 }
>//Document modified as exists already upsert does nothing here.
```

####  findOneAndUpdate() (바뀌기 전의 document 또는 후의 document를 돌려주거나)

####  findOneAndUpdate() : 바꾸기 '전'의 데이터를 리턴
- 아래 2개를 동시에 실행!
- 바뀌기 전의 document
```
MongoDB> db.s.findOneAndUpdate({_id:"a"}, {$inc:{c:1}}, {upsert:true}) //Thread 1
```
null

- 바뀐 후의 document
```
MongoDB> db.s.findOneAndUpdate({_id:"a"}, {$inc:{c:1}}, {upsert:true}) //Thread 2
```
{_id:"a", c:1 }

- findOneAndUpdate() : 바뀐 '후'의 데이터를 리턴
```
MongoDB> db.s.findOneAndUpdate({_id:"a"}, {$inc:{c:1}}, {upsert:true, returnNewDocument: true}) //Thread 1
```
{_id:"a", c:1 }

- 바뀐 후의 document
```
MongoDB> db.s.findOneAndUpdate({_id:"a"}, {$inc:{c:1}}, {upsert:true, returnNewDocument: true}) //Thread 2
```
{_id:"a", c:2 }
#### create index
```db.listingsAndReviews.createIndex({number_of_reviews:1})```
number_of_reviews_1

#### select index
```db.listingsAndReviews.getIndexes()```

#### index size
```db.listingsAndReviews.stats().indexSizes```

#### collection 정보
```db.listingsAndReviews.stats()```

#### Index Options
- index unique constraint
- NULL is a value (두번째 NULL부터 중복 제약에 걸림)
```db.a.createIndex({custid: 1}, { unique: true})```

#### Partial Index
- 특정 조건을 만족(partialFilterExpression)시키는 document에 대해서만 index를 걸겠다
- archived: false : archived 데이터(오래된 데이터)가 아닌 최근에 사용된 데이터에 한정해서 걸겠다는 의미
```db.orders.createIndex( { customer: 1, store: 1 },{ partialFilterExpression: { archived: false } })```

#### Sparse Index
- 필드가 존재하는 경우에 index를 걸겠다 (몽고DB는 필드가 필수가 아님 - document마다)
```db.scores.createIndex( { score: 1 } , { sparse: true } )```


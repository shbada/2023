-- insert
db.customers.insertOne({ _id : "bob@gmail.com", name: "Robert Smit
h", orders: [], spend: 0, lastpurchase: null })

db.customers.insertOne({ _id : "bob@gmail.com", name: "Bobby Smith
", orders: [], spend: 0, lastpurchase: null })

db.customers.insertOne({name: "Andi Smith", orders: [], spend: 0,
    lastpurchase: null })

-- insertMany
-- 1000 Network Calls
let st = ISODate()
for(let d=0;d<1000;d++) {
    db.orders.insertOne({ product: "socks", quantity: d})
}
print(`${ISODate()-st} milliseconds`)

-- 1 Network call, same data
st = ISODate()
let docs = []
for(let d=0;d<1000;d++) {
    docs.push({ product: "socks", quantity: d
})}
db.orders.insertMany(docs)
print(`${ISODate()-st} milliseconds`)

-- ordered
let friends = [ {_id: "joe"}, {_id: "bob"}, {_id: "joe" }, {_id: "jen" } ]
db.collection1.insertMany(friends)

db.collection2.insertMany(friends,{ordered:false})

db.collection1.find() -- 2개
db.collection2.find() -- 3개

-- findOne
db.customers.insertOne({ _id : "tim@gmail.com", name: "Timothy", orders: [], spend: 0, lastpurchase: null })

-- 조건 : value or object(dictionary)
db.customers.findOne({ _id : "tim@gmail.com" })
db.customers.findOne({ spend: 0 })
db.customers.findOne({ spend: 0 , name: "Timothy" })
db.customers.findOne({ name: "timothy" }) // No match
db.customers.findOne({ spend: "0" }) // No Match

-- Projection : choosing the fields to return
db.customers.insertOne({ _id : "ann@gmail.com", name: "Ann", orders: [], spend: 0, lastpurchase: null })

db.customers.findOne({ name: "Ann" })
db.customers.findOne({ name:"Ann" }, {name: 1, spend: 1}) -- name, spend 필드만 조회하겠다
db.customers.findOne({ name:"Ann" }, {name: 0, orders:0})
db.customers.findOne({ name:"Ann" },{name: 0, orders:1})
-- MongoServerError: Cannot do inclusion on field orders in exclusion projection
db.customers.findOne({ name:"Ann" },{_id: 0, name:1})



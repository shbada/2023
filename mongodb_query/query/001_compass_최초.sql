db
test
show collections
db.employees.find({})
var employee = { "name" : "Jon", "hungry" : true, "title" : "director" }
db.employees.insertOne(employee)
{ acknowledged: true,
  insertedId: ObjectId("646e0c7c5da19d53c6e5f80a") }
db.employees.find({hungry:true})
{ _id: ObjectId("646e0c7c5da19d53c6e5f80a"),
  name: 'Jon',
  hungry: true,
  title: 'director' }
show collections
employees
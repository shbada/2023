> redis -cli --clster create localhost:7000 localhost:7001 localhost:7002 localhost:7003 localhost:7004 localhost:7005 --cluster-replicas 1

# 7000
> redis-cli -p 7000
> cluster nodes
> set aa bb
> set aaa dd
  > MOVED 10439 127.0.0.1:7071

# 7001 (aaa key를 담당하는 마스터)
> set aaa 111

# 7003 (replica - set 불가능)
> set aaa 333
  > MOVED 10439 127.0.0.1:7001 (마스터 노드로 가라는 뜻)
> get aaa
  > MOVED 10439 127.0.0.1:7001

> readonly
> get aaa
  > "111" (정상가능)

# 7001 종료
# 7000 들어가기
> cluster nodes
> 7003 이 master가 되었음 (7001이 커버하던것들이 모두 7003으로 넘어옴)

# 7001 다시 살아남
# 7001 : slave
# 7003 : 그대로 master

# node 추가 : 7006
# redis-cli --cluster add-node localhost:7006 localhost:7001(기존노드는 아무거나)
# cluster node (node 추가는 master로 추가됨)
# master 4개(7002, 7000, 7003, 7006), slave(7004, 7005)

# node 추가 : 7007
# redis-cli --cluster add-node localhost:7006 localhost:7006 --cluster-slave 대상지정할수있음, 안하면 7006의 slave
# cluster node
# 7007 : slave로 등록됨(어떤 master node인지? -> 7006)
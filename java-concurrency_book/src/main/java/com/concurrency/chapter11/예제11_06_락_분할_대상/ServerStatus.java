package com.concurrency.chapter11.예제11_06_락_분할_대상;

import java.util.*;

import net.jcip.annotations.*;

/**
 * 데이터베이스에 로그인돼있는 사용자 목록을 관리하고,
 * 그와 함께 현재 실행중인 데이터베이스 쿼리가 어떤 것인지도 관리한다.
 *
 * 사용자가 로그인하고 로그아웃하는 경우,
 * 그리고 쿼리가 실행을 시작하고 실행을 끝마치는 각 순간마다
 * 각자 add 또는 remove 메서드를 사용해 상태 정보가 업데이트된다.
 *
 * 사용자 정보와 실행중인 쿼리 정보는 완전히 독립적인 정보다.
 * 따라서 ServerStatus는 기능에 문제가 생기지 않는 범위에서 심지어는 두 개의 클래스로 분리해 구현할 수도 있다.
 *
 * ServerStatus 라는 락 하나를 갖고 users 변수와 queries 변수를 한번에 동기화하고있다.
 */

/**
 * ServerStatusBeforeSplit
 * <p/>
 * Candidate for lock splitting
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class ServerStatus {
    @GuardedBy("this") public final Set<String> users;
    @GuardedBy("this") public final Set<String> queries;

    public ServerStatus() {
        users = new HashSet<String>();
        queries = new HashSet<String>();
    }

    public synchronized void addUser(String u) {
        users.add(u);
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    public synchronized void removeQuery(String q) {
        queries.remove(q);
    }
}
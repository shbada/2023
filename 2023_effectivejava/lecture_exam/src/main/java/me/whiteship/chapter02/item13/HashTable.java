package me.whiteship.chapter02.item13;

public class HashTable implements Cloneable {

    // final 선언 못한다.
    // 가변객체는 객체를 다시 설정해야할 수도 있는데
    // clone() 안에 result.buckets = new Entry[this.buckets.length];\
    // 굳이 clone() 메서드를 위해 final를 빼야한다.
    private Entry[] buckets = new Entry[10];

    private static class Entry {
        final Object key;
        Object value;
        Entry next;

        Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public void add(Object key, Object value) {
            this.next = new Entry(key, value, null);
        }

//        public Entry deepCopy() {
//            return new Entry(key, value, next == null ? null : next.deepCopy());
//        }

        public Entry deepCopy() {
            Entry result = new Entry(key, value, next);

            // node 가 너무 많으면 StackOverflowError 발생
            for (Entry p = result ; p.next != null ; p = p.next) {
                p.next = new Entry(p.next.key, p.next.value, p.next.next);
            }
            return result;
        }
    }

    /**
     * TODO hasTable -> entryH[],
     * TODO copy -> entryC[]
     * TODO entryH[0] == entryC[0]
     *
     * @return
     */
//    @Override
//    public HashTable clone() {
//        HashTable result = null;
//        try {
//            result = (HashTable)super.clone();
//            result.buckets = this.buckets.clone(); // p82, shallow copy 라서 위험하다.
//            return result;
//        } catch (CloneNotSupportedException e) {
//            throw  new AssertionError();
//        }
//    }

    /**
     * TODO hasTable -> entryH[],
     * TODO copy -> entryC[]
     * TODO entryH[0] != entryC[0]
     *
     * @return
     */
    @Override
    public HashTable clone() {
        HashTable result = null;
        try {
            result = (HashTable)super.clone();
            result.buckets = new Entry[this.buckets.length];

            /*
            혹시라도 clone() 메서드 안에서 이렇게 재정의할 수 있는 메서드를 사용하지말라.
            하위클래스에서 재정의하면 동작이 변경될 수 있다.
            객체가 생성되는 과정에 끼어드는 행위라서 그렇다.
             */
//            result.buckets = createNewBuckets();

            for (int i = 0 ; i < this.buckets.length; i++) {
                if (buckets[i] != null) {
                    result.buckets[i] = this.buckets[i].deepCopy(); // p83, deep copy
                }
            }
            return result;
        } catch (CloneNotSupportedException e) {
            throw  new AssertionError();
        }
    }

//    protected Entry[] createNewBuckets() {
//        throw new AssertionError();
//    }

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        Entry entry = new Entry(new Object(), new Object(), null);
        hashTable.buckets[0] = entry;
        HashTable clone = hashTable.clone();

        // 얕은 복사 : true, true
        // 깊은 복사 : true, false
        System.out.println(hashTable.buckets[0] == entry);
        System.out.println(hashTable.buckets[0] == clone.buckets[0]);
    }
}

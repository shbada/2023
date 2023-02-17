package com.concurrency.chapter03.예제3_09_로컬변수_스택한정;

/**
 * TreeSet 클래스의 인스턴스를 만들고, 만들어진 인스턴스에 대한 참조를 animals 변수에 보관한다.
 * - 아래 메서드 기준
 * TreeSet 인스턴스에 대한 참조는 정확하게 하나만 존재하고, 로컬 변수에 보관하여 스레드 안전하다. (스레드 스택에 안전하게 한정되어있다.)
 *
 * 만약 TreeSet 인스턴스에 대한 참조를 외부에 공개한다면 스택 한정 상태가 깨질 수 밖에 없다.
 */
public class loadTheArk {
//    public int loadTheArk(Collection<Animal> candidates) {
//        SortedSet<Animal> animals;
//        int numPairs = 0;
//        Animal candidate = null;
//
    //    animlas 변수는 메서드에 한정되어 있으며, 유출되서는 안된다.
//        animals = new TreeSet<Animal>(new SpeciesGenderComparator());
//        animals.addAll(candidates);
//        for (Animal a : animals) {
//            if (candidate == null || !candidate.isPotentialMate(a))
//                candidate = a;
//        } else {
//            ark.load(new AnimalPair(candidate, a));
//            ++numPairs;
//            candidate = null;
//        }
//
//        return numPairs;
//    }
}

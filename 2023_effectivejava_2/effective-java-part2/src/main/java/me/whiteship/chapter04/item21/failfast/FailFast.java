package me.whiteship.chapter04.item21.failfast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ConcurrentModificationException
 * 현재 바뀌면 안되는것을 수정할때 발생하는 예외
 *
 * - 멀티 스레드가 아니라 싱글 스레드 환경에서도 발생할 수 있다.
 * ex) 이터레이터를 사용해 콜렉션을 순회하는 중에 콜렉션을 변경하는 경우
 */
public class FailFast {

    public static void main(String[] args) {
        // 수정 불가능 리스트 (remove() 호출시 UnsupportedOperationException 발생)
//        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // 수정 가능한 리스트
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        // 이터레이터로 콜렉션을 순회하는 중에 Collection의 remove를 사용한다면...
        // foreach : 내부적으로 이터레이터 순회
//        for (Integer number : numbers) {
//            if (number == 3) {
                // ConcurrentModificationException 발생
//                numbers.remove(number);
//            }
//        }

        // 이터레이터의 remove 사용하기
        // 이때는 ConcurrentModificationException 발생하지 않고 정상 수행 가능하다.
//        for (Iterator<Integer> iterator = numbers.iterator(); iterator.hasNext();) {
//            Integer integer = iterator.next();
//            if(integer == 3) {
//                iterator.remove();
//            }
//        }

//        // 인덱스 사용하기 (이터레이터 사용 안했으므로 정상 수행 가능)
//        for (int i = 0; i < numbers.size() ; i++) {
//            if (numbers.get(i) == 3) {
//                numbers.remove(numbers.get(i));
//            }
//        }

        // removeIf 사용하기
//        numbers.removeIf(number -> number == 3);

        // 출력
        numbers.forEach(System.out::println);
    }


    public void example(List<String> words) {
        words.forEach(word -> System.out.println(word));
    }
}

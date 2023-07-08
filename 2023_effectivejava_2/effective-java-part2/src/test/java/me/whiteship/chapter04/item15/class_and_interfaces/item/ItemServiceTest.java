//package test.java.me.whiteship.chapter04.item15.class_and_interfaces.item;
//
//import me.whiteship.chapter04.item15.class_and_interfaces.member.MemberService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.testng.Assert.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//class ItemServiceTest {
//
//    @Mock // 가짜객체
//    MemberService memberService;
//
//    @Test
//    void itemService() {
//        ItemService service = new ItemService(memberService);
//        assertNotNull(service);
//        assertNotNull(service.getMemberService());
//    }
//
//}
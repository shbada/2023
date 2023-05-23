package me.whiteship.inflearnthejavatest.study;

import lombok.extern.slf4j.Slf4j;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import me.whiteship.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class) // @Mock 어노테이션을 적용해줄 Extension
@ActiveProfiles("test")
@Testcontainers /* testContainers */
@Slf4j
// 우리가 구현한 ApplicationContextInitializer 구현체를 지정한다.
@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class)
class StudyServiceTest {

    @Mock MemberService memberService;

    @Autowired StudyRepository studyRepository;

    // postgres 가 띄어져있는 동적 port를 알아낼 수 있다.
    @Value("${container.port}") int port;

    // static 이므로 모든 테스트에서 공유
    // static이 아니면 각 테스트마다 컨테이너를 띄었다가 삭제했다가 반복 +
    @Container
    static DockerComposeContainer composeContainer =
            // docker-compose.yml 파일 위치
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    // 5432 port를 exposed 하겠다
                    // port 정보를 Spring Environment 에 저장되고, 이를 꺼내서 사용할 수 있다.
            .withExposedService("study-db", 5432);

    @Test
    void createNewStudy() {
        System.out.println("========");
        System.out.println(port);

        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        Study study = new Study(10, "테스트");

        // memberService.findById(1L) 호출이 되면 이의 결과를 Optional.of(member)로 셋팅하라
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        // 어떤 파라미터로 받던 리턴!
//        given(memberService.findById(any())).willReturn(Optional.of(member));

        // When
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(1L, study.getOwnerId());
        // 1번 호출이 되어야한다.
        verify(memberService, times(1)).notify(study);
        // 한번도 호출이 안되야한다.
        verify(memberService, never()).validate(any());
        then(memberService).should(times(1)).notify(study);

        // 더이상 호출되는게 없다.
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        assertNull(study.getOpenedDateTime());

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }

    /**
     * ApplicationContextInitializer (springboot가 아닌, spring core 가 제공)
     * ApplicationContextInitializer 구현체를 생성해서 프로퍼티 추가
     *
     *
     */
    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            // TestPropertyValues 정의
            // key=value 형식의 문자열
            // context의 Environment 에 적용하겠다 라는 의미
            TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432)) // 5432 에 매핑된 port
                    .applyTo(context.getEnvironment());
        }
    }

}
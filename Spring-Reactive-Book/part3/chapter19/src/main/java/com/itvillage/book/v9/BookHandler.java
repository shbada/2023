package com.itvillage.book.v9;

import com.itvillage.exception.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.net.URI;

@Slf4j
@Component("BookHandlerV9")
public class BookHandler {
    private final BookMapper mapper;
    private final BookValidator validator;
    private final BookService bookService;

    public BookHandler(BookMapper mapper, BookValidator validator, BookService bookService) {
        this.mapper = mapper;
        this.validator = validator;
        this.bookService = bookService;
    }

    public Mono<ServerResponse> createBook(ServerRequest request) {
        return request.bodyToMono(BookDto.Post.class)
                .doOnNext(post -> validator.validate(post))
                .flatMap(post -> bookService.createBook(mapper.bookPostToBook(post)))
                .flatMap(book -> ServerResponse
                        .created(URI.create("/v9/books/" + book.getBookId()))
                        .build())
                /*
                에러가 발생했을대 에러 이벤트를 Downstream으로 전파하지 않고, 대체 Publisher를 통해 에러 이벤트에 대한 대체 값을 emit하거나,
                발생한 에러 이벤트를 래핑한 후에 다시 에러 이벤트를 발생시키는 역할을 한다.
                 */
                // 첫번째 파라미터 : 처리할 Exception 타입
                // 두번째 파라미터 : 대체할 Publisher의 Sequence
                .onErrorResume(BusinessLogicException.class, error -> ServerResponse
                            .badRequest()
                            .bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST,
                                                            error.getMessage())))
                // BusinessLogicException 외의 Exception 발생일 경우
                .onErrorResume(Exception.class, error ->
                        ServerResponse
                                .unprocessableEntity()
                                .bodyValue(
                                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                                        error.getMessage())));
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {
        final long bookId = Long.valueOf(request.pathVariable("book-id"));
        return request
                .bodyToMono(BookDto.Patch.class)
                .doOnNext(patch -> validator.validate(patch))
                .flatMap(patch -> {
                    patch.setBookId(bookId);
                    return bookService.updateBook(mapper.bookPatchToBook(patch));
                })
                .flatMap(book -> ServerResponse.ok()
                                        .bodyValue(mapper.bookToResponse(book)))
                .onErrorResume(error -> ServerResponse
                        .badRequest()
                        .bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST,
                                error.getMessage())));
    }

    public Mono<ServerResponse> getBook(ServerRequest request) {
        long bookId = Long.valueOf(request.pathVariable("book-id"));

        return bookService.findBook(bookId)
                        .flatMap(book -> ServerResponse
                                .ok()
                                .bodyValue(mapper.bookToResponse(book)))
                        .onErrorResume(error -> ServerResponse
                                .badRequest()
                                .bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST,
                                        error.getMessage())));
    }

    public Mono<ServerResponse> getBooks(ServerRequest request) {
        Tuple2<Long, Long> pageAndSize = getPageAndSize(request);
        return bookService.findBooks(pageAndSize.getT1(), pageAndSize.getT2())
                .flatMap(books -> ServerResponse
                        .ok()
                        .bodyValue(mapper.booksToResponse(books)));
    }

    private Tuple2<Long, Long> getPageAndSize(ServerRequest request) {
        long page = request.queryParam("page").map(Long::parseLong).orElse(0L);
        long size = request.queryParam("size").map(Long::parseLong).orElse(0L);
        return Tuples.of(page, size);
    }
}

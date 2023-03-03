## HTTP 요청과 응답

### request, response 
- WEB Client <-> Web Container > Web Component

### HTTP
- 웹 Request와 Response의 기본 구조를 이해하고 내용을 확인할 수 있어야한다.
- Request
  - Request Line : Method, Path, HTTP Version
  - Headers
    - 요청을 처리하는 방식, 응답을 생성하는 타입이 달라지기도 한다.
  - Message Body
    - POST, PUT 과 같은 Body 가 동반되는 요청이 있다.
- Response
  - Status Line : HTTP Version, Status Code, Status Text
    - HTTP Version 이 제일 앞에 나온다.
    - Status Code 라는 응답 코드가 나온다. 
  - Headers
  - Message Body

```
> http -v ":8080/hello?name=Spring"

GET /hello?name=Spring HTTP/1.1   -- GET 메서드로 요청, Path, HTTP 버전 순서로 노출된다.
Accept: */*   -- 다른 설정을 하지 않으면 모든 Content 타입을 수용하겠다는 의미 
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/3.2.1

HTTP/1.1 200  -- HTTP 버전, 상태코드(Status Code)
Connection: keep-alive  -- Header
Content-Length: 12  -- Header
Content-Type: text/plain;charset=UTF-8    -- Plain Text 의미, 인코딩 방법
Date: Thu, 01 Dec 2022 01:45:15 GMT
Keep-Alive: timeout=60

Hello Spring  -- Body
```
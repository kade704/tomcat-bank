# Tomcat Bank
Apache Tomcat + Java Servlet + JSP + OracleDB 기반 은행 웹 애플리케이션

## 샘플 계정 목록
| 계정 | 비밀번호 |
| --- | --- | 
| guest | guest1234 |
| user | user1234 |

## POST 요청 종류

| 경로 | 기능 | 파라미터 |
| --- | --- | --- |
| `/user/signup` | 회원가입 | `csrfToken`, `id`, `password`, `passwordConfirm`, `email`, `fullName`, `phoneNumber`, `age` |
| `/user/login` | 로그인 | `csrfToken`, `id`, `password` |
| `/user/logout` | 로그아웃 | `csrfToken` |
| `/account/create` | 계좌 생성 | `csrfToken`, `branchId` |
| `/account/delete` | 계좌 삭제 | `csrfToken`, `accountId` |
| `/transaction/deposit` | 입금 처리 | `csrfToken`, `accountId`, `amount` |
| `/transaction/withdraw` | 출금 처리 | `csrfToken`, `accountId`, `amount` |
| `/transaction/transfer` | 이체 처리 | `csrfToken`, `accountId`, `accountIdTransfer`, `amount` |

## 적용된 보안 기능

### 공통 보안
- SQL Injection 방어를 위해 PreparedStatement 사용.

### CSRF 방어
- 로그인 성공 시 CSRF 토큰을 새로 발급하여 세션에 저장.
- 세션의 `csrfToken`과 요청의 `csrfToken`(또는 `X-CSRF-TOKEN` 헤더)을 비교.

### XSS 방어
- `XssFilter` + `XssRequestWrapper`를 통해 모든 입력값에 대해 문자 필터링 수행.
- `<`, `>`, 따옴표, 괄호 등의 문자를 치환.

### 세션/쿠키 보안 설정
- `web.xml`의 `session-config`에서 세션 쿠키에 `HttpOnly`, `Secure`를 적용.
- 로그인 시 기존 세션을 무효화 후 재생성하여 세션 고정 위험을 감소.

### 인증/인가
- 주요 POST 요청에서 로그인 여부(`userId` 세션) 확인 후 미인증 요청을 차단.
- 계좌 삭제/입출금/이체 시 본인 계좌 소유 여부를 검증.

### 입력값 검증
- 금액 범위(0 초과, 1,000,000 이하) 검증을 수행.

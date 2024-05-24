# API Practice

### 예외처리
* 예외처리는 Service 계층에서 진행
* Throw 된 예외는 공통의 클래스에서 처리
 
### 유효성처리
* Controller : @Valid 를 사용해서 진행
* Service : 비즈니스로직 관련 유효성 처리 
     
### 기타
* DTO inner class 처리
* Command 와 Query는 분리하여 진행
 
### 테스트
* 도메인 별 Controller, Service **유닛테스트** 작성
* 도메인 별 Service **통합테스트** 작성
* 테스트 케이스
  1. 성공 시나리오
  2. 실패 시나리오

### 커밋 컨벤션
* feat: 새로운 기능의 추가
* remove: 파일, 코드 삭제 또는 기능 삭제
* fix: 버그 수정
* docs: 문서 수정
* style: 스타일 관련 수정 (코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
* refactor: 코드 리팩토링
* test: 테스트 관련 코드
* chore: 빌드 관련 수정 (application.properties, build.gradle, .gitignore ..)

### REF
[SpringSecurity](https://docs.spring.io/spring-security/reference/servlet/architecture.html)

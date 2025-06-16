# 레거시 DSP 시스템에 CDC를 적용하여 대량의 데이터를 마이그레이션 하는 서비스 구현
![image](https://github.com/user-attachments/assets/d09fb333-f6da-4b1a-8d95-e01e289599c1)
※CDC(Change Data Capture) : 소스 데이터 저장소에서 데이터의 커밋도니 상태 변경을 추출하는 것으로 크게 "배치기반CDC", "하이브리드CDC", "지속적인CDC" 3가지 전략으로 나누어질 수 있음

### 상황 예시
- 신규 DB개발로 인한 광고데이터 마이그레이션
- 기존의 4가지 그룹(사용자, 캠페인, 광고그룹, 키워드) -> 3가지 그룹(사용자, 캠페인, 키워드)로 데이터 마이그레이션 작업 진행
- 기존의 사용 DB(Mysql)와 기존 사용 messageQueue(RabbitMQ)에서 신규 사용 DB(PostgreSQL)와 messageQueue(kafka)로 데이터 이관 작업
- 마이그레이션 이관 작업
  - 마이그레이션 신청 시작
  - 마이그레이션 동의 기간 + 레거시 DSP 광고데이터 생성, 수정, 삭제 가능(한달)
  - 마이그레이션 신청 종료
  - 마이그레이션 레거시 DSP 광고데이터 생성, 수정, 삭제 불가(주말 이틀)
  - 신규 DSP 오픈
  - 사용자들이 마이그레이션도니 데이터 확인 및 잘못된 데이터 재처리(일주일)
  - 신규 DSP기준으로 광고 노출

- 마이그레이션 규모(더미데이터)
  - 사용자 1000명, 캠페인1000건, 광고그룹 1,000,000건, 키워드 100,000,000건으로 진행
  - 하루 평균 약 10,000,000건의 데이터 변경 발생(생성, 수정, 삭제)
  - 약 100,000,000건의 광고 데이터를 변경 사항 반영과 함께 마이그레이션 진행
  - 마이그레이션 환경은 6코어, memory32GB, disk 1TB 환경에서 진행

 - 요구사항
   - CRUD가 가능한 레거시 DSP
   - CDC로 변경사항을 전달하는 레거시 DSP
   - 레거시 DSP의 CDC를 통해 신규 DSP에 변경사항 반영
   - 마이그레이션 동의한 사용자 광고데이터만 이관
   - 레거시 DSP의 광고데이터를 변경 없어도 마이그레이션
   - 마이그레이션 재처리 및 진행상항 확인
   - 1억건을 한달 내에, 하루 천만 건을 하루 내에 마이그레이션

- 요구사항 반영 및 진행 과정
  1. 레거시 DSP Application을 통해 광고데이터 CRUD API 개발
  2. 레거시 DSP의 변경사항을 RabbitMQ에 메시지로 전달
  3. Gradual Migration Application을 통한 점진적 마이그레이션 개발
  4. Internal Migration Application 동의 API 개발
  5. Batch Migration Application을 통한 배치성 마이그레이션 개발
  6. Internal Migration Application 재처리, 진행상황 API 개발
  7. 메시지 비동기 처리, Batch Insert, 배치성 로직 수정을 통한 성능 개선

- 사용 기술
  1. Spring Boot
  2. Docker
  3. Mysql
  4. PostgreSQL
  5. RabbitMQ
  6. Kafka
  7. Multi module
  8. AbstractAggregateRoot Class를 이용하여 Domain Event발행(DDD)
  9. 이종 DB 연결
  10. 트랜잭션 분리
  11. @Async 어노테이션을 통한 비동기 처리
  12. Batch Insert
  13. Generic을 이용하여 추상화
  14. 테스트 코드를 작성하며 기능 구현 (TDD)

## 테스트 진행
- 캠페인 마이그레이션 수행 시간
- --page-size:1, 259s동안 1208건 수행 -> 초당 5건정도 수행
- --page-size:10, 99s동안 4310건 수행 -> 초당 44건정도 수행
- --page-size:100, 61s동안 20000건 수행 -> 초당 327건정도 수행
- --page-size:1000, 116s동안 85783건 수행 -> 초당 739건정도 수행

- --[async 적용 후]
- --page-size: 1000, 85783건, 173s -> 484건/s
- 
- --[batch insert 적용 후]
- --page-size: 1000, 85783건, 97s -> 884건/s

- --[배치 마이그레이션 로직 수정 후]
- --page-size: 1000, 85783건, 52s -> 1649건/s

- 키워드 마이그레이션
- --page-size: 1000, 2000건, 21s -> 95건/s

- --[async 적용 후]
- --page-size: 1000, 38000건, 483s -> 78건/s

- --[batch insert 적용 후]
- --page-size: 1000, 36000건, 17s -> 200건/s

- --[배치 마이그레이션 로직 수정 후]
- --page-size: 1000, 829077건, 199s -> 4166건/s -> 1억건 수행 시 대략 6.6시간

### 사용 기술 스택
- Java21
- Spring Boot 3.X
- Mysql 8
- PostgreSQL 16
- RabbitMQ 3.X
- Kafka 3.X
- IntelliJ Ultimate
- Docker 28.X

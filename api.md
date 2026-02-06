# API 목록
(미완성입니다)

| **구현 여부** | **메소드** | **URL** | **설명** | **쿼리파라미터** | **요청(헤더)** | **요청(바디)** | **응답** |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Auth |  |  |  |  |  |  |  |
|  | POST | {baseUrl}/auth/login | 로그인 |  |  | {”email”: “name@domain.com”, “password”: “password”} | {”accessToken”: “…”} |
| User |  |  |  |  |  |  |  |
|  | POST | {baseUrl}/users | 회원가입 |  |  | {”name”: “홍길동”,“email”: “name@domain.com”,“password”: “password”} |  |
|  | GET | {baseUrl}/users/me | 내 정보 조회 |  | {”Authorization”: “Bearer …”} |  | {”id”: 1, ”name”: “홍길동”} |
| Mission |  |  |  |  |  |  |  |
|  | GET | {baseUrl}/missions | 기간 내 미션 조회 | startDate=2020-01-01&endDate=2020-01-02 | {”Authorization”: “Bearer …”} |  | {”missions”: [{”id”: 11, ”name”: “책 한 권 읽기”, “date”: “2020-01-01” “accomplished”: true, “description”: “Clean Code를 읽었다.”},…,]} |
|  | GET | {baseUrl}/missions/today | 오늘의 미션 요청 | durationInSeconds=3600 | {”Authorization”: “Bearer …”} |  | {”missionRecommendations”: [”책 한 권 읽기”, “한 시간 운동하기”, “코딩 문제 하나 풀기”, …]} |
|  | POST | {baseUrl}/missions | 미션 등록 |  | {”Authorization”: “Bearer …”} | {”name”: “책 한 권 읽기”, “date”: “2020-01-01”} | {”id”: 11, “cheerMessage”: “취업에 필요한 기술을 배우거나 마음의 양식을 쌓아 봐요!”} |
|  | PATCH | {baseUrl}/missions/{missionId} | 미션 완수 |  | {”Authorization”: “Bearer …”} | {”accomplished”: true, “description”, “Clean Code를 읽었다.”} | {”cheerMessage”: ”훌륭합니다! Clean Code는 …”} |
| Emotion |  |  |  |  |  |  |  |
|  | GET | {baseUrl}/emotions | 기간 내 감정 조회 | startDate=2020-01-01&endDate=2020-01-02 | {”Authorization”: “Bearer …”} |  | {”emotions”: [{”id”: 21, ”name”: “뿌듯함”, “date”: “2020-01-01”, “description”: “다른 날과 비교해 무언가 이룬 것 같아서 기분이 좋다.”},…,]} |
|  | POST | {baseUrl}/emotions | 감정 등록 |  | {”Authorization”: “Bearer …”} | {”name”: “뿌듯함”, “date”: “2020-01-01”, “description”: “다른 날과 비교해 무언가 이룬 것 같아서 기분이 좋다.”} | {”id”: 21} |
| Summarize |  |  |  |  |  |  |  |
|  | GET | {baseUrl}/summary | 기간 내 데이터를 바탕으로 한 AI 한줄평 요청 | startDate=2020-01-01&endDate=2020-01-02 | {”Authorization”: “Bearer …”} |  | {”summary”: “매우 잘하고 계십니다! 내일도 …”} |
|  |  |  |  |  |  |  |  |
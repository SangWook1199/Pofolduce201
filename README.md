# 📌 TEAM 404총사 : POFOLDUCE201

## 팀 소개
<img src="https://github.com/user-attachments/assets/b8a21c3d-92c4-44d4-8a46-207210ff0b47" width="300" height="200">

| 주순형         | 김혜경          | 전상욱          | 김기영          |
|---------------|---------------|---------------|---------------|
| 개발<br>이슈 관리<br>일정 관리|문서<br>디자인<br>프론트엔드|백엔드<br>버전 관리<br>|스터디<br>데이터베이스<br>|


## 📖 프로젝트 개요 (Introduction / Overview)
<p align="center">
  <img width="100" height="100" alt="Image" src="https://github.com/user-attachments/assets/22867c3d-91ea-4f76-ae04-9fd310379b26" />
</p>


- 포폴듀스201 <br>
- 이력서 및 자기소개서, 포트폴리오를 누구나 확인하고 첨삭하며 자유롭게 토론할 수 있는 웹사이트

***문제 인식 배경***
> 현재 취업준비생들은 취업을 위한 이력서 및 포트폴리오를 준비하는데 어려움을 느끼고 있다. 이것은 이력서/포트폴리오에 대한 적절한 의견을 받는 것이 어렵기 때문이다.

***기획 목적***
> 이력서 및 포트폴리오를 자유롭게 공유하고 평가한다는 새로운 개념을 제안하여 유저들이 무료로 이력서, 자기소개서 업로드 및 첨삭을 받을 수 있는 서비스를 제공한다.

***기대 효과***
>첨삭과 댓글을 통한 다양한 피드백으로 취업/이직 준비생의 이력서/포트폴리오 작성 능력이 향상된다.
>사용자가 직접 유저들의 첨삭 신뢰도를 평가하여, 각 유저에 대한 신뢰도를 피평가자가 확인할 수 있도록 하고, 이를 통해 첨삭 신뢰도가 증가한다.

## 🛠 기술 스택 (Tech Stack)

| 구분       | 내용 |
|------------|------|
| **개발 환경** | WINDOWS / MAC OS, STS, Visual Studio Code, GIT / GitHub, JIRA, MySQL |
| **Backend** | Java 21, Spring Boot 3.4.8, Spring Web, AssertJ, SQL, MyBatis 3.0.4 |
| **Frontend** | HTML, CSS, JavaScript, Ajax - Fetch, Bootstrap5, Thymeleaf |
| **ETC** | Figma |

## 🏛️ 아키텍처 (Architecture)
![pofolduce201-기술구조도](https://github.com/user-attachments/assets/d2876aa1-562e-4e20-a2f4-7266b59b007a)


## ✨ 주요 기능 (Features)
***첨삭***
> 사용자가 이력서/포트폴리오가 업로드된 div를 클릭하면, 해당 div의 eventListner가 클릭을 감지하여 클릭한 위치에 첨삭을 생성할 수 있는 작성 창을 생성하고, 해당 창의 form에서 Fetch API를 사용하여 서버와 비동기 통신을 진행, 입력된 데이터를 서버로 전송한 뒤, 다시한번 Fetch API를 사용하여 서버에서 지금까지 작성된 첨삭의 리스트를 받는다. 이후 해당 리스트를 화면에 출력한다. 리뷰의 수정, 삭제, 평가, 신고 기능에서도 Fetch API를 사용하여 서버에 정보를 전송하고, 서버에서 수정된 내용이 포함된 첨삭 리스트를 받아 화면에 표시한다.


***채용 공고***
> 채용 공고는 웹 크롤링으로 구현했다. 크롤링은 정적 페이지 크롤링(Jsoup)과 동적 페이지 크롤링 (Selenium)로 나뉘는데, 크롤링을 할 사이트는 무한 스크롤로 구현되어 있으므로 동적 크롤링 방법을 사용한다.
> 크롤링 과정은 다음과 같다. 사이트 데이터 크롤링 → 크롤링한 데이터 db에 저장하기 → 공고 페이지에 들어가면 db에 저장된 데이터 출력하기 → 공고 클릭하면 해당 사이트(크롤링 대상 사이트)로 이동하기
> 이후 Spring Boot 의 Scheduler 를 사용하여 약 5분~10분 뒤에 Database 를 비우고 새로운 채용 공고를 가져오도록 했다.

***스터디***
> 스터디 게시판 CRUD 기능을 구현하여 사용자가 게시글을 작성, 수정, 삭제, 조회할 수 있도록 구현했다. 게시판 상세 페이지에서는 신고 버튼을 눌렀을 때 모달이 뜨고, 그 모달을 이용하여 신고를 할 수 있게 구현했고 모달을 컴포넌트화하여 다른 게시판에서도 사용가능하게 했다. 유저 아이디를 클릭했을 때 팝오버가 뜨게 하여 유저의 정보를 간략하게 보여줬고 유저 상세 페이지로 넘어 가게 할 수 있도록 했다.
> 모든 게시글의 주소 정보를 지도 API와 연동하여 마커로 만들어서 지도 위에 시각화했다.


***회사 인증***
> 마이페이지에서는 유저의 정보를 확인할 수 있으며, 수정 버튼을 통해 유저의 이메일, 닉네임 변경을 진행할 수 있다. 내 정보 수정 시에는 이메일, 닉네임이 다른 유저와 중복이라면 변경할 수 없다는 알림창이 나타난다.
또한 회사가 인증되지 않았다면 “인증하기” 버튼을 통해 관련 증명 이미지를 등록하여 관리자에게 전송할 수 있다. 이후 인증이 되기 전까지 “인증 대기중” 이라는 버튼이 나타난다.
> 관리자가 인증을 완료하면 유저의 회사 정보가 나타난다. 만일 관리자가 유저의 회사 인증을 반려한다면 “인증 반려”라는 버튼이 나타나며, 이때는 다시 증명 이미지를 전송하여 재인증 요청을 할 수 있다.
마이페이지 메뉴에서는 유저 본인이 작성한 게시글, 댓글, 첨삭 목록을 확인할 수 있다.

***통합 검색***
> 통합 검색으로 첨삭, 채용 공고, 스터디 게시물들을 검색할 수 있다.
추가적으로 네비게이션 바를 구성하여 각각의 게시판에 대한 전체 검색 결과를 볼 수 있도록 구현하였다.

## 🎥 스크린샷 (Screenshots)
<img width="679" height="976" alt="Image" src="https://github.com/user-attachments/assets/9af99f5d-ca7c-4bff-8d47-774ec8e49781" />
<img width="1023" height="983" alt="Image" src="https://github.com/user-attachments/assets/3b0452ac-c396-47cf-9d94-66dacd9cc164" />
<img width="711" height="1317" alt="image" src="https://github.com/user-attachments/assets/96684ea0-07c6-423a-9721-95a9410b41e8" />
<img width="1035" height="1325" alt="image" src="https://github.com/user-attachments/assets/e2e2a8c8-6110-4b5a-b47e-4ffbdb5d41a0" />
<img width="1034" height="1031" alt="image" src="https://github.com/user-attachments/assets/40c4dad5-937d-4ee8-a2d1-a8a947817280" />
<img width="999" height="757" alt="image" src="https://github.com/user-attachments/assets/8b4ad07f-c0c4-437b-a889-7855e8e7e5f1" />
<img width="1010" height="1100" alt="image" src="https://github.com/user-attachments/assets/e668f17d-dddb-4f6c-ae79-20d4cdd44b02" />
<img width="1059" height="1248" alt="image" src="https://github.com/user-attachments/assets/da43e928-4ec7-41ce-8ce8-8f314d89b505" />

## 📜 라이선스 (License)

| pdf 업로드 | 웹 크롤링 | CSS |
|-----|-----|-----|
| <img width="225" height="225" alt="image" src="https://github.com/user-attachments/assets/6ef74828-8688-4c0d-8651-525a8066077d" /> | <img width="225" height="225" alt="image" src="https://github.com/user-attachments/assets/44a6fbc6-c2b1-40be-a984-8789cabcefce" /> | <img width="225" height="225" alt="image" src="https://github.com/user-attachments/assets/204245ba-ec89-4589-a604-bd8c9f04020c" /> |

## 💡 이슈 관리 (Issue)
***이슈 관리 규칙***
1. 이슈관리대장은 이슈 당사자가 작성한다.
2. 이슈 유형 은 설계, 개발, 비개발 이슈로 나누어진다.
3. 각 이슈는 1차/2차/3차로 절차가 진행된다.
    1. 1차 : 이슈 당사자 스스로 30분간 해결 시도
    2. 2차 : 페어프로그래밍 실시. 30분간 해결 시도
    3. 3차 : 팀 전체 모여 해결 시도
4. 이슈관리대장 작성 항목
    1. 제목
    2. 유형
    3. 차수
    4. 날짜
    5. 당사자
    6. 상세 내용과 해결방법 게시

***이슈 관리 대장***
<br>
https://www.notion.so/23a71b337cea805faac2e2ea4bd8cea0

## 🍰 기타 (ETC)
- 노션 : https://www.notion.so/KOSA-2-23671b337cea80f09b7bf7b25e4cd96b

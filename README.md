<div align="center">
  <h1><img src="./img/BlueLogo.PNG" alt="S-STT Logo" height="30" style="margin-right:10px; vertical-align:bottom;">S-STT Project</h1>
  <h3>음성 파일 텍스트 변환 및 요약 웹 플랫폼</h3>
  <h3>Back-End<img src="https://media.giphy.com/media/cn2LKatpvy89MTVR3e/source.gif" height="30"></h3>
  
  <img src="https://github.com/Kim-SuBin/Kim-SuBin/blob/master/svg/dev/languages/java.svg" alt="java" />
  <img src="https://github.com/Kim-SuBin/Kim-SuBin/blob/master/svg/dev/framework/spring.svg" alt="spring" />
  <img src="https://github.com/Kim-SuBin/Kim-SuBin/blob/master/svg/dev/service/amazondynamodb.svg" alt="amazon Dynamo DB">
  <img src="https://github.com/Kim-SuBin/Kim-SuBin/blob/master/svg/dev/service/amazonaws.svg" alt="amazon AWS" />
  <img src="https://github.com/Kim-SuBin/Kim-SuBin/blob/master/svg/dev/tool/intellijidea.svg" alt="intelliJ" />
  
</div>

---


## 🚀구현목록

### 기본 로그인 기능

1. 전달받은 로그인 정보가 DB에 등록되어 있는지 확인한다.
2. 등록되어 있지 않은 경우, False 전달한다.
3. 등록되어 있는 경우, 세션 등록한다.

### 기본 로그아웃 기능

1. 세션 정보 초기화한다.

### 기본 회원가입 기능

1. 전달받은 아이디가 DB에 등록되어 있는 경우 False, 등록되어 있지 않은 경우 True 전달한다.
2. 회원가입 버튼 클릭을 통해 전달된 정보를 DB에 저장한다. (아이디, 비밀번호, 이메일)

### 소셜 로그인, 회원가입 기능

- 소셜 로그인 예제 찾아보고 적용할게요.

### 게시판 글 목록 기능

세션이 유지되는 경우를 전제로 한다.

1. 주제들에 대한 목록을 오름차순으로 정렬하여 전달한다. (전체 + 주제들) - **주제는 임의로 정해줌**
2. 게시글 목록은 시간순(최신순)으로 정렬하여 전달한다. (게시글 제목, 업로드 시간 함께 전송)
3. 한 페이지에 전달하는 게시글은 최대 15개이다. (페이징하여 전달하기)
4. 게시글 삭제 시 DB에 있는 내용을 삭제한다.

### 게시글 작성 기능

1. 게시글 주제에 대한 정보를 전달한다.
2. 전달 받은 정보를 DB에 저장한다. (제목, 주제, 상세설명, 파일)

### 게시글 상세보기 기능

1. 전달 받은 게시글 ID를 바탕으로 DB에서 정보를 찾아 전달한다. (제목, 상세설명, 키워드, 주제별 상세내용, 원본)
2. 수정 버튼을 통해 전달된 정보를 DB에 Update 한다.
    - 파일 재업로드
    - 제목, 원본 빼고 모두 변경 가능
3. 삭제 버튼 클릭 시 게시글 전시 여부를 N으로 수정한다.
4. 파일재업로드 버튼을 통해 전달된 파일로 DB를 Update한다.
5. 정보가 수정된 경우, 해당 페이지로 DB 정보를 재전송한다.

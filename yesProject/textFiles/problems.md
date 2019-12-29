#### 데스크탑에서만 안되는 내용(노트북과 같은 코드)

##### 음식점 검색 시 지도에 마커가 찍히지 않음

- Uncaught SyntaxError: Unexpected token ',' 발생 : 파싱해서 넘어오는 데이터에 좌표 값이 비어있어서 발생하는 듯

- 원인 : 불명

###### 증상

- HomeController에서 alist를 service에서 받아와 출력했을 때 노트북은 출력이 되는데, 데스크탑은 안됨(이거 해결하면 될 듯)

- main.jsp파일의 alist로 받아오는 data가 없고, branch_search.js -> search_start()의 ajax로 받아오는 data에서도 좌표를 확인할 수 없음

######  해결방법

DB 아이디

노트북 : scott

데스크탑 : yoojs

노트북과 데스크탑이 사용하고 있는 DB의 ID/PW가 달랐다. 데스크탑에서 yoojs의 테이블을 아무리 수정해도 scott에 반영되지 않았던 것이다. 

applicationContext.xml 파일에 등록되어 있는 DB의 ID로 로그인해서

	UPDATE branch_info SET acceptState=true;

을 실행해 정상적으로 실행되도록 했다.

#### 수정해야 될 내용

##### 한글 깨짐

controller(주로 LoginController) 코드 자체가 한글이 깨져서 생기는 문제

##### 원인

- ajax로 주고 받는 데이터가 깨져서 전송돼 JSP파일에서 제대로 인식하지 못했다.(코드에는 이상무)

##### 해결방법

- LoginController의 해당 메소드의 return값을 영어로 바꿔 글자가 깨지지 않도록 하고 member_login.js 파일의 loginCheck메소드에서 전송받는 값을(data) 그대로 사용해 로그인 성공 여부를 확인했다.
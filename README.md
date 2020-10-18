##### 프로젝트명 : 요식업예약 웹사이트

##### 기술 스택

- back-end : Java(Spring)

- front-end : HTML / Java Script / jQuery / JSTL

- DBMS : MySQL


##### 맡은 역할

- 로그인 / 회원가입

- 지도 API 연동

- 리뷰게시판

- 가맹점 등록 승인


##### 작성한 소스 파일

- Controller : LoginController / ReviewController <br>/ AdminController(String updateBranchAcceptState(String id) / String showBranchInfo(String userID, Model model))

- model : LoginDAO / LoginDAOImpl / ReviewDAO / ReviewDAOImpl

- entity : UserVo / ReviewVo / CommentVo

- service : LoginService / LoginServiceImpl / ReviewService / ReviewServiceImpl<br> / AdminService(String updateBranchAcceptState(String id) / String showBranchInfo(String userID, Model model))

- mapper : reviewMapper / userMapper

- jsp : reviewComment / reviewDetail / reviewEdit / reviewList / reviewWrite

- js : memberJoin / memberLogin / set_Map


##### DB

- create / insert 쿼리문 디렉토리 : src/main/webapp/WEB-INF/sql 에 있습니다.

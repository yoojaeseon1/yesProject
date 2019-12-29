#### 데스크탑에서만 안되는 내용(노트북과 같은 코드)

##### 음식점 검색 시 지도에 마커가 찍히지 않음(Uncaught SyntaxError: Unexpected token ',' 발생)

원인불명

main.jsp파일의 alist로 받아오는 data가 없고,

branch_search.js->search_start()의 ajax로 받아오는 data에서도 좌표를 확인할 수 없음
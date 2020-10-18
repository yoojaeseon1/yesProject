var container = document.getElementById('map');
var options = {
	center : new daum.maps.LatLng(37.55243511592138, 126.93755798396944),
	level : 2
};

var map = new daum.maps.Map(container, options);

var mapTypeControl = new daum.maps.MapTypeControl();

map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

var zoomControl = new daum.maps.ZoomControl();
map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

var clusterer = new daum.maps.MarkerClusterer({
	map : map, 
	averageCenter : true,
	minLevel : 6, 
	minClusterSize : 1,
	styles : [ {
		width : '53px',
		height : '52px',
		background : 'url("/resources/imgs/clusterer.png") no-repeat',
		color : '#fff',
		textAlign : 'center',
		lineHeight : '50px'
	} ]
});
var branchCom = [];
var addressArray = [];

var geocoder = new daum.maps.services.Geocoder();
var coords = null;
var markerPosition = null;

daum.maps.event.addListener(map, 'zoom_changed', function() {
	var level = map.getLevel();
	if (level >= 5) {
		$('.wrap').css('display', 'none');
		selectedMarker = null;
	}
});

var selectedMarker = null;

function addMarker(markerPosition, branchArr) {
	$('.btn-gradient').click(function(event) {
		event.preventDefault();
	});

	var id = branchArr[0], branchName = branchArr[1], opTime = branchArr[2], breakTime = branchArr[3], opDate = branchArr[4], phoneNum = branchArr[5], score = branchArr[6], state = branchArr[7], zoneCode = branchArr[8], roadAddress = branchArr[9], jibunAddress = branchArr[10], detailAddress = branchArr[11], markerImage = branchArr[12], mainImage = branchArr[13], image1 = branchArr[14], image2 = branchArr[15], image3 = branchArr[16], image4 = branchArr[17], image5 = branchArr[18], image6 = branchArr[19], image7 = branchArr[20], image8 = branchArr[21], category = branchArr[22], branchExplain = branchArr[23], sido = branchArr[24], sigungu = branchArr[25], category = branchArr[26];

	var imageSrc = '/resources/imgs/markerIcon/' + markerImage, // 마커이미지의 주소
	imageSize = new daum.maps.Size(55, 55), // 마커이미지의 크기
	imageOption = {
		offset : new daum.maps.Point(27, 69)
	}; // 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정

	var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize,
			imageOption);
	var marker = new daum.maps.Marker({
		position : markerPosition,
		title : id,
		image : markerImage
	// 마커이미지 설정
	});
	var className = '' + id + ' wrap';

	var content = document.createElement('div');
	content.className = className;
	content.innerHTML = '<div class="info">' + '<div class="title">' + '<p>'
			+ branchName
			+ '</p>'
			+ '<div class="close" title="닫기"></div>'
			+ '</div>'
			+ '<div class="popupBody">'
			+ '<div class="img">'
			+ '<img src="/resources/imgs/foodimgs/'
			+ image1
			+ '" width="100" height="100">'
			+ '</div>'
			+ '<div class="desc">'
			+ '<div class="address ellipsis">'
			+ roadAddress
			+ ' '
			+ detailAddress
			+ '</div>'
			+ '<div class="jibun ellipsis">(우) '
			+ zoneCode
			+ ' (지번) '
			+ jibunAddress
			+ '</div>'
			+ '<div class="phone ellipsis">'
			+ phoneNum
			+ '</div>'
			+ '<div class="ellipsis" style="margin-top: 20px;"><br/></div>'
			+ '<div class="timeDiv">'
			+ '<div class="timeDiv1">'
			+ '영업시간'
			+ '</div>'
			+ '<div class="timeDiv2">'
			+ opTime
			+ '</div>'
			+ '</div>'
			+ '<div class="dayDiv">'
			+ '<div class="dayDiv1">'
			+ '영업일'
			+ '</div>'
			+ '<div class="dayDiv2">'
			+ opDate
			+ '</div>'
			+ '</div>'
			+ '<div class="breakDiv">'
			+ '<div class="breakDiv1">'
			+ '휴게시간'
			+ '</div>'
			+ '<div class="breakDiv2">'
			+ breakTime
			+ '</div>'
			+ '</div>'
			+ '<div class="restDiv">'
			+ '<div class="restDiv1">'
			+ '대표메뉴'
			+ '</div>'
			+ '<div class="restDiv2">'
			+ '<div class="restDiv2_1">'
			+ '</div>'
			+ '<div class="restDiv2_2">'
			+ '</div>'
			+ '<div class="restDiv2_3">'
			+ '</div>'
			+ '<div class="restDiv3_1">'
			+ '</div>'
			+ '<div class="restDiv3_2">'
			+ '</div>'
			+ '<div class="restDiv3_3" style="text-align: left;">'
			+ '</div>'
			+ '<div class="restDiv4_1">'
			+ '</div>'
			+ '<div class="restDiv4_2">'
			+ '</div>'
			+ '<div class="restDiv4_3">'
			+ '</div>'
			+ '</div>'
			+ '</div>'
			+ '<div class="gradeDiv">'
			+ '<div class="gradeDiv1">'
			+ '이용자 평점'
			+ '</div>'
			+ '<div class="gradeDiv2" style="font-size: 15px;">'
			+ '<span class="reviewScoreAvg"></span>'
			+ ' / 5.0'
			+ '</div>'
			+ '<div class="gradeDiv3">'
			+ '</div>'
			+ '</div>'
			+ '<div class="btnDiv">'
			+ '<a href="#branchDetailModal" onclick="javascript:branchDetail(\''
			+ branchArr
			+ '\');" rel="modal:open" class="btn-gradient gray block">상세보기</a>'
			+ '<a href="#reserveModal" onclick="javascript:reserveModal(\''
			+ branchName + '\',\'' + id
			+ '\')" rel="modal:open" class="btn-gradient red block">바로예약</a>'
			+ '</div>' + '</div>' + '</div>' + '</div>';

	var overlay = new daum.maps.CustomOverlay({
		content : content,
		map : map,
		position : marker.getPosition()
	});

	$('.wrap').css('display', 'none');

	var callback = function() {
		var test = [];
		$.ajax({
			type : 'POST',
			url : './popup',
			data : id,
			dataType : 'json',
			success : function(data) {
				$.each(data, function(idx, val) {
					test.push(val.menu);
					test.push(val.price);
					mapSetMarker(test);
				}); // each end
			} // success end
		}); // ajax end

		function mapSetMarker(test) {

			var mapProjection = map.getProjection(),
			// 해당 위도 경도값을 X,Y값으로 반환해줌
			latlng = markerPosition,
			// 지도 좌표에 해당하는 위치 좌표
			mapPixel = mapProjection.containerPointFromCoords(latlng);

			var center = map.getCenter();
			var mapCenter = mapProjection.containerPointFromCoords(center);
			var deltaX = mapPixel.x - mapCenter.x, deltaY = mapPixel.y
					- mapCenter.y;


			var markerTitle = marker.getTitle();

			if (selectedMarker === null) {
				selectedMarker = markerTitle;
				overlay.setMap(map);
				map.panBy(deltaX - 25, deltaY - 250);
				$('.' + markerTitle + '').css('display', 'inherit');
			} else if (selectedMarker !== markerTitle) {
				overlay.setMap(map);
				map.panBy(deltaX - 25, deltaY - 250);
				$('.wrap').css('display', 'none');
				$('.' + markerTitle + '').css('display', 'inherit');
				selectedMarker = markerTitle;
			} else if (selectedMarker === markerTitle) {
				$('.wrap').css('display', 'none');
				selectedMarker = null;
			}
			$('.close').click(function() {
				$('.wrap').css('display', 'none');
				selectedMarker = null;
			});

			for (i = 0; i < test.length; i++) {
				if (test.length > 1) {
					$('.restDiv2_2').css('border-bottom', '1px dotted gray');
					$('.restDiv2_1').text(test[0]);
					$('.restDiv2_3').text(test[1] + "원");
				}
				if (test.length > 3) {
					$('.restDiv3_2').css('border-bottom', '1px dotted gray');
					$('.restDiv3_1').text(test[2]);
					$('.restDiv3_3').text(test[3] + "원");
				}
				if (test.length > 5) {
					$('.restDiv4_2').css('border-bottom', '1px dotted gray');
					$('.restDiv4_1').text(test[4]);
					$('.restDiv4_3').text(test[5] + "원");
				}
			}

			// 리뷰게시판의 평점 평균을 불러옴
			$.ajax({
				type : 'POST',
				url : './loadReviewScoreAvg',
				data : id,
				dataType : 'text',
				success : function(data) {
					if (data === '6.0')
						$('.reviewScoreAvg').text('평가없음');
					else
						$('.reviewScoreAvg').text(data);
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}
			});
		}
	};
	daum.maps.event.addListener(marker, 'click', callback);
	clusterer.addMarker(marker);
	// 마커가 지도 위에 표시되도록 설정
	marker.setMap(map);
	overlay.setMap(null);
}

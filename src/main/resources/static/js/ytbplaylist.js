// 유튜브 api 통해서 재생목록의 title, description, img, videoId 받아서 출력
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
const PLAYLIST_ID = 'PLRx0vPvlEmdBjfCADjCc41aD4G0bmdl4R'; // 대상 재생목록의 ID
// maxResults=30 : 최대 30개 가져오기, 설정 안 하면 기본적으로 5개만 가지고 옴
const API_URL = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=30&playlistId=${PLAYLIST_ID}&key=${API_KEY}`;

// 재생시간도 가져와야 되는데 이거는 어떻게 가져오냐
fetch(API_URL)
    .then(response => response.json())
    .then(data => {
        const videoList = document.getElementById("videoList");

        data.items.forEach(item => {
            const videoTitle = item.snippet.title;
            const videoDescription = item.snippet.description;
            const videoThumbnail = item.snippet.thumbnails.medium.url;
            const videoId = item.snippet.resourceId.videoId || "";

            // data 객체 생성
            const data = {
                videoTitle: videoTitle,
                videoDescription: videoDescription,
                videoId: videoId
            };

            // 서버로 데이터를 보내는 fetch 요청
            fetch('/api/admin/saveYoutubeData', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(result => {
                    // 데이터 저장 결과 처리
                    console.log('나오나'+ videoTitle);
                })
                .catch(error => {
                    console.error('Error:', error);
                });

            const listItem = document.createElement("li");
            listItem.innerHTML = `
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
            <div class="container" style="display: flex; padding-bottom: 100px">
                <div style="flex: 2; padding-right: 100px;"" >
                    <img src="${videoThumbnail}" alt="${videoTitle}">
                </div>
                <div style="flex: 5; padding-right: 100px;"">
                    <h4>${videoTitle}</h4>
                     <p style="padding-top: 20px;">${videoDescription}</p>
                </div>
                <div style="flex: 2">
                    <!--
                    이거는 화면을 
                    <a href="https://www.youtube.com/embed/${videoId}" target="_blank">
                    <i class="fa-regular fa-circle-play" 
                       style=" font-size: 60px; color: black;
                               width: 200px; height: 150px; padding-top: 50px;"></i>
                    </a>
                    -->
                    <a href="/admin/lms/online/view">
                    <i class="fa-regular fa-circle-play" 
                       style=" font-size: 60px; color: black;
                               width: 200px; height: 150px; padding-top: 50px;"></i>
                    </a>
                </div>
            </div>
            `;

            videoList.appendChild(listItem);
        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });

/*
// 가져온 데이터 DB에 저장
fetch(API_URL)
    .then(response => response.json())
    .then(data => {
        const videoList = document.getElementById("videoList");

        data.items.forEach(item => {
            const videoTitle = item.snippet.title;
            const videoDescription = item.snippet.description;
            const videoThumbnail = item.snippet.thumbnails.medium.url;
            const videoId = item.snippet.resourceId.videoId || "";

            // 이전 코드와 동일한 부분

            // data 객체 생성
            const data = {
                videoTitle: videoTitle,
                videoDescription: videoDescription,
                videoId: videoId
            };

            // 서버로 데이터를 보내는 fetch 요청
            fetch('/saveYoutubeData', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(result => {
                    // 데이터 저장 결과 처리
                })
                .catch(error => {
                    console.error('Error:', error);
                });

            // 나머지 부분도 이전 코드와 동일
        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });
*/

//// 알림창 띄우기
// YouTube IFrame Player API 링크 스크립트 만들어서 가져오기
let tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
let firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

let player;
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        videoId: `wjLwmWyItWI`,
        events: {
            'onReady': onPlayerReady, // 로딩 중에 이벤트 실행
            'onStateChange': onPlayerStateChange // 플레이어 상태 변화 시 이벤트 실행
        }
    });
}

function onPlayerReady(event) {
    // 로딩된 후에 실행될 동작을 작성(소리 크기, 동영상 속도 등)
    //event.target.playVideo(); // 동영상 재생 시작 -> ? 이거 때문에 들어가자마자 바로 시작되는 건가?
}

function onPlayerStateChange(event) {
    // 재생 중일 때 작성한 동작 실행
    if (event.data == YT.PlayerState.PLAYING) {
        const randomTime = Math.floor(Math.random() * (30000 - 5000 + 1)) + 5000; // 5초에서 30초 사이의 무작위 밀리초
        let timeout;

        // 동영상이 재생 중일 때 타이머 설정
        function showNotification() {
            const userConfirmed = confirm('확인 버튼을 누르지 않으면 재생을 중지합니다!');

            if (userConfirmed) {
                player.pauseVideo(); // 사용자가 "확인" 버튼을 누르지 않은 경우 동영상을 일시 중지
            }
            clearTimeout(timeout); // 타이머 취소
        }

        // setTimeout함수는 showNotification함수를 randomTime이후에 실행하도록 예약
        timeout = setTimeout(showNotification, randomTime)

        // 알림 확인 버튼을 클릭할 때 타이머 취소
        // window.confirm("확인 버튼을 클릭하면 동영상 재생이 계속됩니다.") {
        //     clearTimeout(timeout); // 타이머 취소 player.pauseVideo(); // 동영상 일시 중지
        // }
    }
}




/*
// videoId 동적으로 가져와 사용하기 -> 아니면 콘텐츠 컬럼에 videoId = ytbUrl 저장해서 이 컬럼을 가져오기
fetch(API_URL)
    .then(response => response.json())
    .then(data => {
        const videoList = document.getElementById("videoList");

        data.items.forEach(item => {
            const videoTitle = item.snippet.title;
            const videoDescription = item.snippet.description;
            const videoThumbnail = item.snippet.thumbnails.medium.url;
            const videoId = item.snippet.resourceId.videoId || "";

            // videoId를 동적으로 설정하기 위해 onYouTubeIframeAPIReady 함수 내에서 사용 가능하도록 설정
            window.videoId = videoId;

            const listItem = document.createElement("li");
            listItem.innerHTML = `
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
            <div class="container" style="display: flex; padding-bottom: 100px">
                <div style="flex: 2; padding-right: 100px;"" >
                    <img src="${videoThumbnail}" alt="${videoTitle}">
                </div>
                <div style="flex: 5; padding-right: 100px;"">
                    <h4>${videoTitle}</h4>
                     <p style="padding-top: 20px;">${videoDescription}</p>
                </div>
                <div style="flex: 2">
                    <a href="/admin/lms/online/view">
                    <i class="fa-regular fa-circle-play" 
                       style=" font-size: 60px; color: black;
                               width: 200px; height: 150px; padding-top: 50px;"></i>
                    </a>
                </div>
            </div>
            `;

            videoList.appendChild(listItem);
        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });

// 알림창 띄우기
let tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
let firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

let player;
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        videoId: window.videoId, // videoId 값을 동적으로 설정
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
}

function onPlayerReady(event) {
    event.target.playVideo();
}

function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING) {
        setTimeout(function() {
            alert('동영상 재생 중!');
        }, 10000);
    }
}*/

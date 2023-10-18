// 썸네일 가져오기
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 API 키
document.addEventListener("DOMContentLoaded", function() {
    const videoListElements = document.querySelectorAll('[data-ytb-url]');

    const promises = [];

    videoListElements.forEach(videoListElement => {
        const videoIds = videoListElement.getAttribute('data-ytb-url').split(',');

        videoIds.forEach(id => {
            console.log(id + "id다 나오나?");
            const videoAPI_URL = `https://www.googleapis.com/youtube/v3/videos?part=snippet&id=${id}&key=${API_KEY}`;

            promises.push(
                fetch(videoAPI_URL)
                    .then(response => response.json())
                    .then(data => {
                        if (data.items.length > 0) {
                            const videoThumbnail = data.items[0].snippet.thumbnails.medium.url;

                            // 생성된 div 내에 이미지 추가
                            const imgElement = document.createElement("img");
                            imgElement.src = videoThumbnail;
                            videoListElement.appendChild(imgElement);
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching video data for video ID', id, ':', error);
                    })
            );
        });
    });

    // 모든 API 요청을 기다렸다가 처리
    Promise.all(promises)
        .then(() => {
            console.log('All videos processed');
        })
        .catch(error => {
            console.error('Error processing videos:', error);
        });
});


function onYouTubeIframeAPIReady() {
    // 모든 버튼 요소 가져오기
    const playButtons = document.querySelectorAll('.playBtn');

    playButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 해당 버튼에 연결된 비디오 ID 가져오기
            const videoId = button.getAttribute('data-ytb-id');

            console.log(videoId + "나와세요");
            // videoId를 사용하여 YouTube 플레이어 초기화 및 재생
            const player = new YT.Player('player', {
                videoId: videoId,
                events: {
                    'onReady': onPlayerReady,
                    'onStateChange': onPlayerStateChange
                }
            });
            window.location.href = '/admin/lms/online/view';
        });
    });
}

function onPlayerReady(event) {
    // 플레이어가 준비된 경우 원하는 동작을 수행하세요.
}

function onPlayerStateChange(event) {
    // 플레이어 상태가 변경된 경우 원하는 동작을 수행하세요.
}

/*
// 영상 보여주기
// YouTube IFrame Player API 스크립트 로드
let tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
let firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

let players = {}; // 각 비디오 플레이어를 저장할 객체

function onYouTubeIframeAPIReady() {
    // API가 로드되면 여기에 비디오 플레이어를 생성하는 로직을 추가합니다.
}


document.addEventListener("DOMContentLoaded", function() {

    // 요소를 선택하거나 가져옵니다.
    const element = document.querySelector('[data-ytburl]');

// data-ytburl 속성 값을 가져옵니다.
    const ytbUrl = element.getAttribute('data-ytburl');

    const playButtons = document.querySelectorAll('.playBtn');

    playButtons.forEach(playButton => {
        playButton.addEventListener('click', function() {
            const videoId = playButton.getAttribute('data-ytburl');

            if (!players[videoId]) {
                // 비디오 플레이어가 아직 생성되지 않은 경우
                players[videoId] = new YT.Player(videoId, {
                    events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                    }
                });
            } else {
                // 이미 생성된 비디오 플레이어가 있는 경우
                // 이것을 사용하여 원하는 동작을 수행하세요.
            }
        });
    });
});

function onPlayerReady(event) {
    // 플레이어가 준비된 경우 원하는 동작을 수행하세요.
}

function onPlayerStateChange(event) {
    // 플레이어 상태가 변경된 경우 원하는 동작을 수행하세요.
}




/*
// 유튜브 api 통해서 재생목록의 title, description, img, videoId 받아서 출력
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
const PLAYLIST_ID = 'PLz2iXe7EqJOOAo_79II0pnV4-mhQz_Sp-'; // 대상 재생목록의 ID
// maxResults=30 : 최대 30개 가져오기, 설정 안 하면 기본적으로 5개만 가지고 옴
const API_URL = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=40&playlistId=${PLAYLIST_ID}&key=${API_KEY}`;

// 재생시간은 유튜브 api에서 제공해주지 않아서 못 가져옴..
fetch(API_URL)
    .then(response => response.json())
    .then(data => {
        const videoList = document.getElementById("videoList");

        data.items.forEach(item => {
            const videoTitle = item.snippet.title;
            const videoDescription = item.snippet.description;
            const videoThumbnail = item.snippet.thumbnails.medium.url;


            const listItem = document.createElement("div");
            listItem.innerHTML = `
            <!-- 폰트어썸 링크 추가 -->
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
                    <a href="https://www.youtube.com/embed/$videoId}" target="_blank">
                    <i class="fa-regular fa-circle-play"
                       style=" font-size: 60px; color: black;
                               width: 200px; height: 150px; padding-top: 50px;"></i>
                    </a>
                    -->
                    <!-- 영상 href="/admin/lms/online/view"-->
                    <button class="playBtn" th:attr="data-contentNo=${contentNo}">
                    <i class="fa-regular fa-circle-play"
                       style=" font-size: 60px; color: black;
                               width: 200px; height: 150px; padding-top: 50px;"></i>
                    </button>
                </div>
            </div>
            `;

            videoList.appendChild(listItem);
        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });
*/
/*


////////////////////////// ytbControl로 이동(합치기)
// 알림창 띄우기
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
*/



/*
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

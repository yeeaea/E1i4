// 유튜브 api 통해서 재생목록의 title, description, img, videoId 받아서 출력
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
const PLAYLIST_ID = 'PLz2iXe7EqJOOAo_79II0pnV4-mhQz_Sp-'; // 대상 재생목록의 ID
// maxResults=30 : 최대 30개 가져오기, 설정 안 하면 기본적으로 5개만 가지고 옴
const API_URL = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=30&playlistId=${PLAYLIST_ID}&key=${API_KEY}`;

// 재생시간도 가져와야 되는데 이거는 어떻게 가져오냐
fetch(API_URL)
    .then(response => response.json())
    .then(data => {
        const videoList = document.getElementById("videoList");

        data.items.forEach((item, index) => {
            const videoTitle = item.snippet.title;
            const videoDescription = item.snippet.description;
            const videoId = item.snippet.resourceId.videoId || "";
            const videoUrl = `https://www.youtube.com/watch?v=${videoId}`

            // data 객체 생성
            const data = {
                videoTitle: videoTitle,
                videoDescription: videoDescription,
                videoId: videoId,
                videoUrl: videoUrl,
                position: index + 1 // 비디오의 순서를 저장
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
                    console.log(`Video ${videoTitle} saved with position ${index + 1}`);
                    console.log(videoUrl);
                })
                .catch(error => {
                    console.error('Error:', error);
                });

        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });

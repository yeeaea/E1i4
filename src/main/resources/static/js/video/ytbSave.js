
// 유튜브 api 통해서 재생목록의 title, description, img, videoId 받아서 출력
//const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
//const PLAYLIST_ID = 'PLz2iXe7EqJOOAo_79II0pnV4-mhQz_Sp-'; // 대상 재생목록의 ID
// maxResults=30 : 최대 30개 가져오기, 설정 안 하면 기본적으로 5개만 가지고 옴
//const API_URL = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=30&playlistId=${PLAYLIST_ID}&key=${API_KEY}`;
/*
// 재생시간은 유튜브 api에서 제공해주지 않아서 못 가져옴..
// 그래서 그냥 콘텐츠 관리 부분에서 제외했습니다.
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
                .catch(error => {
                    console.error('Error:', error);
                });

        });
    })
    .catch(error => {
        console.error('Error fetching video data: ', error);
    });
*/



const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
const PLAYLIST_IDS = [
    'PLZKTXPmaJk8JDicsOyY2cTcwXmBa-ZceI',
    'PLz2iXe7EqJONQSW20v2vtd6wGDQDtjj2S',
    'PLz2iXe7EqJOOAo_79II0pnV4-mhQz_Sp-',
    'PLVsNizTWUw7GuNAksmGAp-6XgyfUPgGaY',
    'PLVsNizTWUw7GN8wPRhclbKuQa9aI9Cj2V',
    'PLz2iXe7EqJOOTNTK27a4-WsgZU5NVfguh',
    'PLz2iXe7EqJOOt1r8Io-BFAV-SHFWFKYtN',
    'PLZKTXPmaJk8JZ2NAC538UzhY_UNqMdZB4',
    'PLZKTXPmaJk8J_fHAzPLH8CJ_HO_M33e7-',
    'PLVsNizTWUw7GCfy5RH27cQL5MeKYnl8Pm',
    'PLVsNizTWUw7GlCcyc2E8LOvUJ-oR9Q_mJ',
    'PLZtMMLWNjLqkCbsCI1mxY7UcnHVQQOfme',
    'PLtqbFd2VIQv4O6D6l9HcD732hdrnYb6CY',
    'PLJN246lAkhQjoU0C4v8FgtbjOIXxSs_4Q',
    'PLqpIWUSJxdDn9X17lI-WO56i6WAwx0VzS',
    'PLuHgQVnccGMA9-1PvblBehoGg7Pu1lg6q',
    'PLE8CE1CEC45631D51'];// 여러 개의 재생목록 ID를 배열로 정의

const fetchPlaylistData = async (playlistId) => {
    const API_URL = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=40&playlistId=${playlistId}&key=${API_KEY}`;

    try {
        const response = await fetch(API_URL);
        const data = await response.json();

        fetch(API_URL)
            .then(response => response.json())
            .then(data => {
                const videoList = document.getElementById("videoList");

                data.items.forEach((item, index) => {
                    console.log(item);
                    const videoTitle = item.snippet.title;
                    const videoDescription = item.snippet.description;
                    const videoId = item.snippet.resourceId.videoId || "";
                    const videoUrl = `https://www.youtube.com/watch?v=${videoId}`;
                    //const duration = item.contentDetails.duration; // 동영상의 지속 시간 (ISO 8601 형식)



                    // data 객체 생성
                    const data = {
                        videoTitle: videoTitle,
                        videoDescription: videoDescription,
                        videoId: videoId,
                        videoUrl: videoUrl,
                        //duration: duration,
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
                            console.log(duration);
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });

                });
            })
            .catch(error => {
                console.error('Error fetching video data: ', error);
            });

    } catch (error) {
        console.error('Error fetching video data:', error);
    }
};

// PLAYLIST_IDS 배열을 순회하여 각 재생목록 ID에 대한 데이터를 가져오기 위해 fetchPlaylistData 함수를 호출합니다.
PLAYLIST_IDS.forEach((playlistId) => {
    fetchPlaylistData(playlistId);
});


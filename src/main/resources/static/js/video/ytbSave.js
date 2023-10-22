// 재생목록에 있는 콘텐츠 가져와서 테이블에 저장시키기위한 코드
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 api_key
const PLAYLIST_IDS = [
    'PLZKTXPmaJk8JDicsOyY2cTcwXmBa-ZceI',
    'PLz2iXe7EqJONQSW20v2vtd6wGDQDtjj2S',
    'PLz2iXe7EqJOOAo_79II0pnV4-mhQz_Sp-',
    'PLVsNizTWUw7GuNAksmGAp-6XgyfUPgGaY',
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
        // 웹 API로부터 데이터를 가져와 JavaScript 객체로 변환하여 활용하기
        // fetch 함수를 사용하여 API_URL 변수에 지정된 URL로 HTTP GET 요청을 보냄
        const response = await fetch(API_URL);
        // Response 객체의 json 메서드를 사용하여 웹 API의 응답 데이터를 JSON 형식으로 파싱
        const data = await response.json();
        const videoIds = data.items.map(item => item.snippet.resourceId.videoId);
        console.log(videoIds);

        videoIds.forEach(videoId => {
            const videoAPI_URL = `https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails&id=${videoId}&key=${API_KEY}`;


            fetch(videoAPI_URL)
                .then(response => response.json())
                .then(data => {

                    data.items.forEach((item, index) => {

                        const videoTitle = item.snippet.title;
                        const videoDescription = item.snippet.description;
                        const videoUrl = `https://www.youtube.com/watch?v=${videoId}`;

                        const duration = item.contentDetails.duration || "";// 동영상의 지속 시간 (ISO 8601 형식)

                        // ISO 8601 형식을 파싱하여 시간, 분, 초를 추출
                        const matches = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/);

                        // 시간, 분, 초를 추출한 뒤, 초로 변환
                        const hours = matches[1] ? parseInt(matches[1]) * 3600 : 0;
                        const minutes = matches[2] ? parseInt(matches[2]) * 60 : 0;
                        const seconds = matches[3] ? parseInt(matches[3]) : 0;

                        const totalSeconds = (hours + minutes + seconds) - 1;

                        // totalSeconds를 int로 변환
                        const durationInt = parseInt(totalSeconds);

                        // data 객체 생성
                        const data = {
                            videoTitle: videoTitle,
                            videoDescription: videoDescription,
                            videoId: videoId,
                            videoUrl: videoUrl,
                            duration: durationInt,
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
                                //console.log(duration);
                            })
                            .catch(error => {
                                console.error('Error:', error);
                            });

                    });
                })
                .catch(error => {
                    console.error('Error fetching video data: ', error);
                });
        });

    } catch (error) {
        console.error('Error fetching video data:', error);
    }
};

// PLAYLIST_IDS 배열을 순회하여 각 재생목록 ID에 대한 데이터를 가져오기 위해 fetchPlaylistData 함수를 호출합니다.
PLAYLIST_IDS.forEach((playlistId) => {
    fetchPlaylistData(playlistId);
});


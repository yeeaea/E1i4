///// 썸네일 가져오기
// 여기서 썸네일과 같이 강의명, 설명 등 같이 가져올 수 있음... but
// -> 원래는 연동번호 콘텐츠 관리에서 유튜브 연동번호 입력 (강의 제작해 업로드(강의명, 설명 등 자료가 존재))
// -> 그에 맞는 썸네일, 강의명, 설명 등 보여줌(유튜브 api)
// 이지만 우리는 업로드 된 게 없으므로 그냥 디비에 콘텐츠를 저장시킨 후 디비에서 데이터를 model 객체 통해 보여줌
const API_KEY = 'AIzaSyCg9dqHR6cSg7j1smdb50VLVsSLaxRBRA4'; // 내 API 키
document.addEventListener("DOMContentLoaded", function() {
    const videoListElements = document.querySelectorAll('[data-ytb-url]');

    const promises = [];

    videoListElements.forEach(videoListElement => {
        const videoIds = videoListElement.getAttribute('data-ytb-url').split(',');

        videoIds.forEach(id => {
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

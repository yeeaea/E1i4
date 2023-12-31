///// 영상 제어
// 처음 영상 시작 하고 5초를 무조건 들어야 됨
// 시간이 지나면? 어떤 이유로 다른 키로 재 생성돼 값이 동일한 행이 또 생기는 이슈
// 처음 새로 고침 하고 영상 멈춘 상태 인지 확인 하기
// 알림 띄우는 거는 시간 되면 코드 보기
document.addEventListener("DOMContentLoaded", function () {
    onYouTubeIframeAPIReady();
});

let finalTm;

// player라는 id를 가진 div에 플레이어 호출
// 아이디 값은 ytbUrl컬럼에 있는 값을 통해서 영상 가져오기 -> 이거만 하면 ok!
function onYouTubeIframeAPIReady() {
    console.log("onYouTubeIframeAPIReady 실행1?");
    const ytbUrl =
        document.querySelector('[data-ytb-url]')
            .getAttribute('data-ytb-url');

    player = new YT.Player('player', {
        videoId: ytbUrl,
        playerVars: {
            disablekb: 1,  //키보드 입력 제한
            rel: 0
        },
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange,
            'onPlaybackRateChange': onPlayerPlaybackRateChange
        }
    });
}

// 마지막에 보고 있던 시간으로 이동 후 재생
// 로딩된 후에 실행될 동작을 작성하는 곳(소리 크기, 동영상 속도 등)
function onPlayerReady(event) {
    console.log("onPlayerReady 실행2?");
    const ytbUrl =
        document.querySelector('[data-ytb-url]')
            .getAttribute('data-ytb-url');

    let dataValue = document.querySelector('[data-finalTmValue]');
    let finalTmValue;
    try{

     finalTmValue = dataValue.getAttribute('data-finalTmValue');
        if (finalTmValue !== null) {
            finalTmValue = parseFloat(finalTmValue); // 숫자로 변환
        }else {
            finalTmValue = 0;
        }
    }catch (error) {
        console.error("에러 발생: " + error.message);
    }

    event.target.pauseVideo();
    event.target.loadVideoById(ytbUrl, finalTmValue);
    //event.target.playVideo(); // 동영상 재생 시작 -> uri경로 들어가자마자 바로 영상 재생됨

    // 재생시간 중에서 5초를 뺀 값을 통해서 새로운 시간 값 얻기
    // 비디오의 마지막 부분에서 일정한 시간 간격으로 작업을 수행하기 위한 변수
    const runTm =
        document.querySelector('[data-runTm]')
            .getAttribute('data-runTm');
    console.log(runTm + "재생 시간");

    let MaxValue = document.querySelector('[data-maxTmValue]');
    let maxTm;
    try{

        maxTm = MaxValue.getAttribute('data-maxTmValue');
        if (maxTm !== null) {
            maxTm = parseFloat(maxTm); // 숫자로 변환
        }else {
            maxTm = 0;
        }
    }catch (error) {
        console.error("에러 발생: " + error.message);
    }

    // 재생 진행율
    progRt = (maxTm / runTm) * 100;

    console.log(progRt + "이값은뭐여ㅑ");
    // 시간기록하는 함수 실행(저장해야 되는 값)
    updatePosition()
}

// 시간마다 반복할 함수 저장용 -> 재생 중일 때 값을 받아서 인터벌(반복!) 저장
// 재생 중일 때 작성한 동작 실행하는 곳
let recordInterval;

function onPlayerStateChange(event) {
    console.log("onPlayerStateChange 실행3?");

    if (event.data == YT.PlayerState.PLAYING) {

        let MaxTmValue = document.querySelector('[data-maxTmValue]');
        let maxTm;
        try{

            maxTm = MaxTmValue.getAttribute('data-maxTmValue');
            if (maxTm !== null) {
                maxTm = parseFloat(maxTm); // 숫자로 변환
            }else {
                maxTm = 0;
            }
        }catch (error) {
            console.error("에러 발생: " + error.message);
        }
        runTm = event.target.getDuration() - 5;
        console.log(runTm + "3번 runtm?ok")
        console.log(maxTm + "얘를 못뽑아오나?ok")

        progRt = (maxTm / runTm) * 100;
        console.log("progRt를 변수 정의해주기?"+progRt)
        // maxTm null 처리
        //maxTm = maxTm === null ? 0 : maxTm;

        // getCurrentTime(): 현재 재생 위치(초)를 반환
        // Number : 주어진 값을 숫자로 변환
        // + 1 : 브라우저와 YouTube 플레이어의 정확한 동기화를 고려해 1초 정도의 여유를 두기
        if (event.target.getCurrentTime() > Number(maxTm) + 1) {
            // seekTo(seconds): 비디오를 지정된 초 위치로 이동
            event.target.seekTo(maxTm);
        }

        // 영상을 다 들은 경우
        if (event.target.getCurrentTime() >= runTm) {
            player.pauseVideo();
            player.seekTo(finalTm);
        }

        // interval clear 해주기
        if (recordInterval) clearInterval(recordInterval);

        // 5초마다 maxTm과 현재 시간을 저장 -> updatePosition:시간 기록하는 함수
        recordInterval = setInterval(updatePosition, 5000);

    }

    // 일시 정지 중에는 반복을 멈추기
    // 일시 정지한 시간을 기록
    if (event.data == YT.PlayerState.PAUSED) {

        clearInterval(recordInterval);
        // 현재 재생 시간이 maxTm에서 5초 이내에 있는 경우 updatePosition()함수 실행
        if (event.target.getCurrentTime() <= maxTm + 5) {
            updatePosition();
        }
    }

    // 영상이 끝나더라도 이전으로 되돌려서 일시 정지
    // 사용자가 영상의 끝으로 이동하는것을 따로 막기위해 필요함
    if (event.data == YT.PlayerState.ENDED) {
        event.target.seekTo(event.target.getDuration() - 1);
        event.target.pauseVideo();
    }
}

let progressNo;

// 시간 기록
function updatePosition() {
    console.log("updatePosition 실행4?");

    const nthNo =
        document.querySelector('[data-nthNo]')
            .getAttribute('data-nthNo');
    console.log(nthNo + "차시 번호");

    const memberNo =
        document.querySelector('[data-memberNo]')
            .getAttribute('data-memberNo');
    console.log(memberNo + "회원 번호");

    const contentNo =
        document.querySelector('[data-contentNo]')
            .getAttribute('data-contentNo');
    console.log(contentNo + "콘텐츠 번호");

    const lectureNo =
        document.querySelector('[data-lectureNo]')
            .getAttribute('data-lectureNo');
    console.log(lectureNo + "강의 번호");

    let MaxTmValues = document.querySelector('[data-maxTmValue]');
    let maxTm;
    try{

        maxTm = MaxTmValues.getAttribute('data-maxTmValue');
        if (maxTm !== null) {
            maxTm = parseFloat(maxTm); // 숫자로 변환
        }else {
            maxTm = 0;
        }
    }catch (error) {
        console.error("에러 발생: " + error.message);
    }
    console.log(runTm + "4번 runtm?거의다옴")
    console.log(maxTm + "얘를 못뽑아오나?maxTm거의다옴")

    //progRt = (maxTm / runTm) * 100;

    finalTm = player.getCurrentTime();
    // maxTm - 뒤로 영상 이동해도 finalTm값이 실행될 수 있도록 하기
    maxTm = maxTm > finalTm ? maxTm : finalTm;
    console.log("여기까지와야최종maxTm" + maxTm);
    console.log("여기까지와야최종runTm" + runTm);
    // toFixed(4) : 소수점이하 네자리까지 반올림
    progRtt = runTm >= maxTm ? (maxTm / runTm).toFixed(1) * 100 : 100;
console.log(progRtt + "진행률 ㄴ오세요");
    // data 객체 생성
    const data = {
        nthNo: nthNo,
        memberNo: memberNo,
        contentNo: contentNo,
        lectureNo: lectureNo,
        progressNo: progressNo,
        finalTm: finalTm,
        maxTm: maxTm,
        progRt: progRtt
    };

    // 서버로 데이터를 보내는 fetch 요청
    fetch('/api/admin/saveYoutubeTm', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
        })
        .catch(error => {
            console.error('Error:', error);
        });

}

//재생속도가 변경될 때 1을 초과하면 1로 변경
function onPlayerPlaybackRateChange(event) {
    console.log("onPlayerPlaybackRateChange 실행5?");
    // 현재 재생 속도 가져오기
    if (event.target.getPlaybackRate() > 1) {
        event.target.setPlaybackRate(1);
    }
}
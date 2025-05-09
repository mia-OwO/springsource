// axios 사용
// axios.get('경로(8080이후)').then(도착하는거)

// 댓글 삭제
// 삭제 버튼 클릭 시 data-rno 가져오기
// 날짜 포맷
const formatDate = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

const replyListElement = document.querySelector(".replyList");
const replyForm = document.querySelector("#replyForm");

const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);

    const data = res.data;

    // 댓글 수
    console.log("댓글 수", data.length);
    // 댓글 갯수 수정
    // document.querySelector("span").innerHTML = data.length;
    replyListElement.previousElementSibling.querySelector("span").innerHTML = data.length;
    let result = "";

    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
      result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
      result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div>`;
      result += `<div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
      result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      result += `</div></div>`;
    });
    replyListElement.innerHTML = result;
  });
};

document.querySelector(".replyList").addEventListener("click", (e) => {
  // 어느 버튼의 이벤트인가

  console.log(e.target);
  const btn = e.target;

  // rno 가져오기
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);

  // 삭제 or 수정

  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    axios.delete(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      // 댓글 다시 불러오기
      replyList();
    });
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정
    axios.get(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      // console.log(res.data.replyer);
      // console.log(res.data.text);

      // replyFrom 안에 보여주기

      const data = res.data;

      replyForm.rno.value = data.rno; // id는 어디서든 챙겨가기(html -> hidden)
      replyForm.replyer.value = data.replyer;
      replyForm.text.value = data.text;

      //헤헤 내코드
      // const replyForm = document.querySelector("#replyForm");
      // replyForm.replyer.value = res.data.replyer;
      // replyForm.text.textContent = res.data.text;
    });
  }
});

// 폼 submit => 수정 / 삽입
replyForm.addEventListener("submit", (e) => {
  e.preventDefault(); // 기본적으로 막아놓자

  const form = e.target;
  const rno = form.rno.value;

  // (e.target.rno.value)
  if (form.rno.value) {
    // 수정
    axios
      .put(`/replies/${rno}`, form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 수정 완료");

        // form 기존 내용 지우기
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        // 수정 내용 반영
        replyList();
      });
  } else {
    // 삽입
    axios
      .post("/replies/new", form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        alert(res.data + " 댓글 등록");
        // form 기존 내용 지우기
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        // 삽입 내용 반영
        replyList();
      });
  }
});

//페이지 로드시 호출
replyList();

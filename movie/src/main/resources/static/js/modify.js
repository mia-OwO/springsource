// 삭제 클릭시 removeForm 전송
document.querySelector(".move").addEventListener("click", (e) => {
  // a 태그는 움직여서 막아야 됨
  //a 태그 기능 중지
  e.preventDefault;
  document.querySelector("#removeForm").submit();
});

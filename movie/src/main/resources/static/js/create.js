// 등록 버튼을 누르면 폼 submit 중지
// li 태그 정보 수집 후 폼에 추가
// 폼 전송

document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  // li 정보 수집 영역 찾기(업로드 파일 정보)
  // all -> 배열 -> foreach 접근 가능
  const output = document.querySelectorAll(".uploadResult li");

  //속성 : . or getAttribute()
  // data- : . 안됨, dataset으로 접근
  let result = "";
  output.forEach((obj, idx) => {
    console.log(obj.dataset.uuid);

    // movieImages : movieDto의 이름과 맞춰야 담아줌
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}">`;
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
  });
  e.target.insertAdjacentHTML("beforeend", result);
  e.target.submit();
});

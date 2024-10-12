let isDuplicateChecked = false;

function updateSubmitButton() {
  const submitButton = document.querySelector("button[type='submit']");
  submitButton.disabled = !isDuplicateChecked; // 중복 확인 상태에 따라 활성화
}

function checkDuplicate() {
  const loginId = document.getElementById("loginId").value;

  if (!loginId) {
    alert("로그인 ID를 입력해주세요.");
    return;
  }

  fetch('/users/add/check?loginId=' + loginId)
  .then(response => response.json())
  .then(data => {
    if (data.duplicate) {
      alert("이미 사용 중인 로그인 ID입니다.");
      isDuplicateChecked = false;
    } else {
      alert("사용 가능한 로그인 ID입니다.");
      isDuplicateChecked = true;
    }
    updateSubmitButton();
  })
  .catch(error => {
    console.error("Error:", error);
    isDuplicateChecked = false;
    alert("중복 확인 중 오류가 발생했습니다.");
  });
}
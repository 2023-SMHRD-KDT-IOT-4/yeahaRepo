// 웹 페이지가 완전히 로드된 후 실행될 함수를 정의합니다. 이 함수는 설문조사의 첫 번째 질문을 화면에 표시합니다.
document.addEventListener("DOMContentLoaded", () => {
  displayQuestion(); // 설문 질문을 화면에 표시하는 함수를 호출합니다.
});

// 설문 질문들의 배열입니다. 각 질문은 질문 내용, 선택 옵션, 관련 이미지 URL, 질문 유형을 포함합니다.
const questions = [
  // 첫 번째 질문
  {
    question: "Q1.",
    options: ["O", "X"], // 선택지
    image: "https://view01.wemep.co.kr/wmp-product/3/267/1929002673/pm_9px7e6wcrmbh.jpg?1668472671",
    type: "choice", // 질문 유형 (이 경우 선택형)
  },
  // 두 번째 질문
  {
    question: "Q2.",
    options: ["O", "X"],
    image: "https://cdn-icons-png.flaticon.com/512/2408/2408097.png",
    type: "choice",
  },
  // 세 번째 질문
  {
    question: "Q3.",
    options: ["1", "2", "3"],
    image: "https://images.freeimages.com/clg/images/16/169993/emoticons-question-face_f",
    type: "choice",
  },
  // 네 번째 질문
  {
    question: "Q4.",
    options: ["O", "X"],
    image: "https://t1.daumcdn.net/cfile/blog/2418864F5407EBF219",
    type: "choice",
  },
  // 다섯 번째 질문
  {
    question: "Q5.",
    options: ["1", "2", "3", "4"],
    image: "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  // 필요에 따라 추가 질문을 이 배열에 포함시킬 수 있습니다.
];

// 현재 사용자가 답변하고 있는 질문의 인덱스를 추적합니다.
let currentQuestionIndex = 0;
// 사용자의 응답을 저장하는 배열입니다.
let answers = [];

// 현재 질문을 화면에 표시하는 함수입니다.
function displayQuestion() {
  // 질문을 표시할 컨테이너의 DOM 요소를 선택합니다.
  const questionContainer = document.getElementById("question-container");
  if (!questionContainer) return; // 컨테이너가 없으면 함수를 종료합니다.

  const questionData = questions[currentQuestionIndex]; // 현재 질문 데이터를 가져옵니다.

  questionContainer.innerHTML = ""; // 컨테이너의 내용을 초기화합니다.
  questionContainer.style.opacity = 0; // 페이드 효과를 위해 투명도를 0으로 설정합니다.

  setTimeout(() => {
    const questionElement = document.createElement("h2"); // 질문 텍스트를 담을 요소를 생성합니다.
    questionElement.textContent = questionData.question; // 질문 텍스트를 설정합니다.
    questionContainer.appendChild(questionElement); // 생성한 요소를 컨테이너에 추가합니다.

    // 질문 유형에 따라 다른 처리를 수행합니다.
    if (questionData.type === "choice") {
      questionData.options.forEach((option) => { // 각 옵션에 대해 버튼을 생성합니다.
        const button = document.createElement("button");
        button.className = "btn"; // 스타일링을 위한 클래스를 추가합니다.
        button.textContent = option; // 버튼 텍스트를 옵션 값으로 설정합니다.
        button.onclick = () => selectOption(option); // 버튼 클릭 시 해당 옵션을 선택 처리합니다.
        questionContainer.appendChild(button); // 버튼을 컨테이너에 추가합니다.
      });
    }

    questionContainer.style.opacity = 1; // 컨테이너의 투명도를 1로 설정하여 질문이 서서히 나타나게 합니다.

    updateProgress(); // 진행 상황을 업데이트하는 함수를 호출합니다.

    // 현재 질문의 이미지를 업데이트합니다.
    const imageElement = document.querySelector(".answer-sheet-image");
    if (questionData.image) {
      imageElement.src = questionData.image; // 이미지 URL을 설정합니다.
      imageElement.alt = "Question Image"; // 대체 텍스트를 설정합니다.
    }
  }, 500); // 컨테이너의 내용을 업데이트하는 데 소요되는 시간을 고려하여 지연시킵니다.
}

// 사용자가 옵션을 선택했을 때 호출되는 함수입니다.
function selectOption(answer) {
  answers[currentQuestionIndex] = answer; // 선택한 답변을 배열에 저장합니다.
  if (currentQuestionIndex < questions.length - 1) {
    currentQuestionIndex++; // 다음 질문으로 인덱스를 이동합니다.
    displayQuestion(); // 다음 질문을 표시합니다.
  } else {
    displayResults(); // 모든 질문에 답변했다면 결과를 표시합니다.
  }
}

// 설문 진행 상황을 업데이트하는 함수입니다.
function updateProgress() {
  const progressText = document.getElementById("progress-text"); // 진행 상황 텍스트 요소를 선택합니다.
  const progressBar = document.getElementById("progress-bar"); // 진행 상황 바 요소를 선택합니다.
  const progressPercentage = ((currentQuestionIndex + 1) / questions.length) * 100; // 진행률을 계산합니다.
  progressText.textContent = `Question ${currentQuestionIndex + 1} of ${questions.length}`; // 진행 상황 텍스트를 업데이트합니다.
  progressBar.style.width = `${progressPercentage}%`; // 진행률에 따라 바의 너비를 조정합니다.
}

// 설문조사 결과를 화면에 표시하는 함수입니다.
function displayResults() {
  const surveyContainer = document.getElementById("survey-container"); // 결과를 표시할 컨테이너를 선택합니다.
  if (!surveyContainer) return; // 컨테이너가 없으면 함수를 종료합니다.

  surveyContainer.innerHTML = '<h2>Survey Complete</h2><div id="results"></div>'; // 결과 표시를 위한 초기 구조를 설정합니다.
  const resultsDiv = document.getElementById("results"); // 결과를 담을 div 요소를 선택합니다.
  answers.forEach((answer, index) => { // 각 질문과 답변에 대해 반복하여 결과를 구성합니다.
    const questionTitle = document.createElement("h3");
    questionTitle.textContent = `Question ${index + 1}: ${questions[index].question}`; // 질문 제목을 설정합니다.
    const answerText = document.createElement("p");
    answerText.textContent = `Answer: ${answer}`; // 선택한 답변을 표시합니다.
    resultsDiv.appendChild(questionTitle); // 결과 div에 질문 제목을 추가합니다.
    resultsDiv.appendChild(answerText); // 결과 div에 답변 텍스트를 추가합니다.
  });
  const submitButton = document.createElement("button"); // 설문 결과 제출 버튼을 생성합니다.
  submitButton.textContent = "설문 결과 제출"; // 버튼 텍스트를 설정합니다.
  submitButton.className = "btn"; // 스타일링을 위한 클래스를 추가합니다.
  submitButton.onclick = () => submitSurveyResults(answers); // 버튼 클릭 시 설문 결과를 서버로 전송하는 함수를 호출합니다.
  surveyContainer.appendChild(submitButton); // 컨테이너에 버튼을 추가합니다.
}

// 설문조사 결과를 서버로 전송하는 함수입니다.
function submitSurveyResults(surveyResults) {
  // 서버로 전송할 데이터를 포맷합니다.
  const formattedResults = {
    user_id: "사용자 아이디 혹은 고유 식별자",
    value1: surveyResults[0],
    value2: surveyResults[1],
    // 필요한 나머지 값들도 여기에 추가합니다.
  };

  // Fetch API를 사용하여 서버에 POST 요청을 보냅니다.
  fetch("http://localhost:3000/submit-survey", { // 서버url
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formattedResults),
  })
    .then((response) => response.json()) // 응답을 JSON 형태로 변환합니다.
    .then((data) => {
      alert("설문 조사 결과가 성공적으로 저장되었습니다."); // 성공적으로 저장되었음을 알립니다.
    })
    .catch((error) => {
      alert("설문 조사 결과를 저장하는데 실패했습니다."); // 저장에 실패했음을 알립니다.
    });
}

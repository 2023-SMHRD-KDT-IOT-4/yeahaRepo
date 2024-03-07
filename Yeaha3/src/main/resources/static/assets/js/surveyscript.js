// 웹 페이지가 완전히 로드된 후 실행될 함수를 정의합니다. 이 함수는 설문조사의 첫 번째 질문을 화면에 표시합니다.
document.addEventListener("DOMContentLoaded", () => {
  displayQuestion(); // 설문 질문을 화면에 표시하는 함수를 호출합니다.
});

// 설문 질문들의 배열입니다. 각 질문은 질문 내용, 선택 옵션, 관련 이미지 URL, 질문 유형을 포함합니다.
const questions = [
  // 첫 번째 질문
  {
    question: "흉통유형",
    options: ["0", "1", "2", "3"], // 선택지
    image:
      "https://view01.wemep.co.kr/wmp-product/3/267/1929002673/pm_9px7e6wcrmbh.jpg?1668472671",
    type: "choice", // 질문 유형 (이 경우 선택형)
  },
  // 두 번째 질문
  {
    question: "안정기혈압",
    options: [],
    image: "https://cdn-icons-png.flaticon.com/512/2408/2408097.png",
    type: "number",
  },
  // 세 번째 질문
  {
    question: "총콜레스테롤",
    options: ["1", "2", "3"],
    image:
      "https://images.freeimages.com/clg/images/16/169993/emoticons-question-face_f",
    type: "number",
  },
  // 네 번째 질문
  {
    question: "공복혈당",
    options: ["0", "1"],
    image: "https://t1.daumcdn.net/cfile/blog/2418864F5407EBF219",
    type: "choice",
  },
  // 다섯 번째 질문
  {
    question: "안정기심전도",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  // 숫자를 입력받는 질문 추가
  {
    question: "최대심박수",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "협심증유발운동",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "st우울증 위험도",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "피크운동 st 세그먼트의 기울기",
    options: ["0", "1", "2"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "주요혈관수",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "고통형태",
    options: ["0", "1", "2"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "임신횟수",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "포도당",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "혈압",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "피부두께(인슐린저항성)",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "인슐린",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "BMI",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "당뇨혈통",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "고혈압여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "심장병여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "이혼여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "직업타입",
    options: ["0", "1", "2", "3", "4"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "거주타입",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "평균혈당",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "흡연여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "빈혈여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "효소수치",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "당뇨여부",
    options: ["0", "1"],
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g",
    type: "choice",
  },
  {
    question: "심장기능",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "혈소판수치",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "혈청크레아니틴",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
  },
  {
    question: "혈청나트륨",
    options: [], // 숫자 입력 질문은 옵션이 필요 없습니다.
    image:
      "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7mJ/image/kTXkf7bTWMQSOw4b3eiBNGXY87g", // 선택적으로 이미지 URL을 비워둘 수 있습니다.
    type: "number", // 질문 유형을 "number"로 설정합니다.
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
      questionData.options.forEach((option) => {
        // 각 옵션에 대해 버튼을 생성합니다.
        const button = document.createElement("button");
        button.className = "btn"; // 스타일링을 위한 클래스를 추가합니다.
        button.textContent = option; // 버튼 텍스트를 옵션 값으로 설정합니다.
        button.onclick = () => selectOption(option); // 버튼 클릭 시 해당 옵션을 선택 처리합니다.
        questionContainer.appendChild(button); // 버튼을 컨테이너에 추가합니다.
      });
    }
    // 질문 유형이 'number'일 경우의 처리를 추가합니다.
    if (questionData.type === "number") {
      const input = document.createElement("input");
      input.type = "number"; // 입력 타입을 숫자로 설정합니다.
      input.className = "input-number"; // 스타일링을 위한 클래스를 추가합니다.
      questionContainer.appendChild(input); // 입력 필드를 컨테이너에 추가합니다.

      const submitButton = document.createElement("button"); // 입력 값을 제출하는 버튼을 생성합니다.
      submitButton.textContent = "제출"; // 버튼 텍스트를 설정합니다.
      submitButton.className = "btn submit-btn"; // 스타일링을 위한 클래스를 추가합니다.
      submitButton.onclick = () => selectOption(input.value); // 버튼 클릭 시 입력 값을 선택 처리합니다.
      questionContainer.appendChild(submitButton); // 버튼을 컨테이너에 추가합니다.
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
  const progressPercentage =
    ((currentQuestionIndex + 1) / questions.length) * 100; // 진행률을 계산합니다.
  progressText.textContent = `Question ${currentQuestionIndex + 1} of ${
    questions.length
  }`; // 진행 상황 텍스트를 업데이트합니다.
  progressBar.style.width = `${progressPercentage}%`; // 진행률에 따라 바의 너비를 조정합니다.
}

function getSurveyResultsJson(answers) {
  const results = {};
  answers.forEach((answer, index) => {
    results[`question${index + 1}`] = answer;
  });
  return JSON.stringify(results);
}

// 설문조사 결과를 화면에 표시하는 함수입니다.
function displayResults() {
  const surveyContainer = document.getElementById("survey-container"); // 결과를 표시할 컨테이너를 선택합니다.
  if (!surveyContainer) return; // 컨테이너가 없으면 함수를 종료합니다.

  surveyContainer.innerHTML =
    '<h2>Survey Complete</h2><div id="results"></div>'; // 결과 표시를 위한 초기 구조를 설정합니다.
  const resultsDiv = document.getElementById("results"); // 결과를 담을 div 요소를 선택합니다.
  answers.forEach((answer, index) => {
    // 각 질문과 답변에 대해 반복하여 결과를 구성합니다.
    const questionTitle = document.createElement("h3");
    questionTitle.textContent = `Question ${index + 1}: ${
      questions[index].question
    }`; // 질문 제목을 설정합니다.
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

  submitButton.onclick = () => {
    const surveyResultsJson = getSurveyResultsJson(answers);
    console.log("설문 결과 JSON:", surveyResultsJson); // 콘솔에 JSON 출력

    // 추가적으로 다음과 같은 작업을 수행할 수 있습니다.

    // 1. JSON 데이터를 다운로드 링크로 제공합니다.
    const downloadLink = document.createElement("a");
    downloadLink.href =
      "data:application/json;charset=utf-8," + surveyResultsJson;
    downloadLink.download = "survey-results.json";
    downloadLink.click();

    // 2. JSON 데이터를 서버로 전송합니다.
    // ...

    // 3. 다른 웹 페이지로 전송합니다.
    // ...
  };
}

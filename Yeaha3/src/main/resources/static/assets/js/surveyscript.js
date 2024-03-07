document.addEventListener("DOMContentLoaded", () => {
  displayQuestion();
});

const questions = [
  {
    question: "본인의 키를 입력해주세요 (cm)",
    options: [],
    image: "키 이미지 URL",
    type: "number",
  },
  {
    question: "몸무게 (kg)",
    options: [],
    image: "몸무게 이미지 URL",
    type: "number",
  },
  {
    question: "이완기혈압",
    options: [],
    image: "이완기혈압 이미지 URL",
    type: "number",
  },
  {
    question: "총콜레스테롤",
    options: ["1", "2", "3"],
    image: "총콜레스테롤 이미지 URL",
    type: "number",
  },
  {
    question: "식전혈당(공복혈당)",
    options: ["0", "1"],
    image: "식전혈당 이미지 URL",
    type: "choice",
  },
  {
    question: "흡연상태",
    options: ["흡연", "비흡연"],
    type: "choice",
  },
];

let currentQuestionIndex = 0;
let answers = [];

function displayQuestion() {
  const questionContainer = document.getElementById("question-container");
  if (!questionContainer) return;

  const questionData = questions[currentQuestionIndex];

  questionContainer.innerHTML = "";
  questionContainer.style.opacity = 0;

  setTimeout(() => {
    const questionElement = document.createElement("h2");
    questionElement.textContent = questionData.question;
    questionContainer.appendChild(questionElement);

    if (questionData.type === "choice") {
      questionData.options.forEach((option) => {
        const button = document.createElement("button");
        button.className = "btn";
        button.textContent = option;
        button.onclick = () => selectOption(option);
        questionContainer.appendChild(button);
      });
    }

    if (questionData.type === "number") {
      const input = document.createElement("input");
      input.type = "number";
      input.className = "input-number";
      questionContainer.appendChild(input);

      const submitButton = document.createElement("button");
      submitButton.textContent = "제출";
      submitButton.className = "btn submit-btn";
      submitButton.onclick = () => selectOption(input.value);
      questionContainer.appendChild(submitButton);
    }

    questionContainer.style.opacity = 1;

    updateProgress();

    const imageElement = document.querySelector(".answer-sheet-image");
    if (questionData.image) {
      imageElement.src = questionData.image;
      imageElement.alt = "Question Image";
    }
  }, 500);
}

function selectOption(answer) {
  answers[currentQuestionIndex] = answer;

  if (currentQuestionIndex < questions.length - 1) {
    currentQuestionIndex++;
    displayQuestion();
  } else {
    displayResults();
  }
}

function updateProgress() {
  const progressText = document.getElementById("progress-text");
  const progressBar = document.getElementById("progress-bar");
  const progressPercentage =
    ((currentQuestionIndex + 1) / questions.length) * 100;
  progressText.textContent = `Question ${currentQuestionIndex + 1} of ${questions.length}`;
  progressBar.style.width = `${progressPercentage}%`;
}

function getSurveyResultsJson(answers) {
  const results = {};
  answers.forEach((answer, index) => {
    results[`question${index + 1}`] = answer;
  });
  return JSON.stringify(results);
}

function displayResults() {
  const surveyContainer = document.getElementById("survey-container");
  if (!surveyContainer) return;

  surveyContainer.innerHTML =
    '<h2>Survey Complete</h2><div id="results"></div>';
  const resultsDiv = document.getElementById("results");
  answers.forEach((answer, index) => {
    const questionTitle = document.createElement("h3");
    questionTitle.textContent = `Question ${index + 1}: ${questions[index].question}`;
    const answerText = document.createElement("p");
    answerText.textContent = `Answer: ${answer}`;
    resultsDiv.appendChild(questionTitle);
    resultsDiv.appendChild(answerText);
  });
  const submitButton = document.createElement("button");
  submitButton.textContent = "설문 결과 제출";
  submitButton.className = "btn";
  submitButton.onclick = () => submitSurveyResults(answers);
  surveyContainer.appendChild(submitButton);
}

function convertSmokingStatusToNumber(smokingStatus) {
  return smokingStatus === '흡연' ? 1 : 0;
}

function calculateBMI(height, weight) {
  const heightInMeters = height / 100;
  const bmi = weight / (heightInMeters * heightInMeters);
  return bmi.toFixed(2);
}

function getSurveyResultsJson(answers) {
  const email = sessionData.email;
  const gender = sessionData.gender;
  const age = sessionData.age;

  const results = {
    email,
    gender,
    age,
    height: answers[0],
    weight: answers[1],
    bloodPressure: answers[2],
    chol: answers[3],
    glucose: answers[4],
    smokingStatus: convertSmokingStatusToNumber(answers[5]),
    bmi: calculateBMI(parseInt(answers[0]), parseInt(answers[1])),
  };

  const jsonString = JSON.stringify(results);

  return jsonString;
}

function submitSurveyResults(answers) {
  const surveyResponse = {
    email: sessionData.email,
    gender: sessionData.gender,
    age: sessionData.age,
    height: parseInt(answers[0]),
    weight: parseInt(answers[1]),
    bloodPressure: parseInt(answers[2]),
    chol: parseInt(answers[3]),
    glucose: parseInt(answers[4]),
    smokingStatus: convertSmokingStatusToNumber(answers[5]),
    bmi: calculateBMI(parseInt(answers[0]), parseInt(answers[1])),
  };
   const serverUrl1 = "http://localhost:8087/surveyres";
  const serverUrl2 = "http://170.30.1.53:5500/iniAlgo";

  // 두 개의 서버로 동시에 GET 요청을 보냅니다.
    Promise.all([
    fetch(serverUrl1, {
      method: "POST",
      headers: {
        "Content-Type": "application/json", // 요청 바디의 타입을 JSON으로 설정
      },
      body: JSON.stringify(surveyResponse), // surveyResponse 객체를 JSON 문자열로 변환하여 전송
      mode: 'cors',
    }),
    fetch(serverUrl2, {
      method: "POST",
      headers: {
        "Content-Type": "application/json", // 요청 바디의 타입을 JSON으로 설정
      },
      body: JSON.stringify(surveyResponse), // surveyResponse 객체를 JSON 문자열로 변환하여 전송
      mode: 'cors',
    }),
  ])
    .then(responses => {
      const results = responses.map(response => response.json());
      return Promise.all(results);
    })
    .then(data => {
      console.log("서버 응답:", data);
    })
    .catch(error => {
      console.error("서버 요청 실패:", error);
    });

}

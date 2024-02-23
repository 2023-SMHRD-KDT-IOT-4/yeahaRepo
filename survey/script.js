document.addEventListener("DOMContentLoaded", () => {
  displayQuestion();
});

const questions = [
  {
    question: "성별을 선택하세요.",
    options: ["남성", "여성"],
    image:
      "https://img.freepik.com/premium-vector/gender-symbol-meaning-of-sex-and-equality-of-males-and-females_68708-503.jpg",
    type: "choice",
  },
  {
    question: "나이를 입력하세요.",
    type: "input",
    inputType: "number",
    image: "https://cdn.mhns.co.kr/news/photo/201409/1811_3235_2056.jpg",
  },
  {
    question: "키가 얼마나 되나요? (cm)",
    type: "input",
    inputType: "number",
    image:
      "https://www.shutterstock.com/image-vector/height-man-vector-icon-scale-260nw-2184591037.jpg", // Update with an appropriate image path
  },
  {
    question: "몸무게가 얼마나 되나요? (kg)",
    type: "input",
    inputType: "number",
    image:
      "https://media.istockphoto.com/id/1368595082/ko/%EB%B2%A1%ED%84%B0/%EB%AC%B4%EA%B2%8C-%EA%B0%80%EB%8A%98%EA%B3%A0-%EB%AA%B8%EB%AC%B4%EA%B2%8C%EC%97%90-%EC%84%9C-%EC%9E%88%EB%8A%94-%EC%82%AC%EB%9E%8C.jpg?s=612x612&w=0&k=20&c=NX0QjcO9Is1imrYpHXpzkGuLMdvQnkiGFE1q_A_jV8U=", // Update with an appropriate image path
  },
  // Extend with more questions as needed
];

let currentQuestionIndex = 0;
let answers = [];

function displayQuestion() {
  const questionContainer = document.getElementById("question-container");
  if (!questionContainer) return; // questionContainer가 없으면 함수 종료

  const questionData = questions[currentQuestionIndex];

  questionContainer.innerHTML = ""; // Clear previous content
  questionContainer.style.opacity = 0; // Initialize for fade effect

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
    } else if (questionData.type === "input") {
      const input = document.createElement("input");
      input.type = questionData.inputType;
      input.className = "input";
      const submitButton = document.createElement("button");
      submitButton.textContent = "Submit";
      submitButton.className = "btn";
      submitButton.onclick = () => {
        selectOption(input.value);
        input.value = ""; // Clear input after submission
      };
      questionContainer.appendChild(input);
      questionContainer.appendChild(submitButton);
    }

    // Fade in the container with new content
    questionContainer.style.opacity = 1;

    // Update the progress bar
    updateProgress();

    // Optionally update the image for the current question
    const imageElement = document.querySelector(".answer-sheet-image");
    if (questionData.image) {
      imageElement.src = questionData.image;
      imageElement.alt = "Question Image";
    }
  }, 500); // Match this delay with CSS opacity transition duration
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
  progressText.textContent = `Question ${currentQuestionIndex + 1} of ${
    questions.length
  }`;
  progressBar.style.width = `${progressPercentage}%`;
}

function displayResults() {
  const surveyContainer = document.getElementById("survey-container");
  if (!surveyContainer) return; // surveyContainer가 없으면 함수 종료
  surveyContainer.innerHTML =
    '<h2>Survey Complete</h2><div id="results"></div>';
  const resultsDiv = document.getElementById("results");
  answers.forEach((answer, index) => {
    const questionTitle = document.createElement("h3");
    questionTitle.textContent = `Question ${index + 1}: ${
      questions[index].question
    }`;
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

// 설문 조사 결과를 서버로 전송하는 함수
function submitSurveyResults(surveyResults) {
  // 서버로 전송할 데이터를 데이터베이스 스키마에 맞게 포맷
  const formattedResults = {
    user_id: "사용자 아이디 혹은 고유 식별자", // 사용자 고유 식별자 설정 필요
    value1: surveyResults[0],
    value2: surveyResults[1],
    // 나머지 value 필드도 마찬가지로 채워야 합니다.
  };

  // Fetch API를 사용하여 서버에 데이터 전송
  fetch("http://localhost:3000/submit-survey", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formattedResults),
  })
    .then((response) => response.json())
    .then((data) => {
      alert("설문 조사 결과가 성공적으로 저장되었습니다.");
    })
    .catch((error) => {
      alert("설문 조사 결과를 저장하는데 실패했습니다.");
    });
}

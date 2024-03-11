document.addEventListener("DOMContentLoaded", () => {
  displayQuestion();
  console.log("Session Data:", sessionData);

});

const questions = [
  {
    question: "본인의 키를 입력해주세요 (cm)",
    options: [],
    image: "https://static.vecteezy.com/system/resources/previews/001/760/138/original/little-boy-measures-height-free-vector.jpg",
    type: "number",
  },
  {
    question: "몸무게를 입력해 주세요 (kg)",
    options: [],
    image: "https://www.bhf.org.uk/-/media/images/information-support/heart-matters/2018/october-2018/nutrition/best-way-to-measure-body-fat/weight_scales_man_ss_0118_noexp_620x400.jpg?rev=f9f8640517174b1c8cc53df692b032c5&hash=CB6EB85737301885431E12938A5E4EBC",
    type: "number",
  },
  {
    question: "혈압을 입력해 주세요",
    options: [],
    image: "data:image/jpeg;base64,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",
    type: "number",
  },
  {
    question: "총콜레스테롤을 입력해 주세요",
    options: ["1", "2", "3"],
    image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWgC_RzgnhjhtzlRkkl-NV9xW-Y9OnOl5zhg&usqp=CAU",
    type: "number",
  },
  {
    question: "공복혈당을 입력해 주세요",
    options: ["0", "1"],
    image: "https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/health/wp-content/uploads/2023/01/blood_sugar.jpeg.jpg",
    type: "choice",
  },
  {
    question: "흡연여부를 입력해 주세요",
    options: ["흡연", "비흡연"],
    image: "https://www.healthy-heart-guide.com/wp-content/uploads/2015/04/smoking-and-heart-disease.jpeg",
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
    user_email: sessionData.email,
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
  const serverUrl2 = "";

  try {
    // 두 개의 서버로 동시에 POST 요청을 보냅니다.
    Promise.all([
      fetch(serverUrl1, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(surveyResponse),
        mode: 'cors',
      }),
      fetch(serverUrl2, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(surveyResponse),
        mode: 'cors',
      }),
    ])
      .then(responses => Promise.all(responses.map(response => response.json())))
      .then(data => {
        console.log("서버 응답:", data);

        // 두 요청 중 하나라도 성공하면
        if (data[0].success || data[1].success) {
          console.log("서버 중 하나로의 설문 결과가 성공적으로 제출되었습니다.");
        } else {
          console.error("모든 서버 요청이 실패했습니다.");
        }
      })
      .catch(error => {
        console.error("서버 응답 처리 중 오류가 발생했습니다:", error);
      })
      .finally(() => {
        // 항상 페이지로 이동합니다.
        const queryString = `?email=${encodeURIComponent(sessionData.email)}`;
        const redirectUrl = `http://localhost:8087/iniAlgo${queryString}`;
        
        console.log("이메일 쿼리스트링 이동 확인");
         // 지정된 URL로 리다이렉션합니다.
        window.location.href = redirectUrl;
        console.log("다음으로 리다이렉션 중:", redirectUrl);
      });
  } catch (error) {
    console.error("서버 요청 중 오류가 발생했습니다:", error);
  }
}

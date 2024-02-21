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
    image: "https://www.shutterstock.com/image-vector/height-man-vector-icon-scale-260nw-2184591037.jpg" // Update with an appropriate image path
}
  // Extend with more questions as needed
];

let currentQuestionIndex = 0;
let answers = [];

function displayQuestion() {
  const questionContainer = document.getElementById("question-container");
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
}

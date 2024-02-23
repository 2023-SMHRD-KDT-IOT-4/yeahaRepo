const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
const port = 3002;

app.use(bodyParser.json());

const connection = mysql.createConnection({
  host: 'project-db-campus.smhrd.com',
  user: 'yeaha',
  password: '1234',
  database: 'hunmin'
});

connection.connect();

app.post('/submit-survey', (req, res) => {
  const surveyResults = req.body;

  const query = 'INSERT INTO 설문_테이블 SET ?';
  connection.query(query, surveyResults, (error, results) => {
    if (error) {
      return res.status(500).json({ success: false, message: '데이터베이스에 데이터를 저장하는데 문제가 발생했습니다.', error });
    }
    res.json({ success: true, message: '설문 조사 결과가 성공적으로 저장되었습니다.' });
  });
});

app.listen(port, () => {
  console.log(`서버가 http://localhost:${port}에서 실행중입니다.`);
});

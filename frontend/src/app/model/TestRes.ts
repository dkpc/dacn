export class TestRes {
    headers: object;
    body: bodyRes;
    statusCode: string;
    statusCodeValue: number;
  }
  
  export class bodyRes{
    id: number;
    studentId: number;
    examId: number;
    answer: string;
    mark: number
  }
export class LoginRes {
  user: User;
  console: string;
}

export class User{
  id: number;
  username: string;
  password: string;
  name: string;
  studentId: number;
  className: string;
  assignedMachineId: number;
  role: string;
  machineUsername: string;
  machinePassword: string;
}

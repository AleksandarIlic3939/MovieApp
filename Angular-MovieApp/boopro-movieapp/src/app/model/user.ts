export class User {
    email: string;
    password: string;
    repeatPassword: string;
    roleId: number;

    constructor(email: string, password: string, repeatPassword: string, roleId: number) { }
}
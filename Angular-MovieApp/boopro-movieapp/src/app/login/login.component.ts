import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { JwtClientService } from '../service/jwt-client.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User;
  response: any;
  errorMessage = "";
  errorPassword = "";
  errorEmail = "";

  constructor(private jwtClient: JwtClientService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(email: string, password: string) {
    this.jwtClient.generateToken({"email": email, "password": password}).subscribe((data: any) => {
      sessionStorage.setItem("token", data.toString());
      this.router.navigate(['movie-list/1']);
      (error: any) => this.checkError(error.status);
    });
  }

  public checkError(status: any) {
    if(status == '403') {
      this.errorMessage = 'Error: Access Denied. Bad Credentials';
    } else if (status == '422') {
      this.errorEmail = 'Error: Account with that email does not exist';
    } else if (status == '401') {
      this.errorPassword = 'Error: Account exists but the password is not correct';
    } else {
      this.errorMessage = 'Error: An error occured';
    }
  }

  public goToRegister() {
    this.router.navigate(['register']);
  }

}
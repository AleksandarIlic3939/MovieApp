import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { JwtClientService } from '../service/jwt-client.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;
  private authRequest: any;
  response: any;
  errorMessage = "";

  constructor(private jwtClient: JwtClientService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(email: string, password: string, repeatPassword: string) {
    this.user = new User(email, password, repeatPassword, 2);
    this.getAccessToken(this.user, `/users/create`);
  }

  public getAccessToken(user: any, path: string) {
    let response = this.jwtClient.generateToken(user);
    response.subscribe(data => this.accessApi(data, path));
  }

  public accessApi(token: any, path: string) {
    this.jwtClient.saveUser(token, path, this.user).subscribe(data => {
      console.log(data);
      if(data != '') {
        this.errorMessage = '';
        this.router.navigate(['login']);
      }
    }, error => {
      if (error.status == 500) {
        this.errorMessage = 'User already exists';
      } else if (error.status == 404) {
        this.errorMessage = 'Error: An error occured';
      }
      console.log(error.status);
    });
  }

  public goToLogin() {
    this.router.navigate(['login']);
  }

}
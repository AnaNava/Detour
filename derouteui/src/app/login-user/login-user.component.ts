import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.css']
})
export class LoginUserComponent implements OnInit {

  loginForm: FormGroup;
  constructor(private route: ActivatedRoute, private router: Router,private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.loginForm = this.formBuilder.group({
      'username' : [null, Validators.nullValidator],
      'password' : [null, Validators.nullValidator]
  });

  }

  signIn()
  {
    this.router.navigate(['/pricing']);
  }
}

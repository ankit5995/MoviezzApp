import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Observable, Subscriber } from 'rxjs';
import { MessageService } from '../message.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})


export class RegisterComponent {
  constructor(private authService: AuthService, private snackBar: MatSnackBar, private router: Router, private messageService: MessageService) { }
  registerationForm = new FormGroup({
    "userName": new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(21)]),
    'password': new FormControl('', [Validators.required, Validators.minLength(5)]),
    'emailId': new FormControl('', [Validators.required, Validators.email, Validators.pattern('^[a-z0-9+._-]+@[a-z]+\.[a-z]{2,3}')]),
    'phoneNumber': new FormControl('', [Validators.required, Validators.pattern(/^[789]\d{9,9}$/)]),
    "profileUrl": new FormControl('', [Validators.required])
  })

  get userName() {
    return this.registerationForm.controls['userName']
  }
  get phoneNumber() {
    return this.registerationForm.controls['phoneNumber']
  }
  get emailId() {
    return this.registerationForm.controls['emailId']
  }
  get password() {
    return this.registerationForm.controls['password']
  }


  respdata: any
  myImage!: Observable<any>;
  base64code!: any;


  register() {
    console.log(this.registerationForm);
    this.authService.register(this.registerationForm.value).subscribe({
      next: (response: any) => {
        this.messageService.provideMessages('Registered Successfully !! :))', 'success');
        this.router.navigateByUrl("/login");
      },
      error: (error) => {
        this.messageService.provideMessages('user Already exists!!', 'exists');

      }
    });
  }

  changeImage(e: any) {
    this.profileUrl!.setValue(e.target.value, {
      onlySelf: true
    })
  }

  get profileUrl() {
    return this.registerationForm.get('profileUrl');
  }

  onChange($event: Event, e: any) {
    const target = $event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    console.log(file);
    this.convertToBase64(file);
  }


  convertToBase64(file: File) {
    const observable = new Observable((subscriber: Subscriber<any>) => {
      this.readFile(file, subscriber);
    })
    observable.subscribe((d) => {
      this.profileUrl!.setValue(d);
      console.log("key " + d);
    }
    )
  }


  readFile(file: File, subscriber: Subscriber<any>) {
    const filereader = new FileReader();
    filereader.readAsDataURL(file);
    filereader.onload = () => {
      subscriber.next(filereader.result);
      subscriber.complete();
    }
    filereader.onerror = () => {
      subscriber.error();
      subscriber.complete();

    }
  }

}




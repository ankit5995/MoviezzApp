import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private snackBar:MatSnackBar) { }

  provideMessages(displayMessage:string,text:string){
    this.snackBar.open(displayMessage,text,{
      duration:5000,
      horizontalPosition:'center',
      verticalPosition:'bottom'
    });
  }
}

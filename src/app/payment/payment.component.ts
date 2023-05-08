import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MovieService } from '../movie.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  constructor(private activateRoute: ActivatedRoute, private route: Router, private movieService: MovieService) {
  }


  payForm = new FormGroup({
  })


  id = "";

  ngOnInit(): void {
    this.activateRoute.paramMap.subscribe(param => {
      this.id = param.get("id") ?? ""
    })
    this.getUserDetails()
  };

  userdetails:any;
  getUserDetails(){
    this.movieService.getUserDetails().subscribe(data=>{
      this.userdetails=data;

    })
  }

  response:any;
  submit(){
    console.log(this.userdetails.emailId);
    alert("you are subscribed with the exclusive plan of "+this.id);

    this.movieService.sendPaymentEmail(this.userdetails.emailId).subscribe(data=>{
      this.response=data;
      console.log("mail response: "+data);
    })
    this.route.navigateByUrl('home');
  }
}

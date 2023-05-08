import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent {

  constructor(private router:Router) {
  }

  navigateTo(id: any) {
    this.router.navigate(['/payment', id]);
  }
}
